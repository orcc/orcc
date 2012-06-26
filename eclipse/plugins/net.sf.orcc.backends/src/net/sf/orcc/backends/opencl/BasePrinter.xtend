/*
 * Copyright (c) 2012, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.opencl

import net.sf.orcc.ir.util.IrSwitch
import net.sf.orcc.ir.ExprBool
import net.sf.orcc.ir.ExprFloat
import net.sf.orcc.ir.ExprInt
import net.sf.orcc.ir.ExprList
import net.sf.orcc.ir.ExprString
import net.sf.orcc.ir.ExprUnary
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.ExprBinary
/*
 * An expression and type printer
 * 
 * @author Endri Bezati
 */

class BasePrinter extends IrSwitch {
	
	override caseExprBinary(ExprBinary expr) '''(«expr.getE1.doSwitch» «expr.op.text» «expr.getE2.doSwitch»)'''

	override caseExprBool(ExprBool expr) '''«expr.value»'''
	
	override caseExprFloat(ExprFloat expr) '''«expr.value»'''

	override caseExprInt(ExprInt expr) '''«expr.value»«IF expr.long»LL«ENDIF»'''

	override caseExprList(ExprList expr) '''{ «FOR value: expr.value SEPARATOR ", "»«value.doSwitch»«ENDFOR» }'''

	override caseExprString(ExprString expr) '''"«expr.value»"'''

	override caseExprUnary(ExprUnary expr) '''«expr.op.text»(«expr.expr.doSwitch»)'''

	override caseExprVar(ExprVar expr) '''«expr.use.variable.indexedName»'''
	
	override caseTypeBool(TypeBool type)  '''bool'''
	
	override caseTypeInt(TypeInt type) {
		dataTypes(type.size); 
	}

	override caseTypeList(TypeList type) {
		type.type.doSwitch
	}

	override caseTypeString(TypeString type)  '''std::string'''

	override caseTypeUint(TypeUint type) {
		"unsigned " + dataTypes(type.size); 
	}

	override caseTypeVoid(TypeVoid type) {
		"void";
	}
	
	def private dataTypes(int size){
		if (size <= 8) {
			return "char";
		} else if (size <= 16) {
			return "short";
		} else if (size <= 32) {
			return "int";
		} else if (size <= 64) {
			return "long";
		} else {
			return null;
		}
	}
}