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
	 * @param node
	 *            an assign instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Assign node, Object... args);

	/**
	 * Visits a call instruction.
	 * 
	 * @param node
	 *            a call instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Call node, Object... args);

	/**
	 * Visits a hasTokens instruction.
	 * 
	 * @param node
	 *            a hasTokens instruction
	 * @param args
	 *            arguments
	 */
	public void visit(HasTokens node, Object... args);

	/**
	 * Visits an initPort instruction.
	 * 
	 * @param node
	 *            an initPort instruction
	 * @param args
	 *            arguments
	 */
	public void visit(InitPort node, Object... args);

	/**
	 * Visits a load instruction.
	 * 
	 * @param node
	 *            a load instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Load node, Object... args);

	/**
	 * Visits a peek instruction.
	 * 
	 * @param node
	 *            a peek instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Peek node, Object... args);

	/**
	 * Visits a phi assignment instruction.
	 * 
	 * @param node
	 *            a phi assignment instruction
	 * @param args
	 *            arguments
	 */
	public void visit(PhiAssignment node, Object... args);

	/**
	 * Visits a readBegin instruction.
	 * 
	 * @param node
	 *            a readBegin instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Read node, Object... args);

	/**
	 * Visits a readEnd instruction.
	 * 
	 * @param node
	 *            a readEnd instruction
	 * @param args
	 *            arguments
	 */
	public void visit(ReadEnd node, Object... args);

	/**
	 * Visits a return instruction.
	 * 
	 * @param node
	 *            a return instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Return node, Object... args);

	/**
	 * Visits a specific instruction.
	 * 
	 * @param node
	 *            a specific instruction
	 * @param args
	 *            arguments
	 */
	public void visit(SpecificInstruction node, Object... args);

	/**
	 * Visits a store instruction.
	 * 
	 * @param node
	 *            a store instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Store node, Object... args);

	/**
	 * Visits a writeBegin instruction.
	 * 
	 * @param node
	 *            a writeBegin instruction
	 * @param args
	 *            arguments
	 */
	public void visit(Write node, Object... args);

	/**
	 * Visits a writeEnd instruction.
	 * 
	 * @param node
	 *            a writeEnd instruction
	 * @param args
	 *            arguments
	 */
	public void visit(WriteEnd node, Object... args);

}
