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
package net.sf.orcc.df;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.attributes.IAttribute;
import net.sf.orcc.df.attributes.IAttributeContainer;
import net.sf.orcc.df.impl.NetworkImpl;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.MoC;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.resource.ResourceSet;

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
	private IFile file;

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

	private Instance parent;

	/**
	 * the serializer/deserializer referenced by this instance.
	 */
	private SerDes serdes;

	/**
	 * Creates a new instance with the given id and class.
	 * 
	 * @param id
	 *            instance identifier
	 * @param clasz
	 *            the class of the instance
	 */
	public Instance(String id, String clasz) {
		this.id = id;
		this.clasz = clasz;

		this.parameters = new TreeMap<String, Expression>();
		this.attributes = new TreeMap<String, IAttribute>();

		this.hierarchicalId = new ArrayList<String>(1);
		this.hierarchicalId.add(id);

		this.hierarchicalClass = new ArrayList<String>(1);
		this.hierarchicalClass.add(clasz);
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
	public MoC getMoC() {
		MoC clasz = null;

		if (isActor()) {
			clasz = actor.getMoC();
		} else {
			clasz = network.getMoC();
		}

		return clasz;
	}

	/**
	 * Returns the file in which this instance is defined. This file is only
	 * valid for instances that refer to actors and were instantiated.
	 * 
	 * @return the file in which this instance is defined
	 */
	public IFile getFile() {
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
	 * Returns the path of identifiers from the top-level to this instance as a
	 * path of the form /top/network/.../instance.
	 * 
	 * @return the path of identifiers from the top-level to this instance as a
	 *         path of the form /top/network/.../instance
	 */
	public String getHierarchicalPath() {
		StringBuilder builder = new StringBuilder();
		for (String id : hierarchicalId) {
			builder.append('/');
			builder.append(id);
		}

		return builder.toString();
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
	 * Returns the instance that contains this instance, or <code>null</code>.
	 * 
	 * @return the instance that contains this instance, or <code>null</code>
	 */
	public Instance getParent() {
		return parent;
	}

	/**
	 * Returns the wrapper referenced by this instance.
	 * 
	 * @return the wrapper referenced by this instance, or <code>null</code> if
	 *         this instance does not reference a wrapper
	 */
	public SerDes getWrapper() {
		return serdes;
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
	public void instantiate(ResourceSet set, List<IFolder> vtlFolders) {
		String className = new File(clasz).getName();
		for (IFolder path : vtlFolders) {
			file = path.getFile(className.replace('.', '/') + ".ir");
			if (file.exists()) {
				break;
			} else {
				file = null;
			}
		}
		if (file == null) {
			throw new OrccRuntimeException("Actor \"" + className
					+ "\" not found!\nIf this actor has errors, please "
					+ "correct them and try again; otherwise, try to "
					+ "refresh/clean projects.");
		}
		actor = NetworkImpl.getActorFromPool(className);
		if (actor == null) {
			actor = (Actor) IrUtil.deserializeEntity(set, file);
			if (actor == null) {
				throw new OrccRuntimeException("Actor \"" + className
						+ "\" not found!\nIf this actor has errors, please "
						+ "correct them and try again; otherwise, try to "
						+ "refresh/clean projects.");
			}
			NetworkImpl.putActorInPool(className, actor);
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
	public boolean isSerdes() {
		return (serdes != null);
	}

	/**
	 * Sets the contents of this instance to be that of an actor. Removes any
	 * prior contents.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void setContents(Actor actor) {
		this.actor = actor;
		this.broadcast = null;
		this.network = null;
		this.serdes = null;
	}

	/**
	 * Sets the contents of this instance to be that of a broadcast. Removes any
	 * prior contents.
	 * 
	 * @param broadcast
	 *            a broadcast
	 */
	public void setContents(Broadcast broadcast) {
		this.actor = null;
		this.broadcast = broadcast;
		this.network = null;
		this.serdes = null;
	}

	/**
	 * Sets the contents of this instance to be that of a network. Removes any
	 * prior contents.
	 * 
	 * @param network
	 *            a network
	 */
	public void setContents(Network network) {
		this.actor = null;
		this.broadcast = null;
		this.network = network;
		this.serdes = null;
	}

	/**
	 * Sets the contents of this instance to be that of a serdes. Removes any
	 * prior contents.
	 * 
	 * @param serdes
	 *            a serdes
	 */
	public void setContents(SerDes serdes) {
		this.actor = null;
		this.broadcast = null;
		this.network = null;
		this.serdes = serdes;
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

	/**
	 * Sets the parent of this instance.
	 * 
	 * @param parent
	 *            the parent of this instance
	 */
	public void setParent(Instance parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "\"" + id + "\" instance of \"" + clasz + "\"";
	}

}
