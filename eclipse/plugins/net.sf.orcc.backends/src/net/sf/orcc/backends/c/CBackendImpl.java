/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
package net.sf.orcc.backends.c;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.MAPPING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.c.transformations.CBroadcastAdder;
import net.sf.orcc.backends.transformations.TypeResizer;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.backends.util.MappingUtil;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.Instantiator;
import net.sf.orcc.df.transformations.NetworkFlattener;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.tools.normalizer.ActorNormalizer;
import net.sf.orcc.util.WriteListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * C back-end.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class CBackendImpl extends AbstractBackend {

	/**
	 * Backend options
	 */

	private boolean debug;
	private boolean enableTrace;

	private boolean normalize;
	private boolean classify;
	private boolean merge;

	private boolean newScheduler;
	private boolean ringTopology;
	private boolean useGeneticAlgo;
	private int threadsNb;

	/**
	 * Configuration mapping
	 */
	private Map<String, List<Instance>> targetToInstancesMap;
	private Map<String, String> mapping;

	private StandardPrinter printer;

	private void computeOptions(Map<String, Object> options) {
		options.put("newScheduler", newScheduler);
		options.put("ringTopology", ringTopology);
		options.put("fifoSize", fifoSize);

		if (useGeneticAlgo) {
			options.put("useGeneticAlgorithm", useGeneticAlgo);
			options.put("threadsNb", threadsNb);
		} else {
			if (targetToInstancesMap != null) {
				options.put("threads", targetToInstancesMap);
				options.put("threadsNb", targetToInstancesMap.size());
			}
		}
	}

	@Override
	public void doInitializeOptions() {
		mapping = getAttribute(MAPPING, new HashMap<String, String>());
		classify = getAttribute("net.sf.orcc.backends.classify", false);
		normalize = getAttribute("net.sf.orcc.backends.normalize", false);
		if (classify) {
			// only retrieve the merge option if classify is true
			merge = getAttribute("net.sf.orcc.backends.merge", false);
		} else {
			merge = false;
		}

		useGeneticAlgo = getAttribute("net.sf.orcc.backends.geneticAlgorithm",
				false);
		newScheduler = getAttribute("net.sf.orcc.backends.newScheduler", false);
		debug = getAttribute(DEBUG_MODE, true);
		threadsNb = Integer.parseInt(getAttribute(
				"net.sf.orcc.backends.processorsNumber", "1"));
		enableTrace = getAttribute("net.sf.orcc.backends.enableTrace", false);
		String topology = getAttribute(
				"net.sf.orcc.backends.newScheduler.topology", "Ring");
		ringTopology = topology.equals("Ring");

		printer = new StandardPrinter("net/sf/orcc/backends/c/Actor.stg",
				!debug);
		printer.setExpressionPrinter(new CExpressionPrinter());
		printer.setTypePrinter(new CTypePrinter());
		printer.getOptions().put("fifoSize", fifoSize);
		printer.getOptions().put("enableTrace", enableTrace);
		printer.getOptions().put("ringTopology", ringTopology);
		printer.getOptions().put("newScheduler", newScheduler);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("abs", "abs_my_precious");
		replacementMap.put("getw", "getw_my_precious");
		replacementMap.put("index", "index_my_precious");
		replacementMap.put("max", "max_my_precious");
		replacementMap.put("min", "min_my_precious");
		replacementMap.put("select", "select_my_precious");
		replacementMap.put("OUT", "OUT_my_precious");
		replacementMap.put("IN", "IN_my_precious");

		if (normalize) {
			new ActorNormalizer().doSwitch(actor);
		}

		DfSwitch<?>[] transformations = { new UnitImporter(),
				new TypeResizer(true, false, true, true),
				new RenameTransformation(replacementMap) };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
			ResourceSet set = new ResourceSetImpl();
			if (debug && !IrUtil.serializeActor(set, path, actor)) {
				System.out.println("oops " + transformation + " "
						+ actor.getName());
			}
		}

		CActorTemplateData data = new CActorTemplateData();
		data.computeTemplateMaps(actor);
		actor.setTemplateData(data);
	}

	private Network doTransformNetwork(Network network) throws OrccException {
		CNetworkTemplateData data = new CNetworkTemplateData();
		data.computeHierarchicalTemplateMaps(network);
		network.setTemplateData(data);

		// instantiate and flattens network
		write("Instantiating...\n");
		network = new Instantiator().doSwitch(network);
		write("Flattening...\n");
		new NetworkFlattener().doSwitch(network);

		if (classify) {
			write("Classification of actors...\n");
			network.classify();
			if (merge) {
				write("Merging of actors...\n");
				network.mergeActors();
			}
		}

		new CBroadcastAdder(new WriteListener() {
			@Override
			public void writeText(String text) {
				write(text);
			}
		}, fifoSize).doSwitch(network);

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network = doTransformNetwork(network);
		transformActors(network.getAllActors());

		network.computeTemplateMaps();

		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/c/Network.stg");
		printer.setExpressionPrinter(new CExpressionPrinter());
		printer.setTypePrinter(new CTypePrinter());

		for (String component : mapping.values()) {
			if (!component.isEmpty()) {
				targetToInstancesMap = new HashMap<String, List<Instance>>();
				List<Instance> unmappedInstances = new ArrayList<Instance>();
				MappingUtil.computeMapping(network, mapping,
						targetToInstancesMap, unmappedInstances);
				for (Instance instance : unmappedInstances) {
					write("Warning: The instance '" + instance.getName()
							+ "' is not mapped.\n");
				}
				break;
			}
		}

		computeOptions(printer.getOptions());

		// print instances
		printInstances(network);

		// print network
		write("Printing network...\n");
		printer.print(network.getSimpleName() + ".c", path, network);

		// print CMakeLists
		printCMake(network);
		if (!useGeneticAlgo && targetToInstancesMap != null) {
			printMapping(network);
		}
	}

	private void printCMake(Network network) {
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/c/CMakeLists.stg");
		networkPrinter.print("CMakeLists.txt", path, network);
	}

	@Override
	protected boolean printInstance(Instance instance) throws OrccException {
		return printer.print(instance.getName() + ".c", path, instance);
	}

	private void printMapping(Network network) {
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/c/Mapping.stg");
		networkPrinter.getOptions().put("mapping", targetToInstancesMap);
		networkPrinter.print(network.getName() + ".xcf", path, network);
	}

}
