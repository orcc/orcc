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
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstSchedule;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
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
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.AbstractFifoInstruction;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;

import com.google.inject.Inject;

/**
 * This class transforms an AST actor to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorTransformer {

	@Inject
	private AstTransformer astTransformer;

	private final java.util.regex.Pattern dotPattern = java.util.regex.Pattern
			.compile("\\.");

	/**
	 * The file in which the actor is defined.
	 */
	private String file;

	/**
	 * A map from AST ports to IR ports.
	 */
	final private Map<AstPort, Port> mapPorts;

	/**
	 * Creates a new AST to IR transformation.
	 */
	public ActorTransformer() {
		mapPorts = new HashMap<AstPort, Port>();
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
			LocalVariable target = astTransformer
					.getContext()
					.getProcedure()
					.newTempLocalVariable(file,
							IrFactory.eINSTANCE.createTypeBool(),
							"_tmp_hasTokens");
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
		Context context = astTransformer.getContext();
		if (repeat == 1) {
			int i = 0;

			for (AstVariable token : tokens) {
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(new IntExpr(i));

				LocalVariable irToken = (LocalVariable) context
						.getVariable(token);
				Load load = new Load(portVariable.getLocation(), irToken,
						new Use(portVariable), indexes);
				addInstruction(load);

				i++;
			}
		} else {
			Procedure procedure = context.getProcedure();

			// creates loop variable and initializes it
			LocalVariable loopVar = procedure.newTempLocalVariable(file,
					IrFactory.eINSTANCE.createTypeInt(32), "num_repeats");
			Assign assign = new Assign(loopVar, new IntExpr(0));
			addInstruction(assign);

			BlockNode block = new BlockNode(procedure);

			int i = 0;
			int numTokens = tokens.size();
			Type type = ((TypeList) portVariable.getType()).getType();
			for (AstVariable token : tokens) {
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(new BinaryExpr(new BinaryExpr(
						new IntExpr(numTokens), BinaryOp.TIMES, new VarExpr(
								new Use(loopVar)), IrFactory.eINSTANCE
								.createTypeInt(32)), BinaryOp.PLUS,
						new IntExpr(i), IrFactory.eINSTANCE.createTypeInt(32)));

				LocalVariable tmpVar = procedure.newTempLocalVariable(file,
						type, "token");
				Load load = new Load(portVariable.getLocation(), tmpVar,
						new Use(portVariable), indexes);
				block.add(load);

				LocalVariable irToken = (LocalVariable) context
						.getVariable(token);

				indexes = new ArrayList<Expression>(1);
				indexes.add(new VarExpr(new Use(loopVar)));
				Store store = new Store(irToken, indexes, new VarExpr(new Use(
						tmpVar)));
				block.add(store);

				i++;
			}

			// add increment
			assign = new Assign(loopVar,
					new BinaryExpr(new VarExpr(new Use(loopVar)),
							BinaryOp.PLUS, new IntExpr(1), loopVar.getType()));
			block.add(assign);

			// create while node
			Expression condition = new BinaryExpr(
					new VarExpr(new Use(loopVar)), BinaryOp.LT, new IntExpr(
							repeat), IrFactory.eINSTANCE.createTypeBool());
			List<CFGNode> nodes = new ArrayList<CFGNode>(1);
			nodes.add(block);
			WhileNode whileNode = new WhileNode(procedure, condition, nodes,
					new BlockNode(procedure));
			procedure.getNodes().add(whileNode);
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

				Expression value = astTransformer
						.transformExpression(expression);
				Store store = new Store(portVariable, indexes, value);
				addInstruction(store);

				i++;
			}
		} else {
			Context context = astTransformer.getContext();
			Procedure procedure = context.getProcedure();

			// creates loop variable and initializes it
			LocalVariable loopVar = procedure.newTempLocalVariable(file,
					IrFactory.eINSTANCE.createTypeInt(32), "num_repeats");
			Assign assign = new Assign(loopVar, new IntExpr(0));
			addInstruction(assign);

			BlockNode block = new BlockNode(procedure);

			int i = 0;
			int numTokens = values.size();
			Type type = ((TypeList) portVariable.getType()).getType();
			for (AstExpression value : values) {
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(new VarExpr(new Use(loopVar)));

				// each expression of an output pattern must be of type list
				// so they are necessarily variables
				LocalVariable tmpVar = procedure.newTempLocalVariable(file,
						type, "token");
				Expression expression = astTransformer
						.transformExpression(value);
				Use use = ((VarExpr) expression).getVar();
				use.remove();
				use = new Use(use.getVariable());
				Load load = new Load(tmpVar, use, indexes);
				block.add(load);

				indexes = new ArrayList<Expression>(1);
				indexes.add(new BinaryExpr(new BinaryExpr(
						new IntExpr(numTokens), BinaryOp.TIMES, new VarExpr(
								new Use(loopVar)), IrFactory.eINSTANCE
								.createTypeInt(32)), BinaryOp.PLUS,
						new IntExpr(i), IrFactory.eINSTANCE.createTypeInt(32)));
				Store store = new Store(portVariable, indexes, new VarExpr(
						new Use(tmpVar)));
				block.add(store);

				i++;
			}

			// add increment
			assign = new Assign(loopVar,
					new BinaryExpr(new VarExpr(new Use(loopVar)),
							BinaryOp.PLUS, new IntExpr(1), loopVar.getType()));
			block.add(assign);

			// create while node
			Expression condition = new BinaryExpr(
					new VarExpr(new Use(loopVar)), BinaryOp.LT, new IntExpr(
							repeat), IrFactory.eINSTANCE.createTypeBool());
			List<CFGNode> nodes = new ArrayList<CFGNode>(1);
			nodes.add(block);
			WhileNode whileNode = new WhileNode(procedure, condition, nodes,
					new BlockNode(procedure));
			procedure.getNodes().add(whileNode);
		}
	}

	/**
	 * Adds the given instruction to the last block of the current procedure.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	private void addInstruction(Instruction instruction) {
		Context context = astTransformer.getContext();
		BlockNode block = BlockNode.getLast(context.getProcedure());
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
		Context context = astTransformer.getContext();
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
			BlockNode block = new BlockNode(context.getProcedure());
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
			IfNode node = new IfNode(context.getProcedure(), condition,
					thenNodes, elseNodes, new BlockNode(context.getProcedure()));
			context.getProcedure().getNodes().add(node);
		}
	}

	/**
	 * Creates a new empty "initialize" action that is empty and always
	 * schedulable.
	 * 
	 * @return an initialize action
	 */
	private Action createInitialize() {
		Location location = new Location();

		// transform tag
		Tag tag = new Tag();

		Pattern inputPattern = new Pattern(0);
		Pattern outputPattern = new Pattern(0);

		Procedure scheduler = new Procedure("isSchedulable_init_actor",
				location, IrFactory.eINSTANCE.createTypeBool());
		Procedure body = new Procedure("init_actor", location,
				IrFactory.eINSTANCE.createTypeVoid());

		// add return instructions
		astTransformer.addReturn(scheduler, new BoolExpr(true));
		astTransformer.addReturn(body, null);

		// creates action
		Action action = new Action(location, tag, inputPattern, outputPattern,
				scheduler, body);
		return action;
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
		Context context = astTransformer.getContext();
		LocalVariable target = new LocalVariable(true, 0, context
				.getProcedure().getLocation(), port.getName(),
				IrFactory.eINSTANCE.createTypeList(numTokens, port.getType()));
		context.getProcedure().getLocals()
				.put(file, target.getLocation(), target.getName(), target);

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
			Port port = mapPorts.get(pattern.getPort());
			List<AstVariable> tokens = pattern.getTokens();

			// evaluates token consumption
			int totalConsumption = tokens.size();
			int repeat = 1;
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				repeat = new AstExpressionEvaluator(null)
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
			Port port = mapPorts.get(pattern.getPort());
			List<AstExpression> values = pattern.getValues();

			// evaluates token consumption
			int totalConsumption = values.size();
			int repeat = 1;
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				repeat = new AstExpressionEvaluator(null)
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
		astTransformer.setFile(file);

		Context context = astTransformer.getContext();
		try {
			Location location = Util.getLocation(astActor);

			// parameters
			OrderedMap<String, StateVariable> parameters = astTransformer
					.transformGlobalVariables(astActor.getParameters());

			// first state variables, because port's sizes may depend on them.
			OrderedMap<String, StateVariable> stateVars = astTransformer
					.transformGlobalVariables(astActor.getStateVariables());

			// then ports
			OrderedMap<String, Port> inputs = transformPorts(astActor
					.getInputs());
			OrderedMap<String, Port> outputs = transformPorts(astActor
					.getOutputs());

			// creates a new scope before translating things with local
			// variables
			context.newScope();

			// transforms functions and procedures
			OrderedMap<String, Procedure> procedures = astTransformer
					.getProcedures();
			for (AstFunction astFunction : astActor.getFunctions()) {
				astTransformer.transformFunction(astFunction);
			}
			for (AstProcedure astProcedure : astActor.getProcedures()) {
				astTransformer.transformProcedure(astProcedure);
			}

			// transform actions
			ActionList actions = transformActions(astActor.getActions());

			// transform initializes
			ActionList initializes = transformActions(astActor.getInitializes());

			// add call to initialize procedure (if any)
			Procedure initialize = astTransformer.getInitialize();
			if (initialize != null) {
				procedures
						.put(file, location, initialize.getName(), initialize);

				if (initializes.isEmpty()) {
					Action action = createInitialize();
					initializes.add(action);
				}

				for (Action action : initializes) {
					BlockNode block = BlockNode.getFirst(action.getBody());
					List<Expression> params = new ArrayList<Expression>(0);
					block.add(0, new Call(location, null, initialize, params));
				}
			}

			// sort actions by priority
			ActionSorter sorter = new ActionSorter(actions);
			ActionList sortedActions = sorter.applyPriority(astActor
					.getPriorities());

			// transform FSM
			AstSchedule schedule = astActor.getSchedule();
			ActionScheduler scheduler;
			if (schedule == null) {
				scheduler = new ActionScheduler(sortedActions.getAllActions(),
						null);
			} else {
				FSMBuilder builder = new FSMBuilder(astActor.getSchedule());
				FSM fsm = builder.buildFSM(sortedActions);
				scheduler = new ActionScheduler(
						sortedActions.getUntaggedActions(), fsm);
			}

			context.restoreScope();

			// create IR actor
			String name = astActor.getName();
			return new Actor(name, file, parameters, inputs, outputs,
					stateVars, procedures, actions.getAllActions(),
					initializes.getAllActions(), scheduler);
		} finally {
			// cleanup
			astTransformer.clear();
			mapPorts.clear();
		}
	}

	/**
	 * Transforms the given AST action and adds it to the given action list.
	 * 
	 * @param actionList
	 *            an action list
	 * @param astAction
	 *            an AST action
	 */
	private void transformAction(AstAction astAction, ActionList actionList) {
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

		String name = astTransformer.getQualifiedName(astAction);
		name = dotPattern.matcher(name).replaceAll("_");

		// creates scheduler and body
		Procedure scheduler = new Procedure("isSchedulable_" + name, location,
				IrFactory.eINSTANCE.createTypeBool());
		Procedure body = new Procedure(name, location,
				IrFactory.eINSTANCE.createTypeVoid());

		// fills the patterns and procedures
		fillsInputPattern(astAction, inputPattern);
		fillsOutputPattern(astAction, outputPattern);

		transformActionBody(astAction, body);
		transformActionScheduler(astAction, scheduler, inputPattern);

		// creates IR action and add it to action list
		Action action = new Action(location, tag, inputPattern, outputPattern,
				scheduler, body);
		actionList.add(action);
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
		Context oldContext = astTransformer.newContext(body);

		transformInputPattern(astAction, Read.class);
		astTransformer.transformLocalVariables(astAction.getVariables());
		astTransformer.transformStatements(astAction.getStatements());
		transformOutputPattern(astAction);

		astTransformer.restoreContext(oldContext);
		astTransformer.addReturn(body, null);
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
			transformAction(astAction, actionList);
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
		Context oldContext = astTransformer.newContext(scheduler);
		Context context = astTransformer.getContext();

		LocalVariable result = context.getProcedure().newTempLocalVariable(
				file, IrFactory.eINSTANCE.createTypeBool(), "result");

		List<AstExpression> guards = astAction.getGuards();
		if (inputPattern.isEmpty() && guards.isEmpty()) {
			// the action is always fireable
			Assign assign = new Assign(result, new BoolExpr(true));
			addInstruction(assign);
		} else {
			createActionTest(astAction, inputPattern, result);
		}

		astTransformer.restoreContext(oldContext);
		astTransformer.addReturn(scheduler, new VarExpr(new Use(result)));
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
		List<Expression> expressions = astTransformer
				.transformExpressions(guards);
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
		Context context = astTransformer.getContext();
		List<AstInputPattern> astInputPattern = astAction.getInputs();
		for (AstInputPattern pattern : astInputPattern) {
			Port port = mapPorts.get(pattern.getPort());
			List<AstVariable> tokens = pattern.getTokens();

			// evaluates token consumption
			int totalConsumption = tokens.size();
			int repeat = 1;
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				repeat = new AstExpressionEvaluator(null)
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
				LocalVariable local = astTransformer
						.transformLocalVariable(token);
				context.getProcedure().getLocals()
						.put(file, local.getLocation(), local.getName(), local);
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
		Context context = astTransformer.getContext();
		List<CFGNode> nodes = context.getProcedure().getNodes();

		int first = nodes.size();
		nodes.add(new BlockNode(context.getProcedure()));

		List<AstExpression> guards = astAction.getGuards();
		if (guards.isEmpty()) {
			Assign assign = new Assign(result, new BoolExpr(true));
			addInstruction(assign);
		} else {
			transformInputPattern(astAction, Peek.class);
			astTransformer.transformLocalVariables(astAction.getVariables());
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
	 * Transforms the AST output patterns of the given action as expressions and
	 * possibly statements, assigns tokens and adds writes.
	 * 
	 * @param astAction
	 *            an AST action
	 */
	private void transformOutputPattern(AstAction astAction) {
		List<AstOutputPattern> astOutputPattern = astAction.getOutputs();
		for (AstOutputPattern pattern : astOutputPattern) {
			Port port = mapPorts.get(pattern.getPort());
			List<AstExpression> values = pattern.getValues();

			// evaluates token consumption
			int totalConsumption = values.size();
			int repeat = 1;
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat != null) {
				repeat = new AstExpressionEvaluator(null)
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
			mapPorts.put(astPort, port);
			ports.put(file, location, port.getName(), port);
		}

		return ports;
	}

}
