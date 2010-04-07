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
package net.sf.orcc.backends.c;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.NetworkPrinter;
import net.sf.orcc.backends.c.transforms.MoveReadsWritesTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.DeadCodeElimination;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.DeadVariableRemoval;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transforms.BroadcastAdder;

import org.eclipse.core.runtime.CoreException;

/**
 * C back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CBackendImpl extends AbstractBackend {

	public static boolean merge = false;

	/**
	 * printer is protected in order to be visible to CQuasiBackendImpl
	 */
	protected CActorPrinter printer;

	@Override
	protected void doActorCodeGeneration(Network network) throws OrccException {
		network.flatten();

		if (merge) {
			network.classifyActors();
			network.normalizeActors();
			network.mergeActors();
		} else {
			if (configuration != null) {
				try {
					boolean classify = configuration.getAttribute(
							"net.sf.orcc.backends.classify", false);
					if (classify) {
						network.classifyActors();
					}
				} catch (CoreException e) {
					throw new OrccException("could not read configuration", e);
				}
			}
		}

		// until now, printer is default printer
		printer = new CActorPrinter();

		List<Actor> actors = network.getActors();
		transformActors(actors);
		printInstances(network);
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

	@Override
	protected void printNetwork(Network network) throws OrccException {
		try {
			String networkTemplate = "C_network";
			if (configuration != null) {
				boolean useNewScheduler;
				try {
					useNewScheduler = configuration.getAttribute(
							"net.sf.orcc.backends.newScheduler", false);
				} catch (CoreException e) {
					throw new OrccException("could not read configuration", e);
				}

				if (useNewScheduler) {
					networkTemplate = "C_network_newScheduler";
				}
			}

			NetworkPrinter networkPrinter = new NetworkPrinter(networkTemplate);

			// Add broadcasts before printing
			new BroadcastAdder().transform(network);

			String outputName = path + File.separator + network.getName()
					+ ".c";
			networkPrinter.printNetwork(outputName, network, false, fifoSize);

			new CMakePrinter().printCMake(path, network);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	@Override
	protected void transformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = { new DeadGlobalElimination(),
				new DeadCodeElimination(), new DeadVariableRemoval(),
				new PhiRemoval(), new MoveReadsWritesTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}
	}

}
