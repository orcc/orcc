/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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

import net.sf.dftools.util.Attributable;

/**
 * This class defines an instruction.
 * 
 * @author Matthieu Wipliez
 * @model abstract="true"
 */
public interface Instruction extends Attributable {

	/**
	 * Returns the block that contains this instruction.
	 * 
	 * @return the block that contains this instruction
	 */
	BlockBasic getBlock();

	/**
	 * Returns the line number of this instruction.
	 * 
	 * @return the line number of this instruction
	 * @model
	 */
	public int getLineNumber();

	/**
	 * Returns the predicate associated with this instruction. This is used by
	 * if-conversion.
	 * 
	 * @return the predicate associated with this instruction
	 * @model containment="true"
	 */
	Predicate getPredicate();

	/**
	 * Returns <code>true</code> if the instruction is an Assign.
	 * 
	 * @return <code>true</code> if the instruction is an Assign
	 */
	@Deprecated
	boolean isAssign();

	/**
	 * Returns <code>true</code> if the instruction is a Call.
	 * 
	 * @return <code>true</code> if the instruction is a Call
	 */
	@Deprecated
	boolean isCall();

	/**
	 * Returns <code>true</code> if the instruction is a Load.
	 * 
	 * @return <code>true</code> if the instruction is a Load
	 */
	@Deprecated
	boolean isLoad();

	/**
	 * Returns <code>true</code> if the instruction is a Phi.
	 * 
	 * @return <code>true</code> if the instruction is a Phi
	 */
	@Deprecated
	boolean isPhi();

	/**
	 * Returns <code>true</code> if the instruction is a Return.
	 * 
	 * @return <code>true</code> if the instruction is a Return
	 */
	@Deprecated
	boolean isReturn();

	/**
	 * Returns <code>true</code> if the instruction is an Assign.
	 * 
	 * @return <code>true</code> if the instruction is an Assign
	 */
	boolean isInstAssign();

	/**
	 * Returns <code>true</code> if the instruction is a Call.
	 * 
	 * @return <code>true</code> if the instruction is a Call
	 */
	boolean isInstCall();

	/**
	 * Returns <code>true</code> if the instruction is a Load.
	 * 
	 * @return <code>true</code> if the instruction is a Load
	 */
	boolean isInstLoad();

	/**
	 * Returns <code>true</code> if the instruction is a Phi.
	 * 
	 * @return <code>true</code> if the instruction is a Phi
	 */
	boolean isInstPhi();

	/**
	 * Returns <code>true</code> if the instruction is a Return.
	 * 
	 * @return <code>true</code> if the instruction is a Return
	 */
	boolean isInstReturn();

	/**
	 * Returns <code>true</code> if the instruction is a Store.
	 * 
	 * @return <code>true</code> if the instruction is a Store
	 */
	boolean isInstStore();
	
	/**
	 * Returns <code>true</code> if the instruction is a Store.
	 * 
	 * @return <code>true</code> if the instruction is a Store
	 */
	boolean isStore();

	/**
	 * Return <code>true</code> if the instruction is a backend specific instruction
	 * 
	 * @return <code>true</code> if the instruction is a backend specific instruction
	 */
	boolean isInstSpecific();

	/**
	 * Sets the line number of this instruction.
	 * 
	 * @param newLineNumber
	 *            the line number of this instruction
	 */
	void setLineNumber(int newLineNumber);

	/**
	 * Sets the predicate associated with this instruction. This is used by
	 * if-conversion.
	 * 
	 * @param predicate
	 *            the predicate associated with this instruction
	 */
	void setPredicate(Predicate predicate);

}
