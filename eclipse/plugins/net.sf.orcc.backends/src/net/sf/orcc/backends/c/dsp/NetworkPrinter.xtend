/*
 * Copyright (c) 2015, IETR/INSA of Rennes, UPM Madrid
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
package net.sf.orcc.backends.c.dsp

import java.util.Map
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Entity
import net.sf.orcc.df.Network
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex

/**
 * Generate and print network source file for DSP backend.
 *  
 * @author Miguel Chavarrias
 * @author Alexandre Sanchez
 *  
 */

import static net.sf.orcc.backends.BackendsConstants.*

class NetworkPrinter extends CTemplate {
	
	protected var Network network;
	
	protected var boolean profile = false	
	protected var boolean newSchedul = false
	var boolean papify = false
	
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
		
		#include <ti/csl/csl_cacheAux.h>

		#define SIZE «fifoSize»

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

		«additionalDeclarations»

		/////////////////////////////////////////////////
		// Actor functions
		«FOR child : network.children»
			extern void «child.label»_printWbInv(schedinfo_t *si);
			extern void «child.label»_initializecores(schedinfo_t *si);
			extern void «child.label»_setcores(schedinfo_t *si);
			extern void «child.label»_initialize(schedinfo_t *si);
			extern void «child.label»_scheduler(schedinfo_t *si);
		«ENDFOR»

		/////////////////////////////////////////////////
		// Declaration of the actors array
		«FOR child : network.children»
			«IF profile»
				actor_t «child.label» = {"«child.label»", «child.label»_printWbInv, «child.label»_setcores, «child.label»_initializecores, «child.label»_initialize, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, «child.label»_actions, «child.getAdapter(typeof(Actor)).actions.size», 0, "«child.getAdapter(typeof(Actor)).getFile().getProjectRelativePath().removeFirstSegments(1).removeFileExtension().toString().replace("/", ".")»", 0, 0, 0};
			«ELSE»
				actor_t «child.label» = {"«child.label»", «child.label»_printWbInv, «child.label»_setcores, «child.label»_initializecores, «child.label»_initialize, «child.label»_scheduler, 0, 0, 0, 0, NULL, -1, «network.children.indexOf(child)», 0, 1, 0, 0, 0, NULL, 0, 0, "", 0, 0, 0};
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
		
		
		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main() {
			«beforeMain»
			
			/*
            REG(KICK0) = KICK0_UNLOCK;
            REG(KICK1) = KICK1_UNLOCK;

            printf("Frecuencia a 1.2GHz ...\n");
            MAINPLLCTL0 = 0x0B000000;
            PLL1_PLLM = 0x17;


            //DDR3 a 800MHz
            DDR3PLLCTL0 &= ~(0xFF03FFFF);  //limpieza del registro
            DDR3PLLCTL0 |= (0x17 << 6);
            DDR3PLLCTL0 |= (0x0B << 24);

            REG(KICK1) = KICK_LOCK;

            printf("configurado\n");
            */

            /*
            printf("Frecuencia a 0.8GHz ...\n");
            MAINPLLCTL0 = 0x07000000;
            PLL1_PLLM = 0x0F;

            printf("Frecuencia a 1.2GHz ...\n");
            MAINPLLCTL0 = 0x0B000000;
            PLL1_PLLM = 0x17;

            REG(KICK1) = KICK_LOCK;
            */
            
            /////////////////////////////////////////////////
			// Memset allocation
			«FOR child : network.children»
				«child.allocateMemsets»
			«ENDFOR»

            CACHE_setL1PSize(CACHE_L1_32KCACHE);
            CACHE_setL1DSize(CACHE_L1_32KCACHE);
            CACHE_setL2Size(CACHE_256KCACHE);

            int argc=7;
            char* argv[7];

            char arg0[3]="-i";
            char arg1[150]="/home/mchavarrias/Tesis/Secuencias/secuencias_HM_10/ld_main/BQMall_832x480_60_qp32.bin";
            char arg2[3]="-f";
            char arg3[150]="700";
            char arg4[3]="-m";
            char arg5[150]="/home/mchavarrias/Tesis/distribuciones/YUV/n_cores_MIO_YUV.xcf";

            argv[1] = &arg0[0];
            argv[2] = &arg1[0];
            argv[3] = &arg2[0];
            argv[4] = &arg3[0];
            argv[5] = &arg4[0];
            argv[6] = &arg5[0]; 
			
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
	
	def protected allocateMemsets(Vertex vertex) '''
		«FOR connectionList : vertex.getAdapter(typeof(Entity)).outgoingPortMap.values»
			«allocateMemset(connectionList.get(0), connectionList.size)»
		«ENDFOR»
	'''	
	
	def protected allocateFifo(Connection conn, int nbReaders) '''
		#pragma DATA_SECTION (invalidar_«conn.<Object>getValueAsObject("idNoBcast")», ".aux_mem");
		#pragma DATA_SECTION (fifo_«conn.<Object>getValueAsObject("idNoBcast")», ".aux_mem");
		#pragma DATA_SECTION (read_inds_«conn.<Object>getValueAsObject("idNoBcast")», ".aux_mem");
	 	#pragma DATA_SECTION (array_«conn.<Object>getValueAsObject("idNoBcast")», ".aux_mem");
        #pragma DATA_ALIGN (fifo_«conn.<Object>getValueAsObject("idNoBcast")», 128);
        #pragma DATA_ALIGN (array_«conn.<Object>getValueAsObject("idNoBcast")», 128);
        #pragma DATA_ALIGN (read_inds_«conn.<Object>getValueAsObject("idNoBcast")», 128);
        #pragma DATA_ALIGN (core_maps_«conn.<Object>getValueAsObject("idNoBcast")», 128); 
		#pragma DATA_ALIGN (invalidar_«conn.<Object>getValueAsObject("idNoBcast")», 128); 
		DECLARE_FIFO(«conn.sourcePort.type.doSwitch», «if (conn.size != null) conn.size else "SIZE"», «conn.<Object>getValueAsObject("idNoBcast")», «nbReaders»)
	'''
	
	def protected allocateMemset(Connection conn, int nbReaders) '''
		memset(read_inds_«conn.<Object>getValueAsObject("idNoBcast")», 0, «nbReaders»*4);
		memset(fifo_«conn.<Object>getValueAsObject("idNoBcast")», 0, 4);
		memset(array_«conn.<Object>getValueAsObject("idNoBcast")», 0, SIZE*sizeof(«conn.sourcePort.type.doSwitch»));
    '''    
	
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
		event_init();
	«ENDIF»
	'''
	
}