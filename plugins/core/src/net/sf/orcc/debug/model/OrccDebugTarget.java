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
package net.sf.orcc.debug.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.interpreter.DebugThread;
import net.sf.orcc.interpreter.InterpreterMain;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;

/**
 * Orcc Debug Target
 */
public class OrccDebugTarget extends OrccDebugElement implements IDebugTarget,
		PropertyChangeListener {

	// associated interpreter
	private InterpreterMain fInterpreter;

	// containing launch object
	private ILaunch fLaunch;

	// name of the model to be debugged
	private String fName;

	// associated system process
	private IProcess fProcess;

	// suspend state
	private boolean fSuspended = true;

	// terminated state
	private boolean fTerminated = false;

	// threads
	private IThread[] fThreads;
	private Map<String, OrccThread> threadMap;

	/**
	 * Constructs a new debug target in the given launch for the associated Orcc
	 * VM process.
	 * 
	 * @param launch
	 *            containing launch
	 * @param process
	 *            Orcc VM
	 * @param requestPort
	 *            port to send requests to the VM
	 * @param eventPort
	 *            port to read events from
	 * @exception CoreException
	 *                if unable to connect to host
	 */
	public OrccDebugTarget(ILaunch launch, IProcess process,
			InterpreterMain interpreter) throws CoreException {
		super(null);
		fLaunch = launch;
		fTarget = this;
		fProcess = process;
		fInterpreter = interpreter;
		fName = fInterpreter.getNetworkName();
		threadMap = new HashMap<String, OrccThread>();
		List<DebugThread> threads = fInterpreter.getThreads();
		fThreads = new IThread[threads.size()];
		int idx = 0;
		for (DebugThread thread : threads) {
			fThreads[idx] = new OrccThread(this, thread);
			threadMap.put(thread.getName(), (OrccThread) fThreads[idx]);
			idx++;
		}
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse
	 * .debug.core.model.IBreakpoint)
	 */
	public void breakpointAdded(IBreakpoint breakpoint) {
		if (supportsBreakpoint(breakpoint)) {
			try {
				if (breakpoint.isEnabled()) {
					try {
						sendRequest("set", breakpoint.getMarker().getResource()
								.getName(), (((ILineBreakpoint) breakpoint)
								.getLineNumber()));
					} catch (CoreException e) {
					}
				}
			} catch (CoreException e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse
	 * .debug.core.model.IBreakpoint, org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (supportsBreakpoint(breakpoint)) {
			try {
				if (breakpoint.isEnabled()) {
					breakpointAdded(breakpoint);
				} else {
					breakpointRemoved(breakpoint, null);
				}
			} catch (CoreException e) {
			}
		}
	}

	/**
	 * Notification a breakpoint was encountered. Determine which breakpoint was
	 * hit and fire a suspend event.
	 * 
	 * @param event
	 *            debug event
	 */
	private void breakpointHit(String event, OrccThread orccThread) {
		// determine which breakpoint was hit, and set the thread's breakpoint
		int lastSpace = event.lastIndexOf(' ');
		if (lastSpace > 0) {
			String line = event.substring(lastSpace + 1);
			int lineNumber = Integer.parseInt(line);
			IBreakpoint[] breakpoints = DebugPlugin.getDefault()
					.getBreakpointManager().getBreakpoints(ID_ORCC_DEBUG_MODEL);
			for (int i = 0; i < breakpoints.length; i++) {
				IBreakpoint breakpoint = breakpoints[i];
				if (supportsBreakpoint(breakpoint)) {
					if (breakpoint instanceof ILineBreakpoint) {
						ILineBreakpoint lineBreakpoint = (ILineBreakpoint) breakpoint;
						try {
							if (lineBreakpoint.getLineNumber() == lineNumber) {
								orccThread
										.setBreakpoints(new IBreakpoint[] { breakpoint });
								break;
							}
						} catch (CoreException e) {
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse
	 * .debug.core.model.IBreakpoint, org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (supportsBreakpoint(breakpoint)) {
			try {
				sendRequest("clear", breakpoint.getMarker().getResource()
						.getName(), (((ILineBreakpoint) breakpoint)
						.getLineNumber()));
			} catch (CoreException e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
	 */
	public boolean canDisconnect() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		return !isTerminated() && !isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return getProcess().canTerminate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
	 */
	public void disconnect() throws DebugException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getDebugTarget()
	 */
	@Override
	public IDebugTarget getDebugTarget() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getLaunch()
	 */
	@Override
	public ILaunch getLaunch() {
		return fLaunch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long,
	 * long)
	 */
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getName()
	 */
	public String getName() throws DebugException {
		if (fName == null) {
			fName = "RVC-CAL model";
		}
		return fName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getProcess()
	 */
	public IProcess getProcess() {
		return fProcess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getThreads()
	 */
	public IThread[] getThreads() throws DebugException {
		return fThreads;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
	 */
	public boolean hasThreads() throws DebugException {
		return true;
	}

	/**
	 * Install breakpoints that are already registered with the breakpoint
	 * manager.
	 */
	private void installDeferredBreakpoints() {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager().getBreakpoints(ID_ORCC_DEBUG_MODEL);
		for (int i = 0; i < breakpoints.length; i++) {
			breakpointAdded(breakpoints[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#isDisconnected()
	 */
	public boolean isDisconnected() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		return fSuspended;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		fTerminated = getProcess().isTerminated();
		return fTerminated;
	}

	public void propertyChange(PropertyChangeEvent event) {
		OrccThread orccThread = threadMap.get(event.getNewValue());
		 if (orccThread != null) {
			 orccThread.setBreakpoints(null);
		 }

		if (event.getPropertyName().equals("started")) {
			started();
		} else if (event.getPropertyName().equals("terminated")) {
			terminated();
		} else if (event.getPropertyName().startsWith("resumed")) {
			if (event.getPropertyName().endsWith("step")) {
				resumed(DebugEvent.STEP_OVER, orccThread);
			}else if (event.getPropertyName().endsWith("client")) {
				resumed(DebugEvent.CLIENT_REQUEST, orccThread);
			}
		} else if (event.getPropertyName().startsWith("suspended")) {
			if (event.getPropertyName().endsWith("client")) {
				suspended(DebugEvent.CLIENT_REQUEST, orccThread);
			} else if (event.getPropertyName().endsWith("step")) {
				suspended(DebugEvent.STEP_END, orccThread);
			} else if (event.getPropertyName().indexOf("breakpoint") >= 0) {
				if (orccThread != null) {
					breakpointHit(event.getPropertyName(), orccThread);
					suspended(DebugEvent.BREAKPOINT, null);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		sendRequest("resume");
	}

	/**
	 * Notification the target has resumed for the given reason
	 * 
	 * @param detail
	 *            reason for the resume
	 */
	private void resumed(int detail, OrccThread orccThread) {
		if (orccThread != null) {
			orccThread.fireResumeEvent(detail);
		} else {
			fSuspended = false;
			for (IThread thread : fThreads) {
				((OrccThread) thread).fireResumeEvent(detail);
			}
			fireResumeEvent(detail);
		}
	}

	/**
	 * Sends a request to the Orcc VM and waits for an OK.
	 * 
	 * @param request
	 *            debug command
	 * @throws DebugException
	 *             if the request fails
	 */
	private void sendRequest(String request) throws DebugException {
		if (request.equals("resume")) {
			fInterpreter.resumeAll();
		} else if (request.equals("suspend")) {
			fInterpreter.suspendAll();
		} else if (request.equals("step")) {
			fInterpreter.stepAll();
		} else if (request.equals("terminate")) {
			fInterpreter.terminate();
		}
	}

	private void sendRequest(String request, String id, int lineNumber)
			throws DebugException {
		if (request.equals("set")) {
			fInterpreter.set_breakpoint(id, lineNumber);
		} else if (request.equals("clear")) {
			fInterpreter.clear_breakpoint(id, lineNumber);
		}
	}

	/**
	 * Notification we have connected to the VM and it has started. Resume the
	 * VM.
	 */
	private void started() {
		fireCreationEvent();
		installDeferredBreakpoints();
		try {
			resume();
		} catch (DebugException e) {
		}
	}

	/**
	 * Single step the interpreter.
	 * 
	 * @throws DebugException
	 *             if the request fails
	 */
	protected void step() throws DebugException {
		sendRequest("step");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IDebugTarget#supportsBreakpoint(org.eclipse
	 * .debug.core.model.IBreakpoint)
	 */
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		if (breakpoint.getModelIdentifier().equals(ID_ORCC_DEBUG_MODEL)) {
			IMarker marker = breakpoint.getMarker();
			if (marker != null) {
				// try {
				// String inputFile = getLaunch(). getLaunchConfiguration()
				// .getAttribute(INPUT_FILE, "");
				// IPath p = new Path(new File(inputFile).getParent());
				// return marker.getResource().getFullPath().equals(p);
				// } catch (CoreException e) {
				// }
				// TODO : check we are running project containing this resource
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IMemoryBlockRetrieval#supportsStorageRetrieval
	 * ()
	 */
	public boolean supportsStorageRetrieval() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		sendRequest("suspend");
	}

	/**
	 * Notification the target has suspended for the given reason
	 * 
	 * @param detail
	 *            reason for the suspend
	 */
	private void suspended(int detail, OrccThread orccThread) {
		if (orccThread != null) {
			orccThread.fireSuspendEvent(detail);
		} else {
			fSuspended = true;
			for (IThread thread : fThreads) {
				((OrccThread) thread).fireSuspendEvent(detail);
			}
			fireSuspendEvent(detail);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		sendRequest("terminate");
	}

	/**
	 * Called when this debug target terminates.
	 */
	private void terminated() {
		fTerminated = true;
		fSuspended = false;
		DebugPlugin.getDefault().getBreakpointManager()
				.removeBreakpointListener(this);
		fireTerminateEvent();
	}
}
