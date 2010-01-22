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
package net.sf.orcc.tools.classifier;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;

/**
 * This class defines a partial expression evaluator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PartialExpressionEvaluator extends ExpressionEvaluator {

	private boolean schedulableMode;

	@Override
	public Object interpret(BinaryExpr expr, Object... args) {
		Object val1 = expr.getE1().accept(this);
		Object val2 = expr.getE2().accept(this);

		if (!schedulableMode && (val1 == null || val2 == null)) {
			return null;
		}

		return super.interpretBinaryExpr(expr, val1, val2);
	}

	@Override
	public Object interpret(BoolExpr expr, Object... args) {
		return expr.getValue();
	}

	@Override
	public Object interpret(IntExpr expr, Object... args) {
		return expr.getValue();
	}

	@Override
	public Object interpret(ListExpr expr, Object... args) {
		throw new OrccRuntimeException("can not evaluate List expression");
	}

	@Override
	public Object interpret(StringExpr expr, Object... args) {
		return expr.getValue();
	}

	@Override
	public Object interpret(UnaryExpr expr, Object... args) {
		Object value = expr.getExpr().accept(this, Integer.MIN_VALUE);

		if (!schedulableMode && value == null) {
			return null;
		} else {
			return super.interpretUnaryExpr(expr, value);
		}
	}

	@Override
	public Object interpret(VarExpr expr, Object... args) {
		return expr.getVar().getVariable().getValue();
	}

	/**
	 * Sets schedulable mode. When in schedulable mode, evaluations of null
	 * expressions is forbidden. Otherwise it is allowed.
	 * 
	 * @param schedulableMode
	 */
	public void setSchedulableMode(boolean schedulableMode) {
		this.schedulableMode = schedulableMode;
	}
}
