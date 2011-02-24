/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.ir.serialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.InstructionInterpreter;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeInterpreter;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a cloner that clones all attributes of an actor in IR form
 * to a new actor actor in IR form.
 * 
 * @author Jerome Gorin
 * 
 */
public class IRCloner {

	/**
	 * This class defines an expression cloner that dupplicate an expression to
	 * a new Expression.
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private static class ExpressionCloner implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			BinaryOp op = BinaryOp.getOperator(expr.getOp().name());
			Expression e1 = cloneExpression(expr.getE1());
			Expression e2 = cloneExpression(expr.getE2());
			Type type = cloneType(expr.getType());

			return new BinaryExpr(e1, op, e2, type);
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			return new BoolExpr(expr.getValue());
		}

		@Override
		public Object interpret(FloatExpr expr, Object... args) {
			return new FloatExpr(expr.getValue());
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			return new IntExpr(expr.getValue());
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			List<Expression> expressions = new ArrayList<Expression>();

			for (Expression expression : expr.getValue()) {
				expressions.add(cloneExpression(expression));
			}
			return new ListExpr(expressions);
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			return new StringExpr(expr.getValue());
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			UnaryOp op = UnaryOp.getOperator(expr.getOp().name());
			Expression expression = cloneExpression(expr.getExpr());
			Type type = cloneType(expr.getType());

			return new UnaryExpr(op, expression, type);
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			Use use = new Use(getVar(expr.getVar().getVariable()));

			return new VarExpr(use);
		}

	}

	/**
	 * This class defines an instruction clones that clone an instruction into a
	 * new instruction.
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private static class InstructionCloner implements InstructionInterpreter {

		@Override
		public Object interpret(Assign assign, Object... args) {
			Location location = cloneLocation(assign.getLocation());
			LocalVariable target = getLocalVar(assign.getTarget());
			Expression value = cloneExpression(assign.getValue());

			return new Assign(location, target, value);
		}

		@Override
		public Object interpret(Call call, Object... args) {
			Location location = cloneLocation(call.getLocation());
			Procedure procedure = getProcedure(call.getProcedure());
			LocalVariable target = getLocalVar(call.getTarget());
			List<Expression> parameters = cloneExpressions(call.getParameters());

			return new Call(location, target, procedure, parameters);
		}

		@Override
		public Object interpret(Load load, Object... args) {
			Location location = cloneLocation(load.getLocation());
			LocalVariable target = getLocalVar(load.getTarget());
			Use source = new Use(getVar(load.getSource().getVariable()));
			List<Expression> indexes = cloneExpressions(load.getIndexes());

			return new Load(location, target, source, indexes);
		}

		@Override
		public Object interpret(Peek peek, Object... args) {
			Location location = cloneLocation(peek.getLocation());
			Variable target = getVar(peek.getTarget());
			Port port = getPort(peek.getPort());

			return new Peek(location, port, peek.getNumTokens(), target);
		}

		@Override
		public Object interpret(PhiAssignment phi, Object... args) {
			Location location = cloneLocation(phi.getLocation());
			LocalVariable target = getLocalVar(phi.getTarget());
			List<Expression> values = cloneExpressions(phi.getValues());

			return new PhiAssignment(location, target, values);
		}

		@Override
		public Object interpret(Read read, Object... args) {
			Location location = cloneLocation(read.getLocation());
			Variable target = getVar(read.getTarget());
			Port port = getPort(read.getPort());

			return new Read(location, port, read.getNumTokens(), target);
		}

		@Override
		public Object interpret(Return returnInst, Object... args) {
			Expression value;
			Location location = cloneLocation(returnInst.getLocation());

			if (returnInst.getValue() == null) {
				value = null;
			} else {
				value = cloneExpression(returnInst.getValue());
			}

			return new Return(location, value);
		}

		@Override
		public Object interpret(SpecificInstruction specific, Object... args) {
			throw new OrccRuntimeException(
					"IR writer cannot write specific instructions");
		}

		@Override
		public Object interpret(Store store, Object... args) {
			Location location = cloneLocation(store.getLocation());
			Variable target = getVar(store.getTarget());
			List<Expression> indexes = cloneExpressions(store.getIndexes());
			Expression value = cloneExpression(store.getValue());

			return new Store(location, target, indexes, value);
		}

		@Override
		public Object interpret(Write write, Object... args) {
			Location location = cloneLocation(write.getLocation());
			Variable target = getVar(write.getTarget());
			Port port = getPort(write.getPort());

			return new Write(location, port, write.getNumTokens(), target);
		}
	}

	/**
	 * This class defines a node cloner that clones a CFG node into a new CFG
	 * node.
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private static class NodeCloner implements NodeInterpreter {

		@Override
		public Object interpret(BlockNode node, Object... args) {
			Procedure procedure = getProcedure(node.getProcedure());
			Location location = cloneLocation(node.getLocation());
			List<Instruction> instructions = cloneIntructions(node
					.getInstructions());

			BlockNode clonedBlockNode = new BlockNode(location, procedure);
			clonedBlockNode.addAll(instructions);

			return clonedBlockNode;
		}

		@Override
		public Object interpret(IfNode node, Object... args) {
			Location location = cloneLocation(node.getLocation());
			Procedure procedure = getProcedure(node.getProcedure());
			Expression value = cloneExpression(node.getValue());
			List<CFGNode> thenNodes = cloneNodes(node.getThenNodes());
			List<CFGNode> elseNodes = cloneNodes(node.getElseNodes());
			BlockNode joinNode = (BlockNode) cloneNode(node.getJoinNode());

			return new IfNode(location, procedure, value, thenNodes, elseNodes,
					joinNode);
		}

		@Override
		public Object interpret(WhileNode node, Object... args) {
			Location location = cloneLocation(node.getLocation());
			Procedure procedure = getProcedure(node.getProcedure());
			Expression value = cloneExpression(node.getValue());
			List<CFGNode> nodes = cloneNodes(node.getNodes());
			BlockNode joinNode = (BlockNode) cloneNode(node.getJoinNode());

			return new WhileNode(location, procedure, value, nodes, joinNode);
		}
	}

	/**
	 * This class defines a type cloner that clone a type.
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private static class TypeCloner implements TypeInterpreter {

		@Override
		public Object interpret(TypeBool type) {
			return IrFactory.eINSTANCE.createTypeBool();
		}

		@Override
		public Object interpret(TypeFloat type) {
			return IrFactory.eINSTANCE.createTypeFloat();
		}

		@Override
		public Object interpret(TypeInt type) {
			return IrFactory.eINSTANCE.createTypeInt(type.getSize());
		}

		@Override
		public Object interpret(TypeList type) {
			Expression sizeExpr = cloneExpression(type.getSizeExpr());
			Type clonetype = cloneType(type.getType());
			return IrFactory.eINSTANCE.createTypeList(sizeExpr, clonetype);
		}

		@Override
		public Object interpret(TypeString type) {
			return IrFactory.eINSTANCE.createTypeString();
		}

		@Override
		public Object interpret(TypeUint type) {
			return IrFactory.eINSTANCE.createTypeUint(type.getSize());
		}

		@Override
		public Object interpret(TypeVoid type) {
			return IrFactory.eINSTANCE.createTypeVoid();
		}

	}

	private static OrderedMap<String, LocalVariable> localVars;

	private static OrderedMap<String, GlobalVariable> parameters;

	private static OrderedMap<String, Port> ports;

	private static OrderedMap<String, Procedure> procs;

	private static OrderedMap<String, GlobalVariable> stateVars;

	/**
	 * Clone the given expression into a new Expression.
	 * 
	 * @param expression
	 *            the expression to clone
	 * @return a new Expression
	 */
	private static Expression cloneExpression(Expression expression) {
		return (Expression) expression.accept(new ExpressionCloner());
	}

