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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Node;
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
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
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
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.impl.NodeInterpreter;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.InstructionInterpreter;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.DPNMoC;
import net.sf.orcc.moc.KPNMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.MoCInterpreter;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;
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
	 * This class defines an expression cloner that duplicates an expression to
	 * a new Expression.
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private static class ExpressionCloner implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			BinaryOp op = BinaryOp.getOperator(expr.getOp().getText());
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
			UnaryOp op = UnaryOp.getOperator(expr.getOp().getText());
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
	 * This class defines an instruction cloner that clones an instruction into
	 * a new instruction.
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
			LocalVariable target = null;

			if (call.getTarget() != null) {
				target = getLocalVar(call.getTarget());
			}

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
		public Object interpret(PhiAssignment phi, Object... args) {
			Location location = cloneLocation(phi.getLocation());
			LocalVariable target = getLocalVar(phi.getTarget());
			List<Expression> values = cloneExpressions(phi.getValues());

			return new PhiAssignment(location, target, values);
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
					"IR cloner cannot clone specific instructions");
		}

		@Override
		public Object interpret(Store store, Object... args) {
			Location location = cloneLocation(store.getLocation());
			Variable target = getVar(store.getTarget());
			List<Expression> indexes = cloneExpressions(store.getIndexes());
			Expression value = cloneExpression(store.getValue());

			return new Store(location, target, indexes, value);
		}

	}

	/**
	 * This class defines a MoC cloner that clones a MoC into a new MoC.
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private static class MoCCloner implements MoCInterpreter {

		@Override
		public Object interpret(CSDFMoC moc, Object... args) {
			CSDFMoC csdfMoC = new CSDFMoC();

			for (Action action : moc.getActions()) {
				csdfMoC.addAction(getAction(action.getTag()));
			}

			csdfMoC.setNumberOfPhases(moc.getNumberOfPhases());

			// Clone pattern consumption/production
			csdfMoC.setTokenConsumptions(clone);
			csdfMoC.setTokenProductions(clone);

			return csdfMoC;
		}

		@Override
		public Object interpret(DPNMoC moc, Object... args) {
			return new DPNMoC();
		}

		@Override
		public Object interpret(KPNMoC moc, Object... args) {
			return new KPNMoC();
		}

		@Override
		public Object interpret(QSDFMoC moc, Object... args) {
			QSDFMoC qsdfMoC = new QSDFMoC();

			for (Action action : moc.getActions()) {
				SDFMoC sdfMoC = (SDFMoC) cloneMoC(moc.getStaticClass(action));
				qsdfMoC.addConfiguration(getAction(action.getTag()), sdfMoC);
			}

			return qsdfMoC;
		}

		@Override
		public Object interpret(SDFMoC moc, Object... args) {
			SDFMoC sdfMoC = new SDFMoC();

			for (Action action : moc.getActions()) {
				sdfMoC.addAction(getAction(action.getTag()));
			}

			// Clone pattern consumption/production
			sdfMoC.setTokenConsumptions(clone);
			sdfMoC.setTokenProductions(clone);

			return sdfMoC;
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
		public Object interpret(NodeBlock node, Object... args) {
			Location location = cloneLocation(node.getLocation());
			List<Instruction> instructions = cloneIntructions(node
					.getInstructions());

			NodeBlock clonedBlockNode = IrFactoryImpl.eINSTANCE
					.createNodeBlock();
			clonedBlockNode.setLocation(location);
			clonedBlockNode.getInstructions().addAll(instructions);

			return clonedBlockNode;
		}

		@Override
		public Object interpret(NodeIf node, Object... args) {
			NodeIf nodeIf = IrFactoryImpl.eINSTANCE.createNodeIf();

			Location location = cloneLocation(node.getLocation());
			nodeIf.setLocation(location);
			Expression value = cloneExpression(node.getValue());
			nodeIf.setValue(value);
			List<Node> thenNodes = cloneNodes(node.getThenNodes());
			List<Node> elseNodes = cloneNodes(node.getElseNodes());
			NodeBlock joinNode = (NodeBlock) cloneNode(node.getJoinNode());

			node.getThenNodes().addAll(thenNodes);
			node.getElseNodes().addAll(elseNodes);
			node.setJoinNode(joinNode);

			return nodeIf;
		}

		@Override
		public Object interpret(NodeWhile node, Object... args) {
			Location location = cloneLocation(node.getLocation());
			Expression value = cloneExpression(node.getValue());
			List<Node> nodes = cloneNodes(node.getNodes());
			NodeBlock joinNode = (NodeBlock) cloneNode(node.getJoinNode());

			NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
			nodeWhile.setLocation(location);
			nodeWhile.setValue(value);
			nodeWhile.getNodes().addAll(nodes);
			nodeWhile.setJoinNode(joinNode);

			return nodeWhile;
		}
	}

	/**
	 * This class defines a type cloner that clones a type.
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

	private static OrderedMap<String, Action> actions;

	private static Actor clone;

	private static OrderedMap<String, LocalVariable> localVars;

	private static OrderedMap<String, Variable> patternVars;

	private static OrderedMap<String, Variable> peekedVars;

	private static OrderedMap<String, GlobalVariable> parameters;

	private static OrderedMap<String, Port> ports;

	private static OrderedMap<String, Procedure> procs;

	private static OrderedMap<String, GlobalVariable> stateVars;

	private static List<Action> untaggedActions;

	/**
	 * Clones the given expression into a new Expression.
	 * 
	 * @param expression
	 *            the expression to clone
	 * @return a new Expression
	 */
	private static Expression cloneExpression(Expression expression) {
		return (Expression) expression.accept(new ExpressionCloner());
	}

	/**
	 * Clones the given list of expressions whose each member is set to the
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
		if (location == null) {
			return location;
		}

		return new Location(location.getStartLine(), location.getStartColumn(),
				location.getEndColumn());
	}

	private static MoC cloneMoC(MoC moc) {
		return (MoC) moc.accept(new MoCCloner());
	}

	private static Node cloneNode(Node node) {
		return (Node) node.accept(new NodeCloner());
	}

	/**
	 * Clones the given nodes.
	 * 
	 * @param nodes
	 *            a list of nodes to clone
	 * @return the cloned nodes
	 */
	private static List<Node> cloneNodes(List<Node> nodes) {
		List<Node> cloneNodes = new ArrayList<Node>();
		for (Node node : nodes) {
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
	 * Returns the action associated with the tag.
	 * 
	 * @param tag
	 *            a tag of an action
	 * @return the action (or initialize) associated with the tag
	 */
	private static Action getAction(Tag tag) {
		if (tag.isEmpty()) {
			// Get the first untagged action found
			Action action = untaggedActions.remove(0);

			// Set it to the end of the list
			untaggedActions.add(action);

			return action;
		} else {
			return actions.get(tag.toString());
		}
	}

	/**
	 * Returns the cloned local variable that corresponds to the local variable.
	 * 
	 * @param variable
	 *            a local variable
	 * @return the corresponding cloned local variable
	 */
	private static LocalVariable getLocalVar(LocalVariable variable) {
		if (localVars.contains(variable.getName())) {
			return localVars.get(variable.getName());
		}

		// The local variable corresponds to a pattern
		if (patternVars.contains(variable.getName())) {
			return (LocalVariable) patternVars.get(variable.getName());
		}

		// The local variable corresponds to a peeked variable
		return (LocalVariable) peekedVars.get(variable.getName());
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

	/**
	 * Stores an action as untagged or not.
	 * 
	 * @param tag
	 *            a tag of an action
	 * @param action
	 *            an action
	 */
	private static void putAction(Tag tag, Action action) {
		if (tag.isEmpty()) {
			untaggedActions.add(action);
		} else {
			actions.put(tag.toString(), action);
		}
	}

	private OrderedMap<String, Port> inputs;

	private OrderedMap<String, Port> outputs;

	/**
	 * Creates a new full actor cloner.
	 * 
	 */
	public IRCloner() {
		untaggedActions = new ArrayList<Action>();
		actions = new OrderedMap<String, Action>();
		ports = new OrderedMap<String, Port>();
	}

	/**
	 * Creates a new partial actor that clones local elements of an actor.
	 * 
	 * @param actor
	 *            the source actor
	 */
	public IRCloner(Actor actor) {
		untaggedActions = new ArrayList<Action>();
		actions = new OrderedMap<String, Action>();
		ports = new OrderedMap<String, Port>();

		// Get elements of the actor
		parameters = actor.getParameters();
		stateVars = actor.getStateVars();
		inputs = actor.getInputs();
		outputs = actor.getOutputs();
		procs = actor.getProcs();
	}

	public Actor clone(Actor actor) {

		parameters = cloneGlobalVariables(actor.getParameters());
		stateVars = cloneGlobalVariables(actor.getStateVars());
		inputs = clonePorts(actor.getInputs());
		outputs = clonePorts(actor.getOutputs());

		procs = cloneProcedures(actor.getProcs());
		List<Action> actions = cloneActions(actor.getActions());

		List<Action> initializes = cloneInitializes(actor.getInitializes());

		ActionScheduler actionScheduler = cloneActionScheduler(actor
				.getActionScheduler());

		clone = new Actor(actor.getName(), actor.getFile(), parameters, inputs,
				outputs, actor.isNative(), stateVars, procs, actions,
				initializes, actionScheduler);

		// Cloning moc
		if (actor.hasMoC()) {
			clone.setMoC(cloneMoC(actor.getMoC()));
		}

		return clone;
	}

	/**
	 * Returns a cloned action from the given action.
	 * 
	 * @param action
	 *            the action to clone
	 * @return a clone action
	 */
	public Action cloneAction(Action action) {
		patternVars = new OrderedMap<String, Variable>();
		peekedVars = new OrderedMap<String, Variable>();

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
	public Pattern cloneActionPattern(Pattern pattern,
			OrderedMap<String, Port> ports) {
		Pattern clonePattern = new Pattern();

		for (Port port : pattern.getPorts()) {
			Port clonedPort = getPort(port);
			clonePattern.setNumTokens(clonedPort, pattern.getNumTokens(port));

			// Clone pattern variables
			Variable peeked = pattern.getPeeked(port);
			if (peeked != null) {
				clonePattern.setPeeked(clonedPort, clonePeekedVar(peeked));
			}

			Variable var = pattern.getVariable(port);
			if (var != null) {
				clonePattern.setVariable(clonedPort, clonePatternVar(var));
			}
		}

		return clonePattern;
	}

	/**
	 * Returns a clone of the given pattern variable.
	 * 
	 * @param patternVar
	 *            a variable of a pattern;
	 * 
	 * @return the cloned pattern variable
	 */
	public LocalVariable clonePatternVar(Variable patternVar) {
		// Clone variables associated to ports
		LocalVariable cloneVar = cloneLocalVariable((LocalVariable) patternVar);

		// Store the cloned variable
		patternVars.put(cloneVar.getName(), cloneVar);

		return cloneVar;
	}

	/**
	 * Returns a clone of the given peeked variable.
	 * 
	 * @param peekedVar
	 *            a peeked variable of a pattern;
	 * 
	 * @return the cloned peeked variable
	 */
	public LocalVariable clonePeekedVar(Variable peekedVar) {
		// Clone variables associated to ports
		LocalVariable cloneVar = cloneLocalVariable((LocalVariable) peekedVar);

		// Store the cloned variable
		peekedVars.put(cloneVar.getName(), cloneVar);

		return cloneVar;
	}

	/**
	 * Returns an array filled with cloned actions.
	 * 
	 * @param actions
	 *            a list of actions to clone
	 * @return the cloned action list
	 */
	public List<Action> cloneActions(List<Action> actions) {
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
	public ActionScheduler cloneActionScheduler(ActionScheduler scheduler) {
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
	public Tag cloneActionTag(Tag tag) {
		return new Tag(tag.getIdentifiers());
	}

	/**
	 * Clones a Finite State Machine.
	 * 
	 * @param fsm
	 *            an FSM to be cloned
	 * @return the cloned FSM
	 */
	public FSM cloneFSM(FSM fsm) {
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
	 * Clones a global variable.
	 * 
	 * @param variable
	 *            the variable to clone
	 * @return the cloned variables
	 */
	public GlobalVariable cloneGlobalVariable(GlobalVariable variable) {
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
	 * Clones the given map of global variables.
	 * 
	 * @param variables
	 *            an OrderedMap of variable to clone
	 * @return the cloned variables
	 */
	public OrderedMap<String, GlobalVariable> cloneGlobalVariables(
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
	public List<Action> cloneInitializes(List<Action> actions) {
		List<Action> cloneActions = new ArrayList<Action>();

		for (Action action : actions) {
			cloneActions.add(cloneAction(action));
		}

		return cloneActions;
	}

	/**
	 * Clones a local variable.
	 * 
	 * @param variable
	 *            the variable to clone
	 * @return the cloned variables
	 */
	public LocalVariable cloneLocalVariable(LocalVariable variable) {
		Location location = cloneLocation(variable.getLocation());
		Type type = cloneType(variable.getType());

		return new LocalVariable(variable.isAssignable(), variable.getIndex(),
				location, variable.getBaseName(), type);
	}

	/**
	 * Clones the given ordered map of local variables.
	 * 
	 * @param variables
	 *            an ordered map of variables
	 * @return the cloned ordered map
	 */
	public OrderedMap<String, LocalVariable> cloneLocalVariables(
			OrderedMap<String, LocalVariable> variables) {
		OrderedMap<String, LocalVariable> cloneMap = new OrderedMap<String, LocalVariable>();

		for (LocalVariable variable : variables) {
			LocalVariable cloneVar = cloneLocalVariable(variable);
			cloneMap.put(cloneVar.getName(), cloneVar);
		}

		return cloneMap;
	}

	/**
	 * Clones the given port.
	 * 
	 * @param port
	 *            a port
	 * @return the cloned port
	 */
	public Port clonePort(Port port) {
		Location location = cloneLocation(port.getLocation());
		Type type = cloneType(port.getType());
		Port clone = new Port(location, type, port.getName());

		// Clone port token rate
		int tokensConsummed = port.getNumTokensConsumed();
		int tokensProduced = port.getNumTokensProduced();

		if (tokensConsummed > 0) {
			clone.increaseTokenConsumption(tokensConsummed);
		}

		if (tokensProduced > 0) {
			clone.increaseTokenProduction(tokensProduced);
		}

		// Store the port for a later link
		ports.put(clone.getName(), clone);

		return clone;
	}

	/**
	 * Clones an ordered map of ports.
	 * 
	 * @param port
	 *            the ordered map of ports to clone
	 * @return the cloned ordered map
	 */
	public OrderedMap<String, Port> clonePorts(OrderedMap<String, Port> ports) {
		OrderedMap<String, Port> cloneMap = new OrderedMap<String, Port>();

		for (Port port : ports) {
			Port clonePort = clonePort(port);
			cloneMap.put(clonePort.getName(), clonePort);
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
	public Procedure cloneProcedure(Procedure procedure) {
		Type returnType = cloneType(procedure.getReturnType());

		OrderedMap<String, LocalVariable> parameters = cloneLocalVariables(procedure
				.getParameters());
		OrderedMap<String, LocalVariable> locals = cloneLocalVariables(procedure
				.getLocals());

		// Store all local variable for a later link
		localVars = new OrderedMap<String, LocalVariable>();
		localVars.putAll(parameters);
		localVars.putAll(locals);

		List<Node> nodes = cloneNodes(procedure.getNodes());

		return IrFactory.eINSTANCE.createProcedure(procedure.getName(),
				procedure.isNative(), procedure.getLocation(), returnType,
				parameters, locals, nodes);
	}

	/**
	 * Clones the given procedures.
	 * 
	 * @param procedures
	 *            an ordered map of procedures
	 * @return the corresponding clones
	 */
	public OrderedMap<String, Procedure> cloneProcedures(
			OrderedMap<String, Procedure> procedures) {
		OrderedMap<String, Procedure> cloneProcedures = new OrderedMap<String, Procedure>();
		for (Procedure procedure : procedures) {
			Procedure cloneProcedure = cloneProcedure(procedure);
			cloneProcedures.put(cloneProcedure.getName(), procedure);
		}

		return cloneProcedures;
	}

}
