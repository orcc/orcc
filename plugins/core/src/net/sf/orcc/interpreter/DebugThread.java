package net.sf.orcc.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import net.sf.orcc.ir.Variable;

public class DebugThread {

	public class InterpreterStackFrame {
		public String actorFilename;
		public Integer codeLine;
		public String currentAction;
		public String fsmState;
		public Map<String, Object> stateVars;

		public InterpreterStackFrame() {
			actorFilename = "";
			codeLine = 0;
			stateVars = new HashMap<String, Object>();
			currentAction = "";
		}
	}

	private IProgressMonitor monitor;
	private AbstractInterpretedActor actor;
	private boolean eventPending = false;
	private InterpreterMain interpreter;
	private boolean threadTerminated = false;
	private boolean threadStepInto = false;
	private boolean threadStepping = false;
	private boolean threadSuspended = true;
	private List<Integer> breakpoints;

	public DebugThread(InterpreterMain interpreter,
			AbstractInterpretedActor actor, IProgressMonitor monitor) {
		this.monitor = monitor;
		this.actor = actor;
		this.interpreter = interpreter;
		this.breakpoints = new ArrayList<Integer>();
	}

	public AbstractInterpretedActor getActor() {
		return actor;
	}

	public String getName() {
		return actor.name;
	}

	public InterpreterStackFrame getStackFrame() {
		InterpreterStackFrame stackFrame = new InterpreterStackFrame();
		if (actor.actor != null) {
			stackFrame.actorFilename = actor.actor.getFile();
			stackFrame.codeLine = actor.getLastVisitedLocation().getStartLine();
			stackFrame.stateVars.clear();
			for (Variable stateVar : actor.actor.getStateVars()) {
				stackFrame.stateVars.put(stateVar.getName(), stateVar
						.getValue());
			}
			stackFrame.currentAction = actor.getLastVisitedAction();
			if ((actor instanceof SourceActor)
					|| (actor instanceof DisplayActor)
					|| (actor instanceof BroadcastActor)) {
				stackFrame.fsmState = "idle";
			} else {
				stackFrame.fsmState = ((InterpretedActor) actor).getFsmState();
			}
		}
		return stackFrame;
	}

	public boolean isStepping() {
		return threadStepping;
	}

	public boolean isSuspended() {
		return threadSuspended;
	}

	public void resume() {
		if (threadSuspended) {
			threadSuspended = false;
			threadStepping = false;
			eventPending = false;
			interpreter.firePropertyChange("resumed client", null, actor.name);
		}
	}

	public int run() {
		Integer actorStatus = 0;
		if ((!threadSuspended) || (threadStepping)) {
			if (threadStepping) {
//				do {
//					while (!(eventPending)) {
//						if ((monitor.isCanceled()) || 
//								(!threadSuspended) ||
//								(threadTerminated)) {
//							return 0;
//						}
//					}
					actorStatus = actor.step(threadStepInto);
					if (actorStatus > -1) {
						eventPending = false;
						interpreter.firePropertyChange("suspended step", null,
								actor.name);
					}
//				} while (actorStatus == 0);
//				if (actorStatus == 1) {
//					threadStepping = false;
//				} else {
//					actorStatus = 0;
//				}
			} else {
				// actorStatus = actor.schedule();
				do {
					actorStatus = actor.step(false);
					for (Integer breakpoint : breakpoints) {
						if ((breakpoint == actor.lastVisitedLocation
								.getStartLine())) {
							threadSuspended = true;
							threadStepping = true;
							interpreter.firePropertyChange(
									"suspended breakpoint " + breakpoint, null, actor.name);
							actorStatus = 1;
							break;
						}
					}
				} while (actorStatus == 0);
				if (actorStatus < 0) {
					actorStatus = 0;
				}
			}
		}
		return actorStatus;
	}

	public synchronized void stepInto() {
		if (!eventPending) {
			threadStepping = true;
			threadStepInto = true;
			eventPending = true;
			interpreter.firePropertyChange("resumed step", null, actor.name);
			interpreter.stepAll();
		}
	}

	public synchronized void stepOver() {
		if (!eventPending) {
			threadStepping = true;
			eventPending = true;
			interpreter.firePropertyChange("resumed step", null, actor.name);
			interpreter.stepAll();
		}
	}

	public synchronized void suspend() {
		if (!threadSuspended) {
			threadSuspended = true;
			interpreter
					.firePropertyChange("suspended client", null, actor.name);
		}
	}

	public void suspendFromInterpreter() {
		if (!threadSuspended) {
			threadSuspended = true;
		}
	}

	public void set_breakpoint(int bkpt) {
		breakpoints.add(bkpt);
	}

	public void clear_breakpoint(int bkpt) {
		breakpoints.remove(bkpt);
	}
	
	public synchronized void terminate() {
		threadTerminated = true;
	}
}