	/**
	 * Clone the given list of expressions whose each member is set to the
	 * result of {@link #cloneExpression(Expression)}.
	 * 
	 * @param expressions
	 *            a list of expressions to clone
	 * @return the resulting list of cloned expressions
	 */
	private static List<Expression> cloneExpressions(
			List<Expression> expressions) {
		List<Expression> cloneExpressions = new ArrayList<Expression>();
		for (Expression expression : expressions) {
			cloneExpressions.add(cloneExpression(expression));
		}

		return cloneExpressions;
	}

	private static Instruction cloneInstruction(Instruction instruction) {
		return (Instruction) instruction.accept(new InstructionCloner());
	}

	private static List<Instruction> cloneIntructions(
			List<Instruction> instructions) {
		List<Instruction> cloneInstructions = new ArrayList<Instruction>();
		for (Instruction instruction : instructions) {
			cloneInstructions.add(cloneInstruction(instruction));
		}
		return cloneInstructions;
	}
	private static Location cloneLocation(Location location) {
		return new Location(location.getStartLine(), location.getStartColumn(),
				location.getEndColumn());
	}
	private static CFGNode cloneNode(CFGNode node) {
		return (CFGNode) node.accept(new NodeCloner());
	}
	/**
	 * Clone the given nodes.
	 * 
	 * @param nodes
	 *            a list of nodes to clone
	 * @return the cloned nodes
	 */
	private static List<CFGNode> cloneNodes(List<CFGNode> nodes) {
		List<CFGNode> cloneNodes = new ArrayList<CFGNode>();
		for (CFGNode node : nodes) {
			cloneNodes.add(cloneNode(node));
		}

		return cloneNodes;
	}
	/**
	 * Clone the given type.
	 * 
	 * @param type
	 *            the type to clone
	 * @return a cloned type
	 */
	private static Type cloneType(Type type) {
		return (Type) type.accept(new TypeCloner());
	}
	/**
	 * Returns the cloned local variable that corresponds to the local variable.
	 * 
	 * @param variable
	 *            a local variable
	 * @return the corresponding cloned local variable
	 */
	private static LocalVariable getLocalVar(LocalVariable variable) {
		return localVars.get(variable.getName());
	}
	/**
	 * Returns the cloned port that corresponds to the port.
	 * 
	 * @param port
	 *            a port
	 * @return the corresponding cloned port
	 */
	private static Port getPort(Port port) {
		return ports.get(port.getName());
	}
	/**
	 * Returns the cloned procedure that corresponds to the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return the corresponding cloned procedure
	 */
	private static Procedure getProcedure(Procedure procedure) {
		return procs.get(procedure.getName());
	}
	/**
	 * Returns the cloned variable that corresponds to the variable.
	 * 
	 * @param variable
	 *            a variable
	 * @return the corresponding cloned variable
	 */
	private static Variable getVar(Variable variable) {
		if (!variable.isGlobal()) {
			return getLocalVar((LocalVariable) variable);
		}

		if (stateVars.contains(variable.getName())) {
			return stateVars.get(variable.getName());
		}

		if (parameters.contains(variable.getName())) {
			return parameters.get(variable.getName());
		}

		return null;
	}
	private Map<String, Action> actions;

