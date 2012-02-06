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

import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines an expression.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model abstract="true"
 * 
 */
public interface Expression extends EObject {

	/**
	 * Returns the type of this expression.
	 * 
	 * @return the type of this expression
	 */
	public Type getType();

	/**
	 * Returns true if the expression is a binary expression.
	 * 
	 * @return true if the expression is a binary expression
	 */
	public boolean isExprBinary();

	/**
	 * Returns true if the expression is a boolean expression.
	 * 
	 * @return true if the expression is a boolean expression
	 */
	public boolean isExprBool();

	/**
	 * Returns true if the expression is a float expression.
	 * 
	 * @return true if the expression is a float expression
	 */
	public boolean isExprFloat();

	/**
	 * Returns true if the expression is an integer expression.
	 * 
	 * @return true if the expression is an integer expression
	 */
	public boolean isExprInt();

	/**
	 * Returns true if the expression is a list expression.
	 * 
	 * @return true if the expression is a list expression
	 */
	public boolean isExprList();

	/**
	 * Returns true if the expression is a string expression.
	 * 
	 * @return true if the expression is a string expression
	 */
	public boolean isExprString();

	/**
	 * Returns true if the expression is a unary expression.
	 * 
	 * @return true if the expression is a unary expression
	 */
	public boolean isExprUnary();

	/**
	 * Returns true if the expression is a variable expression.
	 * 
	 * @return true if the expression is a variable expression
	 */
	public boolean isExprVar();

}
