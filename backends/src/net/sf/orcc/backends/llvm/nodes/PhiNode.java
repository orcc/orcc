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

import java.util.Map;

import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.type.IType;

/**
 * @author Jérôme GORIN
 * 
 */
public class PhiNode extends AbstractLLVMNode {

	private Map<LabelNode, VarDef> assignements;

	private IType type;

	private VarDef varDef;

	public PhiNode(int id, Location location, VarDef varDef, IType type,
			Map<LabelNode, VarDef> assignements) {
		super(id, location);
		this.varDef = varDef;
		this.assignements = assignements;
		this.type = type;
	}

	@Override
	public void accept(LLVMNodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public Map<LabelNode, VarDef> getAssignements() {
		return assignements;
	}

	public IType getType() {
		return type;
	}

	public VarDef getVarDef() {
		return varDef;
	}

	public void setAssignements(Map<LabelNode, VarDef> assignements) {
		this.assignements = assignements;
	}

	public void setType(IType type) {
		this.type = type;
	}

	public void setVarDef(VarDef varDef) {
		this.varDef = varDef;
	}

}
