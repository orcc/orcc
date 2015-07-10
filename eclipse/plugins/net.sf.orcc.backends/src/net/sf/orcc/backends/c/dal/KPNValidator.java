package net.sf.orcc.backends.c.dal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EMap;

import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.tools.classifier.GuardSatChecker;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.backends.c.dal.transform.IfConverter;
import net.sf.orcc.backends.c.dal.transform.LoadRewriter;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Network;

/**
 * Class for checking Kahn process networks (KPN) compliance
 * of a network.
 *
 * Theory described in EMSOFT 2015 publication
 * "Executing Dataflow Actors as Kahn Processes"
 *
 * @author Jani Boutellier
 * @author James Guthrie
 *
 */
public class KPNValidator {

	private GuardSatChecker satChecker;
	private ArrayList<Action> actions;
	private boolean satError;
	String path;

	private boolean printPeekTree = false;

	public KPNValidator(String srcPath) {
		path = srcPath;
	}

	public void validate(Network network) {
		IfConverter converter = new IfConverter();
		for (Actor originalActor : network.getAllActors()) {
			if (originalActor.getFileName() != null) {
				converter.ifConvertGuards(originalActor);
				Actor actor = IrUtil.copy(originalActor);
				boolean isKPN = true;
				satError = false;
				if (actor.hasFsm()) {
					for (State state : actor.getFsm().getStates()) {
						if (!inspectState(actor, state, originalActor)) {
							isKPN = false;
							break;
						}
						if (satError) {
							break;
						}
					}
				} else {
					if (actor.getActions().size() >= 1) {
						SeqTreeNode root;
						root = inspectActionList(actor, actor.getActionsOutsideFsm(), false);
						actor.setAttribute("SequenceTreeRoot", root);
						if (root == null) {
							isKPN = false;
						}
					}
				}
				if (satError) {
					OrccLogger.noticeln("Actor [" + actor.getName() + "] not classified");
				} else {
					if (isKPN) {
						OrccLogger.noticeln("Actor [" + actor.getName() + "] is KPN");
					} else {
						OrccLogger.noticeln("Actor [" + actor.getName() + "] could not be translated to KPN");
					}
				}
			}
		}
	}

	public void analyzeInputs(Network network) {
		for (Actor actor : network.getAllActors()) {
			if (actor.getInputs().size() == 0) {
				actor.addAttribute("variableInputPattern");
				continue;
			}
			if (actor.getActions().size() > 1) {
				OrccLogger.traceln("Actor " + actor.getName() + " may have variable input rate (more that one action)");
			} else {
				Pattern inputPattern = actor.getActions().get(0).getInputPattern();
				for(Port port : inputPattern.getPorts()) {
					port.setNumTokensConsumed(inputPattern.getNumTokensMap().get(port));
				}
			}
		}
	}

	public void analyzeOutputs(Network network) {
		for (Actor actor : network.getAllActors()) {
			if (actor.getActions().size() > 1) {
				analyzeOutputPorts(actor, actor.getActions());
			} else {
				if (actor.getActions().size() != 0) {
					Pattern outputPattern = actor.getActions().get(0).getOutputPattern();
					for(Port port : outputPattern.getPorts()) {
						port.setNumTokensProduced(outputPattern.getNumTokensMap().get(port));
					}
				}
			}
		}
	}

	private boolean inspectState(Actor actor, State srcState, Actor originalActor) {
		List<Action> actions = new ArrayList<Action>();
		for (Edge edge : srcState.getOutgoing()) {
			actions.add(((Transition) edge).getAction());
		}
		actions.addAll(actor.getActionsOutsideFsm());
		if (printPeekTree) {
			OrccLogger.traceln("State " + srcState.getName());
		}
		SeqTreeNode root = inspectActionList(actor, actions, false);
		srcState.setAttribute("SequenceTreeRoot", root);
		if (root == null) {
			return false;
		} else {
			return true;
		}
	}

