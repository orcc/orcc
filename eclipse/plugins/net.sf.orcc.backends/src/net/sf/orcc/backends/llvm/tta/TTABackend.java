/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta;

import static net.sf.orcc.backends.BackendsConstants.FPGA_CONFIGURATION;
import static net.sf.orcc.backends.BackendsConstants.FPGA_DEFAULT_CONFIGURATION;

import java.io.File;

import net.sf.orcc.backends.llvm.aot.LLVMBackend;
import net.sf.orcc.backends.llvm.transform.ListInitializer;
import net.sf.orcc.backends.llvm.transform.TemplateInfoComputing;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureBuilder;
import net.sf.orcc.backends.llvm.tta.transform.ComplexHwOpDetector;
import net.sf.orcc.backends.llvm.tta.transform.PrintRemoval;
import net.sf.orcc.backends.llvm.tta.transform.StringTransformation;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.ShortCircuitTransformation;
import net.sf.orcc.backends.transform.ssa.ConstantPropagator;
import net.sf.orcc.backends.transform.ssa.CopyPropagator;
import net.sf.orcc.backends.util.Alignable;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.FifoSizePropagator;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.util.Dota;
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
 * TTA back-end.
 * 
 * @author Herve Yviquel
 * 
 */
public class TTABackend extends LLVMBackend {

	private String actorsPath;

	private ArchitectureBuilder architectureBuilder;
	private Design design;
	private FPGA fpga;

	private HwDesignPrinter hwDesignPrinter;
	private HwProcessorPrinter hwProcessorPrinter;
	private HwProjectPrinter hwProjectPrinter;
	private HwTestbenchPrinter hwTestbenchPrinter;
	private PyDesignPrinter pyDesignPrinter;
	private SwActorPrinter swActorPrinter;
	private SwProcessorPrinter swProcessorPrinter;
	private TceDesignPrinter tceDesignPrinter;
	private TceProcessorPrinter tceProcessorPrinter;
	private Dota dota;

	public TTABackend() {
		super();

		hwDesignPrinter = new HwDesignPrinter();
		hwProcessorPrinter = new HwProcessorPrinter();
		hwProjectPrinter = new HwProjectPrinter();
		hwTestbenchPrinter = new HwTestbenchPrinter();
		pyDesignPrinter = new PyDesignPrinter();
		swActorPrinter = new SwActorPrinter();
		swProcessorPrinter = new SwProcessorPrinter();
		tceDesignPrinter = new TceDesignPrinter();
		tceProcessorPrinter = new TceProcessorPrinter();
		dota = new Dota();

		architectureBuilder = new ArchitectureBuilder();
	}

	@Override
	protected void doInitializeOptions() {
		fpga = FPGA.builder(getOption(FPGA_CONFIGURATION,
				FPGA_DEFAULT_CONFIGURATION));

		// Configure the options used in code generation
		swActorPrinter.setOptions(getOptions());
		architectureBuilder.setOptions(getOptions());

		// Create the directory tree
		actorsPath = OrccUtil.createFolder(path, "actors");

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------

		if (mergeActors) {
			networkTransfos.add(new FifoSizePropagator(fifoSize));
			networkTransfos.add(new BroadcastAdder());
		}
		networkTransfos.add(new ComplexHwOpDetector());
		networkTransfos.add(new UnitImporter());
		networkTransfos.add(new Instantiator(true));
		networkTransfos.add(new NetworkFlattener());

		if (classify) {
			networkTransfos.add(new Classifier());
		}
		if (mergeActions) {
			networkTransfos.add(new ActionMerger());
		}
		if (mergeActors) {
			networkTransfos.add(new ActorMerger());
		}

		if (!debug) {
			networkTransfos.add(new PrintRemoval());
		}

		networkTransfos.add(new DisconnectedOutputPortRemoval());

		networkTransfos.add(new TypeResizer(true, true, false, true));
		networkTransfos.add(new DfVisitor<Expression>(
				new ShortCircuitTransformation()));
		networkTransfos.add(new DfVisitor<Void>(new SSATransformation()));
		networkTransfos.add(new StringTransformation());
		networkTransfos.add(new RenameTransformation(this.renameMap));
		networkTransfos.add(new DfVisitor<Expression>(new TacTransformation()));
		networkTransfos.add(new DeadGlobalElimination());
		networkTransfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
		networkTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
		networkTransfos.add(new DfVisitor<Void>(new CopyPropagator()));
		networkTransfos.add(new DfVisitor<Void>(new ConstantPropagator()));
		networkTransfos.add(new DfVisitor<Void>(new InstPhiTransformation()));
		networkTransfos.add(new DfVisitor<Expression>(
				new CastAdder(false, true)));
		networkTransfos.add(new DfVisitor<Void>(new EmptyBlockRemover()));
		networkTransfos.add(new DfVisitor<Void>(new BlockCombine()));
		networkTransfos.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
		networkTransfos.add(new DfVisitor<Void>(new ListInitializer()));
		networkTransfos.add(new DfVisitor<Void>(new TemplateInfoComputing()));

		// computes names of local variables
		networkTransfos.add(new DfVisitor<Void>(new SSAVariableRenamer()));

	}

