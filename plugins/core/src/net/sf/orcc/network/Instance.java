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
package net.sf.orcc.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.classes.IClass;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.serialize.IRParser;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IAttributeContainer;

/**
 * This class defines an instance. An instance has an id, a class, parameters
 * and attributes. The class of the instance points to an actor or a network.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Instance implements Comparable<Instance>, IAttributeContainer {

	/**
	 * the actor referenced by this instance. May be <code>null</code> if this
	 * instance references a network or a broadcast.
	 */
	private Actor actor;

	/**
	 * attributes
	 */
	private Map<String, IAttribute> attributes;

	/**
	 * the broadcast referenced by this instance. May be <code>null</code> if
	 * this instance references an actor or a network.
	 */
	private Broadcast broadcast;

	/**
	 * the class of this instance
	 */
	private String clasz;

	/**
	 * the absolute path this instance is defined in
	 */
	private File file;

	/**
	 * the path of classes from the top-level to this instance.
	 */
	private List<String> hierarchicalClass;

	/**
	 * the path of identifiers from the top-level to this instance.
	 */
	private List<String> hierarchicalId;

	/**
	 * the id of this instance
	 */
	private String id;

	/**
	 * the network referenced by this instance. May be <code>null</code> if this
	 * instance references an actor or a broadcast.
	 */
	private Network network;

	/**
	 * the parameters of this instance
	 */
	private Map<String, Expression> parameters;

	/**
	 * the wrapper referenced by this instance.
	 */
	private Wrapper wrapper;

	/**
	 * Creates a new instance of the given actor with the given identifier.
	 * 
	 * @param id
	 *            the instance identifier
	 * @param actor
	 *            an actor
	 */
	public Instance(String id, Actor actor) {
		this(id, actor.getName());
		this.actor = actor;
	}

	/**
	 * Creates a new instance of the given broadcast with the given identifier.
	 * 
	 * @param id
	 *            the instance identifier
	 * @param broadcast
	 *            a broadcast
	 */
	public Instance(String id, Broadcast broadcast) {
		this(id, "Broadcast");
		this.broadcast = broadcast;
	}

	/**
	 * Creates a new instance of the given network with the given identifier.
	 * 
	 * @param id
	 *            the instance identifier
	 * @param network
	 *            a network
	 */
	public Instance(String id, Network network) {
		this(id, network.getName());
		this.network = network;
	}

	/**
	 * Creates a new instance of the given network with the given identifier.
	 * 
	 * @param id
	 *            the instance identifier
	 * @param network
	 *            a network
	 * @param parameters
	 *            the parameters of this instance
	 * @param attributes
	 *            the attributes of this instance
	 */
	public Instance(String id, Network network,
			Map<String, Expression> parameters,
			Map<String, IAttribute> attributes) {
		this(id, network);
		this.parameters.putAll(parameters);
		this.attributes.putAll(attributes);
	}

	/**
	 * Creates a new instance with the given id and empty parameters and
	 * attributes.
	 * 
	 * @param id
	 *            instance identifier
	 */
	private Instance(String id, String clasz) {
		this.id = id;
		this.clasz = clasz;

		this.parameters = new HashMap<String, Expression>();
		this.attributes = new HashMap<String, IAttribute>();

		this.hierarchicalId = new ArrayList<String>(1);
		this.hierarchicalId.add(id);

		this.hierarchicalClass = new ArrayList<String>(1);
		this.hierarchicalClass.add(clasz);
	}

	/**
	 * Creates a new virtual instance.
	 * 
	 * @param id
	 *            the instance id
	 * @param clasz
	 *            the instance class
	 * @param parameters
	 *            the parameters of this instance
	 * @param attributes
	 *            the attributes of this instance
	 */
	public Instance(String id, String clasz,
			Map<String, Expression> parameters,
			Map<String, IAttribute> attributes) {
		this(id, clasz);
		this.parameters.putAll(parameters);
		this.attributes.putAll(attributes);
	}

	/**
	 * Creates a new instance of the given wrapper with the given identifier.
	 * 
	 * @param id
	 *            the instance identifier
	 * @param wrapper
	 *            a wrapper
	 */
	public Instance(String id, Wrapper wrapper) {
		this(id, "Wrapper");
		this.wrapper = wrapper;
	}

	@Override
	public int compareTo(Instance instance) {
		return id.compareTo(instance.id);
	}

	/**
	 * Returns the actor referenced by this instance.
	 * 
	 * @return the actor referenced by this instance, or <code>null</code> if
	 *         this instance does not reference an actor
	 */
	public Actor getActor() {
		return actor;
	}

	@Override
	public IAttribute getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public Map<String, IAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * Returns the broadcast referenced by this instance.
	 * 
	 * @return the broadcast referenced by this instance, or <code>null</code>
	 *         if this instance does not reference a broadcasst
	 */
	public Broadcast getBroadcast() {
		return broadcast;
	}

	/**
	 * Returns the class of this instance.
	 * 
	 * @return the class of this instance
	 */
	public String getClasz() {
		return clasz;
	}

	/**
	 * Returns the classification class of the instance.
	 * 
	 * @return the classification class of this instance
	 */
	public IClass getContentClass() {
		IClass clasz = null;

		if (isActor()) {
			clasz = actor.getActorClass();
		} else {
			clasz = network.getNetworkClass();
		}

		return clasz;
	}

	/**
	 * Returns the file in which this instance is defined. This file is only
	 * valid for instances that refer to actors and were instantiated.
	 * 
	 * @return the file in which this instance is defined
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Returns the path of classes from the top-level to this instance.
	 * 
	 * @return the path of classes from the top-level to this instance
	 */
	public List<String> getHierarchicalClass() {
		return hierarchicalClass;
	}

	/**
	 * Returns the path of identifiers from the top-level to this instance.
	 * 
	 * @return the path of identifiers from the top-level to this instance
	 */
	public List<String> getHierarchicalId() {
		return hierarchicalId;
	}

	/**
	 * Returns the identifier of this instance.
	 * 
	 * @return the identifier of this instance
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the network referenced by this instance.
	 * 
	 * @return the network referenced by this instance, or <code>null</code> if
	 *         this instance does not reference a network
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * Returns the parameters of this instance. This is a reference, not a copy.
	 * 
	 * @return the parameters of this instance
	 */
	public Map<String, Expression> getParameters() {
		return parameters;
	}

	/**
	 * Returns the wrapper referenced by this instance.
	 * 
	 * @return the wrapper referenced by this instance, or <code>null</code> if
	 *         this instance does not reference a wrapper
	 */
	public Wrapper getWrapper() {
		return wrapper;
	}

	@Override
	public int hashCode() {
		// the hash code of an instance is the hash code of its identifier
		return id.hashCode();
	}

	/**
	 * Tries to load this instance as an actor.
	 * 
	 * @param path
	 *            the path where actors should be looked up
	 * 
	 * @throws OrccException
	 */
	public void instantiate(String path) throws OrccException {
		String className = new File(clasz).getName();
		actor = Network.getActorFromPool(className);
		file = new File(path, className + ".json");
		if (actor == null) {
			// try and load the actor
			try {
				InputStream in = new FileInputStream(file);
				actor = new IRParser().parseActor(in);
				Network.putActorInPool(className, actor);
			} catch (OrccException e) {
				throw new OrccException("Could not parse instance \"" + id
						+ "\" because: " + e.getLocalizedMessage(), e);
			} catch (FileNotFoundException e) {
				throw new OrccException("Actor \"" + id
						+ "\" not found! Did you compile the VTL?", e);
			}
		}

		// replace path-based class by actor class
		clasz = className;
		
		// and update hierarchical class
		if (!hierarchicalClass.isEmpty()) {
			int last = hierarchicalClass.size() - 1;
			hierarchicalClass.remove(last);
			hierarchicalClass.add(clasz);
		}
	}

	/**
	 * Returns true if this instance references an actor.
	 * 
	 * @return true if this instance references an actor.
	 */
	public boolean isActor() {
		return (actor != null);
	}

	/**
	 * Returns true if this instance is a broadcast.
	 * 
	 * @return true if this instance is a broadcast
	 */
	public boolean isBroadcast() {
		return (broadcast != null);
	}

	/**
	 * Returns true if this instance references a network.
	 * 
	 * @return true if this instance references a network.
	 */
	public boolean isNetwork() {
		return (network != null);
	}

	/**
	 * Returns true if this instance is a wrapper.
	 * 
	 * @return true if this instance is a wrapper
	 */
	public boolean isWrapper() {
		return (wrapper != null);
	}

	/**
	 * Changes the identifier of this instance.
	 * 
	 * @param id
	 *            a new identifier
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "\"" + id + "\" instance of \"" + clasz + "\"";
	}

}
