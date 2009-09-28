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
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalParser;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.StateVar;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.consts.AbstractConst;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.type.AbstractType;
import net.sf.orcc.ir.type.BoolType;
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
		new RVCCalASTParser(args[0]).parse();
	}

	private String file;

	/**
	 * input ports
	 */
	private List<VarDef> inputs;

	/**
	 * output ports
	 */
	private List<VarDef> outputs;

	/**
	 * parameters
	 */
	private List<VarDef> parameters;

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
		}
	}

	/**
	 * children = actor, id, parameters, inputs, outputs
	 * 
	 * @param tree
	 * @return
	 */
	private Actor parseActor(Tree tree) throws OrccException {
		String name = tree.getChild(1).getText();
		parameters = parseVarDefs(tree.getChild(2));
		inputs = parseVarDefs(tree.getChild(3));
		outputs = parseVarDefs(tree.getChild(4));
		parseActorDecls(tree.getChild(5));
		return new Actor(name, file, parameters, inputs, outputs, stateVars,
				null, null, null, null, null);
	}

	private void parseActorDecls(Tree actorDecls) throws OrccException {
		stateVars = new ArrayList<StateVar>();
		int n = actorDecls.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree child = actorDecls.getChild(i);
			String declType = child.getText();
			if (declType.equals("STATE_VAR")) {
				stateVars.add(parseStateVar(child));
			}
		}
	}

	private IExpr parseExpression(Tree expr) {
		// TODO parse expression
		return null;
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
	private AbstractType parseType(Tree tree) throws OrccException {
		Tree typeTree = tree.getChild(0);
		Location location = parseLocation(typeTree);
		String typeName = typeTree.getText();
		AbstractType type;

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
			AbstractType subType = parseTypeAttributeType(location, tree
					.getChild(1));
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
			if (attr.getText().equals("EXPR")) {
				return parseExpression(attr);
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

	private AbstractType parseTypeAttributeType(Location location,
			Tree typeAttrs) throws OrccException {
		int n = typeAttrs.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree attr = typeAttrs.getChild(i);
			if (attr.getText().equals("TYPE")) {
				return parseType(attr.getChild(0));
			}
		}

		// size attribute not found, and no default size given => error
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
		AbstractType type = parseType(tree.getChild(0));
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
	 * @param tree
	 * @return
	 */
	private List<VarDef> parseVarDefs(Tree tree) throws OrccException {
		List<VarDef> varDefs = new ArrayList<VarDef>();
		int numPorts = tree.getChildCount();
		for (int i = 0; i < numPorts; i++) {
			Tree child = tree.getChild(i);
			varDefs.add(parseVarDef(child, false, true, 0, null));
		}

		return varDefs;
	}

}
