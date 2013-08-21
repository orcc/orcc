/*
 * Copyright (c) 2011, Abo Akademi University
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
 *   * Neither the name of the Abo Akademi University nor the names of its
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

package net.sf.orcc.backends.promela.transform;

import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;

/**
 * This class generates information about the data/control tokens needed for the
 * promela model. The current version only sets the max number of tokens each
 * port consumes/produces.
 * 
 * @author Johan Ersfolk
 * 
 */

public class PromelaTokenAnalyzer extends DfVisitor<Void> {

	@Override
	public Void caseAction(Action action) {
		Pattern pattern = action.getOutputPattern();
		for (Port port : pattern.getPorts()) {
			// if (schedulingPorts.contains(port)) {
			// System.out.println("Action "+action.getName()+" contributes to the output "+port.getName());
			// }
			int numTokens = pattern.getVariable(port).getType().getDimensions()
					.get(0);
			if (numTokens > port.getNumTokensProduced()) {
				port.setNumTokensProduced(numTokens);
			}
		}
		pattern = action.getInputPattern();
		for (Port port : pattern.getPorts()) {
			int num_tokens = pattern.getVariable(port).getType()
					.getDimensions().get(0);
			if (num_tokens > port.getNumTokensConsumed()) {
				port.setNumTokensConsumed(num_tokens);
			}
		}
		return null;
	}

	@Override
	public Void caseActor(Actor actor) {
		for (Action action : actor.getActions()) {
			doSwitch(action);
		}
		// Set the FIFO sizes according to the token consumtion/production
		Map<Port, Connection> inMap = actor.getIncomingPortMap();
		for (Port port : inMap.keySet()) {
			Connection connection = inMap.get(port);
			if (connection.getSize() == null
					|| port.getNumTokensConsumed() > connection.getSize()) {
				inMap.get(port).setAttribute(Connection.BUFFER_SIZE,
						port.getNumTokensConsumed());
			}
		}
		Map<Port, List<Connection>> outMap = actor.getOutgoingPortMap();
		for (Port port : outMap.keySet()) {
			for (Connection connection : outMap.get(port)) {
				if (connection.getSize() == null
						|| port.getNumTokensProduced() > connection.getSize()) {
					connection.setAttribute(Connection.BUFFER_SIZE,
							port.getNumTokensProduced());
				}
			}
		}
		return null;
	}

	public PromelaTokenAnalyzer(NetworkStateDefExtractor netStateDef) {
		super();
		// this.netStateDef = netStateDef;
		// this.schedulingPorts = netStateDef.getPortsUsedInScheduling();
	}

}
