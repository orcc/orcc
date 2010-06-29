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
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.cal.type.TypeChecker;
import net.sf.orcc.frontend.schedule.ActionSorter;
import net.sf.orcc.frontend.schedule.FSMBuilder;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
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
			BinaryOp op = BinaryOp.getOperator(expression.getOperator());
			Expression e1 = doSwitch(expression.getLeft());
			Expression e2 = doSwitch(expression.getRight());

			return new BinaryExpr(Util.getLocation(expression), e1, op, e2,
					expression.getIrType());
		}

		@Override
		public Expression caseAstExpressionBoolean(
				AstExpressionBoolean expression) {
			Location location = Util.getLocation(expression);
			boolean value = expression.isValue();
			return new BoolExpr(location, value);
		}

		@Override
		public Expression caseAstExpressionCall(AstExpressionCall astCall) {
			Location location = Util.getLocation(astCall);

			// retrieve IR procedure
			AstFunction astFunction = astCall.getFunction();

			// special case if the function is a built-in function
			Expression result = transformBuiltinFunction(astCall);
			if (result != null) {
				return result;
			}

			if (!functionsMap.containsKey(astFunction)) {
				Procedure current = procedure;
				transformFunction(astFunction);
				procedure = current;
			}
			Procedure procedure = functionsMap.get(astFunction);

			// transform parameters
			List<Expression> parameters = transformExpressions(astCall
					.getParameters());

			// generates a new target
			LocalVariable target = newTempLocalVariable(
					procedure.getReturnType(), "call_" + procedure.getName());

			// add call
			Call call = new Call(location, target, procedure, parameters);
			addInstruction(call);

			// return local variable
			Use use = new Use(target);
			Expression varExpr = new VarExpr(location, use);
			return varExpr;
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
			return new StringExpr(expression.getValue());
		}

		@Override
		public Expression caseAstExpressionUnary(AstExpressionUnary expression) {
			UnaryOp op = UnaryOp.getOperator(expression.getUnaryOperator());
			Expression expr = doSwitch(expression.getExpression());

			return new UnaryExpr(Util.getLocation(expression), op, expr,
					expression.getIrType());
		}

		@Override
		public Expression caseAstExpressionVariable(
				AstExpressionVariable expression) {
			AstVariable astVariable = expression.getValue().getVariable();
			Location location = Util.getLocation(expression);

			LocalVariable local = getLocalVariable(astVariable);
			Use use = new Use(local);
			Expression varExpr = new VarExpr(location, use);
			return varExpr;
		}

		/**
		 * Returns the IR mapping of the given AST variable. If the AST variable
		 * is a global, loads it.
		 * 
		 * @param astVariable
		 *            an AST variable
		 * @return a local IR variable
		 */
		private LocalVariable getLocalVariable(AstVariable astVariable) {
			Variable variable = variablesMap.get(astVariable);
			if (variable.isGlobal()) {
				// TODO use a map to only load when necessary

				List<Expression> indexes = new ArrayList<Expression>(0);
				LocalVariable target = newTempLocalVariable(variable.getType(),
						"local_" + variable.getName());
				Load load = new Load(new Location(), target, new Use(variable),
						indexes);
				addInstruction(load);
				return target;
			}

			return (LocalVariable) variable;
		}

		private Expression transformBuiltinFunction(AstExpressionCall astCall) {
			Location location = Util.getLocation(astCall);
			String name = astCall.getFunction().getName();
			if ("bitnot".equals(name)) {
				Expression expr = transformExpression(astCall.getParameters()
						.get(0));
				return new UnaryExpr(location, UnaryOp.BITNOT, expr,
						expr.getType());
			}

			BinaryOp op = null;
			if ("bitand".equals(name)) {
				op = BinaryOp.BITAND;
			}
			if ("bitor".equals(name)) {
				op = BinaryOp.BITOR;
			}
			if ("bitxor".equals(name)) {
				op = BinaryOp.BITXOR;
			}
			if ("lshift".equals(name)) {
				op = BinaryOp.SHIFT_LEFT;
			}
			if ("rshift".equals(name)) {
				op = BinaryOp.SHIFT_RIGHT;
			}

			if (op == null) {
				return null;
			}

			Expression e1 = transformExpression(astCall.getParameters().get(0));
			Expression e2 = transformExpression(astCall.getParameters().get(1));
			return new BinaryExpr(location, e1, op, e2,
					new TypeChecker().getLub(e1.getType(), e2.getType()));
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

			// add assign or store instruction
			Instruction instruction;
			if (indexes.isEmpty() && !target.isGlobal()) {
				LocalVariable local = (LocalVariable) target;
				instruction = new Assign(location, local, value);
			} else {
				instruction = new Store(location, target, indexes, value);
			}
			addInstruction(instruction);

			return null;
		}

		@Override
		public Void caseAstStatementCall(AstStatementCall astCall) {
			Location location = Util.getLocation(astCall);

			// retrieve IR procedure
			AstProcedure astProcedure = astCall.getProcedure();
			if (!proceduresMap.containsKey(astProcedure)) {
				Procedure current = procedure;
				transformProcedure(astProcedure);
				procedure = current;
			}
			Procedure procedure = proceduresMap.get(astProcedure);

			// transform parameters
			List<Expression> parameters = transformExpressions(astCall
					.getParameters());

			// add call
			Call call = new Call(location, null, procedure, parameters);
			addInstruction(call);

			return null;
		}

		@Override
		public Void caseAstStatementForeach(AstStatementForeach foreach) {
			return null;
		}

		@Override
		public Void caseAstStatementIf(AstStatementIf stmtIf) {
			Expression condition = transformExpression(stmtIf.getCondition());

			// transforms "then" statements and "else" statements
			List<CFGNode> thenNodes = getNodes(stmtIf.getThen());
			List<CFGNode> elseNodes = getNodes(stmtIf.getElse());

			IfNode node = new IfNode(procedure, condition, thenNodes,
					elseNodes, new BlockNode(procedure));
			procedure.getNodes().add(node);

			return null;
		}

		@Override
		public Void caseAstStatementWhile(AstStatementWhile stmtWhile) {
			return null;
		}

		/**
		 * Returns a list of CFG nodes from the given list of statements. This
		 * method creates a new block node to hold the statements, transforms
		 * the statements, and transfers the nodes created to a new list that is
		 * the result.
		 * 
		 * @param statements
		 *            a list of statements
		 * @return a list of CFG nodes
		 */
		private List<CFGNode> getNodes(List<AstStatement> statements) {
			List<CFGNode> nodes = procedure.getNodes();

			int first = nodes.size();
			nodes.add(new BlockNode(procedure));
			transformStatements(statements);
			int last = nodes.size();

			// moves selected CFG nodes from "nodes" list to resultNodes
			List<CFGNode> subList = nodes.subList(first, last);
			List<CFGNode> resultNodes = new ArrayList<CFGNode>(subList);
			subList.clear();

			return resultNodes;
		}

	}

	private final java.util.regex.Pattern dotPattern = java.util.regex.Pattern
			.compile("\\.");

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
	 * the list of procedures of the IR actor.
	 */
	private OrderedMap<Procedure> procedures;

	/**
	 * A map from AST procedures to IR procedures.
	 */
	final private Map<AstProcedure, Procedure> proceduresMap;

	/**
	 * statement transformer.
	 */
	final private StatementTransformer stmtTransformer;

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
	}

	private void actionFillIsSchedulable(AstAction astAction,
			Procedure scheduler) {
		// TODO fill is schedulable function
	}

	/**
	 * Loads tokens from the data that was read and put in portVariable.
	 * 
	 * @param portVariable
	 *            a local array that contains data.
	 * @param tokens
	 *            a list of token variables
	 * @param repeat
	 *            an integer number of repeat (equals to one if there is no
	 *            repeat)
	 */
	private void actionLoadTokens(LocalVariable portVariable,
			List<AstVariable> tokens, int repeat) {
		if (repeat == 1) {
			BlockNode block = BlockNode.getLast(procedure);
			int i = 0;
			for (AstVariable token : tokens) {
				LocalVariable irToken = (LocalVariable) variablesMap.get(token);
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(new IntExpr(i));
				Load load = new Load(portVariable.getLocation(), irToken,
						new Use(portVariable), indexes);
				block.add(load);

				i++;
			}
		} else {
			// TODO when repeat is greater than one
		}
	}

	/**
	 * Assigns tokens to the data that will be written.
	 * 
	 * @param portVariable
	 *            a local array that will contain data.
	 * @param values
	 *            a list of AST expressions
	 * @param repeat
	 *            an integer number of repeat (equals to one if there is no
	 *            repeat)
	 */
	private void actionStoreTokens(LocalVariable portVariable,
			List<AstExpression> values, int repeat) {
		if (repeat == 1) {
			BlockNode block = BlockNode.getLast(procedure);
			int i = 0;

			for (AstExpression expression : values) {
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(new IntExpr(i));

				Expression value = transformExpression(expression);
				Store store = new Store(portVariable, indexes, value);
				block.add(store);

				i++;
			}
		} else {
			// TODO when repeat is greater than one
		}
	}

	/**
	 * Adds the given instruction to the last block of the current procedure.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	private void addInstruction(Instruction instruction) {
		BlockNode block = BlockNode.getLast(procedure);
		block.add(instruction);
	}

	/**
	 * Creates a variable to hold the number of tokens on the given port.
	 * 
	 * @param port
	 *            a port
	 * @param numTokens
	 *            number of tokens
	 * @return the local array created
	 */
	private LocalVariable createPortVariable(Port port, int numTokens) {
		// create the variable to hold the tokens
		LocalVariable target = new LocalVariable(true, 0,
				procedure.getLocation(), port.getName(), null,
				IrFactory.eINSTANCE.createTypeList(numTokens, port.getType()));
		procedure.getLocals().add(file, target.getLocation(), target.getName(),
				target);

		return target;
	}

	/**
	 * Creates a new local variable that can be used to hold intermediate
	 * results. The variable is added to {@link #procedure}'s locals.
	 * 
	 * @param type
	 *            type of the variable
	 * @param name
	 *            hint for the variable name
	 * @return a new local variable
	 */
	private LocalVariable newTempLocalVariable(Type type, String hint) {
		String name = hint;
		OrderedMap<Variable> locals = procedure.getLocals();
		Variable variable = locals.get(name);
		int i = 0;
		while (variable != null) {
			name = hint + i;
			variable = locals.get(name);
			i++;
		}

		variable = new LocalVariable(true, 0, new Location(), name, null, type);
		locals.add(hint, variable.getLocation(), variable.getName(), variable);
		return (LocalVariable) variable;
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
		this.file = file;

		String name = astActor.getName();
		try {
			// parameters
			OrderedMap<Variable> parameters = transformGlobalVariables(astActor
					.getParameters());

			// first state variables, because port's sizes may depend on them.
			OrderedMap<Variable> stateVars = transformGlobalVariables(astActor
					.getStateVariables());
			OrderedMap<Port> inputs = transformPorts(astActor.getInputs());
			OrderedMap<Port> outputs = transformPorts(astActor.getOutputs());

			// transforms functions and procedures
			procedures = new OrderedMap<Procedure>();
			for (AstFunction astFunction : astActor.getFunctions()) {
				transformFunction(astFunction);
			}
			for (AstProcedure astProcedure : astActor.getProcedures()) {
				transformProcedure(astProcedure);
			}

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
				scheduler = new ActionScheduler(actions.getUntaggedActions(),
						fsm);
			}

			// create IR actor
			return new Actor(name, file, parameters, inputs, outputs,
					stateVars, procedures, actions.getAllActions(),
					initializes.getAllActions(), scheduler);
		} finally {
			// cleanup
			functionsMap.clear();
			portMap.clear();
			procedure = null;
			procedures.clear();
			proceduresMap.clear();
			variablesMap.clear();
		}
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
		actionFillIsSchedulable(astAction, scheduler);

		// current procedure is the body
		procedure = body;

		transformInputPattern(astAction, inputPattern);
		transformLocalVariables(astAction.getVariables());
		transformStatements(astAction.getStatements());
		transformOutputPattern(astAction, outputPattern);
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
					location, IrFactory.eINSTANCE.createTypeBool());
			Procedure body = new Procedure(name, location,
					IrFactory.eINSTANCE.createTypeVoid());

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

	/**
	 * Transforms the given AST function to an IR procedure, and adds it to the
	 * IR procedure list {@link #procedures} and to the map
	 * {@link #functionsMap}.
	 * 
	 * @param astFunction
	 *            an AST function
	 */
	private void transformFunction(AstFunction astFunction) {
		String name = astFunction.getName();
		Location location = Util.getLocation(astFunction);
		Type type = astFunction.getIrType();

		// sets the current procedure
		procedure = new Procedure(name, location, type);

		transformParameters(astFunction.getParameters());
		transformLocalVariables(astFunction.getVariables());
		transformExpression(astFunction.getExpression());

		procedures.add(file, location, name, procedure);
		functionsMap.put(astFunction, procedure);
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
	private OrderedMap<Variable> transformGlobalVariables(
			List<AstVariable> stateVariables) {
		OrderedMap<Variable> stateVars = new OrderedMap<Variable>();
		for (AstVariable astVariable : stateVariables) {
			Location location = Util.getLocation(astVariable);
			Type type = astVariable.getIrType();
			String name = astVariable.getName();
			boolean assignable = !astVariable.isConstant();

			// initial value (if any) has been computed by validator
			Object initialValue = astVariable.getInitialValue();

			StateVariable stateVariable = new StateVariable(location, type,
					name, assignable, initialValue);
			stateVars.add(file, location, name, stateVariable);

			variablesMap.put(astVariable, stateVariable);
		}

		return stateVars;
	}

	/**
	 * Transforms the AST input patterns of the given action as local variables,
	 * fills the target IR input pattern, adds reads and assigns tokens.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param inputPattern
	 *            target IR input pattern
	 */
	private void transformInputPattern(AstAction astAction, Pattern inputPattern) {
		List<AstInputPattern> astInputPattern = astAction.getInputs();
		for (AstInputPattern pattern : astInputPattern) {
			Port port = portMap.get(pattern.getPort());
			List<AstVariable> tokens = pattern.getTokens();

			// evaluates token consumption
			int totalConsumption = tokens.size();
			int repeat = 1;
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				repeat = new AstExpressionEvaluator()
						.evaluateAsInteger(astRepeat);
				totalConsumption *= repeat;
			}
			inputPattern.put(port, totalConsumption);

			// create port variable
			LocalVariable variable = createPortVariable(port, totalConsumption);

			// add the read
			BlockNode block = BlockNode.getLast(procedure);
			Read read = new Read(port, totalConsumption, variable);
			block.add(read);

			// declare tokens
			for (AstVariable token : tokens) {
				LocalVariable local = transformLocalVariable(token);
				procedure.getLocals().add(file, local.getLocation(),
						local.getName(), local);
			}

			// loads tokens
			actionLoadTokens(variable, tokens, repeat);
		}
	}

	/**
	 * Transforms the given AST variable to an IR variable that has the name and
	 * type of <code>astVariable</code>. A binding is added to the
	 * {@link #variablesMap} between astVariable and the created local variable.
	 * 
	 * @param astVariable
	 *            an AST variable
	 * @return the IR local variable created
	 */
	private LocalVariable transformLocalVariable(AstVariable astVariable) {
		Location location = Util.getLocation(astVariable);
		String name = astVariable.getName();
		boolean assignable = !astVariable.isConstant();
		Type type = astVariable.getIrType();

		LocalVariable local = new LocalVariable(assignable, 0, location, name,
				null, type);

		AstExpression value = astVariable.getValue();
		if (value != null) {
			Expression expression = transformExpression(value);
			Assign assign = new Assign(location, local, expression);
			addInstruction(assign);
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
			LocalVariable local = transformLocalVariable(astVariable);
			procedure.getLocals().add(file, local.getLocation(),
					local.getName(), local);
		}
	}

	/**
	 * Transforms the AST output patterns of the given action as expressions and
	 * possibly statements, fills the target IR output pattern, assigns tokens
	 * and adds writes.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param outputPattern
	 *            target IR output pattern
	 */
	private void transformOutputPattern(AstAction astAction,
			Pattern outputPattern) {
		List<AstOutputPattern> astOutputPattern = astAction.getOutputs();
		for (AstOutputPattern pattern : astOutputPattern) {
			Port port = portMap.get(pattern.getPort());
			List<AstExpression> values = pattern.getValues();

			// evaluates token consumption
			int totalConsumption = values.size();
			int repeat = 1;
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				repeat = new AstExpressionEvaluator()
						.evaluateAsInteger(astRepeat);
				totalConsumption *= repeat;
			}
			outputPattern.put(port, totalConsumption);

			// create port variable
			LocalVariable variable = createPortVariable(port, totalConsumption);

			// store tokens
			actionStoreTokens(variable, values, repeat);

			// add the write
			BlockNode block = BlockNode.getLast(procedure);
			Write write = new Write(port, totalConsumption, variable);
			block.add(write);
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
			LocalVariable local = transformLocalVariable(astParameter);
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
		for (AstPort astPort : portList) {
			Location location = Util.getLocation(astPort);
			Type type = astPort.getIrType();
			Port port = new Port(location, type, astPort.getName());
			portMap.put(astPort, port);
			ports.add(file, location, port.getName(), port);
		}

		return ports;
	}

	/**
	 * Transforms the given AST procedure to an IR procedure, and adds it to the
	 * IR procedure list {@link #procedures} and to the map
	 * {@link #proceduresMap}.
	 * 
	 * @param astProcedure
	 *            an AST procedure
	 */
	private void transformProcedure(AstProcedure astProcedure) {
		String name = astProcedure.getName();
		Location location = Util.getLocation(astProcedure);

		// sets the current procedure
		procedure = new Procedure(name, location,
				IrFactory.eINSTANCE.createTypeVoid());

		transformParameters(astProcedure.getParameters());
		transformLocalVariables(astProcedure.getVariables());
		transformStatements(astProcedure.getStatements());

		procedures.add(file, location, name, procedure);
		proceduresMap.put(astProcedure, procedure);
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

}
