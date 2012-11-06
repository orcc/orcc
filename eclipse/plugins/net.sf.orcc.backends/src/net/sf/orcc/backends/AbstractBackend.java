/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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

import static net.sf.orcc.OrccActivator.getDefault;
import static net.sf.orcc.OrccLaunchConstants.CLASSIFY;
import static net.sf.orcc.OrccLaunchConstants.COMPILE_XDF;
import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.MAPPING;
import static net.sf.orcc.OrccLaunchConstants.MERGE_ACTIONS;
import static net.sf.orcc.OrccLaunchConstants.MERGE_ACTORS;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER_OPTIONS;
import static net.sf.orcc.util.OrccUtil.getFile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.NetworkValidator;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * This class is an abstract implementation of {@link Backend}. The two entry
 * points of this class are the public methods {@link #compileVTL()} and
 * {@link #compileXDF()} which should NOT be called by back-ends themselves.
 * 
 * <p>
 * The following methods are abstract and must be implemented by back-ends:
 * <ul>
 * <li>{@link #doInitializeOptions()} is called by {@link #setOptions(Map)} to
 * initialize the options of the back-end.</li>
 * <li>{@link #doTransformActor(Actor)} is called by
 * {@link #transformActors(List)} to transform a list of actors.</li>
 * <li>{@link #doVtlCodeGeneration(List)} is called to compile a list of actors.
 * </li>
 * <li>{@link #doXdfCodeGeneration(Network)} is called to compile a network.</li>
 * </ul>
 * </p>
 * 
 * The following methods may be extended by back-ends, if they print actors or
 * instances respectively, or if a library must be exported with source file
 * produced.
 * <ul>
 * <li>{@link #printActor(Actor)} is called by {@link #printActors(List)}.</li>
 * <li>{@link #printInstance(Instance)} is called by
 * {@link #printInstances(Network)}.</li>
 * <li>{@link #exportRuntimeLibrary()} is called by {@link #start}.</li>
 * </ul>
 * 
 * The other methods declared <code>final</code> may be called by back-ends.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractBackend implements Backend, IApplication {

	protected boolean debug;
	/**
	 * Fifo size used in backend.
	 */
	protected int fifoSize;

	private IFile inputFile;
	protected Map<String, String> mapping;

	/**
	 * List of transformations to apply on each network
	 */
	protected List<DfSwitch<?>> networkTransfos;
	/**
	 * List of transformations to apply on each actor
	 */
	protected List<DfSwitch<?>> actorTransfos;

	protected boolean classify;
	protected boolean mergeActions;
	protected boolean mergeActors;

	/**
	 * the progress monitor
	 */
	private IProgressMonitor monitor;

	/**
	 * Options of backend execution. Its content can be manipulated with
	 * {@link #getAttribute} and {@link #setAttribute}
	 */
	private Map<String, Object> options;

	/**
	 * Path where output files will be written.
	 */
	protected String path;

	/**
	 * Represents the project where call application to build is located
	 */
	protected IProject project;

	/**
	 * Path of the folder that contains VTL under IR form.
	 */
	private List<IFolder> vtlFolders;

	/**
	 * Initialize some members
	 */
	public AbstractBackend() {
		actorTransfos = new ArrayList<DfSwitch<?>>();
		networkTransfos = new ArrayList<DfSwitch<?>>();
	}

	@Override
	public void compile() {
		compileVTL();

		if ((Boolean) options.get(COMPILE_XDF)) {
			compileXDF();
		}
	}

	final private void compileVTL() {
		// lists actors
		OrccLogger.traceln("Lists actors...");
		List<IFile> vtlFiles = OrccUtil.getAllFiles("ir", vtlFolders);
		doVtlCodeGeneration(vtlFiles);
	}

	final private void compileXDF() {
		// set FIFO size
		ResourceSet set = new ResourceSetImpl();

		// parses top network
		Network network = EcoreHelper.getEObject(set, inputFile);
		if (isCanceled()) {
			return;
		}
		new NetworkValidator().doSwitch(network);

		// because the UnitImporter will load additional resources, we filter
		// only actors
		List<Actor> actors = new ArrayList<Actor>();
		for (Resource resource : set.getResources()) {
			EObject eObject = resource.getContents().get(0);
			if (eObject instanceof Actor) {
				actors.add((Actor) eObject);
			}
		}

		if (isCanceled()) {
			return;
		}
		doXdfCodeGeneration(network);
	}

	/**
	 * Copy <i>source</i> file at <i>destination</i> path. If <i>destination</i>
	 * parents folder does not exists, they will be created
	 * 
	 * @param source
	 *            Resource file path starting with '/'. Must be an existing path
	 *            relative to classpath (JAR file root or project classpath)
	 * @param destination
	 *            Path of the target file
	 * @return <code>true</code> if the file has been successfully copied
	 */
	protected boolean copyFileToFilesystem(final String source,
			final String dest) {
		int bufferSize = 512;

		assert source != null;
		assert dest != null;
		assert source.startsWith("/");

		File fileOut = new File(dest);
		if (!fileOut.exists()) {
			try {
				File parentDir = fileOut.getParentFile();
				if (parentDir != null) {
					parentDir.mkdirs();
				}
				fileOut.createNewFile();
			} catch (IOException e) {
				OrccLogger.warnln("Unable to write " + dest + " file");
				return false;
			}
		}

		if (!fileOut.isFile()) {
			OrccLogger.warnln(dest + " is not a file path");
			fileOut.delete();
			return false;
		}

		InputStream is = this.getClass().getResourceAsStream(source);
		if (is == null) {
			OrccLogger.warnln("Unable to find " + source);
			return false;
		}
		DataInputStream dis;
		dis = new DataInputStream(is);

		FileOutputStream out;
		try {
			out = new FileOutputStream(fileOut);
		} catch (FileNotFoundException e1) {
			OrccLogger.warnln("File " + dest + " not found !");
			return false;
		}

		try {
			byte[] b = new byte[bufferSize];
			int i = is.read(b);
			while (i != -1) {
				out.write(b, 0, i);
				i = is.read(b);
			}
			dis.close();
			out.close();
		} catch (IOException e) {
			OrccLogger.warnln("IOError : " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Copy <i>source</i> folder and all its content under <i>destination</i>.
	 * Final '/' is not required for both parameters. If <i>destination</i> does
	 * not exists, it will be created
	 * 
	 * @param source
	 *            Resource folder path starting with '/'. Must be an existing
	 *            path relative to classpath (JAR file root or project
	 *            classpath)
	 * @param destination
	 *            Filesystem folder path
	 * @return <code>true</code> if the folder has been successfully copied
	 */
	protected boolean copyFolderToFileSystem(final String source,
			final String destination) {
		assert source != null;
		assert destination != null;
		assert source.startsWith("/");

		File outputDir = new File(destination);

		if (!outputDir.exists()) {
			if (!outputDir.mkdirs()) {
				OrccLogger.warnln("Unable to create " + outputDir + " folder");
				return false;
			}
		}

		if (!outputDir.isDirectory()) {
			OrccLogger.warnln(outputDir
					+ " does not exists or is not a directory.");
			return false;
		}

		String inputDir;
		// Remove last '/' character (if needed)
		if (source.charAt(source.length() - 1) == '/') {
			inputDir = source.substring(0, source.length() - 1);
		} else {
			inputDir = source;
		}

		try {
			URL toto = this.getClass().getResource(inputDir);
			URL inputURL = FileLocator.resolve(toto);
			String inputPath = inputURL.toString();

			boolean result = true;

			if (inputPath.startsWith("jar:file:")) {
				// Backend running from jar file
				inputPath = inputPath.substring(9, inputPath.indexOf('!'));

				JarFile jar = new JarFile(inputPath);
				try {
					Enumeration<JarEntry> jarEntries = jar.entries();
					if (jarEntries == null) {
						OrccLogger.warnln("Unable to list content from "
								+ jar.getName() + " file.");
						return false;
					}

					// "source" value without starting '/' char
					String sourceMinusSlash = source.substring(1);

					JarEntry elt;
					while (jarEntries.hasMoreElements()) {

						elt = jarEntries.nextElement();

						// Only deal with sub-files of 'source' path
						if (elt.isDirectory()
								|| !elt.getName().startsWith(sourceMinusSlash)) {
							continue;
						}

						String newInPath = "/" + elt.getName();
						String newOutPath = outputDir
								+ File.separator
								+ elt.getName().substring(
										sourceMinusSlash.length());
						result &= copyFileToFilesystem(newInPath, newOutPath);
					}
					return result;
				} finally {
					jar.close();
				}
			} else {
				// Backend running from filesystem
				File[] listDir = new File(inputPath.substring(5)).listFiles();

				for (File elt : listDir) {
					String newInPath = inputDir + File.separator
							+ elt.getName();

					String newOutPath = outputDir + File.separator
							+ elt.getName();

					if (elt.isDirectory()) {
						result &= copyFolderToFileSystem(newInPath, newOutPath);
					} else {
						result &= copyFileToFilesystem(newInPath, newOutPath);
					}
				}
				return result;
			}
		} catch (IOException e) {
			OrccLogger.warnln("IOError" + e.getMessage());
			return false;
		}
	}

	/**
	 * Called when options are initialized.
	 */
	abstract protected void doInitializeOptions();

	/**
	 * Transforms the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract protected void doTransformActor(Actor actor);

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for VTL.
	 * 
	 * @param files
	 *            a list of IR files
	 */
	abstract protected void doVtlCodeGeneration(List<IFile> files);

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for the network or its instances or both.
	 * 
	 * @param network
	 *            a network
	 */
	abstract protected void doXdfCodeGeneration(Network network);

	/**
	 * Executes the given list of tasks using a thread pool with one thread per
	 * processor available.
	 * 
	 * @param tasks
	 *            a list of tasks
	 */
	private int executeTasks(List<Callable<Boolean>> tasks) {
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
					if (cause instanceof OrccRuntimeException) {
						throw (OrccRuntimeException) e.getCause();
					} else {
						String msg = "";
						if (e.getCause().getMessage() != null) {
							msg = "(" + e.getCause().getMessage() + ")";
						}
						throw new OrccRuntimeException(
								"One actor could not be printed " + msg,
								e.getCause());
					}
				}
			}

			// shutdowns the pool
			// no need to wait because tasks are completed after invokeAll
			pool.shutdown();

			return numCached;
		} catch (InterruptedException e) {
			throw new OrccRuntimeException("actors could not be printed", e);
		}
	}

	/**
	 * Export runtime library used by source produced. Should be overridden by
	 * back-ends that produce code source which need third party libraries at
	 * runtime.
	 * 
	 * @return <code>true</code> if the libraries were correctly exported
	 */
	@Override
	public boolean exportRuntimeLibrary() {
		return false;
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
	 */
	final public boolean getAttribute(String attributeName, boolean defaultValue) {
		Object obj = options.get(attributeName);
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		} else {
			return defaultValue;
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
	 */
	final public int getAttribute(String attributeName, int defaultValue) {
		Object obj = options.get(attributeName);
		if (obj instanceof Integer) {
			return (Integer) obj;
		} else {
			return defaultValue;
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
	 */
	@SuppressWarnings("unchecked")
	final public Map<String, String> getAttribute(String attributeName,
			Map<String, String> defaultValue) {
		Object obj = options.get(attributeName);
		if (obj instanceof Map<?, ?>) {
			return (Map<String, String>) obj;
		} else {
			return defaultValue;
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
	 */
	final public String getAttribute(String attributeName, String defaultValue) {
		Object obj = options.get(attributeName);
		if (obj instanceof String) {
			return (String) obj;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Returns a map containing the backend attributes in this launch
	 * configuration. Returns an empty map if the backend configuration has no
	 * attributes.
	 * 
	 * @return a map of attribute keys and values.
	 */
	final public Map<String, Object> getAttributes() {
		return options;
	}

	/**
	 * Returns true if this process has been canceled.
	 * 
	 * @return true if this process has been canceled
	 */
	protected boolean isCanceled() {
		if (monitor == null) {
			return false;
		} else {
			return monitor.isCanceled();
		}
	}

	/**
	 * Parses the given file list and returns a list of actors.
	 * 
	 * @param files
	 *            a list of JSON files
	 * @return a list of actors
	 */
	final public List<Actor> parseActors(List<IFile> files) {
		// NOTE: the actors are parsed but are NOT put in the actor pool because
		// they may be transformed and not have the same properties (in
		// particular concerning types), and instantiation then complains.

		OrccLogger.traceln("Parsing " + files.size() + " actors...");
		ResourceSet set = new ResourceSetImpl();
		List<Actor> actors = new ArrayList<Actor>();
		for (IFile file : files) {
			Resource resource = set.getResource(URI.createPlatformResourceURI(
					file.getFullPath().toString(), true), true);
			EObject eObject = resource.getContents().get(0);
			if (eObject instanceof Actor) {
				// do not add units
				actors.add((Actor) eObject);
			}

			if (isCanceled()) {
				break;
			}
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
	protected boolean printActor(Actor actor) {
		return false;
	}

	/**
	 * Print instances of the given network.
	 * 
	 * @param actors
	 *            a list of actors
	 */
	final public void printActors(List<Actor> actors) {
		OrccLogger.traceln("Printing actors...");
		long t0 = System.currentTimeMillis();

		// creates a list of tasks: each task will print an actor when called
		List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
		for (final Actor actor : actors) {
			tasks.add(new Callable<Boolean>() {

				@Override
				public Boolean call() {
					if (isCanceled()) {
						return false;
					}
					return printActor(actor);
				}

			});
		}

		// executes the tasks
		int numCached = executeTasks(tasks);

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
				+ "s");

		if (numCached > 0) {
			OrccLogger
					.traceln("*******************************************************************************");
			OrccLogger.traceln("* NOTE: " + numCached
					+ " actors were not regenerated "
					+ "because they were already up-to-date. *");
			OrccLogger
					.traceln("*******************************************************************************");
		}
	}

	/**
	 * Print entities of the given network.
	 * 
	 * @param entities
	 *            a list of entities
	 */
	final public void printEntities(Network network) {
		OrccLogger.traceln("Printing entities...");
		long t0 = System.currentTimeMillis();

		// creates a list of tasks: each task will print an actor when called
		List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
		for (final Vertex vertex : network.getChildren()) {
			tasks.add(new Callable<Boolean>() {

				@Override
				public Boolean call() {
					if (isCanceled()) {
						return false;
					}
					return printEntity(vertex);
				}

			});
		}

		// executes the tasks
		int numCached = executeTasks(tasks);

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
				+ "s");

		if (numCached > 0) {
			OrccLogger
					.traceln("*******************************************************************************");
			OrccLogger.traceln("* NOTE: " + numCached
					+ " entities were not regenerated "
					+ "because they were already up-to-date. *");
			OrccLogger
					.traceln("*******************************************************************************");
		}
	}

	/**
	 * Prints the given entity. Should be overridden by back-ends that wish to
	 * print the given entity.
	 * 
	 * @param entity
	 *            the entity
	 * @return <code>true</code> if the actor was cached
	 */
	protected boolean printEntity(Vertex entity) {
		return false;
	}

	/**
	 * Prints the given instance. Should be overridden by back-ends that wish to
	 * print the given instance.
	 * 
	 * @param instance
	 *            the instance
	 * @return <code>true</code> if the actor was cached
	 */
	protected boolean printInstance(Instance instance) {
		return false;
	}

	/**
	 * Print instances of the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 */
	final public void printInstances(Network network) {
		OrccLogger.traceln("Printing instances...");
		long t0 = System.currentTimeMillis();

		// creates a list of tasks: each task will print an instance when called
		List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
		for (Vertex vertex : network.getChildren()) {
			final Instance instance = vertex.getAdapter(Instance.class);
			if (instance != null) {
				tasks.add(new Callable<Boolean>() {

					@Override
					public Boolean call() {
						return printInstance(instance);
					}

				});
			}
		}

		// executes the tasks
		int numCached = executeTasks(tasks);

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
				+ "s");

		if (numCached > 0) {
			OrccLogger
					.traceln("*******************************************************************************");
			OrccLogger.traceln("* NOTE: " + numCached
					+ " instances were not regenerated "
					+ "because they were already up-to-date. *");
			OrccLogger
					.traceln("*******************************************************************************");
		}
	}

	private void printUsage(IApplicationContext context, Options options,
			String parserMsg) {

		String footer = "";
		if (parserMsg != null && !parserMsg.isEmpty()) {
			footer = "\nMessage of the command line parser :\n" + parserMsg;
		}

		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.setWidth(80);
		helpFormatter.printHelp(getClass().getSimpleName()
				+ " [options] <network.qualified.name>", "Valid options are :",
				options, footer);
	}

	@Override
	public void setOptions(Map<String, Object> options) {
		this.options = options;

		String name = getAttribute(PROJECT, "");
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		project = root.getProject(name);
		vtlFolders = OrccUtil.getOutputFolders(project);

		inputFile = getFile(project, getAttribute(XDF_FILE, ""), "xdf");

		fifoSize = getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		debug = getAttribute(DEBUG_MODE, true);
		mapping = getAttribute(MAPPING, new HashMap<String, String>());

		classify = getAttribute(CLASSIFY, false);
		// Merging transformations need the results of classification
		mergeActions = classify && getAttribute(MERGE_ACTIONS, false);
		mergeActors = classify && getAttribute(MERGE_ACTORS, false);

		String outputFolder;
		Object obj = options.get(OUTPUT_FOLDER);
		if (obj instanceof String) {
			outputFolder = (String) obj;
			if (outputFolder.startsWith("~")) {
				outputFolder = outputFolder.replace("~",
						System.getProperty("user.home"));
			}
		} else {
			outputFolder = "";
		}

		if (outputFolder.isEmpty()) {
			String tmpdir = System.getProperty("java.io.tmpdir");
			File output = new File(tmpdir, "orcc");
			output.mkdir();
			outputFolder = output.getAbsolutePath();
		}

		// set output path
		path = new File(outputFolder).getAbsolutePath();

		doInitializeOptions();
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {

		Options options = new Options();
		Option opt;

		// Required command line arguments
		opt = new Option("p", "project", true, "Project name");
		opt.setRequired(true);
		options.addOption(opt);

		opt = new Option("o", "output", true, "Output folder");
		opt.setRequired(true);
		options.addOption(opt);

		// Optional command line arguments
		options.addOption("d", "debug", false, "Enable debug mode");

		options.addOption("c", "classify", false, "Classify the given network");
		options.addOption("smt", "smt-solver", true,
				"Set path to the binary of the SMT solver (Z3 v4.12+)");
		options.addOption("m", "merge", false, "Merge (1) static actions "
				+ "(2) static actors (3) both");
		options.addOption("s", "advanced-scheduler", false, "(C) Use the "
				+ "data-driven/demand-driven strategy for the actor-scheduler");

		// FIXME: choose independently the transformation to apply
		options.addOption("t", "transfo_add", false,
				"Execute additional transformations before generate code");

		try {
			CommandLineParser parser = new PosixParser();

			// parse the command line arguments
			CommandLine line = parser.parse(options, (String[]) context
					.getArguments().get(IApplicationContext.APPLICATION_ARGS));

			if (line.getArgs().length != 1) {
				throw new ParseException(
						"Expected network name as last argument");
			}
			String networkName = line.getArgs()[0];

			Map<String, Object> optionMap = new HashMap<String, Object>();
			optionMap.put(COMPILE_XDF, true);
			optionMap.put(PROJECT, line.getOptionValue('p'));
			optionMap.put(XDF_FILE, networkName);
			optionMap.put(OUTPUT_FOLDER, line.getOptionValue('o'));

			optionMap.put(DEBUG_MODE, line.hasOption('d'));

			optionMap.put(CLASSIFY, line.hasOption('c'));

			if (line.hasOption("smt")) {
				String smt_path = line.getOptionValue("smt");
				String smt_option = new String();

				if (smt_path.contains("z3")) {
					if (Platform.OS_WIN32.equals(Platform.getOS())) {
						smt_option = "/smt2";
					} else {
						smt_option = "-smt2";
					}
					getDefault().setPreference(P_SOLVER, smt_path);
					getDefault().setPreference(P_SOLVER_OPTIONS, smt_option);
				} else {
					OrccLogger.warnln("Unknown SMT solver.");
				}
			}

			if (line.hasOption('m')) {
				String type = line.getOptionValue('m');
				optionMap.put(MERGE_ACTIONS,
						type.equals("1") || type.equals("3"));
				optionMap.put(MERGE_ACTORS,
						type.equals("2") || type.equals("3"));
			}

			optionMap.put("net.sf.orcc.backends.newScheduler",
					line.hasOption('s'));
			optionMap.put("net.sf.orcc.backends.additionalTransfos",
					line.hasOption('t'));

			try {
				setOptions(optionMap);
				exportRuntimeLibrary();
				compile();
				return IApplication.EXIT_OK;
			} catch (OrccRuntimeException e) {
				OrccLogger.severeln(e.getMessage());
				OrccLogger.severeln("Could not run the back-end with \""
						+ networkName + "\" :");
				OrccLogger.severeln(e.getLocalizedMessage());
			} catch (Exception e) {
				OrccLogger.severeln("Could not run the back-end with \""
						+ networkName + "\" :");
				OrccLogger.severeln(e.getLocalizedMessage());
				e.printStackTrace();
			}
			return IApplication.EXIT_RELAUNCH;

		} catch (UnrecognizedOptionException uoe) {
			printUsage(context, options, uoe.getLocalizedMessage());
		} catch (ParseException exp) {
			printUsage(context, options, exp.getLocalizedMessage());
		}
		return IApplication.EXIT_RELAUNCH;
	}

	@Override
	public void stop() {

	}

	/**
	 * Transforms instances of the given network.
	 * 
	 * @param actors
	 *            a list of actors
	 * @throws OrccException
	 */
	final public void transformActors(List<Actor> actors) {
		OrccLogger.traceln("Transforming actors...");
		for (Actor actor : actors) {
			doTransformActor(actor);
		}
	}
}
