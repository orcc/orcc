package net.sf.orcc.interpreter;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Location;

public abstract class AbstractInterpretedActor {

	protected Actor actor;
	protected String name;
	protected Location lastVisitedLocation;
	protected String lastVisitedAction;

	protected AbstractInterpretedActor(String id, Actor actor) {
		this.name = id;
		this.actor = actor;
		this.lastVisitedLocation = new Location(0,0,0);
		this.lastVisitedAction = "NA";
	}

	public String getName() {
		return name;
	}

	public Location getLastVisitedLocation() {
		return lastVisitedLocation;
	}

	public String getLastVisitedAction() {
		return lastVisitedAction;
	}
	
	public void initialize() {
	}

	public Integer schedule() {
		return 0;
	}

	public void close() {
	}
}
