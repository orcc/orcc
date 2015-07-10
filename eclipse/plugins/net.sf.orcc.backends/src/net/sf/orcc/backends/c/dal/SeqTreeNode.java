package net.sf.orcc.backends.c.dal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sf.orcc.df.Action;

/**
 * A node in the peek sequence tree.
 *
 * @author James Guthrie
 *
 */
public class SeqTreeNode{

	private GuardConstraint constraints;
	private List<Action> actions;
	private Set<Token> processed;
	private List<SeqTreeNode> children;

	SeqTreeNode(GuardConstraint constraints, List<Action> list, Set<Token> tokens){
		this.setConstraints(constraints);
		this.setProcessed(tokens);
		this.actions = new ArrayList<Action>(list);
		this.children = new ArrayList<SeqTreeNode>();
	}

	SeqTreeNode(Collection<Action> actions){
		this.constraints = null;
		this.processed = new TreeSet<Token>();
		this.actions = new ArrayList<Action>(actions);
		this.children = new ArrayList<SeqTreeNode>();
	}

	SeqTreeNode(GuardConstraint constraints, Action action, Set<Token> tokens){
		this.setConstraints(constraints);
		this.setProcessed(tokens);
		this.actions = new ArrayList<Action>();
		this.children = new ArrayList<SeqTreeNode>();
		actions.add(action);
	}

	public SeqTreeNode() {
	}

	public GuardConstraint getConstraints() {
		return constraints;
	}

	public void setConstraints(GuardConstraint constraints) {
		this.constraints = new GuardConstraint(constraints);
	}

	public Set<Token> getProcessed() {
		return this.processed;
	}

	public void setProcessed(Set<Token> processed) {
		Set<Token> temp = new HashSet<Token>(processed);
		this.processed = new TreeSet<Token>();
		this.processed.addAll(temp);
	}

	public List<Action> getActions(){
		return actions;
	}

	public List<SeqTreeNode> getChildren() {
		return children;
	}

	public void addChild(SeqTreeNode child) {
		this.children.add(child);
	}

	public void removeChild(SeqTreeNode child) {
		this.children.remove(child);
	}

	public void addChild(GuardConstraint constraints, Set<Token> tokens, List<Action> actions){
		SeqTreeNode child = new SeqTreeNode(constraints, actions, tokens);
		this.children.add(child);
	}

	public void addChild(GuardConstraint constraints, Set<Token> tokens, Action action){
		List<Action> s = new ArrayList<Action>();
		s.add(action);
		addChild(constraints, tokens, s);
	}

	@Override
	public String toString() {
		return actions.toString() + ": " + constraints.toString();
	}
}
