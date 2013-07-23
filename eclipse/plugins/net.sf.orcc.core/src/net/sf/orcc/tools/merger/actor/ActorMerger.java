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
package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines a network transformation that merges static actors.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * 
 */
public class ActorMerger extends DfVisitor<Void> {

	private final DfFactory dfFactory = DfFactory.eINSTANCE;

	private Copier copier;

	private int index;

	/**
	 * Transforms the network to internalize the given list of vertices in their
	 * own subnetwork and returns this subnetwork.
	 * 
	 * @param vertices
	 *            a given list of vertices
	 * @return the SDF/CSDF child network containing the list of vertices
	 */
	private Network getSubNetwork(Network network, List<Vertex> vertices) {
		// extract the sub-network
		Network subNetwork = IrUtil.copy(network);
		subNetwork.setName("cluster" + index);

		List<Vertex> verticesInSubNetwork = new ArrayList<Vertex>();
		for (Vertex vertex : vertices) {
			Vertex is = subNetwork.getChild(vertex.getLabel());
			verticesInSubNetwork.add(is);
		}

		for (Vertex vertex : new ArrayList<Vertex>(subNetwork.getChildren())) {
			if (!verticesInSubNetwork.contains(vertex)) {
				subNetwork.remove(vertex);
			}
		}

		for (Vertex vertex : subNetwork.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			if (actor != null) {
				List<Port> unconnected = new ArrayList<Port>(actor.getInputs());
				unconnected.removeAll(actor.getIncomingPortMap().keySet());
				for (Port input : unconnected) {
					Port inputPort = EcoreUtil.copy(input);
					inputPort.setName(vertex.getLabel() + "_"
							+ inputPort.getName());
					subNetwork.addInput(inputPort);
					subNetwork.add(dfFactory.createConnection(inputPort, null,
							vertex, input));
				}

				unconnected = new ArrayList<Port>(actor.getOutputs());
				unconnected.removeAll(actor.getOutgoingPortMap().keySet());
				for (Port output : unconnected) {
					Port outputPort = EcoreUtil.copy(output);
					outputPort.setName(vertex.getLabel() + "_"
							+ outputPort.getName());
					subNetwork.addOutput(outputPort);
					subNetwork.add(dfFactory.createConnection(vertex, output,
							outputPort, null));
				}
			}
		}

		return subNetwork;
	}

	@Override
	public Void caseNetwork(Network network) {
		List<List<Vertex>> staticRegions = new StaticRegionDetector()
				.analyze(network);

		for (List<Vertex> instances : staticRegions) {
			copier = new Copier(true);
			// return a copy of the sub-network
			Network subNetwork = getSubNetwork(network, instances);
			// create the static schedule of vertices
			SASLoopScheduler scheduler = new SASLoopScheduler(subNetwork);
			scheduler.schedule();

			int actorcount = subNetwork.getChildren().size();
			int fifocount = 0;
			for (Connection conn : subNetwork.getConnections()) {
				if (conn.getSourcePort() != null
						&& conn.getTargetPort() != null) {
					fifocount++;
				}
			}

			OrccLogger.traceln("Cluster" + index + " (" + actorcount
					+ " actors, " + fifocount + " fifos" + ") => Schedule is "
					+ scheduler.getSchedule());

			// merge sub-network inside a single actor
			Actor superActor = new ActorMergerSDF(scheduler, copier)
					.doSwitch(subNetwork);

			// update the main network
			network.add(superActor);

			List<Connection> newConnections = new ArrayList<Connection>();
			for (Connection connection : network.getConnections()) {
				Vertex src = connection.getSource();
				Vertex tgt = connection.getTarget();

				if (!instances.contains(src) && instances.contains(tgt)) {
					Port tgtPort = superActor.getInput(connection.getTarget()
							.getLabel()
							+ "_"
							+ connection.getTargetPort().getName());
					// Add connection to the parent network
					newConnections.add(dfFactory.createConnection(src,
							connection.getSourcePort(), superActor, tgtPort));

				} else if (instances.contains(src) && !instances.contains(tgt)) {
					Port srcPort = superActor.getOutput(connection.getSource()
							.getLabel()
							+ "_"
							+ connection.getSourcePort().getName());
					// Add connection to the parent network
					newConnections.add(dfFactory.createConnection(superActor,
							srcPort, tgt, connection.getTargetPort()));

				}

			}
			network.removeVertices(instances);
			network.getChildren().removeAll(instances);
			network.getEdges().addAll(newConnections);

			index++;
		}
		return null;
	}

}
