/*
* Copyright(c)2009 Victor Martin, Jani Boutellier
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
* THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

import java.util.ArrayList;

/**
 * 
 * @author vimartin
 *
 */
public class SDFGraph {
	ArrayList<SDFVertex> vertices;
	ArrayList<SDFEdge> edges;
	
	String graphName;
	String name;
	
	public SDFGraph(){
		this(new ArrayList<SDFVertex>(), new ArrayList<SDFEdge>());
	}
	
	public SDFGraph(ArrayList<SDFVertex> vertices , ArrayList<SDFEdge> edges){
		this.vertices = vertices;
		this.edges = edges;
	}
	
	public void setGraphName(String graphName){
		this.graphName = graphName;
	}
	
	public ArrayList<SDFVertex> vertexList(){
		return vertices;
		
	}
	
	public ArrayList<SDFVertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<SDFVertex> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<SDFEdge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<SDFEdge> edges) {
		this.edges = edges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGraphName() {
		return graphName;
	}

	public ArrayList<SDFEdge> edgeList(){
		return edges;
		
	}

	public boolean containsVertex(SDFVertex v) {
		return vertices.contains(v);
	}
	
	public boolean containsEdge(SDFEdge e) {
		return edges.contains(e);
	}

	public SDFEdge addEdge(SDFVertex fromVertex, SDFVertex toVertex) {
		SDFEdge newEdge = new SDFEdge(fromVertex, toVertex);
		edges.add(newEdge);
		return newEdge;
	}

	public void addVertex(SDFVertex v) {
		vertices.add(v);
	}

	public int inDegreeOf(SDFVertex vertex) {
		int degree = 0 ;
		for(int i = 0 ; i < edges.size() ; i++){
			SDFVertex target = edges.get(i).getTarget();
			if(target.equals(vertex))
				degree++;
		}
		return degree;
	}
	
	public int outDegreeOf(SDFVertex vertex) {
		int degree = 0 ;
		for(int i = 0 ; i < edges.size() ; i++){
			SDFVertex source = edges.get(i).getSource();
			if(source.equals(vertex))
				degree++;
		}
		return degree;
	}

	public ArrayList<SDFEdge> incomingEdgesOf(SDFVertex vertex) {
		ArrayList<SDFEdge> edges = new ArrayList<SDFEdge>();
		for(int i = 0 ; i < this.edges.size() ; i++){
			SDFVertex target = this.edges.get(i).getTarget();
			if(target.equals(vertex))
				edges.add(this.edges.get(i));
		}
		return edges;
	}
	
	public ArrayList<SDFEdge> outgoingEdgesOf(SDFVertex vertex) {
		ArrayList<SDFEdge> edges = new ArrayList<SDFEdge>();
		for(int i = 0 ; i < this.edges.size() ; i++){
			SDFVertex source = this.edges.get(i).getSource();
			if(source.equals(vertex))
				edges.add(this.edges.get(i));
		}
		return edges;
	}
	
}
