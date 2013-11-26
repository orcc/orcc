package net.sf.orcc.backends.c.dal;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.graph.Edge;
import net.sf.orcc.util.OrccLogger;
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
 * @author Jani Boutellier
 * 
 */
public class KPNValidator {

	public void validate(Network network) {
		for (Actor actor : network.getAllActors()) {
			if (actor.hasFsm()) {
				for (State state : actor.getFsm().getStates()) {
					if (state.getOutgoing().size() > 1) {
						inspectState(actor, state);
					}
				}
			} else {
				if (actor.getActions().size() > 1) {
					inspectActionList(actor, actor.getActions());
				}
			}
		}
	}
	
	private void inspectState(Actor actor, State srcState) {
		List<Action> actions = new ArrayList<Action>();
		for (Edge edge : srcState.getOutgoing()) {
			actions.add(((Transition) edge).getAction());
		}
		actions.addAll(actor.getActionsOutsideFsm());
		inspectActionList(actor, actions);
	}

	private void inspectActionList(Actor actor, List<Action> actions) {
		for (Action first : actions) {
			for (Action second : actions) {
				comparePatterns(actor, first, second);
			}
		}
	}

	private void comparePatterns(Actor actor, Action firstAction, Action secondAction) {
		Pattern first = firstAction.getInputPattern();
		Pattern second = secondAction.getInputPattern();
		for(Port port : first.getPorts()) {
			int firstTokenRate = first.getNumTokensMap().get(port);
			if (second.getNumTokensMap().get(port) != null) {
				int secondTokenRate = second.getNumTokensMap().get(port);
				if (firstTokenRate != secondTokenRate) {
					OrccLogger.warnln("(" + actor.getName() + ") actions '" + firstAction.getName() + "' and '" + secondAction.getName() +
							"'\n have different token rate for port '" + port.getName() + "' Application may deadlock.");
				}
			} else {
				if (second.getNumTokensMap().size() > 0) {
					OrccLogger.warnln("(" + actor.getName() + ") action '" + firstAction.getName() + "' reads port '"  + port.getName() +
							"'\n but action '"+ secondAction.getName() + "' does not. Application may deadlock.");
				}
			}
		}
	}
}
