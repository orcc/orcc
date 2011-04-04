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
package net.sf.orcc.ir;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.expr.AbstractExpressionVisitor;
import net.sf.orcc.ir.expr.VarExpr;

/**
 * This class defines a use of a var.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Use {

	/**
	 * This class adds uses of variables used in a given node.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class UseAdder extends AbstractExpressionVisitor {

		private User node;

		/**
		 * Creates a new use adder with the given node.
		 * 
		 * @param node
		 *            a node
		 */
		public UseAdder(User node) {
			this.node = node;
		}

		@Override
		public void visit(VarExpr expr, Object... args) {
			Use use = expr.getVar();
			use.setNode(node);
		}

	}

	/**
	 * This class removes uses of variables used in a given node.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class UseRemover extends AbstractExpressionVisitor {

		private User node;

		/**
		 * Creates a new use remover with the given node.
		 * 
		 * @param node
		 *            a node
		 */
		public UseRemover(User node) {
			this.node = node;
		}

		@Override
		public void visit(VarExpr expr, Object... args) {
			Use use = expr.getVar();
			if (use.getNode().equals(node)) {
				use.remove();
			}
		}

	}

	/**
	 * Visits the given expression, and sets the node of all uses to the given
	 * node.
	 * 
	 * @param node
	 *            a node
	 * @param value
	 *            an expression
	 */
	public static void addUses(User node, Expression value) {
		value.accept(new UseAdder(node));
	}

	/**
	 * Visits the given expressions, and sets the node of all uses to the given
	 * node.
	 * 
	 * @param node
	 *            a node
	 * @param values
	 *            a list of expressions
	 */
	public static void addUses(User node, List<Expression> values) {
		for (Expression value : values) {
			addUses(node, value);
		}
	}

	/**
	 * Removes the given uses.
	 * 
	 * @param uses
	 *            a list of Use
	 */
	public static void removeUses(List<Use> uses) {
		for (Use use : uses) {
			use.remove();
		}
	}

	/**
	 * Visits the given expression, and removes the uses that reference the
	 * given node.
	 * 
	 * @param node
	 *            a node
	 * @param value
	 *            an expression
	 */
	public static void removeUses(User node, Expression value) {
		value.accept(new UseRemover(node));
	}

	/**
	 * Visits the given expressions, and removes the uses that reference the
	 * given node.
	 * 
	 * @param node
	 *            a node
	 * @param values
	 *            a list of expressions
	 */
	public static void removeUses(User node, List<Expression> values) {
		for (Expression value : values) {
			removeUses(node, value);
		}
	}

	/**
	 * Replaces uses of oldVar by uses of newVar.
	 * 
	 * @param oldVar
	 *            old var
	 * @param newVar
	 *            new var
	 */
	public static void replaceUses(VarLocal oldVar, VarLocal newVar) {
		// copy uses because #setVariable will change oldVar's uses
		List<Use> existingUses = new ArrayList<Use>(oldVar.getUses());
		for (Use use : existingUses) {
			if (use.getVariable().equals(oldVar)) {
				use.setVariable(newVar);
			}
		}
	}

	/**
	 * the node where the var referenced is used. May be <code>null</code>
	 * if the information is not available or has no meaning.
	 */
	private User node;

	/**
	 * the var referenced
	 */
	private Var var;

	/**
	 * Creates a new use of the given var. This use is added to the use
	 * list of the newly referenced var.
	 * 
	 * @param var
	 *            a var
	 */
	public Use(Var var) {
		setVariable(var);
	}

	/**
	 * Creates a new use of the given var. This use is added to the use
	 * list of the newly referenced var. The node parameter must not be
	 * <code>null</code>, if you want to create a use with no node, use the
	 * other constructor.
	 * 
	 * @param var
	 *            a var
	 * @param user
	 *            a node
	 * @throws NullPointerException
	 *             if node is null.
	 */
	public Use(Var var, User user) {
		if (user == null) {
			throw new NullPointerException();
		}
		setVariable(var);
		this.node = user;
	}

	/**
	 * Returns the node referenced by this use. May be <code>null</code> if the
	 * information is not available.
	 * 
	 * @return the node referenced by this use
	 */
	public User getNode() {
		return node;
	}

	/**
	 * Returns the var referenced by this use.
	 * 
	 * @return the var referenced by this use
	 */
	public Var getVariable() {
		return var;
	}

	/**
	 * Removes this var use from the var referenced.
	 */
	public void remove() {
		getVariable().removeUse(this);
	}

	/**
	 * Sets the node referenced by this use.
	 * 
	 * @param node
	 *            node referenced by this use
	 */
	public void setNode(User node) {
		this.node = node;
	}

	/**
	 * Sets the var referenced by this use to the given var. This use
	 * is removed from the use list of the previously referenced var, and
	 * added to the use list of the newly referenced var.
	 * 
	 * @param var
	 *            a var that this use will reference
	 */
	public void setVariable(Var var) {
		if (this.var != null) {
			this.var.removeUse(this);
		}
		this.var = var;
		var.addUse(this);
	}

	@Override
	public String toString() {
		return String.valueOf(var);
	}

}
