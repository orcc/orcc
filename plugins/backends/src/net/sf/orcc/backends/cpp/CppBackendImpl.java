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

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.NetworkPrinter;
import net.sf.orcc.backends.cpp.codesign.WrapperAdder;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Network;

/**
 * C++ back-end.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class CppBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */

	public static Boolean partitioning = false;

	public static void main(String[] args) {
		String file;
		if (args.length > 0) {
			file = args[0];
			if (args.length > 1) {
				partitioning = Boolean.parseBoolean(args[1]);
			}
			try {
				new CppBackendImpl().generateCode(file, 10000);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err
					.println("Usage: CppBackendImpl <flattened XDF network> [<partition>]");
		}
	}

	private CppActorPrinter impl_printer;

	private CppActorPrinter printer;

	@Override
	protected void beforeInstantiation(Network network) throws OrccException {
		if (partitioning) {
			path += File.separator + network.getName();
			new File(path).mkdir();
		}
		printer = new CppActorPrinter("Cpp_actorDecl");
		impl_printer = new CppActorPrinter("Cpp_actorImpl");
	}

	@Override
	protected void printActor(String id, Actor actor) throws OrccException {
		ActorTransformation[] transformations = { new PhiRemoval() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		try {
			String outputName = path + File.separator + "Actor_" + id + ".h";
			printer.printActor(outputName, id, actor);

			outputName = path + File.separator + "Actor_" + id + ".cpp";
			impl_printer.printActor(outputName, id, actor);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	@Override
	protected void printNetwork(Network network) throws OrccException {
		try {
			NetworkPrinter networkPrinter = new NetworkPrinter(
					"Cpp_networkDecl");
			NetworkPrinter networkImplPrinter = new NetworkPrinter(
					"Cpp_networkImpl");

			if (partitioning) {
				new WrapperAdder().transform(network);
			}

			String name = network.getName();

			String outputName = path + File.separator + "Network_" + name
					+ ".h";
			networkPrinter.printNetwork(outputName, network, false, fifoSize);
			outputName = path + File.separator + "Network_" + name + ".cpp";
			networkImplPrinter.printNetwork(outputName, network, false,
					fifoSize);
			networkImplPrinter.printNetwork(outputName, network, false,
					fifoSize);

			new CppCMakePrinter().printCMake(path, network, partitioning);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
