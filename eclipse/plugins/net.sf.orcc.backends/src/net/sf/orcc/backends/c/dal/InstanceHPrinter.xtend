package net.sf.orcc.backends.c.dal

import net.sf.orcc.backends.c.CTemplate
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Port
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.Var

/**
 * Generate and print actor header file for DAL backend.
 *  
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
class InstanceHPrinter extends CTemplate {

	private var Actor actor

	private var String entityName

	override caseTypeBool(TypeBool type) 
		'''u8'''

	def setActor(Actor actor) {
		this.entityName = actor.name
		this.actor = actor
	}

	def getFileContent() '''
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

		«IF !actor.parameters.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Parameter values of the instance
			«FOR variable : actor.parameters»
				«variable.declareStateVar»
			«ENDFOR»
		«ENDIF»

		#endif
	'''

	def private print(Action action) '''
		«IF action.body.name == "fire_terminate"»
			int «entityName»_fire(DALProcess *);
		«ELSE»
			int «entityName»_«action.body.name»(DALProcess *);
		«ENDIF»
	'''

	def private declareStateVar(Var variable) '''
		«IF variable.assignable»
			«variable.declare»;
		«ENDIF»
	'''

	def private enumerate(Port port, int num) '''
		#define PORT_«port.name» «num»
	'''
}
