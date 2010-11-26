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
package net.sf.orcc.ir;

import net.sf.orcc.ir.instructions.InstructionInterpreter;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.nodes.BlockNode;

/**
 * This class defines an instruction.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface Instruction extends User {

	/**
	 * Accepts the given instruction interpreter.
	 * 
	 * @param interpreter
	 *            an interpreter
	 * @param args
	 *            arguments
	 * @return an object
	 */
	Object accept(InstructionInterpreter interpreter, Object... args);

	/**
	 * Accepts the given instruction visitor.
	 * 
	 * @param visitor
	 *            a visitor
	 * @param args
	 *            arguments
	 */
	void accept(InstructionVisitor visitor);

	/**
	 * Returns the block that contains this instruction.
	 * 
	 * @return the block that contains this instruction
	 */
	BlockNode getBlock();

	/**
	 * Returns instruction casting type if needed.
	 * 
	 * @return Type of cast made by this instruction
	 */
	Cast getCast();

	/**
	 * Returns <code>true</code> if the instruction is an Assign.
	 * 
	 * @return <code>true</code> if the instruction is an Assign
	 */
	boolean isAssign();

	/**
	 * Returns <code>true</code> if the instruction is a Call.
	 * 
	 * @return <code>true</code> if the instruction is a Call
	 */
	boolean isCall();

	/**
	 * Returns <code>true</code> if the instruction is a Load.
	 * 
	 * @return <code>true</code> if the instruction is a Load
	 */
	boolean isLoad();

	/**
	 * Returns <code>true</code> if the instruction is a Peek.
	 * 
	 * @return <code>true</code> if the instruction is a Peek
	 */
	boolean isPeek();

	/**
	 * Returns <code>true</code> if the instruction is a Phi.
	 * 
	 * @return <code>true</code> if the instruction is a Phi
	 */
	boolean isPhi();

	/**
	 * Returns <code>true</code> if the instruction is a Read.
	 * 
	 * @return <code>true</code> if the instruction is a Read
	 */
	boolean isRead();

	/**
	 * Returns <code>true</code> if the instruction is a ReadEnd.
	 * 
	 * @return <code>true</code> if the instruction is a ReadEnd
	 */
	boolean isReadEnd();

	/**
	 * Returns <code>true</code> if the instruction is a Return.
	 * 
	 * @return <code>true</code> if the instruction is a Return
	 */
	boolean isReturn();

	/**
	 * Returns <code>true</code> if the instruction is a Store.
	 * 
	 * @return <code>true</code> if the instruction is a Store
	 */
	boolean isStore();

	/**
	 * Returns <code>true</code> if the instruction is a Write.
	 * 
	 * @return <code>true</code> if the instruction is a Write
	 */
	boolean isWrite();

	/**
	 * Returns <code>true</code> if the instruction is a WriteEnd.
	 * 
	 * @return <code>true</code> if the instruction is a WriteEnd
	 */
	boolean isWriteEnd();

	/**
	 * Sets the block that contains this instruction.
	 * 
	 * @param block
	 *            the block that contains this instruction
	 */
	void setBlock(BlockNode block);

}
