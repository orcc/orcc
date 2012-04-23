/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.transformations.ssa;

import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Replace occurrences with direct assignments to their corresponding values. A
 * direct assignment is an assign instruction of form x = y, which simply
 * assigns the value of y to x.
 * 
 * @author Jerome GORIN
 * @author Herve Yviquel
 * 
 */
public class CopyPropagator extends AbstractIrVisitor<Void> {

	protected boolean changed;

	@Override
	public Void caseProcedure(Procedure procedure) {
		do {
			changed = false;
			super.caseProcedure(procedure);
		} while (changed);
		return null;
	}

	@Override
	public Void caseInstAssign(InstAssign assign) {
		if (assign.getValue().isExprVar()) {
			Var source = ((ExprVar) assign.getValue()).getUse().getVariable();
			Var target = assign.getTarget().getVariable();
			EList<Use> targetUses = target.getUses();
			changed = true;
			while (!targetUses.isEmpty()) {
				targetUses.get(0).setVariable(source);
			}
			EcoreUtil.remove(target);
			IrUtil.delete(assign);
			indexInst--;
		}
		return null;
	}
}
