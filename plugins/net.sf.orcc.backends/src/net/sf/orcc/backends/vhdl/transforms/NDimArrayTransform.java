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

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class defines an actor transformation that transforms indexes
 * assignments from array(index_0, index_1, index_2) to array array(index) with
 * index = index_0 & index_1 & index_2.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class NDimArrayTransform extends AbstractActorTransformation {

	private String file;

	/**
	 * an iterator on the current node. Initiated by
	 * {@link #visit(BlockNode, Object...)}.
	 */
	private ListIterator<CFGNode> nodeIt;

	/**
	 * Creates an "Assign" node that assign the index of a 1D array to index_0,
	 * index_1, ..., index_N of a ND array.
	 * 
	 * @param target
	 *            target local variable
	 * @param expr
	 *            an expression
	 */
	private void createAssignNode(LocalVariable target, Expression expr) {
		Assign inst = new Assign(expr.getLocation(), target, expr);

		// add assign node
		BlockNode block = new BlockNode(procedure);
		Assign assign = new Assign(target, expr);
		block.add(assign);

		nodeIt.add((CFGNode) inst);
	}

	/**
	 * Creates a new block node that will contain the remaining instructions of
	 * the block that is being visited. The new block is added after the
	 * Assignnode.
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

		// adds this block after the Assignnode
		nodeIt.add(block);

		// moves the iterator back so the new block will be visited next
		// nodeIt.previous();
	}

	@Override
	public void transform(Actor actor) {
		this.file = actor.getFile();
		super.transform(actor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(BlockNode node, Object... args) {
		nodeIt = (ListIterator<CFGNode>) args[0];
		ListIterator<Instruction> it = node.listIterator();
		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Load load, Object... args) {
		Expression exprtype = (Expression) load.getSource();
		if (exprtype.isBinaryExpr() || exprtype.isUnaryExpr()) {
			List<Expression> indexes = load.getIndexes();
			if (load.getTarget().isGlobal()) {
				int size = indexes.size();
				// while (size != 0) {
				size--;
				LocalVariable local = procedure.newTempLocalVariable(file,
						IrFactory.eINSTANCE.createTypeBool(), "index");
				procedure.getLocals().put(local.getName(), local);
				createAssignNode(local, indexes.get(size));

				// remove uses of the given indexes
				Use.removeUses(load, indexes);

				// remove indexes from load
				indexes.clear();

				// add index to indexes
				indexes.add(new VarExpr(new Use(local, load)));

				// }
				// moves this store and remaining instructions to a new block
				ListIterator<Instruction> iit = (ListIterator<Instruction>) args[0];
				iit.previous();
				createNewBlock(iit);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Store store, Object... args) {
		Expression exprtype = store.getValue();
		if (exprtype.isBinaryExpr() || exprtype.isUnaryExpr()) {
			List<Expression> expr = store.getIndexes();
			if (store.getTarget().isGlobal()) {
				int size = expr.size();
				// while (size != 0) {
				size--;
				LocalVariable local = procedure.newTempLocalVariable(file,
						IrFactory.eINSTANCE.createTypeBool(), "index");
				procedure.getLocals().put(local.getName(), local);
				store.setValue(new VarExpr(new Use(local)));
				createAssignNode(local, expr.get(size));
				// }
				// moves this store and remaining instructions to a new block
				ListIterator<Instruction> iit = (ListIterator<Instruction>) args[0];
				iit.previous();
				createNewBlock(iit);
			}
		}
	}

}
