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
package net.sf.orcc.ir.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.expr.AbstractExpr;
import net.sf.orcc.ir.parser.IrParser;
import net.sf.orcc.ir.parser.NetworkParseException;

/**
 * An Instance is an {@link Actor} with parameters.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Instance implements Comparable<Instance> {

	private Actor actor;

	private String clasz;

	private File file;

	private String id;

	private Map<String, AbstractExpr> parameters;

	public Instance(String path, String id, String clasz,
			Map<String, AbstractExpr> parameters) {
		this.clasz = clasz;
		this.id = id;
		this.parameters = parameters;

		if (!isBroadcast()) {
			// parse actor
			try {
				String fileName = path + File.separator + id + ".json";
				file = new File(fileName);
				InputStream in = new FileInputStream(file);
				actor = new IrParser().parseActor(in);
			} catch (Exception e) {
				throw new NetworkParseException("Could not parse instance \""
						+ id + "\" because: " + e.getLocalizedMessage());
			}
		}
	}

	@Override
	public int compareTo(Instance instance) {
		return id.compareTo(instance.id);
	}

	public Actor getActor() {
		return actor;
	}

	public String getClasz() {
		return clasz;
	}

	public File getFile() {
		return file;
	}

	public String getId() {
		return id;
	}

	public Map<String, AbstractExpr> getParameters() {
		return parameters;
	}

	public boolean hasActor() {
		return (actor != null);
	}

	public boolean isBroadcast() {
		return clasz.equals(Broadcast.CLASS);
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
