/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccLaunchConstants;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.NetworkPrinter;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.backends.transformations.ListFlattenTransformation;
import net.sf.orcc.backends.transformations.ListOfOneElementToScalarTransformation;
import net.sf.orcc.backends.transformations.VariableRenamer;
import net.sf.orcc.backends.transformations.threeAddressCodeTransformation.CastAdderTransformation;
import net.sf.orcc.backends.transformations.threeAddressCodeTransformation.ExpressionSplitterTransformation;
import net.sf.orcc.backends.xlim.transformations.ArrayInitializeTransformation;
import net.sf.orcc.backends.xlim.transformations.ConstantPhiValuesTransformation;
import net.sf.orcc.backends.xlim.transformations.CustomPeekAdder;
import net.sf.orcc.backends.xlim.transformations.MoveLiteralIntegers;
import net.sf.orcc.backends.xlim.transformations.TernaryOperationAdder;
import net.sf.orcc.backends.xlim.transformations.XlimDeadVariableRemoval;
import net.sf.orcc.backends.xlim.transformations.XlimInlineTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorVisitor;
import net.sf.orcc.ir.transformations.BuildCFG;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFWriter;

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

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(XlimBackendImpl.class, args);
	}

	private String fpgaType;

	private boolean hardwareGen;

	private STPrinter printer;

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
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorVisitor[] transformations = { new ArrayInitializeTransformation(),
				new TernaryOperationAdder(),
				new XlimInlineTransformation(true, true),
				new ListOfOneElementToScalarTransformation(),
				new CustomPeekAdder(), new DeadGlobalElimination(),
				new DeadCodeElimination(), new XlimDeadVariableRemoval(true),
				new ListFlattenTransformation(false, true, false),
				new ExpressionSplitterTransformation(), new BuildCFG(),
				new CastAdderTransformation(true),
				new ConstantPhiValuesTransformation(),
				new MoveLiteralIntegers(), new VariableRenamer() };

		for (ActorVisitor transformation : transformations) {
			transformation.visit(actor);
		}

	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		// do not generate an XLIM VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();
		// print network
		write("Printing network...\n");
		new XDFWriter(new File(path), network);

		// check if "XLiM Hardware Generation" is selected

		hardwareGen = getAttribute("net.sf.orcc.backends.xlimHard", true);

		printer = new STPrinter();
		printer.getOptions().putAll(getAttributes());
		if (hardwareGen) {
			fpgaType = getAttribute("net.sf.orcc.backends.xlimFpgaType",
					"xc2vp30-7-ff1152");
			printer.getOptions().put("fpgaType", fpgaType);
			printer.loadGroup("XLIM_hw_actor");
		} else {
			printer.loadGroup("XLIM_sw_actor");
		}

		printer.setExpressionPrinter(XlimExprPrinter.class);
		printer.setTypePrinter(XlimTypePrinter.class);

		transformActors(network.getActors());
		doTransformNetwork(network);
		printInstances(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	private void doTransformNetwork(Network network) {
		XlimHwNetworkTemplateData data = new XlimHwNetworkTemplateData();
		network.computeTemplateMaps();
		data.computeTemplateMaps(network);
		network.setTemplateData(data);
	}

	private void printCMake(Network network) {
		NetworkPrinter networkPrinter = new NetworkPrinter("XLIM_sw_CMakeLists");
		networkPrinter.print("CMakeLists.txt", path, network, "CMakeLists");
	}

	@Override
	protected boolean printInstance(Instance instance) throws OrccException {
		String id = instance.getId();
		String outputName = path + File.separator + id + ".xlim";
		try {
			return printer.printInstance(outputName, instance);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	private void printMapping(Network network, Map<String, String> mapping) {
		NetworkPrinter networkPrinter = new NetworkPrinter("XLIM_sw_mapping");
		networkPrinter.getOptions().put("mapping",
				computeMapping(network, mapping));
		networkPrinter.getOptions().put("fifoSize", fifoSize);
		networkPrinter.print(network.getName() + ".xcf", path, network,
				"mapping");
	}

	private void printNetwork(Network network) throws OrccException {
		try {
			String outputName = path + File.separator + network.getName();
			if (hardwareGen) {
				outputName += ".vhd";
				printer.loadGroup("XLIM_hw_network");
			} else {
				outputName += ".c";
				printer.loadGroup("XLIM_sw_network");
			}

			printer.printNetwork(outputName, network, false, fifoSize);

			if (!hardwareGen) {
				printCMake(network);

				Map<String, String> mapping = getAttribute(
						OrccLaunchConstants.MAPPING,
						new HashMap<String, String>());
				if (!mapping.isEmpty()) {
					printMapping(network, mapping);
				}
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}
}
