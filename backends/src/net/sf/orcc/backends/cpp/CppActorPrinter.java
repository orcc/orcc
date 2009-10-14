/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.cpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.c.CActorPrinter;
import net.sf.orcc.backends.c.NodePrinterTemplate;
import net.sf.orcc.backends.c.VarDefPrinter;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.type.IType;

import org.antlr.stringtemplate.StringTemplate;

/**
 * Actor printer.
 * 
 * @author Mathieu Wippliez
 * @author Ghislain Roquier
 * 
 */
public class CppActorPrinter extends CActorPrinter {

	/**
	 * Creates a new actor printer with the template "Cpp_actor.stg".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CppActorPrinter(String tmpl_name) throws IOException {
		super(tmpl_name);
		constPrinter = new CppConstPrinter(group);
		typePrinter = new CppTypePrinter();
		varDefPrinter = new VarDefPrinter(typePrinter);
		exprPrinter = new CppExprPrinter(varDefPrinter);
	}

	protected StringTemplate applyProc(String actorName, Procedure proc) {
		StringTemplate procTmpl = group.getInstanceOf("proc");

		procTmpl.setAttribute("actorname", actorName);
		// name
		procTmpl.setAttribute("name", proc.getName());

		// return type
		IType type = proc.getReturnType();
		procTmpl.setAttribute("type", typePrinter.toString(type));

		// parameters
		List<Object> varDefs = new ArrayList<Object>();
		for (VarDef param : proc.getParameters()) {
			Map<String, Object> varDefMap = varDefPrinter.applyVarDef(param);
			varDefs.add(varDefMap);
		}
		procTmpl.setAttribute("parameters", varDefs);

		// locals
		varDefs = new ArrayList<Object>();
		for (VarDef local : proc.getLocals()) {
			Map<String, Object> varDefMap = varDefPrinter.applyVarDef(local);
			varDefs.add(varDefMap);
		}
		procTmpl.setAttribute("locals", varDefs);

		// body
		NodePrinterTemplate printer = new CppNodePrinter(group, procTmpl,
				actorName, varDefPrinter, exprPrinter);
		for (AbstractNode node : proc.getNodes()) {
			node.accept(printer);
		}
		return procTmpl;
	}
}
