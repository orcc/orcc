/*
 * Copyright (c) 2012, IRISA
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
package net.sf.orcc.backends.llvm.tta.architecture.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.llvm.tta.architecture.Component;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Port;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.llvm.tta.architecture.Signal;
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

/**
 * This class define an architecture builder from the mapping of actors on a set
 * of processors. The processors and their interconnections are instantiated
 * during the visit of a given process network.
 * 
 * @author Herve Yviquel
 * 
 */
public class ArchitectureBuilder extends DfSwitch<Design> {

	/**
	 * This class define an architecture visitor used to update needed
	 * information after the projection of a dataflow application onto a network
	 * of processors. For example, it computes the size of each memory according
	 * to the mapping.
	 * 
	 * @author Herve Yviquel
	 * 
	 */
	private class ProjectionFinalizer extends ArchitectureSwitch<Void> {

		@Override
		public Void caseMemory(Memory buffer) {
			FunctionUnit srcLSU = (FunctionUnit) buffer.getSourcePort();
			FunctionUnit tgtLSU = (FunctionUnit) buffer.getTargetPort();

			int bits = 0;
			for (Connection connection : buffer.getMappedConnections()) {
				bits += connection.getSize()
						* connection.getSourcePort().getType().getSizeInBits()
						+ 2 * 32;
				connection.getSourcePort().setAttribute("id",
						srcLSU.getAttribute("id").getValue());
				connection.getTargetPort().setAttribute("id",
						tgtLSU.getAttribute("id").getValue());
			}
			buffer.setDepth(bits / 32);
			int maxAdress = bits / 8;
			srcLSU.getAddressSpace().setMaxAddress(maxAdress);
			tgtLSU.getAddressSpace().setMaxAddress(maxAdress);
			return null;
		}

		@Override
		public Void caseDesign(Design design) {
			for (Memory buffer : design.getSharedMemories()) {
				doSwitch(buffer);
			}
			return null;
		}

	}

	private int bufferId = 0;

	private Map<Component, Map<Component, Memory>> bufferMap;

	private Map<Vertex, Component> componentMap;

	private Design design;
	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;

	private List<String> optimizedActors;

	public ArchitectureBuilder() {
		design = factory.createDesign();
		componentMap = new HashMap<Vertex, Component>();
		bufferMap = new HashMap<Component, Map<Component, Memory>>();

		optimizedActors = new ArrayList<String>();
		optimizedActors.add("decoder_texture_IQ");
		optimizedActors.add("Merger");
		optimizedActors.add("decoder_motion_interpolation");
		optimizedActors.add("decoder_motion_add");
		optimizedActors.add("decoder_texture_idct2d");
		optimizedActors.add("decoder_motion_framebuf");
	}

	/**
	 * Add a simple signal to the design. The signal is the translation of
	 * native connection. External ports and functional units are automatically
	 * added to the design and their processor.
	 * 
	 * @param connection
	 *            the native connection which represents the signal
	 */
	private void addSignal(Connection connection) {
		Vertex source = componentMap.get(connection.getSource());
		Vertex target = componentMap.get(connection.getTarget());
		Port sourcePort = null;
		Port targetPort = null;
		int size;

		if (source == null) {
			// The signal comes from an external port
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
			// The signal targets an external port
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
			// Native connection are hardware signals
			addSignal(connection);
		} else {
			// FIFO connection are mapped to a memory
			mapToBuffer(connection);
		}
		return null;
	}

	@Override
	public Design caseInstance(Instance instance) {
		Actor actor = instance.getActor();
		Component component;
		if (actor.isNative()) {
			// A native actor describes a VHDL component from the library.
			component = factory.createComponent(instance.getActor()
					.getSimpleName());
			// The parameter of this actor is used as generics.
			for (Argument arg : instance.getArguments()) {
				component.setAttribute(arg.getVariable().getName(),
						arg.getValue());
			}
		} else {
			// FIXME: Map several actors on the same processor
			// TODO: Let the user choosing the configuration of the processor

			// FIXME: Compute the RAM size during the last compilation step
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
		new ProjectionFinalizer().doSwitch(design);

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

	/**
	 * Map a connection on a circular buffer. If the actors source and target of
	 * the connection are mapped on different processors, then the connection is
	 * mapped to their interconnected memory and this memory is created in case
	 * it doesn't yet exist. If both actors are mapped to the same processor,
	 * then the connection is mapped to its internal RAM.
	 * 
	 * @param connection
	 *            the connection to map on a buffer
	 */
	private void mapToBuffer(Connection connection) {
		Processor source = (Processor) componentMap.get(connection.getSource());
		Processor target = (Processor) componentMap.get(connection.getTarget());

		Memory ram;

		if (source == target) {
			// Both actors are mapped to the same processor, then the FIFO is
			// mapped to its local RAM.
			ram = source.getLocalRAMs().get(0);
		} else {
			// Both actors are mapped to different processors, then the FIFO is
			// mapped in their shared memory (it is created if necessary).
			Map<Component, Memory> tgtToBufferMap;
			if (bufferMap.containsKey(source)) {
				tgtToBufferMap = bufferMap.get(source);
			} else {
				tgtToBufferMap = new HashMap<Component, Memory>();
				bufferMap.put(source, tgtToBufferMap);
			}

			if (tgtToBufferMap.containsKey(target)) {
				ram = tgtToBufferMap.get(target);
			} else {
				ram = factory.createMemory(bufferId++, source, target);
				design.add(ram);
				tgtToBufferMap.put(target, ram);
			}

		}
		ram.getMappedConnections().add(connection);
	}
}
