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

	private Network network;

	private List<String> mergedActors;

	/**
	 * Transforms the network to internalize the given list of vertices in their
	 * own subnetwork and returns this subnetwork.
	 * 
	 * @param vertices
	 *            a given list of vertices
	 * @return the SDF/CSDF child network containing the list of vertices
	 */
	private Network transformNetwork(List<Vertex> vertices) {
		Network subNetwork = dfFactory.createNetwork();
		subNetwork.setName("cluster" + index);

		List<Connection> newConnections = new ArrayList<Connection>();
		List<Connection> oldConnections = new ArrayList<Connection>();
		mergedActors = new ArrayList<String>();

		for (Vertex vertex : vertices) {
			IrUtil.copy(copier, vertex);
			mergedActors.add(network.getName() + "_" + vertex.getLabel());
		}

		for (Connection connection : network.getConnections()) {
			Vertex oldSrc = connection.getSource();
			Vertex oldTgt = connection.getTarget();

			if (vertices.contains(oldSrc) && vertices.contains(oldTgt)) {
				Vertex src = (Vertex) copier.get(oldSrc);
				Vertex tgt = (Vertex) copier.get(oldTgt);
				Port srcPort = (Port) copier.get(connection.getSourcePort());
				Port tgtPort = (Port) copier.get(connection.getTargetPort());

				subNetwork.add(dfFactory.createConnection(src, srcPort, tgt,
						tgtPort, IrUtil.copy(connection.getAttributes())));
				subNetwork.add(src);
				subNetwork.add(tgt);

				oldConnections.add(connection);
			} else if (!vertices.contains(oldSrc) && vertices.contains(oldTgt)) {
				Vertex tgt = (Vertex) copier.get(oldTgt);
				Port tgtPort = (Port) copier.get(connection.getTargetPort());
				tgtPort.setName(tgt.getLabel() + "_" + tgtPort.getName());

				caseActor(tgt.getAdapter(Actor.class));

				Port input = EcoreUtil.copy(tgtPort);
				subNetwork.addInput(input);

				subNetwork.add(dfFactory.createConnection(input, null, tgt,
						tgtPort, IrUtil.copy(connection.getAttributes())));

				// Add connection to the parent network
				newConnections.add(dfFactory.createConnection(oldSrc,
						connection.getSourcePort(), subNetwork, input,
						IrUtil.copy(connection.getAttributes())));

				oldConnections.add(connection);
			} else if (vertices.contains(oldSrc) && !vertices.contains(oldTgt)) {
				Vertex src = (Vertex) copier.get(oldSrc);
				Port srcPort = (Port) copier.get(connection.getSourcePort());
				srcPort.setName(src.getLabel() + "_" + srcPort.getName());

				caseActor(src.getAdapter(Actor.class));

				Port output = dfFactory.createPort(EcoreUtil.copy(srcPort));
				subNetwork.addOutput(output);

				subNetwork.add(dfFactory.createConnection(src, srcPort, output,
						null, IrUtil.copy(connection.getAttributes())));

				// Add connection to the parent network
				newConnections.add(dfFactory.createConnection(subNetwork,
						output, oldTgt, connection.getTargetPort(),
						IrUtil.copy(connection.getAttributes())));

				oldConnections.add(connection);
			}

		}

		for (Connection newConn : newConnections) {
			network.add(newConn);
		}
		network.add(subNetwork);

		network.removeVertices(vertices);
		network.getChildren().removeAll(vertices);

		return subNetwork;
	}

	@Override
	public Void caseNetwork(Network network) {
		this.network = network;

		List<List<Vertex>> staticRegions = new StaticRegionDetector()
				.analyze(network);

		for (List<Vertex> instances : staticRegions) {
			copier = new Copier(true);
			// transform the parent network and return the child network
			Network subNetwork = transformNetwork(instances);
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

			// merge vertices inside a single actor
			Actor superActor = new ActorMergerSDF(scheduler, copier)
					.doSwitch(subNetwork);
			superActor.setAttribute("mergedActors", mergedActors);

			network.add(superActor);
			EcoreUtil.delete(subNetwork);

			for (Connection connection : network.getConnections()) {
				Port srcPort = (Port) copier.get(connection.getSourcePort());
				if (srcPort != null) {
					connection.setSource(superActor);
					connection.setSourcePort(srcPort);
				}
				Port tgtPort = (Port) copier.get(connection.getTargetPort());
				if (tgtPort != null) {
					connection.setTarget(superActor);
					connection.setTargetPort(tgtPort);
				}
			}

			index++;
		}
		return null;
	}

}
