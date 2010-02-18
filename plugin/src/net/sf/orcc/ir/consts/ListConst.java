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
package net.sf.orcc.ir.consts;

import java.util.List;

import net.sf.orcc.ir.Constant;

/**
 * This class defines a list constant.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * 
 */
public class ListConst extends AbstractConstant {

	private List<Constant> value;

	/**
	 * Creates a new list constant with the given value.
	 * 
	 * @param value
	 *            the value of the constant
	 * @throws NullPointerException
	 *             if value is <code>null</code>
	 */
	public ListConst(List<Constant> value) {
		if (value == null) {
			throw new NullPointerException();
		}

		this.value = value;
	}

	@Override
	public Object accept(ConstantInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(ConstantVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	/**
	 * Returns the value of this constant. The list returned is a reference.
	 * 
	 * @return the value of this constant
	 */
	public List<Constant> getValue() {
		return value;
	}

	@Override
	public boolean isListConst() {
		return true;
	}

}
