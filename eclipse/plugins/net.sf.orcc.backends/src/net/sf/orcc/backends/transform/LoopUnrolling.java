/*
 * Copyright (c) 2013, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;

/**
 * A simple loop unrolling transformation
 * 
 * @author Endri Bezati
 * 
 */
public class LoopUnrolling extends AbstractIrVisitor<Void> {

	private class InnerExpressionVisitor extends AbstractIrVisitor<Expression> {

		private int idxLiteral;

		private Var index;

		public InnerExpressionVisitor(Var index, int idxLiteral) {
			super(true);
			this.index = index;
			this.idxLiteral = idxLiteral;
		}

		@Override
		public Expression caseExprBinary(ExprBinary expr) {
			Expression e1 = doSwitch(expr.getE1());
			Expression e2 = doSwitch(expr.getE2());
			return IrFactory.eINSTANCE.createExprBinary(e1, expr.getOp(), e2,
					IrUtil.copy(expr.getType()));
		}

		@Override
		public Expression caseExprBool(ExprBool object) {
			return IrUtil.copy(object);
		}

		@Override
		public Expression caseExprFloat(ExprFloat object) {
			return IrUtil.copy(object);
		}

		@Override
		public Expression caseExprInt(ExprInt object) {
			return IrUtil.copy(object);
		}

		@Override
		public Expression caseExprString(ExprString object) {
			return IrUtil.copy(object);
		}

		@Override
		public Expression caseExprVar(ExprVar object) {
			Var var = object.getUse().getVariable();
			if (var == index) {
				return IrFactory.eINSTANCE.createExprInt(idxLiteral);
			}
			return IrUtil.copy(object);
		}

	}

	private class InstructionUnroller extends AbstractIrVisitor<Void> {
		@Override
		public Void caseInstAssign(InstAssign assign) {
			Var target = assign.getTarget().getVariable();
			// Test if Assign(index, index + 1)
			if (target == index) {
				Expression value = assign.getValue();
				if (value.isExprBinary()) {
					ExprBinary exprBin = (ExprBinary) value;
					Expression e1 = exprBin.getE1();
					Expression e2 = exprBin.getE2();
					// Assign(index, index + 1)
					if (e1.isExprVar() && e2.isExprInt()
							&& exprBin.getOp() == OpBinary.PLUS) {
						Var idx = ((ExprVar) e1).getUse().getVariable();
						if (idx == index) {
							IrUtil.delete(assign);
						}
					}
				}
			} else {
				for (int i = 0; i <= repetition; i++) {
					Expression value = assign.getValue();

					InnerExpressionVisitor innerVisitor = new InnerExpressionVisitor(
							index, i);

					Expression unrolledValue = innerVisitor.doSwitch(value);

					InstAssign instAssign = IrFactory.eINSTANCE
							.createInstAssign(target, unrolledValue);

					int idxInst = assign.getBlock().indexOf(assign);
					BlockBasic block = (BlockBasic) unrollBlocks.get(i);
					block.add(idxInst, instAssign);
				}
			}
			return null;
		}

		@Override
		public Void caseInstCall(InstCall call) {
			for (int i = 0; i <= repetition; i++) {
				Var target = call.getTarget().getVariable();
				List<Arg> arguments = call.getArguments();
				List<Arg> unrolledArguments = new ArrayList<Arg>(
						arguments.size());

				InnerExpressionVisitor innerVisitor = new InnerExpressionVisitor(
						index, i);

				for (Arg arg : arguments) {
					if (arg.isByVal()) {
						ArgByVal argByVal = (ArgByVal) arg;
						Expression expr = innerVisitor.doSwitch(argByVal
								.getValue());
						Arg unrolledArg = IrFactory.eINSTANCE
								.createArgByVal(expr);
						unrolledArguments.add(unrolledArg);
					} else {
						ArgByRef argByRef = (ArgByRef) arg;
						Var var = argByRef.getUse().getVariable();
						List<Expression> indexes = argByRef.getIndexes();
						List<Expression> newIndexes = new ArrayList<Expression>(
								indexes.size());
						for (Expression expression : indexes) {
							Expression expr = innerVisitor.doSwitch(expression);
							newIndexes.add(expr);
						}
						Use use = IrFactory.eINSTANCE.createUse(var);
						ArgByRef unrolledArg = IrFactory.eINSTANCE
								.createArgByRef();
						unrolledArg.setUse(use);
						unrolledArg.getIndexes().addAll(newIndexes);
					}
				}

				InstCall instCall = IrFactory.eINSTANCE.createInstCall();
				Def defTarget = IrFactory.eINSTANCE.createDef(target);
				instCall.setTarget(defTarget);
				instCall.setProcedure(call.getProcedure());
				instCall.getArguments().addAll(unrolledArguments);

				int idxInst = call.getBlock().indexOf(call);
				BlockBasic block = (BlockBasic) unrollBlocks.get(i);
				block.add(idxInst, instCall);
			}

			return null;
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			for (int i = 0; i <= repetition; i++) {

				Var target = load.getTarget().getVariable();
				Var source = load.getSource().getVariable();

				InnerExpressionVisitor innerVisitor = new InnerExpressionVisitor(
						index, i);
				List<Expression> indexes = load.getIndexes();
				List<Expression> newIndexes = new ArrayList<Expression>(
						indexes.size());

				for (Expression expression : indexes) {
					Expression expr = innerVisitor.doSwitch(expression);
					newIndexes.add(expr);
				}

				InstLoad instLoad = IrFactory.eINSTANCE.createInstLoad(target,
						source, newIndexes);
				int idxInst = load.getBlock().indexOf(load);
				BlockBasic block = (BlockBasic) unrollBlocks.get(i);
				block.add(idxInst, instLoad);
			}
			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			for (int i = 0; i <= repetition; i++) {
				// Create new Store Instruction
				Var target = store.getTarget().getVariable();
				InnerExpressionVisitor innerVisitor = new InnerExpressionVisitor(
						index, i);

				List<Expression> indexes = store.getIndexes();
				List<Expression> newIndexes = new ArrayList<Expression>(
						indexes.size());

				for (Expression expression : indexes) {
					Expression expr = innerVisitor.doSwitch(expression);
					newIndexes.add(expr);
				}

				Expression value = innerVisitor.doSwitch(store.getValue());

				InstStore instStore = IrFactory.eINSTANCE.createInstStore(
						target, newIndexes, value);
				int idxInst = store.getBlock().indexOf(store);
				BlockBasic block = (BlockBasic) unrollBlocks.get(i);
				block.add(idxInst, instStore);
			}
			return null;
		}
	}

