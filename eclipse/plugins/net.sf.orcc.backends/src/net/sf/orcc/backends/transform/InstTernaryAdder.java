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
package net.sf.orcc.backends.transform;

import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.backends.ir.IrSpecificFactory;
import net.sf.orcc.df.Action;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * 
 * This class defines a transformation that replace block 'if' in function by
 * our ternary instruction when it is possible.
 * 
 * @author Herve Yviquel
 * 
 */
public class InstTernaryAdder extends AbstractIrVisitor<Void> {

	private Var condVar;
	private BlockBasic newBasicBlock;

	@Override
	public Void caseBlockIf(BlockIf blockIf) {
		Var oldCondVar = condVar;

		Expression condExpr = blockIf.getCondition();
		condVar = procedure.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeBool(),
				"ifCondition_" + blockIf.getLineNumber());
		condVar.setIndex(1);
		InstAssign assignCond = IrFactory.eINSTANCE.createInstAssign(condVar,
				condExpr);
		newBasicBlock.add(assignCond);

		doSwitch(blockIf.getThenBlocks());
		doSwitch(blockIf.getElseBlocks());
		doSwitch(blockIf.getJoinBlock());
		condVar = oldCondVar;

		return null;
	}

	@Override
	public Void caseInstAssign(InstAssign assign) {
		newBasicBlock.add(IrUtil.copy(assign));
		return null;
	}

	@Override
	public Void caseInstCall(InstCall call) {
		newBasicBlock.add(IrUtil.copy(call));
		return null;
	}

	@Override
	public Void caseInstLoad(InstLoad load) {
		newBasicBlock.add(IrUtil.copy(load));
		return null;
	}

	@Override
	public Void caseInstPhi(InstPhi phi) {
		InstTernary ternaryOp = IrSpecificFactory.eINSTANCE.createInstTernary(
				phi.getTarget().getVariable(),
				IrFactory.eINSTANCE.createExprVar(condVar),
				IrUtil.copy(phi.getValues().get(0)),
				IrUtil.copy(phi.getValues().get(1)));

		newBasicBlock.add(ternaryOp);
		return null;
	}

	@Override
	public Void caseInstReturn(InstReturn returnInstr) {
		newBasicBlock.add(IrUtil.copy(returnInstr));
		return null;
	}

	@Override
	public Void defaultCase(EObject object) {
		if (object instanceof Instruction) {
			newBasicBlock.add((Instruction) IrUtil.copy(object));
		}
		return null;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		newBasicBlock.add(IrUtil.copy(store));
		return null;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		if (isTernarisable(procedure)) {
			newBasicBlock = IrFactoryImpl.eINSTANCE.createBlockBasic();
			super.caseProcedure(procedure);
			IrUtil.delete(procedure.getBlocks());
			procedure.getBlocks().add(newBasicBlock);
		}
		return null;
	}

	/**
	 * Returns true if this procedure can be transformed to ternary instructions
	 * 
	 * @param the
	 *            tested procedure
	 * @return true if this procedure can be transformed to ternary instructions
	 */
	private boolean isTernarisable(Procedure procedure) {
		// The procedure has to be a function
		if (procedure.eContainer() instanceof Action
				|| procedure.getReturnType().isVoid()) {
			return false;
		}
		// The procedure cannot contain any loops
		TreeIterator<EObject> it = EcoreUtil.getAllContents(procedure
				.getBlocks());
		while (it.hasNext()) {
			EObject object = it.next();
			if (object instanceof BlockWhile) {
				return false;
			}
		}
		return true;
	}
}
