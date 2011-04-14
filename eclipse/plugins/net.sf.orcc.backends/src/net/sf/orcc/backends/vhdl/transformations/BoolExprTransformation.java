/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * This class defines an actor transformation that transforms assignments whose
 * right hand side is a boolean expression to if nodes. Note: this
 * transformation must be called after PhiRemoval because it generates non-SSA
 * code.
 * 
 * <p>
 * The algorithm works as follows: Considering a block with instructions [i1,
 * i2, ..., ii, ..., in] where the instruction <code>ii</code> is an assign or a
 * store whose value is a boolean binary/unary expression, then create an NodeIf
 * after the current block that assigns <code>true</code> to the target if
 * <code>true</code>, and assigns <code>false</code> otherwise.
 * </p>
 * 
 * <p>
 * The remaining instructions <code>i(i+1)</code> to <code>in</code> are moved
 * to a new block created after the newly-created NodeIf. The
 * <code>previous</code> method is called on the node iterator so that the new
 * block is to be visited next.
 * </p>
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class BoolExprTransformation extends AbstractActorVisitor<Object> {

	@Override
	public Object caseInstAssign(InstAssign assign) {
		Var target = assign.getTarget().getVariable();
		if (target.getType().isBool()) {
			Expression expr = assign.getValue();
			if (expr.isBinaryExpr() || expr.isUnaryExpr()) {
				createIfNode(assign, target, expr);

				// saves the current block
				NodeBlock block = assign.getBlock();

				// deletes the assign
				EcoreHelper.delete(assign);

				// move the rest of instructions to a new block
				createNewBlock(block);
			}
		}

		return null;
	}

	@Override
	public Object caseInstReturn(InstReturn returnInstr) {
		if (procedure.getReturnType().isBool()) {
			Expression expr = returnInstr.getValue();
			if (expr.isBinaryExpr() || expr.isUnaryExpr()) {
				Var local = newVariable();
				returnInstr.setValue(IrFactory.eINSTANCE.createExprVar(local));
				createIfNode(returnInstr, local, expr);

				// moves this return and remaining instructions to a new block
				createNewBlock(returnInstr.getBlock());
			}
		}

		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		Var target = store.getTarget().getVariable();
		if (target.getType().isBool()) {
			Expression expr = store.getValue();
			if (expr.isBinaryExpr() || expr.isUnaryExpr()) {
				Var local = newVariable();
				store.setValue(IrFactory.eINSTANCE.createExprVar(local));
				createIfNode(store, local, expr);

				// moves this store and remaining instructions to a new block
				createNewBlock(store.getBlock());
			}
		}

		return null;
	}

	/**
	 * Creates an "if" node that assign <code>true</code> or <code>false</code>
	 * to <code>target</code> if the given expression is <code>true</code>,
	 * respectively <code>false</code>/
	 * 
	 * @param target
	 *            target local variable
	 * @param expr
	 *            an expression
	 */
	private void createIfNode(Instruction instruction, Var target,
			Expression expr) {
		NodeIf nodeIf = IrFactoryImpl.eINSTANCE.createNodeIf();
		nodeIf.setCondition(expr);
		nodeIf.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());

		// add "then" nodes
		NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		nodeIf.getThenNodes().add(block);
		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target,
				IrFactory.eINSTANCE.createExprBool(true));
		block.add(assign);

		// add "else" nodes
		block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		nodeIf.getElseNodes().add(block);
		assign = IrFactory.eINSTANCE.createInstAssign(target,
				IrFactory.eINSTANCE.createExprBool(false));
		block.add(assign);

		// increments index and adds the if after the current block
		indexNode++;
		List<Node> nodes = EcoreHelper.getContainingList(EcoreHelper
				.getContainerOfType(instruction, Node.class));
		nodes.add(indexNode, nodeIf);
	}

	/**
	 * Creates a new block node that will contain the remaining instructions of
	 * the block that is being visited. The new block is added after the NodeIf.
	 * 
	 * @param iit
	 *            list iterator
	 */
	private void createNewBlock(NodeBlock block) {
		// adds a new block after the if node created
		// the index is not incremented so the created block will be visited too
		NodeBlock targetBlock = IrFactoryImpl.eINSTANCE.createNodeBlock();
		List<Node> nodes = EcoreHelper.getContainingList(block);
		nodes.add(indexNode, targetBlock);

		// moves instructions
		List<Instruction> instructions = block.getInstructions();
		targetBlock.getInstructions().addAll(
				instructions.subList(indexInst, instructions.size()));
	}

	/**
	 * Returns a new boolean local variable.
	 * 
	 * @return a new boolean local variable
	 */
	private Var newVariable() {
		return procedure.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeBool(), "bool_expr");
	}

}