	private SeqTreeNode inspectActionList(Actor actor, List<Action> actions, boolean actorLevel) {
		SeqTreeNode root = getPeekSequence(actor, actions);
		if (root != null) {
			SeqTreeSearcher searcher = new SeqTreeSearcher(actor);
			searcher.search(root, 0, printPeekTree);
			SeqTreeNode ndNode = searcher.getNondeterministic();
			if (ndNode != null) {
				OrccLogger.traceln("Could not arbitrate actor " + actor.getName() + " actions: " + ndNode.getActions().toString());
				return null;
			}
		}
		return root;
	}

	/**
	 * Get the peek sequence of <code>actor</code> for <code>actions</code>
	 *
	 * @param actor
	 * @param actions
	 * @return The root node of a peek sequence tree
	 */
	private SeqTreeNode getPeekSequence(Actor actor, List<Action> actions) {
		satChecker = new GuardSatChecker(actor);
		this.actions = new ArrayList<Action>(actions);
		SeqTreeNode root = new SeqTreeNode(actions);
		return addChildren(root);
	}

	/**
	 * Recursively construct port peek sequence.
	 *
	 * @param current
	 * @return The root node of a peek sequence tree
	 */
	private SeqTreeNode addChildren(SeqTreeNode current) {
	    List<Action> actions = current.getActions();
		Set<Token> intersection = getNextReadTokens(actions);
		intersection.removeAll(current.getProcessed());
		if (!intersection.isEmpty()) {
			Set<Token> processed = new HashSet<Token>();
			processed.addAll(current.getProcessed());
			processed.addAll(intersection);
			for (Action a: actions) {
				SeqTreeNode node = new SeqTreeNode(new GuardConstraint(a, new TreeSet<Token>(intersection)), a, processed);
				if (!insertChildNode(current, node)) {
					return null;
				}
			}
			for (SeqTreeNode child : current.getChildren()) {
				if (addChildren(child) == null) {
					return null;
				}
			}
		} else {
			if (actions.size() != 1){
				for (Action action : actions) {
					if ((current.getConstraints() == null) || !current.getConstraints().equivalent(action)){
						return current;
					} else {
						for (Action other : actions) {
							// Use actor actions list here to ensure that ordering is correct
							int actionIdx = this.actions.indexOf(action);
							int otherIdx = this.actions.indexOf(other);
							if ((actionIdx > otherIdx) && !action.getInputPattern().isSupersetOf(other.getInputPattern())) {
								return current;
							}
						}
					}
				}
			}
		}
		return current;
	}

	/**
	 * Insert node as a child of current, if the constraints of node are sat
	 * with the constraints of an existing child, the two nodes are resolved
	 * and replaced with child nodes with mutually exclusive constraints.
	 *
	 * @param current
	 * @param node
	 */
	private boolean insertChildNode(SeqTreeNode current, SeqTreeNode node) {
		if (satError) {
			return false;
		}
		List<SeqTreeNode> children = current.getChildren();
		if (children.isEmpty()) {
			current.addChild(node);
		} else {
			boolean unsat = true;
			for (SeqTreeNode child : current.getChildren()) {
				if (sat(node, child) == true) {
					unsat = false;
					Set<SeqTreeNode> nodes = resolve(node, child);
					current.removeChild(child);
					for (SeqTreeNode n : nodes) {
						insertChildNode(current, n);
					}
					break;
				}
			}
			if (unsat == true) {
				current.addChild(node);
			}
		}
		return true;
	}

	/**
	 * Resolve overlapping constraints between left and right, returning
	 * nodes which have mutually exclusive constraints
	 *
	 * @param left
	 * @param right
	 * @return set of nodes with mutually exclusive constraints
	 */
	private Set<SeqTreeNode> resolve(SeqTreeNode left, SeqTreeNode right) {
		GuardConstraint intersect = left.getConstraints().intersect(right.getConstraints());
		GuardConstraint leftConst = left.getConstraints().difference(right.getConstraints());
		GuardConstraint rightConst = right.getConstraints().difference(left.getConstraints());
		Set<SeqTreeNode> nodes = new HashSet<SeqTreeNode>();
		Set<Token> processed = left.getProcessed();

		List<Action> actions = new ArrayList<Action>(left.getActions());
		actions.addAll(right.getActions());
		SeqTreeNode n;
		n = new SeqTreeNode(intersect, actions, processed);
		if (sat(n, actions)) {
			nodes.add(n);
		}
		n = new SeqTreeNode(leftConst, left.getActions(), processed);
		if (sat(n, actions)) {
			nodes.add(n);
		}
		n = new SeqTreeNode(rightConst, right.getActions(), processed);
		if (sat(n, actions)) {
			nodes.add(n);
		}
		return nodes;
	}

