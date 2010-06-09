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
package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
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
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstSchedule;
import net.sf.orcc.cal.cal.AstStatement;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstStatementIf;
import net.sf.orcc.cal.cal.AstStatementWhile;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.frontend.schedule.ActionSorter;
import net.sf.orcc.frontend.schedule.FSMBuilder;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.FloatType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;

import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * This class transforms an AST actor to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstTransformer {

	/**
	 * This class transforms an AST statement into one or more IR instructions
	 * and/or nodes.
	 * 
	 */
	private class ExpressionTransformer extends CalSwitch<Expression> {

		@Override
		public Expression caseAstExpressionBinary(AstExpressionBinary expression) {
			return new IntExpr(42);
		}

		@Override
		public Expression caseAstExpressionBoolean(
				AstExpressionBoolean expression) {
			Location location = Util.getLocation(expression);
			boolean value = expression.isValue();
			return new BoolExpr(location, value);
		}

		@Override
		public Expression caseAstExpressionCall(AstExpressionCall call) {
			return new IntExpr(42);
		}

		@Override
		public Expression caseAstExpressionIf(AstExpressionIf expression) {
			return new IntExpr(42);
		}

		@Override
		public Expression caseAstExpressionIndex(AstExpressionIndex expression) {
			return new IntExpr(42);
		}

		@Override
		public Expression caseAstExpressionInteger(
				AstExpressionInteger expression) {
			Location location = Util.getLocation(expression);
			int value = expression.getValue();
			return new IntExpr(location, value);
		}

		@Override
		public Expression caseAstExpressionList(AstExpressionList expression) {
			return new IntExpr(42);
		}

		@Override
		public Expression caseAstExpressionString(AstExpressionString expression) {
			return new IntExpr(42);
		}

		@Override
		public Expression caseAstExpressionUnary(AstExpressionUnary expression) {
			return new IntExpr(42);
		}

		@Override
		public Expression caseAstExpressionVariable(
				AstExpressionVariable expression) {
			return new IntExpr(42);
		}

	}

	/**
	 * This class transforms an AST statement into one or more IR instructions
	 * and/or nodes. It returns null because it appends the instructions/nodes
	 * directly to the {@link #nodes} field.
	 * 
	 */
	private class StatementTransformer extends CalSwitch<Void> {

		@Override
		public Void caseAstStatementAssign(AstStatementAssign astAssign) {
			Location location = Util.getLocation(astAssign);

			// get target
			AstVariable astTarget = astAssign.getTarget().getVariable();
			Variable target = variablesMap.get(astTarget);

			// transform indexes and value
			List<Expression> indexes = transformExpressions(astAssign
					.getIndexes());
			Expression value = transformExpression(astAssign.getValue());

			List<CFGNode> nodes = procedure.getNodes();
			BlockNode node = BlockNode.getLast(procedure, nodes);
			if (indexes.isEmpty() && !target.isGlobal()) {
				LocalVariable local = (LocalVariable) target;
				Assign assign = new Assign(location, local, value);
				node.add(assign);
			} else {
				// create a store
			}

			return null;
		}

		@Override
		public Void caseAstStatementCall(AstStatementCall call) {
			return null;
		}

		@Override
		public Void caseAstStatementForeach(AstStatementForeach foreach) {
			return null;
		}

		@Override
		public Void caseAstStatementIf(AstStatementIf stmtIf) {
			return null;
		}

		@Override
		public Void caseAstStatementWhile(AstStatementWhile stmtWhile) {
			return null;
		}

	}

	private class TypeTransformer extends CalSwitch<Type> {

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
				size = exprEvaluator.evaluateAsInteger(astSize);
			}
			return new IntType(size);
		}

		@Override
		public Type caseAstTypeList(AstTypeList listType) {
			Type type = transformType(listType.getType());
			int size = exprEvaluator.evaluateAsInteger(listType.getSize());
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
				size = exprEvaluator.evaluateAsInteger(astSize);
			}
			return new UintType(size);
		}

	}

	private final java.util.regex.Pattern dotPattern = java.util.regex.Pattern
			.compile("\\.");

	/**
	 * expression evaluator
	 */
	final private AstExpressionEvaluator exprEvaluator;

	/**
	 * expression transformer.
	 */
	final private ExpressionTransformer exprTransformer;

	/**
	 * The file in which the actor is defined.
	 */
	private String file;

	/**
	 * A map from AST functions to IR procedures.
	 */
	final private Map<AstFunction, Procedure> functionsMap;

	@Inject
	private IQualifiedNameProvider nameProvider;

	/**
	 * A map from AST ports to IR ports.
	 */
	final private Map<AstPort, Port> portMap;

	/**
	 * Contains the current procedures where local variables or nodes should be
	 * added by the expression transformer or statement transformer.
	 */
	private Procedure procedure;

	/**
	 * A map from AST procedures to IR procedures.
	 */
	final private Map<AstProcedure, Procedure> proceduresMap;

	/**
	 * statement transformer.
	 */
	final private StatementTransformer stmtTransformer;

	/**
	 * type transformer.
	 */
	final private TypeTransformer typeTransformer;

	/**
	 * A map from AST variables to IR variables.
	 */
	final private Map<AstVariable, Variable> variablesMap;

	/**
	 * Creates a new AST to IR transformation.
	 */
	public AstTransformer() {
		portMap = new HashMap<AstPort, Port>();

		variablesMap = new HashMap<AstVariable, Variable>();
		proceduresMap = new HashMap<AstProcedure, Procedure>();
		functionsMap = new HashMap<AstFunction, Procedure>();

		exprTransformer = new ExpressionTransformer();
		stmtTransformer = new StatementTransformer();
		typeTransformer = new TypeTransformer();

		exprEvaluator = new AstExpressionEvaluator();
	}

	/**
	 * Adds reads to the current {@link #procedure}'s node list. The number of
	 * tokens to read on each port is stored in the given IR input pattern. This
	 * method also reorganizes tokens if necessary (in cases such as [a, b]
	 * repeat 3).
	 * 
	 * @param astAction
	 *            the AST action
	 * @param inputPattern
	 *            the IR input pattern
	 */
	private void actionAddReads(AstAction astAction, Pattern inputPattern) {
		// TODO add reads to the "nodes" node list
	}

	/**
	 * Adds writes to the current {@link #procedure}'s node list. The number of
	 * tokens to write on each port is stored in the given IR output pattern.
	 * This method also reorganizes tokens if necessary (in cases such as [a, b]
	 * repeat 3).
	 * 
	 * @param astAction
	 *            the AST action
	 * @param outputPattern
	 *            the IR output pattern
	 */
	private void actionAddWrites(AstAction astAction, Pattern outputPattern) {
		// TODO add writes to the "nodes" node list
	}

	private void actionFillIsSchedulable(AstAction astAction,
			Procedure scheduler) {
		// TODO fill is schedulable function
	}

	/**
	 * Fills the IR output pattern from the list of AST output patterns.
	 * 
	 * @param outputs
	 *            a list of AST output patterns, where an output pattern
	 *            contains a list of expressions and possibly a repeat clause.
	 * @param outputPattern
	 *            target IR output pattern
	 */
	private void actionFillOutputPattern(List<AstOutputPattern> outputs,
			Pattern outputPattern) {
		// TODO fill output pattern
	}

	/**
	 * Transforms the given list of AST input patterns as local variables and
	 * fills the target IR input pattern.
	 * 
	 * @param astInputPattern
	 *            a list of AST input patterns, where an AST input patterns
	 *            contains a list of tokens and possibly a repeat clause.
	 * @param inputPattern
	 *            target IR input pattern
	 */
	private void actionTransformInputPattern(
			List<AstInputPattern> astInputPattern, Pattern inputPattern) {
		for (AstInputPattern pattern : astInputPattern) {
			Port port = portMap.get(pattern.getPort());
			List<AstVariable> tokens = pattern.getTokens();

			// number of repeats
			int repeat;

			// type of each token
			Type type;

			// repeat equals to 1 when absent
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat == null) {
				repeat = 1;
				type = port.getType();
			} else {
				repeat = exprEvaluator.evaluateAsInteger(astRepeat);
				type = new ListType(repeat, port.getType());
			}

			// declare tokens
			for (AstVariable token : tokens) {
				LocalVariable local = transformLocalVariable(token, type);
				procedure.getLocals().add(file, local.getLocation(),
						local.getName(), local);
			}

			// set port consumption
			int totalConsumption = repeat * tokens.size();
			inputPattern.put(port, totalConsumption);
		}
	}

	/**
	 * Transforms the given AST Actor to an IR actor.
	 * 
	 * @param file
	 *            the .cal file where the actor is defined
	 * @param astActor
	 *            the AST of the actor
	 * @return the actor in IR form
	 */
	public Actor transform(String file, AstActor astActor) {
		portMap.clear();

		variablesMap.clear();
		proceduresMap.clear();
		functionsMap.clear();

		this.file = file;
		exprEvaluator.initialize(file);

		String name = astActor.getName();
		OrderedMap<Variable> parameters = new OrderedMap<Variable>();

		// first state variables, because port's sizes may depend on them.
		OrderedMap<Variable> stateVars = transformStateVariables(astActor
				.getStateVariables());
		OrderedMap<Port> inputs = transformPorts(astActor.getInputs());
		OrderedMap<Port> outputs = transformPorts(astActor.getOutputs());

		// transforms functions and procedures
		OrderedMap<Procedure> procedures = new OrderedMap<Procedure>();
		transformFunctions(astActor.getFunctions(), procedures);
		transformProcedures(astActor.getProcedures(), procedures);

		// transform actions
		ActionList actions = transformActions(astActor.getActions());

		// transform initializes
		ActionList initializes = transformActions(astActor.getInitializes());

		// sort actions by priority
		ActionSorter sorter = new ActionSorter(actions);
		actions = sorter.applyPriority(astActor.getPriorities());

		// transform FSM
		AstSchedule schedule = astActor.getSchedule();
		ActionScheduler scheduler;
		if (schedule == null) {
			scheduler = new ActionScheduler(actions.getAllActions(), null);
		} else {
			FSMBuilder builder = new FSMBuilder(astActor.getSchedule());
			FSM fsm = builder.buildFSM(actions);
			scheduler = new ActionScheduler(actions.getUntaggedActions(), fsm);
		}

		// create IR actor
		return new Actor(name, file, parameters, inputs, outputs, stateVars,
				procedures, actions.getAllActions(),
				initializes.getAllActions(), scheduler);
	}

	/**
	 * Fills the input/output patterns and the scheduler and body procedures
	 * from the given AST action.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param inputPattern
	 *            the IR input pattern
	 * @param outputPattern
	 *            the IR output pattern
	 * @param scheduler
	 *            the IR isSchedulable function
	 * @param body
	 *            the IR body of the action
	 */
	private void transformAction(AstAction astAction, Pattern inputPattern,
			Pattern outputPattern, Procedure scheduler, Procedure body) {
		actionFillOutputPattern(astAction.getOutputs(), outputPattern);

		actionFillIsSchedulable(astAction, scheduler);

		// current procedure is the body
		procedure = body;

		actionTransformInputPattern(astAction.getInputs(), inputPattern);
		actionAddReads(astAction, inputPattern);
		transformLocalVariables(astAction.getVariables());
		transformStatements(astAction.getStatements());
		actionAddWrites(astAction, outputPattern);
	}

	/**
	 * Transforms the given list of AST actions to an ActionList of IR actions.
	 * 
	 * @param actions
	 *            a list of AST actions
	 * @return an ActionList of IR actions
	 */
	private ActionList transformActions(List<AstAction> actions) {
		ActionList actionList = new ActionList();
		for (AstAction astAction : actions) {
			Location location = Util.getLocation(astAction);

			// transform tag
			AstTag astTag = astAction.getTag();
			Tag tag;
			if (astTag == null) {
				tag = new Tag();
			} else {
				tag = new Tag(astAction.getTag().getIdentifiers());
			}

			Pattern inputPattern = new Pattern();
			Pattern outputPattern = new Pattern();

			String name = nameProvider.getQualifiedName(astAction);
			name = dotPattern.matcher(name).replaceAll("_");

			// creates scheduler and body
			Procedure scheduler = new Procedure("isSchedulable_" + name,
					location, new BoolType());
			Procedure body = new Procedure(name, location, new VoidType());

			// fills the patterns and procedures
			transformAction(astAction, inputPattern, outputPattern, scheduler,
					body);

			// creates IR action and add it to action list
			Action action = new Action(location, tag, inputPattern,
					outputPattern, scheduler, body);
			actionList.add(action);
		}

		return actionList;
	}

	/**
	 * Transforms the given AST expression to an IR expression. In the process
	 * nodes may be created and added to the current {@link #procedure}, since
	 * many RVC-CAL expressions are expressed with IR statements.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return an IR expression
	 */
	private Expression transformExpression(AstExpression expression) {
		return exprTransformer.doSwitch(expression);
	}

	/**
	 * Transforms the given AST expressions to a list of IR expressions. In the
	 * process nodes may be created and added to the current {@link #procedure},
	 * since many RVC-CAL expressions are expressed with IR statements.
	 * 
	 * @param expressions
	 *            a list of AST expressions
	 * @return a list of IR expressions
	 */
	private List<Expression> transformExpressions(
			List<AstExpression> expressions) {
		List<Expression> irExpressions = new ArrayList<Expression>(0);
		for (AstExpression expression : expressions) {
			irExpressions.add(transformExpression(expression));
		}
		return irExpressions;
	}

	private void transformFunctions(List<AstFunction> astFunctions,
			OrderedMap<Procedure> procedures) {
		for (AstFunction astFunction : astFunctions) {
			String name = astFunction.getName();
			Location location = Util.getLocation(astFunction);
			Type type = transformType(astFunction.getType());

			// sets the current procedure
			procedure = new Procedure(name, location, type);

			transformParameters(astFunction.getParameters());
			transformLocalVariables(astFunction.getVariables());
			transformExpression(astFunction.getExpression());

			procedures.add(file, location, name, procedure);

			functionsMap.put(astFunction, procedure);
		}
	}

	/**
	 * Transforms the given AST variable to an IR variable that has the name of
	 * <code>astVariable</code> and the given type. A binding is added to the
	 * {@link #variablesMap} between astVariable and the created local variable.
	 * 
	 * @param astVariable
	 *            an AST variable
	 * @param type
	 *            an IR type
	 * @return the IR local variable created
	 */
	private LocalVariable transformLocalVariable(AstVariable astVariable,
			Type type) {
		Location location = Util.getLocation(astVariable);
		String name = astVariable.getName();
		boolean assignable = !astVariable.isConstant();

		LocalVariable local = new LocalVariable(assignable, 0, location, name,
				null, type);

		AstExpression value = astVariable.getValue();
		if (value != null) {
			Expression expression = transformExpression(value);
			// TODO assign to target variable
			String.valueOf(expression);
		}

		variablesMap.put(astVariable, local);
		return local;
	}

	/**
	 * Transforms the given list of AST variables to IR variables, and adds them
	 * to the current {@link #procedure}'s local variables list.
	 * 
	 * @param variables
	 *            a list of AST variables
	 */
	private void transformLocalVariables(List<AstVariable> variables) {
		for (AstVariable astVariable : variables) {
			Type type = transformType(astVariable.getType());
			LocalVariable local = transformLocalVariable(astVariable, type);
			procedure.getLocals().add(file, local.getLocation(),
					local.getName(), local);
		}
	}

	/**
	 * Transforms the given list of AST parameters to IR variables, and adds
	 * them to the current {@link #procedure}'s parameters list.
	 * 
	 * @param parameters
	 *            a list of AST parameters
	 */
	private void transformParameters(List<AstVariable> parameters) {
		for (AstVariable astParameter : parameters) {
			Type type = transformType(astParameter.getType());
			LocalVariable local = transformLocalVariable(astParameter, type);
			procedure.getParameters().add(file, local.getLocation(),
					local.getName(), local);
		}
	}

	/**
	 * Transforms the given AST ports in an ordered map of IR ports.
	 * 
	 * @param portList
	 *            a list of AST ports
	 * @return an ordered map of IR ports
	 */
	private OrderedMap<Port> transformPorts(List<AstPort> portList) {
		OrderedMap<Port> ports = new OrderedMap<Port>();
		for (AstPort aPort : portList) {
			Location location = Util.getLocation(aPort);
			Type type = transformType(aPort.getType());
			Port port = new Port(location, type, aPort.getName());
			portMap.put(aPort, port);
			ports.add(file, location, port.getName(), port);
		}

		return ports;
	}

	private void transformProcedures(List<AstProcedure> astProcedures,
			OrderedMap<Procedure> procedures) {
		for (AstProcedure astProcedure : astProcedures) {
			String name = astProcedure.getName();
			Location location = Util.getLocation(astProcedure);

			// sets the current procedure
			procedure = new Procedure(name, location, new VoidType());

			transformParameters(astProcedure.getParameters());
			transformLocalVariables(astProcedure.getVariables());
			transformStatements(astProcedure.getStatements());

			procedures.add(file, location, name, procedure);

			proceduresMap.put(astProcedure, procedure);
		}
	}

	/**
	 * Transforms the given AST statement to one or more IR instructions and/or
	 * nodes that are added directly to the current {@link #procedure}.
	 * 
	 * @param statement
	 *            an AST statement
	 */
	private void transformStatement(AstStatement statement) {
		stmtTransformer.doSwitch(statement);
	}

	/**
	 * Transforms the given AST statements to IR instructions and/or nodes that
	 * are added directly to the current {@link #procedure}.
	 * 
	 * @param statements
	 *            a list of AST statements
	 */
	private void transformStatements(List<AstStatement> statements) {
		for (AstStatement statement : statements) {
			transformStatement(statement);
		}
	}

	/**
	 * Transforms AST state variables to IR state variables. The initial value
	 * of an AST state variable is evaluated to a constant by
	 * {@link #exprEvaluator}.
	 * 
	 * @param stateVariables
	 *            a list of AST state variables
	 * @return an ordered map of IR state variables
	 */
	private OrderedMap<Variable> transformStateVariables(
			List<AstVariable> stateVariables) {
		OrderedMap<Variable> stateVars = new OrderedMap<Variable>();
		for (AstVariable astVariable : stateVariables) {
			Location location = Util.getLocation(astVariable);
			Type type = transformType(astVariable.getType());
			String name = astVariable.getName();
			boolean assignable = !astVariable.isConstant();

			// evaluate initial value (if any)
			AstExpression astValue = astVariable.getValue();
			Object initialValue;
			if (astValue == null) {
				initialValue = null;
			} else {
				initialValue = exprEvaluator.evaluate(astValue);

				// register the value
				exprEvaluator.registerValue(astVariable, initialValue);
			}

			StateVariable stateVariable = new StateVariable(location, type,
					name, assignable, initialValue);
			stateVars.add(file, location, name, stateVariable);

			variablesMap.put(astVariable, stateVariable);
		}

		return stateVars;
	}

	/**
	 * Transforms the given AST type to an IR type.
	 * 
	 * @param type
	 *            an AST type
	 * @return an IR type
	 */
	private Type transformType(AstType type) {
		return typeTransformer.doSwitch(type);
	}

}