	private Actor actor;

	private OrderedMap<String, Port> inputs;

	private OrderedMap<String, Port> outputs;

	private List<Action> untaggedActions;

	/**
	 * Creates an actor writer on the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public IRCloner(Actor actor) {
		this.actor = actor;
	}

	@Override
	public Actor clone() {
		parameters = cloneGlobalVariables(actor.getParameters());
		stateVars = cloneGlobalVariables(actor.getStateVars());
		inputs = clonePorts(actor.getInputs());
		outputs = clonePorts(actor.getOutputs());

		procs = cloneProcedures(actor.getProcs());
		List<Action> actions = cloneActions(actor.getActions());

		List<Action> initializes = cloneInitializes(actor.getInitializes());

		ActionScheduler actionScheduler = cloneActionScheduler(actor
				.getActionScheduler());

		return new Actor(actor.getName(), actor.getFile(), parameters, inputs,
				outputs, actor.isNative(), stateVars, procs, actions,
				initializes, actionScheduler);
	}

	/**
	 * Returns a cloned action from the given action.
	 * 
	 * @param action
	 *            the action to clone
	 * @return a clone action
	 */
	private Action cloneAction(Action action) {

		Location location = cloneLocation(action.getLocation());
		Tag tag = cloneActionTag(action.getTag());
		Pattern inputPattern = cloneActionPattern(action.getInputPattern(),
				inputs);
		Pattern outputPattern = cloneActionPattern(action.getOutputPattern(),
				outputs);
		Procedure scheduler = cloneProcedure(action.getScheduler());
		Procedure body = cloneProcedure(action.getBody());
		return new Action(location, tag, inputPattern, outputPattern,
				scheduler, body);
	}

