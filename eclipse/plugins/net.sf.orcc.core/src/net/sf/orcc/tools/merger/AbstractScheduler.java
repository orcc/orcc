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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.moc.CSDFMoC;

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

	protected Map<Connection, Integer> maxTokens;

	protected Map<Vertex, Integer> repetitionsVector;

	private int depth;

	private int maxDepth;

	Map<Connection, Integer> tokens;

	public AbstractScheduler(DirectedGraph<Vertex, Connection> graph) {
		this.graph = graph;

		repetitionsVector = new RepetitionsAnalyzer(graph).getRepetitions();
	}

	private void computeMemoryBound(Schedule schedule) {
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isVertex()) {
				Vertex vertex = iterand.getVertex();
				for (Connection connection : graph.incomingEdgesOf(vertex)) {
					int cns = connection.getTargetPort().getNumTokensConsumed();
					tokens.put(connection, tokens.get(connection) - cns);
				}
				for (Connection connection : graph.outgoingEdgesOf(vertex)) {
					int current = tokens.get(connection);
					int max = maxTokens.get(connection);
					CSDFMoC moc = (CSDFMoC) ((Instance) vertex).getMoC();
					int prd = moc.getNumTokensProduced(connection
							.getSourcePort());
					tokens.put(connection, current + prd);
					if (max < current + prd) {
						maxTokens.put(connection, current + prd);
					}
				}
			} else {
				int count = iterand.getSchedule().getIterationCount();
				while (count != 0) {
					computeMemoryBound(iterand.getSchedule());
					count--;
				}
			}
		}

	}

	private void computeDepth(Schedule schedule) {
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isSchedule()) {
				depth++;
				computeDepth(iterand.getSchedule());
				depth--;
			} else {
				maxDepth = Math.max(depth, maxDepth);
			}
		}
	}

	public Map<Connection, Integer> getMaxTokens() {
		if (maxTokens == null) {
			maxTokens = new HashMap<Connection, Integer>();
			tokens = new HashMap<Connection, Integer>();

			for (Connection connection : graph.edgeSet()) {
				maxTokens.put(connection, 0);
				tokens.put(connection, 0);
			}
			computeMemoryBound(schedule);
		}
		return maxTokens;
	}

	/**
	 * 
	 * @return
	 */
	public int getDepth() {
		depth = maxDepth = 0;
		computeDepth(schedule);
		return maxDepth;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Vertex, Integer> getRepetitionsVector() {
		return repetitionsVector;
	}

	/**
	 * 
	 * @return
	 */
	public Schedule getSchedule() {
		return schedule;
	}

}
