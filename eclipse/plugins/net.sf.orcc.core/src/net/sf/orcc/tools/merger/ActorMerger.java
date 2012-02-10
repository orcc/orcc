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
package net.sf.orcc.tools.merger;

import java.util.HashSet;
import java.util.Set;

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines a network transformation that merges SDF actors.
 * 
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class ActorMerger extends DfSwitch<Void> {

	private Copier copier;

	private int index;

	private Network network;

	/**
	 * 
	 * @param vertices
	 * @return the SDF/CSDF child network
	 */
	private Network transformNetwork(Set<Vertex> vertices) {
		Network subNetwork = DfFactory.eINSTANCE.createNetwork();
		subNetwork.setName("cluster" + index);
		Instance subNetworkInst = DfFactory.eINSTANCE.createInstance("cluster"
				+ index, subNetwork);
		Set<Instance> instances = new HashSet<Instance>();
		Set<Connection> newConnections = new HashSet<Connection>();
		Set<Connection> oldConnections = new HashSet<Connection>();

		copier.copyAll(vertices);
		copier.copyReferences();

		int inIndex = 0, outIndex = 0;
		for (Connection connection : network.getConnections()) {
			Vertex srcVertex = connection.getSource();
			Vertex tgtVertex = connection.getTarget();
			if (vertices.contains(srcVertex) && vertices.contains(tgtVertex)) {
				Instance src = (Instance) copier.get(srcVertex);
				Instance tgt = (Instance) copier.get(tgtVertex);
				instances.add(src);
				instances.add(tgt);
				subNetwork.getConnections().add(
						DfFactory.eINSTANCE.createConnection(src,
								connection.getSourcePort(), tgt,
								connection.getTargetPort(),
								connection.getAttributes()));
				oldConnections.add(connection);

			} else if (!vertices.contains(srcVertex)
					&& vertices.contains(tgtVertex)) {
				Instance tgt = (Instance) copier.get(tgtVertex);
				Port tgtPort = connection.getTargetPort();
				Port input = DfFactory.eINSTANCE
						.createPort(EcoreUtil.copy(tgtPort.getType()), "input_"
								+ inIndex++);
				subNetwork.getInputs().add(input);
				subNetwork.getConnections().add(
						DfFactory.eINSTANCE.createConnection(input, null, tgt,
								tgtPort));

				// add connection is the parent network
				newConnections.add(DfFactory.eINSTANCE.createConnection(
						srcVertex, connection.getSourcePort(), subNetworkInst,
						input));
				oldConnections.add(connection);

			} else if (vertices.contains(srcVertex)
					&& !vertices.contains(tgtVertex)) {
				Instance src = (Instance) copier.get(srcVertex);
				Port srcPort = connection.getSourcePort();
				Port output = DfFactory.eINSTANCE.createPort(
						EcoreUtil.copy(srcPort.getType()), "output_"
								+ outIndex++);
				subNetwork.getOutputs().add(output);
				subNetwork.getConnections().add(
						DfFactory.eINSTANCE.createConnection(src, srcPort,
								output, null));

				// add connection is the parent network
				newConnections.add(DfFactory.eINSTANCE.createConnection(
						subNetworkInst, output, tgtVertex,
						connection.getTargetPort()));
				oldConnections.add(connection);

			}
		}
		subNetwork.getInstances().addAll(instances);

		network.getInstances().add(subNetworkInst);
		network.getInstances().removeAll(vertices);
		network.getConnections().addAll(newConnections);
		network.getConnections().removeAll(oldConnections);
		return subNetwork;
	}

	/**
	 * 
	 */
	@Override
	public Void caseNetwork(Network network) {
		this.network = network;
		copier = new Copier();
		// make instance unique in the network
		new UniqueInstantiator().doSwitch(network);

		// static region detections
		StaticRegionDetector detector = new StaticRegionDetector(network);
		for (Set<Vertex> vertices : detector.staticRegionSets()) {
			// transform the parent network and return the child network
			Network subNetwork = transformNetwork(vertices);

			// create the static schedule of vertices
			AbstractScheduler scheduler = new SASLoopScheduler(subNetwork);
			scheduler.schedule();

			System.out.println("Schedule of cluster" + index + " is "
					+ scheduler.getSchedule());

			// merge vertices inside a single actor
			Actor superActor = new MergerSdf(scheduler, copier)
					.doSwitch(subNetwork);

			// update parent network
			Instance instance = network.getInstance("cluster" + index);
			instance.setEntity(superActor);
			for (Connection connection : network.getConnections()) {
				Port srcPort = (Port) copier.get(connection.getSourcePort());
				if (srcPort != null) {
					connection.setSourcePort(srcPort);
				}
				Port tgtPort = (Port) copier.get(connection.getTargetPort());
				if (tgtPort != null) {
					connection.setTargetPort(tgtPort);
				}
			}

			index++;
		}
		copier = null;
		return null;
	}

}
