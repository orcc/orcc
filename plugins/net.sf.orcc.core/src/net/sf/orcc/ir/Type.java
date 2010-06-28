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
package net.sf.orcc.ir;

import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.ir.type.TypeVisitor;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines a type.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * @model abstract="true"
 * 
 */
public interface Type extends EObject {

	/**
	 * Accepts an interpreter.
	 * 
	 * @param interpreter
	 *            an interpreter
	 * @return an object
	 */
	public Object accept(TypeInterpreter interpreter);

	/**
	 * Accepts a visitor.
	 * 
	 * @param visitor
	 *            a visitor
	 */
	public void accept(TypeVisitor visitor);

	/**
	 * Returns a list of dimensions of this type. Returns an empty list if the
	 * type is not a list.
	 * 
	 * @return the list of dimensions of this type if it is a list
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public EList<Integer> getDimensions();

	/**
	 * Returns true if this type is <tt>bool</tt>.
	 * 
	 * @return true if this type is <tt>bool</tt>
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public boolean isBool();

	/**
	 * Returns true if this type is <tt>float</tt>.
	 * 
	 * @return true if this type is <tt>float</tt>
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public boolean isFloat();

	/**
	 * Returns true if this type is <tt>int</tt>.
	 * 
	 * @return true if this type is <tt>int</tt>
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public boolean isInt();

	/**
	 * Returns true if this type is <tt>List</tt>.
	 * 
	 * @return true if this type is <tt>List</tt>
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public boolean isList();

	/**
	 * Returns true if this type is <tt>String</tt>.
	 * 
	 * @return true if this type is <tt>String</tt>
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public boolean isString();

	/**
	 * Returns true if this type is <tt>uint</tt>.
	 * 
	 * @return true if this type is <tt>uint</tt>
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public boolean isUint();

	/**
	 * Returns true if this type is <tt>void</tt>.
	 * 
	 * @return true if this type is <tt>void</tt>
	 * @model changeable="false" derived="true" volatile="true"
	 */
	public boolean isVoid();

}
