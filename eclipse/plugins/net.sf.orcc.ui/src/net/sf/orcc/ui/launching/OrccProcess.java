/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.ui.launching;

import java.io.IOException;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;

/**
 * This process is used to imitate the behaviour of a classical external process
 * launched in eclipse. It monitors a Job instance and try to stop it when user
 * cancel the process (by clicking on the red square in the Console tab).
 * 
 * @author Antoine Lorence
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
			// Unsupported
		}
	}

	class OrccJobAdapter extends JobChangeAdapter {

		OrccProcess thisProcess;

		OrccJobAdapter(OrccProcess process) {
			thisProcess = process;
		}

		@Override
		public void done(IJobChangeEvent event) {
			DebugEvent endEvent = new DebugEvent(thisProcess,
					DebugEvent.TERMINATE);
			DebugEvent[] events = { endEvent };
			DebugPlugin.getDefault().fireDebugEventSet(events);
		}
	}

	boolean terminated;
	Job job;
	ILaunch launch;
	IStreamsProxy streamProxy;

	OrccProcess(Job job, ILaunch launch) {
		terminated = false;

		this.job = job;
		this.launch = launch;

		streamProxy = new OrccProxy();

		job.addJobChangeListener(new OrccJobAdapter(this));
	}

	@Override
	public boolean canTerminate() {
		return !terminated && job.getState() == Job.RUNNING;
	}

	@Override
	public boolean isTerminated() {
		return terminated && job.getState() == Job.NONE;
	}

	@Override
	public void terminate() throws DebugException {
		job.cancel();
		terminated = true;
	}

	@Override
	public String getLabel() {
		return "Orcc virtual process";
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return streamProxy;
	}

	@Override
	public void setAttribute(String key, String value) {
		// Unsupported
	}

	@Override
	public String getAttribute(String key) {
		// Unsupported
		return null;
	}

	@Override
	public int getExitValue() throws DebugException {
		return job.getResult().getCode();
	}
}
