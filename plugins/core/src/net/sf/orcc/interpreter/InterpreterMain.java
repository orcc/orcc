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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.transforms.DeadCodeElimination;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.DeadVariableRemoval;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.network.transforms.BroadcastAdder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.jgrapht.DirectedGraph;

/**
 * RVC-CAL interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class InterpreterMain extends Thread {

	/**
	 * Interpreter automaton control
	 */
	public enum InterpreterState {
		CONFIGURED, IDLE, READY, RUNNING, SUSPENDED, STEPPING, TERMINATED
	}

	private List<AbstractInterpretedActor> actorQueue;

	private List<CommunicationFifo> fifoList;
	private IProgressMonitor monitor;

	/**
	 * Options
	 */
	// private int fifoSize;
	// private boolean enableTraces;
	private String networkFilename;
	/**
	 * Associated system objects
	 */
	private OrccProcess process;

	/**
	 * The utility class that makes us able to support bound properties. This is
	 * used for easy "debug events" exchange.
	 */
	private PropertyChangeSupport propertyChange;
	private InterpreterState state = InterpreterState.IDLE;
	private String stimulusFilename;

	/**
	 * Executable actor network
	 */
	private List<DebugThread> threadQueue;

	/**
	 * Add the listener <code>listener</code> to the registered listeners.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to add.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}

	/**
	 * Ask each network actor for closing
	 * 
	 */
	private void close() {
		// close actors of the network
		for (AbstractInterpretedActor actor : actorQueue) {
			actor.close();
		}
		// Close network FIFOs
		for (CommunicationFifo fifo : fifoList) {
			fifo.close();
		}
	}

	/**
	 * XDF network transformations : - flatten - set constant actors parameters
	 * - if required, try to merge static actors clusters
	 */
	public static boolean merge = false;

	private void networkTransformations(Network network) throws OrccException {
		// Flatten the hierarchical network
		network.flatten();
		// Closing actors : set parameters constant equal to XDF user's
		// parameters
		// network.closeActors();
		// Try to merge some actors
		if (merge) {
			network.classifyActors();
			network.normalizeActors();
			network.mergeActors();
		}
		// Add broadcasts before connecting actors
		new BroadcastAdder().transform(network);
	}

	/**
	 * Configure interpreter with parameters for a specific network simulation
	 * 
	 * @param inputNetwork
	 * @param fifoSize
	 * @param inputStimulus
	 * @param enableTraces
	 * @throws Exception
	 */
	public synchronized void configNetwork(String inputNetwork, int fifoSize,
			String inputStimulus, boolean enableTraces, String outputFolder)
			throws Exception {
		// Store "input" configuration
		// this.enableTraces = enableTraces;
		this.networkFilename = inputNetwork;
		this.stimulusFilename = inputStimulus;
		// this.fifoSize = fifoSize;

		// Parses top network
		Network network = new XDFParser(networkFilename).parseNetwork();

		// Instantiate the network
		network.instantiate(outputFolder);

		// Apply network transformations
		networkTransformations(network);

		// Prepare an hash table for creating broadcast interpreted actors
		HashMap<String, BroadcastActor> bcastMap = new HashMap<String, BroadcastActor>();

		// Get network graph...
		DirectedGraph<Vertex, Connection> graph = network.getGraph();
		// ...and connect network actors to FIFOs thanks to their port names
		Set<Connection> connections = graph.edgeSet();
		fifoList = new ArrayList<CommunicationFifo>();
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
				Port srcPort = connection.getSource();
				Port tgtPort = connection.getTarget();
				CommunicationFifo fifo = new CommunicationFifo(size,
						enableTraces, outputFolder, srcInst.getId() + "_"
								+ srcPort.getName());
				fifoList.add(fifo);
				// connect source actor to the FIFO
				if (srcPort != null) {
					srcPort.bind(fifo);
					if (srcInst.isBroadcast()) {
						// Broadcast actor to be explicitly connected to its
						// port
						BroadcastActor bcastActor = bcastMap.get(srcInst
								.getId());
						if (bcastActor == null) {
							bcastActor = new BroadcastActor(srcInst.getId(),
									srcInst.getActor());
							bcastMap.put(srcInst.getId(), bcastActor);
						}
						bcastActor.setOutport(srcPort);
					}
				}
				// connect target actor to the FIFO
				if (tgtPort != null) {
					tgtPort.bind(fifo);
					if (tgtInst.isBroadcast()) {
						// Broadcast actor to be explicitly connected to its
						// ports
						BroadcastActor bcastActor = bcastMap.get(tgtInst
								.getId());
						if (bcastActor == null) {
							bcastActor = new BroadcastActor(tgtInst.getId(),
									srcInst.getActor());
							bcastMap.put(tgtInst.getId(), bcastActor);
						}
						bcastActor.setInport(tgtPort);
					}
				}

				/*
				 * process.write("Connecting " + srcInst.getId() + "." +
				 * srcPort.getName() + " to " + tgtInst.getId() + "." +
				 * tgtPort.getName() + "\n");
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
								stimulusFilename));
					} else if ("display".equals(actor.getName())) {
						actorQueue.add(new DisplayActor(instance.getId(),
								actor, process));
					} else {
						// Apply some simplification transformations
						ActorTransformation[] transformations = {
								new DeadGlobalElimination(),
								new DeadCodeElimination(),
								new DeadVariableRemoval(), new PhiRemoval() };

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

		// Create interpreter debug threads from actor queue
		threadQueue = new ArrayList<DebugThread>();
		for (AbstractInterpretedActor actor : actorQueue) {
			threadQueue.add(new DebugThread(this, actor, monitor));
		}

		// Check interpreter completely configured
		if (monitor != null) {
			this.state = InterpreterState.CONFIGURED;
		}
	}

	/**
	 * Configure the interpreter with associated system/debug objects
	 * 
	 * @param process
	 * @param monitor
	 */
	public synchronized void configSystem(OrccProcess process, IProgressMonitor monitor) {
		// Orcc debug model "host" process
		this.process = process;
		// Progress monitor for user cancel
		this.monitor = monitor;
		// Property change support creation for sending interpreter events
		propertyChange = new PropertyChangeSupport(this);
		// Check interpreter completely configured
		if (actorQueue != null) {
			this.state = InterpreterState.CONFIGURED;
		}
	}

	/**
	 * This methods calls
	 * {@link PropertyChangeSupport#firePropertyChange(String, Object, Object)}
	 * on the underlying {@link PropertyChangeSupport} without updating the
	 * value of the property <code>propertyName</code>. This method is
	 * particularly useful when a property should be fired regardless of the
	 * previous value (in case of undo/redo for example, when a same object is
	 * added, removed, and added again).
	 * 
	 * @param propertyName
	 *            The name of the property concerned.
	 * @param oldValue
	 *            The old value of the property.
	 * @param newValue
	 *            The new value of the property.
	 */
	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Provides the name of the currently configured XDF network
	 * 
	 * @return XDF network name
	 */
	public String getNetworkName() {
		return new File(networkFilename).getName();
	}

	/**
	 * Provides the interpreter threads corresponding to the RVC-CAL actors of
	 * currently configured XDF network
	 * 
	 * @return A list of InterpreterThread debugging threads
	 */
	public List<DebugThread> getThreads() {
		return threadQueue;
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
	 * Remove the listener listener from the registered listeners.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}

	/**
	 * Start or restart all network actors
	 */
	public synchronized void resumeAll() {
		if ((state == InterpreterState.READY)
				|| (state == InterpreterState.SUSPENDED)
				|| (state == InterpreterState.STEPPING)) {
			state = InterpreterState.RUNNING;
			for (DebugThread thread : threadQueue) {
				thread.resume();
			}
			firePropertyChange("resumed client", null, null);
		}
	}

	@Override
	public void run() {
		// Wait for the monitor to be set (for user cancel checking)
		while (state != InterpreterState.CONFIGURED)
			;
		// Then wait for the end of the process
		while (state != InterpreterState.TERMINATED) {
			// Asynchronous user cancel
			if (monitor.isCanceled()) {
				terminate();
			}
			switch (state) {
			case IDLE:
				// May not occur...
				terminate();
				break;
			case CONFIGURED:
				// Both running workspace and actors network have been
				// configured, call the initialization function of each actor
				// and get ready for starting simulation
				initialize();
				state = InterpreterState.READY;
				firePropertyChange("started", null, null);
				break;
			case READY:
				// Wait for RESUME
				break;
			case RUNNING:
				// Continue running all threads
				// if (scheduleThreads() <= 0) {
				if (scheduleThreads() <= 0) {
					terminate();
				}
				break;
			case SUSPENDED:
			case STEPPING:
				// Wait for STEP
				break;
			}
		}
	}

	/**
	 * Schedule each actor from the network and return the number of alive
	 * actors
	 * 
	 */
	private int scheduleActors() {
		int nbRunningActors = 0;
		for (AbstractInterpretedActor actor : actorQueue) {
			// process.write("Schedule actor " + actor.getName() + "\n");
			nbRunningActors += actor.schedule();
		}
		return nbRunningActors;
	}

	/**
	 * Schedule each debug thread (corresponding to network actors) if any event
	 * is pending or no thread is suspended and return the number of active
	 * threads
	 * 
	 */
	private int scheduleThreads() {
		int nbRunningActors = 0;
		for (DebugThread thread : threadQueue) {
			nbRunningActors += thread.run();
			if (thread.isSuspended()) {
				state = InterpreterState.SUSPENDED;
			}
		}
		return nbRunningActors;
	}

	/**
	 * Simulates the network until the end of application
	 */
	public synchronized void simulate() {
		initialize();
		while (!monitor.isCanceled() && (scheduleActors() > 0))
			;
		close();
	}

	/**
	 * Schedule next schedulable action for each actor of the network
	 */
	public synchronized void stepAll() {
		List<DebugThread> steppingThreads = new ArrayList<DebugThread>();
		int nbRunningActors = 0;
		if (state == InterpreterState.SUSPENDED) {
			// Execute actors until suspended action(s) is/are schedulable
			do {
				for (DebugThread thread : threadQueue) {
					if (thread.isStepping()) {
						steppingThreads.add(thread);
					}
					nbRunningActors += thread.run();
				}
			}while ((steppingThreads.size() == 0) && (nbRunningActors > 0));
			state = InterpreterState.STEPPING;
		}else if (state == InterpreterState.STEPPING) {
			int status;
			for (DebugThread thread : steppingThreads) {
				status = thread.run();
				if (status > -1) {
					nbRunningActors++;
				}
				if (status == 1) {
					steppingThreads.remove(thread);
				}
			}
			if (steppingThreads.size() == 0) {
				state = InterpreterState.SUSPENDED;
			}
		}

		if (nbRunningActors == 0) {
			terminate();
		}else {
			firePropertyChange("resumed step", null, null);
		}
	}

	/**
	 * Suspend all network actors
	 */
	public synchronized void suspendAll() {
		if (state == InterpreterState.RUNNING) {
			state = InterpreterState.SUSPENDED;
			for (DebugThread thread : threadQueue) {
				thread.suspendFromInterpreter();
			}
			firePropertyChange("suspended client", null, null);
		}
	}

	/**
	 * Set a breakpoint in a specific actor
	 */
	public void set_breakpoint(String descriptor, int lineNumber) {
		for (DebugThread thread : threadQueue) {
			if (descriptor.startsWith(thread.getName())) {
				thread.set_breakpoint(lineNumber);
			}
		}
	}

	/**
	 * Clear a breakpoint from a specific actor
	 */
	public void clear_breakpoint(String descriptor, int lineNumber) {
		for (DebugThread thread : threadQueue) {
			if (descriptor.startsWith(thread.getName())) {
				thread.clear_breakpoint(lineNumber);
			}
		}
	}

	/**
	 * Terminate the interpretation of the current actors network
	 */
	public synchronized void terminate() {
		for (DebugThread thread : threadQueue) {
			thread.terminate();
		}
		close();
		state = InterpreterState.TERMINATED;
		firePropertyChange("terminated", null, null);
	}

}
