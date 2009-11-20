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
package net.sf.orcc.ir.printers;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.consts.BoolConst;
import net.sf.orcc.ir.consts.ConstantVisitor;
import net.sf.orcc.ir.consts.IntConst;
import net.sf.orcc.ir.consts.ListConst;
import net.sf.orcc.ir.consts.StringConst;

/**
 * This class defines the default constant printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DefaultConstantPrinter implements ConstantVisitor {

	protected StringBuilder builder;

	/**
	 * Creates a new expression printer.
	 */
	public DefaultConstantPrinter() {
		builder = new StringBuilder();
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public void visit(BoolConst constant, Object... args) {
		builder.append(constant.getValue());
	}

	@Override
	public void visit(IntConst constant, Object... args) {
		builder.append(constant.getValue());
	}

	@Override
	public void visit(ListConst constant, Object... args) {
		List<Constant> list = constant.getValue();
		if (list.isEmpty()) {
			builder.append("[]");
		} else {
			Iterator<Constant> it = list.iterator();
			builder.append('[');
			builder.append(it.next().toString());
			while (it.hasNext()) {
				builder.append(", ");
				builder.append(it.next().toString());
			}
			builder.append(']');
		}
	}

	@Override
	public void visit(StringConst constant, Object... args) {
		builder.append('"');
		builder.append(constant.getValue().replaceAll("\\\\", "\\\\\\\\"));
		builder.append('"');
	}

}
