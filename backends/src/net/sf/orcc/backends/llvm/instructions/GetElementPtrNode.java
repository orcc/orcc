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

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.nodes.BlockNode;

/**
 * @author Jérôme GORIN
 * 
 */
public class GetElementPtrNode extends AbstractLLVMInstruction {

	private List<Expression> indexes;

	private Use source;

	private LocalVariable target;

	public GetElementPtrNode(BlockNode block, Location location,
			LocalVariable target, Use source, List<Expression> indexs) {
		super(block, location);
		this.target = target;
		this.source = source;
		this.indexes = indexs;
	}

	@Override
	public void accept(LLVMInstructionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public List<Expression> getIndexes() {
		return indexes;
	}

	public Use getSource() {
		return source;
	}

	public LocalVariable getTarget() {
		return target;
	}

	public void setIndexs(List<Expression> indexs) {
		this.indexes = indexs;
	}

	public void setSource(Use source) {
		this.source = source;
	}

	public void setVar(LocalVariable varDef) {
		this.target = varDef;
	}

	@Override
	public String toString() {
		return target + " = getelementptr(" + source + ", " + indexes + ")";
	}

}
