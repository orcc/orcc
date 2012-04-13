package net.sf.orcc.backends.tta.architecture.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.backends.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.tta.architecture.Component;
import net.sf.orcc.backends.tta.architecture.Design;
import net.sf.orcc.backends.tta.architecture.DesignConfiguration;
import net.sf.orcc.backends.tta.architecture.Port;
import net.sf.orcc.backends.tta.architecture.Processor;
import net.sf.orcc.backends.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.tta.architecture.Signal;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;

public class ArchitectureBuilder extends DfSwitch<Design> {

	@SuppressWarnings("unused")
	private DesignConfiguration conf;
	private Design design;
	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;

	@SuppressWarnings("unused")
	private Mapping mapping;

	private Map<Vertex, Component> componentMap;
	private Map<Vertex, Port> portMap;

	public ArchitectureBuilder(DesignConfiguration conf) {
		design = factory.createDesign();
		componentMap = new HashMap<Vertex, Component>();
		portMap = new HashMap<Vertex, Port>();
	}

	public ArchitectureBuilder(DesignConfiguration conf, Mapping mapping) {
		this.conf = conf;
		this.mapping = mapping;
	}

	private void addFifo(Connection connection) {
		Component source = componentMap.get(connection.getSource());
		Component target = componentMap.get(connection.getTarget());
		String srcCalPort = connection.getSourcePort() == null ? connection
				.getSource().getLabel() : connection.getSourcePort().getName();
		String tgtCalPort = connection.getTargetPort() == null ? connection
				.getTarget().getLabel() : connection.getTargetPort().getName();

		if (source != null && target != null) {
			String id = connection.getAttribute("id").getValue().toString();
			Component fifo = factory.createComponent("fifo_" + id, "fifo");

			int size = connection.getSize();
			fifo.setAttribute("size", size);
			fifo.setAttribute("width", 32);
			fifo.setAttribute("widthu",
					(int) Math.ceil(Math.log(size) / Math.log(2)));

			// Ports
			Port fifo_rdreq = fifo.createInput("rdreq");
			Port fifo_data = fifo.createInput("data");
			Port fifo_nb_tokens = fifo.createOutput("tokens");
			Port fifo_wreq = fifo.createInput("wreq");
			Port fifo_q = fifo.createOutput("queue");
			Port fifo_nb_freerooms = fifo.createOutput("rooms");

			Port tgt_data = target.createInput("in_" + tgtCalPort + "_data");
			Port tgt_nb_tokens = target.createInput("in_" + tgtCalPort
					+ "_nb_tokens");
			Port tgt_ack = target.createOutput("in_" + tgtCalPort + "_ack");
			Port src_data = source.createOutput("out_" + srcCalPort + "_data");
			Port src_nb_freerooms = source.createInput("out_" + srcCalPort
					+ "_nb_freerooms");
			Port src_ack = source.createOutput("out_" + srcCalPort + "_ack");

			// Signals
			Signal s_data = factory.createSignal("s_data_" + id, source, fifo,
					src_data, fifo_data);
			Signal s_wrreq = factory.createSignal("s_wrreq_" + id, source,
					fifo, src_ack, fifo_wreq);
			Signal s_nb_freerooms = factory.createSignal("s_rooms_" + id, fifo,
					source, fifo_nb_freerooms, src_nb_freerooms);
			Signal s_q = factory.createSignal("s_queue_" + id, fifo, target,
					fifo_q, tgt_data);
			Signal s_rdreq = factory.createSignal("s_rdreq_" + id, target,
					fifo, tgt_ack, fifo_rdreq);
			Signal s_nb_tokens = factory.createSignal("s_tokens_" + id, fifo,
					target, fifo_nb_tokens, tgt_nb_tokens);

			design.add(s_data);
			design.add(s_wrreq);
			design.add(s_nb_freerooms);
			design.add(s_q);
			design.add(s_rdreq);
			design.add(s_nb_tokens);

			design.add(fifo);
			design.getFifos().add(fifo);
		}
	}

	private void addSignal(Connection connection) {
		Component source = componentMap.get(connection.getSource());
		Component target = componentMap.get(connection.getTarget());
		String srcCalPort = connection.getSourcePort() == null ? connection
				.getSource().getLabel() : connection.getSourcePort().getName();
		String tgtCalPort = connection.getTargetPort() == null ? connection
				.getTarget().getLabel() : connection.getTargetPort().getName();

		Port sourcePort;
		if (source == null) {
			sourcePort = portMap.get(connection.getSource());
		} else if (source.isProcessor()) {
			sourcePort = source.createOutput("out_" + srcCalPort + "_data");
			source.createInput("out_" + srcCalPort + "_nb_freerooms");
			source.createOutput("out_" + srcCalPort + "_ack");
		} else {
			sourcePort = source.createOutput(srcCalPort);
		}

		Port targetPort;
		if (target == null) {
			targetPort = portMap.get(connection.getSource());
		} else if (target.isProcessor()) {
			targetPort = target.createInput("in_" + tgtCalPort + "_data");
			target.createInput("in_" + tgtCalPort + "_nb_tokens");
			target.createOutput("in_" + tgtCalPort + "_ack");
		} else {
			targetPort = target.createInput(tgtCalPort);
		}

		Signal signal = factory.createSignal(
				"s_" + connection.getAttribute("id"), source, target,
				sourcePort, targetPort);

		design.add(signal);
	}

	@Override
	public Design caseBroadcast(Broadcast broadcast) {
		Component component = factory.createComponent(
				broadcast.getSimpleName(), "broadcast");
		design.getBroadcasts().add(component);
		design.add(component);
		componentMap.put(broadcast, component);
		return null;
	}

	@Override
	public Design casePort(net.sf.orcc.df.Port port) {
		Port newPort = factory.createPort(port.getName());
		if (port.getIncoming().isEmpty()) {
			design.addInput(newPort);
		} else {
			design.addOutput(newPort);
		}
		portMap.put(port, newPort);
		return null;
	}

	@Override
	public Design caseConnection(Connection connection) {
		if (connection.hasAttribute("native")) {
			addSignal(connection);
		} else {
			addFifo(connection);
		}
		return null;
	}

	@Override
	public Design caseInstance(Instance instance) {
		if (instance.getActor().isNative()) {
			Component component = factory.createComponent(instance.getName(),
					instance.getActor().getSimpleName());
			design.add(component);
			componentMap.put(instance, component);
		} else {
			int memorySize = computeNeededMemorySize(instance);
			String name = instance.getSimpleName();
			Processor processor = factory.createProcessor(name + "_inst",
					"processor_" + name, ProcessorConfiguration.STANDARD,
					memorySize);
			design.getProcessors().add(processor);
			design.add(processor);
			componentMap.put(instance, processor);
		}
		return null;
	}

	@Override
	public Design caseNetwork(Network network) {
		design = factory.createDesign();
		for (Entity entity : network.getEntities()) {
			doSwitch(entity);
		}
		for (Instance instance : network.getInstances()) {
			doSwitch(instance);
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
