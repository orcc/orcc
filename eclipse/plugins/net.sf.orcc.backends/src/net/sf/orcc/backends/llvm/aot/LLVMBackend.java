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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.llvm.transform.ListInitializer;
import net.sf.orcc.backends.llvm.transform.StringTransformation;
import net.sf.orcc.backends.llvm.transform.TemplateInfoComputing;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.ShortCircuitTransformation;
import net.sf.orcc.backends.transform.ssa.ConstantPropagator;
import net.sf.orcc.backends.transform.ssa.CopyPropagator;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
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
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.tools.merger.actor.ActorMerger;
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
public class LLVMBackend extends AbstractBackend {

	/**
	 * Path to target "src" folder
	 */
	private String srcPath;
	/**
	 * Path to target "lib" folder
	 */
	private String libPath;

	protected final Map<String, String> renameMap;

	/**
	 * Creates a new instance of the LLVM back-end. Initializes the
	 * transformation hash map.
	 */
	public LLVMBackend() {
		renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_");
		renameMap.put("getw", "getw_");
		renameMap.put("index", "index_");
		renameMap.put("min", "min_");
		renameMap.put("max", "max_");
		renameMap.put("select", "select_");
	}

	@Override
	protected void doInitializeOptions() {
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
		libPath = path + File.separator + "libs";
	}

	@Override
	protected void doTransformActor(Actor actor) {
		// do not transform actor
	}

	protected void doTransformNetwork(Network network) {
		OrccLogger.traceln("Analyze and transform the network...");

		List<DfSwitch<?>> visitors = new ArrayList<DfSwitch<?>>();

		visitors.add(new Instantiator(!debug, fifoSize));
		visitors.add(new NetworkFlattener());
		visitors.add(new UnitImporter());

		if (classify) {
			visitors.add(new Classifier());
		}
		if (mergeActions) {
			visitors.add(new ActionMerger());
		}
		if (mergeActors) {
			visitors.add(new ActorMerger());
		}
		if (convertMulti2Mono) {
			visitors.add(new Multi2MonoToken());
		}
		
		visitors.add(new TypeResizer(true, true, false, false));
		visitors.add(new StringTransformation());
		visitors.add(new DfVisitor<Expression>(new ShortCircuitTransformation()));
		visitors.add(new DfVisitor<Void>(new SSATransformation()));
		visitors.add(new DeadGlobalElimination());
		visitors.add(new DfVisitor<Void>(new DeadCodeElimination()));
		visitors.add(new DfVisitor<Void>(new DeadVariableRemoval()));
		visitors.add(new RenameTransformation(this.renameMap));
		visitors.add(new DfVisitor<Expression>(new TacTransformation()));
		visitors.add(new DfVisitor<Void>(new CopyPropagator()));
		visitors.add(new DfVisitor<Void>(new ConstantPropagator()));
		visitors.add(new DfVisitor<Void>(new InstPhiTransformation()));
		visitors.add(new DfVisitor<Expression>(new CastAdder(false, true)));
		visitors.add(new DfVisitor<Void>(new EmptyBlockRemover()));
		visitors.add(new DfVisitor<Void>(new BlockCombine()));
		visitors.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
		visitors.add(new DfVisitor<Void>(new ListInitializer()));

		for (DfSwitch<?> transfo : visitors) {
			transfo.doSwitch(network);
			if (debug) {
				ResourceSet set = new ResourceSetImpl();
				for (Actor actor : network.getAllActors()) {
					if (actor.getFileName() != null
							&& !IrUtil.serializeActor(set, srcPath, actor)) {
						OrccLogger.warnln("Transformation" + transfo
								+ " on actor " + actor.getName());
					}
				}
			}
		}

		new DfVisitor<Void>(new TemplateInfoComputing()).doSwitch(network);
		network.computeTemplateMaps();
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		doTransformNetwork(network);

		// print instances and entities
		printChildren(network);

		// print network
		OrccLogger.traceln("Printing network...");
		new NetworkPrinter(network, options).print(srcPath);

		new CMakePrinter(network, options).printFiles(path);
	}

	@Override
	public boolean exportRuntimeLibrary() {
		if (!getAttribute(NO_LIBRARY_EXPORT, false)) {
			// Copy specific windows batch file
			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
				copyFileToFilesystem("/runtime/C/run_cmake_with_VS_env.bat",
						path + File.separator + "run_cmake_with_VS_env.bat",
						debug);
			}
			OrccLogger.trace("Export libraries sources into " + libPath
					+ "... ");
			if (copyFolderToFileSystem("/runtime/C/libs", libPath, debug)) {
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
		return new InstancePrinter(options).print(srcPath, instance) > 0;
	}

	@Override
	protected boolean printActor(Actor actor) {
		return new InstancePrinter(options).print(srcPath, actor) > 0;
	}

}
