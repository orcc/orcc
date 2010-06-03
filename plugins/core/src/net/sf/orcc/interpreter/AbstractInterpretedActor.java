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
package net.sf.orcc.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Location;

/**
 * This class describes an abstract interpreted actor.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public abstract class AbstractInterpretedActor {

	/**
	 * Interpreted actor's local copy (instantiation)
	 */
	protected Actor actor;

	/**
	 * Interpreted instance general information
	 */
	protected String lastVisitedAction;
	protected Location lastVisitedLocation;
	protected String name;

	/**
	 * Communication FIFOs
	 */
	public Map<String, CommunicationFifo> ioFifos;

	/**
	 * Debug stack frame content definition.
	 * 
	 * @author plagalay
	 * 
	 */
	public class InterpreterStackFrame {
		public String actorFilename;
		public Integer codeLine;
		public String currentAction;
		public String fsmState;
		public Integer nbOfFirings;
		public Map<String, Object> stateVars;

		public InterpreterStackFrame() {
			actorFilename = "";
			codeLine = 0;
			stateVars = new HashMap<String, Object>();
			nbOfFirings = 0;
			currentAction = "";
		}
	}

	protected AbstractInterpretedActor(String id, Actor actor) {
		this.name = id;
		this.actor = actor;
		this.lastVisitedLocation = new Location(0, 0, 0);
		this.lastVisitedAction = "NA";
	}

	/**
	 * Clear the breakpoint from the corresponding line
	 * 
	 * @param breakpoint
	 *            line number of actor's breakpoint
	 */
	public void clearBreakpoint(int breakpoint) {
	}

	/**
	 * Close any additional resources (IO, file...)
	 */
	public void close() {
	}

	/**
	 * Get the communication FIFO corresponding to the given port
	 * 
	 * @param portName
	 * @return communication FIFO description
	 */
	public CommunicationFifo getFifo(String portName) {
		return ioFifos.get(portName);
	}

	/**
	 * Get last action that has been interpreted for this actor
	 * 
	 * @return name of the last action
	 */
	public String getLastVisitedAction() {
		return lastVisitedAction;
	}

	/**
	 * Get line number of the last code that has been interpreted
	 * 
	 * @return code line number
	 */
	public Location getLastVisitedLocation() {
		return lastVisitedLocation;
	}

	/**
	 * Get instance actor's name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get current state of actor for debug print
	 * 
	 * @return
	 */
	public InterpreterStackFrame getStackFrame() {
		InterpreterStackFrame stackFrame = new InterpreterStackFrame();
		stackFrame.actorFilename = actor.getFile();
		stackFrame.codeLine = lastVisitedLocation.getStartLine();
		stackFrame.stateVars.clear();
		stackFrame.currentAction = lastVisitedAction;
		stackFrame.fsmState = "idle";
		return stackFrame;
	}

	/**
	 * Synchronize the interpreted actor with the compiled actor until
	 * breakpoint is reached.
	 */
	public int goToBreakpoint() {
		return 0;
	}

	/**
	 * Initialize the actor state variables
	 */
	public void initialize() {
	}

	/**
	 * Indicate if the current actor is stepping into an action
	 * 
	 * @return true if action is in step-debugging of an action
	 */
	public boolean isStepping() {
		return false;
	}

	/**
	 * Run actor while any action can be scheduled.
	 * 
	 * @return the number of actions fired; "-1" if user has interrupted
	 *         interpretation by closing the display window; "-2" if a
	 *         breakpoint has been hit
	 */
	public Integer run() {
		return 0;
	}

	/**
	 * Schedule next actor's action that can be scheduled.
	 * 
	 * @return "1" if action has been fired; "0" if none action can be
	 *         scheduled; "-1" if user has interrupted interpretation by closing
	 *         the display window; "-2" if a breakpoint has been hit
	 */
	public Integer schedule() {
		return 0;
	}

	/**
	 * Set a breakpoint at the corresponding line
	 * 
	 * @param breakpoint
	 *            line number of actor's breakpoint
	 */
	public void setBreakpoint(int breakpoint) {
	}

	/**
	 * Check next instruction of the current schedulable action to be
	 * interpreted and interpret it.
	 * 
	 * @return "2" if current action has not been completed yet; "1" if action
	 *         has been completed; "0" if no more action can not be scheduled;
	 */
	public int step(boolean doStepInto) {
		return 0;
	}
}
