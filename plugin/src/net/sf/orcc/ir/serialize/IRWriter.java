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
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
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
	 * This class defines an expression writer that serializes an expression to
	 * JSON.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ExpressionWriter implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args)
				throws OrccException {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			array.put(IRConstants.BINARY_EXPR);
			array.put(writeBinaryOp(expr.getOp()));
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
		public Object interpret(ListExpr expr, Object... args)
				throws OrccException {
			throw new OrccException("unsupported expression: List");
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			array.put(expr.getValue());
			return array;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args)
				throws OrccException {
			JSONArray array = new JSONArray();
			array.put(writeLocation(expr.getLocation()));
			array.put(IRConstants.UNARY_EXPR);
			array.put(writeUnaryOp(expr.getOp()));
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

		private String writeBinaryOp(BinaryOp op) {
			switch (op) {
			case BITAND:
				return IRConstants.BOP_BAND;
			case BITOR:
				return IRConstants.BOP_BOR;
			case BITXOR:
				return IRConstants.BOP_BXOR;
			case DIV:
				return IRConstants.BOP_DIV;
			case DIV_INT:
				return IRConstants.BOP_DIV_INT;
			case EQ:
				return IRConstants.BOP_EQ;
			case EXP:
				return IRConstants.BOP_EXP;
			case GE:
				return IRConstants.BOP_GE;
			case GT:
				return IRConstants.BOP_GT;
			case LE:
				return IRConstants.BOP_LE;
			case LOGIC_AND:
				return IRConstants.BOP_LAND;
			case LOGIC_OR:
				return IRConstants.BOP_LOR;
			case LT:
				return IRConstants.BOP_LT;
			case MINUS:
				return IRConstants.BOP_MINUS;
			case MOD:
				return IRConstants.BOP_MOD;
			case NE:
				return IRConstants.BOP_NE;
			case PLUS:
				return IRConstants.BOP_PLUS;
			case SHIFT_LEFT:
				return IRConstants.BOP_SHIFT_LEFT;
			case SHIFT_RIGHT:
				return IRConstants.BOP_SHIFT_RIGHT;
			case TIMES:
				return IRConstants.BOP_TIMES;
			default:
				// never happens
				throw new IllegalArgumentException();
			}
		}

		private String writeUnaryOp(UnaryOp op) {
			switch (op) {
			case BITNOT:
				return IRConstants.UOP_BNOT;
			case LOGIC_NOT:
				return IRConstants.UOP_LNOT;
			case MINUS:
				return IRConstants.UOP_MINUS;
			case NUM_ELTS:
				return IRConstants.UOP_NUM_ELTS;
			default:
				// never happens
				throw new IllegalArgumentException();
			}
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
		obj.put(IRConstants.KEY_STATE_VARS, new JSONArray());
		obj.put(IRConstants.KEY_PROCEDURES, new JSONArray());

		array = writeActions(actor.getActions());
		obj.put(IRConstants.KEY_ACTIONS, array);
		obj.put(IRConstants.KEY_INITIALIZES, new JSONArray());

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

		array.put(new JSONArray()); // parameters
		array.put(new JSONArray()); // local variables
		array.put(new JSONArray()); // nodes

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
			array.put(local.hasSuffix() ? local.getSuffix() : JSONObject.NULL);
			array.put(local.getIndex());
		}

		return array;
	}

	// private JSONArray writeVarDef(VarDef varDef) throws OrccException {
	// JSONArray array = new JSONArray();
	//
	// JSONArray details = new JSONArray();
	// details.put(varDef.getName());
	// details.put(varDef.isAssignable());
	// details.put(varDef.isGlobal());
	// if (varDef.hasSuffix()) {
	// details.put(varDef.getSuffix());
	// } else {
	// details.put((Object) null);
	// }
	// details.put(varDef.getIndex());
	//
	// array.put(details);
	// array.put(writeLocation(varDef.getLoc()));
	// array.put(writeType(varDef.getType()));
	//
	// return array;
	// }

}
