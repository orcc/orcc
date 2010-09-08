/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.cpp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.transforms.DeadCodeElimination;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.DeadVariableRemoval;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;

/**
 * C++ back-end.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class CppBackendImpl extends AbstractBackend {

	public static Boolean partitioning = false;

	private boolean needSerDes = false;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(CppBackendImpl.class, args);
	}

	private NetworkPartitioner partitioner = new NetworkPartitioner();

	private STPrinter printer;

	private void computeMapping(Network network) throws OrccException {
		Map<String, List<Instance>> threads = new HashMap<String, List<Instance>>();
		for (Instance instance : network.getInstances()) {
			String component = getPartNameAttribute(instance);
			if (component != null) {
				List<Instance> list = threads.get(component);
				if (list == null) {
					list = new ArrayList<Instance>();
					threads.put(component, list);
				}
				list.add(instance);
			}
		}
		printer.getOptions().put("threads", threads);
		printer.getOptions().put("needThreads", (threads.keySet().size() > 1));

		// crappy hack!
		Map<Connection, Integer> fifoKind = new HashMap<Connection, Integer>();
		for (Connection connection : network.getConnections()) {
			int kind = 0;

			Instance src = network.getGraph().getEdgeSource(connection)
					.getInstance();
			Instance tgt = network.getGraph().getEdgeTarget(connection)
					.getInstance();

			if (src.isSerdes() || tgt.isSerdes()) {
				needSerDes = true;
				printer.getOptions().put("needSerDes", needSerDes);
				kind = 1;
			} else if (!getPartNameAttribute(src).equals(
					getPartNameAttribute(tgt))) {
				kind = 2;
			}

			fifoKind.put(connection, kind);

		}
		printer.getOptions().put("fifoKind", fifoKind);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = { new DeadGlobalElimination(),
				new DeadCodeElimination(), new DeadVariableRemoval(),
				new PhiRemoval() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		// do not generate a C++ VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		boolean classify = getAttribute("net.sf.orcc.backends.classify", false);
		if (classify) {
			network.classifyActors();

			boolean normalize = getAttribute("net.sf.orcc.backends.normalize",
					false);
			if (normalize) {
				network.normalizeActors();
			}

			boolean merge = getAttribute("net.sf.orcc.backends.merge", false);
			if (merge) {
				network.mergeActors();
			}
		}

		boolean partition = getAttribute("net.sf.orcc.backends.partition",
				true);

		if (partition) {
			partitioning = true;
			partitioner.transform(network);
			if (network.getNetworks().size() > 1) {
				try {
					network.computeTemplateMaps();
					new CppConfigPrinter().print(path, network);
				} catch (IOException e) {
					throw new OrccException("I/O error", e);
				}
			}
		}

		printer = new STPrinter();
		printer.setExpressionPrinter(CppExprPrinter.class);
		printer.setTypePrinter(CppTypePrinter.class);
		printer.setOptions(getAttributes());

		List<Actor> actors = network.getActors();
		transformActors(actors);
		printActors(actors);

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	private String getPartNameAttribute(Instance instance) throws OrccException {
		String partName = null;
		IAttribute attr = instance.getAttribute("partName");
		if (attr != null) {
			Expression expr = ((IValueAttribute) attr).getValue();
			String[] partNames = ((StringExpr) expr).getValue().split("/");
			if (partNames.length > 1) {
				partName = partNames[1];
			}
		}
		return partName;
	}

	@Override
	protected boolean printActor(Actor actor) throws OrccException {
		boolean res = false;
		try {
			String name = actor.getName();

			printer.loadGroups("C_actor", "Cpp_actorDecl");
			String outputName = path + File.separator + name + ".h";
			printer.printActor(outputName, actor);

			outputName = path + File.separator + name + ".cpp";
			printer.loadGroups("C_actor", "Cpp_actorImpl");
			printer.printActor(outputName, actor);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
		return res;
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
			List<Network> networks = (partitioning) ? network.getNetworks()
					: Arrays.asList(network);
			CppCMakePrinter cmakePrinter = new CppCMakePrinter();
			for (Network subnetwork : networks) {

				if (partitioning) {
					new SerDesAdder().transform(subnetwork);
					computeMapping(subnetwork);
				}
				String outputName = path + File.separator
						+ subnetwork.getName() + ".cpp";
				printer.loadGroups("Cpp_network");
				printer.printNetwork(outputName, subnetwork, false, fifoSize);

				cmakePrinter.printCMake(path, subnetwork, needSerDes);
			}

		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
