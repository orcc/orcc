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
package net.sf.orcc.cal.validation;

import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstPriority;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.util.BooleanSwitch;
import net.sf.orcc.cal.util.Util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;

/**
 * This class describes the validation of an RVC-CAL actor. The checks tagged as
 * "expensive" are only performed when the file is saved and before code
 * generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalJavaValidator extends AbstractCalJavaValidator {

	@Check
	public void checkPriorities(AstPriority priority) {

	}

	@Check
	public void checkIsFunctionUsed(final AstFunction function) {
		try {
			boolean used = new BooleanSwitch() {

				@Override
				public Boolean caseAstExpressionCall(
						AstExpressionCall expression) {
					if (expression.getFunction().equals(function)) {
						return true;
					}

					return super.caseAstExpressionCall(expression);
				}

			}.doSwitch(Util.getActor(function));

			if (!used) {
				warning("Unused function", CalPackage.AST_FUNCTION__NAME);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Check
	public void checkIsProcedureUsed(final AstProcedure procedure) {
		try {
			boolean used = new BooleanSwitch() {

				@Override
				public Boolean caseAstStatementCall(AstStatementCall call) {
					if (call.getProcedure().equals(procedure)) {
						return true;
					}

					return false;
				}

			}.doSwitch(Util.getActor(procedure));

			if (!used) {
				warning("Unused procedure", CalPackage.AST_PROCEDURE__NAME);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Check
	public void checkIsVariabledUsed(final AstVariable variable) {
		// do not take variables declared by input patterns and
		// generator/foreach
		EObject container = variable.eContainer();
		if (container instanceof AstInputPattern
				|| container instanceof AstGenerator
				|| container instanceof AstStatementForeach) {
			return;
		}

		try {
			boolean used = new BooleanSwitch() {

				@Override
				public Boolean caseAstStatementAssign(AstStatementAssign assign) {
					if (assign.getTarget().getVariable().equals(variable)) {
						return true;
					}

					return super.caseAstStatementAssign(assign);
				}

				@Override
				public Boolean caseAstExpressionVariable(
						AstExpressionVariable expression) {
					return expression.getValue().getVariable().equals(variable);
				}

				@Override
				public Boolean caseAstExpressionIndex(
						AstExpressionIndex expression) {
					if (expression.getSource().getVariable().equals(variable)) {
						return true;
					}

					return super.caseAstExpressionIndex(expression);
				}

			}.doSwitch(Util.getActor(variable));

			if (!used) {
				warning("Unused variable", CalPackage.AST_VARIABLE__NAME);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
