/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.orcc.backends.PluginGroupLoader;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.network.Broadcast;
import net.sf.orcc.ir.network.Connection;
import net.sf.orcc.ir.network.Instance;
import net.sf.orcc.ir.network.Network;
import net.sf.orcc.ir.type.AbstractType;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * Network printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CNetworkPrinter {

	private StringTemplateGroup group;

	private StringTemplate template;

	private TypeToString typeVisitor;

	/**
	 * Creates a new network printer with the template "C.st".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CNetworkPrinter() throws IOException {
		this("C_network", new TypeToString());
	}

	/**
	 * Creates a new network printer using the given template file name.
	 * 
	 * @param name
	 *            The template file name.
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected CNetworkPrinter(String name, TypeToString typeVisitor)
			throws IOException {
		group = new PluginGroupLoader().loadGroup(name);
		this.typeVisitor = typeVisitor;
	}

	/**
	 * Prints the given network to a file whose name is given. debugFifos
	 * specifies whether debug information should be printed about FIFOs, and
	 * fifoSize is the default FIFO size.
	 * 
	 * @param fileName
	 *            The output file name.
	 * @param network
	 *            The network to generate code for.
	 * @param debugFifos
	 *            Whether debug information should be printed about FIFOs.
	 * @param fifoSize
	 *            Default FIFO size.
	 * @throws IOException
	 *             If the output file could not be written.
	 */
	public void printNetwork(String fileName, Network network,
			boolean debugFifos, int fifoSize) throws IOException {
		template = group.getInstanceOf("network");

		setAttributes(network, debugFifos, fifoSize);

		byte[] b = template.toString().getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

	/**
	 * Set the attributes to the template.
	 * 
	 * @param network
	 *            The network to generate code for.
	 * @param debugFifos
	 *            Whether debug information should be printed about FIFOs.
	 * @param fifoSize
	 *            Default FIFO size.
	 */
	private void setAttributes(Network network, boolean debugFifos, int fifoSize) {
		template.setAttribute("debugFifos", debugFifos);
		template.setAttribute("name", network.getName());
		template.setAttribute("size", fifoSize);

		DirectedMultigraph<Instance, Connection> graph = network.getGraph();
		Set<Instance> instances = new TreeSet<Instance>(graph.vertexSet());
		Set<Connection> connections = graph.edgeSet();

		setBroadcasts(instances);
		setInstances(instances);
		setConnections(graph, connections);
	}

	/**
	 * Sets the broadcasts attribute.
	 * 
	 * @param instances
	 *            The list of instances.
	 */
	private void setBroadcasts(Set<Instance> instances) {
		List<Map<String, Object>> broadcasts = new ArrayList<Map<String, Object>>();
		for (Instance instance : instances) {
			if (instance.isBroadcast()) {
				Broadcast bcast = (Broadcast) instance;
				Map<String, Object> attrs = new HashMap<String, Object>();
				attrs.put("id", bcast.getId());
				AbstractType type = bcast.getType();
				attrs.put("type", typeVisitor.toString(type));

				List<Integer> num = new ArrayList<Integer>();
				for (int i = 0; i < bcast.getNumOutput(); i++) {
					num.add(i);
				}
				attrs.put("num", num);

				broadcasts.add(attrs);
			}
		}

		template.setAttribute("broadcasts", broadcasts);
	}

	/**
	 * Sets the connections attribute.
	 * 
	 * @param graph
	 *            The network's graph.
	 * @param connections
	 *            The graph's connection.
	 */
	private void setConnections(DirectedMultigraph<Instance, Connection> graph,
			Set<Connection> connections) {
		List<Map<String, Object>> conn = new ArrayList<Map<String, Object>>();
		int fifoCount = 0;

		for (Connection connection : connections) {
			Instance source = graph.getEdgeSource(connection);
			Instance target = graph.getEdgeTarget(connection);

			AbstractType type;
			if (source.isBroadcast()) {
				type = connection.getTarget().getType();
			} else {
				type = connection.getSource().getType();
			}

			String size;
			if (connection.hasSize()) {
				size = Integer.toString(connection.getSize());
			} else {
				size = "SIZE";
			}

			Map<String, Object> attrs = new HashMap<String, Object>();
			attrs.put("count", fifoCount);
			attrs.put("size", size);
			attrs.put("type", typeVisitor.toString(type));
			attrs.put("source", source.getId());
			attrs.put("src_port", connection.getSource().getName());
			attrs.put("target", target.getId());
			attrs.put("tgt_port", connection.getTarget().getName());

			conn.add(attrs);

			fifoCount++;
		}

		template.setAttribute("connections", conn);
	}

	/**
	 * Sets the instances and initializes attributes.
	 * 
	 * @param instances
	 *            The list of instances.
	 */
	private void setInstances(Set<Instance> instances) {
		List<String> init = new ArrayList<String>();
		List<String> inst = new ArrayList<String>();

		for (Instance instance : instances) {
			if (!instance.isBroadcast()) {
				List<Action> initializes = instance.getActor().getInitializes();
				if (!initializes.isEmpty()) {
					init.add(instance.getId());
				}

				inst.add(instance.getId());
			}
		}

		template.setAttribute("initializes", init);
		template.setAttribute("instances", inst);
	}

}
