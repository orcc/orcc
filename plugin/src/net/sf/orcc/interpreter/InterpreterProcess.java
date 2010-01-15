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
import org.eclipse.ui.console.IOConsoleOutputStream;

public class InterpreterProcess extends Thread implements IProcess {

	private String state;
	private int nbOfSuspendedThreads = 0;
	private boolean threadCommand = false;

	/**
	 * The hash map that stores property values.
	 */
	private Map<String, Object> properties;

	/**
	 * The utility class that makes us able to support bound properties.
	 */
	private PropertyChangeSupport propertyChange;

	private IProgressMonitor monitor;
	private InterpreterMain interpreter;
	private Map<String, InterpreterThread> threads;
	private String networkName;

	// private final Retransmitter outputMonitor;
	// private final IStreamsProxy streamsProxy = new IStreamsProxy() {
	// public IStreamMonitor getErrorStreamMonitor() {
	// return NullStreamMonitor.INSTANCE;
	// }
	//
	// public IStreamMonitor getOutputStreamMonitor() {
	// return outputMonitor;
	// }
	//
	// public void write(String input) {
	// // ignore
	// }
	// };

	public class InterpreterStackFrame {
		public String actorFilename;
		public Integer codeLine;
		public Map<String, Object> stateVars;
		public String fsmState;
		public String currentAction;
		public Boolean isSchedulable;
	}

	public class InterpreterThread {
		private AbstractInterpretedActor actor;
		private InterpreterProcess process;
		private boolean threadSuspended = true;
		private boolean threadStepping = false;

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

		public int run() {
			Integer actorStatus = 0;
			if ((!threadSuspended) || (threadStepping)) {
				actorStatus = actor.schedule();
				
				
				
				if (threadStepping) {
					threadStepping = false;
					firePropertyChange("suspended step", null, actor.name);
				}
			}
			return actorStatus;
		}

		public void resume() {
			threadSuspended = false;
			nbOfSuspendedThreads--;
			threadCommand = true;
			firePropertyChange("resumed client", null, actor.name);
		}

		public void suspend() {
			threadSuspended = true;
			nbOfSuspendedThreads++;
			firePropertyChange("suspended client", null, actor.name);
		}

		public void stepOver() {
			threadStepping = true;
			threadCommand = true;
			firePropertyChange("resumed step", null, actor.name);
		}

