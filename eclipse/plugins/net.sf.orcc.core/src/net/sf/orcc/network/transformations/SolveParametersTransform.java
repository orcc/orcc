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
package net.sf.orcc.network.transformations;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.IrSwitch;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a network transformation that closes actors in a network.
 * Closing an actor means its parameters (free variables) are transformed to
 * constant state variables, so the actor has no free variables any more, hence
 * it is closed.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SolveParametersTransform extends IrSwitch<Expression> implements
		INetworkTransformation {

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		Expression e1 = doSwitch(expr.getE1());
		Expression e2 = doSwitch(expr.getE2());
		return IrFactory.eINSTANCE.createExprBinary(e1, expr.getOp(), e2,
				expr.getType());
	}

	@Override
	public Expression caseExpression(Expression expr) {
		return EcoreUtil.copy(expr);
	}

	@Override
	public Expression caseExprList(ExprList expr) {
		ExprList list = IrFactory.eINSTANCE.createExprList();
		List<Expression> expressions = expr.getValue();
		List<Expression> values = list.getValue();
		for (Expression expression : expressions) {
			values.add(doSwitch(expression));
		}

		return list;
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		Expression subExpr = doSwitch(expr.getExpr());
		return IrFactory.eINSTANCE.createExprUnary(expr.getOp(), subExpr,
				expr.getType());
	}

	@Override
	public Expression caseExprVar(ExprVar expr) {
		Var var = expr.getUse().getVariable();
		Expression value = var.getInitialValue();
		return EcoreUtil.copy(value);
	}

	/**
	 * Replaces the value of each parameter by an expression where references to
	 * variables have been replaced by the values of the variables.
	 * 
	 * @param parameters
	 *            a map of parameter names to values
	 */
	private void solveParameters(Map<String, Expression> parameters) {
		for (Entry<String, Expression> entry : parameters.entrySet()) {
			Expression value = entry.getValue();
			if (value == null) {
				throw new OrccRuntimeException("Parameter " + entry.getKey()
						+ " has no value");
			}

			Expression resolvedValue = doSwitch(value);
			Object constantValue = new ExpressionEvaluator()
					.doSwitch(resolvedValue);
			value = ValueUtil.getExpression(constantValue);
			entry.setValue(value);
		}
	}

	/**
	 * Walks through the hierarchy and close networks.
	 * 
	 * @throws OrccException
	 *             if a network could not be closed
	 */
	public void transform(Network network) {
		for (Instance instance : network.getInstances()) {
			solveParameters(instance.getParameters());

			if (instance.isNetwork()) {
				Network subNetwork = instance.getNetwork();
				updateNetworkParameters(subNetwork, instance.getParameters());
			}
		}
	}

	/**
	 * Updates the parameters of the given network with the given parameter
	 * values.
	 * 
	 * @param network
	 *            a network
	 * @param values
	 *            a map of parameter names to values
	 */
	private void updateNetworkParameters(Network network,
			Map<String, Expression> values) {
		for (Var parameter : network.getParameters()) {
			String name = parameter.getName();
			Expression value = values.get(name);
			if (value == null) {
				throw new OrccRuntimeException("Network " + network
						+ " has no value for parameter " + name);
			}

			parameter.setInitialValue(value);
		}
	}

}
