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

import net.sf.orcc.util.OrccUtil
import java.io.File
import java.util.List
import net.sf.orcc.df.Action
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeList
import java.util.Map

/*
 * Compile Instance c source code
 *  
 * @author Antoine Lorence and Khaled Jerbi 
 * 
 */
class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {

	new(Map<String, Object> options) {
		super(options)
	}

	override getFileContent() '''
			// Source file is "«instance.getActor.file»"
			
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
				static «IF (arg.value.type as TypeList).innermostType.uint»unsigned «ENDIF»int «arg.variable.name»«arg.value.type.
			dimensionsExpr.printArrayIndexes» = «arg.value.doSwitch»;
			«ELSE»
				#define «arg.variable.name» «arg.value.doSwitch»
			«ENDIF»
			«ENDFOR»
			
			////////////////////////////////////////////////////////////////////////////////
			// Input FIFOS
			
			
			
			«FOR port : instance.getActor.inputs»
			«val connection = instance.incomingPortMap.get(port)»
			«IF connection.sourcePort == null»
				extern stream<«instance.incomingPortMap.get(port).fifoTypeIn.doSwitch»>	«instance.incomingPortMap.get(port).
			fifoName»;
			«ELSE»
				extern «instance.incomingPortMap.get(port).fifoTypeIn.doSwitch»	«instance.incomingPortMap.get(port).ramName»[512];
				extern unsigned int	«instance.incomingPortMap.get(port).wName»[1];
				extern unsigned int	«instance.incomingPortMap.get(port).rName»[1];
				unsigned int «instance.incomingPortMap.get(port).localrName»=0;
			«ENDIF»
			«ENDFOR»
			
		
			
			////////////////////////////////////////////////////////////////////////////////
			// Output FIFOs
			«FOR port : instance.getActor.outputs.filter[! native]»
				«FOR connection : instance.outgoingPortMap.get(port)»
					«IF connection.targetPort == null»
						extern stream<«connection.fifoTypeOut.doSwitch»> «connection.fifoName»;
					«ELSE»
						extern «connection.fifoTypeOut.doSwitch» «connection.ramName»[512];
						extern unsigned int «connection.wName»[1];
						extern unsigned int «connection.rName»[1];
						unsigned int «connection.localwName»=0;
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			
			«IF instance.getActor.outputs.empty»
				extern stream<int> outFIFO_«instance.name»;
			«ENDIF»
			
			
			
			
			////////////////////////////////////////////////////////////////////////////////
			// State variables of the actor
			«FOR variable : instance.getActor.stateVars»
				«variable.declareStateVar»
			«ENDFOR»
			«IF instance.getActor.hasFsm»
				////////////////////////////////////////////////////////////////////////////////
				// Initial FSM state of the actor
				enum states {
					«FOR state : instance.getActor.fsm.states SEPARATOR ","»
						my_state_«state.name»
					«ENDFOR»
				};
				
				static enum states _FSM_state = my_state_«instance.getActor.fsm.initialState.name»;
			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Functions/procedures
			«FOR proc : instance.getActor.procs»
				«IF proc.native»extern«ELSE»static«ENDIF» «proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ",
			[variable.declare])»);
			«ENDFOR»
			
			«FOR proc : instance.getActor.procs.filter[!native]»
				«proc.print»
			«ENDFOR»
			
			////////////////////////////////////////////////////////////////////////////////
			// Actions
			«FOR action : instance.getActor.actions»
				«action.print»
			«ENDFOR»
			
			////////////////////////////////////////////////////////////////////////////////
			// Initializes
			«initializeFunction»
			
			////////////////////////////////////////////////////////////////////////////////
			// Action scheduler
			«IF instance.getActor.hasFsm»
				«printFsm»
			«ELSE»
				void «instance.name»_scheduler() {		
				«IF instance.getActor.outputs.empty»
					if (! outFIFO_«instance.name».full()){
						outFIFO_«instance.name».write(0);
					}
				«ENDIF»
				«instance.getActor.actionsOutsideFsm.printActionSchedulingLoop»
				
				finished:
					return;
				}
			«ENDIF»
	'''