	private void analyzeOutputPorts(Actor actor, List<Action> actions) {
		boolean hasBeenWarned = false;
		for (Action first : actions) {
			for (Action second : actions) {
				hasBeenWarned = compareOutputPatterns(actor, first, second, hasBeenWarned);
			}
		}
	}

	private boolean compareOutputPatterns(Actor actor, Action firstAction, Action secondAction, boolean hasBeenWarned) {
		Pattern first = firstAction.getOutputPattern();
		Pattern second = secondAction.getOutputPattern();
		for(Port port : first.getPorts()) {
			int firstTokenRate = first.getNumTokensMap().get(port);
			port.setNumTokensProduced(firstTokenRate);
			if (second.getNumTokensMap().get(port) != null) {
				int secondTokenRate = second.getNumTokensMap().get(port);
				if (firstTokenRate != secondTokenRate) {
					if (!hasBeenWarned) {
						OrccLogger.traceln("Actor " + actor.getName() + " port " +
								port.getName() + " has a variable output rate." +
								" This actor has limited OpenCL compatibility.");
						hasBeenWarned = true;
					}
					port.setNumTokensProduced(-1);
				}
			} else {
				if (second.getNumTokensMap().size() > 0) {
					if (!hasBeenWarned) {
						OrccLogger.traceln("Actor " + actor.getName() + " action " +
								firstAction.getName() + " writes port "  +
								port.getName() + " but action "+ secondAction.getName() +
								" does not. This actor has limited OpenCL compatibility.");
						hasBeenWarned = true;
					}
				}
			}
		}
		return hasBeenWarned;
	}

	/**
	 * Determine whether constraints of nodes left and right
	 * are simultaneously satisfiable
	 *
	 * @param left
	 * @param right
	 * @return true if satisfiable, false if mutually exclusive
	 */
	private boolean sat(SeqTreeNode left, SeqTreeNode right) {
		// Clone existing actions
		Set<Action> actions = new HashSet<Action>();
		actions.addAll(left.getActions());
		actions.addAll(right.getActions());
		Action leftAction = IrUtil.copy(getPeekiestAction(actions));
		Action rightAction = IrUtil.copy(getPeekiestAction(actions));

		// Set constraints of cloned actions
		boolean leftOk = left.getConstraints().setConstraint(leftAction);
		boolean rightOk =right.getConstraints().setConstraint(rightAction);

		if (!leftOk || !rightOk) {
			satError = true;
			return false;
		}

		boolean result = satChecker.checkSat(leftAction, rightAction);
		satError |= satChecker.hasFailed();
		return result;
	}

	/**
	 * Determine whether constraints of node are satisfiable
	 *
	 * @param node
	 * @return true if satisfiable, false otherwise
	 */
	private boolean sat(SeqTreeNode node, Collection<Action> actions) {
		// Clone existing action
		Action action = IrUtil.copy(getPeekiestAction(actions));

		// Set constraints of cloned action
		if (!node.getConstraints().setConstraint(action)) {
			satError = true;
			return false;
		}

		boolean result = satChecker.checkSat(action);
		satError |= satChecker.hasFailed();
		return result;
	}

	private Action getPeekiestAction(Collection<Action> actions) {
		Iterator<Action> iter = actions.iterator();
		Action action = iter.next();
		while (iter.hasNext()) {
			Action a = iter.next();
			if (a.getPeekPattern().isSupersetOf(action.getPeekPattern())) {
				action = a;
			}
		}
		return action;
	}

