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
package net.sf.orcc.backends.llvm.aot

import net.sf.orcc.backends.util.TemplateUtil
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Port
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeFloat
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.Var
import net.sf.orcc.ir.ExprList
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.util.util.EcoreHelper

/*
 * Default LLVM Printer. Call ExpressionPrinter when necessary and print data types.
 *  
 * @author Antoine Lorence
 * 
 */
class LLVMTemplate extends TemplateUtil {
	
	new(){
		super()
		this.exprPrinter = new LLVMExpressionPrinter
	}
	
	/******************************************
	 * 
	 * Expressions
	 *
	 *****************************************/
	override caseExpression(Expression expr) {
		if (expr.exprVar)
			(expr as ExprVar).use.variable.print
		else if (expr.exprList)
			(expr as ExprList).doSwitch
		else
			exprPrinter.doSwitch(expr)
	}
		
	override caseExprList(ExprList exprList) {
		val list = '''[«exprList.value.join(", ", ['''«IF it.type.list»«it.type.doSwitch»«ELSE»«(EcoreHelper::getContainerOfType(exprList, typeof(Var)).type as TypeList).innermostType.doSwitch» «it.doSwitch»«ENDIF»'''])»]'''
		//return list.wrap
		return list
	}
	
	/******************************************
	 * 
	 * Types
	 *
	 *****************************************/
	override caseTypeBool(TypeBool type) 
		'''i1'''

	override caseTypeInt(TypeInt type)
		'''i«type.size»'''

	override caseTypeUint(TypeUint type) 
		'''i«type.size»'''

	override caseTypeFloat(TypeFloat type) {
		if (type.size == 16) '''half'''
		else if (type.size == 64) '''double'''
		else '''float'''
	}

	override caseTypeString(TypeString type) {
		if(type.size == 0) '''i8''' 
		else '''[«type.size» x i8]'''
	}

	override caseTypeVoid(TypeVoid type)
		'''void'''
	
	override caseTypeList(TypeList typeList)
		'''[«typeList.size» x «typeList.type.doSwitch»]'''

	
	/******************************************
	 * 
	 * Helpers
	 *
	 *****************************************/
		
	def getId(Connection connection, Port port) {
		if(connection != null) connection.getAttribute("id").objectValue
		else port.name
	}

	def getFifoSize(Connection connection) {
		if(connection != null) connection.size.toString
		else "512"
	}
	
	def print(Var variable)
		'''«IF variable.global»@«ELSE»%«ENDIF»«variable.indexedName»'''
}