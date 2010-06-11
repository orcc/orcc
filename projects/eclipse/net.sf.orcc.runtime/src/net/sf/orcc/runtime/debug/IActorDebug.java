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
package net.sf.orcc.runtime.debug;

import java.util.Map;

import net.sf.orcc.debug.Location;
import net.sf.orcc.debug.type.AbstractType;
import net.sf.orcc.runtime.actors.IActor;

public interface IActorDebug extends IActor {

	/**
	 * Returns the RVC-CAL this actor was defined in.
	 * 
	 * @return the RVC-CAL this actor was defined in
	 */
	public String getFile();

	/**
	 * Returns the location of the given action
	 * 
	 * @param action
	 *            action name
	 * @return location
	 */
	public Location getLocation(String action);

	/**
	 * Returns the name of the next schedulable action.
	 * 
	 * @return the name of the next schedulable action, or <code>null</code> if
	 *         no action is schedulable.
	 */
	public String getNextSchedulableAction();

	/**
	 * Returns the value of the given state variable.
	 * 
	 * @param variable
	 *            the state variable name.
	 * 
	 * @return the value of the given state variable
	 */
	public String getValue(String variable);

	/**
	 * Returns the value of the given state variable, if the state variable is
	 * an array.
	 * 
	 * @param variable
	 *            the state variable name.
	 * @param index
	 *            the index.
	 * 
	 * @return the value of the given state variable
	 */
	public String getValue(String variable, int index);

	/**
	 * Returns the map of state variables of this actor.
	 * 
	 * @return the map of state variables of this actor
	 */
	public Map<String, AbstractType> getVariables();

	/**
	 * Resumes this actor.
	 */
	public void resume();

	/**
	 * Suspends this actor.
	 */
	public void suspend();

}
