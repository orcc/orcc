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
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_PEEK;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_PHI;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_READ;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_RETURN;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_STORE;
import static net.sf.orcc.ir.serialize.IRConstants.INSTR_WRITE;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_ACTIONS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_ACTION_SCHED;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INITIALIZES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_INPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_NAME;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_OUTPUTS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_PARAMETERS;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_PROCEDURES;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_SOURCE_FILE;
import static net.sf.orcc.ir.serialize.IRConstants.KEY_STATE_VARS;
import static net.sf.orcc.ir.serialize.IRConstants.NODE_BLOCK;
import static net.sf.orcc.ir.serialize.IRConstants.NODE_IF;
import static net.sf.orcc.ir.serialize.IRConstants.NODE_WHILE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
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
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * This class defines a parser that loads an actor in IR form serialized in JSON
 * in memory.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IRParser {

	final private Map<Tag, Action> actions;

	private String file;

	private OrderedMap<String, Port> inputs;

	private boolean isInitialize;

	private OrderedMap<String, Port> outputs;

	private Procedure procedure;

	final private OrderedMap<String, Procedure> procs;

	final private List<Action> untaggedActions;

	private Scope<String, Variable> variables;

	/**
	 * Creates a new IR parser.
	 */
	public IRParser() {
		actions = new HashMap<Tag, Action>();
		procs = new OrderedMap<String, Procedure>();
		untaggedActions = new ArrayList<Action>();
		variables = new Scope<String, Variable>();
	}

	/**
	 * Returns the action associated with the tag represented by the given JSON
	 * array.
	 * 
	 * @param array
	 *            an array of JSON strings
	 * @return the action (or initialize) associated with the tag
	 */
	private Action getAction(JsonArray array) {
		if (array.size() == 0) {
			// removes the first untagged action found
			return untaggedActions.remove(0);
		} else {
			Tag tag = new Tag();
			for (int i = 0; i < array.size(); i++) {
				tag.add(array.get(i).getAsString());
			}

			return actions.get(tag);
		}
	}

	/**
	 * Returns the variable associated with the variable represented by the
	 * given JSON array.
	 * 
	 * @param array
	 *            an array that contains a variable name, suffix, and SSA index
	 * @return the variable associated with the name
	 * @throws OrccException
	 *             if a semantic error occurs
	 */
	private Variable getVariable(JsonArray array) throws OrccException {
		String name = array.get(0).getAsString();
		int index = array.get(1).getAsInt();

		// retrieve the variable definition
		String indexStr = (index == 0) ? "" : "_" + index;
		String varName = name + indexStr;
		Variable varDef = variables.get(varName);
		if (varDef == null) {
			throw new OrccException("unknown variable: " + varName);
		}
		return varDef;
	}

	/**
	 * Parses the action represented by the given JSON array.
	 * 
	 * @param array
	 *            an array that defines an action
	 * @return an action
	 * @throws OrccException
	 *             if a semantic error occurs
	 */
	private Action parseAction(JsonArray array) throws OrccException {
		JsonArray tagArray = array.get(0).getAsJsonArray();
		Tag tag = new Tag();
		for (int i = 0; i < tagArray.size(); i++) {
			tag.add(tagArray.get(i).getAsString());
		}

		Pattern ip = parsePattern(inputs, array.get(1).getAsJsonArray());
		Pattern op = parsePattern(outputs, array.get(2).getAsJsonArray());

		Procedure scheduler = parseProc(array.get(3).getAsJsonArray());
		Procedure body = parseProc(array.get(4).getAsJsonArray());

		Action action = new Action(body.getLocation(), tag, ip, op, scheduler,
				body);
		putAction(tag, action);
		return action;
	}

	/**
	 * Parses the given JSON array as a list of actions.
	 * 
	 * @param array
	 *            a JSON array whose each entry encodes an action
	 * @return a list of actions
	 * @throws OrccException
	 *             if a semantic error occurs
	 */
	private List<Action> parseActions(JsonArray array) throws OrccException {
		List<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < array.size(); i++) {
			actions.add(parseAction(array.get(i).getAsJsonArray()));
		}

		return actions;
	}

	/**
	 * Parses the given JSON array as an action scheduler.
	 * 
	 * @param array
	 *            an array whose first entry is a list of actions and whose
	 *            second entry is a JSON-encoded FSM
	 * @return an action scheduler
	 * @throws OrccException
	 *             if a semantic error occurs
	 */
	private ActionScheduler parseActionScheduler(JsonArray array)
			throws OrccException {
		JsonArray actionArray = array.get(0).getAsJsonArray();
		List<Action> actions = new ArrayList<Action>();
		for (JsonElement element : actionArray) {
			actions.add(getAction(element.getAsJsonArray()));
		}

		FSM fsm = null;
		JsonElement fsmElement = array.get(1);
		if (fsmElement.isJsonArray()) {
			fsm = parseFSM(fsmElement.getAsJsonArray());
		}
		return new ActionScheduler(actions, fsm);
	}

	/**
	 * Parses the given input stream as JSON and returns an IR actor. The input
	 * stream is closed once the JSON has been parsed.
	 * 
	 * @param in
	 *            an input stream that contains JSON content
	 * @return an {@link Actor}
	 * @throws OrccException
	 *             if a semantic error occurs
	 */
	public Actor parseActor(InputStream in) throws OrccException {
		try {
			// parse input as UTF-8 JSON
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(
					new BufferedReader(new InputStreamReader(in)))
					.getAsJsonObject();

			file = obj.get(KEY_SOURCE_FILE).getAsString();
			String name = obj.get(KEY_NAME).getAsString();

			OrderedMap<String, Variable> parameters = variables;
			parseParameters(obj.get(KEY_PARAMETERS).getAsJsonArray());
			variables = new Scope<String, Variable>(variables, true);

			inputs = parsePorts(obj.get(KEY_INPUTS).getAsJsonArray());
			outputs = parsePorts(obj.get(KEY_OUTPUTS).getAsJsonArray());

			JsonArray array = obj.get(KEY_STATE_VARS).getAsJsonArray();
			OrderedMap<String, GlobalVariable> stateVars = parseStateVars(array);

			array = obj.get(KEY_PROCEDURES).getAsJsonArray();
			for (JsonElement element : array) {
				Procedure proc = parseProc(element.getAsJsonArray());
				procs.put(file, proc.getLocation(), proc.getName(), proc);
			}

			array = obj.get(KEY_ACTIONS).getAsJsonArray();
			List<Action> actions = parseActions(array);

			// a bit dirty, this one...
			// when isInitialize is true, don't put actions in hash tables.
			isInitialize = true;
			array = obj.get(KEY_INITIALIZES).getAsJsonArray();
			List<Action> initializes = parseActions(array);

			array = obj.get(KEY_ACTION_SCHED).getAsJsonArray();
			ActionScheduler sched = parseActionScheduler(array);

			Actor actor = new Actor(name, file, parameters, inputs, outputs,
					stateVars, procs, actions, initializes, sched);

			return actor;
		} finally {
			try {
				// closes the input stream
				in.close();
			} catch (IOException e) {
				throw new OrccException("I/O error", e);
			}
		}
	}

	private Expression parseExpr(JsonElement element) throws OrccException {
		if (element.isJsonPrimitive()) {
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isBoolean()) {
				return new BoolExpr(primitive.getAsBoolean());
			} else if (primitive.isNumber()) {
				Number number = primitive.getAsNumber();
				if (number instanceof BigInteger) {
					return new IntExpr(primitive.getAsBigInteger());
				} else if (number instanceof BigDecimal) {
					return new FloatExpr(primitive.getAsFloat());
				}
			} else if (primitive.isString()) {
				return new StringExpr(element.getAsString());
			}
		} else if (element.isJsonArray()) {
			JsonArray array = element.getAsJsonArray();
			String name = array.get(0).getAsString();
			if (name.equals(EXPR_VAR)) {
				Use var = parseVarUse(array.get(1).getAsJsonArray());
				return new VarExpr(var);
			} else if (name.equals(EXPR_UNARY)) {
				return parseExprUnary(array);
			} else if (name.equals(EXPR_BINARY)) {
				return parseExprBinary(array);
			} else if (name.equals(EXPR_LIST)) {
				return parseExprList(array);
			} else {
				throw new OrccException("Invalid expression kind: " + name);
			}
		}

		throw new OrccException("Invalid expression: " + element);
	}

	private BinaryExpr parseExprBinary(JsonArray array) throws OrccException {
		String name = array.get(1).getAsString();
		Expression e1 = parseExpr(array.get(2));
		Expression e2 = parseExpr(array.get(3));
		Type type = parseType(array.get(4));
		BinaryOp op = BinaryOp.getOperator(name);
		return new BinaryExpr(e1, op, e2, type);
	}

	private ListExpr parseExprList(JsonArray array) throws OrccException {
		int size = array.size();
		List<Expression> exprs = new ArrayList<Expression>(size - 1);
		for (int i = 1; i < size; i++) {
			exprs.add(parseExpr(array.get(i)));
		}
		return new ListExpr(exprs);
	}

	private List<Expression> parseExprs(JsonArray array) throws OrccException {
		int length = array.size();
		List<Expression> exprs = new ArrayList<Expression>(length);
		for (JsonElement element : array) {
			exprs.add(parseExpr(element));
		}

		return exprs;
	}

	private UnaryExpr parseExprUnary(JsonArray array) throws OrccException {
		String name = array.get(1).getAsString();
		Expression expr = parseExpr(array.get(2));
		Type type = parseType(array.get(3));
		UnaryOp op = UnaryOp.getOperator(name);
		return new UnaryExpr(op, expr, type);
	}

	private FSM parseFSM(JsonArray array) throws OrccException {
		String initialState = array.get(0).getAsString();
		FSM fsm = new FSM();
		JsonArray stateArray = array.get(1).getAsJsonArray();
		for (int i = 0; i < stateArray.size(); i++) {
			fsm.addState(stateArray.get(i).getAsString());
		}

		// set the initial state *after* initializing the states to get a
		// prettier order
		fsm.setInitialState(initialState);

		JsonArray transitionsArray = array.get(2).getAsJsonArray();
		for (JsonElement element : transitionsArray) {
			JsonArray transitionArray = element.getAsJsonArray();
			String source = transitionArray.get(0).getAsString();
			JsonArray targetsArray = transitionArray.get(1).getAsJsonArray();
			for (JsonElement targetElement : targetsArray) {
				JsonArray targetArray = targetElement.getAsJsonArray();
				Action action = getAction(targetArray.get(0).getAsJsonArray());
				String target = targetArray.get(1).getAsString();
				fsm.addTransition(source, target, action);
			}
		}

		return fsm;
	}

	/**
	 * Parses the given JSON array as an Assign instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return an Assign instruction
	 * @throws OrccException
	 */
	private Assign parseInstrAssign(Location loc, JsonArray array)
			throws OrccException {
		Variable var = getVariable(array.get(2).getAsJsonArray());
		Expression value = parseExpr(array.get(3));
		LocalVariable local = (LocalVariable) var;
		Assign assign = new Assign(loc, local, value);
		local.setInstruction(assign);
		return assign;
	}

	/**
	 * Parses the given JSON array as a Call instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Call instruction
	 * @throws OrccException
	 */
	private Call parseInstrCall(Location loc, JsonArray array)
			throws OrccException {
		String procName = array.get(2).getAsString();
		Procedure proc = procs.get(procName);
		if (proc == null) {
			throw new OrccException(file, loc, "unknown procedure: \""
					+ procName + "\"");
		}

		List<Expression> parameters = parseExprs(array.get(3).getAsJsonArray());

		LocalVariable res = null;
		if (array.get(4).isJsonArray()) {
			res = (LocalVariable) getVariable(array.get(4).getAsJsonArray());
		}

		Call call = new Call(loc, res, proc, parameters);
		if (res != null) {
			res.setInstruction(call);
		}
		return call;
	}

	/**
	 * Parses the given JSON array as a Load instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Load instruction
	 * @throws OrccException
	 */
	private Load parseInstrLoad(Location loc, JsonArray array)
			throws OrccException {
		LocalVariable target = (LocalVariable) getVariable(array.get(2)
				.getAsJsonArray());
		Use source = parseVarUse(array.get(3).getAsJsonArray());
		List<Expression> indexes = parseExprs(array.get(4).getAsJsonArray());

		Load load = new Load(loc, target, source, indexes);
		target.setInstruction(load);
		return load;
	}

	/**
	 * Parses the given JSON array as a Peek instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Peek instruction
	 * @throws OrccException
	 */
	private Peek parseInstrPeek(Location loc, JsonArray array)
			throws OrccException {
		Variable target = getVariable(array.get(2).getAsJsonArray());
		String fifoName = array.get(3).getAsString();
		Port port = inputs.get(fifoName);
		int numTokens = array.get(4).getAsInt();
		Peek peek = new Peek(loc, port, numTokens, target);
		target.setInstruction(peek);
		return peek;
	}

	/**
	 * Parses the given JSON array as a Phi instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a HasTokens instruction
	 * @throws OrccException
	 */
	private PhiAssignment parseInstrPhi(Location loc, JsonArray array)
			throws OrccException {
		LocalVariable target = (LocalVariable) getVariable(array.get(2)
				.getAsJsonArray());
		List<Expression> values = parseExprs(array.get(3).getAsJsonArray());

		PhiAssignment phi = new PhiAssignment(loc, target, values);
		target.setInstruction(phi);
		return phi;
	}

	/**
	 * Parses the given JSON array as a Read instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Read instruction
	 * @throws OrccException
	 */
	private Read parseInstrRead(Location loc, JsonArray array)
			throws OrccException {
		Variable target = getVariable(array.get(2).getAsJsonArray());
		String fifoName = array.get(3).getAsString();
		Port port = inputs.get(fifoName);
		int numTokens = array.get(4).getAsInt();
		Read read = new Read(loc, port, numTokens, target);
		target.setInstruction(read);
		return read;
	}

	/**
	 * Parses the given JSON array as a Return instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Return instruction
	 * @throws OrccException
	 */
	private Return parseInstrReturn(Location loc, JsonArray array)
			throws OrccException {
		Expression expr = null;
		if (!array.get(2).isJsonNull()) {
			expr = parseExpr(array.get(2));
		}
		return new Return(loc, expr);
	}

	/**
	 * Parses the given JSON array as a Store instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Store instruction
	 * @throws OrccException
	 */
	private Store parseInstrStore(Location loc, JsonArray array)
			throws OrccException {
		Variable target = getVariable(array.get(2).getAsJsonArray());
		List<Expression> indexes = parseExprs(array.get(3).getAsJsonArray());
		Expression value = parseExpr(array.get(4));

		return new Store(loc, target, indexes, value);
	}

	/**
	 * Parses the given JSON array as an Instruction.
	 * 
	 * @param array
	 *            a JSON array
	 * @return an Instruction
	 * @throws OrccException
	 *             if the instruction type is unknown
	 */
	private Instruction parseInstruction(JsonArray array) throws OrccException {
		String name = array.get(0).getAsString();
		Location loc = parseLocation(array.get(1).getAsJsonArray());

		if (name.equals(INSTR_ASSIGN)) {
			return parseInstrAssign(loc, array);
		} else if (name.equals(INSTR_CALL)) {
			return parseInstrCall(loc, array);
		} else if (name.equals(INSTR_LOAD)) {
			return parseInstrLoad(loc, array);
		} else if (name.equals(INSTR_PEEK)) {
			return parseInstrPeek(loc, array);
		} else if (name.equals(INSTR_PHI)) {
			return parseInstrPhi(loc, array);
		} else if (name.equals(INSTR_READ)) {
			return parseInstrRead(loc, array);
		} else if (name.equals(INSTR_RETURN)) {
			return parseInstrReturn(loc, array);
		} else if (name.equals(INSTR_STORE)) {
			return parseInstrStore(loc, array);
		} else if (name.equals(INSTR_WRITE)) {
			return parseInstrWrite(loc, array);
		}

		throw new OrccException("unknown instruction type: " + name);
	}

	/**
	 * Parses the given JSON array as a Write instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Write instruction
	 * @throws OrccException
	 */
	private Write parseInstrWrite(Location loc, JsonArray array)
			throws OrccException {
		Variable target = getVariable(array.get(2).getAsJsonArray());
		String fifoName = array.get(3).getAsString();
		Port port = outputs.get(fifoName);
		int numTokens = array.get(4).getAsInt();
		Write write = new Write(loc, port, numTokens, target);
		target.setInstruction(write);
		return write;
	}

	/**
	 * Parses the given JSON array as a Location
	 * 
	 * @param array
	 *            a JSON array
	 * @return a Location
	 */
	private Location parseLocation(JsonArray array) {
		if (array.size() == 3) {
			int startLine = array.get(0).getAsInt();
			int startCol = array.get(1).getAsInt();
			int endCol = array.get(2).getAsInt();

			return new Location(startLine, startCol, endCol);
		} else {
			return new Location();
		}
	}

	/**
	 * Parses the given JSON array as a CFG node.
	 * 
	 * @param array
	 *            a JSON array
	 * @return a CFG node
	 * @throws OrccException
	 *             if the node type is unknown
	 */
	private CFGNode parseNode(JsonArray array) throws OrccException {
		String name = array.get(0).getAsString();
		Location loc = parseLocation(array.get(1).getAsJsonArray());

		if (name.equals(NODE_BLOCK)) {
			return parseNodeBlock(loc, array);
		} else if (name.equals(NODE_IF)) {
			return parseNodeIf(loc, array);
		} else if (name.equals(NODE_WHILE)) {
			return parseNodeWhile(loc, array);
		}

		throw new OrccException("unknown node type: " + name);
	}

	/**
	 * Parses the given JSON array as a BlockNode.
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a BlockNode
	 * @throws OrccException
	 *             if one instruction could not be parsed
	 */
	private BlockNode parseNodeBlock(Location loc, JsonArray array)
			throws OrccException {
		BlockNode join = new BlockNode(loc, procedure);
		for (JsonElement element : array.get(2).getAsJsonArray()) {
			join.add(parseInstruction(element.getAsJsonArray()));
		}

		return join;
	}

	/**
	 * Parses the given JSON array as an IfNode.
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return an IfNode
	 * @throws OrccException
	 *             if an instruction or a node could not be parsed
	 */
	private IfNode parseNodeIf(Location loc, JsonArray array)
			throws OrccException {
		Expression condition = parseExpr(array.get(2));
		List<CFGNode> thenNodes = parseNodes(array.get(3).getAsJsonArray());
		List<CFGNode> elseNodes = parseNodes(array.get(4).getAsJsonArray());
		BlockNode joinNode = (BlockNode) parseNode(array.get(5)
				.getAsJsonArray());

		return new IfNode(loc, procedure, condition, thenNodes, elseNodes,
				joinNode);
	}

	/**
	 * Parses the given JSON array as a list of CFG nodes.
	 * 
	 * @param array
	 *            a JSON array
	 * @return a list of CFG nodes
	 * @throws OrccException
	 *             if a node or an instruction could not be parsed
	 */
	private List<CFGNode> parseNodes(JsonArray array) throws OrccException {
		List<CFGNode> nodes = new ArrayList<CFGNode>(array.size());
		for (JsonElement element : array) {
			nodes.add(parseNode(element.getAsJsonArray()));
		}
		return nodes;
	}

	/**
	 * Parses the given JSON array as a WhileNode.
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a WhileNode
	 * @throws OrccException
	 *             if an instruction or a node could not be parsed
	 */
	private WhileNode parseNodeWhile(Location loc, JsonArray array)
			throws OrccException {
		Expression condition = parseExpr(array.get(2));
		List<CFGNode> nodes = parseNodes(array.get(3).getAsJsonArray());
		BlockNode joinNode = (BlockNode) parseNode(array.get(4)
				.getAsJsonArray());
		return new WhileNode(loc, procedure, condition, nodes, joinNode);
	}

	private void parseParameters(JsonArray array) throws OrccException {
		for (JsonElement element : array) {
			JsonArray varDefArray = element.getAsJsonArray();

			JsonArray details = varDefArray.get(0).getAsJsonArray();
			String name = details.get(0).getAsString();

			Location location = parseLocation(varDefArray.get(1)
					.getAsJsonArray());
			Type type = parseType(varDefArray.get(2));

			GlobalVariable parameter = new GlobalVariable(location, type, name,
					false);

			// register the state variable
			variables.put(file, location, name, parameter);
		}
	}

	private Pattern parsePattern(OrderedMap<String, Port> ports, JsonArray array)
			throws OrccException {
		Pattern pattern = new Pattern();
		for (int i = 0; i < array.size(); i++) {
			JsonArray patternArray = array.get(i).getAsJsonArray();
			Port port = ports.get(patternArray.get(0).getAsString());
			int numTokens = patternArray.get(1).getAsInt();
			pattern.put(port, numTokens);
		}

		return pattern;
	}

	/**
	 * Parses the given JSON array as a list of ports.
	 * 
	 * @param array
	 *            an array of JSON-encoded ports
	 * @return an ordered map of ports
	 * @throws OrccException
	 *             if a semantic error occurs
	 */
	private OrderedMap<String, Port> parsePorts(JsonArray array)
			throws OrccException {
		OrderedMap<String, Port> ports = new OrderedMap<String, Port>();
		for (JsonElement element : array) {
			JsonArray port = element.getAsJsonArray();

			Location location = parseLocation(port.get(0).getAsJsonArray());
			Type type = parseType(port.get(1));
			String name = port.get(2).getAsString();

			Port po = new Port(location, type, name);
			ports.put(file, location, name, po);
		}

		return ports;
	}

	/**
	 * Parses a procedure.
	 * 
	 * @param array
	 *            a JSON array
	 * @return the procedure parsed
	 * @throws OrccException
	 */
	private Procedure parseProc(JsonArray array) throws OrccException {
		String name = array.get(0).getAsString();
		boolean external = array.get(1).getAsBoolean();

		Location location = parseLocation(array.get(2).getAsJsonArray());
		Type returnType = parseType(array.get(3));
		variables = new Scope<String, Variable>(variables, true);
		OrderedMap<String, Variable> parameters = variables;
		parseVarDefs(array.get(4).getAsJsonArray());
		variables = new Scope<String, Variable>(variables, false);
		OrderedMap<String, Variable> locals = variables;
		parseVarDefs(array.get(5).getAsJsonArray());

		procedure = new Procedure(name, external, location, returnType,
				parameters, locals, null);

		List<CFGNode> nodes = parseNodes(array.get(6).getAsJsonArray());
		procedure.setNodes(nodes);

		// go back to previous scope
		variables = variables.getParent().getParent();

		AbstractNode.resetLabelCount();

		return procedure;
	}

	/**
	 * Parses the given list as a list of state variables. A
	 * {@link StateVariable} is a {@link LocalVariable} with an optional
	 * reference to a constant that contain the variable's initial value.
	 * 
	 * @param list
	 *            A list of JSON-encoded {@link LocalVariable}.
	 * @return A {@link List}&lt;{@link StateVariable}&gt;.
	 */
	private OrderedMap<String, GlobalVariable> parseStateVars(JsonArray array)
			throws OrccException {
		OrderedMap<String, GlobalVariable> stateVars = new OrderedMap<String, GlobalVariable>();
		for (JsonElement element : array) {
			JsonArray stateArray = element.getAsJsonArray();

			JsonArray varDefArray = stateArray.get(0).getAsJsonArray();
			JsonArray details = varDefArray.get(0).getAsJsonArray();
			String name = details.get(0).getAsString();
			boolean assignable = details.get(1).getAsBoolean();

			Location location = parseLocation(varDefArray.get(1)
					.getAsJsonArray());
			Type type = parseType(varDefArray.get(2));

			Expression init = null;
			if (!stateArray.get(1).isJsonNull()) {
				init = parseExpr(stateArray.get(1));
			}

			GlobalVariable stateVar = new GlobalVariable(location, type, name,
					assignable, init);
			stateVars.put(file, location, name, stateVar);

			// register the state variable
			variables.put(file, location, name, stateVar);
		}

		return stateVars;
	}

	/**
	 * Parses the given object as a type definition.
	 * 
	 * @param obj
	 *            A type definition. This is either a {@link String} for simple
	 *            types (bool, float, String, void) or a {@link List} for types
	 *            with parameters (int, uint, List).
	 * @return An {@link Type}.
	 */
	private Type parseType(JsonElement element) throws OrccException {
		if (element.isJsonPrimitive()) {
			String name = element.getAsString();
			if (name.equals(TypeBool.NAME)) {
				return IrFactory.eINSTANCE.createTypeBool();
			} else if (name.equals(TypeString.NAME)) {
				return IrFactory.eINSTANCE.createTypeString();
			} else if (name.equals(TypeVoid.NAME)) {
				return IrFactory.eINSTANCE.createTypeVoid();
			} else {
				throw new OrccException("Unknown type: " + name);
			}
		} else if (element.isJsonArray()) {
			JsonArray array = element.getAsJsonArray();
			String name = array.get(0).getAsString();
			if (name.equals(TypeInt.NAME)) {
				int size = array.get(1).getAsInt();
				return IrFactory.eINSTANCE.createTypeInt(size);
			} else if (name.equals(TypeUint.NAME)) {
				int size = array.get(1).getAsInt();
				return IrFactory.eINSTANCE.createTypeUint(size);
			} else if (name.equals(TypeList.NAME)) {
				Expression size = parseExpr(array.get(1));
				Type subType = parseType(array.get(2));

				return IrFactory.eINSTANCE.createTypeList(size, subType);
			} else {
				throw new OrccException("Unknown type: " + name);
			}
		}

		throw new OrccException("Invalid type definition: " + element);
	}

	/**
	 * Returns a variable definition using objects returned by the given
	 * iterator.
	 * 
	 * @param array
	 *            an array that contains a variable definition
	 * @return A {@link LocalVariable}
	 */
	private LocalVariable parseVarDef(JsonArray array) throws OrccException {
		JsonArray details = array.get(0).getAsJsonArray();
		String name = details.get(0).getAsString();
		boolean assignable = details.get(1).getAsBoolean();
		int index = details.get(2).getAsInt();

		Location loc = parseLocation(array.get(1).getAsJsonArray());
		Type type = parseType(array.get(2));

		LocalVariable varDef = new LocalVariable(assignable, index, loc, name,
				type);

		// register the variable definition
		variables.put(file, loc, varDef.getName(), varDef);

		return varDef;
	}

	private void parseVarDefs(JsonArray array) throws OrccException {
		for (JsonElement element : array) {
			parseVarDef(element.getAsJsonArray());
		}
	}

	private Use parseVarUse(JsonArray array) throws OrccException {
		Variable varDef = getVariable(array);
		return new Use(varDef);
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

}
