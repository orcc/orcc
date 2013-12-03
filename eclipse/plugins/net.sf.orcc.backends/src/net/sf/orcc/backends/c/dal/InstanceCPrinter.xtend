package net.sf.orcc.backends.c.dal

import java.io.File
import java.util.HashMap
import java.util.List
import java.util.Map
import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.backends.ir.BlockFor
import net.sf.orcc.backends.ir.InstTernary
import net.sf.orcc.backends.c.CTemplate
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
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.OrccUtil

/**
 * Generate and print actor source file for DAL backend.
 *  
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
class InstanceCPrinter extends CTemplate {
	
	protected var Instance instance
	protected var Actor actor
	protected var Attributable attributable
	protected var int maxIter
	protected var Map<Port, Connection> incomingPortMap
	protected var Map<Port, List<Connection>> outgoingPortMap
	
	protected var String entityName
		
	protected val Pattern inputPattern = DfFactory::eINSTANCE.createPattern
	protected val Map<State, Pattern> transitionPattern = new HashMap<State, Pattern>
	
	protected var Action currentAction;

	/**
	 * Default constructor, used only by another backend (when subclass) which
	 * not print instances but actors
	 */
	protected new() {
		instance = null
		maxIter = 0
	}
	
	new(Map<String, Object> options) {		
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

	override caseTypeBool(TypeBool type) 
		'''u8'''
	
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
		val file = new File(targetFolder + File::separator + "src" + File::separator + entityName + ".c")
		
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
		this.actor = instance.actor
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
	
	def protected getFileContent() {
		if (!actor.hasAttribute("variableInputPattern")) {
			for (port : actor.getInputs()) {
				if (port.getNumTokensConsumed() > maxIter) {
					maxIter = port.getNumTokensConsumed()
				}
			}
		}
	'''
		#include <stdio.h>
		#include <string.h>
		#include <stdlib.h>

		#include "«entityName».h"

		«IF (!actor.hasAttribute("variableInputPattern"))»
			#define TMP_ITER (MAXBUFFER/«maxIter»)
			#if TMP_ITER < 1  
			  #define MAX_ITER 1
			#else
			  #define MAX_ITER TMP_ITER
			#endif 

			#define TOKEN_QUANTUM («FOR port : actor.getInputs»«port.getNumTokensConsumed()»+«ENDFOR»0) 
			
			void buffer_input(void *port, void *trg, int cnt, DALProcess *p) {
				DAL_read(port, trg, cnt, p);
			}
		«ELSE»
			«FOR port : actor.getInputs»
				«IF (port.hasAttribute("peekPort"))»
					«port.createReadOverload»
					«port.createPeekOverload»
				«ENDIF»
			«ENDFOR»
		«ENDIF»

		«FOR variable : actor.getStateVars»
			«IF variable.initialized && !variable.assignable && !variable.type.list»
				#define «variable.name» «variable.initialValue.doSwitch»
			«ENDIF»
		«ENDFOR»

		«FOR variable : actor.stateVars»
			«variable.declareStateVarListInit»
		«ENDFOR»

		«IF (instance != null && !instance.arguments.nullOrEmpty) || !actor.parameters.nullOrEmpty»
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
			
		«ENDIF»
		«IF actor.hasFsm»
			////////////////////////////////////////////////////////////////////////////////
			// Initial FSM state of the actor
			enum states {
				«FOR state : actor.fsm.states SEPARATOR ","»
					my_state_«state.name»
				«ENDFOR»
			};
			
		«ENDIF»

		«FOR variable : actor.stateVars»
			«variable.declareConstStateVars»
		«ENDFOR»

		«FOR proc : actor.procs»
			«proc.declareProc»
		«ENDFOR»
		
		«FOR proc : actor.procs»
			«proc.print»
		«ENDFOR»
				
		int «entityName»_init(DALProcess *_p) {
			«IF !actor.initializes.nullOrEmpty»
				«FOR init : actor.initializes»
					«init.printInitializeVars»
				«ENDFOR»
			«ENDIF»
			«IF actor.hasFsm»
				_p->local->_FSM_state = my_state_«actor.fsm.initialState.name»;
			«ENDIF»
			«IF actor.inputs.nullOrEmpty || actor.outputs.nullOrEmpty»
				_p->local->_io = NULL;
			«ENDIF»
			«FOR port : actor.getInputs»
				_p->local->_fo_«port.name» = malloc(sizeof(«port.type.doSwitch»));
			«ENDFOR»
			«IF !actor.initializes.nullOrEmpty»
				«FOR init : actor.initializes»
					«init.printInitializeBlocks»
				«ENDFOR»
			«ENDIF»
			«FOR variable : actor.stateVars»
				«variable.declareStateVarInInit»
			«ENDFOR»
			return 0;
		}
		
		int «entityName»_finish(DALProcess *p) {
			«FOR port : actor.getInputs»
				free(p->local->_fo_«port.name»);
			«ENDFOR»
			return 0;
		}

		«FOR action : actor.actions»
			«action.print»
		«ENDFOR»
		

		«actorScheduler»
	'''
	}
	
	def private actorScheduler() '''
		«IF actor.hasFsm»
			«printFsm»
		«ELSE»
			int «entityName»_fire(DALProcess *_p) {
				int iter = 0;
				
			«IF (actor.hasAttribute("variableInputPattern"))»
				for(iter = 0; iter < ITERATIONS; iter++) {
			«ELSE»
				«FOR port : actor.getInputs»
				«port.type.doSwitch» «port.getName()»_buffer[MAX_ITER*«port.getNumTokensConsumed()»];
				int «port.getName()»_index = 0;
				«ENDFOR»
	
				«FOR port : actor.getInputs»
				buffer_input((void*)PORT_«port.getName()», «port.getName()»_buffer, sizeof(«port.type.doSwitch»)*MAX_ITER*«port.getNumTokensConsumed()», _p);
				«ENDFOR»
				while(MAX_ITER*TOKEN_QUANTUM - iter >= TOKEN_QUANTUM) {			
			«ENDIF»
					«actor.actionsOutsideFsm.printActionLoop»
				
				finished:
					iter = iter;
				}
				return 0;
			}
		«ENDIF»
	'''
	
	//========================================
	//                  FSM
	//========================================
	def private printFsm() '''
		«IF ! actor.actionsOutsideFsm.empty»
			«IF (actor.hasAttribute("variableInputPattern"))»
				«inline»void «entityName»_outside_FSM_scheduler(DALProcess *_p) {
			«ELSE»
				«inline»void «entityName»_outside_FSM_scheduler(DALProcess *_p«FOR port : actor.getInputs», «port.type.doSwitch» *«port.getName()»_buffer, int *«port.getName()»_index«ENDFOR», int *iter) {
			«ENDIF»
				«actor.actionsOutsideFsm.printOutsideFSMActionLoop»
			finished:
				// no read_end/write_end here!
				return;
			}
		«ENDIF»
		
		int «entityName»_fire(DALProcess *_p) {
			int iter = 0;
		«IF (actor.hasAttribute("variableInputPattern"))»
			for(iter = 0; iter < ITERATIONS; iter++) {			
		«ELSE»
			«FOR port : actor.getInputs»
			
				«port.type.doSwitch» «port.getName()»_buffer[MAX_ITER*«port.getNumTokensConsumed()»];
				int «port.getName()»_index = 0;
			«ENDFOR»

			«FOR port : actor.getInputs»
			
				buffer_input((void*)PORT_«port.getName()», «port.getName()»_buffer, sizeof(«port.type.doSwitch»)*MAX_ITER*«port.getNumTokensConsumed()», _p);
			«ENDFOR»
				while(MAX_ITER*TOKEN_QUANTUM - iter >= TOKEN_QUANTUM) {			
		«ENDIF»
		«IF ! actor.actionsOutsideFsm.empty»
			«IF (actor.hasAttribute("variableInputPattern"))»
				«entityName»_outside_FSM_scheduler(_p);
			«ELSE»
				«entityName»_outside_FSM_scheduler(_p«FOR port : actor.getInputs», «port.getName()»_buffer, &«port.getName()»_index«ENDFOR», &iter);
				if(MAX_ITER*TOKEN_QUANTUM - iter < TOKEN_QUANTUM) return 0;			
			«ENDIF»
		«ENDIF»
				switch (_p->local->_FSM_state) {
		«FOR state : actor.fsm.states»
		
				case my_state_«state.name»:
					goto l_«state.name»;
		«ENDFOR»
				default:
					printf("unknown state in «entityName».c\n");
		}
		// FSM transitions
		«FOR state : actor.fsm.states»
	«state.printStateLabel»
		«ENDFOR»
		finished:
				iter = iter;
			}
			return 0;
		}
	'''
	
	def private printStateLabel(State state) '''
	l_«state.name»:
		«IF state.outgoing.empty»
			printf("Stuck in state "«state.name»" in «entityName»\n");
		«ELSE»
			«state.printStateTransitions»
		«ENDIF»
	'''
	
	def private printStateTransitions(State state) '''
		«FOR trans : state.outgoing.map[it as Transition] SEPARATOR " else "»
			«IF (actor.hasAttribute("variableInputPattern"))»
				if (isSchedulable_«trans.action.name»(_p)) {
					«trans.action.body.name»(_p);
			«ELSE»
				if (isSchedulable_«trans.action.name»(_p«FOR port : actor.getInputs», «port.getName()»_buffer, &«port.getName()»_index«ENDFOR», &iter)) {
					«trans.action.body.name»(_p«FOR port : actor.getInputs», «port.getName()»_buffer, &«port.getName()»_index«ENDFOR», &iter);
			«ENDIF»
				«IF !trans.target.name.equals(state.name)»
				_p->local->_FSM_state = my_state_«trans.target.name»;
				«ENDIF»
			}«ENDFOR»
		«transitionPattern.get(state).printTransitionPattern»
		goto finished;
		
	'''
	
	def private printTransitionPattern(Pattern pattern) '''
	'''
		
	def protected initializeFunction() '''
		«FOR init : actor.initializes»
			«init.print»
		«ENDFOR»
		
		«inline»void «entityName»_initialize(DALProcess *_p) {
			«IF actor.hasFsm»
				/* Set initial state to current FSM state */
				p->local->_FSM_state = my_state_«actor.fsm.initialState.name»;
			«ENDIF»
			«IF !actor.initializes.nullOrEmpty»
				«actor.initializes.printActions»
			«ENDIF»
			
		finished:
			// no read_end/write_end here!
			return;
		}
		
	'''
	
	def private checkConnectivy() {
		for(port : actor.inputs.filter[!inputConneted]) {
			OrccLogger::noticeln("["+entityName+"] Input port "+port.name+" not connected.")
		}
		for(port : actor.outputs.filter[!outputConnected]) {
			OrccLogger::noticeln("["+entityName+"] Output port "+port.name+" not connected.")
		}
	}
	
	def private printActionLoop(List<Action> actions) '''
		while (1) {
			«actions.printActions»
		}
	'''

	def private printOutsideFSMActionLoop(List<Action> actions) '''
		«IF !actor.hasAttribute("variableInputPattern")» 
			while (MAX_ITER*TOKEN_QUANTUM - iter[0] >= TOKEN_QUANTUM) {
				«actions.printOutsideFSMActions»
			}
		«ELSE»
			while (1) {
				«actions.printOutsideFSMActions»
			}
		«ENDIF»
 	'''
	
	def private printActions(Iterable<Action> actions) '''
		«FOR action : actions SEPARATOR " else "»
			«IF (actor.hasAttribute("variableInputPattern"))»
				if (isSchedulable_«action.name»(_p)) {
					«action.body.name»(_p);
			«ELSE»
				if (isSchedulable_«action.name»(_p«FOR port : actor.getInputs», «port.getName()»_buffer, &«port.getName()»_index«ENDFOR», &iter)) {
					«action.body.name»(_p«FOR port : actor.getInputs», «port.getName()»_buffer, &«port.getName()»_index«ENDFOR», &iter);
			«ENDIF»
				goto finished;
			}«ENDFOR» else {
			«inputPattern.printTransitionPattern»
			goto finished;
		}
	'''

	def private printOutsideFSMActions(Iterable<Action> actions) '''
		«FOR action : actions SEPARATOR " else "»
			«IF (actor.hasAttribute("variableInputPattern"))»
				if (isSchedulable_«action.name»(_p)) {
					«action.body.name»(_p);
			«ELSE»
				if (isSchedulable_«action.name»(_p«FOR port : actor.getInputs», «port.getName()»_buffer, «port.getName()»_index«ENDFOR», iter)) {
					«action.body.name»(_p«FOR port : actor.getInputs», «port.getName()»_buffer, «port.getName()»_index«ENDFOR», iter);
			«ENDIF»
				goto finished;
			}«ENDFOR» else {
			«inputPattern.printTransitionPattern»
			goto finished;
		}
	'''
	
	def private print(Action action) {
		currentAction = action
		val output = '''

			«IF (actor.hasAttribute("variableInputPattern"))»
				int «action.body.name»(DALProcess *_p) {
			«ELSE»
				int «action.body.name»(DALProcess *_p«FOR port : actor.getInputs», «port.type.doSwitch» *«port.getName()»_buffer, int *«port.getName()»_index«ENDFOR», int *iter) {
			«ENDIF»
				«FOR port : action.getInputPattern.getPorts»
					«IF !(port.hasAttribute("peekPort")) && actor.hasAttribute("variableInputPattern")»
						«port.type.doSwitch» buffer_«port.name»[«action.inputPattern.getNumTokens(port)»];
					«ENDIF»
				«ENDFOR»
				«FOR port : action.getOutputPattern.getPorts»
					«port.type.doSwitch» buffer_«port.name»[«action.outputPattern.getNumTokens(port)»];
				«ENDFOR»
				«FOR variable : action.body.locals»
					«variable.declare»;
				«ENDFOR»
				«FOR port : action.getInputPattern.getPorts»
					«IF !(port.hasAttribute("peekPort")) && actor.hasAttribute("variableInputPattern")»
						DAL_read((void*)PORT_«port.name», buffer_«port.name», sizeof(«port.type.doSwitch»)*«action.inputPattern.getNumTokens(port)», _p);
					«ENDIF»
				«ENDFOR»
				«FOR block : action.body.blocks»
					«block.doSwitch»
				«ENDFOR»
				«FOR port : action.getOutputPattern.getPorts»
					DAL_write((void*)PORT_«port.name», buffer_«port.name», sizeof(«port.type.doSwitch»)*«action.outputPattern.getNumTokens(port)», _p);
				«ENDFOR»
				return 0;
			}
			
			«action.scheduler.printScheduler»

		'''
		currentAction = null
		return output
	}

	def private printInitializeVars(Action action) {
		currentAction = action
		val output = '''
				«FOR variable : action.body.locals»
					«variable.declare»;
				«ENDFOR»
		'''
		currentAction = null
		return output
	}

	def private printInitializeBlocks(Action action) {
		currentAction = action
		val output = '''
				«FOR block : action.body.blocks»
					«block.doSwitch»
				«ENDFOR»			
		'''
		currentAction = null
		return output
	}
	
	def protected declareProc(Procedure proc){
		val modifier = "static"
		var comma = ","
		'''
		«IF proc.getParameters().size() == 0»
			«comma = ""»
		«ENDIF»
			«IF proc.native»
				«modifier» int «proc.name»(DALProcess *_p«comma» «proc.parameters.join(", ")[variable.declare]»);
			«ELSE»
				«modifier» «proc.returnType.doSwitch» «proc.name»(DALProcess *_p«comma» «proc.parameters.join(", ")[variable.declare]»);
			«ENDIF»
		'''
	}

	def private print(Procedure proc) {
		var comma = ","
	'''
		«IF proc != null»
		«IF proc.getParameters().size() == 0»
			«comma = ""»
		«ENDIF»
		«IF proc.native»
			«inline» int «proc.name»(DALProcess *_p«comma» «proc.parameters.join(", ")[variable.declare]») {
				#include "«proc.name».def"
			}
		«ELSE»
			«inline»«proc.returnType.doSwitch» «proc.name»(DALProcess *_p«comma» «proc.parameters.join(", ")[variable.declare]») {
				«FOR variable : proc.locals»
					«variable.declare»;
				«ENDFOR»
			
				«FOR block : proc.blocks»
					«block.doSwitch»
				«ENDFOR»
			}
		«ENDIF»
		«ENDIF»
	'''}

	def protected printScheduler(Procedure proc) '''
		«IF (actor.hasAttribute("variableInputPattern"))»
			int «proc.name»(DALProcess *_p) {
		«ELSE»
			int «proc.name»(DALProcess *_p«FOR port : actor.getInputs» ,«port.type.doSwitch» *«port.getName()»_buffer, int *«port.getName()»_index«ENDFOR», int *iter) {
		«ENDIF»
			«FOR variable : proc.locals»
				«variable.declare»;
			«ENDFOR»
		
			«FOR block : proc.blocks»
				«block.doSwitch»
			«ENDFOR»
		}
	'''
	
	def private declareStateVar(Var variable) {
		val varDecl =
			if(variable.initialized && !variable.assignable && !variable.type.list) {
				'''#define «variable.name» «variable.initialValue.doSwitch»'''
			} else {				
				// else branch are important here, to avoid a null value in the list of concat terms 
				val const = if(!variable.assignable) '''const ''' else ''''''
				val init = if(variable.initialized) ''' = «variable.initialValue.doSwitch»''' else ''''''
				
				'''static «const»«variable.declare»«init»;'''
			}
		'''
			«varDecl»
		'''
	}

	def private declareStateVarInInit(Var variable) {
		val varDecl =
			if(variable.initialized && variable.assignable) {
				if(variable.type.list) {
					'''memcpy(_p->local->«variable.name», «variable.name»_initial, sizeof(«variable.name»_initial));'''
				} else {
					val init = if(variable.initialized) ''' = «variable.initialValue.doSwitch»''' else ''''''
					
					'''_p->local->«variable.name»«init»;'''
				}
			}
		'''
			«varDecl»
		'''
	}

	def private declareStateVarListInit(Var variable) {
		val varDecl =
			if(variable.initialized && variable.assignable && variable.type.list) {
				val init = if(variable.initialized) ''' = «variable.initialValue.doSwitch»''' else ''''''
				
				'''static const «variable.type.doSwitch» «variable.name»_initial[]«init»;'''
			}
		'''
			«varDecl»
		'''
	}

	def private declareConstStateVars(Var variable) {
		val varDecl =
			if(!variable.assignable) {
				if(variable.initialized && !variable.assignable && !variable.type.list) {
					'''#define «variable.name» «variable.initialValue.doSwitch»'''
				} else {				
					// else branch are important here, to avoid a null value in the list of concat terms 
					val const = if(!variable.assignable) '''const ''' else ''''''
					val init = if(variable.initialized) ''' = «variable.initialValue.doSwitch»''' else ''''''
					
					'''static «const»«variable.declare»«init»;'''
				}
			}
			else {
				''''''
				}
		'''
			«varDecl»
		'''
	}

	def private createReadOverload(Port port)
	'''
		void _DAL_read_«port.name»(void *port, void *trg, int cnt, DALProcess *p) {
			if(p->local->_fo_filled_«port.name» == 1) {
				memcpy(trg, p->local->_fo_«port.name», cnt);
				DAL_read(port, p->local->_fo_«port.name», cnt, p);
				return;
			}
			
			DAL_read(port, trg, cnt, p);
		}
	'''

	def private createPeekOverload(Port port)
	'''
		void _DAL_peek_«port.name»(void *port, void *trg, int cnt, DALProcess *p) {
			if (p->local->_fo_filled_«port.name» == 1) {
				memcpy(trg, p->local->_fo_«port.name», cnt);
				return;
			}
		
			DAL_read(port, p->local->_fo_«port.name», cnt, p);
		
			p->local->_fo_filled_«port.name» = 1;
		
			memcpy(trg, p->local->_fo_«port.name», cnt);
		}
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

	override caseInstLoad(InstLoad load) {
		val srcPort = load.source.variable.getPort
		'''
			«IF srcPort != null»
				«IF (currentAction?.peekPattern.contains(load.source.variable))»
					«IF !(actor.hasAttribute("variableInputPattern"))»
						«load.target.variable.name» = «srcPort.name»_buffer[«srcPort.name»_index[0]];
					«ELSE»
						_DAL_peek_«srcPort.name»((void*)PORT_«srcPort.name», &«load.target.variable.name», sizeof(«srcPort.type.doSwitch»), _p);
					«ENDIF»
				«ELSE»
					«IF !(actor.hasAttribute("variableInputPattern"))»
						«load.target.variable.name» = «srcPort.name»_buffer[«srcPort.name»_index[0]];
						«srcPort.name»_index[0] ++;
						iter[0] ++;
					«ELSE»
						«IF (srcPort.hasAttribute("peekPort"))»
							_DAL_read_«srcPort.name»((void*)PORT_«srcPort.name», &«load.target.variable.name», sizeof(«srcPort.type.doSwitch»), _p);
						«ELSE»
							«load.target.variable.name» = buffer_«srcPort.name»[«load.indexes.head.doSwitch»];
						«ENDIF»
					«ENDIF»
				«ENDIF»
			«ELSE»
				«IF load.source.variable.isGlobal == true && load.source.variable.assignable == true»
					«load.target.variable.name» = _p->local->«load.source.variable.name»«load.indexes.printArrayIndexes»;
				«ELSE»
					«load.target.variable.name» = «load.source.variable.name»«load.indexes.printArrayIndexes»;
				«ENDIF»
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
				buffer_«trgtPort.name»[«store.indexes.head.doSwitch»] = «store.value.doSwitch»;
			«ENDIF»
		«ELSE»
			«IF store.target.variable.isGlobal == true»
				_p->local->«store.target.variable.name»«store.indexes.printArrayIndexes» = «store.value.doSwitch»;
			«ELSE»
				«store.target.variable.name»«store.indexes.printArrayIndexes» = «store.value.doSwitch»;
			«ENDIF»
		«ENDIF»
		'''
	}


	override caseInstCall(InstCall call) {
		var comma = ","
	'''
		«IF call.getArguments().size() == 0»
			«comma = ""»
		«ENDIF»
		«IF call.print»
			printf(«call.arguments.printfArgs.join(", ")»);
		«ELSE»
			«IF call.target != null»«call.target.variable.name» = «ENDIF»«call.procedure.name»(_p«comma» «call.arguments.join(", ")[printCallArg]»);
		«ENDIF»
	''' }

	override caseInstReturn(InstReturn ret) '''
		«IF ret.value != null»
			return «ret.value.doSwitch»;
		«ENDIF»
	'''
	
	override caseInstTernary(InstTernary inst) '''
		«inst.target.variable.name» = «inst.conditionValue.doSwitch» ? «inst.trueValue.doSwitch» : «inst.falseValue.doSwitch»;
	'''

	//========================================
	//            Helper methods
	//========================================
	def private getPort(Var variable) {
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
			"&" + (arg as ArgByRef).use.variable.name + (arg as ArgByRef).indexes.printArrayIndexes
		} else {
			(arg as ArgByVal).value.doSwitch
		}
	}	

	def private getInline() 
		''''''

	def private isOutputConnected(Port port) {
		// If the port has a list of output connections not defined or empty, returns false
		!outgoingPortMap.get(port).nullOrEmpty
	}

	def private isInputConneted(Port port) {
		// If the port has an input connection, returns true
		incomingPortMap.get(port) != null
	}

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
