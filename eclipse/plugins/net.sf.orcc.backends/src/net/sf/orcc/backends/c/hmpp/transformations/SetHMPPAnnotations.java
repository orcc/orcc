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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a HMMP annotations.
 * 
 * @author Jérôme Gorin
 * 
 */
public class SetHMPPAnnotations extends DfVisitor<Void> {
	Map<Procedure, Integer> codelets;
	int codeletsCnt;

	private class InnerSetHMPPAnnotations extends AbstractIrVisitor<Void> {

		/**
		 * Add hmpp "callsite" pragma for call instructions related to
		 * procedures decorated with hmpp "codelet" pragma
		 */
		@Override
		public Void caseInstCall(InstCall call) {
			Procedure proc = call.getProcedure();
			Attribute attribute = proc.getAttribute("codelet");

			if (attribute != null) {

				// Set callsite
				call.setAttribute("hmpp", "#pragma hmpp "
						+ getCodeletName(proc) + " callsite");

				// Set codelet
				createCodelet(call.getProcedure(), call.getArguments(),
						attribute);

				// Set persistents variables
				createPersistentVars(call);
			}

			return null;
		}

		@Override
		public Void caseBlockWhile(BlockWhile blockWhile) {
			super.caseBlockWhile(blockWhile);

			Attribute attribute = blockWhile.getAttribute("gridify");

			if (attribute != null) {

				createGridify(blockWhile, attribute);
			}

			return null;
		}

		private void createCodelet(Procedure proc, EList<Arg> args,
				Attribute codelet) {
			String codeletName = getCodeletName(proc);

			// Set procedure pragma
			String procPragma = "#pragma hmpp " + codeletName + " codelet";
			ExprList values = (ExprList) codelet.getContainedValue();

			if (values != null) {
				for (int i = 0; i < values.getSize(); i++) {
					ExprList parameter = (ExprList) values.get(i);
					procPragma = procPragma + ", "
							+ ((ExprString) parameter.get(0)).getValue();

					if (parameter.getSize() > 0) {
						procPragma = procPragma + " = "
								+ ((ExprString) parameter.get(1)).getValue();
					}
				}
			}

			// Check if a parameter is an actor state
			for (Arg arg : args) {
				if (arg.isByVal()) {
					Expression value = ((ArgByVal) arg).getValue();
					if (value.isExprVar()) {
						Var var = ((ExprVar) value).getUse().getVariable();
						if (var.isGlobal() && var.isAssignable()) {

							// Get corresponding parameter name
							Param param = proc.getParameters().get(
									args.indexOf(arg));

							procPragma = procPragma + ", args["
									+ param.getVariable().getName()
									+ "].io = inout";
						}
					}

				}
			}

			// Set procedure attribute
			codelet.setName(procPragma);

		}

		private void createGridify(BlockWhile blockWhile, Attribute gridify) {
			// Set gridify annotation
			String gridPragma = "#pragma hmppcg gridify (";
			ExprList args = (ExprList) gridify.getContainedValue();

			if (args != null) {
				for (int i = 0; i < args.getSize(); i++) {

					// Get induction variable
					ExprList parameter = (ExprList) args.get(i);
					String inductionVar = "";
					Expression paramExpr = parameter.get(0);

					if (paramExpr instanceof ExprString) {
						inductionVar = ((ExprString) paramExpr).getValue();
					} else if (paramExpr instanceof ExprVar) {
						inductionVar = ((ExprVar) paramExpr).getUse()
								.getVariable().getName();
					}

					gridPragma = gridPragma + inductionVar;

					if (i != args.getSize() - 1) {
						gridPragma += ", ";
					}
				}
			}
			gridPragma += ")";

			gridify.setName(gridPragma);

		}

		private void createPersistentVars(InstCall call) {
			List<Var> persistentVar = new ArrayList<Var>();
			Procedure proc = call.getProcedure();
			String codeletName = getCodeletName(proc);

			// Check if a state variable is persistent inside the procedure
			for (Var var : actor.getStateVars()) {
				if (var.isAssignable()) {
					for (Use use : var.getUses()) {
						if (EcoreHelper
								.getContainerOfType(use, Procedure.class)
								.equals(proc)
								&& !persistentVar.contains(var)) {
							persistentVar.add(var);
						}
					}
				}
			}

			if (persistentVar.isEmpty()) {
				// The codelet has no persistent variable
				return;
			}

			// Set persistent process
			actor.setAttribute("hmpp", "#pragma hmpp " + codeletName
					+ " group, target=cuda, transfer=manual");
			List<Instruction> prepare = new ArrayList<Instruction>();
			List<Instruction> close = new ArrayList<Instruction>();

			// Create acquire
			prepare.add(createInstrAttribute("#pragma hmpp " + codeletName
					+ " acquire"));

			for (Var var : persistentVar) {
				// Set variable attribute
				var.setAttribute("hmpp", "#pragma hmpp " + codeletName
						+ " resident, args[" + var.getName() + "].io=inout");

				// Create advancedload
				prepare.add(createInstrAttribute("#pragma hmpp " + codeletName
						+ " advancedload args[" + var.getName()
						+ "], hostdata = \"" + var.getName() + "\""));

				// Create delegatedstore
				close.add(createInstrAttribute("#pragma hmpp " + codeletName
						+ " delegatedstore args[" + var.getName()
						+ "], hostdata = \"" + var.getName() + "\""));
			}

			// Create release
			close.add(createInstrAttribute("#pragma hmpp " + codeletName
					+ " release"));

			// Getting current node block and add all
			BlockBasic currentBlock = EcoreHelper.getContainerOfType(call,
					BlockBasic.class);
			currentBlock.getInstructions().addAll(currentBlock.indexOf(call),
					prepare);
			currentBlock.getInstructions().addAll(
					currentBlock.indexOf(call) + 1, close);
		}

		private String getCodeletName(Procedure proc) {

			if (!codelets.containsKey(proc)) {
				codelets.put(proc, codeletsCnt++);
			}

			return "codeletlabel" + codelets.get(proc);
		}

		private Instruction createInstrAttribute(String attrStr) {
			/*
			 * IrInstSpecific instSpecific = IrSpecificFactory.eINSTANCE
			 * .createIrInstSpecific(); instSpecific.setAttribute(attrStr,
			 * null);
			 * 
			 * return instSpecific;
			 */
			return null;
		}
	}

	public SetHMPPAnnotations() {
		super();
		codelets = new HashMap<Procedure, Integer>();
		codeletsCnt = 0;

		irVisitor = new InnerSetHMPPAnnotations();
	}

	@Override
	public Void caseActor(Actor actor) {
		codelets = new HashMap<Procedure, Integer>();
		codeletsCnt = 0;
		this.actor = actor;

		super.caseActor(actor);

		return null;
	}

}
