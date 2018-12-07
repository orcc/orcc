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

import java.util.LinkedList
import java.util.List
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.ir.Arg
import net.sf.orcc.ir.ArgByRef
import net.sf.orcc.ir.ArgByVal
import net.sf.orcc.ir.ExprBinary
import net.sf.orcc.ir.ExprBool
import net.sf.orcc.ir.ExprInt
import net.sf.orcc.ir.ExprString
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.Instruction
import net.sf.orcc.ir.OpBinary
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeFloat
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import net.sf.orcc.util.util.EcoreHelper

/*
 * Default C Printer
 *  
 * @author Antoine Lorence
 * 
 */
abstract class CTemplate extends CommonPrinter {

	/////////////////////////////////
	// Expressions
	/////////////////////////////////
	override caseExprBinary(ExprBinary expr) {
		val op = expr.op
		val container = EcoreHelper.getContainerOfType(expr, typeof(Expression))
		var nextPrec = if (op == OpBinary::SHIFT_LEFT || op == OpBinary::SHIFT_RIGHT) {

				// special case, for shifts always put parentheses because compilers
				// often issue warnings
				Integer::MIN_VALUE;
			} else {
				op.precedence;
			}

		val resultingExpr = '''«expr.e1.printExpr(nextPrec, 0)» «op.stringRepresentation» «expr.e2.printExpr(nextPrec, 1)»'''

		if (op.needsParentheses(precedence, branch) || (container !== null && op.logical)) {
			'''(«resultingExpr»)'''
		} else {
			resultingExpr
		}
	}

	override caseExprBool(ExprBool object) {
		if(object.value) "1" else "0"
	}

	override caseExprInt(ExprInt object) {
		val longVal = object.value.longValue
		if (longVal < Integer::MIN_VALUE || longVal > Integer::MAX_VALUE) {
			'''«longVal»L'''
		} else {
			'''«longVal»'''
		}
	}

	override protected stringRepresentation(OpBinary op) {
		if (op == OpBinary::DIV_INT)
			"/"
		else
			super.stringRepresentation(op)
	}

	/////////////////////////////////
	// Types
	/////////////////////////////////
	override caseTypeBool(TypeBool type) '''i32'''

	override caseTypeInt(TypeInt type) '''i«type.size»'''

	override caseTypeUint(TypeUint type) '''u«type.size»'''

	override caseTypeFloat(TypeFloat type) {
		if (type.size == 64) '''double''' else '''float'''
	}

	override caseTypeString(TypeString type) '''char *'''

	override caseTypeVoid(TypeVoid type) '''void'''

	override caseTypeList(TypeList typeList)
		//TODO : print sizes
	'''«typeList.innermostType.doSwitch»'''

	def protected declare(Var variable) '''«variable.type.doSwitch» «variable.name»«variable.type.dimensionsExpr.
		printArrayIndexes»'''

	/////////////////////////////////
	// Helpers
	/////////////////////////////////
	/**
	  * Print for a type, the corresponding formatted text to
	  * use inside a printf() call.
	  * @param type the type to print
	  * @return printf() type format
	  */
	def protected printfFormat(Type type) {
		switch type {
			case type.bool: "i"
			case type.float: "f"
			case type.int && (type as TypeInt).long: "lli"
			case type.int: "i"
			case type.uint && (type as TypeUint).long: "llu"
			case type.uint: "u"
			case type.list: "p"
			case type.string: "s"
			case type.void: "p"
		}
	}

	def protected printfArgs(List<Arg> args) {
		val finalArgs = new LinkedList<CharSequence>

		val printfPattern = new StringBuilder
		printfPattern.append('"')

		for (arg : args) {

			if (arg.byRef) {
				printfPattern.append("%" + (arg as ArgByRef).use.variable.type.printfFormat)
				finalArgs.add((arg as ArgByRef).use.variable.name)
			} else if ((arg as ArgByVal).value.exprString) {
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

	/**
	 * Print attributes for an Attributable object.
	 * Do nothing on C backend, but is used by others.
	 * @param object the object
	 * @return comment block
	 */
	def protected printAttributes(Attributable object) ''''''

	/**
	 * This helper return a representation of a given instruction without
	 * trailing whitespace and semicolon
	 */
	def protected toExpression(Instruction instruction) {
		instruction.doSwitch.toString.replaceAll("([^;]+);(\\s+)?", "$1")
	}
	
	def protected printNativeLibHeaders(String linkNativeLibHeaders) {
	'''
		// -- Native lib headers
		«FOR header : linkNativeLibHeaders.split(";")»
			#include "«header.trim()»"
		«ENDFOR»
		
	'''
	}	
}
