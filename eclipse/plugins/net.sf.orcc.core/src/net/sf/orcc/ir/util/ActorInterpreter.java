/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir.util;

import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.Transition;
import net.sf.orcc.ir.Transitions;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccUtil;

/**
 * This class defines an interpreter for an actor. The interpreter can
 * {@link #initialize()} and {@link #schedule()} the actor.
 * 
 * @author Pierre-Laurent Lagalaye
 * @author Matthieu Wipliez
 * 
 */
public class ActorInterpreter extends AbstractActorVisitor<Object> {

	/**
	 * branch being visited
	 */
	protected int branch;

	protected ExpressionEvaluator exprInterpreter;

	/**
	 * Actor's FSM current state
	 */
	private State fsmState;

	/**
	 * Actor's constant parameters to be set at initialization time
	 */
	private Map<String, Expression> parameters;

	/**
	 * Creates a new interpreted actor instance for simulation or debug
	 * 
	 * @param id
	 *            name of the associated instance
	 * @param parameters
	 *            actor's parameters to be set
	 * @param actor
	 *            actor class definition
	 */
	public ActorInterpreter(Actor actor, Map<String, Expression> parameters) {
		// Set instance name and actor class definition at parent level
		this.actor = actor;

		exprInterpreter = new ExpressionEvaluator();

		// Get actor FSM properties
		if (actor.hasFsm()) {
			fsmState = actor.getFsm().getInitialState();
		} else {
			fsmState = null;
		}

		// Get the parameters value from instance map
		this.parameters = parameters;
	}

	/**
	 * Allocates the variables of the given pattern.
	 * 
	 * @param pattern
	 *            a pattern
	 */
	final protected void allocatePattern(Pattern pattern) {
		for (Port port : pattern.getPorts()) {
			Var var = pattern.getVariable(port);
			Object value = ValueUtil.createArray((TypeList) var.getType());
			var.setValue(value);
		}
	}

	/**
	 * Calls the given native procedure. Does nothing by default. This method
	 * may be overridden if one wishes to call native procedures.
	 * 
	 * @param procedure
	 *            a native procedure
	 * @return the result of calling the given procedure
	 */
	protected Object callNativeProcedure(Procedure procedure,
			List<Expression> parameters) {
		return null;
	}

