/*
 * Copyright (c) 2010, AKATECH SA
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
 *   * Neither the name of the AKATECH SA nor the names of its
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

import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.plugins.simulators.Simulator.DebugStackFrame;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.actors.Actor_ReadFile;
import net.sf.orcc.simulators.SimuActor;

/**
 * Proxy to connect a Actor_ReadFile for orcc.runtime library as an
 * InterpreterSimuActor to the "interpreter simulator"
 * 
 * @author fabecassis
 * 
 */
public class InterpreterSimuReadFile extends AbstractInterpreterSimuActor
		implements SimuActor {

	private Actor_ReadFile readFile;
	private String instanceId;

	public InterpreterSimuReadFile(String instanceId,
				Map<String, Expression> actorParameters, 
				OrccProcess process) {
		Expression expr = actorParameters.get("filename");
		if (expr.isStringExpr()) {
			StringExpr s = (StringExpr) expr;
			this.readFile = new Actor_ReadFile(s.getValue());
			this.instanceId = instanceId;
		}
		else {
			throw new OrccRuntimeException("Parameter filename for " + instanceId + " is not of type String.");
		}
	}

	@Override
	public void clearBreakpoint(int breakpoint) {
		// Source actor breakpoint not supported
	}

	@Override
	public void close() {
		readFile.close();
	}
	
	@Override
	public String getActorName() {
		return "ReadFile";
	}

	@Override
	public String getFileName() {
		return "";
	}
	
	@Override
	public String getInstanceId() {
		return instanceId;
	}

	@Override
	public DebugStackFrame getStackFrame() {
		DebugStackFrame stackFrame = new DebugStackFrame();
		stackFrame.actorFilename = "";
		stackFrame.codeLine = 0;
		stackFrame.nbOfFirings = nbOfFirings;
		stackFrame.stateVars.clear();
		stackFrame.fsmState = "IDLE";
		return stackFrame;
	}
	
	@Override
	public int runAllSchedulableAction() {
		int status = readFile.schedule();
		nbOfFirings += status;
		return status;
	}

	@Override
	public int runNextSchedulableAction() {
		int status = readFile.schedule();
		nbOfFirings += status;
		return status;
	}

	@Override
	public void setBreakpoint(int breakpoint) {
		// ReadFile actor breakpoint not supported
	}

	@Override
	public void setFifo(Port port, Fifo fifo) {
		readFile.setFifo(port.getName(), fifo);
	}
}
