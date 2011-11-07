/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.normalizer;

import java.util.List;

import net.sf.orcc.df.Pattern;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class defines a transform that changes the load/store on FIFO arrays so
 * they use global arrays.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class ChangeFifoArrayAccess extends AbstractActorVisitor<Object> {

	private void updateIndex(Var var, List<Expression> indexes) {
		if (indexes.size() < 2) {
			Var varCount = actor.getStateVar(var.getName() + "_count");
			ExprBinary expr = IrFactory.eINSTANCE.createExprBinary(
					IrFactory.eINSTANCE.createExprVar(varCount), OpBinary.PLUS,
					indexes.get(0), IrFactory.eINSTANCE.createTypeInt(32));

			indexes.set(0, expr);
		} else {
			System.err.println("TODO index");
		}
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		Use use = load.getSource();
		Var var = use.getVariable();
		if (var.isLocal() && var.eContainer() instanceof Pattern) {
			use.setVariable(actor.getStateVar(var.getName()));
			updateIndex(var, load.getIndexes());
		}
		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		Def def = store.getTarget();
		Var var = def.getVariable();
		if (var.isLocal() && var.eContainer() instanceof Pattern) {
			def.setVariable(actor.getStateVar(var.getName()));
			updateIndex(var, store.getIndexes());
		}
		return null;
	}

}
