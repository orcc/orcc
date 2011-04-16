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

import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;

import java.io.IOException;

import net.sf.orcc.OrccActivator;
import net.sf.orcc.network.Network;
import net.sf.orcc.plugins.simulators.SimulatorFactory;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
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

		private String contents;

		private ListenerList list;

		/**
		 * Creates a new monitor.
		 */
		public OrccMonitor() {
			contents = "";
			list = new ListenerList();
		}

		@Override
		public void addListener(IStreamListener listener) {
			list.add(listener);
		}

		@Override
		public String getContents() {
			synchronized (this) {
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
			synchronized (this) {
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

	private ILaunchConfiguration configuration;

	private ILaunch launch;

	private IProgressMonitor monitor;

	private IStreamsProxy proxy;

	private boolean terminated;

	public OrccProcess(ILaunch launch, ILaunchConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		this.configuration = configuration;
		this.launch = launch;
		this.monitor = monitor;
		proxy = new OrccProxy();
	}

	@Override
	public boolean canTerminate() {
		return !terminated;
	}

	@Override
	public String getAttribute(String key) {
		return null;
	}

	@Override
	public int getExitValue() throws DebugException {
		return 0;
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
			e.printStackTrace();

			Throwable throwable = e;
			StringBuilder builder = new StringBuilder();
			while (throwable != null && throwable.getCause() != throwable) {
				builder.append(throwable.getLocalizedMessage());
				builder.append('\n');
				throwable = throwable.getCause();
			}

			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					simulator + " simulation error: " + builder.toString());
			throw new CoreException(status);
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
		try {
				monitor.subTask("Launching simulator...");
				write("\n");
				write("*********************************************"
						+ "**********************************\n");
				write("Launching Orcc simulator...\n");
				launchSimulator(option);
				write("Orcc simulation done.");
		} finally {
			terminated = true;

			DebugEvent event = new DebugEvent(this, DebugEvent.TERMINATE);
			DebugEvent[] events = { event };
			DebugPlugin.getDefault().fireDebugEventSet(events);
		}
	}

	@Override
	public void terminate() throws DebugException {
		monitor.setCanceled(true);
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
