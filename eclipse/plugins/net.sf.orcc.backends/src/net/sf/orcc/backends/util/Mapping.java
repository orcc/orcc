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
import java.util.Set;

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;

/**
 * The class describes the mapping of network on components.
 * 
 * @author Herve Yviquel
 * 
 */
public class Mapping {

	private class Visitor extends DfVisitor<Void> {
		@Override
		public Void caseInstance(Instance instance) {
			if (mapNativeActors || !instance.getActor().isNative()) {
				map(userMapping.get(instance.getHierarchicalName()), instance);
			}
			return null;
		}
	}

	private Map<String, List<Vertex>> componentToEntitiesMap;

	private Map<Vertex, String> entityToComponentMap;
	private boolean mapBroadcasts;

	private boolean mapNativeActors;
	private List<Vertex> unmappedEntities;
	private Map<String, String> userMapping;

	public Mapping(Network network, Map<String, String> userMapping) {
		this(network, userMapping, false, true);
	}

	public Mapping(Network network, Map<String, String> userMapping,
			boolean mapBroadcasts, boolean mapNativeActors) {
		this.userMapping = userMapping;
		this.mapBroadcasts = mapBroadcasts;
		this.mapNativeActors = mapNativeActors;
		this.componentToEntitiesMap = new HashMap<String, List<Vertex>>();
		this.entityToComponentMap = new HashMap<Vertex, String>();
		this.unmappedEntities = new ArrayList<Vertex>();
		new Visitor().caseNetwork(network);
	}

	/**
	 * @return
	 */
	public Map<String, List<Vertex>> getComponentToEntitiesMap() {
		return componentToEntitiesMap;
	}

	/**
	 * @return
	 */
	public Map<Vertex, String> getEntityToComponentMap() {
		return entityToComponentMap;
	}

	/**
	 * @param entity
	 * @return
	 */
	public String getMappedComponent(Vertex entity) {
		return entityToComponentMap.get(entity);
	}

	/**
	 * @param component
	 * @return
	 */
	public List<Vertex> getMappedEntities(String component) {
		return componentToEntitiesMap.get(component);
	}

	public List<Vertex> getUnmappedEntities() {
		return unmappedEntities;
	}

	/**
	 * @return
	 */
	public Set<String> getComponents() {
		return componentToEntitiesMap.keySet();
	}

	/**
	 * @param entity
	 * @return
	 */
	public boolean isNotMapped(Vertex entity) {
		return unmappedEntities.contains(entity);
	}

	/**
	 * @param name
	 * @param vertex
	 */
	public void map(String component, Vertex vertex) {
		if (component != null && !component.isEmpty()) {
			List<Vertex> list = componentToEntitiesMap.get(component);
			if (list == null) {
				list = new ArrayList<Vertex>();
				componentToEntitiesMap.put(component, list);
			}
			list.add(vertex);
			entityToComponentMap.put(vertex, component);
			unmappedEntities.remove(vertex);
		} else {
			unmappedEntities.add(vertex);
		}
	}

	/**
	 * @return
	 */
	public boolean mapBroadcasts() {
		return mapBroadcasts;
	}

	/**
	 * @return
	 */
	public boolean mapNativeActors() {
		return mapNativeActors;
	}
}
