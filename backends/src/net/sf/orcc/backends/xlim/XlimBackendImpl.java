/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * Samuel Keller EPFL
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
package net.sf.orcc.backends.xlim;

import java.io.File;
import java.io.IOException;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.network.Network;

/**
 * XLIM back-end. XlimBackendImpl manages JSON to XLIM transformation
 * 
 * @author Matthieu Wipliez, Samuel Keller
 */
public class XlimBackendImpl extends AbstractBackend {

	/**
	 * Command line to generate XLIM
	 * 
	 * @param args
	 *            First argument used only: XDF location
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				System.out.println("START");
				new XlimBackendImpl().generateCode(args[0], 10000);
				System.out.println("DONE");
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err
					.println("Usage: XlimBackendImpl <flattened XDF network>");
		}
	}

	/**
	 * Printer for XLIM
	 */
	private XlimActorPrinter printer;

	/**
	 * Create the printer
	 */
	protected void init() throws IOException {
		printer = new XlimActorPrinter();
	}

	/**
	 * Print one actor
	 * 
	 * @param id
	 *            Actor id
	 * @param actor
	 *            Actor structure
	 */
	protected void printActor(String id, Actor actor) throws Exception {
		String outputName = path + File.separator + id + ".xlim";
		printer.printActor(outputName, actor);
	}

	/**
	 * Print the network
	 * 
	 * @param network
	 *            Network structure
	 */
	protected void printNetwork(Network network) throws Exception {
	}

}
