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
import static net.sf.orcc.preferences.PreferenceConstants.P_JADETOOLBOX;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
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
import net.sf.orcc.tools.classifier.ActorClassifier;
import net.sf.orcc.tools.normalizer.ActorNormalizer;
import net.sf.orcc.ui.OrccActivator;
import net.sf.orcc.util.OrccUtil;

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
	/**
	 * Path of JadeToolbox executable
	 */
	protected String jadeToolbox;
	private String llvmGenMod;
	private boolean normalize;

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
		if (classify) {
			new ActorClassifier().transform(actor);

			if (normalize) {
				new ActorNormalizer().transform(actor);
			}
		}

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

		printer = new STPrinter(getAttribute(DEBUG_MODE, false));
		printer.loadGroups("LLVM_header", "LLVM_actor", "LLVM_metadata");
		printer.setExpressionPrinter(LLVMExprPrinter.class);
		printer.setTypePrinter(LLVMTypePrinter.class);

		// transforms and prints actors
		transformActors(actors);
		printActors(actors);

		// Finalize actor generation
		write("Finalize actors...\n");
		finalizeActors(actors);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		// print network
		write("Printing network...\n");
		new XDFWriter(new File(path), network);
	}

	private void finalizeActors(List<Actor> actors) throws OrccException {
		// Jade location has not been set
		if (jadeToolbox.equals("")) {
			if (!optLevel.equals("O0") || !llvmGenMod.equals("Assembly")) {
				throw new OrccRuntimeException(
						"For optimizing, generating bitcode or archive, Jade Toolbox path must first be set in window->Preference->Orcc");
			} else {
				return;
			}
		}

		// JadeToolbox is required to finalize actors
		runJadeToolBox(actors);
	}

	@Override
	protected boolean printActor(Actor actor) throws OrccException {
		// Create folder if necessary
		String folder = path + File.separator + OrccUtil.getFolder(actor);
		new File(folder).mkdirs();

		// Set output file name for this actor
		String outputName = folder + File.separator + actor.getSimpleName();

		try {
			boolean cached = printer.printActor(outputName, actor);

			return cached;
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	private void runJadeToolBox(List<Actor> actors) throws OrccException {
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(jadeToolbox);
		cmdList.add("-" + optLevel);
		cmdList.add("-L");
		cmdList.add(path);

		// Set generation mode
		if (llvmGenMod.equals("Assembly")) {
			cmdList.add("-S");
		} else if (llvmGenMod.equals("Bitcode")) {
			cmdList.add("-c");
		} else if (llvmGenMod.equals("Archive")) {
			cmdList.add("-a");
		}

		// Add list of actor
		for (Actor actor : actors) {
			cmdList.add(actor.getName());
		}

		String[] cmd = cmdList.toArray(new String[] {});

		// Launch application
		try {
			startExec(cmd);
		} catch (IOException e) {
			System.err.println("Jade toolbox error : ");
			e.printStackTrace();
		}
	}

	private void setBackendOptions() throws OrccException {
		llvmGenMod = getAttribute("net.sf.orcc.backends.llvmMode", "Assembly");
		optLevel = getAttribute("net.sf.orcc.backends.optLevel", "O0");
		classify = getAttribute("net.sf.orcc.backends.classify", false);
		normalize = getAttribute("net.sf.orcc.backends.normalize", false);
		jadeToolbox = OrccActivator.getDefault().getPreferenceStore()
				.getString(P_JADETOOLBOX);
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
							write("Generation error :" + line + "\n");
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
