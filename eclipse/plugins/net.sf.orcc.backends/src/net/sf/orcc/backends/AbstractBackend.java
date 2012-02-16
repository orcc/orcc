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

import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;
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
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.util.EcoreHelper;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.WriteListener;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
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

	/**
	 * Fifo size used in backend.
	 */
	protected int fifoSize;

	private IFile inputFile;

	private WriteListener listener;

	/**
	 * the progress monitor
	 */
	private IProgressMonitor monitor;

	private Map<String, Object> options;

	/**
	 * Path where output files will be written.
	 */
	protected String path;

	protected IProject project;

	/**
	 * Path of the folder that contains VTL under IR form.
	 */
	private List<IFolder> vtlFolders;

	@Override
	final public void compileVTL() throws OrccException {
		// lists actors
		write("Lists actors...\n");
		List<IFile> vtlFiles = OrccUtil.getAllFiles("ir", vtlFolders);
		doVtlCodeGeneration(vtlFiles);
	}

	@Override
	final public void compileXDF() throws OrccException {
		// set FIFO size
		ResourceSet set = new ResourceSetImpl();

		// parses top network
		Network network = EcoreHelper.getEObject(set, inputFile);
		if (isCanceled()) {
			return;
		}

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
				write("Unable to write " + dest + " file\n");
				return false;
			}
		}

		if (!fileOut.isFile()) {
			write(dest + " is not a file path\n");
			fileOut.delete();
			return false;
		}

		InputStream is = this.getClass().getResourceAsStream(source);
		if (is == null) {
			write("Unable to find " + source + "\n");
			return false;
		}
		DataInputStream dis;
		dis = new DataInputStream(is);

		FileOutputStream out;
		try {
			out = new FileOutputStream(fileOut);
		} catch (FileNotFoundException e1) {
			write("File " + dest + " not found !" + "\n");
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
			write("IOError : " + e.getMessage() + "\n");
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
				write("Unable to create " + outputDir + " folder\n");
				return false;
			}
		}

		if (!outputDir.isDirectory()) {
			write(outputDir + " does not exists or is not a directory." + "\n");
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

				Enumeration<JarEntry> jarEntries = jar.entries();
				if (jarEntries == null) {
					write("Unable to list content from " + jar.getName()
							+ " file.\n");
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
							+ elt.getName()
									.substring(sourceMinusSlash.length());
					result &= copyFileToFilesystem(newInPath, newOutPath);
				}
				return result;

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
			write("IOError" + e.getMessage() + "\n");
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
	abstract protected void doTransformActor(Actor actor) throws OrccException;

	/**
	 * This method must be implemented by subclasses to do the actual code
	 * generation for VTL.
	 * 
	 * @param files
	 *            a list of IR files
	 * @throws OrccException
	 */
	abstract protected void doVtlCodeGeneration(List<IFile> files)
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
	 * Export runtime library used by source produced. Should be overridden by
	 * back-ends that produce code source which need third party libraries at
	 * runtime.
	 * 
	 * @return <code>true</code> if the libraries were correctly exported
	 */
	public boolean exportRuntimeLibrary() throws OrccException {
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
	 * @throws OrccException
	 */
	final public List<Actor> parseActors(List<IFile> files)
			throws OrccException {
		// NOTE: the actors are parsed but are NOT put in the actor pool because
		// they may be transformed and not have the same properties (in
		// particular concerning types), and instantiation then complains.

		write("Parsing " + files.size() + " actors...\n");
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
		write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");

		if (numCached > 0) {
			write("*******************************************************************************\n");
			write("* NOTE: " + numCached + " actors were not regenerated "
					+ "because they were already up-to-date. *\n");
			write("*******************************************************************************\n");
		}
	}

	/**
	 * Print entities of the given network.
	 * 
	 * @param entities
	 *            a list of entities
	 * @throws OrccException
	 */
	final public void printEntities(Network network) throws OrccException {
		write("Printing entities...\n");
		long t0 = System.currentTimeMillis();

		// creates a list of tasks: each task will print an actor when called
		List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
		for (final Entity entity : network.getEntities()) {
			tasks.add(new Callable<Boolean>() {

				@Override
				public Boolean call() throws OrccException {
					if (isCanceled()) {
						return false;
					}
					return printEntity(entity);
				}

			});
		}

		// executes the tasks
		int numCached = executeTasks(tasks);

		long t1 = System.currentTimeMillis();
		write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");

		if (numCached > 0) {
			write("*******************************************************************************\n");
			write("* NOTE: " + numCached + " entities were not regenerated "
					+ "because they were already up-to-date. *\n");
			write("*******************************************************************************\n");
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
	protected boolean printEntity(Entity entity) throws OrccException {
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
	protected boolean printInstance(Instance instance) throws OrccException {
		return false;
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
					return printInstance(instance);
				}

			});
		}

		// executes the tasks
		int numCached = executeTasks(tasks);

		long t1 = System.currentTimeMillis();
		write("Done in " + ((float) (t1 - t0) / (float) 1000) + "s\n");

		if (numCached > 0) {
			write("*******************************************************************************\n");
			write("* NOTE: " + numCached + " instances were not regenerated "
					+ "because they were already up-to-date. *\n");
			write("*******************************************************************************\n");
		}
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

		String outputFolder;
		Object obj = options.get(OUTPUT_FOLDER);
		if (obj instanceof String) {
			outputFolder = (String) obj;
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
	public void setWriteListener(WriteListener listener) {
		this.listener = listener;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		Map<?, ?> map = context.getArguments();
		String[] args = (String[]) map
				.get(IApplicationContext.APPLICATION_ARGS);

		// create the command line parser
		CommandLineParser parser = new PosixParser();

		// configure the required options
		Options options = new Options();
		Option opt = new Option("p", "project", true, "project name");
		opt.setRequired(true);
		options.addOption(opt);

		opt = new Option("o", "output", true, "output folder");
		opt.setRequired(true);
		options.addOption(opt);

		// add optional options
		options.addOption("c", "classify", false, "classify the given network");

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			String projectName = line.getOptionValue('p');
			String outputFolder = line.getOptionValue('o');
			if (line.getArgs().length != 1) {
				throw new ParseException("expected network name");
			}
			String networkName = line.getArgs()[0];

			Map<String, Object> optionMap = new HashMap<String, Object>();
			optionMap.put(PROJECT, projectName);
			optionMap.put(XDF_FILE, networkName);
			optionMap.put(OUTPUT_FOLDER, outputFolder);
			if (line.hasOption('c')) {
				optionMap.put("net.sf.orcc.backends.classify", true);
			}

			try {
				setOptions(optionMap);
				compileVTL();
				compileXDF();
				exportRuntimeLibrary();
				return IApplication.EXIT_OK;
			} catch (Exception e) {
				System.err.println("Could not run the back-end with \""
						+ networkName + "\"");
				e.printStackTrace();
			}
		} catch (ParseException exp) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(getClass().getSimpleName() + " [options] "
					+ "-p project -o \"output folder\" "
					+ "qualified.name.of.network", options);
		}

		return IApplication.EXIT_OK;
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
		if (listener == null) {
			System.out.print(text);
		} else {
			listener.writeText(text);
		}
	}

}
