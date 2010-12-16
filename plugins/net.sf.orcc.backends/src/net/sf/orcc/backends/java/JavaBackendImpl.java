/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.java;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.backends.cpp.CppExprPrinter;
import net.sf.orcc.backends.transformations.MoveReadsWritesTransformation;
import net.sf.orcc.backends.transformations.RenameTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.transformations.PhiRemoval;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transformations.BroadcastAdder;
import net.sf.orcc.util.OrccUtil;

/**
 * Java back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class JavaBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(JavaBackendImpl.class, args);
	}

	private STPrinter printer;

	private final Map<String, String> transformations;

	public JavaBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("initialize", "initialize_");
		transformations.put("isSchedulable_initialize",
				"isSchedulable_initialize_");
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = { new DeadGlobalElimination(),
				new DeadCodeElimination(), new DeadVariableRemoval(false),
				new RenameTransformation(this.transformations),
				new PhiRemoval(), new MoveReadsWritesTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		List<Actor> actors = parseActors(files);

		printer = new STPrinter();
		printer.loadGroups("C_actor", "Java_actor");
		printer.setExpressionPrinter(CppExprPrinter.class);
		printer.setTypePrinter(JavaTypePrinter.class);

		transformActors(actors);
		printActors(actors);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	@Override
	protected boolean printActor(Actor actor) throws OrccException {
		// Create folder if necessary
		String folder = path + File.separator + OrccUtil.getFolder(actor);
		new File(folder).mkdirs();

		// Set output file name for this actor
		String outputName = folder + File.separator + "Actor_" + actor.getSimpleName()+ ".java";
		try {
			return printer.printActor(outputName, actor);
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
	protected void printNetwork(Network network) throws OrccException {
		try {
			printer.loadGroups("C_network", "Java_network");

			// Add broadcasts before printing
			new BroadcastAdder().transform(network);

			String name = network.getName();
			String outputName = path + File.separator + "Network_" + name
					+ ".java";
			printer.printNetwork(outputName, network, false, fifoSize);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
