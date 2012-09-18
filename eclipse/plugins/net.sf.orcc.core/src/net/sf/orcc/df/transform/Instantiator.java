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
package net.sf.orcc.df.transform;

import static net.sf.orcc.df.Connection.BUFFER_SIZE;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.Attribute;

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
public class Instantiator extends DfSwitch<Void> {

	private int defaultFifoSize;

	private boolean instantiateActors;

	/**
	 * Creates an instantiator, equivalent to
	 * <code>Instantiator(instantiateActors, 0)</code>, that will replace
	 * instances of networks by instantiated networks, and if instantiateActors
	 * is <code>true</code>, then the instantiator also replaces instances of
	 * actors by instantiated actors. The instantiator does not set default FIFO
	 * size.
	 * 
	 * @param instantiateActors
	 *            <code>true</code> if actors should be duplicated
	 */
	public Instantiator(boolean instantiateActors) {
		this(instantiateActors, 0);
	}

	/**
	 * Creates an instantiator that will replace instances of networks by
	 * instantiated networks, and if instantiateActors is <code>true</code>,
	 * then the instantiator also replaces instances of actors by instantiated
	 * actors.
	 * 
	 * @param instantiateActors
	 *            <code>true</code> if actors should be duplicated
	 * @param defaultFifoSize
	 *            default FIFO size
	 */
	public Instantiator(boolean instantiateActors, int defaultFifoSize) {
		this.defaultFifoSize = defaultFifoSize;
		this.instantiateActors = instantiateActors;
	}

	@Override
	public Void caseNetwork(Network network) {
		// copy instances to entities/instances
		List<Vertex> children = new ArrayList<Vertex>(network.getChildren());
		for (Vertex vertex : children) {
			Instance instance = vertex.getAdapter(Instance.class);
			if (instance == null) {
				// cannot instantiate anything else than an instance
				continue;
			}

			EObject entity = instance.getEntity();
			if (entity instanceof Network) {
				instantiate(network, instance);
			} else if (entity instanceof Actor) {
				if (instantiateActors) {
					instantiate(network, instance);
				}

				// set attribute's value when passed as instance parameter
				Actor actor = instance.getAdapter(Actor.class);
				for (Argument argument : instance.getArguments()) {
					Attribute attribute = actor.getAttribute(argument
							.getVariable().getName());
					if (attribute != null) {
						attribute.setValue(argument.getValue());
					}
				}
			}
		}

		// update FIFO size
		for (Connection connection : network.getConnections()) {
			if (connection.getAttribute(BUFFER_SIZE) == null
					&& defaultFifoSize != 0) {
				connection.setAttribute(BUFFER_SIZE, defaultFifoSize);
			}
		}
		return null;
	}

	/**
	 * Replaces connections to the instance by connections to the entity.
	 * 
	 * @param copier
	 *            a copier object used to create the newEntity
	 * @param instance
	 *            an instance
	 * @param newEntity
	 *            the new entity
	 */
	private void connect(Copier copier, Instance instance, Vertex newEntity) {
		List<Edge> incoming = new ArrayList<Edge>(instance.getIncoming());
		for (Edge edge : incoming) {
			edge.setTarget(newEntity);
			Connection connection = (Connection) edge;
			connection.setTargetPort((Port) copier.get(connection
					.getTargetPort()));
		}

		List<Edge> outgoing = new ArrayList<Edge>(instance.getOutgoing());
		for (Edge edge : outgoing) {
			edge.setSource(newEntity);
			Connection connection = (Connection) edge;
			connection.setSourcePort((Port) copier.get(connection
					.getSourcePort()));
		}
	}

	/**
	 * Instantiates the object referenced by the instance in the given network.
	 * 
	 * @param network
	 *            network that contains instance
	 * @param instance
	 *            an instance that references an actor or sub-network
	 */
	private void instantiate(Network network, Instance instance) {
		// copy object
		Copier copier = new Copier(true);
		Vertex newEntity = (Vertex) IrUtil.copy(copier, instance.getEntity());

		// instantiate sub network
		doSwitch(newEntity);

		// rename sub network
		newEntity.setLabel(instance.getName());

		// replace connections of instance
		network.add(newEntity);
		connect(copier, instance, newEntity);

		// assigns arguments' values to network's variables
		for (Argument argument : instance.getArguments()) {
			Var var = (Var) copier.get(argument.getVariable());
			// If instance's parameter correspond to an actor's parameter
			if(var != null) {
				Expression value = argument.getValue();
				var.setInitialValue(value);
			} else {
				// TODO : Display a warning ?
			}
		}

		// remove instance
		network.remove(instance);
	}

}
