/*
 * Copyright (c) 2013, University of Rennes 1 / IRISA
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
 *   * Neither the name of the University of Rennes 1 / IRISA nor the names of its
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
package net.sf.orcc.backends.transform;

import java.util.List;

import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 *
 *
 * @author Herve Yviquel
 *
 */
public class ShortCircuitTransformation extends AbstractIrVisitor<Expression> {

	private static IrFactory factory = IrFactory.eINSTANCE;
	
	public ShortCircuitTransformation() {
		super(true);
	}

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		// visit both branches
		expr.setE1(doSwitch(expr.getE1()));
		expr.setE2(doSwitch(expr.getE2()));

		if (isEvaluableShortly(expr.getOp())) {
			Var newVar = procedure.newTempLocalVariable(
					IrUtil.copy(expr.getType()), "sc_expr");
			ExprVar newExpr = factory.createExprVar(newVar);

			// Binary expression is split into several BlockIf blocks
			BlockIf newIf = factory.createBlockIf();
			newIf.setCondition(expr.getE1());

			BlockBasic blockTrue = factory.createBlockBasic();
			InstAssign assignTrue = factory.createInstAssign();
			assignTrue.setTarget(factory.createDef(newVar));
			blockTrue.add(assignTrue);
			newIf.getThenBlocks().add(blockTrue);

			BlockBasic blockFalse = factory.createBlockBasic();
			InstAssign assignFalse = factory.createInstAssign();
			assignFalse.setTarget(factory.createDef(newVar));
			blockFalse.add(assignFalse);
			newIf.getElseBlocks().add(blockFalse);

			if (expr.getOp() == OpBinary.LOGIC_AND) {
				assignTrue.setValue(expr.getE2());
				assignFalse.setValue(factory.createExprBool(false));
			} else if (expr.getOp() == OpBinary.LOGIC_OR) {
				assignTrue.setValue(factory.createExprBool(true));
				assignFalse.setValue(expr.getE2());
			}
						
			Instruction containingInst = EcoreHelper.getContainerOfType(expr,
					Instruction.class);
			Block containingBlock = EcoreHelper.getContainerOfType(expr,
					Block.class);
			
			// If the expression is contained in the condition of a BlockWhile, 
			// the transformation block is also put at the end of this BlockWhile
			if (containingInst == null && containingBlock.isBlockWhile()) {
					List<Block> whileBlocks = ((BlockWhile) containingBlock)
							.getBlocks();
					whileBlocks.add(IrUtil.copy(newIf));
			}

			// In all cases, the transformation block is added before the expression location
			IrUtil.addBlockBeforeExpr(expr, newIf);
			
			EcoreUtil.replace(expr, newExpr);
			IrUtil.delete(expr);
			
			return newExpr;
		}

		return expr;
	}

	@Override
	public Expression caseExprBool(ExprBool expr) {
		return expr;
	}

	@Override
	public Expression caseExprFloat(ExprFloat expr) {
		return expr;
	}

	@Override
	public Expression caseExprInt(ExprInt expr) {
		return expr;
	}

	@Override
	public Expression caseExprList(ExprList expr) {
		return expr;
	}

	@Override
	public Expression caseExprString(ExprString expr) {
		return expr;
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		expr.setExpr(doSwitch(expr.getExpr()));
		return expr;
	}

	@Override
	public Expression caseExprVar(ExprVar expr) {
		return expr;
	}

	private boolean isEvaluableShortly(OpBinary op) {
		return op == OpBinary.LOGIC_AND || op == OpBinary.LOGIC_OR;
	}

}
