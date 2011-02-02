/*
 * Copyright (c) 2010, Ecole Polytechnique Fédérale de Lausanne 
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the 
 *     names of its contributors may be used to endorse or promote products 
 *     derived from this software without specific prior written permission.
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;

/**
 * This interface defines a scheduler.
 * 
 * @author Ghislain Roquier
 * 
 */
public abstract class AbstractScheduler implements IScheduler {

	protected DirectedGraph<Vertex, Connection> graph;

	protected Schedule schedule;

	protected Map<Connection, Integer> bufferCapacities;

	private boolean stop = false;
	
	public AbstractScheduler(DirectedGraph<Vertex, Connection> graph)
			throws OrccException {
		this.graph = graph;
		schedule = schedule();
	}

	public Map<Connection, Integer> getBufferCapacities() {
		if (bufferCapacities == null) {
			bufferCapacities = new HashMap<Connection, Integer>();

			for (Connection edge : graph.edgeSet()) {
				Vertex src = graph.getEdgeSource(edge);
				Vertex tgt = graph.getEdgeTarget(edge);

				List<Schedule> schedules = getHierarchy(schedule, src);
				schedules.removeAll(getHierarchy(schedule, tgt));

				int q = 1;
				for (Schedule schedule : schedules) {
					q *= schedule.getIterationCount();
				}

				int p = edge.getSource().getNumTokensProduced();
				bufferCapacities.put(edge, p * q);
			}
		}
		return bufferCapacities;
	}

	private List<Schedule> getHierarchy(Schedule schedule, Vertex vertex) {
		List<Schedule> schedules = new LinkedList<Schedule>();
		Iterator<Iterand> it = schedule.getIterands().iterator();
		stop = false;

		while (it.hasNext()) {
			Iterand iterand = it.next();
			if (iterand.isVertex()) {
				if (iterand.getVertex().equals(vertex)) {
					stop = true;
				}
			} else {
				Schedule sched = iterand.getSchedule();
				((LinkedList<Schedule>) schedules).addLast(sched);
				schedules.addAll(getHierarchy(sched, vertex));
				if (stop == true)
					break;
				((LinkedList<Schedule>) schedules).removeLast();
			}
		}

		return schedules;
	}
	
	private int depth;
	
	private int maxDepth;

	
	public int getDepth() {
		depth = maxDepth = 0;
		depth(schedule);
		return maxDepth;
	}

	private void depth(Schedule schedule) {
		for(Iterand iterand : schedule.getIterands()) {
			if(iterand.isSchedule()) {
				depth++;
				depth(iterand.getSchedule());
				depth--;
			} else {
				if(depth > maxDepth) maxDepth = depth;
			}
		}
	}

	public Schedule getSchedule() {
		return schedule;
	}

}
