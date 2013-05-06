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
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF YUSE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
 package net.sf.orcc.backends.c

import java.io.File
import java.util.HashMap
import java.util.List
import java.util.Map
import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.backends.ir.BlockFor
import net.sf.orcc.backends.ir.InstTernary
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.DfFactory
import net.sf.orcc.df.Entity
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
import net.sf.orcc.util.OrccUtil

import static net.sf.orcc.OrccLaunchConstants.*
import static net.sf.orcc.backends.BackendsConstants.*

/**
 * Generate and print instance source file for C backend.
 *  
 * @author Antoine Lorence
 * 
 */
class InstancePrinter extends CTemplate {
	
	protected var Instance instance
	protected var Actor actor
	protected var Attributable attributable
	protected var int fifoSize
	protected var Map<Port, Connection> incomingPortMap
	protected var Map<Port, List<Connection>> outgoingPortMap
	
	protected var String name
	
	protected var boolean geneticAlgo = false
	
	var boolean newSchedul = false
	var boolean ringTopology = false
	
	var boolean enableTrace = false
	var String traceFolder
	var int threadsNb = 1;
	
	protected var profile = false
	
	protected val Pattern inputPattern = DfFactory::eINSTANCE.createPattern
	protected val Map<State, Pattern> transitionPattern = new HashMap<State, Pattern>
	
	protected var Action currentAction;

	/**
	 * Default constructor, used only by another backend (when subclass) which
	 * not print instances but actors
	 */
	protected new() {
		instance = null
		fifoSize = 0
	}
	
	new(Map<String, Object> options) {		
		if (options.containsKey(FIFO_SIZE)) {
			fifoSize = options.get(FIFO_SIZE) as Integer
		} else {
			fifoSize = 512
		}
		
		if (options.containsKey(GENETIC_ALGORITHM)) {
			geneticAlgo = options.get(GENETIC_ALGORITHM) as Boolean
		}
		
		if (options.containsKey(THREADS_NB)) {
			if(options.get(THREADS_NB) instanceof String) {
				threadsNb = Integer::valueOf(options.get(THREADS_NB) as String)
			} else {
				threadsNb = options.get(THREADS_NB) as Integer
			}
		} else if (geneticAlgo) {
			OrccLogger::warnln("Genetic algorithm options has been checked, but THREADS_NB option is not set")
		}
		
		if (options.containsKey(NEW_SCHEDULER)) {
			newSchedul = options.get(NEW_SCHEDULER) as Boolean
		}
		if (options.containsKey(NEW_SCHEDULER_TOPOLOGY)) {
			ringTopology = options.get(NEW_SCHEDULER_TOPOLOGY).equals("Ring")
		}
		if (options.containsKey(ENABLE_TRACES)) {
			enableTrace = options.get(ENABLE_TRACES) as Boolean
			traceFolder = (options.get(TRACES_FOLDER) as String)?.replace('\\', "\\\\")
		}
		if(options.containsKey(PROFILE)){
			profile = options.get(PROFILE) as Boolean
		}
	}
	
	/**
	 * Print file content from a given instance
	 * 
	 * @param targetFolder folder to print the instance file
	 * @param instance the given instance
	 * @return 1 if file was cached, 0 if file was printed
	 */
	def print(String targetFolder, Instance instance) {
		setInstance(instance)	
		print(targetFolder)
	}
	
	/**
	 * Print file content from a given actor
	 * 
	 * @param targetFolder folder to print the actor file
	 * @param actor the given actor
	 * @return 1 if file was cached, 0 if file was printed
	 */
	def print(String targetFolder, Actor actor) {
		setActor(actor)
		print(targetFolder)
	}
	
