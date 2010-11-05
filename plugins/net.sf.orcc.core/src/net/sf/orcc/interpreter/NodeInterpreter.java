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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_String;
import net.sf.orcc.runtime.Fifo_boolean;
import net.sf.orcc.runtime.Fifo_int;
import net.sf.orcc.util.StringUtil;

/**
 * This class defines a node interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class NodeInterpreter extends AbstractActorTransformation {

	private boolean blockReturn;

	protected ExpressionEvaluator exprInterpreter;

	private Map<String, Fifo> fifos;

	protected ListAllocator listAllocator;

	private OrccProcess process;

	private Expression returnValue;

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
	public Expression getReturnValue() {
		if (blockReturn) {
			return returnValue;
		} else {
			return null;
		}
	}

	private void peekFifo(Expression value, Fifo fifo, int numTokens) {
		if (fifo instanceof Fifo_int) {
			ListExpr target = (ListExpr) value;
			int[] intTarget = new int[target.getSize()];
			System.arraycopy(((Fifo_int) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), intTarget, 0, numTokens);
			for (int i = 0; i < intTarget.length; i++) {
				target.set(i, new IntExpr(intTarget[i]));
			}
		} else if (fifo instanceof Fifo_boolean) {
			ListExpr target = (ListExpr) value;
			boolean[] boolTarget = new boolean[target.getSize()];
			System.arraycopy(((Fifo_boolean) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), boolTarget, 0, numTokens);
			for (int i = 0; i < boolTarget.length; i++) {
				target.set(i, new BoolExpr(boolTarget[i]));
			}
		} else if (fifo instanceof Fifo_String) {
			ListExpr target = (ListExpr) value;
			String[] stringTarget = new String[target.getSize()];
			System.arraycopy(((Fifo_String) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), stringTarget, 0, numTokens);
			for (int i = 0; i < stringTarget.length; i++) {
				target.set(i, new StringExpr(stringTarget[i]));
			}
		}
	}

	public void setFifos(Map<String, Fifo> fifos) {
		this.fifos = fifos;
	}

	public void setProcess(OrccProcess process) {
		this.process = process;
	}

	@Override
	public void visit(Assign instr) {
		LocalVariable target = instr.getTarget();
		target.setValue((Expression) instr.getValue().accept(exprInterpreter));
	}

	@Override
	public void visit(Call call) {
		// Get called procedure
		Procedure proc = call.getProcedure();

		// Set the input parameters of the called procedure if any
		List<Expression> callParams = call.getParameters();

		// Special "print" case
		if (call.isPrint()) {
			if (process != null) {
				for (int i = 0; i < callParams.size(); i++) {
					if (callParams.get(i).isStringExpr()) {
						// String characters rework for escaped control
						// management
						String str = ((StringExpr) callParams.get(i))
								.getValue();
						String unescaped = StringUtil.getUnescapedString(str);
						process.write(unescaped);
					} else {
						Object value = callParams.get(i)
								.accept(exprInterpreter);
						process.write(String.valueOf(value));
					}
				}
			}
		} else {
			List<Variable> procParams = proc.getParameters().getList();
			for (int i = 0; i < callParams.size(); i++) {
				Variable procVar = procParams.get(i);
				procVar.setValue((Expression) callParams.get(i).accept(
						exprInterpreter));
			}

			// Allocate procedure local List variables
			for (Variable local : proc.getLocals()) {
				Type type = local.getType();
				if (type.isList()) {
					local.setValue((Expression) type.accept(listAllocator));
				}
			}

			// Interpret procedure body
			for (CFGNode node : proc.getNodes()) {
				node.accept(this);
			}

			// Get procedure result if any
			if (call.hasResult()) {
				call.getTarget().setValue(returnValue);
			}
		}
	}

	@Override
	public void visit(HasTokens instr) {
		Fifo fifo = fifos.get(instr.getPort().getName());
		boolean hasTok = fifo.hasTokens(instr.getNumTokens());
		instr.getTarget().setValue(new BoolExpr(hasTok));
	}

	@Override
	public void visit(IfNode node) {
		// Interpret first expression ("if" condition)
		Expression condition = (Expression) node.getValue().accept(
				exprInterpreter);

		// if (condition is true)
		if (condition != null && condition.isBooleanExpr()) {
			if (((BoolExpr) condition).getValue()) {
				visit(node.getThenNodes());
			} else {
				visit(node.getElseNodes());
			}

			visit(node.getJoinNode());
		} else {
			throw new OrccRuntimeException("Condition not boolean at line "
					+ node.getLocation().getStartLine() + "\n");
		}
	}

	@Override
	public void visit(Load instr) {
		LocalVariable target = instr.getTarget();
		Variable source = instr.getSource().getVariable();
		if (instr.getIndexes().isEmpty()) {
			target.setValue(source.getValue());
		} else {
			try {
				Expression value = source.getValue();
				for (Expression index : instr.getIndexes()) {
					if (value.isListExpr()) {
						value = ((ListExpr) value).get((IntExpr) index
								.accept(exprInterpreter));
					}
				}
				target.setValue(value);
			} catch (IndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLocation().getStartLine());
			}
		}
	}

	@Override
	public void visit(Peek peek) {
		int numTokens = peek.getNumTokens();
		Fifo fifo = fifos.get(peek.getPort().getName());

		if (peek.getTarget() != null) {
			peekFifo(peek.getTarget().getValue(), fifo, numTokens);
		}
	}

	@Override
	public void visit(PhiAssignment phi) {
		// phi must be removed before calling the interpreter
	}

	@Override
	public void visit(Read read) {
		int numTokens = read.getNumTokens();
		Fifo fifo = fifos.get(read.getPort().getName());

		if (read.getTarget() != null) {
			peekFifo(read.getTarget().getValue(), fifo, numTokens);
		}
		fifo.readEnd(numTokens);
	}

	@Override
	public void visit(Return instr) {
		if (instr.getValue() != null) {
			this.returnValue = (Expression) instr.getValue().accept(
					exprInterpreter);
			this.blockReturn = true;
		}
	}

	@Override
	public void visit(SpecificInstruction instr) {
		throw new OrccRuntimeException("does not know how to interpret a "
				+ "specific instruction");
	}

	@Override
	public void visit(Store instr) {
		Variable variable = instr.getTarget();
		if (instr.getIndexes().isEmpty()) {
			variable.setValue((Expression) instr.getValue().accept(
					exprInterpreter));
		} else {
			try {
				Expression target = variable.getValue();
				Iterator<Expression> it = instr.getIndexes().iterator();
				IntExpr index = (IntExpr) it.next().accept(exprInterpreter);
				while (it.hasNext()) {
					if (target.isListExpr()) {
						target = ((ListExpr) target).get(index);
					}
					index = (IntExpr) it.next().accept(exprInterpreter);
				}

				if (target.isListExpr()) {
					Expression value = (Expression) instr.getValue().accept(
							exprInterpreter);
					((ListExpr) target).set(index, value);
				}
			} catch (IndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLocation().getStartLine());
			}
		}
	}

	@Override
	public void visit(WhileNode node) {
		// Interpret first expression ("while" condition)
		Expression condition = (Expression) node.getValue().accept(
				exprInterpreter);

		// while (condition is true) do
		if (condition != null && condition.isBooleanExpr()) {
			while (((BoolExpr) condition).getValue()) {
				visit(node.getNodes());

				condition = (Expression) node.getValue()
						.accept(exprInterpreter);
				if (condition == null || !condition.isBooleanExpr()) {
					throw new OrccRuntimeException(
							"Condition not boolean at line "
									+ node.getLocation().getStartLine() + "\n");
				}
			}
		} else {
			throw new OrccRuntimeException("Condition not boolean at line "
					+ node.getLocation().getStartLine() + "\n");
		}
	}

	@Override
	public void visit(Write instr) {
		int numTokens = instr.getNumTokens();
		Fifo fifo = fifos.get(instr.getPort().getName());

		ListExpr target = (ListExpr) instr.getTarget().getValue();
		if (fifo instanceof Fifo_int) {
			int[] fifoArray = ((Fifo_int) fifo).getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((IntExpr) obj_elem).getIntValue();
			}
			((Fifo_int) fifo).writeEnd(numTokens, fifoArray);
		} else if (fifo instanceof Fifo_boolean) {
			boolean[] fifoArray = ((Fifo_boolean) fifo)
					.getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((BoolExpr) obj_elem).getValue();
			}
			((Fifo_boolean) fifo).writeEnd(numTokens, fifoArray);
		} else if (fifo instanceof Fifo_String) {
			String[] fifoArray = ((Fifo_String) fifo).getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((StringExpr) obj_elem).getValue();
			}
			((Fifo_String) fifo).writeEnd(numTokens, fifoArray);
		}
	}

}
