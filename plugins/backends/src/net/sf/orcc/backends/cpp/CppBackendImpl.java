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
import java.util.Arrays;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.backends.cpp.codesign.NetworkPartitioner;
import net.sf.orcc.backends.cpp.codesign.WrapperAdder;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.DeadCodeElimination;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.DeadVariableRemoval;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFWriter;

/**
 * C++ back-end.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class CppBackendImpl extends AbstractBackend {

	public static Boolean partitioning = false;

	private NetworkPartitioner partitioner;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(CppBackendImpl.class, args);
	}

	private STPrinter impl_printer;

	private STPrinter printer;

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		boolean partition = getAttribute("net.sf.orcc.backends.partition",
				true);
		if (partition) {
			partitioning = true;
			partitioner = new NetworkPartitioner();
			partitioner.transform(network);
			
			new XDFWriter(new File(path), network);

		}

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

		printer = new STPrinter();
		printer.loadGroups("C_actor", "Cpp_actorDecl");
		printer.setExpressionPrinter(CppExprPrinter.class);
		printer.setTypePrinter(CppTypePrinter.class);
		impl_printer = new STPrinter();
		impl_printer.loadGroups("C_actor", "Cpp_actorImpl");
		impl_printer.setExpressionPrinter(CppExprPrinter.class);
		impl_printer.setTypePrinter(CppTypePrinter.class);

		List<Actor> actors = network.getActors();
		transformActors(actors);

		printActors(network.getActors());
	}

	@Override
	protected void printActor(Actor actor) throws OrccException {
		try {
			String name = actor.getName();
			String outputName = path + File.separator + name + ".h";
			printer.printActor(outputName, actor);
			outputName = path + File.separator + name + ".cpp";
			impl_printer.printActor(outputName, actor);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	@Override
	protected void printNetwork(Network network) throws OrccException {
		try {
			STPrinter networkPrinter = new STPrinter();
			networkPrinter.loadGroups("Cpp_networkDecl");
			networkPrinter.setExpressionPrinter(CppExprPrinter.class);
			networkPrinter.setTypePrinter(CppTypePrinter.class);

			STPrinter networkImplPrinter = new STPrinter();
			networkImplPrinter.loadGroups("Cpp_networkImpl");
			networkImplPrinter.setExpressionPrinter(CppExprPrinter.class);
			networkImplPrinter.setTypePrinter(CppTypePrinter.class);

			List<Network> networks;
			if (partitioning) {
				networks = partitioner.getNetworks();
			} else {
				networks = Arrays.asList(network);
			}

			for (Network subnetwork : networks) {
				if (partitioning) {
					new WrapperAdder().transform(subnetwork);
				}

				String name = subnetwork.getName();

				String outputName = path + File.separator + name + ".h";
				networkPrinter.printNetwork(outputName, subnetwork, false,
						fifoSize);
				outputName = path + File.separator + name + ".cpp";
				networkImplPrinter.printNetwork(outputName, subnetwork, false,
						fifoSize);
			}

			new CppMainPrinter().printMain(path, networks, null);
			new CppCMakePrinter().printCMake(path, networks);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	@Override
	protected void transformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = { new DeadGlobalElimination(),
				new DeadCodeElimination(), new DeadVariableRemoval(),
				new PhiRemoval() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}
	}

}
