/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.df.transformations;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.dftools.graph.Edge;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines a transformation that transforms a network into a new
 * network where instances of actors and networks are replaced by new actors and
 * networks where the value of parameters have been appropriately replaced.
 * 
 * @author Matthieu Wipliez
 * @author Hervé Yviquel
 * 
 */
public class Instantiator extends DfSwitch<Network> {

	static String BUFFER_SIZE = Connection.BUFFER_SIZE;

	private Copier copier;

	private Map<Instance, Network> map;

	private int defaultFifoSize;

	/**
	 * Creates a default instantiator, equivalent to
	 * <code>Instantiator(0)</code>.
	 */
	public Instantiator() {
		this(0);
	}

	/**
	 * Creates an instantiator that will replace instances of networks by
	 * instantiated networks.
	 * 
	 * @param defaultFifoSize
	 *            default FIFO size
	 */
	public Instantiator(int defaultFifoSize) {
		this.defaultFifoSize = defaultFifoSize;
		copier = new Copier();
		map = new HashMap<Instance, Network>();
	}

	@Override
	public Network caseNetwork(Network network) {
		Network networkCopy = (Network) copier.copy(network);

		// copy instances to entities/instances
		Map<Instance, Network> instMap = new HashMap<Instance, Network>();
		for (Instance instance : network.getInstances()) {
			EObject entity = instance.getEntity();
			if (entity instanceof Actor) {

			} else if (entity instanceof Network) {
				Network subNetwork = (Network) entity;
				map.put(instance, doSwitch(subNetwork));
			}
		}

		for (Entry<Instance, Network> entry : instMap.entrySet()) {
			Instance instance = entry.getKey();
			Network instantiatedNetwork = entry.getValue();
			networkCopy.add(instantiatedNetwork);

			for (Edge edge : instance.getIncoming()) {
				edge.setTarget(instantiatedNetwork);
				Connection connection = (Connection) edge;
				connection.setTargetPort((Port) copier.get(connection
						.getTargetPort()));
			}

			for (Edge edge : instance.getOutgoing()) {
				edge.setSource(instantiatedNetwork);
				Connection connection = (Connection) edge;
				connection.setSourcePort((Port) copier.get(connection
						.getSourcePort()));
			}

			networkCopy.remove(instance);
		}

		// update FIFO size
		for (Connection connection : networkCopy.getConnections()) {
			if (connection.getAttribute(BUFFER_SIZE) == null
					&& defaultFifoSize != 0) {
				connection.setAttribute(BUFFER_SIZE, defaultFifoSize);
			}
		}

		return networkCopy;
	}

}
