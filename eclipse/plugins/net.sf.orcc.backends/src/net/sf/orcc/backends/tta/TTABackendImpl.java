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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.CustomPrinter;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.llvm.LLVMExpressionPrinter;
import net.sf.orcc.backends.llvm.LLVMTypePrinter;
import net.sf.orcc.backends.llvm.transformations.BoolToIntTransformation;
import net.sf.orcc.backends.llvm.transformations.GetElementPtrAdder;
import net.sf.orcc.backends.llvm.transformations.PrintlnTransformation;
import net.sf.orcc.backends.transformations.CastAdder;
import net.sf.orcc.backends.transformations.EmptyThenElseNodeAdder;
import net.sf.orcc.backends.transformations.InstPhiTransformation;
import net.sf.orcc.backends.transformations.TypeResizer;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.backends.transformations.ssa.ConstantPropagator;
import net.sf.orcc.backends.transformations.ssa.CopyPropagator;
import net.sf.orcc.backends.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.tta.architecture.TTA;
import net.sf.orcc.backends.tta.transformations.BroadcastTypeResizer;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.BroadcastAdder;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.transformations.BlockCombine;
import net.sf.orcc.ir.transformations.BuildCFG;
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

	/**
	 * Backend options
	 */
	private boolean debugMode;

	private final Map<String, String> transformations;
	private final List<String> processorIntensiveActors;

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
		processorIntensiveActors = new ArrayList<String>();
		// processorIntensiveActors.add("fi.oulu.ee.mvg.Mgnt_Address");
		// processorIntensiveActors.add("org.mpeg4.part2.texture.Algo_IDCT2D_ISOIEC_23002_1");
		// processorIntensiveActors.add("fi.oulu.ee.mvg.Algo_IAP");
	}

	@Override
	public void doInitializeOptions() {
		debugMode = getAttribute(DEBUG_MODE, true);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		DfSwitch<?>[] transformations = { new UnitImporter(),
				new SSATransformation(), new BoolToIntTransformation(),
				new TypeResizer(true, true, false),
				new PrintlnTransformation(),
				new RenameTransformation(this.transformations),
				new TacTransformation(true), new CopyPropagator(),
				new ConstantPropagator(), new InstPhiTransformation(),
				new GetElementPtrAdder(), new CastAdder(true, false),
				new EmptyThenElseNodeAdder(), new BlockCombine(),
				new BuildCFG() };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

		actor.setTemplateData(new TTAActorTemplateData(actor));
	}

	private void doTransformNetwork(Network network) throws OrccException {
		network.flatten();
		new BroadcastAdder().doSwitch(network);
		new BroadcastTypeResizer().doSwitch(network);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		doTransformNetwork(network);

		transformActors(network.getActors());
		printInstances(network);

		network.computeTemplateMaps();
		printNetwork(network);
	}

	@Override
	protected boolean printInstance(Instance instance) throws OrccException {
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/tta/LLVM_Actor.stg", !debugMode, true);
		printer.setExpressionPrinter(new LLVMExpressionPrinter());
		printer.setTypePrinter(new LLVMTypePrinter());

		String instancePath = null;
		if (!(instance.isActor() && instance.getActor().isNative())) {
			instancePath = OrccUtil.createFolder(path, instance.getName());
			printProcessor(instance, instancePath);

			// ModelSim
			StandardPrinter tbPrinter = new StandardPrinter(
					"net/sf/orcc/backends/tta/ModelSim_Testbench.stg",
					!debugMode, false);
			StandardPrinter tclPrinter = new StandardPrinter(
					"net/sf/orcc/backends/tta/ModelSim_Script.stg");
			tbPrinter.print(instance.getName() + "_tb.vhd", instancePath,
					instance);
			tclPrinter.print(instance.getName() + ".tcl", path, instance);
		}

		return printer
				.print(instance.getName() + ".ll", instancePath, instance);
	}

	private void printNetwork(Network network) {
		// VHDL Network of TTA processors
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/tta/VHDL_Network.stg");
		networkPrinter.setExpressionPrinter(new LLVMExpressionPrinter());
		networkPrinter.print("top.vhd", path, network);

		// Python package
		StandardPrinter pythonPrinter = new StandardPrinter(
				"net/sf/orcc/backends/tta/Python_Network.stg");
		pythonPrinter.print("informations.py", path, network);
		OrccUtil.createFile(path, "__init__.py");

		// Quartus
		CustomPrinter projectQsfPrinter = new CustomPrinter(
				"net/sf/orcc/backends/tta/Quartus_Project.stg");
		CustomPrinter projectQpfPrinter = new CustomPrinter(
				"net/sf/orcc/backends/tta/Quartus_Project.stg");
		projectQsfPrinter.print("top.qsf", path, "qsfNetwork", "network",
				network);
		projectQpfPrinter.print("top.qpf", path, "qpfNetwork", "network",
				network);

		// ModelSim
		StandardPrinter tclPrinter = new StandardPrinter(
				"net/sf/orcc/backends/tta/ModelSim_Script.stg");
		StandardPrinter tbPrinter = new StandardPrinter(
				"net/sf/orcc/backends/tta/ModelSim_Testbench.stg");
		StandardPrinter wavePrinter = new StandardPrinter(
				"net/sf/orcc/backends/tta/ModelSim_Wave.stg");
		tclPrinter.print("top.tcl", path, network);
		tbPrinter.print("top_tb.vhd", path, network);
		wavePrinter.print("wave.do", path, network);
	}

	private void printProcessor(Instance instance, String instancePath) {
		TTA simpleTTA = ArchitectureFactory.eINSTANCE.createTTASpecialized(
				instance.getName(), instance, getBusNb(instance),
				getRegNb(instance), getAluNb(instance));

		CustomPrinter adfPrinter = new CustomPrinter(
				"net/sf/orcc/backends/tta/TCE_Processor_ADF.stg");
		CustomPrinter idfPrinter = new CustomPrinter(
				"net/sf/orcc/backends/tta/TCE_Processor_IDF.stg");

		adfPrinter.print("processor_" + instance.getName() + ".adf",
				instancePath, "printTTA", "tta", simpleTTA);
		idfPrinter.print("processor_" + instance.getName() + ".idf",
				instancePath, "printTTA", "tta", simpleTTA);
	}

	private int getBusNb(Instance instance) {
		if (instance.isActor()
				&& processorIntensiveActors.contains(instance.getActor()
						.getName())) {
			return 6;
		}
		return 2;
	}

	private int getAluNb(Instance instance) {
		if (instance.isActor()
				&& processorIntensiveActors.contains(instance.getActor()
						.getName())) {
			return 2;
		}
		return 1;
	}

	private int getRegNb(Instance instance) {
		if (instance.isActor()
				&& processorIntensiveActors.contains(instance.getActor()
						.getName())) {
			return 4;
		}
		return 2;
	}

}
