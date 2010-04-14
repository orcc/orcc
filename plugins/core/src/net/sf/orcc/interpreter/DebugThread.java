package net.sf.orcc.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private InterpreterMain interpreter;
	private boolean threadStepping = false;
	private List<Integer> breakpoints;

	public DebugThread(InterpreterMain interpreter,
			AbstractInterpretedActor actor) {
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

	public synchronized InterpreterStackFrame getStackFrame() {
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

	public void resume() {
		threadStepping = false;
	}

	public int schedule() {
		int actorStatus = actor.step(false);
		for (Integer breakpoint : breakpoints) {
			if ((breakpoint == actor.lastVisitedLocation.getStartLine())) {
				threadStepping = true;
				interpreter.firePropertyChange("suspended breakpoint "
						+ breakpoint, null, actor.name);
				actorStatus = 1;
				break;
			}
		}
		return actorStatus;
	}

	public synchronized void stepInto() {
		interpreter.firePropertyChange("resumed step", null, actor.name);
		interpreter.step(this);
		interpreter.firePropertyChange("suspended step", null, actor.name);
	}
	
	public synchronized void stepOver() {
		interpreter.stepAll();
	}

	public void set_breakpoint(int bkpt) {
		breakpoints.add(bkpt);
	}

	public void clear_breakpoint(int bkpt) {
		for (Integer breakpoint : breakpoints) {
			if ((breakpoint == bkpt)) {
				breakpoints.remove(breakpoint);
			}
		}
	}

}
