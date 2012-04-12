package net.sf.orcc.backends.tta.architecture.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Vertex;
import net.sf.orcc.backends.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.tta.architecture.Component;
import net.sf.orcc.backends.tta.architecture.Design;
import net.sf.orcc.backends.tta.architecture.DesignConfiguration;
import net.sf.orcc.backends.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;

public class ArchitectureBuilder extends DfSwitch<Design> {

	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;
	private Design design;
	private DesignConfiguration conf;

	private Map<Vertex, Vertex> vertexMap;
	private Map<Edge, Edge> edgeMap;

	private Mapping mapping;

	public ArchitectureBuilder(DesignConfiguration conf) {
		design = factory.createDesign();
		vertexMap = new HashMap<Vertex, Vertex>();
		edgeMap = new HashMap<Edge, Edge>();
	}

	public ArchitectureBuilder(DesignConfiguration conf, Mapping mapping) {
		this.conf = conf;
		this.mapping = mapping;
	}

	public Design caseNetwork(Network network) {
		design = factory.createDesign();
		if (mapping == null) {
			// One-to-one mapping
			for (Instance instance : network.getInstances()) {
				Vertex vertex;
				if (instance.getActor().isNative()) {
					vertex = factory.createComponent();
				} else {
					int memorySize = ArchitectureMemoryStats
							.computeNeededMemorySize(instance.getActor());
					vertex = factory.createProcessor(instance.getSimpleName(),
							ProcessorConfiguration.STANDARD, memorySize);
				}
				design.add(vertex);
				vertexMap.put(instance, vertex);
			}
		} else {
			// TODO: Map several actors on the same processor
		}
		super.caseNetwork(network);
		return design;
	}

	public Design caseBroadcast(Broadcast broadcast) {
		Component component = factory.createComponent();
		design.add(component);
		vertexMap.put(broadcast, component);
		return null;
	}

	public Design caseConnection(Connection connection) {
		Edge edge;
		if (connection.hasAttribute("native")) {
			edge = factory.createSignal();
		} else {
			edge = factory.createFifo();
		}
		design.add(edge);
		edgeMap.put(connection, edge);
		return null;
	}

}
