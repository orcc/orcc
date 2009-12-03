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

import java.lang.reflect.Array;
import java.util.List;

import net.sf.orcc.interpreter.ListAllocator;
import net.sf.orcc.interpreter.NodeInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Store;
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

	private Action action;

	private ConfigurationAnalyzer analyzer;

	public PartialNodeInterpreter(String id, ConfigurationAnalyzer analyzer) {
		this.analyzer = analyzer;

		listAllocator = new ListAllocator();
		exprInterpreter = new PartialExpressionEvaluator();
	}

	/**
	 * Sets the configuration action that should be executed.
	 * 
	 * @param action
	 *            an action
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public void visit(HasTokens instr, Object... args) {
		// we always have tokens :-)
		instr.getTarget().setValue(true);
	}

	@Override
	public void visit(IfNode node, Object... args) {
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
	}

	@Override
	public void visit(Peek peek, Object... args) {
		if (peek.getPort().equals(analyzer.getConfigurationPort())) {
			int value = analyzer.getConfigurationValue(action);
			Object[] target = (Object[]) peek.getTarget().getValue();
			target[0] = value;
		}
	}

	@Override
	public void visit(Read read, Object... args) {
		if (read.getPort().equals(analyzer.getConfigurationPort())) {
			int value = analyzer.getConfigurationValue(action);
			Object[] target = (Object[]) read.getTarget().getValue();
			target[0] = value;
		}

		read.getPort().increaseTokensConsumption(read.getNumTokens());
	}

	@Override
	public void visit(Store instr, Object... args) {
		Variable variable = instr.getTarget().getVariable();
		if (instr.getIndexes().isEmpty()) {
			variable.setValue(instr.getValue().accept(exprInterpreter));
		} else {
			Object obj = variable.getValue();
			Object objPrev = obj;
			Integer lastIndex = 0;
			for (Expression index : instr.getIndexes()) {
				objPrev = obj;
				lastIndex = (Integer) index.accept(exprInterpreter);
				if (objPrev != null && lastIndex != null) {
					obj = Array.get(objPrev, lastIndex);
				}
			}

			if (objPrev != null && lastIndex != null) {
				Array.set(objPrev, lastIndex, instr.getValue().accept(
						exprInterpreter));
			}
		}
	}

	@Override
	public void visit(Write write, Object... args) {
		write.getPort().increaseTokensProduction(write.getNumTokens());
	}

}
