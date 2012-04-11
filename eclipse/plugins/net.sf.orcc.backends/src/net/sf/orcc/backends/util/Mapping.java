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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

/**
 * The class describes the mapping of network on components.
 * 
 * @author Herve Yviquel
 * 
 */
public class Mapping {

	private Map<String, List<Vertex>> execMapping;
	private Network network;
	private List<Vertex> unmapped;
	private Map<String, String> userMapping;

	public Mapping(Network network, Map<String, String> userMapping,
			boolean mapBroadcast) {
		this.network = network;
		this.userMapping = userMapping;
		this.execMapping = new HashMap<String, List<Vertex>>();
		this.unmapped = new ArrayList<Vertex>();
		computeMapping(mapBroadcast);
	}

	private void computeMapping(boolean mapBroadcast) {
		for (Instance instance : network.getInstances()) {
			map(instance.getHierarchicalName(), instance);
		}
		if (mapBroadcast) {
			for (Entity entity : network.getEntities()) {
				if (entity.isBroadcast()) {
					// Broadcasts are mapped with their source
					String srcName = ((Instance) entity.getPredecessors()
							.get(0)).getHierarchicalName();
					map(srcName, entity);
				}
			}
		}
	}

	public Map<String, List<Vertex>> getExecMapping() {
		return execMapping;
	}

	public List<Vertex> getUnmapped() {
		return unmapped;
	}

	private void map(String name, Vertex vertex) {
		String component = userMapping.get(name);
		if (component != null) {
			List<Vertex> list = execMapping.get(component);
			if (list == null) {
				list = new ArrayList<Vertex>();
				execMapping.put(component, list);
			}
			list.add(vertex);
		} else {
			unmapped.add(vertex);
		}
	}
}
