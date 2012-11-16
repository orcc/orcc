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
 package net.sf.orcc.backends.llvm.aot

import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import net.sf.orcc.backends.ir.InstCast
import net.sf.orcc.df.Action
import net.sf.orcc.df.Connection
import net.sf.orcc.df.FSM
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.Arg
import net.sf.orcc.ir.ArgByVal
import net.sf.orcc.ir.Block
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile
import net.sf.orcc.ir.CfgNode
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstPhi
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Instruction
import net.sf.orcc.ir.Param
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.util.EcoreHelper
import org.eclipse.emf.common.util.EList
import java.util.List
import java.io.File

import static net.sf.orcc.backends.OrccBackendsConstants.*
import static net.sf.orcc.OrccLaunchConstants.*

/*
 * Compile Instance llvm source code
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends LLVMTemplate {
	
	protected val Instance instance
	
	protected val List<Var> castedList = new ArrayList<Var>
	val List<Expression> castedIndexes = new ArrayList<Expression>
	val Map<State, Integer> stateToLabel = new HashMap<State, Integer>
	val Map<Pattern, Map<Port, Integer>> portToIndexByPatternMap = new HashMap<Pattern, Map<Port, Integer>>
	
	var optionProfile = false
	
	/**
	 * Default constructor, do not activate profile option and do not set instance (Jade requirement)
	 */
	new() {
		instance = null
	}
	
	/**
	 * Default constructor, do not activate profile option
	 */
	new(Instance instance, Map<String, Object> options) {
		if ( ! instance.isActor) {
			OrccLogger::severeln("Instance " + instance.name + " is not an Actor's instance")
		}
		
		this.instance = instance
		
		if(options.containsKey("net.sf.orcc.backends.profile")){
			optionProfile = options.get("net.sf.orcc.backends.profile") as Boolean
		}
		
		overwriteAllFiles = options.get(DEBUG_MODE) as Boolean
		
		computeCastedIndex
		computeCastedList
		computeStateToLabel
		computePortToIndexByPatternMap
	}
		
	def print(String targetFolder) {
		
		val content = instanceFileContent
		val file = new File(targetFolder + File::separator + instance.name + ".ll")
		
		if(needToWriteFile(content, file)) {
			printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	def getInstanceFileContent() '''
		«val inputs = instance.actor.inputs.filter[ ! native]»
		«val outputs = instance.actor.outputs.filter[ ! native]»
		«printArchitecture»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Generated from "«instance.actor.name»"
		declare i32 @printf(i8* noalias , ...) nounwind 
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Connected FIFOs
		«FOR port : inputs»
			«printExternalFifo(instance.incomingPortMap.get(port), port)»
			
		«ENDFOR»
		«FOR port : outputs»
			«printExternalFifo(instance.outgoingPortMap.get(port).head, port)»
			
		«ENDFOR»
		«IF ! instance.actor.inputs.empty »
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Input ports
			«FOR port : inputs»
				«port.printInput»
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.outputs.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Output ports
			«FOR port : outputs»
				«port.printOutput»
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.parameters.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Parameter values of the instance
			«FOR arg : instance.arguments»
				@«arg.variable.name» = internal global «arg.variable.type.doSwitch» «arg.value.doSwitch»
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.stateVars.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; State variables of the actor
			«FOR variable : instance.actor.stateVars»
				«variable.stateVar»
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.procs.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Functions/procedures
			«FOR proc : instance.actor.procs»
				«proc.print»
				
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.initializes.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Initializes
			«FOR init : instance.actor.initializes»
				«init.print»
				
			«ENDFOR»
			
		«ENDIF»
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Actions
		«FOR action : instance.actor.actions»
			«action.print»
			
		«ENDFOR»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Action-scheduler
		«IF ! instance.actor.initializes.empty»
			define void @«instance.name»_initialize() nounwind {
			entry:
				«printCallStartTokenFunctions»
				«FOR init : instance.actor.initializes»
					call «init.body.returnType.doSwitch» @«init.body.name»()
				«ENDFOR»
				«printCallEndTokenFunctions»
				ret void
			}
			
		«ENDIF»
		
		«IF optionProfile»
			@_waiting_count = global i32 zeroinitializer, align 32
			
			define void @«instance.name»_wait() nounwind noinline {
			entry:
				%tmp = load volatile i32* @_waiting_count
				%tmp_2 = add i32 %tmp, 1
				store volatile i32 %tmp_2, i32* @_waiting_count
				ret void
			}
		«ENDIF»
		
		«IF instance.actor.hasFsm»
			«schedulerWithFSM»
		«ELSE»
			«schedulerWithoutFSM»
		«ENDIF»
	'''
	
	def printArchitecture() '''target triple = "x86_64"'''
	
	def stateVar(Var variable)
		'''@«variable.name» = internal «variable.stateVarNature» «variable.type.doSwitch» «variable.initialize»'''

	
	def schedulerWithFSM() '''
		@_FSM_state = internal global i32 «stateToLabel.get(instance.actor.fsm.initialState)»
		
		«IF ! instance.actor.actionsOutsideFsm.empty»
			define void @«instance.name»_outside_FSM_scheduler() nounwind {
			entry:
				br label %bb_outside_scheduler_start
				
			bb_outside_scheduler_start:
				;; no read/write here!
			«printActionLoop(instance.actor.actionsOutsideFsm, true)»
			
			bb_outside_finished:
				;; no read_end/write_end here!
				ret void
			}
		«ENDIF»
		
		define void @«instance.name»_scheduler() «IF optionProfile»noinline «ENDIF»nounwind {
		entry:
			br label %bb_scheduler_start
		
		bb_scheduler_start:
			«printCallStartTokenFunctions»
			«instance.actor.fsm.printFsmSwitch»
			br label %bb_scheduler_start
		
		default:
			; TODO: print error
			br label %bb_scheduler_start
		
		«FOR state : instance.actor.fsm.states»
			«state.printTransition»
		«ENDFOR»
		
		bb_waiting:
			«IF optionProfile»call void @«instance.name»_wait()«ENDIF»
			br label %bb_finished
		
		bb_finished:
			«printCallEndTokenFunctions»
			ret void
		}
	'''
	
	def printFsmSwitch(FSM fsm) '''
		%local_FSM_state = load i32* @_FSM_state
		switch i32 %local_FSM_state, label %default [
			«fsm.states.map[printFsmState].join»
		]
	'''
	
	def printFsmState(State state) '''
		i32 «stateToLabel.get(state)», label %bb_s_«state.name»
	'''

	def printTransition(State state) '''
		; STATE «state.name»
		bb_s_«state.name»:
			«IF ! instance.actor.actionsOutsideFsm.empty»
				call void @«instance.name»_outside_FSM_scheduler()
			«ENDIF»
		«FOR transition : state.outgoing.filter(typeof(Transition))»
				«val action = transition.action»
				«val actionName = action.name»
				«val extName = state.name + "_" + actionName»
				; ACTION «actionName»
				«IF ! action.inputPattern.ports.filter[!native].empty»
					;; Input pattern
					«checkInputPattern(action, action.inputPattern, state)»
					%is_schedulable_«extName» = call i1 @«action.scheduler.name» ()
					%is_fireable_«extName» = and i1 %is_schedulable_«extName», %has_valid_inputs_«extName»_«action.inputPattern.ports.size»
					
					br i1 %is_fireable_«extName», label %bb_«extName»_check_outputs, label %bb_«extName»_unschedulable
				«ELSE»
					;; Empty input pattern
					%is_fireable_«extName» = call i1 @«action.scheduler.name» ()
					
					br i1 %is_fireable_«extName», label %bb_«extName»_check_outputs, label %bb_«extName»_unschedulable
				«ENDIF»
			
			
			bb_«extName»_check_outputs:
				«IF ! action.outputPattern.ports.filter[!native].empty»
					;; Output pattern
					«checkOutputPattern(action, action.outputPattern, state)»
					
					br i1 %has_valid_outputs_«action.outputPattern.ports.last.name»_«extName», label %bb_«extName»_fire, label %bb_«state.name»_finished
				«ELSE»
					;; Empty output pattern
					
					br label %bb_«extName»_fire
				«ENDIF»
			
			bb_«extName»_fire:
				call void @«action.body.name» ()
				
				br label %bb_s_«transition.target.name»
			bb_«extName»_unschedulable:
			
		«ENDFOR»
			br label %bb_«state.name»_finished
		
		bb_«state.name»_finished:
			store i32 «stateToLabel.get(state)», i32* @_FSM_state
			br label %bb_waiting
		
		'''

	def schedulerWithoutFSM() '''
		define void @«instance.name»_scheduler() « IF optionProfile»noinline «ENDIF»nounwind {
		entry:
			«printCallStartTokenFunctions»
			br label %bb_scheduler_start
			
		bb_scheduler_start:
		«printActionLoop(instance.actor.actionsOutsideFsm, false)»
		
		bb_waiting:
			«IF optionProfile»call void @«instance.name»_wait()«ENDIF»
			br label %bb_finished
		
		bb_finished:
			«printCallEndTokenFunctions»
			ret void
		}
	'''
	
	def printActionLoop(EList<Action> actions, boolean outsideFSM) '''
		«FOR action : actions»
			«val name = action.name»
				; ACTION «name»				
				«IF ! action.inputPattern.ports.filter[!native].empty»
					;; Input pattern
					«checkInputPattern(action, action.inputPattern)»
					%is_schedulable_«name» = call i1 @«action.scheduler.name» ()
					%is_fireable_«name» = and i1 %is_schedulable_«name», %has_valid_inputs_«name»_«action.inputPattern.ports.size»
					
					br i1 %is_fireable_«name», label %bb_«name»_check_outputs, label %bb_«name»_unschedulable
				«ELSE»
					;; Empty input pattern
					%is_fireable_«name» = call i1 @«action.scheduler.name» ()
					
					br i1 %is_fireable_«name», label %bb_«name»_check_outputs, label %bb_«name»_unschedulable
				«ENDIF»
			
			bb_«name»_check_outputs:
				«IF ! action.outputPattern.ports.filter[!native].empty»
					;; Output pattern
					«checkOutputPattern(action, action.outputPattern)»
					
					br i1 %has_valid_outputs_«action.outputPattern.ports.last.name»_«name», label %bb_«name»_fire, label %bb_finished
				«ELSE»
					;; Empty output pattern
					
					br label %bb_«name»_fire
				«ENDIF»
			
			bb_«name»_fire:
				call void @«action.body.name» ()
				
				«IF outsideFSM»
					br label %bb_outside_scheduler_start
				«ELSE»
					br label %bb_scheduler_start
				«ENDIF»
			
			bb_«name»_unschedulable:
		«ENDFOR»
			«IF outsideFSM»
				br label %bb_outside_finished
			«ELSE»
				br label %bb_waiting
			«ENDIF»
	'''
	
	def checkInputPattern(Action action, Pattern pattern) {
		checkInputPattern(action, pattern, null)
	}
	
	def checkInputPattern(Action action, Pattern pattern, State state) {
		val stateName = if( state != null) '''«state.name»_''' else ""
		val portToIndexMap = portToIndexByPatternMap.get(pattern)
		val firstPort = pattern.ports.filter[!native].head		
		'''
			%numTokens_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @numTokens_«firstPort.name»
			%index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @index_«firstPort.name»
			%status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = sub i32 %numTokens_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», %index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)»
			%has_valid_inputs_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = icmp sge i32 %status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», «pattern.numTokensMap.get(firstPort)»
			
			«FOR port : pattern.ports.filter[!native].tail»
				%numTokens_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = load i32* @numTokens_«port.name»
				%index_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = load i32* @index_«port.name»
				%status_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = sub i32 %numTokens_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)», %index_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)»
				%available_input_«stateName»«action.name»_«port.name» = icmp uge i32 %status_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)», «pattern.numTokensMap.get(port)»
				%has_valid_inputs_«stateName»«action.name»_«portToIndexMap.get(port)» = and i1 %has_valid_inputs_«stateName»«action.name»_«portToIndexMap.get(pattern.ports.get(pattern.ports.indexOf(port) - 1))», %available_input_«stateName»«action.name»_«port.name»
				
			«ENDFOR»
		'''
	}

	def checkOutputPattern(Action action, Pattern pattern) {
		checkOutputPattern(action, pattern, null)
	}
	
	def checkOutputPattern(Action action, Pattern pattern, State state) {
		val stateName = if( state != null) '''«state.name»_''' else ""
		val ports = pattern.ports.filter[!native].toList
		'''
			«FOR port : ports»
				«val extName = port.name + "_" + stateName + action.name»
				«val numTokens = pattern.numTokensMap.get(port)»
				%size_«extName» = load i32* @SIZE_«port.name»
				%index_«extName» = load i32* @index_«port.name»
				%rdIndex_«extName» = load i32* @rdIndex_«port.name»
				%tmp_«extName» = sub i32 %size_«extName», %index_«extName»
				%status_«extName» = add i32 %tmp_«extName», %rdIndex_«extName»
				«IF !port.equals(ports.head)»
					%available_output_«extName» = icmp sge i32 %status_«extName», «numTokens»
					%has_valid_outputs_«extName» = and i1 %has_valid_outputs_«ports.get(ports.indexOf(port) - 1).name»_«stateName»«action.name», %available_output_«extName»
				«ELSE»
					%has_valid_outputs_«extName» = icmp uge i32 %status_«extName», «numTokens»
				«ENDIF»
				
			«ENDFOR»
		'''
	}

	def printCallStartTokenFunctions() '''
		«FOR port : instance.actor.inputs»
			call void @read_«port.name»()
		«ENDFOR»
		«FOR port : instance.actor.outputs.filter[ ! native]»
			call void @write_«port.name»()
		«ENDFOR»
	'''

	def printCallEndTokenFunctions() '''
		«FOR port : instance.actor.inputs»
			call void @read_end_«port.name»()
		«ENDFOR»
		«FOR port : instance.actor.outputs.filter[ ! native]»
			call void @write_end_«port.name»()
		«ENDFOR»
	'''
	
	def print(Action action) '''
		define internal i1 @«action.scheduler.name»() nounwind {
		entry:
			«FOR local : action.scheduler.locals»
				«local.variableDeclaration»
			«ENDFOR»
			«FOR port : action.peekPattern.ports.filter[ ! native]»
				«port.fifoVar»
			«ENDFOR»
			br label %b«action.scheduler.blocks.head.label»
		
		«FOR block : action.scheduler.blocks»
			«block.doSwitch»
		«ENDFOR»
		}
		
		define internal void @«action.body.name»() nounwind {
		entry:
			«FOR local : action.body.locals»
				«local.variableDeclaration»
			«ENDFOR»
			«FOR port : action.inputPattern.ports.filter[ ! native] + action.outputPattern.ports.filter[ ! native]»
				«port.fifoVar»
			«ENDFOR»
			br label %b«action.body.blocks.head.label»
		
		«FOR block : action.body.blocks»
			«block.doSwitch»
		«ENDFOR»
			«FOR port : action.inputPattern.ports.filter[ ! native]»
				«printFifoEnd(port, action.inputPattern.numTokensMap.get(port))»
			«ENDFOR»
			«FOR port : action.outputPattern.ports.filter[ ! native]»
				«printFifoEnd(port, action.outputPattern.numTokensMap.get(port))»
			«ENDFOR»
			ret void
		}
	'''
	
	def printFifoEnd(Port port, Integer numTokens) '''
		%new_index_«port.name» = add i32 %local_index_«port.name», «numTokens»
		store i32 %new_index_«port.name», i32* @index_«port.name»
	'''

	def fifoVar(Port port) '''
		%local_index_«port.name» = load i32* @index_«port.name»
		%local_size_«port.name» = load i32* @SIZE_«port.name»
	'''
	
	def print(Procedure procedure) '''
		«val parameters = procedure.parameters.join(", ", [argumentDeclaration] )»
		«IF procedure.native»
			declare «procedure.returnType.doSwitch» @«procedure.name»(«parameters») nounwind
		«ELSE»
			define internal «procedure.returnType.doSwitch» @«procedure.name»(«parameters») nounwind {
			entry:
			«FOR local : procedure.locals»
			«local.variableDeclaration»
			«ENDFOR»
				br label %b«procedure.blocks.head.label»
			
			«FOR block : procedure.blocks»
				«block.doSwitch»
			«ENDFOR»
			}
		«ENDIF»
	'''
	
	def label(Block block) '''b«block.cfgNode.number»'''
	
	def variableDeclaration(Var variable) {
		if(variable.type.list && ! castedList.contains(variable))
			'''%«variable.indexedName» = alloca «variable.type.doSwitch»'''
	}
	
	def argumentDeclaration(Param param) {
		val variable = param.variable
		if (variable.type.string) '''i8* %«variable.name»'''
		else if (variable.type.list) '''«variable.type.doSwitch»* %«variable.name»'''
		else '''«variable.type.doSwitch» %«variable.name»'''
	}
	
	def initialize(Var variable) {
		if(variable.initialValue != null) variable.initialValue.doSwitch
		else "zeroinitializer, align 32"
	}

	def getStateVarNature(Var variable) {
		if(variable.assignable) "global"
		else "constant"
	}
	
	def printInput(Port port) '''
		«val connection = instance.incomingPortMap.get(port)»
		@SIZE_«port.name» = internal constant i32 «connection.fifoSize»
		@index_«port.name» = internal global i32 0
		@numTokens_«port.name» = internal global i32 0
		
		define internal void @read_«port.name»() {
		entry:
			br label %read
		
		read:
			%rdIndex = load«port.properties» i32«port.addrSpace»* @fifo_«getId(connection, port)»_rdIndex
			store i32 %rdIndex, i32* @index_«port.name»
			%wrIndex = load«port.properties» i32«port.addrSpace»* @fifo_«getId(connection, port)»_wrIndex
			%getNumTokens = sub i32 %wrIndex, %rdIndex
			%numTokens = add i32 %rdIndex, %getNumTokens
			store i32 %numTokens, i32* @numTokens_«port.name»
			ret void
		}

		define internal void @read_end_«port.name»() {
		entry:
			br label %read_end
		
		read_end:
			%rdIndex = load i32* @index_«port.name»
			store«port.properties» i32 %rdIndex, i32«port.addrSpace»* @fifo_«getId(connection, port)»_rdIndex
			ret void
		}
	'''
		
	def printOutput(Port port) '''
		«val connection = instance.outgoingPortMap.get(port).head»
		@SIZE_«port.name» = internal constant i32 «connection.fifoSize»
		@index_«port.name» = internal global i32 0
		@rdIndex_«port.name» = internal global i32 0
		@numFree_«port.name» = internal global i32 0
		
		define internal void @write_«port.name»() {
		entry:
			br label %write
		
		write:
			%wrIndex = load«port.properties» i32«port.addrSpace»* @fifo_«getId(connection, port)»_wrIndex
			store i32 %wrIndex, i32* @index_«port.name»
			%rdIndex = load«port.properties» i32«port.addrSpace»* @fifo_«getId(connection, port)»_rdIndex
			store i32 %rdIndex, i32* @rdIndex_«port.name»
			%size = load i32* @SIZE_«port.name»
			%numTokens = sub i32 %wrIndex, %rdIndex
			%getNumFree = sub i32 %size, %numTokens
			%numFree = add i32 %wrIndex, %getNumFree
			store i32 %numFree, i32* @numFree_«port.name»
			ret void
		}
		
		define internal void @write_end_«port.name»() {
		entry:
			br label %write_end
		
		write_end:
			%wrIndex = load i32* @index_«port.name»
			store«port.properties» i32 %wrIndex, i32«port.addrSpace»* @fifo_«getId(connection, port)»_wrIndex
			ret void
		}
	'''
	
	/**
	 * Returns an annotation describing the address space. 
	 * This annotation is required by the TTA backend.
	 */
	def getAddrSpace(Port port) ''''''
	
	/**
	 * Returns an annotation describing the properties of the access. 
	 * This annotation is required by the TTA backend.
	 */
	def getProperties(Port port) ''''''
	
	def printExternalFifo(Connection conn, Port port) {
		val isConnected = conn != null
		'''
		@fifo_«getId(conn, port)»_content = «IF isConnected»external«port.addrSpace»«ELSE»internal«ENDIF» global [«conn.fifoSize» x «port.type.doSwitch»]«IF !isConnected» zeroinitializer, align 32«ENDIF»
		@fifo_«getId(conn, port)»_rdIndex = «IF isConnected»external«port.addrSpace»«ELSE»internal«ENDIF» global i32«IF !isConnected» zeroinitializer, align 32«ENDIF»
		@fifo_«getId(conn, port)»_wrIndex = «IF isConnected»external«port.addrSpace»«ELSE»internal«ENDIF» global i32«IF !isConnected» zeroinitializer, align 32«ENDIF»
		'''
	}
	
	def getNextLabel(Block block) {
		if (block.blockWhile) (block as BlockWhile).joinBlock.label
		else block.label
	}
	
	override caseBlockBasic(BlockBasic blockBasic) '''
		b«blockBasic.label»:
			«FOR instr : blockBasic.instructions»
				«instr.doSwitch»
			«ENDFOR»
			«IF ! blockBasic.cfgNode.successors.empty»
				br label %b«(blockBasic.cfgNode.successors.head as CfgNode).node.nextLabel»
			«ENDIF»
	'''
	
	override caseBlockIf(BlockIf blockIf) '''
		b«blockIf.label»:
			br i1 «blockIf.condition.doSwitch», label %b«blockIf.thenBlocks.head.nextLabel», label %b«blockIf.elseBlocks.head.nextLabel»
		
		«FOR block : blockIf.thenBlocks»
			«block.doSwitch»
		«ENDFOR»
		
		«FOR block : blockIf.elseBlocks»
			«block.doSwitch»
		«ENDFOR»
		
		«blockIf.joinBlock.doSwitch»
	'''

	override caseBlockWhile(BlockWhile blockwhile) '''
		b«blockwhile.joinBlock.label»:
			«FOR instr : blockwhile.joinBlock.instructions»
				«instr.doSwitch»
			«ENDFOR»
			br i1 «blockwhile.condition.doSwitch», label %b«blockwhile.blocks.head.label», label %b«blockwhile.label»
		
		«FOR block : blockwhile.blocks»
			«block.doSwitch»
		«ENDFOR»
		
		b«blockwhile.label»:
			br label %b«(blockwhile.cfgNode.successors.head as CfgNode).node.nextLabel»
	'''
	
	override caseInstruction(Instruction instr) {
		if(instr instanceof InstCast)
			return caseInstCast(instr as InstCast)
		else
			super.caseInstruction(instr)
	}
	
	def caseInstCast(InstCast cast) '''
		%«cast.target.variable.indexedName» = «cast.castOp» «cast.source.variable.castType» «cast.source.variable.print» to «cast.target.variable.castType»
	'''

	def getCastOp(InstCast cast)
		'''«IF cast.source.variable.type.list»bitcast«ELSEIF ! cast.extended»trunc«ELSEIF cast.signed»sext«ELSE»zext«ENDIF»'''

	def getCastType(Var variable)
		'''«variable.type.doSwitch»«IF variable.type.list»*«ENDIF»'''
	
	override caseInstAssign(InstAssign assign) 
		'''%«assign.target.variable.indexedName» = «assign.value.doSwitch»'''
	
	override caseInstPhi(InstPhi phi)
		'''«phi.target.variable.print» = phi «phi.target.variable.type.doSwitch» «phi.phiPairs»'''
		
	def getPhiPairs(InstPhi phi) 
		'''«printPhiExpr(phi.values.head, (phi.block.cfgNode.predecessors.head as CfgNode).node)», «printPhiExpr(phi.values.tail.head, (phi.block.cfgNode.predecessors.tail.head as CfgNode).node)»'''
	
	def printPhiExpr(Expression expr, Block block)
		'''[«expr.doSwitch», %b«block.label»]'''
		
	override caseInstReturn(InstReturn retInst) {
		val action = EcoreHelper::getContainerOfType(retInst, typeof(Action))
		if ( action == null || EcoreHelper::getContainerOfType(retInst, typeof(Procedure)) == action.scheduler) {
			if(retInst.value == null)
				'''ret void'''
			else
				'''ret «retInst.value.type.doSwitch» «retInst.value.doSwitch»'''
		}	
	}
	
	override caseInstStore(InstStore store) {
		val action = EcoreHelper::getContainerOfType(store, typeof(Action))
		val variable = store.target.variable
		'''
			«IF variable.type.list»
				«val innerType = (variable.type as TypeList).innermostType»
				«IF action != null && action.outputPattern.contains(variable) && ! action.outputPattern.varToPortMap.get(variable).native»
					«val port = action.outputPattern.varToPortMap.get(variable)»
					«printPortAccess(instance.outgoingPortMap.get(port).head, port, variable, store.indexes, store)»
					store«port.properties» «innerType.doSwitch» «store.value.doSwitch», «innerType.doSwitch»«port.addrSpace»* «varName(variable, store)»
				«ELSE»
					«varName(variable, store)» = getelementptr «variable.type.doSwitch»* «variable.print», i32 0«store.indexes.join(", ", ", ", "", [printIndex])»
					store «innerType.doSwitch» «store.value.doSwitch», «innerType.doSwitch»* «varName(variable, store)»
				«ENDIF»
			«ELSE»
				store «variable.type.doSwitch» «store.value.doSwitch», «variable.type.doSwitch»* «variable.print»
			«ENDIF»
		'''
	}

	override caseInstLoad(InstLoad load) {
		val action = EcoreHelper::getContainerOfType(load, typeof(Action))
		val variable = load.source.variable
		val target = load.target.variable.print
		'''
			«IF variable.type.list»
				«val innerType = (variable.type as TypeList).innermostType»
				«IF action != null && action.inputPattern.contains(variable) && ! action.inputPattern.varToPortMap.get(variable).native»
					«val port = action.inputPattern.varToPortMap.get(variable)»
					«printPortAccess(instance.incomingPortMap.get(port), port, variable, load.indexes, load)»
					«target» = load«port.properties» «innerType.doSwitch»«port.addrSpace»* «varName(variable, load)»
				«ELSEIF action != null && action.outputPattern.contains(variable) && ! action.outputPattern.varToPortMap.get(variable).native»
					«val port = action.outputPattern.varToPortMap.get(variable)»
					«printPortAccess(instance.outgoingPortMap.get(port).head, port, variable, load.indexes, load)»
					«target» = load«port.properties» «innerType.doSwitch»«port.addrSpace»* «varName(variable, load)»
				«ELSEIF action != null && action.peekPattern.contains(variable)»
					«val port = action.peekPattern.varToPortMap.get(variable)»
					«printPortAccess(instance.incomingPortMap.get(port), port, variable, load.indexes, load)»
					«target» = load«port.properties» «innerType.doSwitch»«port.addrSpace»* «varName(variable, load)»
				«ELSE»
					«varName(variable, load)» = getelementptr «variable.type.doSwitch»* «variable.print», i32 0«load.indexes.join(", ", ", ", "", [printIndex])»
					«target» = load «innerType.doSwitch»* «varName(variable, load)»
				«ENDIF»				
			«ELSE»
				«target» = load «variable.type.doSwitch»* «variable.print»
			«ENDIF»
		'''
	}
		
	def printIndex (Expression index) {
		var type = ''''''
		if (index.type != null)
			type = index.type.doSwitch
		else
			type = "i32"
		return '''«type» «index.doSwitch»'''
	}
	
	override caseInstCall(InstCall call) '''
		«IF call.print»
			call i32 (i8*, ...)* @printf(«call.parameters.join(", ", [printParameter])»)
		«ELSE»
			«IF call.target != null»%«call.target.variable.indexedName» = «ENDIF»call «call.procedure.returnType.doSwitch» @«call.procedure.name» («formatParameters(call.procedure.parameters, call.parameters).join(", ")»)
		«ENDIF»
	'''
	
	def formatParameters(EList<Param> params, EList<Arg> args) {
		val paramList = new ArrayList<CharSequence>
		if(params.size != 0) {
			for (i : 0..params.size-1) {
				paramList.add(printParameter(args.get(i), params.get(i).variable.type))
			}
		}
		return paramList
	}
	
	def printParameter(Arg arg) {
		printParameter(arg, (arg as ArgByVal).value.type)
	}

	def printParameter(Arg arg, Type type) {
		if (arg.byRef)
			'''TODO'''
		else if (type.string)
			'''i8* «IF ((arg as ArgByVal).value as ExprVar)?.use.variable.local» «(arg as ArgByVal).value.doSwitch» «ELSE» noalias getelementptr inbounds («(arg as ArgByVal).value.type.doSwitch»* «(arg as ArgByVal).value.doSwitch», i64 0, i64 0)«ENDIF»'''
		else
			'''«type.doSwitch»«IF type.list»*«ENDIF» «(arg as ArgByVal).value.doSwitch»'''
	}
	
	
	def varName(Var variable, Instruction instr) {
		val procedure = EcoreHelper::getContainerOfType(instr, typeof(Procedure))
		'''%«variable.name»_elt_«(procedure.getAttribute("accessMap").objectValue as Map<Instruction, Integer>).get(instr)»'''
	}

	def printPortAccess(Connection connection, Port port, Var variable, EList<Expression> indexes, Instruction instr) {
		val procedure = EcoreHelper::getContainerOfType(instr, typeof(Procedure))
		val accessMap = procedure.getAttribute("accessMap").objectValue as Map<Instruction, Integer>
		val accessId = accessMap.get(instr)
		val needCast = castedIndexes.contains(indexes.head)
		'''
			«IF needCast»
				%cast_index_«variable.name»_«accessId» = zext «indexes.head.type.doSwitch» «indexes.head.doSwitch» to i32
			«ENDIF»
			%tmp_index_«variable.name»_«accessId» = add i32 %local_index_«port.name», «IF needCast»%cast_index_«variable.name»_«accessId»«ELSE»«indexes.head.doSwitch»«ENDIF»
			%final_index_«variable.name»_«accessId» = urem i32 %tmp_index_«variable.name»_«accessId», %local_size_«port.name»
			«varName(variable, instr)» = getelementptr [«connection.fifoSize» x «port.type.doSwitch»]«port.addrSpace»* @fifo_«getId(connection, port)»_content, i32 0, i32 %final_index_«variable.name»_«accessId»
		'''
	}
	
	def protected computeCastedList() {
		for (variable : instance.actor.eAllContents.toIterable.filter(typeof(Var))) {
			if(variable.type.list && ! variable.defs.empty && variable.defs.head.eContainer instanceof InstCast) {
				castedList.add(variable)
			}
		}
	}

	def private computeStateToLabel() {
		if(instance.actor.hasFsm){
			var i = 0
			for ( state : instance.actor.fsm.states) {
				stateToLabel.put(state, i)
				i = i + 1
			}
		}
	}
	
	def private computeCastedIndex() {
		for (instr : instance.actor.eAllContents.toIterable.filter(typeof(Instruction))) {
			if(instr.instLoad) {
				val load = instr as InstLoad
				if( ! load.indexes.empty && load.indexes.head.type.sizeInBits != 32) {
					castedIndexes.add(load.indexes.head)
				}
			} else if (instr.instStore) {
				val store = instr as InstStore
				if( ! store.indexes.empty && store.indexes.head.type.sizeInBits != 32) {
					castedIndexes.add(store.indexes.head)
				}
			}
		}
	}
	
	def private computePortToIndexByPatternMap() {
		for(pattern : instance.actor.eAllContents.toIterable.filter(typeof(Pattern))) {
			val portToIndex = new HashMap<Port, Integer>
			var i = 1
			for(port : pattern.ports) {
				portToIndex.put(port, i)
				i = i + 1
			}
			portToIndexByPatternMap.put(pattern, portToIndex)
		}
	}
}