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
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.df.Action

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
			«IF ! actor.actionsOutsideFsm.empty»
				i += «entityName»_outside_FSM_scheduler();
			«ENDIF»
			«IF state.outgoing.empty»
				printf("Stuck in state "«state.name»" in the instance «entityName»\n");
				exit(1);
			«ELSE»
				«state.printStateTransitions»
			«ENDIF»
	'''
	
	override protected printStateTransitions(State state) '''
		«FOR trans : state.outgoing.map[it as Transition] SEPARATOR " else "»
			if («trans.action.inputPattern.checkInputPattern»«trans.action.scheduler.name»()) {
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
		«IF actor.hasFsm»
			«printFsm»
		«ELSE»
			int «entityName»_scheduler() {
				int i = 0;
				«printCallTokensFunctions»
				«actor.actionsOutsideFsm.printActionSchedulingLoop»

			finished:
				«FOR port : actor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : actor.outputs.notNative»
					write_end_«port.name»();
				«ENDFOR»
				«IF actor.inputs.nullOrEmpty && actor.outputs.nullOrEmpty»
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
		«IF ! actor.actionsOutsideFsm.empty»
			int «entityName»_outside_FSM_scheduler() {
				int i = 0;
				«actor.actionsOutsideFsm.printActionSchedulingLoop»
			finished:
				// no read_end/write_end here!
				return i;
			}
		«ENDIF»

		int «entityName»_scheduler() {
			int i = 0;

			«printCallTokensFunctions»

			// jump to FSM state
			switch (_FSM_state) {
			«FOR state : actor.fsm.states»
				case my_state_«state.name»:
					goto l_«state.name»;
			«ENDFOR»
			default:
				printf("unknown state in «entityName».c : %s\n", stateNames[_FSM_state]);
				exit(1);
			}

			// FSM transitions
			«FOR state : actor.fsm.states»
		«state.printStateLabel»
			«ENDFOR»
		finished:
			«FOR port : actor.inputs»
				read_end_«port.name»();
			«ENDFOR»
			«FOR port : actor.outputs.filter[!native]»
				write_end_«port.name»();
			«ENDFOR»
			return i;
		}
	'''
	
	override protected printActionsScheduling(Iterable<Action> actions) '''
		// Action loop
		«FOR action : actions SEPARATOR " else "»
			if («inputPattern.checkInputPattern»«action.scheduler.name»()) {
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
	
}
