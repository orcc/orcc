package net.sf.orcc.interpreter;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Location;

public abstract class AbstractInterpretedActor {

	protected Actor actor;
	protected String lastVisitedAction;
	protected Location lastVisitedLocation;
	protected String name;

	protected AbstractInterpretedActor(String id, Actor actor) {
		this.name = id;
		this.actor = actor;
		this.lastVisitedLocation = new Location(0, 0, 0);
		this.lastVisitedAction = "NA";
	}

	/**
	 * Close any additional resources (IO, file...)
	 */
	public void close() {
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
	 * Initialize the actor state variables
	 */
	public void initialize() {
	}
	

	/**
	 * Check next action to be scheduled and interpret it if I/O FIFO are free.
	 * 
	 * @return the number of actions fired
	 */
	public Integer schedule() {
		return 0;
	}

	/**
	 * Check next instruction of the current schedulable action to be interpreted and interpret it.
	 * 
	 * @return if current schedulable action has been completed
	 */
	public boolean step() {
		return true;
	}
}
