package net.sf.orcc.interpreter;

import net.sf.orcc.interpreter.AbstractInterpretedActor.InterpreterStackFrame;

/**
 * This class describes a debug thread.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class DebugThread {

	public AbstractInterpretedActor instance;
	private InterpreterMain interpreter;
	private boolean isSuspended;

	public DebugThread(InterpreterMain interpreter,
			AbstractInterpretedActor actor) {
		this.instance = actor;
		this.interpreter = interpreter;
	}

	public String getName() {
		return instance.getName();
	}

	public String getActorName() {
		return instance.actor.getName();
	}

	public synchronized InterpreterStackFrame getStackFrame() {
		return instance.getStackFrame();
	}

	public boolean isStepping() {
		return instance.isStepping();
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void resume() {
		isSuspended = false;
	}
	
	public synchronized void stepInto() {
		interpreter.firePropertyChange("resumed step", null, instance.name);
		interpreter.step(instance, true);
		interpreter.firePropertyChange("suspended step", null, instance.name);
	}

	public synchronized void stepOver() {
		interpreter.firePropertyChange("resumed step", null, instance.name);
		interpreter.step(instance, false);
		interpreter.firePropertyChange("suspended step", null, instance.name);
	}

	public synchronized void stepReturn() {
		interpreter.firePropertyChange("resumed step", null, instance.name);
		interpreter.stepReturn(instance);
		interpreter.firePropertyChange("suspended step", null, instance.name);
	}
	
	public void suspend(int breakpoint) {
		isSuspended = true;
		if (breakpoint != 0) {
			interpreter.firePropertyChange(
					"suspended breakpoint " + breakpoint, null, instance.name);
		} else {
			interpreter.firePropertyChange(
					"suspended step", null, instance.name);			
		}
	}

}
