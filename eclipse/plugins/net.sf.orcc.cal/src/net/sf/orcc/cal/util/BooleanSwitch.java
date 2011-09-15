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
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;
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
import net.sf.orcc.cal.cal.VariableReference;
import net.sf.orcc.cal.cal.util.CalSwitch;

import org.eclipse.emf.ecore.EObject;

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
		for (InputPattern input : action.getInputs()) {
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

		for (Statement statement : action.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		for (OutputPattern output : action.getOutputs()) {
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

		for (Function function : actor.getFunctions()) {
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

		for (Statement statement : procedure.getStatements()) {
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
		for (Function function : unit.getFunctions()) {
			if (doSwitch(function)) {
				return true;
			}
		}

		for (AstProcedure procedure : unit.getProcedures()) {
			if (doSwitch(procedure)) {
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
	public Boolean caseExpressionBinary(ExpressionBinary expression) {
		if (doSwitch(expression.getLeft()) || doSwitch(expression.getRight())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseExpressionBoolean(ExpressionBoolean expression) {
		return false;
	}

	@Override
	public Boolean caseExpressionCall(ExpressionCall call) {
		for (AstExpression parameter : call.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseExpressionElsif(ExpressionElsif expression) {
		return doSwitch(expression.getCondition())
				|| doSwitch(expression.getThen());
	}

	@Override
	public Boolean caseExpressionFloat(ExpressionFloat expression) {
		return false;
	}

	@Override
	public Boolean caseExpressionIf(ExpressionIf expression) {
		if (doSwitch(expression.getCondition())
				|| doSwitch(expression.getThen())) {
			return true;
		}

		for (ExpressionElsif elsif : expression.getElsifs()) {
			if (doSwitch(elsif)) {
				return true;
			}
		}

		if (doSwitch(expression.getElse())) {
			return true;
		}

		return false;
	}

	@Override
	public Boolean caseExpressionIndex(ExpressionIndex expression) {
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
	public Boolean caseExpressionInteger(ExpressionInteger expression) {
		return false;
	}

	@Override
	public Boolean caseExpressionList(ExpressionList expression) {
		for (AstExpression subExpression : expression.getExpressions()) {
			if (doSwitch(subExpression)) {
				return true;
			}
		}

		for (Generator generator : expression.getGenerators()) {
			if (doSwitch(generator)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseExpressionString(ExpressionString expression) {
		return false;
	}

	@Override
	public Boolean caseExpressionUnary(ExpressionUnary expression) {
		return doSwitch(expression.getExpression());
	}

	@Override
	public Boolean caseExpressionVariable(ExpressionVariable expression) {
		return doSwitch(expression.getValue());
	}

	@Override
	public Boolean caseFunction(Function function) {
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
	public Boolean caseGenerator(Generator generator) {
		return (doSwitch(generator.getVariable())
				|| doSwitch(generator.getLower()) || doSwitch(generator
					.getHigher()));
	}

	@Override
	public Boolean caseInputPattern(InputPattern input) {
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
	public Boolean caseOutputPattern(OutputPattern output) {
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
	public Boolean caseStatementAssign(StatementAssign assign) {
		for (AstExpression index : assign.getIndexes()) {
			if (doSwitch(index)) {
				return true;
			}
		}

		return doSwitch(assign.getValue());
	}

	@Override
	public Boolean caseStatementCall(StatementCall call) {
		for (AstExpression parameter : call.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseStatementElsif(StatementElsif elsif) {
		if (doSwitch(elsif.getCondition())) {
			return true;
		}

		for (Statement statement : elsif.getThen()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseStatementForeach(StatementForeach foreach) {
		if (doSwitch(foreach.getVariable()) || doSwitch(foreach.getLower())
				|| doSwitch(foreach.getHigher())) {
			return true;
		}

		for (Statement statement : foreach.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseStatementIf(StatementIf stmtIf) {
		if (doSwitch(stmtIf.getCondition())) {
			return true;
		}

		for (Statement statement : stmtIf.getThen()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		for (StatementElsif elsIf : stmtIf.getElsifs()) {
			if (doSwitch(elsIf)) {
				return true;
			}
		}

		for (Statement statement : stmtIf.getElse()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseStatementWhile(StatementWhile stmtWhile) {
		if (doSwitch(stmtWhile.getCondition())) {
			return true;
		}

		for (Statement statement : stmtWhile.getStatements()) {
			if (doSwitch(statement)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean caseVariableReference(VariableReference reference) {
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
