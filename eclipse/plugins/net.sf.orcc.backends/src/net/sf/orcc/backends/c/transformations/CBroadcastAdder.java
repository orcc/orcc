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
package net.sf.orcc.backends.c.transformations;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.transformations.BroadcastAdder;
import net.sf.orcc.util.WriteListener;

/**
 * Adds broadcast actors only when some fifos from same port have different
 * size.
 * 
 * @author Herve Yviquel
 * 
 */
public class CBroadcastAdder extends BroadcastAdder {

	private int defaultFifoSize;

	private WriteListener writeListener;

	public CBroadcastAdder(WriteListener writeListener, int defaultFifoSize) {
		this.writeListener = writeListener;
		this.defaultFifoSize = defaultFifoSize;
	}

	@Override
	protected void examineConnections(Vertex vertex,
			Set<Connection> connections, Map<Port, List<Connection>> outMap) {
		Instance instance = vertex.getInstance();
		for (Connection connection : connections) {
			Port srcPort = connection.getSourcePort();
			if (srcPort != null) {
				List<Connection> outList = outMap.get(srcPort);
				int numOutput = outList.size();
				if (numOutput > 1) {
					int size = getSize(outList.get(0));
					for (Connection connec : outList) {
						if (size != getSize(connec)) {
							createBroadcast(instance.getId(), srcPort, outList);
							writeListener
									.writeText("Warning: Different-sized FIFOs connected to port '"
											+ srcPort.getName()
											+ "' from '"
											+ instance.getId()
											+ "'. A broadcast is created.\n");
							return;
						}
					}
				}
			}
		}
	}

	private int getSize(Connection connection) {
		return connection.getSize() == null ? defaultFifoSize : connection
				.getSize();
	}
}
