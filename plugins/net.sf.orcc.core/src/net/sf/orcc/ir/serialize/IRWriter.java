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

import static net.sf.orcc.ir.serialize.IRConstants.KEY_ACTIONS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INITIALIZES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_NAME;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_OUTPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_PARAMETERS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_PROCEDURES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_SOURCE_FILE;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_STATE_VARS;
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
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.AbstractFifoInstruction;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InstructionVisitor;
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
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.util.OrderedMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
	private class ExpressionWriter implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(writeLocation(expr.getLocation()));
			JsonArray body = new JsonArray();
			array.add(body);

			body.add(new JsonPrimitive(IRConstants.BINARY_EXPR));
			JsonArray body2 = new JsonArray();
			body.add(body2);

			body2.add(new JsonPrimitive(expr.getOp().getText()));
			body2.add(writeExpression(expr.getE1()));
			body2.add(writeExpression(expr.getE2()));
			body2.add(writeType(expr.getType()));

			return array;
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(writeLocation(expr.getLocation()));
			array.add(new JsonPrimitive(expr.getValue()));
			return array;
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(writeLocation(expr.getLocation()));
			array.add(new JsonPrimitive(expr.getValue()));
			return array;
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			throw new OrccRuntimeException("unsupported expression: List");
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(writeLocation(expr.getLocation()));
			array.add(new JsonPrimitive(expr.getValue()));
			return array;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(writeLocation(expr.getLocation()));
			JsonArray body = new JsonArray();
			array.add(body);

			body.add(new JsonPrimitive(IRConstants.UNARY_EXPR));
			JsonArray body2 = new JsonArray();
			body.add(body2);

			body2.add(new JsonPrimitive(expr.getOp().getText()));
			body2.add(writeExpression(expr.getExpr()));
			body2.add(writeType(expr.getType()));

			return array;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			JsonArray array = new JsonArray();
			array.add(writeLocation(expr.getLocation()));
			JsonArray body = new JsonArray();
			array.add(body);

			body.add(new JsonPrimitive(IRConstants.VAR_EXPR));
			body.add(writeVariable(expr.getVar().getVariable()));

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
			JsonArray array = (JsonArray) args[0];
			JsonArray instr = new JsonArray();
			array.add(instr);

			instr.add(new JsonPrimitive(NAME_ASSIGN));
			instr.add(writeLocation(assign.getLocation()));

			JsonArray body = new JsonArray();
			instr.add(body);

			body.add(writeVariable(assign.getTarget()));
			body.add(writeExpression(assign.getValue()));
		}

		@Override
		public void visit(Call call, Object... args) {
			JsonArray array = (JsonArray) args[0];
			JsonArray instr = new JsonArray();
			array.add(instr);

			instr.add(new JsonPrimitive(NAME_CALL));
			instr.add(writeLocation(call.getLocation()));

			JsonArray body = new JsonArray();
			instr.add(body);

			body.add(new JsonPrimitive(call.getProcedure().getName()));
			if (call.hasResult()) {
				body.add(writeVariable(call.getTarget()));
			} else {
				body.add(null);
			}

			body.add(writeExpressions(call.getParameters()));
		}

		@Override
		public void visit(HasTokens hasTokens, Object... args) {
			visitFifoOperation(NAME_HAS_TOKENS, hasTokens.getLocation(),
					hasTokens.getTarget(), hasTokens.getPort(),
					hasTokens.getNumTokens(), (JsonArray) args[0]);
		}

		@Override
		public void visit(Load load, Object... args) {
			JsonArray array = (JsonArray) args[0];
			JsonArray instr = new JsonArray();
			array.add(instr);

			instr.add(new JsonPrimitive(NAME_LOAD));
			instr.add(writeLocation(load.getLocation()));

			JsonArray body = new JsonArray();
			instr.add(body);

			body.add(writeVariable(load.getTarget()));
			body.add(writeVariable(load.getSource().getVariable()));
			body.add(writeExpressions(load.getIndexes()));
		}

		@Override
		public void visit(Peek peek, Object... args) {
			visitFifoInstruction(NAME_PEEK, peek, args[0]);
		}

		@Override
		public void visit(PhiAssignment phi, Object... args) {
			JsonArray array = (JsonArray) args[0];
			JsonArray instr = new JsonArray();
			array.add(instr);

			// target
			instr.add(writeVariable(phi.getTarget()));

			// sources
			instr.add(writeExpressions(phi.getValues()));
		}

		@Override
		public void visit(Read read, Object... args) {
			visitFifoInstruction(NAME_READ, read, args[0]);
		}

		@Override
		public void visit(Return returnInst, Object... args) {
			JsonArray array = (JsonArray) args[0];
			JsonArray instr = new JsonArray();
			array.add(instr);

			instr.add(new JsonPrimitive(NAME_RETURN));
			instr.add(writeLocation(returnInst.getLocation()));

			Expression value = returnInst.getValue();
			if (value == null) {
				instr.add(null);
			} else {
				instr.add(writeExpression(value));
			}
		}

		@Override
		public void visit(SpecificInstruction specific, Object... args) {
			throw new OrccRuntimeException(
					"IR writer cannot write specific instructions");
		}

		@Override
		public void visit(Store store, Object... args) {
			JsonArray array = (JsonArray) args[0];
			JsonArray instr = new JsonArray();
			array.add(instr);

			instr.add(new JsonPrimitive(NAME_STORE));
			instr.add(writeLocation(store.getLocation()));

			JsonArray body = new JsonArray();
			instr.add(body);

			body.add(writeVariable(store.getTarget()));
			body.add(writeExpressions(store.getIndexes()));
			body.add(writeExpression(store.getValue()));
		}

		@Override
		public void visit(Write write, Object... args) {
			visitFifoInstruction(NAME_WRITE, write, args[0]);
		}

		/**
		 * Visits the given FIFO instruction.
		 * 
		 * @param name
		 *            name of the instruction as indicated in
		 *            {@link IRConstants}. Expected to be the name of a
		 *            hasTokens, peek, read, write.
		 * @param instr
		 *            the {@link AbstractFifoInstruction} we want to visit
		 * @param object
		 *            the object passed in args[0], expected to be the target
		 *            JSON array
		 */
		private void visitFifoInstruction(String name,
				AbstractFifoInstruction instr, Object object) {
			visitFifoOperation(name, instr.getLocation(), instr.getTarget(),
					instr.getPort(), instr.getNumTokens(), (JsonArray) object);
		}

		/**
		 * Visits the given FIFO operation.
		 * 
		 * @param name
		 *            name of the instruction as indicated in
		 *            {@link IRConstants}. Expected to be the name of a
		 *            hasTokens, peek, read, write.
		 * @param location
		 *            the location
		 * @param target
		 *            the target variable
		 * @param port
		 *            the source port
		 * @param numTokens
		 *            the number of tokens
		 * @param array
		 *            the target JSON array
		 */
		private void visitFifoOperation(String name, Location location,
				Variable target, Port port, int numTokens, JsonArray array) {
			JsonArray instr = new JsonArray();
			array.add(instr);

			instr.add(new JsonPrimitive(name));
			instr.add(writeLocation(location));

			JsonArray body = new JsonArray();
			instr.add(body);

			body.add(writeVariable(target));
			body.add(new JsonPrimitive(port.getName()));
			body.add(new JsonPrimitive(numTokens));
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
			JsonArray array = (JsonArray) args[0];
			for (Instruction instruction : node) {
				instruction.accept(instrWriter, array);
			}
		}

		@Override
		public void visit(IfNode node, Object... args) {
			JsonArray array = (JsonArray) args[0];

			JsonArray ifArray = new JsonArray();
			array.add(ifArray);

			ifArray.add(new JsonPrimitive(IRConstants.NAME_IF));
			ifArray.add(writeLocation(node.getLocation()));

			JsonArray body = new JsonArray();
			ifArray.add(body);

			body.add(writeExpression(node.getValue()));
			body.add(writeNodes(node.getThenNodes()));
			body.add(writeNodes(node.getElseNodes()));

			// FIXME need to modify this weird stuff in JSON format
			JsonArray joinArray = new JsonArray();
			array.add(joinArray);

			joinArray.add(new JsonPrimitive(IRConstants.NAME_JOIN));
			joinArray.add(writeLocation(node.getLocation()));

			JsonArray phiArray = new JsonArray();
			joinArray.add(phiArray);
			node.getJoinNode().accept(this, phiArray);
		}

		@Override
		public void visit(WhileNode node, Object... args) {
			JsonArray array = (JsonArray) args[0];

			// header
			JsonArray whileArray = new JsonArray();
			array.add(whileArray);

			whileArray.add(new JsonPrimitive(IRConstants.NAME_WHILE));
			whileArray.add(writeLocation(node.getLocation()));

			JsonArray body = new JsonArray();
			whileArray.add(body);

			body.add(writeExpression(node.getValue()));

			// creates the join instruction
			JsonArray joinArray = new JsonArray();

			joinArray.add(new JsonPrimitive(IRConstants.NAME_JOIN));
			joinArray.add(writeLocation(node.getLocation()));

			// with all the phis
			JsonArray phiArray = new JsonArray();
			joinArray.add(phiArray);
			node.getJoinNode().accept(this, phiArray);

			// transforms all the other nodes
			JsonArray nodesArray = writeNodes(node.getNodes());

			// FIXME in the IR the join node of a while is its first node...
			JsonArray bodyArray = new JsonArray();
			bodyArray.add(joinArray);
			for (int i = 0; i < nodesArray.size(); i++) {
				bodyArray.add(nodesArray.get(i));
			}
			body.add(bodyArray);
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
			// FIXME change JSON format back to using integer size
			Expression expr = new IntExpr(type.getSize());
			array.add(writeExpression(expr));
			return array;
		}

		@Override
		public Object interpret(TypeList type) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(TypeList.NAME));
			// FIXME change JSON format back to using integer size
			Expression expr = new IntExpr(type.getSize());
			array.add(writeExpression(expr));
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
			// FIXME change JSON format back to using integer size
			Expression expr = new IntExpr(type.getSize());
			array.add(writeExpression(expr));
			return array;
		}

		@Override
		public Object interpret(TypeVoid type) {
			return new JsonPrimitive(TypeVoid.NAME);
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

	public void write(String outputDir, boolean prettyPrint)
			throws OrccException {
		OutputStream os;
		try {
			os = new FileOutputStream(outputDir + File.separator
					+ actor.getName() + ".json");
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}

		try {
			JsonObject obj = writeActor();
			if (prettyPrint) {
				// use readeable form
				// os.write(obj.toString(2).getBytes("UTF-8"));
			} else {
				// use compact form
				os.write(obj.toString().getBytes("UTF-8"));
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		} finally {
			try {
				os.close();
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
		for (Entry<Port, Integer> entry : pattern.entrySet()) {
			JsonArray patternArray = new JsonArray();
			array.add(patternArray);

			patternArray.add(new JsonPrimitive(entry.getKey().getName()));
			patternArray.add(new JsonPrimitive(entry.getValue().intValue()));
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
		obj.add(KEY_PARAMETERS, writeParameters(actor.getParameters()));
		obj.add(KEY_INPUTS, writePorts(actor.getInputs()));
		obj.add(KEY_OUTPUTS, writePorts(actor.getOutputs()));
		obj.add(KEY_STATE_VARS, writeStateVariables(actor.getStateVars()));
		obj.add(KEY_PROCEDURES, writeProcedures(actor.getProcs()));

		obj.add(KEY_ACTIONS, writeActions(actor.getActions()));
		obj.add(KEY_INITIALIZES, writeActions(actor.getInitializes()));

		array = writeActionScheduler(actor.getActionScheduler());
		obj.add(IRConstants.KEY_ACTION_SCHED, array);
		return obj;
	}

	private JsonElement writeConstant(Object obj) {
		if (obj instanceof Boolean) {
			return new JsonPrimitive((Boolean) obj);
		} else if (obj instanceof Long) {
			return new JsonPrimitive((Long) obj);
		} else if (obj instanceof Float) {
			return new JsonPrimitive((Float) obj);
		} else if (obj instanceof String) {
			return new JsonPrimitive((String) obj);
		} else if (obj instanceof List<?>) {
			List<?> list = (List<?>) obj;
			JsonArray array = new JsonArray();
			for (Object o : list) {
				array.add(writeConstant(o));
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
	private JsonArray writeExpression(Expression expression) {
		return (JsonArray) expression.accept(exprWriter);
	}

	/**
	 * Serializes the given list of expressions as JSON array whose each member
	 * is set to the result of {@link #writeExpression(Expression)}.
	 * 
	 * @param expressions
	 *            a list of expressions
	 * @return a JSON array
	 */
	private JsonArray writeExpressions(List<Expression> expressions) {
		JsonArray array = new JsonArray();
		for (Expression expression : expressions) {
			array.add(writeExpression(expression));
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
	 * Serializes the given variable declaration to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private JsonArray writeLocalVariable(LocalVariable variable) {
		JsonArray array = new JsonArray();

		JsonArray details = new JsonArray();
		array.add(details);
		details.add(new JsonPrimitive(variable.getBaseName()));
		details.add(new JsonPrimitive(variable.isAssignable()));
		details.add(new JsonPrimitive(variable.getIndex()));

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
	private JsonArray writeLocalVariables(OrderedMap<String, Variable> variables) {
		JsonArray array = new JsonArray();
		for (Variable variable : variables) {
			array.add(writeLocalVariable((LocalVariable) variable));
		}
		return array;
	}

	private JsonArray writeLocation(Location location) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(location.getStartLine()));
		array.add(new JsonPrimitive(location.getStartColumn()));
		array.add(new JsonPrimitive(location.getEndColumn()));

		return array;
	}

	/**
	 * Writes the given nodes.
	 * 
	 * @param nodes
	 *            a list of nodes
	 * @return a JSON array
	 */
	private JsonArray writeNodes(List<CFGNode> nodes) {
		JsonArray array = new JsonArray();
		for (CFGNode node : nodes) {
			node.accept(nodeWriter, array);
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
	private JsonArray writeParameter(StateVariable variable) {
		JsonArray variableArray = new JsonArray();

		JsonArray details = new JsonArray();
		variableArray.add(details);

		details.add(new JsonPrimitive(variable.getName()));
		details.add(new JsonPrimitive(variable.isAssignable()));

		variableArray.add(writeLocation(variable.getLocation()));
		variableArray.add(writeType(variable.getType()));

		return variableArray;
	}

	/**
	 * Writes the given ordered map of state variables.
	 * 
	 * @param variables
	 *            an ordered map of variables
	 * @return a JSON array
	 */
	private JsonArray writeParameters(
			OrderedMap<String, ? extends Variable> variables) {
		JsonArray array = new JsonArray();
		for (Variable variable : variables) {
			array.add(writeParameter((StateVariable) variable));
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
		array.add(new JsonPrimitive(procedure.isExternal()));
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

	/**
	 * Serializes the given variable declaration to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private JsonArray writeStateVariable(StateVariable variable) {
		JsonArray array = new JsonArray();

		// variable
		JsonArray variableArray = new JsonArray();
		array.add(variableArray);

		JsonArray details = new JsonArray();
		variableArray.add(details);

		details.add(new JsonPrimitive(variable.getName()));
		details.add(new JsonPrimitive(variable.isAssignable()));

		variableArray.add(writeLocation(variable.getLocation()));
		variableArray.add(writeType(variable.getType()));

		Object constant = variable.getConstantValue();
		if (constant == null) {
			array.add(null);
		} else {
			JsonElement constantValue = writeConstant(constant);
			array.add(constantValue);
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
	private JsonArray writeStateVariables(
			OrderedMap<String, StateVariable> variables) {
		JsonArray array = new JsonArray();
		for (StateVariable variable : variables) {
			array.add(writeStateVariable(variable));
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
	private JsonElement writeType(Type type) {
		return (JsonElement) type.accept(typeWriter);
	}

	/**
	 * Serializes the given variable to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 */
	private JsonArray writeVariable(Variable variable) {
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

}
