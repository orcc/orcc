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

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.ICommunicationFifo;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
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
 * This class defines a node interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class NodeInterpreter implements InstructionVisitor, NodeVisitor {

	private boolean blockReturn;

	protected ExpressionEvaluator exprInterpreter;

	protected ListAllocator listAllocator;

	private Object returnValue;

	public NodeInterpreter(String id) {
		// Create the List allocator for called procedure local vars
		listAllocator = new ListAllocator();

		// Create the expression evaluator
		this.exprInterpreter = new ExpressionEvaluator();
	}

	/**
	 * Creates a new node interpreter without setting its list allocator nor its
	 * expression interpreter.
	 */
	protected NodeInterpreter() {
	}

	public Object getReturnValue() {
		if (blockReturn) {
			return returnValue;
		} else {
			return null;
		}
	}

	@Override
	public void visit(Assign instr, Object... args) {
		LocalVariable target = instr.getTarget();
		target.setValue(instr.getValue().accept(exprInterpreter));
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

	@Override
	public void visit(Call instr, Object... args) {
		// Get called procedure
		Procedure proc = instr.getProcedure();

		// Set the input parameters of the called procedure if any
		List<Expression> callParams = instr.getParameters();
		List<Variable> procParams = proc.getParameters().getList();
		for (int i = 0; i < callParams.size(); i++) {
			Variable procVar = procParams.get(i);
			procVar.setValue(callParams.get(i).accept(exprInterpreter));
		}

		// Allocate procedure local List variables
		for (Variable local : proc.getLocals()) {
			Type type = local.getType();
			if (type.getType() == Type.LIST) {
				local.setValue(listAllocator.allocate(type));
			}
		}

		// Interpret procedure body
		for (CFGNode node : proc.getNodes()) {
			node.accept(this);
		}

		// Get procedure result if any
		if (instr.hasResult()) {
			instr.getTarget().setValue(returnValue);
		}
	}

	@Override
	public void visit(HasTokens instr, Object... args) {
		ICommunicationFifo fifo = instr.getPort().fifo();
		boolean hasTok = fifo.hasTokens(instr.getNumTokens());

		instr.getTarget().setValue(hasTok);
	}

	/**
	 * CFG nodes visitors
	 */

	@Override
	public Object visit(IfNode node, Object... args) {
		/* Interpret first expression ("if" condition) */
		Object condition = node.getValue().accept(exprInterpreter);

		/* if (condition is true) then */
		if ((Boolean) condition) {
			for (CFGNode subNode : node.getThenNodes()) {
				subNode.accept(this, args);
			}
			/* else */
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

	// private int[] toIntArray(List<Integer> integerList) {
	// int[] intArray = new int[integerList.size()];
	// for (int i = 0; i < integerList.size(); i++) {
	// intArray[i] = integerList.get(i);
	// }
	// return intArray;
	// }

	@Override
	public void visit(InitPort instr, Object... args) {
		// Nothing TODO ?
	}

	@Override
	public void visit(Load instr, Object... args) {
		LocalVariable target = instr.getTarget();
		Variable source = instr.getSource().getVariable();
		if (instr.getIndexes().isEmpty()) {
			target.setValue(source.getValue());
		} else {
			Object obj = source.getValue();
			for (Expression index : instr.getIndexes()) {
				obj = Array.get(obj, (Integer) index.accept(exprInterpreter));
			}
			target.setValue(obj);
		}
	}

	@Override
	public void visit(Peek instr, Object... args) {
		Object[] target = (Object[]) (instr.getTarget().getValue());

		ICommunicationFifo fifo = instr.getPort().fifo();
		fifo.peek(target);
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
	public void visit(Read instr, Object... args) {
		Object[] target = (Object[]) instr.getTarget().getValue();

		ICommunicationFifo fifo = instr.getPort().fifo();
		fifo.get(target);
	}

	@Override
	public void visit(ReadEnd instr, Object... args) {
		// Nothing to do
	}

	@Override
	public void visit(Return instr, Object... args) {
		if (instr.getValue() != null) {
			this.returnValue = instr.getValue().accept(exprInterpreter);
			this.blockReturn = true;
		}
	}

	@Override
	public void visit(SpecificInstruction instr, Object... args) {
		throw new OrccRuntimeException("does not know how to interpret a "
				+ "specific instruction");
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
				obj = Array.get(objPrev, lastIndex);
			}
			Array.set(objPrev, lastIndex, instr.getValue().accept(
					exprInterpreter));
		}
	}

	@Override
	public Object visit(WhileNode node, Object... args) {
		// Interpret first expression ("while" condition)
		Object condition = node.getValue().accept(exprInterpreter);
		// while (condition is true) do
		while ((Boolean) condition) {
			// control flow sub-statements
			for (CFGNode subNode : node.getNodes()) {
				subNode.accept(this, args);
			}
			// Interpret next value of "while" condition
			condition = node.getValue().accept(exprInterpreter);
		}
		return null;
	}

	@Override
	public void visit(Write instr, Object... args) {
		Object[] target = (Object[]) instr.getTarget().getValue();

		ICommunicationFifo fifo = instr.getPort().fifo();
		fifo.put(target);
	}

	@Override
	public void visit(WriteEnd instr, Object... args) {
		// Nothing to do
	}

}
