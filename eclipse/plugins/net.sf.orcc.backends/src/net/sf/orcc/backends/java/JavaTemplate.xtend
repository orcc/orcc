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
package net.sf.orcc.backends.java

import java.util.ArrayList
import java.util.List
import net.sf.orcc.backends.util.CommonPrinter
import net.sf.orcc.df.Argument
import net.sf.orcc.df.Port
import net.sf.orcc.ir.Arg
import net.sf.orcc.ir.ArgByRef
import net.sf.orcc.ir.ArgByVal
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeFloat
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.Var
import net.sf.orcc.util.util.EcoreHelper
import org.eclipse.emf.common.util.EList

/*
 * Default Java Printer
 *  
 * @author Antoine Lorence
 * 
 */
class JavaTemplate extends CommonPrinter {
	
	
	new(){
		this.exprPrinter = new JavaExprPrinter
	}
	
	
	/******************************************
	 * 
	 * Expressions
	 *
	 *****************************************/
	override caseExpression(Expression expr)
		'''«exprPrinter.doSwitch(expr)»'''
	
	/******************************************
	 * 
	 * Types
	 *
	 *****************************************/
	override caseTypeBool(TypeBool type) {
		if(type.isFifoType)
			'''Boolean'''
		else
			'''boolean'''
	}

	override caseTypeInt(TypeInt type) {
		if(type.isFifoType)
			printFifoInt(type.size)
		else
			printInt(type.size)
	}

	override caseTypeUint(TypeUint type) {
		if(type.isFifoType)
			printFifoInt(type.size)
		else
			printInt(type.size)
	}

	override caseTypeFloat(TypeFloat type) {
		if( type.isFifoType)
			printFifoFloat(type.size)
		else
			printFloat(type.size)
	}

	override caseTypeString(TypeString type)
		'''String'''

	override caseTypeVoid(TypeVoid type)
		'''void'''
	
	override caseTypeList(TypeList typeList)
		'''«typeList.innermostType.doSwitch»«FOR i : 1..typeList.dimensions.size»[]«ENDFOR»'''

	def private printFloat(int size) {
		if (size == 64)
			'''double'''
		else
			'''float'''
	}
	
	def private printFifoFloat(int size) {
		if (size == 64)
			'''Double'''
		else
			'''Float'''
	}
	
	def private printInt(int size) {
		if (size <= 32) {
			return "int";
		} else if (size <= 64) {
			return "long";
		} else {
			return null;
		}
	}
	
	def private printFifoInt(int size) {
		if (size <= 32) {
			return "Integer";
		} else if (size <= 64) {
			return "Long";
		} else {
			return null;
		}
	}
	
	/******************************************
	 * 
	 * Helpers
	 *
	 *****************************************/
	/**
	 * Return true if this type object is used in a Fifo
	 */
	def isFifoType(Type type) {
		return EcoreHelper::getContainerOfType(type, typeof(Port)) != null
	}
	
	 // Print actor arguments, when initializing it
	def printArguments(EList<Var> actorParams, EList<Argument> arguments)
		'''«FOR paramVar : actorParams SEPARATOR ", "»«arguments.findFirst([arg | arg.variable == paramVar]).value.doSwitch»«ENDFOR»'''
	
	/**
	 * Print procedure parameter, when calling it. This helper may not
	 * be used with "print" calls
	 */
	def printParameters(InstCall call) {
		if(! call.parameters.empty) {
			var List<CharSequence> finalList = new ArrayList<CharSequence>();
			for( i : 0..call.parameters.size-1) {
				val procParamType = call.procedure.parameters.get(i).variable.type
				val callArgType =
					if (call.parameters.get(i).byRef) {
						(call.parameters.get(i) as ArgByRef).use.variable.type
					} else {
						(call.parameters.get(i) as ArgByVal).value.type
					}
				if(isCastNeeded(procParamType, callArgType)) {
					finalList.add('''(«procParamType.doSwitch») («printParameter(call.parameters.get(i))»)''')
				} else {
					finalList.add('''«printParameter(call.parameters.get(i))»''')
				}
			}
			finalList.join(", ")
		}
	}
	
	/**
	 * Print an argument of a call statement
	 */
	def printParameter(Arg arg) {
		if(arg.byVal) {
			if ((arg as ArgByVal).value.exprBinary) {
				return '''(«(arg as ArgByVal).value.doSwitch»)'''
			} else {
				return (arg as ArgByVal).value.doSwitch
			}
		} else {
			return (arg as ArgByRef).use.variable.name
		}
	}
	
	/**
	 * Return true if size of type "to" is less than "from's" one
	 */
	def isCastNeeded(Type to, Type from) {
		val sizeTo =
			if(to.list) (to as TypeList).innermostType.sizeInBits
			else to.sizeInBits
		val sizeFrom =
			if(from.list) (from as TypeList).innermostType.sizeInBits
			else from.sizeInBits
		return sizeTo < sizeFrom
	}
	
	/**
	 * Print a variable declaration, with its modifiers (final, public/private),
	 * its type and its initial value (if any)
	 */
	def declareVariable(Var variable) {
		
		var modifier = ""
		if(!variable.local){
			modifier = if (variable.global) "public " else "private "
			modifier = if(!variable.assignable) '''final «modifier»'''
		}
			
		val initialization =
			if(variable.initialized) {
				''' = «variable.initialValue.doSwitch»'''
			} else if (variable.value != null) {
				''' = «variable.value»'''
			} else if (variable.type.list) {
				val type = variable.type as TypeList
				''' = new «type.innermostType.doSwitch»«type.dimensionsExpr.printArrayIndexes»'''
			}
		
		'''
			«modifier»«variable.type.doSwitch» «variable.name»«initialization»;
		'''
	}
}