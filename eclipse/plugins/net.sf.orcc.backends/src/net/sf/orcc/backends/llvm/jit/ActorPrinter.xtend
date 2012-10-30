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
 * 
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
package net.sf.orcc.backends.llvm.jit

import java.util.HashMap
import java.util.Map
import net.sf.orcc.backends.llvm.aot.InstancePrinter
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Port
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.Var
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.Param
import net.sf.orcc.df.Action

/**
 * Generate Jade content
 * 
 * @author Antoine Lorence
 */
class ActorPrinter extends InstancePrinter {
	
	val Actor actor
	
	var currentId = 0
	val Map<Object, Integer> uids = new HashMap<Object, Integer>
	
	new(Actor actor) {
		super()
		
		this.actor = actor
	}
	
	/**
	 * Return the unique id associated with the given object, prepended with '!'
	 * 
	 * @param object the object
	 * @return unique reference to the given object
	 */
	def getObjectReference(Object object) {
		var id = currentId
		if(uids.containsKey(object)) {
			id = uids.get(object)
		} else {
			uids.put(object, id)
			currentId = id + 1
		}
		return '''!«id»'''
	}
	
	def getActorFileContent() '''
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Generated from "«actor.name»"
		declare i32 @printf(i8* noalias , ...) nounwind 
		
		«IF ! actor.inputs.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Input FIFOs
			«FOR input : actor.inputs»
				«input.fifo»
			«ENDFOR»
		«ENDIF»
		
		«IF ! actor.inputs.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Output FIFOs
			«FOR output : actor.outputs»
				«output.fifo»
			«ENDFOR»
		«ENDIF»
		
		«IF ! actor.parameters.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Parameter values of the instance
			«FOR param : actor.parameters»
				«param.actorParameter»
			«ENDFOR»
		«ENDIF»
		
		«IF ! actor.stateVars.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; State variables of the actor
			«FOR variable : actor.stateVars»
				«variable.stateVar»
			«ENDFOR»
		«ENDIF»
		
		«IF ! actor.procs.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Functions/procedures
			«FOR proc : actor.procs»
				«proc.print»
				
			«ENDFOR»
		«ENDIF»
		
		«IF ! actor.initializes.empty»
			;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
			; Initializes
			«FOR action : actor.initializes»
				«action.print»
				
			«ENDFOR»
		«ENDIF»
		
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		; Actions
		«FOR action : actor.actions»
			«action.print»
			
		«ENDFOR»
		
		«decl_MD»
	'''
	
