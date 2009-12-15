package net.sf.orcc.interpreter;

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

	public void initialize() {
	}

	public Integer schedule() {
		return 0;
	}

	public void close() {
	}
}
