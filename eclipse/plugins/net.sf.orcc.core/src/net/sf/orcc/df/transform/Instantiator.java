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

	private boolean duplicateActors;

	private List<Actor> listActors;

	/**
	 * Creates an instantiator, equivalent to
	 * <code>Instantiator(duplicateActors, 0)</code>, that will replace
	 * instances of networks by instantiated networks, and if duplicateActors is
	 * <code>true</code>, then the instantiator also transforms instances of
	 * actors by duplicating actors. The instantiator does not set default FIFO
	 * size.
	 * 
	 * @param duplicateActors
	 *            <code>true</code> if actors should be duplicated
	 */
	public Instantiator(boolean duplicateActors) {
		this(duplicateActors, 0);
	}

	/**
	 * Creates an instantiator that will replace instances of networks by
	 * instantiated networks, and if duplicateActors is <code>true</code>, then
	 * the instantiator also transforms instances of actors by duplicating
	 * actors.
	 * 
	 * @param duplicateActors
	 *            <code>true</code> if actors should be duplicated
	 * @param defaultFifoSize
	 *            default FIFO size
	 */
	public Instantiator(boolean duplicateActors, int defaultFifoSize) {
		this.defaultFifoSize = defaultFifoSize;
		this.duplicateActors = duplicateActors;
		if (duplicateActors) {
			listActors = new ArrayList<Actor>();
		}
	}

	@Override
	public Void caseNetwork(Network network) {
		// copy instances to entities/instances
		List<Instance> instances = new ArrayList<Instance>(
				network.getInstances());
		for (Instance instance : instances) {
			EObject entity = instance.getEntity();

			if (entity instanceof Actor) {
				Actor actor = (Actor) entity;
				if (duplicateActors) {
					if (listActors.contains(actor)) {
						Actor newActor = duplicateActor(instance);
						listActors.add(newActor);
					} else {
						listActors.add(actor);
					}
				}

				// set attribute's value when passed as instance parameter
				for (Argument argument : instance.getArguments()) {
					Attribute attribute = actor.getAttribute(argument
							.getVariable().getName());
					if (attribute != null) {
						attribute.setValue(argument.getValue());
					}
				}

			} else if (entity instanceof Network) {
				instantiateNetwork(network, instance);
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
	 * Replaces the actor referenced by the instance by a fresh copy, and
	 * updates the instance's connections.
	 * 
	 * @param instance
	 *            an instance that references an actor
	 * @return the duplicate actor
	 */
	private Actor duplicateActor(Instance instance) {
		Copier copier = new Copier();
		Actor newActor = IrUtil.copy(copier, (Actor) instance.getEntity());

		// Update reference to instance's entity
		instance.setEntity(newActor);
		// Update connection to this new entity
		connect(copier, instance, instance);

		return newActor;
	}

	/**
	 * Instantiates the sub-network referenced by the instance in the given
	 * network.
	 * 
	 * @param network
	 *            network that contains instance
	 * @param instance
	 *            an instance that references a sub-network
	 */
	private void instantiateNetwork(Network network, Instance instance) {
		// copy sub network
		Copier copier = new Copier();
		Network subNetwork = (Network) copier.copy(instance.getEntity());
		copier.copyReferences();

		// instantiate sub network
		doSwitch(subNetwork);

		// rename sub network
		subNetwork.setName(instance.getName());

		// replace connections of instance
		network.add(subNetwork);
		connect(copier, instance, subNetwork);

		// remove instance
		network.remove(instance);

		// assigns arguments' values to network's variables
		for (Argument argument : instance.getArguments()) {
			Var var = (Var) copier.get(argument.getVariable());
			Expression value = argument.getValue();
			var.setInitialValue(value);
		}
	}

}
