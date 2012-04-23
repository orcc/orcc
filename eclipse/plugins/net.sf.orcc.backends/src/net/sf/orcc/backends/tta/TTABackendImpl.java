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
package net.sf.orcc.backends.tta;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.CustomPrinter;
import net.sf.orcc.backends.llvm.LLVMExpressionPrinter;
import net.sf.orcc.backends.llvm.LLVMTypePrinter;
import net.sf.orcc.backends.llvm.transformations.BoolToIntTransformation;
import net.sf.orcc.backends.llvm.transformations.GetElementPtrAdder;
import net.sf.orcc.backends.llvm.transformations.StringTransformation;
import net.sf.orcc.backends.transformations.CastAdder;
import net.sf.orcc.backends.transformations.EmptyNodeRemover;
import net.sf.orcc.backends.transformations.InstPhiTransformation;
import net.sf.orcc.backends.transformations.TypeResizer;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.backends.transformations.ssa.ConstantPropagator;
import net.sf.orcc.backends.transformations.ssa.CopyPropagator;
import net.sf.orcc.backends.tta.architecture.Design;
import net.sf.orcc.backends.tta.architecture.DesignConfiguration;
import net.sf.orcc.backends.tta.architecture.Processor;
import net.sf.orcc.backends.tta.architecture.util.ArchitectureBuilder;
import net.sf.orcc.backends.tta.architecture.util.ArchitecturePrinter;
import net.sf.orcc.backends.tta.transformations.ComplexHwOpDetector;
import net.sf.orcc.backends.util.FPGA;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.BroadcastAdder;
import net.sf.orcc.df.transformations.Instantiator;
import net.sf.orcc.df.transformations.NetworkFlattener;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.transformations.BlockCombine;
import net.sf.orcc.ir.transformations.CfgBuilder;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.ir.transformations.SSATransformation;
import net.sf.orcc.ir.transformations.TacTransformation;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;

/**
 * TTA back-end.
 * 
 * @author Herve Yviquel
 * 
 */
public class TTABackendImpl extends AbstractBackend {

	private boolean debug;
	private boolean finalize;
	private FPGA fpga;
	private String libPath;

	private DesignConfiguration conf;
	private Design design;

	private final Map<String, String> transformations;

	String instancePath;

