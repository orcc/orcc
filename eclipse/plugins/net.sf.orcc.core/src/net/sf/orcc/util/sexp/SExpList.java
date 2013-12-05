/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.util.sexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This class defines a list s-expression.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SExpList extends SExp {

	private List<SExp> expressions;

	/**
	 * Creates a new s-expression list with the given expressions as initial
	 * contents.
	 * 
	 * @param exps
	 *            a list of s-expressions
	 */
	public SExpList(SExp... exps) {
		getExpressions().addAll(Arrays.asList(exps));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SExpList) {
			SExpList other = (SExpList) obj;
			return getExpressions().equals(other.getExpressions());
		}
		return false;
	}

	/**
	 * Returns the expression at the specified position in this list.
	 * 
	 * @param index
	 *            position of the expression to return
	 * @return the expression at the specified position in this list
	 */
	public SExp get(int index) {
		return getExpressions().get(index);
	}

	/**
	 * Returns the s-expressions in this s-expression.
	 * 
	 * @return the s-expressions in this s-expression
	 */
	public List<SExp> getExpressions() {
		if (expressions == null) {
			expressions = new ArrayList<SExp>();
		}

		return expressions;
	}

	/**
	 * Returns the symbol s-expression at the specified position in this list.
	 * 
	 * @param index
	 *            position of the expression to return
	 * @return the symbol s-expression at the specified position in this list
	 * @throws IllegalStateException
	 *             if the expression at the given index is not a symbol
	 */
	public SExpSymbol getSymbol(int index) {
		SExp sexp = get(index);
		if (!sexp.isSymbol()) {
			throw new IllegalStateException(
					"expression at the given index is not a symbol");
		}
		return (SExpSymbol) sexp;
	}

	@Override
	public boolean isList() {
		return true;
	}

	/**
	 * Returns the number of expressions in this s-expression.
	 * 
	 * @return the number of expressions in this s-expression
	 */
	public int size() {
		return getExpressions().size();
	}

	/**
	 * Returns <code>true</code> if this s-expression starts with the given
	 * expression.
	 * 
	 * @param sexp
	 *            a s-expression
	 * @return <code>true</code> if this s-expression starts with the given
	 *         expression
	 */
	public boolean startsWith(SExp sexp) {
		Iterator<SExp> it = getExpressions().iterator();
		if (it.hasNext()) {
			return it.next().equals(sexp);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		Iterator<SExp> it = getExpressions().iterator();
		if (it.hasNext()) {
			builder.append(it.next().toString());
			while (it.hasNext()) {
				builder.append(' ');
				builder.append(it.next().toString());
			}
		}
		builder.append(')');

		return builder.toString();
	}

	/**
	 * Adds the given expression to this list of s-expressions.
	 * 
	 * @param sexp
	 *            a s-expression
	 */
	public void add(SExp sexp) {
		getExpressions().add(sexp);
	}

}
