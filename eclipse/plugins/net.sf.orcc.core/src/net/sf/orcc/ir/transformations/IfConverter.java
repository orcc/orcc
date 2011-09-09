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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class performs if-conversion on the given procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IfConverter extends AbstractActorVisitor<Object> {

	private Predicate currentPredicate;

	private NodeBlock targetBlock;

	@Override
	public Object caseNodeBlock(NodeBlock block) {
		List<Instruction> instructions = block.getInstructions();
		// annotate with predicate
		for (Instruction instruction : instructions) {
			instruction.setPredicate(IrUtil.copy(currentPredicate));
		}

		// move to target block
		targetBlock.getInstructions().addAll(instructions);

		// remove this block
		EcoreUtil.remove(block);
		indexNode--;

		return null;
	}

	@Override
	public Object caseNodeIf(NodeIf nodeIf) {
		Predicate previousPredicate = currentPredicate;

		// predicate for "then" branch
		currentPredicate = IrUtil.copy(previousPredicate);
		currentPredicate.getExpressions().add(
				IrUtil.copy(nodeIf.getCondition()));
		doSwitch(nodeIf.getThenNodes());
		IrUtil.delete(currentPredicate);

		// predicate for "else" branch
		currentPredicate = IrUtil.copy(previousPredicate);
		currentPredicate.getExpressions().add(
				IrFactory.eINSTANCE.createExprUnary(OpUnary.LOGIC_NOT,
						IrUtil.copy(nodeIf.getCondition()),
						IrFactory.eINSTANCE.createTypeBool()));
		doSwitch(nodeIf.getElseNodes());
		IrUtil.delete(currentPredicate);

		// restore predicate for "join" node
		currentPredicate = previousPredicate;
		doSwitch(nodeIf.getJoinNode());

		// deletes condition and node
		IrUtil.delete(nodeIf.getCondition());
		EcoreUtil.remove(nodeIf);

		return null;
	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {
		throw new OrccRuntimeException("unsupported NodeWhile");
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		// do not perform if-conversion if procedure contains whiles
		if (EcoreHelper.getObjects(procedure, NodeWhile.class).iterator()
				.hasNext()) {
			return null;
		}

		// now we can safely perform if-conversion
		currentPredicate = IrFactory.eINSTANCE.createPredicate();
		targetBlock = IrFactory.eINSTANCE.createNodeBlock();

		super.caseProcedure(procedure);
		procedure.getNodes().add(targetBlock);
		return null;
	}

}
