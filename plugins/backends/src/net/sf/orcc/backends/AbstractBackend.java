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
package net.sf.orcc.backends;

import java.io.File;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;

import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * Abstract implementation of {@link IBackend}.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractBackend implements IBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(Class<? extends AbstractBackend> clasz,
			String[] args) {
		if (args.length == 2) {
			String inputFile = args[0];
			String outputFolder = args[1];

			try {
				AbstractBackend backend = clasz.newInstance();
				backend.generateCode(inputFile, outputFolder, 10000);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err.println("Usage: " + clasz.getSimpleName()
					+ "<input XDF network> <output folder>");
		}
	}

	/**
	 * the configuration used to launch this back-end.
	 */
	protected ILaunchConfiguration configuration;

	/**
	 * Fifo size used in backend.
	 */
	protected int fifoSize;

	/**
	 * Path of the network.
	 */
	protected String path;

	/**
	 * Here should go the things to do after the instantiation.
	 */
	protected void afterInstantiation(Network network) throws OrccException {
	}

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for actors or instances.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	abstract protected void doActorCodeGeneration(Network network)
			throws OrccException;

	@Override
	public void generateCode(String inputFile, String outputFolder, int fifoSize)
			throws OrccException {
		// set FIFO size
		this.fifoSize = fifoSize;

		// set output path
		path = new File(outputFolder).getAbsolutePath();

		// parses top network
		Network network = new XDFParser(inputFile).parseNetwork();

		// instantiate the network
		network.instantiate(outputFolder);
		Network.clearActorPool();

		afterInstantiation(network);

		doActorCodeGeneration(network);

		// print network
		printNetwork(network);
	}

	/**
	 * Prints the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	protected void printActor(Actor actor) throws OrccException {
	}

	/**
	 * Print instances of the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	protected void printActors(Network network) throws OrccException {
		for (Actor actor : network.getActors()) {
			printActor(actor);
		}
	}

	/**
	 * Prints the given instance.
	 * 
	 * @param instance
	 *            the instance
	 */
	protected void printInstance(Instance instance) throws OrccException {
	}

	/**
	 * Print instances of the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	protected void printInstances(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			if (instance.isActor()) {
				printInstance(instance);
			}
		}
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            the network
	 */
	abstract protected void printNetwork(Network network) throws OrccException;

	@Override
	public void setLaunchConfiguration(ILaunchConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Transforms the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	protected void transformActor(Actor actor) throws OrccException {
	}

	/**
	 * Transforms instances of the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	protected void transformActors(Network network) throws OrccException {
		for (Actor actor : network.getActors()) {
			transformActor(actor);
		}
	}

}
