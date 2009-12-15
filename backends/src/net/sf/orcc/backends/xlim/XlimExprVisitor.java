/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.xlim;

import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.backends.xlim.templates.XlimNodeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimOperationTemplate;
import net.sf.orcc.backends.xlim.templates.XlimTypeTemplate;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionVisitor;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;

import org.w3c.dom.Element;

/**
 * XlimExprVisitor exports Expressions
 * 
 * @author Samuel Keller
 */
public class XlimExprVisitor implements ExpressionVisitor, XlimTypeTemplate, XlimOperationTemplate {

	/**
	 * Binary operators XLIM mapping
	 */
	private static Map<BinaryOp, String> opString;

	/**
	 * Unary operators XLIM mapping
	 */
	private static Map<UnaryOp, String> uopString;

	/**
	 * XLIM naming
	 */
	private XlimNames names;

	/**
	 * Root element where to add everything
	 */
	private Element root;

	/**
	 * Initialization: filling of Unary and Binary Map
	 */
	{
		// Binary operators
		opString = new TreeMap<BinaryOp, String>();
		opString.put(BinaryOp.BITAND, BITAND);
		opString.put(BinaryOp.BITOR, BITOR);
		opString.put(BinaryOp.BITXOR, BITXOR);
		opString.put(BinaryOp.DIV, DIV);
		opString.put(BinaryOp.DIV_INT, IDIV);
		opString.put(BinaryOp.EQ, EQ);
		opString.put(BinaryOp.EXP, EXP);
		opString.put(BinaryOp.GE, GE);
		opString.put(BinaryOp.GT, GT);
		opString.put(BinaryOp.LOGIC_AND, AND);
		opString.put(BinaryOp.LE, LE);
		opString.put(BinaryOp.LOGIC_OR, OR);
		opString.put(BinaryOp.LT, LT);
		opString.put(BinaryOp.MINUS, SUB);
		opString.put(BinaryOp.MOD, MOD);
		opString.put(BinaryOp.NE, NE);
		opString.put(BinaryOp.PLUS, ADD);
		opString.put(BinaryOp.SHIFT_LEFT, LSHIFT);
		opString.put(BinaryOp.SHIFT_RIGHT, RSHIFT);
		opString.put(BinaryOp.TIMES, MUL);

		// Unary operators
		uopString = new TreeMap<UnaryOp, String>();
		uopString.put(UnaryOp.BITNOT, BITNOT);
		uopString.put(UnaryOp.LOGIC_NOT, NOT);
		uopString.put(UnaryOp.MINUS, NEG);
		uopString.put(UnaryOp.NUM_ELTS, ELTS);
	}

	/**
	 * XlimExprVisitor constructor
	 * 
	 * @param names
	 *            XLIM naming
	 * @param root
	 *            Root element where to add everything
	 */
	public XlimExprVisitor(XlimNames names, Element root) {
		this.names = names;
		this.root = root;
	}

	/**
	 * Add binary expression
	 * 
	 * @param expr
	 *            Binary expression node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(BinaryExpr expr, Object... args) {
		Element operationE = XlimNodeTemplate.newDiffOperation(root, opString
				.get(expr.getOp()));

		expr.getE1().accept(this, root);

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		expr.getE2().accept(this, root);

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), expr
				.getUnderlyingType());

		root.appendChild(operationE);
	}

	/**
	 * Add boolean expression
	 * 
	 * @param expr
	 *            Boolean expression node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(BoolExpr expr, Object... args) {
		Element operationE = XlimNodeTemplate.newValueOperation(root,
				LITINT, expr.getValue() ? "1" : "0");
		XlimNodeTemplate
				.newOutPort(operationE, names.putTempName(), "1", INT);
	}

	/**
	 * Add integer expression
	 * 
	 * @param expr
	 *            Integer expression node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(IntExpr expr, Object... args) {
		Element operationE = XlimNodeTemplate.newValueOperation(root,
				LITINT,
				Integer.toString(expr.getValue()));
		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), INT,
				expr.getValue());
	}

	/**
	 * Add list expression
	 * 
	 * @param expr
	 *            List expression node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(ListExpr expr, Object... args) {
		System.out.println("CHECK LIST EXPR");
		// TODO Auto-generated method stub
	}

	/**
	 * Add string expression
	 * 
	 * @param expr
	 *            String expression node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(StringExpr expr, Object... args) {
		System.out.println("CHECK STRING EXPR");
		// TODO Auto-generated method stub
	}

	/**
	 * Add unary expression
	 * 
	 * @param expr
	 *            Unary expression node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(UnaryExpr expr, Object... args) {
		expr.getExpr().accept(this, root);

		Element operationE = XlimNodeTemplate.newOperation(root, uopString
				.get(expr.getOp()));

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), expr
				.getUnderlyingType());
	}

	/**
	 * Add variable expression
	 * 
	 * @param expr
	 *            Variable expression node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(VarExpr expr, Object... args) {
		Element operationE = XlimNodeTemplate.newOperation(root, NOOP);

		XlimNodeTemplate.newInPort(operationE, names.getVarName(expr.getVar()));

		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), expr
				.getVar().getVariable().getType());
	}

}
