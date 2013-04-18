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
package net.sf.orcc.backends.c.hmpp.transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.util.EcoreHelper;

import org.apache.commons.lang.StringUtils;

/**
 * This class defines a HMMP annotations.
 * 
 * @author Jérôme Gorin
 * 
 */
public class SetHMPPAnnotations extends DfVisitor<Void> {

	Map<Procedure, String> codelets;

	private class InnerSetHMPPAnnotations extends AbstractIrVisitor<Void> {

		/**
		 * Add hmpp "callsite" pragma for call instructions related to
		 * procedures decorated with hmpp "codelet" pragma
		 */
		@Override
		public Void caseInstCall(InstCall call) {
			Procedure proc = call.getProcedure();

			if (proc.hasAttribute("codelet")) {

				Attribute codelet = proc.getAttribute("codelet");
				// Set "codelet" label
				if (codelet.hasAttribute("codelet_label")) {
					setCodeletName(proc,
							codelet.getValueAsString("codelet_label"));
				} else {
					codelet.setAttribute("codelet_label", getCodeletName(proc));
				}

				// Set "codelet" missing parameters
				setCodeletParameters(proc, codelet);

				// Set "callsite" directive on call instruction
				call.addAttribute("callsite");
				call.getAttribute("callsite").setAttribute("grp_label",
						getCodeletName(proc));
			}

			return null;
		}

		/**
		 * Add missing parameters to a given codelet, according given proc
		 * parameters type. Non scalars
		 * 
		 * @param proc
		 * @param codelet
		 */
		private void setCodeletParameters(Procedure proc, Attribute codelet) {

			// Explode existing codelet params as a list of String
			List<String> cdltParams = Arrays.asList(codelet.getValueAsString(
					"params").split(","));

			// Check if procedure parameter is not a scalar
			for (Param param : proc.getParameters()) {
				if (param.getVariable().getType().isList()) {
					// TODO: classify arguments according to their usage: in /
					// out / inout
					String newCodeletParam = "args["
							+ param.getVariable().getName() + "].io = inout";

					if (!cdltParams.contains(newCodeletParam)) {
						cdltParams.add(newCodeletParam);
					}
				}
			}

			// Restore codelets params by joining them with ", " separator
			codelet.setAttribute("params", StringUtils.join(cdltParams, ", "));

		}

		/**
		 * If a BlockWhile has an hmppcg "gridify" attribute, it must be
		 * consistent with variable names, even if they have been modified by
		 * another transformation. That's why attribute stringValue is updated.
		 */
		@Override
		public Void caseBlockWhile(BlockWhile blockWhile) {
			super.caseBlockWhile(blockWhile);

			if (blockWhile.hasAttribute("gridify")) {

				Attribute gridify = blockWhile.getAttribute("gridify");

				if (gridify.hasAttribute("params")
						&& gridify.getAttribute("params").getContainedValue() != null) {

					ExprList varList = (ExprList) gridify
							.getAttribute("params").getContainedValue();

					List<String> updatedVarNames = new ArrayList<String>();
					for (Expression expr : varList.getValue()) {
						if (expr instanceof ExprVar) {
							updatedVarNames.add(((ExprVar) expr).getUse()
									.getVariable().getName());
						}
					}

					gridify.setAttribute("params",
							StringUtils.join(updatedVarNames, ","));
				}
			}

			return null;
		}

		/**
		 * Add all pragmas needed to perform manual transfers (hmpp) on global
		 * variables used in procedure defined in the actor and decorated with
		 * codelet/callsite attribute. This method has to be fixed since it does
		 * not produce valid HMPP pragmas. We don't want to delete it because
		 * the code can be reused in a future version of the backend.
		 * 
		 * @param call
		 *            Call instruction decorated with an hmpp "callsite" pragma
		 */
		@SuppressWarnings("unused")
		private void createPersistentVars(InstCall call) {
			Set<Var> persistentVar = new HashSet<Var>();
			Procedure proc = call.getProcedure();
			String codeletName = getCodeletName(proc);

			// Check if a state variable is persistent inside the procedure
			for (Var var : actor.getStateVars()) {
				if (var.isAssignable()) {
					for (Use use : var.getUses()) {
						if (EcoreHelper
								.getContainerOfType(use, Procedure.class)
								.equals(proc)) {
							persistentVar.add(var);
						}
					}
					for (Def def : var.getDefs()) {
						if (EcoreHelper
								.getContainerOfType(def, Procedure.class)
								.equals(proc)) {
							persistentVar.add(var);
						}
					}
				}
			}

			// The codelet has no persistent variable
			if (persistentVar.isEmpty()) {
				return;
			}

			// Set persistent process. Adds
			// "#pragma hmpp <name> group, target=cuda, transfer=manual"
			// before actor declaration
			actor.addAttribute("group");
			actor.getAttribute("group").setAttribute("grp_label", codeletName);
			actor.getAttribute("group").setAttribute("params",
					"target=cuda, transfer=manual");

			// Create acquire. Adds "#pragma hmpp <codeletName> acquire" before
			// call instruction
			actor.addAttribute("acquire");
			actor.getAttribute("acquire").addAttribute("first");
			actor.getAttribute("acquire")
					.setAttribute("grp_label", codeletName);

			// Create release. Adds "#pragma hmpp <codeletName> release" after
			// call
			actor.addAttribute("release");
			actor.getAttribute("release").addAttribute("last");
			actor.getAttribute("release")
					.setAttribute("grp_label", codeletName);

			for (Var var : persistentVar) {
				// Set variable attribute. Adds
				// "#pragma hmpp <codeleltName> resident, args[<var>].io=inout"
				// before each variable declaration
				var.addAttribute("resident");
				var.getAttribute("resident").setAttribute("grp_label",
						codeletName);
				var.getAttribute("resident").setAttribute("params",
						"args[" + var.getName() + "].io=inout");

				// Create advancedload. Adds
				// "#pragma hmpp <codeletName> advancedload, args[<var>], hostdata="<var>""
				// before call for each var
				call.addAttribute("advancedload");
				call.getAttribute("advancedload").setAttribute("grp_label",
						codeletName);
				call.getAttribute("advancedload").setAttribute(
						"params",
						"args[" + var.getName() + "], hostdata = \""
								+ var.getName() + "\"");

				// Create delegatedstore. Adds
				// "#pragma hmpp <codeletName> delegatedstore, args[<var>], hostdata="<var>""
				// after call for each var
				call.addAttribute("delegatedstore");
				call.getAttribute("delegatedstore").addAttribute("after");
				call.getAttribute("delegatedstore").setAttribute("grp_label",
						codeletName);
				call.getAttribute("delegatedstore").setAttribute(
						"params",
						"args[" + var.getName() + "], hostdata = \""
								+ var.getName() + "\"");
			}
		}

		private void setCodeletName(Procedure proc, String name) {
			codelets.put(proc, name);
		}

		private String getCodeletName(Procedure proc) {
			String name = codelets.get(proc);
			if (name == null) {
				name = "codeletlabel" + codelets.size();
				setCodeletName(proc, name);
			}
			return name;
		}
	}

	public SetHMPPAnnotations() {
		super();
		codelets = new HashMap<Procedure, String>();
		irVisitor = new InnerSetHMPPAnnotations();
	}

	@Override
	public Void caseActor(Actor actor) {
		codelets.clear();
		this.actor = actor;

		super.caseActor(actor);

		return null;
	}

}
