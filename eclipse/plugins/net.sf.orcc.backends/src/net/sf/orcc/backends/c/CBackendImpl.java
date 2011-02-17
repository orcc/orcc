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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.backends.transformations.MoveReadsWritesTransformation;
import net.sf.orcc.backends.transformations.RenameTransformation;
import net.sf.orcc.backends.transformations.TypeSizeTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorVisitor;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.transformations.PhiRemoval;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.StringAttribute;
import net.sf.orcc.network.transformations.BroadcastAdder;
import net.sf.orcc.network.transformations.NetworkSplitter;
import net.sf.orcc.tools.classifier.ActorClassifier;
import net.sf.orcc.tools.merger2.ActorMerger2;
import net.sf.orcc.tools.normalizer.ActorNormalizer;

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

	public static boolean merge = false;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(CBackendImpl.class, args);
	}

	private Map<String, List<Instance>> instancesTarget;

	private DirectedGraph<String, StringAttribute> mediumGraph;

	private boolean needPthreads;

	private Network workingNetwork;
	/**
	 * printer is protected in order to be visible to CQuasiBackendImpl
	 */
	protected STPrinter printer;

	private final Map<String, String> transformations;

	/**
	 * Creates a new instance of the C back-end. Initializes the transformation
	 * hash map.
	 */
	public CBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_");
		transformations.put("getw", "getw_");
		transformations.put("index", "index_");
		transformations.put("max", "max_");
		transformations.put("min", "min_");
		transformations.put("select", "select_");
	}

	private void computeMapping(Network network) throws OrccException {
		Map<String, String> mapping = getAttribute(MAPPING,
				new HashMap<String, String>());

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

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		boolean classify = getAttribute("net.sf.orcc.backends.classify", false);
		boolean normalize = getAttribute("net.sf.orcc.backends.normalize",
				false);

		if (classify) {
			new ActorClassifier().visit(actor);
			if (normalize) {
				new ActorNormalizer().visit(actor);
			}
		}

		ActorVisitor[] transformations = { new TypeSizeTransformation(),
				new DeadGlobalElimination(), new DeadCodeElimination(),
				new DeadVariableRemoval(false),
				new RenameTransformation(this.transformations),
				new PhiRemoval(),

				// new MultipleArrayAccessTransformation(),

				new MoveReadsWritesTransformation() };

		for (ActorVisitor transformation : transformations) {
			transformation.visit(actor);
		}

		CTemplateData data = new CTemplateData();
		data.computeTemplateMaps(actor, workingNetwork);
		actor.setTemplateData(data);
		workingNetwork.setTemplateData(data);
	}

	protected void doTransformNetwork(Network network) throws OrccException {

		new BroadcastAdder().transform(network);
		computeMapping(network);
		if (getAttribute("net.sf.orcc.backends.coDesign", false)) {
			printer.getOptions().put("threadsNb", 1);
			new NetworkSplitter(instancesTarget, mediumGraph)
					.transform(network);
		} else {

			boolean needDynamicMapping = getAttribute(
					"net.sf.orcc.backends.dynamicMapping", false);
			needPthreads = (instancesTarget.keySet().size() > 1);

			if (needDynamicMapping) {
				printer.getOptions().put("needDynamicMapping",
						needDynamicMapping);
				printer.getOptions().put(
						"threadsNb",
						getAttribute("net.sf.orcc.backends.processorsNumber",
								"1"));
			} else {
				printer.getOptions().put("needPthreads", needPthreads);
				printer.getOptions().put("threads", instancesTarget);
				printer.getOptions().put("threadsNb", instancesTarget.size());
			}
			network.computeTemplateMaps();
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		boolean merge = getAttribute("net.sf.orcc.backends.merge", false);
		if (merge) {
			network.mergeActors();
		}

		// until now, printer is default printer
		printer = new STPrinter(getAttribute(DEBUG_MODE, true));
		printer.loadGroup("C_actor");
		printer.setExpressionPrinter(CExpressionPrinter.class);
		printer.setTypePrinter(CTypePrinter.class);
		printer.setOptions(getAttributes());

		doTransformNetwork(network);
		workingNetwork = network;
		List<Actor> actors = network.getActors();
		transformActors(actors);

		// Experimental
		boolean merge2 = getAttribute("net.sf.orcc.backends.merge2", false);
		if (merge2) {
			new ActorMerger2().transform(network);
		}

		printInstances(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	@Override
	protected boolean printInstance(Instance instance) throws OrccException {
		String id = instance.getId();
		String outputName = path + File.separator + id + ".c";
		try {
			return printer.printInstance(outputName, workingNetwork, instance);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private void printNetwork(Network network) throws OrccException {
		try {
			printer.getOptions().put("newScheduler",
					getAttribute("net.sf.orcc.backends.newScheduler", false));

			String outputName = path + File.separator + network.getName()
					+ ".c";
			printer.loadGroup("C_network");
			printer.printNetwork(outputName, network, false, fifoSize);

			new CMakePrinter().printCMake(path, network, needPthreads);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
