package net.sf.orcc.frontend.schedule;

import java.util.HashSet;
import java.util.Set;

import net.sf.orcc.df.Tag;

import org.jgrapht.graph.DefaultDirectedGraph;

public class Automaton extends DefaultDirectedGraph<Integer, SimpleEdge> {

	private static final long serialVersionUID = 1L;

	private Set<Tag> alphabet;

	private Set<Integer> finalStates;

	private Integer initialState;

	public Automaton(Class<? extends SimpleEdge> edgeClass) {
		super(edgeClass);
		initialState = -1;
		finalStates = new HashSet<Integer>();
		alphabet = new HashSet<Tag>();
	}

	public boolean addFinalState(Integer s) {
		return finalStates.add(s);
	}

	public Set<Tag> getAlphabet() {
		return alphabet;
	}

	public Set<Integer> getFinalStates() {
		return finalStates;
	}

	public Integer getInitialState() {
		return initialState;
	}

	public boolean registerLetter(Tag letter) {
		return alphabet.add(letter);
	}

	public void setAlphabet(Set<Tag> newAlphabet) {
		alphabet = newAlphabet;
	}

	public void setInitialState(Integer s) {
		initialState = s;
	}

}
