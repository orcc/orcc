/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.c;

import static net.sf.orcc.df.DfFactory.eINSTANCE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.Transitions;

/**
 * This class allows the string template accessing informations about
 * application's instances
 * 
 * @author Matthieu Wipliez
 * @author Damien de Saint Jorre
 * 
 */
public class CActorTemplateData {

	/**
	 * Minimum input pattern that allows all actions outside of an FSM to fire
	 */
	private Pattern inputPattern;

	/**
	 * Map that associates a transition with the minimum input pattern that
	 * allows all actions in the transition to fire
	 */
	private Map<Transitions, Pattern> transitionPattern;

	/**
	 * Builds the input pattern
	 */
	private void buildInputPattern(Actor actor) {
		List<Action> actions = actor.getActionsOutsideFsm();

		for (Action action : actions) {
			Pattern actionPattern = action.getInputPattern();
			for (Port port : actionPattern.getPorts()) {
				Integer numTokens = inputPattern.getNumTokens(port);
				if (numTokens == null) {
					numTokens = actionPattern.getNumTokens(port);
				} else {
					numTokens = Math.max(numTokens,
							actionPattern.getNumTokens(port));
				}

				inputPattern.setNumTokens(port, numTokens);
			}
		}
	}

	/**
	 * Builds the transition pattern map.
	 */
	private void buildTransitionPattern(Actor actor) {
		FSM fsm = actor.getFsm();
		if (fsm == null) {
			return;
		}

		for (Transitions transitions : fsm.getTransitions()) {
			Pattern pattern = eINSTANCE.createPattern();
			for (Transition transition : transitions.getList()) {
				Action action = transition.getAction();
				Pattern actionPattern = action.getInputPattern();
				for (Port port : actionPattern.getPorts()) {
					Integer numTokens = pattern.getNumTokens(port);
					if (numTokens == null) {
						numTokens = actionPattern.getNumTokens(port);
					} else {
						numTokens = Math.max(numTokens,
								actionPattern.getNumTokens(port));
					}

					pattern.setNumTokens(port, numTokens);
				}
			}

			transitionPattern.put(transitions, pattern);
		}
	}

	/**
	 * Computes the mask map that associate a port mask to a transition. The
	 * port mask defines the port(s) read by actions in each transition.
	 */
	public void computeTemplateMaps(Actor actor) {
		inputPattern = eINSTANCE.createPattern();
		transitionPattern = new HashMap<Transitions, Pattern>();

		buildInputPattern(actor);
		buildTransitionPattern(actor);
	}

	/**
	 * Returns the minimum input pattern that allows all actions outside of an
	 * FSM to fire.
	 * 
	 * @return the minimum input pattern that allows all actions outside of an
	 *         FSM to fire
	 */
	public Pattern getInputPattern() {
		return inputPattern;
	}

	/**
	 * Returns the map of associates a transition with the minimum input pattern
	 * that allows all actions in the transition to fire.
	 * 
	 * @return a map of transitions to pattern
	 */
	public Map<Transitions, Pattern> getTransitionPattern() {
		return transitionPattern;
	}

}
