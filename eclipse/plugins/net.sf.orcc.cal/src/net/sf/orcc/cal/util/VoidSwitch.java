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

import org.eclipse.emf.ecore.EObject;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionElsif;
import net.sf.orcc.cal.cal.AstExpressionFloat;
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
import net.sf.orcc.cal.cal.AstStatementElsif;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstStatementIf;
import net.sf.orcc.cal.cal.AstStatementWhile;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
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
		for (AstInputPattern input : action.getInputs()) {
			doSwitch(input);
		}

		for (AstExpression guard : action.getGuards()) {
			doSwitch(guard);
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
		for (AstVariable parameter : actor.getParameters()) {
			doSwitch(parameter);
		}

		for (AstVariable stateVariable : actor.getStateVariables()) {
			doSwitch(stateVariable);
		}

		for (AstFunction function : actor.getFunctions()) {
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
	public Void caseAstExpressionElsif(AstExpressionElsif expression) {
		doSwitch(expression.getCondition());
		doSwitch(expression.getThen());

		return null;
	}

	@Override
	public Void caseAstExpressionFloat(AstExpressionFloat expression) {
		return null;
	}

	@Override
	public Void caseAstExpressionIf(AstExpressionIf expression) {
		doSwitch(expression.getCondition());
		doSwitch(expression.getThen());

		for (AstExpressionElsif elsif : expression.getElsifs()) {
			doSwitch(elsif);
		}

		doSwitch(expression.getElse());

		return null;
	}

	@Override
	public Void caseAstExpressionIndex(AstExpressionIndex expression) {
		doSwitch(expression.getSource());

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
		for (AstGenerator generator : expression.getGenerators()) {
			doSwitch(generator);
		}

		for (AstExpression subExpression : expression.getExpressions()) {
			doSwitch(subExpression);
		}

		return null;
	}

	@Override
	public Void caseAstExpressionString(AstExpressionString expression) {
		return null;
	}

	@Override
	public Void caseAstExpressionUnary(AstExpressionUnary expression) {
		return doSwitch(expression.getExpression());
	}

	@Override
	public Void caseAstExpressionVariable(AstExpressionVariable expression) {
		return doSwitch(expression.getValue());
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

		doSwitch(input.getRepeat());

		return null;
	}

	@Override
	public Void caseAstOutputPattern(AstOutputPattern output) {
		doSwitch(output.getPort());

		for (AstExpression value : output.getValues()) {
			doSwitch(value);
		}

		doSwitch(output.getRepeat());

		return null;
	}

	@Override
	public Void caseAstPort(AstPort port) {
		return doSwitch(port.getType());
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
	public Void caseAstStatementElsif(AstStatementElsif stmtIf) {
		doSwitch(stmtIf.getCondition());

		for (AstStatement statement : stmtIf.getThen()) {
			doSwitch(statement);
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
		
		for (AstStatementElsif statement : stmtIf.getElsifs()) {
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
		for (AstFunction function : unit.getFunctions()) {
			doSwitch(function);
		}

		for (AstVariable variable : unit.getVariables()) {
			doSwitch(variable);
		}

		return null;
	}

	@Override
	public Void caseAstVariable(AstVariable variable) {
		doSwitch(variable.getType());
		return doSwitch(variable.getValue());
	}

	@Override
	public Void caseAstVariableReference(AstVariableReference reference) {
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
