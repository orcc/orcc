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
package net.sf.orcc.backends.llvm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.c.transforms.MoveReadsWritesTransformation;
import net.sf.orcc.backends.llvm.transforms.ThreeAddressCodeTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.AddInstantationProcedure;
import net.sf.orcc.ir.transforms.BuildCFG;
import net.sf.orcc.network.Network;

/**
 * LLVM back-end.
 * 
 * @author Jérôme GORIN
 * 
 */
public class LLVMBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				new LLVMBackendImpl().generateCode(args[0], 10000);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err
					.println("Usage: LLVMBackendImpl <flattened XDF network>");
		}
	}

	private LLVMActorPrinter printer;

	@Override
	protected void afterInstantiation(Network network) throws OrccException {
		if (configuration != null) {
			try {
				boolean classify = configuration.getAttribute(
						"net.sf.orcc.backends.classify", false);
				if (classify) {
					network.classifyActors();
				}
			} catch (CoreException e) {
				throw new OrccException("could not read configuration", e);
			}
		}

		printer = new LLVMActorPrinter();
	}

	@Override
	protected void beforeInstantiation(Network network) throws OrccException {
	}

	@Override
	protected void printActor(String id, Actor actor) throws OrccException {
		ActorTransformation[] transformations = {
				new AddInstantationProcedure(),
				new ThreeAddressCodeTransformation(),
				new MoveReadsWritesTransformation(), new BuildCFG() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		String outputName = path + File.separator + id + ".s";

		try {
			printer.printActor(outputName, id, actor);

			try {
				if (configuration != null) {
					boolean llvmBitcode = configuration.getAttribute(
							"net.sf.orcc.backends.llvmBitcode", false);
					if (llvmBitcode) {
						String llvmAs = configuration.getAttribute(
								"net.sf.orcc.backends.llvm-as", "");
						if (!llvmAs.isEmpty()) {
							printBitcode(llvmAs, outputName, id);
						}
					}
				}
			} catch (CoreException e) {
				throw new OrccException("could not read configuration", e);
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	protected void printBitcode(String execPath, String inputName, String actor) {
		List<String> cmdList = new ArrayList<String>();
		String outputName = path + File.separator + actor + ".bc";

		Runtime run = Runtime.getRuntime();
		cmdList.add(execPath);
		cmdList.add(inputName);
		cmdList.add("-f");
		cmdList.add("-o");
		cmdList.add(outputName);
		String[] cmd = cmdList.toArray(new String[] {});

		try {
			run.exec(cmd);
		} catch (IOException e) {
			System.err.println("Could not print bitcode : ");
			e.printStackTrace();
		}
	}

	@Override
	protected void printNetwork(Network network) throws OrccException {
		// NetworkPrinter networkPrinter = new NetworkPrinter("LLVM_network");

		// Add broadcasts before printing
		// new BroadcastAdder().transform(network);

		// String outputName = path + File.separator + network.getName() + ".s";
		// networkPrinter.printNetwork(outputName, network, false, fifoSize);
	}
}
