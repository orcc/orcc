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
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.util.Rational;

import org.jgrapht.DirectedGraph;

/**
 * This class computes the repetition vector of the graph. All instances of the
 * network are assumed to be static (SDF/CSDF). The network classifier is
 * assumed to be computed first.
 * 
 * @author Ghislain Roquier
 * 
 */
public class RepetitionVectorAnalyzer {

	private DirectedGraph<Vertex, Connection> graph;

	private Map<Vertex, Rational> rationals = new HashMap<Vertex, Rational>();

	private Map<Vertex, Integer> repetitionVector = new HashMap<Vertex, Integer>();

	public RepetitionVectorAnalyzer(DirectedGraph<Vertex, Connection> graph) {
		this.graph = graph;
	}

	/**
	 * Calculates the rate of each instance of the graph
	 * 
	 * @throws OrccException
	 * 
	 */
	private void calculateRate(Vertex vertex, Rational rate)
			throws OrccException {
		Instance instance = vertex.getInstance();
		if (!instance.getMoC().isSDF()) {
			throw new OrccException("class" + instance.getClasz()
					+ "is not static!");
		}
		SDFMoC moc = (SDFMoC) instance.getMoC();

		rationals.put(vertex, rate);

		for (Connection connection : graph.outgoingEdgesOf(vertex)) {
			Vertex tgt = graph.getEdgeTarget(connection);
			if (tgt.isInstance()) {
				if (!rationals.containsKey(tgt)) {

					int produced = moc.getNumTokensProduced(connection
							.getSource());
					int consumed = ((SDFMoC) tgt.getInstance().getMoC())
							.getNumTokensConsumed(connection.getTarget());

					Rational outgoingRate = rate.mul(new Rational(produced,
							consumed));
					calculateRate(tgt, outgoingRate);
				}
			}
		}

		for (Connection connection : graph.incomingEdgesOf(vertex)) {
			Vertex src = graph.getEdgeSource(connection);
			if (src.isInstance()) {
				if (!rationals.containsKey(src)) {

					int produced = ((SDFMoC) src.getInstance().getMoC())
							.getNumTokensProduced(connection.getSource());
					int consumed = moc.getNumTokensConsumed(connection
							.getTarget());

					Rational incomingRate = rate.mul(new Rational(consumed,
							produced));
					calculateRate(src, incomingRate);
				}
			}
		}

	}

	/**
	 * Checks the graph consistency
	 * 
	 * @throws OrccException
	 *             if the graph is inconsistent
	 */
	private void checkConsistency() throws OrccException {
		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (src.isInstance() && tgt.isInstance()) {
				int produced = ((SDFMoC) src.getInstance().getMoC())
						.getNumTokensProduced(connection.getSource());
				int consumed = ((SDFMoC) tgt.getInstance().getMoC())
						.getNumTokensConsumed(connection.getTarget());

				int srcRate = repetitionVector.get(src);
				int tgtRate = repetitionVector.get(tgt);

				if (srcRate * produced != tgtRate * consumed) {
					throw new OrccException(
							"the given network is inconsistent!");
				}
			}
		}
	}

	/**
	 * Computes the repetition vector
	 * 
	 * @return
	 * 
	 * @throws OrccException
	 *             if an actor is not static
	 */
	public Map<Vertex, Integer> getRepetitionVector() throws OrccException {
		if (repetitionVector != null) {
			Vertex vertex = null;

			for (Vertex v : graph.vertexSet()) {
				if (v.isInstance()) {
					vertex = v;
					break;
				}
			}

			calculateRate(vertex, new Rational(1, 1));

			Iterator<Rational> it = rationals.values().iterator();
			int lcm = it.next().getDenominator();
			while (it.hasNext()) {
				lcm = Rational.lcm(lcm, it.next().getDenominator());
			}

			for (Map.Entry<Vertex, Rational> entry : rationals.entrySet()) {

				int rep = entry.getValue().getNumerator() * lcm
						/ entry.getValue().getDenominator();

				repetitionVector.put(entry.getKey(), rep);
			}

			checkConsistency();

			for (Map.Entry<Vertex, Integer> entry : repetitionVector.entrySet()) {
				Integer val = entry.getValue();
				SDFMoC moc = (SDFMoC) entry.getKey().getInstance().getMoC();
				int nbPhases = moc.getNumberOfPhases();
				entry.setValue(val * nbPhases);
			}
		}
		return repetitionVector;
	}

}
