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

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.cal.util.VoidSwitch;
import net.sf.orcc.cal.validation.CalJavaValidator;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an AST type to IR type transformer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeTransformer extends VoidSwitch {

	private CalJavaValidator validator;

	/**
	 * Creates a new AST type to IR type transformation.
	 */
	public TypeTransformer(CalJavaValidator validator) {
		this.validator = validator;
	}

	@Override
	public Void caseAstExpression(AstExpression expression) {
		TypeChecker checker = new TypeChecker(validator);
		checker.getType(expression);
		return null;
	}

	@Override
	public Void caseAstFunction(AstFunction function) {
		TypeConverter converter = new TypeConverter(validator);
		Type type = converter.transformType(function.getType());
		function.setIrType(type);
		return super.caseAstFunction(function);
	}

	@Override
	public Void caseAstInputPattern(AstInputPattern input) {
		AstPort port = input.getPort();
		doSwitch(port);

		// type of each token
		Type type = port.getIrType();

		// repeat equals to 1 when absent
		AstExpression astRepeat = input.getRepeat();
		if (astRepeat != null) {
			int repeat = new AstExpressionEvaluator(validator)
					.evaluateAsInteger(astRepeat);
			type = IrFactory.eINSTANCE.createTypeList(repeat, type);
		}

		for (AstVariable token : input.getTokens()) {
			token.setIrType(type);
		}

		return null;
	}

	@Override
	public Void caseAstPort(AstPort port) {
		if (port.getIrType() == null) {
			TypeConverter converter = new TypeConverter(validator);
			Type type = converter.transformType(port.getType());
			port.setIrType(type);
		}

		return null;
	}

	@Override
	public Void caseAstTypeList(AstTypeList typeList) {
		doSwitch(typeList.getType());
		doSwitch(typeList.getSize());
		return null;
	}

	@Override
	public Void caseAstVariable(AstVariable variable) {
		if (variable.getIrType() != null) {
			// if the variable has already been converted, do not do it again
			return null;
		}

		TypeConverter converter = new TypeConverter(validator);
		doSwitch(variable.getType());

		// convert the type of the variable
		AstType astType = EcoreUtil.copy(variable.getType());
		List<AstExpression> dimensions = variable.getDimensions();
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
		variable.setType(astType);

		Type type = converter.transformType(astType);
		variable.setIrType(type);

		doSwitch(variable.getValue());

		return null;
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
	 * Evaluates the state variables of the given entity.
	 * 
	 * @param entity
	 *            an entity
	 */
	private void evaluateStateVariables(AstEntity entity) {
		List<AstVariable> variables;
		AstActor actor = entity.getActor();
		if (actor == null) {
			AstUnit unit = entity.getUnit();
			variables = unit.getVariables();
		} else {
			variables = actor.getStateVariables();
		}

		evaluateStateVariables(variables);
	}

	/**
	 * Evaluates the given list of state variables, and register them as
	 * variables.
	 * 
	 * @param variables
	 *            a list of state variables
	 */
	private void evaluateStateVariables(List<AstVariable> variables) {
		for (AstVariable astVariable : variables) {
			// evaluate initial value (if any)
			AstExpression astValue = astVariable.getValue();
			if (astValue != null) {
				Expression initialValue = (Expression) astVariable
						.getInitialValue();
				if (initialValue == null) {
					// only evaluates the initial value once (when validating)
					initialValue = new AstExpressionEvaluator(validator)
							.evaluate(astValue);
					if (initialValue == null) {
						error("variable "
								+ astVariable.getName()
								+ " does not have a compile-time constant initial value",
								astVariable, eINSTANCE.getAstVariable_Name(),
								-1);
					} else {
						// register the value
						astVariable.setInitialValue(initialValue);
					}
				}
			}
		}
	}

	/**
	 * Transforms the given AST type to an IR type.
	 * 
	 * @param type
	 *            an AST type
	 * @return an IR type
	 */
	public void transformTypes(AstEntity entity) {
		evaluateStateVariables(entity);
		doSwitch(entity);
	}

}
