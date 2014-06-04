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

import java.io.File
import java.util.List
import java.util.Map
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
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.backends.util.FPGA

/*
 * Compile Instance c source code
 *  
 * @author Antoine Lorence and Khaled Jerbi 
 * 
 */
class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {
	private FPGA fpga = FPGA.builder("Virtex7 (xc7v2000t)") ;

	new(Map<String, Object> options) {
		super(options)
	}

	override getFileContent() '''
		// Source file is "«actor.file»"
		
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
		
		
		«IF instance != null»
			// Parameter values of the instance
			«FOR arg : instance.arguments»
				«IF arg.value.exprList»
					static «IF (arg.value.type as TypeList).innermostType.uint»unsigned «ENDIF»int «arg.variable.name»«arg.value.type.
			dimensionsExpr.printArrayIndexes» = «arg.value.doSwitch»;
				«ELSE»
					#define «arg.variable.name» «arg.value.doSwitch»
				«ENDIF»
			«ENDFOR»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Input FIFOS
		«FOR port : actor.inputs»
			«val connection = incomingPortMap.get(port)»
			«IF connection != null»
				extern «connection.fifoTypeIn.doSwitch»	«connection.ramName»[«connection.size»];
				extern unsigned int	«connection.wName»[1];
				extern unsigned int	«connection.rName»[1];
				unsigned int «connection.localrName»=0;
				#define SIZE_«port.name» «connection.size - 1»
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Output FIFOs
		«FOR port : actor.outputs.filter[! native]»
			«FOR connection : outgoingPortMap.get(port)»					
				extern «connection.fifoTypeOut.doSwitch» «connection.ramName»[«connection.size»];
				extern unsigned int «connection.wName»[1];
				extern unsigned int «connection.rName»[1];
				unsigned int «connection.localwName»=0;
			«ENDFOR»
		«ENDFOR»
		
		«IF actor.outputs.empty»
			extern stream<int> outFIFO_«entityName»;
		«ENDIF»
		
		
		////////////////////////////////////////////////////////////////////////////////
		// State variables of the actor
		«FOR variable : actor.stateVars»
			«variable.declare»
		«ENDFOR»
		«IF actor.hasFsm»
			////////////////////////////////////////////////////////////////////////////////
			// Initial FSM state of the actor
			enum states {
				«FOR state : actor.fsm.states SEPARATOR ","»
					my_state_«state.name»
				«ENDFOR»
			};
			
			static enum states _FSM_state = my_state_«actor.fsm.initialState.name»;
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Functions/procedures
		«FOR proc : actor.procs»
			«IF proc.native»extern«ELSE»static«ENDIF» «proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ",
			[variable.declare])»);
		«ENDFOR»
		
		«FOR proc : actor.procs.filter[!native]»
			«proc.print»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Actions
		«FOR action : actor.actions»
			«action.print»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«initializeFunction»
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«IF actor.hasFsm»
			«printFsm»
		«ELSE»
			void «entityName»_scheduler() {		
			«IF actor.outputs.empty»
				if (! outFIFO_«entityName».full()){
					outFIFO_«entityName».write(0);
				}
			«ENDIF»
			«actor.actionsOutsideFsm.printActionSchedulingLoop»
			
			finished:
				return;
			}
		«ENDIF»
	'''

	override printFsm() '''
		«IF ! actor.actionsOutsideFsm.empty»
			void «entityName»_outside_FSM_scheduler() {
				«actor.actionsOutsideFsm.printActionSchedulingLoop»
			finished:
				return;
			}
		«ENDIF»
		
		void «entityName»_scheduler() {
			// jump to FSM state 
			«IF actor.outputs.empty»
				if (! outFIFO_«entityName».full()){
					outFIFO_«entityName».write(0);
				}
			«ENDIF»
			switch (_FSM_state) {
				«FOR state : actor.fsm.states»
					case my_state_«state.name»:
						goto l_«state.name»;
				«ENDFOR»
			default:
				goto finished;
			}
			// FSM transitions
		«FOR state : actor.fsm.states»
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
			«IF ! actor.actionsOutsideFsm.empty»
				«entityName»_outside_FSM_scheduler();
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

	def printOutputPatternsPort(Pattern pattern, Port port) {
		var i = -1
		'''
			«FOR connection : outgoingPortMap.get(port)»
				«printOutputPatternPort(pattern, port, connection, i = i + 1)»
			«ENDFOR»
		'''
	}

	//&& (512 - «outgoingPortMap.get(port).head.localwName» + «outgoingPortMap.get(port).head.rName»[0] >= «pattern.getNumTokens(port)»)			
	def printOutputPatternPort(Pattern pattern, Port port, Connection successor, int id) '''		
		
			&& («successor.size» - «outgoingPortMap.get(port).head.localwName» + «outgoingPortMap.get(port).head.rName»[0] >= «pattern.
			getNumTokens(port)»)
		
