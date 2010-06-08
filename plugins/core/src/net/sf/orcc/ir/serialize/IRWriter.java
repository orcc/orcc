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

import static net.sf.orcc.ir.serialize.IRConstants.KEY_NAME;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_SOURCE_FILE;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_ASSIGN;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_CALL;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_HAS_TOKENS;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_LOAD;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_PEEK;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_READ;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_RETURN;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_STORE;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_WRITE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.AbstractFifoInstruction;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InitPort;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.ReadEnd;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.instructions.WriteEnd;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.FloatType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class defines a writer that serializes an actor in IR form to JSON.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IRWriter {

	/**
	 * This class defines an expression writer that serializes an expression to
	 * JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ExpressionWriter implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			JSONArray body = new JSONArray();
			array.put(body);

			body.put(IRConstants.BINARY_EXPR);
			JSONArray body2 = new JSONArray();
			body.put(body2);

			body2.put(expr.getOp().getText());
			body2.put(expr.getE1().accept(this));
			body2.put(expr.getE2().accept(this));
			body2.put(writeType(expr.getType()));

			return array;
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			array.put(expr.getValue());
			return array;
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			array.put(expr.getValue());
			return array;
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			throw new OrccRuntimeException("unsupported expression: List");
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			array.put(expr.getValue());
			return array;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			JSONArray body = new JSONArray();
			array.put(body);

			body.put(IRConstants.UNARY_EXPR);
			JSONArray body2 = new JSONArray();
			body.put(body2);

			body2.put(expr.getOp().getText());
			body2.put(expr.getExpr().accept(this));
			body2.put(writeType(expr.getType()));

			return array;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			JSONArray body = new JSONArray();
			array.put(body);

			body.put(IRConstants.VAR_EXPR);
			body.put(writeVariable(expr.getVar().getVariable()));

			return array;
		}

	}

	/**
	 * This class defines an instruction writer that serializes an instruction
	 * to JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class InstructionWriter implements InstructionVisitor {

		@Override
		public void visit(Assign assign, Object... args) {
			JSONArray array = (JSONArray) args[0];
			JSONArray instr = new JSONArray();
			array.put(instr);

			instr.put(NAME_ASSIGN);
			instr.put(writeLocation(assign.getLocation()));

			JSONArray body = new JSONArray();
			instr.put(body);

			body.put(writeVariable(assign.getTarget()));
			body.put(writeExpression(assign.getValue()));
		}

		@Override
		public void visit(Call call, Object... args) {
			JSONArray array = (JSONArray) args[0];
			JSONArray instr = new JSONArray();
			array.put(instr);

			instr.put(NAME_CALL);
			instr.put(writeLocation(call.getLocation()));

			JSONArray body = new JSONArray();
			instr.put(body);

			body.put(call.getProcedure().getName());
			if (call.hasResult()) {
				body.put(writeVariable(call.getTarget()));
			} else {
				body.put(JSONObject.NULL);
			}

			body.put(writeExpressions(call.getParameters()));
		}

		@Override
		public void visit(HasTokens hasTokens, Object... args) {
			visitFifoInstruction(NAME_HAS_TOKENS, hasTokens, args[0]);
		}

		@Override
		public void visit(InitPort initPort, Object... args) {
			// nothing to do
		}

		@Override
		public void visit(Load load, Object... args) {
			JSONArray array = (JSONArray) args[0];
			JSONArray instr = new JSONArray();
			array.put(instr);

			instr.put(NAME_LOAD);
			instr.put(writeLocation(load.getLocation()));

			JSONArray body = new JSONArray();
			instr.put(body);

			body.put(writeVariable(load.getTarget()));
			body.put(writeVariable(load.getSource().getVariable()));
			body.put(writeExpressions(load.getIndexes()));
		}

		@Override
		public void visit(Peek peek, Object... args) {
			visitFifoInstruction(NAME_PEEK, peek, args[0]);
		}

		@Override
		public void visit(PhiAssignment phi, Object... args) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(Read read, Object... args) {
			visitFifoInstruction(NAME_READ, read, args[0]);
		}

		@Override
		public void visit(ReadEnd readEnd, Object... args) {
			// nothing to do
		}

		@Override
		public void visit(Return returnInst, Object... args) {
			JSONArray array = (JSONArray) args[0];
			JSONArray instr = new JSONArray();
			array.put(instr);

			instr.put(NAME_RETURN);
			instr.put(writeLocation(returnInst.getLocation()));

			Expression value = returnInst.getValue();
			if (value == null) {
				instr.put(JSONObject.NULL);
			} else {
				instr.put(writeExpression(value));
			}
		}

		@Override
		public void visit(SpecificInstruction specific, Object... args) {
			throw new OrccRuntimeException(
					"IR writer cannot write specific instructions");
		}

		@Override
		public void visit(Store store, Object... args) {
			JSONArray array = (JSONArray) args[0];
			JSONArray instr = new JSONArray();
			array.put(instr);

			instr.put(NAME_STORE);
			instr.put(writeLocation(store.getLocation()));

			JSONArray body = new JSONArray();
			instr.put(body);

			body.put(writeVariable(store.getTarget().getVariable()));
			body.put(writeExpressions(store.getIndexes()));
			body.put(writeExpression(store.getValue()));
		}

		@Override
		public void visit(Write write, Object... args) {
			visitFifoInstruction(NAME_WRITE, write, args[0]);
		}

		@Override
		public void visit(WriteEnd writeEnd, Object... args) {
			// nothing to do
		}

		/**
		 * Visits the given FIFO instruction.
		 * 
		 * @param name
		 *            name of the instruction as indicated in
		 *            {@link IRConstants}. Expected to be the name of a
		 *            hasTokens, peek, read, write.
		 * @param fifoInstr
		 *            the {@link AbstractFifoInstruction} we want to visit
		 * @param object
		 *            the object passed in args[0], expected to be the target
		 *            JSON array
		 */
		private void visitFifoInstruction(String name,
				AbstractFifoInstruction fifoInstr, Object object) {
			JSONArray array = (JSONArray) object;
			JSONArray instr = new JSONArray();
			array.put(instr);

			instr.put(name);
			instr.put(writeLocation(fifoInstr.getLocation()));

			JSONArray body = new JSONArray();
			instr.put(body);

			body.put(writeVariable(fifoInstr.getTarget()));
			body.put(fifoInstr.getPort().getName());
			body.put(fifoInstr.getNumTokens());
		}

	}

	/**
	 * This class defines a node writer that serializes a CFG node to JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class NodeWriter implements NodeVisitor {

		@Override
		public void visit(BlockNode node, Object... args) {
			JSONArray array = (JSONArray) args[0];
			for (Instruction instruction : node) {
				instruction.accept(instrWriter, array);
			}
		}

		@Override
		public void visit(IfNode node, Object... args) {
			JSONArray array = (JSONArray) args[0];

			JSONArray ifArray = new JSONArray();
			array.put(ifArray);

			ifArray.put(IRConstants.NAME_IF);
			ifArray.put(writeLocation(node.getLocation()));

			JSONArray body = new JSONArray();
			ifArray.put(body);

			body.put(writeExpression(node.getValue()));
			body.put(writeNodes(node.getThenNodes()));
			body.put(writeNodes(node.getElseNodes()));
		}

		@Override
		public void visit(WhileNode node, Object... args) {
			JSONArray array = (JSONArray) args[0];

			// header
			JSONArray whileArray = new JSONArray();
			array.put(whileArray);

			whileArray.put(IRConstants.NAME_WHILE);
			whileArray.put(writeLocation(node.getLocation()));

			JSONArray body = new JSONArray();
			whileArray.put(body);

			body.put(writeExpression(node.getValue()));
			body.put(writeNodes(node.getNodes()));
		}
	}

	/**
	 * This class defines a type writer that serializes a type to JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class TypeWriter implements TypeInterpreter {

		@Override
		public Object interpret(BoolType type) {
			return BoolType.NAME;
		}

		@Override
		public Object interpret(FloatType type) {
			return FloatType.NAME;
		}

		@Override
		public Object interpret(IntType type) {
			JSONArray array = new JSONArray();
			array.put(IntType.NAME);
			// FIXME change JSON format back to using integer size
			Expression expr = new IntExpr(type.getSize());
			array.put(writeExpression(expr));
			return array;
		}

		@Override
		public Object interpret(ListType type) {
			JSONArray array = new JSONArray();
			array.put(ListType.NAME);
			// FIXME change JSON format back to using integer size
			Expression expr = new IntExpr(type.getSize());
			array.put(writeExpression(expr));
			array.put(writeType(type.getElementType()));
			return array;
		}

		@Override
		public Object interpret(StringType type) {
			return StringType.NAME;
		}

		@Override
		public Object interpret(UintType type) {
			JSONArray array = new JSONArray();
			array.put(UintType.NAME);
			// FIXME change JSON format back to using integer size
			Expression expr = new IntExpr(type.getSize());
			array.put(writeExpression(expr));
			return array;
		}

		@Override
		public Object interpret(VoidType type) {
			return VoidType.NAME;
		}

	}

	private Actor actor;

	private final ExpressionWriter exprWriter;

	private final InstructionWriter instrWriter;

	private final NodeWriter nodeWriter;

	private final TypeWriter typeWriter;

	/**
	 * Creates an actor writer on the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public IRWriter(Actor actor) {
		this.actor = actor;

		exprWriter = new ExpressionWriter();
		instrWriter = new InstructionWriter();
		nodeWriter = new NodeWriter();
		typeWriter = new TypeWriter();
	}

	public void write(String outputDir) throws OrccException {
		try {
			OutputStream os = new FileOutputStream(outputDir + File.separator
					+ actor.getName() + ".json");
			JSONObject obj = writeActor();
			os.write(obj.toString(2).getBytes("UTF-8"));
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		} catch (JSONException e) {
			throw new OrccException("JSON error", e);
		}
	}

	/**
	 * Returns a JSON array encoded from the given action.
	 * 
	 * @param action
	 *            an action
	 * @return a JSON array
	 */
	private JSONArray writeAction(Action action) {
		JSONArray array = new JSONArray();
		array.put(writeActionTag(action.getTag()));
		array.put(writeActionPattern(action.getInputPattern()));
		array.put(writeActionPattern(action.getOutputPattern()));
		array.put(writeProcedure(action.getScheduler()));
		array.put(writeProcedure(action.getBody()));

		return array;
	}

	/**
	 * Returns a JSON array encoded from the given pattern.
	 * 
	 * @param pattern
	 *            an input pattern or output pattern as a map of &lt;port,
	 *            integer&gt;
	 * @return a JSON array
	 */
	private JSONArray writeActionPattern(Pattern pattern) {
		JSONArray array = new JSONArray();
		for (Entry<Port, Integer> entry : pattern.entrySet()) {
			array.put(entry.getKey().getName());
			array.put(entry.getValue().intValue());
		}

		return array;
	}

	/**
	 * Returns a JSON array filled with actions.
	 * 
	 * @param actions
	 *            a list of actions
	 * @return the action list encoded in JSON
	 */
	private JSONArray writeActions(List<Action> actions) {
		JSONArray array = new JSONArray();
		for (Action action : actions) {
			array.put(writeAction(action));
		}

		return array;
	}

	/**
	 * Returns a JSON array filled as follows. First entry is an array that
	 * contains the tags of the actions of the action scheduler. Second entry is
	 * the FSM of the action scheduler.
	 * 
	 * @param scheduler
	 *            the action scheduler of the actor this writer was built with
	 * @return the action scheduler encoded in JSON
	 */
	private JSONArray writeActionScheduler(ActionScheduler scheduler) {
		JSONArray array = new JSONArray();

		JSONArray actions = new JSONArray();
		array.put(actions);
		for (Action action : scheduler.getActions()) {
			actions.put(writeActionTag(action.getTag()));
		}

		if (scheduler.hasFsm()) {
			array.put(writeFSM(scheduler.getFsm()));
		} else {
			array.put(JSONObject.NULL);
		}

		return array;
	}

	/**
	 * Returns a JSON array whose entries are the tag identifiers. If the tag is
	 * empty, the array returned is empty.
	 * 
	 * @param tag
	 *            an action tag
	 * @return the tag encoded in JSON
	 */
	private JSONArray writeActionTag(Tag tag) {
		JSONArray array = new JSONArray();
		for (String identifier : tag) {
			array.put(identifier);
		}

		return array;
	}

	private JSONObject writeActor() throws JSONException {
		JSONObject obj = new JSONObject();
		JSONArray array;

		obj.put(KEY_SOURCE_FILE, actor.getFile());
		obj.put(KEY_NAME, actor.getName());
		obj.put(IRConstants.KEY_INPUTS, writePorts(actor.getInputs()));
		obj.put(IRConstants.KEY_OUTPUTS, writePorts(actor.getOutputs()));
		obj.put(IRConstants.KEY_STATE_VARS,
				writeStateVariables(actor.getStateVars()));
		obj.put(IRConstants.KEY_PROCEDURES, writeProcedures(actor.getProcs()));

		obj.put(IRConstants.KEY_ACTIONS, writeActions(actor.getActions()));
		obj.put(IRConstants.KEY_INITIALIZES,
				writeActions(actor.getInitializes()));

		array = writeActionScheduler(actor.getActionScheduler());
		obj.put(IRConstants.KEY_ACTION_SCHED, array);
		return obj;
	}

	private Object writeConstant(Object obj) {
		if (obj instanceof Boolean || obj instanceof Integer
				|| obj instanceof String) {
			return obj;
		} else if (obj instanceof List<?>) {
			List<?> list = (List<?>) obj;
			JSONArray array = new JSONArray();
			for (Object o : list) {
				array.put(writeConstant(o));
			}
			return array;
		} else {
			throw new OrccRuntimeException("Unknown constant: " + obj);
		}
	}

	/**
	 * Writes the given expression as JSON.
	 * 
	 * @param expression
	 *            an expression
	 * @return a JSON array
	 */
	private JSONArray writeExpression(Expression expression) {
		return (JSONArray) expression.accept(exprWriter);
	}

	/**
	 * Serializes the given list of expressions as JSON array whose each member
	 * is set to the result of {@link #writeExpression(Expression)}.
	 * 
	 * @param expressions
	 *            a list of expressions
	 * @return a JSON array
	 */
	private JSONArray writeExpressions(List<Expression> expressions) {
		JSONArray array = new JSONArray();
		for (Expression expression : expressions) {
			array.put(writeExpression(expression));
		}

		return array;
	}

	/**
	 * Writes a Finite State Machine as JSON.
	 * 
	 * @param fsm
	 *            an FSM (must not be <code>null</code>)
	 * @return a JSON array
	 */
	private JSONArray writeFSM(FSM fsm) {
		JSONArray array = new JSONArray();
		array.put(fsm.getInitialState().getName());

		JSONArray stateArray = new JSONArray();
		array.put(stateArray);
		for (String stateName : fsm.getStates()) {
			stateArray.put(stateName);
		}

		JSONArray transitionsArray = new JSONArray();
		array.put(transitionsArray);
		for (Transition transition : fsm.getTransitions()) {
			JSONArray transitionArray = new JSONArray();
			transitionsArray.put(transitionArray);

			transitionArray.put(transition.getSourceState().getName());

			JSONArray actionsArray = new JSONArray();
			transitionArray.put(actionsArray);

			for (NextStateInfo info : transition.getNextStateInfo()) {
				JSONArray actionArray = new JSONArray();
				actionsArray.put(actionArray);

				actionArray.put(writeActionTag(info.getAction().getTag()));
				actionArray.put(info.getTargetState().getName());
			}
		}

		return array;
	}

	/**
	 * Serializes the given variable declaration to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private JSONArray writeLocalVariable(LocalVariable variable) {
		JSONArray array = new JSONArray();

		JSONArray details = new JSONArray();
		array.put(details);
		details.put(variable.getBaseName());
		details.put(variable.isAssignable());
		Object suffix = variable.hasSuffix() ? variable.getSuffix()
				: JSONObject.NULL;
		details.put(suffix);
		details.put(variable.getIndex());

		array.put(writeLocation(variable.getLocation()));
		array.put(writeType(variable.getType()));

		return array;
	}

	/**
	 * Writes the given ordered map of local variables.
	 * 
	 * @param variables
	 *            an ordered map of variables
	 * @return a JSON array
	 */
	private JSONArray writeLocalVariables(OrderedMap<Variable> variables) {
		JSONArray array = new JSONArray();
		for (Variable variable : variables) {
			array.put(writeLocalVariable((LocalVariable) variable));
		}
		return array;
	}

	private JSONArray writeLocation(Location location) {
		JSONArray array = new JSONArray();
		array.put(location.getStartLine());
		array.put(location.getStartColumn());
		array.put(location.getEndColumn());

		return array;
	}

	/**
	 * Writes the given nodes.
	 * 
	 * @param nodes
	 *            a list of nodes
	 * @return a JSON array
	 */
	private JSONArray writeNodes(List<CFGNode> nodes) {
		JSONArray array = new JSONArray();
		for (CFGNode node : nodes) {
			node.accept(nodeWriter, array);
		}

		return array;
	}

	/**
	 * Writes the given port.
	 * 
	 * @param port
	 *            a port
	 * @return a JSON array
	 */
	private JSONArray writePort(Port port) {
		JSONArray array = new JSONArray();
		array.put(writeLocation(port.getLocation()));
		array.put(writeType(port.getType()));
		array.put(port.getName());
		return array;
	}

	private JSONArray writePorts(OrderedMap<Port> ports) {
		JSONArray array = new JSONArray();
		for (Port port : ports) {
			array.put(writePort(port));
		}

		return array;
	}

	/**
	 * Returns a JSON array encoded from the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a JSON array
	 */
	private JSONArray writeProcedure(Procedure procedure) {
		JSONArray array = new JSONArray();
		array.put(procedure.getName());
		array.put(procedure.isExternal());
		array.put(writeLocation(procedure.getLocation()));
		array.put(writeType(procedure.getReturnType()));

		array.put(writeLocalVariables(procedure.getParameters()));
		array.put(writeLocalVariables(procedure.getLocals()));
		array.put(writeNodes(procedure.getNodes())); // nodes

		return array;
	}

	/**
	 * Serializes the given procedures to a JSON array.
	 * 
	 * @param procedures
	 *            an ordered map of procedures
	 * @return a JSON array
	 */
	private JSONArray writeProcedures(OrderedMap<Procedure> procedures) {
		JSONArray array = new JSONArray();
		for (Procedure procedure : procedures) {
			array.put(writeProcedure(procedure));
		}
		return array;
	}

	/**
	 * Serializes the given variable declaration to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private JSONArray writeStateVariable(StateVariable variable) {
		JSONArray array = new JSONArray();

		// variable
		JSONArray variableArray = new JSONArray();
		array.put(variableArray);

		JSONArray details = new JSONArray();
		variableArray.put(details);

		details.put(variable.getName());
		details.put(variable.isAssignable());
		details.put(JSONObject.NULL);
		details.put(0);

		variableArray.put(writeLocation(variable.getLocation()));
		variableArray.put(writeType(variable.getType()));

		Object constant = variable.getConstantValue();
		if (constant == null) {
			array.put(JSONObject.NULL);
		} else {
			Object constantValue = writeConstant(constant);
			array.put(constantValue);
		}

		return array;
	}

	/**
	 * Writes the given ordered map of state variables.
	 * 
	 * @param variables
	 *            an ordered map of variables
	 * @return a JSON array
	 */
	private JSONArray writeStateVariables(OrderedMap<Variable> variables) {
		JSONArray array = new JSONArray();
		for (Variable variable : variables) {
			array.put(writeStateVariable((StateVariable) variable));
		}
		return array;
	}

	/**
	 * Writes the given type as JSON.
	 * 
	 * @param type
	 *            a type
	 * @return JSON content, as a string or an array
	 */
	private Object writeType(Type type) {
		return type.accept(typeWriter);
	}

	/**
	 * Serializes the given variable to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private JSONArray writeVariable(Variable variable) {
		JSONArray array = new JSONArray();
		if (variable.isGlobal()) {
			array.put(variable.getName());
			array.put(JSONObject.NULL);
			array.put(0);
		} else {
			LocalVariable local = (LocalVariable) variable;
			array.put(local.getBaseName());
			array.put(local.hasSuffix() ? local.getSuffix() : JSONObject.NULL);
			array.put(local.getIndex());
		}

		return array;
	}

}
