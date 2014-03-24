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
package net.sf.orcc.cal.util;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeDouble;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeHalf;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.ExpressionElsif;
import net.sf.orcc.cal.cal.ExpressionFloat;
import net.sf.orcc.cal.cal.ExpressionIf;
import net.sf.orcc.cal.cal.ExpressionIndex;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.OutputPattern;
import net.sf.orcc.cal.cal.Statement;
import net.sf.orcc.cal.cal.StatementAssign;
import net.sf.orcc.cal.cal.StatementCall;
import net.sf.orcc.cal.cal.StatementElsif;
import net.sf.orcc.cal.cal.StatementForeach;
import net.sf.orcc.cal.cal.StatementIf;
import net.sf.orcc.cal.cal.StatementWhile;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.VariableReference;
import net.sf.orcc.cal.cal.util.CalSwitch;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a basic switch that visits everything. Case methods should
 * be overridden to implement the desired behavior.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class VoidSwitch extends CalSwitch<Void> {

	@Override
	public Void caseAstAction(AstAction action) {
		for (InputPattern input : action.getInputs()) {
			doSwitch(input);
		}

		for (AstExpression guard : action.getGuards()) {
			doSwitch(guard);
		}

		for (Variable variable : action.getVariables()) {
			doSwitch(variable);
		}

		for (Statement statement : action.getStatements()) {
			doSwitch(statement);
		}

		for (OutputPattern output : action.getOutputs()) {
			doSwitch(output);
		}

		return null;
	}

	@Override
	public Void caseAstActor(AstActor actor) {
		for (Variable parameter : actor.getParameters()) {
			doSwitch(parameter);
		}

		for (Variable stateVariable : actor.getStateVariables()) {
			doSwitch(stateVariable);
		}

		for (Function function : actor.getFunctions()) {
			doSwitch(function);
		}

		for (AstProcedure procedure : actor.getProcedures()) {
			doSwitch(procedure);
		}

		for (AstPort port : actor.getInputs()) {
			doSwitch(port);
		}

		for (AstPort port : actor.getOutputs()) {
			doSwitch(port);
		}

		for (AstAction action : actor.getActions()) {
			doSwitch(action);
		}

		for (AstAction action : actor.getInitializes()) {
			doSwitch(action);
		}

		return null;
	}

	@Override
	public Void caseAstEntity(AstEntity entity) {
		AstActor actor = entity.getActor();
		if (actor == null) {
			AstUnit unit = entity.getUnit();
			doSwitch(unit);
		} else {
			doSwitch(actor);
		}

		return null;
	}

	@Override
	public Void caseAstPort(AstPort port) {
		return doSwitch(port.getType());
	}

	@Override
	public Void caseAstProcedure(AstProcedure procedure) {
		for (Variable parameter : procedure.getParameters()) {
			doSwitch(parameter);
		}

		for (Variable variable : procedure.getVariables()) {
			doSwitch(variable);
		}

		for (Statement statement : procedure.getStatements()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseAstTypeBool(AstTypeBool type) {
		return null;
	}

	@Override
	public Void caseAstTypeDouble(AstTypeDouble type) {
		return null;
	}

	@Override
	public Void caseAstTypeFloat(AstTypeFloat type) {
		return null;
	}

	@Override
	public Void caseAstTypeHalf(AstTypeHalf type) {
		return null;
	}

	@Override
	public Void caseAstTypeInt(AstTypeInt type) {
		return doSwitch(type.getSize());
	}

	@Override
	public Void caseAstTypeList(AstTypeList type) {
		doSwitch(type.getType());
		doSwitch(type.getSize());

		return null;
	}

	@Override
	public Void caseAstTypeString(AstTypeString type) {
		return null;
	}

	@Override
	public Void caseAstTypeUint(AstTypeUint type) {
		return doSwitch(type.getSize());
	}

	@Override
	public Void caseAstUnit(AstUnit unit) {
		for (Function function : unit.getFunctions()) {
			doSwitch(function);
		}

		for (AstProcedure procedure : unit.getProcedures()) {
			doSwitch(procedure);
		}

		for (Variable variable : unit.getVariables()) {
			doSwitch(variable);
		}

		return null;
	}

	@Override
	public Void caseExpressionBinary(ExpressionBinary expression) {
		doSwitch(expression.getLeft());
		doSwitch(expression.getRight());

		return null;
	}

	@Override
	public Void caseExpressionBoolean(ExpressionBoolean expression) {
		return null;
	}

	@Override
	public Void caseExpressionCall(ExpressionCall call) {
		for (AstExpression parameter : call.getParameters()) {
			doSwitch(parameter);
		}

		return null;
	}

	@Override
	public Void caseExpressionElsif(ExpressionElsif expression) {
		doSwitch(expression.getCondition());
		doSwitch(expression.getThen());

		return null;
	}

	@Override
	public Void caseExpressionFloat(ExpressionFloat expression) {
		return null;
	}

	@Override
	public Void caseExpressionIf(ExpressionIf expression) {
		doSwitch(expression.getCondition());
		doSwitch(expression.getThen());

		for (ExpressionElsif elsif : expression.getElsifs()) {
			doSwitch(elsif);
		}

		doSwitch(expression.getElse());

		return null;
	}

	@Override
	public Void caseExpressionIndex(ExpressionIndex expression) {
		doSwitch(expression.getSource());

		for (AstExpression index : expression.getIndexes()) {
			doSwitch(index);
		}

		return null;
	}

	@Override
	public Void caseExpressionInteger(ExpressionInteger expression) {
		return null;
	}

	@Override
	public Void caseExpressionList(ExpressionList expression) {
		for (Generator generator : expression.getGenerators()) {
			doSwitch(generator);
		}

		for (AstExpression subExpression : expression.getExpressions()) {
			doSwitch(subExpression);
		}

		return null;
	}

	@Override
	public Void caseExpressionString(ExpressionString expression) {
		return null;
	}

	@Override
	public Void caseExpressionUnary(ExpressionUnary expression) {
		return doSwitch(expression.getExpression());
	}

	@Override
	public Void caseExpressionVariable(ExpressionVariable expression) {
		return doSwitch(expression.getValue());
	}

	@Override
	public Void caseFunction(Function function) {
		for (Variable parameter : function.getParameters()) {
			doSwitch(parameter);
		}

		for (Variable variable : function.getVariables()) {
			doSwitch(variable);
		}

		doSwitch(function.getType());
		doSwitch(function.getExpression());

		return null;
	}

	@Override
	public Void caseGenerator(Generator generator) {
		doSwitch(generator.getVariable());
		doSwitch(generator.getLower());
		doSwitch(generator.getHigher());
		return null;
	}

	@Override
	public Void caseInputPattern(InputPattern input) {
		doSwitch(input.getPort());

		for (Variable token : input.getTokens()) {
			doSwitch(token);
		}

		doSwitch(input.getRepeat());

		return null;
	}

	@Override
	public Void caseOutputPattern(OutputPattern output) {
		doSwitch(output.getPort());

		for (AstExpression value : output.getValues()) {
			doSwitch(value);
		}

		doSwitch(output.getRepeat());

		return null;
	}

	@Override
	public Void caseStatementAssign(StatementAssign assign) {
		for (AstExpression index : assign.getIndexes()) {
			doSwitch(index);
		}

		doSwitch(assign.getValue());

		return null;
	}

	@Override
	public Void caseStatementCall(StatementCall call) {
		for (AstExpression argument : call.getArguments()) {
			doSwitch(argument);
		}

		return null;
	}

	@Override
	public Void caseStatementElsif(StatementElsif stmtIf) {
		doSwitch(stmtIf.getCondition());

		for (Statement statement : stmtIf.getThen()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseStatementForeach(StatementForeach foreach) {
		doSwitch(foreach.getVariable());
		doSwitch(foreach.getLower());
		doSwitch(foreach.getHigher());

		for (Statement statement : foreach.getStatements()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseStatementIf(StatementIf stmtIf) {
		doSwitch(stmtIf.getCondition());

		for (Statement statement : stmtIf.getThen()) {
			doSwitch(statement);
		}

		for (StatementElsif statement : stmtIf.getElsifs()) {
			doSwitch(statement);
		}

		for (Statement statement : stmtIf.getElse()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseStatementWhile(StatementWhile stmtWhile) {
		doSwitch(stmtWhile.getCondition());

		for (Statement statement : stmtWhile.getStatements()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseVariable(Variable variable) {
		doSwitch(variable.getType());

		for (AstExpression dim : variable.getDimensions()) {
			doSwitch(dim);
		}

		return doSwitch(variable.getValue());
	}

	@Override
	public Void caseVariableReference(VariableReference reference) {
		return null;
	}

	@Override
	public Void doSwitch(EObject theEObject) {
		if (theEObject == null) {
			return null;
		} else {
			return super.doSwitch(theEObject);
		}
	}

}
