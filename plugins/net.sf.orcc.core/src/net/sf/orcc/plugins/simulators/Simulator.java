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
package net.sf.orcc.plugins.simulators;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.plugins.Plugin;

public interface Simulator extends Plugin, Runnable {

	/**
	 * Simulator automaton control states
	 */
	public enum SimulatorState {
		IDLE, CONFIGURED, RUNNING, SUSPENDED, STEPPING, TERMINATED
	}

	/**
	 * Simulator automaton control events
	 */
	public enum SimulatorEvent {
		CLEAR_BREAKPOINT, CONFIGURE, RESUME, SET_BREAKPOINT, SUSPEND, STEP_ALL, STEP_OVER, STEP_INTO, STEP_RETURN, TERMINATE
	}

	/**
	 * Debug stack frame content definition.
	 * 
	 * @author plagalay
	 * 
	 */
	public class DebugStackFrame {
		public String actorFilename;
		public Integer codeLine;
		public String currentAction;
		public String fsmState;
		public Integer nbOfFirings;
		public Map<String, Object> stateVars;

		public DebugStackFrame() {
			actorFilename = "";
			codeLine = 0;
			stateVars = new HashMap<String, Object>();
			nbOfFirings = 0;
			currentAction = "";
		}
	}

	/**
	 * Add the listener <code>listener</code> to the registered listeners.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to add.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Get a list corresponding to the simulated actors instances identifiers.
	 * 
	 * @return List<String> of instances identifiers
	 */
	public List<String> getActorsInstanceIds();

	/**
	 * Get Actor model name from the specified instance ID
	 * 
	 * @param instanceId
	 * @return Actor model name
	 */
	public String getActorName(String instanceId);

	/**
	 * Get the name of the network to simulate
	 * 
	 * @return String : network name
	 */
	public String getNetworkName();

	/**
	 * Get the current action stack frame from the specified actor instance.
	 * 
	 * @param instanceID
	 *            : actor instance unique identifier
	 * @return A DebugStackFrame description
	 */
	public DebugStackFrame getStackFrame(String instanceID);

	/**
	 * Receive a message with data content from master caller
	 * 
	 * @param event
	 *            CLEAR_BREAKPOINT : remove a breakpoint from the simulator list
	 *            (must be associated with 2 arguments data : the file name and
	 *            the line number of the source code); CONFIGURE : set to
	 *            simulator ready for simulation (must be associated with an
	 *            OrccProcess as data); RESUME : run the network; SET_BREAKPOINT
	 *            : add a breakpoint to the simulator list (data = file name and
	 *            line number); STEP_ALL : requires a step over all the actors
	 *            of the simulated network; STEP_INTO/OVER/RETURN : step
	 *            into/over/return from an actor action (data = target actor
	 *            instance ID); SUSPEND : stop the network execution until next
	 *            RESUME or a STEP command; TERMINATE : end of the simulation
	 *            required
	 * 
	 * @param data
	 */
	public void message(SimulatorEvent event, Object[] data);
}
