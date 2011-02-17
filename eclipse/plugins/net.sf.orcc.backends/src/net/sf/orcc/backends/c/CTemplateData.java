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
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.attributes.IAttribute;

public class CTemplateData {

	private Map<Instance, Map<Port, IAttribute>> portMedium;

	private Map<Instance, Map<Port, IAttribute>> portMediumUpperCase;

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

	private void buildPortMedium(Network network) {
		for (Instance instance : network.getInstances()) {
			if (instance.isActor() || instance.isBroadcast()) {
				Map<Port, IAttribute> instancePorts = new HashMap<Port, IAttribute>();
				Map<Port, IAttribute> instancePortsUpperCase = new HashMap<Port, IAttribute>();
				for (Connection connection : network.getIncomingMap().get(
						instance)) {
					instancePorts.put(connection.getTarget(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getTarget(),
							connection.getAttribute("commMediumUpperCase"));
				}
				for (Connection connection : network.getOutgoingMap().get(
						instance)) {
					instancePorts.put(connection.getSource(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getSource(),
							connection.getAttribute("commMediumUpperCase"));
				}
				portMedium.put(instance, instancePorts);
				portMediumUpperCase.put(instance, instancePortsUpperCase);
			}
		}
	}

	/**
	 * Computes the mask map that associate a port mask to a transition. The
	 * port mask defines the port(s) read by actions in each transition.
	 */
	public void computeTemplateMaps(Actor actor, Network network) {
		maskInputs = new HashMap<FSM.Transition, String>();
		maskOutputs = new HashMap<Port, String>();
		portMedium = new HashMap<Instance, Map<Port, IAttribute>>();
		portMediumUpperCase = new HashMap<Instance, Map<Port, IAttribute>>();

		buildMaskInputs(actor);
		buildMaskOutputs(actor);
		buildPortMedium(network);

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

	public Map<Instance, Map<Port, IAttribute>> getPortMedium() {
		return portMedium;
	}

	public Map<Instance, Map<Port, IAttribute>> getUpperCasePortMedium() {
		return portMediumUpperCase;
	}
}
