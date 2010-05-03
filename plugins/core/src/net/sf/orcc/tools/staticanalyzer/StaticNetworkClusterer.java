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

package net.sf.orcc.tools.normalizer;

import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.classes.SDFNetworkClass;
import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.tools.staticanalyzer.RepetitionVectorAnalyzer;

public class StaticNetworkClusterer extends NetworkClusterer {

	private static int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	private Map<Vertex, Integer> rep;

	private Network cluster;

	private int gcd;

	public StaticNetworkClusterer(Network network)
			throws OrccException {
		super(network);
		rep = new RepetitionVectorAnalyzer(network).computeRepetitionsVector();
	}

	public Vertex cluster(String name, Set<Vertex> vertices)
			throws OrccException {
		Vertex vertex = super.cluster(name, vertices);

		SDFNetworkClass clasz = new SDFNetworkClass();

		cluster = vertex.getInstance().getNetwork();

		cluster.setNetworkClass(clasz);

		gcd = 0;

		for (Vertex v : vertices) {
			int q = rep.get(v);
			gcd = gcd(q, gcd);
		}
		computeOutputPortProduction();
		computeInputPortConsumption();

		clasz.setTokenConsumptions(cluster);
		clasz.setTokenProductions(cluster);

		return vertex;
	}

	private void computeInputPortConsumption() {

		for (Connection connection : cluster.getGraph().edgeSet()) {
			Vertex srcVertex = cluster.getGraph().getEdgeSource(connection);
			Vertex tgtVertex = cluster.getGraph().getEdgeTarget(connection);
			if (srcVertex.isPort() && tgtVertex.isInstance()) {
				int cns = connection.getTarget().getNumTokensConsumed();
				int q = rep.get(tgtVertex);

				Port port = srcVertex.getPort();
				port.increaseTokenConsumption(cns * (q / gcd));
			}
		}
	}

	private void computeOutputPortProduction() {

		for (Connection connection : cluster.getGraph().edgeSet()) {
			Vertex srcVertex = cluster.getGraph().getEdgeSource(connection);
			Vertex tgtVertex = cluster.getGraph().getEdgeTarget(connection);
			if (srcVertex.isInstance() && tgtVertex.isPort()) {
				int prd = connection.getSource().getNumTokensProduced();
				int q = rep.get(srcVertex);

				Port port = tgtVertex.getPort();
				port.increaseTokenProduction(prd * (q / gcd));
			}
		}
	}
	
	
	protected Network createPartition(String name, Set<Vertex> vertices)
			throws OrccException {

		cluster = super.createPartition(name, vertices);
		return cluster;
	}

	public int getRepetitions(Vertex vertex) {
		return rep.get(vertex.getInstance().getId());
	}

}
