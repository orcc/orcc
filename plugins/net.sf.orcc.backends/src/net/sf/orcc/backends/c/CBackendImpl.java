/*
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
package net.sf.orcc.backends.c;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.backends.transformations.MoveReadsWritesTransformation;
import net.sf.orcc.backends.transformations.RenameTransformation;
import net.sf.orcc.backends.transformations.TypeSizeTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.DeadCodeElimination;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.DeadVariableRemoval;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transforms.BroadcastAdder;

/**
 * C back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CBackendImpl extends AbstractBackend {

	public static boolean merge = false;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(CBackendImpl.class, args);
	}

	/**
	 * printer is protected in order to be visible to CQuasiBackendImpl
	 */
	protected STPrinter printer;

	private final Map<String, String> transformations;

	public CBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_");
		transformations.put("index", "index_");
		transformations.put("getw", "getw_");
		transformations.put("select", "select_");
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = { new TypeSizeTransformation(),
				new DeadGlobalElimination(), new DeadCodeElimination(),
				new DeadVariableRemoval(),
				new RenameTransformation(this.transformations),
				new PhiRemoval(), new MoveReadsWritesTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		CTemplateData data = new CTemplateData();
		data.computeTemplateMaps(actor);
		actor.setTemplateData(data);
	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		// do not generate a C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		boolean classify = getAttribute(
				"net.sf.orcc.plugins.backends.classify", false);
		if (classify) {
			network.classifyActors();

			boolean normalize = getAttribute(
					"net.sf.orcc.plugins.backends.normalize", false);
			if (normalize) {
				network.normalizeActors();
			}

			boolean merge = getAttribute("net.sf.orcc.plugins.backends.merge",
					false);
			if (merge) {
				network.mergeActors();
			}
		}

		// until now, printer is default printer
		printer = new STPrinter();
		printer.loadGroups("C_actor");
		printer.setExpressionPrinter(CExpressionPrinter.class);
		printer.setTypePrinter(CTypePrinter.class);
		printer.setOptions(getAttributes());

		List<Actor> actors = network.getActors();
		transformActors(actors);
		printInstances(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	@Override
	protected void printInstance(Instance instance) throws OrccException {
		String id = instance.getId();
		String outputName = path + File.separator + id + ".c";
		try {
			printer.printInstance(outputName, instance);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private void printNetwork(Network network) throws OrccException {
		try {
			String[] networkTemplates;
			boolean useNewScheduler = getAttribute(
					"net.sf.orcc.plugins.backends.newScheduler", false);
			if (useNewScheduler) {
				networkTemplates = new String[2];
				networkTemplates[0] = "C_network";
				networkTemplates[1] = "C_network_newScheduler";
			} else {
				networkTemplates = new String[1];
				networkTemplates[0] = "C_network";
			}

			printer.loadGroups(networkTemplates);

			// Add broadcasts before printing
			new BroadcastAdder().transform(network);

			String outputName = path + File.separator + network.getName()
					+ ".c";
			printer.printNetwork(outputName, network, false, fifoSize);

			new CMakePrinter().printCMake(path, network);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
