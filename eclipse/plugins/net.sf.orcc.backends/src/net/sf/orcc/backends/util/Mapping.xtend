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
import javax.xml.parsers.DocumentBuilderFactory
import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Network
import net.sf.orcc.graph.Vertex
import net.sf.orcc.util.OrccLogger
import org.w3c.dom.Element

/**
 * Printer used to create the xcf file, containing information on
 * mapping between actors and processor cores
 * 
 * @author Antoine Lorence
 * 
 */
class Mapping extends CommonPrinter {

	var Network network

	var Map<Vertex, String> invMapping
	var Map<String, List<Vertex>> mapping
	var List<Vertex> unmapped
	var int i

	public new(Network network, Map<String, String> map) {
		setNetwork(network)
		if (!map.values.forall[nullOrEmpty]) {
			for (instance : network.children.actorInstances) {
				instance.tryToMap(map.get(instance.hierarchicalName))
			}
			for (actor : network.children.filter(typeof(Actor))) {

				// In case of a composite actor, try to map it on a component referenced by its children
				// FIXME: There is probably a better way to do this
				if (actor.hasAttribute("mergedActors")) {
					val clusteredActors = actor.<List<String>>getValueAsObject("mergedActors")
					actor.tryToMap(clusteredActors.map(a|map.get(a)).findFirst[!nullOrEmpty])
				} else {
					actor.tryToMap(map.get(network.name + "_" + actor.name))
				}
			}
		}
		else
		{
			for (instance : network.children.actorInstances + network.children.filter(typeof(Actor))) {
				unmapped.add(instance)
			}
		}
	}

	public new(Network network, File xcfFile) {
		setNetwork(network)
		if (!xcfFile.exists || !xcfFile.file)
			throw new OrccRuntimeException("The XCF file does not exist.")

		val builder = DocumentBuilderFactory::newInstance.newDocumentBuilder
		val dom = builder.parse(xcfFile)
		val configuration = dom.documentElement
		configuration.normalize
		val partitioning = configuration.getElementsByTagName("Partitioning").item(0) as Element

		if (partitioning != null) {
			val partitions = partitioning.getElementsByTagName("Partition")

			for (i : 0 .. partitions.length-1) {
				val partNode = partitions.item(i)
				val partition = partNode as Element
				val partName = partition.getAttribute("id")
				val instances = partition.getElementsByTagName("Instance")

				for (j : 0 .. instances.length-1) {
					val instNode = instances.item(j)
					val instance = instNode as Element
					val instName = instance.getAttribute("id")
					val vertex = network.getChild(instName)

					if (vertex != null) {
						tryToMap(vertex, partName)
					} else {
						OrccLogger::warnln("Try to map an unknown actor called " + instName)
					}
				}
			}
		} else {
			throw new OrccRuntimeException("Wrong XCF file")
		}
	}

	private def setNetwork(Network network) {
		this.network = network
		this.mapping = new HashMap<String, List<Vertex>>
		this.invMapping = new HashMap<Vertex, String>
		this.unmapped = new ArrayList<Vertex>
	}

	def private tryToMap(Vertex vertex, String component) {
		if (!component.nullOrEmpty) {
			map(component, vertex)
		} else {
			OrccLogger::warnln("The instance '" + vertex.label + "' is not mapped.")
			unmapped.add(vertex)
		}
	}

	def getContentFile() '''
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
			
			<!-- Other useful informations related to any element of the instanciated model can be printed here -->
		</Configuration>
	'''

	def private getPartition(Iterable<Vertex> entities) '''
		<Partition id="«i = i + 1»">
			«FOR entity : entities»
				<Instance id="«entity.label»"/>
			«ENDFOR»
		</Partition>
	'''

	def getComponents() {
		mapping.keySet
	}

	def getComponent(Vertex v) {
		invMapping.get(v)
	}

	def getMapping() {
		mapping
	}

	def getUnmapped() {
		unmapped
	}

	def map(String component, Vertex v) {
		if (!mapping.containsKey(component)) {
			mapping.put(component, new ArrayList<Vertex>)
		}
		mapping.get(component).add(v)
		invMapping.put(v, component)
	}

}
