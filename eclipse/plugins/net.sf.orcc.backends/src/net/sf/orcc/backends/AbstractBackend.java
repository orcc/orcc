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
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_DEBUG;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfVisitor;
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
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.Bundle;

/**
 * <p>
 * This class is an abstract implementation of {@link Backend}. It supports
 * standard back-ends to generate code from a network and its (instantiated or
 * not children). It also can be used to produce a Video Tool Library (VTL) from
 * CAL projects. To do so, simply use {@link #AbstractBackend(boolean)}
 * signature in the constructor of the concrete back-end class. signature.
 * </p>
 * 
 * <p>
 * When a back-end is launched, the method {@link #setOptions(Map)} is called to
 * configure back-end options, then {@link #compile(IProgressMonitor)} calls all
 * the methods required to transform the IR, validate the result, generate the
 * target code and extract the required libraries.
 * </p>
 * 
 * <p>
 * The following methods may be extended by back-ends depending on their needs.
 * </p>
 * 
 * <ul>
 * <li>{@link #doInitializeOptions()} is called at the end of
 * {@link #setOptions(Map)} to initialize some member variables. It is also used
 * to fill transformations maps {@link #networkTransfos} and
 * {@link #childrenTransfos}.</li>
 * </ul>
 * <p>
 * Then the full compilation is run:
 * </p>
 * <ol>
 * <li>{@link #doValidate(Network)} checks some constraints on the given network
 * </li>
 * <li>{@link #beforeGeneration(Network)} can be used to perform some additional operations</li>
 * <li>{@link #doGenerateNetwork(Network)} produces code from the given network</li>
 * <li>{@link #doAdditionalGeneration(Network)} can be used to generate other
 * files from the given network</li>
 * <li>{@link #beforeGeneration(Actor)} or {@link #beforeGeneration(Instance)}</li>
 * <li>{@link #doGenerateActor(Actor)} or {@link #doGenerateInstance(Instance)}</li>
 * <li>{@link #doAdditionalGeneration(Actor)} or
 * {@link #doAdditionalGeneration(Instance)}</li>
 * </ol>
 * 
 * <p>
 * The following methods were used in previous versions of Orcc. They will be
 * removed and shouldn't be used anymore. Documentation on each method indicates
 * the new alternative to use.
 * <ul>
 * <li>{@link #doTransformActor(Actor)}</li>
 * <li>{@link #transformActors(List)}</li>
 * <li>{@link #doXdfCodeGeneration(Network)}</li>
 * <li>{@link #printActor(Actor)}</li>
 * <li>{@link #printActors(List)}</li>
 * <li>{@link #printChildren(Network)}</li>
 * <li>{@link #printInstance(Instance)}</li>
 * </ul>
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 * 
 */
public abstract class AbstractBackend implements Backend, IApplication {

	/**
	 * Indicates that this is a VTL back-end or not (i.e. a standard back-end).
	 */
	private boolean isVTLBackend;

	/**
	 * Indicates that the back-end has been launched in debug mode or not
	 */
	protected boolean debug;

	/**
	 * Fifo size used in back-end.
	 */
	protected int fifoSize;

	/**
	 * Contains mapping information configured before back-end launch. Key is
	 * the name of an instance, value is a core identifier.
	 */
	protected Map<String, String> mapping;

	// FIXME: XCF files are used only in TTA back-end. These variables should be
	// moved in TTABackend class
	protected boolean importXcfFile;
	protected File xcfFile;

	/**
	 * List of transformations to apply on each network
	 */
	protected List<DfVisitor<?>> networkTransfos;
	/**
	 * List of transformations to apply on each child.
	 */
	protected List<DfVisitor<?>> childrenTransfos;

	/**
	 * Options of back-end execution. Its content can be manipulated with
	 * {@link #getOption} methods.
	 */
	private Map<String, Object> options;

	// Other options
	protected boolean classify;
	protected boolean mergeActions;
	protected boolean mergeActors;
	protected boolean convertMulti2Mono;

	/**
	 * Path where output files will be written. TODO: Rename the variable to
	 * something more explicit (outputPath, or something else)
	 */
	protected String path;

	/**
	 * Represents the project where application to build is located
	 */
	protected IProject project;

	/**
	 * Common ResourceSet can be used by concrete back-ends if needed
	 */
	protected ResourceSet currentResourceSet;
	/**
	 * the progress monitor
	 */
	private IProgressMonitor monitor;

	/**
	 * Construct a new standard back-end.
	 */
	public AbstractBackend() {
		this(false);
	}

