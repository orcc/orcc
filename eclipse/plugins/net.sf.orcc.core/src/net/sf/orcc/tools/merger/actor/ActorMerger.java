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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;

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
public class ActorMerger extends DfVisitor<Void> {

	private class IrVisitor extends AbstractIrVisitor<Void> {

		@Override
		public Void caseInstLoad(InstLoad load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			Port port = action.getInputPattern().getVarToPortMap().get(var);
			if (port != null) {
				var.setName(port.getName());
			}

			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Def def = store.getTarget();
			Var var = def.getVariable();
			Port port = action.getOutputPattern().getVarToPortMap().get(var);
			if (port != null) {
				var.setName(port.getName());
			}

			return null;
		}
	}

	private final DfFactory dfFactory = DfFactory.eINSTANCE;

	private Copier copier;

	private int index;

	private Network network;

	private Action action;

	/**
	 * 
	 * @param vertices
	 * @return the SDF/CSDF child network
	 */
	private Network transformNetwork(List<Instance> vertices) {
		Network subNetwork = dfFactory.createNetwork();
		subNetwork.setName("cluster" + index);
		Instance subNetworkInst = dfFactory.createInstance("cluster" + index,
				subNetwork);

		Set<Connection> newConnections = new HashSet<Connection>();
		List<Connection> oldConnections = new ArrayList<Connection>();

		copier.copyAll(vertices);
		copier.copyReferences();

		int inIndex = 0, outIndex = 0;
		for (Connection connection : network.getConnections()) {
			Vertex srcVertex = connection.getSource();
			Vertex tgtVertex = connection.getTarget();
			if (vertices.contains(srcVertex) && vertices.contains(tgtVertex)) {
				Instance src = (Instance) copier.get(srcVertex);
				Instance tgt = (Instance) copier.get(tgtVertex);
				subNetwork.add(src);
				subNetwork.add(tgt);
				subNetwork.getConnections().add(
						dfFactory.createConnection(src,
								connection.getSourcePort(), tgt,
								connection.getTargetPort(),
								connection.getAttributes()));
				oldConnections.add(connection);
			} else if (!vertices.contains(srcVertex)
					&& vertices.contains(tgtVertex)) {
				Instance tgt = (Instance) copier.get(tgtVertex);
				Port tgtPort = connection.getTargetPort();

				tgtPort.setName("input_" + inIndex);
				caseActor(tgt.getActor());
				Port input = dfFactory
						.createPort(EcoreUtil.copy(tgtPort.getType()), "input_"
								+ inIndex++);
				subNetwork.addInput(input);
				subNetwork.getConnections().add(
						dfFactory.createConnection(input, null, tgt, tgtPort));

				// add connection is the parent network
				newConnections.add(dfFactory.createConnection(srcVertex,
						connection.getSourcePort(), subNetworkInst, input,
						IrUtil.copy(connection.getAttributes())));
				oldConnections.add(connection);
			} else if (vertices.contains(srcVertex)
					&& !vertices.contains(tgtVertex)) {
				Instance src = (Instance) copier.get(srcVertex);
				Port srcPort = connection.getSourcePort();
				srcPort.setName("output_" + outIndex);
				caseActor(src.getActor());
				Port output = dfFactory.createPort(
						EcoreUtil.copy(srcPort.getType()), "output_"
								+ outIndex++);
				subNetwork.addOutput(output);
				subNetwork.getConnections().add(
						dfFactory.createConnection(src, srcPort, output, null));
				// add connection is the parent network
				newConnections.add(dfFactory.createConnection(subNetworkInst,
						output, tgtVertex, connection.getTargetPort(),
						IrUtil.copy(connection.getAttributes())));
				oldConnections.add(connection);
			}
		}

		network.getConnections().addAll(newConnections);
		network.add(subNetworkInst);

		network.removeVertices(vertices);
		network.getChildren().removeAll(vertices);

		return subNetwork;
	}

	@Override
	public Void caseAction(Action action) {
		this.action = action;
		return super.caseAction(action);
	}

	/**
	 * 
	 */
	@Override
	public Void caseNetwork(Network network) {
		this.network = network;
		copier = new Copier();
		irVisitor = new IrVisitor();

		// static region detections
		StaticRegionDetector detector = new StaticRegionDetector(network);
		for (List<Instance> instances : detector.staticRegionSets()) {
			// transform the parent network and return the child network
			Network subNetwork = transformNetwork(instances);
			new Instantiator(true).doSwitch(subNetwork);
			// create the static schedule of vertices
			AbstractScheduler scheduler = new SASLoopScheduler(subNetwork);
			scheduler.schedule();

			System.out.println("Schedule of cluster" + index + " is "
					+ scheduler.getSchedule());

			// merge vertices inside a single actor
			Actor superActor = new MergerSdf(scheduler, copier)
					.doSwitch(subNetwork);

			// update parent network
			Vertex vertex = network.getChild("cluster" + index);
			Instance instance = vertex.getAdapter(Instance.class);
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
