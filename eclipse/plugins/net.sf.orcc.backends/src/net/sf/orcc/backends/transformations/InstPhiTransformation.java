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
package net.sf.orcc.backends.transformations;

import java.util.List;

import net.sf.orcc.backends.instructions.InstTernary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

/**
 * 
 * This class defines a transformation that change the initial value expression
 * by a constant expression in order to put phiNode before whileNode
 * 
 * @author Herve Yviquel
 * 
 */
public class InstPhiTransformation extends AbstractActorVisitor<Object> {

	@Override
	public Object caseInstPhi(InstPhi phi) {
		List<Expression> values = phi.getValues();
		Var target = phi.getTarget().getVariable();
		List<Param> parameters = procedure.getParameters();

		// Remove local variable with index = 0 from value
		for (Expression value : values) {
			if (value.isExprVar()) {
				Var source = ((ExprVar) value).getUse().getVariable();

				// Local variable must not be a parameter of the procedure
				if (source.getIndex() == 0 && !parameters.contains(source.eContainer())) {
					Expression expr;
					if (target.getType().isBool()) {
						expr = IrFactory.eINSTANCE.createExprBool(false);
					} else {
						expr = IrFactory.eINSTANCE.createExprInt(0);
					}
					values.set(values.indexOf(value), expr);
					IrUtil.delete(value);
				}
			}
		}

		return null;
	}

	@Override
	public Object caseInstSpecific(InstSpecific instSpecific) {
		if (instSpecific instanceof InstTernary) {
			InstTernary ternaryOperation = (InstTernary) instSpecific;
			ternaryOperation.setConditionValue(clean(ternaryOperation
					.getConditionValue()));
			ternaryOperation
					.setTrueValue(clean(ternaryOperation.getTrueValue()));
			ternaryOperation.setFalseValue(clean(ternaryOperation
					.getFalseValue()));
		}
		return null;
	}

	public Expression clean(Expression oldExpr) {
		if (oldExpr.isExprVar()) {
			Var var = ((ExprVar) oldExpr).getUse().getVariable();

			// Local variable must not be a parameter of the procedure
			if (var.getIndex() == 0 && !procedure.getParameters().contains(var)) {
				Expression expr;
				if (var.getType().isBool()) {
					expr = IrFactory.eINSTANCE.createExprBool(false);
				} else {
					expr = IrFactory.eINSTANCE.createExprInt(0);
				}
				IrUtil.delete(oldExpr);
				return expr;
			}
		}
		return oldExpr;
	}

}
