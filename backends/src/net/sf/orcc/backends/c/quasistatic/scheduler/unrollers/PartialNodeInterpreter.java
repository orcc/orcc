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
package net.sf.orcc.backends.c.quasistatic.scheduler.unrollers;

import java.util.List;

import net.sf.orcc.backends.interpreter.ListAllocator;
import net.sf.orcc.backends.interpreter.NodeInterpreter;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.IfNode;

/**
 * This class defines a partial node/instruction interpreter. It refines the
 * existing interpreter by not relying on anything that is data-dependent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PartialNodeInterpreter extends NodeInterpreter {

	public PartialNodeInterpreter(String id) {
		listAllocator = new ListAllocator();
		exprInterpreter = new PartialExpressionEvaluator();
	}

	@Override
	public void visit(HasTokens instr, Object... args) {
		// we always have tokens :-)
		instr.getTarget().setValue(true);
	}

	@Override
	public Object visit(IfNode node, Object... args) {
		// Interpret first expression ("if" condition)
		Object condition = node.getValue().accept(exprInterpreter);

		if (condition instanceof Boolean && (Boolean) condition) {
			for (CFGNode subNode : node.getThenNodes()) {
				subNode.accept(this, args);
			}
		} else {
			List<CFGNode> elseNodes = node.getElseNodes();
			if (!elseNodes.isEmpty()) {
				for (CFGNode subNode : elseNodes) {
					subNode.accept(this, args);
				}
			}
		}
		node.getJoinNode().accept(this, args);

		return null;
	}

	@Override
	public void visit(Peek instr, Object... args) {
	}

	@Override
	public void visit(Read read, Object... args) {
		read.getPort().increaseTokensConsumption(read.getNumTokens());
	}

	@Override
	public void visit(Write write, Object... args) {
		write.getPort().increaseTokensProduction(write.getNumTokens());
	}

}
