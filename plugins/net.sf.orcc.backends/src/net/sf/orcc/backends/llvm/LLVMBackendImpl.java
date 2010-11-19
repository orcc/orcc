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

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.backends.llvm.transformations.AddGEPTransformation;
import net.sf.orcc.backends.llvm.transformations.BoolToIntTransformation;
import net.sf.orcc.backends.llvm.transformations.PrintlnTransformation;
import net.sf.orcc.backends.transformations.MoveReadsWritesTransformation;
import net.sf.orcc.backends.transformations.RenameTransformation;
import net.sf.orcc.backends.transformations.TypeSizeTransformation;
import net.sf.orcc.backends.transformations.threeAddressCodeTransformation.ThreeAddressCodeTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFWriter;

/**
 * LLVM back-end.
 * 
 * @author Jerome GORIN
 * 
 */
public class LLVMBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(LLVMBackendImpl.class, args);
	}

	/**
	 * Backend options
	 */
	private boolean classify;

	private String llvmAs;

	private boolean llvmBitcode;
	private String llvmOpt;
	private boolean opt;
	private String optLevel;
	private STPrinter printer;
	private final Map<String, String> transformations;

	/**
	 * Creates a new instance of the LLVM back-end. Initializes the
	 * transformation hash map.
	 */
	public LLVMBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_");
		transformations.put("getw", "getw_");
		transformations.put("index", "index_");
		transformations.put("min", "min_");
		transformations.put("max", "max_");
		transformations.put("select", "select_");
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = { new TypeSizeTransformation(),
				new BoolToIntTransformation(), new PrintlnTransformation(),
				new RenameTransformation(this.transformations),
				new ThreeAddressCodeTransformation(),
				new MoveReadsWritesTransformation(), new AddGEPTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		// Organize medata information for the current actor
		actor.setTemplateData(new LLVMTemplateData(actor));
	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		setBackendOptions();

		List<Actor> actors = parseActors(files);

		if (classify) {
			// TODO classify actors
		}

		printer = new STPrinter(getAttribute(DEBUG_MODE, false));
		printer.loadGroups("LLVM_header", "LLVM_actor", "LLVM_metadata");
		printer.setExpressionPrinter(LLVMExprPrinter.class);
		printer.setTypePrinter(LLVMTypePrinter.class);

		// transforms and prints actors
		transformActors(actors);
		printActors(actors);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		// print network
		write("Printing network...\n");
		new XDFWriter(new File(path), network);
	}

	private void optimizeActor(String execPath, String inputName) {
		List<String> cmdList = new ArrayList<String>();

		cmdList.add(execPath);
		cmdList.add(inputName);
		cmdList.add("-S");
		cmdList.add("-" + optLevel);
		cmdList.add("-o");
		cmdList.add(inputName);

		String[] cmd = cmdList.toArray(new String[] {});

		try {
			startExec(cmd);
		} catch (IOException e) {
			System.err.println("Could not optimize actors : ");
			e.printStackTrace();
		}
	}

	@Override
	protected boolean printActor(Actor actor) throws OrccException {
		String outputName = path + File.separator + actor.getName() + ".s";

		try {
			boolean cached = printer.printActor(outputName, actor);

			if (opt) {
				optimizeActor(llvmOpt, outputName);
			}

			if (llvmBitcode) {
				printBitcode(llvmAs, outputName, actor.getName());
			}

			return cached;
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	private void printBitcode(String execPath, String inputName, String actor) {
		List<String> cmdList = new ArrayList<String>();
		String outputName = path + File.separator + actor + ".bc";

		cmdList.add(execPath);
		cmdList.add(inputName);
		cmdList.add("-f");
		cmdList.add("-o");
		cmdList.add(outputName);
		String[] cmd = cmdList.toArray(new String[] {});

		try {
			startExec(cmd);
		} catch (IOException e) {
			System.err.println("Could not print bitcode : ");
			e.printStackTrace();
		}
	}

	private void setBackendOptions() throws OrccException {
		llvmBitcode = getAttribute("net.sf.orcc.backends.llvmBitcode", false);
		classify = getAttribute("net.sf.orcc.backends.classify", false);
		opt = getAttribute("net.sf.orcc.backends.llvmOpt", false);

		if (llvmBitcode) {
			llvmAs = getAttribute("net.sf.orcc.backends.llvm-as", "");
		}

		if (opt) {
			llvmOpt = getAttribute("net.sf.orcc.backends.opt", "");
			optLevel = getAttribute("net.sf.orcc.backends.optLevel", "");
		}

	}

	private void startExec(String[] cmd) throws IOException {
		Runtime run = Runtime.getRuntime();
		final Process process = run.exec(cmd);

		// Output error message
		new Thread() {
			@Override
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(process.getErrorStream()));
					try {
						String line = reader.readLine();
						if (line != null) {
							write("Generation error :" + line +"\n");
						}
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
