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
package net.sf.orcc.backends.java

import java.io.File
import java.util.Map
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile
import net.sf.orcc.ir.ExprInt
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.util.util.EcoreHelper
import org.eclipse.emf.common.util.EList

import static net.sf.orcc.OrccLaunchConstants.*

/*
 * Compile Top_network Java source code 
 *  
 * @author Antoine Lorence
 * 
 */
class ActorPrinter extends JavaTemplate {
	
	Actor actor
	
	new(Actor actor, Map<String, Object> options){
		this.actor = actor
	}
	
		
	def print(String targetFolder) {
		
		val content = getActorFileContent
		val file = new File(targetFolder + File::separator + actor.simpleName + ".java")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def getActorFileContent() '''
		/**
		 * Generated from "«actor.fileName»"
		 */
		«IF !actor.getPackage.empty»
			package «actor.getPackage»;
		«ENDIF»
		
		import net.sf.orcc.runtime.Fifo;
		import net.sf.orcc.runtime.actors.IActor;
		«IF actor.procs.exists([proc | proc.native])»
			import net.sf.orcc.runtime.NativeProcedure;
		«ENDIF»
		
		public class «actor.simpleName» implements IActor {
			// Input FIFOs
			«FOR port : actor.inputs»
				private Fifo<«port.type.doSwitch»> fifo_«port.name»;
			«ENDFOR»
			// Output FIFOs
			«FOR port : actor.outputs»
				private Fifo<«port.type.doSwitch»> fifo_«port.name»;
			«ENDFOR»
			
			// Actor's parameters
			«FOR variable : actor.parameters»
				«variable.declareVariable»
			«ENDFOR»
			// Actor's state variables
			«FOR stateVar : actor.stateVars»
				«stateVar.declareVariable»
			«ENDFOR»
			
			«IF actor.parameters.size > 0»
				//Constructor
				public «actor.simpleName»(«FOR param : actor.parameters SEPARATOR ", "»«param.type.doSwitch» «param.name»«ENDFOR») {
					«FOR param : actor.parameters»
						this.«param.name» = «param.name»;
					«ENDFOR»
				}
			«ENDIF»
			
			// Functions/procedures
			«FOR proc : actor.procs»
				«proc.doSwitch»
			«ENDFOR»
			
			/***********
			 * Actions
			 **********/
			«FOR action : actor.actions»
				«action.printAction»
			«ENDFOR»
			
			«IF !actor.initializes.empty»
				«FOR init : actor.initializes»
					«init.printAction»
				«ENDFOR»
			«ENDIF»
			
			@Override
			public void initialize() {
				«IF !actor.initializes.empty»
					«FOR initAction : actor.initializes»
						«initAction.name»();
					«ENDFOR»
				«ENDIF»
			}
			
			@Override
			@SuppressWarnings("unchecked")
			public <T> void setFifo(String portName, Fifo<T> fifo) {
				«FOR port : actor.inputs + actor.outputs»if ("«port.name»".equals(portName)) {
					fifo_«port.name» = (Fifo<«port.type.doSwitch»>) fifo;
				} else «ENDFOR» {
					String msg = "unknown port \"" + portName + "\"";
					throw new IllegalArgumentException(msg);
				}
			}
			
			«IF actor.hasFsm»
				«printFSMScheduler»
			«ELSE»
				«printSimpleScheduler»
			«ENDIF»
		}
	'''
	
	/******************************************
	 * 
	 * Procedures / actions / methods
	 *
	 *****************************************/
	override caseProcedure(Procedure procedure) '''
		«IF !procedure.native»
		private «procedure.returnType.doSwitch» «procedure.name»(«FOR param : procedure.parameters SEPARATOR ", "»«param.variable.type.doSwitch» «param.variable.name»«ENDFOR») {
			«FOR local : procedure.locals»«local.declareVariable»«ENDFOR»
			
			«FOR block : procedure.blocks»«block.doSwitch»«ENDFOR»
		}
		«ENDIF»
	'''
	
	def printAction(Action action) '''
		
		public «action.scheduler.returnType.doSwitch» «action.scheduler.name»() {
			«FOR localVar : action.scheduler.locals»
				«localVar.declareVariable»
			«ENDFOR»
			«FOR peekPort : action.peekPattern.ports»
				«action.peekPattern.portToVarMap.get(peekPort).type.doSwitch» «action.peekPattern.portToVarMap.get(peekPort).name» = new «(action.peekPattern.portToVarMap.get(peekPort).type as TypeList).innermostType.doSwitch»«(action.peekPattern.portToVarMap.get(peekPort).type as TypeList).dimensions»;
				for(int fifo_index = 0 ; fifo_index < «action.peekPattern.numTokensMap.get(peekPort)» ; ++fifo_index) {
					«action.peekPattern.portToVarMap.get(peekPort).name»[fifo_index] = fifo_«peekPort.name».peek(fifo_index);
				}
			«ENDFOR»
			«FOR block : action.scheduler.blocks»
				«block.doSwitch»
			«ENDFOR»
		}
		
		private void «action.name»() {
			//Locals
			«FOR local : action.body.locals»
				«local.declareVariable»
			«ENDFOR»
			//Input ports
			«FOR inPort : action.inputPattern.ports»
				«action.inputPattern.portToVarMap.get(inPort).declareVariable»
			«ENDFOR»
			//Output ports
			«FOR outPort : action.outputPattern.ports»
				«action.outputPattern.portToVarMap.get(outPort).declareVariable»
			«ENDFOR»			
			
			«FOR inPort : action.inputPattern.ports»
				for(int fifo_index = 0 ; fifo_index < «action.inputPattern.numTokensMap.get(inPort)» ; ++fifo_index) {
					«action.inputPattern.portToVarMap.get(inPort).name»[fifo_index] = fifo_«inPort.name».read();
				}
			«ENDFOR»
			«FOR block : action.body.blocks»
				«block.doSwitch»
			«ENDFOR»
			«FOR outPort : action.outputPattern.ports»
				for(int fifo_index = 0 ; fifo_index < «action.outputPattern.numTokensMap.get(outPort)» ; ++fifo_index) {
					fifo_«outPort.name».write(«action.outputPattern.portToVarMap.get(outPort).name»[fifo_index]);
				}
			«ENDFOR»
		}
	'''
	
	/******************************************
	 * 
	 * Scheduling / FSM
	 *
	 *****************************************/
	def printSimpleScheduler() '''
		@Override
		public int schedule() {
			boolean res;
			int i = 0;
			do {
				res = false;
				«actor.actions.join(" else ", [actionFireingTest])»
				i += res ? 1 : 0;
			} while(res);
			return i;
		}
	'''
	
	def printFSMScheduler() '''
		private enum States {
			«FOR state : actor.fsm.states SEPARATOR ","»
				«state.name»
			«ENDFOR»
		};
		
		private States _FSM_state = States.«actor.fsm.initialState.name»;
		
		«IF ! actor.actionsOutsideFsm.empty»
			private boolean outside_FSM_scheduler() {
				boolean res = false;
				
				«actor.actionsOutsideFsm.join(" else ", [actionFireingTest])»
				return res;
			}
			
		«ENDIF»
		«FOR state : actor.fsm.states»
			private boolean stateScheduler_«state.name»() {
				boolean res = false;
				«FOR edge : state.outgoing SEPARATOR " else "»
					«(edge as Transition).schedulingTestState»
				«ENDFOR»
				return res;
			}
		«ENDFOR»
		
		@Override
		public int schedule() {
			int i = 0;
			int total;
			do {
				total = i;
				«IF actor.actionsOutsideFsm.empty»
					«actor.fsm.states.fsmSwitch»
				«ELSE»
					if (outside_FSM_scheduler()) {
						i++;
					} else {
						«actor.fsm.states.fsmSwitch»
					}
				«ENDIF»
			} while (total < i);
			return i;
		}
	'''
	
	/**
	 * Print the switch structure of a FSM.
	 */
	def fsmSwitch(EList<State> list) '''
		switch (_FSM_state) {
		«FOR state : actor.fsm.states»
			case «state.name» :
				if(stateScheduler_«state.name»()) ++i;
			break;
		«ENDFOR»
			default: System.out.println("unknown state: %s\n" + _FSM_state); break;
		}
	'''
	
	/**
	 * Print an if structure which test if an action is fireable, fire
	 * it and update current state in FSM.
	 */
	def schedulingTestState(Transition transition) '''
		if («transition.action.inputSchedulingTest») {
			«IF ! transition.action.outputPattern.empty»
				if («transition.action.outputSchedulingTest») {
					«transition.action»();
					_FSM_state = States.«transition.target.name»;
					res = true;
				}
			«ELSE»
				_FSM_state = States.«transition.target.name»;
				«transition.action»();
				res = true;
			«ENDIF»
		}
	'''
	
	/**
	 * Print a if structure which test if an action is fireable and fire it.
	 */
	def actionFireingTest(Action action) '''
		if(«action.inputSchedulingTest»){
			«IF !action.outputPattern.empty»
				if(«action.outputSchedulingTest»){
					«action.name»();
					res = true;
				}
			«ELSE»
				«action.name»();
				res = true;
			«ENDIF»
		}'''
	
	
	/**
	 * Print the list of inputs controls (fifo_x.hasTokens(n)) separated by && and
	 * followed by action firing test.
	 * Only one line printed.
	 */
	def inputSchedulingTest(Action action) {
		if(action.inputPattern.ports.empty) {
			return '''«action.scheduler.name»()'''
		} else {
			return action.inputPattern.ports.join(
				"", // Before
				" && ", // Separator
				''' && «action.scheduler.name»()''', // After
				[ '''fifo_«it.name».hasTokens(«action.inputPattern.numTokensMap.get(it)»)'''] // Function
			)
		}
	}
	
	/**
	 * Print the list of outputs controls (fifo_x.hasRooms(n)) separated by &&.
	 * Only one line printed.
	 */
	def outputSchedulingTest(Action action) {
		action.outputPattern.ports.join(
			" && ", // Separator
			['''fifo_«it.name».hasRoom(«action.outputPattern.numTokensMap.get(it)»)''' ] // Function
		)
	}
	
	/******************************************
	 * 
	 * Blocks
	 *
	 *****************************************/
	override caseBlockIf(BlockIf block)'''
		if(«block.condition.doSwitch»){
			«FOR thenBlock : block.thenBlocks»
				«thenBlock.doSwitch»
			«ENDFOR»
		} «IF block.elseRequired» 
			else {
				«FOR elseBlock : block.elseBlocks»
					«elseBlock.doSwitch»
				«ENDFOR»
			}
		«ENDIF»
	'''

	override caseBlockWhile(BlockWhile blockWhile)'''
		while(«blockWhile.condition.doSwitch») {
			«FOR loopContent : blockWhile.blocks»
				«loopContent.doSwitch»
			«ENDFOR»
		}
	'''
	
	override caseBlockBasic(BlockBasic block) '''
		«FOR instr : block.instructions»
			«instr.doSwitch»
		«ENDFOR»
	'''

	/******************************************
	 * 
	 * Instructions
	 *
	 *****************************************/
	override caseInstAssign(InstAssign inst) {
		if(isCastNeeded(inst.target.variable.type, inst.value.type)) {
			val castType =
				if (inst.target.variable.type.list) (inst.target.variable.type as TypeList).innermostType.doSwitch
				else inst.target.variable.type.doSwitch
			'''
			«inst.target.variable.indexedName» = («castType») («inst.value.doSwitch»);
			'''
		} else {
			'''
			«inst.target.variable.indexedName» = «inst.value.doSwitch»;
			'''
		}
	}
	
	override caseInstLoad(InstLoad load)'''
		«load.target.variable.name» = «load.source.variable.name»«load.indexes.printArrayIndexes»;
	'''
	
	override caseInstStore(InstStore store){
		val sourceExprValue =
			if(isCastNeeded(store.target.variable.type, store.value.type)) {
				val castType =
					if (store.target.variable.type.list)
						(store.target.variable.type as TypeList).innermostType.doSwitch
					else
						store.target.variable.type.doSwitch
				'''(«castType») («store.value.doSwitch»)'''
			} else {
				'''«store.value.doSwitch»'''
			}
		'''
		«store.target.variable.name»«store.indexes.printArrayIndexes» = «sourceExprValue»;
		'''
	}

	override caseInstCall(InstCall call) {
		val native = if (call.procedure.native) "NativeProcedure."
		
		val target = if (call.target != null) {
			val cast = if (call.target.variable.type.sizeInBits < call.procedure.returnType.sizeInBits) {
				'''(«call.target.variable.type.doSwitch») '''
			}
			'''«call.target.variable.indexedName» = «cast»'''
		}
		'''
		«IF call.print»
			System.out.println(«FOR param : call.getArguments SEPARATOR " + "»«printParameter(param)»«ENDFOR»);
		«ELSE»
			«target»«native»«call.procedure.name»(«printParameters(call)»);
		«ENDIF»
		'''
	}
	
	
	override caseInstReturn(InstReturn ret) {
		if(ret.value != null) {
			val proc = EcoreHelper::getContainerOfType(ret, typeof(Procedure))
			if(proc?.returnType.sizeInBits < ret.value.type.sizeInBits) {
				'''
				return («proc.returnType.doSwitch»)(«ret.value.doSwitch»);
				'''			
			} else {
				'''
				return «ret.value.doSwitch»;
				'''
			}
		}
	}
	
	/******************************************
	 * 
	 * Expressions
	 *
	 *****************************************/
	
	override caseExprInt(ExprInt object) {
		val longVal = object.value.longValue
		if(longVal < Integer::MIN_VALUE || longVal > Integer::MAX_VALUE) {
			String::valueOf(object.value) + "L"
		}else{
			String::valueOf(object.value)
		}
	}
	
}
