/*
 * Copyright (c) 2010, EPFL
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the EPFL nor the names of its contributors may be used 
 *     to endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.tools.merger.actor;

import net.sf.orcc.df.Actor;

/**
 * This class defines an element of the body of a schedule. An iterand can be
 * either a vertex or another schedule.
 * 
 * @author Ghislain Roquier
 * 
 */

public class Iterand {

	private enum Type {
		SCHEDULE, ACTOR
	}

	private Object contents;

	private Type type;

	public Iterand(Schedule schedule) {
		contents = schedule;
		type = Type.SCHEDULE;
	}

	public Iterand(Actor actor) {
		contents = actor;
		type = Type.ACTOR;
	}

	public Schedule getSchedule() {
		return (Schedule) contents;
	}

	public Actor getActor() {
		return (Actor) contents;
	}

	public boolean isSchedule() {
		return (type == Type.SCHEDULE);
	}

	public boolean isActor() {
		return (type == Type.ACTOR);
	}

	@Override
	public String toString() {
		Object obj;
		if (isActor()) {
			obj = ((Actor) contents).getName();
		} else {
			obj = contents;
		}
		return "" + obj + "";
	}

}
