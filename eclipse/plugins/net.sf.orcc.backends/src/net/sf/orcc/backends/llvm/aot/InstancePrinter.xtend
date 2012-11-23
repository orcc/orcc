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
	
	protected var optionProfile = false
	
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
		this.instance = instance
		
		if (!instance.isActor) {
			OrccLogger::severeln("Instance " + instance.name + " is not an Actor's instance")
		}
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
	
	def private getInstanceFileContent() '''
		«val inputs = instance.actor.inputs.notNative»
		«val outputs = instance.actor.outputs.notNative»
		«printArchitecture»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Generated from "«instance.actor.name»"
		declare i32 @printf(i8* noalias , ...) nounwind 
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; FIFOs
		«FOR port : inputs»
			«val connection = instance.incomingPortMap.get(port)»
			«connection.printInput(port)»
		«ENDFOR»
		
		«FOR port : outputs»
			«FOR connection : instance.outgoingPortMap.get(port)»
				«connection.printOutput(port)»
			«ENDFOR»
		«ENDFOR»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Parameters
		«FOR arg : instance.arguments»
			@«arg.variable.name» = internal global «arg.variable.type.doSwitch» «arg.value.doSwitch»
		«ENDFOR»

		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; State variables
		«FOR variable : instance.actor.stateVars»
			«variable.declare»
		«ENDFOR»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Functions/procedures
		«FOR proc : instance.actor.procs»
			«proc.print»
			
		«ENDFOR»

		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Initializes
		«FOR init : instance.actor.initializes»
			«init.print»
			
		«ENDFOR»

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
		
		«IF instance.actor.hasFsm»
			«schedulerWithFSM»
		«ELSE»
			«schedulerWithoutFSM»
		«ENDIF»
	'''
	
	def protected printArchitecture() '''target triple = "x86_64"'''
	
	def private schedulerWithFSM() '''
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
		
		define void @«instance.name»_scheduler() nounwind {
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
			br label %bb_finished
		
		bb_finished:
			«printCallEndTokenFunctions»
			ret void
		}
	'''
	
	def private printFsmSwitch(FSM fsm) '''
		%local_FSM_state = load i32* @_FSM_state
		switch i32 %local_FSM_state, label %default [
			«fsm.states.map[printFsmState].join»
		]
	'''
	
	def private printFsmState(State state) '''
		i32 «stateToLabel.get(state)», label %bb_s_«state.name»
	'''

	def private printTransition(State state) '''
		; STATE «state.name»
		bb_s_«state.name»:
			«IF ! instance.actor.actionsOutsideFsm.empty»
				call void @«instance.name»_outside_FSM_scheduler()
			«ENDIF»
		«FOR transition : state.outgoing.filter(typeof(Transition))»
			«val action = transition.action»
			«val actionName = action.name»
			«val extName = state.name + "_" + actionName»
			«val inputPattern = action.inputPattern»
			«val outputPattern = action.outputPattern»
				; ACTION «actionName»
				«IF !inputPattern.ports.notNative.empty»
					;; Input pattern
					«checkInputPattern(action, inputPattern, state)»
					%is_schedulable_«extName» = call i1 @«action.scheduler.name» ()
					%is_fireable_«extName» = and i1 %is_schedulable_«extName», %has_valid_inputs_«extName»_«inputPattern.ports.size»

					br i1 %is_fireable_«extName», label %bb_«extName»_check_outputs, label %bb_«extName»_unschedulable
				«ELSE»
					;; Empty input pattern
					%is_fireable_«extName» = call i1 @«action.scheduler.name» ()

					br i1 %is_fireable_«extName», label %bb_«extName»_check_outputs, label %bb_«extName»_unschedulable
				«ENDIF»
			
			bb_«extName»_check_outputs:
				«IF !outputPattern.ports.notNative.empty»
					«val lastPort = outputPattern.ports.last»
					;; Output pattern
					«checkOutputPattern(action, outputPattern, state)»

					br i1 %has_valid_outputs_«lastPort.name»_«instance.outgoingPortMap.get(lastPort).last.getSafeId(lastPort)»_«extName», label %bb_«extName»_fire, label %bb_«state.name»_finished
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

	def private schedulerWithoutFSM() '''
		define void @«instance.name»_scheduler() nounwind {
		entry:
			«printCallStartTokenFunctions»
			br label %bb_scheduler_start

		bb_scheduler_start:
		«printActionLoop(instance.actor.actionsOutsideFsm, false)»
		
		bb_waiting:
			br label %bb_finished
		
		bb_finished:
			«printCallEndTokenFunctions»
			ret void
		}
	'''
	
	def private printActionLoop(EList<Action> actions, boolean outsideFSM) '''
		«FOR action : actions»
			«val name = action.name»
			«val inputPattern = action.inputPattern»
			«val outputPattern = action.outputPattern»
				; ACTION «name»
				«IF !inputPattern.ports.notNative.empty»
					;; Input pattern
					«checkInputPattern(action, inputPattern, null)»
					%is_schedulable_«name» = call i1 @«action.scheduler.name» ()
					%is_fireable_«name» = and i1 %is_schedulable_«name», %has_valid_inputs_«name»_«inputPattern.ports.size»

					br i1 %is_fireable_«name», label %bb_«name»_check_outputs, label %bb_«name»_unschedulable
				«ELSE»
					;; Empty input pattern
					%is_fireable_«name» = call i1 @«action.scheduler.name» ()

					br i1 %is_fireable_«name», label %bb_«name»_check_outputs, label %bb_«name»_unschedulable
				«ENDIF»
			
			bb_«name»_check_outputs:
				«IF !outputPattern.ports.notNative.empty»
					«val lastPort = outputPattern.ports.last»
					;; Output pattern
					«checkOutputPattern(action, outputPattern, null)»
		
					br i1 %has_valid_outputs_«lastPort.name»_«instance.outgoingPortMap.get(lastPort).last.getSafeId(lastPort)»_«name», label %bb_«name»_fire, label %bb_finished
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
	
	def private checkInputPattern(Action action, Pattern pattern, State state) {
		val stateName = if(state != null) '''«state.name»_''' else ""
		val portToIndexMap = portToIndexByPatternMap.get(pattern)
		val firstPort = pattern.ports.notNative.head
		val firstName = firstPort.name + "_" + instance.incomingPortMap.get(firstPort).getSafeId(firstPort)
		'''
			%numTokens_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @numTokens_«firstName»
			%index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @index_«firstName»
			%status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = sub i32 %numTokens_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», %index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)»
			%has_valid_inputs_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = icmp sge i32 %status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», «pattern.numTokensMap.get(firstPort)»
			
			«FOR port : pattern.ports.notNative.tail»
				«val name = port.name + "_" + instance.incomingPortMap.get(port).getSafeId(port)»
				%numTokens_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = load i32* @numTokens_«name»
				%index_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = load i32* @index_«name»
				%status_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = sub i32 %numTokens_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)», %index_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)»
				%available_input_«stateName»«action.name»_«port.name» = icmp uge i32 %status_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)», «pattern.numTokensMap.get(port)»
				%has_valid_inputs_«stateName»«action.name»_«portToIndexMap.get(port)» = and i1 %has_valid_inputs_«stateName»«action.name»_«portToIndexMap.get(pattern.ports.get(pattern.ports.indexOf(port) - 1))», %available_input_«stateName»«action.name»_«port.name»
				
			«ENDFOR»
		'''
	}
	
	def private checkOutputPattern(Action action, Pattern pattern, State state) {
		val stateName = if(state != null) '''«state.name»_''' else ""
		val connections = pattern.ports.notNative.map[instance.outgoingPortMap.get(it)].flatten.toList
		'''
			«FOR connection : connections»
				«val port = connection.sourcePort»
				«val name = port.name + "_" + connection.getSafeId(port)»
				«val extName = name + "_" + stateName + action.name»
				«val numTokens = pattern.numTokensMap.get(port)»
				%size_«extName» = load i32* @SIZE_«name»
				%index_«extName» = load i32* @index_«name»
				%rdIndex_«extName» = load i32* @rdIndex_«name»
				%tmp_«extName» = sub i32 %size_«extName», %index_«extName»
				%status_«extName» = add i32 %tmp_«extName», %rdIndex_«extName»
				«IF !connection.equals(connections.head)»
					«val lastConnection = connections.get(connections.indexOf(connection) - 1)»
					«val lastName = lastConnection.sourcePort.name + "_" + lastConnection.getSafeId(lastConnection.sourcePort)»
					%available_output_«extName» = icmp sge i32 %status_«extName», «numTokens»
					%has_valid_outputs_«extName» = and i1 %has_valid_outputs_«lastName»_«stateName»«action.name», %available_output_«extName»
				«ELSE»
					%has_valid_outputs_«extName» = icmp uge i32 %status_«extName», «numTokens»
				«ENDIF»
			«ENDFOR»
		'''
	}

	def private printCallStartTokenFunctions() '''
		«FOR port : instance.actor.inputs»
			«val connection = instance.incomingPortMap.get(port)»
			call void @read_«port.name»_«connection.getSafeId(port)»()
		«ENDFOR»
		«FOR port : instance.actor.outputs.notNative»
			«FOR connection : instance.outgoingPortMap.get(port)»
				call void @write_«port.name»_«connection.getSafeId(port)»()
			«ENDFOR»
		«ENDFOR»
	'''

	def private printCallEndTokenFunctions() '''
		«FOR port : instance.actor.inputs»
			«val connection = instance.incomingPortMap.get(port)»
			call void @read_end_«port.name»_«connection.getSafeId(port)»()
		«ENDFOR»
		«FOR port : instance.actor.outputs.notNative»
			«FOR connection : instance.outgoingPortMap.get(port)»
				call void @write_end_«port.name»_«connection.getSafeId(port)»()
			«ENDFOR»
		«ENDFOR»
	'''
	
	def protected print(Action action) '''
		«val inputPattern = action.inputPattern»
		«val outputPattern = action.outputPattern»
		«val peekPattern = action.peekPattern»
		define internal i1 @«action.scheduler.name»() nounwind {
		entry:
			«FOR local : action.scheduler.locals»
				«local.declare»
			«ENDFOR»
			«FOR port : peekPattern.ports.notNative»
				«port.loadVar(instance.incomingPortMap.get(port))»
			«ENDFOR»
			br label %b«action.scheduler.blocks.head.label»
		
		«FOR block : action.scheduler.blocks»
			«block.doSwitch»
		«ENDFOR»
		}
		
		define internal void @«action.body.name»() «IF optionProfile»noinline «ENDIF»nounwind {
		entry:
			«FOR local : action.body.locals»
				«local.declare»
			«ENDFOR»
			«FOR port : inputPattern.ports.notNative»
				«port.loadVar(instance.incomingPortMap.get(port))»
			«ENDFOR»
			«FOR port : outputPattern.ports.notNative»
				«FOR connection : instance.outgoingPortMap.get(port)»
					«port.loadVar(connection)»
				«ENDFOR»
			«ENDFOR»
			br label %b«action.body.blocks.head.label»
		
		«FOR block : action.body.blocks»
			«block.doSwitch»
		«ENDFOR»
			«FOR port : inputPattern.ports.notNative»
				«port.updateVar(instance.incomingPortMap.get(port), inputPattern.getNumTokens(port))»
			«ENDFOR»
			«FOR port : outputPattern.ports.notNative»
				«FOR connection : instance.outgoingPortMap.get(port)»
					«port.updateVar(connection, outputPattern.getNumTokens(port))»
				«ENDFOR»
			«ENDFOR»
			ret void
		}
	'''
	
	def protected loadVar(Port port, Connection connection) '''
		%local_index_«port.name»_«connection.getSafeId(port)» = load i32* @index_«port.name»_«connection.getSafeId(port)»
		%local_size_«port.name»_«connection.getSafeId(port)» = load i32* @SIZE_«port.name»_«connection.getSafeId(port)»
	'''
	
	def protected updateVar(Port port, Connection connection, Integer numTokens) '''
		%new_index_«port.name»_«connection.getSafeId(port)» = add i32 %local_index_«port.name»_«connection.getSafeId(port)», «numTokens»
		store i32 %new_index_«port.name»_«connection.getSafeId(port)», i32* @index_«port.name»_«connection.getSafeId(port)»
	'''	
	
	def protected print(Procedure procedure) '''
		«val parameters = procedure.parameters.join(", ", [argumentDeclaration] )»
		«IF procedure.native»
			declare «procedure.returnType.doSwitch» @«procedure.name»(«parameters») nounwind
		«ELSE»
			define internal «procedure.returnType.doSwitch» @«procedure.name»(«parameters») nounwind {
			entry:
			«FOR local : procedure.locals»
				«local.declare»
			«ENDFOR»
				br label %b«procedure.blocks.head.label»
			
			«FOR block : procedure.blocks»
				«block.doSwitch»
			«ENDFOR»
			}
		«ENDIF»
	'''
	
	def protected label(Block block) '''b«block.cfgNode.number»'''
	
	def protected declare(Var variable) {
		if(variable.global)
			'''@«variable.name» = internal «IF variable.assignable»global«ELSE»constant«ENDIF» «variable.type.doSwitch» «variable.initialize»'''
		else if(variable.type.list && ! castedList.contains(variable))
			'''%«variable.indexedName» = alloca «variable.type.doSwitch»'''
	}
	
	def protected initialize(Var variable) {
		if(variable.initialValue != null) variable.initialValue.doSwitch
		else "zeroinitializer, align 32"
	}
	
	def protected argumentDeclaration(Param param) {
		val variable = param.variable
		if (variable.type.string) '''i8* %«variable.name»'''
		else if (variable.type.list) '''«variable.type.doSwitch»* %«variable.name»'''
		else '''«variable.type.doSwitch» %«variable.name»'''
	}
	
	def private printInput(Connection connection, Port port) '''
		«val id = connection.getSafeId(port)»
		«val name = port.name + "_" + id»
		«val addrSpace = connection.addrSpace»
		«val prop = port.properties»
		«connection.printExternalFifo(port)»
	
		@SIZE_«name» = internal constant i32 «connection.safeSize»
		@index_«name» = internal global i32 0
		@numTokens_«name» = internal global i32 0

		define internal void @read_«name»() {
		entry:
			br label %read

		read:
			%rdIndex = load«prop» i32«addrSpace»* @fifo_«id»_rdIndex
			store i32 %rdIndex, i32* @index_«name»
			%wrIndex = load«prop» i32«addrSpace»* @fifo_«id»_wrIndex
			%getNumTokens = sub i32 %wrIndex, %rdIndex
			%numTokens = add i32 %rdIndex, %getNumTokens
			store i32 %numTokens, i32* @numTokens_«name»
			ret void
		}

		define internal void @read_end_«name»() {
		entry:
			br label %read_end

		read_end:
			%rdIndex = load i32* @index_«name»
			store«prop» i32 %rdIndex, i32«addrSpace»* @fifo_«id»_rdIndex
			ret void
		}
	'''
		
	def private printOutput(Connection connection, Port port) '''
		«val id = connection.getSafeId(port)»
		«val name = port.name + "_" + id»
		«val addrSpace = connection.addrSpace»
		«val prop = port.properties»
		«connection.printExternalFifo(port)»
	
		@SIZE_«name» = internal constant i32 «connection.safeSize»
		@index_«name» = internal global i32 0
		@rdIndex_«name» = internal global i32 0
		@numFree_«name» = internal global i32 0

		define internal void @write_«name»() {
		entry:
			br label %write

		write:
			%wrIndex = load«prop» i32«addrSpace»* @fifo_«id»_wrIndex
			store i32 %wrIndex, i32* @index_«name»
			%rdIndex = load«prop» i32«addrSpace»* @fifo_«id»_rdIndex
			store i32 %rdIndex, i32* @rdIndex_«name»
			%size = load i32* @SIZE_«name»
			%numTokens = sub i32 %wrIndex, %rdIndex
			%getNumFree = sub i32 %size, %numTokens
			%numFree = add i32 %wrIndex, %getNumFree
			store i32 %numFree, i32* @numFree_«name»
			ret void
		}

		define internal void @write_end_«name»() {
		entry:
			br label %write_end
		
		write_end:
			%wrIndex = load i32* @index_«name»
			store«prop» i32 %wrIndex, i32«addrSpace»* @fifo_«id»_wrIndex
			ret void
		}
	'''
	
	/**
	 * Returns an annotation describing the address space. 
	 * This annotation is required by the TTA backend.
	 */
	def protected getAddrSpace(Connection connection) ''''''
	
	/**
	 * Returns an annotation describing the properties of the access. 
	 * This annotation is required by the TTA backend.
	 */
	def protected getProperties(Port port) ''''''
	
	def private printExternalFifo(Connection conn, Port port) '''
		«val name = "fifo_" + conn.getSafeId(port)»
		«val type = port.type.doSwitch»
		«IF conn != null»
			«val addrSpace = conn.addrSpace»
			@«name»_content = external«addrSpace» global [«conn.safeSize» x «type»]
			@«name»_rdIndex = external«addrSpace» global i32
			@«name»_wrIndex = external«addrSpace» global i32
		«ELSE»
			@«name»_content = internal global [«conn.safeSize» x «type»] zeroinitializer, align 32
			@«name»_rdIndex = internal global i32 zeroinitializer, align 32
			@«name»_wrIndex = internal global i32 zeroinitializer, align 32
		«ENDIF»
	'''
	
	def private getNextLabel(Block block) {
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

	def private getCastOp(InstCast cast)
		'''«IF cast.source.variable.type.list»bitcast«ELSEIF ! cast.extended»trunc«ELSEIF cast.signed»sext«ELSE»zext«ENDIF»'''

	def private getCastType(Var variable)
		'''«variable.type.doSwitch»«IF variable.type.list»*«ENDIF»'''
	
	override caseInstAssign(InstAssign assign) 
		'''%«assign.target.variable.indexedName» = «assign.value.doSwitch»'''
	
	override caseInstPhi(InstPhi phi)
		'''«phi.target.variable.print» = phi «phi.target.variable.type.doSwitch» «phi.phiPairs»'''
		
	def private getPhiPairs(InstPhi phi) 
		'''«printPhiExpr(phi.values.head, (phi.block.cfgNode.predecessors.head as CfgNode).node)», «printPhiExpr(phi.values.tail.head, (phi.block.cfgNode.predecessors.tail.head as CfgNode).node)»'''
	
	def private printPhiExpr(Expression expr, Block block)
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
					«FOR connection : instance.outgoingPortMap.get(port)»
						«printPortAccess(connection, port, variable, store.indexes, store)»
						store«port.properties» «innerType.doSwitch» «store.value.doSwitch», «innerType.doSwitch»«connection.addrSpace»* «varName(variable, store)»_«connection.getSafeId(port)»
					«ENDFOR»
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
					«val connection = instance.incomingPortMap.get(port)»
					«printPortAccess(connection, port, variable, load.indexes, load)»
					«target» = load«port.properties» «innerType.doSwitch»«connection.addrSpace»* «varName(variable, load)»_«connection.getSafeId(port)»
				«ELSEIF action != null && action.outputPattern.contains(variable) && ! action.outputPattern.varToPortMap.get(variable).native»
					«val port = action.outputPattern.varToPortMap.get(variable)»
					«val connection = instance.outgoingPortMap.get(port).head»
					«printPortAccess(connection, port, variable, load.indexes, load)»
					«target» = load«port.properties» «innerType.doSwitch»«connection.addrSpace»* «varName(variable, load)»_«connection.getSafeId(port)»
				«ELSEIF action != null && action.peekPattern.contains(variable)»
					«val port = action.peekPattern.varToPortMap.get(variable)»
					«val connection = instance.incomingPortMap.get(port)»
					«printPortAccess(connection, port, variable, load.indexes, load)»
					«target» = load«port.properties» «innerType.doSwitch»«connection.addrSpace»* «varName(variable, load)»_«connection.getSafeId(port)»
				«ELSE»
					«varName(variable, load)» = getelementptr «variable.type.doSwitch»* «variable.print», i32 0«load.indexes.join(", ", ", ", "", [printIndex])»
					«target» = load «innerType.doSwitch»* «varName(variable, load)»
				«ENDIF»				
			«ELSE»
				«target» = load «variable.type.doSwitch»* «variable.print»
			«ENDIF»
		'''
	}
		
	def protected printIndex(Expression index)
		'''«index.type.doSwitch» «index.doSwitch»'''
	
	override caseInstCall(InstCall call) '''
		«IF call.print»
			call i32 (i8*, ...)* @printf(«call.arguments.join(", ", [printParameter((it as ArgByVal).value.type)])»)
		«ELSE»
			«IF call.target != null»%«call.target.variable.indexedName» = «ENDIF»call «call.procedure.returnType.doSwitch» @«call.procedure.name» («call.arguments.format(call.procedure.parameters).join(", ")»)
		«ENDIF»
	'''
	
	def protected format(EList<Arg> args, EList<Param> params) {
		val paramList = new ArrayList<CharSequence>
		if(params.size != 0) {
			for (i : 0..params.size-1) {
				paramList.add(printParameter(args.get(i), params.get(i).variable.type))
			}
		}
		return paramList
	}

	def protected printParameter(Arg arg, Type type) {
		if (arg.byRef)
			'''TODO'''
		else if (type.string)
			'''i8* «IF ((arg as ArgByVal).value as ExprVar)?.use.variable.local» «(arg as ArgByVal).value.doSwitch» «ELSE» noalias getelementptr inbounds («(arg as ArgByVal).value.type.doSwitch»* «(arg as ArgByVal).value.doSwitch», i64 0, i64 0)«ENDIF»'''
		else
			'''«type.doSwitch»«IF type.list»*«ENDIF» «(arg as ArgByVal).value.doSwitch»'''
	}
	
	
	def protected varName(Var variable, Instruction instr) {
		val procedure = EcoreHelper::getContainerOfType(instr, typeof(Procedure))
		'''%«variable.name»_elt_«(procedure.getAttribute("accessMap").objectValue as Map<Instruction, Integer>).get(instr)»'''
	}

	def private printPortAccess(Connection connection, Port port, Var variable, EList<Expression> indexes, Instruction instr) {
		val procedure = EcoreHelper::getContainerOfType(instr, typeof(Procedure))
		val accessMap = procedure.getAttribute("accessMap").objectValue as Map<Instruction, Integer>
		val accessId = accessMap.get(instr)
		val needCast = castedIndexes.contains(indexes.head)
		val fifoName = port.name + "_" + connection.getSafeId(port)
		val extName = variable.name + "_" + accessId + "_" + connection.getSafeId(port)
		'''
			«IF needCast»
				%cast_index_«extName» = zext «indexes.head.type.doSwitch» «indexes.head.doSwitch» to i32
			«ENDIF»
			%tmp_index_«extName» = add i32 %local_index_«fifoName», «IF needCast»%cast_index_«extName»«ELSE»«indexes.head.doSwitch»«ENDIF»
			%final_index_«extName» = urem i32 %tmp_index_«extName», %local_size_«fifoName»
			«varName(variable, instr)»_«connection.getSafeId(port)» = getelementptr [«connection.safeSize» x «port.type.doSwitch»]«connection.addrSpace»* @fifo_«connection.getSafeId(port)»_content, i32 0, i32 %final_index_«extName»
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