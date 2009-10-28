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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccException;

/**
 * This class defines the unary operators of the IR.
 * 
 * @author Matthieu Wipliez
 * 
 */
public enum UnaryOp {

	/**
	 * a binary not (~ in C)
	 */
	BITNOT("~"),

	/**
	 * a logical not (! in C)
	 */
	LOGIC_NOT("!"),

	/**
	 * unary minus
	 */
	MINUS("-"),

	/**
	 * number of elements (# in CAL)
	 */
	NUM_ELTS("#");

	private static final Map<String, UnaryOp> operators = new HashMap<String, UnaryOp>();

	static {
		for (UnaryOp op : UnaryOp.values()) {
			operators.put(op.text, op);
		}
	}

	/**
	 * Returns the unary operator that has the given name.
	 * 
	 * @param name
	 *            an operator name
	 * @return a unary operator
	 * @throws OrccException
	 *             if there is no operator with the given name
	 */
	public static UnaryOp getOperator(String name) throws OrccException {
		UnaryOp op = operators.get(name);
		if (op == null) {
			throw new OrccException("unknown operator \"" + name + "\"");
		} else {
			return op;
		}
	}

	/**
	 * textual representation of this operator
	 */
	private String text;

	/**
	 * Creates an operator with the given textual representation.
	 * 
	 * @param text
	 *            the operator textual representation
	 */
	private UnaryOp(String text) {
		this.text = text;
	}

	/**
	 * Returns the textual representation of this operator.
	 * 
	 * @return the textual representation of this operator
	 */
	public String getText() {
		return text;
	}

}
