package net.sf.orcc.backends.interpreter;

import net.sf.orcc.ir.Actor;

public abstract class AbstractInterpretedActor {

	protected Actor actor;
	protected String name;
	
	protected AbstractInterpretedActor(String id, Actor actor) {
		this.name = id;
		this.actor = actor;
	}
	
	public String getName() {
		return name;
	}

	public void initialize() throws Exception {
	}
	
	public Integer schedule() throws Exception {
		return 0;
	}
}
