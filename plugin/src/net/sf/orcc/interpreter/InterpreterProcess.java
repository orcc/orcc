package net.sf.orcc.interpreter;

import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_TRACES;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_STIMULUS;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.OUTPUT_FOLDER;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Variable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;

public class InterpreterProcess extends Thread implements IProcess {

	private String state;

	/**
	 * The hash map that stores property values.
	 */
	private Map<String, Object> properties;

	/**
	 * The utility class that makes us able to support bound properties.
	 */
	private PropertyChangeSupport propertyChange;

	private IProgressMonitor monitor;
	// private ILaunchConfiguration configuration;
	private InterpreterMain interpreter;
	private Map<String, InterpreterThread> threads;
	private String networkName;
	private String currentThread;

	public class InterpreterStackFrame {
		public String actorFilename;
		public Integer codeLine;
		public Map<String, Object> stateVars;
		public String fsmState;
		public String currentAction;
		public Boolean isSchedulable;

		public InterpreterStackFrame() {
		};

		public InterpreterStackFrame(String actorFilename, Integer codeLine,
				Map<String, Object> stateVars, String fsmState,
				String currentAction, Boolean isSchedulable) {
			this.actorFilename = actorFilename;
			this.codeLine = codeLine;
			this.stateVars = stateVars;
			this.fsmState = fsmState;
			this.currentAction = currentAction;
			this.isSchedulable = isSchedulable;
		}
	}

	public class InterpreterThread {
		private AbstractInterpretedActor actor;
		private InterpreterProcess process;

		public InterpreterThread(InterpreterProcess process,
				AbstractInterpretedActor actor) {
			this.actor = actor;
			this.process = process;
		}

		public String getName() {
			return actor.name;
		}

		public AbstractInterpretedActor getActor() {
			return actor;
		}

		public void resume() {
			currentThread = actor.name;
			process.resumeInterpreter();
		}

		public void suspend() {
			currentThread = actor.name;
			process.suspendInterpreter();
		}

		public void stepOver() {
			currentThread = actor.name;
			process.step();
		}

		public void stepInto() {
		}

		public InterpreterStackFrame getStackFrame() {
			InterpreterStackFrame stackFrame = new InterpreterStackFrame();

			stackFrame.actorFilename = actor.actor.getFile();
			stackFrame.codeLine = actor.getLastVisitedLocation().getStartLine();
			Map<String, Object> stateVars = new HashMap<String, Object>();
			for (Variable stateVar : actor.actor.getStateVars()) {
				stateVars.put(stateVar.getName(), stateVar.getValue());
			}
			stackFrame.stateVars = stateVars;
			stackFrame.currentAction = actor.getLastVisitedAction();
			if ((actor.name.equalsIgnoreCase("source"))
					&& (actor.name.equalsIgnoreCase("display"))) {
				stackFrame.fsmState = ((InterpretedActor) actor).getFsmState();
			} else {
				stackFrame.fsmState = "idle";
			}
			stackFrame.isSchedulable = true;

			return stackFrame;
		}
	}

