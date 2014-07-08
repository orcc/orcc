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
import static net.sf.orcc.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.OrccLaunchConstants.CLASSIFY;
import static net.sf.orcc.OrccLaunchConstants.COMPILE_XDF;
import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.MAPPING;
import static net.sf.orcc.OrccLaunchConstants.MERGE_ACTIONS;
import static net.sf.orcc.OrccLaunchConstants.MERGE_ACTORS;
import static net.sf.orcc.OrccLaunchConstants.NO_LIBRARY_EXPORT;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.TRACES_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;
import static net.sf.orcc.backends.BackendsConstants.ADDITIONAL_TRANSFOS;
import static net.sf.orcc.backends.BackendsConstants.CONVERT_MULTI2MONO;
import static net.sf.orcc.backends.BackendsConstants.NEW_SCHEDULER;
import static net.sf.orcc.backends.BackendsConstants.PROFILE;
import static net.sf.orcc.backends.BackendsConstants.TTA_PROCESSORS_CONFIGURATION;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER_OPTIONS;
import static net.sf.orcc.util.OrccUtil.getFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.NetworkValidator;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.util.EcoreHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.Bundle;

/**
 * This class is an abstract implementation of {@link Backend}. The two entry
 * points of this class are the public methods {@link #compileVTL()} and
 * {@link #compileXDF()} which should NOT be called by back-ends themselves.
 * 
 * <p>
 * The following methods are abstract and must be implemented by back-ends:
 * <ul>
 * <li>{@link #doInitializeOptions()} is called at the end of
 * {@link #setOptions(Map)} to initialize the options of the back-end.</li>
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
	protected boolean balanceMapping;
	protected boolean importXcfFile;
	protected File xcfFile;
	protected int processorNumber;

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
	protected boolean convertMulti2Mono;

	/**
	 * the progress monitor
	 */
	private IProgressMonitor monitor;

	/**
	 * Options of backend execution. Its content can be manipulated with
	 * {@link #getAttribute} and {@link #setAttribute}
	 */
	protected Map<String, Object> options;

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

		boolean compilexdf = getAttribute(COMPILE_XDF, false);

		String orccVersion = "<unknown>";
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		if (bundle != null) {
			orccVersion = bundle.getHeaders().get("Bundle-Version");
		}

		String backendName = getAttribute(BACKEND, "<unknown>");

		OrccLogger.traceln("*********************************************"
				+ "************************************");
		OrccLogger.traceln("* Orcc version : " + orccVersion);
		OrccLogger.traceln("* Backend : " + backendName);
		OrccLogger.traceln("* Project : " + project.getName());
		if (compilexdf) {
			String topNetwork = getAttribute(XDF_FILE, "<unknown>");
			OrccLogger.traceln("* Network : " + topNetwork);
		}
		OrccLogger.traceln("* Output folder : " + path);
		OrccLogger.traceln("*********************************************"
				+ "************************************");

		// If user checked the option "Don't export library", the method
		// extractLibraries() must not be called
		if(!getAttribute(NO_LIBRARY_EXPORT, false)) {
			extractLibraries();
		}

		compileVTL();

		if (compilexdf) {
			compileXDF();
		}

		OrccLogger.traceln("Orcc backend done.");
	}

	final private void compileVTL() {
		// lists actors
		OrccLogger.traceln("Lists actors...");
		List<IFile> vtlFiles = OrccUtil.getAllFiles(OrccUtil.IR_SUFFIX,
				vtlFolders);
		doVtlCodeGeneration(vtlFiles);
	}

	final private void compileXDF() {
		ResourceSet set = new ResourceSetImpl();

		// parses top network
		if (inputFile == null) {
			throw new OrccRuntimeException("The input XDF file does not exist.");
		}
		Network network = EcoreHelper.getEObject(set, inputFile);
		if (isCanceled()) {
			return;
		}
		new NetworkValidator().doSwitch(network);

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
	 * @param dest
	 *            Path of the target file
	 * @param forceOverwrite
	 *            set to true to ensure files will be really wrote. If not, MD5
	 *            sum will be computed to check if files need to be written
	 * @return <code>true</code> if the file has been successfully copied
	 * @deprecated Use methods in {@link FilesManager} instead
	 */
	@Deprecated
	protected boolean copyFileToFilesystem(final String source,
			final String dest, boolean forceOverwrite) {

		assert source != null;
		assert dest != null;
		assert source.startsWith("/");
		int bufferSize = 512;

		InputStream sourceStream = this.getClass().getResourceAsStream(source);

		if (sourceStream == null) {
			OrccLogger.warnln("Unable to find " + source);
			return false;
		}

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
		} else if (!fileOut.isFile()) {
			OrccLogger.warnln(dest + " is not a file path");
			return false;
		} else {
			if (!forceOverwrite) {
				// Compute MD5 for in and out files to check if out file need to
				// be overwritten
				try {
					// Create a new Input stream to keep original one open (used
					// later to write data)
					BufferedInputStream isIn = new BufferedInputStream(this
							.getClass().getResourceAsStream(source));
					FileInputStream isOut = new FileInputStream(fileOut);

					MessageDigest mdIn = MessageDigest.getInstance("MD5");
					MessageDigest mdOut = MessageDigest.getInstance("MD5");

					byte[] b = new byte[bufferSize];
					do {
						isIn.read(b);
						mdIn.update(b);
						isOut.read(b);
						mdOut.update(b);
					} while (isIn.available() > 0);

					isIn.close();
					isOut.close();

					byte[] in = mdIn.digest();
					byte[] out = mdOut.digest();

					if (MessageDigest.isEqual(in, out)) {
						// Target file is the same than input, don't need to
						// write it
						return true;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		// Really write target file
		try {
			BufferedInputStream isIn = new BufferedInputStream(sourceStream);
			FileOutputStream isOut = new FileOutputStream(fileOut);

			byte[] b = new byte[bufferSize];
			int i = isIn.read(b);
			while (i != -1) {
				isOut.write(b, 0, i);
				i = isIn.read(b);
			}
			isOut.close();
			isIn.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	 *            File system folder path
	 * @param forceOverwrite
	 *            set to true to ensure files will be really wrote. If not, MD5
	 *            sum will be computed to check if files need to be written
	 * @return <code>true</code> if the folder has been successfully copied
	 * @deprecated Use methods in {@link FilesManager} instead
	 */
	@Deprecated
	protected boolean copyFolderToFileSystem(String source, String destination,
			boolean forceOverwrite) {
		assert source != null;
		assert destination != null;
		assert source.startsWith("/");

		// Add the last / if needed
		if (!source.endsWith("/")) {
			source = source + "/";
		}
		if (!destination.endsWith("/")) {
			destination = destination + "/";
		}

		File outputDir = new File(destination);
		if (!outputDir.exists()) {
			if (!outputDir.mkdirs()) {
				OrccLogger.warnln("Unable to create " + outputDir + " folder");
				return false;
			}
		} else if (!outputDir.isDirectory()) {
			OrccLogger.warnln(outputDir + " is not a directory.");
			return false;
		}

		try {
			URL dirUrl = this.getClass().getResource(source);

			if (dirUrl.getProtocol().equals("bundleresource")) {
				dirUrl = FileLocator.resolve(dirUrl);
			}

			boolean result = true;

			// Copy is performed from the jar plugin (i.e. orcc has been
			// installed from an update site)
			if (dirUrl.getProtocol().equals("jar")) {
				// Copy folder from a jar
				URI toto = new URI(dirUrl.getFile().split("!")[0]);
				File file = new File(toto);
				JarFile jar = new JarFile(file);

				Enumeration<JarEntry> jarEntries = jar.entries();
				if (jarEntries == null) {
					OrccLogger.warnln("Unable to list content from "
							+ jar.getName() + " file.");
					jar.close();
					return false;
				}

				// "source" value without starting '/' char
				String sourceMinusSlash = source.substring(1);

				for (JarEntry jarEntry : Collections.list(jarEntries)) {

					// Only deal with sub-files of 'source' path
					if (jarEntry.isDirectory()
							|| !jarEntry.getName().startsWith(sourceMinusSlash)) {
						continue;
					}

					String newInPath = "/" + jarEntry.getName();
					String newOutPath = destination
							+ jarEntry.getName().substring(
									sourceMinusSlash.length());
					result &= copyFileToFilesystem(newInPath, newOutPath,
							forceOverwrite);
				}
				jar.close();

				return result;

			}
			// Copy is performed directly from the source (i.e. user is probably
			// a developer)
			else if (dirUrl.getProtocol().equals("file")) {
				// Copy folder from file system
				File[] listDir = new File(dirUrl.getFile()).listFiles();

				for (File dirEntry : listDir) {
					String newInPath = source + dirEntry.getName();
					String newOutPath = destination + dirEntry.getName();

					if (dirEntry.isDirectory()) {
						result &= copyFolderToFileSystem(newInPath, newOutPath,
								forceOverwrite);
					} else {
						result &= copyFileToFilesystem(newInPath, newOutPath,
								forceOverwrite);
					}
				}
				return result;
			} else {
				OrccLogger.warnln("Unknown directory URL format. dirUrl="
						+ dirUrl.toString());
				return false;
			}
		} catch (IOException e) {
			OrccLogger.warnln("IOError : " + e.getMessage());
			return false;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Called by {@link #setOptions(Map)} when options are initialized. This
	 * method may be implemented by backend to set member variables specific to
	 * it.
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
	 * 
	 * @return
	 */
	protected Result extractLibraries() {
		return Result.EMPTY_RESULT;
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
			Resource resource = set.getResource(org.eclipse.emf.common.util.URI
					.createPlatformResourceURI(file.getFullPath().toString(),
							true), true);
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

		int numCached = 0;
		for (final Actor actor : actors) {
			if (printActor(actor)) {
				++numCached;
			}
		}

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
				+ "s");

		if (numCached > 0) {
			OrccLogger.noticeln(numCached + " actors were not regenerated "
					+ "because they were already up-to-date.");
		}
	}

	/**
	 * Print entities of the given network.
	 * 
	 * @param entities
	 *            a list of entities
	 */
	final public void printChildren(Network network) {
		OrccLogger.traceln("Printing children...");
		long t0 = System.currentTimeMillis();

		int numCached = 0;
		for (final Vertex vertex : network.getChildren()) {
			final Instance instance = vertex.getAdapter(Instance.class);
			final Actor actor = vertex.getAdapter(Actor.class);
			if (instance != null) {
				if (printInstance(instance)) {
					++numCached;
				}
			} else if (actor != null) {
				if (printActor(actor)) {
					++numCached;
				}
			}
		}

		long t1 = System.currentTimeMillis();
		OrccLogger.traceln("Done in " + ((float) (t1 - t0) / (float) 1000)
				+ "s");

		if (numCached > 0) {
			OrccLogger.noticeln(numCached + " entities were not regenerated "
					+ "because they were already up-to-date.");
		}
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

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject(getAttribute(PROJECT, ""));

		vtlFolders = OrccUtil.getOutputFolders(project);

		inputFile = getFile(project, getAttribute(XDF_FILE, ""),
				OrccUtil.NETWORK_SUFFIX);

		fifoSize = getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		debug = getAttribute(DEBUG_MODE, true);

		mapping = getAttribute(MAPPING, new HashMap<String, String>());
		balanceMapping = getAttribute("net.sf.orcc.backends.metricMapping",
				false);
		importXcfFile = getAttribute(BackendsConstants.IMPORT_XCF, false);
		if (importXcfFile) {
			xcfFile = new File(getAttribute(BackendsConstants.XCF_FILE, ""));
		}

		processorNumber = Integer.parseInt(getAttribute(
				"net.sf.orcc.backends.processorsNumber", "0"));

		classify = getAttribute(CLASSIFY, false);
		// Merging transformations need the results of classification
		mergeActions = classify && getAttribute(MERGE_ACTIONS, false);
		mergeActors = classify && getAttribute(MERGE_ACTORS, false);

		convertMulti2Mono = getAttribute(CONVERT_MULTI2MONO, false);

		String outputFolder = getAttribute(OUTPUT_FOLDER, "");
		if (outputFolder.isEmpty()) {
			File tempOrccDir = new File(System.getProperty("java.io.tmpdir"),
					"orcc");
			tempOrccDir.mkdir();
			outputFolder = tempOrccDir.getAbsolutePath();
		} else {
			outputFolder = FilesManager.sanitize(outputFolder);
		}

		if (debug) {
			OrccLogger.setLevel(OrccLogger.DEBUG);
			OrccLogger.debugln("Debug mode is enabled");
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
		options.addOption("s", "fifo-size", true,
				"Default size of the FIFO channels");

		options.addOption("c", "classify", false, "Classify the given network");
		options.addOption("smt", "smt-solver", true,
				"Set path to the binary of the SMT solver (Z3 v4.12+)");
		options.addOption("m", "merge", true, "Merge (1) static actions "
				+ "(2) static actors (3) both");
		options.addOption("as", "advanced-scheduler", false, "(C) Use the "
				+ "data-driven/demand-driven strategy for the actor-scheduler");
		options.addOption("m2m", "multi2mono", false,
				"Transform high-level actors with multi-tokens actions"
						+ " in low-level actors with mono-token actions");
		options.addOption("prof", "profile", false, "(C) Enable profiling");
		options.addOption("et", "enable-traces", true,
				"(C) Enable tracing of the FIFOs in the given directory");
		options.addOption(
				"ttapc",
				"tta-processorconf",
				true,
				"(TTA) Predefined configurations for the processors (Standard|Custom|Fast|Huge)");

		// FIXME: choose independently the transformation to apply
		options.addOption("t", "transfo_add", false,
				"Execute additional transformations before generate code");

		try {
			CommandLineParser parser = new PosixParser();

			String cliOpts = StringUtils.join((Object[]) context.getArguments()
					.get(IApplicationContext.APPLICATION_ARGS), " ");
			OrccLogger.traceln("Command line arguments: " + cliOpts);

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
			if (line.hasOption('s')) {
				try {
					int size = Integer.parseInt(line.getOptionValue('s'));
					optionMap.put(FIFO_SIZE, size);
					if (!ValueUtil.isPowerOfTwo(size)) {
						OrccLogger.severeln("FIFO size must be power of two.");
						return IApplication.EXIT_OK;
					}
				} catch (NumberFormatException e) {
					throw new ParseException("Expected integer as FIFO size");
				}
			}

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

			if (line.hasOption("et")) {
				optionMap.put(ENABLE_TRACES, true);
				optionMap.put(TRACES_FOLDER, line.getOptionValue("et"));
			}

			if (line.hasOption("ttapc")) {
				String pc = line.getOptionValue("ttapc");
				if (pc.equals("Standard") || pc.equals("Custom")
						|| pc.equals("Fast") || pc.equals("Huge")) {
					optionMap.put(TTA_PROCESSORS_CONFIGURATION, pc);
				} else {
					OrccLogger
							.warnln("Unknown processors configuration for TTA. Standard configuration will be apply.");
				}
			}

			optionMap.put(NEW_SCHEDULER, line.hasOption("as"));
			optionMap.put(CONVERT_MULTI2MONO, line.hasOption("m2m"));
			optionMap.put(ADDITIONAL_TRANSFOS, line.hasOption('t'));
			optionMap.put(PROFILE, line.hasOption("prof"));

			// Set backend name in options map
			String backend = this.getClass().getName();
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(
							Activator.PLUGIN_ID + ".backends");
			for (IConfigurationElement element : elements) {
				if (backend.equals(element.getAttribute("class"))) {
					backend = element.getAttribute("name");
					break;
				}
			}
			optionMap.put(BACKEND, backend);
			try {

				setOptions(optionMap);
				compile();
				return IApplication.EXIT_OK;

			} catch (OrccRuntimeException e) {

				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					OrccLogger.severeln(e.getMessage());
				}
				OrccLogger.severeln(backend
						+ " backend could not generate code (" + e.getCause()
						+ ")[OrccRuntimeException]");

			} catch (Exception e) {

				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					OrccLogger.severeln(e.getMessage());
				}
				OrccLogger.severeln(backend
						+ " backend could not generate code (" + e.getCause()
						+ ")[Exception]");

				e.printStackTrace();
			}

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
	 */
	final public void transformActors(List<Actor> actors) {
		OrccLogger.traceln("Transforming actors...");
		for (Actor actor : actors) {
			doTransformActor(actor);
		}
	}
}
