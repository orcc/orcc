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
package net.sf.orcc.ir.nodes;

import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.Port;

/**
 * This class defines a node that performs an operation on a port. This can be a
 * Peek, a Read or a Write.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractFifoNode extends AbstractNode {

	private int numTokens;

	private Port port;

	private LocalVariable varDef;

	public AbstractFifoNode(int id, Location location, Port port,
			int numTokens, LocalVariable varDef) {
		super(id, location);
		this.numTokens = numTokens;
		this.port = port;
		port.addUse(this);
		this.varDef = varDef;
		varDef.addUse(this);
	}

	public int getNumTokens() {
		return numTokens;
	}

	public Port getPort() {
		return port;
	}

	public LocalVariable getVarDef() {
		return varDef;
	}

	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
	}

	public void setPort(Port port) {
		this.port = port;
	}

	public void setVar(LocalVariable varDef) {
		this.varDef = varDef;
	}

	@Override
	public String toString() {
		return varDef + " = read(" + port + ", " + numTokens + ")";
	}

}
