/**
 * 
 */
package net.sf.orcc.debug.model;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;

/**
 * @author mwipliez
 * 
 */
public class OrccDebugTarget extends OrccDebugElement implements IDebugTarget {

	private ILaunch launch;

	private boolean suspended;

	private boolean terminated;

	private IThread[] threads;

	public OrccDebugTarget(ILaunch launch) {
		super(null);
		this.target = this;
		this.launch = launch;
		this.threads = new IThread[] { new OrccThread(target) };
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}

	@Override
	public boolean canSuspend() {
		return !isTerminated() && !isSuspended();
	}

	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	@Override
	public void disconnect() throws DebugException {
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}

	@Override
	public String getName() throws DebugException {
		return "hello";
	}

	@Override
	public IProcess getProcess() {
		return null;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		return threads;
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return true;
	}

	@Override
	public boolean isDisconnected() {
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
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		return true;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
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
		fireTerminateEvent();
	}

}
