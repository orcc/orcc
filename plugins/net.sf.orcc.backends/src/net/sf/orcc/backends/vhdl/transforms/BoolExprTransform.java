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
package net.sf.orcc.backends.vhdl.transforms;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class defines an actor transformation that transforms assignments whose
 * right hand side is a boolean expression to if nodes. Note: this
 * transformation must be called after PhiRemoval because it generates non-SSA
 * code.
 * 
 * <p>
 * The algorithm works as follows: Considering a block with instructions [i1,
 * i2, ..., ii, ..., in] where the instruction <code>ii</code> is an assign or a
 * store whose value is a boolean binary/unary expression, then create an IfNode
 * after the current block that assigns <code>true</code> to the target if
 * <code>true</code>, and assigns <code>false</code> otherwise.
 * </p>
 * 
 * <p>
 * The remaining instructions <code>i(i+1)</code> to <code>in</code> are moved
 * to a new block created after the newly-created IfNode. The
 * <code>previous</code> method is called on the node iterator so that the new
 * block is to be visited next.
 * </p>
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class BoolExprTransform extends AbstractActorTransformation {

	private int tempVarCount;

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
	private void createIfNode(LocalVariable target, Expression expr) {
		List<CFGNode> thenNodes = new ArrayList<CFGNode>();
		List<CFGNode> elseNodes = new ArrayList<CFGNode>();
		IfNode node = new IfNode(procedure, expr, thenNodes, elseNodes,
				new BlockNode(procedure));

		// add "then" nodes
		BlockNode block = new BlockNode(procedure);
		thenNodes.add(block);
		Assign assign = new Assign(target, new BoolExpr(true));
		block.add(assign);

		// add "else" nodes
		block = new BlockNode(procedure);
		elseNodes.add(block);
		assign = new Assign(target, new BoolExpr(false));
		block.add(assign);

		nodeIterator.add(node);
	}

	/**
	 * Creates a new block node that will contain the remaining instructions of
	 * the block that is being visited. The new block is added after the IfNode.
	 * 
	 * @param iit
	 *            list iterator
	 */
	private void createNewBlock(ListIterator<Instruction> iit) {
		BlockNode block = new BlockNode(procedure);
		while (iit.hasNext()) {
			Instruction instruction = iit.next();
			iit.remove();
			block.add(instruction);
		}

		// adds this block after the IfNode
		nodeIterator.add(block);

		// moves the iterator back so the new block will be visited next
		nodeIterator.previous();
	}

	/**
	 * Returns a new boolean local variable.
	 * 
	 * @return a new boolean local variable
	 */
	private LocalVariable newVariable() {
		return new LocalVariable(true, tempVarCount++, new Location(),
				"bool_expr", IrFactory.eINSTANCE.createTypeBool());
	}

	@Override
	public void visit(Assign assign) {
		LocalVariable target = assign.getTarget();
		if (target.getType().isBool()) {
			Expression expr = assign.getValue();
			if (expr.isBinaryExpr() || expr.isUnaryExpr()) {
				createIfNode(target, expr);

				// removes this assign and moves remaining instructions to a new
				// block
				instructionIterator.previous();
				instructionIterator.remove();
				createNewBlock(instructionIterator);
			}
		}
	}

	@Override
	public void visit(Return returnInstr) {
		if (procedure.getReturnType().isBool()) {
			Expression expr = returnInstr.getValue();
			if (expr.isBinaryExpr() || expr.isUnaryExpr()) {
				LocalVariable local = newVariable();
				procedure.getLocals().put(local.getName(), local);
				returnInstr.setValue(new VarExpr(new Use(local)));
				createIfNode(local, expr);

				// moves this return and remaining instructions to a new block
				instructionIterator.previous();
				createNewBlock(instructionIterator);
			}
		}
	}

	@Override
	public void visit(Store store) {
		Variable target = store.getTarget();
		if (target.getType().isBool()) {
			Expression expr = store.getValue();
			if (expr.isBinaryExpr() || expr.isUnaryExpr()) {
				LocalVariable local = newVariable();
				procedure.getLocals().put(local.getName(), local);
				store.setValue(new VarExpr(new Use(local)));
				createIfNode(local, expr);

				// moves this store and remaining instructions to a new block
				instructionIterator.previous();
				createNewBlock(instructionIterator);
			}
		}
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		tempVarCount = 1;
		super.visitProcedure(procedure);
	}

}
