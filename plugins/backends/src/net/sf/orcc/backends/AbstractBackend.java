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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRParser;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;

import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * This class is an abstract implementation of {@link Backend}.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractBackend implements Backend {

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
				backend.generateCode(null, inputFile, outputFolder, 10000);
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
	 * the process that launched this backend
	 */
	private OrccProcess process;

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for actors or instances.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	protected void doActorCodeGeneration(Network network) throws OrccException {
	}

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for VTL.
	 * 
	 * @param actors
	 *            a list of actors
	 * @throws OrccException
	 */
	protected void doVtlCodeGeneration(List<Actor> actors) throws OrccException {
	}

	@Override
	final public void generateCode(OrccProcess process, String inputFile,
			String outputFolder, int fifoSize) throws OrccException {
		this.process = process;

		// set FIFO size
		this.fifoSize = fifoSize;

		// set output path
		path = new File(outputFolder).getAbsolutePath();

		// parses top network
		write("Parsing XDF network...\n");
		Network network = new XDFParser(inputFile).parseNetwork();

		// instantiate the network
		write("Instantiating actors...\n");
		network.instantiate(outputFolder);
		Network.clearActorPool();

		write("Instantiation done\n");

		doActorCodeGeneration(network);

		// print network
		write("Printing network...\n");
		printNetwork(network);

		write("That's all folks!\n");
	}

	@Override
	final public void generateVtl(OrccProcess process, String outputFolder)
			throws OrccException {
		this.process = process;

		// set output path
		path = new File(outputFolder).getAbsolutePath();

		// lists actors
		write("Lists actors...\n");
		List<Actor> actors = new ArrayList<Actor>();
		File[] files = new File(outputFolder).listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".json");
			}
		});

		// parses actors
		write("Parsing " + files.length + " actors...\n");
		try {
			for (File file : files) {
				InputStream in = new FileInputStream(file);
				Actor actor = new IRParser().parseActor(in);
				actors.add(actor);
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}

		doVtlCodeGeneration(actors);

		write("That's all folks!\n");
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
	 * @param actors
	 *            a list of actors
	 * @throws OrccException
	 */
	final protected void printActors(List<Actor> actors) throws OrccException {
		write("Printing actors...\n");
		long t0 = System.currentTimeMillis();
		for (Actor actor : actors) {
			printActor(actor);
		}

		long t1 = System.currentTimeMillis();
		write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");
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
	final protected void printInstances(Network network) throws OrccException {
		write("Printing instances...\n");
		long t0 = System.currentTimeMillis();
		for (Instance instance : network.getInstances()) {
			if (instance.isActor()) {
				printInstance(instance);
			}
		}

		long t1 = System.currentTimeMillis();
		write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            the network
	 */
	protected void printNetwork(Network network) throws OrccException {
	}

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
	abstract protected void transformActor(Actor actor) throws OrccException;

	/**
	 * Transforms instances of the given network.
	 * 
	 * @param actors
	 *            a list of actors
	 * @throws OrccException
	 */
	final protected void transformActors(List<Actor> actors)
			throws OrccException {
		write("Transforming actors...\n");
		for (Actor actor : actors) {
			transformActor(actor);
		}
	}

	/**
	 * Writes the given text to the process's normal output.
	 * 
	 * @param text
	 *            a string
	 */
	protected void write(String text) {
		if (process != null) {
			process.write(text);
		}
	}

}
