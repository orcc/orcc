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

import net.sf.orcc.ir.type.AbstractType;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.TypeVisitor;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;

/**
 * Type to string.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeToString implements TypeVisitor {

	private StringBuilder builder;

	/**
	 * Creates a string buffer and fills it with the text representation of the
	 * given type. The buffer contents can be retrieved by using the
	 * {@link #toString()} method.
	 * 
	 * @param type
	 *            An {@link AbstractType}.
	 */
	public TypeToString(AbstractType type) {
		builder = new StringBuilder();
		type.accept(this);
	}

	private void printInt(int size) {
		if (size <= 8) {
			builder.append("char");
		} else if (size <= 16) {
			builder.append("short");
		} else if (size <= 32) {
			builder.append("int");
		} else if (size <= 64) {
			builder.append("long long");
		}
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public void visit(BoolType type) {
		// boolean is a C int.
		builder.append("int");
	}

	@Override
	public void visit(IntType type) {
		printInt(type.getSize());
	}

	@Override
	public void visit(ListType type) {
		// size will be printed later
		type.getType().accept(this);
	}

	@Override
	public void visit(StringType type) {
		builder.append("char *");
	}

	@Override
	public void visit(UintType type) {
		builder.append("unsigned ");
		printInt(type.getSize());
	}

	@Override
	public void visit(VoidType type) {
		builder.append("void");
	}

}
