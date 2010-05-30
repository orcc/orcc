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
package net.sf.orcc.backends.llvm;

import java.io.IOException;
import java.util.List;

import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

/**
 * This class defines a LLVM actor printer.
 * 
 * @author Jérôme GORIN
 * 
 */
public final class LLVMActorPrinter extends STPrinter {

	/**
	 * Creates a new network printer with the template "LLVM_actor".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public LLVMActorPrinter() {
		super("LLVM_core", "LLVM_header", "LLVM_actor", "LLVM_metadata");
	}
	
	@Override
	public String toString(Boolean bool) {
		return bool.booleanValue() ? "1" : "0";
	}

	@Override
	protected String toString(List<?> list) {
		return list.toString();
	}

	@Override
	public String toString(Expression expression) {
		LLVMExprPrinter printer = new LLVMExprPrinter();
		expression.accept(printer);
		return printer.toString();
	}

	@Override
	public String toString(Type type) {
		LLVMTypePrinter printer = new LLVMTypePrinter();
		type.accept(printer);
		return printer.toString();
	}

}
