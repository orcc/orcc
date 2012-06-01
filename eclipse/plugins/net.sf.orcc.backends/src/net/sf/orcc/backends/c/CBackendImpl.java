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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.c.transform.CBroadcastAdder;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.ParameterImporter;
import net.sf.orcc.backends.transform.TypeResizer;
import net.sf.orcc.backends.transform.UnitImporter;
import net.sf.orcc.backends.util.BackendUtil;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.ActorMerger;
import net.sf.orcc.tools.normalizer.ActorNormalizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * C back-end.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class CBackendImpl extends AbstractBackend {

	protected boolean classify;
	/**
	 * Backend options
	 */

	protected boolean debug;

	protected boolean enableTrace;
	protected Map<String, String> mapping;
	protected boolean merge;

	protected boolean newScheduler;
	protected boolean normalize;
	protected StandardPrinter printer;
	protected boolean ringTopology;

	/**
	 * Configuration mapping
	 */
	protected Map<String, List<Instance>> targetToInstancesMap;
	protected int threadsNb;

	protected boolean useGeneticAlgo;

	protected void computeOptions(Map<String, Object> options) {
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

		// Set build and src directory
		File srcDir = new File(path + "/src");
		File buildDir = new File(path + "/build");

		// If directories don't exist, create them
		if (!srcDir.exists()) {
			srcDir.mkdirs();
		}

		// If directories don't exist, create them
		if (!buildDir.exists()) {
			buildDir.mkdirs();
		}

		// Set src directory as path
		path = srcDir.getAbsolutePath();
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

		List<DfSwitch<?>> transformations = new ArrayList<DfSwitch<?>>();
		transformations.add(new UnitImporter());
		transformations.add(new TypeResizer(true, false, true));
		transformations.add(new RenameTransformation(replacementMap));

		// If "-t" option is passed to command line, apply additional
		// transformations
		if (getAttribute("net.sf.orcc.backends.additionalTransfos", false)) {
			transformations.add(new Multi2MonoToken());
			transformations.add(new ParameterImporter());
			transformations.add(new DfVisitor<Void>(new Inliner(true, true)));
			// transformations.add(new StoreOnceTransformation());
		}

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
			ResourceSet set = new ResourceSetImpl();
			if (debug && !IrUtil.serializeActor(set, path, actor)) {
				System.err.println("oops " + transformation + " "
						+ actor.getName());
			}
		}

		CActorTemplateData data = new CActorTemplateData();
		data.computeTemplateMaps(actor);
		actor.setTemplateData(data);
	}

	protected Network doTransformNetwork(Network network) throws OrccException {
		CNetworkTemplateData data = new CNetworkTemplateData();
		data.computeHierarchicalTemplateMaps(network);
		network.setTemplateData(data);

		// instantiate and flattens network
		write("Instantiating...\n");
		new Instantiator(fifoSize).doSwitch(network);
		write("Flattening...\n");
		new NetworkFlattener().doSwitch(network);

		if (classify) {
			write("Classification of actors...\n");
			new Classifier(getWriteListener()).doSwitch(network);
			if (merge) {
				write("Merging of actors...\n");
				new ActorMerger().doSwitch(network);
			}
		}

		new CBroadcastAdder(getWriteListener()).doSwitch(network);

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network = doTransformNetwork(network);

		if (debug) {
			// Serialization of the actors will break proxy link
			EcoreUtil.resolveAll(network);
		}
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
				BackendUtil.computeMapping(network, mapping,
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.AbstractBackend#exportRuntimeLibrary()
	 */
	@Override
	public boolean exportRuntimeLibrary() throws OrccException {
		if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
			File targetPath = new File(path).getParentFile();
			copyFileToFilesystem("/runtime/run_cmake_with_VS_env.bat",
					targetPath + File.separator + "run_cmake_with_VS_env.bat");
		}

		String target = path + File.separator + "libs";
		write("Export libraries sources into " + target + "... ");
		if (copyFolderToFileSystem("/runtime/C", target)) {
			write("OK" + "\n");
			return true;
		} else {
			write("Error" + "\n");
			return false;
		}
	}

	protected void printCMake(Network network) {
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/c/CMakeLists.stg");
		networkPrinter.print("CMakeLists.txt", path, network);
	}

	@Override
	protected boolean printInstance(Instance instance) throws OrccException {
		return printer.print(instance.getName() + ".c", path, instance);
	}

	protected void printMapping(Network network) {
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/util/Mapping.stg");
		networkPrinter.getOptions().put("mapping", targetToInstancesMap);
		networkPrinter.print(network.getName() + ".xcf", path, network);
	}

}
