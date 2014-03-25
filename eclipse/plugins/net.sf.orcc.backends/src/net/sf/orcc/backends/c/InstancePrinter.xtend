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
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Param
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.util.util.EcoreHelper

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

	protected var String entityName

	protected var boolean profileNetwork = false
	protected var boolean profileActions = false
	protected var boolean dynamicMapping = false
	protected var boolean isActionAligned = false
	protected var boolean checkArrayInbounds = false

	var boolean newSchedul = false
	var boolean ringTopology = false

	var boolean enableTrace = false
	var String traceFolder
	var int threadsNb = 1;

	protected var inlineActors = false
	protected var inlineActions = false

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

		if (options.containsKey(PROFILE_NETWORK)) {
			profileNetwork = options.get(PROFILE_NETWORK) as Boolean
			if(options.containsKey(PROFILE_ACTIONS)){
				profileActions = options.get(PROFILE_ACTIONS) as Boolean
			}
		}
		if (options.containsKey(DYNAMIC_MAPPING)) {
			dynamicMapping = options.get(DYNAMIC_MAPPING) as Boolean
		}
		if (options.containsKey(CHECK_ARRAY_INBOUNDS)) {
			checkArrayInbounds = options.get(CHECK_ARRAY_INBOUNDS) as Boolean
		}

		if (options.containsKey(THREADS_NB)) {
			if(options.get(THREADS_NB) instanceof String) {
				threadsNb = Integer::valueOf(options.get(THREADS_NB) as String)
			} else {
				threadsNb = options.get(THREADS_NB) as Integer
			}
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
		if(options.containsKey(INLINE)){
			inlineActors = options.get(INLINE) as Boolean
			if(options.containsKey(INLINE_NOTACTIONS)){
				inlineActions = !options.get(INLINE_NOTACTIONS) as Boolean
			}
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
		val file = new File(targetFolder + File::separator + entityName + ".c")

		if(actor.native) {
			OrccLogger::noticeln(entityName + " is native and not generated.")
		} else if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def protected setInstance(Instance instance) {
		if (!instance.isActor) {
			throw new OrccRuntimeException("Instance " + entityName + " is not an Actor's instance")
		}

		this.instance = instance
		this.entityName = instance.name
		this.actor = instance.getActor
		this.attributable = instance
		this.incomingPortMap = instance.incomingPortMap
		this.outgoingPortMap = instance.outgoingPortMap

		buildInputPattern
		buildTransitionPattern
	}

	def protected setActor(Actor actor) {
		this.entityName = actor.name
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
		«IF checkArrayInbounds»
			#include <assert.h>
		«ENDIF»

		#include "types.h"
		#include "fifo.h"
		#include "util.h"
		#include "scheduler.h"
		#include "dataflow.h"
		«IF profileNetwork || dynamicMapping»
			#include "cycle.h"
		«ENDIF»

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
		extern actor_t «entityName»;

		«IF !actor.inputs.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Input FIFOs
			«FOR port : actor.inputs»
				«if (incomingPortMap.get(port) != null) "extern "» fifo_«port.type.doSwitch»_t *«port.fullName»;
			«ENDFOR»

			////////////////////////////////////////////////////////////////////////////////
			// Input Fifo control variables
			«FOR port : actor.inputs»
				static unsigned int index_«port.name»;
				static unsigned int numTokens_«port.name»;
				#define SIZE_«port.name» «incomingPortMap.get(port).sizeOrDefaultSize»
				#define tokens_«port.name» «port.fullName»->contents
				
				«IF profileNetwork || dynamicMapping»
					extern connection_t connection_«entityName»_«port.name»;
					#define rate_«port.name» connection_«entityName»_«port.name».rate
				«ENDIF»
				
			«ENDFOR»
			«IF enableTrace»
				////////////////////////////////////////////////////////////////////////////////
				// Trace files declaration (in)
				«FOR port : actor.inputs»
					FILE *file_«port.name»;
				«ENDFOR»

			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Predecessors
			«FOR port : actor.inputs»
				«IF incomingPortMap.get(port) != null»
					extern actor_t «incomingPortMap.get(port).source.label»;
				«ENDIF»
			«ENDFOR»

		«ENDIF»
		«IF !actor.outputs.filter[! native].nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Output FIFOs
			«FOR port : actor.outputs.filter[! native]»
				extern fifo_«port.type.doSwitch»_t *«port.fullName»;
			«ENDFOR»

			////////////////////////////////////////////////////////////////////////////////
			// Output Fifo control variables
			«FOR port : actor.outputs.filter[! native]»
				static unsigned int index_«port.name»;
				static unsigned int numFree_«port.name»;
				#define NUM_READERS_«port.name» «outgoingPortMap.get(port).size»
				#define SIZE_«port.name» «outgoingPortMap.get(port).get(0).sizeOrDefaultSize»
				#define tokens_«port.name» «port.fullName»->contents

			«ENDFOR»
			«IF enableTrace»
				////////////////////////////////////////////////////////////////////////////////
				// Trace files declaration (out)
				«FOR port : actor.outputs.filter[! native]»
					FILE *file_«port.name»;
				«ENDFOR»

			«ENDIF»
			////////////////////////////////////////////////////////////////////////////////
			// Successors
			«FOR port : actor.outputs»
				«FOR successor : outgoingPortMap.get(port)»
					extern actor_t «successor.target.label»;
				«ENDFOR»
			«ENDFOR»

		«ENDIF»
		«IF (instance != null && !instance.arguments.nullOrEmpty) || !actor.parameters.nullOrEmpty»
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
					«variable.declare»
				«ENDFOR»
			«ENDIF»

		«ENDIF»
		«IF profileActions && profileNetwork»
			////////////////////////////////////////////////////////////////////////////////
			// Action's workload for profiling
			«FOR action : actor.actions»
				extern action_t action_«actor.name»_«action.name»;
				#define ticks_«action.name» action_«actor.name»_«action.name».ticks
			«ENDFOR»
			
		«ENDIF»
		
		«IF !actor.stateVars.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// State variables of the actor
			«FOR variable : actor.stateVars»
				«variable.declare»
			«ENDFOR»

		«ENDIF»
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
		// Token functions
		«FOR port : actor.inputs»
			«port.readTokensFunctions»
		«ENDFOR»

		«FOR port : actor.outputs.notNative»
			«port.writeTokensFunctions»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Functions/procedures
		«FOR proc : actor.procs»
			«proc.declare»
		«ENDFOR»

		«FOR proc : actor.procs.notNativeProcs»
			«proc.print»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Actions
		«FOR action : actor.actions»
			«action.print()»
		«ENDFOR»

		////////////////////////////////////////////////////////////////////////////////
		// Initializes
		«printInitialize»

		////////////////////////////////////////////////////////////////////////////////
		// Action scheduler
		«printActorScheduler»
	'''

	def protected printActorScheduler() '''
		«IF actor.hasFsm»
			«printFsm»
		«ELSE»
			«noInline»void «entityName»_scheduler(schedinfo_t *si) {
				int i = 0;
				si->ports = 0;

				«printCallTokensFunctions»
				«IF enableTrace»
					«printOpenFiles»
				«ENDIF»

				«actor.actionsOutsideFsm.printActionSchedulingLoop»

			finished:
				«IF enableTrace»
					«printCloseFiles»
				«ENDIF»

				«FOR port : actor.inputs»
					read_end_«port.name»();
				«ENDFOR»
				«FOR port : actor.outputs.notNative»
					write_end_«port.name»();
				«ENDFOR»
				«IF actor.inputs.nullOrEmpty && actor.outputs.nullOrEmpty »
					// no read_end/write_end here!
					return;
				«ENDIF»
			}
		«ENDIF»
	'''

	//========================================
	//                  FSM
	//========================================
	def protected printFsm() '''
		«IF ! actor.actionsOutsideFsm.empty»
			«inline»void «entityName»_outside_FSM_scheduler(schedinfo_t *si) {
				int i = 0;
				«actor.actionsOutsideFsm.printActionSchedulingLoop»
			finished:
				// no read_end/write_end here!
				return;
			}
		«ENDIF»

		«noInline»void «entityName»_scheduler(schedinfo_t *si) {
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
				printf("unknown state in «entityName».c : %s\n", stateNames[_FSM_state]);
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
			«FOR port : actor.outputs.notNative»
				write_end_«port.name»();
			«ENDFOR»
			«IF actor.inputs.nullOrEmpty && actor.outputs.nullOrEmpty »
				// compiler needs to have something after the 'finished' label
				i = i;
			«ENDIF»
		}
	'''

	def protected printStateLabel(State state) '''
	l_«state.name»:
		«IF ! actor.actionsOutsideFsm.empty»
			«entityName»_outside_FSM_scheduler(si);
			i += si->num_firings;
		«ENDIF»
		«IF state.outgoing.empty»
			printf("Stuck in state "«state.name»" in «entityName»\n");
			exit(1);
		«ELSE»
			«state.printStateTransitions»
		«ENDIF»
	'''

	def protected printAlignmentConditions(Action action) '''
		{
			int isAligned = 1;
			«FOR port : action.inputPattern.ports»
				«IF port.hasAttribute(action.name + "_" + ALIGNABLE) && !port.hasAttribute(ALIGNED_ALWAYS)»
					isAligned &= ((index_«port.name» % SIZE_«port.name») < ((index_«port.name» + «action.inputPattern.getNumTokens(port)») % SIZE_«port.name»));
				«ENDIF»
			«ENDFOR»
			«FOR port : action.outputPattern.ports»
				«IF port.hasAttribute(action.name + "_" + ALIGNABLE) && !port.hasAttribute(ALIGNED_ALWAYS)»
					isAligned &= ((index_«port.name» % SIZE_«port.name») < ((index_«port.name» + «action.outputPattern.getNumTokens(port)») % SIZE_«port.name»));
				«ENDIF»
			«ENDFOR»
	'''

	def protected printStateTransition(State state, Transition trans) {
		val output = '''
			if («trans.action.inputPattern.checkInputPattern»isSchedulable_«trans.action.name»()) {
				«IF !trans.action.outputPattern.empty»
					«trans.action.outputPattern.printOutputPattern»
						_FSM_state = my_state_«state.name»;
						si->num_firings = i;
						si->reason = full;
						goto finished;
					}
				«ENDIF»
				«IF trans.action.hasAttribute(ALIGNED_ALWAYS)»
					«trans.action.body.name»_aligned();
				«ELSEIF trans.action.hasAttribute(ALIGNABLE)»
					«trans.action.printAlignmentConditions»
						if (isAligned) {
							«trans.action.body.name»_aligned();
						} else {
							«trans.action.body.name»();
						}
					}
				«ELSE»
					«trans.action.body.name»();
				«ENDIF»
				i++;
				goto l_«trans.target.name»;
		'''
		return output
	}

	def protected printStateTransitions(State state) '''
		«FOR trans : state.outgoing.map[it as Transition] SEPARATOR " else "»
		«printStateTransition(state, trans)»
		}«ENDFOR» else {
			«transitionPattern.get(state).printTransitionPattern»
			_FSM_state = my_state_«state.name»;
			goto finished;
		}
	'''

	def protected printTransitionPattern(Pattern pattern)  {
		'''
		«IF newSchedul»
			«FOR port : pattern.ports»
				«printTransitionPatternPort(port, pattern)»
			«ENDFOR»
		«ENDIF»
		si->num_firings = i;
		si->reason = starved;
		'''
	}

	def private printTransitionPatternPort(Port port, Pattern pattern) '''
		if (numTokens_«port.name» - index_«port.name» < «pattern.getNumTokens(port)») {
			if( ! «entityName».sched->round_robin || i > 0) {
				«IF incomingPortMap.containsKey(port)»
					sched_add_schedulable(«entityName».sched, &«incomingPortMap.get(port).source.label», RING_TOPOLOGY);
				«ENDIF»
			}
		}
	'''

	def protected printCallTokensFunctions() '''
		«FOR port : actor.inputs»
			read_«port.name»();
		«ENDFOR»
		«FOR port : actor.outputs.notNative»
			write_«port.name»();
		«ENDFOR»
	'''

	def protected printInitialize() '''
		«FOR init : actor.initializes»
			«init.print()»
		«ENDFOR»

		«inline»void «entityName»_initialize(schedinfo_t *si) {
			int i = 0;
			«IF actor.hasFsm»
				/* Set initial state to current FSM state */
				_FSM_state = my_state_«actor.fsm.initialState.name»;
			«ENDIF»
			«IF !actor.initializes.nullOrEmpty»
				«actor.initializes.printActionsScheduling»
			«ENDIF»

		finished:
			// no read_end/write_end here!
			return;
		}
	'''

	def private checkConnectivy() {
		for(port : actor.inputs.filter[incomingPortMap.get(it) == null]) {
			OrccLogger::noticeln("["+entityName+"] Input port "+port.name+" not connected.")
		}
		for(port : actor.outputs.filter[outgoingPortMap.get(it).nullOrEmpty]) {
			OrccLogger::noticeln("["+entityName+"] Output port "+port.name+" not connected.")
		}
	}

	def protected printActionSchedulingLoop(List<Action> actions) '''
		while (1) {
			«actions.printActionsScheduling»
		}
	'''

	def protected printActionScheduling(Action action) {
		val output = '''
			if («action.inputPattern.checkInputPattern»isSchedulable_«action.name»()) {
				«IF !action.outputPattern.empty»
					«action.outputPattern.printOutputPattern»
						si->num_firings = i;
						si->reason = full;
						goto finished;
					}
				«ENDIF»
				«IF action.hasAttribute(ALIGNED_ALWAYS)»
					«action.body.name»_aligned();
				«ELSEIF action.hasAttribute(ALIGNABLE)»
					«action.printAlignmentConditions»
						if (isAligned) {
							«action.body.name»_aligned();
						} else {
							«action.body.name»();
						}
					}
				«ELSE»
					«action.body.name»();
				«ENDIF»
				i++;
		'''
		return output
	}

	def protected printActionsScheduling(Iterable<Action> actions) '''
		«FOR action : actions SEPARATOR " else "»
			«action.printActionScheduling»
			}«ENDFOR» else {
			«inputPattern.printTransitionPattern»
			goto finished;
		}
	'''

	def protected printOutputPattern(Pattern pattern) {
		'''
		int stop = 0;
		«FOR port : pattern.ports»
			«printOutputPatternsPort(pattern, port)»
		«ENDFOR»
		if (stop != 0) {
		'''
	}

	def protected printOutputPatternsPort(Pattern pattern, Port port) {
		var i = -1
		'''
			«FOR successor : outgoingPortMap.get(port)»
				«printOutputPatternPort(pattern, port, successor, i = i + 1)»
			«ENDFOR»
		'''
	}

	def protected printOutputPatternPort(Pattern pattern, Port port, Connection successor, int id) {
		'''
		if («pattern.getNumTokens(port)» > SIZE_«port.name» - index_«port.name» + «port.fullName»->read_inds[«id»]) {
			stop = 1;
			«IF newSchedul»
				if( ! «entityName».sched->round_robin || i > 0) {
					sched_add_schedulable(«entityName».sched, &«successor.target.label», RING_TOPOLOGY);
				}
			«ENDIF»
		}
		'''
	}

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
	
	def private printCore(Action action, boolean isAligned) '''
		static «IF inlineActions»«inline»«ELSE»«noInline»«ENDIF»void «action.body.name»«IF isAligned»_aligned«ENDIF»() {
			«action.profileStart»

			// Compute aligned port indexes
			«FOR port : action.inputPattern.ports + action.outputPattern.ports»
				i32 index_aligned_«port.name» = index_«port.name» % SIZE_«port.name»;
			«ENDFOR»

			«FOR variable : action.body.locals»
				«variable.declare»;
			«ENDFOR»
			«writeTraces(action.inputPattern)»
			«beforeActionBody»

			«FOR block : action.body.blocks»
				«block.doSwitch»
			«ENDFOR»

			«afterActionBody»
			«writeTraces(action.outputPattern)»
			// Update ports indexes
			«FOR port : action.inputPattern.ports»
				index_«port.name» += «action.inputPattern.getNumTokens(port)»;
				«IF action.inputPattern.getNumTokens(port) >= MIN_REPEAT_RWEND»
					read_end_«port.name»();
				«ENDIF»
			«ENDFOR»
			«FOR port : action.outputPattern.ports»
				index_«port.name» += «action.outputPattern.getNumTokens(port)»;
				«IF action.outputPattern.getNumTokens(port) >= MIN_REPEAT_RWEND»
					write_end_«port.name»();
				«ENDIF»
			«ENDFOR»

			«action.profileEnd»
		}
	'''

	// These 2 methods are used by HMPP backend to print code before the
	// first line after after the last of an action body
	def protected afterActionBody() ''''''
	def protected beforeActionBody() ''''''
	
	def private writeTraces(Pattern pattern) {
		if(!enableTrace) return ''''''
		'''
			«FOR port : pattern.ports»
				{
					// Write traces
					int i;
					for (i = 0; i < «pattern.getNumTokens(port)»; i++) {
						fprintf(file_«port.name», "%«port.type.printfFormat»\n", tokens_«port.name»[(index_«port.name» + i) % SIZE_«port.name»]);
					}
				}
			«ENDFOR»
		'''
	}
	
	def private profileStart(Action action) '''
		«IF profileActions && profileNetwork && !actor.initializes.contains(action)»
			ticks tick_in = getticks();
			ticks tick_out;
			double diff_tick;
		«ENDIF»
	'''

	def private profileEnd(Action action) '''
		«IF (profileNetwork || dynamicMapping) && !actor.initializes.contains(action)»
			«FOR port : action.inputPattern.ports»
				rate_«port.name» += «action.inputPattern.getNumTokens(port)»;
			«ENDFOR»
		«ENDIF»
		«IF profileActions && profileNetwork && !actor.initializes.contains(action)»
			tick_out = getticks();
			diff_tick = elapsed(tick_out, tick_in);
			ticks_«action.name» += diff_tick;
		«ENDIF»
	'''

	def protected print(Action action) {
		currentAction = action
		isActionAligned = false
		'''
		«action.scheduler.print»
		
		«IF !action.hasAttribute(ALIGNED_ALWAYS)»
			«printCore(action, false)»
		«ENDIF»
		«IF isActionAligned = action.hasAttribute(ALIGNABLE)»
			«printCore(action, true)»
		«ENDIF»
		'''
	}

	def protected print(Procedure proc) {
		val isOptimizable = proc.hasAttribute(C_DIRECTIVE_OPTIMIZE);
		val optCond = proc.getAttribute(C_DIRECTIVE_OPTIMIZE)?.getValueAsString("condition")
		val optName = proc.getAttribute(C_DIRECTIVE_OPTIMIZE)?.getValueAsString("name")
		'''
		static «inline»«proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ")[declare]») {
			«IF isOptimizable»
				#if «optCond»
				«optName»(«proc.parameters.join(", ")[variable.name]»);
				#else
			«ENDIF»
			«FOR variable : proc.locals»
				«variable.declare»;
			«ENDFOR»

			«FOR block : proc.blocks»
				«block.doSwitch»
			«ENDFOR»
			«IF isOptimizable»
				#endif // «optCond»
			«ENDIF»
		}
		'''
	}
	
	def protected declare(Procedure proc){
		val modifier = if(proc.native) "extern" else "static"
		'''«modifier» «proc.returnType.doSwitch» «proc.name»(«proc.parameters.join(", ")[declare]»);'''
	}

	override protected declare(Var variable) {
		if(variable.global && variable.initialized && !variable.assignable && !variable.type.list) {
			'''#define «variable.name» «variable.initialValue.doSwitch»'''
		} else {
			val const = if(!variable.assignable && variable.global) "const "
			val global = if(variable.global) "static "
			val type = variable.type
			val dims = variable.type.dimensionsExpr.printArrayIndexes
			val init = if(variable.initialized) " = " + variable.initialValue.doSwitch
			val end = if(variable.global) ";"
			
			'''«global»«const»«type.doSwitch» «variable.name»«dims»«init»«end»'''
		}
	}
	
	def protected declare(Param param) {
		val variable = param.variable
		'''«variable.type.doSwitch» «variable.name»«variable.type.dimensionsExpr.printArrayIndexes»'''
	}

	def private getReaderId(Port port) {
		if(incomingPortMap.containsKey(port)) {
			String::valueOf(incomingPortMap.get(port).<Integer>getValueAsObject("fifoId"))
		} else {
			"-1"
		}
	}

	def protected fullName(Port port)
		'''«entityName»_«port.name»'''

	def private sizeOrDefaultSize(Connection conn) {
		if(conn == null || conn.size == null) "SIZE"
		else conn.size
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

	//========================================
	//               Blocks
	//========================================
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

	//========================================
	//            Instructions
	//========================================
	override caseInstAssign(InstAssign inst) '''
		«inst.target.variable.name» = «inst.value.doSwitch»;
	'''

	override checkArrayInbounds(List<Expression> exprList, List<Integer> dims) {
		'''
			«IF !exprList.empty»
				«FOR i : 0 .. exprList.size - 1»
					assert((«exprList.get(i).doSwitch») < «dims.get(i)»);
				«ENDFOR»
			«ENDIF»
		'''
	}

	override caseInstLoad(InstLoad load) {
		val srcPort = load.source.variable.getPort
		'''
			«IF srcPort != null»
				«IF (isActionAligned && srcPort.hasAttribute(currentAction.name + "_" + ALIGNABLE)) || srcPort.hasAttribute(ALIGNED_ALWAYS)»
					«load.target.variable.name» = tokens_«srcPort.name»[(index_aligned_«srcPort.name» + («load.indexes.head.doSwitch»))];
				«ELSE»
					«load.target.variable.name» = tokens_«srcPort.name»[(index_«srcPort.name» + («load.indexes.head.doSwitch»)) % SIZE_«srcPort.name»];
				«ENDIF»
			«ELSE»
				«IF checkArrayInbounds»
					«load.indexes.checkArrayInbounds(load.source.variable.type.dimensions)»
				«ENDIF»
				«load.target.variable.name» = «load.source.variable.name»«load.indexes.printArrayIndexes»;
			«ENDIF»
		'''
	}

	override caseInstStore(InstStore store) {
		val trgtPort = store.target.variable.port
		'''
		«IF trgtPort != null»
			«IF trgtPort.native»
				printf("«trgtPort.name» = %i\n", «store.value.doSwitch»);
			«ELSE»
				«IF (isActionAligned && trgtPort.hasAttribute(currentAction.name + "_" + ALIGNABLE)) || trgtPort.hasAttribute(ALIGNED_ALWAYS)»
					tokens_«trgtPort.name»[(index_aligned_«trgtPort.name» + («store.indexes.head.doSwitch»))] = «store.value.doSwitch»;
				«ELSE»
					tokens_«trgtPort.name»[(index_«trgtPort.name» + («store.indexes.head.doSwitch»)) % SIZE_«trgtPort.name»] = «store.value.doSwitch»;
				«ENDIF»
			«ENDIF»
		«ELSE»
			«IF checkArrayInbounds»
				«store.indexes.checkArrayInbounds(store.target.variable.type.dimensions)»
			«ENDIF»
			«store.target.variable.name»«store.indexes.printArrayIndexes» = «store.value.doSwitch»;
		«ENDIF»
		'''
	}

	override caseInstCall(InstCall call) '''
		«IF call.print»
			printf(«call.arguments.printfArgs.join(", ")»);
		«ELSE»
			«IF call.target != null»«call.target.variable.name» = «ENDIF»«call.procedure.name»(«call.arguments.join(", ")[print]»);
		«ENDIF»
	'''

	override caseInstReturn(InstReturn ret) '''
		«IF ret.value != null»
			return «ret.value.doSwitch»;
		«ENDIF»
	'''

	override caseInstTernary(InstTernary inst) '''
		«inst.target.variable.name» = «inst.conditionValue.doSwitch» ? «inst.trueValue.doSwitch» : «inst.falseValue.doSwitch»;
	'''
	
	override caseExprVar(ExprVar expr) {
		val port = expr.copyOf
		if(port != null && isActionAligned){
			// If the argument is just a local copy of input/output tokens
			// use directly the FIFO when the tokens are aligned
			'''&tokens_«port.name»[index_aligned_«port.name»]'''
		} else {
			expr.use.variable.name
		}
	}

	//========================================
	//            Helper methods
	//========================================
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
	
	/**
	 * Returns the port object in case the corresponding expression is 
	 * just a straight copy of the tokens.
	 * 
	 * @param expr
	 *            an expression
	 * @return the corresponding port, or <code>null</code>
	 */
	def private copyOf(ExprVar expr) {
		val action = EcoreHelper.getContainerOfType(expr, Action)
		val variable = expr.use.variable
		if(action == null || !expr.type.list || !variable.hasAttribute("copyOfTokens")) {
			return null
		}
		return variable.getValueAsEObject("copyOfTokens") as Port
	}

	def private print(Arg arg) {
		if(arg.byRef) {
			"&" + (arg as ArgByRef).use.variable.name + (arg as ArgByRef).indexes.printArrayIndexes
		} else {
			(arg as ArgByVal).value.doSwitch
		}
	}

	def private getInline()
		'''«IF inlineActors»__attribute__((always_inline)) «ENDIF»'''

	def private getNoInline()
		'''«IF inlineActors»__attribute__((noinline)) «ENDIF»'''

	//========================================
	//   Old template data initialization
	//========================================
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
