/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.compa

import java.util.Map
import net.sf.orcc.df.Action
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.TypeList
import net.sf.orcc.df.Port

/**
 * Generate and print instance source file for COMPA backend.
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {	
	
	new(Map<String, Object> options) {
		super(options)
	}
	
	override protected printStateLabel(State state) '''
		l_«state.name»:
			«IF ! instance.getActor.actionsOutsideFsm.empty»
				i += «instance.name»_outside_FSM_scheduler();
			«ENDIF»
			«IF state.outgoing.empty»
				printf("Stuck in state "«state.name»" in the instance «instance.name»\n");
				exit(1);
			«ELSE»
				«state.printStateTransitions»
			«ENDIF»
	'''
	
	override protected printStateTransitions(State state) '''
		«FOR trans : state.outgoing.map[it as Transition] SEPARATOR " else "»
			if («trans.action.inputPattern.checkInputPattern»isSchedulable_«trans.action.name»()) {
				«IF trans.action.outputPattern != null»
					«trans.action.outputPattern.printOutputPattern»
						_FSM_state = my_state_«state.name»;
						goto finished;
					}
				«ENDIF»
				«trans.action.body.name»();
				i++;
				goto l_«trans.target.name»;
			}«ENDFOR» else {
			«transitionPattern.get(state).printTransitionPattern»
			_FSM_state = my_state_«state.name»;
			goto finished;
		}
	'''
	
	override protected printTransitionPattern(Pattern pattern) {
		// Does nothing, but do not remove. Some C code has to not be printed*
		// in COMPA backend
	}
	
	override protected printActorScheduler() '''
		«IF instance.getActor.hasFsm»
			«printFsm»
		«ELSE»
			int «instance.name»_scheduler() {
				int i = 0;
				«printCallTokensFunctions»
				«instance.getActor.actionsOutsideFsm.printActionSchedulingLoop»
				
			finished:
				«FOR port : instance.getActor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : instance.getActor.outputs.notNative»
					write_end_«port.name»();
				«ENDFOR»
				«IF instance.getActor.inputs.nullOrEmpty && instance.getActor.outputs.nullOrEmpty »
					// no read_end/write_end here!
					return;
				«ENDIF»
				return i;
			}
		«ENDIF»
	'''
	
	override printInitialize() '''
		«FOR init : actor.initializes»
			«init.print»
		«ENDFOR»
		
		void «entityName»_initialize() {
			int i = 0;
			«IF actor.hasFsm»
				/* Set initial state to current FSM state */
				_FSM_state = my_state_«actor.fsm.initialState.name»;
			«ENDIF»
			«IF !actor.initializes.nullOrEmpty»
				«actor.initializes.printActionsScheduling»
			«ENDIF»
			
		finished:
			// no read_end/write_end here!
			return;
		}
	'''

	override protected printFsm() '''
		«IF ! instance.getActor.actionsOutsideFsm.empty»
			int «instance.name»_outside_FSM_scheduler() {
				int i = 0;
				«instance.getActor.actionsOutsideFsm.printActionSchedulingLoop»
			finished:
				// no read_end/write_end here!
				return i;
			}
		«ENDIF»

		int «instance.name»_scheduler() {
			int i = 0;

			«printCallTokensFunctions»

			// jump to FSM state 
			switch (_FSM_state) {
			«FOR state : instance.getActor.fsm.states»
				case my_state_«state.name»:
					goto l_«state.name»;
			«ENDFOR»
			default:
				printf("unknown state in «instance.name».c : %s\n", stateNames[_FSM_state]);
				exit(1);
			}

			// FSM transitions
			«FOR state : instance.getActor.fsm.states»
		«state.printStateLabel»
			«ENDFOR»
		finished:
			«FOR port : instance.getActor.inputs»
				read_end_«port.name»();
			«ENDFOR»
			«FOR port : instance.getActor.outputs.filter[!native]»
				write_end_«port.name»();
			«ENDFOR»
			return i;
		}
	'''
	
	override protected printActionsScheduling(Iterable<Action> actions) '''
		// Action loop
		«FOR action : actions SEPARATOR " else "»
			if («inputPattern.checkInputPattern»isSchedulable_«action.name»()) {
				«IF action.outputPattern != null»
					«action.outputPattern.printOutputPattern»
						goto finished;
					}
				«ENDIF»
				«action.body.name»();
				i++;
			}«ENDFOR» else {
			«inputPattern.printTransitionPattern»
			goto finished;
		}
	'''
	
	
	
	def printMain() '''
		int main(int argc, char *argv[]) {
			int i;
			int stop = 0;
			
		    init_platform();
«««			init_orcc(argc, argv);

			«entityName»_initialize();

			while(!stop) {
				i = 0;
				i += «entityName»_scheduler();
				stop = stop || (i == 0);
			}
			print("End of simulation !\n");
			
			return compareErrors;
		}
	'''
	
		override protected writeTokensFunctions(Port port) '''
		static void write_«port.name»() {
			index_«port.name» = (*«port.fullName»->write_ind);
			numFree_«port.name» = index_«port.name» + fifo_«port.type.doSwitch»_get_room(«port.fullName», NUM_READERS_«port.name»);
		}

		static void write_end_«port.name»() {
			(*«port.fullName»->write_ind) = index_«port.name»;
		}
	'''
	
	override protected getFileContent() '''
		// Source file is "«actor.file»"

		#include <stdio.h>
		#include <stdlib.h>
		«IF checkArrayInbounds»
			#include <assert.h>
		«ENDIF»

		#include "types.h"
		#include "fifoAllocations.h"
		#include "util.h"
		#include "dataflow.h"
		
		#include "platform.h"
		#include "xparameters.h"
		
		void print(char *str);
		void inbyte(){
		}

		«IF profileNetwork || dynamicMapping»
			#include "cycle.h"
		«ENDIF»

		«IF instance != null»
			«instance.printAttributes»
		«ELSE»
			«actor.printAttributes»
		«ENDIF»
		«IF newSchedul»

			#define RING_TOPOLOGY «IF ringTopology»1«ELSE»0«ENDIF»
		«ENDIF»

