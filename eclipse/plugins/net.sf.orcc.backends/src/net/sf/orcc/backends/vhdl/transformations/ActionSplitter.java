package net.sf.orcc.backends.vhdl.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;

/**
 * This class contains code that is useful for transformations that split
 * actions in several actions. These transformations are
 * WhileSplitTransformation (WST) and MultipleArrayAccessTransformation (MAAT).
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class ActionSplitter extends AbstractActorTransformation {

	/**
	 * This class contains an abstract branch visitor.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	protected class AbstractBranchVisitor extends AbstractActorTransformation {

		/**
		 * name of the branch being visited
		 */
		protected String branchName;

		/**
		 * action being visited
		 */
		protected Action currentAction;

		/**
		 * action to visit next (may be null)
		 */
		protected Action nextAction;

		/**
		 * name of the source state
		 */
		protected String sourceName;

		/**
		 * name of the target state
		 */
		protected String targetName;

		public AbstractBranchVisitor(String sourceName, String targetName) {
			this.sourceName = sourceName;
			this.targetName = targetName;
		}

		/**
		 * Adds an FSM to the given action scheduler.
		 * 
		 * @param actionScheduler
		 *            action scheduler
		 */
		private void addFsm(ActionScheduler actionScheduler) {
			fsm = new FSM();
			fsm.setInitialState("init");
			fsm.addState("init");
			for (Action action : actionScheduler.getActions()) {
				fsm.addTransition("init", "init", action);
			}

			actionScheduler.getActions().clear();
			actionScheduler.setFsm(fsm);
		}

		/**
		 * Creates a new empty action with the given name.
		 * 
		 * @param name
		 *            action name
		 * @return a new empty action with the given name
		 */
		private Action createNewAction(String name) {
			// scheduler
			Procedure scheduler = new Procedure(name, new Location(),
					IrFactory.eINSTANCE.createTypeBool());
			LocalVariable result = scheduler.newTempLocalVariable(
					ActionSplitter.this.actor.getFile(),
					IrFactory.eINSTANCE.createTypeBool(), "result");
			result.setIndex(1);
			BlockNode block = new BlockNode(scheduler);
			block.add(new Assign(result, new BoolExpr(true)));
			block.add(new Return(new VarExpr(new Use(result))));
			scheduler.getNodes().add(block);

			// body
			Procedure body = new Procedure(name, new Location(),
					IrFactory.eINSTANCE.createTypeVoid());
			block = new BlockNode(scheduler);
			block.add(new Return(null));
			scheduler.getNodes().add(block);

			// tag
			Tag tag = new Tag();
			tag.add(name);

			Action action = new Action(new Location(), tag, new Pattern(),
					new Pattern(), scheduler, body);

			// add action to actor's actions
			ActionSplitter.this.actor.getActions().add(action);

			return action;
		}

		/**
		 * Returns a new unique state name.
		 * 
		 * @return a new unique state name
		 */
		private String getNewStateName() {
			String stateName = branchName;
			Integer count = stateNames.get(stateName);
			if (count == null) {
				count = 1;
			}
			stateNames.put(stateName, count + 1);

			return stateName + "_" + count;
		}

		/**
		 * Replaces the transition <code>source</code> -&gt; <code>target</code>
		 * by a transition <code>source</code> -&gt; <code>newState</code> -&gt;
		 * <code>target</code>.
		 * 
		 * @param newAction
		 *            the newly-created action
		 */
		private void replaceTransition(Action newAction) {
			// add an FSM if the actor does not have one
			if (fsm == null) {
				addFsm(ActionSplitter.this.actor.getActionScheduler());
			}

			// add state
			String stateName = newAction.getName();
			fsm.addState(stateName);

			// update transitions
			fsm.removeTransition(sourceName, targetName, currentAction);
			fsm.addTransition(sourceName, stateName, currentAction);
			fsm.addTransition(stateName, targetName, newAction);
		}

		/**
		 * Split the current action
		 */
		protected void splitAction() {
			String newActionName = getNewStateName();

			// create new action
			nextAction = createNewAction(newActionName);

			// move code
			new CodeMover(itInstruction, itNode).moveCode(
					currentAction.getBody(), nextAction.getBody());

			// update transitions
			replaceTransition(nextAction);

			// set new source state to the new state name
			sourceName = newActionName;
		}

	}

	private class CodeMover extends AbstractActorTransformation {

		public CodeMover(ListIterator<Instruction> itInstruction,
				ListIterator<CFGNode> itNode) {
			this.itInstruction = itInstruction;
			this.itNode = itNode;
		}

		public void moveCode(Procedure oldProc, Procedure newProc) {
			// move instructions
			BlockNode block = BlockNode.getLast(newProc);
			Instruction instruction = itInstruction.previous();
			itInstruction.remove();
			block.add(instruction);
			while (itInstruction.hasNext()) {
				instruction = itInstruction.next();
				itInstruction.remove();
				block.add(instruction);
			}
		}

	}

	/**
	 * FSM of the actor. May be null if the actor has no FSM. May be updated if
	 * an FSM is added to the actor.
	 */
	protected FSM fsm;

	/**
	 * Map used to create new unique state names.
	 */
	protected Map<String, Integer> stateNames;

	@Override
	public void transform(Actor actor) {
		this.actor = actor;
		stateNames = new HashMap<String, Integer>();

		fsm = actor.getActionScheduler().getFsm();
		if (fsm == null) {
			// no FSM: simply visit all the actions
			List<Action> actions = new ArrayList<Action>(actor
					.getActionScheduler().getActions());
			for (Action action : actions) {
				// an FSM will be created if needed, from "init" to "init" (and
				// intermediate transitions created by the BranchVisitor)

				String sourceName = "init";
				String targetName = "init";
				visit(sourceName, targetName, action);
			}
		} else {
			// with an FSM: visits all transitions
			DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
			Set<UniqueEdge> edges = graph.edgeSet();
			for (UniqueEdge edge : edges) {
				State source = graph.getEdgeSource(edge);
				String sourceName = source.getName();

				State target = graph.getEdgeTarget(edge);
				String targetName = target.getName();

				Action action = (Action) edge.getObject();
				visit(sourceName, targetName, action);
			}
		}
	}

	/**
	 * Visits the given transition characterized by its source name, target name
	 * and action.
	 * 
	 * @param sourceName
	 *            name of source state
	 * @param targetName
	 *            name of target state
	 * @param action
	 *            action associated with transition
	 */
	abstract protected void visit(String sourceName, String targetName,
			Action action);

}
