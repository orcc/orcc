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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccLaunchConstants;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.InstancePrinter;
import net.sf.orcc.backends.NetworkPrinter;
import net.sf.orcc.backends.transformations.CastAdder;
import net.sf.orcc.backends.transformations.Inliner;
import net.sf.orcc.backends.transformations.threeAddressCodeTransformation.ExpressionSplitter;
import net.sf.orcc.backends.xlim.transformations.CustomPeekAdder;
import net.sf.orcc.backends.xlim.transformations.GlobalArrayInitializer;
import net.sf.orcc.backends.xlim.transformations.InstPhiTransformation;
import net.sf.orcc.backends.xlim.transformations.InstTernaryAdder;
import net.sf.orcc.backends.xlim.transformations.ListFlattener;
import net.sf.orcc.backends.xlim.transformations.LiteralIntegersAdder;
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
import net.sf.orcc.ir.util.EcoreHelper;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;

import org.eclipse.core.resources.IFile;

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
		mapping = getAttribute(OrccLaunchConstants.MAPPING,
				new HashMap<String, String>());
		debugMode = getAttribute(DEBUG_MODE, true);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		XlimActorTemplateData data = new XlimActorTemplateData();
		data.computeTemplateMaps(actor);
		actor.setTemplateData(data);

		ActorVisitor<?>[] transformations = { new SSATransformation(),
				new GlobalArrayInitializer(), new InstTernaryAdder(),
				new Inliner(true, true), new UnaryListRemoval(),
				new CustomPeekAdder(), new DeadGlobalElimination(),
				new DeadCodeElimination(), new XlimDeadVariableRemoval(),
				new ListFlattener(), new ExpressionSplitter(), new BuildCFG(),
				new CastAdder(), new InstPhiTransformation(),
				new LiteralIntegersAdder(), new XlimVariableRenamer(),
				new BlockCombine() };

		for (ActorVisitor<?> transformation : transformations) {
			transformation.doSwitch(actor);
			if (debugMode && !EcoreHelper.serializeActor(path, actor)) {
				System.out.println("oops " + transformation + " "
						+ actor.getName());
			}
		}

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
		NetworkPrinter networkPrinter = new NetworkPrinter("XLIM_sw_CMakeLists");
		networkPrinter.print("CMakeLists.txt", path, network, "CMakeLists");
	}

	@Override
	protected boolean printInstance(Instance instance) {
		InstancePrinter printer;
		if (hardwareGen) {
			printer = new InstancePrinter("XLIM_hw_actor", !debugMode);
			printer.getOptions().put("fpgaType", fpgaType);
		} else {
			printer = new InstancePrinter("XLIM_sw_actor", !debugMode);
		}

		printer.setExpressionPrinter(new XlimExprPrinter());
		printer.setTypePrinter(new XlimTypePrinter());
		return printer.print(instance.getId() + ".xlim", path, instance,
				"instance");
	}

	private void printMapping(Network network, Map<String, String> mapping) {
		NetworkPrinter networkPrinter = new NetworkPrinter("XLIM_sw_mapping");
		networkPrinter.getOptions().put("mapping",
				computeMapping(network, mapping));
		networkPrinter.getOptions().put("fifoSize", fifoSize);
		networkPrinter.print(network.getName() + ".xcf", path, network,
				"mapping");
	}

	private void printNetwork(Network network) {
		NetworkPrinter printer;
		String file = network.getName();
		if (hardwareGen) {
			file += ".vhd";
			printer = new NetworkPrinter("XLIM_hw_network");
		} else {
			file += ".c";
			printer = new NetworkPrinter("XLIM_sw_network");
		}

		printer.setExpressionPrinter(new XlimExprPrinter());
		printer.setTypePrinter(new XlimTypePrinter());
		printer.getOptions().put("fifoSize", fifoSize);
		printer.print(file, path, network, "network");
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
