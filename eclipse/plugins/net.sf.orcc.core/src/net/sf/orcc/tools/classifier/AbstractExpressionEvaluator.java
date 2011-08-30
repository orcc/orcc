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
package net.sf.orcc.tools.classifier;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.ExpressionPrinter;

/**
 * This class defines a partial expression evaluator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AbstractExpressionEvaluator extends ExpressionEvaluator {

	private boolean schedulableMode;

	@Override
	public Object caseExprBinary(ExprBinary expr) {
		Object val1 = doSwitch(expr.getE1());
		Object val2 = doSwitch(expr.getE2());
		Object result = interpretBinaryExpr(val1, expr.getOp(), val2);

		// only throws an exception if we are in schedulable mode and the result
		// is null
		if (schedulableMode && result == null) {
			throw new OrccRuntimeException("Expression '"
					+ new ExpressionPrinter().doSwitch(expr) + "' is null");
		}
		return result;
	}

	@Override
	public Object caseExprUnary(ExprUnary expr) {
		Object value = doSwitch(expr.getExpr());
		Object result = interpretUnaryExpr(expr.getOp(), value);

		if (schedulableMode && value == null) {
			throw new OrccRuntimeException("Expression '"
					+ new ExpressionPrinter().doSwitch(expr) + "' is null");
		}
		return result;
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
