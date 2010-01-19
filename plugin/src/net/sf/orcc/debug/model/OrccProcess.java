package net.sf.orcc.debug.model;

import static net.sf.orcc.ui.launching.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_CACHE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DEBUG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_KEEP;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_TRACES;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.ENABLE_CACHE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_STIMULUS;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.KEEP_INTERMEDIATE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.OUTPUT_FOLDER;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.BackendFactory;
import net.sf.orcc.interpreter.InterpreterMain;
import net.sf.orcc.ui.OrccActivator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.osgi.framework.Bundle;

public class OrccProcess extends PlatformObject implements IProcess {

	private String contents;

	private ListenerList list;

	private class OrccMonitor implements IStreamMonitor {

		public OrccMonitor() {
			list = new ListenerList();
		}

		@Override
		public void addListener(IStreamListener listener) {
			list.add(listener);
		}

		@Override
		public String getContents() {
			synchronized (contents) {
				return contents;
			}
		}

		@Override
		public void removeListener(IStreamListener listener) {
			list.remove(listener);
		}

	}

	private class OrccProxy implements IStreamsProxy {

		private IStreamMonitor outputMonitor;

		public OrccProxy() {
			outputMonitor = new OrccMonitor();
		}

		@Override
		public IStreamMonitor getErrorStreamMonitor() {
			return null;
		}

		@Override
		public IStreamMonitor getOutputStreamMonitor() {
			return outputMonitor;
		}

		@Override
		public void write(String input) throws IOException {
		}

	}

	private ILaunch launch;

	private ILaunchConfiguration configuration;

	private boolean terminated;

	private IProgressMonitor monitor;

	private int value;

	private String[] cmdLine;

	private Process process;

	private IStreamsProxy proxy;

