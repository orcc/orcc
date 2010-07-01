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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import net.sf.orcc.ir.instructions.AbstractFifoInstruction;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

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
			LocalVariable target = procedure.newTempLocalVariable(file,
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
			Location location = Util.getLocation(expression);

			Expression condition = transformExpression(expression
					.getCondition());

			LocalVariable target = procedure.newTempLocalVariable(file,
					expression.getIrType(), "_tmp_if");

			// transforms "then" statements and "else" statements
			List<CFGNode> thenNodes = getNodes(target, expression.getThen());
			List<CFGNode> elseNodes = getNodes(target, expression.getElse());

			IfNode node = new IfNode(location, procedure, condition, thenNodes,
					elseNodes, new BlockNode(procedure));
			procedure.getNodes().add(node);

			Use use = new Use(target);
			Expression varExpr = new VarExpr(location, use);
			return varExpr;
		}

		@Override
		public Expression caseAstExpressionIndex(AstExpressionIndex expression) {
			// we always load in this case

			Location location = Util.getLocation(expression);
			AstVariable astVariable = expression.getSource().getVariable();
			Variable variable = variablesMap.get(astVariable);

			List<Expression> indexes = transformExpressions(expression
					.getIndexes());

			LocalVariable target = procedure.newTempLocalVariable(file,
					expression.getIrType(), "local_" + variable.getName());

			Load load = new Load(location, target, new Use(variable), indexes);
			addInstruction(load);

			Use use = new Use(target);
			Expression varExpr = new VarExpr(location, use);
			return varExpr;
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
				LocalVariable target = procedure.newTempLocalVariable(file,
						variable.getType(), "local_" + variable.getName());
				Load load = new Load(new Location(), target, new Use(variable),
						indexes);
				addInstruction(load);
				return target;
			}

			return (LocalVariable) variable;
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
		private List<CFGNode> getNodes(LocalVariable target,
				AstExpression astExpression) {
			Location location = Util.getLocation(astExpression);
			List<CFGNode> nodes = procedure.getNodes();

			int first = nodes.size();
			nodes.add(new BlockNode(procedure));

			Expression value = transformExpression(astExpression);
			Assign assign = new Assign(location, target, value);
			addInstruction(assign);

			int last = nodes.size();

			// moves selected CFG nodes from "nodes" list to resultNodes
			List<CFGNode> subList = nodes.subList(first, last);
			List<CFGNode> resultNodes = new ArrayList<CFGNode>(subList);
			subList.clear();

			return resultNodes;
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
				// TODO only store when necessary
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
	private OrderedMap<String, Procedure> procedures;

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
	private Scope<AstVariable, Variable> variablesMap;

	/**
	 * Creates a new AST to IR transformation.
	 */
	public AstTransformer() {
		portMap = new HashMap<AstPort, Port>();

		variablesMap = new Scope<AstVariable, Variable>();
		proceduresMap = new HashMap<AstProcedure, Procedure>();
		functionsMap = new HashMap<AstFunction, Procedure>();

		exprTransformer = new ExpressionTransformer();
		stmtTransformer = new StatementTransformer();
	}

	/**
	 * Creates calls to hasTokens to test that the given input pattern is
	 * fulfilled.
	 * 
	 * @param inputPattern
	 *            an IR input pattern
	 * @return a list of local variables that contain the result of the
	 *         hasTokens
	 */
	private List<LocalVariable> actionCreateHasTokens(Pattern inputPattern) {
		List<LocalVariable> hasTokenList = new ArrayList<LocalVariable>(
				inputPattern.size());
		for (Entry<Port, Integer> entry : inputPattern.entrySet()) {
			LocalVariable target = procedure.newTempLocalVariable(file,
					IrFactory.eINSTANCE.createTypeBool(), "_tmp_hasTokens");
			hasTokenList.add(target);

			Port port = entry.getKey();
			int numTokens = entry.getValue();
			HasTokens hasTokens = new HasTokens(port, numTokens, target);
			addInstruction(hasTokens);
		}

		return hasTokenList;
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
			int i = 0;

			for (AstVariable token : tokens) {
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(new IntExpr(i));

				LocalVariable irToken = (LocalVariable) variablesMap.get(token);
				Load load = new Load(portVariable.getLocation(), irToken,
						new Use(portVariable), indexes);
				addInstruction(load);

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
			int i = 0;

			for (AstExpression expression : values) {
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(new IntExpr(i));

				Expression value = transformExpression(expression);
				Store store = new Store(portVariable, indexes, value);
				addInstruction(store);

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
	 * Creates the test for schedulability of the given action.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param inputPattern
	 *            input pattern of action
	 * @param result
	 *            target local variable
	 */
	private void createActionTest(AstAction astAction, Pattern inputPattern,
			LocalVariable result) {
		if (inputPattern.isEmpty()) {
			transformGuards(astAction.getGuards(), result);
		} else {
			// create calls to hasTokens
			List<LocalVariable> hasTokenList = actionCreateHasTokens(inputPattern);

			// create "then" nodes with peeks and guards
			List<CFGNode> thenNodes = transformInputPatternAndGuards(astAction,
					result);

			// create "else" node with Assign(result, false)
			List<CFGNode> elseNodes = new ArrayList<CFGNode>(1);
			BlockNode block = new BlockNode(procedure);
			Assign assign = new Assign(result, new BoolExpr(false));
			block.add(assign);
			elseNodes.add(block);

			// create condition hasTokens1 && hasTokens2 && ... && hasTokensn
			Iterator<LocalVariable> it = hasTokenList.iterator();
			Expression condition = new VarExpr(new Use(it.next()));
			while (it.hasNext()) {
				Expression e2 = new VarExpr(new Use(it.next()));
				condition = new BinaryExpr(condition, BinaryOp.LOGIC_AND, e2,
						IrFactory.eINSTANCE.createTypeBool());
			}

			// create "if" node
			IfNode node = new IfNode(procedure, condition, thenNodes,
					elseNodes, new BlockNode(procedure));
			procedure.getNodes().add(node);
		}
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
				procedure.getLocation(), port.getName(),
				IrFactory.eINSTANCE.createTypeList(numTokens, port.getType()));
		procedure.getLocals().put(file, target.getLocation(), target.getName(),
				target);

		return target;
	}

	/**
	 * Fills the target IR input pattern.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param inputPattern
	 *            target IR input pattern
	 */
	private void fillsInputPattern(AstAction astAction, Pattern inputPattern) {
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
		}
	}

	/**
	 * Fills the target IR output pattern.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param outputPattern
	 *            target IR output pattern
	 */
	private void fillsOutputPattern(AstAction astAction, Pattern outputPattern) {
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
		this.file = file;

		String name = astActor.getName();
		try {
			// parameters
			OrderedMap<String, Variable> parameters = transformGlobalVariables(astActor
					.getParameters());

			// first state variables, because port's sizes may depend on them.
			OrderedMap<String, Variable> stateVars = transformGlobalVariables(astActor
					.getStateVariables());
			OrderedMap<String, Port> inputs = transformPorts(astActor
					.getInputs());
			OrderedMap<String, Port> outputs = transformPorts(astActor
					.getOutputs());

			// transforms functions and procedures
			procedures = new OrderedMap<String, Procedure>();
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
	 * Transforms the given AST action and fills the input pattern, output
	 * pattern, scheduler procedure and body procedure.
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
		fillsInputPattern(astAction, inputPattern);
		fillsOutputPattern(astAction, outputPattern);

		transformActionBody(astAction, body);
		transformActionScheduler(astAction, scheduler, inputPattern);
	}

	/**
	 * Transforms the body of the given AST action into the given body
	 * procedure.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param body
	 *            the procedure that will contain the body
	 */
	private void transformActionBody(AstAction astAction, Procedure body) {
		// current procedure is the body
		procedure = body;

		// create a new scope that extends variablesMap
		Scope<AstVariable, Variable> current = variablesMap;
		variablesMap = new Scope<AstVariable, Variable>(variablesMap, true);

		transformInputPattern(astAction, Read.class);
		transformLocalVariables(astAction.getVariables());
		transformStatements(astAction.getStatements());
		transformOutputPattern(astAction);

		// restore previous scope
		variablesMap = current;
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
	 * Transforms the scheduling information of the given AST action into the
	 * given scheduler procedure.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param scheduler
	 *            the procedure that will contain the scheduler
	 * @param inputPattern
	 *            the input pattern filled by
	 *            {@link #fillsInputPattern(AstAction, Pattern)}
	 */
	private void transformActionScheduler(AstAction astAction,
			Procedure scheduler, Pattern inputPattern) {
		// current procedure is the scheduler
		procedure = scheduler;

		// create a new scope that extends variablesMap
		Scope<AstVariable, Variable> current = variablesMap;
		variablesMap = new Scope<AstVariable, Variable>(variablesMap, true);

		LocalVariable result = procedure.newTempLocalVariable(file,
				IrFactory.eINSTANCE.createTypeBool(), "result");

		List<AstExpression> guards = astAction.getGuards();
		if (inputPattern.isEmpty() && guards.isEmpty()) {
			// the action is always fireable
			Assign assign = new Assign(result, new BoolExpr(true));
			addInstruction(assign);
		} else {
			createActionTest(astAction, inputPattern, result);
		}

		// return result
		Return returnInstr = new Return(new VarExpr(new Use(result)));
		addInstruction(returnInstr);

		// restore previous scope
		variablesMap = current;
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
		int length = expressions.size();
		List<Expression> irExpressions = new ArrayList<Expression>(length);
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

		// create a new scope that extends variablesMap
		Scope<AstVariable, Variable> current = variablesMap;
		variablesMap = new Scope<AstVariable, Variable>(variablesMap, true);

		transformParameters(astFunction.getParameters());
		transformLocalVariables(astFunction.getVariables());
		transformExpression(astFunction.getExpression());

		// restore previous scope
		variablesMap = current;

		procedures.put(file, location, name, procedure);
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
	private OrderedMap<String, Variable> transformGlobalVariables(
			List<AstVariable> stateVariables) {
		OrderedMap<String, Variable> stateVars = new OrderedMap<String, Variable>();
		for (AstVariable astVariable : stateVariables) {
			Location location = Util.getLocation(astVariable);
			Type type = astVariable.getIrType();
			String name = astVariable.getName();
			boolean assignable = !astVariable.isConstant();

			// initial value (if any) has been computed by validator
			Object initialValue = astVariable.getInitialValue();

			StateVariable stateVariable = new StateVariable(location, type,
					name, assignable, initialValue);
			stateVars.put(file, location, name, stateVariable);

			variablesMap.put(astVariable, stateVariable);
		}

		return stateVars;
	}

	/**
	 * Transforms the given guards and assign result the expression g1 && g2 &&
	 * .. && gn.
	 * 
	 * @param guards
	 *            list of guard expressions
	 * @param result
	 *            target local variable
	 */
	private void transformGuards(List<AstExpression> guards,
			LocalVariable result) {
		List<Expression> expressions = transformExpressions(guards);
		Iterator<Expression> it = expressions.iterator();
		Expression value = it.next();
		while (it.hasNext()) {
			value = new BinaryExpr(value, BinaryOp.LOGIC_AND, it.next(),
					IrFactory.eINSTANCE.createTypeBool());
		}

		Assign assign = new Assign(result, value);
		addInstruction(assign);
	}

	/**
	 * Transforms the AST input patterns of the given action as local variables,
	 * adds reads and assigns tokens.
	 * 
	 * @param astAction
	 *            an AST action
	 */
	private void transformInputPattern(AstAction astAction, Class<?> clasz) {
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

			// create port variable
			LocalVariable variable = createPortVariable(port, totalConsumption);

			// add the peek/read
			try {
				Object obj = clasz.newInstance();
				AbstractFifoInstruction instr = (AbstractFifoInstruction) obj;
				instr.setPort(port);
				instr.setNumTokens(totalConsumption);
				instr.setTarget(variable);
				addInstruction(instr);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			// declare tokens
			for (AstVariable token : tokens) {
				LocalVariable local = transformLocalVariable(token);
				procedure.getLocals().put(file, local.getLocation(),
						local.getName(), local);
			}

			// loads tokens
			actionLoadTokens(variable, tokens, repeat);
		}
	}

	/**
	 * Returns a list of CFG nodes where the input pattern of the given action
	 * is peeked. This method creates a new block node to hold the blocks, peeks
	 * the input pattern, and transfers the nodes created to a new list that is
	 * the result.
	 * 
	 * @param astAction
	 *            an AST action
	 * @param result
	 *            a local variable
	 * @return a list of CFG nodes
	 */
	private List<CFGNode> transformInputPatternAndGuards(AstAction astAction,
			LocalVariable result) {
		List<CFGNode> nodes = procedure.getNodes();

		int first = nodes.size();
		nodes.add(new BlockNode(procedure));

		List<AstExpression> guards = astAction.getGuards();
		if (guards.isEmpty()) {
			Assign assign = new Assign(result, new BoolExpr(true));
			addInstruction(assign);
		} else {
			transformInputPattern(astAction, Peek.class);
			transformLocalVariables(astAction.getVariables());
			transformGuards(astAction.getGuards(), result);
		}

		int last = nodes.size();

		// moves selected CFG nodes from "nodes" list to resultNodes
		List<CFGNode> subList = nodes.subList(first, last);
		List<CFGNode> resultNodes = new ArrayList<CFGNode>(subList);
		subList.clear();

		return resultNodes;
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
				type);

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
			procedure.getLocals().put(file, local.getLocation(),
					local.getName(), local);
		}
	}

	/**
	 * Transforms the AST output patterns of the given action as expressions and
	 * possibly statements, assigns tokens and adds writes.
	 * 
	 * @param astAction
	 *            an AST action
	 */
	private void transformOutputPattern(AstAction astAction) {
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

			// create port variable
			LocalVariable variable = createPortVariable(port, totalConsumption);

			// store tokens
			actionStoreTokens(variable, values, repeat);

			// add the write
			Write write = new Write(port, totalConsumption, variable);
			addInstruction(write);
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
			procedure.getParameters().put(file, local.getLocation(),
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
	private OrderedMap<String, Port> transformPorts(List<AstPort> portList) {
		OrderedMap<String, Port> ports = new OrderedMap<String, Port>();
		for (AstPort astPort : portList) {
			Location location = Util.getLocation(astPort);
			Type type = astPort.getIrType();
			Port port = new Port(location, type, astPort.getName());
			portMap.put(astPort, port);
			ports.put(file, location, port.getName(), port);
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

		// create a new scope that extends variablesMap
		Scope<AstVariable, Variable> current = variablesMap;
		variablesMap = new Scope<AstVariable, Variable>(variablesMap, true);

		transformParameters(astProcedure.getParameters());
		transformLocalVariables(astProcedure.getVariables());
		transformStatements(astProcedure.getStatements());

		// restore previous scope
		variablesMap = current;

		procedures.put(file, location, name, procedure);
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
