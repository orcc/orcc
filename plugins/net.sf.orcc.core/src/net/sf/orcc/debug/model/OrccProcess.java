/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.debug.model;

import static net.sf.orcc.OrccLaunchConstants.ACTIVATE_BACKEND;
import static net.sf.orcc.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_BACKEND;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_DEBUG;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_DOT_CFG;
import static net.sf.orcc.OrccLaunchConstants.DEFAULT_KEEP;
import static net.sf.orcc.OrccLaunchConstants.DOT_CFG;
import static net.sf.orcc.OrccLaunchConstants.KEEP_INTERMEDIATE;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;
import static net.sf.orcc.OrccLaunchConstants.VTL_FOLDER;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.network.Network;
import net.sf.orcc.plugins.backends.BackendFactory;
import net.sf.orcc.plugins.simulators.SimulatorFactory;
import net.sf.orcc.ui.OrccActivator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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

/**
 * This class defines an implementation of {@link IProcess} to launch the
 * front-end and back-end of Orcc.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccProcess extends PlatformObject implements IProcess {

	/**
	 * This class defines an implementation of stream monitor.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class OrccMonitor implements IStreamMonitor {

		private ListenerList list;

		/**
		 * Creates a new monitor.
		 */
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

		/**
		 * Writes the given text to the contents watched by this monitor.
		 * 
		 * @param text
		 *            a string
		 */
		private void write(String text) {
			synchronized (contents) {
				contents += text;
			}

			for (Object listener : list.getListeners()) {
				((IStreamListener) listener).streamAppended(text, this);
			}
		}

	}

	private class OrccProxy implements IStreamsProxy {

		private IStreamMonitor errorMonitor;

		private IStreamMonitor outputMonitor;

		public OrccProxy() {
			errorMonitor = new OrccMonitor();
			outputMonitor = new OrccMonitor();
		}

		@Override
		public IStreamMonitor getErrorStreamMonitor() {
			return errorMonitor;
		}

		@Override
		public IStreamMonitor getOutputStreamMonitor() {
			return outputMonitor;
		}

		@Override
		public void write(String input) throws IOException {
			// nothing to do
		}

	}

	/**
	 * This class defines a thread that reads from an input stream.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ReadingThread extends Thread {

		private InputStream in;

		private OrccMonitor monitor;

		/**
		 * Creates a reading thread on the given input stream, and with the
		 * given monitor to write to.
		 * 
		 * @param in
		 *            an input stream
		 * @param monitor
		 *            a monitor to write to
		 */
		public ReadingThread(InputStream in, OrccMonitor monitor) {
			this.in = in;
			this.monitor = monitor;
		}

		@Override
		public void run() {
			byte[] bytes = new byte[8192];
			int numRead = 0;
			while (numRead >= 0) {
				try {
					numRead = in.read(bytes);
					if (numRead > 0) {
						monitor.write(new String(bytes, 0, numRead));
					}
				} catch (IOException e) {
					DebugPlugin.log(e);
				}

				try {
					// just give up CPU to maintain UI responsiveness.
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}

			try {
				in.close();
			} catch (IOException e) {
				DebugPlugin.log(e);
			}
		}
	}

	private ILaunchConfiguration configuration;

	private String contents;

	private ILaunch launch;

	private IProgressMonitor monitor;

	private Process process;

	private IStreamsProxy proxy;

	private boolean terminated;

	private int value;

	public OrccProcess(ILaunch launch, ILaunchConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		this.configuration = configuration;
		this.launch = launch;
		this.monitor = monitor;
		contents = "";
		proxy = new OrccProxy();
	}

	@Override
	public boolean canTerminate() {
		return !terminated;
	}

	private String[] createCmdLine() throws CoreException, IOException {
		List<String> cmdList = new ArrayList<String>();

		// get an OS-specific executable name
		URL url = getFrontEndURL();
		IPath path = new Path(url.getPath());
		String exe = path.toOSString();

		File file = new File(exe);
		file.setExecutable(true);

		String inputFile = configuration.getAttribute(VTL_FOLDER, "");
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");

		cmdList.add(quoteFile(exe));
		cmdList.add("-i");
		cmdList.add(quoteFile(inputFile));
		cmdList.add("-o");
		cmdList.add(quoteFile(outputFolder));

		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
			cmdList.add("--debug");
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

	/**
	 * Returns a file:/ URL where the front-end executable can be found.
	 * 
	 * @return a file:/ URL where the front-end executable can be found
	 * @throws CoreException
	 *             if the executable can not be found
	 * @throws IOException
	 *             if the executable can be found but not read
	 */
	private URL getFrontEndURL() throws CoreException, IOException {
		String exeName;
		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
			exeName = "cal2ir.d.byte";
		} else {
			exeName = "cal2ir.native";
		}

		String os = Platform.getOS();
		String arch = Platform.getOSArch();

		Bundle bundle = OrccActivator.getDefault().getBundle();
		IPath path = new Path("frontend/" + os + "." + arch + "/" + exeName);

		URL url = FileLocator.find(bundle, path, null);
		if (url == null) {
			String detail1 = "It seems that no front-end could be found "
					+ "for your platform \"" + os + "." + arch + "\".";
			IStatus s1 = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					detail1);

			IStatus status = new MultiStatus(OrccActivator.PLUGIN_ID, 0,
					new IStatus[] { s1 },
					"No executable of Orcc frontend available!", null);
			throw new CoreException(status);
		}

		return FileLocator.toFileURL(url);
	}

	@Override
	public String getLabel() {
		return configuration.getName();
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	public IProgressMonitor getProgressMonitor() {
		return monitor;
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
	 * Calls one of the backends.
	 * 
	 * @throws CoreException
	 */
	private void launchBackend() throws CoreException {
		String backend = configuration.getAttribute(BACKEND, "");
		try {
			BackendFactory factory = BackendFactory.getInstance();
			factory.runBackend(this, configuration);
		} catch (Exception e) {
			// clear actor pool because it might not have been done if we got an
			// error too soon
			Network.clearActorPool();

			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					backend + " backend could not generate code", e);
			throw new CoreException(status);
		}
	}

	private void launchFrontend() throws CoreException {
		monitor.beginTask("Compiling dataflow program", 2);
		monitor.subTask("Launching frontend...");

		try {
			String[] cmdLine = createCmdLine();
			process = DebugPlugin.exec(cmdLine, null);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					"I/O error", e);
			throw new CoreException(status);
		}

		// read from process
		ReadingThread t1 = new ReadingThread(process.getErrorStream(),
				(OrccMonitor) proxy.getErrorStreamMonitor());
		ReadingThread t2 = new ReadingThread(process.getInputStream(),
				(OrccMonitor) proxy.getOutputStreamMonitor());
		t1.start();
		t2.start();

		// wait for it to finish
		try {
			value = process.waitFor();

			// wait for reading threads to finish
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		write("Orcc frontend exit code: " + value + "\n");
	}

	/**
	 * Calls one of the simulators.
	 * 
	 * @throws CoreException
	 */
	private void launchSimulator(String option) throws CoreException {
		String simulator = configuration.getAttribute(SIMULATOR, "");
		try {
			SimulatorFactory factory = SimulatorFactory.getInstance();
			factory.runSimulator(this, launch, configuration, option);
		} catch (Exception e) {
			// clear actor pool because it might not have been done if we got an
			// error too soon
			Network.clearActorPool();

			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					simulator + " simulation error", e);
			throw new CoreException(status);
		}
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

	/**
	 * Starts this process with the given option
	 * 
	 * @param option
	 *            "backend" and/or ("simulator" or "debugger")
	 * @throws CoreException
	 */
	public void start(String option) throws CoreException {
		launchFrontend();

		try {
			if (value == 0) {
				if ((option.equals("backend"))
						|| configuration.getAttribute(ACTIVATE_BACKEND,
								DEFAULT_BACKEND)) {
					monitor.subTask("Launching backend...");
					write("\n");
					write("*********************************************"
							+ "**********************************\n");
					write("Launching Orcc backend...\n");
					launchBackend();
					write("Orcc backend done.");
				} 
				
				if (option.equals("simulator") || option.equals("debugger")) {
					monitor.subTask("Launching simulator...");
					write("\n");
					write("*********************************************"
							+ "**********************************\n");
					write("Launching Orcc simulator...\n");
					launchSimulator(option);
					write("Orcc simulation done.");
				}
			}
		} finally {
			terminated = true;

			DebugEvent event = new DebugEvent(this, DebugEvent.TERMINATE);
			DebugEvent[] events = { event };
			DebugPlugin.getDefault().fireDebugEventSet(events);
		}
	}

	@Override
	public void terminate() throws DebugException {
	}

	/**
	 * Writes the given text to the normal output of this process.
	 * 
	 * @param text
	 *            a string
	 */
	public void write(String text) {
		((OrccMonitor) proxy.getOutputStreamMonitor()).write(text);
	}

}