	/**
	 * Returns a clone of the given pattern.
	 * 
	 * @param pattern
	 *            the pattern to clone;
	 * 
	 * @param ports
	 *            a list of cloned port;
	 * @return the cloned pattern
	 */
	private Pattern cloneActionPattern(Pattern pattern,
			OrderedMap<String, Port> ports) {
		Pattern clonePattern = new Pattern();

		for (Entry<Port, Integer> entry : pattern.entrySet()) {
			Port port = ports.get(entry.getKey().getName());
			pattern.put(port, entry.getValue());
		}

		return clonePattern;
	}

	/**
	 * Returns an array filled with cloned actions.
	 * 
	 * @param actions
	 *            a list of actions to clone
	 * @return the cloned action list
	 */
	private List<Action> cloneActions(List<Action> actions) {
		List<Action> cloneActions = new ArrayList<Action>();

		for (Action action : actions) {
			Action cloneAction = cloneAction(action);

			putAction(cloneAction.getTag(), cloneAction);

			cloneActions.add(cloneAction);
		}

		return cloneActions;
	}

	/**
	 * Returns an cloned action scheduler.
	 * 
	 * @param scheduler
	 *            the action scheduler of the actor to clone.
	 * @return the action scheduler cloned.
	 */
	private ActionScheduler cloneActionScheduler(ActionScheduler scheduler) {
		List<Action> actions = new ArrayList<Action>();
		FSM clonedFSM = null;

		for (Action action : scheduler.getActions()) {
			Action clonedAction = getAction(action.getTag());
			actions.add(clonedAction);
		}

		if (scheduler.hasFsm()) {
			clonedFSM = cloneFSM(scheduler.getFsm());
		} else {
			clonedFSM = null;
		}

		return new ActionScheduler(actions, clonedFSM);
	}

	/**
	 * Returns an array whose entries are the cloned tag identifiers. If the tag
	 * is empty, the array returned is empty.
	 * 
	 * @param tag
	 *            the action tag to clone
	 * @return the tag cloned
	 */
	private Tag cloneActionTag(Tag tag) {
		return new Tag(tag.getIdentifiers());
	}

	/**
	 * Clones a Finite State Machine.
	 * 
	 * @param fsm
	 *            an FSM to be cloned
	 * @return the cloned FSM
	 */
	private FSM cloneFSM(FSM fsm) {
		FSM clonedFsm = new FSM();

		for (String state : fsm.getStates()) {
			clonedFsm.addState(state);
		}

		String initialState = fsm.getInitialState().getName();
		clonedFsm.setInitialState(initialState);

		for (Transition transition : fsm.getTransitions()) {
			State source = transition.getSourceState();

			for (NextStateInfo nextState : transition.getNextStateInfo()) {
				Action action = getAction(nextState.getAction().getTag());
				State target = nextState.getTargetState();
				clonedFsm.addTransition(source.getName(), action,
						target.getName());
			}
		}

		return clonedFsm;
	}

	/**
	 * Clone a global variable.
	 * 
	 * @param variable
	 *            the variable to clone
	 * @return the cloned variables
	 */
	private GlobalVariable cloneGlobalVariable(GlobalVariable variable) {
		Location location = cloneLocation(variable.getLocation());
		Type type = cloneType(variable.getType());

		Expression constant;

		if (variable.getInitialValue() == null) {
			constant = null;
		} else {
			constant = cloneExpression(variable.getInitialValue());
		}

		return new GlobalVariable(location, type, variable.getName(),
				variable.isAssignable(), constant);
	}

	/**
	 * Clone the given map of global variables.
	 * 
	 * @param variables
	 *            an OrderedMap of variable to clone
	 * @return the cloned variables
	 */
	private OrderedMap<String, GlobalVariable> cloneGlobalVariables(
			OrderedMap<String, GlobalVariable> variables) {
		OrderedMap<String, GlobalVariable> cloneMap = new OrderedMap<String, GlobalVariable>();

		for (GlobalVariable variable : variables) {
			GlobalVariable duplicateVar = cloneGlobalVariable(variable);
			cloneMap.put(duplicateVar.getName(), duplicateVar);
		}

		return cloneMap;
	}

	/**
	 * Returns an array filled with cloned initialize actions.
	 * 
	 * @param actions
	 *            a list of initialize actions to clone
	 * @return the cloned initialize action list
	 */
	private List<Action> cloneInitializes(List<Action> actions) {
		List<Action> cloneActions = new ArrayList<Action>();

		for (Action action : actions) {
			cloneActions.add(cloneAction(action));
		}

		return cloneActions;
	}

