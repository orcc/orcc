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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.ActorPrinter;
import net.sf.orcc.backends.NetworkPrinter;
import net.sf.orcc.backends.cpp.CppExprPrinter;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transformations.BroadcastAdder;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;

/**
 * Java back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class JavaBackendImpl extends AbstractBackend {

	private ActorPrinter actorPrinter;

	private final Map<String, String> transformations;

	public JavaBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("initialize", "initialize_");
		transformations.put("isSchedulable_initialize",
				"isSchedulable_initialize_");
	}

	@Override
	protected void doInitializeOptions() {
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		new RenameTransformation(this.transformations).doSwitch(actor);
	}

	private void doTransformNetwork(Network network) throws OrccException {
		// Add broadcasts before printing
		new BroadcastAdder().transform(network);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		List<Actor> actors = parseActors(files);

		actorPrinter = new ActorPrinter(
				"net/sf/orcc/backends/java/Java_actor.stg", true);
		actorPrinter.setExpressionPrinter(new CppExprPrinter());
		actorPrinter.setTypePrinter(new JavaTypePrinter());

		transformActors(actors);
		printActors(actors);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		// transform network
		network.flatten();
		doTransformNetwork(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);
	}

	@Override
	protected boolean printActor(Actor actor) {
		// Create folder if necessary
		String folder = path + File.separator + OrccUtil.getFolder(actor);
		new File(folder).mkdirs();

		return actorPrinter.print("Actor_" + actor.getSimpleName() + ".java",
				folder, actor, "actor");
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
		NetworkPrinter printer = new NetworkPrinter(
				"net/sf/orcc/backends/java/Java_network.stg");
		printer.getOptions().put("fifoSize", fifoSize);
		printer.print("Network_" + network.getName() + ".java", path, "network");
	}

}
