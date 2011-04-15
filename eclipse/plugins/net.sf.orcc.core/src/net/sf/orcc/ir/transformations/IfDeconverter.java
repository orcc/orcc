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
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class performs the inverse of if-conversion on the given procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IfDeconverter extends AbstractActorVisitor<Object> {

	private Predicate currentPredicate;

	private Node target;

	@Override
	public Object caseNodeBlock(NodeBlock block) {
		Procedure procedure = EcoreHelper.getContainerOfType(block,
				Procedure.class);

		List<Instruction> instructions = block.getInstructions();
		while (!instructions.isEmpty()) {
			Instruction inst = instructions.get(0);

			Predicate predicate = inst.getPredicate();
			NodeBlock targetBlock;
			if (predicate.isEmpty()) {
				if (currentPredicate.isEmpty()) {
					if (target == null) {
						// if target does not exist yet, create it
						target = IrFactory.eINSTANCE.createNodeBlock();
						procedure.getNodes().add(target);
					}
				} else {
					// end current if
					currentPredicate.getExpressions().clear();
					target = IrFactory.eINSTANCE.createNodeBlock();
					procedure.getNodes().add(target);
				}
				targetBlock = (NodeBlock) target;
			} else {
				if (currentPredicate.isEmpty()) {
					for (Expression condition : predicate.getExpressions()) {
						target = IrFactory.eINSTANCE.createNodeIf();
						((NodeIf) target).setCondition(condition);
						((NodeIf) target).setJoinNode(IrFactory.eINSTANCE
								.createNodeBlock());
						procedure.getNodes().add(target);

						currentPredicate.getExpressions().add(condition);
					}
				}

				inst.getPredicate().getExpressions().clear();
				targetBlock = procedure.getLast(((NodeIf) target)
						.getThenNodes());
			}

			((NodeBlock) targetBlock).getInstructions().add(inst);
		}

		EcoreUtil.remove(block);

		return NULL;
	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {
		throw new OrccRuntimeException("unsupported NodeWhile");
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		target = null;
		currentPredicate = IrFactory.eINSTANCE.createPredicate();

		doSwitch(procedure.getNodes().get(0));

		return NULL;
	}

}
