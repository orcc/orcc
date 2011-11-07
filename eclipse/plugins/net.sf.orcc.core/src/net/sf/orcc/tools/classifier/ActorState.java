/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.classifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.EcoreHelper;

/**
 * This class defines the state of an actor as a set of scalar state variables
 * with initial values that are used by guards, in isSchedulable procedures.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorState {

	private Map<Var, Object> state;

	/**
	 * Creates a new actor state initialized to all the state variables of the
	 * given actor that have an initial constant values and are not lists.
	 * 
	 * @param actor
	 *            an actor
	 */
	public ActorState(Actor actor) {
		state = new HashMap<Var, Object>();
		for (Var variable : actor.getStateVars()) {
			Type type = variable.getType();
			if (variable.isInitialized() && !type.isList()) {
				// we might consider this constant if it is used by guards
				boolean usedByGuard = false;
				for (Use use : variable.getUses()) {
					Procedure proc = EcoreHelper.getContainerOfType(use,
							Procedure.class);
					if (proc.eContainingFeature() == DfPackage.eINSTANCE
							.getAction_Scheduler()) {
						usedByGuard = true;
						break;
					}
				}

				if (usedByGuard) {
					state.put(variable, variable.getValue());
				}
			}
		}
	}

	/**
	 * Returns <code>true</code> if this actor state is empty, which means it
	 * has no scalar variables with initial values.
	 * 
	 * @return <code>true</code> if this actor state is empty
	 */
	public boolean isEmpty() {
		return state.isEmpty();
	}

	/**
	 * Returns true if all the state variables in the given map have runtime
	 * values that equal to their initial value stored in the map.
	 * 
	 * @param state
	 *            a map of state variable and their initial value
	 * @return <code>true</code> if the condition stated above holds
	 */
	public boolean isInitialState() {
		for (Entry<Var, Object> entry : state.entrySet()) {
			Var stateVariable = entry.getKey();
			Object value = stateVariable.getValue();
			if (value == null) {
				// oops not static!
				throw new OrccRuntimeException("null state variable");
			} else {
				if (!value.equals(entry.getValue())) {
					return false;
				}
			}
		}

		// all state variables' values equal initial values
		return true;
	}

	@Override
	public String toString() {
		return state.toString();
	}

}
