package net.sf.orcc.backends.c.dal

import net.sf.orcc.backends.c.CTemplate
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Port
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.Var
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeUint

/**
 * Generate and print actor header file for DAL backend.
 *  
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C InstancePrinter
 */
class InstanceHSPrinter extends CTemplate {

	private var Actor actor

	private var String entityName

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

	def setActor(Actor actor) {
		this.entityName = actor.name
		this.actor = actor
	}

	def getFileContent() '''
		#ifndef «entityName»_H
		#define «entityName»_H

		#include <dal.h>

		#define EVENT_DONE "stop_state1"

		«IF !actor.inputs.nullOrEmpty»
			«FOR i: 1..actor.inputs.size»
				«enumerateInput(actor.getInputs.get(i-1))»
			«ENDFOR»
		«ENDIF»
		«IF !actor.outputs.filter[! native].nullOrEmpty»
			«FOR i: 1..actor.outputs.size»
				«enumerateOutput(actor.getOutputs.get(i-1))»
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
			void *_io;
		} «entityName»_State;

		int «entityName»_init(DALProcess *);
		int «entityName»_fire(DALProcess *);
		int «entityName»_finish(DALProcess *);

		«IF !actor.parameters.nullOrEmpty»
			////////////////////////////////////////////////////////////////////////////////
			// Parameter values of the instance
			«FOR variable : actor.parameters»
				«variable.declareStateVar»
			«ENDFOR»
		«ENDIF»

		#endif
	'''

	def private declareStateVar(Var variable) '''
		«IF variable.assignable»
			«variable.declare»;
		«ENDIF»
	'''

	def private enumerateInput(Port port) '''
		#define PORT_«port.name» "«port.name»"
		typedef «port.type.doSwitch» TOKEN_«port.name»_t;
		#define TOKEN_«port.name»_RATE «port.getNumTokensConsumed()»
	'''
	
	def private enumerateOutput(Port port) '''
		#define PORT_«port.name» "«port.name»"
		typedef «port.type.doSwitch» TOKEN_«port.name»_t;
		#define TOKEN_«port.name»_RATE «-port.getNumTokensProduced()»
		#define BLOCK_«port.name»_SIZE	1
		#define BLOCK_«port.name»_COUNT (TOKEN_«port.name»_RATE / BLOCK_«port.name»_SIZE)
	'''
}
