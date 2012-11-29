/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.graph.util

import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.HashMap
import java.util.Map
import net.sf.orcc.graph.Graph
import net.sf.orcc.graph.Vertex

/**
 * This class defines a graph converter to metiss format.
 * 
 * @author Herve Yviquel
 */
class Metiss {
		
	private Map<Vertex, Integer> vertexMap
	
	private int rank
	
	new() {
		vertexMap = new HashMap<Vertex, Integer>()
	}
	
	def print(Graph graph, String targetFolder, String filename) {
		for(vertex: graph.vertices)
			vertexMap.put(vertex, rank = rank + 1)
		
		val file = new File(targetFolder+ File::separator + filename);
		val ps = new PrintStream(new FileOutputStream(file));
		ps.print(graph.metiss);
		ps.close();
	}

	def private metiss(Graph graph) '''
		«graph.vertices.size» «graph.edges.size» 011
		«FOR vertex : graph.vertices»
			«vertex.metiss»
		«ENDFOR»
		
	'''

	def private metiss(Vertex vertex) '''
		% «vertex.label»
		«vertex.weight» «vertex.neighbors.map[getEdge(vertex, it)].join(" ")»
	'''
	
	def private getEdge(Vertex current, Vertex neighbor) 
		'''«neighbor.id» «getWeight(current, neighbor)»'''

	def private getId(Vertex vertex) {
		vertexMap.get(vertex)
	}
	
	def private getWeight(Vertex vertex) {
		var att = vertex.getAttribute("weight")
		if(att != null) {
			att.stringValue
		} else {
			0
		}
	}
	
	def private getWeight(Vertex current, Vertex neighbor) {
		var weight = 0
		for(outgoing : current.outgoing)
			if(outgoing.target == neighbor)
				weight = weight + 1
		for(incoming : current.incoming)
			if(incoming.source == neighbor)
				weight = weight + 1
		return weight
	}

}