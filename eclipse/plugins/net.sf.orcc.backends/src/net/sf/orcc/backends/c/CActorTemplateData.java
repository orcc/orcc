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

public class CActorTemplateData {

	private Map<Transition, String> maskInputs;

	private Map<Port, String> maskOutputs;

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
