/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.df.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.util.SwitchUtil;
import net.sf.orcc.util.Void;

/**
 * This class propagates the default fifosize to all network connections.
 *
 * @author Jani Boutellier
 *
 */
public class FifoSizePropagator extends DfVisitor<Void> {

	private int defaultFifoSize = 512;
	private boolean selectMaxSize = false;

	public FifoSizePropagator(int fifosize) {
		this.defaultFifoSize = fifosize;
	}

	public FifoSizePropagator(int fifosize, boolean selectMaxSize) {
		this.defaultFifoSize = fifosize;
		this.selectMaxSize = selectMaxSize;
	}

	@Override
	public Void caseNetwork(Network network) {
		for (Connection connection : network.getConnections()) {
			if (connection.getSize() == null) {
				connection.setSize(defaultFifoSize);
			}
		}

		if (selectMaxSize) {
			Map<Port, List<Connection>> portsMap = new HashMap<Port, List<Connection>>();
			for (Connection c : network.getConnections()) {
				Port port = c.getSourcePort();
				List<Connection> pConns = portsMap.get(port);
				if (pConns == null) {
					pConns = new ArrayList<Connection>();
					portsMap.put(port, pConns);
				}
				pConns.add(c);
			}

			for (List<Connection> portConns : portsMap.values()) {
				if (portConns.size() > 1) {
					// compute the maximal size
					int maxSize = 0;
					for (Connection c : portConns) {
						Integer value = c.getSize();
						if (value != null) {
							maxSize = Math.max(maxSize, value.intValue());
						}
					}
					maxSize = (maxSize != 0) ? maxSize : defaultFifoSize;
					for (Connection c : portConns) {
						c.setSize(maxSize);
					}
				}
			}
		}

		return SwitchUtil.DONE;
	}

}