	/**
	 * Get the tokens which can be read by all actions in a DAL KPN.
	 * This is the intersection of the input tokens of all actions and
	 * the union of state tokens of all actions
	 *
	 * @param actions
	 * @return A Set of tokens
	 */
	private Set<Token> getNextReadTokens(Collection<Action> actions) {
		Set<Token> theseTokens = new HashSet<Token>();
		Set<Set<Token>> allInputTokens = new HashSet<Set<Token>>();
		for (Action a : actions){
			Set<Token> inputTokens = getInputTokens(a);
			allInputTokens.add(inputTokens);
		}
		Set<Token> intersection = getIntersection(allInputTokens);
		Set<Var> deps = new HashSet<Var>();
		for (Token t : intersection) {
			if (t instanceof LoadTokenImpl) {
				deps.add(((LoadTokenImpl) t).getInstruction().getTarget().getVariable());
			}
		}
		for (Action a : actions){
			Set<Token> stateTokens = getStateTokens(a, deps);
			theseTokens.addAll(stateTokens);
		}
		Set<Token> nextRead = new TreeSet<Token>();
		nextRead.addAll(intersection);
		nextRead.addAll(theseTokens);
		return nextRead;
	}

	/**
	 * Get state tokens belonging to the scheduler of <code>action</code>
	 * given the <code>inputVars</code> which are defined by the input
	 * tokens.
	 *
	 * @param action
	 * @return A Set of tokens
	 */
	private Set<Token> getStateTokens(Action action, Collection<Var> inputVars) {
		Set<Token> tokens = new TreeSet<Token>();
		Set<Var> vars = new HashSet<Var>(inputVars);
		for (Block b : action.getScheduler().getBlocks()) {
			if (b instanceof BlockBasic) {
				for (Instruction i : ((BlockBasic) b).getInstructions()) {
					if (i instanceof InstLoad || i instanceof InstCall) {
						Token t;
						if (i instanceof InstLoad) {
							t = new LoadTokenImpl((InstLoad) i);
						} else {
							t = new CallTokenImpl((InstCall) i);
						}
						if (t.isStateToken()) {
							tokens.add(t);
							vars.add(t.getTargetVar());
						}
					}
				}
			}
		}
		// Remove tokens whose dependencies are not fulfilled
		for (Iterator<Token> it = tokens.iterator(); it.hasNext(); ) {
		    Token aToken = it.next();
		    if (!aToken.depsFulfilledBy(vars)) {
		        it.remove();
		    }
		}
		return tokens;
	}

	/**
	 * Get the input tokens belonging to the scheduler of <code>action</code>.
	 *
	 * @param actor
	 * @return A Set of load instructions
	 */
	private Set<Token> getInputTokens(Action action) {
		Set<Token> tokens = new TreeSet<Token>();
		Pattern pattern = action.getInputPattern();
		EMap <Port, Var> m = pattern.getPortToVarMap();
		IrFactory irFactory = new IrFactoryImpl();
		for (Block b : action.getScheduler().getBlocks()) {
			if (b.isBlockBasic()) {
				for (Port port : m.keySet()) {
					Var v = m.get(port);
					for (int i = 0; i < pattern.getNumTokens(port); i++) {
						Var target = irFactory.createVar(port.getType(), "placeholder_name" , false, i);
						InstLoad load = irFactory.createInstLoad(target, v, i);
						new LoadRewriter().doSwitch(load);
						tokens.add(new LoadTokenImpl(load));
					}
				}
			}
		}
		return tokens;
	}

	/**
	 * Get the set intersection. Given a set of Sets, this function determines
	 * the intersection of all Sets and returns the elements in the intersection
	 *
	 * @param s a set of Sets
	 * @return a set representing the intersection of the values of the Sets
	 */
	private <E> Set<E> getIntersection(Set<Set<E>> s){
		Iterator<Set<E>> it = s.iterator();
		if (it.hasNext()) {
			Set<E> result = new HashSet<E>(it.next());
			while (it.hasNext()) {
				Set<E> others = it.next();
				result.retainAll(others);
			}
			return result;
		} else {
			return null;
		}
	}

}
