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
package net.sf.orcc.backends.c;

import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.util.ExpressionPrinter;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a C expression printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CExpressionPrinter extends ExpressionPrinter {

	@Override
	public String caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		int currentPrec = op.getPrecedence();

		int nextPrec;
		if (op == OpBinary.SHIFT_LEFT || op == OpBinary.SHIFT_RIGHT) {
			// special case, for shifts always put parentheses because compilers
			// often issue warnings
			nextPrec = Integer.MIN_VALUE;
		} else {
			nextPrec = currentPrec;
		}

		if (op.needsParentheses(precedence, branch)) {
			return "(" + doSwitch(expr.getE1(), nextPrec, 0) + " "
					+ toString(op) + " " + doSwitch(expr.getE2(), nextPrec, 1)
					+ ")";
		} else {
			return doSwitch(expr.getE1(), nextPrec, 0) + " " + toString(op)
					+ " " + doSwitch(expr.getE2(), nextPrec, 1);
		}
	}

	@Override
	public String caseExprBool(ExprBool expr) {
		return expr.isValue() ? "1" : "0";
	}

	@Override
	public String caseExprInt(ExprInt expr) {
		long value = expr.getValue().longValue();
		if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
			return String.valueOf(value) + "L";
		} else {
			return String.valueOf(value);
		}
	}

	@Override
	public String caseExprList(ExprList expr) {
		EList<Expression> value = expr.getValue();
		String result = "{";
		
		for (int i = 0; i < value.size(); ++i) {
			if (i > 0) {
				result += ", ";
			}
			result += doSwitch(value.get(i));
		}
		result += "}";
		
		return result;
	}

}
