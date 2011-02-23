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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;

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
	 * Map that associates an input port to a mask
	 */
	private Map<Port, String> maskInputs;

	/**
	 * Map that associates an output port to a mask
	 */
	private Map<Port, String> maskOutputs;

	/**
	 * Map that associates a transition with the minimum input pattern that
	 * allows all actions in the transition to fire
	 */
	private Map<Transition, Pattern> transitionPattern;

	/**
	 * Builds the input pattern
	 */
	private void buildInputPattern(Actor actor) {
		List<Action> actions = actor.getActionScheduler().getActions();

		for (Action action : actions) {
			Pattern actionPattern = action.getInputPattern();
			for (Entry<Port, Integer> entry : actionPattern.entrySet()) {
				Port port = entry.getKey();
				Integer numTokens = inputPattern.get(port);
				if (numTokens == null) {
					inputPattern.put(entry.getKey(), entry.getValue());
				} else {
					int maxNumTokens = Math.max(numTokens, entry.getValue());
					inputPattern.put(entry.getKey(), maxNumTokens);
				}
			}
		}
	}

	/**
	 * Builds the mask inputs map.
	 */
	private void buildMaskInputs(Actor actor) {
		int i = 0;
		for (Port port : actor.getInputs()) {
			int mask = (1 << i);
			i++;
			maskInputs.put(port, Integer.toHexString(mask));
		}
	}

	/**
	 * Builds the mask outputs map.
	 */
	private void buildMaskOutputs(Actor actor) {
		int i = 0;
		for (Port port : actor.getOutputs()) {
			int mask = (1 << i);
			i++;
			maskOutputs.put(port, Integer.toHexString(mask));
		}
	}

	/**
	 * Builds the transition pattern map.
	 */
	private void buildTransitionPattern(Actor actor) {
		FSM fsm = actor.getActionScheduler().getFsm();
		if (fsm == null) {
			return;
		}

		for (Transition transition : fsm.getTransitions()) {
			Pattern pattern = new Pattern();
			for (NextStateInfo info : transition.getNextStateInfo()) {
				Pattern actionPattern = info.getAction().getInputPattern();
				for (Entry<Port, Integer> entry : actionPattern.entrySet()) {
					Port port = entry.getKey();
					Integer numTokens = pattern.get(port);
					if (numTokens == null) {
						pattern.put(entry.getKey(), entry.getValue());
					} else {
						int maxNumTokens = Math
								.max(numTokens, entry.getValue());
						pattern.put(entry.getKey(), maxNumTokens);
					}
				}
			}

			transitionPattern.put(transition, pattern);
		}
	}

	/**
	 * Computes the mask map that associate a port mask to a transition. The
	 * port mask defines the port(s) read by actions in each transition.
	 */
	public void computeTemplateMaps(Actor actor) {
		inputPattern = new Pattern();
		maskInputs = new HashMap<Port, String>();
		maskOutputs = new HashMap<Port, String>();
		transitionPattern = new HashMap<FSM.Transition, Pattern>();

		buildInputPattern(actor);
		buildMaskInputs(actor);
		buildMaskOutputs(actor);
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
	 * Returns a map that associates an input port to a mask.
	 * 
	 * @return a map that associates an input port to a mask
	 */
	public Map<Port, String> getMaskInputs() {
		return maskInputs;
	}

	/**
	 * Returns a map that associates an output port to a mask.
	 * 
	 * @return a map that associates an output port to a mask
	 */
	public Map<Port, String> getMaskOutputs() {
		return maskOutputs;
	}

	/**
	 * Returns the map of associates a transition with the minimum input pattern
	 * that allows all actions in the transition to fire.
	 * 
	 * @return a map of transitions to pattern
	 */
	public Map<Transition, Pattern> getTransitionPattern() {
		return transitionPattern;
	}

}
