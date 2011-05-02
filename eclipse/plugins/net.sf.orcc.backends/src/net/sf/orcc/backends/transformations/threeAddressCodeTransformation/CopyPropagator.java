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

import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.common.util.EList;

/**
 * Replace occurrences with direct assignments to their corresponding values. A
 * direct assignment is an assign instruction of form x = y, which simply
 * assigns the value of y to x.
 * 
 * @author Jerome GORIN
 * 
 */
public class CopyPropagator extends
		AbstractActorVisitor<Expression> {

	private Map<Var, Expression> copyVars;

	private List<Instruction> removedInstrs;

	public CopyPropagator() {
		copyVars = new HashMap<Var, Expression>();
		removedInstrs = new ArrayList<Instruction>();

	}

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		expr.setE1(doSwitch(expr.getE1()));
		expr.setE2(doSwitch(expr.getE2()));
		return expr;
	}

	@Override
	public Expression caseExprList(ExprList expr) {
		List<Expression> values = expr.getValue();
		for (Expression subExpr : values) {
			values.set(values.indexOf(subExpr), doSwitch(subExpr));
		}
		return expr;
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		expr.setExpr(doSwitch(expr.getExpr()));
		return expr;
	}

	@Override
	public Expression caseExprVar(ExprVar expr) {
		Var var = expr.getUse().getVariable();
		if (copyVars.containsKey(var)) {
			return doSwitch(copyVars.get(var));
		}
		return expr;
	}

	/**
	 * Detect if the assign instructions can be removed
	 * 
	 * @param assign
	 *            assign instruction
	 */
	@Override
	public Expression caseInstAssign(InstAssign assign) {
		Expression value = assign.getValue();

		if ((!value.isBinaryExpr()) && (!value.isUnaryExpr())) {
			// Assign instruction can be remove
			Var target = assign.getTarget().getVariable();
			Expression expr = assign.getValue();

			// Set instruction and variable as to be remove
			copyVars.put(target, expr);
			removedInstrs.add(assign);
		} else {
			assign.setValue(doSwitch(value));
		}

		return null;
	}

	@Override
	public Expression caseInstCall(InstCall call) {
		EList<Expression> parameters = call.getParameters();
		for (Expression expr : parameters) {
			parameters.set(parameters.indexOf(expr), doSwitch(expr));
		}
		return null;
	}

	@Override
	public Expression caseInstLoad(InstLoad load) {
		EList<Expression> indexes = load.getIndexes();
		for (Expression expr : indexes) {
			indexes.set(indexes.indexOf(expr), doSwitch(expr));
		}
		return null;
	}

	@Override
	public Expression caseInstPhi(InstPhi phi) {
		Var target = phi.getTarget().getVariable();
		EList<Var> parameters = procedure.getParameters();

		EList<Expression> values = phi.getValues();
		for (Expression expr : values) {
			values.set(values.indexOf(expr), doSwitch(expr));
		}

		// Remove local variable with index = 0 from value
		for (Expression value : values) {
			if (value.isVarExpr()) {
				ExprVar sourceExpr = (ExprVar) value;
				Var source = sourceExpr.getUse().getVariable();

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
							constants.add(IrFactory.eINSTANCE.createExprInt(0));
						}

						constExpr = IrFactory.eINSTANCE
								.createExprList(constants);
					} else {
						constExpr = IrFactory.eINSTANCE.createExprInt(0);
					}

					values.set(values.indexOf(value), constExpr);
				}
			}
		}
		return null;
	}

	@Override
	public Expression caseInstReturn(InstReturn instReturn) {
		Expression expr = instReturn.getValue();
		if (expr != null) {
			instReturn.setValue(doSwitch(instReturn.getValue()));
		}
		return null;
	}

	@Override
	public Expression caseInstStore(InstStore store) {
		store.setValue(doSwitch(store.getValue()));

		EList<Expression> indexes = store.getIndexes();
		for (Expression expr : indexes) {
			indexes.set(indexes.indexOf(expr), doSwitch(expr));
		}

		return null;
	}

	@Override
	public Expression caseNodeIf(NodeIf nodeIf) {
		nodeIf.setCondition(doSwitch(nodeIf.getCondition()));
		return super.caseNodeIf(nodeIf);
	}

	@Override
	public Expression caseNodeWhile(NodeWhile nodeWhile) {
		nodeWhile.setCondition(doSwitch(nodeWhile.getCondition()));
		return super.caseNodeWhile(nodeWhile);
	}

	@Override
	public Expression caseProcedure(Procedure procedure) {
		copyVars.clear();
		super.visit(procedure);

		// Remove useless instructions and variables
		EcoreHelper.delete(removedInstrs);
		removeVariables(copyVars);

		return null;
	}

	/**
	 * Removes the given list of variables from the procedure
	 * 
	 * @param vars
	 *            a map of variable
	 */
	private void removeVariables(Map<Var, Expression> vars) {
		EList<Var> lovalVars = procedure.getLocals();

		for (Map.Entry<Var, Expression> entry : copyVars.entrySet()) {
			Var var = entry.getKey();
			lovalVars.remove(var.getName());
		}

	}
}
