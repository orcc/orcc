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

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

import org.antlr.runtime.tree.Tree;

/**
 * This class defines an action parser.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActionParser {

	/**
	 * This class defines a pattern type. Input patterns and outputs patterns
	 * may either reference ports by position, or reference ports by name, but
	 * not both.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private enum PatternType {
		/**
		 * the pattern references ports by name
		 */
		NAME,

		/**
		 * the pattern references ports by position
		 */
		POSITION;
	};

	private ExpressionParser exprParser;

	/**
	 * the file being parsed
	 */
	private final String file;

	/**
	 * input pattern of the action being parsed
	 */
	private Map<Port, Integer> inputPattern;

	/**
	 * ordered map of input ports
	 */
	private OrderedMap<Port> inputs;

	/**
	 * list of CFG nodes of the action being parsed
	 */
	private ArrayList<CFGNode> nodes;

	/**
	 * output pattern of the action being parsed
	 */
	private Map<Port, Integer> outputPattern;

	/**
	 * ordered map of output ports
	 */
	private OrderedMap<Port> outputs;

	/**
	 * list of ports referenced by the current pattern
	 */
	private final List<Port> patternPorts;

	/**
	 * type of the current pattern
	 */
	private PatternType patternType;

	/**
	 * position of a port in an input pattern.
	 */
	private int position;

	private Scope<Variable> scope;

	private StatementParser stmtParser;

	/**
	 * this integer is used to give a name to untagged actions.
	 */
	private int untaggedCounter;

	/**
	 * the scope of variables of the action being parsed
	 */
	private Scope<Variable> variables;

	/**
	 * Creates a new action parser with the given file.
	 * 
	 * @param file
	 *            the file being parsed
	 * @param outputs
	 * @param inputs
	 * 
	 */
	public ActionParser(String file, OrderedMap<Port> inputs,
			OrderedMap<Port> outputs, ExpressionParser exprParser,
			StatementParser stmtParser) {
		this.file = file;
		this.inputs = inputs;
		this.outputs = outputs;
		this.exprParser = exprParser;
		this.stmtParser = stmtParser;

		patternPorts = new ArrayList<Port>();
	}

	private Procedure createSchedulingProcedure(Procedure body, Tree guards) {
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Location location = new Location();

		BlockNode block = new BlockNode();
		block.add(new Return(block, location, new BoolExpr(location, true)));
		nodes.add(block);

		Procedure scheduler = new Procedure("isSchedulable_" + body.getName(),
				false, location, new BoolType(), new OrderedMap<Variable>(),
				new OrderedMap<Variable>(), nodes);

		return scheduler;
	}

	/**
	 * Returns a name from the given tag. Location information is used only when
	 * reporting an exception.
	 * 
	 * @param location
	 *            location of the action
	 * @param tag
	 *            tag of the action, possibly empty
	 * @return an action name
	 * @throws OrccException
	 *             if there are too many unnamed actions.
	 */
	private String getActionName(Location location, Tag tag)
			throws OrccException {
		StringBuilder builder = new StringBuilder();
		if (tag.isEmpty()) {
			builder.append("untagged");

			untaggedCounter++;
			if (untaggedCounter >= 100) {
				final String message = "Whoa, this actor contains more than 100 anonymous actions. If you are "
						+ "sure there is no other way for you to do what you want with less, "
						+ "please contact us and we will see what we can do.";
				throw new OrccException(file, location, message);
			}

			new Formatter(builder).format("%02d", untaggedCounter);
		} else {
			Iterator<String> it = tag.iterator();
			builder.append(it.next());
			while (it.hasNext()) {
				builder.append('_');
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

	/**
	 * Returns a port from the ports ordered map. This method checks if the
	 * given tree contains a port name, and if it does not, it uses the position
	 * field.
	 * 
	 * @param ports
	 *            ordered map of ports
	 * @param tree
	 *            a tree
	 * @return a port
	 * @throws OrccException
	 */
	private Port getPort(OrderedMap<Port> ports, Tree tree)
			throws OrccException {
		if (tree.getChildCount() == 0) {
			if (patternType == null) {
				// first reference sets the pattern
				patternType = PatternType.POSITION;
				position = 0;
			} else if (PatternType.NAME.equals(patternType)) {
				throw new OrccException(
						"a pattern references ports by position and by name");
			}

			List<Port> list = ports.getList();
			if (position >= list.size()) {
				throw new OrccException(file, parseLocation(tree),
						"pattern has too many ports");
			}
			return list.get(position++);
		} else {
			if (patternType == null) {
				// first reference sets the pattern
				patternType = PatternType.NAME;
			} else if (PatternType.POSITION.equals(patternType)) {
				throw new OrccException(
						"a pattern references ports by position and by name");
			}

			String portName = tree.getChild(0).getText();
			Port port = ports.get(portName);
			if (port == null) {
				throw new OrccException(file, parseLocation(tree),
						"port cannot be found: \"" + portName + "\"");
			}

			// check that this port is only referenced once
			if (patternPorts.contains(port)) {
				throw new OrccException("port \"" + portName
						+ "\" is referenced more than once");
			} else {
				patternPorts.add(port);
			}

			return port;
		}
	}

	/**
	 * Parses the given tree as an action.
	 * 
	 * @param tree
	 *            a tree
	 * @return an action
	 * @throws OrccException
	 */
	public Action parseAction(Tree tree) throws OrccException {
		Tree tagTree = tree.getChild(0);
		Location location = parseLocation(tree);
		Tag tag = parseActionTag(tagTree);

		variables = new Scope<Variable>(scope, false);
		nodes = new ArrayList<CFGNode>();

		parseInputPattern(tree.getChild(1));
		stmtParser.init(variables, nodes);
		stmtParser.parseLocalVariables(tree.getChild(4));
		stmtParser.parseStatements(tree.getChild(5));
		parseOutputPattern(tree.getChild(2));

		String name = getActionName(location, tag);
		Procedure body = new Procedure(name, false, location, new VoidType(),
				new OrderedMap<Variable>(), variables, nodes);

		Procedure scheduler = createSchedulingProcedure(body, tree.getChild(3));

		Action action = new Action(location, tag, inputPattern, outputPattern,
				scheduler, body);
		return action;
	}

	private void parseInput(Port port, Tree idents, Tree repeat)
			throws OrccException {
		BlockNode block = BlockNode.last(nodes);

		Expression numRepeats = null;
		if (repeat.getChildCount() == 1) {
			exprParser.parseExpression(repeat.getChild(0));
		}

		int n = idents.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree child = idents.getChild(i);
			Location location = parseLocation(child);
			String name = child.getText();

			if (numRepeats == null) {
				LocalVariable local = new LocalVariable(true, 0, location,
						name, null, null, port.getType());
				variables.add(file, location, name, local);

				Read read = new Read(block, location, port, 1, local);
				block.add(read);
			}
		}
	}

	/**
	 * Parses the given tree as an input pattern, and adds Read calls as
	 * appropriate.
	 * 
	 * @param tree
	 *            a tree
	 * @throws OrccException
	 */
	private void parseInputPattern(Tree tree) throws OrccException {
		inputPattern = new HashMap<Port, Integer>();
		patternType = null;
		patternPorts.clear();

		int n = tree.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree input = tree.getChild(i);
			Port port = getPort(inputs, input.getChild(0));
			parseInput(port, input.getChild(1), input.getChild(2));
		}
	}

	private void parseOutput(Port port, Tree expression, Tree repeat) {
		// TODO Auto-generated method stub

	}

	/**
	 * Parses the given tree as an output pattern, and adds Write calls as
	 * appropriate.
	 * 
	 * @param tree
	 *            a tree
	 * @throws OrccException
	 */
	private void parseOutputPattern(Tree tree) throws OrccException {
		outputPattern = new HashMap<Port, Integer>();
		patternType = null;
		patternPorts.clear();

		int n = tree.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree output = tree.getChild(i);
			Port port = getPort(outputs, output.getChild(0));
			parseOutput(port, output.getChild(1), output.getChild(2));
		}
	}

	/**
	 * Sets the current scope of variable.
	 * 
	 * @param scope
	 *            the current scope of variable
	 */
	public void setVariableScope(Scope<Variable> scope) {
		this.scope = scope;
	}

}
