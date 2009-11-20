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
	 * @param args
	 *            arguments
	 */
	public void visit(Assign assign, Object... args);

	/**
	 * Visits a call instruction.
	 * 
	 * @param call
	 *            a call instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Call call, Object... args);

	/**
	 * Visits a hasTokens instruction.
	 * 
	 * @param hasTokens
	 *            an hasTokens instruction
	 * @param args
	 *            arguments
	 */
	public void visit(HasTokens hasTokens, Object... args);

	/**
	 * Visits an initPort instruction.
	 * 
	 * @param initPort
	 *            an initPort instruction
	 * @param args
	 *            arguments
	 */
	public void visit(InitPort initPort, Object... args);

	/**
	 * Visits a load instruction.
	 * 
	 * @param load
	 *            a load instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Load load, Object... args);

	/**
	 * Visits a peek instruction.
	 * 
	 * @param peek
	 *            a peek instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Peek peek, Object... args);

	/**
	 * Visits a phi assignment instruction.
	 * 
	 * @param phi
	 *            a phi assignment instruction
	 * @param args
	 *            arguments
	 */
	public void visit(PhiAssignment phi, Object... args);

	/**
	 * Visits a read instruction.
	 * 
	 * @param read
	 *            a read instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Read read, Object... args);

	/**
	 * Visits a readEnd instruction.
	 * 
	 * @param readEnd
	 *            a readEnd instruction
	 * @param args
	 *            arguments
	 */
	public void visit(ReadEnd readEnd, Object... args);

	/**
	 * Visits a return instruction.
	 * 
	 * @param returnInst
	 *            a return instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Return returnInst, Object... args);

	/**
	 * Visits a specific instruction.
	 * 
	 * @param specific
	 *            a specific instruction
	 * @param args
	 *            arguments
	 */
	public void visit(SpecificInstruction specific, Object... args);

	/**
	 * Visits a store instruction.
	 * 
	 * @param store
	 *            a store instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Store store, Object... args);

	/**
	 * Visits a write instruction.
	 * 
	 * @param write
	 *            a write instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Write write, Object... args);

	/**
	 * Visits a writeEnd instruction.
	 * 
	 * @param writeEnd
	 *            a writeEnd instruction
	 * @param args
	 *            arguments
	 */
	public void visit(WriteEnd writeEnd, Object... args);

}
