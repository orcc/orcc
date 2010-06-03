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
import net.sf.orcc.ir.expr.IntExpr;
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

	@Inject
	private IQualifiedNameProvider nameProvider;

	/**
	 * This class transforms an AST statement into one or more IR instructions
	 * and/or nodes.
	 * 
	 */
	private class ExpressionTransformer extends CalSwitch<Expression> {

		@Override
		public Expression caseAstExpressionBinary(AstExpressionBinary expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionBoolean(
				AstExpressionBoolean expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionCall(AstExpressionCall call) {
			return null;
		}

		@Override
		public Expression caseAstExpressionIf(AstExpressionIf expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionIndex(AstExpressionIndex expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionInteger(
				AstExpressionInteger expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionList(AstExpressionList expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionString(AstExpressionString expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionUnary(AstExpressionUnary expression) {
			return null;
		}

		@Override
		public Expression caseAstExpressionVariable(
				AstExpressionVariable expression) {
			return null;
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
		public Void caseAstStatementAssign(AstStatementAssign assign) {
			String.valueOf(nodes);
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
			Expression size;
			if (astSize == null) {
				size = new IntExpr(Util.getLocation(type), 32);
			} else {
				size = exprEvaluator.evaluateAsIntExpr(astSize);
			}
			return new IntType(size);
		}

		@Override
		public Type caseAstTypeList(AstTypeList listType) {
			Type type = transformType(listType.getType());
			Expression size = exprEvaluator.evaluateAsIntExpr(listType
					.getSize());
			return new ListType(size, type);
		}

		@Override
		public Type caseAstTypeString(AstTypeString type) {
			return new StringType();
		}

		@Override
		public Type caseAstTypeUint(AstTypeUint type) {
			AstExpression astSize = type.getSize();
			Expression size;
			if (astSize == null) {
				size = new IntExpr(Util.getLocation(type), 32);
			} else {
				size = exprEvaluator.evaluateAsIntExpr(astSize);
			}
			return new UintType(size);
		}

	}

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

	/**
	 * Contains the current list of nodes where nodes should be added by the
	 * expression transformer.
	 */
	private List<CFGNode> nodes;

	/**
	 * A map from AST ports to IR ports.
	 */
	final private Map<AstPort, Port> portMap;

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
	 * Adds reads to the {@link #nodes} node list. The number of tokens to read
	 * on each port is stored in the given IR input pattern. This method also
	 * reorganizes tokens if necessary (in cases such as [a, b] repeat 3).
	 * 
	 * @param astAction
	 *            the AST action
	 * @param inputPattern
	 *            the IR input pattern
	 * @param locals
	 *            local variables of the procedure
	 */
	private void actionAddReads(AstAction astAction, Pattern inputPattern,
			OrderedMap<Variable> locals) {
		// TODO add reads to the "nodes" node list
	}

	/**
	 * Adds writes to the {@link #nodes} node list. The number of tokens to
	 * write on each port is stored in the given IR output pattern. This method
	 * also reorganizes tokens if necessary (in cases such as [a, b] repeat 3).
	 * 
	 * @param astAction
	 *            the AST action
	 * @param outputPattern
	 *            the IR output pattern
	 */
	private void actionAddWrites(AstAction astAction, Pattern outputPattern) {
		// TODO add writes to the "nodes" node list
	}

	/**
	 * Declares the tokens declared in the given list of AST input patterns as
	 * local variables.
	 * 
	 * @param inputPattern
	 *            a list of AST input patterns, where an AST input patterns
	 *            contains a list of tokens and possibly a repeat clause.
	 * @param locals
	 *            local variables of the procedure
	 */
	private void actionDeclareTokens(List<AstInputPattern> inputPattern,
			OrderedMap<Variable> locals) {
		// TODO add tokens to "locals" ordered map
	}

	/**
	 * Fills the IR input pattern from the list of AST input patterns.
	 * 
	 * @param inputs
	 *            a list of tokens (and possible repeat clause).
	 * @param inputPattern
	 *            target IR input pattern
	 */
	private void actionFillInputPattern(List<AstInputPattern> inputs,
			Pattern inputPattern) {
		// TODO fill input pattern
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
		OrderedMap<Procedure> procedures = transformProcedures(astActor
				.getProcedures());

		// transform actions
		ActionList actions = transformActions(astActor.getActions());

		// transform initializes
		ActionList initializes = transformActions(astActor.getInitializes());

		// sort actions by priority
		ActionSorter sorter = new ActionSorter(actions);
		actions = sorter.applyPriority(astActor.getPriorities());

		// transform FSM
		AstSchedule schedule = astActor.getSchedule();
		FSM fsm = null;
		if (schedule != null) {
			FSMBuilder builder = new FSMBuilder(astActor.getSchedule());
			fsm = builder.buildFSM(actions);
		}

		// create action scheduler
		ActionScheduler scheduler = new ActionScheduler(actions.getList(), fsm);

		// create IR actor
		return new Actor(name, file, parameters, inputs, outputs, stateVars,
				procedures, actions.getList(), initializes.getList(),
				scheduler, null);
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
		actionFillInputPattern(astAction.getInputs(), inputPattern);
		actionFillOutputPattern(astAction.getOutputs(), outputPattern);

		actionFillIsSchedulable(astAction, scheduler);

		// will append nodes to the body's node list
		this.nodes = body.getNodes();

		actionDeclareTokens(astAction.getInputs(), body.getLocals());
		actionAddReads(astAction, inputPattern, body.getLocals());
		transformLocalVariables(astAction.getVariables(), body.getLocals());
		transformStatements(astAction.getStatements(), body);
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

			// creates scheduler and body
			Procedure scheduler = new Procedure(name, location, new BoolType());
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
	 * nodes may be created and added to the {@link #nodes} attribute, since
	 * many RVC-CAL expressions are expressed with IR statements.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return an IR expression
	 */
	private Expression transformExpression(AstExpression expression) {
		return exprTransformer.doSwitch(expression);
	}

	private void transformLocalVariables(List<AstVariable> variables,
			OrderedMap<Variable> locals) {
		for (AstVariable astVariable : variables) {
			Location location = Util.getLocation(astVariable);
			String name = astVariable.getName();
			Type type = transformType(astVariable.getType());
			boolean assignable = astVariable.isAssignable();

			LocalVariable local = new LocalVariable(assignable, 0, location,
					name, null, null, type);

			AstExpression value = astVariable.getValue();
			if (value != null) {
				Expression expression = transformExpression(value);
				// TODO assign to target variable
				String.valueOf(expression);
			}

			locals.add(name, location, name, local);
			variablesMap.put(astVariable, local);
		}
	}

	private void transformParameters(List<AstVariable> parameters,
			Procedure procedure) {
		for (AstVariable astParameter : parameters) {
			Location location = Util.getLocation(astParameter);
			String name = astParameter.getName();
			Type type = transformType(astParameter.getType());

			LocalVariable variable = new LocalVariable(true, 0, location, name,
					null, null, type);

			procedure.getParameters().add(name, location, name, variable);

			variablesMap.put(astParameter, variable);
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

	private OrderedMap<Procedure> transformProcedures(
			List<AstProcedure> astProcedures) {
		OrderedMap<Procedure> procedures = new OrderedMap<Procedure>();
		for (AstProcedure astProcedure : astProcedures) {
			String name = astProcedure.getName();
			Location location = Util.getLocation(astProcedure);

			Procedure procedure = new Procedure(name, location, new VoidType());

			// will append nodes to this procedure's node list
			this.nodes = procedure.getNodes();

			transformParameters(astProcedure.getParameters(), procedure);
			transformLocalVariables(astProcedure.getVariables(),
					procedure.getLocals());
			transformStatements(astProcedure.getStatements(), procedure);

			procedures.add(file, location, name, procedure);

			proceduresMap.put(astProcedure, procedure);
		}

		return procedures;
	}

	/**
	 * Transforms the given AST statement to one or more IR instructions and/or
	 * nodes that are added directly to the {@link #nodes} attribute.
	 * 
	 * @param statement
	 *            an AST statement
	 */
	private void transformStatement(AstStatement statement) {
		stmtTransformer.doSwitch(statement);
	}

	private void transformStatements(List<AstStatement> statements,
			Procedure procedure) {
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
			boolean assignable = astVariable.isAssignable();

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
