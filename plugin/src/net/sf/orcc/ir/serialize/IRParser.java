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

import static net.sf.orcc.ir.serialize.IRConstants.BINARY_EXPR;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_ACTIONS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_ACTION_SCHED;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INITIALIZES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_NAME;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_OUTPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_PROCEDURES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_SOURCE_FILE;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_STATE_VARS;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_ASSIGN;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_CALL;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_EMPTY;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_HAS_TOKENS;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_IF;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_JOIN;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_LOAD;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_PEEK;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_READ;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_RETURN;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_STORE;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_WHILE;
import static net.sf.orcc.ir.serialize.IRConstants.NAME_WRITE;
import static net.sf.orcc.ir.serialize.IRConstants.UNARY_EXPR;
import static net.sf.orcc.ir.serialize.IRConstants.VAR_EXPR;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.consts.AbstractConstant;
import net.sf.orcc.ir.consts.BoolConst;
import net.sf.orcc.ir.consts.IntConst;
import net.sf.orcc.ir.consts.ListConst;
import net.sf.orcc.ir.consts.StringConst;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transforms.BlockCombine;
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

	private BlockNode block;

	private String file;

	private OrderedMap<Port> inputs;

	private boolean isInitialize;

	private OrderedMap<Port> outputs;

	private CFGNode previousNode;

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
		String varName = stringOfVar(name, suffix, index);
		Variable varDef = variables.get(varName);
		if (varDef == null) {
			throw new OrccException("unknown variable: " + varName);
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

		Pattern ip = parsePattern(inputs, array.getJSONArray(1));
		Pattern op = parsePattern(outputs, array.getJSONArray(2));

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

			// combine basic blocks
			new BlockCombine().transform(actor);

			return actor;
		} catch (JSONException e) {
			throw new OrccException("JSON error", e);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	private Assign parseAssign(Location loc, JSONArray array)
			throws JSONException, OrccException {
		Variable var = getVariable(array.getJSONArray(0));
		Expression value = parseExpr(array.getJSONArray(1));
		LocalVariable local = (LocalVariable) var;
		Assign assign = new Assign(block, loc, local, value);
		local.setInstruction(assign);
		return assign;
	}

	private BinaryExpr parseBinaryExpr(Location location, JSONArray array)
			throws JSONException, OrccException {
		String name = array.getString(0);
		Expression e1 = parseExpr(array.getJSONArray(1));
		Expression e2 = parseExpr(array.getJSONArray(2));
		Type type = parseType(array.get(3));
		BinaryOp op = BinaryOp.getOperator(name);
		return new BinaryExpr(location, e1, op, e2, type);
	}

	private Call parseCall(Location loc, JSONArray array) throws JSONException,
			OrccException {
		LocalVariable res = null;
		String procName = array.getString(0);
		if (!array.isNull(1)) {
			res = (LocalVariable) getVariable(array.getJSONArray(1));
		}

		List<Expression> parameters = parseExprs(array.getJSONArray(2));
		return new Call(block, loc, res, procs.get(procName), parameters);
	}

	/**
	 * Parses the given object as a constant.
	 * 
	 * @param obj
	 *            A YAML-encoded constant that may be a {@link Boolean}, an
	 *            {@link Integer}, a {@link List} or a {@link String}.
	 * @return An {@link AbstractConstant} created from the given object.
	 * @throws JSONException
	 */
	private Constant parseConstant(Object obj) throws JSONException,
			OrccException {
		Constant constant = null;

		if (obj instanceof Boolean) {
			constant = new BoolConst((Boolean) obj);
		} else if (obj instanceof Integer) {
			constant = new IntConst((Integer) obj);
		} else if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			List<Constant> cstList = new ArrayList<Constant>();
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

	private Expression parseExpr(JSONArray array) throws JSONException,
			OrccException {
		Location location = parseLocation(array.getJSONArray(0));
		Object obj = array.get(1);
		Expression expr = null;

		if (obj instanceof Boolean) {
			expr = new BoolExpr(location, (Boolean) obj);
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

	private List<Expression> parseExprs(JSONArray array) throws JSONException,
			OrccException {
		List<Expression> exprs = new ArrayList<Expression>();
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

	private HasTokens parseHasTokens(Location loc, JSONArray array)
			throws JSONException, OrccException {
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(0));
		String fifoName = array.getString(1);
		Port port = inputs.get(fifoName);
		int numTokens = array.getInt(2);
		return new HasTokens(block, loc, port, numTokens, varDef);
	}

	private IfNode parseIfNode(Location loc, JSONArray array)
			throws JSONException, OrccException {
		Expression condition = parseExpr(array.getJSONArray(0));
		List<CFGNode> thenNodes = parseNodes(array.getJSONArray(1));
		List<CFGNode> elseNodes = parseNodes(array.getJSONArray(2));

		return new IfNode(loc, condition, thenNodes, elseNodes, null);
	}

	private BlockNode parseJoinNode(Location loc, JSONArray array)
			throws JSONException, OrccException {
		BlockNode join = new BlockNode(loc);
		block = join;
		for (int i = 0; i < array.length(); i++) {
			join.add(parsePhi(loc, array.getJSONArray(i)));
		}

		return join;
	}

	private Load parseLoad(Location loc, JSONArray array) throws JSONException,
			OrccException {
		LocalVariable target = (LocalVariable) getVariable(array
				.getJSONArray(0));
		Use source = parseVarUse(array.getJSONArray(1));
		List<Expression> indexes = parseExprs(array.getJSONArray(2));

		return new Load(block, loc, target, source, indexes);
	}

	private Location parseLocation(JSONArray array) throws JSONException {
		if (array.length() == 4) {
			int startLine = array.getInt(0);
			int startCol = array.getInt(1);
			int endCol = array.getInt(2);

			return new Location(startLine, startCol, endCol);
		} else {
			return new Location();
		}
	}

	private CFGNode parseNode(JSONArray array) throws JSONException,
			OrccException {
		String name = array.getString(0);
		Location loc = parseLocation(array.getJSONArray(1));
		CFGNode node = null;

		if (name.equals(NAME_IF)) {
			node = parseIfNode(loc, array.getJSONArray(2));
		} else if (name.equals(NAME_JOIN)) {
			node = parseJoinNode(loc, array.getJSONArray(2));

			// if the previous node is an If, then the join node we just parsed
			// is the If's join node.
			// We set it, and just pretend we didn't parse anything (otherwise
			// the join would be referenced once in the if, and once in the node
			// list)
			if (previousNode instanceof IfNode) {
				((IfNode) previousNode).setJoinNode((BlockNode) node);
				node = null;
			}
		} else if (name.equals(NAME_WHILE)) {
			node = parseWhileNode(loc, array.getJSONArray(2));
		} else {
			block = new BlockNode(loc);
			Instruction instr = null;

			if (name.equals(NAME_ASSIGN)) {
				instr = parseAssign(loc, array.getJSONArray(2));
			} else if (name.equals(NAME_CALL)) {
				instr = parseCall(loc, array.getJSONArray(2));
			} else if (name.equals(NAME_EMPTY)) {
				// nothing to do
			} else if (name.equals(NAME_HAS_TOKENS)) {
				instr = parseHasTokens(loc, array.getJSONArray(2));
			} else if (name.equals(NAME_LOAD)) {
				instr = parseLoad(loc, array.getJSONArray(2));
			} else if (name.equals(NAME_PEEK)) {
				instr = parsePeek(loc, array.getJSONArray(2));
			} else if (name.equals(NAME_READ)) {
				instr = parseRead(loc, array.getJSONArray(2));
			} else if (name.equals(NAME_RETURN)) {
				instr = parseReturn(loc, array.optJSONArray(2));
			} else if (name.equals(NAME_STORE)) {
				instr = parseStore(loc, array.getJSONArray(2));
			} else if (name.equals(NAME_WRITE)) {
				instr = parseWrite(loc, array.getJSONArray(2));
			} else {
				throw new OrccException("Invalid node definition: " + name);
			}

			if (instr != null) {
				block.add(instr);
				node = block;
			}
		}

		previousNode = node;
		return node;
	}

	private List<CFGNode> parseNodes(JSONArray array) throws JSONException,
			OrccException {
		List<CFGNode> nodes = new ArrayList<CFGNode>();
		for (int i = 0; i < array.length(); i++) {
			CFGNode node = parseNode(array.getJSONArray(i));
			if (node != null) {
				nodes.add(node);
			}
		}

		return nodes;
	}

	private Pattern parsePattern(OrderedMap<Port> ports,
			JSONArray array) throws JSONException, OrccException {
		Pattern pattern = new Pattern();
		for (int i = 0; i < array.length(); i++) {
			JSONArray patternArray = array.getJSONArray(i);
			Port port = ports.get(patternArray.getString(0));
			int numTokens = patternArray.getInt(1);
			pattern.put(port, numTokens);
		}

		return pattern;
	}

	private Peek parsePeek(Location loc, JSONArray array) throws JSONException,
			OrccException {
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(0));
		String fifoName = array.getString(1);
		Port port = inputs.get(fifoName);
		int numTokens = array.getInt(2);
		return new Peek(block, loc, port, numTokens, varDef);
	}

	private PhiAssignment parsePhi(Location loc, JSONArray array)
			throws JSONException, OrccException {
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(0));
		List<Use> vars = new ArrayList<Use>();
		array = array.getJSONArray(1);
		for (int i = 0; i < array.length(); i++) {
			vars.add(parseVarUse(array.getJSONArray(i)));
		}

		return new PhiAssignment(block, loc, varDef, vars);
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
			Type type = parseType(port.get(1));
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
		String name = array.getString(0);
		boolean external = array.getBoolean(1);

		Location location = parseLocation(array.getJSONArray(2));
		Type returnType = parseType(array.get(3));
		variables = new Scope<Variable>(variables, true);
		OrderedMap<Variable> parameters = variables;
		parseVarDefs(array.getJSONArray(4));
		variables = new Scope<Variable>(variables, false);
		OrderedMap<Variable> locals = variables;
		parseVarDefs(array.getJSONArray(5));

		List<CFGNode> nodes = parseNodes(array.getJSONArray(6));

		// go back to previous scope
		variables = variables.getParent().getParent();

		Procedure proc = new Procedure(name, external, location, returnType,
				parameters, locals, nodes);
		if (register) {
			procs.add(file, location, name, proc);
		}

		AbstractNode.resetLabelCount();

		return proc;
	}

	private Read parseRead(Location loc, JSONArray array) throws JSONException,
			OrccException {
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(0));
		String fifoName = array.getString(1);
		Port port = inputs.get(fifoName);
		int numTokens = array.getInt(2);
		return new Read(block, loc, port, numTokens, varDef);
	}

	private Return parseReturn(Location loc, JSONArray array)
			throws JSONException, OrccException {
		Expression expr = null;
		if (array == null) {
			expr = null;
		} else {
			expr = parseExpr(array);
		}
		return new Return(block, loc, expr);
	}

	/**
	 * Parses the given list as a list of state variables. A
	 * {@link StateVariable} is a {@link LocalVariable} with an optional
	 * reference to an {@link AbstractConstant} that contain the variable's
	 * initial value.
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
			Type type = parseType(varDefArray.get(2));

			Constant init = null;
			if (!stateArray.isNull(1)) {
				init = parseConstant(stateArray.get(1));
			}

			StateVariable stateVar = new StateVariable(location, type, name,
					assignable, init);
			stateVars.add(file, location, name, stateVar);

			// register the state variable
			variables.add(file, location, name, stateVar);
		}

		return stateVars;
	}

	private Store parseStore(Location loc, JSONArray array)
			throws JSONException, OrccException {
		Use target = parseVarUse(array.getJSONArray(0));
		List<Expression> indexes = parseExprs(array.getJSONArray(1));
		Expression value = parseExpr(array.getJSONArray(2));

		return new Store(block, loc, target, indexes, value);
	}

	/**
	 * Parses the given object as a type definition.
	 * 
	 * @param obj
	 *            A YAML-encoded type definition. This is either a
	 *            {@link String} for simple types (bool, String, void) or a
	 *            {@link List} for types with parameters (int, uint, List).
	 * @return An {@link Type}.
	 * @throws JSONException
	 */
	private Type parseType(Object obj) throws JSONException, OrccException {
		Type type = null;

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
				Expression expr = parseExpr(array.getJSONArray(1));
				type = new IntType(expr);
			} else if (name.equals(UintType.NAME)) {
				Expression expr = parseExpr(array.getJSONArray(1));
				type = new UintType(expr);
			} else if (name.equals(ListType.NAME)) {
				Expression expr = parseExpr(array.getJSONArray(1));
				Type subType = parseType(array.get(2));
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
		Expression expr = parseExpr(array.getJSONArray(1));
		Type type = parseType(array.get(2));
		UnaryOp op = UnaryOp.getOperator(name);
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
		Integer suffix = details.isNull(2) ? null : details.getInt(2);
		int index = details.getInt(3);

		Location loc = parseLocation(array.getJSONArray(1));
		Type type = parseType(array.get(2));

		LocalVariable varDef = new LocalVariable(assignable, index, loc, name,
				null, suffix, type);

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
		Variable varDef = getVariable(array);
		return new Use(varDef);
	}

	private WhileNode parseWhileNode(Location loc, JSONArray array)
			throws JSONException, OrccException {
		Expression condition = parseExpr(array.getJSONArray(0));
		List<CFGNode> nodes = parseNodes(array.getJSONArray(1));
		BlockNode joinNode = (BlockNode) nodes.remove(0);
		return new WhileNode(loc, condition, nodes, joinNode);
	}

	private Write parseWrite(Location loc, JSONArray array)
			throws JSONException, OrccException {
		LocalVariable varDef = (LocalVariable) getVariable(array
				.getJSONArray(0));
		String fifoName = array.getString(1);
		Port port = outputs.get(fifoName);
		int numTokens = array.getInt(2);
		return new Write(block, loc, port, numTokens, varDef);
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
		String indexStr = (index == 0) ? "" : "_" + index;
		return name + (suffix == null ? "" : suffix) + indexStr;
	}

}
