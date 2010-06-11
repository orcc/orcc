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

import java.util.List;

import net.sf.orcc.backends.xlim.templates.XlimAttributeTemplate;
import net.sf.orcc.ir.Type;

import org.w3c.dom.Element;

/**
 * XlimValueVisitor prints constant values in XLIM
 * 
 * @author Samuel Keller EPFL
 */
public class XlimValueVisitor implements XlimAttributeTemplate {

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
	public void visit(Boolean constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		element.setAttribute(VALUE, constant.booleanValue() ? "1" : "0");
	}

	/**
	 * Add an integer constant
	 * 
	 * @param constant
	 *            Integer constant to be Added
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Integer constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		element.setAttribute(VALUE, Integer.toString(constant));
	}

	/**
	 * Add a list constant
	 * 
	 * @param constant
	 *            List constant to be Added
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(List<?> constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		// FIXME
		// for (Object value : constant) {
		// Element sub = XlimNodeTemplate.newInitValue(element);
		// value.accept(new XlimValueVisitor(sub, ((ListType) type)
		// .getElementType()));
		// }
	}

	/**
	 * Add a string constant
	 * 
	 * @param constant
	 *            String constant to be added
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(String constant, Object... args) {
		type.accept(new XlimTypeSizeVisitor(element));
		element.setAttribute(VALUE, constant);
	}

}