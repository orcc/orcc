/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
import java.util.Iterator;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an abstract interpreter of an actor. It refines the
 * concrete interpreter by not relying on anything that is data-dependent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AbstractInterpreter extends ActorInterpreter {

	private Map<Port, Expression> configuration;

	private Map<Port, Boolean> portRead;

	private boolean schedulableMode;

	private Action scheduledAction;

	/**
	 * Creates a new abstract interpreter.
	 * 
	 * @param actor
	 *            an actor
	 * @param analyzer
	 *            a configuration analyzer
	 */
	public AbstractInterpreter(Actor actor) {
		super(actor, new HashMap<String, Expression>(0));

		exprInterpreter = new AbstractExpressionEvaluator();
	}

	@Override
	public void execute(Action action) {
		// allocate patterns
		Pattern inputPattern = action.getInputPattern();
		Pattern outputPattern = action.getOutputPattern();
		allocatePattern(inputPattern);
		allocatePattern(outputPattern);

		scheduledAction = action;
		for (Port port : inputPattern.getPorts()) {
			int numTokens = inputPattern.getNumTokens(port);
			if (configuration != null && configuration.containsKey(port)
					&& !portRead.get(port)) {
				// Should we use a range of values in the spirit of
				// "Accurate Static Branch Prediction by Value Range Propagation"?

				// in the meantime, we only use the configuration value in the
				// Peek

				portRead.put(port, true);
			}

			port.increaseTokenConsumption(numTokens);
		}

		doSwitch(action.getBody());

		for (Port port : outputPattern.getPorts()) {
			int numTokens = outputPattern.getNumTokens(port);
			port.increaseTokenProduction(numTokens);
		}
	}

	/**
	 * Returns the latest action that was scheduled by the latest call to
	 * {@link #schedule()}.
	 * 
	 * @return the latest scheduled action
	 */
	public Action getScheduledAction() {
		return scheduledAction;
	}

	@Override
	protected boolean isSchedulable(Action action) {
		// do not check the number of tokens present on FIFOs

		// allocates peeked variables
		Pattern pattern = action.getPeekPattern();
		for (Port port : pattern.getPorts()) {
			Var peeked = pattern.getVariable(port);
			if (peeked != null) {
				peeked.setValue(tokenAllocator.doSwitch(peeked.getType()));

				if (configuration != null && configuration.containsKey(port)
						&& !portRead.get(port)) {
					ExprList target = (ExprList) peeked.getValue();
					target.set(0, configuration.get(port));
				}
			}
		}

		// enable schedulable mode
		setSchedulableMode(true);
		try {
			Object result = doSwitch(action.getScheduler());
			if (result == null) {
				throw new OrccRuntimeException("could not determine if action "
						+ action.toString() + " is schedulable");
			}
			return (result instanceof ExprBool)
					&& ((ExprBool) result).isValue();
		} finally {
			// disable schedulable mode
			setSchedulableMode(false);
		}
	}

	/**
	 * Sets the configuration that should be used by the interpreter.
	 * 
	 * @param configuration
	 *            a configuration as a map of ports and values
	 */
	public void setConfiguration(Map<Port, Expression> configuration) {
		this.configuration = configuration;
		portRead = new HashMap<Port, Boolean>(configuration.size());
		for (Port port : configuration.keySet()) {
			portRead.put(port, false);
		}
	}

	/**
	 * Sets schedulable mode. When in schedulable mode, evaluations of null
	 * expressions is forbidden.
	 * 
	 * @param schedulableMode
	 */
	public void setSchedulableMode(boolean schedulableMode) {
		this.schedulableMode = schedulableMode;
		((AbstractExpressionEvaluator) exprInterpreter)
				.setSchedulableMode(schedulableMode);
	}

	@Override
	public Object caseInstLoad(InstLoad instr) {
		Var target = instr.getTarget().getVariable();
		Var source = instr.getSource().getVariable();
		if (instr.getIndexes().isEmpty()) {
			target.setValue(EcoreUtil.copy(source.getValue()));
		} else {
			Expression value = source.getValue();
			for (Expression index : instr.getIndexes()) {
				if (value != null && value.isListExpr()) {
					index = exprInterpreter.doSwitch(index);
					if (index == null) {
						value = null;
						break;
					} else {
						value = ((ExprList) value).get((ExprInt) index);
					}
				}
			}
			target.setValue(EcoreUtil.copy(value));
		}
		return null;
	}

	@Override
	public Object caseInstPhi(InstPhi phi) {
		if (branch != -1) {
			return super.caseInstPhi(phi);
		}
		return null;
	}

	@Override
	public Object caseInstStore(InstStore instr) {
		Var var = instr.getTarget().getVariable();
		if (instr.getIndexes().isEmpty()) {
			var.setValue(exprInterpreter.doSwitch(instr.getValue()));
		} else {
			Expression target = var.getValue();
			Iterator<Expression> it = instr.getIndexes().iterator();
			ExprInt index = (ExprInt) exprInterpreter.doSwitch(it.next());

			while (it.hasNext()) {
				if (target != null && target.isListExpr() && index != null) {
					target = ((ExprList) target).get(index);
				}
				index = (ExprInt) exprInterpreter.doSwitch(it.next());
			}

			Expression value = (Expression) exprInterpreter.doSwitch(instr
					.getValue());
			if (target != null && target.isListExpr() && index != null) {
				if (value == null) {
					// create new expression with null value
					Type type = ((TypeList) var.getType()).getElementType();
					if (type.isBool()) {
						value = IrFactory.eINSTANCE.createExprBool();
					} else if (type.isFloat()) {
						value = IrFactory.eINSTANCE.createExprFloat();
					} else if (type.isInt() || type.isUint()) {
						value = IrFactory.eINSTANCE.createExprInt();
					} else if (type.isString()) {
						value = IrFactory.eINSTANCE.createExprString();
					} else {
						// else do not set value
						return null;
					}
				}
				((ExprList) target).set(index, value);
			}
		}
		return null;
	}

	@Override
	public Object caseNodeIf(NodeIf node) {
		// Interpret first expression ("if" condition)
		Expression condition = exprInterpreter.doSwitch(node.getCondition());

		int oldBranch = branch;
		if (condition != null && condition.isBooleanExpr()) {
			if (((ExprBool) condition).isValue()) {
				doSwitch(node.getThenNodes());
				branch = 0;
			} else {
				doSwitch(node.getElseNodes());
				branch = 1;
			}

		} else {
			if (schedulableMode) {
				// only throw exception in schedulable mode
				throw new OrccRuntimeException("null condition");
			}

			branch = -1;
		}

		doSwitch(node.getJoinNode());
		branch = oldBranch;
		return null;
	}

	@Override
	public Object caseNodeWhile(NodeWhile node) {
		int oldBranch = branch;
		branch = 0;
		doSwitch(node.getJoinNode());

		// Interpret first expression ("while" condition)
		Expression condition = exprInterpreter.doSwitch(node.getCondition());

		if (condition != null && condition.isBooleanExpr()) {
			branch = 1;
			while (((ExprBool) condition).isValue()) {
				doSwitch(node.getNodes());
				doSwitch(node.getJoinNode());

				// Interpret next value of "while" condition
				condition = exprInterpreter.doSwitch(node.getCondition());
				if (schedulableMode
						&& (condition == null || !condition.isBooleanExpr())) {
					throw new OrccRuntimeException(
							"Condition not boolean at line "
									+ node.getLineNumber() + "\n");
				}
			}
		} else if (schedulableMode) {
			// only throw exception in schedulable mode
			throw new OrccRuntimeException("condition is data-dependent");
		}

		branch = oldBranch;
		return null;
	}

}
