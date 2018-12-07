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
package net.sf.orcc.backends.c

import java.util.HashSet
import java.util.Map
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Entity
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex
import net.sf.orcc.ir.Var

import static net.sf.orcc.backends.BackendsConstants.*

/**
 * Generate and print network source file for C backend.
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends CTemplate {
	
	protected var Network network;
	
	protected var boolean profile = false	
	protected var boolean newSchedul = false
	var boolean papify = false
	var boolean papifyMultiplex = false

	var boolean genWeights = false
	var int genWeightsActionDataCounter = 0
	var int genWeightsSchedulerDataCounter = 0

	var boolean linkNativeLib
	var String linkNativeLibHeaders
		
	def setNetwork(Network network) {
		this.network = network
	}

	override setOptions(Map<String, Object> options) {
		super.setOptions(options)
		if(options.containsKey(PROFILE)){
			profile = options.get(PROFILE) as Boolean
		}
		if (options.containsKey(NEW_SCHEDULER)) {
			newSchedul = options.get(NEW_SCHEDULER) as Boolean
		}
		if(options.containsKey(PAPIFY)){
			papify = options.get(PAPIFY) as Boolean;
			if(options.containsKey(PAPIFY_MULTIPLEX)){
				papifyMultiplex = options.get(PAPIFY_MULTIPLEX) as Boolean;
			}
		}
				
		if(options.containsKey(GEN_WEIGHTS)) {
			genWeights = options.get(GEN_WEIGHTS) as Boolean;
			genWeightsActionDataCounter = 0;
			genWeightsSchedulerDataCounter = 0;
		}

		if(options.containsKey(LINK_NATIVE_LIBRARY)) {
			linkNativeLib = options.get(LINK_NATIVE_LIBRARY) as Boolean;
			linkNativeLibHeaders = options.get(LINK_NATIVE_LIBRARY_HEADERS) as String;
		}
	}

	def protected getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <locale.h>
		#include <stdio.h>
		#include <stdlib.h>
		«printAdditionalIncludes»

		#include "types.h"
		#include "fifo.h"
		#include "util.h"
		#include "dataflow.h"
		#include "serialize.h"
		#include "options.h"
		#include "scheduler.h"

		«IF genWeights»
			#include "rdtsc.h"
			#include <stdint.h>
			
		«ENDIF»
		«IF linkNativeLib && linkNativeLibHeaders != ""»
		«printNativeLibHeaders(linkNativeLibHeaders)»

		«ENDIF»
		/////////////////////////////////////////////////
		// FIFO allocation
		«FOR child : network.children»
			«child.allocateFifos»
		«ENDFOR»

		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR child : network.children»
			«child.assignFifo»
		«ENDFOR»

		«IF profile»
			/////////////////////////////////////////////////
			// Declaration of the actions
			«FOR child : network.children»
				«FOR action : child.getAdapter(typeof(Actor)).actions»
					action_t action_«child.label»_«action.name» = {"«action.name»", 0, 0, -1, -1, -1, 0, 0};
				«ENDFOR»
			«ENDFOR»
			«FOR child : network.children»
				action_t *«child.label»_actions[] = {
					«FOR action : child.getAdapter(typeof(Actor)).actions SEPARATOR ","»
						&action_«child.label»_«action.name»
					«ENDFOR»
				};
				
			«ENDFOR»
		«ENDIF»
		«IF genWeights»
			/////////////////////////////////////////////////
			// Declare rdtsc_data for the actors/actions
			«FOR child : network.children»
				«child.allocateGenWeightsActionData»
			«ENDFOR»

			// Declare rdtsc_data for the actors/scheduler
			«FOR child : network.children»
				«child.allocateGenWeightsSchedulerData»
			«ENDFOR»

		«ENDIF»
		«additionalDeclarations»
		/////////////////////////////////////////////////
		// Actor functions
		«FOR child : network.children»
			extern void «child.label»_initialize(schedinfo_t *si);
			extern void «child.label»_scheduler(schedinfo_t *si);
		«ENDFOR»

		/////////////////////////////////////////////////
		// Declaration of the actors array
		«FOR child : network.children»
			«IF profile»
				actor_t «child.label» = {"«child.label»", «child.label»_initialize, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, «child.label»_actions, «child.getAdapter(typeof(Actor)).actions.size», 0, "«child.getAdapter(typeof(Actor)).getFile().getProjectRelativePath().removeFirstSegments(1).removeFileExtension().toString().replace("/", ".")»", 0, 0, 0};
			«ELSE»
				actor_t «child.label» = {"«child.label»", «child.label»_initialize, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, NULL, 0, 0, "", 0, 0, 0};
			«ENDIF»						
		«ENDFOR»

		actor_t *actors[] = {
			«FOR child : network.children SEPARATOR ","»
				&«child.label»
			«ENDFOR»
		};

		/////////////////////////////////////////////////
		// Declaration of the connections array
		«FOR connection : network.connections»
			connection_t connection_«connection.target.label»_«connection.targetPort.name» = {&«connection.source.label», &«connection.target.label», 0, 0};
		«ENDFOR»

		connection_t *connections[] = {
			«FOR connection : network.connections SEPARATOR ","»
			    &connection_«connection.target.label»_«connection.targetPort.name»
			«ENDFOR»
		};

		/////////////////////////////////////////////////
		// Declaration of the network
		network_t network = {"«network.name»", actors, connections, «network.allActors.size», «network.connections.size»};
		
		«IF network.hasAttribute("network_shared_variables")»
			/////////////////////////////////////////////////
			// Shared Variables
			«FOR v : network.getAttribute("network_shared_variables").objectValue as HashSet<Var>»
				«v.type.doSwitch» «v.name»«FOR dim : v.type.dimensions»[«dim»]«ENDFOR»;
			«ENDFOR»

		«ENDIF»
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main(int argc, char *argv[]) {
			«beforeMain»
			
			options_t *opt = init_orcc(argc, argv);
			set_scheduling_strategy(«IF !newSchedul»"RR"«ELSE»"DD"«ENDIF», opt);
			
			launcher(opt, &network);
			«afterMain»
			
			return compareErrors;
		}
	'''
	
	def protected assignFifo(Vertex vertex) '''
		«FOR connList : vertex.getAdapter(typeof(Entity)).outgoingPortMap.values»
			«printFifoAssign(connList.head.source.label, connList.head.sourcePort, connList.head.<Integer>getValueAsObject("idNoBcast"))»
			«FOR conn : connList»
				«printFifoAssign(conn.target.label, conn.targetPort, conn.<Integer>getValueAsObject("idNoBcast"))»
			«ENDFOR»
			
		«ENDFOR»
	'''
	
	def protected printFifoAssign(String name, Port port, int fifoIndex) '''
		fifo_«port.type.doSwitch»_t *«name»_«port.name» = &fifo_«fifoIndex»;
	'''

	def protected allocateFifos(Vertex vertex) '''
		«FOR connectionList : vertex.getAdapter(typeof(Entity)).outgoingPortMap.values»
			«allocateFifo(connectionList.get(0), connectionList.size)»
		«ENDFOR»
	'''
	
	def protected allocateFifo(Connection conn, int nbReaders) '''
		DECLARE_FIFO(«conn.sourcePort.type.doSwitch», «if (conn.size !== null) conn.size else fifoSize», «conn.<Object>getValueAsObject("idNoBcast")», «nbReaders»)
	'''
	
	def protected allocateGenWeightsActionData(Vertex vertex) '''
		«FOR action : vertex.getAdapter(typeof(Actor)).actions»
			DECLARE_ACTION_PROFILING_DATA(«genWeightsActionDataCounter»)
			rdtsc_data_t *profDataAction_«vertex.label»_«action.name» = &profDataAction_«genWeightsActionDataCounter»;
			«incGenWeightsActionDataCounter»
		«ENDFOR»
	'''
	
	def protected allocateGenWeightsSchedulerData(Vertex vertex) '''
		DECLARE_SCHEDULER_PROFILING_DATA(«genWeightsSchedulerDataCounter», «vertex.getAdapter(typeof(Actor)).actions.length+1», «vertex.getAdapter(typeof(Actor)).actions.length»+1)
		rdtsc_scheduler_map_t *profDataScheduler_«vertex.label» = &profDataScheduler_«genWeightsSchedulerDataCounter»;
		«incGenWeightsSchedulerDataCounter»
	'''
	
	private def incGenWeightsActionDataCounter() {
		genWeightsActionDataCounter++ ''''''
	}
	
	private def incGenWeightsSchedulerDataCounter() {
		genWeightsSchedulerDataCounter++ ''''''
	}
	
	// This method can be override by other backends to print additional includes
	def protected printAdditionalIncludes() ''''''
	
	// This method can be override by other backends to print additional declarations 
	def protected additionalDeclarations() ''''''
	
	// This method can be override by other backends to print additional statements
	// when the program is terminating
	def protected additionalAtExitActions()''''''
	// This method can be override by other backends in case of calling additional 
	// functions before and after the Main function
	def protected afterMain() ''''''
	def protected beforeMain() '''
	«IF papify»
		«IF papifyMultiplex»
			event_init_multiplex();
		«ELSE»
			event_init();	
		«ENDIF»
	«ENDIF»
	'''
}
