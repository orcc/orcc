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
package net.sf.orcc.network.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class defines a parser of XDF expressions.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ExprParser {

	/**
	 * Parses the given node as an expression and returns the matching IExpr
	 * expression.
	 * 
	 * @param node
	 *            a node whose expected to be, or whose sibling is expected to
	 *            be, a DOM element named "Expr".
	 * @return an expression
	 * @throws OrccException
	 *             if the given node or its siblings could not be parsed as an
	 *             expression
	 */
	public IExpr parseExpr(Node node) throws OrccException {
		ParseContinuation<IExpr> cont = parseExprCont(node);
		IExpr expr = cont.getResult();
		if (expr == null) {
			throw new OrccException("Expected an Expr element");
		} else {
			return expr;
		}
	}

	/**
	 * Parses the given node as a binary operator and returns a parse
	 * continuation with the operator parsed.
	 * 
	 * @param node
	 *            a node that is expected, or whose sibling is expected, to be a
	 *            DOM element named "Op".
	 * @return a parse continuation with the operator parsed
	 * @throws OrccException
	 *             if the binary operator could not be parsed
	 */
	private ParseContinuation<BinaryOp> parseExprBinaryOp(Node node)
			throws OrccException {
		while (node != null) {
			if (node.getNodeName().equals("Op")) {
				Element op = (Element) node;
				String name = op.getAttribute("name");
				if (name.equals("and")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.LAND);
				} else if (name.equals("/")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.DIV);
				} else if (name.equals("div")) {
					return new ParseContinuation<BinaryOp>(node,
							BinaryOp.DIV_INT);
				} else if (name.equals("=")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.EQ);
				} else if (name.equals("^")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.EXP);
				} else if (name.equals(">=")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.GE);
				} else if (name.equals(">")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.GT);
				} else if (name.equals("<=")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.LE);
				} else if (name.equals("<")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.LT);
				} else if (name.equals("-")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.MINUS);
				} else if (name.equals("mod")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.MOD);
				} else if (name.equals("!=")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.NE);
				} else if (name.equals("or")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.LOR);
				} else if (name.equals("+")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.PLUS);
				} else if (name.equals("*")) {
					return new ParseContinuation<BinaryOp>(node, BinaryOp.TIMES);
				} else {
					throw new OrccException("Unknown binary operator \"" + name
							+ "\"");
				}
			}

			node = node.getNextSibling();
		}

		return new ParseContinuation<BinaryOp>(node, null);
	}

	/**
	 * Parses the given node and its siblings as a sequence of binary
	 * operations, aka "BinOpSeq". A BinOpSeq is a sequence of expr, op, expr,
	 * op, expr...
	 * 
	 * @param node
	 *            the first child node of a Expr kind="BinOpSeq" element
	 * @return a parse continuation with a BinaryExpr
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private ParseContinuation<IExpr> parseExprBinOpSeq(Node node)
			throws OrccException {
		List<Object> args = new ArrayList<Object>();

		ParseContinuation<? extends Object> cont = parseExprCont(node);
		args.add(cont.getResult());
		node = cont.getNode();
		while (node != null) {
			cont = parseExprBinaryOp(node);
			BinaryOp op = (BinaryOp) cont.getResult();
			node = cont.getNode();
			if (op != null) {
				args.add(op);

				cont = parseExprCont(node);
				IExpr expr = (IExpr) cont.getResult();
				if (expr == null) {
					throw new OrccException("Expected an Expr element");
				}

				args.add(expr);
				node = cont.getNode();
			}
		}

		// TODO one more time, apply operator priority to stupid binary
		// operation sequence...
		return new ParseContinuation<IExpr>(node, new IntExpr(42));
	}

	/**
	 * Parses the given node as an expression and returns the matching IExpr
	 * expression.
	 * 
	 * @param node
	 *            a node whose sibling is expected to be a DOM element named
	 *            "Expr".
	 * @return an expression
	 * @throws OrccException
	 *             if the given node or its siblings could not be parsed as an
	 *             expression
	 */
	private ParseContinuation<IExpr> parseExprCont(Node node)
			throws OrccException {
		IExpr expr = null;
		while (node != null) {
			if (node.getNodeName().equals("Expr")) {
				Element elt = (Element) node;
				String kind = elt.getAttribute("kind");
				if (kind.equals("BinOpSeq")) {
					return parseExprBinOpSeq(elt.getFirstChild());
				} else if (kind.equals("Literal")) {
					expr = parseExprLiteral(elt);
					break;
				} else if (kind.equals("List")) {
					List<IExpr> exprs = parseExprs(node.getFirstChild());
					expr = new ListExpr(new Location(), exprs);
					break;
				} else if (kind.equals("UnaryOp")) {
					ParseContinuation<UnaryOp> cont = parseExprUnaryOp(node
							.getFirstChild());
					UnaryOp op = cont.getResult();
					IExpr unaryExpr = parseExpr(cont.getNode());
					expr = new UnaryExpr(new Location(), op, unaryExpr, null);
					break;
				} else if (kind.equals("Var")) {
					String name = elt.getAttribute("name");
					LocalVariable varDef = new LocalVariable(false, false, 0,
							null, name, null, null, null, null);
					VarUse varUse = new VarUse(varDef, null);
					expr = new VarExpr(new Location(), varUse);
					break;
				} else {
					throw new OrccException("Unsupported Expr kind: \"" + kind
							+ "\"");
				}
			}

			node = node.getNextSibling();
		}

		return new ParseContinuation<IExpr>(node, expr);
	}

	/**
	 * Parses the given "Expr" element as a literal and returns the matching
	 * IExpr expression.
	 * 
	 * @param elt
	 *            a DOM element named "Expr"
	 * @return an expression
	 * @throws OrccException
	 *             if the literal could not be parsed
	 */
	private IExpr parseExprLiteral(Element elt) throws OrccException {
		String kind = elt.getAttribute("literal-kind");
		String value = elt.getAttribute("value");
		if (kind.equals("Boolean")) {
			return new BooleanExpr(new Location(), Boolean.parseBoolean(value));
		} else if (kind.equals("Character")) {
			throw new OrccException("Characters not supported yet");
		} else if (kind.equals("Integer")) {
			return new IntExpr(new Location(), Integer.parseInt(value));
		} else if (kind.equals("Real")) {
			throw new OrccException("Reals not supported yet");
		} else if (kind.equals("String")) {
			return new StringExpr(new Location(), value);
		} else {
			throw new OrccException("Unsupported Expr " + "literal kind: \""
					+ kind + "\"");
		}
	}

	private List<IExpr> parseExprs(Node node) throws OrccException {
		List<IExpr> exprs = new ArrayList<IExpr>();
		while (node != null) {
			if (node.getNodeName().equals("Expr")) {
				exprs.add(parseExpr(node));
			}

			node = node.getNextSibling();
		}

		return exprs;
	}

	/**
	 * Parses the given node as a unary operator and returns a parse
	 * continuation with the operator parsed.
	 * 
	 * @param node
	 *            a node that is expected, or whose sibling is expected, to be a
	 *            DOM element named "Op".
	 * @return a parse continuation with the operator parsed
	 * @throws OrccException
	 *             if the unary operator could not be parsed
	 */
	private ParseContinuation<UnaryOp> parseExprUnaryOp(Node node)
			throws OrccException {
		while (node != null) {
			if (node.getNodeName().equals("Op")) {
				Element op = (Element) node;
				String name = op.getAttribute("name");
				if (name.equals("#")) {
					return new ParseContinuation<UnaryOp>(node,
							UnaryOp.NUM_ELTS);
				} else if (name.equals("not")) {
					return new ParseContinuation<UnaryOp>(node, UnaryOp.LNOT);
				} else if (name.equals("-")) {
					return new ParseContinuation<UnaryOp>(node, UnaryOp.MINUS);
				} else {
					throw new OrccException("Unknown unary operator \"" + name
							+ "\"");
				}
			}

			node = node.getNextSibling();
		}

		throw new OrccException("Expected an Op element");
	}

}
