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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.llvm.type.LLVMAbstractType;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.nodes.AbstractFifoNode;

/**
 * 
 * @author Jérôme GORIN
 * 
 */
public class LLVMVarDefPrinter {

	protected LLVMExprPrinter exprPrinter;

	private TypeToString typeVisitor;

	public LLVMVarDefPrinter(TypeToString typeVisitor) {
		this.typeVisitor = typeVisitor;
		exprPrinter = new LLVMExprPrinter(typeVisitor, this);
	}

	/**
	 * Returns an instance of the "vardef" template with attributes set using
	 * the given VarDef varDef.
	 * 
	 * @param varDef
	 *            a variable definition
	 * @return a string template
	 */
	public Map<String, Object> applyVarDef(Variable varDef) {
		Map<String, Object> varDefMap = new HashMap<String, Object>();
		varDefMap.put("name", getVarDefName(varDef, false));

		IType type;

		if (varDef.getType().getType() == LLVMAbstractType.POINT) {
			PointType iType = (PointType) varDef.getType();
			type = iType.getElementType();
		} else {
			type = varDef.getType();
		}

		varDefMap.put("type", typeVisitor.toString(type));
		varDefMap.put("isGlobal", varDef instanceof GlobalVariable);

		boolean isPort = false;
		for (Use use : varDef.getUses()) {
			if (use.getNode() instanceof AbstractFifoNode) {
				AbstractFifoNode fifoNode = (AbstractFifoNode) use.getNode();
				if (fifoNode.getPort().getName().equals(varDef.getName())) {
					isPort = true;
					break;
				}
			}
		}
		varDefMap.put("isPort", isPort);

		return varDefMap;
	}

	/**
	 * Returns the full name of the given variable definition, with index and
	 * suffix.
	 * 
	 * @param variable
	 *            the variable definition
	 * @return a string with its full name
	 */
	public String getVarDefName(Variable variable, Object... args) {
		Boolean showType = (Boolean) args[0];
		String name = "";

		if (showType) {
			name = typeVisitor.toString(variable.getType());
			name += " ";
		}

		if (variable instanceof LocalVariable) {
			LocalVariable local = (LocalVariable) variable;

			if (local.isConstant()) {
				name += exprPrinter.toString(local.getConstant(), false);
				return name;
			}

			if (local.isGlobal()) {
				name += "@";
			} else {
				name += "%";
			}
			name += local.getName();

			if (local.hasSuffix()) {
				name += local.getSuffix();
			}

			if (!local.isGlobal()) {
				int index = local.getIndex();
				if (index != 0) {
					name += "_" + local.getIndex();
				}
			}
		}

		return name;
	}

}