	/** The Loop index **/
	private Var index = null;

	/** The Loop repetition **/
	private int repetition = 0;

	/** The unrolled loops **/
	private List<BlockBasic> unrollBlocks;

	@Override
	public Void caseBlockWhile(BlockWhile blockWhile) {
		super.caseBlockWhile(blockWhile);
		if (blockWhile.hasAttribute("unroll")) {
			unroll(blockWhile);
		}
		return null;
	}

	/**
	 * This method test if a blockWhile contains only BlockBasic blocks
	 * 
	 * @param blockWhile
	 * @return
	 */
	protected boolean testSimpleLoop(BlockWhile blockWhile) {
		for (Block block : blockWhile.getBlocks()) {
			if (block.isBlockIf() || block.isBlockWhile()) {
				return false;
			}
		}
		return true;
	}

	protected void unroll(BlockWhile blockWhile) {
		Expression condition = blockWhile.getCondition();
		if (condition.isExprBinary()) {
			ExprBinary exprBin = (ExprBinary) condition;
			Expression e1 = exprBin.getE1();
			Expression e2 = exprBin.getE2();
			OpBinary op = exprBin.getOp();
			if (e1.isExprVar() && e2.isExprInt()
					&& (op == OpBinary.LT || op == OpBinary.LE)) {
				index = ((ExprVar) e1).getUse().getVariable();
				if (op == OpBinary.LT) {
					repetition = ((ExprInt) e2).getIntValue() - 1;
				} else {
					repetition = ((ExprInt) e2).getIntValue();
				}
				if (testSimpleLoop(blockWhile)) {
					unrollBlocks = new ArrayList<BlockBasic>(repetition);
					for (int i = 0; i <= repetition; i++) {
						unrollBlocks.add(i,
								IrFactory.eINSTANCE.createBlockBasic());
					}

					// Unroll Loop
					InstructionUnroller unroller = new InstructionUnroller();
					unroller.doSwitch(blockWhile.getBlocks());

					List<Block> containerBlocks = null;
					// Take the unroll blocks and add them before the blockWhile
					if (blockWhile.eContainer() instanceof Procedure) {
						Procedure container = (Procedure) blockWhile
								.eContainer();
						containerBlocks = container.getBlocks();
					} else if (blockWhile.eContainer() instanceof BlockWhile) {
						BlockWhile container = (BlockWhile) blockWhile
								.eContainer();
						containerBlocks = container.getBlocks();
					} else if (blockWhile.eContainer() instanceof BlockIf) {
						BlockIf container = (BlockIf) blockWhile.eContainer();
						// Test if then or else blocks contains this blockWhile
						if (container.getThenBlocks().contains(blockWhile)) {
							containerBlocks = container.getThenBlocks();
						} else {
							containerBlocks = container.getElseBlocks();
						}
					}

					if (containerBlocks != null) {
						BlockBasic combinedBlock = IrFactory.eINSTANCE.createBlockBasic();
						for (int i = 0; i <= repetition; i++) {
							combinedBlock.getInstructions().addAll(unrollBlocks.get(i).getInstructions());
						}
						
						int idxBlockWhile = containerBlocks.indexOf(blockWhile);
						containerBlocks.add(idxBlockWhile,combinedBlock);
								
						IrUtil.delete(blockWhile);
					}

				} else {
					OrccLogger
							.warnln("LoopUnrolling cannot process on loop at line: "
									+ blockWhile.getLineNumber());
				}
			} else {
				OrccLogger
						.warnln("LoopUnrolling cannot process on loop at line: "
								+ blockWhile.getLineNumber());
			}
		}
	}

}
