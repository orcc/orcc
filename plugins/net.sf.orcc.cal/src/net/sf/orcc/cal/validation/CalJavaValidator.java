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
package net.sf.orcc.cal.validation;

import java.util.List;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstPriority;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstSchedule;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.cal.type.TypeChecker;
import net.sf.orcc.cal.type.TypeTransformer;
import net.sf.orcc.cal.util.BooleanSwitch;
import net.sf.orcc.cal.util.CalActionList;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.validation.Check;

import com.google.inject.Inject;

/**
 * This class describes the validation of an RVC-CAL actor. The checks tagged as
 * "expensive" are only performed when the file is saved and before code
 * generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalJavaValidator extends AbstractCalJavaValidator {

	private static CalJavaValidator instance;

	/**
	 * Returns the instance of this validator.
	 * 
	 * @return the instance of this validator
	 */
	public static CalJavaValidator getInstance() {
		return instance;
	}

	private TypeChecker checker;

	@Inject
	private IQualifiedNameProvider nameProvider;

	/**
	 * Creates a new CAL validator written in Java.
	 */
	public CalJavaValidator() {
		// set this validator as the instance
		CalJavaValidator.instance = this;
	}

	/**
	 * Check action tag coherence. Tag's name must be different to port and
	 * state variable name.
	 */
	@Check
	public void checkActionTag(final AstAction action) {
		AstActor actor = EcoreUtil2.getContainerOfType(action, AstActor.class);
		String name = nameProvider.getQualifiedName(action);

		// Check if tag name is not already used in a state variable
		List<AstVariable> variables = actor.getStateVariables();
		for (AstVariable variable : variables) {
			if (name.equals(variable.getName())) {
				error("Action " + name
						+ " has the same name as a state variable",
						CalPackage.AST_ACTION__TAG);
			}
		}

		// Check if tag name is not already used in an input port
		List<AstPort> inputs = actor.getInputs();
		for (AstPort input : inputs) {
			if (name.equals(input.getName())) {
				error("Action " + name + " has the same name as an input port",
						CalPackage.AST_ACTION__TAG);
			}
		}

		// Check if tag name is not already used in an output port
		List<AstPort> outputs = actor.getOutputs();
		for (AstPort output : outputs) {
			if (name.equals(output.getName())) {
				error("Action " + name + " has the same name as an output port",
						CalPackage.AST_ACTION__TAG);
			}
		}
	}

	@Check
	public void checkActor(AstActor actor) {
		checker = new TypeChecker();

		evaluateStateVariables(actor.getStateVariables());

		// transforms AST types to IR types
		// this is a prerequisite for type checking
		TypeTransformer typeTransformer = new TypeTransformer();
		typeTransformer.transformTypes(actor);
	}

	@Check
	public void checkAssign(AstStatementAssign assign) {
		AstVariable variable = assign.getTarget().getVariable();
		if (variable.isConstant()) {
			error("The variable " + variable.getName() + " is not assignable",
					CalPackage.AST_STATEMENT_ASSIGN__TARGET);
		}

		// create expression
		AstExpressionIndex expression = CalFactory.eINSTANCE
				.createAstExpressionIndex();

		// set reference
		AstVariableReference reference = CalFactory.eINSTANCE
				.createAstVariableReference();
		reference.setVariable(variable);
		expression.setSource(reference);

		// copy indexes
		expression.getIndexes().addAll(EcoreUtil.copyAll(assign.getIndexes()));

		// check types
		Type targetType = checker.getType(expression);
		Type type = checker.getType(assign.getValue());
		if (!checker.areTypeCompatible(type, targetType)) {
			error("Type mismatch: cannot convert from " + type + " to "
					+ targetType, CalPackage.AST_STATEMENT_ASSIGN__VALUE);
		}
	}

	@Check
	public void checkFsm(AstSchedule schedule) {
		AstActor actor = (AstActor) schedule.eContainer();
		CalActionList actionList = new CalActionList();
		actionList.addActions(actor.getActions());

		for (AstTransition transition : schedule.getTransitions()) {
			AstTag tag = transition.getTag();
			if (tag != null) {
				List<AstAction> actions = actionList.getActions(tag
						.getIdentifiers());
				if (actions == null || actions.isEmpty()) {
					error("tag " + nameProvider.getQualifiedName(tag)
							+ " does not refer to any action", transition,
							CalPackage.AST_TRANSITION__TAG);
				}
			}
		}
	}

	@Check
	public void checkIsFunctionUsed(final AstFunction function) {
		try {
			boolean used = new BooleanSwitch() {

				@Override
				public Boolean caseAstExpressionCall(
						AstExpressionCall expression) {
					if (expression.getFunction().equals(function)) {
						return true;
					}

					return super.caseAstExpressionCall(expression);
				}

			}.doSwitch(Util.getActor(function));

			if (!used) {
				warning("The function " + function.getName()
						+ " is never called", CalPackage.AST_FUNCTION__NAME);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Check
	public void checkIsProcedureUsed(final AstProcedure procedure) {
		try {
			boolean used = new BooleanSwitch() {

				@Override
				public Boolean caseAstStatementCall(AstStatementCall call) {
					if (call.getProcedure().equals(procedure)) {
						return true;
					}

					return false;
				}

			}.doSwitch(Util.getActor(procedure));

			if (!used) {
				warning("The procedure " + procedure.getName()
						+ " is never called", CalPackage.AST_PROCEDURE__NAME);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Check
	public void checkIsVariabledUsed(final AstVariable variable) {
		// do not take variables declared by input patterns and
		// generator/foreach
		EObject container = variable.eContainer();
		if (container instanceof AstInputPattern
				|| container instanceof AstGenerator
				|| container instanceof AstStatementForeach) {
			return;
		}

		try {
			boolean used = new BooleanSwitch() {

				@Override
				public Boolean caseAstExpressionIndex(
						AstExpressionIndex expression) {
					if (expression.getSource().getVariable().equals(variable)) {
						return true;
					}

					return super.caseAstExpressionIndex(expression);
				}

				@Override
				public Boolean caseAstExpressionVariable(
						AstExpressionVariable expression) {
					return expression.getValue().getVariable().equals(variable);
				}

				@Override
				public Boolean caseAstStatementAssign(AstStatementAssign assign) {
					if (assign.getTarget().getVariable().equals(variable)) {
						return true;
					}

					return super.caseAstStatementAssign(assign);
				}

			}.doSwitch(Util.getActor(variable));

			if (!used) {
				warning("The variable " + variable.getName() + " is never read",
						CalPackage.AST_VARIABLE__NAME);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Check
	public void checkPriorities(AstPriority priority) {
	}

	@Override
	public void error(String string, EObject source, Integer feature) {
		super.error(string, source, feature);
	}

	/**
	 * Evaluates the given list of state variables, and register them as
	 * variables.
	 * 
	 * @param stateVariables
	 *            a list of state variables
	 */
	private void evaluateStateVariables(List<AstVariable> stateVariables) {
		for (AstVariable astVariable : stateVariables) {
			// evaluate initial value (if any)
			AstExpression astValue = astVariable.getValue();
			Object initialValue;
			if (astValue != null) {
				initialValue = new AstExpressionEvaluator().evaluate(astValue);
				if (initialValue == null) {
					error("variable "
							+ astVariable.getName()
							+ " does not have a compile-time constant initial value",
							astVariable, CalPackage.AST_VARIABLE);
				} else {
					// register the value
					astVariable.setInitialValue(initialValue);
				}
			}
		}
	}

}
