/*
 * Copyright (c) 2009-2011, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.xlim;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.MAPPING;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.CustomPrinter;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.llvm.transformations.EmptyElseNodeAdder;
import net.sf.orcc.backends.transformations.CastAdder;
import net.sf.orcc.backends.transformations.DivisionSubstitution;
import net.sf.orcc.backends.transformations.Inliner;
import net.sf.orcc.backends.transformations.InstPhiTransformation;
import net.sf.orcc.backends.transformations.Multi2MonoToken;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.backends.transformations.tac.ExpressionSplitter;
import net.sf.orcc.backends.vhdl.transformations.StoreOnceTransformation;
import net.sf.orcc.backends.xlim.transformations.CustomPeekAdder;
import net.sf.orcc.backends.xlim.transformations.GlobalArrayInitializer;
import net.sf.orcc.backends.xlim.transformations.InstTernaryAdder;
import net.sf.orcc.backends.xlim.transformations.ListFlattener;
import net.sf.orcc.backends.xlim.transformations.LiteralIntegersAdder;
import net.sf.orcc.backends.xlim.transformations.LocalArrayRemoval;
import net.sf.orcc.backends.xlim.transformations.UnaryListRemoval;
import net.sf.orcc.backends.xlim.transformations.XlimDeadVariableRemoval;
import net.sf.orcc.backends.xlim.transformations.XlimVariableRenamer;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.transformations.BlockCombine;
import net.sf.orcc.ir.transformations.BuildCFG;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.SSATransformation;
import net.sf.orcc.ir.util.ActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * This class defines a template-based XLIM back-end.
 * 
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * @author Mickael Raulet
 * @author Endri Bezati
 * 
 */
public class XlimBackendImpl extends AbstractBackend {

	private boolean debugMode;

	private String fpgaType;

	private boolean hardwareGen;

	private Map<String, String> mapping;

	private boolean multi2mono;

	private List<String> entities;

	private HashSet<String> entitySet;

	private Map<Integer, List<Instance>> computeMapping(Network network,
			Map<String, String> mapping) {
		Map<Integer, List<Instance>> computedMap = new HashMap<Integer, List<Instance>>();

		for (Instance instance : network.getInstances()) {
			String path = instance.getHierarchicalPath();
			String component = mapping.get(path);
			if (component != null) {
				int coreId = Integer.parseInt(component.substring(1));
				List<Instance> actors = computedMap.get(coreId);
				if (actors == null) {
					actors = new ArrayList<Instance>();
					computedMap.put(coreId, actors);
				}
				actors.add(instance);
			}
		}

		return computedMap;
	}

	@Override
	public void doInitializeOptions() {
		hardwareGen = getAttribute("net.sf.orcc.backends.xlimHard", true);
		fpgaType = getAttribute("net.sf.orcc.backends.xlimFpgaType",
				"xc2vp30-7-ff1152");
		multi2mono = getAttribute("net.sf.orcc.backends.multi2mono", true);
		mapping = getAttribute(MAPPING, new HashMap<String, String>());
		debugMode = getAttribute(DEBUG_MODE, true);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		XlimActorTemplateData data = new XlimActorTemplateData();
		actor.setTemplateData(data);

		if (hardwareGen) {
			new StoreOnceTransformation().doSwitch(actor);
		}

		if (multi2mono) {
			new Multi2MonoToken().doSwitch(actor);
			new LocalArrayRemoval().doSwitch(actor);
			new DivisionSubstitution().doSwitch(actor);
		}

		ActorVisitor<?>[] transformations = { new UnitImporter(),
				new SSATransformation(),
				new GlobalArrayInitializer(hardwareGen),
				new InstTernaryAdder(), new Inliner(true, true),
				new UnaryListRemoval(), new CustomPeekAdder(),
				new DeadGlobalElimination(), new DeadCodeElimination(),
				new XlimDeadVariableRemoval(), new ListFlattener(),
				new ExpressionSplitter(true), /* new CopyPropagator(), */
				new BuildCFG(), new InstPhiTransformation(),
				new LiteralIntegersAdder(true), new CastAdder(true, true),
				new XlimVariableRenamer(), new EmptyElseNodeAdder(), new BlockCombine() };

		for (ActorVisitor<?> transformation : transformations) {
			transformation.doSwitch(actor);
			ResourceSet set = new ResourceSetImpl();
			if (debugMode && !IrUtil.serializeActor(set, path, actor)) {
				System.out.println("oops " + transformation + " "
						+ actor.getName());
			}
		}

		data.computeTemplateMaps(actor);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate an XLIM VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		transformActors(network.getActors());

		printInstances(network);

		network.computeTemplateMaps();
		XlimNetworkTemplateData data = new XlimNetworkTemplateData();
		data.computeTemplateMaps(network);
		network.setTemplateData(data);

		write("Printing network...\n");
		printNetwork(network);
	}

