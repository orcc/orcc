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
package net.sf.orcc.cal.formatting;

import net.sf.orcc.cal.services.CalGrammarAccess;
import net.sf.orcc.cal.services.CalGrammarAccess.AstActionElements;
import net.sf.orcc.cal.services.CalGrammarAccess.AstActorElements;
import net.sf.orcc.cal.services.CalGrammarAccess.AstEntityElements;
import net.sf.orcc.cal.services.CalGrammarAccess.AstProcedureElements;
import net.sf.orcc.cal.services.CalGrammarAccess.AstTypeIntElements;
import net.sf.orcc.cal.services.CalGrammarAccess.AstTypeListElements;
import net.sf.orcc.cal.services.CalGrammarAccess.AstTypeUintElements;
import net.sf.orcc.cal.services.CalGrammarAccess.ExpressionCallElements;
import net.sf.orcc.cal.services.CalGrammarAccess.ExpressionIfElements;
import net.sf.orcc.cal.services.CalGrammarAccess.ExpressionIndexElements;
import net.sf.orcc.cal.services.CalGrammarAccess.ExpressionListElements;
import net.sf.orcc.cal.services.CalGrammarAccess.ExpressionPostfixElements;
import net.sf.orcc.cal.services.CalGrammarAccess.FunctionElements;
import net.sf.orcc.cal.services.CalGrammarAccess.InitializeElements;
import net.sf.orcc.cal.services.CalGrammarAccess.PriorityElements;
import net.sf.orcc.cal.services.CalGrammarAccess.ScheduleFsmElements;
import net.sf.orcc.cal.services.CalGrammarAccess.StateVariableElements;
import net.sf.orcc.cal.services.CalGrammarAccess.StatementAssignElements;
import net.sf.orcc.cal.services.CalGrammarAccess.StatementCallElements;
import net.sf.orcc.cal.services.CalGrammarAccess.StatementForeachElements;
import net.sf.orcc.cal.services.CalGrammarAccess.StatementIfElements;
import net.sf.orcc.cal.services.CalGrammarAccess.StatementWhileElements;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an
 * example
 */
public class CalFormatter extends AbstractDeclarativeFormatter {

	private CalGrammarAccess f;

	private void body(FormattingConfig c, Keyword kwdDo, Keyword kwdEnd) {
		// "do" unindents and indents
		c.setIndentation(kwdDo, kwdDo);

		// "end" unindents
		c.setIndentation(null, kwdEnd);

		// add some spaces around do and end
		c.setLinewrap().before(kwdDo);
		c.setLinewrap().after(kwdDo);

		c.setLinewrap().before(kwdEnd);
		c.setLinewrap(2).after(kwdEnd);
	}

