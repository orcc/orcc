package net.sf.orcc.backends.c.quasistatic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.CNetworkPrinter;
import net.sf.orcc.backends.c.TypeToString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.network.Broadcast;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;

import org.jgrapht.graph.DirectedMultigraph;

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

	private HashMap<String, String> customBuffersSize;

	public CQuasiStaticNetworkPrinter() throws IOException {
		super("C_quasistatic_network", new TypeToString());
		initBuffersMap();
	}

	private void initBuffersMap() {
		customBuffersSize = new HashMap<String, String>();
		customBuffersSize.put("IS_PQF_AC", "63");
		customBuffersSize.put("invpred_QF_DC", "1");
		customBuffersSize.put("invpred_QUANT", "1");
		customBuffersSize.put("invpred_SIGNED", "1");
		customBuffersSize.put("IAP_QF_AC", "63");
		customBuffersSize.put("address_RA", "81");
		customBuffersSize.put("address_WA", "SIZE");
		customBuffersSize.put("DCsplit_AC", "63");
		customBuffersSize.put("mvrecon_MV", "2");
		customBuffersSize.put("parseheaders_BTYPE", "1");
		customBuffersSize.put("add_VID", "SIZE");
		customBuffersSize.put("broadcast_add_VID_output_0", "SIZE");
		// customBuffersSize.put("broadcast_add_VID_output_1", "SIZE");
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
				Type type = bcast.getType();
				attrs.put("type", typeVisitor.toString(type));

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
	 * Sets the connections attribute.
	 * 
	 * @param graph
	 *            The network's graph.
	 * @param connections
	 *            The graph's connection.
	 * @throws OrccException
	 */
	protected void setConnections(
			DirectedMultigraph<Instance, Connection> graph,
			Set<Connection> connections) throws OrccException {
		List<Map<String, Object>> conn = new ArrayList<Map<String, Object>>();
		int fifoCount = 0;

		for (Connection connection : connections) {
			Instance source = graph.getEdgeSource(connection);
			Instance target = graph.getEdgeTarget(connection);

			Type type;
			if (source.isBroadcast()) {
				type = connection.getTarget().getType();
			} else {
				type = connection.getSource().getType();
			}

			String size;
			IAttribute attr = connection.getAttribute(Connection.BUFFER_SIZE);
			if (customBuffersSize.containsKey(source.getId() + "_"
					+ connection.getSource().getName()))
				size = customBuffersSize.get(source.getId() + "_"
						+ connection.getSource().getName());
			else if (attr != null && attr.getType() == IAttribute.VALUE) {
				Expression expr = ((IValueAttribute) attr).getValue();
				size = Integer.toString(new ExpressionEvaluator()
						.evaluateAsInteger(expr) + 1);
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

}