	/**
	 * Clone a local variable.
	 * 
	 * @param variable
	 *            the variable to clone
	 * @return the cloned variables
	 */
	private LocalVariable cloneLocalVariable(LocalVariable variable) {
		Location location = cloneLocation(variable.getLocation());
		Type type = cloneType(variable.getType());

		return new LocalVariable(variable.isAssignable(), variable.getIndex(),
				location, variable.getName(), type);
	}

	/**
	 * Clones the given ordered map of local variables.
	 * 
	 * @param variables
	 *            an ordered map of variables
	 * @return the cloned ordered map
	 */
	private OrderedMap<String, LocalVariable> cloneLocalVariables(
			OrderedMap<String, LocalVariable> variables) {
		OrderedMap<String, LocalVariable> cloneMap = new OrderedMap<String, LocalVariable>();

		for (LocalVariable variable : variables) {
			LocalVariable cloneVar = cloneLocalVariable(variable);
			cloneMap.put(cloneVar.getName(), cloneVar);
		}

		return cloneMap;
	}

	/**
	 * Clone the given port.
	 * 
	 * @param port
	 *            a port
	 * @return the cloned port
	 */
	private Port clonePort(Port port) {
		Location location = cloneLocation(port.getLocation());
		Type type = cloneType(port.getType());

		return new Port(location, type, port.getName());
	}

	/**
	 * Clone an ordered map of ports.
	 * 
	 * @param port
	 *            the ordered map of ports to clone
	 * @return the cloned ordered map
	 */
	private OrderedMap<String, Port> clonePorts(OrderedMap<String, Port> ports) {
		OrderedMap<String, Port> cloneMap = new OrderedMap<String, Port>();

		for (Port port : ports) {
			Port clonePort = clonePort(port);
			cloneMap.put(clonePort.getName(), clonePort);
			ports.put(clonePort.getName(), clonePort);
		}

		return cloneMap;
	}

	/**
	 * Returns a cloned procedure from the given procedure.
	 * 
	 * @param procedure
	 *            the procedure to clone
	 * @return a cloned procedure
	 */
	private Procedure cloneProcedure(Procedure procedure) {
		Type returnType = cloneType(procedure.getReturnType());

		OrderedMap<String, LocalVariable> parameters = cloneLocalVariables(procedure
				.getParameters());
		OrderedMap<String, LocalVariable> locals = cloneLocalVariables(procedure
				.getLocals());

		// Store all local variable for a later link
		localVars = new OrderedMap<String, LocalVariable>();
		localVars.putAll(parameters);
		localVars.putAll(locals);

		List<CFGNode> nodes = cloneNodes(procedure.getNodes());

		return new Procedure(procedure.getName(), procedure.isNative(),
				procedure.getLocation(), returnType, parameters, locals, nodes);
	}

	/**
	 * Clones the given procedures.
	 * 
	 * @param procedures
	 *            an ordered map of procedures
	 * @return the corresponding clones
	 */
	private OrderedMap<String, Procedure> cloneProcedures(
			OrderedMap<String, Procedure> procedures) {
		OrderedMap<String, Procedure> cloneProcedures = new OrderedMap<String, Procedure>();
		for (Procedure procedure : procedures) {
			Procedure cloneProcedure = cloneProcedure(procedure);
			cloneProcedures.put(cloneProcedure.getName(), procedure);
		}

		return cloneProcedures;
	}

	/**
	 * Returns the action associated with the tag.
	 * 
	 * @param tag
	 *            a tag of an action
	 * @return the action (or initialize) associated with the tag
	 */
	private Action getAction(Tag tag) {
		if (tag.isEmpty()) {
			// removes the first untagged action found
			return untaggedActions.remove(0);
		} else {
			return actions.get(tag.toString());
		}
	}

	/**
	 * Stores an action as untagged or not.
	 * 
	 * @param tag
	 *            a tag of an action
	 * @param action
	 *            an action
	 */
	private void putAction(Tag tag, Action action) {
		if (tag.isEmpty()) {
			untaggedActions.add(action);
		} else {
			actions.put(tag.toString(), action);
		}
	}

}