	private void printCMake(Network network) {
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/xlim/XLIM_sw_CMakeLists.stg");
		networkPrinter.print("CMakeLists.txt", path, network);
	}

	@Override
	protected boolean printInstance(Instance instance) {
		StandardPrinter printer;
		if (hardwareGen) {
			printer = new StandardPrinter(
					"net/sf/orcc/backends/xlim/hardware/XLIM_hw_actor.stg",
					!debugMode);
			printer.getOptions().put("fpgaType", fpgaType);
		} else {
			printer = new StandardPrinter(
					"net/sf/orcc/backends/xlim/XLIM_sw_actor.stg", !debugMode);
		}

		printer.setExpressionPrinter(new XlimExprPrinter());
		printer.setTypePrinter(new XlimTypePrinter());
		return printer.print(instance.getId() + ".xlim", path, instance);
	}

	private void printMapping(Network network, Map<String, String> mapping) {
		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/xlim/XLIM_sw_mapping.stg");
		networkPrinter.getOptions().put("mapping",
				computeMapping(network, mapping));
		networkPrinter.getOptions().put("fifoSize", fifoSize);
		networkPrinter.print(network.getName() + ".xcf", path, network);
	}

	private void printTestbench(StandardPrinter printer, Instance instance) {
		printer.print(instance.getId() + "_tb.vhd", path + File.separator
				+ "Testbench", instance);

		if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			for (Instance subInstance : network.getInstances()) {
				printTestbench(printer, subInstance);
			}
		}
	}

	private void printTCL(Instance instance) {
		CustomPrinter printer = new CustomPrinter(
				"net/sf/orcc/backends/xlim/hardware/Verilog_TCLLists.stg");

		entities = new ArrayList<String>();
		entitySet = new HashSet<String>();
		computeEntityList(instance);

		printer.print("TCLLists.tcl", path, "TCLLists", "name", instance
				.getNetwork().getName(), "entities", entities);
	}

	private void computeEntityList(Instance instance) {
		if (instance.isActor()) {
			String name = instance.getId();
			if (!entitySet.contains(name)) {
				entitySet.add(name);
				entities.add(name);
			}
		} else if (instance.isNetwork()) {
			String name = instance.getId();
			Network network = instance.getNetwork();
			if (!entitySet.contains(name)) {
				for (Instance subInstance : network.getInstances()) {
					computeEntityList(subInstance);
				}

				entitySet.add(name);
			}
		}
	}

	private void printNetwork(Network network) {
		StandardPrinter printer;
		String file = network.getName();
		if (hardwareGen) {
			// create a folder where to put .v files generated with openforge
			File sourceFolder = new File(path + File.separator + "Design");
			if (!sourceFolder.exists()) {
				sourceFolder.mkdir();
			}
			// generate instances test bench
			File folder = new File(path + File.separator + "Testbench");
			if (!folder.exists()) {
				folder.mkdir();
			}
			StandardPrinter instancePrinter = new StandardPrinter(
					"net/sf/orcc/backends/xlim/hardware/Verilog_testbench.stg");
			Instance instance = new Instance(network.getName(),
					network.getName());
			instance.setContents(network);
			printTestbench(instancePrinter, instance);
			printTCL(instance);

			file += ".vhd";
			printer = new StandardPrinter(
					"net/sf/orcc/backends/xlim/hardware/XLIM_hw_network.stg");
		} else {
			file += ".c";
			printer = new StandardPrinter(
					"net/sf/orcc/backends/xlim/XLIM_sw_network.stg");
		}

		printer.setExpressionPrinter(new XlimExprPrinter());
		printer.setTypePrinter(new XlimTypePrinter());
		printer.getOptions().put("fifoSize", fifoSize);
		printer.print(file, path, network);
		if (!hardwareGen) {
			printCMake(network);
			if (!mapping.isEmpty()) {
				printMapping(network, mapping);
			}
		}
	}

	public void setFpgaType(String fpgaType) {
		this.fpgaType = fpgaType;
	}

	public void setHardwareGen(Boolean hardwareGen) {
		this.hardwareGen = hardwareGen;
	}
}
