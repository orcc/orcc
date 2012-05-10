package net.sf.orcc.backends.llvm.tta.architecture.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.llvm.tta.architecture.AddressSpace;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.llvm.tta.architecture.Buffer;
import net.sf.orcc.backends.llvm.tta.architecture.Component;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.DesignConfiguration;
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit;
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
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;

public class ArchitectureBuilder extends DfSwitch<Design> {

	private Map<Component, Map<Component, Buffer>> bufferMap;

	private Map<Vertex, Component> componentMap;

	@SuppressWarnings("unused")
	private DesignConfiguration conf;
	private Design design;

	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;
	@SuppressWarnings("unused")
	private Mapping mapping;
	private List<String> optimizedActors;

	private int bufferId = 0;

	public ArchitectureBuilder(DesignConfiguration conf) {
		design = factory.createDesign();
		componentMap = new HashMap<Vertex, Component>();
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

	private FunctionUnit addBuffer(Processor processor, Buffer buffer) {
		int i = processor.getData().size();
		AddressSpace buf = factory.createAddressSpace("buf_" + i, i, 8, 0,
				buffer.getDepth() * 4);
		FunctionUnit lsu = factory.createLSU("LSU_buf_" + i, processor, buf);
		processor.getData().add(buf);
		processor.getFunctionUnits().add(lsu);
		return lsu;
	}

	private Buffer createBuffer(Vertex source, Vertex target) {
		Buffer buffer = factory.createBuffer(bufferId++, source, target);
		Port sourcePort = addBuffer((Processor) source, buffer);
		Port targetPort = addBuffer((Processor) target, buffer);
		buffer.setSourcePort(sourcePort);
		buffer.setTargetPort(targetPort);
		design.add(buffer);
		return buffer;
	}

	private void addSignal(Connection connection) {
		Vertex source = componentMap.get(connection.getSource());
		Vertex target = componentMap.get(connection.getTarget());
		Port sourcePort = null;
		Port targetPort = null;
		int size;

		if (source == null) {
			Port port = factory.createPort((net.sf.orcc.df.Port) connection
					.getSource());
			design.addInput(port);
			source = port;
		} else {
			if (source instanceof Processor) {
				Processor processor = (Processor) source;
				FunctionUnit fu = factory.createOutputSignalUnit(processor,
						connection.getSourcePort().getName());
				processor.getFunctionUnits().add(fu);
				sourcePort = fu;
			} else {
				Component component = (Component) source;
				sourcePort = factory.createPort(connection.getSourcePort());
				component.addOutput(sourcePort);
			}
		}

		if (target == null) {
			Port port = factory.createPort((net.sf.orcc.df.Port) connection
					.getTarget());
			design.addOutput(port);
			size = port.getSize();
			target = port;
		} else {
			if (target instanceof Processor) {
				throw new OrccRuntimeException("Unsupported input signal.");
			} else {
				Component component = (Component) target;
				targetPort = factory.createPort(connection.getTargetPort());
				component.addInput(targetPort);
				size = targetPort.getSize();
			}
		}

		Signal signal = factory.createSignal(connection.getAttribute("id")
				.getValue().toString(), size, source, target, sourcePort,
				targetPort);

		design.add(signal);
	}

	private void mapToBuffer(Connection connection) {
		Component source = componentMap.get(connection.getSource());
		Component target = componentMap.get(connection.getTarget());

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
			buffer = createBuffer(source, target);
			tgtToBufferMap.put(target, buffer);
		}
		buffer.getMappedConnections().add(connection);

		FunctionUnit srcLSU = (FunctionUnit) buffer.getSourcePort();
		connection.getSourcePort().setAttribute("id",
				srcLSU.getAddressSpace().getId());
		FunctionUnit tgtLSU = (FunctionUnit) buffer.getTargetPort();
		connection.getTargetPort().setAttribute("id",
				tgtLSU.getAddressSpace().getId());
	}

	@Override
	public Design caseBroadcast(Broadcast broadcast) {
		ProcessorConfiguration conf = ProcessorConfiguration.STANDARD;
		Processor processor = factory.createProcessor(
				"processor_" + broadcast.getName(), conf, 512);
		processor.getMappedActors().add(broadcast);
		design.add(processor);
		componentMap.put(broadcast, processor);
		return null;
	}

	@Override
	public Design caseConnection(Connection connection) {
		if (isNative(connection)) {
			addSignal(connection);
		} else {
			mapToBuffer(connection);
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
			for (Argument arg : instance.getArguments()) {
				component.setAttribute(arg.getVariable().getName(),
						arg.getValue());
			}
		} else {
			int memorySize = computeNeededMemorySize(instance);

			ProcessorConfiguration conf = ProcessorConfiguration.STANDARD;
			Processor processor = factory.createProcessor("processor_"
					+ instance.getName(), conf, memorySize);
			component = processor;
			processor.getMappedActors().add(instance);
		}
		design.add(component);
		componentMap.put(instance, component);
		return null;
	}

	@Override
	public Design caseNetwork(Network network) {
		design = factory.createDesign();
		for (Vertex entity : network.getEntities()) {
			doSwitch(entity);
		}
		for (Instance instance : network.getInstances()) {
			doSwitch(instance);
		}
		for (Connection connection : network.getConnections()) {
			doSwitch(connection);
		}
		for (Buffer buffer : design.getBuffers()) {
			buffer.update();
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

	private boolean isNative(Connection connection) {
		net.sf.orcc.df.Port source = connection.getSourcePort();
		net.sf.orcc.df.Port target = connection.getTargetPort();

		return (source != null && source.isNative())
				|| (target != null && target.isNative());
	}
}
