/*
 * Copyright (c) 2009-2011, Artemis SudParis-IETR/INSA of Rennes
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
package net.sf.orcc.backends.llvm.aot;

import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.llvm.transform.ListInitializer;
import net.sf.orcc.backends.llvm.transform.StringTransformation;
import net.sf.orcc.backends.llvm.transform.TemplateInfoComputing;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.TypeResizer;
import net.sf.orcc.backends.transform.ssa.ConstantPropagator;
import net.sf.orcc.backends.transform.ssa.CopyPropagator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.transform.BlockCombine;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.transform.DeadCodeElimination;
import net.sf.orcc.ir.transform.DeadGlobalElimination;
import net.sf.orcc.ir.transform.DeadVariableRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * LLVM back-end.
 * 
 * @author Herve Yviquel
 * 
 */
public class LLVMBackendImpl extends AbstractBackend {

	protected StandardPrinter printer;
	/**
	 * Path to target "src" folder
	 */
	private String srcPath;

	protected final Map<String, String> transformations;

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
	public void doInitializeOptions() {
		// Set build and src directory
		File srcDir = new File(path + File.separator + "src");
		File buildDir = new File(path + File.separator + "build");
		File binDir = new File(path + File.separator + "bin");

		// If directories don't exist, create them
		if (!srcDir.exists()) {
			srcDir.mkdirs();
		}
		if (!buildDir.exists()) {
			buildDir.mkdirs();
		}
		if (!binDir.exists()) {
			binDir.mkdirs();
		}

		// Set src directory as path
		srcPath = srcDir.getAbsolutePath();
	}

	@Override
	protected void doTransformActor(Actor actor) {
		// do not transform actor
	}

	protected Network doTransformNetwork(Network network) {

		// instantiate and flattens network
		OrccLogger.traceln("Instantiating...");
		new Instantiator(false, fifoSize).doSwitch(network);
		OrccLogger.traceln("Flattening...");
		new NetworkFlattener().doSwitch(network);

		DfSwitch<?>[] transformations = { new BroadcastAdder(),
				new UnitImporter(), new TypeResizer(true, true, false),
				new DfVisitor<Void>(new SSATransformation()),
				new DeadGlobalElimination(),
				new DfVisitor<Void>(new DeadCodeElimination()),
				new DfVisitor<Void>(new DeadVariableRemoval()),
				new StringTransformation(),
				new RenameTransformation(this.transformations),
				new DfVisitor<Expression>(new TacTransformation()),
				new DfVisitor<Void>(new CopyPropagator()),
				new DfVisitor<Void>(new ConstantPropagator()),
				new DfVisitor<Void>(new InstPhiTransformation()),
				new DfVisitor<Expression>(new CastAdder(false, true)),
				new DfVisitor<Void>(new EmptyBlockRemover()),
				new DfVisitor<Void>(new BlockCombine()),
				new DfVisitor<CfgNode>(new ControlFlowAnalyzer()),
				new DfVisitor<Void>(new ListInitializer()), };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(network);
			if (debug) {
				ResourceSet set = new ResourceSetImpl();
				for (Actor actor : network.getAllActors()) {
					if (actor.getFileName() != null
							&& !IrUtil.serializeActor(set, srcPath, actor)) {
						System.err.println("Error with " + transformation
								+ " on actor " + actor.getName());
					}
				}
			}
		}

		new DfVisitor<Void>(new TemplateInfoComputing()).doSwitch(network);
		network.computeTemplateMaps();

		for (Actor actor : network.getAllActors()) {
			actor.setTemplateData(new LLVMTemplateData().compute(actor));
		}

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		network = doTransformNetwork(network);

		// print instances and entities
		printer = new StandardPrinter("net/sf/orcc/backends/llvm/aot/Actor.stg");
		printer.setExpressionPrinter(new LLVMExpressionPrinter());
		printer.setTypePrinter(new LLVMTypePrinter());
		printer.getOptions().put("fifoSize", fifoSize);
		printInstances(network);

		// print network
		OrccLogger.traceln("Printing network...");
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/llvm/aot/Network.stg");
		printer.setExpressionPrinter(new LLVMExpressionPrinter());
		printer.setTypePrinter(new LLVMTypePrinter());
		printer.print(network.getSimpleName() + ".ll", srcPath, network);

		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/llvm/aot/CMakeLists.stg");
		networkPrinter.print("CMakeLists.txt", srcPath, network);

		networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/llvm/aot/RootCMakeLists.stg");
		networkPrinter.print("CMakeLists.txt", path, network);
	}

	@Override
	public boolean exportRuntimeLibrary() {
		if (!getAttribute(NO_LIBRARY_EXPORT, false)) {
			// Copy specific windows batch file
			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
				copyFileToFilesystem("/runtime/C/run_cmake_with_VS_env.bat",
						path + File.separator + "run_cmake_with_VS_env.bat");
			}

			String target = path + File.separator + "libs";
			OrccLogger
					.trace("Export libraries sources into " + target + "... ");
			if (copyFolderToFileSystem("/runtime/C/libs", target)) {
				OrccLogger.traceRaw("OK" + "\n");
				return true;
			} else {
				OrccLogger.warnRaw("Error" + "\n");
				return false;
			}
		}
		return false;
	}

	@Override
	protected boolean printInstance(Instance instance) {
		return printer.print(instance.getSimpleName() + ".ll", srcPath,
				instance);
	}

}
