/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c;

import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;
import static net.sf.orcc.backends.BackendsConstants.ADDITIONAL_TRANSFOS;
import static net.sf.orcc.backends.BackendsConstants.GENETIC_ALGORITHM;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.c.transform.CBroadcastAdder;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.DeadVariableRemoval;
import net.sf.orcc.backends.transform.DivisionSubstitution;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.InstTernaryAdder;
import net.sf.orcc.backends.transform.ListFlattener;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.ParameterImporter;
import net.sf.orcc.backends.transform.StoreOnceTransformation;
import net.sf.orcc.backends.util.Metis;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.ArgumentEvaluator;
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
import net.sf.orcc.ir.transform.PhiRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.tools.stats.StatisticsPrinter;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * C back-end.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @author Antoine Lorence
 * 
 */
public class CBackend extends AbstractBackend {

	/**
	 * Path to target "src" folder
	 */
	private String srcPath;

	@Override
	protected void doInitializeOptions() {
		// Create empty folders
		new File(path + File.separator + "build").mkdirs();
		new File(path + File.separator + "bin").mkdirs();

		srcPath = path + File.separator + "src";
	}

	@Override
	protected void doTransformActor(Actor actor) {
		Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("abs", "abs_my_precious");
		replacementMap.put("getw", "getw_my_precious");
		replacementMap.put("index", "index_my_precious");
		replacementMap.put("max", "max_my_precious");
		replacementMap.put("min", "min_my_precious");
		replacementMap.put("select", "select_my_precious");
		replacementMap.put("OUT", "OUT_my_precious");
		replacementMap.put("IN", "IN_my_precious");

		if (mergeActions) {
			new ActionMerger().doSwitch(actor);
		}
		if (convertMulti2Mono) {
			new Multi2MonoToken().doSwitch(actor);
		}

		List<DfSwitch<?>> transformations = new ArrayList<DfSwitch<?>>();
		transformations.add(new TypeResizer(true, false, true, false));
		transformations.add(new RenameTransformation(replacementMap));

		// If "-t" option is passed to command line, apply additional
		// transformations
		if (getAttribute(ADDITIONAL_TRANSFOS, false)) {
			transformations.add(new StoreOnceTransformation());
			transformations.add(new DfVisitor<Void>(new SSATransformation()));
			transformations.add(new DfVisitor<Object>(new PhiRemoval()));
			transformations.add(new Multi2MonoToken());
			transformations.add(new DivisionSubstitution());
			transformations.add(new ParameterImporter());
			transformations.add(new DfVisitor<Void>(new Inliner(true, true)));

			// transformations.add(new UnaryListRemoval());
			// transformations.add(new GlobalArrayInitializer(true));

			transformations.add(new DfVisitor<Void>(new InstTernaryAdder()));
			transformations.add(new DeadGlobalElimination());

			transformations.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			transformations.add(new DfVisitor<Void>(new DeadCodeElimination()));
			transformations.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			transformations.add(new DfVisitor<Void>(new ListFlattener()));
			transformations.add(new DfVisitor<Expression>(
					new TacTransformation()));
			transformations.add(new DfVisitor<CfgNode>(
					new ControlFlowAnalyzer()));
			transformations
					.add(new DfVisitor<Void>(new InstPhiTransformation()));
			transformations.add(new DfVisitor<Void>(new EmptyBlockRemover()));
			transformations.add(new DfVisitor<Void>(new BlockCombine()));

			transformations.add(new DfVisitor<Expression>(new CastAdder(true,
					true)));
		}

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
			if (debug) {
				ResourceSet set = new ResourceSetImpl();
				if (!IrUtil.serializeActor(set, srcPath, actor)) {
					OrccLogger.warnln("Error with " + transformation
							+ " on actor " + actor.getName());
				}
			}
		}
	}

	protected void doTransformNetwork(Network network) {
		OrccLogger.traceln("Instantiating...");
		new Instantiator(true, fifoSize).doSwitch(network);
		OrccLogger.traceln("Flattening...");
		new NetworkFlattener().doSwitch(network);
		new UnitImporter().doSwitch(network);

		if (classify) {
			OrccLogger.traceln("Classification of actors...");
			new Classifier().doSwitch(network);
		}
		if (mergeActors) {
			OrccLogger.traceln("Merging of actors...");
			new ActorMerger().doSwitch(network);
		}

		new CBroadcastAdder().doSwitch(network);
		new ArgumentEvaluator().doSwitch(network);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		doTransformNetwork(network);

		if (debug) {
			// Serialization of the actors will break proxy link
			EcoreUtil.resolveAll(network);
		}
		transformActors(network.getAllActors());

		network.computeTemplateMaps();

		// print instances
		printChildren(network);

		// print network
		OrccLogger.trace("Printing network... ");
		if (new NetworkPrinter(network, options).print(srcPath) > 0) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}

		// print CMakeLists
		OrccLogger.traceln("Printing CMake project files");
		new CMakePrinter(network).printCMakeFiles(path);
		new StatisticsPrinter().print(srcPath, network);

		if (balanceMapping) {
			// Solve load balancing using Metis. The 'mapping' variable should
			// be the weightsMap, giving a weight to each actor/instance.
			mapping = new Metis().partition(network, path, processorNumber,
					mapping);
		}
		if (!getAttribute(GENETIC_ALGORITHM, false)) {
			new Mapping(network, mapping).print(srcPath);
		}
	}

	@Override
	public boolean exportRuntimeLibrary() {
		boolean exportLibrary = !getAttribute(NO_LIBRARY_EXPORT, false);

		if (exportLibrary) {
			String libsPath = path + File.separator + "libs";

			// Copy specific windows batch file
			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
				copyFileToFilesystem("/runtime/C/run_cmake_with_VS_env.bat",
						path + File.separator + "run_cmake_with_VS_env.bat",
						debug);
			}

			copyFileToFilesystem("/runtime/C/README.txt", path + File.separator
					+ "README.txt", debug);

			OrccLogger.trace("Export libraries sources into " + libsPath
					+ "... ");
			if (copyFolderToFileSystem("/runtime/C/libs", libsPath, debug)) {
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
