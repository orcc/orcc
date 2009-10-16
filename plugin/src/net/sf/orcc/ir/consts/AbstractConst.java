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
package net.sf.orcc.ir.consts;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;

/**
 * This class defines an abstract class that deals with constants.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractConst {

	/**
	 * Returns a constant created by evaluating the given expression.
	 * 
	 * @param expr
	 *            an expression
	 * @return a constant
	 * @throws OrccException
	 *             if the expression could not be evaluated to a constant
	 */
	public static IConst evaluate(IExpr expr) throws OrccException {
		if (expr.getType() == IExpr.BOOLEAN) {
			boolean value = ((BooleanExpr) expr).getValue();
			return new BoolConst(value);
		} else if (expr.getType() == IExpr.INT) {
			int value = ((IntExpr) expr).getValue();
			return new IntConst(value);
		} else if (expr.getType() == IExpr.LIST) {
			List<IExpr> value = ((ListExpr) expr).getValue();
			List<IConst> list = new ArrayList<IConst>(value.size());
			for (IExpr subExpr : value) {
				list.add(evaluate(subExpr));
			}

			return new ListConst(list);
		} else if (expr.getType() == IExpr.STRING) {
			String value = ((StringExpr) expr).getValue();
			return new StringConst(value);
		} else {
			throw new OrccException("this expression is not constant");
		}
	}

}
