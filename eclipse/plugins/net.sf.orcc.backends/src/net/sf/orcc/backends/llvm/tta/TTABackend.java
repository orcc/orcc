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
import static net.sf.orcc.backends.BackendsConstants.TTA_DEFAULT_PROCESSORS_CONFIGURATION;
import static net.sf.orcc.backends.BackendsConstants.TTA_PROCESSORS_CONFIGURATION;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccLaunchConstants;
import net.sf.orcc.backends.llvm.aot.LLVMBackend;
import net.sf.orcc.backends.llvm.transform.ListInitializer;
import net.sf.orcc.backends.llvm.transform.TemplateInfoComputing;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration;
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
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
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

	String actorsPath;
	private Mapping computedMapping;
	private ProcessorConfiguration configuration;
	private Design design;
	private boolean finalize;

	private FPGA fpga;
	private String libPath;
	private boolean reduceConnections;

	@Override
	protected void doInitializeOptions() {
		finalize = getOption("net.sf.orcc.backends.tta.finalizeGeneration",
				false);
		fpga = FPGA.builder(getOption(FPGA_CONFIGURATION,
				FPGA_DEFAULT_CONFIGURATION));
		configuration = ProcessorConfiguration.getByName(getOption(
				TTA_PROCESSORS_CONFIGURATION,
				TTA_DEFAULT_PROCESSORS_CONFIGURATION));
		reduceConnections = getOption(
				"net.sf.orcc.backends.llvm.tta.reduceConnections", false);
	}

	@Override
	protected void doTransformActor(Actor actor) {
		// do not transform actors
	}

	protected void doTransformNetwork(Network network) {
		OrccLogger.traceln("Analyze and transform the network...");

		List<DfSwitch<?>> visitors = new ArrayList<DfSwitch<?>>();

		visitors.add(new ComplexHwOpDetector());
		visitors.add(new UnitImporter());
		visitors.add(new Instantiator(true));
		visitors.add(new NetworkFlattener());

		if (classify) {
			visitors.add(new Classifier());
		}
		if (mergeActions) {
			visitors.add(new ActionMerger());
		}
		if (mergeActors) {
			visitors.add(new ActorMerger());
		}

		if (!debug) {
			visitors.add(new PrintRemoval());
			OrccLogger.noticeln("All calls to printing functions are"
					+ " removed for performance purpose.");
		} else {
			OrccLogger.noticeln("A noticeable deterioration in "
					+ "performance could appear due to printing call.");
		}

		visitors.add(new DisconnectedOutputPortRemoval());

		visitors.add(new TypeResizer(true, true, false, true));
		visitors.add(new DfVisitor<Expression>(new ShortCircuitTransformation()));
		visitors.add(new DfVisitor<Void>(new SSATransformation()));
		visitors.add(new StringTransformation());
		visitors.add(new RenameTransformation(this.renameMap));
		visitors.add(new DfVisitor<Expression>(new TacTransformation()));
		visitors.add(new DeadGlobalElimination());
		visitors.add(new DfVisitor<Void>(new DeadCodeElimination()));
		visitors.add(new DfVisitor<Void>(new DeadVariableRemoval()));
		visitors.add(new DfVisitor<Void>(new CopyPropagator()));
		visitors.add(new DfVisitor<Void>(new ConstantPropagator()));
		visitors.add(new DfVisitor<Void>(new InstPhiTransformation()));
		visitors.add(new DfVisitor<Expression>(new CastAdder(false, true)));
		visitors.add(new DfVisitor<Void>(new EmptyBlockRemover()));
		visitors.add(new DfVisitor<Void>(new BlockCombine()));
		visitors.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
		visitors.add(new DfVisitor<Void>(new ListInitializer()));
		visitors.add(new DfVisitor<Void>(new TemplateInfoComputing()));

		// computes names of local variables
		visitors.add(new DfVisitor<Void>(new SSAVariableRenamer()));

		for (DfSwitch<?> transfo : visitors) {
			transfo.doSwitch(network);
			if (debug) {
				OrccUtil.validateObject(transfo.toString(), network);
			}
		}

		network.computeTemplateMaps();
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		doTransformNetwork(network);

		// update alignment information
		Alignable.setAlignability(network);

		// Compute the actor mapping
		if (importXcfFile) {
			computedMapping = new Mapping(network, xcfFile);
		} else {
			computedMapping = new Mapping(network, mapping);
		}

		// Build the design from the mapping
		OrccLogger.traceln("TTA Architecture configuration setted to : "
				+ configuration.getName());
		int fifosize = getOption(OrccLaunchConstants.FIFO_SIZE, OrccLaunchConstants.DEFAULT_FIFO_SIZE);
		design = new ArchitectureBuilder().build(network, configuration,
				computedMapping, reduceConnections, fifosize);

		// Generate files
		actorsPath = OrccUtil.createFolder(path, "actors");
		printChildren(network);
		printDesign(design);

		if (finalize) {
			// Launch the TCE toolset
			runPythonScript();
		}
	}

	@Override
	protected Result extractLibraries() {

		Result result = FilesManager.extract("/runtime/TTA/libs", path);
		result.merge(FilesManager.extract("/runtime/common/scripts", path));

		// Will be used later to execute the scripts
		libPath = path + File.separator + "libs";

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

	/**
	 * Prints a set of files used to generate the given design.
	 * 
	 * @param design
	 *            a design
	 */
	private void printDesign(Design design) {
		printProcessors(design);

		OrccLogger.traceln("Printing design...");
		long t0 = System.currentTimeMillis();

		// Create HDL project
		new HwDesignPrinter(fpga).print(design, path);
		new HwProjectPrinter(fpga).print(design, path);
		new HwTestbenchPrinter(fpga).print(design, path);

		// Create TCE project
		new PyDesignPrinter(fpga).print(design, path);
		new TceDesignPrinter(getOptions(), path).print(design, path);

		new Dota().print(design, path, "top.dot");

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + (t1 - t0) / 1000.0 + "s");
	}

	/**
	 * Print processor of the given design. If some files already exist and are
	 * identical, then they are not printed.
	 * 
	 * @param design
	 *            the given design
	 */
	private void printProcessors(Design design) {
		OrccLogger.traceln("Printing processors...");
		long t0 = System.currentTimeMillis();

		int numCached = 0;

		for (Processor processor : design.getProcessors()) {
			numCached += printProcessor(processor);
		}

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + (t1 - t0) / 1000.0 + "s");

		if (numCached > 0) {
			OrccLogger.noticeln(numCached + " files were not regenerated "
					+ "because they were already up-to-date.");
		}
	}

	/**
	 * Prints a set of files used to generate the given processor.
	 * 
	 * @param tta
	 *            a processor
	 * @return the number of cached files
	 */
	private int printProcessor(Processor tta) {
		String processorPath = OrccUtil.createFolder(path, tta.getName());
		int cached = 0;

		// Print VHDL description
		cached += new HwProcessorPrinter(fpga).print(tta, processorPath);

		// Print high-level description
		cached += new TceProcessorPrinter(design.getHardwareDatabase()).print(
				tta, processorPath);

		// Print assembly code of actor-scheduler
		cached += new SwProcessorPrinter(getOptions()).print(tta, processorPath);

		return cached;
	}

	@Override
	protected boolean printInstance(Instance instance) {
		return new SwActorPrinter(getOptions(), design.getActorToProcessorMap().get(
				instance)).print(actorsPath, instance) > 0;
	}

	@Override
	protected boolean printActor(Actor actor) {
		return new SwActorPrinter(getOptions(), design.getActorToProcessorMap().get(
				actor)).print(actorsPath, actor) > 0;
	}

	/**
	 * Runs the python script to compile the application and generate the whole
	 * design using the TCE toolset. (FIXME: Rewrite this awful method)
	 */
	private void runPythonScript() {
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(libPath + File.separator + "ttanetgen");
		cmdList.add("-cg");
		if (debug) {
			cmdList.add("--debug");
		}
		cmdList.add(path);

		OrccLogger.traceln("Generating design...");
		long t0 = System.currentTimeMillis();
		OrccUtil.runExternalProgram(cmdList);
		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + (t1 - t0) / 1000.0 + "s");
	}

}
