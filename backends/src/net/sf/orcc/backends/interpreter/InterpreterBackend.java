/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.backends.IBackend;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.ir.transforms.BlockCombine;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.serialize.XDFParser;

import org.jgrapht.DirectedGraph;

/**
 * Interpreter "back-end".
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class InterpreterBackend implements IBackend {

	protected int fifoSize;

	protected String path;

	private List<InterpretedActor> actorQueue;

	@Override
	public void generateCode(String fileName, int fifoSize) throws Exception {
		// set FIFO size
		this.fifoSize = fifoSize;

		System.out.println("Starting interpretation");

		// parses top network
		Network network = new XDFParser(fileName).parseNetwork();

		// instantiate the network
		network.instantiate();

		// get network graph
		DirectedGraph<Vertex, Connection> graph = network.getGraph();

		// build an actor queue with network graph vertexes
		actorQueue = new ArrayList<InterpretedActor>();
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isActor()) {
					Actor actor = instance.getActor();
					// Apply some simplification transformations
					ActorTransformation[] transformations = {
							new DeadGlobalElimination(), 
							new BlockCombine(),
							new PhiRemoval() };

					for (ActorTransformation transformation : transformations) {
						transformation.transform(actor);
					}
					actorQueue
							.add(new InterpretedActor(instance.getId(), actor));
				}
			}
		}

		// connect network actors to FIFOs thanks to their port names
		Set<Connection> connections = graph.edgeSet();
		for (Connection connection : connections) {
			Vertex srcVertex = graph.getEdgeSource(connection);
			Vertex tgtVertex = graph.getEdgeTarget(connection);

			// for each network connection...
			if (srcVertex.isInstance() && tgtVertex.isInstance()) {
				Instance srcInst = srcVertex.getInstance();
				Instance tgtInst = tgtVertex.getInstance();

				if (srcInst.isActor() && tgtInst.isActor()) {
					Actor source = srcInst.getActor();
					Actor target = tgtInst.getActor();

					// get FIFO size (user-defined nor default)
					Integer size;
					IAttribute attr = connection
							.getAttribute(Connection.BUFFER_SIZE);
					if (attr != null && attr.getType() == IAttribute.VALUE) {
						Expression expr = ((IValueAttribute) attr).getValue();
						size = new ExpressionEvaluator().evaluateAsInteger(expr) + 1;
					} else {
						size = fifoSize;
					}

					// create the communication FIFO between source and target
					// actors
					IntFifo fifo = new IntFifo(size);
					// connect source actor to the FIFO
					Port srcPort = connection.getSource();
					if (srcPort != null) {
						srcPort.bind(fifo);
					}
					// connect target actor to the FIFO
					Port tgtPort = connection.getTarget();
					if (tgtPort != null) {
						tgtPort.bind(fifo);
					}
					System.out.println("Connecting " + source.getName() + "."
							+ srcPort.getName() + " to " + target.getName()
							+ "." + tgtPort.getName());
				}
			}
		}

		// Call network initializer main function
		initialize();

		// Call network scheduler main function
		scheduler();

	}

	/**
	 * Initialize each network actor if initializing function exists
	 */
	private void initialize() {
		// init actors of the network
		for (InterpretedActor actor : actorQueue) {
			actor.initialize();
		}
	}

	/**
	 * Schedule each actor from the network while at least one actor still
	 * active
	 * 
	 * @throws Exception
	 */
	private void scheduler() throws Exception {

		int running = 1;
		// While at least one actor is running
		while (running > 0) {
			running = 0;
			for (InterpretedActor actor : actorQueue) {
				System.out.println("Schedule actor " + actor.getName());
				running += actor.schedule();
			}
			// Manage empty and full FIFOs
			FifoManager.getInstance().emptyFifos();
		}
		System.out.println("Interpretation ended");
	}

}
