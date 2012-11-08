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
 package net.sf.orcc.backends.c

import java.util.HashMap
import java.util.LinkedList
import java.util.List
import java.util.Map
import net.sf.orcc.df.Action
import net.sf.orcc.df.Connection
import net.sf.orcc.df.DfFactory
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.df.State
import net.sf.orcc.df.Transition
import net.sf.orcc.ir.Arg
import net.sf.orcc.ir.ArgByRef
import net.sf.orcc.ir.ArgByVal
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile
import net.sf.orcc.ir.ExprString
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.OrccLogger

/*
 * Compile Instance c source code
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends CTemplate {
	
	val Instance instance
	val int fifoSize;
	
	var boolean geneticAlgo = false
	
	var boolean newSchedul = false
	var boolean ringTopology = false
	
	var boolean enableTrace = false
	var int threadsNb = 1;
	
	val Pattern inputPattern = DfFactory::eINSTANCE.createPattern
	val Map<State, Pattern> transitionPattern = new HashMap<State, Pattern>
	
	var Action currentAction;
	
	/**
	 * Default constructor, used only by another backend (when subclass)
	 */
	new() {
		instance = null
		fifoSize = 0
	}
	
	new(Instance instance, Map<String, Object> options) {
		
		if ( ! instance.isActor) {
			OrccLogger::severeln("Instance " + instance.name + " is not an Actor's instance")
		}
		
		this.instance = instance
		
		if (options.containsKey("fifoSize")) {
			fifoSize = options.get("fifoSize") as Integer
		} else {
			fifoSize = 512
		}
		
		if (options.containsKey("useGeneticAlgorithm")) {
			geneticAlgo = options.get("useGeneticAlgorithm") as Boolean
			if (options.containsKey("threadsNb")) {
				threadsNb = options.get("threadsNb") as Integer
			} else {
				OrccLogger::warnln("Genetic algorithm options has been checked, but threadsNb option is not set")
			}
		}
		if (options.containsKey("newScheduler")) {
			newSchedul = options.get("newScheduler") as Boolean
		}
		if (options.containsKey("ringTopology")) {
			ringTopology = options.get("ringTopology") as Boolean
		}
		if (options.containsKey("enableTrace")) {
			enableTrace = options.get("enableTrace") as Boolean
		}
		
		buildInputPattern
		buildTransitionPattern
	}
	
	def getInstanceFileContent() '''
		// Source file is "«instance.actor.file»"
		
		#include <stdio.h>
		#include <stdlib.h>
		
		#include "orcc_types.h"
		#include "orcc_fifo.h"
		#include "orcc_util.h"
		#include "orcc_scheduler.h"
		
		#define SIZE «fifoSize»
		«instance.printAttributes»
		
		«IF newSchedul»
			#define RING_TOPOLOGY «IF ringTopology»1«ELSE»0«ENDIF»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Instance
		extern struct actor_s «instance.name»;
		
		////////////////////////////////////////////////////////////////////////////////
		// Input FIFOs
		«FOR port : instance.actor.inputs»
			«if (instance.incomingPortMap.get(port) != null) "extern "»struct fifo_«port.type.doSwitch»_s *«port.fullName»;
		«ENDFOR»
		«FOR port : instance.actor.inputs»
			static unsigned int index_«port.name»;
			static unsigned int numTokens_«port.name»;
			#define NUM_READERS_«port.name» «port.getNumReaders»
			#define SIZE_«port.name» «instance.incomingPortMap.get(port).sizeOrDefaultSize»
			#define tokens_«port.name» «port.fullName»->contents
			
		«ENDFOR»
		«IF enableTrace»
			«FOR port : instance.actor.inputs»
				FILE *file_«port.name»;
			«ENDFOR»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Predecessors
		«FOR port : instance.actor.inputs»
			«IF instance.incomingPortMap.get(port) != null»
				extern struct actor_s «(instance.incomingPortMap.get(port).source as Instance).name»;
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Output FIFOs
		«FOR port : instance.actor.outputs.filter[! native]»
			extern struct fifo_«port.type.doSwitch»_s *«port.fullName»;
		«ENDFOR»
		«FOR port : instance.actor.outputs.filter[! native]»
			static unsigned int index_«port.name»;
			static unsigned int numFree_«port.name»;
			#define NUM_READERS_«port.name» «instance.outgoingPortMap.get(port).size»
			#define SIZE_«port.name» «instance.outgoingPortMap.get(port).get(0).sizeOrDefaultSize»
			#define tokens_«port.name» «port.fullName»->contents
			
		«ENDFOR»
		«IF enableTrace»
			«FOR port : instance.actor.outputs»
				FILE *file_«port.name»;
			«ENDFOR»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Successors
		«FOR port : instance.actor.outputs»
			«FOR successor : instance.outgoingPortMap.get(port)»
				extern struct actor_s «(successor.target as Instance).name»;
			«ENDFOR»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Input FIFOs Id
		«FOR port : instance.actor.inputs»
			static unsigned int fifo_«port.fullName»_id;
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Parameter values of the instance
		«instanceArgs»
		
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
			
			static char *stateNames[] = {
				«FOR state : instance.actor.fsm.states SEPARATOR ","»
					"«state.name»"
				«ENDFOR»
			};
			
			static enum states _FSM_state;
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
		// Token functions
		«FOR port : instance.actor.inputs»
			«port.readTokensFunctions»
		«ENDFOR»
		
		«FOR port : instance.actor.outputs.filter[!native]»
			«port.writeTokensFunctions»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«initializeFunction»
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«IF instance.actor.hasFsm»
			«printFsm»
		«ELSE»
			void «instance.name»_scheduler(struct schedinfo_s *si) {
				int i = 0;
				si->ports = 0;
			
				«printCallTokensFunctions»
				«IF enableTrace»
					«printOpenFiles»
				«ENDIF»
				
				«instance.actor.actionsOutsideFsm.printActionLoop»
				
			finished:
				«IF enableTrace»
					«printCloseFiles»
				«ENDIF»
				
				«FOR port : instance.actor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : instance.actor.outputs.filter[!native]»
					write_end_«port.name»();
				«ENDFOR»
			}
		«ENDIF»
	'''
	
	/******************************************
	 * 
	 * FSM
	 *
	 *****************************************/
	def printFsm() '''
		«IF ! instance.actor.actionsOutsideFsm.empty»
		void «instance.name»_outside_FSM_scheduler(struct schedinfo_s *si) {
			int i = 0;
			«instance.actor.actionsOutsideFsm.printActionLoop»
		finished:
			// no read_end/write_end here!
			return;
		}
		«ENDIF»
		
		void «instance.name»_scheduler(struct schedinfo_s *si) {
			int i = 0;
		
			«printCallTokensFunctions»
			«IF enableTrace»
				«printOpenFiles»
			«ENDIF»
		
			// jump to FSM state 
			switch (_FSM_state) {
			«FOR state : instance.actor.fsm.states»
				case my_state_«state.name»:
					goto l_«state.name»;
			«ENDFOR»
			default:
				printf("unknown state in «instance.name».c : %s\n", stateNames[_FSM_state]);
				wait_for_key();
				exit(1);
			}
		
			// FSM transitions
			«FOR state : instance.actor.fsm.states»
		«state.printTransition»
			«ENDFOR»
		finished:
			«IF enableTrace»
				«printCloseFiles»
			«ENDIF»
			«FOR port : instance.actor.inputs»
				read_end_«port.name»();
			«ENDFOR»
			«FOR port : instance.actor.outputs.filter[!native]»
				write_end_«port.name»();
			«ENDFOR»
		}
	'''
	
	def printTransition(State state) '''
	l_«state.name»:
		«IF ! instance.actor.actionsOutsideFsm.empty»
			«instance.name»_outside_FSM_scheduler(si);
			i += si->num_firings;
		«ENDIF»
		«IF state.outgoing.empty»
			printf("Stuck in state "«state.name»" in the instance «instance.name»\n");
			wait_for_key();
			exit(1);
		«ELSE»
			«schedulingState(state, state.outgoing.map[it as Transition])»
		«ENDIF»
	'''
	
	def schedulingState(State state, Iterable<Transition> transitions) '''
		«IF ! transitions.empty»
			«actionTestState(state, transitions)»
		«ELSE»
			«transitionPattern.get(state).printTransitionPattern»
			_FSM_state = my_state_«state.name»;
			goto finished;
		«ENDIF»
	'''
	
	def printTransitionPattern(Pattern pattern) '''
		«IF newSchedul»
			«FOR port : pattern.ports»
				«printTransitionPatternPort(port, pattern)»
			«ENDFOR»
		«ENDIF»
		si->num_firings = i;
		si->reason = starved;
	'''
	
	def printTransitionPatternPort(Port port, Pattern pattern) '''
		if (numTokens_«port.name» - index_«port.name» < «pattern.numTokensMap.get(port)») {
			if( ! «instance.name».sched->round_robin || i > 0) {
				«IF instance.incomingPortMap.containsKey(port)»
					sched_add_schedulable(«instance.name».sched, &«(instance.incomingPortMap.get(port).source as Instance).name», RING_TOPOLOGY);
				«ENDIF»
			}
		}
	'''
	
	def actionTestState(State srcState, Iterable<Transition> transitions) '''
		if («transitions.head.action.inputPattern.checkInputPattern»isSchedulable_«transitions.head.action.name»()) {
			«IF transitions.head.action.outputPattern != null»
				«transitions.head.action.outputPattern.printOutputPattern»
					_FSM_state = my_state_«srcState.name»;
					si->num_firings = i;
					si->reason = full;
					goto finished;
				}
			«ENDIF»
			«transitions.head.action.body.name»();
			i++;
			goto l_«transitions.head.target.name»;
		} else {
			«schedulingState(srcState, transitions.tail)»
		}
	'''

	
	def printCallTokensFunctions() '''
		«FOR port : instance.actor.inputs»
			read_«port.name»();
		«ENDFOR»
		«FOR port : instance.actor.outputs.filter[!native]»
			write_«port.name»();
		«ENDFOR»
	'''
	
	def initializeFunction() '''
		«IF ! instance.actor.initializes.empty»
			«FOR init : instance.actor.initializes»
				«init.print»
			«ENDFOR»
			
			static void initialize(struct schedinfo_s *si) {
				int i = 0;
				«instance.actor.initializes.printActions»
				
			finished:
				// no read_end/write_end here!
				return;
			}
			
		«ENDIF»
		void «instance.name»_initialize(«instance.actor.inputs.join(", ", ['''unsigned int fifo_«name»_id'''])») {
			«IF ! instance.actor.initializes.empty»
				struct schedinfo_s si;
				si.num_firings = 0;
			«ENDIF»
			«IF instance.actor.hasFsm»
				
				/* Set initial state to current FSM state */
				_FSM_state = my_state_«instance.actor.fsm.initialState.name»;
			«ENDIF»
			
			/* Set initial value to global variable */
			«FOR variable : instance.actor.stateVars»
				«variable.stateVarInit»
			«ENDFOR»
			
			/* Initialize input FIFOs id */
			«FOR port : instance.actor.inputs»
				«port.initializeFifoId»
			«ENDFOR»
			«IF ! instance.actor.initializes.empty»
				
				/* Launch CAL initialize procedure */
				initialize(&si);
			«ENDIF»
		}
	'''
	
	def initializeFifoId(Port port) '''
		«IF instance.incomingPortMap.get(port) != null»
			fifo_«port.fullName»_id = fifo_«port.name»_id;
		«ELSE»
			«OrccLogger::warnln("["+instance.name+"] Input port "+port.fullName+" not connected.")»
		«ENDIF»
	'''

	def stateVarInit(Var variable) '''
		«IF variable.assignable && variable.initialized»
			«IF ! variable.type.list»
				«variable.name» = «variable.initialValue.doSwitch»;
			«ELSE»
				memcpy(«variable.name», «variable.name»_backup, sizeof(«variable.name»_backup));
			«ENDIF»
		«ENDIF»
	'''
	
	def printActionLoop(List<Action> actions) '''
		while (1) {
			«actions.printActions»
		}
	'''
	
	def printActions(Iterable<Action> actions) '''
		«IF !actions.empty»
			«actionTest(actions.head, actions.tail)»
		«ELSE»
			«printTransitionPattern(inputPattern)»
			goto finished;
		«ENDIF»
	'''
	
	def actionTest(Action action, Iterable<Action> others) '''
		if («action.inputPattern.checkInputPattern»isSchedulable_«action.name»()) {
			«IF action.outputPattern != null»
				«action.outputPattern.printOutputPattern»
					si->num_firings = i;
					si->reason = full;
					goto finished;
				}
			«ENDIF»
			«action.body.name»();
			i++;
		} else {
			«others.printActions»
		}
	'''
	
	def printOutputPattern(Pattern pattern) '''
		int stop = 0;
		«FOR port : pattern.ports»
			«printOutputPatternsPort(pattern, port)»
		«ENDFOR»
		if (stop != 0) {
	'''
	
	def printOutputPatternsPort(Pattern pattern, Port port) {
		var i = -1
		'''
			«FOR successor : instance.outgoingPortMap.get(port)»
				«printOutputPatternPort(pattern, port, successor, i = i + 1)»
			«ENDFOR»
		'''
	}
	
	def printOutputPatternPort(Pattern pattern, Port port, Connection successor, int id) '''
		if («pattern.numTokensMap.get(port)» > SIZE_«port.name» - index_«port.name» + «port.fullName»->read_inds[«id»]) {
			stop = 1;
			«IF newSchedul»
				if( ! «instance.name».sched->round_robin || i > 0) {
					sched_add_schedulable(«instance.name».sched, &«(successor.target as Instance).name», RING_TOPOLOGY);
				}
			«ENDIF»
		}
	'''

	def checkInputPattern(Pattern pattern)
		'''«FOR port : pattern.ports»numTokens_«port.name» - index_«port.name» >= «pattern.numTokensMap.get(port)» && «ENDFOR»'''
	
	def writeTokensFunctions(Port port) '''
		static void write_«port.name»() {
			index_«port.name» = «port.fullName»->write_ind;
			numFree_«port.name» = index_«port.name» + fifo_«port.type.doSwitch»_get_room(«port.fullName», NUM_READERS_«port.name»);
		}
		
		static void write_end_«port.name»() {
			«port.fullName»->write_ind = index_«port.name»;
		}
	'''

	def readTokensFunctions(Port port) '''
		static void read_«port.name»() {
			«IF instance.incomingPortMap.containsKey(port)»
				index_«port.name» = «port.fullName»->read_inds[fifo_«port.fullName»_id];
				numTokens_«port.name» = index_«port.name» + fifo_«port.type.doSwitch»_get_num_tokens(«port.fullName», fifo_«port.fullName»_id);
			«ELSE»
				/* Input port «port.fullName» not connected */
				index_«port.name» = 0;
				numTokens_«port.name» = 0;
			«ENDIF»
		}
		
		static void read_end_«port.name»() {
			«IF instance.incomingPortMap.containsKey(port)»
				«port.fullName»->read_inds[fifo_«port.fullName»_id] = index_«port.name»;
			«ELSE»
				/* Input port «port.fullName» not connected */
			«ENDIF»
		}
	'''

	def print(Action action) {
		currentAction = action
		val output = '''
			static void «action.body.name»() {
				«FOR variable : action.body.locals»
					«variable.declare»;
				«ENDFOR»
			
				«FOR block : action.body.blocks»
					«block.doSwitch»
				«ENDFOR»
			
				«FOR port : action.inputPattern.ports»
					«IF enableTrace»
					{
						int i;
						for (i = 0; i < «action.inputPattern.numTokensMap.get(port)»; i++) {
							fprintf(file_«port.name», "%«port.type.printfFormat»\n", tokens_«port.name»[(index_«port.name» + i) % SIZE_«port.name»]);
						}
					}
					«ENDIF»
					index_«port.name» += «action.inputPattern.numTokensMap.get(port)»;
				«ENDFOR»
				
				«FOR port : action.outputPattern.ports»
					«IF enableTrace»
						{
							int i;
							for (i = 0; i < «action.outputPattern.numTokensMap.get(port)»; i++) {
								fprintf(file_«port.name», "%«port.type.printfFormat»\n", tokens_«port.name»[(index_«port.name» + i) % SIZE_«port.name»]);
							}
						}
					«ENDIF»
					index_«port.name» += «action.outputPattern.numTokensMap.get(port)»;
				«ENDFOR»
			}
			
			«action.scheduler.print»
			
		'''
		currentAction = null
		return output
	}
	
	def print(Procedure proc) '''
		«proc.printAttributes»
		static «proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ", [variable.declare])») {
			«FOR variable : proc.locals»
				«variable.declare»;
			«ENDFOR»
		
			«FOR block : proc.blocks»
				«block.doSwitch»
			«ENDFOR»
		}
	'''
	
	// TODO : simplify this :
	def declareStateVar(Var variable) '''
		«variable.printAttributes»
		«IF variable.initialized»
			«IF ! variable.assignable»
				«IF ! variable.type.list»
					#define «variable.name» «variable.initialValue.doSwitch»
				«ELSE»
					static const «variable.declare» = «variable.initialValue.doSwitch»;
				«ENDIF»
			«ELSE»
				«IF variable.type.list»
					static «variable.type.doSwitch» «variable.name»_backup«variable.type.dimensionsExpr.printArrayIndexes» = «variable.initialValue.doSwitch»;
				«ENDIF»
				static «variable.declare»;
			«ENDIF»
		«ELSE»
			static «variable.declare»;
		«ENDIF»
	'''
	
	def declare(Var variable)
		'''«variable.type.doSwitch» «variable.indexedName»«variable.type.dimensionsExpr.printArrayIndexes»'''

	
	def printAttributes(Attributable object) '''
		«IF false && ! object.attributes.empty»
			//Attributes for «object.toString» :
			«FOR attr : object.attributes»
				//«attr.name» = «attr.objectValue»
			«ENDFOR»
		«ENDIF»
	'''

	def instanceArgs() '''
		«FOR arg : instance.arguments»
			«IF arg.value.exprList»
				static «IF (arg.value.type as TypeList).innermostType.uint»unsigned «ENDIF»int «arg.variable.name»«arg.value.type.dimensionsExpr.printArrayIndexes» = «arg.value.doSwitch»;
			«ELSE»
				#define «arg.variable.name» «arg.value.doSwitch»
			«ENDIF»
		«ENDFOR»
	'''

	def fullName(Port port)
		'''«instance.name»_«port.name»'''
	
	def sizeOrDefaultSize(Connection conn) {
		if(conn == null || conn.size == null) "SIZE"
		else conn.size
	}
	
	def getNumReaders(Port port) {
		if (instance.incomingPortMap.get(port) == null) {
			'''0'''
		} else {
			val predecessor = instance.incomingPortMap.get(port).source as Instance
			val predecessorPort = instance.incomingPortMap.get(port).sourcePort
			'''«predecessor.outgoingPortMap.get(predecessorPort).size»'''
		}
	}
	
	def printOpenFiles() '''
		«FOR port : instance.actor.inputs + instance.actor.outputs»
			file_«port.name» = fopen("«port.fullName».txt", "a");
		«ENDFOR»
	'''
	
	def printCloseFiles() '''
		«FOR port : instance.actor.inputs + instance.actor.outputs»
			fclose(file_«port.name»);
		«ENDFOR»
	'''
	


	/******************************************
	 * 
	 * Blocks
	 *
	 *****************************************/
	override caseBlockIf(BlockIf block)'''
		if («block.condition.doSwitch») {
			«FOR thenBlock : block.thenBlocks»
				«thenBlock.doSwitch»
			«ENDFOR»
		}«IF block.elseRequired» else {
			«FOR elseBlock : block.elseBlocks»
				«elseBlock.doSwitch»
			«ENDFOR»
		}
		«ENDIF»
	'''
	
	override caseBlockWhile(BlockWhile blockWhile)'''
		while («blockWhile.condition.doSwitch») {
			«FOR block : blockWhile.blocks»
				«block.doSwitch»
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
	override caseInstAssign(InstAssign inst) '''
		«inst.target.variable.indexedName» = «inst.value.doSwitch»;
	'''
	
	override caseInstLoad(InstLoad load) {
		val srcPort = load.source.variable.getPort
		'''
			«IF srcPort != null»
				«load.target.variable.indexedName» = tokens_«srcPort.name»[(index_«srcPort.name» + («load.indexes.head.doSwitch»)) % SIZE_«srcPort.name»];
			«ELSE»
				«load.target.variable.indexedName» = «load.source.variable.name»«load.indexes.printArrayIndexes»;
			«ENDIF»
		'''
	}

	
	override caseInstStore(InstStore store) {
		val trgtPort = store.target.variable.port
		'''
		«IF trgtPort != null»
			«IF currentAction.outputPattern.varToPortMap.get(store.target.variable)?.native»
				printf("«trgtPort.name» = %i\n", «store.value.doSwitch»);
			«ELSE»
				tokens_«trgtPort.name»[(index_«trgtPort.name» + («store.indexes.head.doSwitch»)) % SIZE_«trgtPort.name»] = «store.value.doSwitch»;
			«ENDIF»
		«ELSE»
			«store.target.variable.name»«store.indexes.printArrayIndexes» = «store.value.doSwitch»;
		«ENDIF»
		'''
	}
	
	override caseInstCall(InstCall call) '''
		«IF call.print»
			printf(«call.parameters.printfArgs.join(", ")»);
		«ELSE»
			«IF call.target != null»«call.target.variable.indexedName» = «ENDIF»«call.procedure.name»(«call.parameters.join(", ", [printCallArg])»);
		«ENDIF»
	'''
	
	override caseInstReturn(InstReturn ret) '''
		«IF ret.value != null»
			return «ret.value.doSwitch»;
		«ENDIF»
	'''
	
		
	def getPort(Var variable) {
		if(currentAction == null) {
			null
		} else if (currentAction?.inputPattern.varToPortMap.containsKey(variable)) {
			currentAction.inputPattern.varToPortMap.get(variable)
		} else if(currentAction?.outputPattern.varToPortMap.containsKey(variable)) {
			currentAction.outputPattern.varToPortMap.get(variable)
		} else if(currentAction?.peekPattern.varToPortMap.containsKey(variable)) {
			currentAction.peekPattern.varToPortMap.get(variable)
		} else {
			null
		}
	}

	def printCallArg(Arg arg) {
		if(arg.byRef) {
			"&" + (arg as ArgByRef).use.variable.indexedName + (arg as ArgByRef).indexes.printArrayIndexes
		} else {
			(arg as ArgByVal).value.doSwitch
		}
	}	
	
	def printfArgs(List<Arg> args) {
		val finalArgs = new LinkedList<CharSequence>

		val printfPattern = new StringBuilder
		printfPattern.append('"')
		
		for (arg : args) {
			
			if(arg.byRef) {
				printfPattern.append("%" + (arg as ArgByRef).use.variable.type.printfFormat)
				finalArgs.add((arg as ArgByRef).use.variable.name)
			} else if((arg as ArgByVal).value.exprString) {
				printfPattern.append(((arg as ArgByVal).value as ExprString).value)
			} else {
				printfPattern.append("%" + (arg as ArgByVal).value.type.printfFormat)
				finalArgs.add((arg as ArgByVal).value.doSwitch)
			}
			
		}
		printfPattern.append('"')
		finalArgs.addFirst(printfPattern.toString)
		return finalArgs
	}
	
	/******************************************
	 * 
	 * Old templateData initialization
	 *
	 *****************************************/		
	def buildInputPattern() {
		for (action : instance.actor.actionsOutsideFsm) {
			val actionPattern = action.inputPattern
			for (port : actionPattern.ports) {
				var numTokens = inputPattern.getNumTokens(port);
				if (numTokens == null) {
					numTokens = actionPattern.getNumTokens(port);
				} else {
					numTokens = Math::max(numTokens, actionPattern.getNumTokens(port));
				}

				inputPattern.setNumTokens(port, numTokens);
			}
		}
	}
	
	def buildTransitionPattern() {		
		val fsm = instance.actor.getFsm()
		
		if (fsm != null) {
			for (state : fsm.getStates()) {
				val pattern = DfFactory::eINSTANCE.createPattern()
				
				for (edge : state.getOutgoing()) { 
					val actionPattern = (edge as Transition).getAction.getInputPattern()
					
					for (Port port : actionPattern.getPorts()) {
						
						var numTokens = pattern.getNumTokens(port)
						
						if (numTokens == null) {
							numTokens = actionPattern.getNumTokens(port)
						} else {
							numTokens = Math::max(numTokens, actionPattern.getNumTokens(port))
						}
	
						pattern.setNumTokens(port, numTokens)
					}
				}
				transitionPattern.put(state, pattern)
			}
		}
	}

}