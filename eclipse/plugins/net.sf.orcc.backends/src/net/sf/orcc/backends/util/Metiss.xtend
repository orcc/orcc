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
package net.sf.orcc.backends.util

import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import net.sf.orcc.OrccActivator
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.graph.Edge
import net.sf.orcc.graph.Vertex
import net.sf.orcc.preferences.PreferenceConstants
import net.sf.orcc.util.OrccLogger

/**
 * This class defines a graph converter to metiss format.
 * 
 * @author Herve Yviquel
 */
class Metiss {
	
	private String metis;
	
	private Map<Vertex, Integer> vertexMap
	private int rank
	
	new() {
		vertexMap = new HashMap<Vertex, Integer>()
		metis = OrccActivator::getDefault().getPreference(PreferenceConstants::P_METIS, "");
	}
	
	def partition(Network network, String folder, int partitionNumber) {
		print(network, folder, "top.metis")
		run(partitionNumber, folder)
		
		val mapping = new Mapping
		mapping.unmappedEntities.addAll(network.children)
		return mapping
	}
	
	def private print(Network network, String targetFolder, String filename) {
		for(vertex: network.vertices)
			vertexMap.put(vertex, rank = rank + 1)
		
		val file = new File(targetFolder+ File::separator + filename);
		val ps = new PrintStream(new FileOutputStream(file));
		ps.print(network.metiss);
		ps.close();
	}

	def private metiss(Network network) '''
		«network.vertices.notNative.size» «network.edgeNumber» 011
		«FOR instance : network.children.notNative»
			«instance.metiss»
		«ENDFOR»
		
	'''

	def private metiss(Instance instance) '''
		% «instance.label»
		«instance.weight» «instance.neighbors.notNative.map[getEdge(instance, it)].join(" ")»
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
			1000
		}
	}
	
	def private getWeight(Vertex current, Vertex neighbor) {
		var weight = 0
		for(outgoing : current.outgoing.notSignal)
			if(outgoing.target == neighbor)
				weight = weight + 1
		for(incoming : current.incoming.notSignal)
			if(incoming.source == neighbor)
				weight = weight + 1
		return weight
	}
	
	def private run(int partitionNumber, String path) {
		var cmdList = new ArrayList<String>()
		cmdList.add(metis)
		cmdList.add(path + File::separator + "top.metis")
		cmdList.add(partitionNumber.toString)
		OrccLogger::traceln(cmdList.toString)
		OrccLogger::traceln("Solving actor partitioning...");
		val t0 = System::currentTimeMillis;
		BackendUtil::runExternalProgram(cmdList)
		val t1 = System::currentTimeMillis();
		OrccLogger::traceln("Done in " + ((t1 - t0) as float / 1000.0)
					+ "s");
	}
	
	def private getEdgeNumber(Network network) {
		var number = 0
		var visited = new ArrayList<Vertex>
		for(instance : network.vertices.notNative) {
			for(neighbor: instance.neighbors.notNative){
				if(!visited.contains(neighbor)) {
					number = number + 1
				}
			}
			visited.add(instance)
		}
		return number		
	}
	
	def private getNotNative(List<Vertex> vertices) {
		vertices.filter(typeof(Instance)).filter[!actor.native]
	}
	
	def private getNotSignal(List<Edge> edges) {
		edges.filter(typeof(Connection)).filter[!sourcePort.native && !targetPort.native]
	}

}