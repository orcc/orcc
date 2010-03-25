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
package net.sf.orcc.network.transforms;

import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a network transformation that closes actors in a network.
 * Closing an actor means its parameters (free variables) are transformed to
 * constant state variables, so the actor has no free variables any more, hence
 * it is closed.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CloseActorsTransform implements INetworkTransformation {

	/**
	 * Closes the given actor with the given parameter values.
	 * 
	 * @param actor
	 *            an actor
	 * @param values
	 *            a map of parameter names to values
	 */
	private void closeActor(Actor actor, Map<String, Expression> values) {
		OrderedMap<Variable> stateVars = actor.getStateVars();
		for (Variable parameter : actor.getParameters()) {
			String name = parameter.getName();
			Expression value = values.get(name);
			if (value == null) {
				throw new OrccRuntimeException("Actor " + actor
						+ " has no value for parameter " + name);
			}
			
			// a parameter is coded as a state variable
			StateVariable stateVar = (StateVariable) parameter;
			stateVar.setExpression(value);
			
			// add the parameter as a state variable
			stateVars.add(actor.getFile(), parameter.getLocation(), name, parameter);
		}
	}

	/**
	 * Walks through the hierarchy and close actors.
	 * 
	 * @throws OrccException
	 *             if an actor could not be closed
	 */
	public void transform(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			if (instance.isNetwork()) {
				// closes actors in the child network
				instance.getNetwork().closeActors();
			} else if (!instance.isBroadcast()) {
				// closes the child actor
				closeActor(instance.getActor(), instance.getParameters());
			}
		}
	}

}
