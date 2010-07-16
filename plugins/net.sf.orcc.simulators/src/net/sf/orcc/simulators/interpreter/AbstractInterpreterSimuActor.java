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

import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.runtime.Fifo;

/**
 * This class defines an abstract representation of an actor instance to be
 * simulated with the "interpreter simulator"
 * 
 * @author plagalay
 * 
 */
public abstract class AbstractInterpreterSimuActor {

	protected String lastVisitedAction = "NA";

	protected Location lastVisitedLocation = new Location(0, 0, 0);
	
	protected int nbOfFirings;

	/**
	 * Close the actor instance if necessary (for example I/O devices to be
	 * closed)
	 */
	public void close() {
	}

	/**
	 * Reach the breakpoint that has been detected in the next schedulable
	 * action.
	 * 
	 * @return the line number of the reached breakpoint
	 */
	public int goToBreakpoint() {
		return 0;
	}

	/**
	 * Indicate actor can connect itself to its FIFOs
	 */
	public void connect() {
	}
	
	/**
	 * Initialize the actor instance : instantiate parameters values and set
	 * initial values of actor's state variables.
	 */
	public void initialize() {
	}
	
	/**
	 * Indicate if the current actor is stepping.
	 * 
	 * @return true if actor is stepping over an action
	 */
	public boolean isStepping() {
		return false;
	}

	/**
	 * Look for next schedulable action and fire it until no more action can be
	 * scheduled or a breakpoint has been hit if debug mode is enabled.
	 * 
	 * @return the number of fired actions; "-1" if user has interrupted
	 *         simulation by closing the display window; "-2" if a breakpoint
	 *         has been hit
	 */
	public int runAllSchedulableAction() {
		return 0;
	}

	/**
	 * Look for the next schedulable action, if any. Then fire it until action
	 * complete or a breakpoint has been hit if debug mode is enabled.
	 * 
	 * @return "1" if action has been fired; "0" if none action can be
	 *         scheduled; "-1" if user has interrupted simulation by closing the
	 *         display window; "-2" if a breakpoint has been hit
	 */
	public int runNextSchedulableAction() {
		return 0;
	}

	/**
	 * Register a Fifo (of type Fifo_int, Fifo_boolean or Fifo_String) to the
	 * specified I/O port.
	 * 
	 * @param fifo
	 * @param port
	 */
	public void setFifo(Port port, Fifo fifo) {
	}

	/**
	 * Single step the current schedulable action. This method is only called
	 * when the current actor instance is suspended (breakpoint hit or user
	 * suspend).
	 * 
	 * @param stepInto
	 *            : flag indicating if we have to step into (and not over) the
	 *            current stack
	 * @return "2" if current action has not been completed yet; "1" if action
	 *         has been completed; "0" if no more action can be scheduled;
	 * 
	 */
	public int step(boolean stepInto) {
		return 1;
	}
}
