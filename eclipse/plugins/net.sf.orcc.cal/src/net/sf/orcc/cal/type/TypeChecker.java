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

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionFloat;
import net.sf.orcc.cal.cal.AstExpressionIf;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionList;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.AstExpressionEvaluator;
import net.sf.orcc.cal.validation.CalJavaValidator;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.TypeUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a type checker for RVC-CAL AST. Note that types must have
 * been transformed to IR types first.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeChecker extends CalSwitch<Type> {

	private static enum Unification {
		GLB, LUB, LUB_PLUS_1, LUB_SUM_SIZE
	}

	private int maxSize;

	private CalJavaValidator validator;

	/**
	 * Creates a new type checker.
	 */
	public TypeChecker(CalJavaValidator validator) {
		this.validator = validator;
	}

	@Override
	public Type caseAstExpressionBinary(AstExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Type t1 = getType(expression.getLeft());
		Type t2 = getType(expression.getRight());
		return getTypeBinary(op, t1, t2, expression,
				eINSTANCE.getAstExpressionBinary_Operator(), -1);
	}

	@Override
	public Type caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return IrFactory.eINSTANCE.createTypeBool();
	}

	@Override
	public Type caseAstExpressionCall(AstExpressionCall astCall) {
		AstFunction function = astCall.getFunction();
		TypeTransformer transformer = new TypeTransformer();
		Type type = transformer.doSwitch(function);

		String name = function.getName();
		List<AstExpression> parameters = astCall.getParameters();
		if (function.getParameters().size() != parameters.size()) {
			error("function " + name + " takes "
					+ function.getParameters().size() + " arguments.", astCall,
					eINSTANCE.getAstExpressionCall_Function(), -1);
			return null;
		}

		Iterator<AstVariable> itFormal = function.getParameters().iterator();
		Iterator<AstExpression> itActual = parameters.iterator();
		int index = 0;
		while (itFormal.hasNext() && itActual.hasNext()) {
			Type formalType = transformer.doSwitch(itFormal.next());
			AstExpression expression = itActual.next();
			Type actualType = getType(expression);

			// check types
			if (!TypeUtil.isConvertibleTo(actualType, formalType)) {
				error("Type mismatch: cannot convert from " + actualType
						+ " to " + formalType, astCall,
						eINSTANCE.getAstExpressionCall_Parameters(), index);
			}
			index++;
		}

		return type;
	}

	@Override
	public Type caseAstExpressionFloat(AstExpressionFloat expression) {
		return IrFactory.eINSTANCE.createTypeFloat();
	}

	@Override
	public Type caseAstExpressionIf(AstExpressionIf expression) {
		Type type = getType(expression.getCondition());
		if (type == null) {
			return null;
		}

		if (!type.isBool()) {
			error("Cannot convert " + type + " to bool", expression,
					eINSTANCE.getAstExpressionIf_Condition(), -1);
			return null;
		}

		Type t1 = getType(expression.getThen());
		Type t2 = getType(expression.getElse());
		if (t1 == null || t2 == null) {
			return null;
		}

		type = TypeUtil.getLub(t1, t2);
		if (type == null) {
			error("Incompatible operand types " + t1 + " and " + t2,
					expression, eINSTANCE.getAstExpressionIf_Condition(), -1);
			return null;
		}

		return type;
	}

	@Override
	public Type caseAstExpressionIndex(AstExpressionIndex expression) {
		AstVariable variable = expression.getSource().getVariable();
		TypeTransformer transformer = new TypeTransformer();
		Type type = transformer.doSwitch(variable);

		List<AstExpression> indexes = expression.getIndexes();
		int errorIdx = 0;
		for (AstExpression index : indexes) {
			Type subType = getType(index);
			if (type.isList()) {
				if (subType != null && (subType.isInt() || subType.isUint())) {
					type = ((TypeList) type).getType();
				} else {
					error("index must be an integer", index,
							eINSTANCE.getAstExpressionIndex_Indexes(), errorIdx);
				}
			} else {
				error("Cannot convert " + type + " to List", expression,
						eINSTANCE.getAstExpressionIndex_Source(), -1);
				return null;
			}
			errorIdx++;
		}

		return type;
	}

	@Override
	public Type caseAstExpressionInteger(AstExpressionInteger expression) {
		return IrFactory.eINSTANCE.createTypeInt(TypeUtil.getSize(expression
				.getValue()));
	}

	@Override
	public Type caseAstExpressionList(AstExpressionList expression) {
		List<AstExpression> expressions = expression.getExpressions();

		int size = 1;

		// size of generators
		for (AstGenerator generator : expression.getGenerators()) {
			// getType(generator.getLower());
			// getType(generator.getHigher());

			int lower = new AstExpressionEvaluator(validator)
					.evaluateAsInteger(generator.getLower());
			int higher = new AstExpressionEvaluator(validator)
					.evaluateAsInteger(generator.getHigher());
			size *= (higher - lower) + 1;
		}

		// size of expressions
		size *= expressions.size();

		Type type = getType(expressions);
		return IrFactory.eINSTANCE.createTypeList(size, type);
	}

	@Override
	public Type caseAstExpressionString(AstExpressionString expression) {
		TypeString type = IrFactory.eINSTANCE.createTypeString();
		type.setSize(expression.getValue().length());
		return type;
	}

	@Override
	public Type caseAstExpressionUnary(AstExpressionUnary expression) {
		OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());
		Type type = getType(expression.getExpression());
		if (type == null) {
			return null;
		}

		switch (op) {
		case BITNOT:
			if (!(type.isInt() || type.isUint())) {
				error("Cannot convert " + type + " to int/uint", expression,
						eINSTANCE.getAstExpressionUnary_Expression(), -1);
				return null;
			}
			return type;
		case LOGIC_NOT:
			if (!type.isBool()) {
				error("Cannot convert " + type + " to boolean", expression,
						eINSTANCE.getAstExpressionUnary_Expression(), -1);
				return null;
			}
			return type;
		case MINUS:
			if (type.isUint()) {
				return IrFactory.eINSTANCE.createTypeInt(((TypeUint) type)
						.getSize());
			}
			if (!type.isInt()) {
				error("Cannot convert " + type + " to int", expression,
						eINSTANCE.getAstExpressionUnary_Expression(), -1);
				return null;
			}
			return type;
		case NUM_ELTS:
			if (!type.isList()) {
				error("Cannot convert " + type + " to List", expression,
						eINSTANCE.getAstExpressionUnary_Expression(), -1);
				return null;
			}
			TypeList listType = (TypeList) type;
			return IrFactory.eINSTANCE.createTypeInt(TypeUtil.getSize(listType
					.getSize()));
		default:
			error("Unknown unary operator", expression,
					eINSTANCE.getAstExpressionUnary_Expression(), -1);
			return null;
		}
	}

	@Override
	public Type caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		TypeTransformer transformer = new TypeTransformer();
		return transformer.doSwitch(variable);
	}

	@Override
	public Type caseAstGenerator(AstGenerator expression) {
		error("cannot evaluate generator", expression,
				eINSTANCE.getAstGenerator_Variable(), -1);
		return null;
	}

	/**
	 * Creates a new type based on the unification of t1 and t2, and clips its
	 * size to {@link #maxSize}.
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            a type
	 * @param unification
	 *            how to unify t1 and t2
	 */
	private Type createType(Type t1, Type t2, Unification unification) {
		Type type = null;
		int size = 0;

		switch (unification) {
		case GLB:
			type = TypeUtil.getGlb(t1, t2);
			size = type.getSizeInBits();
			break;

		case LUB:
			type = TypeUtil.getLub(t1, t2);
			size = type.getSizeInBits();
			break;

		case LUB_PLUS_1:
			type = TypeUtil.getLub(t1, t2);
			size = type.getSizeInBits() + 1;
			break;

		case LUB_SUM_SIZE:
			type = TypeUtil.getLub(t1, t2);
			size = t1.getSizeInBits() + t2.getSizeInBits();
			break;
		}

		// clips the size
		if (size > maxSize) {
			size = maxSize;
		}

		if (type.isInt()) {
			TypeInt typeInt = (TypeInt) type;
			typeInt.setSize(size);
		} else if (type.isUint()) {
			TypeUint typeUint = (TypeUint) type;
			typeUint.setSize(size);
		}

		return type;
	}

	/**
	 * Wrapper call to {@link CalJavaValidator#error(String, EObject, Integer)}.
	 * 
	 * @param string
	 *            error message
	 * @param source
	 *            source object
	 * @param feature
	 *            feature of the object that caused the error
	 */
	private void error(String string, EObject source,
			EStructuralFeature feature, int index) {
		if (validator != null) {
			validator.error(string, source, feature, index);
		}
	}

	/**
	 * Returns the type necessary to hold the index that contains (directly or
	 * indirectly) the given expression. For instance suppose a list L with a
	 * type List(type:List(type:int, size=4), size=150), then in L[a * 3][b],
	 * the expression "a" will be constrained to uint(size=8), and "b" will be
	 * constrained to uint(size=2) (the index goes from 0 to 3 at most).
	 * 
	 * @param reference
	 *            a reference to a variable whose type is supposed to be a list
	 * @param indexes
	 *            a list of indexes as expressions
	 * @param expression
	 *            the expression that is a child of one of the indexes
	 * @return the type that is necessary to store the index t
	 */
	private Type findIndexType(AstVariableReference reference,
			List<AstExpression> indexes, AstExpression expression) {
		AstVariable variable = reference.getVariable();
		if (variable == null) {
			return null;
		}

		TypeTransformer transformer = new TypeTransformer();
		Type type = transformer.doSwitch(variable);
		List<Expression> dimensions = type.getDimensionsExpr();

		Iterator<Expression> itD = dimensions.iterator();
		Iterator<AstExpression> itI = indexes.iterator();
		while (itD.hasNext() && itI.hasNext()) {
			Expression dim = itD.next();
			if (dim == null) {
				// no index size: assume 32 bits
				dim = IrFactory.eINSTANCE.createExprInt(32);
			}

			AstExpression index = itI.next();
			if (EcoreUtil.isAncestor(index, expression)) {
				// index goes from 0 to dim - 1
				int indexSize = TypeUtil.getSize(new ExpressionEvaluator()
						.evaluateAsInteger(dim) - 1);
				return IrFactory.eINSTANCE.createTypeInt(indexSize);
			}
		}

		return null;
	}

	/**
	 * Finds the type of the formal parameter that corresponds to the given
	 * expression in the actual parameters.
	 * 
	 * @param formalParameters
	 *            formal parameters
	 * @param actualParameters
	 *            actual parameters
	 * @param expression
	 *            an expression
	 * @return the type of the formal parameter, or <code>null</code>
	 */
	private Type findParameter(List<AstVariable> formalParameters,
			List<AstExpression> actualParameters, AstExpression expression) {
		Iterator<AstVariable> itF = formalParameters.iterator();
		Iterator<AstExpression> itA = actualParameters.iterator();
		while (itF.hasNext() && itA.hasNext()) {
			AstVariable formal = itF.next();
			AstExpression actual = itA.next();
			if (actual == expression) {
				TypeTransformer transformer = new TypeTransformer();
				return transformer.doSwitch(formal);
			}
		}

		return null;
	}

	/**
	 * Computes and returns the type of the given expression.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return a type
	 */
	public Type getType(AstExpression expression) {
		if (expression == null) {
			return null;
		}

		TypeChecker checker = new TypeChecker(validator);
		checker.setTargetType(expression);
		return checker.doSwitch(expression);
	}

	/**
	 * Computes and returns the type that is the Least Upper Bound of the types
	 * for the given expressions.
	 * 
	 * @param expressions
	 *            a list of expressions
	 * @return the common type to the given expressions
	 */
	public Type getType(List<AstExpression> expressions) {
		Iterator<AstExpression> it = expressions.iterator();
		if (it.hasNext()) {
			AstExpression expression = it.next();
			Type t1 = getType(expression);
			while (it.hasNext()) {
				expression = it.next();
				Type t2 = getType(expression);
				t1 = TypeUtil.getLub(t1, t2);
			}
			return t1;
		}

		return null;
	}

	/**
	 * Returns the type for an addition whose left operand has type t1 and right
	 * operand has type t2. Result has type String if t1 or t2 is a String,
	 * lub(t1, t2) + 1 for integers (signed or not), and lub(t1, t2) for other
	 * types.
	 * 
	 * @param t1
	 *            type of left operand
	 * @param t2
	 *            type of right operand
	 * @param source
	 *            source object
	 * @param feature
	 *            feature
	 * @return type of the addition
	 */
	private Type getTypeAdd(Type t1, Type t2, EObject source,
			EStructuralFeature feature, int index) {
		if (t1.isString()) {
			if (t2.isList()) {
				error("Cannot convert " + t2 + " to String", source, feature,
						index);
				return null;
			} else {
				return t1;
			}
		}

		if (t2.isString()) {
			if (t1.isList()) {
				error("Cannot convert " + t1 + " to String", source, feature,
						index);
				return null;
			} else {
				return t1;
			}
		}

		if (t1.isBool() || t2.isBool()) {
			error("Addition is not defined for booleans", source, feature,
					index);
			return null;
		}

		return createType(t1, t2, Unification.LUB_PLUS_1);
	}

	/**
	 * Returns the type of a binary expression whose left operand has type t1
	 * and right operand has type t2, and whose operator is given.
	 * 
	 * @param op
	 *            operator
	 * @param t1
	 *            type of the first operand
	 * @param t2
	 *            type of the second operand
	 * @param source
	 *            source object
	 * @param feature
	 *            feature
	 * @return the type of the binary expression, or <code>null</code>
	 */
	private Type getTypeBinary(OpBinary op, Type t1, Type t2, EObject source,
			EStructuralFeature feature, int index) {
		if (t1 == null || t2 == null) {
			return null;
		}

		switch (op) {
		case BITAND:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + t1 + " to int/uint", source, feature,
						index);
				return null;
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + t2 + " to int/uint", source, feature,
						index);
				return null;
			}
			return TypeUtil.getGlb(t1, t2);

		case BITOR:
		case BITXOR:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + t1 + " to int/uint", source, feature,
						index);
				return null;
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + t2 + " to int/uint", source, feature,
						index);
				return null;
			}
			return TypeUtil.getLub(t1, t2);

		case TIMES:
			return getTypeTimes(t1, t2, source, feature, index);

		case MINUS:
			return getTypeMinus(t1, t2, source, feature, index);

		case PLUS:
			return getTypeAdd(t1, t2, source, feature, index);

		case DIV:
		case DIV_INT:
		case SHIFT_RIGHT:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + t1 + " to int/uint", source, feature,
						index);
				return null;
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + t2 + " to int/uint", source, feature,
						index);
				return null;
			}
			return t1;

		case MOD:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + t1 + " to int/uint", source, feature,
						index);
				return null;
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + t2 + " to int/uint", source, feature,
						index);
				return null;
			}
			return t2;

		case SHIFT_LEFT:
			return getTypeShiftLeft(t1, t2, source, feature, index);

		case EQ:
		case GE:
		case GT:
		case LE:
		case LT:
		case NE:
			Type type = TypeUtil.getLub(t1, t2);
			if (type == null) {
				error("Incompatible operand types " + t1 + " and " + t2,
						source, feature, index);
				return null;
			}
			return IrFactory.eINSTANCE.createTypeBool();

		case EXP:
			error("Operator ** not implemented", source, feature, index);
			return null;

		case LOGIC_AND:
		case LOGIC_OR:
			if (!t1.isBool()) {
				error("Cannot convert " + t1 + " to bool", source, feature,
						index);
				return null;
			}
			if (!t2.isBool()) {
				error("Cannot convert " + t2 + " to bool", source, feature,
						index);
				return null;
			}
			return IrFactory.eINSTANCE.createTypeBool();
		}

		return null;
	};

	/**
	 * Returns the type for a subtraction whose left operand has type t1 and
	 * right operand has type t2. Result has lub(t1, t2) + 1 for integers.
	 * 
	 * @param t1
	 *            type of left operand
	 * @param t2
	 *            type of right operand
	 * @param source
	 *            source object
	 * @param feature
	 *            feature
	 * @return type of the subtraction
	 */
	private Type getTypeMinus(Type t1, Type t2, EObject source,
			EStructuralFeature feature, int index) {
		if (!t1.isInt() && !t1.isUint()) {
			error("Cannot convert " + t1 + " to int/uint", source, feature,
					index);
			return null;
		}
		if (!t2.isInt() && !t2.isUint()) {
			error("Cannot convert " + t2 + " to int/uint", source, feature,
					index);
			return null;
		}

		return createType(t1, t2, Unification.LUB_PLUS_1);
	}

	/**
	 * Returns the type for a left shift whose left operand has type t1 and
	 * right operand has type t2.
	 * 
	 * @param t1
	 *            type of left operand
	 * @param t2
	 *            type of right operand
	 * @param source
	 *            source object
	 * @param feature
	 *            feature
	 * @return type of the left shift
	 */
	private Type getTypeShiftLeft(Type t1, Type t2, EObject source,
			EStructuralFeature feature, int index) {
		int s1;
		if (t1.isInt()) {
			s1 = ((TypeInt) t1).getSize();
		} else if (t1.isUint()) {
			s1 = ((TypeUint) t1).getSize();
		} else {
			error("Cannot convert " + t1 + " to int/uint", source, feature,
					index);
			return null;
		}

		int shift;
		if (t2.isInt()) {
			// shift is unsigned, so we do not take the bit sign into account
			shift = ((TypeInt) t2).getSize() - 1;
		} else if (t2.isUint()) {
			shift = ((TypeUint) t2).getSize();
		} else {
			error("Cannot convert " + t2 + " to int/uint", source, feature,
					index);
			return null;
		}

		int size;
		// 1 << 6 = 64
		if (shift >= 6) {
			size = maxSize;
		} else {
			size = s1 + (1 << shift) - 1;
			if (size > maxSize) {
				size = maxSize;
			}
		}

		return IrFactory.eINSTANCE.createTypeInt(size);
	}

	/**
	 * Returns the type for a multiplication whose left operand has type t1 and
	 * right operand has type t2. Result has size(t1) + size(t2) for integers.
	 * 
	 * @param t1
	 *            type of left operand
	 * @param t2
	 *            type of right operand
	 * @param source
	 *            source object
	 * @param feature
	 *            feature
	 * @return type of the multiplication
	 */
	private Type getTypeTimes(Type t1, Type t2, EObject source,
			EStructuralFeature feature, int index) {
		if (!t1.isInt() && !t1.isUint()) {
			error("Cannot convert " + t1 + " to int/uint", source, feature,
					index);
			return null;
		}
		if (!t2.isInt() && !t2.isUint()) {
			error("Cannot convert " + t2 + " to int/uint", source, feature,
					index);
			return null;
		}

		return createType(t1, t2, Unification.LUB_SUM_SIZE);
	}

	/**
	 * Finds the target type of the container of the given expression, and sets
	 * the maxSize field from it. If no target is found, set maxSize to 32. The
	 * container is the direct container of the expression.
	 * 
	 * @param expression
	 *            an expression
	 */
	private void setTargetType(AstExpression expression) {
		TypeTransformer transformer = new TypeTransformer();
		EObject cter = expression.eContainer();
		Type targetType = null;

		if (cter != null) {
			switch (cter.eClass().getClassifierID()) {
			case CalPackage.AST_EXPRESSION_CALL: {
				AstExpressionCall call = (AstExpressionCall) cter;
				List<AstVariable> formal = call.getFunction().getParameters();
				List<AstExpression> actual = call.getParameters();
				targetType = findParameter(formal, actual, expression);
				break;
			}

			case CalPackage.AST_EXPRESSION_INDEX: {
				AstExpressionIndex index = (AstExpressionIndex) cter;
				targetType = findIndexType(index.getSource(),
						index.getIndexes(), expression);
				break;
			}

			case CalPackage.AST_FUNCTION:
				AstFunction func = (AstFunction) cter;
				targetType = transformer.doSwitch(func);
				break;

			case CalPackage.AST_GENERATOR:
				AstGenerator generator = (AstGenerator) cter;
				targetType = transformer.doSwitch(generator.getVariable());
				break;

			case CalPackage.AST_OUTPUT_PATTERN:
				AstOutputPattern pattern = (AstOutputPattern) cter;
				targetType = transformer.doSwitch(pattern.getPort());
				break;

			case CalPackage.AST_STATEMENT_ASSIGN: {
				AstStatementAssign assign = (AstStatementAssign) cter;
				if (expression.eContainer() == assign.getValue()) {
					// expression is located in the value
					targetType = transformer.doSwitch(assign.getTarget()
							.getVariable());
				} else {
					// expression is located in the indexes
					targetType = findIndexType(assign.getTarget(),
							assign.getIndexes(), expression);
				}
				break;
			}

			case CalPackage.AST_STATEMENT_CALL: {
				AstStatementCall call = (AstStatementCall) cter;
				List<AstVariable> formal = call.getProcedure().getParameters();
				List<AstExpression> actual = call.getParameters();
				targetType = findParameter(formal, actual, expression);
				break;
			}

			case CalPackage.AST_STATEMENT_FOREACH:
				AstStatementForeach foreach = (AstStatementForeach) cter;
				targetType = transformer.doSwitch(foreach.getVariable());
				break;

			case CalPackage.AST_VARIABLE:
				AstVariable variable = (AstVariable) cter;
				targetType = transformer.doSwitch(variable);
				break;
			}
		}

		if (targetType == null) {
			// in expressions contained in other expressions, and in if & while
			// conditions, guard expressions, calls to built-in functions
			maxSize = 32;
		} else {
			maxSize = targetType.getSizeInBits();
		}
	}

}
