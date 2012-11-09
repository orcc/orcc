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
package net.sf.orcc.backends.c.hls

import static net.sf.orcc.backends.OrccBackendsConstants.*
import static net.sf.orcc.OrccLaunchConstants.*
import java.util.Map
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex
import net.sf.orcc.util.OrccLogger
import java.util.HashMap
import java.io.File
import net.sf.orcc.backends.c.CTemplate

/**
 * Compile top Network c source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends CTemplate {
	
	val Network network;
	val int fifoSize;
	
	val int numberOfGroups
	val Map<Instance, Integer> instanceToIdMap
	
	new(Network network, Map<String, Object> options) {
		this.network = network
		
		if (options.containsKey(FIFO_SIZE)) {
			fifoSize = options.get(FIFO_SIZE) as Integer
		} else {
			fifoSize = DEFAULT_FIFO_SIZE
		}
		
		overwriteAllFiles = options.get(DEBUG_MODE) as Boolean
		
		//Template data :
		// TODO : set the right values when genetic algorithm will be fixed
		numberOfGroups = 0
		instanceToIdMap = computeInstanceToIdMap
	}
	
	def print(String targetFolder) {
		
		val content = networkFileContent
		val file = new File(targetFolder + File::separator + network.simpleName + ".c")
		
		if(needToWriteFile(content, file)) {
			printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <locale.h>
		#include <stdio.h>
		#include <stdlib.h>
		
		#ifndef _WIN32
		#define __USE_GNU
		#endif
		
		#include "orcc_types.h"
		#include "orcc_fifo.h"
		#include "orcc_scheduler.h"
		#include "orcc_util.h"

		
		#define SIZE «fifoSize»
		// #define PRINT_FIRINGS

		/////////////////////////////////////////////////
		// FIFO allocation
		«FOR vertice : network.children.filter(typeof(Instance)).filter[isActor]»
			«vertice.allocateFifos»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			«instance.assignFifo»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Action schedulers
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			extern void «instance.name»_initialize(«FOR port : instance.actor.inputs SEPARATOR ", "»unsigned int fifo_«port.name»_id«ENDFOR»);
		«ENDFOR»
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			extern void «instance.name»_scheduler(struct schedinfo_s *si);
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Declaration of a struct actor for each actor
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			struct actor_s «instance.name»;
		«ENDFOR»

		/////////////////////////////////////////////////
		// Declaration of the actors array
		«FOR instance : network.children.filter(typeof(Instance)).filter[isActor]»
			struct actor_s «instance.name» = {"«instance.name»", «instanceToIdMap.get(instance)», «instance.name»_scheduler, «instance.actor.inputs.size»0, «instance.actor.outputs.size», 0, 0, NULL, 0};			
		«ENDFOR»
		
		struct actor_s *actors[] = {
			«FOR instance : network.children.filter(typeof(Instance)).filter[isActor] SEPARATOR ","»
				&«instance.name»
			«ENDFOR»
		};
		
		/////////////////////////////////////////////////
		// Initializer and launcher
		void initialize_instances() {
			«FOR instance : network.children.filter(typeof(Instance))»
				«IF instance.isActor»
					«instance.name»_initialize(«FOR port : instance.actor.inputs SEPARATOR ","»«if (instance.incomingPortMap.get(port) != null) instance.incomingPortMap.get(port).<Object>getValueAsObject("fifoId") else "-1"»«ENDFOR»);
				«ENDIF»
			«ENDFOR»
		}
		
		struct scheduler_s scheduler;
		
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main(int argc, char *argv[]) {
			initialize_instances();
			
			while(1) {
				«FOR instance : network.children.filter(typeof(Instance))»
					«IF instance.isActor»
						«instance.name»_scheduler(scheduler);
					«ENDIF»
				«ENDFOR»
			}
			return compareErrors;
		}
	'''
	
	// TODO : simplify this :
	def assignFifo(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF !(connList.head.source instanceof Port) && !(connList.head.target instanceof Port)»
				«printFifoAssign(connList.head.source, connList.head.sourcePort, connList.head.<Integer>getValueAsObject("idNoBcast"))»
			«ENDIF»
			«FOR conn : connList»
				«IF conn.source instanceof Instance && conn.target instanceof Instance»
					«printFifoAssign(conn.target as Instance, conn.targetPort, conn.<Integer>getValueAsObject("idNoBcast"))»
				«ENDIF»
			«ENDFOR»
			
		«ENDFOR»
	'''
	
	def printFifoAssign(Vertex vertex, Port port, int fifoIndex) '''
		«IF vertex instanceof Instance»struct fifo_«port.type.doSwitch»_s *«(vertex as Instance).name»_«port.name» = &fifo_«fifoIndex»;«ENDIF»
	'''


	def clearFifosWithMap(Instance instance) '''
		«FOR connList : instance.outgoingPortMap.values»
			«IF connList.get(0).source instanceof Instance && connList.get(0).target instanceof Instance»
				«clearFifo(connList.get(0))»
			«ENDIF»
		«ENDFOR»
	'''
	
	def clearFifo(Connection connection) '''
		«IF connection.source instanceof Actor»
			fifo_«connection.sourcePort.type.doSwitch»_clear(&fifo_«connection.getAttribute("idNoBcast")»);
		«ELSE»
			fifo_«connection.targetPort.type.doSwitch»_clear(&fifo_«connection.getAttribute("id")»);
		«ENDIF»
	'''

	def allocateFifos(Instance instance) '''
		«FOR connectionList : instance.outgoingPortMap.values»
			«allocateFifo(connectionList.get(0), connectionList.size)»
		«ENDFOR»
	'''
	
	def allocateFifo(Connection conn, int nbReaders) '''
		«IF conn.source instanceof Instance»
			DECLARE_FIFO(«conn.sourcePort.type.doSwitch», «if (conn.size != null) conn.size else "SIZE"», «conn.<Object>getValueAsObject("idNoBcast")», «nbReaders»)
		«ELSEIF conn.target instanceof Instance»
			DECLARE_FIFO(«conn.targetPort.type.doSwitch», «if (conn.size != null) conn.size else "SIZE"», «conn.<Object>getValueAsObject("idNoBcast")», «nbReaders»)
		«ELSE»
			«/* TODO: debug to find types of source & target when compiling a non-top network */»
			«OrccLogger::warnln("An edge has both source and target linked to a non-Actor vertex.")»
		«ENDIF»
	'''

	def computeInstanceToIdMap() {
		val instanceToIdMap = new HashMap<Instance, Integer>
		
		for(instance : network.children.filter(typeof(Instance))) {
			// TODO : compute the right value when genetic algorithm will be fixed
			instanceToIdMap.put(instance, 0)
		}
		
		return instanceToIdMap
	}
}