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
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.InstancePrinter;
import net.sf.orcc.backends.NetworkPrinter;
import net.sf.orcc.backends.c.transformations.CBroadcastAdder;
import net.sf.orcc.backends.transformations.TypeSizeTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.transformations.PhiRemoval;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.ir.util.ActorVisitor;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.StringAttribute;
import net.sf.orcc.network.transformations.BroadcastAdder;
import net.sf.orcc.network.transformations.NetworkClassifier;
import net.sf.orcc.network.transformations.NetworkSplitter;
import net.sf.orcc.tools.merger2.NetworkMerger;
import net.sf.orcc.tools.normalizer.ActorNormalizer;
import net.sf.orcc.util.WriteListener;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * C back-end.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class CBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(CBackendImpl.class, args);
	}

	private boolean classify;
	private boolean codesign;
	private boolean debugMode;
	private boolean dynamicMapping;
	private boolean enableTrace;
	private Map<String, List<Instance>> instancesTarget;
	private Map<String, String> mapping;
	private Map<String, Network> mapTargetsNetworks;
	private DirectedGraph<String, StringAttribute> mediumGraph;
	private boolean merge;
	private boolean merger2;
	private boolean newScheduler;
	private boolean normalize;
	private int threadsNb;
	private boolean ringTopology;
	private Network workingNetwork;

	private void computeMapping(Network network) {

		// compute the different threads
		instancesTarget = new HashMap<String, List<Instance>>();
		for (Instance instance : network.getInstances()) {
			String path = null;
			if (instance.isActor()) {
				path = instance.getHierarchicalPath();
			} else if (instance.isBroadcast()) {
				// use source instance for broadcasts
				Set<Connection> edges = network.getGraph().incomingEdgesOf(
						new Vertex(instance));
				if (!edges.isEmpty()) {
					Connection incoming = edges.iterator().next();
					Vertex source = network.getGraph().getEdgeSource(incoming);
					if (source.isInstance()) {
						path = source.getInstance().getHierarchicalPath();
					}
				}
			}

			// get component
			String component = mapping.get(path);
			if (component != null) {
				List<Instance> list = instancesTarget.get(component);
				if (list == null) {
					list = new ArrayList<Instance>();
					instancesTarget.put(component, list);
				}

				list.add(instance);
			} else {
				write("Warning: The instance '" + instance.getId()
						+ "' is not mapped.\n");
			}
		}

		mediumGraph = new DirectedMultigraph<String, StringAttribute>(
				StringAttribute.class);
		Set<String> targets = instancesTarget.keySet();

		for (String target : targets) {
			mediumGraph.addVertex(target);
		}

		for (String target : targets) {
			for (String otherTarget : targets) {
				if (!target.equals(otherTarget)) {
					mediumGraph.addEdge(target, otherTarget,
							new StringAttribute("_socket"));
					mediumGraph.addEdge(otherTarget, target,
							new StringAttribute("_socket"));
				}
			}
		}
	}

	private void computeOptions(Map<String, Object> options) {
		options.put("newScheduler", newScheduler);
		options.put("ringTopology", ringTopology);
		options.put("fifoSize", fifoSize);

		if (codesign) {
			options.put("threadsNb", 1);
		} else {
			if (dynamicMapping) {
				options.put("needDynamicMapping", dynamicMapping);
				options.put("threadsNb", threadsNb);
			} else {
				if (instancesTarget != null) {
					options.put("threads", instancesTarget);
					options.put("threadsNb", instancesTarget.size());
				}
			}
		}

		for (String targetName : mapTargetsNetworks.keySet()) {
			Network worknet = mapTargetsNetworks.get(targetName);
			CNetworkTemplateData data = new CNetworkTemplateData();
			data.computeTemplateMaps(worknet);
			worknet.setTemplateData(data);
		}
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
			new ActorNormalizer().visit(actor);
		}

		ActorVisitor[] transformations = { new TypeSizeTransformation(),
				new DeadGlobalElimination(), new DeadCodeElimination(),
				new DeadVariableRemoval(),
				new RenameTransformation(replacementMap), new PhiRemoval() };

		for (ActorVisitor transformation : transformations) {
			transformation.visit(actor);
		}

		CActorTemplateData data = new CActorTemplateData();
		data.computeTemplateMaps(actor);
		actor.setTemplateData(data);
	}

	private void doTransformNetwork(Network network) throws OrccException {
		network.flatten();

		if (classify) {
			new NetworkClassifier().transform(network);
		}

		// Experimental
		if (merger2) {
			new NetworkMerger().transform(network);
		}

		if (codesign) {
			new BroadcastAdder().transform(network);

			// computeMapping(network);
			NetworkSplitter netSplit = new NetworkSplitter(instancesTarget,
					mediumGraph);
			netSplit.transform(network);
			mapTargetsNetworks = netSplit.getNetworksMap();
		} else {
			new CBroadcastAdder(new WriteListener() {
				@Override
				public void writeText(String text) {
					write(text);
				}
			}, fifoSize).transform(network);
			mapTargetsNetworks = new HashMap<String, Network>();
			mapTargetsNetworks.put(new String(""), network);
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {

		// Transform the network
		doTransformNetwork(network);

		String rootPath = new String(path);
		for (String targetName : mapTargetsNetworks.keySet()) {
			workingNetwork = mapTargetsNetworks.get(targetName);

			// Transform all actors of the network
			transformActors(workingNetwork.getActors());

			if (merge) {
				workingNetwork.mergeActors();
			}

			workingNetwork.computeTemplateMaps();

			NetworkPrinter printer = new NetworkPrinter("C_network");
			printer.setTypePrinter(CTypePrinter.class);

			instancesTarget = null;
			for (String mappedThing : mapping.values()) {
				if (!mappedThing.isEmpty()) {
					computeMapping(workingNetwork);
					break;
				}
			}

			computeOptions(printer.getOptions());

			if (codesign) {
				write("\nPrinting " + targetName + "'s instances\n");
				path = rootPath + File.separator + targetName;
				File directory = new File(path);
				if (!directory.exists()) {
					directory.mkdirs();
				}
			}

			// print instances
			printInstances(workingNetwork);

			// print network
			write("Printing network...\n");
			printer.print(workingNetwork.getName() + ".c", path,
					workingNetwork, "network");

			// print CMakeLists
			printCMake(workingNetwork);
		}
	}

	private void printCMake(Network network) {
		NetworkPrinter networkPrinter = new NetworkPrinter("C_CMakeLists");
		networkPrinter.print("CMakeLists.txt", path, network, "CMakeLists");
	}

	@Override
	protected boolean printInstance(Instance instance) throws OrccException {
		InstancePrinter printer = new InstancePrinter("C_actor", !debugMode);
		printer.setExpressionPrinter(CExpressionPrinter.class);
		printer.setTypePrinter(CTypePrinter.class);
		printer.getOptions().put("network", workingNetwork);
		printer.getOptions().put("fifoSize", fifoSize);
		printer.getOptions().put("enableTrace", enableTrace);
		printer.getOptions().put("ringTopology", ringTopology);
		printer.getOptions().put("newScheduler", newScheduler);
		return printer.print(instance.getId() + ".c", path, instance,
				"instance");
	}

	@Override
	public void setOptions() throws OrccException {
		mapping = getAttribute(MAPPING, new HashMap<String, String>());
		classify = getAttribute("net.sf.orcc.backends.classify", false);
		normalize = getAttribute("net.sf.orcc.backends.normalize", false);
		merge = getAttribute("net.sf.orcc.backends.merge", false);
		merger2 = getAttribute("net.sf.orcc.backends.merger2", false);
		codesign = getAttribute("net.sf.orcc.backends.coDesign", false);
		dynamicMapping = getAttribute("net.sf.orcc.backends.dynamicMapping",
				false);
		newScheduler = getAttribute("net.sf.orcc.backends.newScheduler", false);
		debugMode = getAttribute(DEBUG_MODE, true);
		threadsNb = Integer.parseInt(getAttribute(
				"net.sf.orcc.backends.processorsNumber", "1"));
		enableTrace = getAttribute("net.sf.orcc.backends.enableTrace", false);
		String topology = getAttribute(
				"net.sf.orcc.backends.newScheduler.topology", "Ring");
		ringTopology = topology.equals("Ring");
	}

}