	/**
	 * Interpreter process constructor
	 * 
	 * @param enableTraces
	 * @param monitor
	 * @param fileName
	 * @param inputBitstream
	 * @param fifoSize
	 */
	public InterpreterProcess(IProgressMonitor monitor,
			ILaunchConfiguration configuration) {
		// Container for receiving interpreter events
		propertyChange = new PropertyChangeSupport(this);
		properties = new HashMap<String, Object>();
		// Interpreter global state initialization
		state = "IDLE";
		currentThread = "";
		// Get launch related objects
		this.monitor = monitor;
		// this.configuration = configuration;
		// Create a new interpreter, get configuration parameters and the
		// network to be interpreted
		interpreter = InterpreterMain.getInstance();
		try {
			String inputFile = configuration.getAttribute(INPUT_FILE, "");
			String inputStimulus = configuration.getAttribute(INPUT_STIMULUS,
					"");
			String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");
			networkName = new File(inputFile).getName();
			String fileName = outputFolder + File.separator + networkName;
			int fifoSize = configuration.getAttribute(FIFO_SIZE,
					DEFAULT_FIFO_SIZE);
			boolean enableTraces = configuration.getAttribute(ENABLE_TRACES,
					DEFAULT_TRACES);
			List<AbstractInterpretedActor> actorQueue = interpreter
					.interpretNetwork(enableTraces, fileName, inputStimulus,
							fifoSize);
			threads = new HashMap<String, InterpreterThread>();
			int threadIdx = 0;
			for (AbstractInterpretedActor actor : actorQueue) {
				InterpreterThread thread = new InterpreterThread(this, actor);
				threads.put(actor.name, thread);
				threadIdx++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, InterpreterThread> getThreads() {
		return threads;
	}

	public String getNetworkName() {
		return networkName;
	}

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
	 * Returns the value of the property whose name is <code>propertyName</code>
	 * .
	 * 
	 * @param propertyName
	 *            The name of the property to retrieve.
	 * @return The value of the property.
	 */
	public Object getValue(String propertyName) {
		return properties.get(propertyName);
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
	 * Sets the value of the property whose name is <code>propertyName</code> to
	 * value <code>newValue</code>, and report the property update to any
	 * registered listeners.
	 * 
	 * @param propertyName
	 *            The name of the property to set.
	 * @param newValue
	 *            The new value of the property.
	 * @return The previous value of the property.
	 */
	public Object setValue(String propertyName, Object newValue) {
		Object oldValue = properties.put(propertyName, newValue);
		propertyChange.firePropertyChange(propertyName, oldValue, newValue);
		return oldValue;
	}

	@Override
	public void run() {
		state = "IDLE";
		while (!state.equals("TERMINATED")) {
			// Asynchronous user cancel
			if (monitor.isCanceled()) {
				try {
					terminate();
				} catch (DebugException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// Synchronization with debug model
				if (state.equals("IDLE")) {
					// Send the "started" event and wait for the resume/step
					// command
					state = "STARTED";
					firePropertyChange("started", null, currentThread);
					// Nominal running
				} else if (state.equals("RUNNING")) {
					try {
						if (interpreter.scheduler() <= 0) {
							// Error found in the model interpretation
							terminate();
						}
					} catch (DebugException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (state.equals("STEPPING")) {
					try {
						if (interpreter.scheduler() <= 0) {
							// Error found in the model interpretation
							terminate();
						}
					} catch (DebugException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					firePropertyChange("suspend step", null, currentThread);
				}
			}
		}
	}

	//
	// Debugger commands
	//

	/**
	 * "resume" debugger command
	 */
	public void resumeInterpreter() {
		if (state.equals("STARTED")) {
			// Call network initializer main function
			interpreter.initialize();
			state = "RUNNING";
			firePropertyChange("resumed client", null, currentThread);
		} else if ((state.equals("SUSPENDED")) || (state.equals("STEPPING"))) {
			state = "RUNNING";
			firePropertyChange("resumed client", null, currentThread);
		}
	}

	/**
	 * "suspend" debugger command
	 */
	public void suspendInterpreter() {
		state = "SUSPENDED";
	}

	/**
	 * "step" debugger command
	 */
	public void step() {
		if (state.equals("STARTED")) {
			// Call network initializer main function
			interpreter.initialize();
			state = "STEPPING";
			firePropertyChange("resumed step", null, currentThread);
		} else if (state.equals("STEPPING")) {
			firePropertyChange("resumed step", null, currentThread);
		} else if (state.equals("SUSPENDED")) {
			state = "STEPPING";
			firePropertyChange("resumed step", null, currentThread);
		}
	}

	/**
	 * "set breakpoint <nb>" debugger command
	 */
	public void set_breakpoint(int bkptNb) {
		// TODO
	}

	/**
	 * "clear breakpoint <nb>" debugger command
	 */
	public void clear_breakpoint(int bkptNb) {
		// TODO
	}

	/**
	 * "get stack" debugger command
	 */
	public InterpreterStackFrame getStackFrame(String threadId) {
		return threads.get(threadId).getStackFrame();
	}

	//
	// IProcess methods (unuseful ?)
	//	

	@Override
	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getExitValue() throws DebugException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILaunch getLaunch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String key, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canTerminate() {
		return !state.equals("TERMINATED");
	}

	@Override
	public boolean isTerminated() {
		return state.equals("TERMINATED");
	}

	@Override
	public void terminate() throws DebugException {
		interpreter.close();
		state = "TERMINATED";
	}

}
