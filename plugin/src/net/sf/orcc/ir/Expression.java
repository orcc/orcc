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

import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.ExpressionVisitor;

/**
 * This interface defines an expression.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * 
 */
public interface Expression extends Localizable {

	/**
	 * binary expression
	 */
	public static final int BINARY = 1;

	/**
	 * boolean expression
	 */
	public static final int BOOLEAN = 2;

	/**
	 * integer expression
	 */
	public static final int INT = 3;

	/**
	 * list expression
	 */
	public static final int LIST = 4;

	/**
	 * string expression
	 */
	public static final int STRING = 5;

	/**
	 * unary expression
	 */
	public static final int UNARY = 6;

	/**
	 * variable reference expression
	 */
	public static final int VAR = 7;

	/**
	 * Accepts an interpreter.
	 * 
	 * @param interpreter
	 *            an expression interpreter
	 * @param args
	 *            arguments
	 * @return an object
	 */
	public Object accept(ExpressionInterpreter interpreter, Object... args);

	/**
	 * Accepts a visitor.
	 * 
	 * @param visitor
	 *            an expression visitor
	 * @param args
	 *            arguments
	 */
	public void accept(ExpressionVisitor visitor, Object... args);

	/**
	 * Returns Type corresponding to the type of this expression.
	 * 
	 * @return Type of this expression
	 */
	public Type getType();
	
	/**
	 * Returns an integer corresponding to the type of this expression.
	 * 
	 * @return Integer representing type of this expression
	 */
	public int getTypeOf();

}
