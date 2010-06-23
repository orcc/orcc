package net.sf.orcc.backends.llvm;

import java.util.HashMap;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Variable;

public class LLVMTemplateData {

	public HashMap<Object, Integer> computeTemplateMaps(Actor actor) {
		HashMap<Object, Integer> MDMap = new HashMap<Object, Integer>();
		int MDId = 0;

		// Insert source file info
		MDMap.put(actor.getFile(), MDId++);

		// Insert name
		MDMap.put(actor.getName(), MDId++);

		// Insert action scheduler
		ActionScheduler actSched = actor.getActionScheduler();
		MDMap.put(actor.getActionScheduler(), MDId++);
		if (actSched.hasFsm()) {
			FSM fsm = actSched.getFsm();
			MDMap.put(fsm, MDId++);
			MDMap.put(fsm.getStates(), MDId++);
			MDMap.put(fsm.getTransitions(), MDId++);
			
			for (Transition transition : fsm.getTransitions()){
				MDMap.put(transition, MDId++);
				MDMap.put(transition.getNextStateInfo(), MDId++);
				for (NextStateInfo nextState : transition.getNextStateInfo()){
					MDMap.put(nextState, MDId++);
				}
				
			}
		}

		// Insert inputs
		for (Port input : actor.getInputs()) {
			MDMap.put(input, MDId++);
			MDMap.put(input.getType(), MDId++);

		}

		// Insert outputs
		for (Port output : actor.getOutputs()) {
			MDMap.put(output, MDId++);
			MDMap.put(output.getType(), MDId++);
		}

		// Insert statevars
		for (Variable var : actor.getStateVars()) {
			MDMap.put(var, MDId++);
			MDMap.put(var.getName(), MDId++);
			MDMap.put(var.getType(), MDId++);
		}
		
		// Insert parameters
		for (Variable parameter : actor.getParameters()) {
			MDMap.put(parameter, MDId++);
			MDMap.put(parameter.getName(), MDId++);
			MDMap.put(parameter.getType(), MDId++);
		}

		// Insert actions
		for (Action action : actor.getActions()) {
			MDMap.put(action, MDId++);
			MDMap.put(action.getTag(), MDId++);
			MDMap.put(action.getScheduler(), MDId++);
			MDMap.put(action.getBody(), MDId++);

		}

		return MDMap;
	}
}
