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

import java.util.ArrayList;
import java.util.List;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Vertex;
import net.sf.orcc.df.Connection;
import net.sf.orcc.util.Rational;

/**
 * 
 * This class computes a looped schedule from a one-level looped schedule in
 * order to mimimize memory usage.
 * 
 * @author Ghislain Roquier
 * 
 */

public class NestedLoopTransformation {

	private double[][] m;

	private int[][] s, gcds;

	private int size;

	Schedule schedule;

	private void computeMatrixChainOrder(Schedule schedule) {
		List<Iterand> iterands = schedule.getIterands();

		for (int i = 0; i < size; i++) {
			int gcd = 1;
			if (iterands.get(i).isSchedule()) {
				gcd = iterands.get(i).getSchedule().getIterationCount();
			}
			gcds[i][i] = gcd;
		}

		for (int L = 1; L < size; L++) {
			for (int i = 0; i < size - L; i++) {
				int j = i + L;

				m[i][j] = Double.MAX_VALUE;

				int rep = 1;
				if (iterands.get(j).isSchedule()) {
					rep = iterands.get(j).getSchedule().getIterationCount();
				}

				int gcd = Rational.gcd(gcds[i][j - 1], rep);

				for (int k = i; k < j; k++) {
					List<Vertex> left = new ArrayList<Vertex>();
					for (int index = i; index < k + 1; index++) {
						left.add(iterands.get(index).getSchedule()
								.getIterands().get(0).getVertex());
					}

					List<Vertex> right = new ArrayList<Vertex>();
					for (int index = k + 1; index < j + 1; index++) {
						right.add(iterands.get(index).getSchedule()
								.getIterands().get(0).getVertex());
					}

					Schedule subsched = iterands.get(k).getSchedule();
					int q = subsched.getIterationCount();

					double cost = m[i][k] + m[k + 1][j];

					for (Vertex vertex : left) {
						for (Edge edge : vertex.getOutgoing()) {
							if (right.contains(edge.getTarget())) {
								Connection connection = (Connection) edge;
								cost += connection.getSourcePort()
										.getNumTokensProduced() * q / gcd;
							}
						}
					}

					if (cost < m[i][j]) {
						m[i][j] = cost;
						gcds[i][j] = gcd;
						s[i][j] = k;
					}
				}
			}
		}
	}

	private void computeOptimalParens(Schedule schedule, int i, int j) {
		if (i == j) {
			Vertex vertex = getVertex(this.schedule.getIterands().get(i));
			schedule.add(new Iterand(vertex));
		} else {

			int gcd = gcds[i][j];
			int split = s[i][j];

			int rep = gcds[i][split] / gcd;
			if (rep > 1) {
				Schedule left = new Schedule();
				left.setIterationCount(rep);
				schedule.add(new Iterand(left));
				computeOptimalParens(left, i, split);
			} else {
				computeOptimalParens(schedule, i, split);
			}

			rep = gcds[split + 1][j] / gcd;
			if (rep > 1) {
				Schedule right = new Schedule();
				right.setIterationCount(rep);
				schedule.add(new Iterand(right));
				computeOptimalParens(right, split + 1, j);
			} else {
				computeOptimalParens(schedule, split + 1, j);
			}
		}
	}

	private Vertex getVertex(Iterand iterand) {
		Vertex vertex = null;
		if (iterand.isSchedule()) {
			vertex = iterand.getSchedule().getIterands().get(0).getVertex();
		} else {
			vertex = iterand.getVertex();
		}
		return vertex;
	}

	public void transform(Schedule schedule) {
		this.schedule = new Schedule(schedule);

		size = schedule.getIterands().size();
		m = new double[size][size];
		s = new int[size][size];
		gcds = new int[size][size];

		computeMatrixChainOrder(schedule);

		schedule.removeAll(schedule.getIterands());

		schedule.setIterationCount(1);

		computeOptimalParens(schedule, 0, size - 1);
	}

}