	/**
	 * Construct a new back-end instance.
	 * 
	 * @param isVTLBackend
	 *            Set to true if this back-end will generate a complete VTL.
	 */
	public AbstractBackend(boolean isVTLBackend) {
		networkTransfos = new ArrayList<DfVisitor<?>>();
		childrenTransfos = new ArrayList<DfVisitor<?>>();

		this.isVTLBackend = isVTLBackend;
	}

	/**
	 * Calculate the time elapsed between the given <em>t0</em> and the current
	 * timestamp.
	 * 
	 * @return The number of seconds, as a float
	 */
	final private float getDuration(long t0) {
		return (float) (System.currentTimeMillis() - t0) / 1000;
	}

	@Override
	public void compile(IProgressMonitor progressMonitor) {

		// New ResourceSet for a new compilation
		currentResourceSet = new ResourceSetImpl();

		// Initialize the monitor. Can be used to stop the back-end
		// execution and provide feedback to user
		monitor = progressMonitor;

		// Does code generation at network level should happen ?
		boolean compileXdf = getOption(COMPILE_XDF, false);

		// -----------------------------------------------------
		// Configure the console log header
		// -----------------------------------------------------
		String orccVersion = "<unknown>";
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		if (bundle != null) {
			orccVersion = bundle.getHeaders().get("Bundle-Version");
		}

		String backendName = getOption(BACKEND, "<unknown>");

		OrccLogger.traceln("*********************************************"
				+ "************************************");
		OrccLogger.traceln("* Orcc version : " + orccVersion);
		OrccLogger.traceln("* Backend : " + backendName);
		OrccLogger.traceln("* Project : " + project.getName());
		if (compileXdf) {
			String topNetwork = getOption(XDF_FILE, "<unknown>");
			OrccLogger.traceln("* Network : " + topNetwork);
		}
		OrccLogger.traceln("* Output folder : " + path);
		OrccLogger.traceln("*********************************************"
				+ "************************************");

		// -----------------------------------------------------
		// Libraries files export
		// -----------------------------------------------------
		// If user checked the option "Don't export library", the method
		// extractLibraries() must not be called
		if (!getOption(NO_LIBRARY_EXPORT, false)) {
			stopIfRequested();
			final long t0 = System.currentTimeMillis();
			final Result result = doLibrariesExtraction();
			if(!result.isEmpty()) {
				OrccLogger.traceln("Library export done in " + getDuration(t0) + "s");
			}
		}

		// -----------------------------------------------------
		// Network level code generation
		// -----------------------------------------------------
		final IFile xdfFile = getFile(project, getOption(XDF_FILE, ""),
				OrccUtil.NETWORK_SUFFIX);
		final Network network = EcoreHelper.getEObject(currentResourceSet,
				xdfFile);

		if (compileXdf) {
			if (xdfFile == null) {
				throw new OrccRuntimeException(
						"The input XDF file does not exists.");
			} else if (network == null) {
				throw new OrccRuntimeException(
						"The input file seems to not contains any network");
			}

			if (!networkTransfos.isEmpty()) {
				stopIfRequested();
				OrccLogger.traceln("Network transformations");
				final long t0 = System.currentTimeMillis();
				applyTransformations(network, networkTransfos);
				OrccLogger.traceln("Done in " + getDuration(t0) + "s");
			}

			stopIfRequested();
			beforeGeneration(network);

			stopIfRequested();
			OrccLogger.traceln("Network validation");
			doValidate(network);

			stopIfRequested();
			OrccLogger.traceln("Network generation");
			final long t0 = System.currentTimeMillis();
			final Result result = doGenerateNetwork(network);
			result.merge(doAdditionalGeneration(network));
			OrccLogger.traceln("Done in " + getDuration(t0) + "s. " + result);

			// For backward compatibility
			stopIfRequested();
			doXdfCodeGeneration(network);
		}

		// -----------------------------------------------------
		// VTL back-ends code generation
		// -----------------------------------------------------
		if (isVTLBackend) {
			stopIfRequested();
			OrccLogger.traceln("Compute the list of actors to generate");
			List<IFolder> projectsFolders = OrccUtil.getOutputFolders(project);
			List<IFile> irFiles = OrccUtil.getAllFiles(OrccUtil.IR_SUFFIX,
					projectsFolders);

			OrccLogger.traceln("Parsing " + irFiles.size() + " IR files");
			List<Actor> actors = new ArrayList<Actor>();
			for (IFile file : irFiles) {
				final EObject eObject = EcoreHelper.getEObject(
						currentResourceSet, file);
				// do not add units
				if (eObject instanceof Actor) {
					actors.add((Actor) eObject);
				}
			}
			OrccLogger.traceln(actors.size()  + " actors will be added to the VTL (other IR files are units)");

			if (!childrenTransfos.isEmpty()) {
				stopIfRequested();
				OrccLogger.traceln("Actors transformations");
				final long t0 = System.currentTimeMillis();
				applyTransformations(actors, childrenTransfos);
				OrccLogger.traceln("Done in " + getDuration(t0) + "s");
			}

			stopIfRequested();
			OrccLogger.traceln("Actors generation");
			final long t0 = System.currentTimeMillis();
			final Result result = Result.newInstance();
			for (final Actor actor : actors) {
				beforeGeneration(actor);
				result.merge(doGenerateActor(actor));
				result.merge(doAdditionalGeneration(actor));
			}
			OrccLogger.traceln("Done in " + getDuration(t0) + "s. " + result);
		}
		// -----------------------------------------------------
		// Standard back-ends children level code generation
		// -----------------------------------------------------
		else {

			if (!childrenTransfos.isEmpty()) {
				stopIfRequested();
				OrccLogger.traceln("Children transformations");
				final long t0 = System.currentTimeMillis();
				applyTransformations(network.getAllActors(), childrenTransfos);
				OrccLogger.traceln("Done in " + getDuration(t0) + "s");
			}

			stopIfRequested();
			OrccLogger.traceln("Children generation");
			final long t0 = System.currentTimeMillis();
			final Result result = Result.newInstance();
			for (final Vertex vertex : network.getChildren()) {
				stopIfRequested();
				final Instance instance = vertex.getAdapter(Instance.class);
				final Actor actor = vertex.getAdapter(Actor.class);
				if (instance != null) {
					beforeGeneration(instance);
					result.merge(doGenerateInstance(instance));
					result.merge(doAdditionalGeneration(instance));

					// For backward compatibility only
					printInstance(instance);
				} else if (actor != null) {
					beforeGeneration(actor);
					result.merge(doGenerateActor(actor));
					result.merge(doAdditionalGeneration(instance));

					// For backward compatibility only
					printActor(actor);
				}
			}

			OrccLogger.traceln("Done in " + getDuration(t0) + "s. " + result);
		}

		OrccLogger.traceln("Orcc backend done.");
	}

