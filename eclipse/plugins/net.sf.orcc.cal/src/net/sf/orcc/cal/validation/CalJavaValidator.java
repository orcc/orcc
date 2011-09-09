/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.services.AstExpressionEvaluator;
import net.sf.orcc.cal.type.TypeCycleDetector;
import net.sf.orcc.cal.type.TypeTransformer;
import net.sf.orcc.cal.type.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.cal.util.VoidSwitch;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import com.google.inject.Inject;

/**
 * This class describes the validation of an RVC-CAL actor. The checks tagged as
 * "expensive" are only performed when the file is saved and before code
 * generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalJavaValidator extends AbstractCalJavaValidator {

	public class FullAstEvaluator extends VoidSwitch {

		private List<ValidationError> errors;

		private List<EObject> seen;

		/**
		 * Creates a new type checker.
		 */
		public FullAstEvaluator(List<ValidationError> errors) {
			this.errors = errors;
			seen = new ArrayList<EObject>();
		}

		@Override
		public Void caseAstExpressionCall(AstExpressionCall call) {
			AstFunction function = call.getFunction();
			if (!seen.contains(function)) {
				seen.add(function);
				doSwitch(function);
			}
			return super.caseAstExpressionCall(call);
		}

		@Override
		public Void caseAstStatementCall(AstStatementCall call) {
			AstProcedure procedure = call.getProcedure();
			if (!seen.contains(procedure)) {
				doSwitch(procedure);
				seen.add(procedure);
			}
			return super.caseAstStatementCall(call);
		}

		@Override
		public Void caseAstVariable(AstVariable variable) {
			AstExpression expression = variable.getValue();
			if ((variable.eContainer() instanceof AstActor || variable
					.eContainer() instanceof AstUnit) && (expression != null)) {
				// only evaluate if it has not been done before
				if (Util.getValue(variable) == null) {
					Expression value = new AstExpressionEvaluator(errors)
							.evaluate(expression);
					Util.putValue(variable, value);
				}
			}

			return null;
		}

		@Override
		public Void caseAstVariableReference(AstVariableReference reference) {
			doSwitch(reference.getVariable());
			return null;
		}

	}

	public class FullAstTyper extends VoidSwitch {

		private List<ValidationError> errors;

		private List<EObject> seen;

		/**
		 * Creates a new type checker.
		 */
		public FullAstTyper(List<ValidationError> errors) {
			this.errors = errors;
			seen = new ArrayList<EObject>();
		}

		@Override
		public Void caseAstExpression(AstExpression expression) {
			// only type if it has not been done before
			if (Util.getType(expression) == null) {
				Type type = new Typer(errors).getType(expression);
				Util.putType(expression, type);
			}
			return null;
		}

		@Override
		public Void caseAstExpressionCall(AstExpressionCall call) {
			AstFunction function = call.getFunction();
			if (!seen.contains(function)) {
				seen.add(function);
				doSwitch(function);
			}
			return super.caseAstExpressionCall(call);
		}

		@Override
		public Void caseAstStatementCall(AstStatementCall call) {
			AstProcedure procedure = call.getProcedure();
			if (!seen.contains(procedure)) {
				doSwitch(procedure);
				seen.add(procedure);
			}
			return super.caseAstStatementCall(call);
		}

		@Override
		public Void caseAstVariableReference(AstVariableReference reference) {
			doSwitch(reference.getVariable());
			return null;
		}

	}

	@Inject
	private StructuralValidator structuralValidator;

	@Inject
	private TypeValidator typeValidator;

	/**
	 * Creates a new CAL validator written in Java.
	 */
	public CalJavaValidator() {
	}

	@Check(CheckType.NORMAL)
	public void checkAstEntity(AstEntity entity) {
		// to hold errors
		List<ValidationError> errors = new ArrayList<ValidationError>(0);

		// check there are no cycles in type definitions
		if (new TypeCycleDetector().detectCycles(entity, errors)) {
			showErrors(errors);

			// don't perform further checks, because cyclic type definitions
			// mess things up
			return;
		}

		// empty cache
		Util.removeCacheForURI(EcoreUtil.getURI(entity));

		// evaluates constants and initial value of state variables
		new FullAstEvaluator(errors).doSwitch(entity);

		// converts all types
		// will evaluate expressions when necessary
		new TypeTransformer(errors).doSwitch(entity);

		// then type every expression
		new FullAstTyper(errors).doSwitch(entity);

		// finally, perform structural validation and type checking
		structuralValidator.validate(entity, getChain(), getContext());
		typeValidator.validate(entity, getChain(), getContext());
	}

	private void showErrors(List<ValidationError> errors) {
		for (ValidationError error : errors) {
			error(error.getMessage(), error.getSource(), error.getFeature(),
					error.getIndex());
		}
	}

}
