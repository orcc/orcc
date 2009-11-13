/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.xlim;

import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.consts.BoolConst;
import net.sf.orcc.ir.consts.ConstVisitor;
import net.sf.orcc.ir.consts.IntConst;
import net.sf.orcc.ir.consts.ListConst;
import net.sf.orcc.ir.consts.StringConst;
import net.sf.orcc.ir.type.ListType;

import org.w3c.dom.Element;

/**
 * XlimValueVisitor prints constant values in XLIM
 * 
 * @author Samuel Keller EPFL
 */
public class XlimValueVisitor implements ConstVisitor {

	/**
	 * Element to modify
	 */
	private Element element;

	/**
	 * Type of Value
	 */
	private Type type;

	/**
	 * XlimValueVisitor constructor with element and document initialization
	 * 
	 * @param element
	 *            Element to modify
	 * @param type
	 *            Type of Value
	 */
	XlimValueVisitor(Element element, Type type) {
		this.element = element;
		this.type = type;
	}

	/**
	 * Add a boolean constant
	 * 
	 * @param constant
	 *            Boolean constant to be Added
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(BoolConst constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		element.setAttribute("value", constant.getValue() ? "1" : "0");
	}

	/**
	 * Add an integer constant
	 * 
	 * @param constant
	 *            Integer constant to be Added
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(IntConst constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		element.setAttribute("value", constant.toString());
	}

	/**
	 * Add a list constant
	 * 
	 * @param constant
	 *            List constant to be Added
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(ListConst constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		for (Constant value : constant.getValue()) {
			Element sub = XlimNodeTemplate.newInitValue(element);
			value.accept(new XlimValueVisitor(sub, ((ListType) type)
					.getElementType()));
		}
	}

	/**
	 * Add a string constant
	 * 
	 * @param constant
	 *            String constant to be added
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(StringConst constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		element.setAttribute("value", constant.toString());
	}

}