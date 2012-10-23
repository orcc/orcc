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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.llvm.aot.LLVMBackendImpl;
import net.sf.orcc.backends.llvm.aot.LLVMExpressionPrinter;
import net.sf.orcc.backends.llvm.aot.LLVMTypePrinter;
import net.sf.orcc.backends.llvm.transform.StringTransformation;
import net.sf.orcc.backends.llvm.transform.TemplateInfoComputing;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitectureBuilder;
import net.sf.orcc.backends.llvm.tta.architecture.util.ArchitecturePrinter;
import net.sf.orcc.backends.llvm.tta.transform.ComplexHwOpDetector;
import net.sf.orcc.backends.llvm.tta.transform.PrintRemoval;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.TypeResizer;
import net.sf.orcc.backends.transform.ssa.ConstantPropagator;
import net.sf.orcc.backends.transform.ssa.CopyPropagator;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Edge;
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
public class TTABackendImpl extends LLVMBackendImpl {

	String actorsPath;
	private Mapping computedMapping;
	private ProcessorConfiguration configuration;
	private Design design;
	private boolean finalize;

	private FPGA fpga;
	private String libPath;
	private boolean profile;
	private boolean reduceConnections;

	private Map<Port, Integer> computePortToIdMap(Vertex vertex) {
		Map<Port, Integer> map = new HashMap<Port, Integer>();
		Processor processor = design.getActorToProcessorMap().get(vertex);
		for (Edge edge : vertex.getIncoming()) {
			Connection connection = (Connection) edge;
			Port port = connection.getTargetPort();
			if (!port.isNative()) {
				map.put(port, processor.getAddrSpaceId(connection));
			}
		}
		for (Edge edge : vertex.getOutgoing()) {
			Connection connection = (Connection) edge;
			Port port = connection.getSourcePort();
			if (!port.isNative()) {
				map.put(port, processor.getAddrSpaceId(connection));
			}
		}
		return map;
	}

	@Override
	public void doInitializeOptions() {
		finalize = getAttribute("net.sf.orcc.backends.tta.finalizeGeneration",
				false);
		fpga = FPGA.builder(getAttribute("net.sf.orcc.backends.tta.fpga",
				"Stratix III (EP3SL150F1152C2)"));
		profile = getAttribute("net.sf.orcc.backends.profile", false);
		configuration = ProcessorConfiguration.getByName(getAttribute(
				"net.sf.orcc.backends.llvm.tta.configuration", "Huge"));
		reduceConnections = getAttribute(
				"net.sf.orcc.backends.llvm.tta.reduceConnections", false);

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
		new BroadcastAdder().doSwitch(network);
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
				new DfVisitor<Void>(new PrintRemoval()),
				new DfVisitor<Void>(new SSATransformation()),
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
				new DfVisitor<Void>(new TemplateInfoComputing()), };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(network);
		}

		network.computeTemplateMaps();

		for (Actor actor : network.getAllActors()) {
			actor.setTemplateData(new TTAActorTemplateData().compute(actor));
		}

		return network;
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		doTransformNetwork(network);

		// build the design
		computedMapping = new Mapping(network, mapping, reduceConnections,
				false);
		design = new ArchitectureBuilder().build(network, configuration,
				computedMapping, reduceConnections);

		// print instances and entities
		actorsPath = OrccUtil.createFolder(path, "actors");
		printInstances(network);

		// print the design
		for (Processor processor : design.getProcessors()) {
			generateProcessor(processor);
		}
		generateDesign(design);

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

	private void generateDesign(Design design) {
		// VHDL Network of TTA processors
		CommonPrinter.printFile(new VHDL_Design(fpga).doSwitch(design), path,
				"top.vhd");

		// Python package
		String pythonPath = OrccUtil.createFolder(path, "informations_");
		CommonPrinter.printFile(new Python_Design(fpga).doSwitch(design),
				pythonPath, "informations.py");
		OrccUtil.createFile(pythonPath, "__init__.py");

		if (fpga.isAltera()) {
			// Quartus
			Quartus_Project template = new Quartus_Project(fpga);
			CommonPrinter.printFile(template.caseDesign(design), path,
					"top.qsf");
			CommonPrinter.printFile(template.printQpf(), path, "top.qpf");
		} else if (fpga.isXilinx()) {
			// ISE
			ISE_Project template = new ISE_Project();
			CommonPrinter.printFile(template.caseDesign(design), path,
					"top.xise");
			CommonPrinter.printFile(template.printUcf(), path, "top.ucf");
		}

		// ModelSim
		CommonPrinter.printFile(new ModelSim_Script(fpga).doSwitch(design),
				path, "top.tcl");
		CommonPrinter.printFile(new VHDL_Testbench().doSwitch(design), path,
				"top_tb.vhd");
		CommonPrinter.printFile(new ModelSim_Wave().doSwitch(design), path,
				"wave.do");

		// TCE
		CommonPrinter.printFile(new TCE_Design_PNDF(path).doSwitch(design),
				path, "top.pndf");

		CommonPrinter.printFile(new Dota().printDot(design), path, "top.dot");
	}

	private void generateProcessor(Processor tta) {
		String processorPath = OrccUtil.createFolder(path, tta.getName());

		// Print VHDL description
		CommonPrinter.printFile(new VHDL_Processor(fpga).doSwitch(tta),
				processorPath, tta.getName() + ".vhd");

		// Print high-level description
		CommonPrinter.printFile(new TCE_Processor_ADF().print(tta),
				processorPath, tta.getName() + ".adf");
		CommonPrinter.printFile(
				new TCE_Processor_IDF(design.getHardwareDatabase())
						.doSwitch(tta), processorPath, tta.getName() + ".idf");

		// Print assembly code of actor-scheduler
		ArchitecturePrinter schedulerPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/llvm/tta/LLVM_Processor.stg");
		schedulerPrinter.setExpressionPrinter(new LLVMExpressionPrinter());
		schedulerPrinter.setTypePrinter(new LLVMTypePrinter());
		schedulerPrinter.print(tta.getName() + ".ll", processorPath, tta);
	}

	@Override
	protected boolean printInstance(Instance instance) {
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/llvm/tta/LLVM_Actor.stg");
		printer.setExpressionPrinter(new LLVMExpressionPrinter());
		printer.setTypePrinter(new LLVMTypePrinter());
		printer.getOptions().put("profile", profile);
		printer.getOptions().put("portToIdMap", computePortToIdMap(instance));
		return printer.print(instance.getSimpleName() + ".ll", actorsPath,
				instance);
	}

	private void runPythonScript() {
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(libPath + File.separator + "generate");
		cmdList.add("-cg");
		if (debug) {
			cmdList.add("--debug");
		}
		cmdList.add(path);

		String[] cmd = cmdList.toArray(new String[] {});
		try {
			OrccLogger.traceln("Generating design...");
			long t0 = System.currentTimeMillis();
			final Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			long t1 = System.currentTimeMillis();
			OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
					+ "s");
		} catch (Exception e) {
			OrccLogger.severeln(e.getMessage());
		}
	}

}
