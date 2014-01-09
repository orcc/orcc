/*
 * Copyright (c) 2013, University of Rennes 1 / IRISA
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
 *   * Neither the name of the University of Rennes 1 / IRISA nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
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
package net.sf.orcc.backends.util

import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Entity
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.df.Pattern
import net.sf.orcc.graph.Vertex
import net.sf.orcc.util.OrccLogger

/**
 * Class containing static methods useful to check the validity of an input
 * network before generating code.
 *
 * @author Herve Yviquel
 */
class Validator {
	/**
	 * Check if the given network does not contain any ports. In the contrary
	 * case, an RuntimeException is thrown.
	 *
	 * @param network
	 *            the given network
	 */
	def static void checkTopLevel(Network network) {
		if (!network.getInputs().isEmpty() || !network.getOutputs().isEmpty()) {

			// FIXME: A warning could be sufficient if the generation of
			// sub-network is supported
			throw new OrccRuntimeException(
					"The given network contains port(s): Be sure to run the code "
							+ "generation on the top-level network.");
		}
	}

	/**
	 * Check if the given network does not contain repeat bigger than the size of the associated connection.
	 * In the contrary case, a warning is displayed.
	 *
	 * @param network
	 *            the given network
	 * @param size
	 *            default connection size
	 */
	def static void checkMinimalFifoSize(Network network, int size) {
		for (actor : network.children.filter(typeof(Actor))) {
			for (action : actor.actions + actor.initializes) {
				checkInputRepeat(action.inputPattern, actor, size)
				checkOutputRepeat(action.outputPattern, actor, size)
			}
		}
		for (instance : network.children.filter(typeof(Instance)).filter[isActor]) {
			val actor = instance.getActor
			for (action : actor.actions + actor.initializes) {
				checkInputRepeat(action.inputPattern, instance, size)
				checkOutputRepeat(action.outputPattern, instance, size)
			}
		}
		for (subnetwork : network.allNetworks) {
			checkMinimalFifoSize(subnetwork, size)
		}
	}

	def private static void checkInputRepeat(Pattern pattern, Vertex vertex, int size) {
		val incomingPortMap = vertex.getAdapter(typeof(Entity)).incomingPortMap
		for (port : pattern.ports) {
			if(incomingPortMap.containsKey(port)) {
				checkConnectionSize(incomingPortMap.get(port), pattern.getNumTokens(port), size)
			}
		}
	}

	def private static void checkOutputRepeat(Pattern pattern, Vertex vertex, int size) {
		val outgoingPortMap = vertex.getAdapter(typeof(Entity)).outgoingPortMap
		for (port : pattern.ports) {
			if(outgoingPortMap.containsKey(port)) {
				for (outgoing : outgoingPortMap.get(port)) {
					checkConnectionSize(outgoing, pattern.getNumTokens(port), size)
				}
			}
		}
	}

	def private static void checkConnectionSize(Connection connection, int numTokens, int size) {
		if (connection != null && numTokens >= connection.size ?: size) {
			OrccLogger::warnln(
				"Potential deadlock due to the size of (" + connection + ")."
			)
		}
	}

}
