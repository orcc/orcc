/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.ir.util;

import java.util.List;

import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * This abstract class implements a no-op visitor on IR procedures, nodes,
 * instructions, and (if visitFull is <code>true</code>) expressions. This class
 * should be extended by classes that implement intra-procedural IR visitors and
 * transformations.
 * 
 * @author Matthieu Wipliez
 * @since 1.2
 */
public abstract class AbstractIrVisitor<T> extends IrSwitch<T> {

	protected int indexInst;

	protected int indexNode;

	/**
	 * current procedure being visited
	 */
	protected Procedure procedure;

	private final boolean visitFull;

	/**
	 * Creates a new abstract IR visitor that visits all nodes and instructions.
	 */
	public AbstractIrVisitor() {
		visitFull = false;
	}

	/**
	 * Creates a new abstract actor visitor that visits all nodes and
	 * instructions, and may also visit all the expressions if
	 * <code>visitFull</code> is <code>true</code>.
	 * 
	 * @param visitFull
	 *            when <code>true</code>, visits all the expressions
	 */
	public AbstractIrVisitor(boolean visitFull) {
		this.visitFull = visitFull;
	}

	@Override
	public T caseArgByVal(ArgByVal arg) {
		doSwitch(arg.getValue());
		return null;
	}

	@Override
	public T caseBlockBasic(BlockBasic block) {
		return visitInstructions(block.getInstructions());
	}

	@Override
	public T caseBlockIf(BlockIf nodeIf) {
		if (visitFull) {
			doSwitch(nodeIf.getCondition());
		}

		doSwitch(nodeIf.getThenBlocks());
		doSwitch(nodeIf.getElseBlocks());
		doSwitch(nodeIf.getJoinBlock());
		return null;
	}

	@Override
	public T caseBlockWhile(BlockWhile nodeWhile) {
		if (visitFull) {
			doSwitch(nodeWhile.getCondition());
		}

		doSwitch(nodeWhile.getBlocks());
		doSwitch(nodeWhile.getJoinBlock());
		return null;
	}

	@Override
	public T caseExprBinary(ExprBinary expr) {
		doSwitch(expr.getE1());
		doSwitch(expr.getE2());
		return null;
	}

	@Override
	public T caseExprUnary(ExprUnary expr) {
		doSwitch(expr.getExpr());
		return null;
	}

	@Override
	public T caseInstAssign(InstAssign assign) {
		if (visitFull) {
			doSwitch(assign.getValue());
		}
		return null;
	}

	@Override
	public T caseInstCall(InstCall call) {
		if (visitFull) {
			for (Arg arg : call.getParameters()) {
				doSwitch(arg);
			}
		}
		return null;
	}

	@Override
	public T caseInstLoad(InstLoad load) {
		if (visitFull) {
			for (Expression expr : load.getIndexes()) {
				doSwitch(expr);
			}
		}
		return null;
	}

	@Override
	public T caseInstPhi(InstPhi phi) {
		if (visitFull) {
			for (Expression expr : phi.getValues()) {
				doSwitch(expr);
			}
		}
		return null;
	}

	@Override
	public T caseInstReturn(InstReturn returnInstr) {
		if (visitFull) {
			Expression expr = returnInstr.getValue();
			if (expr != null) {
				doSwitch(expr);
			}
		}
		return null;
	}

	@Override
	public T caseInstStore(InstStore store) {
		if (visitFull) {
			for (Expression expr : store.getIndexes()) {
				doSwitch(expr);
			}

			doSwitch(store.getValue());
		}
		return null;
	}

	@Override
	public T caseProcedure(Procedure procedure) {
		this.procedure = procedure;
		return doSwitch(procedure.getBlocks());
	}

	@Override
	public final T doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}
		return doSwitch(eObject.eClass(), eObject);
	}

	/**
	 * Visits each block of the given block list.
	 * 
	 * @param blocks
	 *            a list of blocks that belong to a procedure
	 */
	public T doSwitch(List<Block> blocks) {
		return visitBlocks(blocks);
	}

	@Override
	public boolean isSwitchFor(EPackage ePackage) {
		// just so we can use it in DfVisitor
		return super.isSwitchFor(ePackage);
	}

	/**
	 * Visits each block of the given block list.
	 * 
	 * @param blocks
	 *            a list of blocks that belong to a procedure
	 */
	public T visitBlocks(List<Block> blocks) {
		int oldIndexNode = indexNode;
		T result = null;
		for (indexNode = 0; indexNode < blocks.size() && result == null; indexNode++) {
			Block node = blocks.get(indexNode);
			result = doSwitch(node);
		}

		indexNode = oldIndexNode;
		return result;
	}

	/**
	 * Visits the given list of instructions.
	 * 
	 * @param instructions
	 *            a list of instructions
	 * @return a result
	 */
	public T visitInstructions(List<Instruction> instructions) {
		int oldIndexInst = indexInst;
		T result = null;
		for (indexInst = 0; indexInst < instructions.size() && result == null; indexInst++) {
			Instruction inst = instructions.get(indexInst);
			result = doSwitch(inst);
		}

		// restore old index
		indexInst = oldIndexInst;
		return result;
	}

}
