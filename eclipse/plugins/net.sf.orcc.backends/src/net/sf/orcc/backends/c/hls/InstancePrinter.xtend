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

import net.sf.orcc.df.Instance
import java.util.Map

/*
 * Compile Instance c source code
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {
	
	new(Instance instance, Map<String, Object> options) {
		super(instance, options)
	}
	
	override getInstanceFileContent() '''
		// Source file is "«instance.actor.file»"
		
		#include <stdio.h>
		#include <stdlib.h>
		
		#include "orcc_types.h"
		#include "orcc_fifo.h"
		
		#define SIZE «fifoSize»
		«instance.printAttributes»

		
		////////////////////////////////////////////////////////////////////////////////
		// Instance
		
		////////////////////////////////////////////////////////////////////////////////
		// Input FIFOs
		«FOR port : instance.actor.inputs»
			«if (instance.incomingPortMap.get(port) != null) "extern "»struct fifo_«port.type.doSwitch»_s *«port.fullName»;
		«ENDFOR»
		«FOR port : instance.actor.inputs»
			static unsigned int index_«port.name»;
			static unsigned int numTokens_«port.name»;
			#define NUM_READERS_«port.name» «port.getNumReaders»
			#define SIZE_«port.name» «instance.incomingPortMap.get(port).sizeOrDefaultSize»
			#define tokens_«port.name» «port.fullName»->contents
			
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Predecessors
		«FOR port : instance.actor.inputs»
			«IF instance.incomingPortMap.get(port) != null»
				extern struct actor_s «(instance.incomingPortMap.get(port).source as Instance).name»;
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Output FIFOs
		«FOR port : instance.actor.outputs.filter[! native]»
			extern struct fifo_«port.type.doSwitch»_s *«port.fullName»;
		«ENDFOR»
		«FOR port : instance.actor.outputs.filter[! native]»
			static unsigned int index_«port.name»;
			static unsigned int numFree_«port.name»;
			#define NUM_READERS_«port.name» «instance.outgoingPortMap.get(port).size»
			#define SIZE_«port.name» «instance.outgoingPortMap.get(port).get(0).sizeOrDefaultSize»
			#define tokens_«port.name» «port.fullName»->contents
			
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Successors
		«FOR port : instance.actor.outputs»
			«FOR successor : instance.outgoingPortMap.get(port)»
				extern struct actor_s «(successor.target as Instance).name»;
			«ENDFOR»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Input FIFOs Id
		«FOR port : instance.actor.inputs»
			static unsigned int fifo_«port.fullName»_id;
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Parameter values of the instance
		«instanceArgs»
		
		////////////////////////////////////////////////////////////////////////////////
		// State variables of the actor
		«FOR variable : instance.actor.stateVars»
			«variable.declareStateVar»
		«ENDFOR»
		«IF instance.actor.hasFsm»
			////////////////////////////////////////////////////////////////////////////////
			// Initial FSM state of the actor
			enum states {
				«FOR state : instance.actor.fsm.states SEPARATOR ","»
					my_state_«state.name»
				«ENDFOR»
			};
			
			static char *stateNames[] = {
				«FOR state : instance.actor.fsm.states SEPARATOR ","»
					"«state.name»"
				«ENDFOR»
			};
			
			static enum states _FSM_state;
		«ENDIF»
		////////////////////////////////////////////////////////////////////////////////
		// Functions/procedures
		«FOR proc : instance.actor.procs»
			«IF proc.native»extern«ELSE»static«ENDIF» «proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ", [variable.declare])»);
		«ENDFOR»
		
		«FOR proc : instance.actor.procs.filter[!native]»
			«proc.print»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Actions
		«FOR action : instance.actor.actions»
			«action.print»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Token functions
		«FOR port : instance.actor.inputs»
			«port.readTokensFunctions»
		«ENDFOR»
		
		«FOR port : instance.actor.outputs.filter[!native]»
			«port.writeTokensFunctions»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«initializeFunction»
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«IF instance.actor.hasFsm»
			«printFsm»
		«ELSE»
			void «instance.name»_scheduler(struct schedinfo_s *si) {
				int i = 0;
				si->ports = 0;
			
				«printCallTokensFunctions»
				
				«instance.actor.actionsOutsideFsm.printActionLoop»
				
			finished:
				
				«FOR port : instance.actor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : instance.actor.outputs.filter[!native]»
					write_end_«port.name»();
				«ENDFOR»
			}
		«ENDIF»
	'''


}