	/**
	 * Validate the network. The default implementation calls
	 * {@link Validator#checkTopLevel(Network)} and
	 * {@link Validator#checkMinimalFifoSize(Network, int)}
	 * 
	 * @param network
	 */
	protected void doValidate(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		new NetworkValidator().doSwitch(network);
	}

	/**
	 * Called by {@link #setOptions(Map)} when options are initialized. This
	 * method must be implemented by back-end to set specific member variables
	 * and additional back-end specific options.
	 */
	abstract protected void doInitializeOptions();

	/**
	 * Do not use or override this method anymore. Instead, fill the
	 * {@link #childrenTransfos} list with all transformations to apply at Actor
	 * level.
	 * 
	 * @deprecated
	 */
	@Deprecated
	protected void doTransformActor(Actor actor) {
	}

	/**
	 * Do not use or override this method anymore. Instead, fill the
	 * {@link #childrenTransfos} list with all transformations to apply at Actor
	 * level.
	 * 
	 * @deprecated
	 */
	@Deprecated
	final public void transformActors(List<Actor> actors) {
		OrccLogger.traceln("Transforming actors...");
		for (Actor actor : actors) {
			doTransformActor(actor);
		}
	}

	/**
	 * Do not use or override this method anymore. Instead, extends
	 * {@link #doGenerateNetwork(Network)} to print code from a Network, and/or
	 * {@link #doGenerateActor(Actor)} or {@link #doGenerateInstance(Instance)}
	 * to print code from its children.
	 * 
	 * @see #doAdditionalGeneration(Network)
	 * @see #doAdditionalGeneration(Instance)
	 * @see #doAdditionalGeneration(Actor)
	 * @deprecated
	 */
	@Deprecated
	protected void doXdfCodeGeneration(Network network) {
	}

	/**
	 * Callback called before network generation, just after network
	 * transformations. It can be used to perform additional transformations,
	 * validations, or other operations at network level.
	 * 
	 * @param network
	 */
	protected void beforeGeneration(final Network network) {
		// Does nothing by default
	}

