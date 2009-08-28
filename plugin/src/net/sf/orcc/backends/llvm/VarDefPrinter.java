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

import java.util.List;

import net.sf.orcc.ir.VarDef;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * 
 * @author Jérôme GORIN
 * 
 */
public class VarDefPrinter {

	private StringTemplateGroup group;

	private ListSizePrinter listSizePrinter;

	private List<String> ports;

	public VarDefPrinter(StringTemplateGroup group, List<String> ports) {
		this.group = group;
		this.listSizePrinter = new ListSizePrinter();
		this.ports = ports;
	}

	/**
	 * Returns an instance of the "vardef" template with attributes set using
	 * the given VarDef varDef.
	 * 
	 * @param varDef
	 *            a variable definition
	 * @return a string template
	 */
	public StringTemplate applyVarDef(VarDef varDef) {
		StringTemplate varDefTmpl = group.getInstanceOf("vardef");
		varDefTmpl.setAttribute("name", getVarDefName(varDef));
		TypeToString type = new TypeToString(varDef.getType());
		varDefTmpl.setAttribute("type", type.toString());
		
		// if varDef is a list, => list of dimensions
		listSizePrinter.setTemplate(varDefTmpl);
		varDef.getType().accept(listSizePrinter);

		varDefTmpl.setAttribute("isPort", ports.contains(varDef.getName()));
		varDefTmpl.setAttribute("isGlobal", varDef.isGlobal());

		return varDefTmpl;
	}

	/**
	 * Returns the full name of the given variable definition, with index and
	 * suffix.
	 * 
	 * @param varDef
	 *            the variable definition
	 * @return a string with its full name
	 */
	public String getVarDefName(VarDef varDef) {
		String name;
		
		if (varDef.isGlobal()) {
			name = "@";
		}else{
			name = "%";
		}
		
		name += varDef.getName();

		
		if (varDef.hasSuffix()) {
			name += varDef.getSuffix();
		}

		if (!varDef.isGlobal()) {
			int index = varDef.getIndex();
			if (index != 0) {
				name += "_" + varDef.getIndex();
			}
		}

		return name;
	}
}