	public OrccProcess(ILaunch launch, ILaunchConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		this.configuration = configuration;
		this.launch = launch;
		this.monitor = monitor;
		contents = "";
		proxy = new OrccProxy();

		try {
			cmdLine = createCmdLine();
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					"I/O error", e);
			throw new CoreException(status);
		}
	}

	/**
	 * Calls one of the backends.
	 * 
	 * @param configuration
	 *            The configuration.
	 * @throws CoreException
	 */
	private void launchBackend(ILaunchConfiguration configuration)
			throws CoreException {
		String backend = configuration.getAttribute(BACKEND, "");

		String inputFile = configuration.getAttribute(INPUT_FILE, "");
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");

		String file = new File(inputFile).getName();
		String name = outputFolder + File.separator + file;
		int fifoSize = configuration.getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		try {
			BackendFactory factory = BackendFactory.getInstance();
			factory.runBackend(backend, name, fifoSize);
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					backend + " backend could not generate code", e);
			throw new CoreException(status);
		}
	}

	@Override
	public boolean canTerminate() {
		return !terminated;
	}

	/**
	 * Calls the Orcc RVC-CAL interpreter for simulation.
	 * 
	 * @param option
	 *            Selects debugger or simulator.
	 * @param launch
	 *            The current launch context.
	 * @param monitor
	 *            The progress monitor.
	 * @param configuration
	 *            The configuration.
	 * @throws CoreException
	 */
	private void launchInterpreter(String option)
			throws CoreException {
		// Get configuration options
		String inputFile = configuration.getAttribute(INPUT_FILE, "");
		String inputStimulus = configuration.getAttribute(INPUT_STIMULUS, "");
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");
		String file = new File(inputFile).getName();
		String filename = outputFolder + File.separator + file;
		int fifoSize = configuration.getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		boolean enableTraces = configuration.getAttribute(ENABLE_TRACES,
				DEFAULT_TRACES);
		try {
			// Get interpreter instance and configure it
			InterpreterMain interpreter = new InterpreterMain();
			interpreter.configSystem(this, monitor);
			interpreter.configNetwork(filename, fifoSize, inputStimulus,
					enableTraces);
			if (option.equals("debugger")) {
				// Add the DebugTarget as a listener of interpreter
				OrccDebugTarget target = new OrccDebugTarget(launch, this, interpreter);
				launch.addDebugTarget(target);
				interpreter.addPropertyChangeListener(target);
				// Start interpreter thread...
				interpreter.start();
				// ...wait for the end of inputStimulus consumption
				interpreter.join();
			}else {
				// No debug thread to be used : call directly the simulator
				interpreter.simulate();
			}
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					"simulator error", e);
			throw new CoreException(status);
		}
	}

	/**
	 * Checks that the URL that points to Orcc frontend executable is not null,
	 * and throws a CoreException otherwise.
	 * 
	 * @param url
	 * @throws CoreException
	 */
	private void checkUrl(URL url) throws CoreException {
		if (url == null) {
			String detail1 = "This may be caused by a missing cal2ir.d.byte or "
					+ "cal2ir.native file in the fragment matching your "
					+ "platform.";
			IStatus s1 = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					detail1);

			String detail2 = "Another cause of this problem might be that no "
					+ "Orcc frontend is available for your platform.";
			IStatus s2 = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					detail2);

			IStatus status = new MultiStatus(OrccActivator.PLUGIN_ID, 0,
					new IStatus[] { s1, s2 },
					"No executable of Orcc frontend available!", null);
			throw new CoreException(status);
		}
	}

	private String[] createCmdLine() throws CoreException, IOException {
		List<String> cmdList = new ArrayList<String>();
		String exeName;
		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
			exeName = "cal2ir.d.byte";
		} else {
			exeName = "cal2ir.native";
		}

		Bundle bundle = OrccActivator.getDefault().getBundle();
		IPath path = new Path("frontend/" + exeName);

		URL url = FileLocator.find(bundle, path, null);
		checkUrl(url);
		url = FileLocator.toFileURL(url);

		// get an OS-specific executable name
		path = new Path(url.getPath());
		String exe = path.toOSString();

		File file = new File(exe);
		file.setExecutable(true);

		String networkName = configuration.getAttribute(INPUT_FILE, "");
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");

		cmdList.add(quoteFile(exe));
		cmdList.add("-i");
		cmdList.add(quoteFile(networkName));
		cmdList.add("-o");
		cmdList.add(quoteFile(outputFolder));

		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
			cmdList.add("--debug");
		}
		if (configuration.getAttribute(ENABLE_CACHE, DEFAULT_CACHE)) {
			cmdList.add("--cache");
		}
		if (configuration.getAttribute(KEEP_INTERMEDIATE, DEFAULT_KEEP)) {
			cmdList.add("--keep");
		}
		if (configuration.getAttribute(DOT_CFG, DEFAULT_DOT_CFG)) {
			cmdList.add("--dot-cfg");
		}

		return cmdList.toArray(new String[] {});
	}

	@Override
	public String getAttribute(String key) {
		return null;
	}

	@Override
	public int getExitValue() throws DebugException {
		return value;
	}

	@Override
	public String getLabel() {
		return configuration.getName();
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return proxy;
	}

	@Override
	public boolean isTerminated() {
		return terminated;
	}

	/**
	 * Quote file if file contains spaces.
	 * 
	 * @param file
	 *            The file to check
	 * @return file or a new string that contains file between double quotes.
	 */
	private String quoteFile(String file) {
		if (file.contains(" ")) {
			return '"' + file + '"';
		} else {
			return file;
		}
	}

	@Override
	public void setAttribute(String key, String value) {
	}

	@Override
	public void terminate() throws DebugException {

	}

	public void start(String option) throws CoreException {
		monitor.beginTask("Compiling dataflow program", 2);
		monitor.subTask("Launching frontend...");
		process = DebugPlugin.exec(cmdLine, null);
		try {
			value = process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		write("Orcc frontend exit code: " + value + "\n");
		if (value == 0) {
			if (option.equals("backend")) {
				monitor.subTask("Launching backend...");
				write("\n");
				write("*********************************************"
						+ "**********************************\n");
				write("Launching Orcc backend...\n");
				launchBackend(configuration);
				write("Orcc backend done.");
			} else {
				monitor.subTask("Launching simulator...");
				write("\n");
				write("*********************************************"
						+ "**********************************\n");
				write("Launching Orcc simulator...\n");
				launchInterpreter(option);
				write("Orcc simulation done.");
			}
		}

		terminated = true;

		DebugEvent event = new DebugEvent(this, DebugEvent.TERMINATE);
		DebugEvent[] events = { event };
		DebugPlugin.getDefault().fireDebugEventSet(events);
	}

	public void write(String msg) {
		synchronized (contents) {
			contents += msg;
		}

		for (Object listener : list.getListeners()) {
			((IStreamListener) listener).streamAppended(msg, proxy
					.getOutputStreamMonitor());
		}
	}

}