		public boolean isTerminated() {
			return process.isTerminated();
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
			if ((!actor.name.equalsIgnoreCase("source"))
					&& (!actor.name.equalsIgnoreCase("display"))) {
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
	 * @param monitor
	 * @param configuration
	 */
	// public InterpreterProcess(IProgressMonitor monitor,
	// ILaunchConfiguration configuration, Retransmitter retransmitter) {
	// this.outputMonitor = retransmitter;
	public InterpreterProcess(IProgressMonitor monitor,
			ILaunchConfiguration configuration, IOConsoleOutputStream out) {

		// Container for receiving interpreter events
		propertyChange = new PropertyChangeSupport(this);
		properties = new HashMap<String, Object>();
		// Interpreter global state initialization
		state = "IDLE";
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
							fifoSize, out);
			threads = new HashMap<String, InterpreterThread>();
			int threadIdx = 0;
			for (AbstractInterpretedActor actor : actorQueue) {
				InterpreterThread thread = new InterpreterThread(this, actor);
				threads.put(actor.name, thread);
				threadIdx++;
				nbOfSuspendedThreads++;
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
					firePropertyChange("started", null, null);
					// Nominal running
				} else if (state.equals("RUNNING")) {
					if ((nbOfSuspendedThreads == 0) || (threadCommand)) {
						threadCommand = false;
						try {
							if (runThreads() <= 0) {
								terminate();
							}
						} catch (DebugException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (state.equals("STEPPING")) {
					if ((nbOfSuspendedThreads == 0) || (threadCommand)) {
						threadCommand = false;
						try {
							if (runThreads() <= 0) {
								terminate();
							}
						} catch (DebugException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						state = "SUSPENDED";
						firePropertyChange("suspended step", null, null);
					}
				}
			}
		}
	}

	private int runThreads() {
		int running = 0;
		for (InterpreterThread thread : threads.values()) {
			int threadStatus = thread.run();
			if (threadStatus < 0) {
				return -1;
			} else {
				running += threadStatus;
			}
		}
		return running;
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
			for (InterpreterThread thread : threads.values()) {
				thread.resume();
			}
			firePropertyChange("resumed client", null, null);
		} else if (state.equals("SUSPENDED")) {
			state = "RUNNING";
			for (InterpreterThread thread : threads.values()) {
				thread.resume();
			}
			firePropertyChange("resumed client", null, null);
		}
	}

	/**
	 * "suspend" debugger command
	 */
	public void suspendInterpreter() {
		state = "SUSPENDED";
		for (InterpreterThread thread : threads.values()) {
			thread.suspend();
		}
		firePropertyChange("suspended client", null, null);
	}

	/**
	 * "step" debugger command
	 */
	public void step() {
		if (state.equals("STARTED")) {
			// Call network initializer main function
			interpreter.initialize();
			state = "STEPPING";
			for (InterpreterThread thread : threads.values()) {
				thread.stepOver();
			}
			firePropertyChange("resumed step", null, null);
		} else if (state.equals("SUSPENDED")) {
			state = "STEPPING";
			for (InterpreterThread thread : threads.values()) {
				thread.stepOver();
			}
			firePropertyChange("resumed step", null, null);
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
	 * "get thread stack" debugger command
	 */
	public InterpreterStackFrame getStackFrame(String threadId) {
		return threads.get(threadId).getStackFrame();
	}

	//
	// IProcess methods
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
		// return streamsProxy;
		return null;
	}

	@Override
	public void setAttribute(String key, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	@SuppressWarnings("unchecked")
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

	// private static class NullStreamMonitor implements IStreamMonitor {
	// public void addListener(IStreamListener listener) {
	// }
	//
	// public String getContents() {
	// return null;
	// }
	//
	// public void removeListener(IStreamListener listener) {
	// }
	//
	// static final NullStreamMonitor INSTANCE = new NullStreamMonitor();
	// }
	//
	// /**
	// * Responsible for getting text as {@link Writer} and retransmitting it as
	// * {@link IStreamMonitor} to whoever is interested. However in its initial
	// * state it only receives signal (the text) and saves it in a buffer. For
	// * {@link Retransmitter} to start giving the signal away one should call
	// * {@link #startFlushing} method.
	// */
	// public static class Retransmitter extends Writer implements
	// IStreamMonitor {
	// private StringWriter writer = new StringWriter();
	// private boolean isFlushing = false;
	// private final List<IStreamListener> listeners = new
	// ArrayList<IStreamListener>(
	// 2);
	//
	// public synchronized void addListener(IStreamListener listener) {
	// listeners.add(listener);
	// }
	//
	// public String getContents() {
	// return null;
	// }
	//
	// public synchronized void removeListener(IStreamListener listener) {
	// listeners.remove(listener);
	// }
	//
	// @Override
	// public synchronized void flush() {
	// if (!isFlushing) {
	// return;
	// }
	// String text = writer.toString();
	// int lastLinePos = text.lastIndexOf('\n');
	// if (lastLinePos == -1) {
	// // No full line in the buffer.
	// return;
	// }
	// String readyText = text.substring(0, lastLinePos + 1);
	// writer = new StringWriter();
	// if (lastLinePos + 1 != text.length()) {
	// String rest = text.substring(lastLinePos + 1);
	// writer.append(rest);
	// }
	// for (IStreamListener listener : listeners) {
	// listener.streamAppended(readyText, this);
	// }
	// }
	//
	// @Override
	// public synchronized void close() {
	// // do nothing
	// }
	//
	// @Override
	// public synchronized void write(char[] cbuf, int off, int len) {
	// writer.write(cbuf, off, len);
	// }
	//
	// public synchronized void startFlushing() {
	// isFlushing = true;
	// flush();
	// }
	//
	// public void processClosed() {
	//
	// }
	// }

}