	override printFsm() '''
		«IF ! instance.getActor.actionsOutsideFsm.empty»
			void «instance.name»_outside_FSM_scheduler() {
				«instance.getActor.actionsOutsideFsm.printActionSchedulingLoop»
			finished:
				return;
			}
		«ENDIF»
		
		void «instance.name»_scheduler() {
			// jump to FSM state 
			«IF instance.getActor.outputs.empty»
				if (! outFIFO_«instance.name».full()){
					outFIFO_«instance.name».write(0);
				}
			«ENDIF»
			switch (_FSM_state) {
				«FOR state : instance.getActor.fsm.states»
					case my_state_«state.name»:
						goto l_«state.name»;
				«ENDFOR»
			default:
				goto finished;
			}
			// FSM transitions
		«FOR state : instance.getActor.fsm.states»
			«state.printStateLabel»
		«ENDFOR»
		finished:
			return;
		}
	'''

	def getId(Connection connection, Port port) {
		if(connection != null) connection.getAttribute("id").objectValue else port.name
	}

	override printStateLabel(State state) '''
		l_«state.name»:
			«IF ! instance.getActor.actionsOutsideFsm.empty»
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
		var i = -1
		'''
			«FOR successor : instance.outgoingPortMap.get(port)»
				«printOutputPatternPort(pattern, port, successor, i = i + 1)»
			«ENDFOR»
		'''
	}

	override printOutputPatternPort(Pattern pattern, Port port, Connection successor, int id) '''
	«IF instance.outgoingPortMap.get(port).head.targetPort == null»
		«IF !instance.outgoingPortMap.get(port).head.fifoName.toString.empty»
			&& (! «instance.outgoingPortMap.get(port).head.fifoName».full())
		«ENDIF»
	«ELSE»
		&& (512 - «instance.outgoingPortMap.get(port).head.localwName» + «instance.outgoingPortMap.get(port).head.rName»[0] >= «pattern.
		getNumTokens(port)»)			
	«ENDIF»'''

	override checkInputPattern(Pattern pattern) '''«FOR port : pattern.ports»
		«IF instance.incomingPortMap.get(port).sourcePort == null»
			«IF !instance.incomingPortMap.get(port).fifoName.toString.empty»
				!«instance.incomingPortMap.get(port).fifoName».empty() &&
			«ENDIF»
		«ELSE»
			«instance.incomingPortMap.get(port).wName»[0] - «instance.incomingPortMap.get(port).localrName» >= «pattern.
		getNumTokens(port)»  &&
		«ENDIF»
	«ENDFOR»'''

	override print(String targetFolder) {
		val content = getFileContent
		val scriptContent = script(targetFolder);
		val directiveContent = directive(targetFolder);
		val file = new File(targetFolder + File::separator + instance.name + ".cpp")
		val scriptFile = new File(
			targetFolder + File::separator + "script_" + instance.name + ".tcl"
		)
		val directiveFile = new File(targetFolder + File::separator + "directive_" + instance.name + ".tcl")

		if (needToWriteFile(content, file)) {
			OrccUtil::printFile(scriptContent, scriptFile)
			OrccUtil::printFile(directiveContent, directiveFile)
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

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
		if (load.eContainer != null) {
			val srcPort = load.source.variable.getPort
			'''
				«IF (srcPort != null)» 
					«IF instance.incomingPortMap.get(srcPort).sourcePort == null»
						«IF !instance.incomingPortMap.get(srcPort).fifoName.toString.empty»
							«instance.incomingPortMap.get(srcPort).fifoName».read_nb(«load.target.variable.name»);
						«ENDIF»
					«ELSE»
						i32 «instance.incomingPortMap.get(srcPort).maskName» = «instance.incomingPortMap.get(srcPort).localrName» & 511;
						«load.target.variable.name» = «instance.incomingPortMap.get(srcPort).ramName»[«instance.incomingPortMap.get(
					srcPort).maskName»];
						«instance.incomingPortMap.get(srcPort).localrName» = «instance.incomingPortMap.get(srcPort).localrName»+1;
						«instance.incomingPortMap.get(srcPort).rName»[0] = «instance.incomingPortMap.get(srcPort).localrName»;
					«ENDIF»
				«ELSE»
					«load.target.variable.name» = «load.source.variable.name»«load.indexes.printArrayIndexes»;
				«ENDIF»
			'''

		}
	}

	override caseInstStore(InstStore store) {
		val trgtPort = store.target.variable.port
		'''
			«IF (trgtPort != null)»
				«IF instance.outgoingPortMap.get(trgtPort).head.targetPort == null»
					«IF !instance.outgoingPortMap.get(trgtPort).head.fifoName.toString.empty»
						«instance.outgoingPortMap.get(trgtPort).head.fifoName».write_nb(«store.value.doSwitch»);
					«ENDIF»
				«ELSE»
					i32 «instance.outgoingPortMap.get(trgtPort).head.maskName» = «instance.outgoingPortMap.get(trgtPort).head.
				localwName» & 511;
					«instance.outgoingPortMap.get(trgtPort).head.ramName»[«instance.outgoingPortMap.get(trgtPort).head.maskName»]=«store.
				value.doSwitch»;
					«instance.outgoingPortMap.get(trgtPort).head.localwName» = «instance.outgoingPortMap.get(trgtPort).head.localwName» +1;
					«instance.outgoingPortMap.get(trgtPort).head.wName»[0] = «instance.outgoingPortMap.get(trgtPort).head.localwName»;
				«ENDIF»
			«ELSE»
				«store.target.variable.name»«store.indexes.printArrayIndexes» = «store.value.doSwitch»;
			«ENDIF»
		'''
	}

	override printActionSchedulingLoop(List<Action> actions) '''
		«actions.printActionsScheduling»
	'''

	def fifoName(Connection connection) '''«IF connection != null»myStream_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def ramName(Connection connection) '''«IF connection != null»tab_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def wName(Connection connection) '''«IF connection != null»writeIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def localwName(Connection connection) '''«IF connection != null»wIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def localrName(Connection connection) '''«IF connection != null»rIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def rName(Connection connection) '''«IF connection != null»readIdx_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def maskName(Connection connection) '''«IF connection != null»mask_«connection.getAttribute("id").objectValue»«ENDIF»'''

	def fifoTypeOut(Connection connection) {
		if (connection.sourcePort == null) {
			connection.targetPort.type
		} else {
			connection.sourcePort.type
		}
	}

	def fifoTypeIn(Connection connection) {
		if (connection.targetPort == null) {
			connection.sourcePort.type
		} else {
			connection.targetPort.type
		}
	}

	override initializeFunction() '''
		«IF ! instance.getActor.initializes.empty»
			«FOR init : instance.getActor.initializes»
				«init.print»
			«ENDFOR»
			
