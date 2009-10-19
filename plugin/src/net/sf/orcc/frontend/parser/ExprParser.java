/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.frontend.parser;

import net.sf.orcc.OrccException;
import net.sf.orcc.common.Location;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalParser;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.VarExpr;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 * This class defines a parser that can parse RVC-CAL expressions and translate
 * them to IR expressions. The parser can parse both ANTLR trees or Strings.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ExprParser extends CommonParser {

	/**
	 * Returns the binary operator that match the type of the given tree.
	 * 
	 * @param op
	 *            a Tree that represents an operator
	 * @return a binary operator
	 * @throws OrccException
	 *             if the operator is not valid
	 */
	private BinaryOp parseBinaryOp(Tree op) throws OrccException {
		switch (op.getType()) {
		case RVCCalLexer.AND:
			return BinaryOp.LAND;
		case RVCCalLexer.BITAND:
			return BinaryOp.BAND;
		case RVCCalLexer.BITOR:
			return BinaryOp.BOR;
		case RVCCalLexer.DIV:
			return BinaryOp.DIV;
		case RVCCalLexer.DIV_INT:
			return BinaryOp.DIV_INT;
		case RVCCalLexer.EQ:
			return BinaryOp.EQ;
		case RVCCalLexer.EXP:
			return BinaryOp.EXP;
		case RVCCalLexer.GE:
			return BinaryOp.GE;
		case RVCCalLexer.GT:
			return BinaryOp.GT;
		case RVCCalLexer.LE:
			return BinaryOp.LE;
		case RVCCalLexer.LT:
			return BinaryOp.LT;
		case RVCCalLexer.MINUS:
			return BinaryOp.MINUS;
		case RVCCalLexer.MOD:
			return BinaryOp.MOD;
		case RVCCalLexer.NE:
			return BinaryOp.NE;
		case RVCCalLexer.OR:
			return BinaryOp.LOR;
		case RVCCalLexer.PLUS:
			return BinaryOp.PLUS;
		case RVCCalLexer.SHIFT_LEFT:
			return BinaryOp.SHIFT_LEFT;
		case RVCCalLexer.SHIFT_RIGHT:
			return BinaryOp.SHIFT_RIGHT;
		case RVCCalLexer.TIMES:
			return BinaryOp.TIMES;
		default:
			throw new OrccException("Unknown operator: " + op.getText());
		}
	}

	/**
	 * Parses the given input as an expression.
	 * 
	 * @param input
	 *            a string that supposedly represents a valid RVC-CAL expression
	 * @return an {@link IExpr}
	 * @throws OrccException
	 *             if there was a parse error.
	 */
	public IExpr parseExpression(String input) throws OrccException {
		try {
			CharStream stream = new ANTLRStringStream(input);
			Lexer lexer = new RVCCalLexer(stream);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			RVCCalParser parser = new RVCCalParser(tokens);
			RVCCalParser.expression_return ret = parser.expression();
			return parseExpression((Tree) ret.getTree());
		} catch (RecognitionException e) {
			throw new OrccException("parse error", e);
		}
	}

	/**
	 * Parses the given tree as an expression. This method is package because it
	 * should be called from {@link RVCCalASTParser} only.
	 * 
	 * @param expr
	 *            a tree that contains an expression
	 * @return an {@link IExpr}.
	 * @throws OrccException
	 */
	IExpr parseExpression(Tree expr) throws OrccException {
		switch (expr.getType()) {
		case RVCCalLexer.EXPR_BINARY: {
			IExpr e1 = parseExpression(expr.getChild(0));
			int n = expr.getChildCount();
			int i = 1;
			while (i < n) {
				BinaryOp op = parseBinaryOp(expr.getChild(i++));
				IExpr e2 = parseExpression(expr.getChild(i++));
				Location location = new Location(e1.getLocation(), e2
						.getLocation());
				e1 = new BinaryExpr(location, e1, op, e2, null);
			}

			return e1;
		}
		case RVCCalLexer.EXPR_BOOL: {
			expr = expr.getChild(0);
			boolean value = Boolean.parseBoolean(expr.getText());
			return new BooleanExpr(parseLocation(expr), value);
		}
		case RVCCalLexer.EXPR_FLOAT:
			throw new OrccException("not yet implemented!");
		case RVCCalLexer.EXPR_INT:
			expr = expr.getChild(0);
			int value = Integer.parseInt(expr.getText());
			return new IntExpr(parseLocation(expr), value);
		case RVCCalLexer.EXPR_STRING:
			expr = expr.getChild(0);
			return new StringExpr(parseLocation(expr), expr.getText());
		case RVCCalLexer.EXPR_VAR:
			expr = expr.getChild(0);
			VarUse varUse = new VarUse(null, null);
			return new VarExpr(parseLocation(expr), varUse);
		default:
			throw new OrccException("not yet implemented");
		}
	}

}
