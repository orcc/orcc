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

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.cal.CalDiagnostic;
import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.ExpressionElsif;
import net.sf.orcc.cal.cal.ExpressionIf;
import net.sf.orcc.cal.cal.ExpressionIndex;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Guard;
import net.sf.orcc.cal.cal.OutputPattern;
import net.sf.orcc.cal.cal.StatementAssign;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.StatementElsif;
import net.sf.orcc.cal.cal.StatementIf;
import net.sf.orcc.cal.cal.StatementWhile;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.VariableReference;
import net.sf.orcc.cal.services.Evaluator;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.util.TypePrinter;
import net.sf.orcc.ir.util.TypeUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

/**
 * This class describes a validator that performs type checking for an RVC-CAL
 * actor/unit. The checks tagged as "normal" are only performed when the file is
 * saved and before code generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeValidator extends AbstractCalJavaValidator {

	private final TypePrinter printer;

	public TypeValidator() {
		printer = new TypePrinter();
	}

	private void checkActionGuard(Guard guard) {

		if (guard == null)
			return;

		int index = 0;
		for (AstExpression guardExpression : guard.getExpressions()) {
			Type type = Typer.getType(guardExpression);
			if (!TypeUtil.isConvertibleTo(type,
					IrFactory.eINSTANCE.createTypeBool())) {
				error("Type mismatch: cannot convert from " + print(type)
						+ " to bool", guard, eINSTANCE.getGuard_Expressions(),
						index, CalDiagnostic.ERROR_TYPE_CONVERSION);
			}
			index++;
		}
	}

	/**
	 * Checks the token expressions are correctly typed.
	 * 
	 * @param outputs
	 *            the output patterns of an action
	 */
	private void checkActionOutputs(List<OutputPattern> outputs) {
		for (OutputPattern pattern : outputs) {
			AstPort port = pattern.getPort();

			Type portType = Typer.getType(port);
			AstExpression astRepeat = pattern.getRepeat();
			if (astRepeat == null) {
				List<AstExpression> values = pattern.getValues();
				int index = 0;
				for (AstExpression value : values) {
					Type type = Typer.getType(value);
					if (!TypeUtil.isConvertibleTo(type, portType)) {
						error("this expression must be of type "
								+ print(portType), pattern,
								eINSTANCE.getOutputPattern_Values(), index);
					}
					index++;
				}
			} else {
				int repeat = Evaluator.getIntValue(astRepeat);
				if (repeat != 1) {
					// each value is supposed to be a list
					List<AstExpression> values = pattern.getValues();
					int index = 0;
					for (AstExpression value : values) {
						Type type = Typer.getType(value);
						if (type.isList()) {
							TypeList typeList = (TypeList) type;
							Type lub = TypeUtil.getLub(portType,
									typeList.getType());
							if (lub != null && typeList.getSize() >= repeat) {
								continue;
							}
						}

						error("Type mismatch: expected " + print(portType)
								+ "[" + repeat + "]", pattern,
								eINSTANCE.getOutputPattern_Values(), index);
						index++;
					}
				}
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkAstAction(AstAction action) {
		checkActionGuard(action.getGuard());
		checkActionOutputs(action.getOutputs());
	}

	@Check(CheckType.NORMAL)
	public void checkExpressionBinary(ExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		checkTypeBinary(op, Typer.getType(expression.getLeft()),
				Typer.getType(expression.getRight()), expression,
				eINSTANCE.getExpressionBinary_Operator());
	}

	@Check(CheckType.NORMAL)
	public void checkExpressionCall(ExpressionCall call) {
		Function function = call.getFunction();

		String name = function.getName();
		List<AstExpression> parameters = call.getParameters();
		if (function.getParameters().size() != parameters.size()) {
			error("function " + name + " takes "
					+ function.getParameters().size() + " arguments.", call,
					eINSTANCE.getExpressionCall_Function());
			return;
		}

		Iterator<Variable> itFormal = function.getParameters().iterator();
		Iterator<AstExpression> itActual = parameters.iterator();
		int index = 0;
		while (itFormal.hasNext() && itActual.hasNext()) {
			Type formalType = Typer.getType(itFormal.next());
			AstExpression expression = itActual.next();
			Type actualType = Typer.getType(expression);

			// check types
			if (!TypeUtil.isConvertibleTo(actualType, formalType)) {
				error("Type mismatch: cannot convert from " + print(actualType)
						+ " to " + print(formalType), call,
						eINSTANCE.getExpressionCall_Parameters(), index,
						CalDiagnostic.ERROR_TYPE_CONVERSION);
			}
			index++;
		}
	}

	@Check(CheckType.NORMAL)
	public void checkExpressionElsif(ExpressionElsif expression) {
		Type type = Typer.getType(expression.getCondition());
		if (type == null || !type.isBool()) {
			error("Cannot convert " + type + " to bool", expression,
					eINSTANCE.getExpressionElsif_Condition());
		}
	}

	@Check(CheckType.NORMAL)
	public void checkExpressionIf(ExpressionIf expression) {
		Type type = Typer.getType(expression.getCondition());
		if (type == null || !type.isBool()) {
			error("Cannot convert " + print(type) + " to bool", expression,
					eINSTANCE.getExpressionIf_Condition());
		}

		Type typeThen = Typer.getType(expression.getThen());
		type = typeThen;
		int index = 0;
		for (ExpressionElsif elsif : expression.getElsifs()) {
			Type typeElsif = Typer.getType(elsif);
			type = TypeUtil.getLub(type, typeElsif);
			if (type == null) {
				error("Type mismatch: cannot convert " + print(typeElsif)
						+ " to " + print(typeThen), expression,
						eINSTANCE.getExpressionIf_Elsifs(), index,
						CalDiagnostic.ERROR_TYPE_CONVERSION);
			}
			index++;
		}

		Type typeElse = Typer.getType(expression.getElse());
		type = TypeUtil.getLub(type, typeElse);
		if (type == null) {
			error("Type mismatch: cannot convert " + print(typeElse) + " to "
					+ print(type), expression, eINSTANCE.getExpressionIf_Else(),
					CalDiagnostic.ERROR_TYPE_CONVERSION);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkExpressionIndex(ExpressionIndex expression) {
		Variable variable = expression.getSource().getVariable();
		Type type = Typer.getType(variable);

		List<AstExpression> indexes = expression.getIndexes();
		int errorIdx = 0;
		for (AstExpression index : indexes) {
			Type subType = Typer.getType(index);
			if (type != null && type.isList()) {
				if (subType.isInt() || subType.isUint()) {
					type = ((TypeList) type).getType();
				} else {
					error("index must be an integer", expression,
							eINSTANCE.getExpressionIndex_Indexes(), errorIdx);
				}
			} else {
				error("Cannot convert " + print(type) + " to List", expression,
						eINSTANCE.getExpressionIndex_Source());
			}
			errorIdx++;
		}
	}

	@Check(CheckType.NORMAL)
	public void checkExpressionUnary(ExpressionUnary expression) {
		OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());
		Type type = Typer.getType(expression.getExpression());
		if (type == null) {
			return;
		}

		switch (op) {
		case BITNOT:
			if (!(type.isInt() || type.isUint())) {
				error("Cannot convert " + print(type) + " to int/uint",
						expression, eINSTANCE.getExpressionUnary_Expression(),
						-1);
			}
			break;
		case LOGIC_NOT:
			if (!type.isBool()) {
				error("Cannot convert " + print(type) + " to boolean",
						expression, eINSTANCE.getExpressionUnary_Expression(),
						-1);
			}
			break;
		case MINUS:
			if (!type.isUint() && !type.isInt() && !type.isFloat()) {
				error("Cannot convert " + print(type) + " to int or float",
						expression, eINSTANCE.getExpressionUnary_Expression(),
						-1);
			}
			break;
		case NUM_ELTS:
			if (!type.isList()) {
				error("Cannot convert " + print(type) + " to List", expression,
						eINSTANCE.getExpressionUnary_Expression());
			}
			break;
		default:
			error("Unknown unary operator", expression,
					eINSTANCE.getExpressionUnary_Expression());
		}
	}

	@Check(CheckType.NORMAL)
	public void checkFunction(final Function function) {
		if (!Util.hasAnnotation("native", function.getAnnotations())) {
			checkReturnType(function);
		}
	}

	private void checkReturnType(Function function) {
		Type returnType = Typer.getType(function);
		Type expressionType = Typer.getType(function.getExpression());
		if (!TypeUtil.isConvertibleTo(expressionType, returnType)
				&& expressionType != null) {
			error("Type mismatch: cannot convert from " + print(expressionType)
					+ " to " + print(returnType), function,
					eINSTANCE.getFunction_Expression(),
					CalDiagnostic.ERROR_TYPE_CONVERSION);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkStatementAssign(StatementAssign assign) {
		Variable variable = assign.getTarget().getVariable();
		if (!Util.isAssignable(variable)) {
			error("The variable " + variable.getName() + " is not assignable",
					eINSTANCE.getStatementAssign_Target(),
					CalDiagnostic.ERROR_CONSTANT_ASSIGNATION);
		}

		// create expression
		ExpressionIndex expression = CalFactory.eINSTANCE
				.createExpressionIndex();

		// set reference
		VariableReference reference = CalFactory.eINSTANCE
				.createVariableReference();
		reference.setVariable(variable);
		expression.setSource(reference);

		// copy indexes
		expression.getIndexes().addAll(EcoreUtil.copyAll(assign.getIndexes()));

		// check types
		Type targetType = Typer.getType(expression);
		AstExpression value = assign.getValue();
		if (value != null) {
			Type type = Typer.getType(value);
			if (!TypeUtil.isConvertibleTo(type, targetType) && type != null) {
				error("Type mismatch: cannot convert from " + print(type)
						+ " to " + print(targetType), assign,
						eINSTANCE.getStatementAssign_Value(),
						CalDiagnostic.ERROR_TYPE_CONVERSION);
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkStatementCall(StatementCall call) {
		AstProcedure procedure = call.getProcedure();
		String name = procedure.getName();
		List<AstExpression> arguments = call.getArguments();

		if (procedure.eContainer() == null) {
			if ("print".equals(name) || "println".equals(name)) {
				if (arguments.size() > 1) {
					error("built-in procedure " + name
							+ " takes at most one expression", call,
							eINSTANCE.getStatementCall_Procedure());
				}
			}

			return;
		}

		if (procedure.getParameters().size() != arguments.size()) {
			error("procedure " + name + " takes "
					+ procedure.getParameters().size() + " arguments.", call,
					eINSTANCE.getStatementCall_Procedure());
			return;
		}

		Iterator<Variable> itFormal = procedure.getParameters().iterator();
		Iterator<AstExpression> itActual = arguments.iterator();
		int index = 0;
		while (itFormal.hasNext() && itActual.hasNext()) {
			Type formalType = Typer.getType(itFormal.next());
			AstExpression expression = itActual.next();
			Type actualType = Typer.getType(expression);

			// check types
			if (!TypeUtil.isConvertibleTo(actualType, formalType)
					&& actualType != null) {
				error("Type mismatch: cannot convert from " + print(actualType)
						+ " to " + print(formalType), call,
						eINSTANCE.getStatementCall_Arguments(), index,
						CalDiagnostic.ERROR_TYPE_CONVERSION);
			}
			index++;
		}
	}

	@Check(CheckType.NORMAL)
	public void checkStatementElsif(StatementElsif elsIf) {
		Type type = Typer.getType(elsIf.getCondition());
		if (!TypeUtil.isConvertibleTo(type,
				IrFactory.eINSTANCE.createTypeBool())
				&& type != null) {
			error("Type mismatch: cannot convert from " + print(type)
					+ " to bool", elsIf,
					eINSTANCE.getStatementElsif_Condition(),
					CalDiagnostic.ERROR_TYPE_CONVERSION);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkStatementIf(StatementIf stmtIf) {
		Type type = Typer.getType(stmtIf.getCondition());
		if (!TypeUtil.isConvertibleTo(type,
				IrFactory.eINSTANCE.createTypeBool())
				&& type != null) {
			error("Type mismatch: cannot convert from " + print(type)
					+ " to bool", stmtIf, eINSTANCE.getStatementIf_Condition(),
					CalDiagnostic.ERROR_TYPE_CONVERSION);
		}
	}

	@Check(CheckType.NORMAL)
	public void checkStatementWhile(StatementWhile stmtWhile) {
		Type type = Typer.getType(stmtWhile.getCondition());
		if (!TypeUtil.isConvertibleTo(type,
				IrFactory.eINSTANCE.createTypeBool())
				&& type != null) {
			error("Type mismatch: cannot convert from " + print(type)
					+ " to bool", stmtWhile,
					eINSTANCE.getStatementWhile_Condition(),
					CalDiagnostic.ERROR_TYPE_CONVERSION);
		}
	}

	/**
	 * Check that the type of a binary expression whose left operand has type t1
	 * and right operand has type t2, and whose operator is given, is correct.
	 * 
	 * @param op
	 *            operator
	 * @param t1
	 *            type of the first operand
	 * @param t2
	 *            type of the second operand
	 * @param source
	 *            source object
	 * @param feature
	 *            feature
	 */
	private void checkTypeBinary(OpBinary op, Type t1, Type t2, EObject source,
			EStructuralFeature feature) {
		if (t1 == null || t2 == null) {
			return;
		}

		if (t1.isList() || t2.isList()) {
			error("Binary operations on Lists are not supported", source,
					feature);
		}

		switch (op) {
		case BITAND:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + print(t1) + " to int/uint", source,
						feature);
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + print(t2) + " to int/uint", source,
						feature);
			}
			break;

		case BITOR:
		case BITXOR:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + print(t1) + " to int/uint", source,
						feature);
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + print(t2) + " to int/uint", source,
						feature);
			}
			break;

		case TIMES:
			if (!t1.isInt() && !t1.isUint() && !t1.isFloat()) {
				error("Cannot convert " + print(t1) + " to int/uint/float",
						source, feature);
			}
			if (!t2.isInt() && !t2.isUint() && !t2.isFloat()) {
				error("Cannot convert " + print(t2) + " to int/uint/float",
						source, feature);
			}
			break;

		case MINUS:
			if (!t1.isInt() && !t1.isUint() && !t1.isFloat()) {
				error("Cannot convert " + print(t1) + " to int/uint/float",
						source, feature);
			}
			if (!t2.isInt() && !t2.isUint() && !t2.isFloat()) {
				error("Cannot convert " + print(t2) + " to int/uint/float",
						source, feature);
			}
			break;

		case PLUS:
			if (t1.isBool() && !t2.isString() || !t1.isString() && t2.isBool()) {
				error("Addition is not defined for booleans", source, feature);
			}
			break;

		case DIV:
		case DIV_INT:
		case SHIFT_RIGHT:
			if (!t1.isInt() && !t1.isUint() && !t1.isFloat()) {
				error("Cannot convert " + print(t1) + " to int/uint/float",
						source, feature);
			}
			if (!t2.isInt() && !t2.isUint() && !t2.isFloat()) {
				error("Cannot convert " + print(t2) + " to int/uint/float",
						source, feature);
			}
			break;

		case MOD:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + print(t1) + " to int/uint", source,
						feature);
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + print(t2) + " to int/uint", source,
						feature);
			}
			break;

		case SHIFT_LEFT:
			if (!t1.isInt() && !t1.isUint()) {
				error("Cannot convert " + print(t1) + " to int/uint", source,
						feature);
			}
			if (!t2.isInt() && !t2.isUint()) {
				error("Cannot convert " + print(t2) + " to int/uint", source,
						feature);
			}
			break;

		case EQ:
		case GE:
		case GT:
		case LE:
		case LT:
		case NE:
			Type type = TypeUtil.getLub(t1, t2);
			if (type == null) {
				error("Incompatible operand types " + print(t1) + " and "
						+ print(t2), source, feature);
			}
			break;

		case EXP:
			error("Operator ** not implemented", source, feature);
			break;

		case LOGIC_AND:
		case LOGIC_OR:
			if (!t1.isBool()) {
				error("Cannot convert " + print(t1) + " to bool", source,
						feature);
			}
			if (!t2.isBool()) {
				error("Cannot convert " + print(t2) + " to bool", source,
						feature);
			}
			break;

		default:
			break;
		}
	}

	@Check(CheckType.NORMAL)
	public void checkTypeInt(AstTypeInt type) {
		AstExpression size = type.getSize();
		if (size != null) {
			int value = Evaluator.getIntValue(size);
			if (value <= 0) {
				error("This size must evaluate to a compile-time "
						+ "constant greater than zero", type,
						eINSTANCE.getAstTypeInt_Size(),
						CalDiagnostic.ERROR_TYPE_SYNTAX);
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkTypeList(AstTypeList type) {
		AstExpression size = type.getSize();
		if (size != null) {
			int value = Evaluator.getIntValue(size);
			if (value <= 0) {
				error("This size must evaluate to a compile-time "
						+ "constant greater than zero", type,
						eINSTANCE.getAstTypeList_Size());
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkTypeUint(AstTypeUint type) {
		AstExpression size = type.getSize();
		if (size != null) {
			int value = Evaluator.getIntValue(size);
			if (value <= 0) {
				error("This size must evaluate to a compile-time "
						+ "constant greater than zero", type,
						eINSTANCE.getAstTypeUint_Size());
			}
		}
	}

	@Check(CheckType.NORMAL)
	public void checkVariable(Variable variable) {
		AstExpression value = variable.getValue();
		if (value != null) {
			// check types
			Type targetType = Typer.getType(variable);
			Type type = Typer.getType(value);
			if (!TypeUtil.isConvertibleTo(type, targetType)) {
				error("Type mismatch: cannot convert from " + print(type)
						+ " to " + print(targetType), variable,
						eINSTANCE.getVariable_Value(),
						CalDiagnostic.ERROR_TYPE_CONVERSION);
			}
		}
	}

	private String print(Type type) {
		if (type == null) {
			return "null";
		}
		return printer.doSwitch(type);
	}

}
