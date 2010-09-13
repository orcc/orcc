/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.AbstractExpressionInterpreter;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.util.OrderedMap;

/**
 * Remove instructions that directly assign a value or a variable to a target
 * 
 * @author Jérôme GORIN
 * 
 */
public class CopyPropagationTransformation extends AbstractActorTransformation {

	private class ExpressionCopy extends AbstractExpressionInterpreter {

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			Variable var = expr.getVar().getVariable();
			if (copyVars.containsKey(var)) {
				return copyVars.get(var);
			}

			return expr;
		}
	}

	private Map<Variable, Expression> copyVars;
	private List<Instruction> removedInstrs;

	public CopyPropagationTransformation() {
		copyVars = new HashMap<Variable, Expression>();
		removedInstrs = new ArrayList<Instruction>();

	}

	/**
	 * Removes the given list of instructions that store to an unused state
	 * variable.
	 * 
	 * @param instructions
	 *            a list of instructions
	 */
	private void removeInstructions(List<Instruction> instructions) {
		if (instructions != null) {
			for (Instruction instr : instructions) {
				instr.getBlock().getInstructions().remove(instr);
			}
		}
	}

	/**
	 * Removes the given list of variables from the procedure
	 * 
	 * @param variables
	 *            a map of variable
	 */
	private void removeVariables(Map<Variable, Expression> variables) {
		OrderedMap<String, Variable> lovalVars = procedure.getLocals();

		for (Map.Entry<Variable, Expression> entry : copyVars.entrySet()) {
			Variable var = entry.getKey();
			lovalVars.remove(var.getName());
		}

	}

	/**
	 * Detect if the assign instructions can be removed
	 * 
	 * @param assign
	 *            assign instruction
	 */
	@Override
	public void visit(Assign assign, Object... args) {
		Expression value = assign.getValue();

		if ((!(value instanceof BinaryExpr)) && (!(value instanceof UnaryExpr))) {
			// Assign instruction can be remove
			LocalVariable target = assign.getTarget();
			Expression expr = assign.getValue();

			// Set instruction and variable as to be remove
			copyVars.put(target, expr);
			removedInstrs.add(assign);
		}
	}

	//
	@Override
	public void visit(PhiAssignment phi, Object... args) {
		List<Expression> values = phi.getValues();
		for (Expression expr : phi.getValues()) {
			Expression newExpr = (Expression) expr.accept(new ExpressionCopy());
			values.set(values.indexOf(expr), newExpr);
		}
	}

	@Override
	public void visit(Return returnInstr, Object... args) {
		Expression expr = returnInstr.getValue();
		if (expr != null) {
			Expression newExpr = (Expression) expr.accept(new ExpressionCopy());
			returnInstr.setValue(newExpr);
		}
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		copyVars.clear();
		super.visitProcedure(procedure);

		// Remove useless instructions and variables
		removeInstructions(removedInstrs);
		removeVariables(copyVars);
	}

}
