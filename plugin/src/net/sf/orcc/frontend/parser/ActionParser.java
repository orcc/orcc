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
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

import org.antlr.runtime.tree.Tree;

public class ActionParser {

	/**
	 * the file being parsed
	 */
	private final String file;

	/**
	 * input pattern of the action being parsed
	 */
	private Map<Port, Integer> inputPattern;

	/**
	 * list of CFG nodes of the action being parsed
	 */
	private ArrayList<CFGNode> nodes;

	/**
	 * output pattern of the action being parsed
	 */
	private Map<Port, Integer> outputPattern;

	private Scope<Variable> scope;

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
	 * 
	 */
	public ActionParser(String file) {
		this.file = file;
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

	public Action parseAction(Tree tree) throws OrccException {
		Tree tagTree = tree.getChild(0);
		Location location = parseLocation(tree);
		Tag tag = parseActionTag(tagTree);

		variables = new Scope<Variable>(scope, true);
		nodes = new ArrayList<CFGNode>();

		parseInputPattern(tree.getChild(1));
		parseBody(tree, 4, 5);
		parseOutputPattern(tree.getChild(2));

		String name = getActionName(location, tag);
		Procedure body = new Procedure(name, false, location, new VoidType(),
				new OrderedMap<Variable>(), variables, nodes);

		Procedure scheduler = createSchedulingProcedure(body, tree.getChild(3));

		Action action = new Action(location, tag, inputPattern, outputPattern,
				scheduler, body);
		return action;
	}

	private void parseBody(Tree tree, int i, int j) {
		// TODO Auto-generated method stub
	}

	private void parseInputPattern(Tree tree) {
		inputPattern = new HashMap<Port, Integer>();
	}

	private void parseOutputPattern(Tree child) {
		outputPattern = new HashMap<Port, Integer>();
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
