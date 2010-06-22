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
package net.sf.orcc.ir.instructions;

import net.sf.orcc.ir.AbstractLocalizable;
import net.sf.orcc.ir.Cast;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.nodes.BlockNode;

/**
 * This class defines an abstract instruction.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractInstruction extends AbstractLocalizable implements
		Instruction {

	private BlockNode block;

	protected AbstractInstruction(Location location) {
		super(location);
	}

	@Override
	public BlockNode getBlock() {
		return block;
	}

	@Override
	public Cast getCast() {
		return null;
	}

	@Override
	public boolean isAssign() {
		return false;
	}

	@Override
	public boolean isCall() {
		return false;
	}

	@Override
	public boolean isCFGNode() {
		return false;
	}

	@Override
	public boolean isHasTokens() {
		return false;
	}

	@Override
	public boolean isInstruction() {
		return true;
	}

	@Override
	public boolean isLoad() {
		return false;
	}

	@Override
	public boolean isPeek() {
		return false;
	}

	@Override
	public boolean isPhi() {
		return false;
	}

	@Override
	public boolean isRead() {
		return false;
	}

	@Override
	public boolean isReadEnd() {
		return false;
	}

	@Override
	public boolean isReturn() {
		return false;
	}

	@Override
	public boolean isStore() {
		return false;
	}

	@Override
	public boolean isWrite() {
		return false;
	}

	@Override
	public boolean isWriteEnd() {
		return false;
	}

	@Override
	public void setBlock(BlockNode block) {
		this.block = block;
	}

}
