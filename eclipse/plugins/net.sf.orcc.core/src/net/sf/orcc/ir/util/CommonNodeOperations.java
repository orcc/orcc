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
package net.sf.orcc.ir.util;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalTargetContainer;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.TargetContainer;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.ValueContainer;
import net.sf.orcc.ir.Variable;

/**
 * This class defines operations common to nodes.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CommonNodeOperations {

	/**
	 * Sets the target of the given instruction.
	 * 
	 * @param instruction
	 *            an instruction
	 * @param target
	 *            a variable
	 */
	public static void setTarget(TargetContainer instruction, Variable target) {
		Variable thisTarget = instruction.getTarget();
		if (thisTarget != null) {
			thisTarget.removeInstruction((Instruction) instruction);
		}

		if (target != null) {
			target.addInstruction((Instruction) instruction);
		}

		instruction.internalSetTarget(target);
	}

	/**
	 * Sets the target of the given instruction.
	 * 
	 * @param instruction
	 *            an instruction
	 * @param target
	 *            a variable
	 */
	public static void setTarget(LocalTargetContainer instruction,
			LocalVariable target) {
		Variable thisTarget = instruction.getTarget();
		if (thisTarget != null) {
			thisTarget.setInstruction(null);
		}

		if (target != null) {
			target.setInstruction((Instruction) instruction);
		}

		instruction.internalSetTarget(target);
	}

	/**
	 * Sets the value of the given user.
	 * 
	 * @param user
	 *            an {@link CFGNode} or an {@link Instruction} that implements
	 *            {@link ValueContainer}
	 * @param value
	 *            an expression
	 */
	public static void setValue(ValueContainer user, Expression value) {
		Expression thisValue = user.getValue();
		if (thisValue != null) {
			Use.removeUses(user, thisValue);
		}

		if (value != null) {
			Use.addUses(user, value);
		}

		user.internalSetValue(value);
	}

}
