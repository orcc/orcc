package net.sf.orcc.backends.c.dal

import java.io.File
import java.util.List
import java.util.Map
import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.backends.c.CTemplate

/**
 * Generate and print actor header file for DAL backend.
 *  
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
class InstanceHPrinter extends CTemplate {
	
	protected var Instance instance
	protected var Actor actor
	protected var Attributable attributable
	protected var Map<Port, Connection> incomingPortMap
	protected var Map<Port, List<Connection>> outgoingPortMap
	
	protected var String entityName
	
	
	protected var Action currentAction;

	/**
	 * Default constructor, used only by another backend (when subclass) which
	 * not print instances but actors
	 */
	protected new() {
		instance = null
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
	
	def private print(String targetFolder) {
		
		val content = fileContent
		val file = new File(targetFolder + File::separator + "src" + File::separator + entityName + ".h")
		
		if(actor.native) {
			OrccLogger::noticeln(entityName + " is native and not generated.")
		} else if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	def private setInstance(Instance instance) {
		if (!instance.isActor) {
			throw new OrccRuntimeException("Instance " + entityName + " is not an Actor's instance")
		}
		
		this.instance = instance
		this.entityName = instance.name
		this.actor = instance.actor
		this.attributable = instance
		this.incomingPortMap = instance.incomingPortMap
		this.outgoingPortMap = instance.outgoingPortMap		
	}
	
	def private setActor(Actor actor) {
		this.entityName = actor.name
		this.actor = actor
		this.attributable = actor
		this.incomingPortMap = actor.incomingPortMap
		this.outgoingPortMap = actor.outgoingPortMap		
	}
	
	def private getFileContent() '''
	
	#ifndef «entityName»_H
	#define «entityName»_H

	#include <dal.h>
	#include "global.h"

	#define EVENT_DONE "stop_state1"

	«IF !actor.inputs.nullOrEmpty»
		«FOR i: 1..actor.inputs.size»
			«enumerate(actor.getInputs.get(i-1), actor.getInputs.get(i-1).getNumber)»
		«ENDFOR» 
	«ENDIF»
	«IF !actor.outputs.filter[! native].nullOrEmpty»
		«FOR i: 1..actor.outputs.size»
			«enumerate(actor.getOutputs.get(i-1), actor.getOutputs.get(i-1).getNumber)»
		«ENDFOR» 
	«ENDIF»

	typedef struct _local_states {
		«IF !actor.stateVars.nullOrEmpty»
			«FOR variable : actor.stateVars»
				«variable.declareStateVar»
			«ENDFOR»
		«ENDIF»
		«FOR port : actor.getInputs»
			«IF port.hasAttribute("peekPort") && actor.hasAttribute("variableInputPattern")»
				«port.type.doSwitch» _fo_«port.name»;
				int _fo_filled_«port.name»;
			«ENDIF»
		«ENDFOR»
		int _count;
		int _FSM_state;
	«IF actor.inputs.nullOrEmpty || actor.outputs.nullOrEmpty»
		void *_io;
	«ENDIF»
	} «entityName»_State;

	int «entityName»_init(DALProcess *);
	int «entityName»_fire(DALProcess *);
	int «entityName»_finish(DALProcess *);

	«FOR action : actor.initializes»
		«action.print»
	«ENDFOR»

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
					«variable.declareStateVar»
				«ENDFOR»
			«ENDIF»
			
		«ENDIF»

	#endif	
	'''
	
	def private print(Action action) {
		currentAction = action
		val output = '''
			«IF action.body.name == "fire_terminate"»
				int «entityName»_fire(DALProcess *);
			«ELSE»
				int «entityName»_«action.body.name»(DALProcess *);
			«ENDIF»
		'''
		currentAction = null
		return output
	}
	
	def private declareStateVar(Var variable) {
		'''
			«IF variable.assignable»
				«variable.declare»;
			«ENDIF»
		'''
	}

	def private enumerate(Port port, int num) '''
		#define PORT_«port.name» «num»
	'''
}
