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

package net.sf.orcc.network.transforms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.classes.StaticClass;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.util.Rational;

import org.jgrapht.DirectedGraph;

/**
 * This class computes the repetition vector of the graph. All instances of the
 * network are assumed to be SDF. The network classifier is assumed to be
 * computed first.
 * 
 * @author Ghislain Roquier
 * 
 */

public class NetworkSdfAnalyzer {

	private Map<Vertex, Integer> repetitionVector = new HashMap<Vertex, Integer>();
	private Map<Vertex, net.sf.orcc.util.Rational> rationalRepetitionVector = new HashMap<Vertex, Rational>();

	private DirectedGraph<Vertex, Connection> graph;

	int lcm = 0;

	public void transform(Network network) throws OrccException {

		graph = network.getGraph();
		computeRepetitionVector();

	}

	/**
	 * Computes the repetition vector
	 * 
	 */
	public void computeRepetitionVector() throws OrccException {

		Vertex vertex = (Vertex) graph.vertexSet().toArray()[0];

		calculateRate(vertex, new Rational(1, 1));

		Iterator<Rational> it = rationalRepetitionVector.values().iterator();
		int lcm = it.next().getDenominator();
		while (it.hasNext()) {
			lcm = Rational.lcm(lcm, it.next().getDenominator());
		}

		for (Map.Entry<Vertex, Rational> entry : rationalRepetitionVector
				.entrySet()) {
			int denom = entry.getValue().getNumerator() * lcm
					/ entry.getValue().getDenominator();
			repetitionVector.put(entry.getKey(), denom);
		}

		checkConsistency();

	}

	/**
	 * Calculates the rate of each instance of the graph
	 * 
	 */
	private void calculateRate(Vertex vertex, Rational rate) {
		StaticClass SdfActor = (StaticClass) vertex.getInstance().getActor()
				.getActorClass();

		rationalRepetitionVector.put(vertex, rate);

		for (Connection connection : graph.outgoingEdgesOf(vertex)) {
			Vertex tgt = graph.getEdgeTarget(connection);
			if (tgt.isInstance()) {
				if (!rationalRepetitionVector.containsKey(tgt)) {

					Port srcPort = connection.getSource();
					Port tgtPort = connection.getTarget();
					StaticClass tgtSdfActor = (StaticClass) tgt.getInstance()
							.getActor().getActorClass();

					int produced = SdfActor.getNumTokensProduced(srcPort);
					int consumed = tgtSdfActor.getNumTokensConsumed(tgtPort);

					Rational outgoingRate = rate.times(new Rational(produced,
							consumed));
					calculateRate(tgt, outgoingRate);
				}
			}
		}
		for (Connection connection : graph.incomingEdgesOf(vertex)) {
			Vertex src = graph.getEdgeSource(connection);
			if (src.isInstance()) {
				if (!rationalRepetitionVector.containsKey(src)) {

					Port srcPort = connection.getSource();
					Port tgtPort = connection.getTarget();
					StaticClass srcSdfActor = (StaticClass) src.getInstance()
							.getActor().getActorClass();

					int produced = srcSdfActor.getNumTokensProduced(srcPort);
					int consumed = SdfActor.getNumTokensConsumed(tgtPort);

					Rational incomingRate = rate.times(new Rational(consumed,
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

				Port srcPort = connection.getSource();
				Port tgtPort = connection.getTarget();
				StaticClass srcSdfActor = (StaticClass) src.getInstance()
						.getActor().getActorClass();
				StaticClass tgtSdfActor = (StaticClass) tgt.getInstance()
						.getActor().getActorClass();

				int produced = srcSdfActor.getNumTokensProduced(srcPort);
				int consumed = tgtSdfActor.getNumTokensConsumed(tgtPort);

				int srcRate = repetitionVector.get(src);
				int tgtRate = repetitionVector.get(tgt);

				if (srcRate * produced != tgtRate * consumed) {
					throw new OrccException(
							"the given network is inconsistent!");
				}
			}
		}
	}

}
