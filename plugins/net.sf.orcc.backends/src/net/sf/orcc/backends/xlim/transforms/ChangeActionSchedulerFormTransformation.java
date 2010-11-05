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
package net.sf.orcc.backends.xlim.transforms;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * 
 * This class defines a transformation that transforms the 'ActionScheduler'
 * function in order to use XLIM back-end with Ericsson OpenDF runtime
 * 
 * @author Herv� Yviquel
 * 
 */
public class ChangeActionSchedulerFormTransformation extends
		AbstractActorTransformation {

	@Override
	public void transform(Actor actor) throws OrccException {

		for (Action action : actor.getActions()) {
			List<CFGNode> schedulerNodes = action.getScheduler().getNodes();
			List<IfNode> ifNodes = new ArrayList<IfNode>();

			// Search for guard computing
			for (CFGNode node : schedulerNodes) {
				if (node instanceof IfNode) {
					ifNodes.add((IfNode) node);
				}
			}

			for (IfNode ifNode : ifNodes) {
				schedulerNodes.remove(ifNode);

				PhiAssignment phi = (PhiAssignment) ifNode.getJoinNode()
						.getInstructions().get(0);

				// Creation of instruction 'result=hasToken(input) AND guard'
				Expression guard = phi.getValues().get(0);
				Expression and = new BinaryExpr(ifNode.getValue(),
						BinaryOp.LOGIC_AND, guard,
						IrFactory.eINSTANCE.createTypeBool());
				Instruction assignment = new Assign(phi.getTarget(), and);

				schedulerNodes.addAll(ifNode.getThenNodes());

				BlockNode blockNode = new BlockNode(action.getScheduler());
				blockNode.add(assignment);
				blockNode.add(ifNode.getJoinNode().getInstructions().get(1));
				schedulerNodes.add(blockNode);
			}
		}
	}

}
