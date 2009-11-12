/*
 * Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

/**
 * 
 * @author sadhanal for intermediatory work
 */
class StateSDF {
	SDFGraph graph;
	FlowVertex startVertex;
	FlowVertex endVertex;

	public StateSDF(SDFGraph graph, FlowVertex startVertex, FlowVertex endVertex) {
		super();
		this.graph = graph;
		this.startVertex = startVertex;
		this.endVertex = endVertex;
	}

	public StateSDF() {
		super();
	}

	/**
	 * @return the graph
	 */
	public SDFGraph getGraph() {
		return graph;
	}

	/**
	 * @param graph
	 *            the graph to set
	 */
	public void setGraph(SDFGraph graph) {
		this.graph = graph;
	}

	/**
	 * @return the startVertex
	 */
	public FlowVertex getStartVertex() {
		return startVertex;
	}

	/**
	 * @param startVertex
	 *            the startVertex to set
	 */
	public void setStartVertex(FlowVertex startVertex) {
		this.startVertex = startVertex;
	}

	/**
	 * @return the endVertex
	 */
	public FlowVertex getEndVertex() {
		return endVertex;
	}

	/**
	 * @param endVertex
	 *            the endVertex to set
	 */
	public void setEndVertex(FlowVertex endVertex) {
		this.endVertex = endVertex;
	}
}
