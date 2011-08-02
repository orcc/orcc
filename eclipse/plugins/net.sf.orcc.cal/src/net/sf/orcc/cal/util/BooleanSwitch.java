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
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstStatementIf;
import net.sf.orcc.cal.cal.AstStatementWhile;
import net.sf.orcc.cal.cal.AstType;
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
 * This class defines a basic switch that visits everything until
 * {@link #doSwitch(org.eclipse.emf.ecore.EObject)} returns <code>true</code>.
 * Case methods should be overridden to implement predicates.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class BooleanSwitch extends CalSwitch<Boolean> {

	@Override
	public Boolean caseAstAction(AstAction action) {
		for (AstInputPattern input : action.getInputs()) {
			if (doSwitch(input)) {
				return true;
			}
		}

		for (AstExpression guard : action.getGuards()) {
			if (doSwitch(guard)) {
				return true;
			}
		}

		for (AstVariable variable : action.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statement : action.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		for (AstOutputPattern output : action.getOutputs()) {
			if (doSwitch(output)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstActor(AstActor actor) {
		for (AstAction action : actor.getActions()) {
			if (doSwitch(action)) {
				return true;
			}
		}

		for (AstFunction function : actor.getFunctions()) {
			if (doSwitch(function)) {
				return true;
			}
		}

		for (AstAction action : actor.getInitializes()) {
			if (doSwitch(action)) {
				return true;
			}
		}

		for (AstPort port : actor.getInputs()) {
			if (doSwitch(port)) {
				return true;
			}
		}

		for (AstPort port : actor.getOutputs()) {
			if (doSwitch(port)) {
				return true;
			}
		}

		for (AstVariable parameter : actor.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		for (AstProcedure procedure : actor.getProcedures()) {
			if (doSwitch(procedure)) {
				return true;
			}
		}

		for (AstVariable parameter : actor.getStateVariables()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstEntity(AstEntity entity) {
		AstActor actor = entity.getActor();
		if (actor == null) {
			AstUnit unit = entity.getUnit();
			return doSwitch(unit);
		} else {
			return doSwitch(actor);
		}
	}

	@Override
	public Boolean caseAstExpressionBinary(AstExpressionBinary expression) {
		if (doSwitch(expression.getLeft()) || doSwitch(expression.getRight())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return false;
	}

	@Override
	public Boolean caseAstExpressionCall(AstExpressionCall call) {
		for (AstExpression parameter : call.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionFloat(AstExpressionFloat expression) {
		return false;
	}

	@Override
	public Boolean caseAstExpressionIf(AstExpressionIf expression) {
		if (doSwitch(expression.getCondition())
				|| doSwitch(expression.getThen())
				|| doSwitch(expression.getElse())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionIndex(AstExpressionIndex expression) {
		if (doSwitch(expression.getSource())) {
			return true;
		}
		
		for (AstExpression index : expression.getIndexes()) {
			if (doSwitch(index)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionInteger(AstExpressionInteger expression) {
		return false;
	}

	@Override
	public Boolean caseAstExpressionList(AstExpressionList expression) {
		for (AstExpression subExpression : expression.getExpressions()) {
			if (doSwitch(subExpression)) {
				return true;
			}
		}

		for (AstGenerator generator : expression.getGenerators()) {
			if (doSwitch(generator)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstExpressionString(AstExpressionString expression) {
		return false;
	}

	@Override
	public Boolean caseAstExpressionUnary(AstExpressionUnary expression) {
		return doSwitch(expression.getExpression());
	}

	@Override
	public Boolean caseAstExpressionVariable(AstExpressionVariable expression) {
		return doSwitch(expression.getValue());
	}

	@Override
	public Boolean caseAstFunction(AstFunction function) {
		for (AstVariable parameter : function.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		for (AstVariable variable : function.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		return doSwitch(function.getType())
				|| doSwitch(function.getExpression());
	}

	@Override
	public Boolean caseAstGenerator(AstGenerator generator) {
		return (doSwitch(generator.getVariable())
				|| doSwitch(generator.getLower()) || doSwitch(generator
				.getHigher()));
	}

	@Override
	public Boolean caseAstInputPattern(AstInputPattern input) {
		if (doSwitch(input.getPort())) {
			return true;
		}

		for (AstVariable token : input.getTokens()) {
			if (doSwitch(token)) {
				return true;
			}
		}

		if (doSwitch(input.getRepeat())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstOutputPattern(AstOutputPattern output) {
		if (doSwitch(output.getPort())) {
			return true;
		}

		for (AstExpression value : output.getValues()) {
			if (doSwitch(value)) {
				return true;
			}
		}

		if (doSwitch(output.getRepeat())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseAstPort(AstPort port) {
		return doSwitch(port.getType());
	}

	@Override
	public Boolean caseAstProcedure(AstProcedure procedure) {
		for (AstVariable parameter : procedure.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		for (AstVariable variable : procedure.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		for (AstStatement statement : procedure.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementAssign(AstStatementAssign assign) {
		for (AstExpression index : assign.getIndexes()) {
			if (doSwitch(index)) {
				return true;
			}
		}

		return doSwitch(assign.getValue());
	}

	@Override
	public Boolean caseAstStatementCall(AstStatementCall call) {
		for (AstExpression parameter : call.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementForeach(AstStatementForeach foreach) {
		if (doSwitch(foreach.getVariable()) || doSwitch(foreach.getLower())
				|| doSwitch(foreach.getHigher())) {
			return true;
		}

		for (AstStatement statement : foreach.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementIf(AstStatementIf stmtIf) {
		if (doSwitch(stmtIf.getCondition())) {
			return true;
		}

		for (AstStatement statement : stmtIf.getThen()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		for (AstStatement statement : stmtIf.getElse()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstStatementWhile(AstStatementWhile stmtWhile) {
		if (doSwitch(stmtWhile.getCondition())) {
			return true;
		}

		for (AstStatement statement : stmtWhile.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstTypeBool(AstTypeBool type) {
		return false;
	}

	@Override
	public Boolean caseAstTypeFloat(AstTypeFloat type) {
		return false;
	}

	@Override
	public Boolean caseAstTypeInt(AstTypeInt type) {
		return doSwitch(type.getSize());
	}

	@Override
	public Boolean caseAstTypeList(AstTypeList type) {
		return doSwitch(type.getType()) || doSwitch(type.getSize());
	}

	@Override
	public Boolean caseAstTypeString(AstTypeString type) {
		return false;
	}

	@Override
	public Boolean caseAstTypeUint(AstTypeUint type) {
		return doSwitch(type.getSize());
	}

	@Override
	public Boolean caseAstUnit(AstUnit unit) {
		for (AstFunction function : unit.getFunctions()) {
			if (doSwitch(function)) {
				return true;
			}
		}

		for (AstVariable variable : unit.getVariables()) {
			if (doSwitch(variable)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseAstVariable(AstVariable variable) {
		AstType type = variable.getType();
		if (doSwitch(type)) {
			return true;
		}
		
		for (AstExpression dim : variable.getDimensions()) {
			if (doSwitch(dim)) {
				return true;
			}
		}

		return doSwitch(variable.getValue());
	}

	@Override
	public Boolean caseAstVariableReference(AstVariableReference reference) {
		return false;
	}

	@Override
	public Boolean doSwitch(EObject theEObject) {
		if (theEObject == null) {
			return false;
		} else {
			return super.doSwitch(theEObject);
		}
	}

}
