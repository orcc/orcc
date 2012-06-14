/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.xlim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;

/**
 * This class is giving the necessary information for the XLIM Network
 * generation
 * 
 * @author Endri Bezati
 * 
 */
public class XlimNetworkTemplateData {

	/**
	 * Contains a Map which indicates the number of the broadcasted actor
	 */

	private Map<Connection, Integer> networkPortConnectionFanout;

	/**
	 * Contains a Map which indicates the number of a Network Port broadcasted
	 */

	private Map<Port, Integer> networkPortFanout;

	/**
	 * Count the fanout of the actor's output port
	 * 
	 * @param network
	 */
	public void computeActorOutputPortFanout(Network network) {
		for (Vertex vertex : network.getEntities()) {
			Entity entity = vertex.getAdapter(Entity.class);
			Map<Port, List<Connection>> map = entity.getOutgoingPortMap();
			for (List<Connection> values : map.values()) {
				int cp = 0;
				for (Connection connection : values) {
					networkPortConnectionFanout.put(connection, cp++);
				}
			}
		}

	}

	/**
	 * Count the fanout of the network's input port
	 * 
	 * @param network
	 */

	public void computeNetworkInputPortFanout(Network network) {
		for (Port port : network.getInputs()) {
			int cp = 0;
			for (Connection connection : network.getConnections()) {
				if (connection.getSource() == port) {
					networkPortFanout.put(port, cp + 1);
					networkPortConnectionFanout.put(connection, cp);
					cp++;
				}
			}
		}
	}

	/**
	 * build all informations needed in the template data.
	 * 
	 * @param network
	 *            a network
	 */
	public void computeTemplateMaps(Network network) {
		networkPortFanout = new HashMap<Port, Integer>();
		networkPortConnectionFanout = new HashMap<Connection, Integer>();

		computeNetworkInputPortFanout(network);
		computeActorOutputPortFanout(network);
	}

	/**
	 * Return a Map which contains a connection and an associated number
	 */
	public Map<Connection, Integer> getNetworkPortConnectionFanout() {
		return networkPortConnectionFanout;
	}

	/**
	 * Return a Map which contains a connection and an associated number
	 */
	public Map<Port, Integer> getNetworkPortFanout() {
		return networkPortFanout;
	}

}