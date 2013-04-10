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

import java.util.Arrays;
import java.util.List;

import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.Attribute;

/**
 * This class add a list of variables to attributes on while blocks annotated
 * with hmppcg "gridify" pragma. These reference to variables is used later (see
 * SetHMPPAnnotations) to update attribute params and ensure parameters will be
 * consistent with variables in code, even if they have been modified by another
 * transformation.
 * 
 * @author Jérôme Gorin
 * 
 */
public class PrepareHMPPAnnotations extends AbstractIrVisitor<Void> {

	/**
	 * This visitor search in a model element for a given variable name. When it
	 * ends, it can return a reference to the corresponding Var object.
	 * 
	 * @author Jérôme Gorin
	 * 
	 */
	private class ExprVarGetter extends AbstractIrVisitor<Void> {
		private ExprVar result;
		private String varName;

		public ExprVarGetter() {
			// Visit also expressions
			super(true);
		}

		public void setVariableName(String varName) {
			this.varName = varName;
		}

		@Override
		public Void caseExprVar(ExprVar expr) {
			if (expr.getUse().getVariable().getName().compareTo(varName) == 0) {
				result = expr;
			}

			return null;
		}

		public ExprVar getResult() {
			return result;
		}
	}

	@Override
	public Void caseBlockWhile(BlockWhile blockWhile) {
		super.caseBlockWhile(blockWhile);

		Attribute attribute = blockWhile.getAttribute("gridify");
		ExprVarGetter exprVarGetter = new ExprVarGetter();

		if (attribute != null && attribute.hasAttribute("params")) {

			List<String> varNames = Arrays.asList(attribute
					.getAttribute("params").getStringValue().split(","));

			for (String varName : varNames) {
				exprVarGetter.setVariableName(varName);
				exprVarGetter.doSwitch(blockWhile);

				if (exprVarGetter.getResult() != null) {

					if (attribute.getAttribute("params").getContainedValue() == null) {

						attribute.getAttribute("params").setContainedValue(
								IrFactory.eINSTANCE.createExprList());
					}
					ExprList variablesList = (ExprList) attribute.getAttribute(
							"params").getContainedValue();

					variablesList.getValue().add(exprVarGetter.getResult());

				}
			}

		}

		return null;

	}
}
