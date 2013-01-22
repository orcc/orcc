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
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstStore
import java.util.List
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeList

/*
 * Compile Instance c source code
 *  
 * @author Antoine Lorence and Khaled Jerbi 
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
		#include <stdio.h>
		#include <stdlib.h>
		
		typedef signed char i8;
		typedef short i16;
		typedef int i32;
		typedef long long int i64;
		
		typedef unsigned char u8;
		typedef unsigned short u16;
		typedef unsigned int u32;
		typedef unsigned long long int u64;
		
		
		// Parameter values of the instance
		«FOR arg : instance.arguments»
			«IF arg.value.exprList»
				static «IF (arg.value.type as TypeList).innermostType.uint»unsigned «ENDIF»int «arg.variable.name»«arg.value.type.dimensionsExpr.printArrayIndexes» = «arg.value.doSwitch»;
			«ELSE»
				#define «arg.variable.name» «arg.value.doSwitch»
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Input FIFOS
		«FOR port : instance.actor.inputs»
			«IF instance.incomingPortMap.get(port) != null»
				extern stream<«instance.incomingPortMap.get(port).fifoType.doSwitch»>	«instance.incomingPortMap.get(port).fifoName»;
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Output FIFOs
		«FOR port : instance.actor.outputs.filter[! native]»
			«FOR connection : instance.outgoingPortMap.get(port)»
				extern stream<«connection.fifoType.doSwitch»> «connection.fifoName»;
			«ENDFOR»
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
			
			static enum states _FSM_state = my_state_«instance.actor.fsm.initialState.name»;;
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
		«initializeFunction»
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«IF instance.actor.hasFsm»
			«printFsm»
		«ELSE»
			void «instance.name»_scheduler() {		
				
				«instance.actor.actionsOutsideFsm.printActionLoop»
				
			finished:
				return;
			}
		«ENDIF»
	'''
		
	override printFsm() '''
		«IF ! instance.actor.actionsOutsideFsm.empty»
			void «instance.name»_outside_FSM_scheduler() {
				«instance.actor.actionsOutsideFsm.printActionLoop»
			finished:
				return;
			}
		«ENDIF»
		
		void «instance.name»_scheduler() {
			// jump to FSM state 
			switch (_FSM_state) {
				«FOR state : instance.actor.fsm.states»
					case my_state_«state.name»:
						goto l_«state.name»;
				«ENDFOR»
			default:
				goto finished;
			}
			// FSM transitions
			«FOR state : instance.actor.fsm.states»
		«state.printStateLabel»
			«ENDFOR»
		finished:
			return;
		}
	'''
	
	def getId(Connection connection, Port port) {
		if(connection != null) connection.getAttribute("id").objectValue
		else port.name
	}
	
	override printStateLabel(State state) '''
		l_«state.name»:
			«IF ! instance.actor.actionsOutsideFsm.empty»
				«instance.name»_outside_FSM_scheduler();
			«ENDIF»
			«IF !state.outgoing.empty»
				«printStateTransitions(state)»
			«ENDIF»
	'''
	
	override printOutputPattern(Pattern pattern) '''
		«FOR port : pattern.ports» 
			«printOutputPatternsPort(pattern, port)»
		«ENDFOR»
	'''
	
	override printOutputPatternsPort(Pattern pattern, Port port) {
		var i = -1 '''
		«FOR successor : instance.outgoingPortMap.get(port)»
			 «printOutputPatternPort(pattern, port, successor, i = i + 1)»
		«ENDFOR»
	'''
	}
	
	override printOutputPatternPort(Pattern pattern, Port port, Connection successor, int id) 
	'''&& (! «instance.outgoingPortMap.get(port).head.fifoName».full())'''
	
	override checkInputPattern(Pattern pattern)
	'''«FOR port : pattern.ports»!«instance.incomingPortMap.get(port).fifoName».empty() &&«ENDFOR»'''
	
	override printInstance(String targetFolder) {
		val content = instanceFileContent
		val scriptContent = script(targetFolder);
		val directiveContent = directive(targetFolder);
		val file = new File(targetFolder + File::separator + instance.name + ".cpp")
		val scriptFile = new File(targetFolder+ File::separator+"subProject_"+ instance.name + File::separator + "script_" + instance.name + ".tcl"
		)
		val directiveFile = new File(targetFolder+ File::separator+"subProject_"+ instance.name  + File::separator + "directive_" + instance.name + ".tcl")
		
		if(needToWriteFile(content, file)) {
			printFile(scriptContent, scriptFile)
			printFile(directiveContent, directiveFile)
			printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	override actionTest(Action action, Iterable<Action> others) '''
		if («action.inputPattern.checkInputPattern»isSchedulable_«action.name»()) {
			«IF action.outputPattern != null»
				«action.outputPattern.printOutputPattern»
			«ENDIF»
			«instance.name»_«action.body.name»();
		} else {
			«others.printActions»
		}
	'''
	
	override print(Action action) {
		currentAction = action
		val output = '''
			static void «instance.name»_«action.body.name»() {
				«FOR variable : action.body.locals»
					«variable.declare»;
				«ENDFOR»
			
				«FOR block : action.body.blocks»
					«block.doSwitch»
				«ENDFOR»
			}
			
			«action.scheduler.print»
			
		'''
		currentAction = null
		return output
	}
	
	override caseInstLoad(InstLoad load) {
		if(load.eContainer != null){
		val srcPort = load.source.variable.getPort
		'''
			«IF srcPort != null»
				 «instance.incomingPortMap.get(srcPort).fifoName».read_nb(«load.target.variable.indexedName»);
			«ELSE»
				«load.target.variable.indexedName» = «load.source.variable.name»«load.indexes.printArrayIndexes»;
			«ENDIF»
		'''
		
		}
	}

	
	override caseInstStore(InstStore store) {
		val trgtPort = store.target.variable.port
		'''
		«IF trgtPort != null»
				«instance.outgoingPortMap.get(trgtPort).head.fifoName».write_nb(«store.value.doSwitch»);
		«ELSE»
			«store.target.variable.name»«store.indexes.printArrayIndexes» = «store.value.doSwitch»;
		«ENDIF»
		'''
	}
	 
	override printActionLoop(List<Action> actions) '''
		«actions.printActions»
	'''
	
	def fifoName(Connection connection) '''
		«IF connection != null»
			myStream_«connection.getAttribute("id").objectValue»
		«ENDIF»
	'''
	
	def fifoType(Connection connection) {
		if(connection.sourcePort == null){
		connection.targetPort.type}
		else{
			connection.sourcePort.type
		}
	}
	
	override initializeFunction() '''
		«IF ! instance.actor.initializes.empty»
			«FOR init : instance.actor.initializes»
				«init.print»
			«ENDFOR»
			
			static void initialize() {
				
				«instance.actor.initializes.printActions»
				
			finished:
				// no read_end/write_end here!
				return;
			}
			
		«ENDIF»
	'''
	
	override printActions(Iterable<Action> actions) '''
		«IF !actions.empty»
			«actionTest(actions.head, actions.tail)»
		«ELSE»
			goto finished;
		«ENDIF»
	'''
	
	override printStateTransitions(State state) '''
		«FOR transitions : state.outgoing.map[it as Transition] SEPARATOR " else "»
			«IF transitions.action.outputPattern == null»
				if («transitions.action.inputPattern.checkInputPattern»isSchedulable_«transitions.action.name»()) {
			«ELSE»
				if («transitions.action.inputPattern.checkInputPattern»isSchedulable_«transitions.action.name»() «transitions.action.outputPattern.printOutputPattern») {
			«ENDIF»	
			«instance.name»_«transitions.action.body.name»();
			«IF transitions.target != state»
				_FSM_state = my_state_«transitions.target.name»;
				goto finished;
			«ELSE»
				goto l_«transitions.target.name»;
			«ENDIF»
			}«ENDFOR» else {
			_FSM_state = my_state_«state.name»;
			goto finished;
		}
	'''
	
	override caseTypeBool(TypeBool type) 
		'''bool'''
		
	def script (String path)'''
	open_project subProject
	set_top «instance.name»_scheduler
	add_files ../«instance.name».cpp
	
	open_solution "solution"
	set_part  {xc7a100tcsg324-1}
	create_clock -period 10
	
	source "directive_«instance.name».tcl"
	csynth_design
	exit
	exit
	'''
	
	def directive (String path)'''
	 #set_directives
	'''
}