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
import java.util.Iterator;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
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

	private Map<Instance, Rational> rationals = new HashMap<Instance, Rational>();

	private Map<Instance, Integer> repetitions = new HashMap<Instance, Integer>();

	public RepetitionsAnalyzer(Network network) {
		this.network = network;

		analyze();
	}

	/**
	 * Computes the repetition vector of the given SDF graph
	 * 
	 */
	private void analyze() {
		// must be an instance's vertex
		Instance initialVertex = network.getInstances().iterator().next();

		calculateRate(initialVertex, new Rational(1, 1));

		Iterator<Rational> it = rationals.values().iterator();
		int lcm = it.next().getDenominator();
		while (it.hasNext()) {
			lcm = Rational.lcm(lcm, it.next().getDenominator());
		}

		for (Map.Entry<Instance, Rational> entry : rationals.entrySet()) {
			Instance vertex = entry.getKey();
			Rational rat = entry.getValue();
			int rep = rat.getNumerator() * lcm / rat.getDenominator();
			repetitions.put(vertex, rep);
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
	private void calculateRate(Instance instance, Rational rate) {
		if (!instance.getMoC().isCSDF()) {
			throw new OrccRuntimeException("actor" + instance.getEntity()
					+ "is not SDF or CSDF!");
		}

		CSDFMoC moc = (CSDFMoC) instance.getMoC();

		rationals.put(instance, rate);

		for (Connection conn : instance.getOutgoing()) {
			Instance tgt = (Instance) conn.getTarget();
			CSDFMoC tgtMoC = (CSDFMoC) ((Instance) tgt).getMoC();
			if (!rationals.containsKey(tgt)) {
				int prd = moc.getNumTokensProduced(conn.getSourcePort());
				int cns = tgtMoC.getNumTokensConsumed(conn.getTargetPort());
				calculateRate(tgt, rate.mul(new Rational(prd, cns)));
			}
		}

		for (Connection conn : instance.getIncoming()) {
			Instance src = (Instance) conn.getSource();
			CSDFMoC srcMoC = (CSDFMoC) ((Instance) src).getMoC();
			if (!rationals.containsKey(src)) {
				int prd = srcMoC.getNumTokensProduced(conn.getSourcePort());
				int cns = moc.getNumTokensConsumed(conn.getTargetPort());
				calculateRate(src, rate.mul(new Rational(cns, prd)));
			}
		}
	}

	/**
	 * Checks the consistency of the given SDF graph
	 * 
	 */
	private void checkConsistency() {
		for (Connection connection : network.getConnections()) {
			int srcRate = repetitions.get(connection.getSource());
			int tgtRate = repetitions.get(connection.getTarget());

			CSDFMoC srcMoc = (CSDFMoC) ((Instance) connection.getSource())
					.getMoC();
			CSDFMoC tgtMoc = (CSDFMoC) ((Instance) connection.getTarget())
					.getMoC();

			int prd = srcMoc.getNumTokensProduced(connection.getSourcePort());
			int cns = tgtMoc.getNumTokensConsumed(connection.getTargetPort());

			if (srcRate * prd != tgtRate * cns) {
				throw new OrccRuntimeException(
						"the given network is inconsistent!");
			}
		}
	}

	public Integer getRepetition(Instance instance) {
		return repetitions.get(instance);
	}

	public Map<Instance, Integer> getRepetitions() {
		return repetitions;
	}

}
