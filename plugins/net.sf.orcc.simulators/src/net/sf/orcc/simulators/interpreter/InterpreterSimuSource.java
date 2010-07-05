/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.simulators.interpreter;

import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Port;
import net.sf.orcc.plugins.simulators.Simulator.DebugStackFrame;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.actors.Actor_Source;
import net.sf.orcc.simulators.SimuActor;

/**
 * Proxy to connect a Source_Actor for orcc.runtime library as an
 * InterpreterSimuActor to the "interpreter simulator"
 * 
 * @author plagalay
 * 
 */
public class InterpreterSimuSource extends AbstractInterpreterSimuActor
		implements SimuActor {

	private Actor_Source source;
	private String instanceId;

	public InterpreterSimuSource(String instanceId, String intputFile,
			OrccProcess process) {
		this.source = new Actor_Source(intputFile);
		this.instanceId = instanceId;
	}

	@Override
	public void clearBreakpoint(int breakpoint) {
		// Source actor breakpoint not supported
	}

	@Override
	public void close() {
		source.close();
	}
	
	@Override
	public String getActorName() {
		return "Source";
	}

	@Override
	public String getInstanceId() {
		return instanceId;
	}

	@Override
	public DebugStackFrame getStackFrame() {
		DebugStackFrame stackFrame = new DebugStackFrame();
		stackFrame.actorFilename = "Actor_Source";
		stackFrame.codeLine = 0;
		stackFrame.nbOfFirings = nbOfFirings;
		stackFrame.stateVars.clear();
		stackFrame.currentAction = source.getNextSchedulableAction();
		stackFrame.fsmState = "IDLE";
		return stackFrame;
	}

	@Override
	public void initialize() {
		source.initialize();
	}
	
	@Override
	public int runAllSchedulableAction() {
		int status = source.schedule();
		nbOfFirings += status;
		return status;
	}

	@Override
	public int runNextSchedulableAction() {
		int status = source.schedule();
		nbOfFirings += status;
		return status;
	}

	@Override
	public void setBreakpoint(int breakpoint) {
		// Source actor breakpoint not supported
	}

	@Override
	public void setFifo(Port port, Fifo fifo) {
		source.setFifo(port.getName(), fifo);
	}
}
