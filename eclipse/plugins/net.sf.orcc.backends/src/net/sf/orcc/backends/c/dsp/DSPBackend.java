/*
 * Copyright (c) 2015, IETR/INSA of Rennes, UPM Madrid
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
package net.sf.orcc.backends.c.dsp;

import static net.sf.orcc.backends.BackendsConstants.ADDITIONAL_TRANSFOS;
import static net.sf.orcc.backends.BackendsConstants.BXDF_FILE;
import static net.sf.orcc.backends.BackendsConstants.IMPORT_BXDF;
import static net.sf.orcc.backends.BackendsConstants.DSP_CONFIGURATION;
import static net.sf.orcc.backends.BackendsConstants.DSP_DEFAULT_CONFIGURATION;
import java.io.File;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.c.TracesPrinter;
import net.sf.orcc.backends.c.omp.InstancePrinter;
import net.sf.orcc.backends.c.omp.NetworkPrinter;
import net.sf.orcc.backends.c.transform.CBroadcastAdder;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.DeadVariableRemoval;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.transform.DivisionSubstitution;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.backends.transform.InlinerByAnnotation;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.InstTernaryAdder;
import net.sf.orcc.backends.transform.ListFlattener;
import net.sf.orcc.backends.transform.LoopUnrolling;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.ParameterImporter;
import net.sf.orcc.backends.transform.StoreOnceTransformation;
import net.sf.orcc.backends.util.DSP;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.BroadcastRemover;
import net.sf.orcc.df.transform.FifoSizePropagator;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
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
import net.sf.orcc.ir.transform.SSAVariableRenamer;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.mapping.XmlBufferSizeConfiguration;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.Void;

/**
 * C backend for DSP
 * 
 * @author Miguel Chavarrias
 * @author Alexandre Sanchez
 * 
 */
public class DSPBackend extends CBackend {
	
	private NetworkPrinter networkPrinter;
	private InstancePrinter instancePrinter;
	private TracesPrinter tracesPrinter;

	private CcsProjectPrinter ccsProjectPrinter;
	private DSP dsp;
	
	public DSPBackend() {
		instancePrinter = new InstancePrinter();
		networkPrinter = new NetworkPrinter();
		tracesPrinter = new TracesPrinter();

		ccsProjectPrinter = new CcsProjectPrinter();
	}

	@Override
	protected void doInitializeOptions() {
		// Configure paths
		srcPath = outputPath + File.separator + "src";

		// Configure DSP
		dsp = DSP.builder(getOption(DSP_CONFIGURATION, DSP_DEFAULT_CONFIGURATION));
		ccsProjectPrinter.setDsp(dsp);
		
		// Load options map into code generator instances
		networkPrinter.setOptions(getOptions());
		instancePrinter.setOptions(getOptions());
		tracesPrinter.setOptions(getOptions());

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		if (mergeActors) {
			networkTransfos.add(new FifoSizePropagator(fifoSize));
			networkTransfos.add(new BroadcastAdder());
		}
		networkTransfos.add(new Instantiator(true));
		networkTransfos.add(new NetworkFlattener());
		networkTransfos.add(new UnitImporter());
		networkTransfos.add(new DisconnectedOutputPortRemoval());
		if (classify) {
			networkTransfos.add(new Classifier());
		}
		if (mergeActors) {
			networkTransfos.add(new ActorMerger());
		} else {
			networkTransfos.add(new CBroadcastAdder());
		}
		if (mergeActors) {
			networkTransfos.add(new BroadcastRemover());
		}
		networkTransfos.add(new ArgumentEvaluator());
		networkTransfos.add(new TypeResizer(true, false, true, false));
		networkTransfos.add(new RenameTransformation(getRenameMap()));

		// -------------------------------------------------------------------
		// Transformations that will be applied on children (instances/actors)
		// -------------------------------------------------------------------
		if (mergeActions) {
			childrenTransfos.add(new ActionMerger());
		}
		if (convertMulti2Mono) {
			childrenTransfos.add(new Multi2MonoToken());
		}
		childrenTransfos.add(new DfVisitor<Void>(new InlinerByAnnotation()));
		childrenTransfos.add(new DfVisitor<Void>(new LoopUnrolling()));

		// If "-t" option is passed to command line, apply additional
		// transformations
		if (getOption(ADDITIONAL_TRANSFOS, false)) {
			childrenTransfos.add(new StoreOnceTransformation());
			childrenTransfos.add(new DfVisitor<Void>(new SSATransformation()));
			childrenTransfos.add(new DfVisitor<Void>(new PhiRemoval()));
			childrenTransfos.add(new Multi2MonoToken());
			childrenTransfos.add(new DivisionSubstitution());
			childrenTransfos.add(new ParameterImporter());
			childrenTransfos.add(new DfVisitor<Void>(new Inliner(true, true)));

			// transformations.add(new UnaryListRemoval());
			// transformations.add(new GlobalArrayInitializer(true));

			childrenTransfos.add(new DfVisitor<Void>(new InstTernaryAdder()));
			childrenTransfos.add(new DeadGlobalElimination());

			childrenTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			childrenTransfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
			childrenTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			childrenTransfos.add(new DfVisitor<Void>(new ListFlattener()));
			childrenTransfos.add(new DfVisitor<Expression>(
					new TacTransformation()));
			childrenTransfos.add(new DfVisitor<CfgNode>(
					new ControlFlowAnalyzer()));
			childrenTransfos
					.add(new DfVisitor<Void>(new InstPhiTransformation()));
			childrenTransfos.add(new DfVisitor<Void>(new EmptyBlockRemover()));
			childrenTransfos.add(new DfVisitor<Void>(new BlockCombine()));

			childrenTransfos.add(new DfVisitor<Expression>(new CastAdder(true,
					true)));
			childrenTransfos.add(new DfVisitor<Void>(new SSAVariableRenamer()));
		}
	}
	
