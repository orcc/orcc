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
			if (actor.getInputs().size() == 0) {
				actor.addAttribute("variableInputPattern");
				continue;
			}
			if (actor.hasFsm()) {
				for (State state : actor.getFsm().getStates()) {
					if (state.getOutgoing().size() > 1) {
						inspectState(actor, state);
					}
				}
			} else {
				if (actor.getActions().size() > 1) {
					inspectActionList(actor, actor.getActions(), false);
				}
			}
			if (actor.getActions().size() > 1) {
				inspectActionList(actor, actor.getActions(), true);
			} else {
				Pattern inputPattern = actor.getActions().get(0).getInputPattern();
				for(Port port : inputPattern.getPorts()) {
					port.setNumTokensConsumed(inputPattern.getNumTokensMap().get(port));
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
		inspectActionList(actor, actions, false);
	}

	private void inspectActionList(Actor actor, List<Action> actions, boolean actorLevel) {
		for (Action first : actions) {
			for (Action second : actions) {
				comparePatterns(actor, first, second, actorLevel);
			}
		}
	}

	private void comparePatterns(Actor actor, Action firstAction, Action secondAction, boolean actorLevel) {
		Pattern first = firstAction.getInputPattern();
		Pattern second = secondAction.getInputPattern();
		for(Port port : first.getPorts()) {
			int firstTokenRate = first.getNumTokensMap().get(port);
			port.setNumTokensConsumed(firstTokenRate);
			if (second.getNumTokensMap().get(port) != null) {
				int secondTokenRate = second.getNumTokensMap().get(port);
				if (firstTokenRate != secondTokenRate) {
					if (!actorLevel) {
						OrccLogger.warnln("(" + actor.getName() + ") actions '" + firstAction.getName() + "' and '" + secondAction.getName() +
								"'\n have different token rate for port '" + port.getName() + "' Application may deadlock.");
						port.setNumTokensConsumed(-1);
					}
				} 
			} else {
				if (second.getNumTokensMap().size() > 0) {
					if (actorLevel) {
						if (!actor.hasAttribute("variableInputPattern")) {
							actor.addAttribute("variableInputPattern");
							OrccLogger.traceln("Info: " + actor.getName() + " has varying input patterns");
						}
					} else {
						OrccLogger.warnln("(" + actor.getName() + ") action '" + firstAction.getName() + "' reads port '"  + port.getName() +
								"'\n but action '"+ secondAction.getName() + "' does not. Application may deadlock.");
					}
				}
			}
		}
	}
}
