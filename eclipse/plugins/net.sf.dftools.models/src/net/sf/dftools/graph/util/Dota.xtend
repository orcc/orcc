/*
 * Copyright (c) 2012, Synflow
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
package net.sf.dftools.graph.util

import java.util.HashMap
import java.util.Map
import net.sf.dftools.graph.Edge
import net.sf.dftools.graph.Graph
import net.sf.dftools.graph.Vertex

/**
 * This class defines a (very) simple Graph to DOT converter.
 * 
 * @author Matthieu Wipliez
 */
class Dota {
	
	private Map<Vertex, Integer> vertexMap
	
	private int rank
	
	new() {
		vertexMap = new HashMap<Vertex, Integer>()
	}

	def printDot(Graph graph) '''
		digraph G {
			node [label="", shape=box];
			«FOR vertex : graph.vertices»
			«print(vertex)»
			«ENDFOR»
			«FOR edge : graph.edges»
			«print(edge)»
			«ENDFOR»
		}
	'''

	def private print(Edge edge) {
		var label = edge.label
		'''
		node_«getId(edge.source)» -> node_«getId(edge.target)» «IF label != null && !label.empty » [label="(«edge.label»"]; «ENDIF»;
		'''
	}

	def private print(Vertex vertex) {
		var label = vertex.label
		'''
		node_«getId(vertex)» [label="(«vertex.number»)«IF label != null && !label.empty» «label»«ENDIF»"];
		'''
	}

	def private getId(Vertex vertex) {
		var id = vertexMap.get(vertex)
		if (id == null) {
			id = rank
			vertexMap.put(vertex, id)
			rank = rank + 1
		}
		id
	}

}