			static void initialize() {
				
				«instance.getActor.initializes.printActionsScheduling»
				
			finished:
				// no read_end/write_end here!
				return;
			}
			
		«ENDIF»
	'''

	override printActionsScheduling(Iterable<Action> actions) '''
		«FOR action : actions SEPARATOR " else "»
			if («action.inputPattern.checkInputPattern»isSchedulable_«action.name»()) {
				if(1
				«IF action.outputPattern != null»
					«action.outputPattern.printOutputPattern»
				«ENDIF»){
					«instance.name»_«action.body.name»();
				}
			}«ENDFOR» else {
			goto finished;
		}
	'''

	override printStateTransitions(State state) '''
		«FOR transitions : state.outgoing.map[it as Transition] SEPARATOR " else "»
			if («transitions.action.inputPattern.checkInputPattern»isSchedulable_«transitions.action.name»()) {
				«instance.name»_«transitions.action.body.name»();
				_FSM_state = my_state_«transitions.target.name»;
				goto finished;
			
			}
		«ENDFOR»
		else {
			_FSM_state = my_state_«state.name»;
			goto finished;
		}
	'''

	override caseTypeBool(TypeBool type) '''bool'''

	def script(String path) '''
		
		open_project -reset subProject_«instance.name»
		set_top «instance.name»_scheduler
		add_files «instance.name».cpp
		add_files -tb «instance.name»TestBench.cpp
		open_solution -reset "solution1"
		set_part  {xc7vx330tffg1157-2}
		create_clock -period 20
		
		
		csynth_design
		exit
	'''

	def directive(String path) '''
		set_directive_inline -region -recursive «instance.name»_scheduler
	'''
}
