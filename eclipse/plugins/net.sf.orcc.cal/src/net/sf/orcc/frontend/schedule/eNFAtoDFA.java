package net.sf.orcc.frontend.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import net.sf.orcc.df.Tag;

/**
 * This class performs e-closure and determinization in one step.
 * 
 * @author Felix Abecassis
 * 
 */
public class eNFAtoDFA {

	private ArrayList<Set<Integer>> closure;

	private Automaton DFA;

	private Automaton eNFA;

	public eNFAtoDFA(Automaton a) {
		eNFA = a;
		DFA = new Automaton(SimpleEdge.class);
		DFA.setAlphabet(eNFA.getAlphabet());
		closure = new ArrayList<Set<Integer>>(eNFA.vertexSet().size());
		for (Integer v : eNFA.vertexSet()) {
			doClosure(v);
		}
	}

	public Automaton convert() {
		// Associate each set of states from eNFA to the index of the
		// corresponding state in DFA.
		Map<Set<Integer>, Integer> seen = new HashMap<Set<Integer>, Integer>();
		Set<Integer> initial = new HashSet<Integer>();
		Stack<Set<Integer>> todo = new Stack<Set<Integer>>();
		Integer index = 0;

		Integer initialState = eNFA.getInitialState();
		initial.add(initialState);
		todo.push(initial);
		seen.put(initial, index);
		++index;
		DFA.addVertex(initialState);
		DFA.setInitialState(initialState);
		while (!todo.empty()) {
			Set<Integer> src = todo.pop();
			Integer srcIndex = seen.get(src);
			Set<Integer> eClosure = getSuccsEpsilon(src);
			for (Tag label : DFA.getAlphabet()) {
				Set<Integer> dst = getSuccsLabel(eClosure, label);
				if (dst.isEmpty()) {
					continue;
				}
				Integer i = seen.get(dst);
				Integer dstIndex;
				if (i != null) {
					dstIndex = i;
				} else {
					// Add this new set to seen, and add a new state to DFA.
					dstIndex = index;
					seen.put(dst, index);
					todo.push(dst);
					DFA.addVertex(index);
					for (Integer v : dst) {
						if (eNFA.getFinalStates().contains(v)) {
							// The set contains a final state, the new state
							// must be final.
							DFA.addFinalState(index);
							break;
						}
					}
					++index;
				}
				// Add this transition
				DFA.addEdge(srcIndex, dstIndex, new SimpleEdge(label));
			}
		}
		return DFA;
	}

	/**
	 * From state src, computes the set of reachable states using epsilon
	 * transitions The resulting set is added to the closure attribute.
	 * 
	 * @param src
	 *            the source state
	 */
	private void doClosure(Integer src) {
		Set<Integer> curClosure = new HashSet<Integer>();
		Stack<Integer> todo = new Stack<Integer>();
		// A state is in in its own closure.
		curClosure.add(src);
		todo.push(src);

		while (!todo.empty()) {
			Integer cur = todo.pop();
			if (eNFA.getFinalStates().contains(cur)) {
				// src can reach a final state, src is therefore a final state
				eNFA.addFinalState(src);
			}
			Set<SimpleEdge> succs = eNFA.outgoingEdgesOf(cur);
			for (SimpleEdge e : succs) {
				Integer target = eNFA.getEdgeTarget(e);
				Tag label = (Tag) e.getObject();
				if (label == null) {
					if (!curClosure.contains(target)) {
						// A new state reachable through epsilon transitions
						todo.push(target);
						curClosure.add(target);
					}
				}
			}
		}
		closure.add(curClosure);
	}

	/**
	 * Given a set of states, returns the set of reachable states through
	 * epsilon transitions.
	 * 
	 * @param src
	 *            a set of states
	 * @return the set of epsilon-reachable states
	 */
	private Set<Integer> getSuccsEpsilon(Set<Integer> src) {
		Set<Integer> result = new HashSet<Integer>();
		// Compute the union of all precomputed closure sets.
		for (Integer v : src) {
			result.addAll(closure.get(v));
		}
		return result;
	}

	/**
	 * Given a set of states and a letter from the alphabet, returns the set of
	 * reachable states using this letter.
	 * 
	 * @param stateSet
	 *            a set of states
	 * @param letter
	 *            the letter from the alphabet
	 * @return the set of reachable states using the letter
	 */
	private Set<Integer> getSuccsLabel(Set<Integer> stateSet, Tag letter) {
		Set<Integer> result = new HashSet<Integer>();
		for (Integer v : stateSet) {
			Set<SimpleEdge> succs = eNFA.outgoingEdgesOf(v);
			for (SimpleEdge e : succs) {
				Integer target = eNFA.getEdgeTarget(e);
				if (letter.equals(e.getObject())) {
					result.add(target);
				}
			}
		}
		return result;
	}
}
