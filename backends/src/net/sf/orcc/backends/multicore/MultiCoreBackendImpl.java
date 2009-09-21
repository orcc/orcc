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
package net.sf.orcc.backends.multicore;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;

import net.sf.orcc.backends.IBackend;
import net.sf.orcc.backends.c.transforms.IncrementPeephole;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.network.Instance;
import net.sf.orcc.ir.network.Network;
import net.sf.orcc.ir.parser.NetworkParser;
import net.sf.orcc.ir.transforms.BroadcastAdder;
import net.sf.orcc.ir.transforms.PhiRemoval;

/**
 * C MultiCore back-end.
 * 
 * @author Jérôme GORIN
 * 
 */
public class MultiCoreBackendImpl implements IBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				new MultiCoreBackendImpl().generateCode(args[0], 10000);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err
					.println("Usage: MultiCoreBackendImpl <flattened XDF network>");
		}
	}

	@Override
	public void generateCode(String fileName, int fifoSize) throws Exception {
		File file = new File(fileName);
		String path = file.getParent();
		Network network = new NetworkParser().parseNetwork(path,
				new FileInputStream(file));

		Set<Instance> instances = network.getGraph().vertexSet();
		for (Instance instance : instances) {
			if (instance.hasActor()) {
				Actor actor = instance.getActor();

				// transforms Phi assignments to copies
				new PhiRemoval(actor);

				// replaces nodes by specific C nodes where appropriate
				new IncrementPeephole(actor);

				// prints actor
				// String outputName = path + File.separator + instance.getId()
				// + ".c";
				// new MultiCoreActorPrinter(outputName, actor);
			}
		}

		// add broadcasts
		new BroadcastAdder(network);

		// print network
		MultiCoreNetworkPrinter networkPrinter = new MultiCoreNetworkPrinter();
		String outputName = path + File.separator + network.getName() + ".c";
		networkPrinter.printNetwork(outputName, network, false, fifoSize);
	}
}
