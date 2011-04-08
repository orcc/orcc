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

import org.eclipse.emf.ecore.EStructuralFeature;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
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
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.impl.NodeImpl;
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

	private static final String JSON_ERR_MSG = "JSON error probably caused by"
			+ " a change in the IR serialization format,"
			+ " please try to clean/rebuild your actors";

	final private Map<Tag, Action> actions;

	private String file;

	private Actor actor;

	private boolean isInitialize;

	final private OrderedMap<String, Procedure> procs;

	final private List<Action> untaggedActions;

	private Scope<String, Var> vars;

	/**
	 * Creates a new IR parser.
	 */
	public IRParser() {
		actions = new HashMap<Tag, Action>();
		procs = new OrderedMap<String, Procedure>();
		untaggedActions = new ArrayList<Action>();
		vars = new Scope<String, Var>();
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
			Tag tag = IrFactory.eINSTANCE.createTag();
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
	 *            an array that contains a variable name and SSA index
	 * @return the variable associated with the name
	 */
	private Var getVariable(JsonArray array) {
		String varName = getVariableName(array);
		Var varDef = vars.get(varName);
		if (varDef == null) {
			throw new OrccRuntimeException("unknown variable: " + varName);
		}
		return varDef;
	}

	/**
	 * Returns the name of the variable represented by the given JSON array.
	 * 
	 * @param array
	 *            an array that contains a variable name and SSA index
	 * @return the name of the variable represented by the given JSON array
	 */
	private String getVariableName(JsonArray array) {
		String name = array.get(0).getAsString();
		int index = array.get(1).getAsInt();

		// retrieve the variable definition
		String indexStr = (index == 0) ? "" : "_" + index;
		return name + indexStr;
	}

	/**
	 * Parses the action represented by the given JSON array.
	 * 
	 * @param array
	 *            an array that defines an action
	 * @return an action
	 */
	private Action parseAction(JsonArray array) {
		JsonArray tagArray = array.get(0).getAsJsonArray();
		Tag tag = IrFactory.eINSTANCE.createTag();
		for (int i = 0; i < tagArray.size(); i++) {
			tag.add(tagArray.get(i).getAsString());
		}

		vars = new Scope<String, Var>(vars, true);
		Pattern ip = parseInputPattern(array.get(1).getAsJsonArray());
		Pattern op = parseOutputPattern(array.get(2).getAsJsonArray());

		Procedure body = parseProc(array.get(4).getAsJsonArray());

		// add peeked vars
		vars = new Scope<String, Var>(vars, true);
		for (Port port : ip.getPorts()) {
			Var peeked = ip.getPeeked(port);
			if (peeked != null) {
				vars.put(file, peeked.getLocation(), peeked.getName(), peeked);
			}
		}
		Procedure scheduler = parseProc(array.get(3).getAsJsonArray());

		vars = vars.getParent().getParent();

		Action action = IrFactory.eINSTANCE.createAction(body.getLocation(),
				tag, ip, op, scheduler, body);
		putAction(tag, action);
		return action;
	}

	/**
	 * Parses the given JSON array as a list of actions.
	 * 
	 * @param array
	 *            a JSON array whose each entry encodes an action
	 * @return a list of actions
	 */
	@SuppressWarnings("unchecked")
	private void parseActions(EStructuralFeature feature, JsonArray array) {
		for (int i = 0; i < array.size(); i++) {
			Action action = parseAction(array.get(i).getAsJsonArray());
			((List<Action>) actor.eGet(feature)).add(action);
		}
	}

	/**
	 * Parses the given JSON array as an action scheduler.
	 * 
	 * @param array
	 *            an array whose first entry is a list of actions and whose
	 *            second entry is a JSON-encoded FSM
	 * @return an action scheduler
	 */
	private ActionScheduler parseActionScheduler(JsonArray array) {
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

			actor = IrFactory.eINSTANCE.createActor();

			file = obj.get(KEY_SOURCE_FILE).getAsString();
			actor.setFile(file);
			String name = obj.get(KEY_NAME).getAsString();
			actor.setName(name);

			JsonArray array = obj.get(KEY_PARAMETERS).getAsJsonArray();
			parseGlobalVariables(IrPackage.eINSTANCE.getActor_Parameters(),
					array);
			vars = new Scope<String, Var>(vars, true);

			actor.setNative(obj.get(KEY_NATIVE).getAsBoolean());

			parsePorts(IrPackage.eINSTANCE.getActor_Inputs(),
					obj.get(KEY_INPUTS).getAsJsonArray());
			parsePorts(IrPackage.eINSTANCE.getActor_Outputs(),
					obj.get(KEY_OUTPUTS).getAsJsonArray());

			array = obj.get(KEY_STATE_VARS).getAsJsonArray();
			parseGlobalVariables(IrPackage.eINSTANCE.getActor_StateVars(),
					array);

			array = obj.get(KEY_PROCEDURES).getAsJsonArray();
			for (JsonElement element : array) {
				Procedure proc = parseProc(element.getAsJsonArray());
				procs.put(file, proc.getLocation(), proc.getName(), proc);
				actor.getProcs().add(proc);
			}

			array = obj.get(KEY_ACTIONS).getAsJsonArray();
			parseActions(IrPackage.eINSTANCE.getActor_Actions(), array);

			// a bit dirty, this one...
			// when isInitialize is true, don't put actions in hash tables.
			isInitialize = true;
			array = obj.get(KEY_INITIALIZES).getAsJsonArray();
			parseActions(IrPackage.eINSTANCE.getActor_Initializes(), array);

			array = obj.get(KEY_ACTION_SCHED).getAsJsonArray();
			actor.setActionScheduler(parseActionScheduler(array));

			return actor;
		} catch (RuntimeException e) {
			throw new OrccException(JSON_ERR_MSG);
		} finally {
			try {
				// closes the input stream
				in.close();
			} catch (IOException e) {
				throw new OrccException("I/O error", e);
			}
		}
	}

	private Expression parseExpr(JsonElement element) {
		if (element.isJsonPrimitive()) {
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isBoolean()) {
				return IrFactory.eINSTANCE.createExprBool(primitive
						.getAsBoolean());
			} else if (primitive.isNumber()) {
				Number number = primitive.getAsNumber();
				if (number instanceof BigInteger) {
					return IrFactory.eINSTANCE.createExprInt(primitive
							.getAsBigInteger());
				} else if (number instanceof BigDecimal) {
					return IrFactory.eINSTANCE.createExprFloat(primitive
							.getAsFloat());
				}
			} else if (primitive.isString()) {
				return IrFactory.eINSTANCE.createExprString(element
						.getAsString());
			}
		} else if (element.isJsonArray()) {
			JsonArray array = element.getAsJsonArray();
			String name = array.get(0).getAsString();
			if (name.equals(EXPR_VAR)) {
				Use var = parseVarUse(array.get(1).getAsJsonArray());
				return IrFactory.eINSTANCE.createExprVar(var);
			} else if (name.equals(EXPR_UNARY)) {
				return parseExprUnary(array);
			} else if (name.equals(EXPR_BINARY)) {
				return parseExprBinary(array);
			} else if (name.equals(EXPR_LIST)) {
				return parseExprList(array);
			} else {
				throw new OrccRuntimeException("Invalid expression kind: "
						+ name);
			}
		}

		throw new OrccRuntimeException("Invalid expression: " + element);
	}

	private ExprBinary parseExprBinary(JsonArray array) {
		String name = array.get(1).getAsString();
		Expression e1 = parseExpr(array.get(2));
		Expression e2 = parseExpr(array.get(3));
		Type type = parseType(array.get(4));
		OpBinary op = OpBinary.getOperator(name);
		return IrFactory.eINSTANCE.createExprBinary(e1, op, e2, type);
	}

	private ExprList parseExprList(JsonArray array) {
		int size = array.size();
		List<Expression> exprs = new ArrayList<Expression>(size - 1);
		for (int i = 1; i < size; i++) {
			exprs.add(parseExpr(array.get(i)));
		}
		return IrFactory.eINSTANCE.createExprList(exprs);
	}

	private List<Expression> parseExprs(JsonArray array) {
		int length = array.size();
		List<Expression> exprs = new ArrayList<Expression>(length);
		for (JsonElement element : array) {
			exprs.add(parseExpr(element));
		}

		return exprs;
	}

	private ExprUnary parseExprUnary(JsonArray array) {
		String name = array.get(1).getAsString();
		Expression expr = parseExpr(array.get(2));
		Type type = parseType(array.get(3));
		OpUnary op = OpUnary.getOperator(name);
		return IrFactory.eINSTANCE.createExprUnary(op, expr, type);
	}

	private FSM parseFSM(JsonArray array) {
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
				fsm.addTransition(source, action, target);
			}
		}

		return fsm;
	}

	/**
	 * Parses the given list as a list of global vars. A {@link VarGlobal} is a
	 * {@link Var} with an optional reference to a constant that contain the
	 * variable's initial value.
	 * 
	 * @param array
	 *            A list of JSON-encoded {@link VarGlobal}.
	 * @return A {@link List}&lt;{@link VarGlobal}&gt;.
	 */
	@SuppressWarnings("unchecked")
	private void parseGlobalVariables(EStructuralFeature feature,
			JsonArray arrayGlobals) {
		for (JsonElement element : arrayGlobals) {
			JsonArray array = element.getAsJsonArray();

			String name = array.get(0).getAsString();
			boolean assignable = array.get(1).getAsBoolean();

			Location location = parseLocation(array.get(2).getAsJsonArray());
			Type type = parseType(array.get(3));

			Expression init = null;
			if (!array.get(4).isJsonNull()) {
				init = parseExpr(array.get(4));
			}

			Var stateVar = IrFactory.eINSTANCE.createVar(location, type, name,
					assignable, init);
			((List<Var>) actor.eGet(feature)).add(stateVar);

			// register the state variable
			vars.put(file, location, name, stateVar);
		}
	}

	/**
	 * Parses the given JSON array as an Assign instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return an Assign instruction
	 */
	private InstAssign parseInstrAssign(Location loc, JsonArray array) {
		Var var = getVariable(array.get(2).getAsJsonArray());
		Expression value = parseExpr(array.get(3));
		return IrFactory.eINSTANCE.createInstAssign(loc, var, value);
	}

	/**
	 * Parses the given JSON array as a Call instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Call instruction
	 */
	private InstCall parseInstrCall(Location loc, JsonArray array) {
		String procName = array.get(2).getAsString();
		Procedure proc = procs.get(procName);
		if (proc == null) {
			throw new OrccRuntimeException(file, loc, "unknown procedure: \""
					+ procName + "\"");
		}

		List<Expression> parameters = parseExprs(array.get(3).getAsJsonArray());

		Var res = null;
		if (array.get(4).isJsonArray()) {
			res = getVariable(array.get(4).getAsJsonArray());
		}

		return IrFactory.eINSTANCE.createInstCall(loc, res, proc, parameters);
	}

	/**
	 * Parses the given JSON array as a Load instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Load instruction
	 */
	private InstLoad parseInstrLoad(Location loc, JsonArray array) {
		Var target = getVariable(array.get(2).getAsJsonArray());
		Use source = parseVarUse(array.get(3).getAsJsonArray());
		List<Expression> indexes = parseExprs(array.get(4).getAsJsonArray());

		return IrFactory.eINSTANCE.createInstLoad(loc,
				IrFactory.eINSTANCE.createDef(target), source, indexes);
	}

	/**
	 * Parses the given JSON array as a Phi instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Phi instruction
	 */
	private InstPhi parseInstrPhi(Location loc, JsonArray array) {
		Var target = getVariable(array.get(2).getAsJsonArray());
		List<Expression> values = parseExprs(array.get(3).getAsJsonArray());
		return IrFactory.eINSTANCE.createInstPhi(loc,
				IrFactory.eINSTANCE.createDef(target), values);
	}

	/**
	 * Parses the given JSON array as a Return instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Return instruction
	 */
	private InstReturn parseInstrReturn(Location loc, JsonArray array) {
		Expression expr = null;
		if (!array.get(2).isJsonNull()) {
			expr = parseExpr(array.get(2));
		}
		return IrFactory.eINSTANCE.createInstReturn(loc, expr);
	}

	/**
	 * Parses the given JSON array as a Store instruction
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a Store instruction
	 */
	private InstStore parseInstrStore(Location loc, JsonArray array) {
		Var target = getVariable(array.get(2).getAsJsonArray());
		List<Expression> indexes = parseExprs(array.get(3).getAsJsonArray());
		Expression value = parseExpr(array.get(4));

		return IrFactory.eINSTANCE.createInstStore(loc,
				IrFactory.eINSTANCE.createDef(target), indexes, value);
	}

	/**
	 * Parses the given JSON array as an Instruction.
	 * 
	 * @param array
	 *            a JSON array
	 * @return an Instruction
	 */
	private Instruction parseInstruction(JsonArray array) {
		String name = array.get(0).getAsString();
		Location loc = parseLocation(array.get(1).getAsJsonArray());

		if (name.equals(INSTR_ASSIGN)) {
			return parseInstrAssign(loc, array);
		} else if (name.equals(INSTR_CALL)) {
			return parseInstrCall(loc, array);
		} else if (name.equals(INSTR_LOAD)) {
			return parseInstrLoad(loc, array);
		} else if (name.equals(INSTR_PHI)) {
			return parseInstrPhi(loc, array);
		} else if (name.equals(INSTR_RETURN)) {
			return parseInstrReturn(loc, array);
		} else if (name.equals(INSTR_STORE)) {
			return parseInstrStore(loc, array);
		}

		throw new OrccRuntimeException("unknown instruction type: " + name);
	}

	private Var parseLocalVariable(JsonArray array) {
		String name = array.get(0).getAsString();
		boolean assignable = array.get(1).getAsBoolean();
		int index = array.get(2).getAsInt();
		Location location = parseLocation(array.get(3).getAsJsonArray());
		Type type = parseType(array.get(4));

		return IrFactory.eINSTANCE.createVar(location, type, name, assignable,
				index);
	}

	/**
	 * Parses the given list as a list of local vars. A {@link VarLocal} is a
	 * {@link Var} with an SSA index.
	 * 
	 * @param array
	 *            A list of JSON-encoded {@link VarLocal}.
	 * @return A {@link List}&lt;{@link VarLocal}&gt;.
	 */
	private void parseLocalVariables(List<Var> variables, JsonArray arrayVars) {
		for (JsonElement element : arrayVars) {
			JsonArray array = element.getAsJsonArray();

			Var varDef = parseLocalVariable(array);
			variables.add(varDef);

			// register the variable definition
			vars.put(file, varDef.getLocation(), varDef.getIndexedName(),
					varDef);
		}
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

			return IrFactory.eINSTANCE.createLocation(startLine, startCol,
					endCol);
		} else {
			return IrFactory.eINSTANCE.createLocation();
		}
	}

	/**
	 * Parses the given JSON array as a CFG node.
	 * 
	 * @param array
	 *            a JSON array
	 * @return a CFG node
	 */
	private Node parseNode(JsonArray array) {
		String name = array.get(0).getAsString();
		Location loc = parseLocation(array.get(1).getAsJsonArray());

		if (name.equals(NODE_BLOCK)) {
			return parseNodeBlock(loc, array);
		} else if (name.equals(NODE_IF)) {
			return parseNodeIf(loc, array);
		} else if (name.equals(NODE_WHILE)) {
			return parseNodeWhile(loc, array);
		}

		throw new OrccRuntimeException("unknown node type: " + name);
	}

	/**
	 * Parses the given JSON array as a NodeBlock.
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a NodeBlock
	 */
	private NodeBlock parseNodeBlock(Location loc, JsonArray array) {
		NodeBlock join = IrFactoryImpl.eINSTANCE.createNodeBlock();
		for (JsonElement element : array.get(2).getAsJsonArray()) {
			join.add(parseInstruction(element.getAsJsonArray()));
		}

		return join;
	}

	/**
	 * Parses the given JSON array as an NodeIf.
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return an NodeIf
	 */
	private NodeIf parseNodeIf(Location loc, JsonArray array) {
		Expression condition = parseExpr(array.get(2));
		List<Node> thenNodes = parseNodes(array.get(3).getAsJsonArray());
		List<Node> elseNodes = parseNodes(array.get(4).getAsJsonArray());
		NodeBlock joinNode = (NodeBlock) parseNode(array.get(5)
				.getAsJsonArray());

		NodeIf nodeIf = IrFactoryImpl.eINSTANCE.createNodeIf();
		nodeIf.setLocation(loc);
		nodeIf.setCondition(condition);
		nodeIf.getThenNodes().addAll(thenNodes);
		nodeIf.getElseNodes().addAll(elseNodes);
		nodeIf.setJoinNode(joinNode);

		return nodeIf;
	}

	/**
	 * Parses the given JSON array as a list of CFG nodes.
	 * 
	 * @param array
	 *            a JSON array
	 * @return a list of CFG nodes
	 */
	private List<Node> parseNodes(JsonArray array) {
		List<Node> nodes = new ArrayList<Node>(array.size());
		for (JsonElement element : array) {
			nodes.add(parseNode(element.getAsJsonArray()));
		}
		return nodes;
	}

	/**
	 * Parses the given JSON array as a NodeWhile.
	 * 
	 * @param loc
	 *            location information
	 * @param array
	 *            a JSON array
	 * @return a NodeWhile
	 */
	private NodeWhile parseNodeWhile(Location loc, JsonArray array) {
		Expression condition = parseExpr(array.get(2));
		List<Node> nodes = parseNodes(array.get(3).getAsJsonArray());
		NodeBlock joinNode = (NodeBlock) parseNode(array.get(4)
				.getAsJsonArray());

		NodeWhile node = IrFactoryImpl.eINSTANCE.createNodeWhile();
		node.setCondition(condition);
		node.getNodes().addAll(nodes);
		node.setJoinNode(joinNode);

		return node;
	}

	private Pattern parseInputPattern(JsonArray array) {
		Pattern pattern = IrFactory.eINSTANCE.createPattern();
		for (int i = 0; i < array.size(); i++) {
			JsonArray patternArray = array.get(i).getAsJsonArray();
			Port port = actor.getInput(patternArray.get(0).getAsString());

			int numTokens = patternArray.get(1).getAsInt();
			pattern.setNumTokens(port, numTokens);

			if (!patternArray.get(2).isJsonNull()) {
				Var peeked = parseLocalVariable(patternArray.get(2)
						.getAsJsonArray());
				pattern.setPeeked(port, peeked);
			}

			Var variable = parseLocalVariable(patternArray.get(3)
					.getAsJsonArray());
			pattern.setVariable(port, variable);

			// register the variable definition
			vars.put(file, variable.getLocation(), variable.getName(), variable);
		}

		return pattern;
	}

	private Pattern parseOutputPattern(JsonArray array) {
		Pattern pattern = IrFactory.eINSTANCE.createPattern();
		for (int i = 0; i < array.size(); i++) {
			JsonArray patternArray = array.get(i).getAsJsonArray();
			Port port = actor.getOutput(patternArray.get(0).getAsString());

			int numTokens = patternArray.get(1).getAsInt();
			pattern.setNumTokens(port, numTokens);

			if (!patternArray.get(2).isJsonNull()) {
				Var peeked = parseLocalVariable(patternArray.get(2)
						.getAsJsonArray());
				pattern.setPeeked(port, peeked);
			}

			Var variable = parseLocalVariable(patternArray.get(3)
					.getAsJsonArray());
			pattern.setVariable(port, variable);

			// register the variable definition
			vars.put(file, variable.getLocation(), variable.getName(), variable);
		}

		return pattern;
	}

	/**
	 * Parses the given JSON array as a list of ports.
	 * 
	 * @param array
	 *            an array of JSON-encoded ports
	 * @return an ordered map of ports
	 */
	@SuppressWarnings("unchecked")
	private void parsePorts(EStructuralFeature feature, JsonArray array) {
		for (JsonElement element : array) {
			JsonArray port = element.getAsJsonArray();

			Location location = parseLocation(port.get(0).getAsJsonArray());
			Type type = parseType(port.get(1));
			String name = port.get(2).getAsString();

			Port po = IrFactory.eINSTANCE.createPort(location, type, name);
			((List<Port>) actor.eGet(feature)).add(po);
		}
	}

	/**
	 * Parses a procedure.
	 * 
	 * @param array
	 *            a JSON array
	 * @return the procedure parsed
	 */
	private Procedure parseProc(JsonArray array) {
		String name = array.get(0).getAsString();
		boolean external = array.get(1).getAsBoolean();

		Location location = parseLocation(array.get(2).getAsJsonArray());
		Type returnType = parseType(array.get(3));

		Procedure procedure = IrFactory.eINSTANCE.createProcedure(name,
				location, returnType);
		procedure.setNative(external);

		vars = new Scope<String, Var>(vars, true);
		parseLocalVariables(procedure.getParameters(), array.get(4)
				.getAsJsonArray());
		vars = new Scope<String, Var>(vars, false);
		parseLocalVariables(procedure.getLocals(), array.get(5)
				.getAsJsonArray());

		List<Node> nodes = parseNodes(array.get(6).getAsJsonArray());
		procedure.getNodes().addAll(nodes);

		// go back to previous scope
		vars = vars.getParent().getParent();

		NodeImpl.resetLabelCount();

		return procedure;
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
	private Type parseType(JsonElement element) {
		if (element.isJsonPrimitive()) {
			String name = element.getAsString();
			if (name.equals(TypeBool.NAME)) {
				return IrFactory.eINSTANCE.createTypeBool();
			} else if (name.equals(TypeString.NAME)) {
				return IrFactory.eINSTANCE.createTypeString();
			} else if (name.equals(TypeVoid.NAME)) {
				return IrFactory.eINSTANCE.createTypeVoid();
			} else {
				throw new OrccRuntimeException("Unknown type: " + name);
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
				throw new OrccRuntimeException("Unknown type: " + name);
			}
		}

		throw new OrccRuntimeException("Invalid type definition: " + element);
	}

	private Use parseVarUse(JsonArray array) {
		Var varDef = getVariable(array);
		return IrFactory.eINSTANCE.createUse(varDef);
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
