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
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;

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
	 * This class defines a network visitor used to create a design according to
	 * the given network and the mapping.
	 */
	private class Mapper extends DfVisitor<Void> {
		@Override
		public Void caseBroadcast(Broadcast broadcast) {
			Processor processor = design.getProcessor(mapping
					.getMappedComponent(broadcast));
			processor.getMappedActors().add(broadcast);
			componentMap.put(broadcast, processor);
			return null;
		}

		@Override
		public Void caseConnection(Connection connection) {
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
		public Void caseInstance(Instance instance) {
			Actor actor = instance.getActor();
			if (actor.isNative()) {
				Component component;
				// A native actor describes a VHDL component from the library.
				component = factory.createComponent(instance.getActor()
						.getSimpleName());
				// The parameter of this actor is used as generic.
				for (Argument arg : instance.getArguments()) {
					component.setAttribute(arg.getVariable().getName(),
							arg.getValue());
				}
				design.add(component);
				componentMap.put(instance, component);
			} else {
				Processor processor = design.getProcessor(mapping
						.getMappedComponent(instance));
				processor.getMappedActors().add(instance);
				componentMap.put(instance, processor);
			}

			return null;
		}
	}

	private int bufferId = 0;

	private Map<Component, Map<Component, Memory>> bufferMap;
	private Map<Vertex, Component> componentMap;

	private Design design;
	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;
	private Mapping mapping;

	private boolean limitConnection = false;

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

	/**
	 * @param network
	 * @param configuration
	 * @param mapping
	 * @return
	 */
	public Design build(Network network, ProcessorConfiguration configuration,
			Mapping mapping) {
		this.mapping = mapping;
		this.componentMap = new HashMap<Vertex, Component>();
		this.bufferMap = new HashMap<Component, Map<Component, Memory>>();
		this.design = factory.createDesign();

		// Map all unmapped component to its own processor
		for (Vertex unmapped : new ArrayList<Vertex>(
				mapping.getUnmappedEntities())) {
			mapping.map("processor_" + unmapped.getLabel(), unmapped);
		}

		// Build processors
		for (String name : mapping.getComponents()) {
			design.add(factory.createProcessor(name, configuration, 0));
		}

		new Mapper().doSwitch(network);
		new ArchitectureMemoryEstimator().doSwitch(design);

		return design;
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
		Map<Component, Memory> tgtToBufferMap = null;

		if (limitConnection) {
			if (bufferMap.containsKey(source)) {
				tgtToBufferMap = bufferMap.get(source);
			} else {
				tgtToBufferMap = new HashMap<Component, Memory>();
				bufferMap.put(source, tgtToBufferMap);
			}
		}

		if (limitConnection && tgtToBufferMap.containsKey(target)) {
			ram = tgtToBufferMap.get(target);
		} else {
			if (source == target) {
				// Both actors are mapped to the same processor, then the
				// FIFO is mapped to a new local RAM.
				ram = factory.createMemory("lmem_" + bufferId);
				FunctionUnit lsu = source.connect(ram);
				ram.setSourcePort(lsu);
				ram.setTargetPort(lsu);
				source.getLocalRAMs().add(ram);
			} else {
				// Both actors are mapped to different processors, then the
				// FIFO is mapped in a new shared memory.
				ram = factory.createMemory("smem_" + bufferId);
				FunctionUnit sourceLSU = source.connect(ram);
				FunctionUnit targetLSU = target.connect(ram);
				ram.setSource(source);
				ram.setTarget(target);
				ram.setSourcePort(sourceLSU);
				ram.setTargetPort(targetLSU);
				design.add(ram);
			}
			ram.setAttribute("id", bufferId++);

			if (limitConnection) {
				tgtToBufferMap.put(target, ram);
			}
		}

		ram.getMappedConnections().add(connection);
	}
}
