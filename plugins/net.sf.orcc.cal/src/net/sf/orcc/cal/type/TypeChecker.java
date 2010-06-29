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

import java.util.List;

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
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.validation.CalJavaValidator;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.UnaryOp;

/**
 * This class defines a type checker for RVC-CAL AST. Note that types must have
 * been transformed to IR types first.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeChecker extends CalSwitch<Type> {

	/**
	 * Creates a new type checker.
	 */
	public TypeChecker() {
	}

	/**
	 * Returns <code>true</code> if the two given types are compatible.
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return <code>true</code> if the two given types are compatible
	 */
	public boolean areTypeCompatible(Type t1, Type t2) {
		if (t1 == null || t2 == null) {
			return false;
		}

		return getLub(t1, t2) != null;
	}

	@Override
	public Type caseAstExpressionBinary(AstExpressionBinary expression) {
		BinaryOp op = BinaryOp.getOperator(expression.getOperator());
		Type t1 = getType(expression.getLeft());
		Type t2 = getType(expression.getRight());

		if (t1 == null || t2 == null) {
			return null;
		}

		switch (op) {
		case BITAND:
		case MOD:
		case SHIFT_RIGHT:
			if (!t1.isInt() && !t1.isUint()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t1 + " to int/uint", expression,
						CalPackage.AST_EXPRESSION_BINARY__LEFT);
				return null;
			}
			if (!t2.isInt() && !t2.isUint()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t2 + " to int/uint", expression,
						CalPackage.AST_EXPRESSION_BINARY__RIGHT);
				return null;
			}
			return t2;

		case BITOR:
		case BITXOR:
		case MINUS:
		case TIMES:
			if (!t1.isInt() && !t1.isUint()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t1 + " to int/uint", expression,
						CalPackage.AST_EXPRESSION_BINARY__LEFT);
				return null;
			}
			if (!t2.isInt() && !t2.isUint()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t2 + " to int/uint", expression,
						CalPackage.AST_EXPRESSION_BINARY__RIGHT);
				return null;
			}
			return getLub(t1, t2);

		case PLUS:
			if (t1.isString()) {
				if (t2.isList()) {
					CalJavaValidator.getInstance().error(
							"Cannot convert " + t2 + " to String", expression,
							CalPackage.AST_EXPRESSION_BINARY__RIGHT);
					return null;
				} else {
					return t1;
				}
			}
			if (t2.isString()) {
				if (t1.isList()) {
					CalJavaValidator.getInstance().error(
							"Cannot convert " + t1 + " to String", expression,
							CalPackage.AST_EXPRESSION_BINARY__LEFT);
					return null;
				} else {
					return t1;
				}
			}
			if (t1.isList()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t1 + " to scalar", expression,
						CalPackage.AST_EXPRESSION_BINARY__LEFT);
				return null;
			}
			if (t2.isList()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t2 + " to scalar", expression,
						CalPackage.AST_EXPRESSION_BINARY__RIGHT);
				return null;
			}
			return getLub(t1, t2);

		case DIV:
		case DIV_INT:
		case SHIFT_LEFT:
			if (!t1.isInt() && !t1.isUint()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t1 + " to int/uint", expression,
						CalPackage.AST_EXPRESSION_BINARY__LEFT);
				return null;
			}
			if (!t2.isInt() && !t2.isUint()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t2 + " to int/uint", expression,
						CalPackage.AST_EXPRESSION_BINARY__RIGHT);
				return null;
			}
			return t1;

		case EQ:
		case GE:
		case GT:
		case LE:
		case LT:
		case NE:
			Type type = getLub(t1, t2);
			if (type == null) {
				CalJavaValidator.getInstance().error(
						"Incompatible operand types " + t1 + " and " + t2,
						expression, CalPackage.AST_EXPRESSION_BINARY);
				return null;
			}
			return IrFactory.eINSTANCE.createTypeBool();

		case EXP:
			CalJavaValidator.getInstance().error("Operator ^ not implemented",
					expression, CalPackage.AST_EXPRESSION_BINARY__OPERATOR);
			return null;

		case LOGIC_AND:
		case LOGIC_OR:
			if (!t1.isBool()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t1 + " to bool", expression,
						CalPackage.AST_EXPRESSION_BINARY__LEFT);
				return null;
			}
			if (!t2.isBool()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + t2 + " to bool", expression,
						CalPackage.AST_EXPRESSION_BINARY__RIGHT);
				return null;
			}
			return IrFactory.eINSTANCE.createTypeBool();
		}

		return null;
	}

	@Override
	public Type caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return IrFactory.eINSTANCE.createTypeBool();
	}

	@Override
	public Type caseAstExpressionCall(AstExpressionCall expression) {
		return expression.getFunction().getIrType();
	}

	@Override
	public Type caseAstExpressionIf(AstExpressionIf expression) {
		Type type = getType(expression.getCondition());
		if (type == null) {
			return null;
		}

		if (!type.isBool()) {
			CalJavaValidator.getInstance().error(
					"Cannot convert " + type + " to bool", expression,
					CalPackage.AST_EXPRESSION_IF__CONDITION);
			return null;
		}

		Type t1 = getType(expression.getThen());
		Type t2 = getType(expression.getElse());
		if (t1 == null || t2 == null) {
			return null;
		}

		type = getLub(t1, t2);
		if (type == null) {
			CalJavaValidator.getInstance().error(
					"Incompatible operand types " + t1 + " and " + t2,
					expression, CalPackage.AST_EXPRESSION_IF);
			return null;
		}

		return type;
	}

	@Override
	public Type caseAstExpressionIndex(AstExpressionIndex expression) {
		AstVariable variable = expression.getSource().getVariable();
		Type type = variable.getIrType();

		if (type == null) {
			return null;
		}

		List<AstExpression> indexes = expression.getIndexes();

		for (AstExpression index : indexes) {
			Type subType = getType(index);
			if (type.isList()) {
				if (subType.isInt() || subType.isUint()) {
					type = ((TypeList) type).getType();
				} else {
					CalJavaValidator.getInstance().error(
							"index must be an integer", index,
							CalPackage.AST_EXPRESSION);
				}
			} else {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + type + " to List", expression,
						CalPackage.AST_EXPRESSION_INDEX__SOURCE);
				return null;
			}
		}

		return type;
	}

	@Override
	public Type caseAstExpressionInteger(AstExpressionInteger expression) {
		return IrFactory.eINSTANCE.createTypeInt(getSize(expression
				.getValue()));
	}

	@Override
	public Type caseAstExpressionList(AstExpressionList expression) {
		return IrFactory.eINSTANCE.createTypeList(0, getType(expression
				.getExpressions().get(0)));
	}

	@Override
	public Type caseAstExpressionString(AstExpressionString expression) {
		TypeString type = IrFactory.eINSTANCE.createTypeString();
		type.setSize(expression.getValue().length());
		return type;
	}

	@Override
	public Type caseAstExpressionUnary(AstExpressionUnary expression) {
		UnaryOp op = UnaryOp.getOperator(expression.getUnaryOperator());
		Type type = getType(expression.getExpression());
		if (type == null) {
			return null;
		}

		switch (op) {
		case BITNOT:
			if (!(type.isInt() || type.isUint())) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + type + " to int/uint", expression,
						CalPackage.AST_EXPRESSION_UNARY__EXPRESSION);
				return null;
			}
			return type;
		case LOGIC_NOT:
			if (!type.isBool()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + type + " to boolean", expression,
						CalPackage.AST_EXPRESSION_UNARY__EXPRESSION);
				return null;
			}
			return type;
		case MINUS:
			if (type.isUint()) {
				return IrFactory.eINSTANCE.createTypeInt(((TypeUint) type)
						.getSize());
			}
			if (!type.isInt()) {
				CalJavaValidator.getInstance().error(
						"Cannot convert " + type + " to int", expression,
						CalPackage.AST_EXPRESSION_UNARY__EXPRESSION);
				return null;
			}
			return type;
		case NUM_ELTS:
		default:
			CalJavaValidator.getInstance().error("Not implemented yet",
					expression, CalPackage.AST_EXPRESSION_UNARY__EXPRESSION);
			return null;
		}
	}

	@Override
	public Type caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		return variable.getIrType();
	}

	@Override
	public Type caseAstGenerator(AstGenerator expression) {
		CalJavaValidator.getInstance().error("cannot evaluate generator",
				expression, CalPackage.AST_GENERATOR);
		return null;
	}

	/**
	 * Returns the Least Upper Bound of the given types.
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return the Least Upper Bound of the given types
	 */
	public Type getLub(Type t1, Type t2) {
		if (t1.isBool() && t2.isBool()) {
			return t1;
		} else if (t1.isFloat() && t2.isFloat()) {
			return t1;
		} else if (t1.isString() && t2.isString()) {
			return t1;
		} else if (t1.isInt() && t2.isInt()) {
			return IrFactory.eINSTANCE.createTypeInt(Math.max(
					((TypeInt) t1).getSize(), ((TypeInt) t2).getSize()));
		} else if (t1.isList() && t2.isList()) {
			return getLub(((TypeList) t1).getType(), ((TypeList) t2).getType());
		} else if (t1.isUint() && t2.isUint()) {
			return IrFactory.eINSTANCE.createTypeInt(Math.max(
					((TypeUint) t1).getSize(), ((TypeUint) t2).getSize()));
		} else if (t1.isInt() && t2.isUint()) {
			int si = ((TypeInt) t1).getSize();
			int su = ((TypeUint) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(si);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			}
		} else if (t1.isUint() && t2.isInt()) {
			int su = ((TypeUint) t1).getSize();
			int si = ((TypeInt) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(si);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			}
		} else {
			return null;
		}
	}

	/**
	 * Returns the size in bits needed to store the given number as an int.
	 * 
	 * @param number
	 *            a number
	 * @return the size in bits needed to store the given number as an int
	 */
	private int getSize(int number) {
		int v;
		if (number >= 0) {
			v = number + 1;
		} else {
			v = -number;
		}

		int size = (int) Math.ceil(Math.log(v) / Math.log(2)) + 1;
		return size;
	}

	/**
	 * Computes and returns the type of the given expression.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return a type
	 */
	public Type getType(AstExpression expression) {
		Type type = expression.getIrType();
		if (type == null) {
			type = doSwitch(expression);
			expression.setIrType(type);
		}

		return type;
	}

}
