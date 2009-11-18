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
package net.sf.orcc.backends.interpreter;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.ICommunicationFifo;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InitPort;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.ReadEnd;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.instructions.WriteEnd;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;

/**
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class NodeInterpreter implements InstructionVisitor, NodeVisitor {

	// private String actorName;
	private ExpressionEvaluator exprInterpreter;
	private Object return_value;
	private boolean block_return = false;

	public NodeInterpreter(String id) {
		// this.actorName = id;
		this.exprInterpreter = new ExpressionEvaluator();
	}

	public Object getReturnValue() {
		if (block_return) {
			return return_value;
		} else {
			return null;
		}
	}

	/**
	 * CFG nodes visitors
	 */

	@Override
	public Object visit(IfNode node, Object... args) {
		/* Interpret first expression ("if" condition) */
		// Expression expr = exprInterpreter.interpret(node.getValue());
		Object condition = node.getValue().accept(exprInterpreter);

		/* if (condition si true) then */
		if ((Boolean) condition) {
			for (CFGNode subNode : node.getThenNodes()) {
				subNode.accept(this, args);
			}
			/* else */
		} else {
			List<CFGNode> elseNodes = node.getElseNodes();
			if (elseNodes.size() != 1) {
				for (CFGNode subNode : elseNodes) {
					subNode.accept(this, args);
				}
			}
		}
		node.getJoinNode().accept(this, args);
		return null;
	}

	@Override
	public Object visit(WhileNode node, Object... args) {
		/* Interpret first expression ("while" condition) */
		// Expression expr = exprInterpreter.interpret(node.getValue());
		Object condition = node.getValue().accept(exprInterpreter);

		/* while (condition is true) do */
		while ((Boolean) condition) {
			/* control flow sub-statements */
			for (CFGNode subNode : node.getNodes()) {
				subNode.accept(this, args);
			}
			/* Interpret next value of "while" condition */
			// expr = exprInterpreter.interpret(node.getValue());
			condition = node.getValue().accept(exprInterpreter);
		}
		return null;
	}

	@Override
	public Object visit(BlockNode node, Object... args) {
		Iterator<Instruction> it = node.iterator();

		while (it.hasNext()) {
			Instruction instr = it.next();
			instr.accept(this, args);
		}
		return null;
	}

	/**
	 * Instructions visitors
	 */

	@Override
	public void visit(InitPort instr, Object... args) {
		// Nothing TODO ?
	}

	@Override
	public void visit(HasTokens instr, Object... args) {
		ICommunicationFifo fifo = instr.getPort().fifo();
		boolean hasTok = fifo.hasTokens(instr.getNumTokens());

		instr.getTarget().setValue(hasTok);
	}

	@Override
	public void visit(Peek instr, Object... args) {
		int[] target = (int[]) instr.getTarget().getValue();

		ICommunicationFifo fifo = instr.getPort().fifo();
		fifo.peek(target);
	}

	@Override
	public void visit(Read instr, Object... args) {
		int[] target = (int[]) instr.getTarget().getValue();

		ICommunicationFifo fifo = instr.getPort().fifo();
		fifo.get(target);
	}

	@Override
	public void visit(ReadEnd instr, Object... args) {
		// Nothing to do
	}

	@Override
	public void visit(Write instr, Object... args) {
		int[] target = (int[]) instr.getTarget().getValue();

		ICommunicationFifo fifo = instr.getPort().fifo();
		fifo.put(target);
	}

	@Override
	public void visit(WriteEnd instr, Object... args) {
		// Nothing to do
	}

	@Override
	public void visit(Assign instr, Object... args) {
		LocalVariable target = instr.getTarget();
		target.setValue(instr.getValue().accept(exprInterpreter));
	}

	@Override
	public void visit(PhiAssignment instr, Object... args) {
		LocalVariable target = instr.getTarget();
		for (Use use : instr.getVars()) {
			Variable var = use.getVariable();
			target.setValue(var.getExpression().accept(exprInterpreter));
		}
	}

	@Override
	public void visit(Load instr, Object... args) {
		LocalVariable target = instr.getTarget();
		Variable source = instr.getSource().getVariable();
		target.setValue(source.getValue());
	}

	@Override
	public void visit(Store instr, Object... args) {
		Variable variable = instr.getTarget().getVariable();
		variable.setValue(instr.getValue().accept(exprInterpreter));
	}

	@Override
	public void visit(Call instr, Object... args) {
		for (CFGNode node : instr.getProcedure().getNodes()) {
			node.accept(this);
		}

		if (instr.hasResult()) {
			instr.getTarget().setValue(return_value);
		}
	}

	@Override
	public void visit(Return instr, Object... args) {
		this.return_value = instr.getValue().accept(exprInterpreter);
		this.block_return = true;
	}

	@Override
	public void visit(SpecificInstruction instr, Object... args) {
		// Nothing to do
	}

}
