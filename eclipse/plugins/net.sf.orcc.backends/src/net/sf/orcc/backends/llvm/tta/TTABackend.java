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

import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.llvm.aot.LLVMBackend;
import net.sf.orcc.backends.llvm.transform.StringTransformation;
import net.sf.orcc.backends.llvm.transform.TemplateInfoComputing;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureBuilder;
import net.sf.orcc.backends.llvm.tta.transform.ComplexHwOpDetector;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.TypeResizer;
import net.sf.orcc.backends.transform.ssa.ConstantPropagator;
import net.sf.orcc.backends.transform.ssa.CopyPropagator;
import net.sf.orcc.backends.util.BackendUtil;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.backends.util.Metiss;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.graph.util.Dota;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.transform.BlockCombine;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;

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
	private boolean balanceMapping;

	private int processorNumber;

	@Override
	protected void doInitializeOptions() {
		finalize = getAttribute("net.sf.orcc.backends.tta.finalizeGeneration",
				false);
		fpga = FPGA.builder(getAttribute("net.sf.orcc.backends.tta.fpga",
				"Stratix III (EP3SL150F1152C2)"));
		configuration = ProcessorConfiguration.getByName(getAttribute(
				"net.sf.orcc.backends.llvm.tta.configuration", "Huge"));
		reduceConnections = getAttribute(
				"net.sf.orcc.backends.llvm.tta.reduceConnections", false);
		balanceMapping = getAttribute("net.sf.orcc.backends.metricMapping",
				false);
		processorNumber = Integer.parseInt(getAttribute(
				"net.sf.orcc.backends.processorsNumber", "0"));
	}

	@Override
	protected void doTransformActor(Actor actor) {
		// do not transform actors
	}

	@Override
	protected Network doTransformNetwork(Network network) {
		OrccLogger.traceln("Analyze and transform the network...");
		new ComplexHwOpDetector().doSwitch(network);
		new Instantiator(false, fifoSize).doSwitch(network);
		new NetworkFlattener().doSwitch(network);
		if (classify) {
			new Classifier().doSwitch(network);
		}
		if (mergeActions) {
			new ActionMerger().doSwitch(network);
		}
		if (mergeActors) {
			new ActorMerger().doSwitch(network);
		}

		DfSwitch<?>[] transformations = { new UnitImporter(),
				new TypeResizer(true, true, false),
				new DfVisitor<Void>(new SSATransformation()),
				new StringTransformation(),
				new RenameTransformation(this.renameMap),
				new DfVisitor<Expression>(new TacTransformation()),
				new DfVisitor<Void>(new CopyPropagator()),
				new DfVisitor<Void>(new ConstantPropagator()),
				new DfVisitor<Void>(new InstPhiTransformation()),
				new DfVisitor<Expression>(new CastAdder(false, true)),
				new DfVisitor<Void>(new EmptyBlockRemover()),
				new DfVisitor<Void>(new BlockCombine()),
				new DfVisitor<CfgNode>(new ControlFlowAnalyzer()),
				new DfVisitor<Void>(new TemplateInfoComputing()), };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(network);
		}

		network.computeTemplateMaps();

		return network;
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		doTransformNetwork(network);

		if (balanceMapping) {
			for (Vertex vertex : network.getChildren()) {
				Instance instance = vertex.getAdapter(Instance.class);
				if (instance != null) {
					String weight = mapping.get(instance.getHierarchicalName());
					if (weight != null) {
						instance.setAttribute("weight", weight);
					}
				}
			}
			computedMapping = new Metiss().partition(network, path,
					processorNumber);
		} else {
			computedMapping = new Mapping(network, mapping, reduceConnections,
					false);
		}

		// build the design
		design = new ArchitectureBuilder().build(network, configuration,
				computedMapping, reduceConnections);

		// print instances and entities
		actorsPath = OrccUtil.createFolder(path, "actors");
		printInstances(network);

		// print the design
		printDesign(design);

		if (finalize) {
			runPythonScript();
		}
	}

	@Override
	public boolean exportRuntimeLibrary() {
		if (!getAttribute(NO_LIBRARY_EXPORT, false)) {
			libPath = path + File.separator + "libs";
			OrccLogger.trace("Export library files into " + libPath + "... ");
			if (copyFolderToFileSystem("/runtime/TTA", libPath)) {
				OrccLogger.traceRaw("OK" + "\n");
				new File(libPath + File.separator + "generate")
						.setExecutable(true);
				return true;
			} else {
				OrccLogger.warnRaw("Error" + "\n");
				return false;
			}
		}
		return false;
	}

	/**
	 * Prints a set of files used to generate the given design.
	 * 
	 * @param design
	 *            a design
	 */
	private void printDesign(Design design) {
		OrccLogger.traceln("Printing design...");
		long t0 = System.currentTimeMillis();

		// Generate each processor
		for (Processor processor : design.getProcessors()) {
			printProcessor(processor);
		}

		// VHDL Network of TTA processors
		new VHDL_Design(fpga).print(design, path);

		// Create python package
		new Python_Design(fpga).print(design, path);

		// Create project files
		if (fpga.isAltera()) {
			new Quartus_Project(fpga).print(design, path);
		} else if (fpga.isXilinx()) {
			new ISE_Project().print(design, path);
		}

		// ModelSim
		new ModelSimPrinter(fpga).print(design, path);
		new VHDL_Testbench().print(design, path);

		// TCE
		new TCE_Design_PNDF(path).print(design, path);

		new Dota().print(design, path, "top.dot");

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
				+ "s");
	}

	/**
	 * Prints a set of files used to generate the given processor.
	 * 
	 * @param tta
	 *            a processor
	 */
	private void printProcessor(Processor tta) {
		String processorPath = OrccUtil.createFolder(path, tta.getName());

		// Print VHDL description
		new VHDL_Processor(fpga).print(tta, processorPath);

		// Print high-level description
		new TCE_Processor_ADF().print(tta, processorPath);
		new TCE_Processor_IDF(design.getHardwareDatabase()).print(tta,
				processorPath);

		// Print assembly code of actor-scheduler
		new LLVM_Processor().print(tta, processorPath);
	}

	@Override
	protected boolean printInstance(Instance instance) {
		new LLVM_Actor(instance, options, design.getActorToProcessorMap().get(
				instance)).print(actorsPath);
		return false;
	}

	/**
	 * Runs the python script to compile the application and generate the whole
	 * design using the TCE toolset. (FIXME: Rewrite this awful method)
	 */
	private void runPythonScript() {
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(libPath + File.separator + "generate");
		cmdList.add("-cg");
		if (debug) {
			cmdList.add("--debug");
		}
		cmdList.add(path);

		OrccLogger.traceln("Generating design...");
		long t0 = System.currentTimeMillis();
		BackendUtil.runExternalProgram(cmdList);
		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
				+ "s");
	}

}
