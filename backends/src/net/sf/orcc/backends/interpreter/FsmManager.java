package net.sf.orcc.backends.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;

/**
 * Interpreter class providing easy FSM management for the interpreter scheduler
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class FsmManager {

	HashMap<String, List<TransitionManager>> fsm;

	public class TransitionManager {
		public String targetState;
		public Action transitionAction;

		public TransitionManager(String targetState, Action transitionAction) {
			this.targetState = targetState;
			this.transitionAction = transitionAction;
		}
	}

	public FsmManager(FSM irFsm) {
		// Build the manager map <currentState, transitionTable>
		fsm = new HashMap<String, List<TransitionManager>>();
		List<Transition> irTransitions = irFsm.getTransitions();
		for (Transition transition : irTransitions) {
			String sourceState = transition.getSourceState().getName();
			List<TransitionManager> transitionManagerList = new ArrayList<TransitionManager>();
			for (NextStateInfo nextStateInfo : transition.getNextStateInfo()) {
				String targetState = nextStateInfo.getTargetState().getName();
				Action transitionAction = nextStateInfo.getAction();
				TransitionManager transitionManager = new TransitionManager(
						targetState, transitionAction);
				transitionManagerList.add(transitionManager);
			}
			fsm.put(sourceState, transitionManagerList);
		}
	}

	public Iterator<TransitionManager> getIt(String currState) {
		return fsm.get(currState).iterator();
	}

}
