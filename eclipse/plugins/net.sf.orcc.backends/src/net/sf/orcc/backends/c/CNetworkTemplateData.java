package net.sf.orcc.backends.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.StringAttribute;

public class CNetworkTemplateData {

	private List<Instance> listMediumInstances;

	private List<Port> listMediumPorts;

	private Map<Instance, List<String>> listMediumUsed;

	private List<String> listMediumUsedAllInstances;

	private Map<Instance, Map<Port, IAttribute>> portMedium;

	private Map<Instance, Map<Port, IAttribute>> portMediumUpperCase;

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

						StringAttribute connectionAttribute = (StringAttribute) connection
								.getAttribute("commMedium");
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

						StringAttribute connectionAttribute = (StringAttribute) connection
								.getAttribute("commMedium");
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

	public void computeTemplateMaps(Network network) {
		portMedium = new HashMap<Instance, Map<Port, IAttribute>>();
		portMediumUpperCase = new HashMap<Instance, Map<Port, IAttribute>>();
		listMediumInstances = new ArrayList<Instance>();
		listMediumPorts = new ArrayList<Port>();
		listMediumUsed = new HashMap<Instance, List<String>>();
		listMediumUsedAllInstances = new ArrayList<String>();

		buildMediumInfo(network);
	}

	public List<String> getAllMediumsAllInstances() {
		return listMediumUsedAllInstances;
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

	public Map<Instance, Map<Port, IAttribute>> getPortMedium() {
		return portMedium;
	}

	public Map<Instance, Map<Port, IAttribute>> getUpperCasePortMedium() {
		return portMediumUpperCase;
	}
}
