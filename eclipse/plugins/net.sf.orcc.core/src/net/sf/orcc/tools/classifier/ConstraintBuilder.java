/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.tools.classifier;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrSwitch;
import net.sf.orcc.network.Network;

public class ConstraintBuilder {

	/**
	 * This class defines a visitor that examines expressions that depend on a
	 * value peeked from the configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ConstraintExpressionVisitor extends IrSwitch<Object> {

		/**
		 * Creates a new constraint expression visitor.
		 * 
		 */
		public ConstraintExpressionVisitor() {
		}

	}

	private boolean negateConstraints;

	/**
	 * the constraint network
	 */
	private Network network;

	/**
	 * a map of IR vars to constraint vars
	 */
	private Map<Var, Var> vars;

	public ConstraintBuilder(Actor actor) {
		vars = new HashMap<Var, Var>();
	}

	/**
	 * Associate the target local variable with the source variable.
	 * 
	 * @param target
	 *            a target local variable
	 * @param var
	 *            a state variable or a port
	 */
	private void associateVariable(Var target, Var var) {
		Var source = vars.get(target);
		if (source == null) {
			vars.put(target, var);
		}
	}

	/**
	 * Returns the domain of the given variable, or throws an exception.
	 * 
	 * @param var
	 *            a variable
	 * @return the domain of the given variable
	 */
	private void getDomain(Var var) {
		int lo;
		int hi;

		Expression value = var.getValue();
		Type type = var.getType();
		if (type.isList()) {
			type = ((TypeList) type).getElementType();
		}

		if (type.isInt()) {
			if (value != null && value.isIntExpr()) {
				lo = ((ExprInt) value).getIntValue();
				hi = lo;
			} else {
				int size = ((TypeInt) type).getSize();
				lo = -(1 << (size - 1));
				hi = (1 << (size - 1)) - 1;
			}
		} else if (type.isUint()) {
			if (value != null && value.isIntExpr()) {
				lo = ((ExprInt) value).getIntValue();
				hi = lo;
			} else {
				int size = ((TypeUint) type).getSize();
				lo = 0;
				hi = 1 << size - 1;
			}
		} else if (type.isBool()) {
			if (value != null && value.isBooleanExpr()) {
				lo = ((ExprBool) value).isValue() ? 1 : 0;
				hi = lo;
			} else {
				lo = 0;
				hi = 1;
			}
		} else {
			throw new OrccRuntimeException("type of variable not supported");
		}

		//return new IntDomain(lo, hi);
	}

	/**
	 * Returns the network of constraints associated with this constraint
	 * builder.
	 * 
	 * @return the network of constraints associated with this constraint
	 *         builder
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * Sets the negateConstraints flag.
	 * 
	 * @param negateConstraints
	 *            if <code>true</code>, constraints created will be negated
	 */
	public void setNegateConstraints(boolean negateConstraints) {
		this.negateConstraints = negateConstraints;
	}

	/**
	 * Visits the given action with the given visitor.
	 * 
	 * @param action
	 *            action associated with the next state
	 * @param visitor
	 *            a node visitor
	 */
	public void visitAction(Action action) {
		Pattern pattern = action.getPeekPattern();
		for (Port port : pattern.getPorts()) {
			Var peeked = pattern.getVariable(port);
			if (peeked != null) {
				// associate variable
				Var source = vars.get(peeked);
				if (source == null) {
					source = IrFactory.eINSTANCE.createVar(port.getType(),
							port.getName(), true, 0);
					vars.put(peeked, source);
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean checkSat() {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<Port, Expression> getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
