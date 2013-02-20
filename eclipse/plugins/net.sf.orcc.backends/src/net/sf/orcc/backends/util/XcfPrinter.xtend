/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
 * about
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
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.df.Network
import net.sf.orcc.graph.Vertex
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.df.Actor

/**
 * Printer used to create the xcf file, containing information on
 * mapping between actors and processor cores
 * 
 * @author Antoine Lorence
 * 
 */
class XcfPrinter extends CommonPrinter {
	
	var Map<String, List<Vertex>> mapping
	var List<Vertex> unmapped
	var int i
	
	def print(String targetFolder, Network network, Map<String, String> initialMapping) {
		val xcfFile = new File(targetFolder + File::separator + network.simpleName + ".xcf")
		
		network.computeMapping(initialMapping)
		printFile(network.contentFile, xcfFile)
	}
	
	def private void computeMapping(Network network, Map<String, String> initialMapping) {	
		mapping = new HashMap<String, List<Vertex>>
		unmapped = new ArrayList<Vertex>
		i = 0
		if(!initialMapping.values.forall[nullOrEmpty]) {
			for (instance : network.children.actorInstances) {
				instance.tryToMap(initialMapping.get(instance.hierarchicalName))
			}
			for (actor : network.children.filter(typeof(Actor))) {
				actor.tryToMap(initialMapping.get(network.name + "_" + actor.name))
			}
		}
	}
	
	def private tryToMap(Vertex vertex, String component) {	
		if (!component.nullOrEmpty) {
			if (!mapping.containsKey(component)) {
				mapping.put(component, new ArrayList<Vertex>);
			}
			mapping.get(component).add(vertex);
		} else {
			OrccLogger::warnln("The instance '" + vertex.label
						+ "' is not mapped.");
			unmapped.add(vertex)
		}
	}
	
	def private getContentFile(Network network) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<Configuration>
			<Partitioning>
				«IF !mapping.empty»
					«FOR vertices : mapping.values»
						«vertices.partition»
					«ENDFOR»
				«ELSE»
					«network.children.partition»
				«ENDIF»
				«FOR vertex : unmapped»
					<!-- Unmapped id="«vertex.label»" -->
				«ENDFOR»
			</Partitioning>
			
			«otherStuff»
		</Configuration>
	'''
	
	def private getPartition(Iterable<Vertex> entities) '''
		<Partition id="« i = i + 1 »">
			«FOR entity : entities»
				<Instance id="«entity.label»"/>
			«ENDFOR»
		</Partition>
	'''
	
	def private otherStuff() '''
		<!-- Other useful informations related to any element of the instanciated model can be printed here -->
	'''
	
}