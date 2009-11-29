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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.consts.BoolConst;
import net.sf.orcc.ir.consts.ConstantInterpreter;
import net.sf.orcc.ir.consts.IntConst;
import net.sf.orcc.ir.consts.ListConst;
import net.sf.orcc.ir.consts.StringConst;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.type.BoolType;
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
	 * This class defines a constant writer that serializes a constant to JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ConstantWriter implements ConstantInterpreter {

		@Override
		public Object interpret(BoolConst constant, Object... args) {
			return constant.getValue();
		}

		@Override
		public Object interpret(IntConst constant, Object... args) {
			return constant.getValue();
		}

		@Override
		public Object interpret(ListConst constant, Object... args) {
			JSONArray array = new JSONArray();
			List<Constant> constants = constant.getValue();
			for (Constant cst : constants) {
				array.put(cst.accept(this));
			}
			return array;
		}

		@Override
		public Object interpret(StringConst constant, Object... args) {
			return constant.getValue();
		}

	}

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
			array.put(IRConstants.BINARY_EXPR);
			array.put(expr.getOp().getText());
			array.put(expr.getE1().accept(this));
			array.put(expr.getE2().accept(this));
			array.put(expr.getUnderlyingType().accept(new TypeWriter()));
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
			array.put(IRConstants.UNARY_EXPR);
			array.put(expr.getOp().getText());
			array.put(expr.getExpr().accept(this));
			array.put(expr.getUnderlyingType().accept(new TypeWriter()));
			return array;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			array.put(writeVariable(expr.getVar().getVariable()));
			return array;
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
		public Object interpret(IntType type) {
			JSONArray array = new JSONArray();
			array.put(IntType.NAME);
			Expression expr = type.getSize();
			try {
				array.put(writeExpression(expr));
			} catch (OrccException e) {
				e.printStackTrace();
			}
			return array;
		}

		@Override
		public Object interpret(ListType type) {
			JSONArray array = new JSONArray();
			array.put(ListType.NAME);
			Expression expr = type.getSize();
			try {
				array.put(writeExpression(expr));
				array.put(writeType(type.getElementType()));
			} catch (OrccException e) {
				e.printStackTrace();
			}
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
			Expression expr = type.getSize();
			try {
				array.put(writeExpression(expr));
			} catch (OrccException e) {
				e.printStackTrace();
			}
			return array;
		}

		@Override
		public Object interpret(VoidType type) {
			return VoidType.NAME;
		}

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

	public void write(String outputDir) throws OrccException {
		try {
			OutputStream os = new FileOutputStream(outputDir + File.separator
					+ actor.getName() + "_2.json");
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
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private JSONArray writeAction(Action action) throws OrccException {
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
	private JSONArray writeActionPattern(Map<Port, Integer> pattern) {
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
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private JSONArray writeActions(List<Action> actions) throws OrccException {
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

	private JSONObject writeActor() throws JSONException, OrccException {
		JSONObject obj = new JSONObject();
		JSONArray array;

		obj.put(KEY_SOURCE_FILE, actor.getFile());
		obj.put(KEY_NAME, actor.getName());
		obj.put(IRConstants.KEY_INPUTS, writePorts(actor.getInputs()));
		obj.put(IRConstants.KEY_OUTPUTS, writePorts(actor.getOutputs()));
		obj.put(IRConstants.KEY_STATE_VARS, writeStateVariables(actor
				.getStateVars()));
		obj.put(IRConstants.KEY_PROCEDURES, writeProcedures(actor.getProcs()));

		obj.put(IRConstants.KEY_ACTIONS, writeActions(actor.getActions()));
		obj.put(IRConstants.KEY_INITIALIZES, writeActions(actor
				.getInitializes()));

		array = writeActionScheduler(actor.getActionScheduler());
		obj.put(IRConstants.KEY_ACTION_SCHED, array);
		return obj;
	}

	/**
	 * Writes the given expression as JSON.
	 * 
	 * @param expression
	 *            an expression
	 * @return a JSON array
	 * @throws OrccException
	 */
	private JSONArray writeExpression(Expression expression)
			throws OrccException {
		ExpressionWriter writer = new ExpressionWriter();
		return (JSONArray) expression.accept(writer);
	}

	private JSONArray writeFSM(FSM fsm) {
		return null;
	}

	/**
	 * Serializes the given variable declaration to JSON.
	 * 
	 * @param variable
	 *            a variable
	 * @return
	 * @throws OrccException
	 */
	private JSONArray writeLocalVariable(LocalVariable variable)
			throws OrccException {
		JSONArray array = new JSONArray();

		JSONArray details = new JSONArray();
		array.put(details);
		details.put(variable.getBaseName());
		details.put(variable.isAssignable());
		details.put(42); // TODO remove when front-end done
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
	 * @throws OrccException
	 */
	private JSONArray writeLocalVariables(OrderedMap<Variable> variables)
			throws OrccException {
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

		// TODO remove when frontend done
		array.put(42);
		array.put(location.getEndColumn());

		return array;
	}

	/**
	 * Writes the given port.
	 * 
	 * @param port
	 *            a port
	 * @return a JSON array
	 * @throws OrccException
	 */
	private JSONArray writePort(Port port) throws OrccException {
		JSONArray array = new JSONArray();
		array.put(writeLocation(port.getLocation()));
		array.put(writeType(port.getType()));
		array.put(port.getName());
		return array;
	}

	private JSONArray writePorts(OrderedMap<Port> ports) throws OrccException {
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
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private JSONArray writeProcedure(Procedure procedure) throws OrccException {
		JSONArray array = new JSONArray();
		array.put(procedure.getName());
		array.put(procedure.isExternal());
		array.put(writeLocation(procedure.getLocation()));
		array.put(writeType(procedure.getReturnType()));

		array.put(writeLocalVariables(procedure.getParameters()));
		array.put(writeLocalVariables(procedure.getLocals()));
		array.put(new JSONArray()); // nodes

		return array;
	}

	/**
	 * Serializes the given procedures to a JSON array.
	 * 
	 * @param procedures
	 *            an ordered map of procedures
	 * @return a JSON array
	 * @throws OrccException
	 */
	private JSONArray writeProcedures(OrderedMap<Procedure> procedures)
			throws OrccException {
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
	 * @throws OrccException
	 */
	private JSONArray writeStateVariable(StateVariable variable)
			throws OrccException {
		JSONArray array = new JSONArray();

		// variable
		JSONArray variableArray = new JSONArray();
		array.put(variableArray);

		JSONArray details = new JSONArray();
		variableArray.put(details);

		details.put(variable.getName());
		details.put(variable.isAssignable());
		details.put(42); // TODO remove when front-end done
		details.put(JSONObject.NULL);
		details.put(0);

		variableArray.put(writeLocation(variable.getLocation()));
		variableArray.put(writeType(variable.getType()));

		Constant constant = variable.getConstantValue();
		if (constant == null) {
			array.put(JSONObject.NULL);
		} else {
			Object constantValue = variable.getConstantValue().accept(
					new ConstantWriter());
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
	 * @throws OrccException
	 */
	private JSONArray writeStateVariables(OrderedMap<Variable> variables)
			throws OrccException {
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
	 * @throws OrccException
	 */
	private Object writeType(Type type) throws OrccException {
		return type.accept(new TypeWriter());
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
