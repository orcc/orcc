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

import static net.sf.orcc.ir.IrConstants.BINARY_EXPR;
import static net.sf.orcc.ir.IrConstants.BOP_BAND;
import static net.sf.orcc.ir.IrConstants.BOP_BOR;
import static net.sf.orcc.ir.IrConstants.BOP_BXOR;
import static net.sf.orcc.ir.IrConstants.BOP_DIV;
import static net.sf.orcc.ir.IrConstants.BOP_DIV_INT;
import static net.sf.orcc.ir.IrConstants.BOP_EQ;
import static net.sf.orcc.ir.IrConstants.BOP_EXP;
import static net.sf.orcc.ir.IrConstants.BOP_GE;
import static net.sf.orcc.ir.IrConstants.BOP_GT;
import static net.sf.orcc.ir.IrConstants.BOP_LAND;
import static net.sf.orcc.ir.IrConstants.BOP_LE;
import static net.sf.orcc.ir.IrConstants.BOP_LOR;
import static net.sf.orcc.ir.IrConstants.BOP_LT;
import static net.sf.orcc.ir.IrConstants.BOP_MINUS;
import static net.sf.orcc.ir.IrConstants.BOP_MOD;
import static net.sf.orcc.ir.IrConstants.BOP_NE;
import static net.sf.orcc.ir.IrConstants.BOP_PLUS;
import static net.sf.orcc.ir.IrConstants.BOP_SHIFT_LEFT;
import static net.sf.orcc.ir.IrConstants.BOP_SHIFT_RIGHT;
import static net.sf.orcc.ir.IrConstants.BOP_TIMES;
import static net.sf.orcc.ir.IrConstants.KEY_ACTIONS;
import static net.sf.orcc.ir.IrConstants.KEY_ACTION_SCHED;
import static net.sf.orcc.ir.IrConstants.KEY_INITIALIZES;
import static net.sf.orcc.ir.IrConstants.KEY_INPUTS;
import static net.sf.orcc.ir.IrConstants.KEY_NAME;
import static net.sf.orcc.ir.IrConstants.KEY_OUTPUTS;
import static net.sf.orcc.ir.IrConstants.KEY_PROCEDURES;
import static net.sf.orcc.ir.IrConstants.KEY_SOURCE_FILE;
import static net.sf.orcc.ir.IrConstants.KEY_STATE_VARS;
import static net.sf.orcc.ir.IrConstants.NAME_ASSIGN;
import static net.sf.orcc.ir.IrConstants.NAME_CALL;
import static net.sf.orcc.ir.IrConstants.NAME_EMPTY;
import static net.sf.orcc.ir.IrConstants.NAME_HAS_TOKENS;
import static net.sf.orcc.ir.IrConstants.NAME_IF;
import static net.sf.orcc.ir.IrConstants.NAME_JOIN;
import static net.sf.orcc.ir.IrConstants.NAME_LOAD;
import static net.sf.orcc.ir.IrConstants.NAME_PEEK;
import static net.sf.orcc.ir.IrConstants.NAME_READ;
import static net.sf.orcc.ir.IrConstants.NAME_RETURN;
import static net.sf.orcc.ir.IrConstants.NAME_STORE;
import static net.sf.orcc.ir.IrConstants.NAME_WHILE;
import static net.sf.orcc.ir.IrConstants.NAME_WRITE;
import static net.sf.orcc.ir.IrConstants.UNARY_EXPR;
import static net.sf.orcc.ir.IrConstants.UOP_BNOT;
import static net.sf.orcc.ir.IrConstants.UOP_LNOT;
import static net.sf.orcc.ir.IrConstants.UOP_MINUS;
import static net.sf.orcc.ir.IrConstants.UOP_NUM_ELTS;
import static net.sf.orcc.ir.IrConstants.VAR_EXPR;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.IConst;
import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.INode;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.ActionScheduler;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.FSM;
import net.sf.orcc.ir.actor.StateVariable;
import net.sf.orcc.ir.actor.Tag;
import net.sf.orcc.ir.consts.AbstractConst;
import net.sf.orcc.ir.consts.BoolConst;
import net.sf.orcc.ir.consts.IntConst;
import net.sf.orcc.ir.consts.ListConst;
import net.sf.orcc.ir.consts.StringConst;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.nodes.HasTokensNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.nodes.WriteNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * This class defines a parser that loads an actor in IR form serialized in JSON
 * in memory.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IRParser {

	private Map<Tag, Action> actions;

	private String file;

	private OrderedMap<Port> inputs;

	private boolean isInitialize;

	private OrderedMap<Port> outputs;

	private INode previousNode;

	private OrderedMap<Procedure> procs;

	private List<Action> untaggedActions;

	private Scope<Variable> variables;

	private Action getAction(JSONArray array) throws JSONException {
		if (array.length() == 0) {
			// removes the first untagged action found
			return untaggedActions.remove(0);
		} else {
			Tag tag = new Tag();
			for (int i = 0; i < array.length(); i++) {
				tag.add(array.getString(i));
			}

			return actions.get(tag);
		}
	}

	private Variable getVariable(JSONArray array) throws JSONException,
			OrccException {
		String name = array.getString(0);
		Integer suffix = array.isNull(1) ? null : array.getInt(1);
		int index = array.getInt(2);

		// retrieve the variable definition
		Variable varDef = variables.get(stringOfVar(name, suffix, index));
		if (varDef == null) {
			throw new OrccException("unknown variable: " + name + suffix + "_"
					+ index);
		}
		return varDef;
	}

	private Action parseAction(JSONArray array) throws JSONException,
			OrccException {
		JSONArray tagArray = array.getJSONArray(0);
		Tag tag = new Tag();
		for (int i = 0; i < tagArray.length(); i++) {
			tag.add(tagArray.getString(i));
		}

		Map<Port, Integer> ip = parsePattern(inputs, array.getJSONArray(1));
		Map<Port, Integer> op = parsePattern(outputs, array.getJSONArray(2));

		Procedure scheduler = parseProc(array.getJSONArray(3), false);
		Procedure body = parseProc(array.getJSONArray(4), false);

		Action action = new Action(body.getLocation(), tag, ip, op, scheduler,
				body);
		putAction(tag, action);
		return action;
	}

	private List<Action> parseActions(JSONArray array) throws JSONException,
			OrccException {
		List<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < array.length(); i++) {
			actions.add(parseAction(array.getJSONArray(i)));
		}

		return actions;
	}

	private ActionScheduler parseActionScheduler(JSONArray array)
			throws JSONException, OrccException {
		JSONArray actionArray = array.getJSONArray(0);
		List<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < actionArray.length(); i++) {
			actions.add(getAction(actionArray.getJSONArray(i)));
		}

		FSM fsm = null;
		if (!array.isNull(1)) {
			fsm = parseFSM(array.getJSONArray(1));
		}
		return new ActionScheduler(actions, fsm);
	}

	/**
	 * Parses the input stream that this parser's constructor was given as JSON,
	 * and returns an actor from it.
	 * 
	 * @return An {@link Actor}.
	 * @throws JSONException
	 */
	public Actor parseActor(InputStream in) throws OrccException {
		try {
			actions = new HashMap<Tag, Action>();
			procs = new OrderedMap<Procedure>();
			untaggedActions = new ArrayList<Action>();
			variables = new Scope<Variable>();

			JSONTokener tokener = new JSONTokener(new InputStreamReader(in));
			JSONObject obj = new JSONObject(tokener);

			file = obj.getString(KEY_SOURCE_FILE);
			String name = obj.getString(KEY_NAME);
			inputs = parsePorts(obj.getJSONArray(KEY_INPUTS));
			outputs = parsePorts(obj.getJSONArray(KEY_OUTPUTS));

			JSONArray array = obj.getJSONArray(KEY_STATE_VARS);
			OrderedMap<Variable> stateVars = parseStateVars(array);

			array = obj.getJSONArray(KEY_PROCEDURES);
			for (int i = 0; i < array.length(); i++) {
				parseProc(array.getJSONArray(i), true);
			}

			array = obj.getJSONArray(KEY_ACTIONS);
			List<Action> actions = parseActions(array);

			// a bit dirty, this one...
			// when isInitialize is true, don't put actions in hash tables.
			isInitialize = true;
			array = obj.getJSONArray(KEY_INITIALIZES);
			List<Action> initializes = parseActions(array);

			array = obj.getJSONArray(KEY_ACTION_SCHED);
			ActionScheduler sched = parseActionScheduler(array);

			// no parameters at this point.
			OrderedMap<Variable> parameters = new OrderedMap<Variable>();

			Actor actor = new Actor(name, file, parameters, inputs, outputs,
					stateVars, procs, actions, initializes, sched, null);
			in.close();
			return actor;
		} catch (JSONException e) {
			throw new OrccException("JSON error", e);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	private AssignVarNode parseAssignVarNode(int id, Location loc,
			JSONArray array) throws JSONException, OrccException {
		Variable var = getVariable(array.getJSONArray(0));
		IExpr value = parseExpr(array.getJSONArray(1));
		return new AssignVarNode(id, loc, (LocalVariable) var, value);
	}

	private BinaryExpr parseBinaryExpr(Location location, JSONArray array)
			throws JSONException, OrccException {
		String name = array.getString(0);
		IExpr e1 = parseExpr(array.getJSONArray(1));
		IExpr e2 = parseExpr(array.getJSONArray(2));
		IType type = parseType(array.get(3));
		BinaryOp op = null;

		if (name.equals(BOP_BAND)) {
			op = BinaryOp.BAND;
		} else if (name.equals(BOP_BOR)) {
			op = BinaryOp.BOR;
		} else if (name.equals(BOP_BXOR)) {
			op = BinaryOp.BXOR;
		} else if (name.equals(BOP_DIV)) {
			op = BinaryOp.DIV;
		} else if (name.equals(BOP_DIV_INT)) {
			op = BinaryOp.DIV_INT;
		} else if (name.equals(BOP_EQ)) {
			op = BinaryOp.EQ;
		} else if (name.equals(BOP_EXP)) {
			op = BinaryOp.EXP;
		} else if (name.equals(BOP_GE)) {
			op = BinaryOp.GE;
		} else if (name.equals(BOP_GT)) {
			op = BinaryOp.GT;
		} else if (name.equals(BOP_LAND)) {
			op = BinaryOp.LAND;
		} else if (name.equals(BOP_LE)) {
			op = BinaryOp.LE;
		} else if (name.equals(BOP_LOR)) {
			op = BinaryOp.LOR;
		} else if (name.equals(BOP_LT)) {
			op = BinaryOp.LT;
		} else if (name.equals(BOP_MINUS)) {
			op = BinaryOp.MINUS;
		} else if (name.equals(BOP_MOD)) {
			op = BinaryOp.MOD;
		} else if (name.equals(BOP_NE)) {
			op = BinaryOp.NE;
		} else if (name.equals(BOP_PLUS)) {
			op = BinaryOp.PLUS;
		} else if (name.equals(BOP_SHIFT_LEFT)) {
			op = BinaryOp.SHIFT_LEFT;
		} else if (name.equals(BOP_SHIFT_RIGHT)) {
			op = BinaryOp.SHIFT_RIGHT;
		} else if (name.equals(BOP_TIMES)) {
			op = BinaryOp.TIMES;
		} else {
			throw new OrccException("Invalid binary operator: " + name);
		}

		return new BinaryExpr(location, e1, op, e2, type);
	}

	private CallNode parseCallNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		LocalVariable res = null;
		String procName = array.getString(0);
		if (!array.isNull(1)) {
			res = (LocalVariable) getVariable(array.getJSONArray(1));
		}

		List<IExpr> parameters = parseExprs(array.getJSONArray(2));
		return new CallNode(id, loc, res, procs.get(procName), parameters);
	}

	/**
	 * Parses the given object as a constant.
	 * 
	 * @param obj
	 *            A YAML-encoded constant that may be a {@link Boolean}, an
	 *            {@link Integer}, a {@link List} or a {@link String}.
	 * @return An {@link AbstractConst} created from the given object.
	 * @throws JSONException
	 */
	private IConst parseConstant(Object obj) throws JSONException,
			OrccException {
		IConst constant = null;

		if (obj instanceof Boolean) {
			constant = new BoolConst((Boolean) obj);
		} else if (obj instanceof Integer) {
			constant = new IntConst((Integer) obj);
		} else if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			List<IConst> cstList = new ArrayList<IConst>();
			for (int i = 0; i < array.length(); i++) {
				cstList.add(parseConstant(array.get(i)));
			}
			constant = new ListConst(cstList);
		} else if (obj instanceof String) {
			constant = new StringConst((String) obj);
		} else {
			throw new OrccException("Unknown constant: " + obj);
		}

		return constant;
	}

	private EmptyNode parseEmptyNode(int nodeId, Location location) {
		return new EmptyNode(nodeId, location);
	}

	private IExpr parseExpr(JSONArray array) throws JSONException,
			OrccException {
		Location location = parseLocation(array.getJSONArray(0));
		Object obj = array.get(1);
		IExpr expr = null;

		if (obj instanceof Boolean) {
			expr = new BooleanExpr(location, (Boolean) obj);
		} else if (obj instanceof Integer) {
			expr = new IntExpr(location, (Integer) obj);
		} else if (obj instanceof String) {
			expr = new StringExpr(location, (String) obj);
		} else if (obj instanceof JSONArray) {
			array = (JSONArray) obj;
			String name = array.getString(0);
			if (name.equals(VAR_EXPR)) {
				Use var = parseVarUse(array.getJSONArray(1));
				expr = new VarExpr(location, var);
			} else if (name.equals(UNARY_EXPR)) {
				return parseUnaryExpr(location, array.getJSONArray(1));
			} else if (name.equals(BINARY_EXPR)) {
				return parseBinaryExpr(location, array.getJSONArray(1));
			} else {
				throw new OrccException("Invalid expression kind: " + name);
			}
		} else {
			throw new OrccException("Invalid expression: " + obj);
		}

		return expr;
	}

	private List<IExpr> parseExprs(JSONArray array) throws JSONException,
			OrccException {
		List<IExpr> exprs = new ArrayList<IExpr>();
		for (int i = 0; i < array.length(); i++) {
			exprs.add(parseExpr(array.getJSONArray(i)));
		}

		return exprs;
	}

	private FSM parseFSM(JSONArray array) throws JSONException, OrccException {
		String initialState = array.getString(0);
		FSM fsm = new FSM();
		JSONArray stateArray = array.getJSONArray(1);
		for (int i = 0; i < stateArray.length(); i++) {
			fsm.addState(stateArray.getString(i));
		}

		// set the initial state *after* initializing the states to get a
		// prettier order
		fsm.setInitialState(initialState);

		JSONArray transitionsArray = array.getJSONArray(2);
		for (int i = 0; i < transitionsArray.length(); i++) {
			JSONArray transitionArray = transitionsArray.getJSONArray(i);
			String source = transitionArray.getString(0);
			JSONArray targetsArray = transitionArray.getJSONArray(1);
			for (int j = 0; j < targetsArray.length(); j++) {
				JSONArray targetArray = targetsArray.getJSONArray(j);
				Action action = getAction(targetArray.getJSONArray(0));
				String target = targetArray.getString(1);
				fsm.addTransition(source, target, action);
			}
		}

		return fsm;
	}

	private HasTokensNode parseHasTokensNode(int id, Location loc,
			JSONArray array) throws JSONException, OrccException {
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(0));
		String fifoName = array.getString(1);
		Port port = inputs.get(fifoName);
		int numTokens = array.getInt(2);
		return new HasTokensNode(id, loc, port, numTokens, varDef);
	}

	private IfNode parseIfNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		IExpr condition = parseExpr(array.getJSONArray(0));
		List<INode> thenNodes = parseNodes(array.getJSONArray(1));
		List<INode> elseNodes = parseNodes(array.getJSONArray(2));

		return new IfNode(id, loc, condition, thenNodes, elseNodes, null);
	}

	private JoinNode parseJoinNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		JoinNode join = new JoinNode(id, loc);
		List<PhiAssignment> phis = join.getPhiAssignments();
		for (int i = 0; i < array.length(); i++) {
			phis.add(parsePhiNode(array.getJSONArray(i)));
		}

		return join;
	}

	private LoadNode parseLoadNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		LocalVariable target = (LocalVariable) getVariable(array
				.getJSONArray(0));
		Use source = parseVarUse(array.getJSONArray(1));
		List<IExpr> indexes = parseExprs(array.getJSONArray(2));

		return new LoadNode(id, loc, target, source, indexes);
	}

	private Location parseLocation(JSONArray array) throws JSONException {
		if (array.length() == 4) {
			int startLine = array.getInt(0);
			int startCol = array.getInt(1);
			// TODO to be removed when Java frontend done.
			// int endLine = array.getInt(2);
			int endCol = array.getInt(3);

			return new Location(startLine, startCol, endCol);
		} else {
			return new Location();
		}
	}

	private INode parseNode(JSONArray array) throws JSONException,
			OrccException {
		String name = array.getString(0);
		int id = array.getInt(1);
		Location loc = parseLocation(array.getJSONArray(2));
		INode node = null;

		if (name.equals(NAME_ASSIGN)) {
			node = parseAssignVarNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_CALL)) {
			node = parseCallNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_EMPTY)) {
			node = parseEmptyNode(id, loc);
		} else if (name.equals(NAME_HAS_TOKENS)) {
			node = parseHasTokensNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_IF)) {
			node = parseIfNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_JOIN)) {
			node = parseJoinNode(id, loc, array.getJSONArray(3));

			// if the previous node is an If, then the join node we just parsed
			// is the If's join node.
			// We set it, and just pretend we didn't parse anything (otherwise
			// the join would be referenced once in the if, and once in the node
			// list)
			if (previousNode instanceof IfNode) {
				((IfNode) previousNode).setJoinNode((JoinNode) node);
				node = null;
			}
		} else if (name.equals(NAME_LOAD)) {
			node = parseLoadNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_PEEK)) {
			node = parsePeekNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_READ)) {
			node = parseReadNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_RETURN)) {
			node = parseReturnNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_STORE)) {
			node = parseStoreNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_WHILE)) {
			node = parseWhileNode(id, loc, array.getJSONArray(3));
		} else if (name.equals(NAME_WRITE)) {
			node = parseWriteNode(id, loc, array.getJSONArray(3));
		} else {
			throw new OrccException("Invalid node definition: " + name);
		}

		previousNode = node;
		return node;
	}

	private List<INode> parseNodes(JSONArray array) throws JSONException,
			OrccException {
		List<INode> nodes = new ArrayList<INode>();
		for (int i = 0; i < array.length(); i++) {
			INode node = parseNode(array.getJSONArray(i));
			if (node != null) {
				nodes.add(node);
			}
		}

		return nodes;
	}

	private Map<Port, Integer> parsePattern(OrderedMap<Port> ports,
			JSONArray array) throws JSONException, OrccException {
		Map<Port, Integer> pattern = new HashMap<Port, Integer>();
		for (int i = 0; i < array.length(); i++) {
			JSONArray patternArray = array.getJSONArray(i);
			Port port = ports.get(patternArray.getString(0));
			int numTokens = patternArray.getInt(1);
			pattern.put(port, numTokens);
		}

		return pattern;
	}

	private PeekNode parsePeekNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		String fifoName = array.getString(0);
		Port port = inputs.get(fifoName);
		int numTokens = array.getInt(1);
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(2));
		return new PeekNode(id, loc, port, numTokens, varDef);
	}

	private PhiAssignment parsePhiNode(JSONArray array) throws JSONException,
			OrccException {
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(0));
		List<Use> vars = new ArrayList<Use>();
		array = array.getJSONArray(1);
		for (int i = 0; i < array.length(); i++) {
			vars.add(parseVarUse(array.getJSONArray(i)));
		}

		return new PhiAssignment(varDef, vars);
	}

	/**
	 * Parses the given list as a list of ports.
	 * 
	 * @param list
	 *            A list of YAML-encoded {@link LocalVariable}s.
	 * @return A {@link List}&lt;{@link LocalVariable}&gt;.
	 * @throws JSONException
	 */
	private OrderedMap<Port> parsePorts(JSONArray array) throws JSONException,
			OrccException {
		OrderedMap<Port> ports = new OrderedMap<Port>();
		for (int i = 0; i < array.length(); i++) {
			JSONArray port = array.getJSONArray(i);

			Location location = parseLocation(port.getJSONArray(0));
			IType type = parseType(port.get(1));
			String name = port.getString(2);

			Port po = new Port(location, type, name);
			ports.add(file, location, name, po);
		}

		return ports;
	}

	/**
	 * Parses a procedure and optionally adds it to the {@link #procs} map.
	 * 
	 * @param array
	 *            a JSON array
	 * @param register
	 *            if true, add this procedure to the {@link #procs} map.
	 * @return the procedure parsed
	 * @throws JSONException
	 * @throws OrccException
	 */
	private Procedure parseProc(JSONArray array, boolean register)
			throws JSONException, OrccException {
		JSONArray array1 = array.getJSONArray(0);
		String name = array1.getString(0);
		boolean external = array1.getBoolean(1);

		Location location = parseLocation(array.getJSONArray(1));
		IType returnType = parseType(array.get(2));
		variables = new Scope<Variable>(variables);
		OrderedMap<Variable> parameters = variables;
		parseVarDefs(array.getJSONArray(3));
		variables = new Scope<Variable>(variables);
		OrderedMap<Variable> locals = variables;
		parseVarDefs(array.getJSONArray(4));

		List<INode> nodes = parseNodes(array.getJSONArray(5));

		// go back to previous scope
		variables = variables.getParent();

		Procedure proc = new Procedure(name, external, location, returnType,
				parameters, locals, nodes);
		if (register) {
			procs.add(file, location, name, proc);
		}
		return proc;
	}

	private ReadNode parseReadNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		String fifoName = array.getString(0);
		Port port = inputs.get(fifoName);
		int numTokens = array.getInt(1);
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(2));
		return new ReadNode(id, loc, port, numTokens, varDef);
	}

	private ReturnNode parseReturnNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		IExpr expr = parseExpr(array);
		return new ReturnNode(id, loc, expr);
	}

	/**
	 * Parses the given list as a list of state variables. A
	 * {@link StateVariable} is a {@link LocalVariable} with an optional
	 * reference to an {@link AbstractConst} that contain the variable's initial
	 * value.
	 * 
	 * @param list
	 *            A list of YAML-encoded {@link LocalVariable}.
	 * @return A {@link List}&lt;{@link StateVariable}&gt;.
	 * @throws JSONException
	 */
	private OrderedMap<Variable> parseStateVars(JSONArray array)
			throws JSONException, OrccException {
		OrderedMap<Variable> stateVars = new OrderedMap<Variable>();
		for (int i = 0; i < array.length(); i++) {
			JSONArray stateArray = array.getJSONArray(i);

			JSONArray varDefArray = stateArray.getJSONArray(0);
			JSONArray details = varDefArray.getJSONArray(0);
			String name = details.getString(0);
			boolean assignable = details.getBoolean(1);

			Location location = parseLocation(varDefArray.getJSONArray(1));
			IType type = parseType(varDefArray.get(2));

			IConst init = null;
			if (!stateArray.isNull(1)) {
				init = parseConstant(stateArray.get(1));
			}

			StateVariable stateVar = new StateVariable(location, type, name,
					assignable, init);
			stateVars.add(file, location, name, stateVar);

			// register the state variable
			variables.add(file, location, name + "_0", stateVar);
		}

		return stateVars;
	}

	private StoreNode parseStoreNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		Use target = parseVarUse(array.getJSONArray(0));
		List<IExpr> indexes = parseExprs(array.getJSONArray(1));
		IExpr value = parseExpr(array.getJSONArray(2));

		return new StoreNode(id, loc, target, indexes, value);
	}

	/**
	 * Parses the given object as a type definition.
	 * 
	 * @param obj
	 *            A YAML-encoded type definition. This is either a
	 *            {@link String} for simple types (bool, String, void) or a
	 *            {@link List} for types with parameters (int, uint, List).
	 * @return An {@link IType}.
	 * @throws JSONException
	 */
	private IType parseType(Object obj) throws JSONException, OrccException {
		IType type = null;

		if (obj instanceof String) {
			String name = (String) obj;
			if (name.equals(BoolType.NAME)) {
				type = new BoolType();
			} else if (name.equals(StringType.NAME)) {
				type = new StringType();
			} else if (name.equals(VoidType.NAME)) {
				type = new VoidType();
			} else {
				throw new OrccException("Unknown type: " + name);
			}
		} else if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			String name = array.getString(0);
			if (name.equals(IntType.NAME)) {
				IExpr expr = parseExpr(array.getJSONArray(1));
				type = new IntType(expr);
			} else if (name.equals(UintType.NAME)) {
				IExpr expr = parseExpr(array.getJSONArray(1));
				type = new UintType(expr);
			} else if (name.equals(ListType.NAME)) {
				IExpr expr = parseExpr(array.getJSONArray(1));
				IType subType = parseType(array.get(2));
				type = new ListType(expr, subType);
			} else {
				throw new OrccException("Unknown type: " + name);
			}
		} else {
			throw new OrccException("Invalid type definition: "
					+ obj.toString());
		}

		return type;
	}

	private UnaryExpr parseUnaryExpr(Location location, JSONArray array)
			throws JSONException, OrccException {
		String name = array.getString(0);
		IExpr expr = parseExpr(array.getJSONArray(1));
		IType type = parseType(array.get(2));
		UnaryOp op = null;

		if (name.equals(UOP_BNOT)) {
			op = UnaryOp.BNOT;
		} else if (name.equals(UOP_LNOT)) {
			op = UnaryOp.LNOT;
		} else if (name.equals(UOP_MINUS)) {
			op = UnaryOp.MINUS;
		} else if (name.equals(UOP_NUM_ELTS)) {
			op = UnaryOp.NUM_ELTS;
		} else {
			throw new OrccException("Invalid unary operator: " + name);
		}

		return new UnaryExpr(location, op, expr, type);
	}

	/**
	 * Returns a variable definition using objects returned by the given
	 * iterator.
	 * 
	 * @param it
	 *            An iterator on YAML-encoded objects that hold information
	 *            about a variable definition.
	 * @return A {@link LocalVariable}.
	 * @throws JSONException
	 */
	private LocalVariable parseVarDef(JSONArray array) throws JSONException,
			OrccException {
		JSONArray details = array.getJSONArray(0);
		String name = details.getString(0);
		boolean assignable = details.getBoolean(1);
		Integer suffix = details.isNull(3) ? null : details.getInt(3);
		int index = details.getInt(4);

		Location loc = parseLocation(array.getJSONArray(1));
		IType type = parseType(array.get(2));

		INode node = null;

		LocalVariable varDef = new LocalVariable(assignable, index, loc, name,
				node, suffix, type);

		// register the variable definition
		variables.add(file, loc, varDef.getName(), varDef);

		return varDef;
	}

	private void parseVarDefs(JSONArray array) throws JSONException,
			OrccException {
		for (int i = 0; i < array.length(); i++) {
			parseVarDef(array.getJSONArray(i));
		}
	}

	private Use parseVarUse(JSONArray array) throws JSONException,
			OrccException {
		Variable varDef = getVariable(array.getJSONArray(0));
		return new Use(varDef);
	}

	private WhileNode parseWhileNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		IExpr condition = parseExpr(array.getJSONArray(0));
		List<INode> nodes = parseNodes(array.getJSONArray(1));
		JoinNode joinNode = (JoinNode) nodes.remove(0);
		return new WhileNode(id, loc, condition, nodes, joinNode);
	}

	private WriteNode parseWriteNode(int id, Location loc, JSONArray array)
			throws JSONException, OrccException {
		String fifoName = array.getString(0);
		Port port = outputs.get(fifoName);
		int numTokens = array.getInt(1);
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(2));
		return new WriteNode(id, loc, port, numTokens, varDef);
	}

	private void putAction(Tag tag, Action action) {
		if (!isInitialize) {
			if (tag.isEmpty()) {
				untaggedActions.add(action);
			} else {
				actions.put(tag, action);
			}
		}
	}

	private String stringOfVar(String name, Integer suffix, int index) {
		return name + (suffix == null ? "" : suffix) + "_" + index;
	}

}
