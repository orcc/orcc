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
package net.sf.orcc.ir;

import java.util.List;

/**
 * This class defines an action scheduler.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActionScheduler {

	private List<Action> actions;

	private FSM fsm;

	public ActionScheduler(List<Action> actions, FSM fsm) {
		this.actions = actions;
		this.fsm = fsm;
	}

	/**
	 * Returns the actions that are outside of an FSM. If this action scheduler
	 * has no FSM, all actions of the actor are returned. The actions are sorted
	 * by decreasing priority.
	 * 
	 * @return a list of actions
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * Returns the FSM of this action scheduler, or <code>null</code> if it does
	 * not have one.
	 * 
	 * @return the FSM of this action scheduler
	 */
	public FSM getFsm() {
		return fsm;
	}

	/**
	 * Returns true if this action scheduler has an FSM.
	 * 
	 * @return true if this action scheduler has an FSM
	 */
	public boolean hasFsm() {
		return (fsm != null);
	}

	/**
	 * Sets the FSM of this action scheduler to the given FSM.
	 * 
	 * @param fsm
	 *            an FSM
	 */
	public void setFsm(FSM fsm) {
		this.fsm = fsm;
	}

}
