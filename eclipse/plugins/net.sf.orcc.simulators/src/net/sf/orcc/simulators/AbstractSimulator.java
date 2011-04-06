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
package net.sf.orcc.simulators;

import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.INPUT_STIMULUS;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.serialize.IRParser;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.util.ActorVisitor;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.network.transformations.BroadcastAdder;
import net.sf.orcc.plugins.simulators.Simulator;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.jgrapht.DirectedGraph;

public abstract class AbstractSimulator implements Simulator {

	/**
	 * Simulator control message. Consists of an event ID and message data given
	 * as an object.
	 * 
	 * @author plagalay
	 * 
	 */
	public class SimulatorMessage {
		public Object[] data;

		public SimulatorEvent event;

		public SimulatorMessage(SimulatorEvent event, Object[] data) {
			this.event = event;
			this.data = data;
		}
	}

	/**
	 * For the moment, merge of actors is not possible for any simulator. This
	 * flag should be set later through the launch configuration with specific
	 * simulators checkbox option.
	 */
	public static boolean merge = false;

	/**
	 * the configuration used to launch this back-end.
	 */
	private ILaunchConfiguration configuration = null;

	/**
	 * Indicate to the simulator implementation the we are in debug mode.
	 */
	protected boolean debugMode;

	/**
	 * default FIFO size.
	 */
	protected int fifoSize;

	/**
	 * Flag indicating the simulator is currently stepping
	 */
	private boolean isStepping = false;

	/**
	 * Simulator control messages (infinite) queue.
	 */
	private Queue<SimulatorMessage> messageQueue;

	/**
	 * Monitor associated to the simulator execution. Used for user
	 * cancellation.
	 */
	protected IProgressMonitor monitor = null;

	/**
	 * Master caller associated process for console I/O access.
	 */
	protected OrccProcess process;

	/**
	 * The utility class that makes us able to support bound properties. This is
	 * used for easy "debug events" exchange.
	 */
	private PropertyChangeSupport propertyChange;

	/**
	 * Hash table containing actors instances from the network to be simulated.
	 * Key = instance ID; Value = a simulator actor instance implementing
	 * SimuActor interface.
	 */
	protected Map<String, SimuActor> simuActorsMap;

	/**
	 * Simulator current automaton state, initialized to "IDLE" by default
	 */
	private SimulatorState state = SimulatorState.IDLE;

	/**
	 * input stimulus file name
	 */
	protected String stimulusFile;

	protected List<String> vtlFolders;

	/**
	 * input XDF network file name
	 */
	protected String xdfFile;

	public AbstractSimulator() {
		messageQueue = new LinkedList<SimulatorMessage>();
		simuActorsMap = new HashMap<String, SimuActor>();
	}

