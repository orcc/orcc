/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.StatementAssign;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.StatementForeach;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.VariableReference;
import net.sf.orcc.cal.util.BooleanSwitch;
import net.sf.orcc.cal.util.Util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

/**
 * This class describes a validator that computes warnings for an RVC-CAL
 * actor/unit.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class WarningValidator extends AbstractCalJavaValidator {

	@Check(CheckType.NORMAL)
	public void checkImport(Import import_) {
		
		
	}
	
	@Check(CheckType.NORMAL)
	public void checkAstProcedure(final AstProcedure procedure) {
		boolean used = new BooleanSwitch() {

			@Override
			public Boolean caseStatementCall(StatementCall call) {
				if (call.getProcedure().equals(procedure)) {
					return true;
				}

				return false;
			}

		}.doSwitch(Util.getTopLevelContainer(procedure));

		if (!used && procedure.eContainer() instanceof AstActor
				&& !procedure.isNative()) {
			warning("The procedure " + procedure.getName() + " is never called",
					procedure, eINSTANCE.getAstProcedure_Name(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkFunction(final Function function) {
		// do not check functions of a unit
		if (function.eContainer() instanceof AstUnit) {
			return;
		}

		boolean used = new BooleanSwitch() {

			@Override
			public Boolean caseExpressionCall(ExpressionCall expression) {
				if (expression.getFunction().equals(function)) {
					return true;
				}

				return super.caseExpressionCall(expression);
			}

		}.doSwitch(Util.getTopLevelContainer(function));

		if (!used && !function.isNative()) {
			warning("The function " + function.getName() + " is never called",
					function, eINSTANCE.getFunction_Name(), -1);
		}
	}

	/**
	 * Checks that the given variable is used. If it is not, issue a warning.
	 * 
	 * @param variable
	 *            a variable
	 */
	private void checkIsVariableUsed(final Variable variable) {
		EObject container = variable.eContainer();
		if (container instanceof InputPattern || container instanceof Generator
				|| container instanceof StatementForeach) {
			// do not warn about these variables because it is ok if they are
			// not used
			return;
		} else if (container instanceof AstUnit) {
			// variables in unit, too, because they may be used in other
			// entities
			return;
		} else if (container instanceof Function) {
			Function function = (Function) variable.eContainer();
			if (function.isNative()) {
				// parameters in native functions are not read in CAL
				return;
			}
		} else if (container instanceof AstProcedure) {
			AstProcedure procedure = (AstProcedure) variable.eContainer();
			if (procedure.isNative()) {
				// parameters in native procedures are not read in CAL
				return;
			}
		}

		EReference reference = variable.eContainmentFeature();
		AstEntity entity = EcoreUtil2.getContainerOfType(variable,
				AstEntity.class);
		if (reference == eINSTANCE.getAstActor_Parameters()
				&& entity.isNative()) {
			// parameters in native actors are not read in CAL
			return;
		}

		boolean isRead = new BooleanSwitch() {

			@Override
			public Boolean caseVariableReference(VariableReference ref) {
				return ref.getVariable().equals(variable);
			}

		}.doSwitch(entity);

		boolean isWritten = new BooleanSwitch() {

			@Override
			public Boolean caseStatementAssign(StatementAssign assign) {
				return assign.getTarget().getVariable().equals(variable);
			}

		}.doSwitch(entity);

		// do not warn about unused actor parameters
		// used for system actors
		if (!isRead && !isWritten) {
			warning("The variable " + variable.getName() + " is never used",
					variable, eINSTANCE.getVariable_Name(), -1);
		} else if (!isRead) {
			warning("The variable " + variable.getName() + " is never read",
					variable, eINSTANCE.getVariable_Name(), -1);
		} else if (!isWritten) {
			if (variable.isConstant()
					|| reference == eINSTANCE.getAstActor_Parameters()
					|| reference == eINSTANCE.getFunction_Parameters()
					|| reference == eINSTANCE.getAstProcedure_Parameters()) {
				return;
			}

			warning("The variable " + variable.getName() + " is never written",
					variable, eINSTANCE.getVariable_Name(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkVariable(Variable variable) {
		checkIsVariableUsed(variable);
	}

}
