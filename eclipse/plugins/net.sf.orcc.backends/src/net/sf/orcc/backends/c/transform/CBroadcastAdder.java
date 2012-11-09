/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.c.transform;

import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;

/**
 * Adds broadcast actors only when some fifos from same port have different
 * size.
 * 
 * @author Herve Yviquel
 * 
 */
public class CBroadcastAdder extends BroadcastAdder {

	@Override
	public Void casePort(Port port) {
		createNeededBcast(port, port.getOutgoing());
		return null;
	}

	@Override
	protected void handle(Vertex vertex) {
		Entity entity = vertex.getAdapter(Entity.class);
		Map<Port, List<Connection>> outMap = entity.getOutgoingPortMap();
		for (Port srcPort : outMap.keySet()) {
			createNeededBcast(srcPort, outMap.get(srcPort));
		}
	}

	private void createNeededBcast(Port srcPort, List<? extends Edge> edges) {
		if (edges.size() > 1) {
			int size = ((Connection) edges.get(0)).getSize();
			for (Edge edge : edges) {
				if (size != ((Connection) edge).getSize()) {
					createBroadcast(network.getSimpleName(), srcPort, edges);
					OrccLogger
							.warnln("Different-sized FIFOs connected to port '"
									+ srcPort.getName() + "' in '"
									+ network.getName()
									+ "'. A broadcast is created.\n");
				}
			}
		}

	}

}
