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
package net.sf.orcc.ir.actor;

import java.util.List;

import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.ir.NameTransformer;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.type.IType;

/**
 * @author Matthieu Wipliez
 * 
 */
public class Procedure {

	private boolean external;

	private List<LocalVariable> locals;

	private Location location;

	private String name;

	private List<AbstractNode> nodes;

	private List<LocalVariable> parameters;

	private IType returnType;

	/**
	 * Construcs a new procedure.
	 * 
	 * @param name
	 *            The procedure name.
	 * @param external
	 *            Whether it is external or not.
	 * @param location
	 *            The procedure location.
	 * @param returnType
	 *            The procedure return type.
	 * @param parameters
	 *            The procedure parameters.
	 * @param locals
	 *            The procedure local variables.
	 */
	public Procedure(String name, boolean external, Location location,
			IType returnType, List<LocalVariable> parameters,
			List<LocalVariable> locals, List<AbstractNode> nodes) {
		this.external = external;
		this.nodes = nodes;
		this.locals = locals;
		this.location = location;
		this.name = name;
		this.parameters = parameters;
		this.returnType = returnType;
	}

	public List<LocalVariable> getLocals() {
		return locals;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return NameTransformer.transform(name);
	}

	public List<AbstractNode> getNodes() {
		return nodes;
	}

	public List<LocalVariable> getParameters() {
		return parameters;
	}

	public IType getReturnType() {
		return returnType;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReturnType(IType returnType) {
		this.returnType = returnType;
	}

	@Override
	public String toString() {
		return name;
	}

}
