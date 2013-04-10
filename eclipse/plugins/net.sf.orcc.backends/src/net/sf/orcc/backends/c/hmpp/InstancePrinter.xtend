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

import java.util.Map
import net.sf.orcc.df.Instance
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.Attribute
import net.sf.orcc.ir.InstCall
import net.sf.orcc.backends.ir.BlockFor

class InstancePrinter extends net.sf.orcc.backends.c.InstancePrinter {
	
	val hmppDirectives = #["codelet", "codesite", "group", "acquire",
		"release", "advancedload", "delegatedstore", "resident"]
	val hmppcgDirectives = #["gridify"]
	
	new(Map<String, Object> options) {
		super(options)
	}
		
	def private filterBeforeAttributes(Iterable<Attribute> attributes) {
		attributes.filter[!hasAttribute("after")]
	}
	def private filterAfterAttributes(Iterable<Attribute> attributes) {
		attributes.filter[hasAttribute("after")]
	}

	override printAttributes(Attributable eobject) '''
		«IF eobject instanceof Instance»
			«(eobject as Instance).actor.printAttributes»
		«ELSE»
			«FOR attribute : eobject.attributes.filterBeforeAttributes»
				«attribute.printHmppPragma»
			«ENDFOR»
		«ENDIF»
	'''
	
	override caseInstCall(InstCall call) '''
		«FOR attr : call.attributes.filterBeforeAttributes»
			«attr.printHmppPragma»
		«ENDFOR»
		«super.caseInstCall(call)»
		«FOR attr : call.attributes.filterAfterAttributes»
			«attr.printHmppPragma»
		«ENDFOR»
	'''
	
	override caseBlockFor(BlockFor block) '''
		«FOR attr : block.attributes»
			«attr.printHmppPragma»
		«ENDFOR»
		«super.caseBlockFor(block)»
	'''
	
	def private printHmppPragma(Attribute attr) {
		val directiveType=
			if(hmppDirectives.contains(attr.name)) "hmpp"
			else if(hmppcgDirectives.contains(attr.name)) "hmppcg"
			else return ""

		var labels = ""
		if(attr.hasAttribute("grp_label"))
			labels = " " + attr.getAttribute("grp_label").stringValue
		if(attr.hasAttribute("codelet_label"))
			labels = " " + attr.getAttribute("codelet_label").stringValue
			
		val params = if(attr.hasAttribute("params"))
			", " + attr.getAttribute("params").stringValue

		'''#pragma «directiveType»«labels» «attr.name»«params»'''
	}
}