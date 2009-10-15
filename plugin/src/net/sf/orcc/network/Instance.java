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
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.parser.IrParser;
import net.sf.orcc.network.parser.NetworkParser;

/**
 * An Instance is an {@link Actor} with parameters.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Instance implements Comparable<Instance> {

	/**
	 * the actor referenced by this instance. May be <code>null</code> if this
	 * instance references a network.
	 */
	private Actor actor;

	/**
	 * the class of this instance
	 */
	private String clasz;

	/**
	 * the absolute path this instance is defined in
	 */
	private File file;

	/**
	 * the id of this instance
	 */
	private String id;

	private boolean isBroadcast;

	/**
	 * the network referenced by this instance. May be <code>null</code> if this
	 * instance references a actor.
	 */
	private Network network;

	/**
	 * the parameters of this instance
	 */
	private Map<String, IExpr> parameters;

	/**
	 * Creates a new virtual instance. Only used by subclass Broadcast.
	 * 
	 * @param id
	 *            the instance id
	 * @param clasz
	 *            the instance class
	 * @param parameters
	 *            parameters of this instance
	 */
	protected Instance(String id, String clasz, Map<String, IExpr> parameters) {
		this.clasz = clasz;
		this.id = id;
		this.isBroadcast = true;
		this.parameters = parameters;
	}

	/**
	 * Creates a new instance, and try to load it. The path indicates the path
	 * in which files should be searched.
	 * 
	 * @param path
	 *            the path in which we should look for files
	 * @param id
	 *            the instance id
	 * @param clasz
	 *            the instance class
	 * @param parameters
	 *            parameters of this instance
	 * @throws OrccException
	 */
	public Instance(String path, String id, String clasz,
			Map<String, IExpr> parameters) throws OrccException {
		this.clasz = clasz;
		this.id = id;
		this.parameters = parameters;

		String fileName = path + File.separator + clasz + ".xdf";
		file = new File(fileName);
		if (file.exists()) {
			// cool, we got a network
			NetworkParser parser = new NetworkParser(fileName);
			network = parser.parseNetwork();
		} else {
			fileName = path + File.separator + clasz + ".json";
			file = new File(fileName);
			try {
				if (file.exists()) {
					// TODO when new front end is ready, add instantiation
					// here?
					InputStream in = new FileInputStream(file);
					actor = new IrParser().parseActor(in);
				} else {
					fileName = path + File.separator + id + ".json";
					file = new File(fileName);
					// this may cause a FileNotFoundException
					InputStream in = new FileInputStream(file);
					actor = new IrParser().parseActor(in);
				}
			} catch (OrccException e) {
				throw new OrccException("Could not parse instance \"" + id
						+ "\" because: " + e.getLocalizedMessage(), e);
			} catch (FileNotFoundException e) {
				throw new OrccException(
						"I/O error when parsing \"" + id + "\"", e);
			}
		}
	}

	@Override
	public int compareTo(Instance instance) {
		return id.compareTo(instance.id);
	}

	/**
	 * Returns the actor referenced by this instance.
	 * 
	 * @return the actor referenced by this instance, or <code>null</code> if
	 *         this instance references a network.
	 */
	public Actor getActor() {
		return actor;
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
	 * Returns the file in which this instance is defined.
	 * 
	 * @return the file in which this instance is defined
	 */
	public File getFile() {
		return file;
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
	 *         this instance references an actor.
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * Returns the parameters of this instance. This is a reference, not a copy.
	 * 
	 * @return the parameters of this instance
	 */
	public Map<String, IExpr> getParameters() {
		return parameters;
	}

	/**
	 * Returns true if this instance references an actor.
	 * 
	 * @return true if this instance references an actor.
	 */
	public boolean hasActor() {
		return (actor != null);
	}

	/**
	 * Returns true if this instance is a broadcast.
	 * 
	 * @return true if this instance is a broadcast
	 */
	public boolean isBroadcast() {
		return isBroadcast;
	}

	public void setClasz(String clasz) {
		this.clasz = clasz;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "\"" + id + "\" instance of \"" + clasz + "\"";
	}

}
