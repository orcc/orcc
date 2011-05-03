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

import net.sf.orcc.backends.instructions.InstTernary;
import net.sf.orcc.backends.instructions.InstructionsFactory;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * 
 * This class defines a transformation that replace ifNode in function by our
 * ternary operation.
 * 
 * @author Herve Yviquel
 * 
 */
public class InstTernaryAdder extends AbstractActorVisitor<Object> {

	private NodeBlock newBlockNode;
	private Var condVar;

	@Override
	public Object caseProcedure(Procedure procedure) {
		if (!procedure.getReturnType().isVoid()) {
			newBlockNode = IrFactoryImpl.eINSTANCE.createNodeBlock();
			super.caseProcedure(procedure);
			EcoreHelper.delete(procedure.getNodes());
			procedure.getNodes().add(newBlockNode);
		}
		return null;
	}

	@Override
	public Object caseNodeIf(NodeIf nodeIf) {
		Var oldCondVar = condVar;

		Expression condExpr = nodeIf.getCondition();
		condVar = procedure.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeBool(), "ifCondition_"
						+ nodeIf.getLocation().getStartLine());
		condVar.setIndex(1);
		InstAssign assignCond = IrFactory.eINSTANCE.createInstAssign(condVar,
				condExpr);
		newBlockNode.add(assignCond);

		doSwitch(nodeIf.getThenNodes());
		doSwitch(nodeIf.getElseNodes());
		doSwitch(nodeIf.getJoinNode());
		condVar = oldCondVar;

		return null;
	}

	@Override
	public Object caseInstAssign(InstAssign assign) {
		newBlockNode.add(EcoreHelper.copy(assign));
		return null;
	}

	@Override
	public Object caseInstCall(InstCall call) {
		newBlockNode.add(EcoreHelper.copy(call));
		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		newBlockNode.add(EcoreHelper.copy(load));
		return null;
	}

	@Override
	public Object caseInstPhi(InstPhi phi) {
		InstTernary ternaryOp = InstructionsFactory.eINSTANCE
				.createInstTernary(phi.getTarget().getVariable(),
						IrFactory.eINSTANCE.createExprVar(condVar),
						EcoreHelper.copy(phi.getValues().get(0)),
						EcoreHelper.copy(phi.getValues().get(1)));

		newBlockNode.add(ternaryOp);
		return null;
	}

	@Override
	public Object caseInstReturn(InstReturn returnInstr) {
		newBlockNode.add(EcoreHelper.copy(returnInstr));
		return null;
	}

	@Override
	public Object caseInstSpecific(InstSpecific inst) {
		newBlockNode.add(EcoreHelper.copy(inst));
		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		newBlockNode.add(EcoreHelper.copy(store));
		return null;
	}
}
