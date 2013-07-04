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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * This class adds annotations to some IR elements. These annotations are used
 * later to produce valid HMPP pragmas.
 * 
 * @author Jérôme Gorin
 * @author Antoine Lorence
 * 
 */
public class SetHMPPAnnotations extends DfVisitor<Void> {

	Map<Procedure, String> codelets;

	private class InnerSetHMPPAnnotations extends AbstractIrVisitor<Void> {

		/**
		 * This the entry point for this transformation. It visits InstCall
		 * instances and check for ones having a "codelet" attribute. For these
		 * ones, it run methods to add HMPP attributes to all IR elements which
		 * needs.
		 */
		@Override
		public Void caseInstCall(InstCall call) {
			Procedure proc = call.getProcedure();

			// Procedure referenced by this call instruction must be run on the
			// graphic card using HMPP compiler
			if (proc.hasAttribute("codelet")) {

				// This will set attributes for pragmas codelet, group (only
				// once per procedure)
				if (!codelets.containsKey(proc)) {
					setCodeletAttribute(proc);
				}

				// This will set attributes for pragmas callsite, resident,
				// advancedload, delegatedstore
				setCallsiteAttribute(call, getGroupLabel(),
						getCodeletLabel(proc));
			}

			return null;
		}

		/**
		 * Set <b>codelet</b> attribute to given <i>proc</i> and updates its
		 * sub-attributes.
		 * 
		 * @param proc
		 */
		private void setCodeletAttribute(Procedure proc) {

			Attribute codeletAttr = proc.getAttribute("codelet");
			setGroupAttribute(getGroupLabel(), codeletAttr.getAttributes());

			Attribute grp = addGroupLabel(codeletAttr, getGroupLabel());
			Attribute cdlt = addCodeletLabel(grp, getCodeletLabel(proc));

			Map<String, String> cdltParams = new HashMap<String, String>();

			// Check if procedure parameter is not a scalar
			for (Param param : proc.getParameters()) {
				Var var = param.getVariable();
				if (var.getType().isList()) {

					String direction = isVarUsed(var, proc) ? "in" : "";
					direction += isVarDefined(var, proc) ? "out" : "";

					cdltParams.put(var.getName(), direction);
				}
			}

			cdlt.setObjectValue(cdltParams);
		}

		/**
		 * Add to given <i>call</i> a <b>callsite</b> attribute and add it a
		 * group label as child, a codelet label as sub-child. Call necessary
		 * methods to add other attributes to the call and the variables used
		 * inside the procedure called.
		 * 
		 * @param call
		 * @param grpLabel
		 * @param cdltLabel
		 */
		private void setCallsiteAttribute(InstCall call, String grpLabel,
				String cdltLabel) {

			Attribute callsite = call.getAttribute("callsite");
			if (callsite == null) {
				call.addAttribute("callsite");
				callsite = call.getAttribute("callsite");
			}
			Attribute grp = addGroupLabel(callsite, grpLabel);
			addCodeletLabel(grp, cdltLabel);

			// Check for state variables used or defined inside the procedure
			Procedure proc = call.getProcedure();
			Set<String> inVars = new HashSet<String>();
			Set<String> outVars = new HashSet<String>();
			for (Var var : actor.getStateVars()) {
				if (var.isAssignable()) {

					String direction = "";
					if (isVarUsed(var, proc)) {
						inVars.add(var.getName());
						direction += "in";
					}
					if (isVarDefined(var, proc)) {
						outVars.add(var.getName());
						direction += "out";
					}

					if (!direction.isEmpty()) {
						setResidentAttribute(var, grpLabel, direction);
					}
				}
			}

			setAdvanceloadAttribute(call, cdltLabel, grpLabel, inVars);
			setDelegatedstoreAttribute(call, cdltLabel, grpLabel, outVars);
		}

		/**
		 * Add to given <i>call</i> a <b>advancedload</b> attribute and add it a
		 * group label as child, a codelet label as sub-child. Given <i>vars</i>
		 * are added to codelet label's objectValue to determine variables to
		 * load after the call.
		 * 
		 * @param call
		 * @param cdltLabel
		 * @param grpLabel
		 * @param vars
		 */
		private void setAdvanceloadAttribute(InstCall call, String cdltLabel,
				String grpLabel, Set<String> vars) {

			Procedure parentProc = EcoreHelper.getContainerOfType(call,
					Procedure.class);

			Attribute delStore = parentProc.getAttribute("advancedload");
			if (delStore == null) {
				parentProc.addAttribute("advancedload");
				delStore = parentProc.getAttribute("advancedload");
			}

			Attribute grp = addGroupLabel(delStore, getGroupLabel());
			Attribute cdlt = addCodeletLabel(grp,
					getCodeletLabel(call.getProcedure()));

			cdlt.setObjectValue(vars);
		}

