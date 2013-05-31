/*
 * Copyright (c) 2010-2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.df.Action;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an actor transformation that inline function(s) and/or
 * procedure(s)
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * @author Thavot Richard
 * 
 * @version 1.1
 * 
 */
public class Inliner extends AbstractIrVisitor<Void> {
	private class ExpressionUpdater extends AbstractIrVisitor<Void> {

		public ExpressionUpdater() {
			super(true);
		}

		/**
		 * Replace the local variable name if a reference already exists to a
		 * global variable
		 */
		@Override
		public Void caseExprVar(ExprVar exprVar) {
			Var var = exprVar.getUse().getVariable();
			if (localToLocalsMap.containsKey(var)) {
				exprVar.getUse().setVariable(localToLocalsMap.get(var));
			}
			return null;
		}

		@Override
		public Void caseInstAssign(InstAssign assign) {
			// Replace the local variable name by visiting caseExprVar
			super.doSwitch(assign.getValue());
			Var var = assign.getTarget().getVariable();
			if (localToLocalsMap.containsKey(var)) {
				assign.getTarget().setVariable(localToLocalsMap.get(var));
			}
			return null;
		}

		@Override
		public Void caseInstCall(InstCall call) {
			Def def = call.getTarget();
			if (def != null) {
				Var var = def.getVariable();
				if (localToLocalsMap.containsKey(var)) {
					call.getTarget().setVariable(localToLocalsMap.get(var));
				}
			}
			for (Arg arg : call.getArguments()) {
				doSwitch(arg);
			}
			return null;
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			// Replace the local variable name by visiting caseExprVar
			Var var = load.getTarget().getVariable();
			if (localToLocalsMap.containsKey(var)) {
				load.getTarget().setVariable(localToLocalsMap.get(var));
			}
			var = load.getSource().getVariable();
			if (localToLocalsMap.containsKey(var)) {
				load.getSource().setVariable(localToLocalsMap.get(var));
			}
			for (Expression e : load.getIndexes()) {
				super.doSwitch(e);
			}
			return null;
		}

		@Override
		public Void caseInstPhi(InstPhi instPhi) {
			// Replace the local variable name by visiting caseExprVar
			Var var = instPhi.getTarget().getVariable();
			if (localToLocalsMap.containsKey(var)) {
				instPhi.getTarget().setVariable(localToLocalsMap.get(var));
			}
			for (Expression e : instPhi.getValues()) {
				super.doSwitch(e);
			}
			return null;
		}

		@Override
		public Void caseInstReturn(InstReturn inst) {
			// Replace the local variable name by visiting caseExprVar
			instReturn = inst;
			return super.doSwitch(inst.getValue());
		}

		@Override
		public Void caseInstStore(InstStore store) {
			// Replace the local variable name by visiting caseExprVar
			super.doSwitch(store.getValue());
			Var var = store.getTarget().getVariable();
			if (localToLocalsMap.containsKey(var)) {
				store.getTarget().setVariable(localToLocalsMap.get(var));
			}
			for (Expression e : store.getIndexes()) {
				super.doSwitch(e);
			}
			return null;
		}

		@Override
		public Void defaultCase(EObject object) {
			if (object instanceof InstTernary) {
				InstTernary ternary = (InstTernary) object;
				Var var = ternary.getTarget().getVariable();
				if (localToLocalsMap.containsKey(var)) {
					ternary.getTarget().setVariable(localToLocalsMap.get(var));
				}
				doSwitch(ternary.getConditionValue());
				doSwitch(ternary.getTrueValue());
				doSwitch(ternary.getFalseValue());
			}
			return null;
		}
	}

	private boolean inlineFunction;
	private boolean inlineProcedure;
	private boolean inlineActionBodyProcedure;

	private InstReturn instReturn;

	private Map<Var, Var> localToLocalsMap;
	private ExpressionUpdater updater;

	public Inliner(boolean inlineProcedure, boolean inlineFunction) {
		this.inlineProcedure = inlineProcedure;
		this.inlineFunction = inlineFunction;
		this.inlineActionBodyProcedure = true;
		this.updater = new ExpressionUpdater();
	}

	public Inliner(boolean inlineActionBodyProcedure, boolean inlineProcedure,
			boolean inlineFunction) {
		this.inlineProcedure = inlineProcedure;
		this.inlineFunction = inlineFunction;
		this.inlineActionBodyProcedure = inlineActionBodyProcedure;
		this.updater = new ExpressionUpdater();
	}