	/**
	 * This method may be implemented by subclasses to do the code generation at
	 * network level.
	 * 
	 * @param network
	 *            a network
	 * @return The generation Result object
	 */
	protected Result doGenerateNetwork(Network network) {
		return Result.newInstance();
	}

	/**
	 * Can be overridden in back-ends to generates files at network level, but
	 * not directly related to the network itself.
	 * 
	 * @param network
	 * @return The generation Result object
	 */
	protected Result doAdditionalGeneration(final Network network) {
		return Result.newInstance();
	}

	/**
	 * <p>
	 * This method may be implemented in concrete back-ends if some runtime
	 * libraries files have to be extracted while code generation.
	 * </p>
	 * 
	 * <p>
	 * This method is called from {@link #compile()} only if user didn't check
	 * the back-end option "Don't export library".
	 * </p>
	 * 
	 * @return A Result instance representing the number of files really written
	 *         to the disk and the number cached (not written because already
	 *         up-to-date)
	 */
	protected Result doLibrariesExtraction() {
		return Result.newInstance();
	}

	/**
	 * Apply given <em>transformations</em> on given <em>objects</em>.
	 * 
	 * @param objects
	 * @param transformations
	 */
	final private <T extends EObject> void applyTransformations(
			Iterable<T> objects, Iterable<DfVisitor<?>> transformations) {
		for (final T object : objects) {
			applyTransformations(object, transformations);
		}
	}

	/**
	 * Apply given <em>transformations</em> on given <em>object</em>.
	 * 
	 * @param object
	 * @param transformations
	 */
	final private void applyTransformations(EObject object,
			Iterable<DfVisitor<?>> transformations) {
		for (final DfVisitor<?> transformation : transformations) {
			transformation.doSwitch(object);
		}
	}

