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

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import net.sf.orcc.OrccActivator
import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.graph.Edge
import net.sf.orcc.graph.Vertex
import net.sf.orcc.preferences.PreferenceConstants
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.OrccUtil

/**
 * This class defines a graph converter to Metis format.
 * 
 * @author Herve Yviquel
 */
class Metis {
	
	private String metisPath;
	
	private Network network
	private BiMap<Vertex, Integer> vertexMap
	
	new() {
		metisPath = OrccActivator::getDefault().getPreference(PreferenceConstants::P_METIS, "");
		if(!new File(metisPath).exists) {
			throw new OrccRuntimeException("The given path to the Metis solver seems wrong.")
		}
	}
	
	def partition(Network network, String folder, int partitionNumber, Map<String, String> weightsMap) {
		val file = new File(folder + File::separator + "top.metis")
		var rank = 0

		this.vertexMap = HashBiMap::create()
		this.network = network

		for(vertex: network.notNativeChildren) {
			// Set a unique number to each instance/actor
			vertexMap.put(vertex, rank = rank + 1)
			// Add the 'weight' attribute  to each instance/actor
			vertex.setAttribute("weight", weightsMap.get(vertex.name))
		}
		
		// Print metis-understandable graph representation in a file
		OrccUtil::printFile(network.metis, file)
		// Solve the graph partitioning problem by launching Metis
		run(partitionNumber, folder)

		return parse(partitionNumber, folder)
	}

	def private getMetis(Network network) '''
		«network.notNativeChildren.size» «network.notNativeConnections.size» 010
		«FOR child : network.notNativeChildren»
			% «child.label»
			«child.weight» «child.incoming.notNativeConnections.map[source.id].join(" ")» «child.outgoing.notNativeConnections.map[target.id].join(" ")»
		«ENDFOR»
	'''
	
	def private getName(Vertex vertex) {
		if(vertex instanceof Actor) {
			network.name + "_" + (vertex as Actor).name
		} else if (vertex instanceof Instance) {
			(vertex as Instance).hierarchicalName
		} else {
			throw new OrccRuntimeException(vertex.label + " is neither an actor nor an instance.")
		}
	}

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
	
	def private void run(int partitionNumber, String path) {
		var cmdList = new ArrayList<String>
		cmdList.add(metisPath)
		cmdList.add(path + File::separator + "top.metis")
		cmdList.add(partitionNumber.toString)

		OrccLogger::traceln("Solving actors partitioning on " + partitionNumber + " processors...");

		val t0 = System::currentTimeMillis;
		BackendUtil::runExternalProgram(cmdList)
		val t1 = System::currentTimeMillis();
		OrccLogger::traceln("Done in " + ((t1 - t0) as float / 1000.0) + "s");
	}
	
	def private parse(int partitionNumber, String path) {
		val indexMap = vertexMap.inverse as BiMap<Integer, Vertex>
		val mapping = new HashMap<String, String>
		try{
			// Open the file that is the first command line parameter
			val fstream = new FileInputStream(path + File::separator + "top.metis.part." + partitionNumber)
			// Get the object of DataInputStream
			val in = new DataInputStream(fstream)
			val br = new BufferedReader(new InputStreamReader(in))
			var partition = ""

			var i = 1
			//Read File Line By Line
			while ((partition = br.readLine()) != null) {
				mapping.put(indexMap.get(i).name, partition)
				i = i + 1
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			OrccLogger::severeln("Error: " + e.getMessage());
		}
		return mapping
	}
	
	def private getNotNativeChildren(Network network) {
		network.children.filter(typeof(Instance)).filter[!actor.native]
			+ network.children.filter(typeof(Actor)).filter[!native]
	}
	
	def private getNotNativeConnections(Network network) {
		network.connections.filter[!sourcePort.native && !targetPort.native]
	}

	def private getNotNativeConnections(List<Edge> edges) {
		edges.filter(typeof(Connection)).filter[!sourcePort.native && !targetPort.native]
	}

}