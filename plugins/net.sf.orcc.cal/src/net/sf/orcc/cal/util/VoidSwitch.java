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
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIf;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionList;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstOutputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstStatement;
import net.sf.orcc.cal.cal.AstStatementAssign;
import net.sf.orcc.cal.cal.AstStatementCall;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstStatementIf;
import net.sf.orcc.cal.cal.AstStatementWhile;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;

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
		for (AstExpression guard : action.getGuards()) {
			doSwitch(guard);
		}

		for (AstInputPattern input : action.getInputs()) {
			doSwitch(input);
		}

		for (AstVariable variable : action.getVariables()) {
			doSwitch(variable);
		}

		for (AstStatement statement : action.getStatements()) {
			doSwitch(statement);
		}

		for (AstOutputPattern output : action.getOutputs()) {
			doSwitch(output);
		}

		return null;
	}

	@Override
	public Void caseAstActor(AstActor actor) {
		for (AstAction action : actor.getActions()) {
			doSwitch(action);
		}

		for (AstFunction function : actor.getFunctions()) {
			doSwitch(function);
		}

		for (AstAction action : actor.getInitializes()) {
			doSwitch(action);
		}

		for (AstPort port : actor.getInputs()) {
			doSwitch(port);
		}

		for (AstPort port : actor.getOutputs()) {
			doSwitch(port);
		}

		for (AstVariable parameter : actor.getParameters()) {
			doSwitch(parameter);
		}

		for (AstProcedure procedure : actor.getProcedures()) {
			doSwitch(procedure);
		}

		for (AstVariable parameter : actor.getStateVariables()) {
			doSwitch(parameter);
		}

		return null;
	}

	@Override
	public Void caseAstExpressionBinary(AstExpressionBinary expression) {
		doSwitch(expression.getLeft());
		doSwitch(expression.getRight());

		return null;
	}

	@Override
	public Void caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return null;
	}

	@Override
	public Void caseAstExpressionCall(AstExpressionCall call) {
		for (AstExpression parameter : call.getParameters()) {
			doSwitch(parameter);
		}

		return null;
	}

	@Override
	public Void caseAstExpressionIf(AstExpressionIf expression) {
		doSwitch(expression.getCondition());
		doSwitch(expression.getThen());
		doSwitch(expression.getElse());

		return null;
	}

	@Override
	public Void caseAstExpressionIndex(AstExpressionIndex expression) {
		for (AstExpression index : expression.getIndexes()) {
			doSwitch(index);
		}

		return null;
	}

	@Override
	public Void caseAstExpressionInteger(AstExpressionInteger expression) {
		return null;
	}

	@Override
	public Void caseAstExpressionList(AstExpressionList expression) {
		for (AstExpression subExpression : expression.getExpressions()) {
			doSwitch(subExpression);
		}

		for (AstGenerator generator : expression.getGenerators()) {
			doSwitch(generator);
		}

		return null;
	}

	@Override
	public Void caseAstExpressionString(AstExpressionString expression) {
		return null;
	}

	@Override
	public Void caseAstExpressionUnary(AstExpressionUnary expression) {
		doSwitch(expression.getExpression());
		return null;
	}

	@Override
	public Void caseAstExpressionVariable(AstExpressionVariable expression) {
		return null;
	}

	@Override
	public Void caseAstFunction(AstFunction function) {
		for (AstVariable parameter : function.getParameters()) {
			doSwitch(parameter);
		}

		for (AstVariable variable : function.getVariables()) {
			doSwitch(variable);
		}

		doSwitch(function.getType());
		doSwitch(function.getExpression());

		return null;
	}

	@Override
	public Void caseAstGenerator(AstGenerator generator) {
		doSwitch(generator.getVariable());
		doSwitch(generator.getLower());
		doSwitch(generator.getHigher());
		return null;
	}

	@Override
	public Void caseAstInputPattern(AstInputPattern input) {
		doSwitch(input.getPort());

		for (AstVariable token : input.getTokens()) {
			doSwitch(token);
		}

		AstExpression repeat = input.getRepeat();
		if (repeat != null) {
			doSwitch(repeat);
		}

		return null;
	}

	@Override
	public Void caseAstOutputPattern(AstOutputPattern output) {
		doSwitch(output.getPort());

		for (AstExpression value : output.getValues()) {
			doSwitch(value);
		}

		AstExpression repeat = output.getRepeat();
		if (repeat != null) {
			doSwitch(repeat);
		}

		return null;
	}

	@Override
	public Void caseAstPort(AstPort port) {
		doSwitch(port.getType());
		return null;
	}

	@Override
	public Void caseAstProcedure(AstProcedure procedure) {
		for (AstVariable parameter : procedure.getParameters()) {
			doSwitch(parameter);
		}

		for (AstVariable variable : procedure.getVariables()) {
			doSwitch(variable);
		}

		for (AstStatement statement : procedure.getStatements()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseAstStatementAssign(AstStatementAssign assign) {
		for (AstExpression index : assign.getIndexes()) {
			doSwitch(index);
		}

		doSwitch(assign.getValue());

		return null;
	}

	@Override
	public Void caseAstStatementCall(AstStatementCall call) {
		for (AstExpression parameter : call.getParameters()) {
			doSwitch(parameter);
		}

		return null;
	}

	@Override
	public Void caseAstStatementForeach(AstStatementForeach foreach) {
		doSwitch(foreach.getVariable());
		doSwitch(foreach.getLower());
		doSwitch(foreach.getHigher());

		for (AstStatement statement : foreach.getStatements()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseAstStatementIf(AstStatementIf stmtIf) {
		doSwitch(stmtIf.getCondition());

		for (AstStatement statement : stmtIf.getThen()) {
			doSwitch(statement);
		}

		for (AstStatement statement : stmtIf.getElse()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseAstStatementWhile(AstStatementWhile stmtWhile) {
		doSwitch(stmtWhile.getCondition());

		for (AstStatement statement : stmtWhile.getStatements()) {
			doSwitch(statement);
		}

		return null;
	}

	@Override
	public Void caseAstTypeBool(AstTypeBool type) {
		return null;
	}

	@Override
	public Void caseAstTypeFloat(AstTypeFloat type) {
		return null;
	}

	@Override
	public Void caseAstTypeInt(AstTypeInt type) {
		AstExpression value = type.getSize();
		if (value == null) {
			return null;
		} else {
			return doSwitch(value);
		}
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
		AstExpression value = type.getSize();
		if (value == null) {
			return null;
		} else {
			return doSwitch(value);
		}
	}

	@Override
	public Void caseAstVariable(AstVariable variable) {
		doSwitch(variable.getType());

		AstExpression value = variable.getValue();
		if (value == null) {
			return null;
		} else {
			return doSwitch(value);
		}
	}

}
