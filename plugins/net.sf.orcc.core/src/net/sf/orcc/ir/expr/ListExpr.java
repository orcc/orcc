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
package net.sf.orcc.ir.expr;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;

/**
 * This class defines a list expression. This kind of expression is only present
 * at the network level.
 * 
 * @author Matthieu Wipliez
 * @author J�r�me Gorin
 * 
 */
public class ListExpr extends AbstractExpression {

	private List<Expression> value;

	public ListExpr(List<Expression> value) {
		this.value = value;
	}

	public ListExpr(ListExpr l1, ListExpr l2) {
		value = new ArrayList<Expression>(l1.value.size() + l2.value.size());
		value.addAll(l1.value);
		value.addAll(l2.value);
	}

	@Override
	public Object accept(ExpressionInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(ExpressionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	@Override
	public Type getType() {
		if (value.size() == 0) {
			return null;
		}

		// Verify if every expressions on the list are getting the same type
		Expression firstExpr = value.get(0);
		Type refType = firstExpr.getType();
		for (Expression expr : value) {
			Type type = expr.getType();
			if (!refType.equals(type)) {
				return null;
			}
		}

		return IrFactory.eINSTANCE.createTypeList(value.size(), refType);
	}

	public List<Expression> getValue() {
		return value;
	}

	@Override
	public boolean isListExpr() {
		return true;
	}

	public Expression get(int index) {
		return value.get(index);
	}

}
