/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.xlim.transformations;

import java.util.List;

import net.sf.orcc.backends.xlim.instructions.TernaryOperation;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.util.OrderedMap;

/**
 * 
 * This class defines a transformation that change the initial value expression
 * by a constant expression in order to put phiNode before whileNode
 * 
 * @author Herve Yviquel
 * 
 */
public class ConstantPhiValuesTransformation extends AbstractActorVisitor {

	@Override
	public void visit(PhiAssignment phi) {
		List<Expression> values = phi.getValues();
		VarLocal target = phi.getTarget();
		OrderedMap<String, VarLocal> parameters = procedure
				.getParameters();

		// Remove local variable with index = 0 from value
		for (Expression value : values) {
			if (value.isVarExpr()) {
				VarExpr sourceExpr = (VarExpr) value;
				VarLocal source = (VarLocal) sourceExpr.getVar()
						.getVariable();

				// Local variable must not be a parameter of the procedure
				if (source.getIndex() == 0
						&& !parameters.contains(source.getName())) {
					Expression expr;
					if (target.getType().isBool()) {
						expr = new BoolExpr(false);
					} else {
						expr = new IntExpr(0);
					}
					values.set(values.indexOf(value), expr);
				}
			}
		}
	}

	@Override
	public void visit(SpecificInstruction node) {
		if (node instanceof TernaryOperation) {
			TernaryOperation ternaryOperation = (TernaryOperation) node;
			ternaryOperation.setConditionValue(clean(ternaryOperation
					.getConditionValue()));
			ternaryOperation
					.setTrueValue(clean(ternaryOperation.getTrueValue()));
			ternaryOperation.setFalseValue(clean(ternaryOperation
					.getFalseValue()));
		}
	}

	public Expression clean(Expression oldExpr) {
		if (oldExpr.isVarExpr()) {
			VarLocal var = (VarLocal) ((VarExpr) oldExpr).getVar()
					.getVariable();

			// Local variable must not be a parameter of the procedure
			if (var.getIndex() == 0
					&& !procedure.getParameters().contains(var.getName())) {
				Expression expr;
				if (var.getType().isBool()) {
					expr = new BoolExpr(false);
				} else {
					expr = new IntExpr(0);
				}
				return expr;
			}
		}
		return oldExpr;
	}

}
