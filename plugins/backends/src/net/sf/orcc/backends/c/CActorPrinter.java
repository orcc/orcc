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
package net.sf.orcc.backends.c;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.util.INameable;

/**
 * This class defines a C actor printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public final class CActorPrinter extends STPrinter {

	private Map<String, String> transformations;

	/**
	 * Creates a new network printer with the template "C_actor".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CActorPrinter() {
		super("C_actor");

		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_");
		transformations.put("index", "index_");
		transformations.put("getw", "getw_");
		transformations.put("select", "select_");
	}

	@Override
	public String toString(Constant constant) {
		CConstPrinter printer = new CConstPrinter(group);
		constant.accept(printer);
		return printer.toString();
	}

	@Override
	public String toString(Expression expression) {
		CExpressionPrinter printer = new CExpressionPrinter();
		expression.accept(printer, Integer.MAX_VALUE);
		return printer.toString();
	}

	@Override
	public String toString(INameable nameable) {
		String name = nameable.getName();
		if (transformations.containsKey(name)) {
			return transformations.get(name);
		} else {
			return name;
		}
	}

	@Override
	public String toString(Type type) {
		CTypePrinter printer = new CTypePrinter();
		type.accept(printer);
		return printer.toString();
	}

}
