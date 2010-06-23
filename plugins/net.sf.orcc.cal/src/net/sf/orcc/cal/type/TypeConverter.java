/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.type;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.FloatType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;

/**
 * This class defines an AST type to IR type converter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeConverter extends CalSwitch<Type> {

	/**
	 * Creates a new AST type to IR type conversion.
	 */
	public TypeConverter() {
	}

	@Override
	public Type caseAstTypeBool(AstTypeBool type) {
		return new BoolType();
	}

	@Override
	public Type caseAstTypeFloat(AstTypeFloat type) {
		return new FloatType();
	}

	@Override
	public Type caseAstTypeInt(AstTypeInt type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = new AstExpressionEvaluator().evaluateAsInteger(astSize);
		}
		return new IntType(size);
	}

	@Override
	public Type caseAstTypeList(AstTypeList listType) {
		Type type = transformType(listType.getType());
		AstExpression expression = listType.getSize();
		int size = new AstExpressionEvaluator().evaluateAsInteger(expression);
		return new ListType(size, type);
	}

	@Override
	public Type caseAstTypeString(AstTypeString type) {
		return new StringType();
	}

	@Override
	public Type caseAstTypeUint(AstTypeUint type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = new AstExpressionEvaluator().evaluateAsInteger(astSize);
		}
		return new UintType(size);
	}

	/**
	 * Transforms the given AST type to an IR type.
	 * 
	 * @param type
	 *            an AST type
	 * @return an IR type
	 */
	public Type transformType(AstType type) {
		return doSwitch(type);
	}

}
