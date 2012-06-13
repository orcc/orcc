/*
 * Copyright (c) 2010-2011, IRISA
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
package net.sf.orcc.backends.xlim.transform;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an actor transformation that replace list of one element
 * used to stock input/output value by a scalar
 * 
 * @author Herve Yviquel
 * 
 */
public class UnaryListRemoval extends DfVisitor<Void> {

	@Override
	public Void caseAction(Action action) {
		doSwitch(action.getInputPattern());
		doSwitch(action.getOutputPattern());

		return null;
	}

	@Override
	public Void casePattern(Pattern pattern) {
		List<Port> ports = new ArrayList<Port>(pattern.getPorts());
		for (Port port : ports) {
			if (pattern.getNumTokens(port) == 1) {
				Var var = pattern.getVariable(port);

				// Transform in scalar variable
				var.setType(((TypeList) var.getType()).getInnermostType());

				// Transform load in assignment
				for (Use use : new ArrayList<Use>(var.getUses())) {
					InstLoad load = EcoreHelper.getContainerOfType(use,
							InstLoad.class);
					InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
							load.getTarget().getVariable(), load.getSource()
									.getVariable());
					EcoreUtil.replace(load, assign);
					IrUtil.delete(load);
				}

				// Transform store in assignment
				for (Def def : new ArrayList<Def>(var.getDefs())) {
					InstStore store = EcoreHelper.getContainerOfType(def,
							InstStore.class);
					store.getIndexes().clear();
				}
			}
		}
		return null;
	}
}
