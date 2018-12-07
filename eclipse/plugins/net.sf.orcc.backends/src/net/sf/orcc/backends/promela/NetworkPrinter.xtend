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
package net.sf.orcc.backends.promela

import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Network

/**
 * Compile top Network Promela source code 
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends PromelaTemplate {
	
	var Network network;
	
	def setNetwork(Network network) {
		this.network = network
	}

	def getNetworkFileContent() '''
		// Generated from "«network.name»"
		
		#define uint int
		#define SIZE 1
		
		// FIFO sizes
		«FOR connection : network.connections»
			«connection.setSizesFifo»
		«ENDFOR»

		// FIFO allocation
		«FOR connection : network.connections»
			«connection.allocateFifo»
		«ENDFOR»
		
		// FIFO assignment
		«FOR connection : network.connections»
			«connection.assignFifo»
		«ENDFOR»
		
		int promela_prog_initiated=0;
		
		int promela_has_progress=0;
		
		// Include the actors
		#ifdef MANAGED
		#include "tmp_include_actors.pml"
		#else
		«FOR actor : network.children.filter(typeof(Actor))»
			#include "«actor.simpleName».pml"
		«ENDFOR»
		#endif
		
		proctype dummy() {
		chan_0?promela_prog_initiated;}
		
		init {
			/*Inputs here*/
			#ifdef MANAGED
			#include "tmp_state.pml"
			#endif
			promela_prog_initiated=1;
		
			/*Start processes*/
			atomic{
				#ifdef MANAGED
				#include "tmp_start_actors.pml"
				#else
				«FOR actor : network.children.filter(typeof(Actor))»
					run «actor.simpleName»();
				«ENDFOR»
				#endif
			}	
		}
		#ifdef MANAGED
		#include "tmp_ltl_expr.pml"
		#endif
	'''

	def setSizesFifo(Connection connection) { 
		val size = if (connection.sourcePort===null || connection.targetPort===null) 1000
					else if (connection.size !== null) connection.size
					else "SIZE"
		'''
			«IF connection.sourcePort !== null»
			#define chan_«(connection.source as Actor).simpleName»_«connection.sourcePort.name»_SIZE «size»
			«ENDIF»
			«IF connection.targetPort !== null»
			#define chan_«(connection.target as Actor).simpleName»_«connection.targetPort.name»_SIZE «size»
			«ENDIF»
		'''
	}

	def allocateFifo(Connection connection) { 
		'''
			«IF connection.sourcePort !== null»
				chan chan_«connection.<Object>getValueAsObject("id")» = [chan_«(connection.source as Actor).simpleName»_«connection.sourcePort.name»_SIZE] of {«connection.sourcePort.type.doSwitch»};
			«ELSE»
				chan chan_«connection.<Object>getValueAsObject("id")» = [chan_«(connection.target as Actor).simpleName»_«connection.targetPort.name»_SIZE] of {«connection.targetPort.type.doSwitch»};
			«ENDIF»
		'''
	}
	
	def assignFifo(Connection connection) '''
		«IF connection.sourcePort !== null»
			#define chan_«(connection.source as Actor).simpleName»_«connection.sourcePort.name» chan_«connection.<Object>getValueAsObject("id")»
		«ENDIF»
		«IF connection.targetPort !== null»
			#define chan_«(connection.target as Actor).simpleName»_«connection.targetPort.name» chan_«connection.<Object>getValueAsObject("id")»
		«ENDIF»
	'''
}