	@Override
	public Void caseInstCall(InstCall call) {
		Type returnType = call.getProcedure().getReturnType();
		if (returnType.isVoid() && inlineProcedure || !returnType.isVoid()
				&& inlineFunction) {
			if (!call.getProcedure().isNative()) {
				if (inlineActionBodyProcedure) {
					inlineProcedure(call);
				} else {
					Procedure procedure = call.getProcedure();
					Action action = EcoreHelper.getContainerOfType(procedure,
							Action.class);
					if (action != null) {
						if (procedure != action.getBody()) {
							inlineProcedure(call);
						}
					} else {
						inlineProcedure(call);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Find the given block into the given blocks location.
	 * 
	 * @param locationBlocks
	 * @param blockToFind
	 * @return
	 */
	public List<Block> findBlock(List<Block> locationBlocks, Block blockToFind) {
		if (locationBlocks.contains(blockToFind)) {
			return locationBlocks;
		} else {
			List<Block> n = null;
			for (Block block : locationBlocks) {
				if (block.isBlockIf()) {
					n = findBlock(((BlockIf) block).getElseBlocks(),
							blockToFind);
					if (n == null) {
						n = findBlock(((BlockIf) block).getThenBlocks(),
								blockToFind);
					}
				} else if (block.isBlockWhile()) {
					n = findBlock(((BlockWhile) block).getBlocks(), blockToFind);
				}
				if (n != null) {
					return n;
				}
			}
		}
		return null;
	}

	/**
	 * Inline the currentCall;
	 */
	private void inlineProcedure(InstCall currentCall) {
		// 1. Clone procedure
		Procedure calledProc = IrUtil.copy(currentCall.getProcedure());
		// 2. Get current procedure
		Procedure callerProc = EcoreHelper.getContainerOfType(currentCall,
				Procedure.class);
		// 3.1 Create a new local variable
		localToLocalsMap = new HashMap<Var, Var>();
		for (Var var : calledProc.getLocals()) {
			Var newVar = callerProc.newTempLocalVariable(var.getType(),
					"inlined_" + var.getName());
			newVar.setIndex(var.getIndex());
			// newVar.setLineNumber(var.getLineNumber());
			newVar.setAssignable(var.isAssignable());
			localToLocalsMap.put(var, newVar);
		}
		// 3.2
		int i = 0;
		BlockBasic assignBlock = IrFactory.eINSTANCE.createBlockBasic();
		for (Arg arg : currentCall.getArguments()) {
			Param param = calledProc.getParameters().get(i);
			if (arg.isByRef()) {
				ArgByRef ref = (ArgByRef) arg;
				localToLocalsMap.put(param.getVariable(), ref.getUse()
						.getVariable());
			} else {
				ArgByVal val = (ArgByVal) arg;
				if (val.getValue().isExprVar()) {
					Var var = ((ExprVar) val.getValue()).getUse().getVariable();
					localToLocalsMap.put(param.getVariable(), var);
				} else {
					Var newVar = callerProc.newTempLocalVariable(param
							.getVariable().getType(), "inlined_"
							+ param.getVariable().getName());
					newVar.setAssignable(true);
					localToLocalsMap.put(param.getVariable(), newVar);
					InstAssign instAssign = IrFactory.eINSTANCE
							.createInstAssign(newVar, val.getValue());
					assignBlock.add(instAssign);
				}
			}
			i++;
		}
		// 4. Rename local variable
		updater.doSwitch(calledProc);
		// 5. Transform return
		if ((currentCall.getTarget() == null)
				| calledProc.getReturnType().isVoid()) {
			IrUtil.delete(instReturn);
		} else {
			InstAssign instAssign = IrFactory.eINSTANCE.createInstAssign(
					currentCall.getTarget().getVariable(),
					IrUtil.copy(instReturn.getValue()));
			EcoreUtil.replace(instReturn, instAssign);
		}
		// 6. Cut the block containing call instruction in two parts
		BlockBasic beginningBlock = currentCall.getBlock();
		BlockBasic followingBlock = IrFactory.eINSTANCE.createBlockBasic();
		indexInst = 0;
		while (indexInst < beginningBlock.getInstructions().size()) {
			if (beginningBlock.getInstructions().get(indexInst)
					.equals(currentCall)) {
				break;
			}
			indexInst++;
		}
		while (indexInst < beginningBlock.getInstructions().size()) {
			followingBlock.add(beginningBlock.getInstructions().get(indexInst));
		}
		// 7. Add all inlined blocks
		List<Block> blocks = findBlock(callerProc.getBlocks(), beginningBlock);
		indexBlock = blocks.indexOf(beginningBlock);
		List<Block> inlined = calledProc.getBlocks();
		if (!assignBlock.getInstructions().isEmpty()) {
			inlined.add(0, assignBlock);
		}
		inlined.add(followingBlock);
		blocks.addAll(indexBlock + 1, inlined);
		// 8.
		IrUtil.delete(currentCall);
	}

}
