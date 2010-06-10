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

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.orcc.cal.cal.AstSchedule;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * This class defines a FSM builder.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FSMBuilder {

	private Map<Action, Integer> actionRank;

	private DirectedGraph<String, UniqueEdge> graph;

	private String initialState;

	/**
	 * Creates a FSM builder and initializes it with the given FSM AST.
	 * 
	 * @param tree
	 *            an ANTLR tree that represents the AST of an FSM.
	 */
	public FSMBuilder(AstSchedule schedule) {
		graph = new DirectedMultigraph<String, UniqueEdge>(UniqueEdge.class);
		initialState = schedule.getInitialState().getName();
		parseTransitions(schedule.getTransitions());
	}

	/**
	 * Add transitions from source to each (action, state) couple from targets.
	 * The transitions are ordered by descending priority of the actions.
	 * 
	 * @param fsm
	 *            the FSM being created
	 * @param source
	 *            source state
	 * @param targets
	 *            an (action, state) map of targets
	 */
	private void addTransitions(FSM fsm, String source,
			Map<Action, String> targets) {
		// Note: The higher the priority the lower the rank
		List<Action> nextActions = new ArrayList<Action>(targets.keySet());
		Collections.sort(nextActions, new Comparator<Action>() {

			@Override
			public int compare(Action a1, Action a2) {
				// compare the ranks
				return actionRank.get(a1).compareTo(actionRank.get(a2));
			}

		});

		// add the transitions in the right order
		for (Action action : nextActions) {
			String target = targets.get(action);
			fsm.addTransition(source, target, action);
		}
	}

	/**
	 * Builds an FSM from the FSM graph and the given sorted action list. The
	 * action list must be obtained from the ActionSorter.
	 * 
	 * @param actionList
	 *            a sorted action list
	 * @return an FSM
	 */
	public FSM buildFSM(ActionList actionList) {
		actionRank = new HashMap<Action, Integer>();
		int rank = 0;
		for (Action action : actionList) {
			actionRank.put(action, rank++);
		}

		Set<String> states = new TreeSet<String>(graph.vertexSet());
		FSM fsm = new FSM();

		// adds states by alphabetical order
		for (String source : states) {
			fsm.addState(source);
		}

		// adds transitions
		for (String source : states) {
			Map<Action, String> targets = getTargets(fsm, source, actionList);
			addTransitions(fsm, source, targets);
		}

		// set initial state
		fsm.setInitialState(initialState);

		return fsm;
	}

	/**
	 * Returns an (action, target state) map. The map is created as follows:
	 * 
	 * <pre>
	 * for each outgoing edge of source
	 *   let &quot;tag&quot; be the edge label
	 *   let &quot;target&quot; be the edge target
	 *   let &quot;actions&quot; be the list of actions matching &quot;tag&quot;
	 *   for each action in &quot;actions&quot;
	 *     add (action, target) to the map
	 * </pre>
	 * 
	 * @param fsm
	 *            the FSM being created
	 * @param source
	 *            source state
	 * @param actionList
	 *            a list of actions
	 * @return an (action, target state) map
	 */
	private Map<Action, String> getTargets(FSM fsm, String source,
			ActionList actionList) {
		Map<Action, String> targets = new HashMap<Action, String>();
		Set<UniqueEdge> edges = graph.outgoingEdgesOf(source);
		for (UniqueEdge edge : edges) {
			String target = graph.getEdgeTarget(edge);
			Tag tag = (Tag) edge.getObject();
			List<Action> actions = actionList.getTaggedActions(tag);
			if (actions == null) {
				// non-existent target state
				System.out.println("non-existent target state: " + edge);
			} else {
				for (Action action : actions) {
					targets.put(action, target);
				}
			}
		}
		return targets;
	}

	/**
	 * Parses the transitions of an FSM and builds the preliminary graph from
	 * them.
	 * 
	 * @param tree
	 *            an ANTLR tree whose root is TRANSITIONS
	 */
	private void parseTransitions(List<AstTransition> transitions) {
		for (AstTransition transition : transitions) {
			String source = transition.getSource().getName();
			Tag tag = new Tag(transition.getTag().getIdentifiers());
			String target = transition.getTarget().getName();
			graph.addVertex(source);
			graph.addVertex(target);
			graph.addEdge(source, target, new UniqueEdge(tag));
		}
	}

	/**
	 * Prints the graph representation built.
	 * 
	 * @param out
	 *            output stream
	 */
	public void printGraph(OutputStream out) {
		DOTExporter<String, UniqueEdge> exporter = new DOTExporter<String, UniqueEdge>(
				new StringNameProvider<String>(), null,
				new StringEdgeNameProvider<UniqueEdge>());
		exporter.export(new OutputStreamWriter(out), graph);
	}

}
