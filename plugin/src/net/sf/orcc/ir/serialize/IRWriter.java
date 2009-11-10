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

import static net.sf.orcc.ir.IrConstants.KEY_NAME;
import static net.sf.orcc.ir.IrConstants.KEY_SOURCE_FILE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.IrConstants;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.ActionScheduler;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.FSM;
import net.sf.orcc.ir.actor.Tag;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
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
		obj.put(IrConstants.KEY_INPUTS, writePorts(actor.getInputs()));
		obj.put(IrConstants.KEY_OUTPUTS, writePorts(actor.getOutputs()));
		obj.put(IrConstants.KEY_STATE_VARS, new JSONArray());
		obj.put(IrConstants.KEY_PROCEDURES, new JSONArray());

		array = writeActions(actor.getActions());
		obj.put(IrConstants.KEY_ACTIONS, array);
		obj.put(IrConstants.KEY_INITIALIZES, new JSONArray());

		array = writeActionScheduler(actor.getActionScheduler());
		obj.put(IrConstants.KEY_ACTION_SCHED, array);
		return obj;
	}

	private JSONArray writeExpr(IExpr expr) {
		JSONArray array = new JSONArray();
		array.put(writeLocation(expr.getLocation()));
		switch (expr.getType()) {
		case IExpr.BINARY:
			break;
		case IExpr.BOOLEAN:
			array.put(((BooleanExpr) expr).getValue());
			break;
		case IExpr.INT:
			array.put(((IntExpr) expr).getValue());
			break;
		case IExpr.LIST:
			break;
		case IExpr.STRING:
			array.put(((StringExpr) expr).getValue());
			break;
		case IExpr.UNARY:
			break;
		case IExpr.VAR:
			break;
		}

		return array;
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

	private Object writeType(IType type) throws OrccException {
		if (type.getType() == IType.BOOLEAN) {
			return BoolType.NAME;
		} else if (type.getType() == IType.STRING) {
			return StringType.NAME;
		} else if (type.getType() == IType.VOID) {
			return VoidType.NAME;
		} else {
			JSONArray array = new JSONArray();
			if (type.getType() == IType.INT) {
				array.put(IntType.NAME);
				IExpr expr = ((IntType) type).getSize();
				array.put(writeExpr(expr));
			} else if (type.getType() == IType.UINT) {
				array.put(UintType.NAME);
				IExpr expr = ((UintType) type).getSize();
				array.put(writeExpr(expr));
			} else if (type.getType() == IType.LIST) {
				array.put(ListType.NAME);
				IExpr expr = ((ListType) type).getSize();
				array.put(writeExpr(expr));
				array.put(writeType(((ListType) type).getElementType()));
			} else {
				throw new OrccException("Invalid type definition: "
						+ type.toString());
			}

			return array;
		}
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
