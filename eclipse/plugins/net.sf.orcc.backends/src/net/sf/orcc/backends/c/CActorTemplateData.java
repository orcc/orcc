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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;

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
	 * Map that associate an input port mask to a transition
	 */
	private Map<Transition, String> maskInputs;

	/**
	 * Map that associate an output port mask to a transition
	 */
	private Map<Port, String> maskOutputs;

	/**
	 * Contains number of inputs in this instance.
	 */
	private int numInputs;

	/**
	 * Builds the mask inputs map.
	 */
	private void buildMaskInputs(Actor actor) {
		FSM fsm = actor.getActionScheduler().getFsm();
		if (fsm == null) {
			return;
		}

		for (Transition transition : fsm.getTransitions()) {
			Set<Port> ports = new HashSet<Port>();
			for (NextStateInfo info : transition.getNextStateInfo()) {
				Pattern pattern = info.getAction().getInputPattern();
				for (Entry<Port, Integer> entry : pattern.entrySet()) {
					ports.add(entry.getKey());
				}
			}

			// create the mask
			int mask = 0;
			int i = 0;
			for (Port port : actor.getInputs()) {
				if (ports.contains(port)) {
					mask |= (1 << i);
				}

				i++;
			}

			maskInputs.put(transition, Integer.toHexString(mask));
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
	 * Computes the mask map that associate a port mask to a transition. The
	 * port mask defines the port(s) read by actions in each transition.
	 */
	public void computeTemplateMaps(Actor actor) {
		maskInputs = new HashMap<FSM.Transition, String>();
		maskOutputs = new HashMap<Port, String>();

		buildMaskInputs(actor);
		buildMaskOutputs(actor);

		numInputs = actor.getInputs().getLength();
	}

	/**
	 * Returns the mask for all the input ports of the actor. Bit 0 is set for
	 * port 0, until bit n is set for port n.
	 * 
	 * @return an integer mask for all the input ports of the actor
	 */
	public String getMaskInputs() {
		int mask = (1 << numInputs) - 1;
		return Integer.toHexString(mask);
	}

	/**
	 * Returns the map of transition to mask of input ports of the actor read by
	 * actions in the transition. Bit 0 is set for port 0, until bit n is set
	 * for port n.
	 * 
	 * @return a map of transitions to input ports' masks
	 */
	public Map<Transition, String> getMaskInputsTransition() {
		return maskInputs;
	}

	/**
	 * Returns the mask for the output port of the actor read by actions in the
	 * given transition. Bit 0 is set for port 0, until bit n is set for port n.
	 * 
	 * @return an integer mask for the input ports of the actor read by actions
	 *         in the given transition
	 */
	public Map<Port, String> getMaskOutput() {
		return maskOutputs;
	}
}
