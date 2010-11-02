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
package net.sf.orcc.interpreter;

import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.runtime.Fifo;

/**
 * This class defines an actor that can be interpreted by calling
 * {@link #initialize()} and {@link #schedule()}. It consists in an action
 * scheduler, an FSM state, and a node interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ActorInterpreter {

	/**
	 * Actor's Intermediate Representation duplication for "inline" visiting
	 * interpretation
	 */
	private Actor actor;

	protected ExpressionEvaluator exprInterpreter;

	/**
	 * Actor's FSM current state
	 */
	protected String fsmState;

	/**
	 * Communication fifos map : key = related I/O port name; value =
	 * Fifo_int/boolean/String
	 */
	private Map<String, Fifo> ioFifos;

	private ListAllocator listAllocator;

	/**
	 * Interpretation and evaluation tools
	 */
	protected NodeInterpreter nodeInterpreter;

	/**
	 * Actor's constant parameters to be set at initialization time
	 */
	private Map<String, Expression> parameters;

	/**
	 * Actor's action scheduler
	 */
	protected ActionScheduler sched;

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
	public ActorInterpreter(Map<String, Expression> parameters, Actor actor,
			OrccProcess process) {
		// Set instance name and actor class definition at parent level
		this.actor = actor;

		// Build a node interpreter for visiting CFG and instructions
		nodeInterpreter = new NodeInterpreter();
		nodeInterpreter.setProcess(process);

		// Create the List allocator for state and procedure local vars
		this.listAllocator = new ListAllocator();

		// Create the expression evaluator
		this.exprInterpreter = new ExpressionEvaluator();

		// Get actor FSM properties
		this.sched = actor.getActionScheduler();
		if (sched.hasFsm()) {
			this.fsmState = sched.getFsm().getInitialState().getName();
		} else {
			this.fsmState = "IDLE";
		}

		// Get the parameters value from instance map
		this.parameters = parameters;
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
		if (outputPattern != null) {
			for (Entry<Port, Integer> entry : outputPattern.entrySet()) {
				Port outputPort = entry.getKey();
				Integer nbOfTokens = entry.getValue();
				if (!ioFifos.get(outputPort.getName()).hasRoom(nbOfTokens)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Executes the given action.
	 * 
	 * @param action
	 */
	public void execute(Action action) {
		// Interpret the whole action
		interpretProc(action.getBody());
	}

	/**
	 * Returns the name of the current FSM state.
	 * 
	 * @return the name of the current FSM state
	 */
	public String getFsmState() {
		return fsmState;
	}

	/**
	 * Get the next schedulable action to be executed for this actor
	 * 
	 * @return the schedulable action or null
	 */
	public Action getNextAction() {
		if (sched.hasFsm()) {
			// Check for untagged actions first
			for (Action action : sched.getActions()) {
				if (isSchedulable(action)) {
					if (checkOutputPattern(action.getOutputPattern())) {
						return action;
					}
					break;
				}
			}

			// Then check for next FSM transition
			for (NextStateInfo info : sched.getFsm().getTransitions(fsmState)) {
				Action action = info.getAction();
				if (isSchedulable(action)) {
					// Update FSM state
					if (checkOutputPattern(action.getOutputPattern())) {
						fsmState = info.getTargetState().getName();
						return action;
					}
					break;
				}
			}
		} else {
			// Check next schedulable action in respect of the priority order
			for (Action action : sched.getActions()) {
				if (isSchedulable(action)) {
					if (checkOutputPattern(action.getOutputPattern())) {
						return action;
					}
					break;
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
			for (Variable param : actor.getParameters()) {
				Expression value = parameters.get(param.getName());
				if (value != null) {
					param.setValue((Expression) value.accept(exprInterpreter));
				}
			}

			// Check for List state variables which need to be allocated or
			// initialized
			for (Variable stateVar : actor.getStateVars()) {
				Type type = stateVar.getType();
				// Initialize variables with constant values
				Expression initConst = ((StateVariable) stateVar)
						.getConstantValue();
				if (initConst == null) {
					if (type.isList()) {
						// Allocate empty array variable
						stateVar.setValue((Expression) type
								.accept(listAllocator));
					}
				} else {
					// initialize
					stateVar.setValue(initConst);
				}
			}

			// Get initializing procedure if any
			for (Action action : actor.getInitializes()) {
				if (isSchedulable(action)) {
					interpretProc(action.getBody());
					continue;
				}
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName(), ex);
		}
	}

	/**
	 * Interprets the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * 
	 * @return an object which contains procedure returned value
	 */
	protected Expression interpretProc(Procedure procedure) {
		// Don't mind about procedure parameters (already set)

		// Allocate local List variables
		for (Variable local : procedure.getLocals()) {
			Type type = local.getType();
			if (type.isList()) {
				local.setValue((Expression) type.accept(listAllocator));
			}
		}

		// Interpret procedure body
		for (CFGNode node : procedure.getNodes()) {
			node.accept(nodeInterpreter);
		}

		return nodeInterpreter.getReturnValue();
	}

	/**
	 * Returns true if the given action is schedulable.
	 * 
	 * @param action
	 *            an action
	 * @return true if the given action is schedulable
	 */
	protected boolean isSchedulable(Action action) {
		Expression isSchedulable = interpretProc(action.getScheduler());
		if (isSchedulable != null && isSchedulable.isBooleanExpr()) {
			return ((BoolExpr) isSchedulable).getValue();
		} else {
			return false;
		}
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
	 * Set the communication FIFOs map for interpreter to be able to execute
	 * read/write accesses.
	 * 
	 * @param fifos
	 */
	public void setFifos(Map<String, Fifo> fifos) {
		ioFifos = fifos;
		nodeInterpreter.setFifos(ioFifos);
	}

	/**
	 * Set the interpreter FSM state. Must be used with caution. This method is
	 * useful for synchronizing the interpretation with an external actor's
	 * instance state.
	 * 
	 */
	public void setFsmState(String newState) {
		fsmState = newState;
	}

}
