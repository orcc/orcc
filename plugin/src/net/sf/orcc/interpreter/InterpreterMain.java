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
package net.sf.orcc.interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.network.transforms.BroadcastAdder;

import org.jgrapht.DirectedGraph;

/**
 * Interpreter "back-end".
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class InterpreterMain {

	private static final InterpreterMain instance = new InterpreterMain();

	/**
	 * Returns the single instance of this interpreter
	 * 
	 * @return the single instance of this interperter
	 */
	public static InterpreterMain getInstance() {
		return instance;
	}

	protected int fifoSize;
	protected String path;
	private List<AbstractInterpretedActor> actorQueue;

	private FileOutputStream fos;
	private OutputStreamWriter out;

	private InterpreterMain() {
	}

	public void interpretNetwork(String fileName, String inputBitstream,
			int fifoSize) throws Exception {

		// set FIFO size
		this.fifoSize = fifoSize;

		System.out.println("Starting interpretation");

		// Parses top network
		Network network = new XDFParser(fileName).parseNetwork();

		// Instantiate the network
		network.instantiate();

		// Add broadcasts before connecting actors
		new BroadcastAdder().transform(network);
		// Prepare an hash table for creating broadcast interpreted actors
		HashMap<String, BroadcastActor> bcastMap = new HashMap<String, BroadcastActor>();

		// Create network communication tracing file
		File file = new File(fileName).getParentFile();
		try {
			fos = new FileOutputStream(new File(file, "traces.txt"));
			out = new OutputStreamWriter(fos, "UTF-8");
		} catch (FileNotFoundException e) {
			String msg = "file not found: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}

		// get network graph
		DirectedGraph<Vertex, Connection> graph = network.getGraph();

		// connect network actors to FIFOs thanks to their port names
		Set<Connection> connections = graph.edgeSet();
		for (Connection connection : connections) {
			Vertex srcVertex = graph.getEdgeSource(connection);
			Vertex tgtVertex = graph.getEdgeTarget(connection);

			// for each network connection...
			if (srcVertex.isInstance() && tgtVertex.isInstance()) {
				Instance srcInst = srcVertex.getInstance();
				Instance tgtInst = tgtVertex.getInstance();

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
				CommunicationFifo fifo;
				if (tgtInst.getId().equals("display")) {
					fifo = new CommunicationFifo(size, out);
				} else {
					fifo = new CommunicationFifo(size, null);
				}
				// connect source actor to the FIFO
				Port srcPort = connection.getSource();
				if (srcPort != null) {
					srcPort.bind(fifo);
					fifo.setSource(srcPort);
					if (srcInst.isBroadcast()) {
						// Broadcast actor to be explicitly connected to its
						// port
						BroadcastActor bcastActor = bcastMap.get(srcInst
								.getId());
						if (bcastActor == null) {
							bcastActor = new BroadcastActor(srcInst.getId(),
									null);
							bcastMap.put(srcInst.getId(), bcastActor);
						}
						bcastActor.setOutport(srcPort);
					}
				}
				// connect target actor to the FIFO
				Port tgtPort = connection.getTarget();
				if (tgtPort != null) {
					tgtPort.bind(fifo);
					fifo.setTarget(tgtPort);
					if (tgtInst.isBroadcast()) {
						// Broadcast actor to be explicitly connected to its
						// ports
						BroadcastActor bcastActor = bcastMap.get(tgtInst
								.getId());
						if (bcastActor == null) {
							bcastActor = new BroadcastActor(tgtInst.getId(),
									null);
							bcastMap.put(tgtInst.getId(), bcastActor);
						}
						bcastActor.setInport(tgtPort);
					}
				}

				/*
				 * System.out.println("Connecting " + srcInst.getId() + "." +
				 * srcPort.getName() + " to " + tgtInst.getId() + "." +
				 * tgtPort.getName());
				 */
			}
		}

		// Build an actor queue with network graph vertexes
		actorQueue = new ArrayList<AbstractInterpretedActor>();
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isActor()) {
					Actor actor = instance.getActor();
					if ("source".equals(actor.getName())) {
						actorQueue.add(new SourceActor(instance.getId(), actor,
								inputBitstream));
					} else if ("display".equals(actor.getName())) {
						actorQueue
								.add(new DisplayActor(instance.getId(), actor));
					} else {
						// Apply some simplification transformations
						ActorTransformation[] transformations = {
								new DeadGlobalElimination(), new PhiRemoval() };

						for (ActorTransformation transformation : transformations) {
							transformation.transform(actor);
						}
						actorQueue.add(new InterpretedActor(instance.getId(),
								actor));
					}
				} else if (instance.isBroadcast()) {
					actorQueue.add(bcastMap.get(instance.getId()));
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
	 * 
	 */
	private void initialize() {
		// init actors of the network
		for (AbstractInterpretedActor actor : actorQueue) {
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
			for (AbstractInterpretedActor actor : actorQueue) {
				// System.out.println("Schedule actor " + actor.getName());
				running += actor.schedule();
			}
		}
		System.out.println("Interpretation ended");
	}

}
