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
package net.sf.orcc.ir.util;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

/**
 * This class defines a type entry.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Entry {

	/**
	 * expression entry
	 */
	public static final int EXPR = 1;

	/**
	 * type entry
	 */
	public static final int TYPE = 2;

	/**
	 * the contents of this entry: expression or type.
	 */
	private Object content;

	/**
	 * the type of this entry
	 */
	private int type;

	/**
	 * Creates a new expression entry
	 * 
	 * @param expr
	 *            an expression
	 */
	public Entry(Expression expr) {
		this.content = expr;
		this.type = EXPR;
	}

	/**
	 * Creates a new type entry
	 * 
	 * @param type
	 *            a type
	 */
	public Entry(Type type) {
		this.content = type;
		this.type = TYPE;
	}

	/**
	 * Returns this entry's content as an expression
	 * 
	 * @return this entry's content as an expression
	 */
	public Expression getEntryAsExpr() {
		if (getType() == EXPR) {
			return (Expression) content;
		} else {
			throw new OrccRuntimeException(
					"this entry does not contain an expression");
		}
	}

	/**
	 * Returns this entry's content as a type
	 * 
	 * @return this entry's content as a type
	 */
	public Type getEntryAsType() {
		if (getType() == TYPE) {
			return (Type) content;
		} else {
			throw new OrccRuntimeException("this entry does not contain a type");
		}
	}

	/**
	 * Returns the type of this entry.
	 * 
	 * @return the type of this entry
	 */
	public int getType() {
		return type;
	}

}
