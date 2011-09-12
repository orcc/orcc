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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
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
 * This class performs the inverse of if-conversion on the given procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IfDeconverter extends AbstractActorVisitor<Object> {

	private Predicate currentPredicate;

	private List<NodeIf> nodeIfList;

	@Override
	public Object caseNodeBlock(NodeBlock block) {
		Procedure procedure = EcoreHelper.getContainerOfType(block,
				Procedure.class);
		NodeBlock targetBlock = null;

		List<Instruction> instructions = block.getInstructions();
		while (!instructions.isEmpty()) {
			Instruction inst = instructions.get(0);

			Predicate predicate = inst.getPredicate();
			if (!predicate.isSameAs(currentPredicate)) {
				// deletes the current predicate
				if (currentPredicate != null) {
					IrUtil.delete(currentPredicate);
				}

				// updates the target block
				targetBlock = updateTargetBlock(procedure, predicate);
			}

			// deletes the predicate of the instruction
			IrUtil.delete(predicate);

			// moves the instruction
			targetBlock.getInstructions().add(inst);
		}

		// deletes the current predicate
		if (currentPredicate != null) {
			IrUtil.delete(currentPredicate);
		}

		// remove this block
		EcoreUtil.remove(block);

		return null;
	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {
		throw new OrccRuntimeException("unsupported NodeWhile");
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		// do not perform if-deconversion if procedure contains whiles
		if (EcoreHelper.getObjects(procedure, NodeWhile.class).iterator()
				.hasNext()) {
			return null;
		}

		nodeIfList = new ArrayList<NodeIf>();

		// initialized to "null" so that the first empty predicate will create
		// an unconditional block
		currentPredicate = null;

		List<Node> nodes = procedure.getNodes();
		if (!nodes.isEmpty()) {
			doSwitch(nodes.get(0));
		}

		return null;
	}

	/**
	 * Returns the list of nodes that matches the given condition (if any).
	 * 
	 * @param parentNodes
	 *            list of nodes to which the "if" we're looking for belongs
	 * @param condition
	 *            a condition
	 * @return the list of nodes that matches the given condition, or
	 *         <code>null</code>
	 */
	private List<Node> findNodes(List<Node> parentNodes, Expression condition) {
		for (NodeIf nodeIf : nodeIfList) {
			List<Node> nodes = EcoreHelper.getContainingList(nodeIf);
			if (EcoreUtil.equals(condition, nodeIf.getCondition())
					&& parentNodes == nodes) {
				return nodeIf.getThenNodes();
			} else {
				if (condition.isUnaryExpr()) {
					ExprUnary unary = (ExprUnary) condition;
					Expression expr = unary.getExpr();

					if (unary.getOp() == OpUnary.LOGIC_NOT
							&& EcoreUtil.equals(expr, nodeIf.getCondition())
							&& parentNodes == nodes) {
						return nodeIf.getElseNodes();
					}
				}
			}
		}

		return null;
	}

	/**
	 * Updates targetBlock according to the given predicate.
	 * 
	 * @param procedure
	 *            the current procedure
	 * @param predicate
	 *            a predicate
	 */
	private NodeBlock updateTargetBlock(Procedure procedure, Predicate predicate) {
		currentPredicate = IrFactory.eINSTANCE.createPredicate();
		List<Node> parentNodes = procedure.getNodes();

		if (predicate.isEmpty()) {
			// unconditioned predicate => forgets all ifs
			nodeIfList.clear();

			// creates a new block
			NodeBlock block = IrFactory.eINSTANCE.createNodeBlock();
			procedure.getNodes().add(block);
			return block;
		} else {
			for (Expression condition : predicate.getExpressions()) {
				List<Node> nodes = findNodes(parentNodes, condition);
				if (nodes == null) {
					// create a new if
					NodeIf nodeIf = IrFactory.eINSTANCE.createNodeIf();
					nodeIf.setCondition(IrUtil.copy(condition));
					nodeIf.setJoinNode(IrFactory.eINSTANCE.createNodeBlock());
					nodeIfList.add(nodeIf);
					parentNodes.add(nodeIf);

					// use the nodes of the "then" branch
					parentNodes = nodeIf.getThenNodes();
				} else {
					parentNodes = nodes;
				}

				currentPredicate.add(IrUtil.copy(condition));
			}

			return IrUtil.getLast(parentNodes);
		}
	}

}