	def protected print(String targetFolder) {
		checkConnectivy
		
		val content = fileContent
		val file = new File(targetFolder + File::separator + name + ".c")
		
		if(actor.native) {
			OrccLogger::noticeln(name + " is native and not generated.")
		} else if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	def protected setInstance(Instance instance) {
		if (!instance.isActor) {
			throw new OrccRuntimeException("Instance " + name + " is not an Actor's instance")
		}
		
		this.instance = instance
		this.name = instance.name
		this.actor = instance.actor
		this.attributable = instance
		this.incomingPortMap = instance.incomingPortMap
		this.outgoingPortMap = instance.outgoingPortMap		
		
		buildInputPattern
		buildTransitionPattern
	}
	
	def protected setActor(Actor actor) {
		this.name = actor.name
		this.actor = actor
		this.attributable = actor
		this.incomingPortMap = actor.incomingPortMap
		this.outgoingPortMap = actor.outgoingPortMap		
		
		buildInputPattern
		buildTransitionPattern
	}
	
	def protected getFileContent() '''
		// Source file is "«actor.file»"
		
		#include <stdio.h>
		#include <stdlib.h>
		
		#include "orcc_types.h"
		#include "orcc_fifo.h"
		#include "orcc_util.h"
		#include "orcc_scheduler.h"
		
		#define SIZE «fifoSize»
		«IF instance != null»
			«instance.printAttributes»
		«ELSE»
			«actor.printAttributes»
		«ENDIF»
		
		«IF newSchedul»
			#define RING_TOPOLOGY «IF ringTopology»1«ELSE»0«ENDIF»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Instance
		extern struct actor_s «name»;
		
		////////////////////////////////////////////////////////////////////////////////
		// Input FIFOs
		«FOR port : actor.inputs»
			«if (incomingPortMap.get(port) != null) "extern "»struct fifo_«port.type.doSwitch»_s *«port.fullName»;
		«ENDFOR»
		«FOR port : actor.inputs»
			static unsigned int index_«port.name»;
			static unsigned int numTokens_«port.name»;
			#define NUM_READERS_«port.name» «port.numReaders»
			#define SIZE_«port.name» «incomingPortMap.get(port).sizeOrDefaultSize»
			#define tokens_«port.name» «port.fullName»->contents
			
		«ENDFOR»
		«IF enableTrace»
			«FOR port : actor.inputs»
				FILE *file_«port.name»;
			«ENDFOR»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Predecessors
		«FOR port : actor.inputs»
			«IF incomingPortMap.get(port) != null»
				extern struct actor_s «incomingPortMap.get(port).source.label»;
			«ENDIF»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Output FIFOs
		«FOR port : actor.outputs.filter[! native]»
			extern struct fifo_«port.type.doSwitch»_s *«port.fullName»;
		«ENDFOR»
		«FOR port : actor.outputs.filter[! native]»
			static unsigned int index_«port.name»;
			static unsigned int numFree_«port.name»;
			#define NUM_READERS_«port.name» «outgoingPortMap.get(port).size»
			#define SIZE_«port.name» «outgoingPortMap.get(port).get(0).sizeOrDefaultSize»
			#define tokens_«port.name» «port.fullName»->contents
			
		«ENDFOR»
		«IF enableTrace»
			«FOR port : actor.outputs»
				FILE *file_«port.name»;
			«ENDFOR»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// Successors
		«FOR port : actor.outputs»
			«FOR successor : outgoingPortMap.get(port)»
				extern struct actor_s «successor.target.label»;
			«ENDFOR»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Parameter values of the instance
		«IF instance != null»
			«FOR arg : instance.arguments»
				«IF arg.value.exprList»
					static «IF (arg.value.type as TypeList).innermostType.uint»unsigned «ENDIF»int «arg.variable.name»«arg.value.type.dimensionsExpr.printArrayIndexes» = «arg.value.doSwitch»;
				«ELSE»
					#define «arg.variable.name» «arg.value.doSwitch»
				«ENDIF»
			«ENDFOR»
		«ELSE»
			«FOR variable : actor.parameters»
				«variable.declareStateVar»
			«ENDFOR»
		«ENDIF»
		
		////////////////////////////////////////////////////////////////////////////////
		// State variables of the actor
		«FOR variable : actor.stateVars»
			«variable.declareStateVar»
		«ENDFOR»
		«IF actor.hasFsm»
			////////////////////////////////////////////////////////////////////////////////
			// Initial FSM state of the actor
			enum states {
				«FOR state : actor.fsm.states SEPARATOR ","»
					my_state_«state.name»
				«ENDFOR»
			};
			
			static char *stateNames[] = {
				«FOR state : actor.fsm.states SEPARATOR ","»
					"«state.name»"
				«ENDFOR»
			};
			
			static enum states _FSM_state;
		«ENDIF»
		////////////////////////////////////////////////////////////////////////////////
		// Functions/procedures
		«FOR proc : actor.procs»
			«IF proc.native»extern«ELSE»static«ENDIF» «proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ")[variable.declare]»);
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
		// Token functions
		«FOR port : actor.inputs»
			«port.readTokensFunctions»
		«ENDFOR»
		
		«FOR port : actor.outputs.filter[!native]»
			«port.writeTokensFunctions»
		«ENDFOR»
		
		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«initializeFunction»
		
		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«actorScheduler»
	'''
	
	def protected actorScheduler() '''
		«IF actor.hasFsm»
			«printFsm»
		«ELSE»
			«noInline»void «name»_scheduler(struct schedinfo_s *si) {
				int i = 0;
				si->ports = 0;
			
				«printCallTokensFunctions»
				«IF enableTrace»
					«printOpenFiles»
				«ENDIF»
				
				«actor.actionsOutsideFsm.printActionLoop»
				
			finished:
				«IF enableTrace»
					«printCloseFiles»
				«ENDIF»
				
				«FOR port : actor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : actor.outputs.filter[!native]»
					write_end_«port.name»();
				«ENDFOR»
				«IF actor.inputs.nullOrEmpty && actor.outputs.nullOrEmpty »
					// no read_end/write_end here!
					return;
				«ENDIF»
			}
		«ENDIF»
	'''
	
	/******************************************
	 * 
	 * FSM
	 *
	 *****************************************/
	def protected printFsm() '''
		«IF ! actor.actionsOutsideFsm.empty»
			«inline»void «name»_outside_FSM_scheduler(struct schedinfo_s *si) {
				int i = 0;
				«actor.actionsOutsideFsm.printActionLoop»
			finished:
				// no read_end/write_end here!
				return;
			}
		«ENDIF»
		
		«noInline»void «name»_scheduler(struct schedinfo_s *si) {
			int i = 0;
		
			«printCallTokensFunctions»
			«IF enableTrace»
				«printOpenFiles»
			«ENDIF»
		
			// jump to FSM state 
			switch (_FSM_state) {
			«FOR state : actor.fsm.states»
				case my_state_«state.name»:
					goto l_«state.name»;
			«ENDFOR»
			default:
				printf("unknown state in «name».c : %s\n", stateNames[_FSM_state]);
				wait_for_key();
				exit(1);
			}
		
			// FSM transitions
			«FOR state : actor.fsm.states»
		«state.printStateLabel»
			«ENDFOR»
		finished:
			«IF enableTrace»
				«printCloseFiles»
			«ENDIF»
			«FOR port : actor.inputs»
				read_end_«port.name»();
			«ENDFOR»
			«FOR port : actor.outputs.filter[!native]»
				write_end_«port.name»();
			«ENDFOR»
		}
	'''
	
	def protected printStateLabel(State state) '''
	l_«state.name»:
		«IF ! actor.actionsOutsideFsm.empty»
			«name»_outside_FSM_scheduler(si);
			i += si->num_firings;
		«ENDIF»
		«IF state.outgoing.empty»
			printf("Stuck in state "«state.name»" in «name»\n");
			wait_for_key();
			exit(1);
		«ELSE»
			«state.printStateTransitions»
		«ENDIF»
	'''
	
	def protected printStateTransitions(State state) '''
		«FOR trans : state.outgoing.map[it as Transition] SEPARATOR " else "»
			if («trans.action.inputPattern.checkInputPattern»isSchedulable_«trans.action.name»()) {
				«IF !trans.action.outputPattern.empty»
					«trans.action.outputPattern.printOutputPattern»
						_FSM_state = my_state_«state.name»;
						si->num_firings = i;
						si->reason = full;
						goto finished;
					}
				«ENDIF»
				«trans.action.body.name»();
				i++;
				goto l_«trans.target.name»;
			}«ENDFOR» else {
			«transitionPattern.get(state).printTransitionPattern»
			_FSM_state = my_state_«state.name»;
			goto finished;
		}
	'''
	
	def protected printTransitionPattern(Pattern pattern) '''
		«IF newSchedul»
			«FOR port : pattern.ports»
				«printTransitionPatternPort(port, pattern)»
			«ENDFOR»
		«ENDIF»
		si->num_firings = i;
		si->reason = starved;
	'''
	
	def private printTransitionPatternPort(Port port, Pattern pattern) '''
		if (numTokens_«port.name» - index_«port.name» < «pattern.getNumTokens(port)») {
			if( ! «name».sched->round_robin || i > 0) {
				«IF incomingPortMap.containsKey(port)»
					sched_add_schedulable(«name».sched, &«incomingPortMap.get(port).source.label», RING_TOPOLOGY);
				«ENDIF»
			}
		}
	'''
	
	def protected printCallTokensFunctions() '''
		«FOR port : actor.inputs»
			read_«port.name»();
		«ENDFOR»
		«FOR port : actor.outputs.filter[!native]»
			write_«port.name»();
		«ENDFOR»
	'''
	
	def protected initializeFunction() '''
		«FOR init : actor.initializes»
			«init.print»
		«ENDFOR»
		
		«inline»void «name»_initialize(struct schedinfo_s *si) {
			int i = 0;
			«IF actor.hasFsm»
				/* Set initial state to current FSM state */
				_FSM_state = my_state_«actor.fsm.initialState.name»;
			«ENDIF»
			«IF !actor.initializes.nullOrEmpty»
				«actor.initializes.printActions»
			«ENDIF»
			
		finished:
			// no read_end/write_end here!
			return;
		}
		
		«IF(geneticAlgo)»
			void «name»_reinitialize(struct schedinfo_s *si) {
				int i = 0;
				«FOR variable : actor.stateVars»
					«IF variable.assignable && variable.initialized»
						«IF !variable.type.list»
							«variable.indexedName» = «variable.initialValue.doSwitch»;
						«ELSE»
							memcpy(«variable.indexedName», «variable.indexedName»_backup, sizeof(«variable.indexedName»_backup));
						«ENDIF»
					«ENDIF»
				«ENDFOR»
				«IF actor.hasFsm»
					/* Set initial state to current FSM state */
					_FSM_state = my_state_«actor.fsm.initialState.name»;
				«ENDIF»
				«IF !actor.initializes.nullOrEmpty»
					«actor.initializes.printActions»
				«ENDIF»
				
			finished:
				// no read_end/write_end here!
				return;
			}
		«ENDIF»
	'''
	
	def private checkConnectivy() {
		for(port : actor.inputs) {
			if(incomingPortMap.get(port) == null) {
				OrccLogger::noticeln("["+name+"] Input port "+port.name+" not connected.")
			}
		}
		for(port : actor.outputs) {
			if(outgoingPortMap.get(port) == null) {
				OrccLogger::noticeln("["+name+"] Output port "+port.name+" not connected.")
			}
		}
	}
	
	def protected printActionLoop(List<Action> actions) '''
		while (1) {
			«actions.printActions»
		}
	'''
	
	def protected printActions(Iterable<Action> actions) '''
		«FOR action : actions SEPARATOR " else "»
			if («action.inputPattern.checkInputPattern»isSchedulable_«action.name»()) {
				«IF !action.outputPattern.empty»
					«action.outputPattern.printOutputPattern»
						si->num_firings = i;
						si->reason = full;
						goto finished;
					}
				«ENDIF»
				«action.body.name»();
				i++;
			}«ENDFOR» else {
			«inputPattern.printTransitionPattern»
			goto finished;
		}
	'''
	
	def protected printOutputPattern(Pattern pattern) '''
		int stop = 0;
		«FOR port : pattern.ports»
			«printOutputPatternsPort(pattern, port)»
		«ENDFOR»
		if (stop != 0) {
	'''
	
	def protected printOutputPatternsPort(Pattern pattern, Port port) {
		var i = -1
		'''
			«FOR successor : outgoingPortMap.get(port)»
				«printOutputPatternPort(pattern, port, successor, i = i + 1)»
			«ENDFOR»
		'''
	}
	
	def protected printOutputPatternPort(Pattern pattern, Port port, Connection successor, int id) '''
		if («pattern.getNumTokens(port)» > SIZE_«port.name» - index_«port.name» + «port.fullName»->read_inds[«id»]) {
			stop = 1;
			«IF newSchedul»
				if( ! «name».sched->round_robin || i > 0) {
					sched_add_schedulable(«name».sched, &«successor.target.label», RING_TOPOLOGY);
				}
			«ENDIF»
		}
	'''

	def protected checkInputPattern(Pattern pattern)
		'''«FOR port : pattern.ports»numTokens_«port.name» - index_«port.name» >= «pattern.getNumTokens(port)» && «ENDFOR»'''
	
	def private writeTokensFunctions(Port port) '''
		static void write_«port.name»() {
			index_«port.name» = «port.fullName»->write_ind;
			numFree_«port.name» = index_«port.name» + fifo_«port.type.doSwitch»_get_room(«port.fullName», NUM_READERS_«port.name»);
		}
		
		static void write_end_«port.name»() {
			«port.fullName»->write_ind = index_«port.name»;
		}
	'''

	def private readTokensFunctions(Port port) '''
		static void read_«port.name»() {
			«IF incomingPortMap.containsKey(port)»
				index_«port.name» = «port.fullName»->read_inds[«port.readerId»];
				numTokens_«port.name» = index_«port.name» + fifo_«port.type.doSwitch»_get_num_tokens(«port.fullName», «port.readerId»);
			«ELSE»
				/* Input port «port.fullName» not connected */
				index_«port.name» = 0;
				numTokens_«port.name» = 0;
			«ENDIF»
		}
		
		static void read_end_«port.name»() {
			«IF incomingPortMap.containsKey(port)»
				«port.fullName»->read_inds[«port.readerId»] = index_«port.name»;
			«ELSE»
				/* Input port «port.fullName» not connected */
			«ENDIF»
		}
	'''

	def protected print(Action action) {
		currentAction = action
		val output = '''
			static «inline»void «action.body.name»() {
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
						for (i = 0; i < «action.inputPattern.getNumTokens(port)»; i++) {
							fprintf(file_«port.name», "%«port.type.printfFormat»\n", tokens_«port.name»[(index_«port.name» + i) % SIZE_«port.name»]);
						}
					}
					«ENDIF»
					index_«port.name» += «action.inputPattern.getNumTokens(port)»;
				«ENDFOR»
				
				«FOR port : action.outputPattern.ports»
					«IF enableTrace»
						{
							int i;
							for (i = 0; i < «action.outputPattern.getNumTokens(port)»; i++) {
								fprintf(file_«port.name», "%«port.type.printfFormat»\n", tokens_«port.name»[(index_«port.name» + i) % SIZE_«port.name»]);
							}
						}
					«ENDIF»
					index_«port.name» += «action.outputPattern.getNumTokens(port)»;
				«ENDFOR»
			}
			
			«action.scheduler.print»
			
		'''
		currentAction = null
		return output
	}
	
	def protected print(Procedure proc) '''
		«proc.printAttributes»
		static «inline»«proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ")[variable.declare]») {
			«FOR variable : proc.locals»
				«variable.declare»;
			«ENDFOR»
		
			«FOR block : proc.blocks»
				«block.doSwitch»
			«ENDFOR»
		}
	'''
	
	// TODO : simplify this :
	def protected declareStateVar(Var variable) '''
		«variable.printAttributes»
		«IF variable.initialized»
			«IF ! variable.assignable»
				«IF ! variable.type.list»
					#define «variable.name» «variable.initialValue.doSwitch»
				«ELSE»
					static const «variable.declare» = «variable.initialValue.doSwitch»;
				«ENDIF»
			«ELSE»
				static «variable.declare» = «variable.initialValue.doSwitch»;
				«IF geneticAlgo»
					static «variable.type.doSwitch» «variable.indexedName»_backup«variable.type.dimensionsExpr.printArrayIndexes» = «variable.initialValue.doSwitch»;
				«ENDIF»
			«ENDIF»
		«ELSE»
			static «variable.declare»;
		«ENDIF»
	'''
	
	def private getReaderId(Port port) {
		if(incomingPortMap.containsKey(port)) {
			String::valueOf(incomingPortMap.get(port).<Integer>getValueAsObject("fifoId"))
		} else {
			"-1"
		}
	}

	def protected fullName(Port port)
		'''«name»_«port.name»'''
	
	def private sizeOrDefaultSize(Connection conn) {
		if(conn == null || conn.size == null) "SIZE"
		else conn.size
	}
	
	def private getNumReaders(Port port) {
		if (incomingPortMap.get(port) == null) {
			'''0'''
		} else {
			val predecessor = incomingPortMap.get(port).source.getAdapter(typeof(Entity))
			val predecessorPort = incomingPortMap.get(port).sourcePort
			'''«predecessor.outgoingPortMap.get(predecessorPort).size»'''
		}
	}
	
	def private printOpenFiles() '''
		«FOR port : actor.inputs + actor.outputs»
			file_«port.name» = fopen("«traceFolder»«File::separator.replace('\\', "\\\\")»«port.fullName».txt", "a");
		«ENDFOR»
	'''
	
	def private printCloseFiles() '''
		«FOR port : actor.inputs + actor.outputs»
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
	
	override caseBlockFor(BlockFor block) '''
		for («block.init.join(", ")['''«toExpression»''']» ; «block.condition.doSwitch» ; «block.step.join(", ")['''«toExpression»''']») {
			«FOR contentBlock : block.blocks»
				«contentBlock.doSwitch»
			«ENDFOR»
		}
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
			«IF currentAction.outputPattern.varToPortMap.get(store.target.variable).native»
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
			printf(«call.arguments.printfArgs.join(", ")»);
		«ELSE»
			«IF call.target != null»«call.target.variable.indexedName» = «ENDIF»«call.procedure.name»(«call.arguments.join(", ")[printCallArg]»);
		«ENDIF»
	'''
	
	override caseInstReturn(InstReturn ret) '''
		«IF ret.value != null»
			return «ret.value.doSwitch»;
		«ENDIF»
	'''
	
	override caseInstTernary(InstTernary inst) '''
		«inst.target.variable.indexedName» = «inst.conditionValue.doSwitch» ? «inst.trueValue.doSwitch» : «inst.falseValue.doSwitch»;
	'''

	def protected getPort(Var variable) {
		if(currentAction == null) {
			null
		} else if (currentAction?.inputPattern.contains(variable)) {
			currentAction.inputPattern.getPort(variable)
		} else if(currentAction?.outputPattern.contains(variable)) {
			currentAction.outputPattern.getPort(variable)
		} else if(currentAction?.peekPattern.contains(variable)) {
			currentAction.peekPattern.getPort(variable)
		} else {
			null
		}
	}

	def private printCallArg(Arg arg) {
		if(arg.byRef) {
			"&" + (arg as ArgByRef).use.variable.indexedName + (arg as ArgByRef).indexes.printArrayIndexes
		} else {
			(arg as ArgByVal).value.doSwitch
		}
	}	

	def private getInline() 
		'''«IF profile»__attribute__((always_inline)) «ENDIF»'''

	def private getNoInline() 
		'''«IF profile»__attribute__((noinline)) «ENDIF»'''

	/******************************************
	 * 
	 * Old templateData initialization
	 *
	 *****************************************/		
	def private buildInputPattern() {
		for (action : actor.actionsOutsideFsm) {
			val actionPattern = action.inputPattern
			for (port : actionPattern.ports) {
				var numTokens = Math::max(inputPattern.getNumTokens(port), actionPattern.getNumTokens(port))
				inputPattern.setNumTokens(port, numTokens)
			}
		}
	}

	def private buildTransitionPattern() {		
		val fsm = actor.getFsm()

		if (fsm != null) {
			for (state : fsm.getStates()) {
				val pattern = DfFactory::eINSTANCE.createPattern()

				for (edge : state.getOutgoing()) { 
					val actionPattern = (edge as Transition).getAction.getInputPattern()

					for (Port port : actionPattern.getPorts()) {
						var numTokens = Math::max(pattern.getNumTokens(port), actionPattern.getNumTokens(port))
						pattern.setNumTokens(port, numTokens)
					}
				}
				transitionPattern.put(state, pattern)
			}
		}
	}
}
