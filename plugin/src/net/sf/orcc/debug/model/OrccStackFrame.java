package net.sf.orcc.debug.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

public class OrccStackFrame extends OrccDebugElement implements IStackFrame {

	private OrccThread thread;

	public OrccStackFrame(OrccDebugTarget target, OrccThread thread) {
		super(target);
		this.thread = thread;
	}

	@Override
	public boolean canResume() {
		return thread.canResume();
	}

	@Override
	public boolean canStepInto() {
		return thread.canStepInto();
	}

	@Override
	public boolean canStepOver() {
		return thread.canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		return thread.canStepReturn();
	}

	@Override
	public boolean canSuspend() {
		return thread.canSuspend();
	}

	@Override
	public boolean canTerminate() {
		return thread.canTerminate();
	}

	@Override
	public int getCharEnd() throws DebugException {
		return 80;
	}

	@Override
	public int getCharStart() throws DebugException {
		return 1;
	}

	@Override
	public int getLineNumber() throws DebugException {
		return 42;
	}

	@Override
	public String getName() throws DebugException {
		return "my stack frame";
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return null;
	}

	@Override
	public IThread getThread() {
		return thread;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		return new IVariable[0];
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return false;
	}

	@Override
	public boolean isStepping() {
		return thread.isStepping();
	}

	@Override
	public boolean isSuspended() {
		return thread.isSuspended();
	}

	@Override
	public boolean isTerminated() {
		return thread.isTerminated();
	}

	@Override
	public void resume() throws DebugException {
		thread.resume();
	}

	@Override
	public void stepInto() throws DebugException {
		thread.stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		thread.stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		thread.stepReturn();
	}

	@Override
	public void suspend() throws DebugException {
		thread.suspend();
	}

	@Override
	public void terminate() throws DebugException {
		thread.terminate();
	}

}