«««		#define SIZE «fifoSize»

«««		////////////////////////////////////////////////////////////////////////////////
«««		// Instance
«««		extern actor_t «entityName»;
		«IF !actor.inputs.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Input FIFOs
			«FOR port : actor.inputs»
				«if (incomingPortMap.get(port) != null) "extern "» fifo_«port.type.doSwitch»_t *«port.fullName»;
			«ENDFOR»

			////////////////////////////////////////////////////////////////////////////////
			// Input Fifo control variables
			«FOR port : actor.inputs»
				static unsigned int index_«port.name»;
				static unsigned int numTokens_«port.name»;
				#define SIZE_«port.name» «incomingPortMap.get(port).sizeOrDefaultSize»
				#define tokens_«port.name» «port.fullName»->contents
				
				«IF profileNetwork || dynamicMapping»
					extern connection_t connection_«entityName»_«port.name»;
					#define rate_«port.name» connection_«entityName»_«port.name».rate
				«ENDIF»
				
			«ENDFOR»
			«IF enableTrace»
				////////////////////////////////////////////////////////////////////////////////
				// Trace files declaration (in)
				«FOR port : actor.inputs»
					FILE *file_«port.name»;
				«ENDFOR»

			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Predecessors
			«FOR port : actor.inputs»
				«IF incomingPortMap.get(port) != null»
					extern actor_t «incomingPortMap.get(port).source.label»;
				«ENDIF»
			«ENDFOR»
		«ENDIF»
		
		«IF !actor.outputs.filter[! native].nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Output FIFOs
			«FOR port : actor.outputs.filter[! native]»
				extern fifo_«port.type.doSwitch»_t *«port.fullName»;
			«ENDFOR»

			////////////////////////////////////////////////////////////////////////////////
			// Output Fifo control variables
			«FOR port : actor.outputs.filter[! native]»
				static unsigned int index_«port.name»;
				static unsigned int numFree_«port.name»;
				#define NUM_READERS_«port.name» «outgoingPortMap.get(port).size»
				#define SIZE_«port.name» «outgoingPortMap.get(port).get(0).sizeOrDefaultSize»
				#define tokens_«port.name» «port.fullName»->contents

			«ENDFOR»
			«IF enableTrace»
				////////////////////////////////////////////////////////////////////////////////
				// Trace files declaration (out)
				«FOR port : actor.outputs.filter[! native]»
					FILE *file_«port.name»;
				«ENDFOR»

			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Successors
			«FOR port : actor.outputs»
				«FOR successor : outgoingPortMap.get(port)»
					extern actor_t «successor.target.label»;
				«ENDFOR»
			«ENDFOR»

		«ENDIF»
		«IF (instance != null && !instance.arguments.nullOrEmpty) || !actor.parameters.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Parameter values of the instance
			«IF instance != null»
				«FOR arg : instance.arguments»
					«IF arg.value.exprList»
						static «IF (arg.value.type as TypeList).innermostType.uint»unsigned «ENDIF»int «arg.variable.name»«arg.value.type.dimensionsExpr.printArrayIndexes» = «arg.value.doSwitch»;
					«ELSE»
						#define «arg.variable.name» «arg.value.doSwitch»
					«ENDIF»
				«ENDFOR»
			«ELSE»
				«FOR variable : actor.parameters»
					«variable.declare»
				«ENDFOR»
			«ENDIF»

		«ENDIF»
		«IF profileActions && profileNetwork»
			////////////////////////////////////////////////////////////////////////////////
			// Action's workload for profiling
			«FOR action : actor.actions»
				extern action_t action_«actor.name»_«action.body.name»;
				#define ticks_«action.body.name» action_«actor.name»_«action.body.name».ticks
			«ENDFOR»		
			
		«ENDIF»
		
		«IF !actor.stateVars.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// State variables of the actor
			«FOR variable : actor.stateVars»
				«variable.declare»
			«ENDFOR»

		«ENDIF»
		«IF actor.hasFsm»
			////////////////////////////////////////////////////////////////////////////////
			// Initial FSM state of the actor
			enum states {
				«FOR state : actor.fsm.states SEPARATOR ","»
					my_state_«state.name»
				«ENDFOR»
			};

			static char *stateNames[] = {
				«FOR state : actor.fsm.states SEPARATOR ","»
					"«state.name»"
				«ENDFOR»
			};

			static enum states _FSM_state;

		«ENDIF»
		////////////////////////////////////////////////////////////////////////////////
		// Token functions
		«FOR port : actor.inputs»
			«port.readTokensFunctions»
		«ENDFOR»

		«FOR port : actor.outputs.notNative»
			«port.writeTokensFunctions»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Functions/procedures
		«FOR proc : actor.procs»
			«proc.declare»
		«ENDFOR»

		«FOR proc : actor.procs.notNativeProcs»
			«proc.print»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Actions
		«FOR action : actor.actions»
			«action.print()»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«printInitialize»

		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«printActorScheduler»
		
		////////////////////////////////////////////////////////////////////////////////
		// main
		«printMain»

	'''
}

