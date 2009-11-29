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
import net.sf.orcc.frontend.parser.internal.ALBaseLexer;
import net.sf.orcc.frontend.parser.internal.C_ALLexer;
import net.sf.orcc.frontend.parser.internal.C_ALParser;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalParser;
import net.sf.orcc.frontend.schedule.ActionSorter;
import net.sf.orcc.frontend.schedule.FSMBuilder;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.BinOpSeqParser;
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
	 * This class defines a block. A block contains variables and nodes.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class Block {

		private List<CFGNode> nodes;

		private Scope<Variable> variables;

		/**
		 * Creates a new block with the given variables and nodes.
		 * 
		 * @param variables
		 *            a scope of variables
		 * @param nodes
		 *            a list of CFG nodes
		 */
		public Block(Scope<Variable> variables, List<CFGNode> nodes) {
			this.variables = variables;
			this.nodes = nodes;
		}

		public List<CFGNode> getNodes() {
			return nodes;
		}

		public Scope<Variable> getVariables() {
			return variables;
		}

		@Override
		public String toString() {
			return nodes.toString();
		}

	}

	/**
	 * This class defines a parser that can parse AL expressions and translate
	 * them to IR expressions.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ExprParser {

		/**
		 * Returns the binary operator that match the type of the given tree.
		 * 
		 * @param op
		 *            a Tree that represents an operator
		 * @return a binary operator
		 * @throws OrccException
		 *             if the operator is not valid
		 */
		private BinaryOp parseBinaryOp(Tree op) throws OrccException {
			switch (op.getType()) {
			case ALBaseLexer.LOGIC_AND:
				return BinaryOp.LOGIC_AND;
			case ALBaseLexer.BITAND:
				return BinaryOp.BITAND;
			case ALBaseLexer.BITOR:
				return BinaryOp.BITOR;
			case ALBaseLexer.DIV:
				return BinaryOp.DIV;
			case ALBaseLexer.DIV_INT:
				return BinaryOp.DIV_INT;
			case ALBaseLexer.EQ:
				return BinaryOp.EQ;
			case ALBaseLexer.EXP:
				return BinaryOp.EXP;
			case ALBaseLexer.GE:
				return BinaryOp.GE;
			case ALBaseLexer.GT:
				return BinaryOp.GT;
			case ALBaseLexer.LE:
				return BinaryOp.LE;
			case ALBaseLexer.LT:
				return BinaryOp.LT;
			case ALBaseLexer.MINUS:
				return BinaryOp.MINUS;
			case ALBaseLexer.MOD:
				return BinaryOp.MOD;
			case ALBaseLexer.NE:
				return BinaryOp.NE;
			case ALBaseLexer.LOGIC_OR:
				return BinaryOp.LOGIC_OR;
			case ALBaseLexer.PLUS:
				return BinaryOp.PLUS;
			case ALBaseLexer.SHIFT_LEFT:
				return BinaryOp.SHIFT_LEFT;
			case ALBaseLexer.SHIFT_RIGHT:
				return BinaryOp.SHIFT_RIGHT;
			case ALBaseLexer.TIMES:
				return BinaryOp.TIMES;
			default:
				throw new OrccException("Unknown operator: " + op.getText());
			}
		}

		/**
		 * Parses a sequence of binary operations represented by an ANTLR tree,
		 * and transforms it to a binary expression tree using the operators'
		 * precedences.
		 * 
		 * @param expr
		 *            a tree that contains a binary operation sequence
		 * @return an expression
		 * @throws OrccException
		 */
		private Expression parseBinOpSeq(Tree expr) throws OrccException {
			Tree treeExprs = expr.getChild(0);
			int numExprs = treeExprs.getChildCount();
			List<Expression> expressions = new ArrayList<Expression>(numExprs);
			for (int i = 0; i < numExprs; i++) {
				expressions.add(parseExpression(treeExprs.getChild(i)));
			}

			Tree treeOps = expr.getChild(1);
			int numOps = treeOps.getChildCount();
			List<BinaryOp> operators = new ArrayList<BinaryOp>(numOps);
			for (int i = 0; i < numOps; i++) {
				operators.add(parseBinaryOp(treeOps.getChild(i)));
			}

			return BinOpSeqParser.parse(expressions, operators);
		}

		/**
		 * Parses the given tree as an expression. This method is package
		 * because it should be called from {@link RVCCalASTParser} only.
		 * 
		 * @param expr
		 *            a tree that contains an expression
		 * @return an {@link Expression}.
		 * @throws OrccException
		 */
		public Expression parseExpression(Tree expr) throws OrccException {
			switch (expr.getType()) {
			case ALBaseLexer.EXPR_BINARY:
				return parseBinOpSeq(expr);
			case ALBaseLexer.EXPR_BOOL: {
				expr = expr.getChild(0);
				boolean value = Boolean.parseBoolean(expr.getText());
				return new BoolExpr(parseLocation(expr), value);
			}
			case ALBaseLexer.EXPR_FLOAT:
				throw new OrccException("not yet implemented!");
			case ALBaseLexer.EXPR_INT:
				expr = expr.getChild(0);
				int value = Integer.parseInt(expr.getText());
				return new IntExpr(parseLocation(expr), value);
			case ALBaseLexer.EXPR_STRING:
				expr = expr.getChild(0);
				return new StringExpr(parseLocation(expr), expr.getText());
			case ALBaseLexer.EXPR_VAR:
				return parseExprVar(expr.getChild(0));
			default:
				throw new OrccException("not yet implemented");
			}
		}

		private Expression parseExprVar(Tree tree) throws OrccException {
			Location location = parseLocation(tree);

			Variable variable = currentScope.get(tree.getText());
			if (variable == null) {
				throw new OrccException(file, location, "unknown variable: "
						+ variable);
			}
			Use localUse = new Use(variable);
			return new VarExpr(location, localUse);
		}

	}

	private class StatementParser {

		private Block parseBlock(Tree tree, int start) throws OrccException {
			Scope<Variable> variables = new Scope<Variable>(currentScope, false);
			currentScope = variables;
			parseLocalVariables(variables, tree.getChild(start));

			// parse nodes
			List<CFGNode> nodes = null;
			currentScope = currentScope.getParent();

			return new Block(variables, nodes);
		}

		/**
		 * Parses the given tree as a local parameter/local variable.
		 * 
		 * @param tree
		 *            a tree whose root is VARIABLE.
		 * @return a local variable
		 * @throws OrccException
		 */
		private LocalVariable parseLocalVariable(Tree tree)
				throws OrccException {
			Type type = typeParser.parseType(tree.getChild(0));
			Tree nameTree = tree.getChild(1);
			Location location = parseLocation(nameTree);
			String name = nameTree.getText();
			boolean assignable = (tree.getChild(2).getType() == RVCCalLexer.ASSIGNABLE);

			if (tree.getChildCount() == 4) {
				exprParser.parseExpression(tree.getChild(3));
			}

			Instruction instruction = null;
			return new LocalVariable(assignable, 0, location, name,
					instruction, null, type);
		}

		private void parseLocalVariables(Scope<Variable> variables, Tree tree)
				throws OrccException {
			int numChildren = tree.getChildCount();
			for (int i = 0; i < numChildren; i++) {
				LocalVariable variable = parseLocalVariable(tree.getChild(i));
				variables.add(file, variable.getLocation(), variable.getName(),
						variable);
			}
		}
	}

	/**
	 * This class defines a parser that can parse AL types and translate them to
	 * IR types;
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class TypeParser {

		/**
		 * Parses the given tree as an {@link Type}.
		 * 
		 * @param tree
		 *            a tree whose root is TYPE or TYPE_LIST.
		 * @return a type
		 * @throws OrccException
		 */
		private Type parseType(Tree tree) throws OrccException {
			if (tree.getType() == ALBaseLexer.TYPE) {
				return parseTYPE(tree);
			} else if (tree.getType() == ALBaseLexer.TYPE_LIST) {
				return parseTypeList(tree);
			} else {
				throw new OrccException("unknown type node: " + tree.getType());
			}
		}

		private Type parseTYPE(Tree tree) throws OrccException {
			Tree typeTree = tree.getChild(0);
			Location location = parseLocation(typeTree);
			String typeName = typeTree.getText();

			if (typeName.equals(BoolType.NAME)) {
				return new BoolType();
			} else if (typeName.equals(StringType.NAME)) {
				return new StringType();
			} else if (typeName.equals(VoidType.NAME)) {
				return new VoidType();
			} else if (typeName.equals(IntType.NAME)) {
				Expression size = parseTypeAttributeSize(location, tree, 32);
				return new IntType(size);
			} else if (typeName.equals(UintType.NAME)) {
				Expression size = parseTypeAttributeSize(location, tree, 32);
				return new UintType(size);
			} else if (typeName.equals(ListType.NAME)) {
				Expression size = parseTypeAttributeSize(location, tree, null);
				Type subType = parseTypeAttributeType(location, tree);
				return new ListType(size, subType);
			} else if (typeName.equals("float") || typeName.equals("unsigned")) {
				System.out.println("TODO: " + typeName);
				return new VoidType();
			} else {
				throw new OrccException(file, location, "Unknown type: "
						+ typeName);
			}
		}

		private Expression parseTypeAttributeSize(Location location, Tree type,
				Integer defaultSize) throws OrccException {
			int n = type.getChildCount();
			for (int i = 1; i < n; i++) {
				Tree attr = type.getChild(i);
				if (attr.getType() == RVCCalLexer.EXPR) {
					if (attr.getChildCount() > 1
							&& !attr.getChild(1).getText().equals(AST.SIZE)) {
						throw new OrccException(file, location, "unknown \""
								+ attr.getChild(1).getText() + "\" attribute");
					}

					return exprParser.parseExpression(attr.getChild(0));
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

		private Type parseTypeAttributeType(Location location, Tree typeAttrs)
				throws OrccException {
			int n = typeAttrs.getChildCount();
			for (int i = 1; i < n; i++) {
				Tree attr = typeAttrs.getChild(i);
				if (attr.getType() == RVCCalLexer.TYPE) {
					if (attr.getChildCount() > 1
							&& !attr.getChild(1).getText().equals(AST.TYPE)) {
						throw new OrccException(file, location, "unknown \""
								+ attr.getChild(1).getText() + "\" attribute");
					}

					return parseType(attr.getChild(0));
				}
			}

			// type attribute not found, and no default type given => error
			throw new OrccException(file, location,
					"missing \"type\" attribute");
		}

		/**
		 * Parses a TYPE_LIST tree and return a {@link ListType}.
		 * 
		 * @param tree
		 *            a tree whose root is TYPE_LIST
		 * @return an {@link Type}
		 * @throws OrccException
		 */
		private Type parseTypeList(Tree tree) throws OrccException {
			Type type = parseType(tree.getChild(0));
			for (int i = tree.getChildCount() - 1; i > 0; i--) {
				Expression size = exprParser.parseExpression(tree.getChild(i));
				type = new ListType(size, type);
			}

			return type;
		}
	}

	/**
	 * the action parser
	 */
	private ActionParser actionParser;

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
	private final ExprParser exprParser;

	/**
	 * absolute name of input file
	 */
	private final String file;

	/**
	 * FSM builder.
	 */
	private FSMBuilder fsmBuilder;

	/**
	 * list of initialize actions
	 */
	private ActionList initializes;

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
	 * statement parser
	 */
	private final StatementParser stmtParser;

	/**
	 * type parser
	 */
	private final TypeParser typeParser;

	/**
	 * creates a new parser from the given file name.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public ALAstParser(String fileName) throws OrccException {
		try {
			this.file = new File(fileName).getCanonicalPath();

			actionParser = new ActionParser(this);
			exprParser = new ExprParser();
			stmtParser = new StatementParser();
			typeParser = new TypeParser();
		} catch (IOException e) {
			String msg = "could not solve the path \"" + fileName + "\"";
			throw new OrccException(msg, e);
		}
	}

	/**
	 * Adds the given action to the action list of this AST parser.
	 * 
	 * @param action
	 *            an action
	 */
	public void add(Action action) {
		actions.add(action);
	}

	/**
	 * Returns the file this AST parser is parsing.
	 * 
	 * @return the file this AST parser is parsing
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Returns the current scope of this AST parser.
	 * 
	 * @return the current scope of this AST parser
	 */
	public Scope<Variable> getScope() {
		return currentScope;
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

		// create actions, procedures, priorities
		actions = new ActionList();
		initializes = new ActionList();
		procedures = new OrderedMap<Procedure>();
		priorities = new ArrayList<List<Tag>>();

		// parse parameters
		parameters = new Scope<Variable>();
		currentScope = parameters;
		parseActorParameters(tree.getChild(2));

		// parse ports and return them as ordered maps
		inputs = parsePorts(tree.getChild(3));
		outputs = parsePorts(tree.getChild(4));

		// parse actor declarations
		stateVars = new Scope<Variable>(parameters, false);
		currentScope = stateVars;
		parseActorDeclarations(tree.getChild(5));

		// sort actions by priority
		sorter = new ActionSorter(actions);
		ActionList actions = sorter.applyPriority(priorities);

		// builds FSM
		FSM fsm = (fsmBuilder == null) ? null : fsmBuilder.buildFSM(actions);
		ActionScheduler scheduler = new ActionScheduler(actions.getList(), fsm);

		return new Actor(name, file, parameters, inputs, outputs, stateVars,
				procedures, actions.getList(), initializes.getList(),
				scheduler, null);
	}

	/**
	 * parse actor declarations.
	 * 
	 * @param actorDecls
	 * @throws OrccException
	 */
	private void parseActorDeclarations(Tree actorDecls) throws OrccException {
		int n = actorDecls.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree child = actorDecls.getChild(i);
			switch (child.getType()) {
			case RVCCalLexer.ACTION:
				actionParser.parseAction(child);
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

	/**
	 * Parses the given tree as a list of parameters. Each child of the tree is
	 * parsed as a parameter represented by a {@link GlobalVariable}, and added
	 * to {@link #parameters}.
	 * 
	 * @param tree
	 *            a tree
	 */
	private void parseActorParameters(Tree tree) throws OrccException {
		int numParameters = tree.getChildCount();
		for (int i = 0; i < numParameters; i++) {
			Tree child = tree.getChild(i);
			Type type = typeParser.parseType(child.getChild(0));
			Tree nameTree = child.getChild(1);
			Location location = parseLocation(nameTree);
			String name = nameTree.getText();

			Expression init = null;
			if (child.getChildCount() == 3) {
				init = exprParser.parseExpression(child.getChild(2));
			}

			GlobalVariable globalVariable = new GlobalVariable(location, type,
					name, init);
			parameters.add(file, location, name, globalVariable);
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

			Type type = typeParser.parseType(child.getChild(0));
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

		stmtParser.parseLocalVariables(parameters, tree.getChild(1));

		Block block = stmtParser.parseBlock(tree, 2);
		currentScope = currentScope.getParent();

		Procedure procedure = new Procedure(name, false, location,
				new VoidType(), parameters, block.getVariables(), block
						.getNodes());

		procedures.add(file, location, name, procedure);
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
		Type type = typeParser.parseType(tree.getChild(0));
		Tree nameTree = tree.getChild(1);
		Location location = parseLocation(nameTree);
		String name = nameTree.getText();
		boolean assignable = (tree.getChild(2).getType() == RVCCalLexer.ASSIGNABLE);

		Expression init = null;
		if (tree.getChildCount() == 4) {
			init = exprParser.parseExpression(tree.getChild(3));
		}

		StateVariable stateVariable = new StateVariable(location, type, name,
				assignable, init);
		stateVars.add(file, location, name, stateVariable);
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