		/**
		 * Add to given <i>call</i> a <b>delegatedstore</b> attribute and add it
		 * a group label as child, a codelet label as sub-child. Given
		 * <i>vars</i> are added to codelet label's objectValue to determine
		 * variables to store before the call.
		 * 
		 * @param call
		 * @param cdltLabel
		 * @param grpLabel
		 * @param vars
		 */
		private void setDelegatedstoreAttribute(InstCall call,
				String cdltLabel, String grpLabel, Set<String> vars) {

			Procedure parentProc = EcoreHelper.getContainerOfType(call,
					Procedure.class);

			Attribute delStore = parentProc.getAttribute("delegatedstore");
			if (delStore == null) {
				parentProc.addAttribute("delegatedstore");
				delStore = parentProc.getAttribute("delegatedstore");
			}

			Attribute grp = addGroupLabel(delStore, getGroupLabel());
			Attribute cdlt = addCodeletLabel(grp,
					getCodeletLabel(call.getProcedure()));

			cdlt.setObjectValue(vars);
		}

		/**
		 * Add to current <i>actor</i> a <b>group</b> attribute and add it a
		 * group label as child. Given <i>parameters</i> are added to group
		 * label attribute's children.
		 * 
		 * @param grpLabel
		 * @param params
		 */
		private void setGroupAttribute(String grpLabel,
				Collection<Attribute> params) {

			Attribute grpPragma = actor.getAttribute("group");
			if (grpPragma == null) {
				actor.addAttribute("group");
				grpPragma = actor.getAttribute("group");
			}

			Attribute grp = addGroupLabel(grpPragma, grpLabel);
			grp.getAttributes().addAll(params);
		}

		/**
		 * Add to given <i>variable</i> a <b>resident</b> attribute and add it a
		 * group label as child. Parameters are added to group label attribute's
		 * children. The direction (in/out/inout) is currently the only
		 * parameter supported. Value is given by <i>direction</i> parameter.
		 * 
		 * @param var
		 * @param grpLabel
		 * @param direction
		 */
		private void setResidentAttribute(Var var, String grpLabel,
				String direction) {

			Attribute resident = var.getAttribute("resident");
			if (resident == null) {
				var.addAttribute("resident");
				resident = var.getAttribute("resident");
			}

			Attribute grp = addGroupLabel(resident, grpLabel);

			grp.setAttribute("direction", direction);
		}

		/**
		 * Add a group label to a given attribute
		 * 
		 * @param attribute
		 * @param grpLabel
		 *            The group label
		 * @return The Attribute just created
		 */
		private Attribute addGroupLabel(Attribute attribute, String grpLabel) {
			attribute.addAttribute(grpLabel);
			return attribute.getAttribute(grpLabel);
		}

		/**
		 * Add a codelet label to a given attribute
		 * 
		 * @param attribute
		 * @param cdltLabel
		 *            The codelet label
		 * @return The Attribute just created
		 */
		private Attribute addCodeletLabel(Attribute attribute, String cdltLabel) {
			attribute.addAttribute(cdltLabel);
			return attribute.getAttribute(cdltLabel);
		}

		/**
		 * Generate and returns a group label. This method is now used to ensure
		 * the same name will be used everywhere. It should be modified when
		 * HMPP backend will support having multiple groups defined in a file.
		 * 
		 * @return A default group label
		 */
		private String getGroupLabel() {
			return "<grp_default>";
		}

		/**
		 * Return the codelet label for this procedure. This method will NOT
		 * update procedure to add missing attributes.
		 * 
		 * @param proc
		 * @return
		 */
		private String getCodeletLabel(Procedure proc) {

			if (codelets.containsKey(proc)) {
				return codelets.get(proc);
			} else if (proc.hasAttribute("codelet")
					&& proc.getAttribute("codelet").hasAttribute(
							"codelet_label")) {
				String name = proc.getAttribute("codelet").getValueAsString(
						"codelet_label");
				codelets.put(proc, name);
				return name;
			} else {
				String name = "cdlt_" + proc.getName();
				codelets.put(proc, name);
				return name;
			}
		}

		/**
		 * Return true if the given variable is used in the given procedure.
		 * (i.e. the given procedure contains uses on the variable)
		 * 
		 * @param var
		 * @param proc
		 * @return
		 */
		private boolean isVarUsed(Var var, Procedure proc) {
			for (Use use : var.getUses()) {
				if (EcoreHelper.getContainerOfType(use, Procedure.class)
						.equals(proc)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Return true if the given variable is defined in the given procedure.
		 * (i.e. the given procedure contains defs on the variable)
		 * 
		 * @param var
		 * @param proc
		 * @return
		 */
		private boolean isVarDefined(Var var, Procedure proc) {
			for (Def def : var.getDefs()) {
				if (EcoreHelper.getContainerOfType(def, Procedure.class)
						.equals(proc)) {
					return true;
				}
			}
			return false;
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
