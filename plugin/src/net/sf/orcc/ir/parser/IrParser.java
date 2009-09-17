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
package net.sf.orcc.ir.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.ActionScheduler;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.FSM;
import net.sf.orcc.ir.actor.NextStateInfo;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.StateVar;
import net.sf.orcc.ir.actor.Transition;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.consts.AbstractConst;
import net.sf.orcc.ir.consts.BoolConst;
import net.sf.orcc.ir.consts.IntConst;
import net.sf.orcc.ir.consts.ListConst;
import net.sf.orcc.ir.consts.StringConst;
import net.sf.orcc.ir.expr.AbstractExpr;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
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
import net.sf.orcc.ir.type.AbstractType;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * This class provides a parser for orcc's IR.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IrParser {

	private static final String BINARY_EXPR = "2 op";

	private static final String BOP_BAND = "bitand";

	private static final String BOP_BOR = "bitor";

	private static final String BOP_BXOR = "bitxor";

	private static final String BOP_DIV = "div";

	private static final String BOP_DIV_INT = "intDiv";

	private static final String BOP_EQ = "equal";

	private static final String BOP_EXP = "exp";

	private static final String BOP_GE = "ge";

	private static final String BOP_GT = "gt";

	private static final String BOP_LAND = "and";

	private static final String BOP_LE = "le";

	private static final String BOP_LOR = "or";

	private static final String BOP_LT = "lt";

	private static final String BOP_MINUS = "minus";

	private static final String BOP_MOD = "mod";

	private static final String BOP_NE = "ne";

	private static final String BOP_PLUS = "plus";

	private static final String BOP_SHIFT_LEFT = "lshift";

	private static final String BOP_SHIFT_RIGHT = "rshift";

	private static final String BOP_TIMES = "mul";

	private static final String KEY_ACTION_SCHED = "action scheduler";

	private final static String KEY_ACTIONS = "actions";

	private final static String KEY_INITIALIZES = "initializes";

	private final static String KEY_INPUTS = "inputs";

	private final static String KEY_NAME = "name";

	private final static String KEY_OUTPUTS = "outputs";

	private final static String KEY_PROCEDURES = "procedures";

	private final static String KEY_STATE_VARS = "state variables";

	private static final String NAME_ASSIGN = "assign";

	private static final String NAME_CALL = "call";

	private static final String NAME_EMPTY = "empty";

	private static final String NAME_HAS_TOKENS = "hasTokens";

	private static final String NAME_IF = "if";

	private static final String NAME_JOIN = "join";

	private static final String NAME_LOAD = "load";

	private static final String NAME_PEEK = "peek";

	private static final String NAME_READ = "read";

	private static final String NAME_RETURN = "return";

	private static final String NAME_STORE = "store";

	private static final String NAME_WHILE = "while";

	private static final String NAME_WRITE = "write";

	private static final String UNARY_EXPR = "1 op";

	private static final String UOP_BNOT = "bitnot";

	private static final String UOP_LNOT = "not";

	private static final String UOP_MINUS = "unaryMinus";

	private static final String UOP_NUM_ELTS = "numElements";

	private static final String VAR_EXPR = "var";

	private Map<List<String>, Action> actions;

	private String file;

	private boolean isInitialize;

	private AbstractNode previousNode;

	private Map<String, Procedure> procs;

	private List<Action> untaggedActions;

	private Map<String, VarDef> varDefs;

	private Action getAction(JSONArray array) throws JSONException {
		if (array.length() == 0) {
			// removes the first untagged action found
			return untaggedActions.remove(0);
		} else {
			List<String> tagList = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				tagList.add(array.getString(i));
			}

			return actions.get(tagList);
		}
	}

	private VarDef getVarDef(JSONArray array) throws JSONException {
		String name = array.getString(0);
		Integer suffix = array.isNull(1) ? null : array.getInt(1);
		int index = array.getInt(2);

		// retrieve the variable definition
		VarDef varDef = varDefs.get(stringOfVar(name, suffix, index));
		if (varDef == null) {
			throw new IrParseException("unknown variable: " + name + suffix
					+ "_" + index);
		}
		return varDef;
	}

	private Action parseAction(JSONArray array) throws JSONException {
		JSONArray tagArray = array.getJSONArray(0);
		List<String> tag = new ArrayList<String>();
		for (int i = 0; i < tagArray.length(); i++) {
			tag.add(tagArray.getString(i));
		}

		Map<VarDef, Integer> ip = parsePattern(array.getJSONArray(1));
		Map<VarDef, Integer> op = parsePattern(array.getJSONArray(2));

		Procedure scheduler = parseProc(array.getJSONArray(3));
		Procedure body = parseProc(array.getJSONArray(4));

		Action action = new Action(tag, ip, op, scheduler, body);
		putAction(tag, action);
		return action;
	}

	private List<Action> parseActions(JSONArray array) throws JSONException {
		List<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < array.length(); i++) {
			actions.add(parseAction(array.getJSONArray(i)));
		}

		return actions;
	}

	private ActionScheduler parseActionScheduler(JSONArray array)
			throws JSONException {
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
	 * Parses the input stream that this parser's constructor was given as YAML,
	 * and returns an actor from it.
	 * 
	 * @return An {@link Actor}.
	 * @throws JSONException
	 */
	public Actor parseActor(InputStream in) throws JSONException {
		actions = new HashMap<List<String>, Action>();
		procs = new HashMap<String, Procedure>();
		untaggedActions = new ArrayList<Action>();
		varDefs = new HashMap<String, VarDef>();

		JSONTokener tokener = new JSONTokener(new InputStreamReader(in));
		JSONObject obj = new JSONObject(tokener);

		file = obj.getString("source file");
		String name = obj.getString(KEY_NAME);
		List<VarDef> inputs = parsePorts(obj.getJSONArray(KEY_INPUTS));
		List<VarDef> outputs = parsePorts(obj.getJSONArray(KEY_OUTPUTS));

		JSONArray array = obj.getJSONArray(KEY_STATE_VARS);
		List<StateVar> stateVars = parseStateVars(array);

		array = obj.getJSONArray(KEY_PROCEDURES);
		List<Procedure> procs = parseProcs(array);

		array = obj.getJSONArray(KEY_ACTIONS);
		List<Action> actions = parseActions(array);

		// a bit dirty, this one...
		// when isInitialize is true, don't put actions in hash tables.
		isInitialize = true;
		array = obj.getJSONArray(KEY_INITIALIZES);
		List<Action> initializes = parseActions(array);

		array = obj.getJSONArray(KEY_ACTION_SCHED);
		ActionScheduler sched = parseActionScheduler(array);

		Actor actor = new Actor(name, inputs, outputs, stateVars, procs,
				actions, initializes, sched, null);

		try {
			in.close();
		} catch (IOException e) {
			throw new IrParseException("Could not close input stream", e);
		}

		return actor;
	}

	private AssignVarNode parseAssignVarNode(int id, Location loc,
			JSONArray array) throws JSONException {
		VarDef var = getVarDef(array.getJSONArray(0));
		AbstractExpr value = parseExpr(array.getJSONArray(1));
		return new AssignVarNode(id, loc, var, value);
	}

	private BinaryExpr parseBinaryExpr(Location location, JSONArray array)
			throws JSONException {
		String name = array.getString(0);
		AbstractExpr e1 = parseExpr(array.getJSONArray(1));
		AbstractExpr e2 = parseExpr(array.getJSONArray(2));
		AbstractType type = parseType(array.get(3));
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
			throw new IrParseException("Invalid binary operator: " + name);
		}

		return new BinaryExpr(location, e1, op, e2, type);
	}

	private CallNode parseCallNode(int id, Location loc, JSONArray array)
			throws JSONException {
		VarDef res = null;
		String procName = array.getString(0);
		if (!array.isNull(1)) {
			res = getVarDef(array.getJSONArray(1));
		}

		List<AbstractExpr> parameters = parseExprs(array.getJSONArray(2));
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
	private AbstractConst parseConstant(Object obj) throws JSONException {
		AbstractConst constant = null;

		if (obj instanceof Boolean) {
			constant = new BoolConst((Boolean) obj);
		} else if (obj instanceof Integer) {
			constant = new IntConst((Integer) obj);
		} else if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			List<AbstractConst> cstList = new ArrayList<AbstractConst>();
			for (int i = 0; i < array.length(); i++) {
				cstList.add(parseConstant(array.get(i)));
			}
			constant = new ListConst(cstList);
		} else if (obj instanceof String) {
			constant = new StringConst((String) obj);
		} else {
			throw new IrParseException("Unknown constant: " + obj);
		}

		return constant;
	}

	private EmptyNode parseEmptyNode(int nodeId, Location location) {
		return new EmptyNode(nodeId, location);
	}

	private AbstractExpr parseExpr(JSONArray array) throws JSONException {
		Location location = parseLocation(array.getJSONArray(0));
		Object obj = array.get(1);
		AbstractExpr expr = null;

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
				VarUse var = parseVarUse(array.getJSONArray(1));
				expr = new VarExpr(location, var);
			} else if (name.equals(UNARY_EXPR)) {
				return parseUnaryExpr(location, array.getJSONArray(1));
			} else if (name.equals(BINARY_EXPR)) {
				return parseBinaryExpr(location, array.getJSONArray(1));
			} else {
				throw new IrParseException("Invalid expression kind: " + name);
			}
		} else {
			throw new IrParseException("Invalid expression: " + obj);
		}

		return expr;
	}

	private List<AbstractExpr> parseExprs(JSONArray array) throws JSONException {
		List<AbstractExpr> exprs = new ArrayList<AbstractExpr>();
		for (int i = 0; i < array.length(); i++) {
			exprs.add(parseExpr(array.getJSONArray(i)));
		}

		return exprs;
	}

	private FSM parseFSM(JSONArray array) throws JSONException {
		String initialState = array.getString(0);
		List<String> states = new ArrayList<String>();
		JSONArray stateArray = array.getJSONArray(1);
		for (int i = 0; i < stateArray.length(); i++) {
			states.add(stateArray.getString(i));
		}

		List<Transition> transitions = parseFSMTransitions(array
				.getJSONArray(2));
		return new FSM(initialState, states, transitions);
	}

	private List<NextStateInfo> parseFSMNextStates(JSONArray array)
			throws JSONException {
		List<NextStateInfo> nextStates = new ArrayList<NextStateInfo>();
		for (int i = 0; i < array.length(); i++) {
			JSONArray stateArray = array.getJSONArray(i);
			Action action = getAction(stateArray.getJSONArray(0));
			String targetState = stateArray.getString(1);
			nextStates.add(new NextStateInfo(action, targetState));
		}
		return nextStates;
	}

	private List<Transition> parseFSMTransitions(JSONArray array)
			throws JSONException {
		List<Transition> transitions = new ArrayList<Transition>();
		for (int i = 0; i < array.length(); i++) {
			JSONArray transitionArray = array.getJSONArray(i);

			String sourceState = transitionArray.getString(0);
			List<NextStateInfo> nextStates = parseFSMNextStates(transitionArray
					.getJSONArray(1));

			transitions.add(new Transition(sourceState, nextStates));
		}
		return transitions;
	}

	private HasTokensNode parseHasTokensNode(int id, Location loc,
			JSONArray array) throws JSONException {
		VarDef varDef = getVarDef(array.getJSONArray(0));
		String fifoName = array.getString(1);
		int numTokens = array.getInt(2);
		return new HasTokensNode(id, loc, fifoName, numTokens, varDef);
	}

	private IfNode parseIfNode(int id, Location loc, JSONArray array)
			throws JSONException {
		AbstractExpr condition = parseExpr(array.getJSONArray(0));
		List<AbstractNode> thenNodes = parseNodes(array.getJSONArray(1));
		List<AbstractNode> elseNodes = parseNodes(array.getJSONArray(2));

		return new IfNode(id, loc, condition, thenNodes, elseNodes, null);
	}

	private JoinNode parseJoinNode(int id, Location loc, JSONArray array)
			throws JSONException {
		List<PhiAssignment> phis = new ArrayList<PhiAssignment>();
		for (int i = 0; i < array.length(); i++) {
			phis.add(parsePhiNode(array.getJSONArray(i)));
		}
		return new JoinNode(id, loc, phis);
	}

	private LoadNode parseLoadNode(int id, Location loc, JSONArray array)
			throws JSONException {
		VarDef target = getVarDef(array.getJSONArray(0));
		VarUse source = parseVarUse(array.getJSONArray(1));
		List<AbstractExpr> indexes = parseExprs(array.getJSONArray(2));

		return new LoadNode(id, loc, target, source, indexes);
	}

	/**
	 * Parses the given list as a location.
	 * 
	 * @param list
	 *            A YAML-encoded list of objects that contain location
	 *            information.
	 * @return A {@link Location}.
	 * @throws JSONException
	 */
	private Location parseLocation(JSONArray array) throws JSONException {
		if (array.length() == 4) {
			int startLine = array.getInt(0);
			int startCol = array.getInt(1);
			int endLine = array.getInt(2);
			int endCol = array.getInt(3);

			return new Location(file, startLine, startCol, endLine, endCol);
		} else {
			return new Location();
		}
	}

	private AbstractNode parseNode(JSONArray array) throws JSONException {
		String name = array.getString(0);
		int id = array.getInt(1);
		Location loc = parseLocation(array.getJSONArray(2));
		AbstractNode node = null;

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
			throw new IrParseException("Invalid node definition: " + name);
		}

		previousNode = node;
		return node;
	}

	private List<AbstractNode> parseNodes(JSONArray array) throws JSONException {
		List<AbstractNode> nodes = new ArrayList<AbstractNode>();
		for (int i = 0; i < array.length(); i++) {
			AbstractNode node = parseNode(array.getJSONArray(i));
			if (node != null) {
				nodes.add(node);
			}
		}

		return nodes;
	}

	private Map<VarDef, Integer> parsePattern(JSONArray array)
			throws JSONException {
		Map<VarDef, Integer> pattern = new HashMap<VarDef, Integer>();
		for (int i = 0; i < array.length(); i++) {
			JSONArray patternArray = array.getJSONArray(i);
			VarDef port = getVarDef(patternArray.getJSONArray(0));
			int numTokens = patternArray.getInt(1);
			pattern.put(port, numTokens);
		}
		return pattern;
	}

	private PeekNode parsePeekNode(int id, Location loc, JSONArray array)
			throws JSONException {
		String fifoName = array.getString(0);
		int numTokens = array.getInt(1);
		VarDef varDef = getVarDef(array.getJSONArray(2));
		return new PeekNode(id, loc, fifoName, numTokens, varDef);
	}

	private PhiAssignment parsePhiNode(JSONArray array) throws JSONException {
		VarDef varDef = getVarDef(array.getJSONArray(0));
		List<VarUse> vars = new ArrayList<VarUse>();
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
	 *            A list of YAML-encoded {@link VarDef}s.
	 * @return A {@link List}&lt;{@link VarDef}&gt;.
	 * @throws JSONException
	 */
	private List<VarDef> parsePorts(JSONArray array) throws JSONException {
		List<VarDef> ports = new ArrayList<VarDef>();
		for (int i = 0; i < array.length(); i++) {
			ports.add(parseVarDef(array.getJSONArray(i)));
		}

		return ports;
	}

	private Procedure parseProc(JSONArray array) throws JSONException {
		JSONArray array1 = array.getJSONArray(0);
		String name = array1.getString(0);
		boolean external = array1.getBoolean(1);

		Location location = parseLocation(array.getJSONArray(1));
		AbstractType returnType = parseType(array.get(2));
		List<VarDef> parameters = parseVarDefs(array.getJSONArray(3));
		List<VarDef> locals = parseVarDefs(array.getJSONArray(4));

		List<AbstractNode> nodes = parseNodes(array.getJSONArray(5));

		Procedure proc = new Procedure(name, external, location, returnType,
				parameters, locals, nodes);
		procs.put(name, proc);
		return proc;
	}

	/**
	 * Parses the given list as a list of procedures.
	 * 
	 * @param list
	 *            A list of YAML-encoded {@link Procedure}s.
	 * @return A {@link List}&lt;{@link Procedure}&gt;.
	 * @throws JSONException
	 */
	private List<Procedure> parseProcs(JSONArray array) throws JSONException {
		List<Procedure> procs = new ArrayList<Procedure>();
		for (int i = 0; i < array.length(); i++) {
			procs.add(parseProc(array.getJSONArray(i)));
		}

		return procs;
	}

	private ReadNode parseReadNode(int id, Location loc, JSONArray array)
			throws JSONException {
		String fifoName = array.getString(0);
		int numTokens = array.getInt(1);
		VarDef varDef = getVarDef(array.getJSONArray(2));
		return new ReadNode(id, loc, fifoName, numTokens, varDef);
	}

	private List<VarUse> parseRefs(JSONArray array) {
		// TODO parse references
		List<VarUse> refs = new ArrayList<VarUse>();
		return refs;
	}

	private ReturnNode parseReturnNode(int id, Location loc, JSONArray array)
			throws JSONException {
		AbstractExpr expr = parseExpr(array);
		return new ReturnNode(id, loc, expr);
	}

	/**
	 * Parses the given list as a list of state variables. A {@link StateVar} is
	 * a {@link VarDef} with an optional reference to an {@link AbstractConst}
	 * that contain the variable's initial value.
	 * 
	 * @param list
	 *            A list of YAML-encoded {@link VarDef}.
	 * @return A {@link List}&lt;{@link StateVar}&gt;.
	 * @throws JSONException
	 */
	private List<StateVar> parseStateVars(JSONArray array) throws JSONException {
		List<StateVar> stateVars = new ArrayList<StateVar>();
		for (int i = 0; i < array.length(); i++) {
			JSONArray varDefArray = array.getJSONArray(i);
			VarDef def = parseVarDef(varDefArray.getJSONArray(0));
			AbstractConst init = null;
			if (!varDefArray.isNull(1)) {
				init = parseConstant(varDefArray.get(1));
			}

			StateVar stateVar = new StateVar(def, init);
			stateVars.add(stateVar);
		}

		return stateVars;
	}

	private StoreNode parseStoreNode(int id, Location loc, JSONArray array)
			throws JSONException {
		VarUse target = parseVarUse(array.getJSONArray(0));
		List<AbstractExpr> indexes = parseExprs(array.getJSONArray(1));
		AbstractExpr value = parseExpr(array.getJSONArray(2));

		return new StoreNode(id, loc, target, indexes, value);
	}

	/**
	 * Parses the given object as a type definition.
	 * 
	 * @param obj
	 *            A YAML-encoded type definition. This is either a
	 *            {@link String} for simple types (bool, String, void) or a
	 *            {@link List} for types with parameters (int, uint, List).
	 * @return An {@link AbstractType}.
	 * @throws JSONException
	 */
	private AbstractType parseType(Object obj) throws JSONException {
		AbstractType type = null;

		if (obj instanceof String) {
			String name = (String) obj;
			if (name.equals(BoolType.NAME)) {
				type = new BoolType();
			} else if (name.equals(StringType.NAME)) {
				type = new StringType();
			} else if (name.equals(VoidType.NAME)) {
				type = new VoidType();
			} else {
				throw new IrParseException("Unknown type: " + name);
			}
		} else if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			String name = array.getString(0);
			if (name.equals(IntType.NAME)) {
				int size = array.getInt(1);
				type = new IntType(size);
			} else if (name.equals(UintType.NAME)) {
				int size = array.getInt(1);
				type = new UintType(size);
			} else if (name.equals(ListType.NAME)) {
				int size = array.getInt(1);
				AbstractType subType = parseType(array.get(2));
				type = new ListType(size, subType);
			} else {
				throw new IrParseException("Unknown type: " + name);
			}
		} else {
			throw new IrParseException("Invalid type definition: "
					+ obj.toString());
		}

		return type;
	}

	private UnaryExpr parseUnaryExpr(Location location, JSONArray array)
			throws JSONException {
		String name = array.getString(0);
		AbstractExpr expr = parseExpr(array.getJSONArray(1));
		AbstractType type = parseType(array.get(2));
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
			throw new IrParseException("Invalid unary operator: " + name);
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
	 * @return A {@link VarDef}.
	 * @throws JSONException
	 */
	private VarDef parseVarDef(JSONArray array) throws JSONException {
		JSONArray details = array.getJSONArray(0);
		String name = details.getString(0);
		boolean assignable = details.getBoolean(1);
		boolean global = details.getBoolean(2);
		Integer suffix = details.isNull(3) ? null : details.getInt(3);
		int index = details.getInt(4);

		// TODO parse node in VarDef
		// int nodeId =
		details.getInt(5);

		Location loc = parseLocation(array.getJSONArray(1));
		AbstractType type = parseType(array.get(2));

		AbstractNode node = null;
		List<VarUse> refs = parseRefs(array.getJSONArray(3));

		VarDef varDef = new VarDef(assignable, global, index, loc, name, node,
				refs, suffix, type);

		// register the variable definition
		varDefs.put(stringOfVar(name, suffix, index), varDef);

		return varDef;
	}

	private List<VarDef> parseVarDefs(JSONArray array) throws JSONException {
		List<VarDef> variables = new ArrayList<VarDef>();
		for (int i = 0; i < array.length(); i++) {
			variables.add(parseVarDef(array.getJSONArray(i)));
		}

		return variables;
	}

	private VarUse parseVarUse(JSONArray array) throws JSONException {
		VarDef varDef = getVarDef(array.getJSONArray(0));

		// TODO parse node in VarUse
		// int nodeId =
		array.getInt(1);

		return new VarUse(varDef, null);
	}

	private WhileNode parseWhileNode(int id, Location loc, JSONArray array)
			throws JSONException {
		AbstractExpr condition = parseExpr(array.getJSONArray(0));
		List<AbstractNode> nodes = parseNodes(array.getJSONArray(1));
		JoinNode joinNode = (JoinNode) nodes.remove(0);
		return new WhileNode(id, loc, condition, nodes, joinNode);
	}

	private WriteNode parseWriteNode(int id, Location loc, JSONArray array)
			throws JSONException {
		String fifoName = array.getString(0);
		int numTokens = array.getInt(1);
		VarDef varDef = getVarDef(array.getJSONArray(2));
		return new WriteNode(id, loc, fifoName, numTokens, varDef);
	}

	private void putAction(List<String> tag, Action action) {
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
