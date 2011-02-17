/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.ListIterator;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;

/**
 * This class defines methods to move code (blocks and instructions) from one
 * procedure to another.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CodeMover extends AbstractActorVisitor {

	private BlockNode targetBlock;

	private Procedure targetProcedure;

	/**
	 * Moves the list of instructions from the position of the given list
	 * iterator.
	 * 
	 * @param itInstruction
	 *            a list iterator of instructions
	 */
	public void moveInstructions(ListIterator<Instruction> itInstruction) {
		targetBlock = BlockNode.getLast(targetProcedure);
		while (itInstruction.hasNext()) {
			Instruction instruction = itInstruction.next();
			this.procedure = instruction.getBlock().getProcedure();
			itInstruction.remove();
			instruction.accept(this);

			targetBlock.add(instruction);
		}
	}

	/**
	 * Moves local variable used by the given instruction to the target
	 * procedure.
	 * 
	 * @param instruction
	 *            an instruction
	 * @param variable
	 *            a variable
	 */
	private void moveLocalVariable(Instruction instruction,
			LocalVariable variable) {
		procedure.getLocals().remove(variable.getName());
		targetProcedure.getLocals().put(variable.getName(), variable);
	}

	/**
	 * Moves the list of nodes from the position of the given list iterator.
	 * 
	 * @param itNode
	 *            a list iterator of nodes
	 */
	public void moveNodes(ListIterator<CFGNode> itNode) {
		while (itNode.hasNext()) {
			CFGNode node = itNode.next();
			itNode.remove();
			node.accept(this);

			targetProcedure.getNodes().add(node);
		}
	}

	/**
	 * Sets the target procedure, ie the procedure in which the code mover will
	 * move instructions and nodes.
	 * 
	 * @param procedure
	 *            the target procedure
	 */
	public void setTargetProcedure(Procedure procedure) {
		this.targetProcedure = procedure;
	}

	@Override
	public void visit(Assign assign) {
		moveLocalVariable(assign, assign.getTarget());
	}

	@Override
	public void visit(BlockNode blockNode) {
		this.procedure = blockNode.getProcedure();
		moveInstructions(blockNode.listIterator());
	}

	@Override
	public void visit(Load load) {
		moveLocalVariable(load, load.getTarget());
	}

	@Override
	public void visit(Write write) {
		moveLocalVariable(write, (LocalVariable) write.getTarget());
	}

}
