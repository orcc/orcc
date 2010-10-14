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

/**
 * This interface defines an instruction visitor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface InstructionVisitor {

	/**
	 * Visits an assign instruction.
	 * 
	 * @param assign
	 *            an assign instruction
	 */
	public void visit(Assign assign);

	/**
	 * Visits a call instruction.
	 * 
	 * @param call
	 *            a call instruction
	 */
	public void visit(Call call);

	/**
	 * Visits a hasTokens instruction.
	 * 
	 * @param hasTokens
	 *            an hasTokens instruction
	 */
	public void visit(HasTokens hasTokens);

	/**
	 * Visits a load instruction.
	 * 
	 * @param load
	 *            a load instruction
	 */
	public void visit(Load load);

	/**
	 * Visits a peek instruction.
	 * 
	 * @param peek
	 *            a peek instruction
	 */
	public void visit(Peek peek);

	/**
	 * Visits a phi assignment instruction.
	 * 
	 * @param phi
	 *            a phi assignment instruction
	 */
	public void visit(PhiAssignment phi);

	/**
	 * Visits a read instruction.
	 * 
	 * @param read
	 *            a read instruction
	 */
	public void visit(Read read);

	/**
	 * Visits a return instruction.
	 * 
	 * @param returnInst
	 *            a return instruction
	 */
	public void visit(Return returnInst);

	/**
	 * Visits a specific instruction.
	 * 
	 * @param specific
	 *            a specific instruction
	 */
	public void visit(SpecificInstruction specific);

	/**
	 * Visits a store instruction.
	 * 
	 * @param store
	 *            a store instruction
	 */
	public void visit(Store store);

	/**
	 * Visits a write instruction.
	 * 
	 * @param write
	 *            a write instruction
	 */
	public void visit(Write write);

}
