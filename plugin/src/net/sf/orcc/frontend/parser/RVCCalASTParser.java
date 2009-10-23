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

import static net.sf.orcc.frontend.parser.Util.parseActionTag;
import static net.sf.orcc.frontend.parser.Util.parseLocation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.common.LocalUse;
import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.Port;
import net.sf.orcc.common.Variable;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalParser;
import net.sf.orcc.frontend.schedule.ActionSorter;
import net.sf.orcc.frontend.schedule.FSMBuilder;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.ActionScheduler;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.FSM;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.StateVariable;
import net.sf.orcc.ir.actor.Tag;
import net.sf.orcc.ir.consts.IConst;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

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

	/**
	 * list of actions
	 */
	private ActionList actions;

	/**
	 * Contains the current scope of variables
	 */
	private Scope<Variable> currentScope;

	/**
	 * expression parser.
	 */
	private ExprParser exprParser;

	/**
	 * absolute name of input file
	 */
	private String file;

	/**
	 * FSM builder.
	 */
	private FSMBuilder fsmBuilder;

	/**
	 * scope of input ports
	 */
	private OrderedMap<Port> inputs;

	/**
	 * scope of output ports
	 */
	private OrderedMap<Port> outputs;

	/**
	 * list of actor parameters
	 */
	private List<LocalVariable> parameters;

	/**
	 * Priorities are a list of priority relations. A priority relation is a
	 * partial order between several tags.
	 */
	private List<List<Tag>> priorities;

	/**
	 * Contains the current scope of procedures
	 */
	private OrderedMap<Procedure> procedures;

	/**
	 * action sorter
	 */
	private ActionSorter sorter;

	/**
	 * list of state variables
	 */
	private List<StateVariable> stateVars;

	/**
	 * creates a new parser from the given file name.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public RVCCalASTParser(String fileName) throws OrccException {
		try {
			this.file = new File(fileName).getCanonicalPath();

			exprParser = new ExprParser();
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

	private void parseAction(Tree tree) {
		Tree tagTree = tree.getChild(0);
		Location location = parseLocation(tree);
		Tag tag = parseActionTag(tagTree);
		Action action = new Action(location, tag, null, null, null, null);
		actions.add(action);
	}

	/**
	 * parse tree. children are: actor, id, parameters, inputs, outputs
	 * 
	 * @param tree
	 * @return an actor
	 */
	private Actor parseActor(Tree tree) throws OrccException {
		String name = tree.getChild(1).getText();
		currentScope = new Scope<Variable>();

		actions = new ActionList();
		parameters = parseVarDefs(currentScope, tree.getChild(2));
		procedures = new OrderedMap<Procedure>();
		inputs = parsePorts(tree.getChild(3));
		outputs = parsePorts(tree.getChild(4));
		priorities = new ArrayList<List<Tag>>();
		stateVars = new ArrayList<StateVariable>();

		parseActorDecls(tree.getChild(5));

		sorter = new ActionSorter(actions);
		ActionList actions = sorter.applyPriority(priorities);

		FSM fsm = (fsmBuilder == null) ? null : fsmBuilder.buildFSM(actions);
		ActionScheduler scheduler = new ActionScheduler(actions.getList(), fsm);

		return new Actor(name, file, parameters, inputs, outputs, stateVars,
				procedures, null, null, scheduler, null);
	}

	/**
	 * parse actor declarations.
	 * 
	 * @param actorDecls
	 * @throws OrccException
	 */
	private void parseActorDecls(Tree actorDecls) throws OrccException {
		// actor declarations are in a new scope
		currentScope = new Scope<Variable>(currentScope);

		int n = actorDecls.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree child = actorDecls.getChild(i);
			switch (child.getType()) {
			case RVCCalLexer.ACTION:
				parseAction(child);
				break;
			case RVCCalLexer.FUNCTION:
				parseFunction(child);
				break;
			case RVCCalLexer.INITIALIZE:
				parseInitialize(child);
				break;
			case RVCCalLexer.PRIORITY:
				parsePriority(child);
				break;
			case RVCCalLexer.PROCEDURE:
				parseProcedure(child);
				break;
			case RVCCalLexer.SCHEDULE:
				// there is at most one schedule in an actor
				fsmBuilder = new FSMBuilder(child);
				break;
			case RVCCalLexer.STATE_VAR: {
				StateVariable stateVar = parseStateVar(child);
				currentScope.register(file, stateVar.getLocation(), stateVar
						.getName(), stateVar);
				stateVars.add(stateVar);
				break;
			}
			default:
				throw new OrccException("not yet implemented");
			}
		}
	}

	private void parseFunction(Tree tree) {
	}

	private void parseInitialize(Tree tree) {
	}

	/**
	 * Returns an ordered map of ports parsed from the given tree.
	 * 
	 * @param tree
	 *            a tree
	 * @return an ordered map of ports
	 */
	private OrderedMap<Port> parsePorts(Tree tree) throws OrccException {
		OrderedMap<Port> ports = new OrderedMap<Port>();
		int numPorts = tree.getChildCount();
		for (int i = 0; i < numPorts; i++) {
			Tree child = tree.getChild(i);

			IType type = parseType(child.getChild(0));
			Tree nameTree = child.getChild(1);
			String name = nameTree.getText();
			Location location = parseLocation(nameTree);

			Port port = new Port(location, type, name);
			ports.register(file, location, name, port);
		}

		return ports;
	}

	/**
	 * Parses a priority declaration.
	 * 
	 * @param tree
	 *            a tree whose root node is PRIORITY
	 */
	private void parsePriority(Tree tree) {
		int numInequalities = tree.getChildCount();
		for (int i = 0; i < numInequalities; i++) {
			Tree inequality = tree.getChild(i);
			int numTags = inequality.getChildCount();
			List<Tag> list = new ArrayList<Tag>(numTags);
			for (int j = 0; j < numTags; j++) {
				Tag tag = parseActionTag(inequality.getChild(j));
				list.add(tag);
			}
			priorities.add(list);
		}
	}

	private void parseProcedure(Tree tree) {
	}

	private StateVariable parseStateVar(Tree stateVar) throws OrccException {
		boolean assignable = (stateVar.getChild(2).getType() == RVCCalLexer.ASSIGNABLE);
		LocalVariable def = parseVarDef(stateVar, assignable, true, 0, null);
		IConst init = null;
		if (stateVar.getChildCount() == 4) {
		}

		return new StateVariable(def.getLocation(), def.getType(), def
				.getName(), init);
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
			if (attr.getType() == RVCCalLexer.EXPR
					&& attr.getChild(0).getText().equals("size")) {
				return exprParser.parseExpression(attr.getChild(1));
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
			if (attr.getType() == RVCCalLexer.TYPE
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
	private LocalVariable parseVarDef(Tree tree, boolean assignable,
			boolean global, int index, Integer suffix) throws OrccException {
		IType type = parseType(tree.getChild(0));
		Tree nameTree = tree.getChild(1);
		String name = nameTree.getText();

		Location loc = parseLocation(nameTree);

		List<LocalUse> references = new ArrayList<LocalUse>();
		AbstractNode node = new EmptyNode(0, new Location());

		return new LocalVariable(assignable, global, index, loc, name, node,
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
	private List<LocalVariable> parseVarDefs(OrderedMap<Variable> scope,
			Tree tree) throws OrccException {
		List<LocalVariable> varDefs = new ArrayList<LocalVariable>();
		int numPorts = tree.getChildCount();
		for (int i = 0; i < numPorts; i++) {
			Tree child = tree.getChild(i);
			LocalVariable varDef = parseVarDef(child, false, true, 0, null);
			scope
					.register(file, varDef.getLocation(), varDef.getName(),
							varDef);
			varDefs.add(varDef);
		}

		return varDefs;
	}

	/**
	 * Prints the graph that represents the FSM.
	 * 
	 * @param fileName
	 *            output file name
	 * @throws OrccException
	 *             if something goes wrong (most probably I/O error)
	 */
	public void printFSMGraph(String fileName) throws OrccException {
		try {
			if (fsmBuilder != null) {
				fsmBuilder.printGraph(new FileOutputStream(fileName));
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	/**
	 * Prints the graph that represents the priorities.
	 * 
	 * @param fileName
	 *            output file name
	 * @throws OrccException
	 *             if something goes wrong (most probably I/O error)
	 */
	public void printPriorityGraph(String fileName) throws OrccException {
		try {
			sorter.printGraph(new FileOutputStream(fileName));
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
