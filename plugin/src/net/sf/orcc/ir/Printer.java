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

import net.sf.orcc.ir.printers.DefaultPrinter;
import net.sf.orcc.util.INameable;

/**
 * This class defines an IR printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class Printer {

	/**
	 * The default printer
	 */
	private static Printer defaultPrinter = new DefaultPrinter();

	/**
	 * The unique instance of a printer, initialized to {@link #defaultPrinter}.
	 */
	private static Printer instance = defaultPrinter;

	/**
	 * Returns the unique instance of this printer.
	 * 
	 * @return the unique instance of this printer
	 */
	public static Printer getInstance() {
		return instance;
	}

	/**
	 * Sets the unique instance of this printer to the given printer
	 * 
	 * @param printer
	 */
	public static void register(Printer printer) {
		Printer.instance = printer;
	}

	/**
	 * Returns a string representation of the given constant.
	 * 
	 * @param constant
	 *            a constant
	 * @return a string representation of the given constant
	 */
	public abstract String toString(Constant constant);

	/**
	 * Returns a string representation of the given expression.
	 * 
	 * @param expression
	 *            an expression
	 * @return a string representation of the given expression
	 */
	public abstract String toString(Expression expression);

	/**
	 * Returns a string representation of the given nameable object.
	 * 
	 * @param nameable
	 *            a nameable object
	 * @return a string representation of the given nameable object
	 */
	public abstract String toString(INameable nameable);

	/**
	 * Returns a string representation of the given type.
	 * 
	 * @param type
	 *            a type
	 * @return a string representation of the given type
	 */
	public abstract String toString(Type type);

}
