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

import static net.sf.orcc.ir.serialize.IRConstants.EXPR_BINARY;
import static net.sf.orcc.ir.serialize.IRConstants.EXPR_LIST;
import static net.sf.orcc.ir.serialize.IRConstants.EXPR_UNARY;
import static net.sf.orcc.ir.serialize.IRConstants.EXPR_VAR;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_ASSIGN;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_CALL;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_LOAD;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_PHI;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_RETURN;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_STORE;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_ACTIONS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_ACTION_SCHED;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INITIALIZES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_NAME;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_NATIVE;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_OUTPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_PARAMETERS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_PROCEDURES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_SOURCE_FILE;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_STATE_VARS;
import static net.sf.orcc.ir.serialize.IRConstants.NODE_BLOCK;
import static net.sf.orcc.ir.serialize.IRConstants.NODE_IF;
import static net.sf.orcc.ir.serialize.IRConstants.NODE_WHILE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
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
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.InstructionInterpreter;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.NodeInterpreter;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.OrderedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
	private static class ExpressionWriter implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(EXPR_BINARY));
			array.add(new JsonPrimitive(expr.getOp().getText()));
			array.add(writeExpression(expr.getE1()));
			array.add(writeExpression(expr.getE2()));
			array.add(writeType(expr.getType()));
			return array;
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			return new JsonPrimitive(expr.getValue());
		}

		@Override
		public Object interpret(FloatExpr expr, Object... args) {
			return new JsonPrimitive(expr.getValue());
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			return new JsonPrimitive(expr.getValue());
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(EXPR_LIST));
			for (Expression expression : expr.getValue()) {
				array.add(writeExpression(expression));
			}
			return array;
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			return new JsonPrimitive(expr.getValue());
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(EXPR_UNARY));
			array.add(new JsonPrimitive(expr.getOp().getText()));
			array.add(writeExpression(expr.getExpr()));
			array.add(writeType(expr.getType()));
			return array;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(EXPR_VAR));
			array.add(writeVariable(expr.getVar().getVariable()));
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
	private static class InstructionWriter implements InstructionInterpreter {

		@Override
		public Object interpret(Assign assign, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(INSTR_ASSIGN));
			array.add(writeLocation(assign.getLocation()));

			array.add(writeVariable(assign.getTarget()));
			array.add(writeExpression(assign.getValue()));

			return array;
		}

		@Override
		public Object interpret(Call call, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(INSTR_CALL));
			array.add(writeLocation(call.getLocation()));

			array.add(new JsonPrimitive(call.getProcedure().getName()));
			array.add(writeExpressions(call.getParameters()));

			array.add(call.hasResult() ? writeVariable(call.getTarget()) : null);

			return array;
		}

		@Override
		public Object interpret(Load load, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(INSTR_LOAD));
			array.add(writeLocation(load.getLocation()));

			array.add(writeVariable(load.getTarget()));
			array.add(writeVariable(load.getSource().getVariable()));
			array.add(writeExpressions(load.getIndexes()));

			return array;
		}

		@Override
		public Object interpret(PhiAssignment phi, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(INSTR_PHI));
			array.add(writeLocation(phi.getLocation()));

			array.add(writeVariable(phi.getTarget()));
			array.add(writeExpressions(phi.getValues()));

			return array;
		}

		@Override
		public Object interpret(Return returnInst, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(INSTR_RETURN));
			array.add(writeLocation(returnInst.getLocation()));

			Expression value = returnInst.getValue();
			if (value == null) {
				array.add(null);
			} else {
				array.add(writeExpression(value));
			}

			return array;
		}

		@Override
		public Object interpret(SpecificInstruction specific, Object... args) {
			throw new OrccRuntimeException(
					"IR writer cannot write specific instructions");
		}

		@Override
		public Object interpret(Store store, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(INSTR_STORE));
			array.add(writeLocation(store.getLocation()));

			array.add(writeVariable(store.getTarget()));
			array.add(writeExpressions(store.getIndexes()));
			array.add(writeExpression(store.getValue()));

			return array;
		}

	}

	/**
	 * This class defines a node writer that serializes a CFG node to JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class NodeWriter implements NodeInterpreter {

		@Override
		public Object interpret(NodeBlock node, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(NODE_BLOCK));
			array.add(writeLocation(node.getLocation()));

			array.add(writeIntructions(node.getInstructions()));

			return array;
		}

		@Override
		public Object interpret(NodeIf node, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(NODE_IF));
			array.add(writeLocation(node.getLocation()));

			array.add(writeExpression(node.getValue()));
			array.add(writeNodes(node.getThenNodes()));
			array.add(writeNodes(node.getElseNodes()));

			array.add(writeNode(node.getJoinNode()));

			return array;
		}

		@Override
		public Object interpret(NodeWhile node, Object... args) {
			JsonArray array = new JsonArray();

			array.add(new JsonPrimitive(NODE_WHILE));
			array.add(writeLocation(node.getLocation()));

			array.add(writeExpression(node.getValue()));
			array.add(writeNodes(node.getNodes()));
			array.add(writeNode(node.getJoinNode()));

			return array;
		}
	}

	/**
	 * This class defines a type writer that serializes a type to JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class TypeWriter implements TypeInterpreter {

		@Override
		public Object interpret(TypeBool type) {
			return new JsonPrimitive(TypeBool.NAME);
		}

		@Override
		public Object interpret(TypeFloat type) {
			return new JsonPrimitive(TypeFloat.NAME);
		}

		@Override
		public Object interpret(TypeInt type) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(TypeInt.NAME));
			array.add(new JsonPrimitive(type.getSize()));
			return array;
		}

		@Override
		public Object interpret(TypeList type) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(TypeList.NAME));
			array.add(writeExpression(type.getSizeExpr()));
			array.add(writeType(type.getType()));
			return array;
		}

		@Override
		public Object interpret(TypeString type) {
			return new JsonPrimitive(TypeString.NAME);
		}

		@Override
		public Object interpret(TypeUint type) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(TypeUint.NAME));
			array.add(new JsonPrimitive(type.getSize()));
			return array;
		}

		@Override
		public Object interpret(TypeVoid type) {
			return new JsonPrimitive(TypeVoid.NAME);
		}

	}

	/**
	 * Writes the given expression as JSON.
	 * 
	 * @param expression
	 *            an expression
	 * @return a JSON array
	 */
	private static JsonElement writeExpression(Expression expression) {
		return (JsonElement) expression.accept(new ExpressionWriter());
	}

	/**
	 * Serializes the given list of expressions as JSON array whose each member
	 * is set to the result of {@link #writeExpression(Expression)}.
	 * 
	 * @param expressions
	 *            a list of expressions
	 * @return a JSON array
	 */
	private static JsonArray writeExpressions(List<Expression> expressions) {
		JsonArray array = new JsonArray();
		for (Expression expression : expressions) {
			array.add(writeExpression(expression));
		}

		return array;
	}

	private static JsonElement writeInstruction(Instruction instruction) {
		return (JsonElement) instruction.accept(new InstructionWriter());
	}

	private static JsonArray writeIntructions(List<Instruction> instructions) {
		JsonArray array = new JsonArray();
		for (Instruction instruction : instructions) {
			array.add(writeInstruction(instruction));
		}
		return array;
	}

	private static JsonArray writeLocation(Location location) {
		if (location == null) {
			location = new Location();
		}

		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(location.getStartLine()));
		array.add(new JsonPrimitive(location.getStartColumn()));
		array.add(new JsonPrimitive(location.getEndColumn()));

		return array;
	}

	private static JsonElement writeNode(Node node) {
		return (JsonElement) node.accept(new NodeWriter());
	}

	/**
	 * Writes the given nodes.
	 * 
	 * @param nodes
	 *            a list of nodes
	 * @return a JSON array
	 */
	private static JsonArray writeNodes(List<Node> nodes) {
		JsonArray array = new JsonArray();
		for (Node node : nodes) {
			array.add(writeNode(node));
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
	private static JsonElement writeType(Type type) {
		return (JsonElement) type.accept(new TypeWriter());
	}

	/**
	 * Serializes the given variable to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private static JsonArray writeVariable(Variable variable) {
		JsonArray array = new JsonArray();
		if (variable.isGlobal()) {
			array.add(new JsonPrimitive(variable.getName()));
			array.add(new JsonPrimitive(0));
		} else {
			LocalVariable local = (LocalVariable) variable;
			array.add(new JsonPrimitive(local.getBaseName()));
			array.add(new JsonPrimitive(local.getIndex()));
		}

		return array;
	}

	private Actor actor;

	/**
	 * Creates an actor writer on the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public IRWriter(Actor actor) {
		this.actor = actor;
	}

	public void write(String outputDir, boolean prettyPrint)
			throws OrccException {
		Writer writer;
		try {
			String folder = OrccUtil.getFolder(actor);
			new File(outputDir + File.separator + folder).mkdirs();
			OutputStream os = new FileOutputStream(outputDir + File.separator
					+ OrccUtil.getFile(actor) + ".json");
			// write output as UTF-8
			writer = new BufferedWriter(new OutputStreamWriter(os));
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}

		try {
			JsonObject obj = writeActor();
			GsonBuilder builder = new GsonBuilder();
			builder.disableHtmlEscaping();
			if (prettyPrint) {
				builder.setPrettyPrinting();
			}

			Gson gson = builder.create();
			gson.toJson(obj, writer);
		} finally {
			// because some GSON methods may throw a RuntimeException
			try {
				writer.close();
			} catch (IOException e) {
				throw new OrccException("I/O error", e);
			}
		}
	}

	/**
	 * Returns a JSON array encoded from the given action.
	 * 
	 * @param action
	 *            an action
	 * @return a JSON array
	 */
	private JsonArray writeAction(Action action) {
		JsonArray array = new JsonArray();
		array.add(writeActionTag(action.getTag()));
		array.add(writeActionPattern(action.getInputPattern()));
		array.add(writeActionPattern(action.getOutputPattern()));
		array.add(writeProcedure(action.getScheduler()));
		array.add(writeProcedure(action.getBody()));

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
	private JsonArray writeActionPattern(Pattern pattern) {
		JsonArray array = new JsonArray();
		for (Port port : pattern.getPorts()) {
			JsonArray patternArray = new JsonArray();
			array.add(patternArray);

			patternArray.add(new JsonPrimitive(port.getName()));
			patternArray.add(new JsonPrimitive(pattern.getNumTokens(port)));
			Variable peeked = pattern.getPeeked(port);
			if (peeked == null) {
				patternArray.add(new JsonNull());
			} else {
				patternArray.add(writeLocalVariable((LocalVariable) peeked));
			}
			patternArray.add(writeLocalVariable((LocalVariable) pattern
					.getVariable(port)));
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
	private JsonArray writeActions(List<Action> actions) {
		JsonArray array = new JsonArray();
		for (Action action : actions) {
			array.add(writeAction(action));
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
	private JsonArray writeActionScheduler(ActionScheduler scheduler) {
		JsonArray array = new JsonArray();

		JsonArray actions = new JsonArray();
		array.add(actions);
		for (Action action : scheduler.getActions()) {
			actions.add(writeActionTag(action.getTag()));
		}

		if (scheduler.hasFsm()) {
			array.add(writeFSM(scheduler.getFsm()));
		} else {
			array.add(null);
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
	private JsonArray writeActionTag(Tag tag) {
		JsonArray array = new JsonArray();
		for (String identifier : tag) {
			array.add(new JsonPrimitive(identifier));
		}

		return array;
	}

	private JsonObject writeActor() {
		JsonObject obj = new JsonObject();
		JsonArray array;

		obj.addProperty(KEY_SOURCE_FILE, actor.getFile());
		obj.addProperty(KEY_NAME, actor.getName());
		obj.add(KEY_PARAMETERS, writeGlobalVariables(actor.getParameters()));
		obj.add(KEY_INPUTS, writePorts(actor.getInputs()));
		obj.add(KEY_OUTPUTS, writePorts(actor.getOutputs()));
		obj.addProperty(KEY_NATIVE, actor.isNative());
		obj.add(KEY_STATE_VARS, writeGlobalVariables(actor.getStateVars()));
		obj.add(KEY_PROCEDURES, writeProcedures(actor.getProcs()));

		obj.add(KEY_ACTIONS, writeActions(actor.getActions()));
		obj.add(KEY_INITIALIZES, writeActions(actor.getInitializes()));

		array = writeActionScheduler(actor.getActionScheduler());
		obj.add(KEY_ACTION_SCHED, array);
		return obj;
	}

	/**
	 * Writes a Finite State Machine as JSON.
	 * 
	 * @param fsm
	 *            an FSM (must not be <code>null</code>)
	 * @return a JSON array
	 */
	private JsonArray writeFSM(FSM fsm) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(fsm.getInitialState().getName()));

		JsonArray stateArray = new JsonArray();
		array.add(stateArray);
		for (String stateName : fsm.getStates()) {
			stateArray.add(new JsonPrimitive(stateName));
		}

		JsonArray transitionsArray = new JsonArray();
		array.add(transitionsArray);
		for (Transition transition : fsm.getTransitions()) {
			JsonArray transitionArray = new JsonArray();
			transitionsArray.add(transitionArray);

			transitionArray.add(new JsonPrimitive(transition.getSourceState()
					.getName()));

			JsonArray actionsArray = new JsonArray();
			transitionArray.add(actionsArray);

			for (NextStateInfo info : transition.getNextStateInfo()) {
				JsonArray actionArray = new JsonArray();
				actionsArray.add(actionArray);

				actionArray.add(writeActionTag(info.getAction().getTag()));
				actionArray.add(new JsonPrimitive(info.getTargetState()
						.getName()));
			}
		}

		return array;
	}

	/**
	 * Serializes the given global variable declaration to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private JsonArray writeGlobalVariable(GlobalVariable variable) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(variable.getName()));
		array.add(new JsonPrimitive(variable.isAssignable()));
		array.add(writeLocation(variable.getLocation()));
		array.add(writeType(variable.getType()));

		Expression constant = variable.getInitialValue();
		if (constant == null) {
			array.add(null);
		} else {
			JsonElement constantValue = writeExpression(constant);
			array.add(constantValue);
		}

		return array;
	}

	/**
	 * Writes the given ordered map of global variables.
	 * 
	 * @param variables
	 *            an ordered map of global variables
	 * @return a JSON array
	 */
	private JsonArray writeGlobalVariables(
			OrderedMap<String, GlobalVariable> variables) {
		JsonArray array = new JsonArray();
		for (GlobalVariable variable : variables) {
			array.add(writeGlobalVariable(variable));
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
	private JsonArray writeLocalVariable(LocalVariable variable) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(variable.getBaseName()));
		array.add(new JsonPrimitive(variable.isAssignable()));
		array.add(new JsonPrimitive(variable.getIndex()));
		array.add(writeLocation(variable.getLocation()));
		array.add(writeType(variable.getType()));

		return array;
	}

	/**
	 * Writes the given ordered map of local variables.
	 * 
	 * @param variables
	 *            an ordered map of variables
	 * @return a JSON array
	 */
	private JsonArray writeLocalVariables(
			OrderedMap<String, LocalVariable> variables) {
		JsonArray array = new JsonArray();
		for (LocalVariable variable : variables) {
			array.add(writeLocalVariable(variable));
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
	private JsonArray writePort(Port port) {
		JsonArray array = new JsonArray();
		array.add(writeLocation(port.getLocation()));
		array.add(writeType(port.getType()));
		array.add(new JsonPrimitive(port.getName()));
		return array;
	}

	private JsonArray writePorts(OrderedMap<String, Port> ports) {
		JsonArray array = new JsonArray();
		for (Port port : ports) {
			array.add(writePort(port));
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
	private JsonArray writeProcedure(Procedure procedure) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(procedure.getName()));
		array.add(new JsonPrimitive(procedure.isNative()));
		array.add(writeLocation(procedure.getLocation()));
		array.add(writeType(procedure.getReturnType()));

		array.add(writeLocalVariables(procedure.getParameters()));
		array.add(writeLocalVariables(procedure.getLocals()));
		array.add(writeNodes(procedure.getNodes())); // nodes

		return array;
	}

	/**
	 * Serializes the given procedures to a JSON array.
	 * 
	 * @param procedures
	 *            an ordered map of procedures
	 * @return a JSON array
	 */
	private JsonArray writeProcedures(OrderedMap<String, Procedure> procedures) {
		JsonArray array = new JsonArray();
		for (Procedure procedure : procedures) {
			array.add(writeProcedure(procedure));
		}
		return array;
	}

}
