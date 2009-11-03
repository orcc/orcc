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
import net.sf.orcc.frontend.parser.internal.C_ALLexer;
import net.sf.orcc.frontend.parser.internal.C_ALParser;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalParser;
import net.sf.orcc.frontend.schedule.ActionSorter;
import net.sf.orcc.frontend.schedule.FSMBuilder;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.IConst;
import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.INode;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.ActionScheduler;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.FSM;
import net.sf.orcc.ir.actor.StateVariable;
import net.sf.orcc.ir.actor.Tag;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.type.BoolType;
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
public class ALAstParser {

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
	private Scope<Variable> parameters;

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
	private Scope<Variable> stateVars;

	/**
	 * creates a new parser from the given file name.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public ALAstParser(String fileName) throws OrccException {
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
	 * @return an actor
	 * @throws IOException
	 * @throws RVCCalParseException
	 */
	public Actor parse() throws OrccException {
		try {
			Tree tree;
			File file = new File(this.file + ".cal");
			if (file.exists()) {
				CharStream stream = new ANTLRFileStream(file.toString());
				Lexer lexer = new RVCCalLexer(stream);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				RVCCalParser parser = new RVCCalParser(tokens);
				RVCCalParser.actor_return ret = parser.actor();
				tree = (Tree) ret.getTree();
			} else {
				file = new File(this.file + ".c");
				if (file.exists()) {
					CharStream stream = new ANTLRFileStream(file.toString());
					Lexer lexer = new C_ALLexer(stream);
					CommonTokenStream tokens = new CommonTokenStream(lexer);
					C_ALParser parser = new C_ALParser(tokens);
					C_ALParser.actor_return ret = parser.actor();
					tree = (Tree) ret.getTree();
				} else {
					throw new OrccException("unknown actor file extension");
				}
			}

			return parseActor(tree);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		} catch (RecognitionException e) {
			throw new OrccException("parse error", e);
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
	 * Parse the given tree as an actor. The method:
	 * <ul>
	 * <li>initializes fields</li>
	 * <li>parses ports</li>
	 * <li>parses parameters</li>
	 * <li>parse actor declarations</li>
	 * <li>sort actions by priority</li>
	 * <li>build FSM (if the actor contains an FSM)</li>
	 * </ul>
	 * 
	 * @param tree
	 *            an ANTLR tree whose root is ACTOR
	 * @return an actor
	 */
	private Actor parseActor(Tree tree) throws OrccException {
		String name = tree.getChild(1).getText();

		// parameters is top-level scope, state variables extend the scope

		// actions, procedures, priorities
		actions = new ActionList();
		procedures = new OrderedMap<Procedure>();
		priorities = new ArrayList<List<Tag>>();

		// parse ports and return them as ordered maps
		inputs = parsePorts(tree.getChild(3));
		outputs = parsePorts(tree.getChild(4));

		// parse parameters
		parameters = new Scope<Variable>();
		parseParameters(tree.getChild(2));

		// parse actor declarations
		stateVars = new Scope<Variable>(parameters, false);
		currentScope = stateVars;
		parseActorDecls(tree.getChild(5));

		// sort actions by priority
		sorter = new ActionSorter(actions);
		ActionList actions = sorter.applyPriority(priorities);

		// builds FSM
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
				parseStateVariable(child);
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
	 * Parses the given tree as a local parameter/local variable.
	 * 
	 * @param tree
	 *            a tree whose root is VARIABLE.
	 * @return a local variable
	 * @throws OrccException
	 */
	private LocalVariable parseLocalVariable(Tree tree) throws OrccException {
		IType type = parseType(tree.getChild(0));
		Tree nameTree = tree.getChild(1);
		Location location = parseLocation(nameTree);
		String name = nameTree.getText();
		boolean assignable = (tree.getChild(2).getType() == RVCCalLexer.ASSIGNABLE);

		if (tree.getChildCount() == 4) {
		}

		INode node = new EmptyNode(0, new Location());

		return new LocalVariable(assignable, 0, location, name, node, null,
				type);
	}

	/**
	 * Parses the given tree as a list of parameters. Each child of the tree is
	 * parsed as a parameter represented by a {@link GlobalVariable}, and added
	 * to {@link #parameters}.
	 * 
	 * @param tree
	 *            a tree
	 */
	private void parseParameters(Tree tree) throws OrccException {
		int numParameters = tree.getChildCount();
		for (int i = 0; i < numParameters; i++) {
			Tree child = tree.getChild(i);
			IType type = parseType(child.getChild(0));
			Tree nameTree = child.getChild(1);
			Location location = parseLocation(nameTree);
			String name = nameTree.getText();

			IExpr init = null;
			if (child.getChildCount() == 3) {
				init = exprParser.parseExpression(child.getChild(2));
			}

			GlobalVariable globalVariable = new GlobalVariable(location, type,
					name, init);
			parameters.add(file, location, name, globalVariable);
		}
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
			ports.add(file, location, name, port);
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

	private void parseProcedure(Tree tree) throws OrccException {
		Scope<Variable> parameters = new Scope<Variable>(currentScope, true);
		currentScope = parameters;
		Tree nameTree = tree.getChild(0);
		String name = nameTree.getText();
		Location location = parseLocation(nameTree);

		Tree child = tree.getChild(1);
		int numChildren = child.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			LocalVariable parameter = parseLocalVariable(child.getChild(i));
			parameters.add(file, parameter.getLocation(), parameter.getName(),
					parameter);
		}

		Scope<Variable> variables = new Scope<Variable>(currentScope, false);
		currentScope = variables;
		child = tree.getChild(2);
		numChildren = child.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			LocalVariable parameter = parseLocalVariable(child.getChild(i));
			parameters.add(file, parameter.getLocation(), parameter.getName(),
					parameter);
		}

		Procedure procedure = new Procedure(name, false, location,
				new VoidType(), parameters, variables, null);

		procedures.add(file, location, name, procedure);
		currentScope = currentScope.getParent().getParent();
	}

	/**
	 * Parses the given tree as a state variable. The state variable is added to
	 * {@link #stateVars}.
	 * 
	 * @param tree
	 *            a tree whose root is STATE_VAR
	 * @throws OrccException
	 */
	private void parseStateVariable(Tree tree) throws OrccException {
		IType type = parseType(tree.getChild(0));
		Tree nameTree = tree.getChild(1);
		Location location = parseLocation(nameTree);
		String name = nameTree.getText();
		boolean assignable = (tree.getChild(2).getType() == RVCCalLexer.ASSIGNABLE);

		IConst init = null;
		if (tree.getChildCount() == 4) {
		}

		StateVariable stateVariable = new StateVariable(location, type, name,
				assignable, init);
		stateVars.add(file, location, name, stateVariable);
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
					&& attr.getChild(0).getText().equals(AST.SIZE)) {
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
					&& attr.getChild(0).getText().equals(AST.TYPE)) {
				return parseType(attr.getChild(1));
			}
		}

		// type attribute not found, and no default type given => error
		throw new OrccException(file, location, "missing \"type\" attribute");
	}

	/**
	 * Prints the graph that represents the FSM.
	 * 
	 * @param file
	 *            output file
	 * @throws OrccException
	 *             if something goes wrong (most probably I/O error)
	 */
	public void printFSMGraph(File file) throws OrccException {
		try {
			if (fsmBuilder != null) {
				fsmBuilder.printGraph(new FileOutputStream(file));
			}
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	/**
	 * Prints the graph that represents the priorities.
	 * 
	 * @param file
	 *            output file
	 * @throws OrccException
	 *             if something goes wrong (most probably I/O error)
	 */
	public void printPriorityGraph(File file) throws OrccException {
		try {
			sorter.printGraph(new FileOutputStream(file));
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

}
