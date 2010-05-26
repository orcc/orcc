package net.sf.orcc.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Location;

public abstract class AbstractInterpretedActor {

	protected Actor actor;
	protected String lastVisitedAction;
	protected Location lastVisitedLocation;
	protected String name;
	public Map<String, CommunicationFifo> ioFifos;

	public class InterpreterStackFrame {
		public String actorFilename;
		public Integer codeLine;
		public String currentAction;
		public String fsmState;
		public Integer nbOfFirings;
		public Map<String, Object> stateVars;

		public InterpreterStackFrame() {
			actorFilename = "";
			codeLine = 0;
			stateVars = new HashMap<String, Object>();
			nbOfFirings = 0;
			currentAction = "";
		}
	}

	protected AbstractInterpretedActor(String id, Actor actor) {
		this.name = id;
		this.actor = actor;
		this.lastVisitedLocation = new Location(0, 0, 0);
		this.lastVisitedAction = "NA";
	}

	/**
	 * Clear the breakpoint from the corresponding line
	 * 
	 * @param breakpoint
	 *            line number of actor's breakpoint
	 */
	public void clearBreakpoint(int breakpoint) {
	}

	/**
	 * Close any additional resources (IO, file...)
	 */
	public void close() {
	}
	
	/**
	 * Get the communication FIFO corresponding to the given port
	 * 
	 * @param portName
	 * @return communication FIFO description
	 */
	public CommunicationFifo getFifo(String portName) {
		return ioFifos.get(portName);
	}

	/**
	 * Get last action that has been interpreted for this actor
	 * 
	 * @return name of the last action
	 */
	public String getLastVisitedAction() {
		return lastVisitedAction;
	}

	/**
	 * Get line number of the last code that has been interpreted
	 * 
	 * @return code line number
	 */
	public Location getLastVisitedLocation() {
		return lastVisitedLocation;
	}

	/**
	 * Get instance actor's name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get current state of actor for debug print
	 * 
	 * @return
	 */
	public InterpreterStackFrame getStackFrame() {
		InterpreterStackFrame stackFrame = new InterpreterStackFrame();
		stackFrame.actorFilename = actor.getFile();
		stackFrame.codeLine = lastVisitedLocation.getStartLine();
		stackFrame.stateVars.clear();
		stackFrame.currentAction = lastVisitedAction;
		stackFrame.fsmState = "idle";
		return stackFrame;
	}

	/**
	 * Synchronize the interpreted actor with the compiled actor until
	 * breakpoint is reached.
	 */
	public int goToBreakpoint() {
		return 0;
	}

	/**
	 * Initialize the actor state variables
	 */
	public void initialize() {
	}

	/**
	 * Indicate if the current actor is stepping into an action
	 * 
	 * @return true if action is in step-debugging of an action
	 */
	public boolean isStepping() {
		return false;
	}

	/**
	 * Run actor while any action can be scheduled.
	 * 
	 * @return the number of actions fired; "-1" if user has interrupted
	 *         interpretation by closing the display window; "-2" if a
	 *         breakpoint has been hit
	 */
	public Integer run() {
		return 0;
	}

	/**
	 * Schedule next actor's action that can be scheduled.
	 * 
	 * @return "1" if action has been fired; "0" if none action can be
	 *         scheduled; "-1" if user has interrupted interpretation by closing
	 *         the display window; "-2" if a breakpoint has been hit
	 */
	public Integer schedule() {
		return 0;
	}

	/**
	 * Set a breakpoint at the corresponding line
	 * 
	 * @param breakpoint
	 *            line number of actor's breakpoint
	 */
	public void setBreakpoint(int breakpoint) {
	}

	/**
	 * Check next instruction of the current schedulable action to be
	 * interpreted and interpret it.
	 * 
	 * @return "2" if current action has not been completed yet; "1" if action
	 *         has been completed; "0" if no more action can not be scheduled;
	 */
	public int step(boolean doStepInto) {
		return 0;
	}
}
