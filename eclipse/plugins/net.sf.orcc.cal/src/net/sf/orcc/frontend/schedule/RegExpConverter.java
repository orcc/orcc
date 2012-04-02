/*
 * Copyright (c) 2010, AKAtech SA
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
 *   * Neither the name of AKAtech SA nor the names of its
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

import static net.sf.orcc.df.DfFactory.eINSTANCE;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.orcc.cal.cal.RegExp;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Tag;

import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;

public class RegExpConverter {

	/**
	 * Associate for each state in the automaton a String representation for the
	 * FSM.
	 */
	private Map<Integer, State> nameMap;

	private RegExp regexp;

	/**
	 * Creates a RegExp Converter and initializes it with the given
	 * AstScheduleRegExp
	 * 
	 * @param tree
	 *            an ANTLR tree that represents the AST of a ScheduleRegExp.
	 */
	public RegExpConverter(RegExp scheduleRegExp) {
		regexp = scheduleRegExp;
		nameMap = new TreeMap<Integer, State>();
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
	private void addTransitions(FSM fsm, Integer source,
			Map<Action, Integer> targets) {
		List<Action> nextActions = new ArrayList<Action>(targets.keySet());
		State sourceState = nameMap.get(source);

		// add the transitions in the right order
		for (Action action : nextActions) {
			Integer target = targets.get(action);
			State targetState = nameMap.get(target);
			fsm.addTransition(sourceState, action, targetState);
		}
	}

	/**
	 * Convert the AstScheduleRegExp tree to a FSM.
	 * 
	 * @param actionList
	 *            a sorted action list
	 * @return an FSM
	 */
	public FSM convert(ActionList actionList) {
		FSM fsm = eINSTANCE.createFSM();
		ThompsonBuilder thompsonBuilder = new ThompsonBuilder();
		Automaton eNFA = thompsonBuilder.build(regexp);
		Automaton DFA = new eNFAtoDFA(eNFA).convert();

		// DFA --> FSM

		// Convert the states to String and add them to the FSM.
		Set<Integer> states = DFA.vertexSet();
		for (Integer state : states) {
			State fsmState = formatState(state);
			nameMap.put(state, fsmState);
			fsm.getStates().add(fsmState);
		}
		// Add the transitions
		for (Integer source : states) {
			Map<Action, Integer> targets = getTargets(fsm, DFA, source,
					actionList);
			addTransitions(fsm, source, targets);
		}

		State initialState = nameMap.get(DFA.getInitialState());
		fsm.setInitialState(initialState);

		return fsm;
	}

	private State formatState(Integer s) {
		return eINSTANCE.createState("State" + s);
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
	 * @param automaton
	 *            the automaton to convert
	 * @param source
	 *            source state
	 * @param actionList
	 *            a list of actions
	 * @return an (action, target state) map
	 */
	private Map<Action, Integer> getTargets(FSM fsm, Automaton automaton,
			Integer source, ActionList actionList) {
		Map<Action, Integer> targets = new HashMap<Action, Integer>();
		Set<SimpleEdge> edges = automaton.outgoingEdgesOf(source);
		for (SimpleEdge edge : edges) {
			Integer target = automaton.getEdgeTarget(edge);
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
	 * Print an automaton.
	 * 
	 * @param out
	 *            output stream
	 * @param automaton
	 *            an automaton
	 */
	@SuppressWarnings("unused")
	private void printAutomaton(OutputStream out, Automaton automaton) {
		DOTExporter<Integer, SimpleEdge> exporter = new DOTExporter<Integer, SimpleEdge>(
				new StringNameProvider<Integer>(), null,
				new StringEdgeNameProvider<SimpleEdge>());
		exporter.export(new OutputStreamWriter(out), automaton);
	}

}
