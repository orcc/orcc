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
import net.sf.orcc.graph.Edge
import net.sf.orcc.ir.Block
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile
import net.sf.orcc.ir.CfgNode
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstPhi
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Instruction
import net.sf.orcc.ir.Param
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.util.EcoreHelper
import org.eclipse.emf.common.util.EList

/*
 * Compile Instance llvm source code
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends LLVMTemplate {
	
	val Instance instance
	
	val Map<Expression, Expression> castedIndexes
	val Map<Var, Var> castedList
	val Map<State, Integer> stateToLabel
	val Map<Pattern, Map<Port, Integer>> portToIndexByPatternMap
	
	var optionProfile = false
	
	/**
	 * Default constructor, do not activate profile option
	 */
	new(Instance instance) {
		this(instance, false)
	}
	
	/**
	 * Constructor, set profile option to true or false
	 */
	new(Instance instance, boolean optionProfile) {
		
		if ( ! instance.isActor) {
			OrccLogger::severeln("Instance " + instance.name + " is not an Actor's instance")
		}
		
		this.instance = instance
		
		
		castedIndexes = computeCastedIndex
		castedList = computeCastedList
		stateToLabel = computeStateToLabel
		portToIndexByPatternMap = computePortToIndexByPatternMap
	}
	
	def getInstanceFileContent() '''
		target triple = "x86_64"
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Generated from "«instance.actor.name»"
		declare i32 @printf(i8* noalias , ...) nounwind 
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Connected FIFOs
		«FOR port : instance.actor.inputs.filter[ ! native]»
			«printExternalFifo(instance.incomingPortMap.get(port), port)»
			
		«ENDFOR»
		«FOR port : instance.actor.outputs.filter[ ! native]»
			«printExternalFifo(instance.outgoingPortMap.get(port).head, port)»
			
		«ENDFOR»
		«IF ! instance.actor.inputs.empty »
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Input ports
			«FOR port : instance.actor.inputs.filter[ ! native]»
				«printInput(port, instance.incomingPortMap.get(port))»
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.outputs.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Output ports
			«FOR port : instance.actor.inputs.filter[ ! native]»
				«printOutput(port, instance.outgoingPortMap.get(port).head)»
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.parameters.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Parameter values of the instance
			«FOR i : 0..instance.arguments.size-1»
				@«instance.actor.parameters.get(i).name» = internal global «instance.actor.parameters.get(i).type» «instance.arguments.get(i).value.doSwitch»
			«ENDFOR»
			
		«ENDIF»
		«IF ! instance.actor.stateVars.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; State variables of the actor
			«FOR variable : instance.actor.stateVars»
				@«variable.name» = internal «variable.printStateVarNature» «variable.type.doSwitch» «variable.initialize»
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
					call «init.body.returnType» @«init.body.name»
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
		switch i32 %local_FSM_state, label %default [ «fsm.states.join("\n", [printFsmState])» ]
	'''
	
	def printFsmState(State state) '''
		i32 «stateToLabel.get(state)», label %bb_s_«state.name»
	'''

	def printTransition(State state) '''
		; STATE <state.name>
		bb_s_«state.name»:
			«IF ! instance.actor.actionsOutsideFsm.empty»
				call void @«instance.name»_outside_FSM_scheduler()
			«ENDIF»
		«schedulingStates(state, state.outgoing)»
	'''
	
	def schedulingStates(State state, EList<Edge> outgoing) '''
		«state.actionTestState(outgoing)»
	'''
	
	// TODO : replace recursive calls by loop
	def actionTestState(State sourceState, Iterable<Edge> outgoing) {
		val transition = outgoing.head as Transition
		'''
				; ACTION «transition.action.name»
			«IF ! transition.action.inputPattern.empty»
				;; Input pattern
				«checkInputPattern(transition.action, transition.action.inputPattern, sourceState)»
				%is_schedulable_«sourceState.name»_«transition.action.name» = call i1 @«transition.action.scheduler.name» ()
				%is_fireable_«sourceState.name»_«transition.action.name» = and i1 %is_schedulable_«sourceState.name»_«transition.action.name», %has_valid_inputs_«sourceState.name»_«transition.action.name»_«transition.action.inputPattern.ports.size»
				
				br i1 %is_fireable_«sourceState.name»_«transition.action.name», label %bb_«sourceState.name»_«transition.action.name»_check_outputs, label %bb_«sourceState.name»_«transition.action.name»_unschedulable
			«ELSE»
				;; Empty input pattern
				%is_fireable_«sourceState.name»_«transition.action.name» = call i1 @«transition.action.scheduler.name» ()
				
				br i1 %is_fireable_«sourceState.name»_«transition.action.name», label %bb_«sourceState.name»_«transition.action.name»_check_outputs, label %bb_«sourceState.name»_«transition.action.name»_unschedulable
			«ENDIF»
			
			
			bb_«sourceState.name»_«transition.action.name»_check_outputs:
			«IF ! transition.action.outputPattern.empty»
				;; Output pattern
				«checkOutputPattern(transition.action, transition.action.outputPattern, sourceState)»
				
				br i1 %has_valid_outputs_«sourceState.name»_«transition.action.name»_«transition.action.outputPattern.ports.size», label %bb_«sourceState.name»_«transition.action.name»_fire, label %bb_«sourceState.name»_finished<
			«ELSE»
				;; Empty output pattern
				
				br label %bb_«sourceState.name»_«transition.action.name»_fire
			«ENDIF»
			
			bb_«sourceState.name»_«transition.action.name»_fire:
				call void @«transition.action.body.name» ()
				
				br label %bb_s_«transition.target.name»
			bb_«sourceState.name»_«transition.action.name»_unschedulable
			«IF outgoing.tail.size > 0»
				«actionTestState(sourceState, outgoing.tail)»
			«ELSE»
				\tbr label %bb_«sourceState.name»_finished
				bb_«sourceState.name»_finished:
					store i32 «stateToLabel.get(sourceState)», i32* @_FSM_state
					br label %bb_waiting
			«ENDIF»
		'''
	}

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
	
	// TODO : replace this recursive method by a loop,simpler to read and maintain
	def printActionLoop(EList<Action> actions, boolean outsideFSM) '''
		«actionTest(actions.head, actions.tail, outsideFSM)»
	'''
	
	def actionTest(Action action, Iterable<Action> restActions, boolean outsideFSM) '''
			; ACTION «action.name»
		«IF ! action.inputPattern.empty»
			;; Input pattern
			«checkInputPattern(action, action.inputPattern)»
			%is_schedulable_«action.name» = call i1 @«action.scheduler.name» ()
			%is_fireable_«action.name» = and i1 %is_schedulable_«action.name», %has_valid_inputs_«action.name»_«action.inputPattern.ports.size»
			
			br i1 %is_fireable_«action.name», label %bb_«action.name»_check_outputs, label %bb_«action.name»_unschedulable
		«ELSE»
			;; Empty input pattern
			%is_fireable_«action.name» = call i1 @«action.scheduler.name» ()
			
			br i1 %is_fireable_«action.name», label %bb_«action.name»_check_outputs, label %bb_«action.name»_unschedulable
		«ENDIF»
		
		
		bb_«action.name»_check_outputs:
		«IF ! action.outputPattern.empty»
			;; Output pattern
			«checkOutputPattern(action, action.outputPattern)»
			
			br i1 %has_valid_outputs_«action.name»_«action.outputPattern.ports.size», label %bb_«action.name»_fire, label %bb_finished
		«ELSE»
			;; Empty output pattern
			
			br label %bb_«action.name»_fire
		«ENDIF»
		
		bb_«action.name»_fire:
			call void @«action.body.name» ()
		
		«IF outsideFSM»
			br label %bb_outside_scheduler_start
		«ELSE»
			br label %bb_scheduler_start
		«ENDIF»
		
		bb_«action.name»_unschedulable:
		«IF ! restActions.empty»
			«actionTest(restActions.head, restActions.tail, outsideFSM)»
		«ELSE»
			«IF outsideFSM»
				br label %bb_outside_finished
			«ELSE»
				br label %bb_waiting
			«ENDIF»
		«ENDIF»
	'''
	
	def checkInputPattern(Action action, Pattern pattern) {
		checkInputPattern(action, pattern, null)
	}
	def checkInputPattern(Action action, Pattern pattern, State state) {
		val stateName = if( state != null) '''«state.name»_''' else ""
		val portToIndexMap = portToIndexByPatternMap.get(pattern)
		val firstPort = pattern.ports.head		
		'''
			%numTokens_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @numTokens_«firstPort.name»
			%index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @index_«firstPort.name»
			%status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = sub i32 %numTokens_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», %index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)»
			%has_valid_inputs_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = icmp sge i32 %status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», «pattern.numTokensMap.get(firstPort)»
			
			«FOR port : pattern.ports.tail»
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
		val portToIndexMap = portToIndexByPatternMap.get(pattern)
		val firstPort = pattern.ports.head
		'''
			%size_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @SIZE_«firstPort.name»
			%index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @index_«firstPort.name»
			%rdIndex_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = load i32* @fifo_«getId(instance.outgoingPortMap.get(firstPort).head, firstPort)»_rdIndex
			%tmp_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = sub i32 %size_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», %index_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)»
			%status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = add i32 %tmp_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», %rdIndex_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)»
			%has_valid_outputs_«stateName»«action.name»_«portToIndexMap.get(firstPort)» = icmp uge i32 %status_«firstPort.name»_«stateName»«action.name»_«portToIndexMap.get(firstPort)», «pattern.numTokensMap.get(firstPort)»
			
			«FOR port : pattern.ports.tail»
				%size_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = load i32* @SIZE_<port.name>
				%index_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = load i32* @index_<port.name>
				%rdIndex_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = load i32* @fifo_«getId(instance.outgoingPortMap.get(port).head, port)»_rdIndex
				%tmp_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = sub i32 %size_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)», %index_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)»
				%status_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)» = add i32 %tmp_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)», %rdIndex_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)»
				%available_output_«stateName»«action.name»_«port.name» = icmp sge i32 %status_«port.name»_«stateName»«action.name»_«portToIndexMap.get(port)», «pattern.numTokensMap.get(port)»
				%has_valid_outputs_«stateName»«action.name»_«portToIndexMap.get(port)» = and i1 %has_valid_outputs_«stateName»«action.name»_«portToIndexMap.get(pattern.ports.get(pattern.ports.indexOf(port) - 1))», %available_output_«stateName»«action.name»_«port.name»
				
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
			
		«action.scheduler.blocks.doSwitch»
		
		define internal void @«action.body.name»() nounwind {
		entry:
			«FOR local : action.body.locals»
				«local.variableDeclaration»
			«ENDFOR»
			«FOR port : action.inputPattern.ports.filter[ ! native] + action.outputPattern.ports.filter[ ! native]»
				«port.fifoVar»
			«ENDFOR»
			br label %b«action.body.blocks.head.label»
		
		«action.body.blocks.doSwitch»
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
		«IF procedure.native»
			declare «procedure.returnType» @«procedure.name»(«procedure.parameters.join(", ", [argumentDeclaration] )») nounwind
		«ELSE»
			define internal «procedure.returnType» @«procedure.name»(«procedure.parameters.join(", ", [argumentDeclaration])») nounwind {
			entry:
				«IF ! procedure.locals.empty»
					«FOR local : procedure.locals»
						«local.variableDeclaration»
					«ENDFOR»
				«ENDIF»
				br label %b«procedure.blocks.head.label»
				
				«procedure.blocks.doSwitch»
			}
		«ENDIF»
	'''
	
	def label(Block block) '''b«block.cfgNode.number»'''
	
	def variableDeclaration(Var variable) {
		if(variable.type.list && ! castedList.containsKey(variable)) '''%«variable.indexedName» = alloca «variable.type.doSwitch»'''
	}
	
	def argumentDeclaration(Param param) {
		if (param.variable.type.string) '''i8* %«param.variable.name»'''
		else if (param.variable.type.list) '''«param.variable.type.doSwitch»* %«param.variable.name»'''
		else '''«param.variable.type.doSwitch» %«param.variable.name»'''
	}
	
	def initialize(Var variable) {
		if(variable.initialValue != null) variable.initialValue.doSwitch
		else "zeroinitializer, align 32"
	}

	def printStateVarNature(Var variable) {
		if(variable.assignable) "global"
		else "constant"
	}
	
	def printInput(Port port, Connection connection) '''
		@SIZE_«port.name» = internal constant i32 «connection.getFifoSize»
		@index_«port.name» = internal global i32 0
		@numTokens_«port.name» = internal global i32 0
		
		«printReadTokensFunction(port, connection)»
	'''
	
	def printReadTokensFunction(Port port, Connection connection) '''
		define internal void @read_«port.name»() {
		entry:
			br label %read
		
		read:
			%rdIndex = load i32* @fifo_«getId(connection, port)»_rdIndex
			store i32 %rdIndex, i32* @index_«port.name»
			%wrIndex = load i32* @fifo_«getId(connection, port)»_wrIndex
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
			store i32 %rdIndex, i32* @fifo_«getId(connection, port)»_rdIndex
			ret void
		}
	'''
		
	def printOutput(Port port, Connection connection) '''
		@SIZE_«port.name» = internal constant i32 «connection.getFifoSize»
		@index_«port.name» = internal global i32 0
		@numFree_«port.name» = internal global i32 0
		
		«printWriteTokensFunction(port, connection)»
	'''
	
	def printWriteTokensFunction(Port port, Connection connection) '''
		define internal void @write_«port.name»() {
		entry:
			br label %write
		
		write:
			%wrIndex = load i32* @fifo_«getId(connection, port)»_wrIndex
			store i32 %wrIndex, i32* @index_«port.name»
			%rdIndex = load i32* @fifo_«getId(connection, port)»_rdIndex
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
			store i32 %wrIndex, i32* @fifo_«getId(connection, port)»_wrIndex
			ret void
		}
	'''
	
	def printExternalFifo(Connection conn, Port port) '''
		@fifo_«getId(conn, port)»_content = «IF conn != null»external«ELSE»internal«ENDIF» global [«conn.getFifoSize» x «port.type.doSwitch»]«IF conn != null» zeroinitializer, align 32«ENDIF»
		@fifo_«getId(conn, port)»_rdIndex = «IF conn != null»external«ELSE»internal«ENDIF» global i32«IF conn != null» zeroinitializer, align 32«ENDIF»
		@fifo_«getId(conn, port)»_wrIndex = «IF conn != null»external«ELSE»internal«ENDIF» global i32«IF conn != null» zeroinitializer, align 32«ENDIF»
	'''
	
	def printNextLabel(Block block) {
		if (block.blockWhile) (block as BlockWhile).joinBlock.label
		else block.label
	}
	
	override caseBlockBasic(BlockBasic blockBasic) '''
		b«blockBasic.label»:
			«blockBasic.instructions.visitInstructions»
			«IF ! blockBasic.cfgNode.successors.empty»
				br label %b«(blockBasic.cfgNode.successors.head as CfgNode).node.doSwitch»
			«ENDIF»
	'''
	
	override caseBlockIf(BlockIf blockIf) '''
		b«blockIf.label»:
			br i1 «blockIf.condition.doSwitch», label %b«blockIf.thenBlocks.head.printNextLabel», label %b«blockIf.elseBlocks.head.printNextLabel»
		
		«blockIf.thenBlocks.doSwitch»
		
		«blockIf.elseBlocks.doSwitch»
		
		«blockIf.joinBlock.doSwitch»
	'''

	override caseBlockWhile(BlockWhile whileNode) '''
		b«whileNode.joinBlock.label»:
			«whileNode.joinBlock.instructions.visitInstructions»
			br i1 «whileNode.condition.doSwitch», label %b«whileNode.blocks.head.label», label %b«whileNode.label»
		
		«whileNode.blocks.doSwitch»
		
		b«whileNode.label»:
			br label %b«(whileNode.cfgNode.successors.head as CfgNode).node.printNextLabel»
	'''
	
	override caseInstAssign(InstAssign assign) 
		'''%«assign.target.variable.indexedName» = «assign.value.doSwitch»'''
	
	override caseInstPhi(InstPhi phi)
		'''«phi.target.variable.print» = phi «phi.target.variable.type.doSwitch» «phi.printPhiPairs»'''
		
	def printPhiPairs(InstPhi phi) 
		'''«printPhiExpr(phi.values.head, (phi.block.cfgNode.predecessors.head as CfgNode).node)», «printPhiExpr(phi.values.tail.head, (phi.block.cfgNode.predecessors.tail.head as CfgNode).node)»'''
	
	def printPhiExpr(Expression expr, Block block)
		'''[«expr.doSwitch», %b«block.label»]'''
		
	override caseInstReturn(InstReturn retInst) {
		if (EcoreHelper::getContainerOfType(retInst, typeof(Action)) == null)
			'''ret «retInst.value.type.doSwitch» «retInst.value.doSwitch»'''
	}
	
	override caseInstStore(InstStore store) {
		val action = EcoreHelper::getContainerOfType(store, typeof(Action))
		'''
			«IF store.target.variable.type.list»
				«IF action.outputPattern.varToPortMap.get(store.target.variable) != null && ! action.outputPattern.varToPortMap.get(store.target.variable).native»
					«printPortAccess(instance.outgoingPortMap.get(action.outputPattern.varToPortMap.get(store.target.variable)).head, action.outputPattern.varToPortMap.get(store.target.variable), store.target.variable, store.indexes, store)»
				«ELSE»
					«varName(store.target.variable, store)» = getelementptr «store.target.variable.type.doSwitch»* «store.target.variable.print», i32 0«store.indexes.join(", ", ", ", "", [doSwitch])»
				«ENDIF»
				store «(store.target.variable.type as TypeList).innermostType» «store.value.doSwitch», «(store.target.variable.type as TypeList).innermostType»* «varName(store.target.variable, store)»
			«ELSE»
				store «store.target.variable.type.doSwitch» «store.value.doSwitch», «store.target.variable.type.doSwitch»* «store.target.variable.print»
			«ENDIF»
		'''
	}
	
	override caseInstLoad(InstLoad load) {
		val action = EcoreHelper::getContainerOfType(load, typeof(Action))
		'''
			«IF load.source.variable.type.list»
				«IF action.inputPattern.varToPortMap.get(load.source.variable) != null && ! action.inputPattern.varToPortMap.get(load.source.variable).native»
					«printPortAccess(instance.incomingPortMap.get(action.inputPattern.varToPortMap.get(load.source.variable)), action.inputPattern.varToPortMap.get(load.source.variable), load.source.variable, load.indexes, load)»
				«ELSEIF action.outputPattern.varToPortMap.get(load.source.variable) != null && ! action.outputPattern.varToPortMap.get(load.source.variable).native»
					«printPortAccess(instance.outgoingPortMap.get(action.outputPattern.varToPortMap.get(load.source.variable)).head, action.outputPattern.varToPortMap.get(load.source.variable), load.source.variable, load.indexes, load)»
				«ELSEIF action.peekPattern.varToPortMap.get(load.source.variable) != null»
					«printPortAccess(instance.incomingPortMap.get(action.peekPattern.varToPortMap.get(load.source.variable)), action.peekPattern.varToPortMap.get(load.source.variable), load.source.variable, load.indexes, load)»
				«ELSE»
					«varName(load.source.variable, load)» = getelementptr «load.source.variable.type.doSwitch»* «load.source.variable.print», i32 0, «load.indexes.join(", ", ", ", "", [doSwitch])»
				«ENDIF»
				«load.target.variable.print» = load «(load.source.variable.type as TypeList).innermostType.doSwitch»* «varName(load.source.variable, load)»
			«ELSE»
				«load.target.variable.print» = load «load.source.variable.type.doSwitch»* «load.source.variable.print»
			«ENDIF»
		'''
	}
	
	def varName(Var variable, Instruction instr) {
		val procedure = EcoreHelper::getContainerOfType(instr, typeof(Procedure))
		'''%«variable.name»_elt_«(procedure.getAttribute("accessMap").objectValue as Map<Instruction, Integer>).get(instr)»'''
	}

	def printPortAccess(Connection connection, Port port, Var variable, EList<Expression> indexes, Instruction instr) {
		val procedure = EcoreHelper::getContainerOfType(instr, typeof(Procedure))
		val accessMap = procedure.getAttribute("accessMap").objectValue as Map<Instruction, Integer>
		'''
			«IF castedIndexes.containsKey(indexes.head)»
				%cast_index_«variable.name»_«accessMap.get(instr)» = zext «indexes.head.type.doSwitch» «indexes.head.doSwitch» to i32
			«ENDIF»
			%tmp_index_«variable.name»_«accessMap.get(instr)» = add i32 %local_index_«port.name», «IF castedIndexes.containsKey(indexes.head)»%cast_index_«variable.name»_«accessMap.get(instr)»«ELSE»«indexes.head.doSwitch»«ENDIF»
			%final_index_«variable.name»_«accessMap.get(instr)» = urem i32 %tmp_index_«variable.name»_«accessMap.get(instr)», %local_size_«port.name»
			«varName(variable, instr)» = getelementptr [«connection.getFifoSize» x «port.type.doSwitch»]* @fifo_«getId(connection, port)»_content, i32 0, i32 %final_index_«variable.name»_«accessMap.get(instr)»
		'''
	}
	
	def computeCastedList() {
		val castedList = new HashMap<Var, Var>
		for (variable : instance.actor.eAllContents.toIterable.filter(typeof(Var))) {
			if(variable.type.list && ! variable.defs.empty && variable.defs.head.eContainer instanceof InstCast) {
				castedList.put(variable, variable)
			}
		}
		return castedList
	}

	def computeStateToLabel() {
		val stateToLabel = new HashMap<State, Integer>
		if(instance.actor.hasFsm){
			for ( i : 0..instance.actor.fsm.states.size-1) {
				stateToLabel.put(instance.actor.fsm.states.get(i), i)
			}
		}
		return stateToLabel
	}
	
	def computeCastedIndex() {
		val castedIndexes = new HashMap<Expression, Expression>
		for (instr : instance.actor.eAllContents.toIterable.filter(typeof(Instruction))) {
			if(instr.instLoad) {
				val load = instr as InstLoad
				if( ! load.indexes.empty && load.indexes.head.type.sizeInBits != 32) {
					castedIndexes.put(load.indexes.head, load.indexes.head)
				}
			} else if (instr.instStore) {
				val store = instr as InstStore
				if( ! store.indexes.empty && store.indexes.head.type.sizeInBits != 32) {
					castedIndexes.put(store.indexes.head, store.indexes.head)
				}
			}
		}
		return castedIndexes
	}
	
	def computePortToIndexByPatternMap() {
		val portToIndexByPatternMap = new HashMap<Pattern, Map<Port, Integer>>
		for(pattern : instance.actor.eAllContents.toIterable.filter(typeof(Pattern))) {
			val portToIndex = new HashMap<Port, Integer>
			for(i : 0..pattern.ports.size-1) {
				portToIndex.put(pattern.ports.get(i), i + 1)
			}
			portToIndexByPatternMap.put(pattern, portToIndex)
		}
		return portToIndexByPatternMap
	}
}