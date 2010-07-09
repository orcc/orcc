package net.sf.orcc.backends.llvm;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;

/**
 * This class computes a map for inserting metadata information into LLVM
 * template.
 * 
 * @author Jerome GORIN
 * 
 */
public class LLVMTemplateData {

	/**
	 * Actor to compute
	 */
	private Actor actor;

	/**
	 * Medata identifier counter
	 */
	private int id;

	/**
	 * Medata container
	 */
	private HashMap<Object, Integer> templateDataMap;

	/**
	 * Computes a map of metadata for LLVM template
	 * 
	 */
	public LLVMTemplateData(Actor actor) {
		templateDataMap = new HashMap<Object, Integer>();
		this.actor = actor;
		this.id = 0;

		computeTemplateMaps();
	}

	private void computeAction(Action action) {
		templateDataMap.put(action, id++);
		// Avoid to add null id in map
		if (!action.getTag().getIdentifiers().isEmpty()) {
			templateDataMap.put(action.getTag(), id++);
		}
		templateDataMap.put(action.getScheduler(), id++);
		templateDataMap.put(action.getBody(), id++);
	}

	private void computeActionScheduler(ActionScheduler actSched) {
		templateDataMap.put(actor.getActionScheduler(), id++);
		if (actSched.hasFsm()) {
			FSM fsm = actSched.getFsm();
			templateDataMap.put(fsm, id++);
			templateDataMap.put(fsm.getStates(), id++);
			templateDataMap.put(fsm.getTransitions(), id++);

			for (Transition transition : fsm.getTransitions()) {
				templateDataMap.put(transition, id++);
				templateDataMap.put(transition.getNextStateInfo(), id++);
				for (NextStateInfo nextState : transition.getNextStateInfo()) {
					templateDataMap.put(nextState, id++);
				}

			}
		}
	}

	private void computePort(Port port) {
		templateDataMap.put(port, id++);
		templateDataMap.put(port.getType(), id++);
	}

	private void computeProc(Procedure proc) {
		templateDataMap.put(proc, id++);
	}

	private void computeTemplateMaps() {
		// Insert source file info
		templateDataMap.put(actor.getFile(), id++);

		// Insert name
		templateDataMap.put(actor.getName(), id++);

		// Insert action scheduler
		computeActionScheduler(actor.getActionScheduler());

		// Insert inputs
		for (Port input : actor.getInputs()) {
			computePort(input);
		}

		// Insert outputs
		for (Port output : actor.getOutputs()) {
			computePort(output);
		}

		// Insert statevars
		for (Variable var : actor.getStateVars()) {
			computeVar(var);
		}

		// Insert parameters
		for (Variable parameter : actor.getParameters()) {
			computeVar(parameter);
		}

		// Insert procedures
		for (Procedure procedure : actor.getProcs()) {
			computeProc(procedure);
		}

		// Insert procedures
		for (Action initialize : actor.getInitializes()) {
			computeAction(initialize);
		}

		// Insert actions
		for (Action action : actor.getActions()) {
			computeAction(action);
		}
	}

	private void computeVar(Variable var) {
		templateDataMap.put(var, id++);
		templateDataMap.put(var.getName(), id++);
		templateDataMap.put(var.getType(), id++);
	}

	/**
	 * get the template map computed
	 * 
	 * @return a map of metadata information associated with their id.
	 */
	public Map<Object, Integer> getTemplateData() {
		return templateDataMap;
	}

}
