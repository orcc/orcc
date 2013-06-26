/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import java.util.List;

/**
 * This class defines a List type.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model extends="net.sf.orcc.ir.Type"
 * 
 */
public interface TypeList extends Type {

	public static final String NAME = "List";

	/**
	 * Returns the innermost type of this List type declaration.
	 * 
	 * <p>
	 * The difference between the "type" of a list and the "innermost type" of a
	 * list is as follows: given a list
	 * <code>List(type: List(type: uint(size=3), size=59), size=42)</code>
	 * </p>
	 * <ul>
	 * <li>
	 * type is <code>List(type: uint(size=3), size=59)</code></li>
	 * <li>
	 * innermost type is <code>uint(size=3)</code></li>
	 * </ul>
	 * 
	 * @return the innermost type of this List type
	 */
	Type getInnermostType();

	/**
	 * Returns the number of elements of this list type.
	 * 
	 * @return the number of elements of this list type
	 */
	int getSize();

	/**
	 * Returns the number of elements of this list type as an expression.
	 * 
	 * @return the number of elements of this list type as an expression
	 * @model containment="true"
	 */
	Expression getSizeExpr();

	/**
	 * Returns a list of indexes that can be used inside a template.
	 * 
	 * @return a list of indexes corresponding to the list size
	 */
	List<Integer> getSizeIterator();

	/**
	 * Returns the type of the list
	 * 
	 * @return the type of the list
	 * @model containment="true"
	 */
	Type getType();

	/**
	 * Sets the number of elements of this list type as an expression.
	 * 
	 * @param value
	 *            the number of elements of this list type as an expression
	 */
	void setSizeExpr(Expression value);

	/**
	 * Sets the type of this list.
	 * 
	 * @param type
	 *            element type
	 */
	void setType(Type type);

}
