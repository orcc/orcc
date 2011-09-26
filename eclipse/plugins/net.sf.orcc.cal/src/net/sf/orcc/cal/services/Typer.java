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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.ExpressionElsif;
import net.sf.orcc.cal.cal.ExpressionFloat;
import net.sf.orcc.cal.cal.ExpressionIf;
import net.sf.orcc.cal.cal.ExpressionIndex;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.OutputPattern;
import net.sf.orcc.cal.cal.StatementAssign;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.StatementForeach;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.VariableReference;
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a typer for RVC-CAL AST. Note that types must have been
 * transformed to IR types first.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Typer extends CalSwitch<Type> {

	private static class Glb implements Unification {

		public static Glb instance = new Glb();

		@Override
		public int getSize(Type t1, Type t2) {
			return 0;
		}

		@Override
		public Type getType(Type t1, Type t2) {
			return TypeUtil.getGlb(t1, t2);
		}

	}

	private static class Lub implements Unification {

		public static Lub instance = new Lub();

		protected Type type;

		@Override
		public int getSize(Type t1, Type t2) {
			return 0;
		}

		@Override
		public Type getType(Type t1, Type t2) {
			type = TypeUtil.getLub(t1, t2);
			return type;
		}

	}

	private static class LubPlus1 extends Lub {

		public static LubPlus1 instance = new LubPlus1();

		@Override
		public int getSize(Type t1, Type t2) {
			return type.getSizeInBits() + 1;
		}

	}

	private static class LubSum extends Lub {

		public static LubSum instance = new LubSum();

		@Override
		public int getSize(Type t1, Type t2) {
			return t1.getSizeInBits() + t2.getSizeInBits();
		}

	}

	private static class LubSumPow extends Lub {

		public static LubSumPow instance = new LubSumPow();

		@Override
		public int getSize(Type t1, Type t2) {
			int shift = t2.getSizeInBits();
			if (shift >= 6) {
				// limits type size to 64
				shift = 6;
			}
			return t1.getSizeInBits() + (1 << shift) - 1;
		}

	}

	private static interface Unification {

		int getSize(Type t1, Type t2);

		Type getType(Type t1, Type t2);

	}

	/**
	 * Returns the type of the given object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public static Type getType(EObject eObject) {
		return CacheManager.instance.getOrCompute(eObject, new Typer(),
				CachePackage.eINSTANCE.getCache_TypesMap(),
				CachePackage.eINSTANCE.getCache_Types());
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
	}

	@Override
	public Type caseExpressionBinary(ExpressionBinary expression) {
		setTargetType(expression);

		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Type t1 = getType(expression.getLeft());
		Type t2 = getType(expression.getRight());
		return getTypeBinary(op, t1, t2, expression,
				eINSTANCE.getExpressionBinary_Operator(), -1);
	}

	@Override
	public Type caseExpressionBoolean(ExpressionBoolean expression) {
		return IrFactory.eINSTANCE.createTypeBool();
	}

	@Override
	public Type caseExpressionCall(ExpressionCall call) {
		Function function = call.getFunction();
		Type type = getType(function);
		return EcoreUtil.copy(type);
	}

	@Override
	public Type caseExpressionElsif(ExpressionElsif expression) {
		Type type = getType(expression.getThen());
		return EcoreUtil.copy(type);
	}

	@Override
	public Type caseExpressionFloat(ExpressionFloat expression) {
		return IrFactory.eINSTANCE.createTypeFloat();
	}

	@Override
	public Type caseExpressionIf(ExpressionIf expression) {
		Type type = getType(expression.getCondition());
		Type t1 = getType(expression.getThen());
		for (ExpressionElsif elsif : expression.getElsifs()) {
			t1 = TypeUtil.getLub(t1, getType(elsif));
		}

		Type t2 = getType(expression.getElse());
		type = TypeUtil.getLub(t1, t2);
		return type;
	}

	@Override
	public Type caseExpressionIndex(ExpressionIndex expression) {
		Variable variable = expression.getSource().getVariable();
		Type type = getType(variable);

		List<AstExpression> indexes = expression.getIndexes();
		for (AstExpression index : indexes) {
			Type subType = getType(index);
			if (type != null && type.isList()) {
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
	public Type caseExpressionInteger(ExpressionInteger expression) {
		BigInteger value = BigInteger.valueOf(expression.getValue());
		return IrFactory.eINSTANCE.createTypeIntOrUint(value);
	}

	@Override
	public Type caseExpressionList(ExpressionList expression) {
		List<AstExpression> expressions = expression.getExpressions();

		int size = 1;

		// size of generators
		for (Generator generator : expression.getGenerators()) {
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
	public Type caseExpressionString(ExpressionString expression) {
		TypeString type = IrFactory.eINSTANCE.createTypeString();
		type.setSize(expression.getValue().length());
		return type;
	}

	@Override
	public Type caseExpressionUnary(ExpressionUnary expression) {
		setTargetType(expression);

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
				int size = ((TypeUint) type).getSize() + 1;
				type = IrFactory.eINSTANCE.createTypeInt(size);
			}
			return limitType(type);
		case NUM_ELTS:
			if (!type.isList()) {
				return IrFactory.eINSTANCE.createTypeInt(1);
			}
			TypeList listType = (TypeList) type;
			// uint because the size of a list is always positive
			return IrFactory.eINSTANCE.createTypeUint(TypeUtil.getSize(listType
					.getSize()));
		default:
			return null;
		}
	}

	@Override
	public Type caseExpressionVariable(ExpressionVariable expression) {
		Variable variable = expression.getValue().getVariable();
		return EcoreUtil.copy(getType(variable));
	}

	@Override
	public Type caseFunction(Function function) {
		return doSwitch(function.getType());
	}

	@Override
	public Type caseGenerator(Generator expression) {
		return null;
	};

	@Override
	public Type caseVariable(Variable variable) {
		AstType astType;
		List<AstExpression> dimensions;

		if (variable.eContainer() instanceof InputPattern) {
			InputPattern pattern = (InputPattern) variable.eContainer();
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
		Type type = unification.getType(t1, t2);
		int size = unification.getSize(t1, t2);

		// only set the size if it is different than 0
		if (size != 0) {
			if (type.isInt()) {
				((TypeInt) type).setSize(size);
			} else if (type.isUint()) {
				((TypeUint) type).setSize(size);
			}
		}

		return limitType(type);
	}

	@Override
	public Type doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}

		return super.doSwitch(eObject);
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
	private Type findIndexType(VariableReference reference,
			List<AstExpression> indexes, AstExpression expression) {
		Variable variable = reference.getVariable();
		if (variable == null) {
			return null;
		}

		Type type = getType(variable);
		if (type == null) {
			return null;
		}

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
				// uint because index goes from 0 to dim - 1
				int indexSize = TypeUtil.getSize(new ExpressionEvaluator()
						.evaluateAsInteger(dim) - 1);
				return IrFactory.eINSTANCE.createTypeUint(indexSize);
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
	private Type findParameter(List<Variable> formalParameters,
			List<AstExpression> actualParameters, AstExpression expression) {
		Iterator<Variable> itF = formalParameters.iterator();
		Iterator<AstExpression> itA = actualParameters.iterator();
		while (itF.hasNext() && itA.hasNext()) {
			Variable formal = itF.next();
			AstExpression actual = itA.next();
			if (actual == expression) {
				return getType(formal);
			}
		}

		return null;
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
			return createType(t1, t2, Glb.instance);

		case BITOR:
		case BITXOR:
			return createType(t1, t2, Lub.instance);

		case TIMES:
			return createType(t1, t2, LubSum.instance);

		case MINUS:
			return createType(t1, t2, LubPlus1.instance);

		case PLUS:
			if (t1.isString() && !t2.isList() || t2.isString() && !t1.isList()) {
				return IrFactory.eINSTANCE.createTypeString();
			}

			return createType(t1, t2, LubPlus1.instance);

		case DIV:
		case DIV_INT:
		case SHIFT_RIGHT:
			return limitType(t1);

		case MOD:
			return limitType(t2);

		case SHIFT_LEFT:
			return createType(t1, t2, LubSumPow.instance);

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
	 * Returns a new type that is bounded by the {@link #boundType}.
	 * 
	 * @param type
	 *            a type
	 * @return a new type that is bounded by the {@link #boundType}
	 */
	private Type limitType(Type type) {
		if (type != null) {
			if (boundType == null) {
				// when no bound type exists, limit to 32 bits
				int size = 32;
				Type maxUint = IrFactory.eINSTANCE.createTypeUint(size);
				Type maxType = TypeUtil.getGlb(type, maxUint);

				if (maxType != null) {
					if (maxType.getSizeInBits() > size) {
						if (maxType.isInt()) {
							((TypeInt) maxType).setSize(size);
						} else if (maxType.isUint()) {
							((TypeUint) maxType).setSize(size);
						}
					}
				}
				return maxType;
			} else {
				return TypeUtil.getGlb(type, boundType);
			}
		}

		return type;
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
		if (cter != null) {
			switch (cter.eClass().getClassifierID()) {
			case CalPackage.EXPRESSION_CALL: {
				ExpressionCall call = (ExpressionCall) cter;
				List<Variable> formal = call.getFunction().getParameters();
				List<AstExpression> actual = call.getParameters();
				boundType = findParameter(formal, actual, expression);
				break;
			}

			case CalPackage.EXPRESSION_INDEX: {
				ExpressionIndex index = (ExpressionIndex) cter;
				boundType = findIndexType(index.getSource(),
						index.getIndexes(), expression);
				break;
			}

			case CalPackage.FUNCTION:
				Function func = (Function) cter;
				boundType = getType(func);
				break;

			case CalPackage.GENERATOR:
				Generator generator = (Generator) cter;
				boundType = getType(generator.getVariable());
				break;

			case CalPackage.OUTPUT_PATTERN:
				OutputPattern pattern = (OutputPattern) cter;
				boundType = getType(pattern.getPort());
				break;

			case CalPackage.STATEMENT_ASSIGN: {
				StatementAssign assign = (StatementAssign) cter;
				if (expression == assign.getValue()) {
					// expression is located in the value

					// get the innermost type of the target
					boundType = getType(assign.getTarget().getVariable());
					for (int i = 0; i < assign.getIndexes().size(); i++) {
						if (boundType != null && boundType.isList()) {
							boundType = ((TypeList) boundType).getType();
						}
					}
				} else {
					// expression is located in the indexes
					boundType = findIndexType(assign.getTarget(),
							assign.getIndexes(), expression);
				}
				break;
			}

			case CalPackage.STATEMENT_CALL: {
				StatementCall call = (StatementCall) cter;
				List<Variable> formal = call.getProcedure().getParameters();
				List<AstExpression> actual = call.getParameters();
				boundType = findParameter(formal, actual, expression);
				break;
			}

			case CalPackage.STATEMENT_FOREACH:
				StatementForeach foreach = (StatementForeach) cter;
				boundType = getType(foreach.getVariable());
				break;

			case CalPackage.VARIABLE:
				Variable variable = (Variable) cter;
				boundType = getType(variable);
				break;
			}
		}
	}

}
