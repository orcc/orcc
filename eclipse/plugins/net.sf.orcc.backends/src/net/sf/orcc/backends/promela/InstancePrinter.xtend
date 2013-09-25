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
 package net.sf.orcc.backends.promela

import java.io.File
import java.util.List
import java.util.Map
import java.util.Set
import net.sf.orcc.df.Action
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Var
import net.sf.orcc.util.OrccUtil
import org.eclipse.emf.ecore.EObject
import net.sf.orcc.df.Actor
import net.sf.orcc.backends.promela.transform.PromelaSchedulingModel

/*
 * Compile Instance promela
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends PromelaTemplate {
	
	val Actor actor
	val Set<Var> schedulingVars
	
	val Map<Action, List<InstLoad>> loadPeeks
	val Map<Action, List<Expression>> guards
	val Map<EObject, List<Action>> priority
	
	new(Actor actor, Map<String, Object> options, PromelaSchedulingModel schedulingModel) {
		this.actor = actor
		this.schedulingVars = schedulingModel.allSchedulingVars
		
		loadPeeks = options.get("loadPeeks") as Map<Action, List<InstLoad>>
		guards = options.get("guards") as Map<Action, List<Expression>>
		priority = options.get("priority") as Map<EObject, List<Action>>
	}
	
	/**
	 * Print file content for the instance
	 * 
	 */
	def printInstance(String targetFolder) {
		val content = instanceFileContent
		val file = new File(targetFolder + File::separator + actor.name + ".pml")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	def getInstanceFileContent() '''
		«IF actor.hasFsm»
			/* States of the FSM */
			«FOR i : 0..actor.fsm.states.size-1»
				int «actor.simpleName»_state_«actor.fsm.states.get(i).name» = «i»;
			«ENDFOR»
			/* Initial State */
			int fsm_state_«actor.simpleName» = «actor.simpleName»_state_«actor.fsm.initialState.name»;
		«ENDIF»
		
		«IF ! actor.stateVars.nullOrEmpty»
			/* State variables */
			«FOR stateVar : actor.stateVars»
				«IF schedulingVars.contains(stateVar)»
					«stateVar.declareStateVar»
				«ENDIF»
			«ENDFOR»
		«ENDIF»
		
		/* Process */
		proctype «actor.simpleName»() {

			«IF ! actor.stateVars.nullOrEmpty»
				/* State variables */
				«FOR stateVar : actor.stateVars»
					«IF !schedulingVars.contains(stateVar)»
						«stateVar.declareStateVar»
					«ENDIF»
				«ENDFOR»
			«ENDIF»
		
			/*peek variables*/
			«FOR action : actor.actions»
				«FOR inst : loadPeeks.get(action)»
					«inst.target.variable.type.doSwitch» «inst.target.variable.name» = 0;
				«ENDFOR»
				«FOR inst : loadPeeks.get(action)»
					bool «inst.target.variable.name»_done = 0;
				«ENDFOR»
			«ENDFOR»

			«IF ! actor.parameters.nullOrEmpty»
				/* Actor parameters*/
				«FOR variable : actor.parameters»
					«variable.declareStateVar»
				«ENDFOR»
			«ENDIF»
			
			/* Ports */
			«FOR port : actor.inputs»
				«port.type.doSwitch» «port.name»[«port.numTokensConsumed»];
			«ENDFOR»
			«FOR port : actor.outputs»
				«port.type.doSwitch» «port.name»[«port.numTokensProduced»];
			«ENDFOR»
		
			«IF actor.hasFsm»
				do
				«FOR state : actor.fsm.states»
					«state.newState»
				«ENDFOR»
				od;
			«ELSE»
				do
				:: skip ->
					if
					«FOR action : actor.actionsOutsideFsm»
						«action.printPeekPattern»
					«ENDFOR»
					«FOR action : actor.actionsOutsideFsm»
						«action.printScheduler»
					«ENDFOR»
					fi;
				od;
			«ENDIF»
		}
	'''
	
	def newState(State state) '''
		::	fsm_state_«actor.simpleName» == «actor.simpleName»_state_«state.name» -> {
			if
			«FOR edge : state.outgoing»
				«(edge as Transition).action.printPeekPattern»
				«(edge as Transition).action.printSchedulerFSM((edge as Transition))»
			«ENDFOR»
			«FOR action : actor.actionsOutsideFsm»
				«action.printPeekPattern»
			«ENDFOR»
			«FOR action : actor.actionsOutsideFsm»
				«action.printScheduler»
			«ENDFOR»
			fi;
		}
	'''

	def printPeekPattern(Action action) {
		val pattern = action.peekPattern
		if( ! pattern.variables.nullOrEmpty) {
			'''
				::	/*«action.name»_peek()*/ atomic { 
					nempty(«pattern.variables.join(" && ", [peekPatternCheck(pattern)])»)«loadPeeks.get(action).join(" && ", " && ", "", ['''«target.variable.name»_done == 0'''])» ->
					«FOR variable : pattern.variables»
						chan_«actor.simpleName»_«pattern.varToPortMap.get(variable).name»?<«variable.name»[0]>;
					«ENDFOR»
					«FOR variable : pattern.variables»
						«FOR ld : loadPeeks.get(action)»
							«ld.doSwitch»
						«ENDFOR»
					«ENDFOR»
					«FOR instLoad : loadPeeks.get(action)»
						«instLoad.target.variable.name»_done = 1;
					«ENDFOR»
				}
			'''
		}
	}
	
	def peekPatternCheck(Var variable, Pattern pattern)
		'''chan_«actor.simpleName»_«pattern.varToPortMap.get(variable).name»'''
	
	
	def printSchedulerFSM(Action action, Transition trans) '''
		::	/* «action.name» */ atomic { 
			«action.guardFSM(trans)»
			«action.inputPattern.inputChannelCheck»
			«action.outputPattern.outputChannelCheck»
			-> 
			/* Temp variables*/
			int promela_io_index; // used for reading/writing multiple tokens
			«FOR local : action.body.locals»
				«local.declare»;
			«ENDFOR»
			 
			«action.inputPattern.inputPattern»
			
			«FOR block : action.body.blocks»
				«block.doSwitch»
			«ENDFOR»
			
			«action.outputPattern.outputPattern»
			
			fsm_state_«actor.simpleName» = «actor.simpleName»_state_«trans.target.name»;
			
			«FOR instLoad : loadPeeks.get(action)»
				«instLoad.target.variable.name»_done = 0;
			«ENDFOR»
			
			#ifdef PXML
			printf("<iterand actor=\"«actor.simpleName»\" action=\"«action.name»\" repetitions=\"1\"/>\n");
			#endif
			#ifdef PNAME
			printf("«actor.simpleName».«action.name»();\n");
			#endif
			#ifdef PFSM
			printf("state = «actor.simpleName»_state_«trans.target.name»;\n");
			#endif
			«IF ! actor.stateVars.nullOrEmpty»
				#ifdef PSTATE
				printf("«actor.stateVars.join(";", ['''«name»«type.dimensions.join("",["[0]"])»=%d'''])»\n\n", «actor.stateVars.join(", ", ['''«name»«type.dimensions.join(",",["[0]"])»'''])»);
				#endif
			«ENDIF»
		}
	'''
	
	def guardFSM(Action action, EObject object) {
		if ( ! guards.get(action).nullOrEmpty) {
			'''«guards.get(action).join(" && ", [doSwitch])»«priority.get(object).join(" && ", " && ", "", [priorities])»«action.peekDone»'''
		} else {
			'''skip«priority.get(object).join(" && ", " && ", "", [priorities])»«action.peekDone»'''
		}
	}
	
	def priorities(Action action) {
		if(guards.containsKey(action)) {
			'''!(«guards.get(action).join(" && ", [doSwitch])»)«action.peekDone»'''
		}
	}
	
	def peekDone(Action action)
		'''«loadPeeks.get(action).join(" && ", " && ", "", ['''«target.variable.name»_done == 1'''])»'''

	def inputChannelCheck(Pattern pattern) {
	'''
		«FOR port : pattern.ports»
		&& len(chan_«actor.simpleName»_«port.name»)>=«pattern.getNumTokens(port)»
		«ENDFOR»
	'''	
	}

	def outputChannelCheck(Pattern pattern) {
	'''
		«FOR port : pattern.ports»
		&& chan_«actor.simpleName»_«port.name»_SIZE - len(chan_«actor.simpleName»_«port.name»)>=«pattern.getNumTokens(port)»
		«ENDFOR»
	'''
	}
	
	def inputPattern(Pattern pattern) '''
		«FOR variable : pattern.variables»
			promela_io_index=0;
			do
			:: promela_io_index < «variable.type.dimensions.head» -> 
				chan_«actor.simpleName»_«pattern.varToPortMap.get(variable).name»?«variable.name»[promela_io_index];
				promela_io_index = promela_io_index + 1;
			:: else -> break;
			od;
		«ENDFOR»
	'''

	def outputPattern(Pattern pattern) '''
		«FOR variable : pattern.variables»
			promela_io_index=0;
			do
			:: promela_io_index < «variable.type.dimensions.head» -> 
				chan_«actor.simpleName»_«pattern.varToPortMap.get(variable).name»!«variable.name»[promela_io_index];
				promela_io_index = promela_io_index + 1;
			:: else -> break;
			od;
		«ENDFOR»
	'''

	def printScheduler(Action action) '''
		:: /* «action.name» */ atomic {
			«action.guard»
			«action.inputPattern.inputChannelCheck»
			«action.outputPattern.outputChannelCheck»
			->
			
			/* Temp variables*/
			int promela_io_index; // used for reading/writing multiple tokens
			«FOR local : action.body.locals»
				«local.declare»;
			«ENDFOR»
			
			«action.inputPattern.inputPattern»
			
			«FOR block : action.body.blocks»
				«block.doSwitch»
			«ENDFOR»
			
			«action.outputPattern.outputPattern»
			
			#ifdef PNAME
			printf("«actor.simpleName».«action.name»();\n");
			#endif
			«IF ! actor.stateVars.nullOrEmpty»
				#ifdef PSTATE
				printf("«actor.stateVars.join(";", ['''«name»«type.dimensions.join("",["[0]"])»=%d'''])»\n\n", «actor.stateVars.join(",", ['''«name»«type.dimensions.join("",["[0]"])»'''])»);
				#endif
			«ENDIF»
		}	
	'''
	
	def guard(Action action) {
		guardFSM(action, action)
	}

	def declareStateVar(Var variable) '''
		«IF variable.initialized»
			«IF ! variable.assignable»
				«IF ! variable.type.list»
					«variable.type.doSwitch» «variable.name» = «variable.initialValue.doSwitch.wrap»;
				«ELSE»
					«variable.declare» = «variable.initialValue.doSwitch.wrap»;
				«ENDIF»
			«ELSE»
				«variable.declare» = «variable.initialValue.doSwitch.wrap»;
			«ENDIF»
		«ELSE»
			«variable.declare»=0;
		«ENDIF»
	'''

	override declare(Var variable) {
		variable.declare("")
	}

	def declare(Var variable, String nameSuffix) {
		'''«variable.type.doSwitch» «variable.indexedName»«nameSuffix»«variable.type.dimensionsExpr.printArrayIndexes»'''
	}
	
	override caseInstAssign(InstAssign assign) '''
		«assign.target.variable.indexedName» = «assign.value.doSwitch»;
	'''

	override caseInstCall(InstCall call) {
		//ERROR this function call must be removed, in this case we give a 1, it is OK if it is "data"
		if(call.print) '''
			printf(«call.arguments.printfArgs.join(", ")»);
		'''
		else '''
			«IF call.target != null»«call.target.variable.name» = «ENDIF»1;
		'''
	}
	
	override caseInstLoad(InstLoad load) '''
		«load.target.variable.indexedName» = «load.source.variable.indexedName»«load.indexes.printArrayIndexes»;
	'''
	
	override caseInstStore(InstStore store) '''
		«store.target.variable.name»«store.indexes.printArrayIndexes» = «store.value.doSwitch»;
	'''
	
	override caseInstReturn(InstReturn returnInstr) ''''''
	
	override caseBlockBasic(BlockBasic block) '''
		«FOR instr : block.instructions»
			«instr.doSwitch»
		«ENDFOR»
	'''
	
	override caseBlockIf(BlockIf blockIf) '''
		if 
		:: («blockIf.condition.doSwitch») -> skip;
			«FOR block : blockIf.thenBlocks»
				«block.doSwitch»
			«ENDFOR»
		«IF ! blockIf.elseBlocks.nullOrEmpty»
		:: else -> skip;
			«FOR block : blockIf.elseBlocks»
				«block.doSwitch»
			«ENDFOR»
		«ENDIF»
		fi;
			«blockIf.joinBlock.doSwitch»
	'''
	
	override caseBlockWhile(BlockWhile blockWhile) '''
		do 
		:: «blockWhile.condition.doSwitch» -> skip;
			«FOR block : blockWhile.blocks»
				«block.doSwitch»
			«ENDFOR»
		:: else -> break;
		od;
		
		«blockWhile.joinBlock.doSwitch»
	'''
	
}