	/**
	 * Returns the boolean-valued option with the given name. Returns the given
	 * default value if the option is undefined.
	 * 
	 * @param optionName
	 *            the name of the option
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the fallback default value
	 */
	final public boolean getOption(String optionName, boolean defaultValue) {
		Object obj = options.get(optionName);
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Returns the integer-valued option with the given name. Returns the given
	 * default value if the option is undefined.
	 * 
	 * @param optionName
	 *            the name of the option
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the fallback default value
	 */
	final public int getOption(String optionName, int defaultValue) {
		Object obj = options.get(optionName);
		if (obj instanceof Integer) {
			return (Integer) obj;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Returns the map-valued option with the given name. Returns the given
	 * default value if the option is undefined.
	 * 
	 * @param optionName
	 *            the name of the option
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the fallback default value
	 */
	@SuppressWarnings("unchecked")
	final public Map<String, String> getOption(String optionName,
			Map<String, String> defaultValue) {
		Object obj = options.get(optionName);
		if (obj instanceof Map<?, ?>) {
			return (Map<String, String>) obj;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Returns the string-valued option with the given name. Returns the given
	 * default value if the option is undefined.
	 * 
	 * @param optionName
	 *            the name of the option
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the fallback default value
	 */
	final public String getOption(String optionName, String defaultValue) {
		Object obj = options.get(optionName);
		if (obj instanceof String) {
			return (String) obj;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Returns a map containing the back-end options in this launch
	 * configuration. Returns an empty map if the back-end configuration has no
	 * options.
	 * 
	 * @return a map of options keys and values.
	 */
	final public Map<String, Object> getOptions() {
		return options;
	}

	/**
	 * Check the current ProgressMonitor for cancellation, and throws a
	 * OperationCanceledException if needed. This will simply stop the back-end
	 * execution.
	 */
	private void stopIfRequested() {
		if (monitor != null) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
	}

	/**
	 * Do not use or override this method anymore. Instead, extends
	 * {@link #doGenerateActor(Actor)}.
	 * 
	 * @see #doAdditionalGeneration(Actor)
	 * @deprecated
	 */
	@Deprecated
	protected boolean printActor(Actor actor) {
		return false;
	}

	/**
	 * Callback called before actor generation, just after actors
	 * transformations. It can be used to perform additional transformations,
	 * validations, or other operations at actor level.
	 * 
	 * @param actor
	 */
	protected void beforeGeneration(final Actor actor) {
		// Does nothing by default
	}

	/**
	 * This method may be implemented by subclasses to do the code generation at
	 * actor level. It will automatically be called at code generation time by
	 * VTL back-ends and by standard back-ends (if network has been fully
	 * instantiated).
	 * 
	 * @param actor
	 *            an actor
	 * @see #doAdditionalGeneration(Actor)
	 * @return The generation Result object
	 */
	protected Result doGenerateActor(final Actor actor) {
		return Result.newInstance();
	}

	/**
	 * This method may be implemented by subclasses to do additional generation
	 * at actor level.
	 * 
	 * @param actor
	 *            an actor
	 * @return The generation Result object
	 */
	protected Result doAdditionalGeneration(final Actor actor) {
		return Result.newInstance();
	}

	/**
	 * Do not use or override this method anymore. Instead, extends
	 * {@link #doGenerateActor(Actor)} and/or
	 * {@link #doGenerateInstance(Instance)} .
	 * 
	 * @see #doAdditionalGeneration(Actor)
	 * @see #doAdditionalGeneration(Instance)
	 * @deprecated
	 */
	@Deprecated
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
	 * Do not use or override this method anymore. Instead, extends
	 * {@link #doGenerateInstance(Instance)} .
	 * 
	 * @see #doAdditionalGeneration(Actor)
	 * @see #doAdditionalGeneration(Instance)
	 * @deprecated
	 */
	@Deprecated
	protected boolean printInstance(Instance instance) {
		return false;
	}

	/**
	 * Callback called before instance generation, just after instances
	 * transformations. It can be used to perform additional transformations,
	 * validations, or other operations at instance level.
	 * 
	 * @param instance
	 */
	protected void beforeGeneration(final Instance instance) {
		// Does nothing by default
	}

	/**
	 * This method may be implemented by subclasses to do the code generation at
	 * instance level. It will automatically be called at code generation time
	 * by standard back-ends.
	 * 
	 * @param instance
	 *            an instance
	 * @see #doAdditionalGeneration(Actor)
	 * @return The generation Result object
	 */

	protected Result doGenerateInstance(final Instance instance) {
		return Result.newInstance();
	}

	/**
	 * This method may be implemented by subclasses to do additional generation
	 * at instance level.
	 * 
	 * @param instance
	 *            an instance
	 * @return The generation Result object
	 */
	protected Result doAdditionalGeneration(final Instance instance) {
		return Result.newInstance();
	}

	/**
	 * Print command line documentation on options
	 * 
	 * @param context
	 * @param options
	 * @param parserMsg
	 */
	private void printUsage(Options options, String parserMsg) {

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
	final public void setOptions(Map<String, Object> options) {
		this.options = options;

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject(getOption(PROJECT, ""));

		fifoSize = getOption(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		debug = getOption(DEBUG_MODE, DEFAULT_DEBUG);

		mapping = getOption(MAPPING, new HashMap<String, String>());
		importXcfFile = getOption(BackendsConstants.IMPORT_XCF, false);
		if (importXcfFile) {
			xcfFile = new File(getOption(BackendsConstants.XCF_FILE, ""));
		}

		classify = getOption(CLASSIFY, false);
		// Merging transformations need the results of classification
		mergeActions = classify && getOption(MERGE_ACTIONS, false);
		mergeActors = classify && getOption(MERGE_ACTORS, false);

		convertMulti2Mono = getOption(CONVERT_MULTI2MONO, false);

		String outputFolder = getOption(OUTPUT_FOLDER, "");
		if (outputFolder.isEmpty()) {
			File tempOrccDir = new File(System.getProperty("java.io.tmpdir"),
					"orcc");
			tempOrccDir.mkdir();
			outputFolder = tempOrccDir.getAbsolutePath();
		} else {
			outputFolder = FilesManager.sanitize(outputFolder);
		}
		// set output path
		path = new File(outputFolder).getAbsolutePath();

		if (debug) {
			OrccLogger.setLevel(OrccLogger.DEBUG);
			OrccLogger.debugln("Debug mode is enabled");
		}

		// To avoid applying many times the same transformations when the same
		// back-end is re-run, we have to clear transfos lists. They will be
		// filled in doInitializeOptions()
		networkTransfos.clear();
		childrenTransfos.clear();
		doInitializeOptions();
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

			// Set back-end name in options map
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
				compile(new NullProgressMonitor());
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
			printUsage(options, uoe.getLocalizedMessage());
		} catch (ParseException exp) {
			printUsage(options, exp.getLocalizedMessage());
		}
		return IApplication.EXIT_RELAUNCH;
	}

	@Override
	public void stop() {
		OrccLogger.traceln("The ");
		monitor.setCanceled(true);
	}
}
