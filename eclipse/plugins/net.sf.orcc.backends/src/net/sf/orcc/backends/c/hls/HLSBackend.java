/*
 * Copyright (c) 2012, IRISA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IRISA nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.hls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.TypeResizer;
import net.sf.orcc.backends.util.BackendUtil;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * C backend targeting high-level synthesis tools
 * 
 * @author Herve Yviquel
 */
public class HLSBackend extends CBackend {

	/**
	 * Path to target "src" folder
	 */
	private String srcPath;

	/**
	 * Path to target "testBench" folder
	 */
	private String testBenchPath;

	/**
	 * Configuration mapping
	 */
	protected Map<String, List<Instance>> targetToInstancesMap;

	@Override
	protected void doInitializeOptions() {

		new File(path + File.separator + "build").mkdirs();
		new File(path + File.separator + "bin").mkdirs();

		srcPath = path + File.separator + "src";
		testBenchPath = path + File.separator + "testBench";
	}

	@Override
	protected void doTransformActor(Actor actor) {
		Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("abs", "abs_my_precious");
		replacementMap.put("getw", "getw_my_precious");
		replacementMap.put("index", "index_my_precious");
		replacementMap.put("max", "max_my_precious");
		replacementMap.put("min", "min_my_precious");
		replacementMap.put("select", "select_my_precious");
		replacementMap.put("OUT", "OUT_my_precious");
		replacementMap.put("IN", "IN_my_precious");
		replacementMap.put("bitand", "bitand_my_precious");

		if (mergeActions) {
			new ActionMerger().doSwitch(actor);
		}
		if (convertMulti2Mono) {
			new Multi2MonoToken().doSwitch(actor);
		}

		List<DfSwitch<?>> transformations = new ArrayList<DfSwitch<?>>();
		transformations.add(new UnitImporter());
		transformations.add(new TypeResizer(true, false, true, false));
		transformations.add(new RenameTransformation(replacementMap));
		transformations.add(new Multi2MonoToken());
		transformations.add(new DfVisitor<Void>(new Inliner(true, true)));

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
			if (debug) {
				ResourceSet set = new ResourceSetImpl();
				if (!IrUtil.serializeActor(set, srcPath, actor)) {
					OrccLogger.warnln("Error with " + transformation
							+ " on actor " + actor.getName());
				}
			}
		}
	}

	@Override
	protected void doTransformNetwork(Network network) {
		// instantiate and flattens network
		OrccLogger.traceln("Instantiating...");
		new Instantiator(false, fifoSize).doSwitch(network);
		OrccLogger.traceln("Flattening...");
		new NetworkFlattener().doSwitch(network);

		if (classify) {
			OrccLogger.traceln("Classification of actors...");
			new Classifier().doSwitch(network);
		}
		if (mergeActors) {
			OrccLogger.traceln("Merging of actors...");
			new ActorMerger().doSwitch(network);
		}

		new BroadcastAdder().doSwitch(network);
		new ArgumentEvaluator().doSwitch(network);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		doTransformNetwork(network);

		if (debug) {
			// Serialization of the actors will break proxy link
			EcoreUtil.resolveAll(network);
		}
		transformActors(network.getAllActors());

		network.computeTemplateMaps();

		for (String component : mapping.values()) {
			if (!component.isEmpty()) {
				targetToInstancesMap = new HashMap<String, List<Instance>>();
				List<Instance> unmappedInstances = new ArrayList<Instance>();
				BackendUtil.computeMapping(network, mapping,
						targetToInstancesMap, unmappedInstances);
				for (Instance instance : unmappedInstances) {
					OrccLogger.warnln("The instance '" + instance.getName()
							+ "' is not mapped.");
				}
				break;
			}
		}

		// print instances
		printInstances(network);

		// print network
		OrccLogger.trace("Printing network... ");
		if (new NetworkPrinter(network, options).print(srcPath) > 0) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}
		
		OrccLogger.trace("Printing network testbench... ");
		if (new NetworkTestBenchPrinter(network, options).print(testBenchPath) > 0) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}
		
		
	}

	@Override
	protected boolean printInstance(Instance instance) {
		new InstanceTestBenchPrinter(instance, options).printInstance(testBenchPath);
		return new InstancePrinter(instance, options).printInstance(srcPath) > 0;
	}
}