	/**
	 * Creates a new instance of the TTA back-end. Initializes the
	 * transformation hash map.
	 */
	public TTABackendImpl() {
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
		debug = getAttribute(DEBUG_MODE, true);
		finalize = getAttribute("net.sf.orcc.backends.tta.finalizeGeneration",
				false);
		fpga = FPGA.builder(getAttribute("net.sf.orcc.backends.tta.fpga",
				"Stratix III (EP3SL150F1152C2)"));
		// Set default FIFO size to 256
		fifoSize = getAttribute(FIFO_SIZE, 256);

		conf = DesignConfiguration.getByName(getAttribute(
				"net.sf.orcc.backends.tta.configuration", "Standard"));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.AbstractBackend#exportRuntimeLibrary()
	 */
	@Override
	public boolean exportRuntimeLibrary() throws OrccException {
		libPath = path + File.separator + "libs";
		write("Export library files into " + libPath + "... ");
		if (copyFolderToFileSystem("/runtime/TTA", libPath)) {
			write("OK" + "\n");
			new File(libPath + File.separator + "generate").setExecutable(true);
			return true;
		} else {
			write("Error" + "\n");
			return false;
		}
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		DfSwitch<?>[] transformations = { new UnitImporter(),
				new ComplexHwOpDetector(getWriteListener()),
				new SSATransformation(), new BoolToIntTransformation(),
				new TypeResizer(true, true, false, true),
				new StringTransformation(),
				new RenameTransformation(this.transformations),
				new TacTransformation(),
				new DfVisitor<Void>(new CopyPropagator()),
				new DfVisitor<Void>(new ConstantPropagator()),
				new InstPhiTransformation(), new GetElementPtrAdder(),
				new CastAdder(false), new EmptyNodeRemover(),
				new BlockCombine(), new CfgBuilder() };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

		actor.setTemplateData(new TTAActorTemplateData(actor));
	}

	private Network doTransformNetwork(Network network) throws OrccException {
		write("Instantiating...\n");
		network = new Instantiator(fifoSize).doSwitch(network);
		write("Flattening...\n");
		new NetworkFlattener().doSwitch(network);

		new BroadcastAdder().doSwitch(network);

		return network;
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network = doTransformNetwork(network);

		transformActors(network.getAllActors());

		network.computeTemplateMaps();

		design = new ArchitectureBuilder(conf).caseNetwork(network);
		printDesign(design);

		if (finalize) {
			runPythonScript();
		}
	}

	private void printDesign(Design design) {
		for (Processor processor : design.getProcessors()) {
			printProcessor(processor);
		}

		// VHDL Network of TTA processors
		ArchitecturePrinter vhdlPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/VHDL_Network.stg");
		vhdlPrinter.setExpressionPrinter(new LLVMExpressionPrinter());
		vhdlPrinter.getOptions().put("fpga", fpga);
		vhdlPrinter.print("top.vhd", path, design);

		// Python package
		String pythonPath = OrccUtil.createFolder(path, "informations_");
		ArchitecturePrinter pythonPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/Python_Network.stg");
		pythonPrinter.getOptions().put("fpga", fpga);
		pythonPrinter.print("informations.py", pythonPath, design);
		OrccUtil.createFile(pythonPath, "__init__.py");

		if (fpga.isAltera()) {
			// Quartus
			ArchitecturePrinter projectQsfPrinter = new ArchitecturePrinter(
					"net/sf/orcc/backends/tta/Quartus_Project.stg");
			projectQsfPrinter.getOptions().put("fpga", fpga);
			CustomPrinter projectQpfPrinter = new CustomPrinter(
					"net/sf/orcc/backends/tta/Quartus_Project.stg");
			projectQsfPrinter.print("top.qsf", path, design);
			projectQpfPrinter.print("top.qpf", path, "printQpf");
		} else if (fpga.isXilinx()) {
			// ISE
			ArchitecturePrinter projectXisePrinter = new ArchitecturePrinter(
					"net/sf/orcc/backends/tta/ISE_Project.stg");
			projectXisePrinter.getOptions().put("fpga", fpga);
			projectXisePrinter.print("top.xise", path, design);
			projectXisePrinter.print("top.ucf", path, design);
		}

		// ModelSim
		ArchitecturePrinter tclPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/ModelSim_Script.stg");
		tclPrinter.getOptions().put("fpga", fpga);
		ArchitecturePrinter tbPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/VHDL_Testbench.stg");
		tbPrinter.getOptions().put("fifoSize", fifoSize);
		ArchitecturePrinter wavePrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/ModelSim_Wave.stg");
		wavePrinter.setExpressionPrinter(new LLVMExpressionPrinter());
		tclPrinter.print("top.tcl", path, design);
		tbPrinter.print("top_tb.vhd", path, design);
		wavePrinter.print("wave.do", path, design);
	}

	private void printProcessor(Processor tta) {
		String processorPath = OrccUtil.createFolder(path, tta.getName());

		// Print high-level description
		ArchitecturePrinter adfPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/TCE_Processor_ADF.stg");
		ArchitecturePrinter idfPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/TCE_Processor_IDF.stg");

		adfPrinter.print(tta.getName() + ".adf", processorPath, tta);
		idfPrinter.print(tta.getName() + ".idf", processorPath, tta);

		// Print ModelSim testbench and wave
		String simPath = OrccUtil.createFolder(processorPath, "simulation");
		ArchitecturePrinter tbPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/VHDL_Testbench.stg");
		tbPrinter.getOptions().put("fifoSize", fifoSize);
		tbPrinter.getOptions().put("fpga", fpga);
		ArchitecturePrinter tclPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/ModelSim_Script.stg");
		tclPrinter.getOptions().put("fpga", fpga);
		ArchitecturePrinter wavePrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/ModelSim_Wave.stg");

		tbPrinter.print(tta.getName() + "_tb.vhd", simPath, tta);
		wavePrinter.print("wave.do", simPath, tta);
		tclPrinter.print(tta.getName() + ".tcl", processorPath, tta);

		// Print assembly code of actor-scheduler
		ArchitecturePrinter schedulerPrinter = new ArchitecturePrinter(
				"net/sf/orcc/backends/tta/LLVM_Actor.stg");
		schedulerPrinter.setExpressionPrinter(new LLVMExpressionPrinter());
		schedulerPrinter.setTypePrinter(new LLVMTypePrinter());
		schedulerPrinter.print(tta.getName() + ".ll", processorPath, tta);
	}

	private void runPythonScript() throws OrccException {
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(libPath + File.separator + "generate");
		cmdList.add("-cg");
		if (debug) {
			cmdList.add("--debug");
		}
		cmdList.add(path);

		String[] cmd = cmdList.toArray(new String[] {});
		try {
			write("Generating design...\n");
			long t0 = System.currentTimeMillis();
			final Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			long t1 = System.currentTimeMillis();
			write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");
		} catch (IOException e) {
			System.err.println("TCE error: ");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
