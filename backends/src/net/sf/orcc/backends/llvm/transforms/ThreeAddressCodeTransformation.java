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
package net.sf.orcc.backends.llvm.transforms;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.common.LocalUse;
import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.Util;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.transforms.IActorTransformation;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;

/**
 * Split expression and effective node.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ThreeAddressCodeTransformation extends AbstractLLVMNodeVisitor
		implements IActorTransformation {

	int exprCounter;

	// Add a second pass to check the binary expression type cohesion
	// This pass will be useless when the IR associate the correct type to a
	// binary expression
	public IType checkType(BinaryExpr expr) {
		IType type;

		if ((expr.getOp() == BinaryOp.EQ) || (expr.getOp() == BinaryOp.GE)
				|| (expr.getOp() == BinaryOp.GT)
				|| (expr.getOp() == BinaryOp.LE)
				|| (expr.getOp() == BinaryOp.LT)
				|| (expr.getOp() == BinaryOp.NE)) {
			type = new BoolType();
		} else if ((expr.getE1().getType() == IExpr.VAR)
				&& (expr.getE2().getType() == IExpr.VAR)) {
			IType typeE1 = ((VarExpr) expr.getE1()).getVar().getLocalVariable()
					.getType();
			IType typeE2 = ((VarExpr) expr.getE1()).getVar().getLocalVariable()
					.getType();

			if (sizeOf(typeE1) > sizeOf(typeE2)) {
				type = typeE1;
			} else {
				type = typeE2;
			}

		} else if (expr.getE1().getType() == IExpr.VAR) {
			type = ((VarExpr) expr.getE1()).getVar().getLocalVariable()
					.getType();
		} else if (expr.getE2().getType() == IExpr.VAR) {
			type = ((VarExpr) expr.getE2()).getVar().getLocalVariable()
					.getType();
		} else {
			type = expr.getUnderlyingType();
		}

		return type;
	}

	public IExpr removeUnaryExpr(UnaryExpr expr) {
		// Unary expression doesn't exists in LLVM
		Location loc = expr.getLocation();
		IType type = expr.getUnderlyingType();
		IExpr exprE1 = expr.getExpr();
		UnaryOp op = expr.getOp();
		IntExpr constExpr;

		BinaryExpr varExpr;

		switch (op) {
		case MINUS:
			constExpr = new IntExpr(new Location(), 0);
			varExpr = new BinaryExpr(loc, constExpr, BinaryOp.MINUS, exprE1,
					type);
			return varExpr;
		case LNOT:
			constExpr = new IntExpr(new Location(), 0);
			varExpr = new BinaryExpr(loc, exprE1, BinaryOp.NE, constExpr, type);
			return varExpr;

		case BNOT:
			varExpr = new BinaryExpr(loc, exprE1, BinaryOp.BXOR, exprE1, type);
			return varExpr;
		default:
			throw new NullPointerException();
		}
	}

	private int sizeOf(IType type) {
		int size = 0;

		if (type.getType() == IType.INT) {
			try {
				return Util.evaluateAsInteger(((IntType) type).getSize());
			} catch (OrccException e) {
				e.printStackTrace();
				return 32;
			}
		} else if (type.getType() == IType.BOOLEAN) {
			size = 1;
		}

		return size;
	}

	@SuppressWarnings("unchecked")
	public VarExpr splitBinaryExpr(BinaryExpr expr, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];

		if (expr.getE1().getType() == IExpr.BINARY) {
			expr.setE1(splitBinaryExpr((BinaryExpr) expr.getE1(), it));
		}

		if (expr.getE2().getType() == IExpr.BINARY) {
			expr.setE2(splitBinaryExpr((BinaryExpr) expr.getE2(), it));
		}

		LocalVariable vardef = varDefCreate(checkType(expr));
		LocalUse varuse = new LocalUse(vardef, null);
		VarExpr varexpr = new VarExpr(new Location(), varuse);

		AssignVarNode assignNode = new AssignVarNode(0, new Location(), vardef,
				expr);

		it.add(assignNode);

		return varexpr;

	}

	@SuppressWarnings("unchecked")
	public List<IExpr> splitIndex(List<IExpr> indexes, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		List<IExpr> tmpIndexes = new ArrayList<IExpr>();

		for (IExpr index : indexes) {
			if (index.getType() == IExpr.BINARY) {
				VarExpr expr = splitBinaryExpr((BinaryExpr) index, it);
				tmpIndexes.add(expr);
			} else {
				tmpIndexes.add(index);
			}
		}

		return tmpIndexes;

	}

	@Override
	public void transform(Actor actor) {
		for (Procedure proc : actor.getProcs()) {
			visitProc(proc);
		}

		for (Action action : actor.getActions()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}
	}

	private LocalVariable varDefCreate(IType type) {
		return new LocalVariable(false, false, exprCounter++, new Location(),
				"expr", null, null, 0, type);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(AssignVarNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		IExpr value = node.getValue();

		// Change unary expression into binary expression
		if (node.getValue().getType() == IExpr.UNARY) {
			node.setValue(removeUnaryExpr((UnaryExpr) node.getValue()));
		}

		if (value.getType() == IExpr.BINARY) {
			it.previous();
			BinaryExpr expr = (BinaryExpr) value;
			if (expr.getE1().getType() == IExpr.BINARY) {
				expr.setE1(splitBinaryExpr((BinaryExpr) expr.getE1(), it));
			}

			if (expr.getE2().getType() == IExpr.BINARY) {
				expr.setE2(splitBinaryExpr((BinaryExpr) expr.getE2(), it));
			}
			it.next();
		}
	}

	@Override
	public void visit(BrNode node, Object... args) {

		node.getCondition();
		// Change unary expression into binary expression
		if (node.getCondition().getType() == IExpr.UNARY) {
			node.setCondition(removeUnaryExpr((UnaryExpr) node.getCondition()));
		}

		if (node.getCondition().getType() == IExpr.BINARY) {
			ListIterator<AbstractNode> itConditionNodes = node
					.getConditionNodes().listIterator();
			VarExpr expr = splitBinaryExpr((BinaryExpr) node.getCondition(),
					itConditionNodes);

			node.setCondition(expr);
		}

		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(CallNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		List<IExpr> exprs = node.getParameters();
		for (IExpr expr : exprs) {
			if (expr.getType() == IExpr.UNARY) {
				exprs.set(exprs.indexOf(expr),
						removeUnaryExpr((UnaryExpr) expr));
			}

			if (expr.getType() == IExpr.BINARY) {
				it.previous();
				VarExpr varExpr = splitBinaryExpr((BinaryExpr) expr, args);
				it.next();
				exprs.set(exprs.indexOf(expr), varExpr);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(LoadNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		List<IExpr> indexes = node.getIndexes();

		if (!indexes.isEmpty()) {
			it.previous();
			node.setIndexes(splitIndex(indexes, args));
			it.next();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(ReturnNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		// Change unary expression into binary expression
		if (node.getValue().getType() == IExpr.UNARY) {
			node.setValue(removeUnaryExpr((UnaryExpr) node.getValue()));
		}

		if (node.getValue().getType() == IExpr.BINARY) {
			it.previous();
			VarExpr expr = splitBinaryExpr((BinaryExpr) node.getValue(), args);
			it.next();
			node.setValue(expr);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(SelectNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		// Change unary expression into binary expression
		if (node.getCondition().getType() == IExpr.UNARY) {
			node.setCondition(removeUnaryExpr((UnaryExpr) node.getCondition()));
		}

		if (node.getCondition().getType() == IExpr.BINARY) {
			it.previous();
			VarExpr expr = splitBinaryExpr((BinaryExpr) node.getCondition(),
					args);
			it.next();
			node.setCondition(expr);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(StoreNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];

		// Change unary expression into binary expression
		if (node.getValue().getType() == IExpr.UNARY) {
			node.setValue(removeUnaryExpr((UnaryExpr) node.getValue()));
		}

		if (node.getValue().getType() == IExpr.BINARY) {
			it.previous();
			VarExpr expr = splitBinaryExpr((BinaryExpr) node.getValue(), it);
			it.next();
			node.setValue(expr);
		}

		List<IExpr> indexes = node.getIndexes();
		if (!indexes.isEmpty()) {
			it.previous();
			node.setIndexes(splitIndex(indexes, args));
			it.next();
		}
	}

	private void visitNodes(List<AbstractNode> nodes) {
		ListIterator<AbstractNode> it = nodes.listIterator();

		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	private void visitProc(Procedure proc) {
		exprCounter = 0;

		List<AbstractNode> nodes = proc.getNodes();
		visitNodes(nodes);
	}
}
