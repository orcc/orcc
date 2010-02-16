/*
 * Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl;

import java.util.List;

import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.consts.BoolConst;
import net.sf.orcc.ir.consts.ListConst;
import net.sf.orcc.ir.printers.DefaultConstantPrinter;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * This class defines a VHDL constant printer.
 * 
 * @author Nicolas Siret
 * 
 */
public class VHDLConstPrinter extends DefaultConstantPrinter {

	/**
	 * template group
	 */
	private StringTemplateGroup group;

	/**
	 * Creates a new const printer from the given template group.
	 * 
	 * @param group
	 *            template group
	 */
	public VHDLConstPrinter(StringTemplateGroup group) {
		this.group = group;
	}

	@Override
	public void visit(BoolConst constant, Object... args) {
		builder.append(constant.getValue() ? "'1'" : "'0'");
	}

	@Override
	public void visit(ListConst constant, Object... args) {
		// set instance of list template as current template
		StringTemplate template = group.getInstanceOf("listValue");

		List<Constant> list = constant.getValue();
		for (Constant cst : list) {
			template.setAttribute("value", cst.toString());
		}

		// restore previous template as current template, and set attribute
		// "value" to the instance of the list template
		builder.append(template.toString(80));
	}

}
