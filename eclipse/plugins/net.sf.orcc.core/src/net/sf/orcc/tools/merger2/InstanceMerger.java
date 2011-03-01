/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.merger2;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.util.MultiMap;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that merges a region of static instances
 * into a unique instance.
 * 
 * 
 * @author Jerome Gorin
 * 
 */
public class InstanceMerger {

	private ActorMerger actorMerger;
	DirectedGraph<Vertex, Connection> graph;

	int nMerged;

	public InstanceMerger(Network network) {
		this.nMerged = 0;
		this.graph = network.getGraph();
	}

	public Vertex getEquivalentVertices(Map<Vertex, Integer> verticesRate) {
		// Get the set of vertex to process
		Set<Vertex> vertices = verticesRate.keySet();

		// Create a composite actor
		MultiMap<Actor, Port> extInput = getExtInput(vertices);
		MultiMap<Actor, Port> extOutput = getExtOutput(vertices);

		actorMerger = new ActorMerger(extInput, extOutput);

		for (Entry<Vertex, Integer> entry : verticesRate.entrySet()) {
			Vertex vertex = entry.getKey();
			Instance instance = vertex.getInstance();

			actorMerger.add(instance.getActor(), entry.getValue());
		}

		// Create the merged instance
		Actor composite = actorMerger.getComposite();
		Instance compositeInst = new Instance("Merged" + nMerged++,
				composite.getName());
		compositeInst.setContents(composite);

		// Set instance attributes
		putAttributes(compositeInst, vertices);

		return new Vertex(compositeInst);
	}

	private MultiMap<Actor, Port> getExtInput(Set<Vertex> vertices) {
		MultiMap<Actor, Port> inputs = new MultiMap<Actor, Port>();

		for (Vertex vertex : vertices) {
			Set<Connection> connections = graph.incomingEdgesOf(vertex);

			for (Connection connection : connections) {
				Vertex srcVertex = graph.getEdgeSource(connection);

				if (!vertices.contains(srcVertex)) {
					Port input = connection.getTarget();
					Actor actor = vertex.getInstance().getActor();

					inputs.add(actor, input);
				}
			}

		}

		return inputs;
	}

	private MultiMap<Actor, Port> getExtOutput(Set<Vertex> vertices) {
		MultiMap<Actor, Port> outputs = new MultiMap<Actor, Port>();

		for (Vertex vertex : vertices) {
			Set<Connection> connections = graph.outgoingEdgesOf(vertex);

			for (Connection connection : connections) {
				Vertex dstVertex = graph.getEdgeTarget(connection);

				if (!vertices.contains(dstVertex)) {
					Port output = connection.getSource();
					Actor actor = vertex.getInstance().getActor();
					outputs.add(actor, output);
				}
			}

		}

		return outputs;
	}

	private void putAttributes(Instance instance, Set<Vertex> vertices) {
		Map<String, IAttribute> attributes = instance.getAttributes();

		for (Vertex vertex : vertices) {
			Instance refInstance = vertex.getInstance();
			attributes.putAll(refInstance.getAttributes());
		}
	}
}
