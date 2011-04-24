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
import static net.sf.orcc.ir.serialize.IRConstants.KEY_LOCATION;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Transition;
import net.sf.orcc.ir.Transitions;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrSwitch;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class IRWriter extends IrSwitch<JsonElement> {

	private static JsonArray writeLocation(Location location) {
		if (location == null) {
			location = IrFactory.eINSTANCE.createLocation();
		}

		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(location.getStartLine()));
		array.add(new JsonPrimitive(location.getStartColumn()));
		array.add(new JsonPrimitive(location.getEndColumn()));

		return array;
	}

	/**
	 * Serializes the given variable to JSON.
	 * 
	 * @param var
	 *            a variable
	 * @return
	 */
	private static JsonArray writeVariable(Var var) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(var.getName()));
		array.add(new JsonPrimitive(var.getIndex()));
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

	@Override
	public JsonArray caseExprBinary(ExprBinary expr) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(EXPR_BINARY));
		array.add(new JsonPrimitive(expr.getOp().getText()));
		array.add(doSwitch(expr.getE1()));
		array.add(doSwitch(expr.getE2()));
		array.add(doSwitch(expr.getType()));
		return array;
	}

	@Override
	public JsonPrimitive caseExprBool(ExprBool expr) {
		return new JsonPrimitive(expr.isValue());
	}

	@Override
	public JsonPrimitive caseExprFloat(ExprFloat expr) {
		return new JsonPrimitive(expr.getValue());
	}

	@Override
	public JsonPrimitive caseExprInt(ExprInt expr) {
		return new JsonPrimitive(expr.getValue());
	}

	@Override
	public JsonArray caseExprList(ExprList expr) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(EXPR_LIST));
		for (Expression expression : expr.getValue()) {
			array.add(doSwitch(expression));
		}
		return array;
	}

	@Override
	public JsonPrimitive caseExprString(ExprString expr) {
		return new JsonPrimitive(expr.getValue());
	}

	@Override
	public JsonArray caseExprUnary(ExprUnary expr) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(EXPR_UNARY));
		array.add(new JsonPrimitive(expr.getOp().getText()));
		array.add(doSwitch(expr.getExpr()));
		array.add(doSwitch(expr.getType()));
		return array;
	}

	@Override
	public JsonArray caseExprVar(ExprVar expr) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(EXPR_VAR));
		array.add(writeVariable(expr.getUse().getVariable()));
		return array;
	}

	@Override
	public JsonArray caseInstAssign(InstAssign assign) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(INSTR_ASSIGN));
		array.add(writeLocation(assign.getLocation()));

		array.add(writeVariable(assign.getTarget().getVariable()));
		array.add(doSwitch(assign.getValue()));

		return array;
	}

	@Override
	public JsonArray caseInstCall(InstCall call) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(INSTR_CALL));
		array.add(writeLocation(call.getLocation()));

		array.add(new JsonPrimitive(call.getProcedure().getName()));
		array.add(writeExpressions(call.getParameters()));

		array.add(call.hasResult() ? writeVariable(call.getTarget()
				.getVariable()) : null);

		return array;
	}

	@Override
	public JsonArray caseInstLoad(InstLoad load) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(INSTR_LOAD));
		array.add(writeLocation(load.getLocation()));

		array.add(writeVariable(load.getTarget().getVariable()));
		array.add(writeVariable(load.getSource().getVariable()));
		array.add(writeExpressions(load.getIndexes()));

		return array;
	}

	@Override
	public JsonArray caseInstPhi(InstPhi phi) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(INSTR_PHI));
		array.add(writeLocation(phi.getLocation()));

		array.add(writeVariable(phi.getTarget().getVariable()));
		array.add(writeExpressions(phi.getValues()));

		return array;
	}

	@Override
	public JsonArray caseInstReturn(InstReturn returnInst) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(INSTR_RETURN));
		array.add(writeLocation(returnInst.getLocation()));

		Expression value = returnInst.getValue();
		if (value == null) {
			array.add(null);
		} else {
			array.add(doSwitch(value));
		}

		return array;
	}

	@Override
	public JsonArray caseInstSpecific(InstSpecific specific) {
		throw new OrccRuntimeException(
				"IR writer cannot write specific instructions");
	}

	@Override
	public JsonArray caseInstStore(InstStore store) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(INSTR_STORE));
		array.add(writeLocation(store.getLocation()));

		array.add(writeVariable(store.getTarget().getVariable()));
		array.add(writeExpressions(store.getIndexes()));
		array.add(doSwitch(store.getValue()));

		return array;
	}

	@Override
	public JsonArray caseNodeBlock(NodeBlock node) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(NODE_BLOCK));
		array.add(writeLocation(node.getLocation()));

		array.add(writeIntructions(node.getInstructions()));

		return array;
	}

	@Override
	public JsonArray caseNodeIf(NodeIf node) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(NODE_IF));
		array.add(writeLocation(node.getLocation()));

		array.add(doSwitch(node.getCondition()));
		array.add(writeNodes(node.getThenNodes()));
		array.add(writeNodes(node.getElseNodes()));

		array.add(doSwitch(node.getJoinNode()));

		return array;
	}

	@Override
	public JsonArray caseNodeWhile(NodeWhile node) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(NODE_WHILE));
		array.add(writeLocation(node.getLocation()));

		array.add(doSwitch(node.getCondition()));
		array.add(writeNodes(node.getNodes()));
		array.add(doSwitch(node.getJoinNode()));

		return array;
	}

	@Override
	public JsonPrimitive caseTypeBool(TypeBool type) {
		return new JsonPrimitive(TypeBool.NAME);
	}

	@Override
	public JsonPrimitive caseTypeFloat(TypeFloat type) {
		return new JsonPrimitive(TypeFloat.NAME);
	}

	@Override
	public JsonArray caseTypeInt(TypeInt type) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(TypeInt.NAME));
		array.add(new JsonPrimitive(type.getSize()));
		return array;
	}

	@Override
	public JsonArray caseTypeList(TypeList type) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(TypeList.NAME));
		array.add(doSwitch(type.getSizeExpr()));
		array.add(doSwitch(type.getType()));
		return array;
	}

	@Override
	public JsonPrimitive caseTypeString(TypeString type) {
		return new JsonPrimitive(TypeString.NAME);
	}

	@Override
	public JsonArray caseTypeUint(TypeUint type) {
		JsonArray array = new JsonArray();
		array.add(new JsonPrimitive(TypeUint.NAME));
		array.add(new JsonPrimitive(type.getSize()));
		return array;
	}

	@Override
	public JsonPrimitive caseTypeVoid(TypeVoid type) {
		return new JsonPrimitive(TypeVoid.NAME);
	}

	public void write(IFolder outputDir, boolean prettyPrint)
			throws OrccException {
		Writer writer;
		try {
			if (!outputDir.exists()) {
				outputDir.create(true, false, null);
			}

			String folderName = OrccUtil.getFolder(actor);
			IPath path = new Path(folderName);
			IFolder folder = outputDir.getFolder(path);
			if (!folder.exists()) {
				folder = outputDir;
				for (int i = 0; i < path.segmentCount(); i++) {
					folder = folder.getFolder(path.segment(i));
					if (!folder.exists()) {
						folder.create(true, false, null);
					}
				}
			}

			IFile file = outputDir.getFile(new Path(OrccUtil.getFile(actor)
					+ ".json"));
			if (file.exists()) {
				file.delete(true, null);
			}

			// write output as UTF-8
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			writer = new BufferedWriter(new OutputStreamWriter(os));

			try {
				JsonObject obj = writeActor();
				GsonBuilder builder = new GsonBuilder();
				builder.disableHtmlEscaping();
				if (prettyPrint) {
					builder.setPrettyPrinting();
				}

				Gson gson = builder.create();
				gson.toJson(obj, writer);
				writer.flush();
				file.create(new ByteArrayInputStream(os.toByteArray()), 0, null);
			} finally {
				// because some GSON methods may throw a RuntimeException
				try {
					writer.close();
				} catch (IOException e) {
					throw new OrccException("I/O error", e);
				}
			}
		} catch (CoreException e) {
			throw new OrccException("I/O error", e);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
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
		array.add(writeActionPattern(action.getPeekPattern()));
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
			patternArray.add(writeLocalVariable(pattern.getVariable(port)));
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
	private JsonArray writeActionScheduler(Actor actor) {
		JsonArray array = new JsonArray();

		JsonArray actions = new JsonArray();
		array.add(actions);
		for (Action action : actor.getActionsOutsideFsm()) {
			actions.add(writeActionTag(action.getTag()));
		}

		if (actor.hasFsm()) {
			array.add(writeFSM(actor.getFsm()));
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
		for (String identifier : tag.getIdentifiers()) {
			array.add(new JsonPrimitive(identifier));
		}

		return array;
	}

	private JsonObject writeActor() {
		JsonObject obj = new JsonObject();
		JsonArray array;

		obj.addProperty(KEY_SOURCE_FILE, actor.getFile());
		obj.add(KEY_LOCATION, writeLocation(actor.getLocation()));
		obj.addProperty(KEY_NAME, actor.getName());
		obj.add(KEY_PARAMETERS, writeGlobalVariables(actor.getParameters()));
		obj.add(KEY_INPUTS, writePorts(actor.getInputs()));
		obj.add(KEY_OUTPUTS, writePorts(actor.getOutputs()));
		obj.addProperty(KEY_NATIVE, actor.isNative());
		obj.add(KEY_STATE_VARS, writeGlobalVariables(actor.getStateVars()));
		obj.add(KEY_PROCEDURES, writeProcedures(actor.getProcs()));

		obj.add(KEY_ACTIONS, writeActions(actor.getActions()));
		obj.add(KEY_INITIALIZES, writeActions(actor.getInitializes()));

		array = writeActionScheduler(actor);
		obj.add(KEY_ACTION_SCHED, array);
		return obj;
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
			array.add(doSwitch(expression));
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
		for (State state : fsm.getStates()) {
			stateArray.add(new JsonPrimitive(state.getName()));
		}

		JsonArray transitionsArray = new JsonArray();
		array.add(transitionsArray);
		for (Transitions transitions : fsm.getTransitions()) {
			JsonArray transitionArray = new JsonArray();
			transitionsArray.add(transitionArray);

			transitionArray.add(new JsonPrimitive(transitions.getSourceState()
					.getName()));

			JsonArray actionsArray = new JsonArray();
			transitionArray.add(actionsArray);

			for (Transition transition : transitions.getList()) {
				JsonArray actionArray = new JsonArray();
				actionsArray.add(actionArray);

				actionArray
						.add(writeActionTag(transition.getAction().getTag()));
				actionArray.add(new JsonPrimitive(transition.getState()
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
	private JsonArray writeGlobalVariable(Var variable) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(variable.getName()));
		array.add(new JsonPrimitive(variable.isAssignable()));
		array.add(writeLocation(variable.getLocation()));
		array.add(doSwitch(variable.getType()));

		Expression constant = variable.getInitialValue();
		if (constant == null) {
			array.add(null);
		} else {
			array.add(doSwitch(constant));
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
	private JsonArray writeGlobalVariables(List<Var> variables) {
		JsonArray array = new JsonArray();
		for (Var variable : variables) {
			array.add(writeGlobalVariable(variable));
		}
		return array;
	}

	private JsonArray writeIntructions(List<Instruction> instructions) {
		JsonArray array = new JsonArray();
		for (Instruction instruction : instructions) {
			array.add(doSwitch(instruction));
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
	private JsonArray writeLocalVariable(Var variable) {
		JsonArray array = new JsonArray();

		array.add(new JsonPrimitive(variable.getName()));
		array.add(new JsonPrimitive(variable.isAssignable()));
		array.add(new JsonPrimitive(variable.getIndex()));
		array.add(writeLocation(variable.getLocation()));
		array.add(doSwitch(variable.getType()));

		return array;
	}

	/**
	 * Writes the given ordered map of local variables.
	 * 
	 * @param variables
	 *            an ordered map of variables
	 * @return a JSON array
	 */
	private JsonArray writeLocalVariables(List<Var> variables) {
		JsonArray array = new JsonArray();
		for (Var variable : variables) {
			array.add(writeLocalVariable(variable));
		}
		return array;
	}

	/**
	 * Writes the given nodes.
	 * 
	 * @param nodes
	 *            a list of nodes
	 * @return a JSON array
	 */
	private JsonArray writeNodes(List<Node> nodes) {
		JsonArray array = new JsonArray();
		for (Node node : nodes) {
			array.add(doSwitch(node));
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
		array.add(doSwitch(port.getType()));
		array.add(new JsonPrimitive(port.getName()));
		return array;
	}

	private JsonArray writePorts(List<Port> ports) {
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
		array.add(doSwitch(procedure.getReturnType()));

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
	private JsonArray writeProcedures(List<Procedure> procedures) {
		JsonArray array = new JsonArray();
		for (Procedure procedure : procedures) {
			array.add(writeProcedure(procedure));
		}
		return array;
	}

}
