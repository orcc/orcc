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
package net.sf.orcc.backends.llvm.nodes;

import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;

/**
 * @author Jérôme GORIN
 * 
 */
public class LoadFifo extends AbstractLLVMNode {

	private String fifoName;

	private int index;

	private int numTokens;

	private LocalVariable varDef;

	public LoadFifo(int id, Location location, String fifoName,
			LocalVariable varDef, int index) {
		super(id, location);
		this.fifoName = fifoName;
		this.varDef = varDef;
		this.index = index;
	}

	@Override
	public void accept(LLVMNodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public String getFifoName() {
		return fifoName;
	}

	public int getIndex() {
		return index;
	}

	public int getNumTokens() {
		return numTokens;
	}

	public LocalVariable getVarDef() {
		return varDef;
	}

	public void setFifoName(String fifoName) {
		this.fifoName = fifoName;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
	}

	public void setVar(LocalVariable varDef) {
		this.varDef = varDef;
	}

	@Override
	public String toString() {
		return varDef + " = loadFifo(" + fifoName + ", " + numTokens + ")";
	}

}
