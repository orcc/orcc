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
package net.sf.orcc.backends.vhdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.VarGlobal;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.instructions.Load;

/**
 * This class defines template data for the VHDL back-end.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class VHDLTemplateData extends AbstractActorVisitor {

	/**
	 * This class defines a visitor that computes the sensitivity list of the
	 * scheduler process.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class SensitivityComputer extends AbstractActorVisitor {

		@Override
		public void visit(Actor actor) {
			for (Port port : actor.getInputs()) {
				signals.add(port.getName() + "_send");
			}

			for (Port port : actor.getOutputs()) {
				signals.add(port.getName() + "_rdy");
			}

			for (Action action : actor.getActions()) {
				Pattern inputPattern = action.getInputPattern();
				for (Port port : inputPattern.getPorts()) {
					if (inputPattern.getPeeked(port) != null) {
						String name = port.getName() + "_data";
						signals.add(name);
					}
				}
			}

			if (actor.getActionScheduler().hasFsm()) {
				signals.add("FSM");
			}
		}

		@Override
		public void visit(Load node) {
			Var var = node.getSource().getVariable();
			if (!var.getType().isList() && var.isAssignable()) {
				signals.add(var.getName());
			}
		}

	}

	private Map<VarGlobal, Boolean> customInitMap;

	private Expression initValue;

	private Map<VarGlobal, Expression> initValueMap;

	/**
	 * the set of signals that appear in the sensitivity list of the scheduler
	 * process.
	 */
	private Set<String> signals;

	/**
	 * Returns <code>true</code> if the given variable has an initial value that
	 * is not a list of a unique value.
	 * 
	 * @param variable
	 *            a variable
	 * @param value
	 *            a value
	 * @return <code>true</code> if the given variable has an initial value that
	 *         is not a list of a unique value
	 */
	private boolean getCustomInitFlag(VarGlobal variable, Expression value) {
		if (value.isListExpr()) {
			ListExpr exprList = (ListExpr) value;
			for (Expression subExpr : exprList.getValue()) {
				boolean customInit = getCustomInitFlag(variable, subExpr);
				if (customInit) {
					return true;
				}
			}
		} else {
			if (initValue == null) {
				initValue = value;
			} else {
				if (!initValue.equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns the map of global variables to custom init flag.
	 * 
	 * @return the map of global variables to custom init flag
	 */
	public Map<VarGlobal, Boolean> getCustomInitMap() {
		return customInitMap;
	}

	/**
	 * Returns the initValue map.
	 * 
	 * @return the initValue map
	 */
	public Map<VarGlobal, Expression> getInitValueMap() {
		return initValueMap;
	}

	/**
	 * Returns the list of variables.
	 * 
	 * @return the list of variables
	 */
	public List<String> getSignals() {
		return new ArrayList<String>(signals);
	}

	/**
	 * Initializes the template data from the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void initializeFrom(Actor actor) {
		signals = new LinkedHashSet<String>();
		new SensitivityComputer().visit(actor);

		customInitMap = new HashMap<VarGlobal, Boolean>();
		initValueMap = new HashMap<VarGlobal, Expression>();
		for (VarGlobal variable : actor.getStateVars()) {
			Expression initialValue = variable.getInitialValue();
			if (variable.getType().isList() && variable.isAssignable()
					&& initialValue != null) {
				initValue = null;

				// compute custom init
				boolean customInit = getCustomInitFlag(variable, initialValue);
				customInitMap.put(variable, customInit);

				// if not custom init, save initValue
				if (!customInit) {
					initValueMap.put(variable, initValue);
				}
			}
		}
	}

}
