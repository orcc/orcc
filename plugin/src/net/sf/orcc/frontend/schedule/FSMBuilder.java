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
package net.sf.orcc.frontend.schedule;

import static net.sf.orcc.frontend.parser.Util.parseActionTag;

import java.util.Set;
import java.util.TreeSet;

import net.sf.orcc.ir.actor.FSM;
import net.sf.orcc.ir.actor.Tag;
import net.sf.orcc.util.ActionList;

import org.antlr.runtime.tree.Tree;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * This class defines a FSM builder.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FSMBuilder {

	private DirectedGraph<String, Tag> graph;

	private String initialState;

	/**
	 * Creates a FSM builder and initializes it with the given FSM AST.
	 * 
	 * @param tree
	 *            an ANTLR tree that represents the AST of an FSM.
	 */
	public FSMBuilder(Tree tree) {
		graph = new DirectedMultigraph<String, Tag>(Tag.class);
		initialState = tree.getChild(0).getText();
		parseTransitions(tree.getChild(1));
	}

	/**
	 * 
	 * @param actions
	 * @return
	 */
	public FSM buildFSM(ActionList actions) {
		Set<String> states = new TreeSet<String>(graph.vertexSet());
		FSM fsm = new FSM();
		for (String state : states) {
			fsm.addState(state);
		}

		fsm.setInitialState(initialState);

		return fsm;
	}

	/**
	 * Parses the transitions of an FSM and builds the preliminary graph from
	 * them.
	 * 
	 * @param tree
	 *            an ANTLR tree whose root is TRANSITIONS
	 */
	private void parseTransitions(Tree tree) {
		int n = tree.getChildCount();
		for (int i = 0; i < n; i++) {
			Tree transition = tree.getChild(i);
			String source = transition.getChild(0).getText();
			Tag tag = parseActionTag(transition.getChild(1));
			String target = transition.getChild(2).getText();
			graph.addVertex(source);
			graph.addVertex(target);
			graph.addEdge(source, target, tag);
		}
	}

}
