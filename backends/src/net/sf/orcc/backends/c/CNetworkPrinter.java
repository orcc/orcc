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

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.TemplateGroupLoader;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.network.Broadcast;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jgrapht.DirectedGraph;

/**
 * This class defines a C network printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CNetworkPrinter {

	private StringTemplateGroup group;

	/**
	 * template is protected in order to be visible to
	 * CQuasiStaticNetworkPrinter
	 */
	protected StringTemplate template;

	/**
	 * Creates a new network printer with the template "C_network".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CNetworkPrinter() throws IOException {
		this("C_network");
	}

	/**
	 * Creates a new network printer using the given template file name.
	 * 
	 * @param name
	 *            The template file name.
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected CNetworkPrinter(String name) throws IOException {
		group = new TemplateGroupLoader().loadGroup(name);
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
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void printNetwork(String fileName, Network network,
			boolean debugFifos, int fifoSize) throws OrccException {
		template = group.getInstanceOf("network");

		setAttributes(network, debugFifos, fifoSize);

		byte[] b = template.toString(80).getBytes();
		try {
			OutputStream os = new FileOutputStream(fileName);
			os.write(b);
			os.close();
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	/**
	 * Set the attributes to the template.
	 * 
	 * setAttributes is protected due to it has to be visible for
	 * CQuasiStaticNetworkPrinter
	 * 
	 * @param network
	 *            The network to generate code for.
	 * @param debugFifos
	 *            Whether debug information should be printed about FIFOs.
	 * @param fifoSize
	 *            Default FIFO size.
	 * @throws OrccException
	 */
	protected void setAttributes(Network network, boolean debugFifos, int fifoSize)
			throws OrccException {
		template.setAttribute("debugFifos", debugFifos);
		template.setAttribute("name", network.getName());
		template.setAttribute("size", fifoSize);

		DirectedGraph<Vertex, Connection> graph = network.getGraph();
		Set<Instance> instances = new TreeSet<Instance>();
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				instances.add(vertex.getInstance());
			}
		}

		Set<Connection> connections = graph.edgeSet();

		setBroadcasts(instances);
		setInstances(instances);
		setConnections(graph, connections);
	}

	/**
	 * Sets the broadcasts attribute.
	 * 
	 * setBroadcasts is protected due to has been overwritten by
	 * CQuasiStaticNetworkPrinter
	 * 
	 * @param instances
	 *            The list of instances.
	 */
	protected void setBroadcasts(Set<Instance> instances) {
		List<Map<String, Object>> broadcasts = new ArrayList<Map<String, Object>>();
		for (Instance instance : instances) {
			if (instance.isBroadcast()) {
				Broadcast bcast = (Broadcast) instance;
				Map<String, Object> attrs = new HashMap<String, Object>();
				attrs.put("id", bcast.getId());
				attrs.put("type", bcast.getType().toString());

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
	 * setConnections is protected due to has been overwritten by
	 * CQuasiStaticNetworkPrinter
	 * 
	 * @param graph
	 *            The network's graph.
	 * @param connections
	 *            The graph's connection.
	 * @throws OrccException
	 */
	protected void setConnections(DirectedGraph<Vertex, Connection> graph,
			Set<Connection> connections) throws OrccException {
		List<Map<String, Object>> conn = new ArrayList<Map<String, Object>>();
		int fifoCount = 0;

		for (Connection connection : connections) {
			Vertex srcVertex = graph.getEdgeSource(connection);
			Vertex tgtVertex = graph.getEdgeTarget(connection);

			if (srcVertex.isInstance() && tgtVertex.isInstance()) {
				Instance source = srcVertex.getInstance();
				Instance target = tgtVertex.getInstance();

				Type type;
				if (source.isBroadcast()) {
					type = connection.getTarget().getType();
				} else {
					type = connection.getSource().getType();
				}

				String size;
				IAttribute attr = connection
						.getAttribute(Connection.BUFFER_SIZE);
				if (attr != null && attr.getType() == IAttribute.VALUE) {
					Expression expr = ((IValueAttribute) attr).getValue();
					size = Integer.toString(new ExpressionEvaluator()
							.evaluateAsInteger(expr) + 1);
				} else {
					size = "SIZE";
				}

				Map<String, Object> attrs = new HashMap<String, Object>();
				attrs.put("count", fifoCount);
				attrs.put("size", size);
				attrs.put("type", type.toString());
				attrs.put("source", source.getId());
				attrs.put("src_port", connection.getSource().getName());
				attrs.put("target", target.getId());
				attrs.put("tgt_port", connection.getTarget().getName());

				conn.add(attrs);

				fifoCount++;
			}
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
