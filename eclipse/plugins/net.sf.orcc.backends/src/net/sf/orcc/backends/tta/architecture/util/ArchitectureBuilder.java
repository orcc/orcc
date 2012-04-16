package net.sf.orcc.backends.tta.architecture.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.backends.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.tta.architecture.Component;
import net.sf.orcc.backends.tta.architecture.Design;
import net.sf.orcc.backends.tta.architecture.DesignConfiguration;
import net.sf.orcc.backends.tta.architecture.Fifo;
import net.sf.orcc.backends.tta.architecture.Port;
import net.sf.orcc.backends.tta.architecture.Processor;
import net.sf.orcc.backends.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.tta.architecture.Signal;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class ArchitectureBuilder extends DfSwitch<Design> {

	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;

	private Design design;

	@SuppressWarnings("unused")
	private DesignConfiguration conf;
	@SuppressWarnings("unused")
	private Mapping mapping;

	private Map<Vertex, Component> componentMap;
	private Map<net.sf.orcc.df.Port, Port> portMap;

	public ArchitectureBuilder(DesignConfiguration conf) {
		design = factory.createDesign();
		componentMap = new HashMap<Vertex, Component>();
		portMap = new HashMap<net.sf.orcc.df.Port, Port>();
	}

	public ArchitectureBuilder(DesignConfiguration conf, Mapping mapping) {
		this.conf = conf;
		this.mapping = mapping;
	}

	private void addFifo(Connection connection) {
		Component source = componentMap.get(connection.getSource());
		Component target = componentMap.get(connection.getTarget());
		Port sourcePort = portMap.get(connection.getSourcePort());
		Port targetPort = portMap.get(connection.getTargetPort());

		String id = connection.getAttribute("id").getValue().toString();
		Fifo fifo = factory.createFifo(id, source, target, sourcePort,
				targetPort);

		// Generics
		int size = connection.getSize();
		fifo.setAttribute("size", size);
		fifo.setAttribute("width", 32);
		fifo.setAttribute("widthu",
				(int) Math.ceil(Math.log(size) / Math.log(2)));

		design.add(fifo);
		design.getFifos().add(fifo);
	}

	private EList<Port> createPorts(EList<net.sf.orcc.df.Port> calPorts) {
		EList<Port> ports = new BasicEList<Port>();
		for (net.sf.orcc.df.Port calPort : calPorts) {
			Port port = factory.createPort();
			port.setLabel(calPort.getName());
			ports.add(port);
			portMap.put(calPort, port);
		}
		return ports;
	}

	private void addSignal(Connection connection) {
		Vertex source = componentMap.get(connection.getSource());
		Vertex target = componentMap.get(connection.getTarget());
		Port sourcePort = portMap.get(connection.getSourcePort());
		Port targetPort = portMap.get(connection.getTargetPort());
		int size;

		if (source == null) {
			net.sf.orcc.df.Port calPort = (net.sf.orcc.df.Port) connection
					.getSource();
			source = portMap.get(calPort);
			size = calPort.getType().getSizeInBits();
		} else {
			size = connection.getSourcePort().getType().getSizeInBits();
		}

		if (target == null) {
			target = portMap.get((net.sf.orcc.df.Port) connection.getTarget());
		}

		Signal signal = factory.createSignal(connection.getAttribute("id")
				.getValue().toString(), size, source, target, sourcePort,
				targetPort);

		design.add(signal);
	}

	@Override
	public Design caseBroadcast(Broadcast broadcast) {
		Component component = factory.createComponent(
				broadcast.getSimpleName(), "broadcast_"
						+ broadcast.getOutputs().size());
		component.getInputs().addAll(createPorts(broadcast.getInputs()));
		component.getOutputs().addAll(createPorts(broadcast.getOutputs()));
		design.getBroadcasts().add(component);
		design.add(component);
		componentMap.put(broadcast, component);
		return null;
	}

	@Override
	public Design caseConnection(Connection connection) {
		net.sf.orcc.df.Port sourcePort = connection.getSourcePort() == null ? (net.sf.orcc.df.Port) connection
				.getSource() : connection.getSourcePort();
		net.sf.orcc.df.Port targetPort = connection.getTargetPort() == null ? (net.sf.orcc.df.Port) connection
				.getTarget() : connection.getTargetPort();

		if (sourcePort.isNative() || targetPort.isNative()) {
			addSignal(connection);
		} else {
			addFifo(connection);
		}
		return null;
	}

	@Override
	public Design caseInstance(Instance instance) {
		Actor actor = instance.getActor();
		Component component;
		if (actor.isNative()) {
			component = factory.createComponent(instance.getName(), instance
					.getActor().getSimpleName());
			design.getComponents().add(component);
			design.add(component);
			componentMap.put(instance, component);
		} else {
			int memorySize = computeNeededMemorySize(instance);
			String name = instance.getSimpleName();
			Processor processor = factory.createProcessor(name + "_inst",
					"processor_" + name, ProcessorConfiguration.STANDARD,
					memorySize);
			component = processor;
			design.getProcessors().add(processor);
			design.add(processor);
			componentMap.put(instance, processor);
		}
		component.getInputs().addAll(createPorts(actor.getInputs()));
		component.getOutputs().addAll(createPorts(actor.getOutputs()));
		return null;
	}

	@Override
	public Design caseNetwork(Network network) {
		design = factory.createDesign();
		design.getInputs().addAll(createPorts(network.getInputs()));
		design.getOutputs().addAll(createPorts(network.getOutputs()));
		for (Vertex entity : network.getEntities()) {
			doSwitch(entity);
		}
		for (Instance instance : network.getInstances()) {
			doSwitch(instance);
		}
		for (net.sf.orcc.df.Port port : network.getInputs()) {
			doSwitch(port);
		}
		for (Connection connection : network.getConnections()) {
			doSwitch(connection);
		}
		return design;
	}

	private int computeNeededMemorySize(Action action) {
		int neededMemorySize = 0;
		neededMemorySize += computeNeededMemorySize(action.getBody()
				.getLocals());
		neededMemorySize += computeNeededMemorySize(action.getInputPattern()
				.getVariables());
		neededMemorySize += computeNeededMemorySize(action.getScheduler()
				.getLocals());
		neededMemorySize += computeNeededMemorySize(action.getPeekPattern()
				.getVariables());
		return neededMemorySize;
	}

	/**
	 * Returns the memory size needed by the given actor in bits. This size
	 * corresponds to the sum of state variable size (only assignable variables
	 * or constant arrays) plus the maximum of the sum of local arrays per each
	 * action.
	 * 
	 * @param instance
	 *            the given instance
	 * @return the memory size needed by the given actor
	 */
	private int computeNeededMemorySize(Instance instance) {
		int neededMemorySize = 0;
		// Compute memory size needed by state variable
		for (Var var : instance.getActor().getStateVars()) {
			if (var.isAssignable() || var.getType().isList()) {
				neededMemorySize += getSize(var.getType());
			}
		}
		// Compute memory size needed by the actions
		for (Action action : instance.getActor().getActions()) {
			neededMemorySize += computeNeededMemorySize(action) * 1.3;
		}
		return neededMemorySize;
	}

	/**
	 * Return the memory size needed by the given variables. That corresponds to
	 * the total size of the contained arrays.
	 * 
	 * @param vars
	 *            the list procedure
	 * @return the memory size needed by the given procedure
	 */
	private int computeNeededMemorySize(List<Var> localVars) {
		int neededMemorySize = 0;
		// Compute memory size needed by local arrays
		for (Var var : localVars) {
			if (var.getType().isList()) {
				neededMemorySize += getSize(var.getType());
			}
		}

		return neededMemorySize;
	}

	private int getSize(Type type) {
		int size;
		if (type.isList()) {
			size = getSize(((TypeList) type).getInnermostType());
			for (int dim : type.getDimensions()) {
				size *= dim;
			}
		} else if (type.isBool()) {
			size = 8;
		} else {
			size = type.getSizeInBits();
		}
		return size;
	}
}
