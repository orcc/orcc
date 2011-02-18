package net.sf.orcc.backends.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import net.sf.orcc.network.attributes.StringAttribute;

public class CTemplateData {

	private Map<Instance, Map<Port, IAttribute>> portMedium;

	private Map<Instance, Map<Port, IAttribute>> portMediumUpperCase;

	private Map<Transition, String> maskInputs;

	private Map<Port, String> maskOutputs;

	private List<Instance> listMediumInstances;

	private List<Port> listMediumPorts;

	private Map<Instance, List<String>> listMediumUsed;

	private List<String> listMediumUsedAllInstances;

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

	private void buildMediumInfo(Network network) {
		Map<Connection, Instance> connectionToInstance = new HashMap<Connection, Instance>();
		Map<Connection, Port> connectionToPort = new HashMap<Connection, Port>();
		Set<String> allMedium = new HashSet<String>();

		for (Instance instance : network.getInstances()) {
			if (instance.isActor() || instance.isBroadcast()) {
				Set<String> mediumSet = new HashSet<String>();
				Map<Port, IAttribute> instancePorts = new HashMap<Port, IAttribute>();
				Map<Port, IAttribute> instancePortsUpperCase = new HashMap<Port, IAttribute>();

				for (Connection connection : network.getIncomingMap().get(
						instance)) {
					instancePorts.put(connection.getTarget(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getTarget(),
							connection.getAttribute("commMediumUpperCase"));
					if (connection.getAttributes().containsKey("commMedium")) {
						connectionToInstance.put(connection, instance);
						connectionToPort
								.put(connection, connection.getTarget());

						StringAttribute connectionAttribute = (StringAttribute) connection.getAttribute("commMedium");
						mediumSet.add(connectionAttribute.getValue());
					}
				}
				for (Connection connection : network.getOutgoingMap().get(
						instance)) {
					instancePorts.put(connection.getSource(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getSource(),
							connection.getAttribute("commMediumUpperCase"));
					if (connection.getAttributes().containsKey("commMedium")) {
						connectionToInstance.put(connection, instance);
						connectionToPort
								.put(connection, connection.getSource());

						StringAttribute connectionAttribute = (StringAttribute) connection.getAttribute("commMedium");
						mediumSet.add(connectionAttribute.getValue());
					}
				}
				allMedium.addAll(mediumSet);

				List<String> mediumList = new ArrayList<String>();
				mediumList.addAll(mediumSet);
				listMediumUsed.put(instance, mediumList);

				portMedium.put(instance, instancePorts);
				portMediumUpperCase.put(instance, instancePortsUpperCase);
			}
		}

		List<Connection> allConnections = new ArrayList<Connection>(
				connectionToInstance.keySet());
		Collections.sort(allConnections);
		for (Connection connection : allConnections) {
			listMediumInstances.add(connectionToInstance.get(connection));
			listMediumPorts.add(connectionToPort.get(connection));
		}
		listMediumUsedAllInstances.addAll(allMedium);
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
		listMediumInstances = new ArrayList<Instance>();
		listMediumPorts = new ArrayList<Port>();
		listMediumUsed = new HashMap<Instance, List<String>>();
		listMediumUsedAllInstances = new ArrayList<String>();

		buildMaskInputs(actor);
		buildMaskOutputs(actor);
		buildMediumInfo(network);

		numInputs = actor.getInputs().getLength();
	}

	public void computeTemplateMaps(Network network) {
		portMedium = new HashMap<Instance, Map<Port, IAttribute>>();
		portMediumUpperCase = new HashMap<Instance, Map<Port, IAttribute>>();
		listMediumInstances = new ArrayList<Instance>();
		listMediumPorts = new ArrayList<Port>();
		listMediumUsed = new HashMap<Instance, List<String>>();
		listMediumUsedAllInstances = new ArrayList<String>();

		buildMediumInfo(network);
	}

	public Map<Instance, List<String>> getListAllMedium() {
		return listMediumUsed;
	}

	public List<Instance> getListMediumInstances() {
		return listMediumInstances;
	}

	public List<Port> getListMediumPorts() {
		return listMediumPorts;
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

	public List<String> getAllMediumsAllInstances() {
		return listMediumUsedAllInstances;
	}

	public Map<Instance, Map<Port, IAttribute>> getUpperCasePortMedium() {
		return portMediumUpperCase;
	}
}
