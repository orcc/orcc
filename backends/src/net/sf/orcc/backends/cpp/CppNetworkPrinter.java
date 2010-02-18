/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.cpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.CNetworkPrinter;
import net.sf.orcc.backends.cpp.codesign.Wrapper;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;

/**
 * This class defines a C++ network printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CppNetworkPrinter extends CNetworkPrinter {

	/**
	 * Creates a new network printer with the given template.
	 * 
	 * @param tmplName
	 *            template name
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CppNetworkPrinter(String tmplName) throws IOException {
		super(tmplName);
	}

	protected void setAttributes(Network network, boolean debugFifos,
			int fifoSize) throws OrccException {
		super.setAttributes(network, debugFifos, fifoSize);
		setWrapper(new HashSet<Instance>(network.getInstances()));
	}

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
				if(source instanceof Wrapper || target instanceof Wrapper ) {
					attrs.put("lock", 1);
				} else {
					attrs.put("lock", 0);
				}

				conn.add(attrs);

				fifoCount++;
			}
		}

		template.setAttribute("connections", conn);
	}
	
	protected void setWrapper(Set<Instance> instances) {
		for (Instance instance : instances) {
			if (instance.isWrapper()) {
				Wrapper wrap = (Wrapper) instance;
				Map<String, Object> attrs = new HashMap<String, Object>();
				attrs.put("id", wrap.getId());
				attrs.put("type", "Wrapper");

				template.setAttribute("wrapper", attrs);
			}
		}

	}
}
