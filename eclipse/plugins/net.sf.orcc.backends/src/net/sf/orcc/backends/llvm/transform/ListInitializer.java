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
package net.sf.orcc.backends.llvm.transform;

import java.util.List;

import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.Void;

/**
 * Check coherence between a list size and its number of initial values.
 * 
 * @author Jerome Gorin
 * 
 */
public class ListInitializer extends AbstractIrVisitor<Void> {

	@Override
	public Void caseVar(Var var) {
		if (var.getType().isList() && var.isInitialized()) {
			TypeList type = (TypeList) var.getType();
			Expression init = var.getInitialValue();

			if (init.isExprList()) {
				checkSize(type.getDimensions(), ((ExprList) init).getValue(), 0);
			}
		}
		return null;
	}

	private void checkSize(List<Integer> dims, List<Expression> inits, int idx) {
		while (inits.size() < dims.get(idx)) {
			if (idx == dims.size() - 1) {
				inits.add(IrFactory.eINSTANCE.createExprInt(0));
			} else {
				inits.add(IrFactory.eINSTANCE.createExprList());
			}
		}

		for (Expression init : inits) {
			if (init.isExprList()) {
				checkSize(dims, ((ExprList) init).getValue(), idx + 1);
			}
		}
	}

}
