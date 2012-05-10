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

package net.sf.orcc.tools.merger;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.graph.Vertex;

/**
 * This class defines a schedule. A schedule is composed of a header
 * (iterationCount) that defines the number of iteration of the schedule and a
 * body (iterands) that defines the order of invocations of vertex/sub-schedule.
 * 
 * @author Ghislain Roquier
 * 
 */
public class Schedule {

	private int iterationCount;

	private LinkedList<Iterand> iterands;

	public Schedule() {
		iterands = new LinkedList<Iterand>();
	}

	public Schedule(Schedule schedule) {
		this.setIterationCount(schedule.getIterationCount());
		iterands = new LinkedList<Iterand>(schedule.getIterands());
	}

	public void add(Iterand iterand) {
		iterands.offer(iterand);
	}

	public void addAll(List<Iterand> iterands) {
		this.iterands.addAll(iterands);
	}

	public Set<Vertex> getActors() {
		Set<Vertex> actors = new LinkedHashSet<Vertex>();
		LinkedList<Iterand> stack = new LinkedList<Iterand>(iterands);

		while (!stack.isEmpty()) {
			Iterand iterand = stack.pop();
			if (iterand.isVertex()) {
				actors.add(iterand.getVertex());
			} else {
				Schedule schedule = iterand.getSchedule();
				for (Iterand subIterand : schedule.getIterands()) {
					stack.push(subIterand);
				}
			}
		}
		return actors;
	}

	public List<Iterand> getIterands() {
		return iterands;
	}

	public int getIterationCount() {
		return iterationCount;
	}

	public void remove() {
		iterands.poll();
	}

	public void remove(Iterand iterand) {
		iterands.remove(iterand);
	}

	public void removeAll(List<Iterand> iterands) {
		iterands.removeAll(iterands);
	}

	public void setIterationCount(int interationCount) {
		this.iterationCount = interationCount;
	}

	@Override
	public String toString() {
		String its = "";
		for (Iterand iterand : iterands)
			its += iterand;
		String res = "";
		if (iterationCount == 0 || iterationCount == 1) {
			res = its;
		} else {
			res = "(" + iterationCount + its + ")";
		}
		return res;
	}

}
