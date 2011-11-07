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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.Transitions;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
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
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Unit;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

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
	protected Map<String, Expression> parameters;

	/**
	 * Creates a new interpreter with no actor and no parameters.
	 * 
	 */
	public ActorInterpreter() {
		this.parameters = Collections.emptyMap();
		exprInterpreter = new ExpressionEvaluator();
	}

	/**
	 * Creates a new interpreter.
	 * 
	 * @param actor
	 *            the actor to interpret
	 * @param parameters
	 *            parameters of the instance of the given actor
	 */
	public ActorInterpreter(Actor actor, Map<String, Expression> parameters) {
		this.parameters = parameters;
		exprInterpreter = new ExpressionEvaluator();
		setActor(actor);
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
			List<Arg> parameters) {
		return null;
	}

	/**
	 * Calls the print procedure. Prints to stdout by default. This method may
	 * be overridden.
	 * 
	 * @param procedure
	 *            a native procedure
	 * @param arguments
	 *            arguments of the procedure
	 */
	protected void callPrintProcedure(Procedure procedure, List<Arg> arguments) {
		for (Arg arg : arguments) {
			if (arg.isByVal()) {
				Expression expr = ((ArgByVal) arg).getValue();
				if (expr.isStringExpr()) {
					// String characters rework for escaped control
					// management
					String str = ((ExprString) expr).getValue();
					String unescaped = OrccUtil.getUnescapedString(str);
					System.out.print(unescaped);
				} else {
					Object value = exprInterpreter.doSwitch(expr);
					System.out.print(String.valueOf(value));
				}
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
		List<Arg> callParams = call.getParameters();

		// Special "print" case
		if (call.isPrint()) {
			callPrintProcedure(proc, callParams);
		} else if (proc.isNative()) {
			Object result = callNativeProcedure(proc, callParams);
			if (call.hasResult()) {
				call.getTarget().getVariable().setValue(result);
			}
		} else {
			List<Param> procParams = proc.getParameters();
			for (int i = 0; i < callParams.size(); i++) {
				Var procVar = procParams.get(i).getVariable();
				Arg arg = callParams.get(i);
				if (arg.isByVal()) {
					Expression value = ((ArgByVal) arg).getValue();
					procVar.setValue(exprInterpreter.doSwitch(value));
				}
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

				Type type = ((TypeList) source.getType()).getInnermostType();
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

				Type type = ((TypeList) target.getType()).getInnermostType();
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
			throw new OrccRuntimeException("Condition "
					+ new ExpressionPrinter().doSwitch(node.getCondition())
					+ " not boolean at line " + node.getLineNumber() + "\n");
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
	 * Executes the given action. This implementation allocates input/output
	 * pattern and executes the body. Should be overriden by implementations to
	 * perform read/write from/to FIFOs.
	 * 
	 * @param action
	 *            an action
	 */
	protected void execute(Action action) {
		Pattern input = action.getInputPattern();
		Pattern output = action.getOutputPattern();
		allocatePattern(input);
		allocatePattern(output);

		doSwitch(action.getBody());
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
			// initializes actors parameters from instance map
			for (Var param : actor.getParameters()) {
				Expression value = parameters.get(param.getName());
				param.setValue(exprInterpreter.doSwitch(value));
			}

			// initializes state variables
			for (Var stateVar : actor.getStateVars()) {
				initializeVar(stateVar);
			}

			// initializes runtime value of constants declared in units
			Resource resource = actor.eResource();
			if (resource != null) {
				ResourceSet set = resource.getResourceSet();
				for (Resource res : set.getResources()) {
					EObject eObject = res.getContents().get(0);
					if (eObject instanceof Unit) {
						Unit unit = (Unit) eObject;
						for (Var var : unit.getConstants()) {
							if (var.getValue() == null) {
								initializeVar(var);
							}
						}
					}
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
	 * Initializes the given variable.
	 * 
	 * @param variable
	 *            a variable
	 */
	protected void initializeVar(Var variable) {
		Type type = variable.getType();
		Expression initConst = variable.getInitialValue();
		if (initConst == null) {
			if (type.isList()) {
				// allocate empty array variable
				variable.setValue(ValueUtil.createArray((TypeList) type));
			}
		} else {
			// evaluate initial constant value
			if (type.isList()) {
				exprInterpreter.setType((TypeList) type);
			}
			variable.setValue(exprInterpreter.doSwitch(initConst));
		}
	}

	/**
	 * Returns true if the given action is schedulable. This implementation
	 * allocates the peek pattern and calls the scheduler procedure. This method
	 * should be overridden to define how to test the schedulability of an
	 * action.
	 * 
	 * @param action
	 *            an action
	 * @return true if the given action is schedulable
	 */
	protected boolean isSchedulable(Action action) {
		Pattern pattern = action.getPeekPattern();
		allocatePattern(pattern);
		Object result = doSwitch(action.getScheduler());
		return ValueUtil.isTrue(result);
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

	/**
	 * Sets the actor interpreter by this interpreter.
	 * 
	 * @param actor
	 *            an actor
	 */
	protected void setActor(Actor actor) {
		this.actor = actor;

		// set fsm state to initial state (if any)
		if (actor.hasFsm()) {
			fsmState = actor.getFsm().getInitialState();
		} else {
			fsmState = null;
		}

	}

}
