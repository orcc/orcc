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
import net.sf.orcc.cal.cal.AstTypeDouble;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeHalf;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.CalFactory;
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
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.util.TypeUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a typer for RVC-CAL AST. Note that types must have been
 * transformed to IR types first.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Typer extends CalSwitch<Type> {



	/**
	 * Returns the type of the given object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the type of the given object
	 */
	public static Type getType(EObject eObject) {
		return CacheManager.instance.getOrCompute(eObject, new Typer(),
				CachePackage.eINSTANCE.getCache_TypesMap());
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
		return IrFactory.eINSTANCE.createTypeFloat(32);
	}

	@Override
	public Type caseAstTypeDouble(AstTypeDouble type) {
		return IrFactory.eINSTANCE.createTypeFloat(64);
	}

	@Override
	public Type caseAstTypeHalf(AstTypeHalf type) {
		return IrFactory.eINSTANCE.createTypeFloat(16);
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
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Type t1 = getType(expression.getLeft());
		Type t2 = getType(expression.getRight());
		return TypeUtil.getTypeBinary(op, t1, t2);
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
		return IrFactory.eINSTANCE.createTypeFloat(32);
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
				return IrFactory.eINSTANCE.createTypeInt(size);
			} else {
				return EcoreUtil.copy(type);
			}
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



	@Override
	public Type doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}

		return super.doSwitch(eObject);
	}



}
