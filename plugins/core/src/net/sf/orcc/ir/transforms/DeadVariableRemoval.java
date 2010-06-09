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
package net.sf.orcc.ir.transforms;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a very simple Dead Variable Elimination.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DeadVariableRemoval extends AbstractActorTransformation {

	private boolean changed;

	/**
	 * Removes the given instruction(s) that store to an unused local variable.
	 * 
	 * @param variable
	 *            a local variable
	 */
	private void remove(LocalVariable variable) {
		List<Instruction> instructions = variable.getInstructions();
		if (instructions != null) {
			for (Instruction instruction : instructions) {
				instruction.getBlock().getInstructions().remove(instruction);
			}
		}

		Instruction instruction = variable.getInstruction();
		if (instruction != null) {
			instruction.getBlock().getInstructions().remove(instruction);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Assign assign, Object... args) {
		LocalVariable variable = assign.getTarget();
		if (!variable.isUsed()) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			assign.setValue(null);
			it.remove();

			procedure.getLocals().remove(variable);
			changed = true;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Load load, Object... args) {
		LocalVariable variable = load.getTarget();
		if (!variable.isUsed()) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			load.setSource(null);
			it.remove();

			procedure.getLocals().remove(variable);
			changed = true;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Peek peek, Object... args) {
		Variable variable = peek.getTarget();
		if (!variable.isUsed()) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			peek.setPort(null);
			it.remove();

			procedure.getLocals().remove(variable);
			changed = true;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(PhiAssignment phi, Object... args) {
		Variable variable = phi.getTarget();
		if (!variable.isUsed()) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			it.remove();

			List<Use> vars = phi.getVars();
			for (Use use : vars) {
				use.remove();
			}

			procedure.getLocals().remove(variable);
			changed = true;
		}
	}

	@Override
	public void visit(Read read, Object... args) {
		// do NOT remove read!
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		changed = true;

		while (changed) {
			changed = false;

			// first shot: removes locals not used by any instruction
			OrderedMap<Variable> locals = procedure.getLocals();
			Iterator<Variable> it = locals.iterator();
			while (it.hasNext()) {
				LocalVariable local = (LocalVariable) it.next();
				if (!local.isUsed() && local.getInstruction() == null) {
					changed = true;
					it.remove();
					remove(local);
				}
			}

			super.visitProcedure(procedure);
		}
	}

}
