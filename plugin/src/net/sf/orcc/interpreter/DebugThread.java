package net.sf.orcc.interpreter;

import java.util.HashMap;
import java.util.Map;

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

	private AbstractInterpretedActor actor;
	private boolean eventPending = false;
	private InterpreterMain interpreter;
	private boolean threadStepInto = false;
	private boolean threadStepping = false;
	private boolean threadSuspended = true;

	public DebugThread(InterpreterMain interpreter,
			AbstractInterpretedActor actor) {
		this.actor = actor;
		this.interpreter = interpreter;
	}

	public boolean eventPending() {
		return eventPending;
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
			if (stackFrame.codeLine != 0) {
				System.out.println("Location not null !");
			}
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

	public boolean isSuspended() {
		return threadSuspended;
	}

	public void resume() {
		threadSuspended = false;
		eventPending = true;
		interpreter.firePropertyChange("resumed client", null, actor.name);
	}

	public int run() {
		Integer actorStatus = 0;
		if ((!threadSuspended) || (threadStepping)) {
			if (threadStepInto) {
				while (threadStepInto) {
					while (!eventPending)
						;
					if (actor.step()) {
						threadStepInto = false;
						threadStepping = false;
						actorStatus = 1;
					}
					eventPending = false;
					interpreter.firePropertyChange("suspended step", null,
							actor.name);
				}
			} else {
				actorStatus = actor.schedule();
				eventPending = false;
				if (threadStepping) {
					threadStepping = false;
					interpreter.firePropertyChange("suspended step", null,
							actor.name);
				}
			}
		}
		return actorStatus;
	}

	public void stepInto() {
		threadStepping = true;
		threadStepInto = true;
		eventPending = true;
		interpreter.firePropertyChange("resumed step", null, actor.name);
	}

	public void stepOver() {
		threadStepping = true;
		eventPending = true;
		interpreter.firePropertyChange("resumed step", null, actor.name);
	}

	public void suspend() {
		threadSuspended = true;
		interpreter.firePropertyChange("suspended client", null, actor.name);
	}
}
