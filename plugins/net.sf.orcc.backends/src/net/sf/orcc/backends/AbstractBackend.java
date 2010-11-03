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

import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRParser;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.plugins.backends.Backend;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * This class is an abstract implementation of {@link Backend}. The two entry
 * points of this class are the public methods
 * {@link #compileVTL(OrccProcess, String)} and
 * {@link #compileXDF(OrccProcess, String, String)} which should NOT be called
 * by back-ends themselves.
 * 
 * <p>
 * The following methods are abstract and must be implemented by back-ends:
 * <ul>
 * <li>{@link #doTransformActor(Actor)} is called by
 * {@link #transformActors(List)} to transform a list of actors.</li>
 * <li>{@link #doVtlCodeGeneration(List)} is called to compile a list of actors.
 * </li>
 * <li>{@link #doXdfCodeGeneration(Network)} is called to compile a network.</li>
 * </ul>
 * </p>
 * 
 * The following methods may be extended by back-ends, if they print actors or
 * instances respectively.
 * <ul>
 * <li>{@link #printActor(Actor)} is called by {@link #printActors(List)}.</li>
 * <li>{@link #printInstance(Instance)} is called by
 * {@link #printInstances(Network)}.</li>
 * </ul>
 * 
 * The other methods declared <code>final</code> may be called by back-ends.
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
		if (args.length == 3) {
			String inputFile = args[0];
			String vtlFolder = args[1];
			String outputFolder = args[2];

			try {
				AbstractBackend backend = clasz.newInstance();
				backend.setOutputFolder(outputFolder);
				backend.compileVTL(null, vtlFolder);
				backend.compileXDF(null, inputFile);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err.println("Usage: " + clasz.getSimpleName()
					+ " <input XDF network> <VTL folder> <output folder>");
		}
	}

	/**
	 * the configuration used to launch this back-end.
	 */
	private ILaunchConfiguration configuration;

	/**
	 * Fifo size used in backend.
	 */
	protected int fifoSize;

	/**
	 * Path where output files will be written.
	 */
	protected String path;

	/**
	 * the process that launched this backend
	 */
	private OrccProcess process;

	/**
	 * Path of the folder that contains VTL under IR form.
	 */
	private String vtlFolder;

	@Override
	final public void compileVTL(OrccProcess process, String vtlFolder)
			throws OrccException {
		this.process = process;
		this.vtlFolder = vtlFolder;

		// lists actors
		write("Lists actors...\n");
		File[] files = new File(vtlFolder).listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".json");
			}

		});

		Arrays.sort(files, new Comparator<File>() {

			@Override
			public int compare(File f1, File f2) {
				return f1.compareTo(f2);
			}

		});
		doVtlCodeGeneration(Arrays.asList(files));
	}

	@Override
	final public void compileXDF(OrccProcess process, String inputFile)
			throws OrccException {
		this.process = process;

		// set FIFO size
		this.fifoSize = getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);

		// parses top network
		write("Parsing XDF network...\n");
		Network network = new XDFParser(inputFile).parseNetwork();
		network.updateIdentifiers();

		write("Instantiating actors...\n");
		network.instantiate(vtlFolder);
		Network.clearActorPool();
		write("Instantiation done\n");

		doXdfCodeGeneration(network);
	}

	/**
	 * Transforms the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract protected void doTransformActor(Actor actor) throws OrccException;

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for VTL.
	 * 
	 * @param files
	 *            a list of IR files
	 * @throws OrccException
	 */
	abstract protected void doVtlCodeGeneration(List<File> files)
			throws OrccException;

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for the network or its instances or both.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	abstract protected void doXdfCodeGeneration(Network network)
			throws OrccException;

	/**
	 * Executes the given list of tasks using a thread pool with one thread per
	 * processor available.
	 * 
	 * @param tasks
	 *            a list of tasks
	 * @throws OrccException
	 *             if something goes wrong (code generation fails or a task is
	 *             interrupted)
	 */
	private int executeTasks(List<Callable<Boolean>> tasks)
			throws OrccException {
		// creates the pool
		int nThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(nThreads);
		try {
			// invokes all tasks and wait for them to complete
			List<Future<Boolean>> completeTasks = pool.invokeAll(tasks);

			// counts number of cached actors and checks exceptions
			int numCached = 0;
			for (Future<Boolean> completeTask : completeTasks) {
				try {
					if (completeTask.get()) {
						numCached++;
					}
				} catch (ExecutionException e) {
					Throwable cause = e.getCause();
					if (cause instanceof OrccException) {
						throw (OrccException) e.getCause();
					} else {
						throw new OrccException(
								"one actor could not be printed", cause);
					}
				}
			}

			// shutdowns the pool
			// no need to wait because tasks are completed after invokeAll
			pool.shutdown();

			return numCached;
		} catch (InterruptedException e) {
			throw new OrccException("actors could not be printed", e);
		}
	}

	/**
	 * Returns the boolean-valued attribute with the given name. Returns the
	 * given default value if the attribute is undefined.
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the default value if no value was found.
	 * @throws OrccException
	 */
	final public boolean getAttribute(String attributeName, boolean defaultValue)
			throws OrccException {
		if (configuration == null) {
			return defaultValue;
		}

		try {
			return configuration.getAttribute(attributeName, defaultValue);
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Returns the integer-valued attribute with the given name. Returns the
	 * given default value if the attribute is undefined.
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the default value if no value was found.
	 * @throws OrccException
	 */
	final public int getAttribute(String attributeName, int defaultValue)
			throws OrccException {
		if (configuration == null) {
			return defaultValue;
		}

		try {
			return configuration.getAttribute(attributeName, defaultValue);
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Returns the map-valued attribute with the given name. Returns the given
	 * default value if the attribute is undefined.
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the default value if no value was found.
	 * @throws OrccException
	 */
	@SuppressWarnings("unchecked")
	final public Map<String, String> getAttribute(String attributeName,
			Map<String, String> defaultValue) throws OrccException {
		if (configuration == null) {
			return defaultValue;
		}

		try {
			return configuration.getAttribute(attributeName, defaultValue);
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Returns the string-valued attribute with the given name. Returns the
	 * given default value if the attribute is undefined.
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the default value if no value was found.
	 * @throws OrccException
	 */
	final public String getAttribute(String attributeName, String defaultValue)
			throws OrccException {
		if (configuration == null) {
			return defaultValue;
		}

		try {
			return configuration.getAttribute(attributeName, defaultValue);
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Returns a map containing the backend attributes in this launch
	 * configuration. Returns an empty map if the backend configuration has no
	 * attributes.
	 * 
	 * @return a map of attribute keys and values.
	 * @throws OrccException
	 */
	@SuppressWarnings("unchecked")
	final public Map<String, Object> getAttributes() throws OrccException {
		if (configuration == null) {
			return new HashMap<String, Object>();
		}

		try {
			return configuration.getAttributes();
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Parses the given file list and returns a list of actors.
	 * 
	 * @param files
	 *            a list of JSON files
	 * @return a list of actors
	 * @throws OrccException
	 */
	final public List<Actor> parseActors(List<File> files) throws OrccException {
		// NOTE: the actors are parsed but are NOT put in the actor pool because
		// they may be transformed and not have the same properties (in
		// particular concerning types), and instantiation then complains.

		write("Parsing " + files.size() + " actors...\n");
		List<Actor> actors = new ArrayList<Actor>();
		try {
			for (File file : files) {
				InputStream in = new FileInputStream(file);
				Actor actor = new IRParser().parseActor(in);
				actors.add(actor);
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
		return actors;
	}

	/**
	 * Prints the given actor. Should be overridden by back-ends that wish to
	 * print the given actor.
	 * 
	 * @param actor
	 *            the actor
	 * @return <code>true</code> if the actor was cached
	 */
	protected boolean printActor(Actor actor) throws OrccException {
		return false;
	}

	/**
	 * Print instances of the given network.
	 * 
	 * @param actors
	 *            a list of actors
	 * @throws OrccException
	 */
	final public void printActors(List<Actor> actors) throws OrccException {
		write("Printing actors...\n");
		long t0 = System.currentTimeMillis();

		// creates a list of tasks: each task will print an actor when called
		List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
		for (final Actor actor : actors) {
			tasks.add(new Callable<Boolean>() {

				@Override
				public Boolean call() throws OrccException {
					return printActor(actor);
				}

			});
		}

		// executes the tasks
		int numCached = executeTasks(tasks);

		long t1 = System.currentTimeMillis();
		write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");

		if (numCached > 0) {
			write("*******************************************************************************\n");
			write("* NOTE: " + numCached + " actors were not regenerated "
					+ "because they were already up-to-date. *\n");
			write("*******************************************************************************\n");
		}
	}

	/**
	 * Prints the given instance. Should be overridden by back-ends that wish to
	 * print the given instance.
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
	final public void printInstances(Network network) throws OrccException {
		write("Printing instances...\n");
		long t0 = System.currentTimeMillis();

		// creates a list of tasks: each task will print an instance when called
		List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
		for (final Instance instance : network.getInstances()) {
			tasks.add(new Callable<Boolean>() {

				@Override
				public Boolean call() throws OrccException {
					if (instance.isActor()) {
						printInstance(instance);
					}
					return false;
				}

			});
		}

		// executes the tasks
		executeTasks(tasks);

		long t1 = System.currentTimeMillis();
		write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");
	}

	@Override
	final public void setLaunchConfiguration(ILaunchConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	final public void setOutputFolder(String outputFolder) {
		// set output path
		path = new File(outputFolder).getAbsolutePath();
	}

	/**
	 * Transforms instances of the given network.
	 * 
	 * @param actors
	 *            a list of actors
	 * @throws OrccException
	 */
	final public void transformActors(List<Actor> actors) throws OrccException {
		write("Transforming actors...\n");
		for (Actor actor : actors) {
			doTransformActor(actor);
		}
	}

	/**
	 * Writes the given text to the process's normal output.
	 * 
	 * @param text
	 *            a string
	 */
	final public void write(String text) {
		if (process != null) {
			process.write(text);
		}
	}

}
