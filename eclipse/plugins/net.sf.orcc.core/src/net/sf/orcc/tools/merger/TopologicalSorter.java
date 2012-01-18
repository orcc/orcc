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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Edge;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;

/**
 * This class computes a topological sort of the graph. The graph must be
 * acyclic. The results with cyclic graphs are undefined.
 * 
 * @author Ghislain Roquier
 * 
 */
public class TopologicalSorter {

	private class DFS {

		private Set<Vertex> defined = new HashSet<Vertex>();

		private Network network;

		private Map<Vertex, TimeStamp> timeStamps = new HashMap<Vertex, TimeStamp>();

		private int currentTime = 0;

		public DFS(Network network) {
			this.network = network;
		}

		private void dfsVisit(Vertex vertex) {
			defined.add(vertex);
			currentTime++;
			timeStamps.put(vertex, new TimeStamp(currentTime, 0));

			for (Edge edge : vertex.getOutgoing()) {
				Vertex tgtVertex = edge.getTarget();
				if (!defined.contains(tgtVertex)) {
					dfsVisit(tgtVertex);
				}
			}
			currentTime++;
			timeStamps.get(vertex).setFinished(currentTime);
		}

		private Map<Vertex, TimeStamp> getTimestamps() {
			for (Vertex vertex : network.getVertices()) {
				if (!defined.contains(vertex)) {
					dfsVisit(vertex);
				}
			}
			return timeStamps;
		}

		public List<Vertex> sortByFinishingTime() {
			List<Vertex> sorted = new LinkedList<Vertex>(getTimestamps()
					.keySet());
			Collections.sort(sorted, new TimeStampComparator(timeStamps));
			return sorted;
		}

	};

	private static class TimeStamp implements Comparable<TimeStamp> {
		private int finish;
		private int start;

		public TimeStamp(int discovered, int finished) {
			this.start = discovered;
			this.finish = finished;
		}

		public int getFinished() {
			return finish;
		}

		@SuppressWarnings("unused")
		public int getStarted() {
			return start;
		}

		public void setFinished(int finished) {
			this.finish = finished;
		}

		@Override
		public int compareTo(TimeStamp that) {
			return that.getFinished() - this.getFinished();
		}
	};

	private class TimeStampComparator implements Comparator<Vertex> {

		private Map<Vertex, TimeStamp> map;

		public TimeStampComparator(Map<Vertex, TimeStamp> map) {
			this.map = map;
		}

		public int compare(Vertex v1, Vertex v2) {
			return map.get(v2).getFinished() - map.get(v1).getFinished();
		}

	};

	private Network network;

	public TopologicalSorter(Network network) {
		this.network = network;
	}

	public List<Vertex> topologicalSort() {
		return new DFS(network).sortByFinishingTime();
	}
}
