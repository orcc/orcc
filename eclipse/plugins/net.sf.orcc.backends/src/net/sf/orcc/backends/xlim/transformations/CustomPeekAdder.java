/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.xlim.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.xlim.XlimActorTemplateData;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * 
 * This class defines a transformation that replace Peek instruction by our
 * custom Peek instruction.
 * 
 * @author Herve Yviquel
 * 
 */
public class CustomPeekAdder extends AbstractActorVisitor<Object> {

	private Map<Port, Map<Integer, Var>> customPeekedMap;

	@Override
	public Object caseAction(Action action) {
		customPeekedMap = new HashMap<Port, Map<Integer, Var>>();
		doSwitch(action.getPeekPattern());
		((XlimActorTemplateData) EcoreHelper.getContainerOfType(action,
				Actor.class).getTemplateData()).getCustomPeekedMapPerAction()
				.put(action, customPeekedMap);
		return null;
	}

	@Override
	public Object casePattern(Pattern pattern) {
		for (Port port : pattern.getPorts()) {
			Map<Integer, Var> indexToVariableMap = new HashMap<Integer, Var>();
			Var oldTarget = pattern.getVariable(port);

			List<Use> uses = new ArrayList<Use>(oldTarget.getUses());
			for (Use use : uses) {
				// Create a custom peek for each load of this variable
				InstLoad load = EcoreHelper.getContainerOfType(use,
						InstLoad.class);

				Var newVar = load.getTarget().getVariable();

				Expression indexExpr = load.getIndexes().get(0);
				if (indexExpr.isIntExpr()) {
					indexToVariableMap.put(((ExprInt) indexExpr).getIntValue(),
							newVar);
				} else {
					Actor actor = EcoreHelper.getContainerOfType(pattern,
							Actor.class);
					Action action = EcoreHelper.getContainerOfType(pattern,
							Action.class);
					throw new OrccRuntimeException(
							"One repeat and one guard on the same input port are forbidden with XLIM backend. \nActor: "
									+ actor.getName()
									+ " - Action: "
									+ action.getName());
				}
				IrUtil.delete(load);
			}
			EcoreUtil.remove(oldTarget);
			customPeekedMap.put(port, indexToVariableMap);
		}
		return null;
	}
}