	/**
	 * Calls the print procedure. Prints to stdout by default. This method may
	 * be overridden.
	 * 
	 * @param procedure
	 *            a native procedure
	 */
	protected void callPrintProcedure(Procedure procedure,
			List<Expression> parameters) {
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).isStringExpr()) {
				// String characters rework for escaped control
				// management
				String str = ((ExprString) parameters.get(i)).getValue();
				String unescaped = OrccUtil.getUnescapedString(str);
				System.out.print(unescaped);
			} else {
				Object value = exprInterpreter.doSwitch(parameters.get(i));
				System.out.print(String.valueOf(value));
			}
		}
	}

	@Override
	public Object caseInstAssign(InstAssign instr) {
		try {
			Var target = instr.getTarget().getVariable();
			target.setValue(exprInterpreter.doSwitch(instr.getValue()));
		} catch (OrccRuntimeException e) {
			String file;
			if (actor == null) {
				file = "";
			} else {
				file = actor.getFileName();
			}

			throw new OrccRuntimeException(file, instr.getLineNumber(), "", e);
		}
		return null;
	}

	@Override
	public Object caseInstCall(InstCall call) {
		// Get called procedure
		Procedure proc = call.getProcedure();

		// Set the input parameters of the called procedure if any
		List<Expression> callParams = call.getParameters();

		// Special "print" case
		if (call.isPrint()) {
			callPrintProcedure(proc, callParams);
		} else if (proc.isNative()) {
			Object result = callNativeProcedure(proc, callParams);
			if (call.hasResult()) {
				call.getTarget().getVariable().setValue(result);
			}
		} else {
			List<Var> procParams = proc.getParameters();
			for (int i = 0; i < callParams.size(); i++) {
				Var procVar = procParams.get(i);
				procVar.setValue(exprInterpreter.doSwitch(callParams.get(i)));
			}

			// Interpret procedure body
			Object result = doSwitch(proc);
			if (call.hasResult()) {
				call.getTarget().getVariable().setValue(result);
			}
		}
		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad instr) {
		Var target = instr.getTarget().getVariable();
		Var source = instr.getSource().getVariable();
		if (instr.getIndexes().isEmpty()) {
			target.setValue(source.getValue());
		} else {
			try {
				Object array = source.getValue();
				Object[] indexes = new Object[instr.getIndexes().size()];
				int i = 0;
				for (Expression index : instr.getIndexes()) {
					indexes[i++] = exprInterpreter.doSwitch(index);
				}

				Type type = ((TypeList) source.getType()).getElementType();
				Object value = ValueUtil.get(type, array, indexes);
				target.setValue(value);
			} catch (IndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLineNumber());
			}
		}
		return null;
	}

	@Override
	public Object caseInstPhi(InstPhi phi) {
		Expression value = phi.getValues().get(branch);
		phi.getTarget().getVariable().setValue(exprInterpreter.doSwitch(value));
		return null;
	}

	@Override
	public Object caseInstReturn(InstReturn instr) {
		if (instr.getValue() == null) {
			return null;
		}
		return exprInterpreter.doSwitch(instr.getValue());
	}

	@Override
	public Object caseInstSpecific(InstSpecific instr) {
		throw new OrccRuntimeException("does not know how to interpret a "
				+ "specific instruction");
	}

	@Override
	public Object caseInstStore(InstStore instr) {
		Var target = instr.getTarget().getVariable();
		Object value = exprInterpreter.doSwitch(instr.getValue());
		if (instr.getIndexes().isEmpty()) {
			target.setValue(value);
		} else {
			try {
				Object array = target.getValue();
				Object[] indexes = new Object[instr.getIndexes().size()];
				int i = 0;
				for (Expression index : instr.getIndexes()) {
					indexes[i++] = exprInterpreter.doSwitch(index);
				}

				Type type = ((TypeList) target.getType()).getElementType();
				ValueUtil.set(type, array, value, indexes);
			} catch (IndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLineNumber());
			}
		}
		return null;
	}

	@Override
	public Object caseNodeIf(NodeIf node) {
		// Interpret first expression ("if" condition)
		Object condition = exprInterpreter.doSwitch(node.getCondition());

		Object ret;
		// if (condition is true)
		if (ValueUtil.isBool(condition)) {
			int oldBranch = branch;
			if (ValueUtil.isTrue(condition)) {
				doSwitch(node.getThenNodes());
				branch = 0;
			} else {
				doSwitch(node.getElseNodes());
				branch = 1;
			}

			ret = doSwitch(node.getJoinNode());
			branch = oldBranch;
		} else {
			throw new OrccRuntimeException("Condition not boolean at line "
					+ node.getLineNumber() + "\n");
		}
		return ret;
	}

	@Override
	public Object caseNodeWhile(NodeWhile node) {
		int oldBranch = branch;
		branch = 0;
		doSwitch(node.getJoinNode());

		// Interpret first expression ("while" condition)
		Object condition = exprInterpreter.doSwitch(node.getCondition());

		// while (condition is true) do
		branch = 1;
		while (ValueUtil.isTrue(condition)) {
			doSwitch(node.getNodes());
			doSwitch(node.getJoinNode());

			// Interpret next value of "while" condition
			condition = exprInterpreter.doSwitch(node.getCondition());
		}

		branch = oldBranch;
		return null;
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		// Allocate local List variables
		for (Var local : procedure.getLocals()) {
			Type type = local.getType();
			if (type.isList()) {
				Object value = ValueUtil
						.createArray((TypeList) local.getType());
				local.setValue(value);
			}
		}

		return super.caseProcedure(procedure);
	}

	/**
	 * Returns true if the action has no output pattern, or if it has an output
	 * pattern and there is enough room in the FIFOs to satisfy it.
	 * 
	 * @param outputPattern
	 *            output pattern of an action
	 * @return true if the pattern is empty or satisfiable
	 */
	protected boolean checkOutputPattern(Pattern outputPattern) {
		return true;
	}

	/**
	 * Executes the given action. Does nothing by default. May be overriden by
	 * implementations.
	 * 
	 * @param action
	 *            an action
	 */
	protected void execute(Action action) {
	}

	/**
	 * Returns the current FSM state.
	 * 
	 * @return the current FSM state
	 */
	public final State getFsmState() {
		return fsmState;
	}

	/**
	 * Get the next schedulable action to be executed for this actor
	 * 
	 * @return the schedulable action or null
	 */
	public final Action getNextAction() {
		// Check next schedulable action in respect of the priority order
		for (Action action : actor.getActionsOutsideFsm()) {
			if (isSchedulable(action)) {
				if (checkOutputPattern(action.getOutputPattern())) {
					return action;
				}
				return null;
			}
		}

		if (actor.hasFsm()) {
			// Then check for next FSM transition
			Transitions transitions = actor.getFsm().getTransitions(fsmState);
			for (Transition transition : transitions.getList()) {
				Action action = transition.getAction();
				if (isSchedulable(action)) {
					// Update FSM state
					if (checkOutputPattern(action.getOutputPattern())) {
						fsmState = transition.getState();
						return action;
					}
					return null;
				}
			}
		}

		return null;
	}

	/**
	 * Initialize interpreted actor. That is to say constant parameters,
	 * initialized state variables, allocation and initialization of state
	 * arrays.
	 */
	public void initialize() {
		try {
			// Initialize actors parameters with instance map
			for (Var param : actor.getParameters()) {
				Expression value = parameters.get(param.getName());
				param.setValue(exprInterpreter.doSwitch(value));
			}

			// Check for List state variables which need to be allocated or
			// initialized
			for (Var stateVar : actor.getStateVars()) {
				Type type = stateVar.getType();
				// Initialize variables with constant values
				Expression initConst = stateVar.getInitialValue();
				if (initConst == null) {
					if (type.isList()) {
						// Allocate empty array variable
						stateVar.setValue(ValueUtil
								.createArray((TypeList) type));
					}
				} else {
					// initialize
					stateVar.setValue(exprInterpreter.doSwitch(initConst));
				}
			}

			// Get initializing procedure if any
			for (Action action : actor.getInitializes()) {
				if (isSchedulable(action)) {
					execute(action);
					continue;
				}
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName(), ex);
		}
	}

	/**
	 * Returns true if the given action is schedulable. Always returns
	 * <code>true</code> by default. This method should be overridden to define
	 * how to test the schedulability of an action.
	 * 
	 * @param action
	 *            an action
	 * @return true if the given action is schedulable
	 */
	protected boolean isSchedulable(Action action) {
		return true;
	}

	/**
	 * Schedule next schedulable action if any
	 * 
	 * @return <code>true</code> if an action was scheduled, <code>false</code>
	 *         otherwise
	 */
	public boolean schedule() {
		try {
			// "Synchronous-like" scheduling policy : schedule only 1 action per
			// actor at each "schedule" (network logical cycle) call
			Action action = getNextAction();
			if (action == null) {
				return false;
			} else {
				execute(action);
				return true;
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName(), ex);
		}
	}

}
