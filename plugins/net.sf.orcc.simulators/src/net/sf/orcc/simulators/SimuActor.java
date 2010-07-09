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
package net.sf.orcc.simulators;

import net.sf.orcc.plugins.simulators.Simulator.DebugStackFrame;

/**
 * Generic common interface for simulated actors. Any simulator must implement
 * this interface in order to communicate with the Orcc Core Debug Model.
 * 
 * @author plagalay
 * 
 */
public interface SimuActor {

	/**
	 * Clear the breakpoint from the corresponding line
	 * 
	 * @param breakpoint
	 *            line number of actor's breakpoint
	 */
	public void clearBreakpoint(int breakpoint);

	/**
	 * Return the name of the Actor model definition of this actor instance.
	 * 
	 * @return The name of the Actor model
	 */
	public String getActorName();

	/**
	 * Return the name of the Actor description file of this actor instance.
	 * 
	 * @return The name of the Actor description file
	 */
	public String getFileName();
	
	/**
	 * Return the identifier of the current actor instance.
	 * 
	 * @return The ID of the current actor instance
	 */
	public String getInstanceId();

	/**
	 * Return the debug stack frame in the currently scheduled action.
	 * 
	 * @return Debug stack frame of the currently scheduled action.
	 */
	public DebugStackFrame getStackFrame();

	/**
	 * Set a breakpoint at the corresponding line
	 * 
	 * @param breakpoint
	 *            line number of actor's breakpoint
	 */
	public void setBreakpoint(int breakpoint);

}
