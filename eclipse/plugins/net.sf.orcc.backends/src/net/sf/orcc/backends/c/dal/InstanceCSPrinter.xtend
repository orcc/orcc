package net.sf.orcc.backends.c.dal

import java.util.HashMap
import java.util.List
import java.util.Map
import net.sf.orcc.backends.c.CTemplate
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
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.ExprBinary
import net.sf.orcc.ir.OpBinary

/**
 * Generate and print actor source file for DAL backend.
 *  
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
class InstanceCSPrinter extends CTemplate {
	
	protected var Instance instance
	protected var Actor actor
	protected var Map<Port, Connection> incomingPortMap
	protected var Map<Port, List<Connection>> outgoingPortMap
	
	protected var String entityName
	protected val Pattern inputPattern = DfFactory::eINSTANCE.createPattern
	protected val Map<State, Pattern> transitionPattern = new HashMap<State, Pattern>

	protected var Action currentAction;

	protected new() {
		super()
		instance = null
	}
	
	override caseTypeBool(TypeBool type) '''int'''

	override caseTypeInt(TypeInt type) {
		if (type.size > 16)
			'''int'''
		else if (type.size > 8)
			'''short'''
		else
			'''char'''
	}

	override caseTypeUint(TypeUint type) {
		if (type.size > 16)
			'''unsigned int'''
		else if (type.size > 8)
			'''unsigned short'''
		else
			'''unsigned char'''
	}
	
	def protected setActor(Actor actor) {
		this.entityName = actor.name
		this.actor = actor
		this.incomingPortMap = actor.incomingPortMap
		this.outgoingPortMap = actor.outgoingPortMap		

		checkConnectivy
		buildInputPattern
		buildTransitionPattern
	}
	
	def protected getFileContent() {
	'''
		#include <stdio.h>
		#include <string.h>
		#include <stdlib.h>

		int sqrti(int x) {
			return (int) sqrt((float) x);
		}

		#include "«entityName».h"

		«IF !actor.stateVars.nullOrEmpty»
			«FOR variable : actor.stateVars»
				«IF variable.initialized && !variable.assignable && !variable.type.list»
					#define «variable.name» «variable.initialValue.doSwitch»
				«ENDIF»
			«ENDFOR»

			«FOR variable : actor.stateVars»
				«variable.declareStateVarListInit»
			«ENDFOR»
		«ENDIF»

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

		«IF !actor.stateVars.nullOrEmpty»
			«FOR variable : actor.stateVars»
				«variable.declareConstStateVarsNonList»
			«ENDFOR»
		«ENDIF»

		int «entityName»_init(DALProcess *_p) {
			«IF !actor.stateVars.nullOrEmpty»
				«FOR variable : actor.stateVars»
					«variable.declareStateVarInInit»
				«ENDFOR»
			«ENDIF»
			return 0;
		}
		
		int «entityName»_finish(DALProcess *_p) {
			return 0;
		}

		«actorScheduler»
	'''
	}
	
	def private actorScheduler() '''
		int «entityName»_fire(DALProcess *_p) {
			
			«IF !actor.stateVars.nullOrEmpty»
				«FOR variable : actor.stateVars»
					«variable.declareConstStateVarsList»
				«ENDFOR»
			«ENDIF»
			«FOR action : actor.actions»
				«action.print»
			«ENDFOR»
			
			return 0;
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
	
	def private print(Action action) {
		currentAction = action
		val output = '''
			«FOR variable : action.body.locals»
				«variable.declare»;
			«ENDFOR»
			«FOR port : action.getInputPattern.getPorts»
				TOKEN_«port.name»_t *rbuf«port.name» = (TOKEN_«port.name»_t *)DAL_read_begin(PORT_«port.name», sizeof(TOKEN_«port.name»_t), TOKEN_«port.name»_RATE, _p);
			«ENDFOR»
			«FOR block : action.body.blocks»
				«IF block.isBlockWhile»
					«(block as BlockWhile).forEach(action.getOutputPattern.getPorts.get(0))»
				«ELSE»
					«block.doSwitch»
				«ENDIF»
			«ENDFOR»
			«FOR port : action.getInputPattern.getPorts»
				DAL_read_end(PORT_«port.name», rbuf«port.name», _p);
			«ENDFOR»
		'''
		currentAction = null
		return output
	}
	
	def protected printScheduler(Procedure proc) '''
			int «proc.name»(DALProcess *_p«FOR port : actor.getInputs» ,«port.type.doSwitch» *«port.getName()»_buffer, int *«port.getName()»_index«ENDFOR», int *iter) {
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

	def private declareConstStateVarsNonList(Var variable) {
		val varDecl =
			if(!variable.assignable) {
				if(variable.initialized && !variable.assignable && !variable.type.list) {
					'''#define «variable.name» «variable.initialValue.doSwitch»'''
				} else {
					''''''
				}
			}
			else {
				''''''
				}
		'''
			«varDecl»
		'''
	}

	def private declareConstStateVarsList(Var variable) {
		val varDecl =
			if(!variable.assignable) {
				if(variable.initialized && !variable.assignable && !variable.type.list) {
					''''''
				} else {				
					// else branch are important here, to avoid a null value in the list of concat terms 
					val const = if(!variable.assignable) '''const ''' else ''''''
					val init = if(variable.initialized) ''' = «variable.initialValue.doSwitch»''' else ''''''
					
					'''«const» «variable.declare»«init»;'''
				}
			}
			else {
				''''''
				}
		'''
			«varDecl»
		'''
	}

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
	
	def printIterations(BlockWhile blockWhile) {
		if (blockWhile.getCondition().isExprBinary()) {
			var eb = blockWhile.getCondition() as ExprBinary;
			if (eb.getOp() == OpBinary.LE) {
				'''«eb.getE2().doSwitch»'''
			} else {
				OrccLogger.traceln("Unsupported optype: " + eb.getOp().getName());
				'''unknown'''
			}
		} else {
			OrccLogger.traceln("Unsupported expression type: " + blockWhile.getCondition().toString());
			'''unknown'''
		}
	}

	def printIterand(BlockWhile blockWhile) {
		if (blockWhile.getCondition().isExprBinary()) {
			var eb = blockWhile.getCondition() as ExprBinary;
			if (eb.getOp() == OpBinary.LE) {
				if (eb.getE1().isExprVar()) {
					'''«eb.getE1().doSwitch»'''
				} else {
					OrccLogger.traceln("Unsupported iterand expression type: " + eb.getE1().toString());
					'''unknown'''
				}
			} else {
				OrccLogger.traceln("Unsupported optype: " + eb.getOp().getName());
				'''unknown'''
			}
		} else {
			OrccLogger.traceln("Unsupported expression type: " + blockWhile.getCondition().toString());
			'''unknown'''
		}
	}

	def forEach(BlockWhile blockWhile, Port port)'''
		//int BLOCK_loopIterations_COUNT = «blockWhile.printIterations» + 1;
		#ifdef LOCAL_MODE
		for («blockWhile.printIterand» = get_local_id(0) * (BLOCK_«port.name»_COUNT / get_local_size(0)); «blockWhile.printIterand» < (get_local_id(0) + 1) * (BLOCK_«port.name»_COUNT / get_local_size(0)); ) // block!
		#else
		DAL_foreach (blk : PORT_«port.name»)
		#endif
		{
			«FOR block : blockWhile.blocks»
				«block.doSwitch»
			«ENDFOR»
		}
	'''

	/*
	def forEach(BlockWhile blockWhile, Port port)'''
		//int BLOCK_loopIterations_COUNT = «blockWhile.printIterations» + 1;
		DAL_foreach (blk : PORT_«port.name»)
		{
			«FOR block : blockWhile.blocks»
				«block.doSwitch»
			«ENDFOR»
		}
	'''
	*/
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
				«load.target.variable.name» = rbuf«srcPort.name»[«load.indexes.head.doSwitch»];
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
				TOKEN_«trgtPort.name»_t *wbuf«trgtPort.name» = (TOKEN_«trgtPort.name»_t *)DAL_write_begin(PORT_«trgtPort.name», sizeof(TOKEN_«trgtPort.name»_t), TOKEN_«trgtPort.name»_RATE, BLOCK_«trgtPort.name»_SIZE, «store.indexes.head.doSwitch», _p);
				*wbuf«trgtPort.name» = «store.value.doSwitch»;
				DAL_write_end(PORT_«trgtPort.name», wbuf«trgtPort.name», _p);
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
			«IF call.target != null»«call.target.variable.name» = «ENDIF»«call.procedure.name»(«call.arguments.join(", ")[printCallArg]»);
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