	@Override
	final public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}

	/**
	 * Clear a breakpoint related to one or several instances according to the
	 * actor source file and the line number.
	 * 
	 * @param actorFileName
	 * @param lineNumber
	 */
	private void clearBreakpoint(String actorName, int lineNumber) {
		for (Entry<String, SimuActor> entry : simuActorsMap.entrySet()) {
			SimuActor instance = entry.getValue();
			if (instance.getActorName().equals(actorName)) {
				instance.clearBreakpoint(lineNumber);
			}
		}
	}

	/**
	 * Close the network
	 * 
	 */
	abstract protected void closeNetwork();

	@Override
	final public void configure(OrccProcess process, IProgressMonitor monitor,
			boolean debugMode) {
		this.state = SimulatorState.IDLE;
		this.process = process;
		this.monitor = monitor;
		this.debugMode = debugMode;
		try {
			// Parse XDF file, do some transformations and return the
			// graph corresponding to the flat network instantiation.
			DirectedGraph<Vertex, Connection> graph = getGraphFromNetwork();
			// Instantiate simulator actors from the graph
			instantiateNetwork(graph);
			// Build the network according to the specified topology.
			connectNetwork(graph);
			initializeNetwork();
		} catch (OrccException e) {
			throw new OrccRuntimeException(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new OrccRuntimeException(e.getMessage());
		}
	}

	/**
	 * Connect two actors instances from the network together.
	 * 
	 * @param source
	 *            : the source actor
	 * @param srcPort
	 *            : source actor's output port
	 * @param target
	 *            : the target actor
	 * @param tgtPort
	 *            : target actor's input port
	 * @param fifoSize
	 *            : the limited size of the FIFO
	 */
	abstract protected void connectActors(SimuActor source, Port srcPort,
			SimuActor target, Port tgtPort, int fifoSize);

	/**
	 * Visit the network graph for building the required topology. Edges of the
	 * graph correspond to the connections between the actors. These connections
	 * should be implemented as FIFOs of specific size as defined in the CAL
	 * model or a common default size.
	 * 
	 * @param graph
	 */
	public void connectNetwork(DirectedGraph<Vertex, Connection> graph) {
		// Get edges from the graph has actors point-to-point connections.
		Set<Connection> connections = graph.edgeSet();
		// Loop over the connections and ask for the source and target actors
		// connection through specified I/O ports.
		for (Connection connection : connections) {
			Vertex srcVertex = graph.getEdgeSource(connection);
			Vertex tgtVertex = graph.getEdgeTarget(connection);

			if (srcVertex.isInstance() && tgtVertex.isInstance()) {
				Instance srcInst = srcVertex.getInstance();
				Instance tgtInst = tgtVertex.getInstance();

				// get FIFO size (user-defined nor default)
				int size;
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

				// connect source and target actors
				if ((srcPort != null) && (tgtPort != null)) {
					// Connect actors through specific simulator method
					connectActors(simuActorsMap.get(srcInst.getId()), srcPort,
							simuActorsMap.get(tgtInst.getId()), tgtPort, size);
					// process.write("Connecting " + srcInst.getId() + "."
					// + srcPort.getName() + " to " + tgtInst.getId()
					// + "." + tgtPort.getName() + "\n");
				}
			}
		}
	}

	/**
	 * Create a broadcast actor. Broadcasts have been detected by the XDF parser
	 * and instantiated in the directed graph.
	 */
	abstract protected SimuActor createSimuActorBroadcast(String instanceId,
			int numOutputs, boolean isBool);

	/**
	 * Create an actor instance for the simulation. This actor must implement
	 * the SimuActor interface.
	 */
	abstract protected SimuActor createSimuActorInstance(String instanceId,
			Map<String, Expression> actorParameters, Actor actorIR);

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

	@Override
	public synchronized String getActorName(String instanceId) {
		return simuActorsMap.get(instanceId).getActorName();
	}

	@Override
	public synchronized List<String> getActorsInstanceIds() {
		List<String> instanceIds = new ArrayList<String>();
		for (SimuActor simuActorInstance : simuActorsMap.values()) {
			instanceIds.add(simuActorInstance.getInstanceId());
		}
		return instanceIds;
	}

	/**
	 * Returns the boolean-valued attribute with the given name. Returns the
	 * given default value if the attribute is undefined.
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the default value if no value was found.
	 * @throws OrccException
	 */
	final public boolean getAttribute(String attributeName, boolean defaultValue)
			throws OrccException {
		if (configuration == null) {
			return defaultValue;
		}

		try {
			return configuration.getAttribute(attributeName, defaultValue);
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Returns the integer-valued attribute with the given name. Returns the
	 * given default value if the attribute is undefined.
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the default value if no value was found.
	 * @throws OrccException
	 */
	final public int getAttribute(String attributeName, int defaultValue)
			throws OrccException {
		if (configuration == null) {
			return defaultValue;
		}

		try {
			return configuration.getAttribute(attributeName, defaultValue);
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Returns the string-valued attribute with the given name. Returns the
	 * given default value if the attribute is undefined.
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 * @param defaultValue
	 *            the value to use if no value is found
	 * @return the value or the default value if no value was found.
	 * @throws OrccException
	 */
	final public String getAttribute(String attributeName, String defaultValue)
			throws OrccException {
		if (configuration == null) {
			return defaultValue;
		}

		try {
			return configuration.getAttribute(attributeName, defaultValue);
		} catch (CoreException e) {
			throw new OrccException("could not read configuration", e);
		}
	}

	/**
	 * Get the instance ID of the actor that has just hit a breakpoint.
	 * 
	 * @return String : breakpoint actor's instance ID
	 */
	abstract protected String getBreakpointActorInstanceId();

	/**
	 * Get the line number of the current hit breakpoint.
	 * 
	 * @return Integer : the line number of the current breakpoint
	 */
	abstract protected Integer getBreakpointLineNumber();

	/**
	 * Util function for getting actor or network names from file names.
	 * 
	 * @param filename
	 * @return the filename without complete path abnd without the extension
	 */
	private String getFilenameWithoutExtension(String filename) {
		File file = new File(filename);
		int index = file.getName().lastIndexOf('.');
		if ((index > 0) && (index <= file.getName().length() - 2)) {
			return file.getName().substring(0, index);
		}
		return file.getName();
	}

	/**
	 * Parse XDF input file and build a directed graph representing the network
	 * with actors as vertexes and FIFO connections as edges. We then
	 * instantiate the network from JSON descriptors in order to do some
	 * transformations on the model. The modified (transformed) graph is then
	 * returned.
	 * 
	 * @return A directed graph representing the flat network of connected
	 *         actors instances
	 * @throws OrccException
	 */
	private DirectedGraph<Vertex, Connection> getGraphFromNetwork()
			throws OrccException {
		Network network = new XDFParser(xdfFile).parseNetwork();

		// Instantiate the network
		network.instantiate(vtlFolders);
		Network.clearActorPool();

		// Flatten the hierarchical network
		network.flatten();

		// Add broadcasts before connecting actors
		new BroadcastAdder().transform(network);

		return network.getGraph();
	}

	@Override
	public synchronized String getNetworkName() {
		return getFilenameWithoutExtension(xdfFile);
	}

	@Override
	public synchronized SimulatorState getSimulatorState() {
		return state;
	}

	@Override
	public synchronized DebugStackFrame getStackFrame(String instanceID) {
		return simuActorsMap.get(instanceID).getStackFrame();
	}

	/**
	 * Initialize the network
	 */
	abstract protected void initializeNetwork();

	/**
	 * Visit the network graph for instantiating the vertexes (actors we want to
	 * simulate). Created actor instances are stored in the simuActorsMap.
	 * 
	 * @param graph
	 * @throws OrccException
	 * @throws FileNotFoundException
	 */
	public void instantiateNetwork(DirectedGraph<Vertex, Connection> graph)
			throws OrccException, FileNotFoundException {
		// Loop over the graph vertexes and get instances definition for
		// instantiating the network to simulate.
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				SimuActor simuActorInstance = null;
				if (instance.isActor()) {
					// Create a new actor instance for interpretation
					InputStream in = new FileInputStream(instance.getFile());
					Actor actorIR = new IRParser().parseActor(in);
					// Apply some simplification transformations
					ActorVisitor[] transformations = {
							new DeadGlobalElimination(),
							new DeadCodeElimination(),
							new DeadVariableRemoval() };
					for (ActorVisitor transformation : transformations) {
						transformation.visit(actorIR);
					}

					simuActorInstance = createSimuActorInstance(
							instance.getId(), instance.getParameters(), actorIR);
				} else if (instance.isBroadcast()) {
					// Broadcast simulated actor
					simuActorInstance = createSimuActorBroadcast(
							instance.getId(), instance.getBroadcast()
									.getNumOutputs(), instance.getBroadcast()
									.getInput().getType().isBool());
				}
				// Register the simulated actor instance to the hash map.
				if (simuActorInstance != null) {
					simuActorsMap.put(instance.getId(), simuActorInstance);
				}
			}
		}
	}

	@Override
	public synchronized boolean isStepping() {
		return isStepping;
	}

	@Override
	public synchronized void message(SimulatorEvent event, Object[] data) {
		messageQueue.add(new SimulatorMessage(event, data));
	}

	/**
	 * Start (again) the actor network before going to RUNNING state
	 * 
	 */
	abstract protected int resumeNetwork();

	/**
	 * Main simulator thread entry : implements the simulation FSM.
	 */
	@Override
	public void run() throws OrccRuntimeException {
		// Loop until the simulator has terminated
		while (state != SimulatorState.TERMINATED) {
			if (process.getProgressMonitor().isCanceled()) {
				return;
			}

			// Synchronous behavior : check propertyChanges (events)
			// for transitions and apply current state action
			SimulatorMessage msg = messageQueue.peek();
			// Synchronous events received at any state :
			if (msg != null) {
				switch (msg.event) {
				case TERMINATE:
					messageQueue.remove();
					terminate();
					break;
				}
			}
			// FSM :
			switch (state) {
			//
			// IDLE state : wait for the simulator to be configured. Go to
			// CONFIGURED state when CONFIGURE event has been received.
			//
			case IDLE:
				isStepping = false;
				if ((msg != null) && (msg.event == SimulatorEvent.START)) {
					// Check debug mode is activated
					messageQueue.remove();
					if (debugMode) {
						state = SimulatorState.CONFIGURED;
					} else {
						state = SimulatorState.RUNNING;
					}
				}
				// TODO : throw OrccException if CONFIGURE message data is
				// not valid
				break;
			//
			// CONFIGURED : transitory state for indicate the debug target that
			// we have started debugging mode. Automatic transition to SUSPENDED
			// state, waiting for RESUME event.
			//
			case CONFIGURED:
				state = SimulatorState.SUSPENDED;
				firePropertyChange("started", null, null);
				break;
			//
			// RUNNING : this state corresponds to an execution of the
			// entire network until every actor is waiting for input data
			// (end of the network simulation) or until the simulator has
			// been interrupted by the user (suspend, close, cancel) or a
			// breakpoint has been hit. GOTO SUSPENDED state if a SUSPEND
			// event has been received or a breakpoint has been hit. GOTO
			// TERMINATED if no more action is schedulable in the entire
			// network.
			//
			case RUNNING:
				// "Asynchronous" user cancel
				if (monitor.isCanceled()) {
					terminate();
				} else {
					// Check suspension or unchanging state events
					if (msg != null) {
						switch (msg.event) {
						case SUSPEND:
							messageQueue.remove();
							if (suspendNetwork() > 0) {
								state = SimulatorState.SUSPENDED;
								firePropertyChange("suspended client", null,
										null);
							}
							break;
						case SET_BREAKPOINT:
							messageQueue.remove();
							// TODO : check message data is valid
							setBreakpoint(
									getFilenameWithoutExtension((String) msg.data[0]),
									(Integer) msg.data[1]);
							break;
						case CLEAR_BREAKPOINT:
							messageQueue.remove();
							// TODO : check message data is valid
							clearBreakpoint(
									getFilenameWithoutExtension((String) msg.data[0]),
									(Integer) msg.data[1]);
							break;
						}
					} else {
						// Continue running the whole network of actors
						int status = runNetwork();
						if (status == -2) {
							// Breakpoint hit
							suspendNetwork();
							state = SimulatorState.SUSPENDED;
							String instanceId = getBreakpointActorInstanceId();
							Integer breakpointLineNumber = getBreakpointLineNumber();
							firePropertyChange("suspended breakpoint "
									+ breakpointLineNumber, null, instanceId);
						} else if (status <= 0) {
							terminate();
						}
					}
				}
				break;
			//
			// SUSPENDED : network simulation has been suspended. This state
			// admits events asking for stepping.
			//
			case SUSPENDED:
				// "Asynchronous" user cancel
				if (monitor.isCanceled()) {
					terminate();
				} else {
					// Wait for STEP or RESUME
					if (msg != null) {
						messageQueue.remove();
						switch (msg.event) {
						case RESUME:
							resumeNetwork();
							state = SimulatorState.RUNNING;
							firePropertyChange("resumed client", null, null);
							break;
						case SET_BREAKPOINT:
							// TODO : check message data is valid
							setBreakpoint(
									getFilenameWithoutExtension((String) msg.data[0]),
									(Integer) msg.data[1]);
							break;
						case CLEAR_BREAKPOINT:
							// TODO : check message data is valid
							clearBreakpoint(
									getFilenameWithoutExtension((String) msg.data[0]),
									(Integer) msg.data[1]);
							break;
						case STEP_ALL:
							firePropertyChange("resumed step", null, null);
							isStepping = true;
							if (stepNetwork() <= 0) {
								terminate();
							} else {
								// Suspended again
								isStepping = false;
								firePropertyChange("suspended step", null, null);
							}
							break;
						case STEP_INTO:
						case STEP_OVER:
						case STEP_RETURN:
							// Get step parameters
							String instanceId = (String) msg.data[0];
							int status = 1;
							// Send resumed event to debug target
							firePropertyChange("resumed step", null, instanceId);
							isStepping = true;
							// Run required step command
							if (msg.event == SimulatorEvent.STEP_INTO)
								status = stepInto(instanceId);
							else if (msg.event == SimulatorEvent.STEP_OVER)
								status = stepOver(instanceId);
							else if (msg.event == SimulatorEvent.STEP_RETURN)
								stepReturn(instanceId);
							// Check returned status
							if (status <= 0) {
								terminate();
							} else {
								// Suspended again
								isStepping = false;
								firePropertyChange("suspended step", null,
										instanceId);
							}
							break;
						}
					}
				}
				break;
			}
		}
		// Reset the state to IDLE for next launch
		state = SimulatorState.IDLE;
	}

	/**
	 * Simulate the actor network until SUSPEND command or BREAKPOINT hit.
	 * 
	 * @param debugMode
	 * @return
	 */
	abstract protected int runNetwork();

	/**
	 * Set a breakpoint related to one or several instances according to the
	 * actor source file and the line number.
	 * 
	 * @param actorFileName
	 * @param lineNumber
	 */
	private void setBreakpoint(String actorName, int lineNumber) {
		for (Entry<String, SimuActor> entry : simuActorsMap.entrySet()) {
			SimuActor instance = entry.getValue();
			if (instance.getActorName().equals(actorName)) {
				instance.setBreakpoint(lineNumber);
			}
		}
	}

	@Override
	final public void setLaunchConfiguration(ILaunchConfiguration configuration) {
		this.configuration = configuration;
		try {
			// Get configuration attributes
			stimulusFile = configuration.getAttribute(INPUT_STIMULUS, "");

			String name = configuration.getAttribute(PROJECT, "");
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = root.getProject(name);

			vtlFolders = OrccUtil.getOutputPaths(project);

			fifoSize = configuration.getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
			xdfFile = configuration.getAttribute(XDF_FILE, "");
		} catch (CoreException e) {
			throw new OrccRuntimeException(e.getMessage());
		}
		// Property change support creation for sending interpreter events
		propertyChange = new PropertyChangeSupport(this);
	}

	/**
	 * Step into a specific actor instance.
	 * 
	 */
	abstract protected int stepInto(String instanceId);

	/**
	 * Step over the whole network of actors.
	 * 
	 */
	abstract protected int stepNetwork();

	/**
	 * Step over a specific actor instance.
	 * 
	 */
	abstract protected int stepOver(String instanceId);

	/**
	 * Step return from specific actor instance.
	 * 
	 */
	abstract protected void stepReturn(String instanceId);

	/**
	 * Suspend the network simulation until next RESUME or STEP command.
	 */
	abstract protected int suspendNetwork();

	/**
	 * End of simulation reached or required by user
	 */
	private void terminate() {
		closeNetwork();
		state = SimulatorState.TERMINATED;
		firePropertyChange("terminated", null, null);
	}
}
