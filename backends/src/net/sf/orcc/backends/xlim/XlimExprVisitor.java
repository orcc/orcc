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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExprVisitor;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;

public class XlimExprVisitor implements ExprVisitor {
	
	private Document xlim;
	private XlimNames names;
	private Element root;
	
	private static Map<BinaryOp, String> opString;
	private static Map<UnaryOp, String> uopString;
	
	public XlimExprVisitor(XlimNames names, Element root){
		this.xlim = root.getOwnerDocument();
		this.names = names;
		this.root = root;
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		
		Element operationE = xlim.createElement("operation");
		//expr.getOp() == BinaryOp.PLUS;
		operationE.setAttribute("kind", opString.get(expr.getOp()));
		
		expr.getE1().accept(this, root);
		
		XlimNodeTemplate.newInPort(operationE, names.getTempName());
		
		expr.getE2().accept(this, root);
		
		XlimNodeTemplate.newInPort(operationE, names.getTempName());
		
		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), null, null);
		//expr.getUnderlyingType().accept(new XlimTypeSizeVisitor(portO));
		root.appendChild(operationE);
	}

	@Override
	public void visit(BooleanExpr expr, Object... args) {
		Element operationE = XlimNodeTemplate.newValueOperation(root, "$literal_Integer", expr.getValue()?"1":"0");
		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), "1", "int");
	}

	@Override
	public void visit(IntExpr expr, Object... args) {
		Element operationE = XlimNodeTemplate.newValueOperation(root, "$literal_Integer", expr.toString());
		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), null, "int");
		// TODO Add size

	}

	@Override
	public void visit(ListExpr expr, Object... args) {
		System.out.println("CHECK LIST EXPR");
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringExpr expr, Object... args) {
		System.out.println("CHECK STRING EXPR");
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		System.out.println("CHECK UNARY EXPR");
		// TODO Auto-generated method stub
		
		expr.getExpr().accept(this, root);
		
		Element operationE = XlimNodeTemplate.newOperation(root, uopString.get(expr.getOp()));
		
		XlimNodeTemplate.newInPort(operationE, names.getTempName());
		
		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), null, null);
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
		Element operationE = XlimNodeTemplate.newOperation(root, "noop");
		
		XlimNodeTemplate.newInPort(operationE, names.getVarName(expr.getVar()));
		
		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), null, null);
	}
	
	{
		opString = new TreeMap<BinaryOp, String>();
		opString.put(BinaryOp.BITAND, "$and");
		opString.put(BinaryOp.BITOR, "$or");
		opString.put(BinaryOp.BITXOR, "$xor");
		opString.put(BinaryOp.DIV, "$div");
		opString.put(BinaryOp.DIV_INT, "$idiv");
		opString.put(BinaryOp.EQ, "$eq");
		opString.put(BinaryOp.EXP, "$exp");
		opString.put(BinaryOp.GE, "$ge");
		opString.put(BinaryOp.GT, "$gt");
		opString.put(BinaryOp.LOGIC_AND, "$land");
		opString.put(BinaryOp.LE, "$le");
		opString.put(BinaryOp.LOGIC_OR, "$lor");
		opString.put(BinaryOp.LT, "$lt");
		opString.put(BinaryOp.MINUS, "$sub");
		opString.put(BinaryOp.MOD, "$mod");
		opString.put(BinaryOp.NE, "$ne");
		opString.put(BinaryOp.PLUS, "$add");
		opString.put(BinaryOp.SHIFT_LEFT, "lshift");
		opString.put(BinaryOp.SHIFT_RIGHT, "rshift");
		opString.put(BinaryOp.TIMES, "$mul");
		
		uopString = new TreeMap<UnaryOp, String>();
		uopString.put(UnaryOp.BITNOT,"$not");
		uopString.put(UnaryOp.LOGIC_NOT,"$lnot");
		uopString.put(UnaryOp.MINUS,"$negate");
		uopString.put(UnaryOp.NUM_ELTS,"$elts");
	}

}