	@Override
	protected void beforeGeneration(Network network) {
		network.computeTemplateMaps();

		// if required, load the buffer size from the mapping file
		if (getOption(IMPORT_BXDF, false)) {
			File f = new File(getOption(BXDF_FILE, ""));
			new XmlBufferSizeConfiguration().load(f, network);
		}

		if(network.getVertex(network.getSimpleName()) != null) {
			final StringBuilder warnMsg = new StringBuilder();
			warnMsg.append('"').append(network.getSimpleName()).append('"');
			warnMsg.append(" is the name of both the network you want to generate");
			warnMsg.append(" and a vertex in this network.").append('\n');
			warnMsg.append("The 2 entities will be generated");
			warnMsg.append(" in the same file. Please rename one of these elements to prevent");
			warnMsg.append(" unwanted overwriting.");
			OrccLogger.warnln(warnMsg.toString());
		}
	}

	@Override
	protected Result doLibrariesExtraction() {
		final Result result = FilesManager.extract("/runtime/DSP/README.txt", outputPath);

		result.merge(FilesManager.extract("/runtime/C/libs/orcc-native", outputPath));
		result.merge(FilesManager.extract("/runtime/C/libs/orcc-runtime", outputPath));
		result.merge(FilesManager.extract("/runtime/C/libs/roxml", outputPath));

		result.merge(FilesManager.extract("/runtime/DSP/config/config.cfg", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/config/config.h", srcPath));

		//! TODO : Merge DSP-native and orcc-native in this backend ASAP
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/cache.c", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/cache.h", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/medidas.c", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/medidas.h", srcPath));
		
		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		final Result result = Result.newInstance();
		final Mapping mapper = new Mapping(network, mapping);

		result.merge(FilesManager.writeFile(mapper.getContentFile(), srcPath,
				network.getSimpleName() + ".xcf"));

		String libOrccNativePath = outputPath + File.separator + "orcc-native";
		String libOrccRuntimePath = outputPath + File.separator + "orcc-runtime";
		String libRoxmlPath = outputPath + File.separator + "roxml";

		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcxxml(dsp), srcPath, dsp.getDevice() + ".ccxml"));

		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject(network.getSimpleName()), srcPath, ".project"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject("orcc-native"), libOrccNativePath, ".project"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject("orcc-runtime"), libOrccRuntimePath, ".project"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject("roxml"), libRoxmlPath, ".project"));

		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, network.getSimpleName()), srcPath, ".cproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, "orcc-native"), libOrccNativePath, ".cproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, "orcc-runtime"), libOrccRuntimePath, ".cproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, "roxml"), libRoxmlPath, ".cproject"));
		
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, true), srcPath, ".ccsproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, false), libOrccNativePath, ".ccsproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, false), libOrccRuntimePath, ".ccsproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, false), libRoxmlPath, ".ccsproject"));
		
		return result;
	}
}
