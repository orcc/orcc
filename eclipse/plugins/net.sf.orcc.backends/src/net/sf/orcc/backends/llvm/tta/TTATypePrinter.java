/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta;

import net.sf.orcc.backends.ir.InstGetElementPtr;
import net.sf.orcc.backends.llvm.aot.LLVMTypePrinter;
import net.sf.orcc.df.Action;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * This class defines a TTA type printer.
 * 
 * @author Herve Yviquel
 * 
 */
public class TTATypePrinter extends LLVMTypePrinter {

	@Override
	public String caseTypeBool(TypeBool type) {
		Integer addrSpace = getAddressSpace(type);
		if (addrSpace != null) {
			return super.caseTypeBool(type) + " addrspace(" + addrSpace + ")";
		}
		return super.caseTypeBool(type);
	}

	private Integer getAddressSpace(Type type) {
		Action action = EcoreHelper.getContainerOfType(type, Action.class);
		if (action != null && type.eContainer() instanceof Var) {
			Var var = (Var) type.eContainer();
			Def def = var.getDefs().get(0);
			if (def.eContainer() instanceof InstGetElementPtr) {
				InstGetElementPtr gep = (InstGetElementPtr) def.eContainer();
				Var source = gep.getSource().getVariable();
				if (action.getInputPattern().contains(source)) {
					return (Integer) action.getInputPattern().getPort(source)
							.getAttribute("id").getValue();
				} else if (action.getOutputPattern().contains(source)) {
					return (Integer) action.getOutputPattern().getPort(source)
							.getAttribute("id").getValue();
				} else if (action.getPeekPattern().contains(source)) {
					return (Integer) action.getPeekPattern().getPort(source)
							.getAttribute("id").getValue();
				}
			}
		}
		return null;
	}

	@Override
	public String caseTypeFloat(TypeFloat type) {
		Integer addrSpace = getAddressSpace(type);
		if (addrSpace != null) {
			return super.caseTypeFloat(type) + " addrspace(" + addrSpace + ")";
		}
		return super.caseTypeFloat(type);
	}

	@Override
	public String caseTypeInt(TypeInt type) {
		Integer addrSpace = getAddressSpace(type);
		if (addrSpace != null) {
			return super.caseTypeInt(type) + " addrspace(" + addrSpace + ")";
		}
		return super.caseTypeInt(type);
	}

	@Override
	public String caseTypeUint(TypeUint type) {
		Integer addrSpace = getAddressSpace(type);
		if (addrSpace != null) {
			return super.caseTypeUint(type) + " addrspace(" + addrSpace + ")";
		}
		return super.caseTypeUint(type);
	}
}
