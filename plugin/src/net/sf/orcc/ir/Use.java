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

import java.util.List;

import net.sf.orcc.ir.expr.AbstractExpressionVisitor;
import net.sf.orcc.ir.expr.VarExpr;

/**
 * This class defines a use of a variable.
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
	 * the node where the variable referenced is used. May be <code>null</code>
	 * if the information is not available or has no meaning.
	 */
	private User node;

	/**
	 * the variable referenced
	 */
	private Variable variable;

	/**
	 * Creates a new use of the given variable. This use is added to the use
	 * list of the newly referenced variable.
	 * 
	 * @param variable
	 *            a variable
	 */
	public Use(Variable variable) {
		setVariable(variable);
	}

	/**
	 * Creates a new use of the given variable. This use is added to the use
	 * list of the newly referenced variable. The node parameter must not be
	 * <code>null</code>, if you want to create a use with no node, use the
	 * other constructor.
	 * 
	 * @param variable
	 *            a variable
	 * @param user
	 *            a node
	 * @throws NullPointerException
	 *             if node is null.
	 */
	public Use(Variable variable, User user) {
		if (user == null) {
			throw new NullPointerException();
		}
		setVariable(variable);
		this.node = user;
	}

	/**
	 * Returns the node referenced by this use.May be <code>null</code> if the
	 * information is not available.
	 * 
	 * @return the node referenced by this use
	 */
	public User getNode() {
		return node;
	}

	/**
	 * Returns the variable referenced by this use.
	 * 
	 * @return the variable referenced by this use
	 */
	public Variable getVariable() {
		return variable;
	}

	/**
	 * Removes this variable use from the variable referenced.
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
	 * Sets the variable referenced by this use to the given variable. This use
	 * is removed from the use list of the previously referenced variable, and
	 * added to the use list of the newly referenced variable.
	 * 
	 * @param variable
	 *            a variable that this use will reference
	 */
	public void setVariable(Variable variable) {
		if (this.variable != null) {
			this.variable.removeUse(this);
		}
		this.variable = variable;
		variable.addUse(this);
	}

	@Override
	public String toString() {
		return variable.toString();
	}

}