	/**
	 * Configure action.
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureAction(FormattingConfig c) {
		AstActionElements access = f.getAstActionAccess();

		c.setLinewrap().before(access.getTagAssignment_1_0());
		c.setNoSpace().before(access.getColonKeyword_1_1());

		configureActionInputs(c);
		configureActionOutputs(c);

		// "action" indents
		c.setIndentation(access.getActionKeyword_2(), null);

		// "guard" unindents and indents, configure comma rules
		keywordAndCommas(c, access.getGuardKeyword_6_0(),
				access.getCommaKeyword_6_2_0());

		// "var" unindents and indents, configure comma rules
		keywordAndCommas(c, access.getVarKeyword_7_0(),
				access.getCommaKeyword_7_2_0());

		body(c, access.getDoKeyword_8_0(), access.getEndKeyword_9());
	}

	/**
	 * Configure action input pattern.
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureActionInputs(FormattingConfig c) {
		AstActionElements access = f.getAstActionAccess();

		c.setNoSpace().before(access.getCommaKeyword_3_1_0());
		c.setNoSpace().before(f.getInputPatternAccess().getColonKeyword_1());
		c.setNoSpace().before(f.getInputPatternAccess().getCommaKeyword_4_0());
		c.setNoSpace().before(
				f.getInputPatternAccess().getLeftSquareBracketKeyword_2());
	}

	/**
	 * Configure action output pattern.
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureActionOutputs(FormattingConfig c) {
		AstActionElements access = f.getAstActionAccess();

		c.setNoSpace().before(access.getCommaKeyword_5_1_0());
		c.setNoSpace().before(f.getOutputPatternAccess().getColonKeyword_1());
		c.setNoSpace().before(f.getOutputPatternAccess().getCommaKeyword_4_0());
		c.setNoSpace().before(
				f.getOutputPatternAccess().getLeftSquareBracketKeyword_2());
	}

	/**
	 * Configure actor body
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureActorBody(FormattingConfig c) {
		AstActorElements access = f.getAstActorAccess();

		c.setLinewrap(2).after(access.getColonKeyword_7());
		c.setIndentation(access.getColonKeyword_7(), access.getEndKeyword_11());

		c.setLinewrap(2).before(access.getEndKeyword_11());
		c.setLinewrap(2).after(access.getEndKeyword_11());

		c.setLinewrap(2).after(access.getEndKeyword_11());

		c.setIndentation(f.getScheduleFsmAccess().getFsmKeyword_1(),
				access.getEndKeyword_11());
	}

	/**
	 * Configure call expression
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureExpressionCall(FormattingConfig c) {
		ExpressionCallElements access = f.getExpressionCallAccess();

		c.setNoSpace().around(access.getLeftParenthesisKeyword_1());
		c.setNoSpace().before(access.getCommaKeyword_2_1_0());
		c.setNoSpace().before(access.getRightParenthesisKeyword_3());
	}

	/**
	 * Configure if expression
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureExpressionIf(FormattingConfig c) {
		ExpressionIfElements access = f.getExpressionIfAccess();
		c.setIndentation(access.getThenKeyword_2(), access.getElseKeyword_5());
		c.setLinewrap().after(access.getThenKeyword_2());
		c.setIndentation(access.getElseKeyword_5(), access.getEndKeyword_7());
		c.setLinewrap().before(access.getElseKeyword_5());
		c.setLinewrap().after(access.getElseKeyword_5());
		c.setLinewrap().before(access.getEndKeyword_7());
	}

	/**
	 * Configure index expression
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureExpressionIndex(FormattingConfig c) {
		ExpressionIndexElements access = f.getExpressionIndexAccess();

		c.setNoSpace().around(access.getLeftSquareBracketKeyword_1_0());
		c.setNoSpace().before(access.getRightSquareBracketKeyword_1_2());
	}

	/**
	 * Configure list expression
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureExpressionList(FormattingConfig c) {
		ExpressionListElements eaccess = f.getExpressionListAccess();

		c.setNoSpace().before(eaccess.getCommaKeyword_2_0());
		c.setNoSpace().before(eaccess.getCommaKeyword_3_2_0());
	}

	/**
	 * Configure postfix expression
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureExpressionPostfix(FormattingConfig c) {
		ExpressionPostfixElements access = f.getExpressionPostfixAccess();

		c.setNoSpace().after(access.getLeftParenthesisKeyword_6_0());
		c.setNoSpace().before(access.getRightParenthesisKeyword_6_2());
	}

	/**
	 * Configure expressions
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureExpressions(FormattingConfig c) {
		configureExpressionCall(c);
		configureExpressionList(c);
		configureExpressionIf(c);
		configureExpressionIndex(c);
		configureExpressionPostfix(c);

		c.setNoSpace().after(
				f.getExpressionUnaryAccess().getUnaryOperatorAssignment_0_1());
	}

	@Override
	protected void configureFormatting(FormattingConfig c) {
		f = (CalGrammarAccess) getGrammarAccess();
		c.setLinewrap(2).before(f.getML_COMMENTRule());
		c.setLinewrap().after(f.getML_COMMENTRule());

		// Tags
		c.setNoSpace().around(f.getAstTagAccess().getFullStopKeyword_1_0());

		configureAction(c);
		configureActorBody(c);
		configureFunction(c);
		configureExpressions(c);
		configureInitialize(c);
		configureParameters(c);
		configurePorts(c);
		configurePriorities(c);
		configureProcedure(c);
		configureSchedule(c);
		configureStatements(c);
		configureStateVariable(c);
		configureTypeInt(c);
		configureTypeList(c);
		configureTypeUint(c);
		configureUnitBody(c);

		f = null;
	}

	/**
	 * Configure function
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureFunction(FormattingConfig c) {
		FunctionElements access = f.getFunctionAccess();

		c.setNoSpace().around(access.getLeftParenthesisKeyword_2());
		c.setNoSpace().before(access.getCommaKeyword_3_1_0());
		c.setNoSpace().before(access.getRightParenthesisKeyword_4());

		// "procedure" indents
		c.setIndentation(access.getFunctionKeyword_0(), null);

		// "var" unindents and indents, configure comma rules
		keywordAndCommas(c, access.getVarKeyword_7_0(),
				access.getCommaKeyword_7_2_0());

		c.setLinewrap().after(access.getColonKeyword_8());

		// "end" unindents
		c.setIndentation(null, access.getEndKeyword_11());

		c.setLinewrap().before(access.getEndKeyword_11());
		c.setLinewrap(2).after(access.getEndKeyword_11());
	}

	/**
	 * Configure initialize.
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureInitialize(FormattingConfig c) {
		InitializeElements access = f.getInitializeAccess();

		c.setLinewrap().before(access.getTagAssignment_1_0());
		c.setNoSpace().before(access.getColonKeyword_1_1());

		// output pattern
		c.setNoSpace().before(access.getCommaKeyword_4_1_0());

		// "initialize" indents
		c.setIndentation(access.getInitializeKeyword_2(), null);

		// "guard" unindents and indents, configure comma rules
		keywordAndCommas(c, access.getGuardKeyword_5_0(),
				access.getCommaKeyword_5_2_0());

		// "var" unindents and indents, configure comma rules
		keywordAndCommas(c, access.getVarKeyword_6_0(),
				access.getCommaKeyword_5_2_0());

		body(c, access.getDoKeyword_7_0(), access.getEndKeyword_8());
	}

	private void configureParameters(FormattingConfig c) {
		AstActorElements access = f.getAstActorAccess();

		c.setIndentation(access.getLeftParenthesisKeyword_1(),
				access.getRightParenthesisKeyword_3());
		c.setNoSpace().after(access.getLeftParenthesisKeyword_1());
		c.setLinewrap().before(access.getParametersAssignment_2_0());
		c.setNoSpace().before(access.getCommaKeyword_2_1_0());
		c.setLinewrap().after(access.getCommaKeyword_2_1_0());
		c.setNoSpace().before(access.getRightParenthesisKeyword_3());
	}

	private void configurePorts(FormattingConfig c) {
		AstActorElements access = f.getAstActorAccess();

		c.setIndentation(access.getInputsAssignment_4_0(),
				access.getColonKeyword_7());
		c.setLinewrap().around(access.getInputsAssignment_4_1_1());
		c.setIndentation(
				access.getEqualsSignEqualsSignGreaterThanSignKeyword_5(),
				access.getColonKeyword_7());
		c.setLinewrap().around(access.getOutputsAssignment_6_1_1());
		c.setNoSpace().before(access.getCommaKeyword_4_1_0());
		c.setNoSpace().before(access.getCommaKeyword_6_1_0());
	}

	private void configurePriorities(FormattingConfig c) {
		PriorityElements access = f.getPriorityAccess();

		c.setIndentation(access.getPriorityKeyword_1(),
				access.getEndKeyword_3());
		c.setLinewrap().after(access.getPriorityKeyword_1());
		c.setLinewrap(2).after(access.getEndKeyword_3());

		c.setNoSpace().before(f.getInequalityAccess().getSemicolonKeyword_2());
		c.setLinewrap().after(f.getInequalityAccess().getSemicolonKeyword_2());
	}

	/**
	 * Configure procedure
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureProcedure(FormattingConfig c) {
		AstProcedureElements access = f.getAstProcedureAccess();

		c.setNoSpace().around(access.getLeftParenthesisKeyword_2());
		c.setNoSpace().before(access.getCommaKeyword_3_1_0());
		c.setNoSpace().before(access.getRightParenthesisKeyword_4());
		c.setLinewrap().after(access.getRightParenthesisKeyword_4());

		// "procedure" indents
		c.setIndentation(access.getProcedureKeyword_0(), null);

		// "var" unindents and indents, configure comma rules
		keywordAndCommas(c, access.getVarKeyword_5_0(),
				access.getCommaKeyword_5_2_0());

		body(c, access.getBeginKeyword_6(), access.getEndKeyword_9());
	}

	private void configureSchedule(FormattingConfig c) {
		ScheduleFsmElements access = f.getScheduleFsmAccess();

		c.setLinewrap().after(access.getColonKeyword_3());

		c.setNoSpace().before(
				f.getAstTransitionAccess().getSemicolonKeyword_6());
		c.setLinewrap().after(
				f.getAstTransitionAccess().getSemicolonKeyword_6());
	}

	/**
	 * Configure assign statement
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureStatementAssign(FormattingConfig c) {
		StatementAssignElements access = f.getStatementAssignAccess();

		c.setNoSpace().around(access.getLeftSquareBracketKeyword_1_0());
		c.setNoSpace().before(access.getRightSquareBracketKeyword_1_2());

		c.setLinewrap().after(access.getSemicolonKeyword_3());
		c.setNoSpace().before(access.getSemicolonKeyword_3());
	}

	/**
	 * Configure call statement
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureStatementCall(FormattingConfig c) {
		StatementCallElements access = f.getStatementCallAccess();

		c.setNoSpace().around(access.getLeftParenthesisKeyword_1());
		c.setNoSpace().around(access.getRightParenthesisKeyword_3());

		c.setNoSpace().before(access.getCommaKeyword_2_1_0());

		c.setLinewrap().after(access.getSemicolonKeyword_4());
	}

	/**
	 * Configure foreach statement
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureStatementForeach(FormattingConfig c) {
		StatementForeachElements access = f.getStatementForeachAccess();

		c.setIndentation(access.getDoKeyword_6(), access.getEndKeyword_8());

		c.setLinewrap().after(access.getDoKeyword_6());
		c.setLinewrap().after(access.getEndKeyword_8());
	}

	/**
	 * Configure if statement
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureStatementIf(FormattingConfig c) {
		StatementIfElements access = f.getStatementIfAccess();

		c.setIndentation(access.getThenKeyword_2(), null);
		c.setIndentation(access.getElseKeyword_5_0(),
				access.getElseKeyword_5_0());
		c.setIndentation(null, access.getEndKeyword_6());

		c.setLinewrap().after(access.getThenKeyword_2());
		c.setLinewrap().after(access.getElseKeyword_5_0());
		c.setLinewrap().after(access.getEndKeyword_6());
	}

	private void configureStatements(FormattingConfig c) {
		configureStatementAssign(c);
		configureStatementCall(c);
		configureStatementForeach(c);
		configureStatementIf(c);
		configureStatementWhile(c);
	}

	/**
	 * Configure while statement
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureStatementWhile(FormattingConfig c) {
		StatementWhileElements access = f.getStatementWhileAccess();

		c.setIndentation(access.getDoKeyword_2(), access.getEndKeyword_4());

		c.setLinewrap().after(access.getDoKeyword_2());
		c.setLinewrap().after(access.getEndKeyword_4());
	}

	private void configureStateVariable(FormattingConfig c) {
		StateVariableElements access = f.getStateVariableAccess();

		c.setNoSpace().before(access.getSemicolonKeyword_1());
		c.setLinewrap(2).after(access.getSemicolonKeyword_1());
	}

	private void configureTypeInt(FormattingConfig c) {
		AstTypeIntElements access = f.getAstTypeIntAccess();

		c.setNoSpace().around(access.getLeftParenthesisKeyword_2_0());
		c.setNoSpace().after(access.getSizeKeyword_2_1());
		c.setNoSpace().after(access.getEqualsSignKeyword_2_2());
		c.setNoSpace().before(access.getRightParenthesisKeyword_2_4());

		c.setNoLinewrap().around(access.getLeftParenthesisKeyword_2_0());
		c.setNoLinewrap().around(access.getEqualsSignKeyword_2_2());
		c.setNoLinewrap().around(access.getRightParenthesisKeyword_2_4());
	}

	private void configureTypeList(FormattingConfig c) {
		AstTypeListElements access = f.getAstTypeListAccess();

		c.setNoSpace().around(access.getLeftParenthesisKeyword_1());
		c.setNoSpace().after(access.getTypeKeyword_2());
		c.setNoSpace().before(access.getCommaKeyword_5());
		c.setNoSpace().after(access.getSizeKeyword_6());
		c.setNoSpace().after(access.getEqualsSignKeyword_7());
		c.setNoSpace().before(access.getRightParenthesisKeyword_9());

		c.setNoLinewrap().around(access.getLeftParenthesisKeyword_1());
		c.setNoLinewrap().around(access.getColonKeyword_3());
		c.setNoLinewrap().around(access.getCommaKeyword_5());
		c.setNoLinewrap().around(access.getEqualsSignKeyword_7());
		c.setNoLinewrap().around(access.getRightParenthesisKeyword_9());
	}

	private void configureTypeUint(FormattingConfig c) {
		AstTypeUintElements access = f.getAstTypeUintAccess();

		c.setNoSpace().before(access.getLeftParenthesisKeyword_2_0());
		c.setNoSpace().after(access.getLeftParenthesisKeyword_2_0());
		c.setNoSpace().after(access.getSizeKeyword_2_1());
		c.setNoSpace().after(access.getEqualsSignKeyword_2_2());
		c.setNoSpace().before(access.getRightParenthesisKeyword_2_4());

		c.setNoLinewrap().around(access.getLeftParenthesisKeyword_2_0());
		c.setNoLinewrap().around(access.getEqualsSignKeyword_2_2());
		c.setNoLinewrap().around(access.getRightParenthesisKeyword_2_4());
	}

	/**
	 * Configure unit body
	 * 
	 * @param c
	 *            formatting config
	 */
	private void configureUnitBody(FormattingConfig c) {
		AstEntityElements access = f.getAstEntityAccess();

		c.setLinewrap(2).after(access.getSemicolonKeyword_0_2());
		c.setNoSpace().before(access.getSemicolonKeyword_0_2());

		c.setLinewrap(2).after(access.getImportsAssignment_1());
	}

	/**
	 * <pre>
	 * keyword
	 *   x,
	 *   y
	 * </pre>
	 * 
	 * @param c
	 * @param keyword
	 * @param comma
	 */
	private void keywordAndCommas(FormattingConfig c, Keyword keyword,
			Keyword comma) {
		// keyword indents
		c.setIndentation(keyword, keyword);

		// newline before and after keyword
		c.setLinewrap().before(keyword);
		c.setLinewrap().after(keyword);

		// no space before comma, new line after comma
		c.setLinewrap().after(comma);
		c.setNoSpace().before(comma);
	}

}
