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
package net.sf.orcc.interpreter;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
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

	/**
	 * Creates a new node interpreter that has a list allocator and uses the
	 * {@link ExpressionEvaluator} class to evaluate expressions.
	 */
	public NodeInterpreter() {
		// Create the List allocator for called procedure local vars
		listAllocator = new ListAllocator();

		// Create the expression evaluator
		this.exprInterpreter = new ExpressionEvaluator();
	}

	/**
	 * Returns the value returned by the last procedure interpreted, or
	 * <code>null</code>.
	 * 
	 * @return the value returned by the last procedure interpreted
	 */
	public Object getReturnValue() {
		if (blockReturn) {
			return returnValue;
		} else {
			return null;
		}
	}

	/**
	 * Returns a new string that is an unescaped version of the given string.
	 * Unespaced means that "\\\\", "\\n", "\\r", "\\t" are replaced by '\\',
	 * '\n', '\r', '\t' respectively.
	 * 
	 * @param string
	 *            a string
	 * @return a new string that is an unescaped version of the given string
	 */
	private String getUnescapedString(String string) {
		StringBuilder builder = new StringBuilder(string.length());
		boolean escape = false;
		for (int i = 0; i < string.length(); i++) {
			char chr = string.charAt(i);
			if (escape) {
				switch (chr) {
				case '\\':
					builder.append('\\');
					break;
				case 'n':
					builder.append('\n');
					break;
				case 'r':
					builder.append('\r');
					break;
				case 't':
					builder.append('\t');
					break;
				default:
					// we could throw an exception here
					builder.append(chr);
					break;
				}
				escape = false;
			} else {
				if (chr == '\\') {
					escape = true;
				} else {
					builder.append(chr);
				}
			}
		}

		return builder.toString();
	}

	@Override
	public void visit(Assign instr, Object... args) {
		LocalVariable target = instr.getTarget();
		target.setValue(instr.getValue().accept(exprInterpreter));
	}

	@Override
	public void visit(BlockNode node, Object... args) {
		Iterator<Instruction> it = node.iterator();

		while (it.hasNext()) {
			Instruction instr = it.next();
			instr.accept(this, args);
		}
	}

	@Override
	public void visit(Call call, Object... args) {
		// Get called procedure
		Procedure proc = call.getProcedure();

		// Set the input parameters of the called procedure if any
		List<Expression> callParams = call.getParameters();

		// Special "print" case
		if (call.isPrint()) {
			OrccProcess process = (OrccProcess) args[1];

			for (int i = 0; i < callParams.size(); i++) {
				if (callParams.get(i).isStringExpr()) {
					// String characters rework for escaped control
					// management
					String str = ((StringExpr) callParams.get(i)).getValue();
					String unescaped = getUnescapedString(str);
					process.write(unescaped);
				} else {
					Object value = callParams.get(i).accept(exprInterpreter);
					process.write(String.valueOf(value));
				}
			}
		} else {
			List<Variable> procParams = proc.getParameters().getList();
			for (int i = 0; i < callParams.size(); i++) {
				Variable procVar = procParams.get(i);
				procVar.setValue(callParams.get(i).accept(exprInterpreter));
			}

			// Allocate procedure local List variables
			for (Variable local : proc.getLocals()) {
				Type type = local.getType();
				if (type.isList()) {
					local.setValue(listAllocator.allocate(type));
				}
			}

			// Interpret procedure body
			for (CFGNode node : proc.getNodes()) {
				node.accept(this, args);
			}

			// Get procedure result if any
			if (call.hasResult()) {
				call.getTarget().setValue(returnValue);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(HasTokens instr, Object... args) {
		CommunicationFifo fifo = ((Map<String, CommunicationFifo>) args[0])
				.get(instr.getPort().getName());
		boolean hasTok = fifo.hasTokens(instr.getNumTokens());
		instr.getTarget().setValue(hasTok);
	}

	@Override
	public void visit(IfNode node, Object... args) {
		/* Interpret first expression ("if" condition) */
		Object condition = node.getValue().accept(exprInterpreter);

		/* if (condition is true) then */
		if (condition instanceof Boolean) {
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
		} else {
			throw new OrccRuntimeException("Condition not boolean at line "
					+ node.getLocation().getStartLine() + "\n");
		}
	}

	@Override
	public void visit(Load instr, Object... args) {
		LocalVariable target = instr.getTarget();
		Variable source = instr.getSource().getVariable();
		if (instr.getIndexes().isEmpty()) {
			target.setValue(source.getValue());
		} else {
			try {
				Object obj = source.getValue();
				for (Expression index : instr.getIndexes()) {
					obj = Array.get(obj,
							(Integer) index.accept(exprInterpreter));
				}
				target.setValue(obj);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLocation().getStartLine());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(Peek instr, Object... args) {
		Object[] target = (Object[]) (instr.getTarget().getValue());
		CommunicationFifo fifo = ((Map<String, CommunicationFifo>) args[0])
				.get(instr.getPort().getName());
		fifo.peek(target);
	}

	@Override
	public void visit(PhiAssignment instr, Object... args) {
		LocalVariable target = instr.getTarget();
		for (Use use : instr.getVars()) {
			Variable var = use.getVariable();
			target.setValue(var.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(Read instr, Object... args) {
		Object[] target = (Object[]) instr.getTarget().getValue();
		CommunicationFifo fifo = ((Map<String, CommunicationFifo>) args[0])
				.get(instr.getPort().getName());
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
		Variable variable = instr.getTarget();
		if (instr.getIndexes().isEmpty()) {
			variable.setValue(instr.getValue().accept(exprInterpreter));
		} else {
			Object obj = variable.getValue();
			Object objPrev = obj;
			Integer lastIndex = 0;
			try {
				for (Expression index : instr.getIndexes()) {
					objPrev = obj;
					lastIndex = (Integer) index.accept(exprInterpreter);
					obj = Array.get(objPrev, lastIndex);
				}
				Array.set(objPrev, lastIndex,
						instr.getValue().accept(exprInterpreter));
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLocation().getStartLine());
			}
		}
	}

	@Override
	public void visit(WhileNode node, Object... args) {
		// Interpret first expression ("while" condition)
		Object condition = node.getValue().accept(exprInterpreter);
		// while (condition is true) do
		if (condition instanceof Boolean) {
			while ((Boolean) condition) {
				// control flow sub-statements
				for (CFGNode subNode : node.getNodes()) {
					subNode.accept(this, args);
				}
				// Interpret next value of "while" condition
				condition = node.getValue().accept(exprInterpreter);
			}
		} else {
			throw new OrccRuntimeException("Condition not boolean at line "
					+ node.getLocation().getStartLine() + "\n");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(Write instr, Object... args) {
		Object[] target = (Object[]) instr.getTarget().getValue();
		CommunicationFifo fifo = ((Map<String, CommunicationFifo>) args[0])
				.get(instr.getPort().getName());
		fifo.put(target);
	}

	@Override
	public void visit(WriteEnd instr, Object... args) {
		// Nothing to do
	}

}
