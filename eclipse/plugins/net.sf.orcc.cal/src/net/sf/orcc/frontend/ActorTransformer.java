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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstSchedule;
import net.sf.orcc.cal.cal.AstScheduleRegExp;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.cal.util.VoidSwitch;
import net.sf.orcc.frontend.schedule.ActionSorter;
import net.sf.orcc.frontend.schedule.FSMBuilder;
import net.sf.orcc.frontend.schedule.RegExpConverter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.OrderedMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

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

	/**
	 * The file in which the actor is defined.
	 */
	private String file;

	/**
	 * A map from AST ports to IR ports.
	 */
	final private Map<AstPort, Port> mapPorts;

	/**
	 * count of un-tagged actions
	 */
	private int untaggedCount;

	/**
	 * Creates a new AST to IR transformation.
	 */
	public ActorTransformer() {
		mapPorts = new HashMap<AstPort, Port>();
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
	private void actionLoadTokens(Var portVariable, List<AstVariable> tokens,
			int repeat) {
		Context context = astTransformer.getContext();
		if (repeat == 1) {
			int i = 0;

			for (AstVariable token : tokens) {
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(IrFactory.eINSTANCE.createExprInt(i));
				Location location = portVariable.getLocation();

				Var irToken = context.getVariable(token);
				InstLoad load = IrFactory.eINSTANCE.createInstLoad(location,
						irToken, IrFactory.eINSTANCE.createUse(portVariable),
						indexes);
				addInstruction(load);

				i++;
			}
		} else {
			Procedure procedure = context.getProcedure();

			// creates loop variable and initializes it
			Var loopVar = procedure.newTempLocalVariable(file,
					IrFactory.eINSTANCE.createTypeInt(32), "num_repeats");
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(loopVar,
					IrFactory.eINSTANCE.createExprInt(0));
			addInstruction(assign);

			NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();

			int i = 0;
			int numTokens = tokens.size();
			Type type = ((TypeList) portVariable.getType()).getType();
			for (AstVariable token : tokens) {
				Location location = portVariable.getLocation();
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(IrFactory.eINSTANCE.createExprBinary(
						IrFactory.eINSTANCE.createExprBinary(
								IrFactory.eINSTANCE.createExprInt(numTokens),
								OpBinary.TIMES,
								IrFactory.eINSTANCE.createExprVar(loopVar),
								IrFactory.eINSTANCE.createTypeInt(32)),
						OpBinary.PLUS, IrFactory.eINSTANCE.createExprInt(i),
						IrFactory.eINSTANCE.createTypeInt(32)));

				Var tmpVar = procedure
						.newTempLocalVariable(file, type, "token");
				InstLoad load = IrFactory.eINSTANCE.createInstLoad(location,
						tmpVar, IrFactory.eINSTANCE.createUse(portVariable),
						indexes);
				block.add(load);

				Var irToken = context.getVariable(token);

				indexes = new ArrayList<Expression>(1);
				indexes.add(IrFactory.eINSTANCE.createExprVar(loopVar));
				InstStore store = IrFactory.eINSTANCE.createInstStore(location,
						irToken, indexes,
						IrFactory.eINSTANCE.createExprVar(tmpVar));
				block.add(store);

				i++;
			}

			// add increment
			assign = IrFactory.eINSTANCE.createInstAssign(loopVar,
					IrFactory.eINSTANCE.createExprBinary(
							IrFactory.eINSTANCE.createExprVar(loopVar),
							OpBinary.PLUS,
							IrFactory.eINSTANCE.createExprInt(1),
							loopVar.getType()));
			block.add(assign);

			// create while node
			Expression condition = IrFactory.eINSTANCE.createExprBinary(
					IrFactory.eINSTANCE.createExprVar(loopVar), OpBinary.LT,
					IrFactory.eINSTANCE.createExprInt(repeat),
					IrFactory.eINSTANCE.createTypeBool());
			List<Node> nodes = new ArrayList<Node>(1);
			nodes.add(block);

			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
			nodeWhile.setCondition(condition);
			nodeWhile.getNodes().addAll(nodes);

			procedure.getNodes().add(nodeWhile);
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
	private void actionStoreTokens(Var portVariable,
			List<AstExpression> values, int repeat) {
		if (repeat == 1) {
			int i = 0;

			for (AstExpression expression : values) {
				Location location = portVariable.getLocation();
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(IrFactory.eINSTANCE.createExprInt(i));

				Expression value = astTransformer
						.transformExpression(expression);
				InstStore store = IrFactory.eINSTANCE.createInstStore(location,
						portVariable, indexes, value);
				addInstruction(store);

				i++;
			}
		} else {
			Context context = astTransformer.getContext();
			Procedure procedure = context.getProcedure();

			// creates loop variable and initializes it
			Var loopVar = procedure.newTempLocalVariable(file,
					IrFactory.eINSTANCE.createTypeInt(32), "num_repeats");
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(loopVar,
					IrFactory.eINSTANCE.createExprInt(0));
			addInstruction(assign);

			NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();

			int i = 0;
			int numTokens = values.size();
			Type type = ((TypeList) portVariable.getType()).getType();
			for (AstExpression value : values) {
				Location location = portVariable.getLocation();
				List<Expression> indexes = new ArrayList<Expression>(1);
				indexes.add(IrFactory.eINSTANCE.createExprVar(loopVar));

				// each expression of an output pattern must be of type list
				// so they are necessarily variables
				Var tmpVar = procedure
						.newTempLocalVariable(file, type, "token");
				Expression expression = astTransformer
						.transformExpression(value);
				Use use = ((ExprVar) expression).getUse();

				use = IrFactory.eINSTANCE.createUse(use.getVariable());
				InstLoad load = IrFactory.eINSTANCE.createInstLoad(tmpVar, use,
						indexes);
				block.add(load);

				indexes = new ArrayList<Expression>(1);
				indexes.add(IrFactory.eINSTANCE.createExprBinary(
						IrFactory.eINSTANCE.createExprBinary(
								IrFactory.eINSTANCE.createExprInt(numTokens),
								OpBinary.TIMES,
								IrFactory.eINSTANCE.createExprVar(loopVar),
								IrFactory.eINSTANCE.createTypeInt(32)),
						OpBinary.PLUS, IrFactory.eINSTANCE.createExprInt(i),
						IrFactory.eINSTANCE.createTypeInt(32)));
				InstStore store = IrFactory.eINSTANCE.createInstStore(location,
						portVariable, indexes,
						IrFactory.eINSTANCE.createExprVar(tmpVar));
				block.add(store);

				i++;
			}

			// add increment
			assign = IrFactory.eINSTANCE.createInstAssign(loopVar,
					IrFactory.eINSTANCE.createExprBinary(
							IrFactory.eINSTANCE.createExprVar(loopVar),
							OpBinary.PLUS,
							IrFactory.eINSTANCE.createExprInt(1),
							loopVar.getType()));
			block.add(assign);

			// create while node
			Expression condition = IrFactory.eINSTANCE.createExprBinary(
					IrFactory.eINSTANCE.createExprVar(loopVar), OpBinary.LT,
					IrFactory.eINSTANCE.createExprInt(repeat),
					IrFactory.eINSTANCE.createTypeBool());
			List<Node> nodes = new ArrayList<Node>(1);
			nodes.add(block);

			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
			nodeWhile.setCondition(condition);
			nodeWhile.getNodes().addAll(nodes);

			procedure.getNodes().add(nodeWhile);
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
		NodeBlock block = context.getProcedure().getLast();
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
			Var result) {
		List<AstExpression> guards = astAction.getGuards();
		if (guards.isEmpty()) {
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(result,
					IrFactory.eINSTANCE.createExprBool(true));
			addInstruction(assign);
		} else {
			transformInputPatternPeek(astAction, inputPattern);
			// local variables are not transformed because they are not
			// supposed to be available for guards
			// astTransformer.transformLocalVariables(astAction.getVariables());
			transformGuards(astAction.getGuards(), result);
		}
	}

	/**
	 * Creates a new empty "initialize" action that is empty and always
	 * schedulable.
	 * 
	 * @return an initialize action
	 */
	private Action createInitialize() {
		Location location = IrFactory.eINSTANCE.createLocation();

		// transform tag
		Tag tag = new Tag();

		Pattern inputPattern = new Pattern(0);
		Pattern outputPattern = new Pattern(0);

		Procedure scheduler = IrFactory.eINSTANCE.createProcedure(
				"isSchedulable_init_actor", location,
				IrFactory.eINSTANCE.createTypeBool());
		Procedure body = IrFactory.eINSTANCE.createProcedure("init_actor",
				location, IrFactory.eINSTANCE.createTypeVoid());

		// add return instructions
		astTransformer.addReturn(scheduler,
				IrFactory.eINSTANCE.createExprBool(true));
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
	private Var createPortVariable(Port port, int numTokens) {
		// create the variable to hold the tokens
		Location location = astTransformer.getContext().getProcedure()
				.getLocation();
		return IrFactory.eINSTANCE.createVar(location,
				IrFactory.eINSTANCE.createTypeList(numTokens, port.getType()),
				port.getName(), true, 0);
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
			inputPattern.setNumTokens(port, totalConsumption);
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
			outputPattern.setNumTokens(port, totalConsumption);
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
			OrderedMap<String, Var> parameters = new OrderedMap<String, Var>();
			astTransformer.setGlobalsMap(parameters);
			for (AstVariable astVariable : astActor.getParameters()) {
				astTransformer.transformGlobalVariable(astVariable);
			}

			// map for state variables
			// state variables are transformed on demand when referenced by
			// expressions or statements
			// this allows imported variables to be transparently handled
			OrderedMap<String, Var> stateVars = new OrderedMap<String, Var>();
			astTransformer.setGlobalsMap(stateVars);

			// transform ports
			OrderedMap<String, Port> inputs = transformPorts(astActor
					.getInputs());
			OrderedMap<String, Port> outputs = transformPorts(astActor
					.getOutputs());

			// creates a new scope before translating things with local
			// variables
			context.newScope();

			// retrieves the procedure map
			// like state variables, this is populated on demand
			OrderedMap<String, Procedure> procedures = astTransformer
					.getProcedures();

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
					NodeBlock block = action.getBody().getFirst();
					List<Expression> params = new ArrayList<Expression>(0);
					block.add(0, IrFactory.eINSTANCE.createInstCall(location,
							null, initialize, params));
				}
			}

			// sort actions by priority
			ActionSorter sorter = new ActionSorter(actions);
			ActionList sortedActions = sorter.applyPriority(astActor
					.getPriorities());

			// transform FSM
			AstSchedule schedule = astActor.getSchedule();
			AstScheduleRegExp scheduleRegExp = astActor.getScheduleRegExp();
			ActionScheduler scheduler;
			if (schedule == null && scheduleRegExp == null) {
				scheduler = new ActionScheduler(sortedActions.getAllActions(),
						null);
			} else {
				FSM fsm = null;
				if (schedule != null) {
					FSMBuilder builder = new FSMBuilder(astActor.getSchedule());
					fsm = builder.buildFSM(sortedActions);
				} else {
					RegExpConverter converter = new RegExpConverter(
							scheduleRegExp);
					fsm = converter.convert(sortedActions);
				}
				scheduler = new ActionScheduler(
						sortedActions.getUntaggedActions(), fsm);
			}

			context.restoreScope();

			// create IR actor
			AstEntity entity = (AstEntity) astActor.eContainer();

			return new Actor(
					net.sf.orcc.cal.util.Util.getQualifiedName(entity), file,
					parameters, inputs, outputs, entity.isNative(), stateVars,
					procedures, actions.getAllActions(),
					initializes.getAllActions(), scheduler);
		} finally {
			// cleanup
			astTransformer.clear();
			mapPorts.clear();

			untaggedCount = 0;
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
		String name;
		if (astTag == null) {
			tag = new Tag();
			name = "untagged_" + untaggedCount++;
		} else {
			tag = new Tag(astAction.getTag().getIdentifiers());
			name = OrccUtil.toString(tag, "_");
		}

		Pattern inputPattern = new Pattern();
		Pattern outputPattern = new Pattern();

		// creates scheduler and body
		Procedure scheduler = IrFactory.eINSTANCE.createProcedure(
				"isSchedulable_" + name, location,
				IrFactory.eINSTANCE.createTypeBool());
		Procedure body = IrFactory.eINSTANCE.createProcedure(name, location,
				IrFactory.eINSTANCE.createTypeVoid());

		// fills the patterns and procedures
		fillsInputPattern(astAction, inputPattern);
		fillsOutputPattern(astAction, outputPattern);

		transformActionBody(astAction, body, inputPattern, outputPattern);
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
	private void transformActionBody(AstAction astAction, Procedure body,
			Pattern inputPattern, Pattern outputPattern) {
		Context oldContext = astTransformer.newContext(body);

		for (AstInputPattern pattern : astAction.getInputs()) {
			transformInputPattern(pattern, inputPattern, false);
		}

		astTransformer.transformLocalVariables(astAction.getVariables());
		astTransformer.transformStatements(astAction.getStatements());
		transformOutputPattern(astAction, outputPattern);

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

		Var result = context.getProcedure().newTempLocalVariable(file,
				IrFactory.eINSTANCE.createTypeBool(), "result");

		List<AstExpression> guards = astAction.getGuards();
		if (inputPattern.isEmpty() && guards.isEmpty()) {
			// the action is always fireable
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(result,
					IrFactory.eINSTANCE.createExprBool(true));
			addInstruction(assign);
		} else {
			createActionTest(astAction, inputPattern, result);
		}

		astTransformer.restoreContext(oldContext);
		astTransformer.addReturn(scheduler,
				IrFactory.eINSTANCE.createExprVar(result));
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
	private void transformGuards(List<AstExpression> guards, Var result) {
		List<Expression> expressions = astTransformer
				.transformExpressions(guards);
		Iterator<Expression> it = expressions.iterator();
		Expression value = it.next();
		while (it.hasNext()) {
			value = IrFactory.eINSTANCE.createExprBinary(value,
					OpBinary.LOGIC_AND, EcoreUtil.copy(it.next()),
					IrFactory.eINSTANCE.createTypeBool());
		}

		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(result, value);
		addInstruction(assign);
	}

	/**
	 * Transforms the AST input pattern of the given action as a local variable,
	 * adds peeks/reads and assigns tokens.
	 * 
	 * @param pattern
	 *            an input pattern
	 */
	private void transformInputPattern(AstInputPattern pattern,
			Pattern irInputPattern, boolean peek) {
		Context context = astTransformer.getContext();
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
		Var variable = createPortVariable(port, totalConsumption);
		if (peek) {
			irInputPattern.setPeeked(port, variable);
		} else {
			irInputPattern.setVariable(port, variable);
		}

		// declare tokens
		for (AstVariable token : tokens) {
			Var local = astTransformer.transformLocalVariable(token);
			context.getProcedure().getLocals()
					.put(file, local.getLocation(), local.getName(), local);
		}

		// loads tokens
		actionLoadTokens(variable, tokens, repeat);
	}

	/**
	 * Transforms the input patterns of the given AST action when necessary by
	 * generating Peek instructions. An input pattern needs to be transformed to
	 * Peeks when guards reference tokens from the pattern.
	 * 
	 * @param astAction
	 *            an AST action
	 */
	private void transformInputPatternPeek(final AstAction astAction,
			Pattern inputPattern) {
		final Set<AstInputPattern> patterns = new HashSet<AstInputPattern>();
		VoidSwitch peekVariables = new VoidSwitch() {

			@Override
			public Void caseAstVariableReference(AstVariableReference reference) {
				EObject obj = reference.getVariable().eContainer();
				if (obj instanceof AstInputPattern) {
					patterns.add((AstInputPattern) obj);
				}

				return null;
			}

		};

		// fills the patterns set by visiting guards
		for (AstExpression guard : astAction.getGuards()) {
			peekVariables.doSwitch(guard);
		}

		// add peeks for each pattern of the patterns set
		for (AstInputPattern pattern : patterns) {
			transformInputPattern(pattern, inputPattern, true);
		}
	}

	/**
	 * Transforms the AST output patterns of the given action as expressions and
	 * possibly statements, assigns tokens and adds writes.
	 * 
	 * @param astAction
	 *            an AST action
	 */
	private void transformOutputPattern(AstAction astAction,
			Pattern irOutputPattern) {
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
			Var variable = createPortVariable(port, totalConsumption);
			irOutputPattern.setVariable(port, variable);

			// store tokens
			actionStoreTokens(variable, values, repeat);
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
