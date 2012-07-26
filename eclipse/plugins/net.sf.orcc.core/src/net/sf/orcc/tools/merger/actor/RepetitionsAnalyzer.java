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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.util.Rational;

/**
 * This class computes the repetition vector of the graph. All instances of the
 * network are assumed to be SDF. The network classifier is assumed to be
 * computed first.
 * 
 * @author Ghislain Roquier
 * 
 */
public class RepetitionsAnalyzer {

	private Network network;

	private Map<Actor, Rational> rationals = new HashMap<Actor, Rational>();

	private Map<Actor, Integer> repetitions = new HashMap<Actor, Integer>();

	public RepetitionsAnalyzer(Network network) {
		this.network = network;

		analyze();
	}

	/**
	 * Computes the repetition vector of the given SDF graph
	 * 
	 */
	private void analyze() {
		calculateRate(network.getChildren().get(0).getAdapter(Actor.class),
				new Rational(1, 1));

		// get least common denominator
		int lcm = 1;
		for (Rational rat : rationals.values()) {
			lcm = Rational.lcm(lcm, rat.getDenominator());
		}

		for (Actor actor : rationals.keySet()) {
			Rational rat = rationals.get(actor);
			repetitions.put(actor,
					rat.getNumerator() * lcm / rat.getDenominator());
		}

		checkConsistency();
	}

	/**
	 * Calculates the rate of each instance of the graph
	 * 
	 * @param vertex
	 * @param rate
	 * 
	 */
	private void calculateRate(Actor actor, Rational rate) {
		if (!actor.getMoC().isCSDF()) {
			throw new OrccRuntimeException("actor" + actor.getName()
					+ "is not SDF or CSDF!");
		}

		CSDFMoC moc = (CSDFMoC) actor.getMoC();

		rationals.put(actor, rate);

		for (Edge edge : actor.getOutgoing()) {
			Connection conn = (Connection) edge;
			Actor tgt = conn.getTarget().getAdapter(Actor.class);
			if (tgt != null) {
				CSDFMoC tgtMoC = (CSDFMoC) tgt.getMoC();
				if (!rationals.containsKey(tgt)) {
					int prd = moc.getNumTokensProduced(conn.getSourcePort());
					int cns = tgtMoC.getNumTokensConsumed(conn.getTargetPort());
					calculateRate(tgt, rate.mul(new Rational(prd, cns)));
				}
			}
		}

		for (Edge edge : actor.getIncoming()) {
			Connection conn = (Connection) edge;
			Actor src = conn.getSource().getAdapter(Actor.class);
			if (src != null) {
				CSDFMoC srcMoC = (CSDFMoC) src.getMoC();
				if (!rationals.containsKey(src)) {
					int prd = srcMoC.getNumTokensProduced(conn.getSourcePort());
					int cns = moc.getNumTokensConsumed(conn.getTargetPort());
					calculateRate(src, rate.mul(new Rational(cns, prd)));
				}
			}
		}
	}

	/**
	 * Checks the consistency of the given SDF graph
	 * 
	 */
	private void checkConsistency() {
		for (Connection connection : network.getConnections()) {
			if (connection.getSource() instanceof Instance
					&& connection.getTarget() instanceof Instance) {
				int srcRate = repetitions.get(connection.getSource());
				int tgtRate = repetitions.get(connection.getTarget());

				CSDFMoC srcMoc = (CSDFMoC) ((Instance) connection.getSource())
						.getMoC();
				CSDFMoC tgtMoc = (CSDFMoC) ((Instance) connection.getTarget())
						.getMoC();

				int prd = srcMoc.getNumTokensProduced(connection
						.getSourcePort());
				int cns = tgtMoc.getNumTokensConsumed(connection
						.getTargetPort());

				if (srcRate * prd != tgtRate * cns) {
					throw new OrccRuntimeException(
							"the given network is inconsistent!");
				}
			}
		}
	}

	public Map<Actor, Integer> getRepetitions() {
		return repetitions;
	}

}
