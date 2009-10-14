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
package net.sf.orcc.frontend.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.ActionList;
import net.sf.orcc.frontend.Scope;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalParser;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.StateVar;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.consts.AbstractConst;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 * Parses an actor written in RVC-CAL, and translates the resulting tree to an
 * AST.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RVCCalASTParser {

	public static void main(String[] args) throws OrccException {
		for (String arg : args) {
			new RVCCalASTParser(arg).parse();
		}
	}

	/**
	 * list of actions
	 */
	private ActionList actions;

	/**
	 * Contains the current scope of variables
	 */
	private Scope<VarDef> currentScope;

	/**
	 * absolute name of input file
	 */
	private String file;

	/**
	 * scope of input ports
	 */
	private Scope<VarDef> inputs;

	/**
	 * scope of output ports
	 */
	private Scope<VarDef> outputs;

	/**
	 * list of actor parameters
	 */
	private List<VarDef> parameters;

	/**
	 * Contains the current scope of procedures
	 */
	private Scope<Procedure> procedures;

	/**
	 * list of state variables
	 */
	private List<StateVar> stateVars;

	/**
	 * creates a new parser from the given file name.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public RVCCalASTParser(String fileName) throws OrccException {
		try {
			this.file = new File(fileName).getCanonicalPath();
		} catch (IOException e) {
			String msg = "could not solve the path \"" + fileName + "\"";
			throw new OrccException(msg, e);
		}
	}

	/**
	 * parses the file this parser was created with and return an actor.
	 * 
	 * @return
	 * @throws IOException
	 * @throws RVCCalParseException
	 */
	public Actor parse() throws OrccException {
		try {
			CharStream stream = new ANTLRFileStream(file);
			Lexer lexer = new RVCCalLexer(stream);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			RVCCalParser parser = new RVCCalParser(tokens);
			RVCCalParser.actor_return ret = parser.actor();
			return parseActor((Tree) ret.getTree());
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		} catch (RecognitionException e) {
			throw new OrccException("parse error", e);
		} catch (OrccException e) {
			throw e;
		}
	}

	private Action parseAction(Tree tree) {
		Tree tagTree = tree.getChild(0);
		Location location = parseLocation(tree);
		List<String> tag = parseActionTag(tagTree);
		return new Action(location, tag, null, null, null, null);
	}

	private List<String> parseActionTag(Tree tree) {
		List<String> tag = new ArrayList<String>();
		int n = tree.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree child = tree.getChild(i);
			tag.add(child.getText());
		}

		return tag;
	}

	/**
	 * parse tree. children are: actor, id, parameters, inputs, outputs
	 * 
	 * @param tree
	 * @return an actor
	 */
	private Actor parseActor(Tree tree) throws OrccException {
		String name = tree.getChild(1).getText();
		currentScope = new Scope<VarDef>();
		procedures = new Scope<Procedure>();

		// TODO remove when scopeProcedures are actually used
		procedures.toString();

		parameters = parseVarDefs(currentScope, tree.getChild(2));

		inputs = new Scope<VarDef>();
		parseVarDefs(inputs, tree.getChild(3));

		outputs = new Scope<VarDef>();
		parseVarDefs(outputs, tree.getChild(4));

		actions = new ActionList();
		stateVars = new ArrayList<StateVar>();
		parseActorDecls(tree.getChild(5));

		return new Actor(name, file, parameters, inputs.getList(), outputs
				.getList(), stateVars, null, null, null, null, null);
	}

	/**
	 * parse actor declarations.
	 * 
	 * @param actorDecls
	 * @throws OrccException
	 */
	private void parseActorDecls(Tree actorDecls) throws OrccException {
		// actor declarations are in a new scope
		currentScope = new Scope<VarDef>(currentScope);

		int n = actorDecls.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree child = actorDecls.getChild(i);
			String declType = child.getText();
			if (declType.equals("action")) {
				Action action = parseAction(child);
				actions.register(file, action.getLocation(), action.getTag(),
						action);
			} else if (declType.equals("PRIORITY")) {
				parsePriority(child);
			} else if (declType.equals("SCHEDULE")) {
				parseSchedule(child);
			} else if (declType.equals("STATE_VAR")) {
				StateVar stateVar = parseStateVar(child);
				VarDef varDef = stateVar.getDef();
				currentScope.register(file, varDef.getLoc(), varDef.getName(),
						varDef);
				stateVars.add(stateVar);
			} else {
				throw new OrccException("not yet implemented");
			}
		}
	}

	private IExpr parseExpression(Tree expr) throws OrccException {
		String type = expr.getText();
		if (type.equals("EXPR_BOOL")) {
			expr = expr.getChild(0);
			boolean value = Boolean.parseBoolean(expr.getText());
			return new BooleanExpr(parseLocation(expr), value);
		} else if (type.equals("EXPR_FLOAT")) {
			throw new OrccException("not yet implemented!");
		} else if (type.equals("EXPR_INT")) {
			expr = expr.getChild(0);
			int value = Integer.parseInt(expr.getText());
			return new IntExpr(parseLocation(expr), value);
		} else if (type.equals("EXPR_STRING")) {
			expr = expr.getChild(0);
			return new StringExpr(parseLocation(expr), expr.getText());
		} else if (type.equals("EXPR_VAR")) {
			expr = expr.getChild(0);
			VarUse varUse = new VarUse(null, null);
			return new VarExpr(parseLocation(expr), varUse);
		} else {
			throw new OrccException("not yet implemented");
		}
	}

	/**
	 * Returns a location from a tree that contains a real token.
	 * 
	 * @param tree
	 *            a tree
	 * @return a location
	 */
	private Location parseLocation(Tree tree) {
		int lineNumber = tree.getLine();
		int startColumn = tree.getCharPositionInLine();
		int endColumn = startColumn + tree.getText().length();

		return new Location(lineNumber, startColumn, endColumn);
	}

	private void parsePriority(Tree tree) {
		// TODO parse priority

	}

	private void parseSchedule(Tree tree) {
		// TODO parse schedule

	}

	private StateVar parseStateVar(Tree stateVar) throws OrccException {
		boolean assignable = stateVar.getChild(2).getText()
				.equals("ASSIGNABLE");
		VarDef def = parseVarDef(stateVar, assignable, true, 0, null);
		AbstractConst init = null;
		if (stateVar.getChildCount() == 4) {
		}

		return new StateVar(def, init);
	}

	/**
	 * tree = TYPE, children = type name (ID), TYPE_ATTRS
	 * 
	 * @param tree
	 * @return
	 * @throws RVCCalParseException
	 */
	private IType parseType(Tree tree) throws OrccException {
		Tree typeTree = tree.getChild(0);
		Location location = parseLocation(typeTree);
		String typeName = typeTree.getText();
		IType type;

		if (typeName.equals(BoolType.NAME)) {
			type = new BoolType();
		} else if (typeName.equals(StringType.NAME)) {
			type = new StringType();
		} else if (typeName.equals(VoidType.NAME)) {
			type = new VoidType();
		} else if (typeName.equals(IntType.NAME)) {
			IExpr size = parseTypeAttributeSize(location, tree.getChild(1), 32);
			type = new IntType(size);
		} else if (typeName.equals(UintType.NAME)) {
			IExpr size = parseTypeAttributeSize(location, tree.getChild(1), 32);
			type = new UintType(size);
		} else if (typeName.equals(ListType.NAME)) {
			IExpr size = parseTypeAttributeSize(location, tree.getChild(1),
					null);
			IType subType = parseTypeAttributeType(location, tree.getChild(1));
			type = new ListType(size, subType);
		} else {
			throw new OrccException(file, location, "Unknown type: " + typeName);
		}

		return type;
	}

	private IExpr parseTypeAttributeSize(Location location, Tree typeAttrs,
			Integer defaultSize) throws OrccException {
		int n = typeAttrs.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree attr = typeAttrs.getChild(i);
			if (attr.getText().equals("EXPR")
					&& attr.getChild(0).getText().equals("size")) {
				return parseExpression(attr.getChild(1));
			}
		}

		// if there is a default size, return it
		if (defaultSize == null) {
			// size attribute not found, and no default size given => error
			throw new OrccException(file, location,
					"missing \"size\" attribute");
		} else {
			return new IntExpr(location, defaultSize);
		}
	}

	private IType parseTypeAttributeType(Location location, Tree typeAttrs)
			throws OrccException {
		int n = typeAttrs.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree attr = typeAttrs.getChild(i);
			if (attr.getText().equals("TYPE")
					&& attr.getChild(0).getText().equals("type")) {
				return parseType(attr.getChild(1));
			}
		}

		// type attribute not found, and no default type given => error
		throw new OrccException(file, location, "missing \"type\" attribute");
	}

	/**
	 * tree = PORT/PARAMETER, children = TYPE, ID
	 * 
	 * @param tree
	 * @param assignable
	 * @param global
	 * @param index
	 * @param suffix
	 * @return
	 */
	private VarDef parseVarDef(Tree tree, boolean assignable, boolean global,
			int index, Integer suffix) throws OrccException {
		IType type = parseType(tree.getChild(0));
		Tree nameTree = tree.getChild(1);
		String name = nameTree.getText();

		Location loc = parseLocation(nameTree);

		List<VarUse> references = new ArrayList<VarUse>();
		AbstractNode node = new EmptyNode(0, new Location());

		return new VarDef(assignable, global, index, loc, name, node,
				references, suffix, type);
	}

	/**
	 * children = vardef 1, ..., vardef n
	 * 
	 * @param scope
	 *            a scope
	 * @param tree
	 *            a tree
	 */
	private List<VarDef> parseVarDefs(Scope<VarDef> scope, Tree tree)
			throws OrccException {
		List<VarDef> varDefs = new ArrayList<VarDef>();
		int numPorts = tree.getChildCount();
		for (int i = 0; i < numPorts; i++) {
			Tree child = tree.getChild(i);
			VarDef varDef = parseVarDef(child, false, true, 0, null);
			scope.register(file, varDef.getLoc(), varDef.getName(), varDef);
			varDefs.add(varDef);
		}

		return varDefs;
	}

}
