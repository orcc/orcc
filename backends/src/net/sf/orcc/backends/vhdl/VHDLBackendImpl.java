/*
 * Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl;

import java.io.File;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.NetworkPrinter;
import net.sf.orcc.backends.vhdl.transforms.BoolExprTransform;
import net.sf.orcc.backends.vhdl.transforms.TransformConditionals;
import net.sf.orcc.backends.vhdl.transforms.VariableRedimension;
import net.sf.orcc.backends.vhdl.transforms.VariableRenamer;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.DeadCodeElimination;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.Inline;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transforms.BroadcastAdder;

/**
 * VHDL back-end.
 * 
 * @author Nicolas Siret
 * 
 */
public class VHDLBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				new VHDLBackendImpl().generateCode(args[0], 10000);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err
					.println("Usage: VHDLBackendImpl <flattened XDF network>");
		}
	}

	/**
	 * printer is protected
	 */
	private VHDLActorPrinter printer;

	@Override
	protected void beforeInstantiation(Network network) throws OrccException {
		printer = new VHDLActorPrinter();
	}

	@Override
	protected void printActor(String id, Actor actor) throws Exception {
		ActorTransformation[] transformations = { new DeadGlobalElimination(),
				new DeadCodeElimination(), new Inline(), new PhiRemoval(),
				new VariableRenamer(), new VariableRedimension(),
				new BoolExprTransform(), new TransformConditionals() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		String outputName = path + File.separator + id + ".vhd";
		printer.printActor(outputName, id, actor);
	}

	@Override
	protected void printNetwork(Network network) throws Exception {
		VHDLTestbenchPrinter tbPrinter = new VHDLTestbenchPrinter();
		for (Instance instance : network.getInstances()) {
			if (instance.isActor()) {
				Actor actor = instance.getActor();
				String id = instance.getId();
				String outputName = path + File.separator + id + "_tb.vhd";
				tbPrinter.printTestbench(outputName, id, actor);
			}
		}

		NetworkPrinter networkPrinter = new NetworkPrinter("VHDL_network");

		// Add broadcasts before printing
		new BroadcastAdder().transform(network);

		String outputName = path + File.separator + network.getName() + ".vhd";
		networkPrinter.printNetwork(outputName, network, false, fifoSize);
	}

}
