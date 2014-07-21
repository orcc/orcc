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
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.ShortCircuitTransformation;
import net.sf.orcc.backends.transform.ssa.ConstantPropagator;
import net.sf.orcc.backends.transform.ssa.CopyPropagator;
import net.sf.orcc.backends.util.Alignable;
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
import net.sf.orcc.ir.transform.SSAVariableRenamer;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.Void;

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

	@Override
	protected void doInitializeOptions() {

		// Create the empty folders
		new File(path, "bin").mkdir();
		new File(path, "build").mkdir();

		// Configure the path where source files will be written
		srcPath = new File(path, "src").toString();

		// Configure the map used in RenameTransformation
		final Map<String, String> renameMap;
		renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_");
		renameMap.put("getw", "getw_");
		renameMap.put("index", "index_");
		renameMap.put("min", "min_");
		renameMap.put("max", "max_");
		renameMap.put("select", "select_");

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		networkTransfos.add(new Instantiator(!debug));
		networkTransfos.add(new NetworkFlattener());
		networkTransfos.add(new UnitImporter());

		if (classify) {
			networkTransfos.add(new Classifier());
		}
		if (mergeActions) {
			networkTransfos.add(new ActionMerger());
		}
		if (mergeActors) {
			networkTransfos.add(new ActorMerger());
		}
		if (convertMulti2Mono) {
			networkTransfos.add(new Multi2MonoToken());
		}

		networkTransfos.add(new DisconnectedOutputPortRemoval());
		networkTransfos.add(new TypeResizer(true, false, false, false));
		networkTransfos.add(new StringTransformation());
		networkTransfos.add(new DfVisitor<Expression>(new ShortCircuitTransformation()));
		networkTransfos.add(new DfVisitor<Void>(new SSATransformation()));
		networkTransfos.add(new DeadGlobalElimination());
		networkTransfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
		networkTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
		networkTransfos.add(new RenameTransformation(renameMap));
		networkTransfos.add(new DfVisitor<Expression>(new TacTransformation()));
		networkTransfos.add(new DfVisitor<Void>(new CopyPropagator()));
		networkTransfos.add(new DfVisitor<Void>(new ConstantPropagator()));
		networkTransfos.add(new DfVisitor<Void>(new InstPhiTransformation()));
		networkTransfos.add(new DfVisitor<Expression>(new CastAdder(false, true)));
		networkTransfos.add(new DfVisitor<Void>(new EmptyBlockRemover()));
		networkTransfos.add(new DfVisitor<Void>(new BlockCombine()));
		networkTransfos.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
		networkTransfos.add(new DfVisitor<Void>(new ListInitializer()));

		// computes names of local variables
		networkTransfos.add(new DfVisitor<Void>(new SSAVariableRenamer()));
	}

	@Override
	protected void doValidate(Network network) {
		super.doValidate(network);

		new DfVisitor<Void>(new TemplateInfoComputing()).doSwitch(network);
		network.computeTemplateMaps();
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		// update "vectorizable" information
		Alignable.setAlignability(network);

		// print instances and entities
		printChildren(network);

		// print network
		OrccLogger.traceln("Printing network...");
		new NetworkPrinter(network, getOptions()).print(srcPath);

		CMakePrinter printer = new CMakePrinter(network);
		FilesManager.writeFile(printer.rootCMakeContent(), path, "CMakeLists.txt");
		FilesManager.writeFile(printer.srcCMakeContent(), srcPath, "CMakeLists.txt");
	}

	@Override
	protected Result extractLibraries() {
		Result result = FilesManager.extract("/runtime/C/README.txt", path);
		// Copy specific windows batch file
		if (FilesManager.getCurrentOS() == FilesManager.OS_WINDOWS) {
			result.merge(FilesManager.extract(
					"/runtime/C/run_cmake_with_VS_env.bat", path));
		}

		OrccLogger.traceln("Export libraries sources");
		result.merge(FilesManager.extract("/runtime/C/libs", path));

		return result;
	}

	@Override
	protected boolean printInstance(Instance instance) {
		return new InstancePrinter(getOptions()).print(srcPath, instance) > 0;
	}

	@Override
	protected boolean printActor(Actor actor) {
		return new InstancePrinter(getOptions()).print(srcPath, actor) > 0;
	}
}
