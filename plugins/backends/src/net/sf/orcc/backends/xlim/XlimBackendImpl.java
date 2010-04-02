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

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;

/**
 * XLIM back-end. XlimBackendImpl manages JSON to XLIM transformation
 * 
 * @author Matthieu Wipliez, Samuel Keller
 */
public class XlimBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(XlimBackendImpl.class, args);
	}

	/**
	 * Printer for XLIM
	 */
	private XlimActorPrinter printer;

	/**
	 * Create the printer
	 */
	protected void beforeInstantiation(Network network) throws OrccException {
		printer = new XlimActorPrinter();
	}

	@Override
	protected void doActorCodeGeneration(Network network) throws OrccException {
		printInstances(network);
	}

	/**
	 * Print one actor
	 * 
	 * @param id
	 *            Actor id
	 * @param actor
	 *            Actor structure
	 */
	@Override
	protected void printInstance(Instance instance) throws OrccException {
		String id = instance.getId();
		Actor actor = instance.getActor();

		System.out.println("FILE " + id);
		String outputName = path + File.separator + id + ".xlim";
		try {
			printer.printActor(outputName, actor);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	/**
	 * Print the network
	 * 
	 * @param network
	 *            Network structure
	 */
	@Override
	protected void printNetwork(Network network) throws OrccException {
	}

}
