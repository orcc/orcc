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

import net.sf.orcc.backends.xlim.templates.XlimAttributeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimTypeTemplate;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.FloatType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.TypeVisitor;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;

import org.w3c.dom.Element;

/**
 * XlimTypeSizeVisitor prints typeName and size of types
 * 
 * @author Samuel Keller EPFL
 */
public class XlimTypeSizeVisitor implements TypeVisitor, XlimTypeTemplate,
		XlimAttributeTemplate {

	/**
	 * Element to modify
	 */
	Element element;

	/**
	 * XlimTypeSizeVisitor constructor with element
	 * 
	 * @param element
	 *            Element to modify
	 */
	public XlimTypeSizeVisitor(Element element) {
		this.element = element;
	}

	/**
	 * Add typeName and size for boolean type
	 * 
	 * @param type
	 *            Boolean type
	 */
	public void visit(BoolType type) {
		element.setAttribute(SIZE, "1");
		element.setAttribute(TYPENAME, BOOL);
	}

	/**
	 * Add typeName and size for integer type
	 * 
	 * @param type
	 *            Integer type
	 */
	public void visit(IntType type) {
		element.setAttribute(SIZE, Integer.toString(type.getSize()));
		element.setAttribute(TYPENAME, INT);
	}

	/**
	 * Add typeName and size for list type
	 * 
	 * @param type
	 *            List type
	 */
	public void visit(ListType type) {
		element.setAttribute(TYPENAME, LIST);
	}

	/**
	 * Add typeName and size for string type
	 * 
	 * @param type
	 *            String type
	 */
	public void visit(StringType type) {
		element.setAttribute(TYPENAME, STRING);

	}

	/**
	 * Add typeName and size for unsigned integer type
	 * 
	 * @param type
	 *            Unsigned integer type
	 */
	public void visit(UintType type) {
		element.setAttribute(SIZE, Integer.toString(type.getSize()));
		element.setAttribute(TYPENAME, UINT);
	}

	/**
	 * Add no typeName and size for void type
	 * 
	 * @param type
	 *            Void type
	 */
	public void visit(VoidType type) {
	}

	@Override
	public void visit(FloatType type) {
		element.setAttribute(SIZE, "32");
		element.setAttribute(TYPENAME, FLOAT);
	}

}
