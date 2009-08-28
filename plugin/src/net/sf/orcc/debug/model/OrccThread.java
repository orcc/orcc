/**
 * 
 */
package net.sf.orcc.debug.model;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

/**
 * @author mwipliez
 * 
 */
public class OrccThread extends OrccDebugElement implements IThread {

	private IStackFrame[] stackFrames;

	private boolean suspended;

	private boolean terminated;

	public OrccThread(OrccDebugTarget target) {
		super(target);
		stackFrames = new IStackFrame[] { new OrccStackFrame(target, this) };
	}

	@Override
	public boolean canResume() {
		return isSuspended();
	}

	@Override
	public boolean canStepInto() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canStepOver() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canStepReturn() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSuspend() {
		return !isSuspended();
	}

	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return new IBreakpoint[0];
	}

	@Override
	public String getName() throws DebugException {
		return "my thread";
	}

	@Override
	public int getPriority() throws DebugException {
		return 0;
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		return stackFrames;
	}

	@Override
	public IStackFrame getTopStackFrame() throws DebugException {
		return stackFrames[0];
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return true;
	}

	@Override
	public boolean isStepping() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuspended() {
		return suspended;
	}

	@Override
	public boolean isTerminated() {
		return terminated;
	}

	@Override
	public void resume() throws DebugException {
		suspended = false;
		fireResumeEvent(DebugEvent.CLIENT_REQUEST);
	}

	@Override
	public void stepInto() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepOver() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepReturn() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspend() throws DebugException {
		suspended = true;
		fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
	}

	@Override
	public void terminate() throws DebugException {
		suspended = false;
		terminated = true;
	}

}
