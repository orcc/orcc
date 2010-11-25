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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccLaunchConstants;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.backends.transformations.InlineTransformation;
import net.sf.orcc.backends.transformations.ListFlattenTransformation;
import net.sf.orcc.backends.transformations.ListOfOneElementToScalarTransformation;
import net.sf.orcc.backends.transformations.VariableRenamer;
import net.sf.orcc.backends.transformations.threeAddressCodeTransformation.CastAdderTransformation;
import net.sf.orcc.backends.transformations.threeAddressCodeTransformation.ExpressionSplitterTransformation;
import net.sf.orcc.backends.xlim.transformations.ArrayInitializeTransformation;
import net.sf.orcc.backends.xlim.transformations.ChangeActionSchedulerFormTransformation;
import net.sf.orcc.backends.xlim.transformations.FirstPhiValuesOfWhileNodeTransformation;
import net.sf.orcc.backends.xlim.transformations.MoveLiteralIntegers;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transformations.BuildCFG;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
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

	private STPrinter printer;

	private boolean hardwareGen;

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = {
				new ArrayInitializeTransformation(),
				new InlineTransformation(true, true),
				new ListOfOneElementToScalarTransformation(),
				new DeadGlobalElimination(), new DeadCodeElimination(),
				new DeadVariableRemoval(true),
				new ListFlattenTransformation(false, true, false),
				new ExpressionSplitterTransformation(), new BuildCFG(),
				new CastAdderTransformation(),
				new FirstPhiValuesOfWhileNodeTransformation(),
				new MoveLiteralIntegers(), new VariableRenamer(),
				new ChangeActionSchedulerFormTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
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

		hardwareGen = getAttribute("net.sf.orcc.backends.xlimHard", false);

		printer = new STPrinter();

		if (hardwareGen) {
			printer.loadGroups("XLIM_actor", "XLIM_actor_hard");
		} else {
			printer.loadGroups("XLIM_actor_exp");
		}

		printer.setExpressionPrinter(XlimExprPrinter.class);
		printer.setTypePrinter(XlimTypePrinter.class);

		transformActors(network.getActors());
		printInstances(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	@Override
	protected void printInstance(Instance instance) throws OrccException {
		String id = instance.getId();
		String outputName = path + File.separator + id + ".xlim";
		try {
			printer.printInstance(outputName, instance);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	private void printNetwork(Network network) throws OrccException {
		try {
			String outputName = path + File.separator + network.getName();
			if (hardwareGen) {
				outputName += ".vhd";
				printer.loadGroups("XLIM_VHDL_network");
			} else {
				outputName += ".c";
				printer.loadGroups("XLIM_C_network");
			}

			printer.printNetwork(outputName, network, false, fifoSize);

			new XlimCMakePrinter().printCMake(path, network);

			Map<String, String> mapping = getAttribute(
					OrccLaunchConstants.MAPPING, new HashMap<String, String>());
			if (!mapping.isEmpty()) {
				new XlimMappingPrinter().printMapping(path, network, mapping,
						fifoSize);
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