	@Override
	protected Result doGenerateNetwork(Network network) {
		// Do nothing
		return Result.newInstance();
	}

	@Override
	protected void doValidate(Network network) {
		// FIXME: Allow native ports in top level checking
		// Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		// Configuration before the code generation
		// FIXME: Make it in better place

		// Compute the actor mapping from a xcf file or from the user interface
		Mapping computedMapping;
		if (importXcfFile) {
			computedMapping = new Mapping(network, xcfFile);
		} else {
			computedMapping = new Mapping(network, mapping);
		}

		network.computeTemplateMaps();

		// Update alignment information
		Alignable.setAlignability(network);

		if (!debug) {
			OrccLogger.noticeln("All calls to printing functions are"
					+ " removed for performance purpose.");
		} else {
			OrccLogger.noticeln("A noticeable deterioration in "
					+ "performance could appear due to printing call.");
		}

		// Build the design from the mapping
		design = architectureBuilder.build(network, computedMapping);
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		final Result result = Result.newInstance();

		OrccLogger.traceln("Design generation...");

		hwProcessorPrinter.setFpga(fpga);
		tceProcessorPrinter.setHwDb(design.getHardwareDatabase());
		swProcessorPrinter.setOptions(getOptions());

		for (Processor tta : design.getProcessors()) {
			String processorPath = OrccUtil.createFolder(path, tta.getName());

			// Print VHDL description
			result.merge(FilesManager.writeFile(
					hwProcessorPrinter.getVhdl(tta), processorPath,
					tta.getName() + ".vhd"));
			// Print high-level description
			tceProcessorPrinter.print(tta, processorPath);
			// Print assembly code of actor-scheduler
			result.merge(FilesManager.writeFile(
					swProcessorPrinter.getContent(tta), processorPath,
					tta.getName() + ".ll"));
		}

		// Create HDL project
		hwDesignPrinter.setFpga(fpga);
		result.merge(FilesManager.writeFile(hwDesignPrinter.getVhdl(design),
				path, "top.vhd"));

		hwProjectPrinter.setFpga(fpga);
		if (fpga.isAltera()) {
			result.merge(FilesManager.writeFile(
					hwProjectPrinter.getQcf(design), path, "top.qsf"));
			result.merge(FilesManager.writeFile(
					hwProjectPrinter.getQpf(design), path, "top.qpf"));
		} else {
			result.merge(FilesManager.writeFile(
					hwProjectPrinter.getUcf(design), path, "top.ucf"));
			result.merge(FilesManager.writeFile(
					hwProjectPrinter.getXise(design), path, "top.xise"));
		}

		hwTestbenchPrinter.setFpga(fpga);
		result.merge(FilesManager.writeFile(hwTestbenchPrinter.getVhdl(design),
				path, "top_tb.vhd"));
		result.merge(FilesManager.writeFile(hwTestbenchPrinter.getWave(design),
				path, "wave.do"));
		result.merge(FilesManager.writeFile(hwTestbenchPrinter.getTcl(design),
				path, "top.tcl"));

		// Create TCE project
		String pyPath = OrccUtil.createFolder(path, "informations_");
		OrccUtil.createFile(pyPath, "__init__.py");
		pyDesignPrinter.setFpga(fpga);
		result.merge(FilesManager.writeFile(pyDesignPrinter.getPython(design),
				pyPath, "informations.py"));

		tceDesignPrinter.setOptions(getOptions());
		tceDesignPrinter.setPath(path);
		result.merge(FilesManager.writeFile(tceDesignPrinter.getPndf(design),
				path, "top.pndf"));

		result.merge(FilesManager.writeFile(dota.dot(design), path, "top.dot"));

		return result;
	}

	@Override
	protected Result doLibrariesExtraction() {
		Result result = FilesManager.extract("/runtime/TTA/libs", path);
		result.merge(FilesManager.extract("/runtime/common/scripts", path));

		// Will be used later to execute the scripts
		String libPath = path + File.separator + "libs";

		// Ensure scripts have execution rights
		new File(libPath, "ttanetgen").setExecutable(true);
		new File(libPath, "ttaanalyse.py").setExecutable(true);
		new File(libPath, "ttamergehtml.py").setExecutable(true);
		new File(libPath, "ttamergecsv.py").setExecutable(true);
		new File(libPath, "ttamerge.py").setExecutable(true);

		// TODO: This renaming will become useless when the TTA specific scripts
		// will be moved into the right folder (/runtime/TTA/scripts) and
		// extracted under <path>/scripts
		new File(path, "scripts").renameTo(new File(path, "libs/common"));

		return result;
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		swActorPrinter.setProcessor(design.getActorToProcessorMap().get(actor));
		return FilesManager.writeFile(swActorPrinter.getContent(actor),
				actorsPath, actor.getName() + ".ll");
	}

}
