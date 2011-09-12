/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.cal.services;

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionElsif;
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
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.util.CalSwitch;
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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a typer for RVC-CAL AST. Note that types must have been
 * transformed to IR types first.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Typer extends CalSwitch<Type> {

	private static enum Unification {
		GLB, LUB, LUB_PLUS_1, LUB_SUM_SIZE
	}

	/**
	 * Returns the type of the given object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public static Type getType(EObject eObject) {
		Resource resource = eObject.eResource();
		Type type;
		if (resource == null) {
			type = new Typer().doSwitch(eObject);
		} else {
			Cache cache = CacheManager.instance.getCache(resource.getURI());

			URI uri = EcoreUtil.getURI(eObject);
			String fragment = uri.fragment();
			type = cache.getTypesMap().get(fragment);

			if (type == null) {
				type = new Typer().doSwitch(eObject);
				if (type != null) {
					cache.getTypes().add(type);
					cache.getTypesMap().put(fragment, type);
				}
			}
		}

		return type;
	}

	/**
	 * Returns the type of the given list of objects using their URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public static Type getType(List<? extends EObject> eObjects) {
		Iterator<? extends EObject> it = eObjects.iterator();
		if (!it.hasNext()) {
			return null;
		}

		Type type = getType(it.next());
		while (it.hasNext()) {
			type = TypeUtil.getLub(type, getType(it.next()));
		}

		return type;
	}

	private Type boundType;

	@Override
	public Type caseAstExpressionBinary(AstExpressionBinary expression) {
		setTargetType(expression);

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
		Type type = getType(function);
		return EcoreUtil.copy(type);
	}

	@Override
	public Type caseAstExpressionElsif(AstExpressionElsif expression) {
		Type type = getType(expression.getThen());
		return EcoreUtil.copy(type);
	}

	@Override
	public Type caseAstExpressionFloat(AstExpressionFloat expression) {
		return IrFactory.eINSTANCE.createTypeFloat();
	}

	@Override
	public Type caseAstExpressionIf(AstExpressionIf expression) {
		Type type = getType(expression.getCondition());
		Type t1 = getType(expression.getThen());
		for (AstExpressionElsif elsif : expression.getElsifs()) {
			t1 = TypeUtil.getLub(t1, getType(elsif));
		}

		Type t2 = getType(expression.getElse());
		type = TypeUtil.getLub(t1, t2);
		return type;
	}

	@Override
	public Type caseAstExpressionIndex(AstExpressionIndex expression) {
		AstVariable variable = expression.getSource().getVariable();
		Type type = getType(variable);

		List<AstExpression> indexes = expression.getIndexes();
		for (AstExpression index : indexes) {
			Type subType = getType(index);
			if (type.isList()) {
				if (subType != null && (subType.isInt() || subType.isUint())) {
					type = ((TypeList) type).getType();
				}
			} else {
				return null;
			}
		}

		return EcoreUtil.copy(type);
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
			int lower = Evaluator.getIntValue(generator.getLower());
			int higher = Evaluator.getIntValue(generator.getHigher());
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
		case LOGIC_NOT:
			return EcoreUtil.copy(type);
		case MINUS:
			if (type.isUint()) {
				return IrFactory.eINSTANCE.createTypeInt(((TypeUint) type)
						.getSize());
			}
			return EcoreUtil.copy(type);
		case NUM_ELTS:
			if (!type.isList()) {
				return IrFactory.eINSTANCE.createTypeInt(1);
			}
			TypeList listType = (TypeList) type;
			return IrFactory.eINSTANCE.createTypeInt(TypeUtil.getSize(listType
					.getSize()));
		default:
			return null;
		}
	}

	@Override
	public Type caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		return EcoreUtil.copy(getType(variable));
	}

	@Override
	public Type caseAstFunction(AstFunction function) {
		return doSwitch(function.getType());
	}

	@Override
	public Type caseAstGenerator(AstGenerator expression) {
		return null;
	}

	@Override
	public Type caseAstPort(AstPort port) {
		return doSwitch(port.getType());
	}

	@Override
	public Type caseAstTypeBool(AstTypeBool type) {
		return IrFactory.eINSTANCE.createTypeBool();
	}

	@Override
	public Type caseAstTypeFloat(AstTypeFloat type) {
		return IrFactory.eINSTANCE.createTypeFloat();
	}

	@Override
	public Type caseAstTypeInt(AstTypeInt type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = Evaluator.getIntValue(astSize);
		}
		return IrFactory.eINSTANCE.createTypeInt(size);
	}

	@Override
	public Type caseAstTypeList(AstTypeList listType) {
		Type type = doSwitch(listType.getType());
		AstExpression expression = listType.getSize();
		Expression size = Evaluator.getValue(expression);
		size = EcoreUtil.copy(size);
		return IrFactory.eINSTANCE.createTypeList(size, type);
	}

	@Override
	public Type caseAstTypeString(AstTypeString type) {
		return IrFactory.eINSTANCE.createTypeString();
	}

	@Override
	public Type caseAstTypeUint(AstTypeUint type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = Evaluator.getIntValue(astSize);
		}

		return IrFactory.eINSTANCE.createTypeUint(size);
	};

	@Override
	public Type caseAstVariable(AstVariable variable) {
		AstType astType;
		List<AstExpression> dimensions;

		if (variable.eContainer() instanceof AstInputPattern) {
			AstInputPattern pattern = (AstInputPattern) variable.eContainer();
			astType = EcoreUtil.copy(pattern.getPort().getType());
			dimensions = new ArrayList<AstExpression>();
			AstExpression repeat = pattern.getRepeat();
			if (repeat != null) {
				dimensions.add(repeat);
			}
		} else {
			astType = EcoreUtil.copy(variable.getType());
			dimensions = variable.getDimensions();
		}

		// convert the type of the variable
		ListIterator<AstExpression> it = dimensions.listIterator(dimensions
				.size());
		while (it.hasPrevious()) {
			AstExpression expression = it.previous();

			AstTypeList newAstType = CalFactory.eINSTANCE.createAstTypeList();
			AstExpression size = EcoreUtil.copy(expression);
			newAstType.setSize(size);
			newAstType.setType(astType);

			astType = newAstType;
		}

		return doSwitch(astType);
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
			if (type != null) {
				size = type.getSizeInBits();
			}
			break;

		case LUB:
			type = TypeUtil.getLub(t1, t2);
			if (type != null) {
				size = type.getSizeInBits();
			}
			break;

		case LUB_PLUS_1:
			type = TypeUtil.getLub(t1, t2);
			if (type != null) {
				size = type.getSizeInBits() + 1;
			}
			break;

		case LUB_SUM_SIZE:
			type = TypeUtil.getLub(t1, t2);
			size = t1.getSizeInBits() + t2.getSizeInBits();
			break;
		}

		if (type != null) {
			if (type.isInt()) {
				((TypeInt) type).setSize(size);
			} else if (type.isUint()) {
				((TypeUint) type).setSize(size);
			}

			Type maxType = TypeUtil.getGlb(type, boundType);
			if (maxType != null) {
				type = maxType;
			}
		}

		return type;
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

		Type type = getType(variable);
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
				return getType(formal);
			}
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
		if (t1.isString() && !t2.isList() || t2.isString() && !t1.isList()) {
			return t1;
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
			return createType(t1, t2, Unification.GLB);

		case BITOR:
		case BITXOR:
			return createType(t1, t2, Unification.LUB);

		case TIMES:
			return createType(t1, t2, Unification.LUB_SUM_SIZE);

		case MINUS:
			return createType(t1, t2, Unification.LUB_PLUS_1);

		case PLUS:
			return getTypeAdd(t1, t2, source, feature, index);

		case DIV:
		case DIV_INT:
		case SHIFT_RIGHT:
			return EcoreUtil.copy(t1);

		case MOD:
			return EcoreUtil.copy(t2);

		case SHIFT_LEFT:
			return getTypeShiftLeft(t1, t2, source, feature, index);

		case EQ:
		case GE:
		case GT:
		case LE:
		case LT:
		case NE:
			return IrFactory.eINSTANCE.createTypeBool();

		case EXP:
			return null;

		case LOGIC_AND:
		case LOGIC_OR:
			return IrFactory.eINSTANCE.createTypeBool();
		}

		return null;
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
			s1 = 32;
		}

		int shift;
		if (t2.isInt()) {
			// shift is unsigned, so we do not take the bit sign into account
			shift = ((TypeInt) t2).getSize() - 1;
		} else if (t2.isUint()) {
			shift = ((TypeUint) t2).getSize();
		} else {
			shift = 0;
		}

		int size;
		int maxSize = boundType.getSizeInBits();

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
	 * Finds the target type of the container of the given expression, and sets
	 * the maxSize field from it. If no target is found, set maxSize to 32. The
	 * container is the direct container of the expression.
	 * 
	 * @param expression
	 *            an expression
	 */
	private void setTargetType(AstExpression expression) {
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
				targetType = getType(func);
				break;

			case CalPackage.AST_GENERATOR:
				AstGenerator generator = (AstGenerator) cter;
				targetType = getType(generator.getVariable());
				break;

			case CalPackage.AST_OUTPUT_PATTERN:
				AstOutputPattern pattern = (AstOutputPattern) cter;
				targetType = getType(pattern.getPort());
				break;

			case CalPackage.AST_STATEMENT_ASSIGN: {
				AstStatementAssign assign = (AstStatementAssign) cter;
				if (expression == assign.getValue()) {
					// expression is located in the value
					targetType = getType(assign.getTarget().getVariable());
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
				targetType = getType(foreach.getVariable());
				break;

			case CalPackage.AST_VARIABLE:
				AstVariable variable = (AstVariable) cter;
				targetType = getType(variable);
				break;
			}
		}

		if (targetType == null) {
			// in expressions contained in other expressions, and in if & while
			// conditions, guard expressions, calls to built-in functions
			boundType = IrFactory.eINSTANCE.createTypeInt(32);
		} else {
			boundType = targetType;
		}
	}

}
