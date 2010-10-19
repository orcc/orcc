/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.transformations.threeAddressCodeTransformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.AbstractExpressionInterpreter;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.util.OrderedMap;

/**
 * Replace occurrences with direct assignments to their corresponding values. A
 * direct assignment is an assign instruction of form x = y, which simply
 * assigns the value of y to x.
 * 
 * @author Jérôme GORIN
 * 
 */
public class CopyPropagationTransformation extends AbstractActorTransformation {

	private class ExpressionCopy extends AbstractExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			expr.setE1((Expression) expr.getE1().accept(this, args));
			expr.setE2((Expression) expr.getE2().accept(this, args));
			return expr;
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			List<Expression> values = expr.getValue();
			for (Expression subExpr : values) {
				Expression newSubExpr = (Expression) subExpr.accept(this, args);
				if (subExpr != newSubExpr) {
					values.set(values.indexOf(subExpr), newSubExpr);
				}
			}
			return expr;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			expr.setExpr((Expression) expr.getExpr().accept(this, args));
			return expr;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			Variable var = expr.getVar().getVariable();

			if (copyVars.containsKey(var)) {
				return copyVars.get(var).accept(this, args);
			}

			return expr;
		}
	}

	private Map<Variable, Expression> copyVars;
	private List<Instruction> removedInstrs;

	public CopyPropagationTransformation() {
		copyVars = new HashMap<Variable, Expression>();
		removedInstrs = new ArrayList<Instruction>();

	}

	/**
	 * Removes the given list of instructions that store to an unused state
	 * variable.
	 * 
	 * @param instructions
	 *            a list of instructions
	 */
	private void removeInstructions(List<Instruction> instructions) {
		if (instructions != null) {
			for (Instruction instr : instructions) {
				instr.getBlock().getInstructions().remove(instr);
			}
		}
	}

	/**
	 * Removes the given list of variables from the procedure
	 * 
	 * @param variables
	 *            a map of variable
	 */
	private void removeVariables(Map<Variable, Expression> variables) {
		OrderedMap<String, Variable> lovalVars = procedure.getLocals();

		for (Map.Entry<Variable, Expression> entry : copyVars.entrySet()) {
			Variable var = entry.getKey();
			lovalVars.remove(var.getName());
		}

	}

	/**
	 * Detect if the assign instructions can be removed
	 * 
	 * @param assign
	 *            assign instruction
	 */
	@Override
	public void visit(Assign assign) {
		Expression value = assign.getValue();

		if ((!value.isBinaryExpr()) && (!value.isUnaryExpr())) {
			// Assign instruction can be remove
			LocalVariable target = assign.getTarget();
			Expression expr = assign.getValue();

			// Set instruction and variable as to be remove
			copyVars.put(target, expr);
			removedInstrs.add(assign);
		} else {

			// Check if the expression can be propagate
			Expression newExpr = (Expression) value
					.accept(new ExpressionCopy());
			assign.setValue(newExpr);
		}
	}

	@Override
	public void visit(Call call) {
		visitExpressions(call.getParameters());
	}

	@Override
	public void visit(IfNode ifNode) {
		// Visit value of store
		Expression value = ifNode.getValue();
		Expression newExpr = (Expression) value.accept(new ExpressionCopy());
		ifNode.setValue(newExpr);

		super.visit(ifNode);
	}

	@Override
	public void visit(Load load) {
		// Visit indexes of load
		visitExpressions(load.getIndexes());
	}

	//
	@Override
	public void visit(PhiAssignment phi) {
		List<Expression> values = phi.getValues();
		LocalVariable target = phi.getTarget();
		OrderedMap<String, Variable> parameters = procedure.getParameters();

		// Visit expressions of value of phi
		visitExpressions(values);

		// Remove local variable with index = 0 from value
		for (Expression value : values) {
			if (value.isVarExpr()) {
				VarExpr sourceExpr = (VarExpr) value;
				LocalVariable source = (LocalVariable) sourceExpr.getVar()
						.getVariable();

				// Local variable must not be a parameter of the procedure
				if (source.getIndex() == 0
						&& !parameters.contains(source.getName())) {
					Expression constExpr;
					Type type = target.getType();

					// Creating constants value
					if (type.isList()) {
						TypeList typeList = (TypeList) type;
						List<Expression> constants = new ArrayList<Expression>();

						for (int i = 0; i < typeList.getSize(); i++) {
							constants.add(new IntExpr(0));
						}

						constExpr = new ListExpr(constants);
					} else {
						constExpr = new IntExpr(0);
					}

					values.set(values.indexOf(value), constExpr);
				}
			}
		}
	}

	@Override
	public void visit(Return returnInstr) {
		Expression expr = returnInstr.getValue();
		if (expr != null) {
			Expression newExpr = (Expression) expr.accept(new ExpressionCopy());
			returnInstr.setValue(newExpr);
		}
	}

	@Override
	public void visit(Store store) {
		// Visit value of store
		Expression value = store.getValue();
		Expression newExpr = (Expression) value.accept(new ExpressionCopy());
		store.setValue(newExpr);

		// Visit indexes of store
		visitExpressions(store.getIndexes());
	}

	@Override
	public void visit(WhileNode whileNode) {
		Expression value = whileNode.getValue();
		Expression newExpr = (Expression) value.accept(new ExpressionCopy());
		whileNode.setValue(newExpr);

		super.visit(whileNode);
	}

	private void visitExpressions(List<Expression> expressions) {
		for (Expression expr : expressions) {
			Expression newExpr = (Expression) expr.accept(new ExpressionCopy());
			if (expr != newExpr) {
				expressions.set(expressions.indexOf(expr), newExpr);
			}
		}
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		copyVars.clear();
		super.visitProcedure(procedure);

		// Remove useless instructions and variables
		removeInstructions(removedInstrs);
		removeVariables(copyVars);
	}
}
