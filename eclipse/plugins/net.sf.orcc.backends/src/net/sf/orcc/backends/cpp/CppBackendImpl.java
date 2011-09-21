/*
 * Copyright (c) 2009-2011, Ecole Polytechnique Fédérale de Lausanne
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.cpp.transformations.SerDesAdder;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.util.ActorVisitor;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;

import org.eclipse.core.resources.IFile;

/**
 * C++ back-end.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class CppBackendImpl extends AbstractBackend {

	public static final String DEFAULT_PARTITION = "default_partition";

	public static Boolean printHeader = false;

	private boolean classify;

	private boolean merge;

	private boolean needSerDes = false;

	private boolean normalize;

	private Map<Connection, Integer> computeFifoKind(Network network)
			throws OrccException {
		Map<Connection, Integer> fifoKind = new HashMap<Connection, Integer>();
		for (Connection connection : network.getConnections()) {
			int kind = 0;

			Instance src = network.getGraph().getEdgeSource(connection)
					.getInstance();
			Instance tgt = network.getGraph().getEdgeTarget(connection)
					.getInstance();

			String srcName = getPartNameAttribute(src);
			String tgtName = getPartNameAttribute(tgt);

			if (src.isSerdes() || tgt.isSerdes()) {
				needSerDes = true;

				kind = 1;
			} else if (!srcName.equals(tgtName)) {
				kind = 2;
			}
			fifoKind.put(connection, kind);
		}
		return fifoKind;
	}

	private Map<String, List<Instance>> computeMapping(Network network)
			throws OrccException {
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
			} else {
				throw new OrccException("instance " + instance.getId()
						+ " has no partName attribute!");
			}
		}
		return threads;
	}

	@Override
	public void doInitializeOptions() {
		classify = getAttribute("net.sf.orcc.backends.classify", false);
		normalize = getAttribute("net.sf.orcc.backends.normalize", false);
		merge = getAttribute("net.sf.orcc.backends.merge", false);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorVisitor<?>[] transformations = { new UnitImporter() };

		for (ActorVisitor<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

	}

	private void doTransformNetwork(Network network) throws OrccException {
		if (classify) {
			network.classify();
			if (normalize) {
				network.normalizeActors();
			}
			if (merge) {
				network.mergeActors();
			}
		}

		// add wrapper if needed
		new SerDesAdder().transform(network);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a C++ VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		transformActors(network.getActors());

		doTransformNetwork(network);

		printActors(network.getActors());

		// print network
		write("Printing network...\n");
		network.computeTemplateMaps();
		printNetwork(network);
	}

	private String getPartNameAttribute(Instance instance) throws OrccException {
		String partName = DEFAULT_PARTITION;
		IAttribute attr = instance.getAttribute("partName");
		if (attr != null) {
			Expression expr = ((IValueAttribute) attr).getValue();
			partName = ((ExprString) expr).getValue();
		}
		return partName;
	}

	@Override
	protected boolean printActor(Actor actor) {
		StandardPrinter actorPrinter = new StandardPrinter(
				"net/sf/orcc/backends/cpp/Cpp_actorImpl.stg", false);
		StandardPrinter headerPrinter = new StandardPrinter(
				"net/sf/orcc/backends/cpp/Cpp_actorDecl.stg", false);

		actorPrinter.setTypePrinter(new CppTypePrinter());
		headerPrinter.setTypePrinter(new CppTypePrinter());
		actorPrinter.setExpressionPrinter(new CppExprPrinter());
		headerPrinter.setExpressionPrinter(new CppExprPrinter());

		String hier = path + File.separator
				+ actor.getPackage().replace('.', File.separatorChar);
		new File(hier).mkdirs();

		actorPrinter.print(actor.getSimpleName() + ".cpp", hier, actor);
		headerPrinter.print(actor.getSimpleName() + ".h", hier, actor);

		return false;
	}

	private void printCMake(Network network) {
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/cpp/Cpp_CMakeLists.stg");
		networkPrinter.getOptions().put("needSerDes", needSerDes);
		networkPrinter.print("CMakeLists.txt", path, network);
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
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/cpp/Cpp_network.stg");

		printer.setExpressionPrinter(new CppExprPrinter());
		printer.setTypePrinter(new CppTypePrinter());

		// compute thread lists if need
		printer.getOptions().put("threads", computeMapping(network));
		// compute kind of fifos
		printer.getOptions().put("fifoKind", computeFifoKind(network));

		printer.getOptions().put("needSerDes", needSerDes);

		printer.print(network.getName() + ".cpp", path, network);

		printCMake(network);
	}

}