	def decl_MD() '''
		!source = !{«actor.file.fullPath.getObjectReference»}
		!name = !{«actor.name.objectReference»}
		!action_scheduler = !{«actor.objectReference»}
		«IF ! actor.inputs.empty»
			!inputs = !{«actor.inputs.join(", ", [objectReference])»}
		«ENDIF»
		«IF ! actor.outputs.empty»
			!outputs = !{«actor.outputs.join(", ", [objectReference])»}
		«ENDIF»
		«IF ! actor.parameters.empty»
			!parameters = !{«actor.parameters.join(", ", [objectReference])»}
		«ENDIF»
		«IF ! actor.stateVars.empty»
			!state_variables = !{«actor.stateVars.join(", ", [objectReference])»}
		«ENDIF»
		«IF ! actor.procs.empty»
			!procedures = !{«actor.procs.join(", ", [objectReference])»}
		«ENDIF»
		«IF ! actor.initializes.empty»
			!initializes = !{«actor.initializes.join(", ", [objectReference])»}
		«ENDIF»
		«IF ! actor.actions.empty»
			!actions = !{«actor.actions.join(", ", [objectReference])»}
		«ENDIF»
		«IF actor.moC != null»
			!MoC = !{«actor.moC.objectReference»}
		«ENDIF»
		
		«actor.file.fullPath.objectReference» = metadata !{«actor.file.fullPath.toString.varName_MD»}
		«actor.name.objectReference» = metadata !{«actor.name.varName_MD»}
		
		; Action-scheduler
		«actionScheduler_MD»
		«IF ! actor.inputs.empty»
			; Input ports
			«FOR port : actor.inputs»
				«port.objectReference» = metadata !{metadata «port.type.objectReference», «port.name.varName_MD», «port.type.doSwitch»** «port.fifoVarName»}
				«port.type.objectReference» = metadata  !{«port.type.varType_MD», «port.type.varSize_MD»}
			«ENDFOR»
		«ENDIF»
		«IF ! actor.outputs.empty»
			; Output ports
			«FOR port : actor.outputs»
				«port.objectReference» = metadata !{metadata «port.type.objectReference», «port.name.varName_MD», «port.type.doSwitch»** «port.fifoVarName»}
				«port.type.objectReference» = metadata  !{«port.type.varType_MD», «port.type.varSize_MD»}
			«ENDFOR»
		«ENDIF»
		«IF ! actor.parameters.empty»
			; Parameters
			«FOR param : actor.parameters»
				«param.objectReference» = metadata !{metadata «param.name.objectReference», metadata «param.type.objectReference», «param.type.doSwitch»* @«param.name»}
				«param.name.objectReference» = metadata !{«param.name.varName_MD», «param.varAssignable_MD», i32 0, «param.varIndex_MD»}
				«param.type.objectReference» = metadata  !{«param.type.varType_MD», «param.type.varSize_MD»}
				
			«ENDFOR»
		«ENDIF»
		«IF ! actor.stateVars.empty»
			; State Variables
			«FOR stateVar : actor.stateVars»
				;; «stateVar.name»
				«stateVar.objectReference» = metadata !{metadata «stateVar.name.objectReference», metadata «stateVar.type.objectReference», «IF stateVar.initialized» metadata «stateVar.name.objectReference»«ELSE»null«ENDIF», «stateVar.type.doSwitch»* @«stateVar.name»}
				«stateVar.name.objectReference» = metadata !{«stateVar.name.varName_MD», «stateVar.varAssignable_MD», i32 0, «stateVar.varIndex_MD»}
				«stateVar.type.objectReference» = metadata !{«stateVar.type.varType_MD», «stateVar.type.varSize_MD»}«IF stateVar.initialized»«stateVar.name.objectReference» = metadata !{«stateVar.type.doSwitch» «stateVar.initialValue.doSwitch»}«ENDIF»
			«ENDFOR»
		«ENDIF»
		«IF ! actor.procs.empty»
			; Functions and procedures
			
			«FOR proc : actor.procs»
				«proc.objectReference» = metadata !{«proc.name.varName_MD», «proc.procNative_MD», «proc.returnType.doSwitch»(«proc.parameters.join(", ", [argumentTypeDeclaration]).wrap»)* @«proc.name»}
			«ENDFOR»
		«ENDIF»
		«IF ! actor.initializes.empty»
			; Initializes
			«FOR action : actor.initializes»
				«action.action_MD»
				
			«ENDFOR»
		«ENDIF»
		; Actions
		«FOR action : actor.actions»
			«action.action_MD»
		«ENDFOR»
		; Patterns
		<actor.templateData.patterns.keys: {pattern | <pattern_MD(actor, pattern)>}; separator="\n">
		; Variables of patterns
		<actor.templateData.patterns.keys: {pattern | <pattern_vars_MD(actor, pattern, pattern.portToVarMap)>}; separator="\n">
		; Number of tokens of patterns
		<actor.templateData.patterns.keys: {pattern | <pattern_numTokens_MD(actor, pattern, pattern.numTokensMap)>}; separator="\n">
		<if(actor.MoC)>
		; MoC
		<MoC_MD(actor)>
		<endif>
	'''
	
	def action_MD(Action action) {
		val tag = if (action.tag.identifiers.empty) "null"
				else '''metadata «action.tag.objectReference»'''
		val input = if (action.inputPattern.empty) "null"
				else '''metadata «action.inputPattern.objectReference»'''
		val output = if (action.outputPattern.empty) "null"
				else '''metadata «action.outputPattern.objectReference»'''
		val peek = if (action.peekPattern.empty) "null"
				else '''metadata «action.peekPattern.objectReference»'''
		'''
			;; «action.name»
			«action.objectReference» = metadata !{«tag», «input», «output»,«peek», metadata «action.objectReference»
				>, metadata <Body_decl_MD(actor, action)>}
			<if(!action.tag.identifiers.empty)>
			<Tag_MD(actor, action.tag)>
			<endif>
			<Sched_MD(actor, action)>
			<Body_MD(actor, action)>
		'''
	}

