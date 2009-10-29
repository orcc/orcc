/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.java.debug;

import java.io.File;
import java.io.IOException;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.IBackend;
import net.sf.orcc.backends.c.transforms.IncrementPeephole;
import net.sf.orcc.ir.IActorTransformation;
import net.sf.orcc.ir.NameTransformer;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transforms.BroadcastAdder;

/**
 * C back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class JavaDebugBackendImpl extends AbstractBackend implements IBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				new JavaDebugBackendImpl().generateCode(args[0], 10000);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err
					.println("Usage: JavaDebugBackendImpl <flattened XDF network>");
		}
	}

	private String outputPath;

	private JavaDebugActorPrinter printer;

	@Override
	protected void init() throws IOException {
		printer = new JavaDebugActorPrinter();

		NameTransformer.names.clear();

		String sep = File.separator;

		// create paths
		outputPath = path + sep;
		String[] segments = { "src", "net", "sf", "orcc", "generated" };
		for (String segment : segments) {
			outputPath += segment + sep;
			new File(outputPath).mkdir();
		}
	}

	@Override
	protected void printActor(String id, Actor actor) throws Exception {
		IActorTransformation[] transformations = { new PhiRemoval(),
				new IncrementPeephole() };

		for (IActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		String outputName = outputPath + "Actor_" + id + ".java";
		printer.printActor(outputName, actor);
	}

	@Override
	protected void printNetwork(Network network) throws Exception {
		JavaDebugNetworkPrinter networkPrinter = new JavaDebugNetworkPrinter();

		// Add broadcasts before printing
		new BroadcastAdder().transform(network);

		String name = network.getName();
		String outputName = outputPath + "Network_" + name + ".java";
		networkPrinter.printNetwork(outputName, network, false, fifoSize);
	}
}
