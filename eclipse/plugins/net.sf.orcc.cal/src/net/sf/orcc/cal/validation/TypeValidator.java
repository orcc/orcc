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

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementElsif;
import net.sf.orcc.cal.cal.AstStatementIf;
import net.sf.orcc.cal.cal.AstStatementWhile;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.util.TypeUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

/**
 * This class describes the validation of an RVC-CAL actor. The checks tagged as
 * "expensive" are only performed when the file is saved and before code
 * generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeValidator extends AbstractCalJavaValidator {

	private void checkActionGuards(AstAction action) {
		List<AstExpression> guards = action.getGuards();
		int index = 0;
		for (AstExpression guard : guards) {
			Type type = Util.getType(guard);
			if (!TypeUtil.isConvertibleTo(type,
					IrFactory.eINSTANCE.createTypeBool())) {
				error("Type mismatch: cannot convert from " + type + " to bool",
						action, eINSTANCE.getAstAction_Guards(), index);
			}
			index++;
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstAction(AstAction action) {
		checkActionGuards(action);
		checkActionOutputs(action.getOutputs());
	}

	/**
	 * Checks the token expressions are correctly typed.
	 * 
	 * @param outputs
	 *            the output patterns of an action
	 */
	private void checkActionOutputs(List<AstOutputPattern> outputs) {
		for (AstOutputPattern pattern : outputs) {
			AstPort port = pattern.getPort();

			Type portType = Util.getType(port);
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat == null) {
				List<AstExpression> values = pattern.getValues();
				int index = 0;
				for (AstExpression value : values) {
					Type type = Util.getType(value);
					if (!TypeUtil.isConvertibleTo(type, portType)) {
						error("this expression must be of type " + portType,
								pattern,
								eINSTANCE.getAstOutputPattern_Values(), index);
					}
					index++;
				}
			} else {
				int repeat = Util.getIntValue(astRepeat);
				if (repeat != 1) {
					// each value is supposed to be a list
					List<AstExpression> values = pattern.getValues();
					int index = 0;
					for (AstExpression value : values) {
						Type type = Util.getType(value);
						if (type.isList()) {
							TypeList typeList = (TypeList) type;
							Type lub = TypeUtil.getLub(portType,
									typeList.getType());
							if (lub != null && typeList.getSize() >= repeat) {
								continue;
							}
						}

						error("Type mismatch: expected " + portType + "["
								+ repeat + "]", pattern,
								eINSTANCE.getAstOutputPattern_Values(), index);
						index++;
					}
				}
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstExpressionCall(AstExpressionCall astCall) {
		AstFunction function = astCall.getFunction();
		String name = function.getName();

		EObject rootCter = EcoreUtil.getRootContainer(astCall);
		EObject rootCterFunction = EcoreUtil.getRootContainer(function);
		if (function.eContainer() instanceof AstActor
				&& rootCter != rootCterFunction) {
			// calling an actor's function from another actor/unit
			error("function " + name
					+ " cannot be called from another actor/unit", astCall,
					eINSTANCE.getAstExpressionCall_Function(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementAssign(AstStatementAssign assign) {
		AstVariable variable = assign.getTarget().getVariable();
		if (variable.isConstant()
				|| variable.eContainingFeature() == CalPackage.Literals.AST_ACTOR__PARAMETERS) {
			error("The variable " + variable.getName() + " is not assignable",
					eINSTANCE.getAstStatementAssign_Target());
		}

		// create expression
		AstExpressionIndex expression = CalFactory.eINSTANCE
				.createAstExpressionIndex();

		// set reference
		AstVariableReference reference = CalFactory.eINSTANCE
				.createAstVariableReference();
		reference.setVariable(variable);
		expression.setSource(reference);

		// copy indexes
		expression.getIndexes().addAll(EcoreUtil.copyAll(assign.getIndexes()));

		// check types
		Type targetType = Util.getType(expression);
		Type type = Util.getType(assign.getValue());
		if (!TypeUtil.isConvertibleTo(type, targetType)) {
			error("Type mismatch: cannot convert from " + type + " to "
					+ targetType, assign,
					eINSTANCE.getAstStatementAssign_Value(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementCall(AstStatementCall astCall) {
		AstProcedure procedure = astCall.getProcedure();
		String name = procedure.getName();
		List<AstExpression> parameters = astCall.getParameters();

		if (procedure.eContainer() == null) {
			if ("print".equals(name) || "println".equals(name)) {
				if (parameters.size() > 1) {
					error("built-in procedure " + name
							+ " takes at most one expression", astCall,
							eINSTANCE.getAstStatementCall_Procedure(), -1);
				}
			}

			return;
		}

		EObject rootCter = EcoreUtil.getRootContainer(astCall);
		EObject rootCterProcedure = EcoreUtil.getRootContainer(procedure);
		if (procedure.eContainer() instanceof AstActor
				&& rootCter != rootCterProcedure) {
			// calling an actor's procedure from another actor/unit
			error("procedure " + name
					+ " cannot be called from another actor/unit", astCall,
					eINSTANCE.getAstStatementCall_Procedure(), -1);
		}

		if (procedure.getParameters().size() != parameters.size()) {
			error("procedure " + name + " takes "
					+ procedure.getParameters().size() + " arguments.",
					astCall, eINSTANCE.getAstStatementCall_Procedure(), -1);
			return;
		}

		Iterator<AstVariable> itFormal = procedure.getParameters().iterator();
		Iterator<AstExpression> itActual = parameters.iterator();
		int index = 0;
		while (itFormal.hasNext() && itActual.hasNext()) {
			Type formalType = Util.getType(itFormal.next());
			AstExpression expression = itActual.next();
			Type actualType = Util.getType(expression);

			// check types
			if (!TypeUtil.isConvertibleTo(actualType, formalType)) {
				error("Type mismatch: cannot convert from " + actualType
						+ " to " + formalType, astCall,
						eINSTANCE.getAstStatementCall_Parameters(), index);
			}
			index++;
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstVariable(AstVariable variable) {
		AstExpression value = variable.getValue();
		if (value != null) {
			// check types
			Type targetType = Util.getType(variable);
			Type type = Util.getType(value);
			if (!TypeUtil.isConvertibleTo(type, targetType)) {
				error("Type mismatch: cannot convert from " + type + " to "
						+ targetType, variable,
						eINSTANCE.getAstVariable_Value(), -1);
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstFunction(final AstFunction function) {
		if (!function.isNative()) {
			checkReturnType(function);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementElsif(AstStatementElsif elsIf) {
		Type type = Util.getType(elsIf.getCondition());
		if (!TypeUtil.isConvertibleTo(type,
				IrFactory.eINSTANCE.createTypeBool())) {
			error("Type mismatch: cannot convert from " + type + " to bool",
					elsIf, eINSTANCE.getAstStatementElsif_Condition(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementIf(AstStatementIf astIf) {
		Type type = Util.getType(astIf.getCondition());
		if (!TypeUtil.isConvertibleTo(type,
				IrFactory.eINSTANCE.createTypeBool())) {
			error("Type mismatch: cannot convert from " + type + " to bool",
					astIf, eINSTANCE.getAstStatementIf_Condition(), -1);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstStatementWhile(AstStatementWhile astWhile) {
		Type type = Util.getType(astWhile.getCondition());
		if (!TypeUtil.isConvertibleTo(type,
				IrFactory.eINSTANCE.createTypeBool())) {
			error("Type mismatch: cannot convert from " + type + " to bool",
					astWhile, eINSTANCE.getAstStatementWhile_Condition(), -1);
		}
	}

	private void checkReturnType(AstFunction function) {
		Type returnType = Util.getType(function);
		Type expressionType = Util.getType(function.getExpression());
		if (!TypeUtil.isConvertibleTo(expressionType, returnType)) {
			error("Type mismatch: cannot convert from " + expressionType
					+ " to " + returnType, function,
					eINSTANCE.getAstFunction_Expression(), -1);
		}
	}

}
