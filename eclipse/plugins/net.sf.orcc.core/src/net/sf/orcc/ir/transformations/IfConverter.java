/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir.transformations;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class performs if-conversion on the given procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IfConverter extends AbstractActorVisitor {

	private Expression currentPredicate;

	private NodeBlock targetBlock;

	@Override
	public Object caseNodeBlock(NodeBlock block) {
		List<Instruction> instructions = block.getInstructions();
		while (!instructions.isEmpty()) {
			Instruction inst = instructions.get(0);

			// annotate with predicate
			inst.setPredicate(currentPredicate);

			// move to target block
			targetBlock.add(inst);
		}

		// remove this block
		EcoreUtil.remove(block);

		return NULL;
	}

	@Override
	public Object caseNodeIf(NodeIf nodeIf) {
		Expression previousPredicate = currentPredicate;
		if (currentPredicate == null) {
			currentPredicate = EcoreHelper.copyWithUses(nodeIf.getCondition());
		} else {
			currentPredicate = IrFactory.eINSTANCE.createExprBinary(
					EcoreHelper.copyWithUses(currentPredicate),
					OpBinary.LOGIC_AND,
					EcoreHelper.copyWithUses(nodeIf.getCondition()),
					IrFactory.eINSTANCE.createTypeBool());
		}

		doSwitch(nodeIf.getThenNodes());
		doSwitch(nodeIf.getElseNodes());

		currentPredicate = previousPredicate;
		doSwitch(nodeIf.getJoinNode());

		EcoreHelper.delete(nodeIf.getCondition());
		EcoreUtil.remove(nodeIf);

		return NULL;
	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {
		doSwitch(nodeWhile.getNodes());
		doSwitch(nodeWhile.getJoinNode());

		EcoreUtil.remove(nodeWhile);

		return NULL;
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		targetBlock = IrFactory.eINSTANCE.createNodeBlock();
		super.caseProcedure(procedure);
		procedure.getNodes().add(targetBlock);
		return NULL;
	}

	@Override
	public Object doSwitch(List<Node> nodes) {
		while (!nodes.isEmpty()) {
			doSwitch(nodes.get(0));
		}

		return NULL;
	}

}
