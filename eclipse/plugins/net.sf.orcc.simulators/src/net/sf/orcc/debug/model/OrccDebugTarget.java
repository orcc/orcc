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
import java.util.Map;

import net.sf.orcc.simulators.Simulator;

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
	public Simulator fSimulator;

	// containing launch object
	private ILaunch fLaunch;

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
	public OrccDebugTarget(ILaunch launch, IProcess process, Simulator simulator)
			throws CoreException {
		super(null);
		fLaunch = launch;
		fTarget = this;
		fProcess = process;
		fSimulator = simulator;
		threadMap = new HashMap<String, OrccThread>();

		DebugPlugin.getDefault().getBreakpointManager()
				.addBreakpointListener(this);
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		if (supportsBreakpoint(breakpoint)) {

		}
	}

	@Override
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

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (supportsBreakpoint(breakpoint)) {

		}
	}

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}

	@Override
	public boolean canSuspend() {
		return !isTerminated() && !isSuspended();
	}

	@Override
	public boolean canTerminate() {
		return getProcess().canTerminate();
	}

	@Override
	public void disconnect() throws DebugException {
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return this;
	}

	@Override
	public ILaunch getLaunch() {
		return fLaunch;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}

	@Override
	public String getName() throws DebugException {
		return "";
	}

	@Override
	public IProcess getProcess() {
		return fProcess;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		return fThreads;
	}

	@Override
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

	@Override
	public boolean isDisconnected() {
		return false;
	}

	@Override
	public boolean isSuspended() {
		return fSuspended;
	}

	@Override
	public boolean isTerminated() {
		fTerminated = getProcess().isTerminated();
		return fTerminated;
	}

	@Override
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
			} else if (event.getPropertyName().endsWith("client")) {
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
					suspended(DebugEvent.BREAKPOINT, orccThread);
				}
			}
		}
	}

	@Override
	public void resume() throws DebugException {
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
	}

	/**
	 * Step into the specified instance actor's stack frame.
	 * 
	 * @param instanceId
	 * @throws DebugException
	 */
	protected void stepInto(String instanceId) throws DebugException {
	}

	/**
	 * Step over the specified instance actor's stack frame.
	 * 
	 * @param instanceId
	 * @throws DebugException
	 */
	protected void stepOver(String instanceId) throws DebugException {
	}

	/**
	 * Step return from the specified instance actor's stack frame.
	 * 
	 * @param instanceId
	 * @throws DebugException
	 */
	protected void stepReturn(String instanceId) throws DebugException {
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		if (breakpoint.getModelIdentifier().equals(ID_ORCC_DEBUG_MODEL)) {
			IMarker marker = breakpoint.getMarker();
			if (marker != null) {
				// try {
				// String inputFile = getLaunch(). getLaunchConfiguration()
				// .getAttribute(XDF_FILE, "");
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

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public void suspend() throws DebugException {
	}

	/**
	 * Notification the target has suspended for the given reason
	 * 
	 * @param detail
	 *            reason for the suspend
	 */
	private void suspended(int detail, OrccThread orccThread) {
		fSuspended = true;
		if (orccThread != null) {
			orccThread.fireSuspendEvent(detail);
		}
		fireSuspendEvent(detail);
	}

	@Override
	public void terminate() throws DebugException {
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
