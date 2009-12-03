package net.sf.orcc.backends.c.quasistatic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.CNetworkPrinter;
import net.sf.orcc.backends.c.quasistatic.scheduler.main.Scheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.parsers.InputXDFParser;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;
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

import org.jgrapht.DirectedGraph;

/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class CQuasiStaticNetworkPrinter extends CNetworkPrinter {

	//private HashMap<String, String> customBuffersSize;

	public CQuasiStaticNetworkPrinter() throws IOException {
		super("C_quasistatic_network");
		//initBuffersMap();
	}

	/**
	 * Sets the broadcasts attribute.
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
				attrs.put("isAddActor", bcast.getId().contains("add"));
				broadcasts.add(attrs);
			}
		}

		template.setAttribute("broadcasts", broadcasts);
	}
	
	/**
	 * Sets the qs_scheduler_stmts attribute.
	 * @throws OrccException 
	 */
	private void setQSStmts() throws OrccException{
		InputXDFParser inputXDFParser = new InputXDFParser(
				Scheduler.workingDirectoryPath + File.separator
						+ Constants.INPUT_FILE_NAME);
		List<String> stmts = inputXDFParser.parseQSSchedulerStmts();
		template.setAttribute("qs_scheduler_stmts", stmts);
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
	 * @throws OrccException
	 */
	protected void setAttributes(Network network, boolean debugFifos, int fifoSize)
			throws OrccException {
		super.setAttributes(network, debugFifos, fifoSize);
		setQSStmts();
	}
	
	/**
	 * Sets the connections attribute.
	 * 
	 * setConnections is protected due to has been overwritten by
	 * CQuasiStaticNetworkPrinter
	 * 
	 * Overwrite to include custom buffers sizes
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
				InputXDFParser inputXDFParser = new InputXDFParser(
						Scheduler.workingDirectoryPath + File.separator
								+ Constants.INPUT_FILE_NAME);
				HashMap<String, String> customBuffersSize = inputXDFParser
						.parseCustomIndividualBuffersSizes();
				if (customBuffersSize.containsKey(source.getId() + "_"
						+ connection.getSource().getName())){
					size = customBuffersSize.get(source.getId() + "_"
							+ connection.getSource().getName());
				} else if (attr != null && attr.getType() == IAttribute.VALUE) {
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
}
