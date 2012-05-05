package net.sf.orcc.backends.llvm.tta.architecture.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.llvm.tta.architecture.Buffer;
import net.sf.orcc.backends.llvm.tta.architecture.Component;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.DesignConfiguration;
import net.sf.orcc.backends.llvm.tta.architecture.Port;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.llvm.tta.architecture.Signal;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
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
import org.eclipse.emf.ecore.util.EcoreUtil;

public class ArchitectureBuilder extends DfSwitch<Design> {

	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;

	private Design design;

	@SuppressWarnings("unused")
	private DesignConfiguration conf;
	@SuppressWarnings("unused")
	private Mapping mapping;

	private Map<Vertex, Component> componentMap;
	private Map<net.sf.orcc.df.Port, Port> portMap;
	private Map<Component, Map<Component, Buffer>> bufferMap;

	private List<String> optimizedActors;

	public ArchitectureBuilder(DesignConfiguration conf) {
		design = factory.createDesign();
		componentMap = new HashMap<Vertex, Component>();
		portMap = new HashMap<net.sf.orcc.df.Port, Port>();
		bufferMap = new HashMap<Component, Map<Component, Buffer>>();

		optimizedActors = new ArrayList<String>();
		optimizedActors.add("decoder_texture_IQ");
		optimizedActors.add("Merger");
		optimizedActors.add("decoder_motion_interpolation");
		optimizedActors.add("decoder_motion_add");
		optimizedActors.add("decoder_texture_idct2d");
		optimizedActors.add("decoder_motion_framebuf");
	}

	public ArchitectureBuilder(DesignConfiguration conf, Mapping mapping) {
		this.conf = conf;
		this.mapping = mapping;
	}

	private void addToBuffer(Connection connection) {
		Component source = componentMap.get(connection.getSource());
		Component target = componentMap.get(connection.getTarget());
		Port sourcePort = portMap.get(connection.getSourcePort());
		Port targetPort = portMap.get(connection.getTargetPort());

		Map<Component, Buffer> tgtToBufferMap;
		if (bufferMap.containsKey(source)) {
			tgtToBufferMap = bufferMap.get(source);
		} else {
			tgtToBufferMap = new HashMap<Component, Buffer>();
			bufferMap.put(source, tgtToBufferMap);
		}

		Buffer buffer;
		if (tgtToBufferMap.containsKey(target)) {
			buffer = tgtToBufferMap.get(target);
		} else {
			buffer = factory.createBuffer(source, target, sourcePort,
					targetPort);
			tgtToBufferMap.put(target, buffer);
			design.add(buffer);
		}
		buffer.getMappedConnections().add(connection);
	}

	private EList<Port> createPorts(EList<net.sf.orcc.df.Port> calPorts) {
		EList<Port> ports = new BasicEList<Port>();
		for (net.sf.orcc.df.Port calPort : calPorts) {
			Port port = factory.createPort();
			port.setLabel(calPort.getName());
			port.getAttributes().addAll(
					EcoreUtil.copyAll(calPort.getAttributes()));
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
			source.setAttribute("size", size);
		} else {
			size = connection.getSourcePort().getType().getSizeInBits();
			sourcePort.setAttribute("size", size);
		}

		if (target == null) {
			target = portMap.get((net.sf.orcc.df.Port) connection.getTarget());
			target.setAttribute("size", size);
		} else {
			targetPort.setAttribute("size", size);
		}

		Signal signal = factory.createSignal(connection.getAttribute("id")
				.getValue().toString(), size, source, target, sourcePort,
				targetPort);

		design.add(signal);
	}

	@Override
	public Design caseBroadcast(Broadcast broadcast) {
		ProcessorConfiguration conf = ProcessorConfiguration.STANDARD;
		Processor processor = factory.createProcessor(
				"processor_" + broadcast.getName(), conf, 512);
		processor.getMappedActors().add(broadcast);
		processor.getInputs().addAll(createPorts(broadcast.getInputs()));
		processor.getOutputs().addAll(createPorts(broadcast.getOutputs()));
		design.add(processor);
		componentMap.put(broadcast, processor);
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
			addToBuffer(connection);
		}
		return null;
	}

	@Override
	public Design caseInstance(Instance instance) {
		Actor actor = instance.getActor();
		Component component;
		if (actor.isNative()) {
			component = factory.createComponent(instance.getActor()
					.getSimpleName());
			design.getComponents().add(component);
			design.add(component);
			for (Argument arg : instance.getArguments()) {
				component.setAttribute(arg.getVariable().getName(),
						arg.getValue());
			}
			componentMap.put(instance, component);
		} else {
			int memorySize = computeNeededMemorySize(instance);

			// ProcessorConfiguration conf = optimizedActors.contains(instance
			// .getName()) ? ProcessorConfiguration.CUSTOM
			// : ProcessorConfiguration.STANDARD;

			ProcessorConfiguration conf = ProcessorConfiguration.STANDARD;
			Processor processor = factory.createProcessor("processor_"
					+ instance.getName(), conf, memorySize);
			component = processor;
			processor.getMappedActors().add(instance);
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
