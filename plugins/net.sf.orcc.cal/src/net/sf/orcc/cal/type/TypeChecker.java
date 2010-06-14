/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIf;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionList;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.frontend.Util;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.StringType;

/**
 * This class defines a type checker for RVC-CAL AST.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeChecker extends CalSwitch<Type> {

	private TypeTransformer typeTransformer;

	/**
	 * Creates a new type checker with the given type transformer.
	 * 
	 * @param typeTransformer
	 *            an AST type to IR type transformer
	 */
	public TypeChecker(TypeTransformer typeTransformer) {
		this.typeTransformer = typeTransformer;
	}

	public boolean areTypeCompatible(Type type) {
		return true;
	}

	@Override
	public Type caseAstExpressionBinary(AstExpressionBinary expression) {
		return new IntType(32);
		/*
		 * BinaryOp op = BinaryOp.getOperator(expression.getOperator()); Type t1
		 * = getType(expression.getLeft()); Type t2 =
		 * getType(expression.getRight());
		 * 
		 * switch (op) { case BITAND: if (val1 instanceof Integer && val2
		 * instanceof Integer) { int i1 = (Integer) val1; int i2 = (Integer)
		 * val2; return i1 & i2; } break; case BITOR: if (val1 instanceof
		 * Integer && val2 instanceof Integer) { int i1 = (Integer) val1; int i2
		 * = (Integer) val2; return i1 | i2; } break; case BITXOR: if (val1
		 * instanceof Integer && val2 instanceof Integer) { int i1 = (Integer)
		 * val1; int i2 = (Integer) val2; return i1 ^ i2; } break; case DIV: if
		 * (val1 instanceof Integer && val2 instanceof Integer) { int i1 =
		 * (Integer) val1; int i2 = (Integer) val2; return i1 / i2; } break;
		 * case DIV_INT: if (val1 instanceof Integer && val2 instanceof Integer)
		 * { int i1 = (Integer) val1; int i2 = (Integer) val2; return i1 / i2; }
		 * break; case EQ: if (val1 instanceof Integer && val2 instanceof
		 * Integer) { int i1 = (Integer) val1; int i2 = (Integer) val2; return
		 * i1 == i2; } else if (val1 instanceof Boolean && val2 instanceof
		 * Boolean) { boolean b1 = (Boolean) val1; boolean b2 = (Boolean) val2;
		 * return b1 == b2; } break; case EXP: break; case GE: if (val1
		 * instanceof Integer && val2 instanceof Integer) { int i1 = (Integer)
		 * val1; int i2 = (Integer) val2; return i1 >= i2; } break; case GT: if
		 * (val1 instanceof Integer && val2 instanceof Integer) { int i1 =
		 * (Integer) val1; int i2 = (Integer) val2; return i1 > i2; } break;
		 * case LOGIC_AND: if (val1 instanceof Boolean && val2 instanceof
		 * Boolean) { boolean b1 = (Boolean) val1; boolean b2 = (Boolean) val2;
		 * return b1 && b2; } break; case LE: if (val1 instanceof Integer &&
		 * val2 instanceof Integer) { int i1 = (Integer) val1; int i2 =
		 * (Integer) val2; return i1 <= i2; } break; case LOGIC_OR: if (val1
		 * instanceof Boolean && val2 instanceof Boolean) { boolean b1 =
		 * (Boolean) val1; boolean b2 = (Boolean) val2; return b1 || b2; }
		 * break; case LT: if (val1 instanceof Integer && val2 instanceof
		 * Integer) { int i1 = (Integer) val1; int i2 = (Integer) val2; return
		 * i1 < i2; } break; case MINUS: if (val1 instanceof Integer && val2
		 * instanceof Integer) { int i1 = (Integer) val1; int i2 = (Integer)
		 * val2; return i1 - i2; } break; case MOD: if (val1 instanceof Integer
		 * && val2 instanceof Integer) { int i1 = (Integer) val1; int i2 =
		 * (Integer) val2; return i1 % i2; } break; case NE: if (val1 instanceof
		 * Integer && val2 instanceof Integer) { int i1 = (Integer) val1; int i2
		 * = (Integer) val2; return i1 != i2; } break; case PLUS: if (val1
		 * instanceof Integer && val2 instanceof Integer) { int i1 = (Integer)
		 * val1; int i2 = (Integer) val2; return i1 + i2; } break; case
		 * SHIFT_LEFT: if (val1 instanceof Integer && val2 instanceof Integer) {
		 * int i1 = (Integer) val1; int i2 = (Integer) val2; return i1 << i2; }
		 * break; case SHIFT_RIGHT: if (val1 instanceof Integer && val2
		 * instanceof Integer) { int i1 = (Integer) val1; int i2 = (Integer)
		 * val2; return i1 >> i2; } break; case TIMES: if (val1 instanceof
		 * Integer && val2 instanceof Integer) { int i1 = (Integer) val1; int i2
		 * = (Integer) val2; return i1 * i2; } break; }
		 * 
		 * throw new OrccRuntimeException("Uninitialized variable at line " +
		 * Util.getLocation(expression).getStartLine() +
		 * "\nCould not evaluate binary expression " + op.toString() + "(" +
		 * op.getText() + ")\n");
		 */
	}

	@Override
	public Type caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return new BoolType();
	}

	@Override
	public Type caseAstExpressionCall(AstExpressionCall expression) {
		return new IntType(32);
	}

	@Override
	public Type caseAstExpressionIf(AstExpressionIf expression) {
		return new IntType(32);
	}

	@Override
	public Type caseAstExpressionIndex(AstExpressionIndex expression) {
		return new IntType(32);
	}

	@Override
	public Type caseAstExpressionInteger(AstExpressionInteger expression) {
		return new IntType(getSize(expression.getValue()));
	}

	@Override
	public Type caseAstExpressionList(AstExpressionList expression) {
		return new IntType(32);
	}

	@Override
	public Type caseAstExpressionString(AstExpressionString expression) {
		return new StringType();
	}

	@Override
	public Type caseAstExpressionUnary(AstExpressionUnary expression) {
		UnaryOp op = UnaryOp.getOperator(expression.getUnaryOperator());
		Type type = getType(expression.getExpression());

		switch (op) {
		case BITNOT:
			if (!(type.isInt() || type.isUint())) {
				throw new TypeMismatchException("Cannot convert " + type
						+ " to int/uint", expression.getExpression());
			}
			return type;
		case LOGIC_NOT:
			if (!type.isBool()) {
				throw new TypeMismatchException("Cannot convert " + type
						+ " to boolean", expression.getExpression());
			}
			return type;
		case MINUS:
			if (!type.isInt()) {
				throw new TypeMismatchException("Cannot convert " + type
						+ " to int", expression.getExpression());
			}
			return type;
		case NUM_ELTS:
		default:
			throw new OrccRuntimeException("Not implemented yet");
		}
	}

	@Override
	public Type caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		return typeTransformer.transformType(variable.getType());
	}

	@Override
	public Type caseAstGenerator(AstGenerator expression) {
		throw new OrccRuntimeException(Util.getLocation(expression),
				"TODO generator");
	}

	/**
	 * Returns the size in bits needed to store the given number.
	 * 
	 * @param number
	 *            a number
	 * @return the size in bits
	 */
	private int getSize(int number) {
		return (int) Math.floor(Math.log(number) / Math.log(2)) + 1;
	}

	/**
	 * Computes and returns the type of the given expression.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return a type
	 */
	public Type getType(AstExpression expression) {
		return doSwitch(expression);
	}

}