	def argumentTypeDeclaration(Param param) {
		if (param.variable.type.string) " i8* "
		else if (param.variable.type.list) ''' «param.variable.type.doSwitch»* '''
		else ''' «param.variable.type.doSwitch» '''
	}

	def procNative_MD(Procedure proc)
		'''«IF proc.native»i1 1«ELSE»i1 0«ENDIF»'''

	
	def varIndex_MD(Var variable)
		'''i32 «IF variable.index != null»«variable.index»«ELSE»0«ENDIF»'''
	
	def varAssignable_MD(Var variable) 
		'''i1 «IF variable.assignable»1«ELSE»0«ENDIF»'''
	
	def varSize_MD(Type type)
		'''«IF type.list» «(type as TypeList).dimensions.join(", ", ['''i32 «it»'''])» «ELSE» null «ENDIF»'''
	
	def varName_MD(String name)
		'''metadata !"«name»"'''
	
	def actionScheduler_MD() {
		val outsideFSM = if ( ! actor.actionsOutsideFsm.empty) '''metadata «actor.actionsOutsideFsm.objectReference»''' else "null"
		val fsm = if (actor.fsm != null) '''metadata «actor.fsm.objectReference»''' else "null"
		'''
			«actor.objectReference» = metadata !{«outsideFSM», «fsm»}
			«IF ! actor.actionsOutsideFsm.empty»
				;; Actions outside FSM
				«actor.actionsOutsideFsm.objectReference» = metadata !{«actor.actionsOutsideFsm.join(", ", ['''metadata «objectReference»'''])»}
			«ENDIF»
			«IF actor.fsm != null»
				;; FSM
				«FSM_MD»
			«ENDIF»
			
		'''
	}
	
	def FSM_MD() '''
		«actor.fsm.objectReference» = metadata !{«actor.fsm.initialState.name.varName_MD», metadata «actor.fsm.states.objectReference», metadata «actor.fsm.transitions.objectReference»}
		;;; States
		«actor.fsm.states.objectReference» = metadata !{«actor.fsm.states.join(", ", [name.varName_MD])»}
		;;; All transitions
		«actor.fsm.transitions.objectReference» = metadata !{«actor.fsm.states.join(", ", ['''metadata «objectReference»'''])»}
		«FOR state : actor.fsm.states»
			«state.transition_MD»
		«ENDFOR»
	'''
	
	def transition_MD(State state) '''
		;;;; Transitions from «state.name»
		«IF state.outgoing.empty»
			«state.objectReference» = metadata !{«state.name.varName_MD», null}
		«ELSE»
			«state.objectReference» = metadata !{«state.name.varName_MD», metadata «state.outgoing.objectReference»}
			«state.outgoing.objectReference» = metadata !{«state.outgoing.join(", ", ['''metadata «objectReference»'''])»}
			«FOR transition : state.outgoing»
				«transition.objectReference» = metadata !{metadata «(transition as Transition).action.objectReference», «state.name.varName_MD»"}
			«ENDFOR»
		«ENDIF»
	'''

	def varType_MD(Type type) {
		switch type {
			case type.isInt: '''i32 «(type as TypeInt).size»'''
			case type.isUint: '''i32 «(type as TypeUint).size»'''
			case type.bool: '''i32 1'''
			case type.list: '''«(type as TypeList).innermostType.varType_MD»'''
			case type.string: '''i32 8'''
			default: ""
		}
	}
	
	def actorParameter(Var variable)
		'''@«variable.name» = global «variable.type.doSwitch» undef'''

	
	def fifo(Port port) '''
		«port.fifoVarName» = global «port.type.doSwitch»* null
	'''
	
	def fifoVarName(Port port)
		'''@«port.name»_ptr'''


	
}