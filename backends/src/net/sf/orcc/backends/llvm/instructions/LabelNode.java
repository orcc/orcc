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
package net.sf.orcc.backends.llvm.instructions;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.nodes.BlockNode;

/**
 * @author Jérôme GORIN
 * 
 */
public class LabelNode extends AbstractLLVMInstruction {

	private String labelName;

	private List<LabelNode> precedence;

	private Instruction successor;

	public LabelNode(BlockNode block, Location location, String labelName) {
		super(block, location);
		precedence = new ArrayList<LabelNode>();
		this.labelName = labelName;
		this.successor = null;
	}

	@Override
	public void accept(LLVMInstructionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public void addPrecedence(LabelNode precedence) {
		this.precedence.add(precedence);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LabelNode) {
			return (labelName.compareTo(((LabelNode) obj).getLabelName()) == 0);
		} else {
			return false;
		}
	}

	public String getLabelName() {
		return labelName;
	}

	public List<LabelNode> getPrecedence() {
		return precedence;
	}

	public Instruction getSuccessor() {
		return successor;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setPrecedence(List<LabelNode> precedence) {
		this.precedence = precedence;
	}

	public void setSuccessor(Instruction successor) {
		this.successor = successor;
	}

	@Override
	public String toString() {
		return labelName;
	}

}
