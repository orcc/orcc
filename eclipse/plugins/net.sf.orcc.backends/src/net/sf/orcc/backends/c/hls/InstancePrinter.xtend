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
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Port
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Action
import java.io.File

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
		
		#include <hls_stream.h>
		using namespace hls;
		
		typedef signed char i8;
		typedef short i16;
		typedef int i32;
		typedef long long int i64;
		
		typedef unsigned char u8;
		typedef unsigned short u16;
		typedef unsigned int u32;
		typedef unsigned long long int u64;
		
		
		
		////////////////////////////////////////////////////////////////////////////////
		// Predecessor FIFOS
		«FOR port : instance.actor.inputs»
			«IF instance.incomingPortMap.get(port) != null»
				extern stream<«port.type.doSwitch»>	myStream_«(instance.incomingPortMap.get(port).getId(port))»;
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Output FIFOs
		«FOR port : instance.actor.outputs.filter[! native]»
			extern stream<«port.type.doSwitch»>	myStream_«instance.outgoingPortMap.values.head.get(0).getId(port)»;
		«ENDFOR»
		
		
		
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
		// Initializes
		
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«IF instance.actor.hasFsm»
			«printFsm»
		«ELSE»
			void «instance.name»_scheduler() {		
				
				«instance.actor.actionsOutsideFsm.printActionLoop»
			}
		«ENDIF»
	'''
		
	override printFsm() '''
		«IF ! instance.actor.actionsOutsideFsm.empty»
		void «instance.name»_outside_FSM_scheduler() {
			/* Set initial state to current FSM state */
				_FSM_state = my_state_«instance.actor.fsm.initialState.name»;
			«instance.actor.actionsOutsideFsm.printActionLoop»
		}
		«ENDIF»
		
		void «instance.name»_scheduler() {
		/* Set initial state to current FSM state */
				_FSM_state = my_state_«instance.actor.fsm.initialState.name»;
			// jump to FSM state 
			switch (_FSM_state) {
			«FOR state : instance.actor.fsm.states»
				case my_state_«state.name»:
					goto l_«state.name»;
			«ENDFOR»
		
			// FSM transitions
			«FOR state : instance.actor.fsm.states»
		«state.printTransition»
			«ENDFOR»
		
		}
	'''
	
	def getId(Connection connection, Port port) {
		if(connection != null) connection.getAttribute("id").objectValue
		else port.name
	}
	
	override printTransition(State state) '''
		l_«state.name»:
		«IF ! instance.actor.actionsOutsideFsm.empty»
			«instance.name»
		«ENDIF»
		«IF !state.outgoing.empty»
			«schedulingState(state, state.outgoing.map[it as Transition])»
		«ENDIF»
	'''

	override schedulingState(State state, Iterable<Transition> transitions) '''
		«IF ! transitions.empty»
			«actionTestState(state, transitions)»
		«ENDIF»
	'''
	
	override printActions(Iterable<Action> actions) '''
		«IF !actions.empty»
			«actionTest(actions.head, actions.tail)»
		«ENDIF»
	'''
	
	override actionTestState(State srcState, Iterable<Transition> transitions) '''
		if («transitions.head.action.inputPattern.checkInputPattern»isSchedulable_«transitions.head.action.name»()) {
			«IF transitions.head.action.outputPattern != null»
				«transitions.head.action.outputPattern.printOutputPattern»
					_FSM_state = my_state_«srcState.name»;	
					goto finished;
				}
			«ENDIF»
			«transitions.head.action.body.name»();
			i++;
			goto l_«transitions.head.target.name»;
		} else {
			«schedulingState(srcState, transitions.tail)»
		}
	'''
	
	override printOutputPatternPort(Pattern pattern, Port port, Connection successor, int id) '''
		if (!myStream_«instance.outgoingPortMap.values.head.get(0).getId(port)».empty()) {
			stop = 1;
		}
	'''
	
	override checkInputPattern(Pattern pattern)
	'''«FOR port : pattern.ports»!myStream_«(instance.incomingPortMap.get(port).getId(port))».empty() && «ENDFOR»'''
	
	override printInstance(String targetFolder) {
		val content = instanceFileContent
		val file = new File(targetFolder + File::separator + instance.name + ".cpp")
		
		if(needToWriteFile(content, file)) {
			printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
}