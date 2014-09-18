/*
 * Copyright (c) 2009-2013, IETR/INSA of Rennes
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
 * 
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
 
package net.sf.orcc.backends.c.hmpp

import java.util.List
import java.util.Map
import java.util.Set
import net.sf.orcc.backends.ir.BlockFor
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Instance
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.Attribute

class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {

	val List<Procedure> codelets = newArrayList
	val List<InstCall> callsites = newArrayList

	def getCodelets() {
		codelets
	}

	def getCallSites() {
		callsites
	}

	def String defaultFileName(Procedure procedure) '''CODELET_CODE_«procedure.name»_DEFAULT.c'''
	def String wrapperFileName(Procedure procedure) '''CODELET_CODE_«procedure.name»_GEN_WRAPPER.c'''
	def String selectorFileName(InstCall call) '''CODELET_CODE_«call.procedure.name»_SELECTOR.c'''

	override caseInstCall(InstCall call) '''
		«call.printCallsite»
		«super.caseInstCall(call)»
	'''

	override caseBlockFor(BlockFor block) '''
		«block.printGridify»
		«super.caseBlockFor(block)»
	'''

	// Note: this method shoudn't add a new line at the end of
	// the returned string
	override protected declare(Var variable) '''
		«variable.printResident»
		«super.declare(variable)»'''

	override protected declare(Procedure proc) '''
		«proc.printCodelet»
		«super.declare(proc)»
	'''

	override protected beforeActionBody() {
		currentAction.body.printAdvancedload
	}

	override protected afterActionBody() {
		currentAction.body.printDelegatedstore
	}

	def getDefaultContent(Procedure procedure) {
		// Returns the full procedure c code
		super.print(procedure)
	}

	def getWrapperContent(Procedure procedure) '''
		#include "«procedure.defaultFileName»"
	'''

	def getSelectorContent(InstCall call) {
		val group = call.getAttribute("callsite").attributes.filterGroupsLabels.head
		'''
			/* Group name chosen */
			#pragma orcc2hmpp group=«group.name.replaceAll("<|>", "")»
			/* Accelerator identifier */
			#pragma orcc2hmpp GPU=1
			#pragma hmpp «group.name» cdlt_«call.procedure.name» callsite
		'''
	}

	override protected print(Procedure proc) {
		// This method override only codelet printing
		if(proc.hasAttribute("codelet")) {
			codelets.add(proc)
			'''
				#include "«proc.wrapperFileName»"
			'''
		} else {
			super.print(proc)
		}
	}

	override protected printAttributes(Attributable object) '''
		«IF (object instanceof Instance || object instanceof Actor) && object.hasAttribute("group")»
			«FOR grp : object.getAttribute("group").attributes.filterGroupsLabels»
				«val params = grp.attributes»
				#pragma hmpp «grp.name» group«params.join(", ", ", ", "")['''«name»=«stringValue»''']»
			«ENDFOR»
		«ENDIF»
	'''

	def private printResident(Var variable) '''
		«IF variable.hasAttribute("resident")»
			«FOR grp : variable.getAttribute("resident").attributes.filterGroupsLabels»
				«val direction = grp.getValueAsString("direction")»
				«val sizeArg =
					if(variable.type.list)
						''', args[::«variable.name»].size={«(variable.type as TypeList).dimensions.join(",")»}'''
					else ""»
				#pragma hmpp «grp.name» resident, args[::«variable.name»].io=«direction»«sizeArg»
			«ENDFOR»
		«ENDIF»
	'''

	def private printGridify (BlockFor block) {
		 if(block.hasAttribute("gridify")) {
			val attrList = block.getAttribute("gridify").attributes
			'''#pragma hmppcg gridify («attrList.join(",")['''«(containedValue as ExprVar).use.variable.name»''']»)'''
		}
	}

	def private printCodelet(Procedure proc) '''
		«IF proc.hasAttribute("codelet")»
			«FOR grp : proc.getAttribute("codelet").attributes.filterGroupsLabels»
				«FOR cdlt : grp.attributes.filterCodeletsLabels»
					«val params = proc.getAttribute("codelet").attributes»
					«val paramsString = params.filterNoGroupsLabels.join(", ", ", ", "")['''«name»=«stringValue»''']»

					«val vars = cdlt.objectValue as Map<String, String>»
					«val transferString = vars.entrySet.join(", ", ", ", "")['''args[«key»].io=«value»''']»
					#pragma hmpp «grp.name» «cdlt.name» codelet«paramsString»«transferString»
				«ENDFOR»
			«ENDFOR»
		«ENDIF»
	'''

	def private printCallsite(InstCall call) {
		if(call.hasAttribute("callsite")) {
			callsites.add(call)
			'''#include "«call.selectorFileName»"'''
		}
	}

	def private printAdvancedload(Procedure procedure) '''
		«IF procedure.hasAttribute("advancedload")»
			«FOR grp : procedure.getAttribute("advancedload").attributes.filterGroupsLabels»
				«FOR cdlt : grp.attributes.filterCodeletsLabels»
					«FOR varName : cdlt.objectValue as Set<String>»
						#pragma hmpp «grp.name» «cdlt.name» advancedload, args[::«varName»]
					«ENDFOR»
				«ENDFOR»
			«ENDFOR»
		«ENDIF»
	'''

	def private printDelegatedstore(Procedure procedure) '''
		«IF procedure.hasAttribute("delegatedstore")»
			«FOR grp : procedure.getAttribute("delegatedstore").attributes.filterGroupsLabels»
				«FOR cdlt : grp.attributes.filterCodeletsLabels»
					«FOR varName : cdlt.objectValue as Set<String>»
						#pragma hmpp «grp.name» «cdlt.name» delegatedstore, args[::«varName»]
					«ENDFOR»
				«ENDFOR»
			«ENDFOR»
		«ENDIF»
	'''

	def private filterGroupsLabels(Iterable<Attribute> attrs) {
		attrs.filter[it.name.startsWith("<grp_")]
	}

	def private filterNoGroupsLabels(Iterable<Attribute> attrs) {
		attrs.filter[!it.name.startsWith("<grp_")]
	}

	def private filterCodeletsLabels(Iterable<Attribute> attrs) {
		attrs.filter[it.name.startsWith("cdlt_")]
	}
}
