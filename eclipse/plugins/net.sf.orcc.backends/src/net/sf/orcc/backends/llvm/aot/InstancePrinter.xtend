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

import java.util.Map
import net.sf.orcc.df.Action
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.ir.Block
import net.sf.orcc.ir.Param
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.Var
import net.sf.orcc.util.OrccLogger
import java.util.HashMap
import net.sf.orcc.backends.ir.InstCast
import net.sf.orcc.df.State
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.Instruction
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstStore
import net.sf.orcc.df.Pattern
import org.eclipse.emf.common.util.EList
import net.sf.orcc.df.FSM

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
	
	// TODO : check if it is used
	var Action currentAction;
	
	new(Instance instance, Map<String, Object> options) {
		
		if ( ! instance.isActor) {
			OrccLogger::severeln("Instance " + instance.name + " is not an Actor's instance")
		}
		
		this.instance = instance
		
		if(options.containsKey("profile")) {
			optionProfile = options.get("profile") as Boolean
		}
		
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
			«printExternalFifo(instance.outgoingPortMap.get(port).get(0), port)»
			
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
				«printOutput(port, instance.outgoingPortMap.get(port).get(0))»
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
			<instance.actor.stateVars: stateVar(); separator="\n">
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
			«schedulerWithFSM(instance.actor.actionsOutsideFsm, instance.actor.fsm)»
		«ELSE»
			«schedulerWithoutFSM(instance.actor.actionsOutsideFsm)»
		«ENDIF»
	'''
	def schedulerWithFSM(EList<Action> list, FSM fsm) { }

	def schedulerWithoutFSM(EList<Action> list) '''
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
		«actionTest(actions.get(0), actions.tail, outsideFSM)»
	'''
	
	def actionTest(Action action, Iterable<Action> restActions, boolean outsideFSM) '''
			; ACTION <action.name>
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
		
		bb_<action.name>_fire:
			call void @<action.body.name> ()
		
		<if(outsideFSM)>
			br label %bb_outside_scheduler_start
		<else>
			br label %bb_scheduler_start
		<endif>
		
		bb_<action.name>_unschedulable:
		<if(restActions)>
		<actionTest(first(restActions), rest(restActions),outsideFSM)>
		<else>
			<if(outsideFSM)>
				br label %bb_outside_finished
			<else>
				br label %bb_waiting
			<endif>
		<endif>
	'''
	
	def checkInputPattern(Action action, Pattern pattern) { }

	def checkOutputPattern(Action action, Pattern pattern) { }


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
			br label %b«action.scheduler.blocks.get(0).label»
			
		«action.scheduler.blocks.doSwitch»
		
		define internal void @«action.body.name»() nounwind {
		entry:
			«FOR local : action.body.locals»
				«local.variableDeclaration»
			«ENDFOR»
			«FOR port : action.inputPattern.ports.filter[ ! native] + action.outputPattern.ports.filter[ ! native]»
				«port.fifoVar»
			«ENDFOR»
			br label %b«action.body.blocks.get(0).label»
		
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
				br label %b«procedure.blocks.get(0).label»
				
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
		@SIZE_«port.name» = internal constant i32 «connection.getSize»
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
		@SIZE_«port.name» = internal constant i32 «connection.getSize»
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
		@fifo_«getId(conn, port)»_content = «IF conn != null»external«ELSE»internal«ENDIF» global [«conn.getSize» x «port.type.doSwitch»]«IF conn != null» zeroinitializer, align 32«ENDIF»
		@fifo_«getId(conn, port)»_rdIndex = «IF conn != null»external«ELSE»internal«ENDIF» global i32«IF conn != null» zeroinitializer, align 32«ENDIF»
		@fifo_«getId(conn, port)»_wrIndex = «IF conn != null»external«ELSE»internal«ENDIF» global i32«IF conn != null» zeroinitializer, align 32«ENDIF»
	'''
	
	def getId(Connection connection, Port port) {
		if(connection != null) connection.getAttribute("id").stringValue
		else port.name
	}

	def getSize(Connection connection) {
		if(connection != null) connection.size.toString
		else "512"
	}

	def computeCastedList() {
		val castedList = new HashMap<Var, Var>
		for (variable : instance.actor.eAllContents.toIterable.filter(typeof(Var))) {
			if(variable.type.list && ! variable.defs.empty && variable.defs.get(0).eContainer instanceof InstCast) {
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
				if( ! load.indexes.empty && load.indexes.get(0).type.sizeInBits != 32) {
					castedIndexes.put(load.indexes.get(0), load.indexes.get(0))
				}
			} else if (instr.instStore) {
				val store = instr as InstStore
				if( ! store.indexes.empty && store.indexes.get(0).type.sizeInBits != 32) {
					castedIndexes.put(store.indexes.get(0), store.indexes.get(0))
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