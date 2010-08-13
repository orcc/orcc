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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstInequality;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
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
import net.sf.orcc.cal.naming.CalQualifiedNameProvider;
import net.sf.orcc.cal.type.TypeChecker;
import net.sf.orcc.cal.type.TypeTransformer;
import net.sf.orcc.cal.util.BooleanSwitch;
import net.sf.orcc.cal.util.CalActionList;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.cal.util.VoidSwitch;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.validation.Check;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

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

	@Check
	public void checkAction(AstAction action) {
		checkActionTag(action);
		checkActionVariables(action);
		checkActionOutputs(action.getOutputs());
	}

	/**
	 * Checks the token expressions are correctly typed.
	 * 
	 * @param outputs
	 *            the output patterns of an action
	 */
	private void checkActionOutputs(List<AstOutputPattern> outputs) {
		for (AstOutputPattern pattern : outputs) {
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				int repeat = new AstExpressionEvaluator()
						.evaluateAsInteger(astRepeat);
				if (repeat != 1) {
					// each value is supposed to be a list
					List<AstExpression> values = pattern.getValues();
					for (AstExpression value : values) {
						Type type = checker.getType(value);
						if (type.isList()) {
							TypeList typeList = (TypeList) type;
							Type lub = checker.getLub(pattern.getPort()
									.getIrType(), typeList.getType());
							if (lub != null && typeList.getSize() >= repeat) {
								continue;
							}
						}

						error("this expression must be of type List of "
								+ pattern.getPort().getIrType().toString()
								+ " with a size greater than or equal to "
								+ repeat, value, CalPackage.AST_EXPRESSION);
					}
				}
			}
		}
	}

	/**
	 * Check that the action tag is different from port and state variable
	 * names.
	 * 
	 * @param action
	 *            the action
	 */
	private void checkActionTag(AstAction action) {
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

	/**
	 * Checks the tokens and variables declared in the action are unique.
	 * 
	 * @param action
	 *            the action to check
	 */
	private void checkActionVariables(AstAction action) {
		List<AstInputPattern> inputs = action.getInputs();
		List<AstVariable> variables = new ArrayList<AstVariable>();
		for (AstInputPattern pattern : inputs) {
			variables.addAll(pattern.getTokens());
		}

		variables.addAll(action.getVariables());
		checkUniqueNames(variables);
	}

	@Check
	public void checkActor(AstActor actor) {
		// fill the name provider's cache
		((CalQualifiedNameProvider) nameProvider).resetUntaggedCount();
		getNames(actor);

		// evaluate state variables
		checker = new TypeChecker();
		evaluateStateVariables(actor.getStateVariables());

		// transforms AST types to IR types
		// this is a prerequisite for type checking
		TypeTransformer typeTransformer = new TypeTransformer();
		typeTransformer.transformTypes(actor);

		checkActorStructure(actor);
	}

	/**
	 * Checks the actor structural information is correct. Checks name,
	 * priorities and FSM.
	 * 
	 * @param actor
	 *            the actor
	 */
	private void checkActorStructure(AstActor actor) {
		// check actor name matches file name
		String path = actor.eResource().getURI().path();
		String fileName = new File(path).getName();
		if (!fileName.equals(actor.getName() + ".cal")) {
			error("Actor " + actor.getName()
					+ " must be defined in a file named \"" + actor.getName()
					+ ".cal\"", actor, CalPackage.AST_ACTOR__NAME);
		}

		// check unique names
		checkUniqueNames(actor.getParameters());
		checkUniqueNames(actor.getStateVariables());

		// build action list
		CalActionList actionList = new CalActionList();
		actionList.addActions(actor.getActions());

		// check FSM and priorities
		AstSchedule schedule = actor.getSchedule();
		if (schedule != null) {
			checkFsm(actionList, schedule);
		}

		checkPriorities(actor, actionList);
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
	public void checkAstStatementCall(AstStatementCall astCall) {
		AstProcedure procedure = astCall.getProcedure();
		String name = procedure.getName();
		List<AstExpression> parameters = astCall.getParameters();
		if (procedure.eContainer() == null) {
			if ("print".equals(name) || "println".equals(name)) {
				if (parameters.size() > 1) {
					error("built-in procedure " + name
							+ " takes at most one expression", astCall,
							CalPackage.AST_STATEMENT_CALL);
				}
			}

			return;
		}

		if (procedure.getParameters().size() != parameters.size()) {
			error("procedure " + name + " takes "
					+ procedure.getParameters().size() + " arguments.",
					astCall, CalPackage.AST_STATEMENT_CALL);
			return;
		}

		Iterator<AstVariable> itFormal = procedure.getParameters().iterator();
		Iterator<AstExpression> itActual = parameters.iterator();
		while (itFormal.hasNext() && itActual.hasNext()) {
			Type formalType = itFormal.next().getIrType();
			AstExpression expression = itActual.next();
			Type actualType = checker.getType(expression);

			// check types
			if (!checker.areTypeCompatible(formalType, actualType)) {
				error("Type mismatch: cannot convert from " + actualType
						+ " to " + formalType, expression,
						CalPackage.AST_EXPRESSION);
			}
		}
	}

	/**
	 * Checks the given FSM using the given action list. This check is not
	 * annotated because we need to build the action list, which is also useful
	 * for checking the priorities, and we do not want to build that twice.
	 * 
	 * @param actionList
	 *            the action list of the actor
	 * @param schedule
	 *            the FSM of the actor
	 */
	private void checkFsm(CalActionList actionList, AstSchedule schedule) {
		for (AstTransition transition : schedule.getTransitions()) {
			AstTag tag = transition.getTag();
			if (tag != null) {
				List<AstAction> actions = actionList.getTaggedActions(tag
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
	public void checkFunction(final AstFunction function) {
		checkUniqueNames(function.getParameters());
		checkUniqueNames(function.getVariables());

		boolean used = new BooleanSwitch() {

			@Override
			public Boolean caseAstExpressionCall(AstExpressionCall expression) {
				if (expression.getFunction().equals(function)) {
					return true;
				}

				return super.caseAstExpressionCall(expression);
			}

		}.doSwitch(Util.getActor(function));

		if (!used) {
			warning("The function " + function.getName() + " is never called",
					CalPackage.AST_FUNCTION__NAME);
		}
	}

	@Check
	public void checkGenerator(AstGenerator generator) {
		AstExpression astValue = generator.getLower();
		Object initialValue = new AstExpressionEvaluator().evaluate(astValue);
		Long lower = null;
		if (initialValue instanceof Long) {
			lower = (Long) initialValue;
		} else {
			error("lower bound must be a compile-time constant", generator,
					CalPackage.AST_GENERATOR__LOWER);
		}

		astValue = generator.getHigher();
		initialValue = new AstExpressionEvaluator().evaluate(astValue);
		Long higher = null;
		if (initialValue instanceof Long) {
			higher = (Long) initialValue;
		} else {
			error("higher bound must be a compile-time constant", generator,
					CalPackage.AST_GENERATOR__HIGHER);
		}

		if (lower != null && higher != null) {
			if (higher < lower) {
				error("higher bound must be greater than lower bound",
						generator, CalPackage.AST_GENERATOR);
			}
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

		boolean used = new BooleanSwitch() {

			@Override
			public Boolean caseAstExpressionIndex(AstExpressionIndex expression) {
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
	}

	/**
	 * Checks the priorities of the given actor using the given action list.
	 * This check is not annotated because we need to build the action list,
	 * which is also useful for checking the FSM, and we do not want to build
	 * that twice.
	 * 
	 * @param actor
	 *            the actor
	 * @param actionList
	 *            the action list of the actor
	 */
	private void checkPriorities(AstActor actor, CalActionList actionList) {
		List<AstPriority> priorities = actor.getPriorities();
		DirectedGraph<AstAction, DefaultEdge> graph = new DefaultDirectedGraph<AstAction, DefaultEdge>(
				DefaultEdge.class);

		// add one vertex per tagged action
		for (AstAction action : actionList) {
			AstTag tag = action.getTag();
			if (tag != null) {
				graph.addVertex(action);
			}
		}

		for (AstPriority priority : priorities) {
			for (AstInequality inequality : priority.getInequalities()) {
				// the grammar requires there be at least two tags
				Iterator<AstTag> it = inequality.getTags().iterator();
				AstTag previousTag = it.next();

				List<AstAction> sources = actionList
						.getTaggedActions(previousTag.getIdentifiers());
				if (sources == null || sources.isEmpty()) {
					error("tag " + nameProvider.getQualifiedName(previousTag)
							+ " does not refer to any action", inequality,
							CalPackage.AST_INEQUALITY);
				}

				while (it.hasNext()) {
					AstTag tag = it.next();
					sources = actionList.getTaggedActions(previousTag
							.getIdentifiers());
					List<AstAction> targets = actionList.getTaggedActions(tag
							.getIdentifiers());

					if (targets == null || targets.isEmpty()) {
						error("tag " + nameProvider.getQualifiedName(tag)
								+ " does not refer to any action", inequality,
								CalPackage.AST_INEQUALITY);
					}

					if (sources != null && targets != null) {
						for (AstAction source : sources) {
							for (AstAction target : targets) {
								graph.addEdge(source, target);
							}
						}
					}

					previousTag = tag;
				}
			}
		}

		CycleDetector<AstAction, DefaultEdge> cycleDetector = new CycleDetector<AstAction, DefaultEdge>(
				graph);
		Set<AstAction> cycle = cycleDetector.findCycles();
		if (!cycle.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (AstAction action : cycle) {
				builder.append(nameProvider.getQualifiedName(action.getTag()));
				builder.append(", ");
			}

			Iterator<AstAction> it = cycle.iterator();
			builder.append(nameProvider.getQualifiedName(it.next().getTag()));

			error("priorities of actor " + actor.getName()
					+ " contain a cycle: " + builder.toString(), actor,
					CalPackage.AST_ACTOR__PRIORITIES);
		}
	}

	@Check
	public void checkProcedure(final AstProcedure procedure) {
		checkUniqueNames(procedure.getParameters());
		checkUniqueNames(procedure.getVariables());

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
			warning("The procedure " + procedure.getName() + " is never called",
					CalPackage.AST_PROCEDURE__NAME);
		}
	}

	private void checkUniqueNames(List<AstVariable> variables) {
		Set<String> names = new HashSet<String>();
		for (AstVariable variable : variables) {
			String name = nameProvider.getQualifiedName(variable);
			if (names.contains(name)) {
				error("Duplicate variable " + variable.getName(), variable,
						CalPackage.AST_VARIABLE__NAME);
			}
			names.add(name);
		}
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

	/**
	 * Fills the name provider's cache by getting names for every action,
	 * function, generator, foreach.
	 * 
	 * @param actor
	 *            the actor
	 */
	private void getNames(AstActor actor) {
		new VoidSwitch() {

			@Override
			public Void caseAstAction(AstAction action) {
				((CalQualifiedNameProvider) nameProvider).resetBlockCount();
				super.caseAstAction(action);
				return null;
			}

			@Override
			public Void caseAstFunction(AstFunction function) {
				((CalQualifiedNameProvider) nameProvider).resetBlockCount();
				super.caseAstFunction(function);
				return null;
			}

			@Override
			public Void caseAstGenerator(AstGenerator generator) {
				nameProvider.getQualifiedName(generator);
				super.caseAstGenerator(generator);
				return null;
			}

			@Override
			public Void caseAstProcedure(AstProcedure procedure) {
				((CalQualifiedNameProvider) nameProvider).resetBlockCount();
				super.caseAstProcedure(procedure);
				return null;
			}

			@Override
			public Void caseAstStatementForeach(AstStatementForeach foreach) {
				nameProvider.getQualifiedName(foreach);
				super.caseAstStatementForeach(foreach);
				return null;
			}

		}.doSwitch(actor);
	}

}
