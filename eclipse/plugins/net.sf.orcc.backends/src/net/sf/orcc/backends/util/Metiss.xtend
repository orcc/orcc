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
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.PrintStream
import java.util.ArrayList
import java.util.List
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
	
	private BiMap<Vertex, Integer> vertexMap
	private int rank = 0
	
	new() {
		vertexMap = HashBiMap::create()
		metis = OrccActivator::getDefault().getPreference(PreferenceConstants::P_METIS, "");
	}
	
	def partition(Network network, String folder, int partitionNumber) {
		print(network, folder, "top.metis")
		run(partitionNumber, folder)
		return parse(partitionNumber, folder)
	}
	
	def private print(Network network, String targetFolder, String filename) {
		for(vertex: network.children.notNative)
			vertexMap.put(vertex, rank = rank + 1)
		
		val file = new File(targetFolder+ File::separator + filename);
		val ps = new PrintStream(new FileOutputStream(file));
		ps.print(network.metiss);
		ps.close();
	}

	def private metiss(Network network) '''
		«network.vertices.notNative.size» «network.edges.notSignal.size» 010
		«FOR instance : network.children.notNative»
			«instance.metiss»
		«ENDFOR»
		
	'''

	def private metiss(Instance instance) '''
		% «instance.label»
		«instance.weight» «instance.incoming.notSignal.map[source.id].join(" ")» «instance.outgoing.notSignal.map[target.id].join(" ")»
	'''
	
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
	
	def private parse(int partitionNumber, String path) {
		val mapping = new Mapping
		try{
			// Open the file that is the first 
			// command line parameter
			val fstream = new FileInputStream(path + File::separator + "top.metis.part." + partitionNumber)
			// Get the object of DataInputStream
			val in = new DataInputStream(fstream)
			val br = new BufferedReader(new InputStreamReader(in))
			var partition = ""
			val map = vertexMap.inverse as BiMap<Integer, Vertex>
			var i = 1
			//Read File Line By Line
			while ((partition = br.readLine()) != null) {
				mapping.map("proc_" + partition, map.get(i))
				i = i + 1
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			OrccLogger::severeln("Error: " + e.getMessage());
		}
		return mapping
	}
	
	def private getNotNative(List<Vertex> vertices) {
		vertices.filter(typeof(Instance)).filter[!actor.native]
	}
	
	def private getNotSignal(List<Edge> edges) {
		edges.filter(typeof(Connection)).filter[!sourcePort.native && !targetPort.native]
	}

}