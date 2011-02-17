/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
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
package net.sf.orcc.tools.merger2;

import java.util.Set;

import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.util.Rational;

import org.jgrapht.DirectedGraph;

/**
 * This class follows the four conditions introduced by pino's theorem for
 * clustering two SDF actors.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class SDFCompositionAnalyzer {
	private DirectedGraph<Vertex, Connection> graph;
	private StaticGraph staticGraph;

	public SDFCompositionAnalyzer(Network network, StaticGraph staticGraph) {
		this.graph = network.getGraph();
		this.staticGraph = staticGraph;

		analyseGraph();
	}

	private Rational calculateRate(Connection connection) {
		int comsuption = getComsuptionRate(connection);
		int production = getProductionRate(connection);
		return new Rational(production, comsuption);
	}

	private void analyseGraph() {
		for (Vertex vertex : staticGraph.getStaticVertices()) {
			Set<Vertex> staticNeighbours = staticGraph
					.getStaticNeighbors(vertex);

			for (Vertex neighbour : staticNeighbours) {
				setRepetitionFactor(vertex, neighbour);
			}
		}

	}

	private int getComsuptionRate(Connection connection) {
		Vertex vertex = graph.getEdgeTarget(connection);
		CSDFMoC moc = getCSDFMoC(vertex);

		return moc.getNumTokensConsumed(connection.getTarget());
	}

	private CSDFMoC getCSDFMoC(Vertex vertex) {
		if (!vertex.isInstance()) {
			// Error of analysis
			return null;
		}

		Instance instance = vertex.getInstance();

		// TODO extend this process to QSDF
		return (CSDFMoC) instance.getActor().getMoC();
	}

	private int getProductionRate(Connection connection) {
		Vertex vertex = graph.getEdgeSource(connection);
		CSDFMoC moc = getCSDFMoC(vertex);
		return moc.getNumTokensProduced(connection.getSource());

	}

	public Boolean isValid() {
		return true;
	}

	private Boolean setRepetitionFactor(Vertex source, Vertex target) {
		Set<Connection> connections = graph.getAllEdges(source, target);
		Rational rational = null;

		for (Connection connection : connections) {
			Rational result = calculateRate(connection);

			if (rational == null) {
				// This is the reference rational
				rational = result;
			} else if (!rational.equals(result)) {
				// Merging is inconsistent
				return false;
			}
		}

		// Merging is consistent set number of repetition of each vertex
		staticGraph.setRepetitionFactor(source, rational.getNumerator(),
				target, rational.getDenominator());
		return true;
	}

}
