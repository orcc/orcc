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

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

/**
 * A Orcc VM thread. A Orcc VM is single threaded.
 */
public class OrccThread extends OrccDebugElement implements IThread {

	/**
	 * Name of the CAL actor model associated to the current debug thread
	 */
	private String actorName;

	/**
	 * Actor instance ID associated to the current debug thread
	 */
	private String threadId;

	/**
	 * Breakpoints this thread is suspended at or <code>null</code> if none.
	 */
	private IBreakpoint[] breakpoints;

	/**
	 * Debugging objects
	 */
	private OrccDebugTarget target;

	/**
	 * Constructs a new thread for the given target
	 * 
	 * @param target
	 * @param threadId
	 * @param actorName
	 */
	public OrccThread(OrccDebugTarget target, String threadId, String actorName) {
		super(target);
		this.target = target;
		this.threadId = threadId;
		this.actorName = actorName;
	}

	@Override
	public boolean canResume() {
		return isSuspended() && !isStepping();
	}

	@Override
	public boolean canStepInto() {
		return isSuspended() && !isStepping();
	}

	@Override
	public boolean canStepOver() {
		return isSuspended() && !isStepping();
	}

	@Override
	public boolean canStepReturn() {
		return isSuspended() && !isStepping();
	}

	@Override
	public boolean canSuspend() {
		return !isSuspended();
	}

	@Override
	public boolean canTerminate() {
		return target.canTerminate();
	}

	public String getActorName() throws DebugException {
		return actorName;
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		if (breakpoints == null) {
			return new IBreakpoint[0];
		}
		return breakpoints;
	}

	@Override
	public String getName() throws DebugException {
		return threadId;
	}

	@Override
	public int getPriority() throws DebugException {
		return 0;
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		if (isSuspended()) {
			return new IStackFrame[0];
		} else {
			return new IStackFrame[0];
		}
	}

	@Override
	public IStackFrame getTopStackFrame() throws DebugException {
		IStackFrame[] frames = getStackFrames();
		if (frames.length > 0) {
			return frames[0];
		}
		return null;
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return isSuspended();
	}

	@Override
	public boolean isStepping() {
		return false;
	}

	@Override
	public boolean isSuspended() {
		return target.isSuspended() && !isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return target.isTerminated();
	}

	@Override
	public void resume() throws DebugException {
		target.resume();
	}

	/**
	 * Sets the breakpoints this thread is suspended at, or <code>null</code> if
	 * none.
	 * 
	 * @param breakpoints
	 *            the breakpoints this thread is suspended at, or
	 *            <code>null</code> if none
	 */
	protected void setBreakpoints(IBreakpoint[] breakpoints) {
		this.breakpoints = breakpoints;
	}

	@Override
	public void stepInto() throws DebugException {
		target.stepInto(threadId);
	}

	@Override
	public void stepOver() throws DebugException {
		target.stepOver(threadId);
	}

	@Override
	public void stepReturn() throws DebugException {
		target.stepReturn(threadId);
	}

	@Override
	public void suspend() throws DebugException {
		target.suspend();
	}

	@Override
	public void terminate() throws DebugException {
		target.terminate();
	}

}
