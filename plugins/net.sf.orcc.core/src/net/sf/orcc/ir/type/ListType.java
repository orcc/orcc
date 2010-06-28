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
package net.sf.orcc.ir.type;

import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a List type.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * @model extends="net.sf.orcc.ir.Type"
 * 
 */
public interface ListType extends Type {

	public static final String NAME = "List";

	/**
	 * Returns the type of the elements of this list
	 * 
	 * @return the number of elements of this list
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public Type getElementType();

	/**
	 * Returns the number of elements of this list type.
	 * 
	 * @return the number of elements of this list type
	 * @model
	 */
	public int getSize();

	/**
	 * Returns a list of indexes that can be used inside a template.
	 * 
	 * @return a list of indexes corresponding to the list size
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public EList<Integer> getSizeIterator();

	/**
	 * Returns the type of the list
	 * 
	 * @return the type of the list
	 * @model
	 */
	public Type getType();

	/**
	 * Sets the number of elements of this list type.
	 * 
	 * @param size
	 *            the number of elements of this list type
	 */
	public void setSize(int size);

	/**
	 * Sets the type of this list.
	 * 
	 * @param type
	 *            element type
	 */
	public void setType(Type type);

}