	'''

	//«incomingPortMap.get(port).wName»[0] - «incomingPortMap.get(port).localrName» >= «pattern.getNumTokens(port)»  &&
	override checkInputPattern(Pattern pattern) '''
		«FOR port : pattern.ports»
			«val connection = incomingPortMap.get(port)»
			«IF connection != null»
				«connection.wName»[0] - «connection.localrName» >= «pattern.getNumTokens(port)»  &&
			«ENDIF»
		«ENDFOR»
	'''

	override print(String targetFolder) {
		val content = getFileContent
		val scriptContent = script(targetFolder);
		val directiveContent = directive(targetFolder);
		val file = new File(targetFolder + File::separator + entityName + ".cpp")
		val scriptFile = new File(
			targetFolder + File::separator + "script_" + entityName + ".tcl"
		)
		val directiveFile = new File(targetFolder + File::separator + "directive_" + entityName + ".tcl")

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
			static void «entityName»_«action.body.name»() {
			
				«FOR variable : action.body.locals»
					«variable.declare»;
				«ENDFOR»
			
				«FOR block : action.body.blocks»
					«block.doSwitch»
				«ENDFOR»
			
				«FOR port : action.inputPattern.ports»
					«val connection = incomingPortMap.get(port)»
					«IF connection != null»
						«connection.localrName» = «connection.localrName»+«action.inputPattern.getNumTokens(port)»;
						«connection.rName»[0] = «connection.localrName»;
					«ENDIF»
				«ENDFOR»
				«FOR port : action.outputPattern.ports»	
					«FOR connection : outgoingPortMap.get(port)»
						«connection.localwName» = «connection.localwName» + «action.outputPattern.getNumTokens(port)»;
						«connection.wName»[0] = «connection.localwName»;
					«ENDFOR»
				«ENDFOR»
				
			}
			
			«action.scheduler.print»
			
		'''

		currentAction = null
		return output
	}

	//i32 «incomingPortMap.get(srcPort).maskName» = «incomingPortMap.get(srcPort).localrName» & 511;
	//«incomingPortMap.get(srcPort).localrName» = «incomingPortMap.get(srcPort).localrName»+1;
	//«incomingPortMap.get(srcPort).rName»[0] = «incomingPortMap.get(srcPort).localrName»;
	//«load.target.variable.name» = «incomingPortMap.get(srcPort).ramName»[(«incomingPortMap.get(srcPort).maskName»  + («load.indexes.head.doSwitch»))];
	override caseInstLoad(InstLoad load) {
		if (load.eContainer != null) {
			val srcPort = load.source.variable.getPort
			'''
				
					
					
						«IF (srcPort != null)»
							«load.target.variable.name» = «incomingPortMap.get(srcPort).ramName»[((«incomingPortMap.get(srcPort).localrName» & SIZE_«srcPort.
					name»)  + («load.indexes.head.doSwitch»))];
						«ELSE»
							«load.target.variable.name» = «load.source.variable.name»«load.indexes.printArrayIndexes»;
						«ENDIF»
				'''

		}
	}

	//i32 «outgoingPortMap.get(trgtPort).head.maskName» = «outgoingPortMap.get(trgtPort).head.localwName» & 511;
	//«outgoingPortMap.get(trgtPort).head.localwName» = «outgoingPortMap.get(trgtPort).head.localwName» +1;
	//«outgoingPortMap.get(trgtPort).head.wName»[0] = «outgoingPortMap.get(trgtPort).head.localwName»;
	//«outgoingPortMap.get(trgtPort).head.ramName»[(«outgoingPortMap.get(trgtPort).head.maskName» + («store.indexes.head.doSwitch»))]=«store.value.doSwitch»;
	override caseInstStore(InstStore store) {
		val trgtPort = store.target.variable.port
		'''
			«IF (trgtPort != null)»
				«outgoingPortMap.get(trgtPort).head.ramName»[((«outgoingPortMap.get(trgtPort).head.localwName» & («outgoingPortMap.
				get(trgtPort).head.size - 1» )) + («store.indexes.head.doSwitch»))]=«store.value.doSwitch»;
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

	def initializeFunction() '''
		«IF ! actor.initializes.empty»
			«FOR init : actor.initializes»
				«init.print»
			«ENDFOR»
			
			static void initialize() {
				
				«actor.initializes.printActionsScheduling»
				
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
					«entityName»_«action.body.name»();
				}
			}«ENDFOR» else {
			goto finished;
		}
	'''

	override printStateTransitions(State state) '''
		«FOR transitions : state.outgoing.map[it as Transition] SEPARATOR " else "»
			if («transitions.action.inputPattern.checkInputPattern» isSchedulable_«transitions.action.name»() «transitions.
			action.outputPattern.printOutputPattern») {
				«entityName»_«transitions.action.body.name»();
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
		
		open_project -reset subProject_«entityName»
		set_top «entityName»_scheduler
		add_files «entityName».cpp
		add_files -tb «entityName»TestBench.cpp
		open_solution -reset "solution1"
		set_part  {«fpga.device»«fpga.package»«fpga.version»}
		create_clock -period 20
		
		
		csynth_design
		exit
	'''

	def directive(String path) '''
		
		«FOR port : actor.inputs»
			«FOR action : actor.actions»
				«val connection = incomingPortMap.get(port)»
				«IF action.inputPattern.contains(port)»
					set_directive_resource -core RAM_1P "«entityName»_«action.body.name»" «connection.ramName»
				«ENDIF»
			«ENDFOR»
		«ENDFOR»
		
	'''
}
