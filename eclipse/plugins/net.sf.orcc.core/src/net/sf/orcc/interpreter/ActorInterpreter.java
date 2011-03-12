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

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_String;
import net.sf.orcc.runtime.Fifo_boolean;
import net.sf.orcc.runtime.Fifo_int;
import net.sf.orcc.util.OrccUtil;

/**
 * This class defines an actor that can be interpreted by calling
 * {@link #initialize()} and {@link #schedule()}. It consists in an action
 * scheduler, an FSM state, and a node interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ActorInterpreter extends AbstractActorVisitor {

	private class JavaExpressionConverter implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			return null;
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			return expr.getValue();
		}

		@Override
		public Object interpret(FloatExpr expr, Object... args) {
			return expr.getValue();
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			return expr.getIntValue();
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			Object[] values = new Object[expr.getValue().size()];
			int i = 0;
			for (Expression subExpr : expr.getValue()) {
				if (subExpr != null) {
					values[i] = subExpr.accept(this);
				}
				i++;
			}
			return values;
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			return expr.getValue();
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			return null;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			return expr.getVar().getVariable().getValue().accept(this);
		}

	}

	/**
	 * Actor's Intermediate Representation duplication for "inline" visiting
	 * interpretation
	 */
	private Actor actor;

	private boolean blockReturn;

	protected int branch;

	protected ExpressionEvaluator exprInterpreter;

	/**
	 * Communication fifos map : key = related I/O port name; value =
	 * Fifo_int/boolean/String
	 */
	private Map<String, Fifo> fifos;

	/**
	 * Actor's FSM current state
	 */
	protected String fsmState;

	protected ListAllocator listAllocator;

	/**
	 * Actor's constant parameters to be set at initialization time
	 */
	private Map<String, Expression> parameters;

	private OrccProcess process;

	private Expression returnValue;

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
	 * Allocates the variables of the given pattern.
	 * 
	 * @param pattern
	 *            a pattern
	 */
	private void allocatePattern(Pattern pattern) {
		for (Port port : pattern.getPorts()) {
			Variable variable = pattern.getVariable(port);
			variable.setValue((Expression) variable.getType().accept(
					listAllocator));
		}
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
			for (Entry<Port, Integer> entry : outputPattern.getNumTokensMap()
					.entrySet()) {
				Port outputPort = entry.getKey();
				Integer nbOfTokens = entry.getValue();
				if (!fifos.get(outputPort.getName()).hasRoom(nbOfTokens)) {
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
		// allocate patterns
		Pattern inputPattern = action.getInputPattern();
		Pattern outputPattern = action.getOutputPattern();
		allocatePattern(inputPattern);
		allocatePattern(outputPattern);

		for (Port port : inputPattern.getPorts()) {
			int numTokens = inputPattern.getNumTokens(port);
			Fifo fifo = fifos.get(port.getName());
			peekFifo(inputPattern.getVariable(port).getValue(), fifo, numTokens);
		}

		// Interpret the whole action
		visit(action.getBody());

		for (Port port : inputPattern.getPorts()) {
			int numTokens = inputPattern.getNumTokens(port);
			Fifo fifo = fifos.get(port.getName());
			fifo.readEnd(numTokens);
		}

		for (Port port : outputPattern.getPorts()) {
			int numTokens = outputPattern.getNumTokens(port);
			Fifo fifo = fifos.get(port.getName());
			writeFifo(outputPattern.getVariable(port).getValue(), fifo,
					numTokens);
		}
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
	 * Returns the value returned by the last procedure interpreted, or
	 * <code>null</code>.
	 * 
	 * @return the value returned by the last procedure interpreted
	 */
	public Expression getReturnValue() {
		if (blockReturn) {
			return returnValue;
		} else {
			return null;
		}
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
				param.setValue(value);
			}

			// Check for List state variables which need to be allocated or
			// initialized
			for (GlobalVariable stateVar : actor.getStateVars()) {
				Type type = stateVar.getType();
				// Initialize variables with constant values
				Expression initConst = stateVar.getInitialValue();
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
					visit(action.getBody());
					continue;
				}
			}
		} catch (OrccRuntimeException ex) {
			throw new OrccRuntimeException("Runtime exception thrown by actor "
					+ actor.getName(), ex);
		}
	}

	/**
	 * Returns true if the given action is schedulable.
	 * 
	 * @param action
	 *            an action
	 * @return true if the given action is schedulable
	 */
	protected boolean isSchedulable(Action action) {
		Pattern pattern = action.getInputPattern();
		// check tokens
		for (Port port : pattern.getPorts()) {
			Fifo fifo = fifos.get(port.getName());
			boolean hasTok = fifo.hasTokens(pattern.getNumTokens(port));
			if (!hasTok) {
				return false;
			}
		}

		// allocates peeked variables
		for (Port port : pattern.getPorts()) {
			Variable peeked = pattern.getPeeked(port);
			if (peeked != null) {
				peeked.setValue((Expression) peeked.getType().accept(
						listAllocator));
				int numTokens = pattern.getNumTokens(port);
				Fifo fifo = fifos.get(port.getName());
				peekFifo(peeked.getValue(), fifo, numTokens);
			}
		}

		visit(action.getScheduler());
		if (returnValue != null && returnValue.isBooleanExpr()) {
			return ((BoolExpr) returnValue).getValue();
		} else {
			return false;
		}
	}

	private void peekFifo(Expression value, Fifo fifo, int numTokens) {
		if (fifo instanceof Fifo_int) {
			ListExpr target = (ListExpr) value;
			int[] intTarget = new int[target.getSize()];
			System.arraycopy(((Fifo_int) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), intTarget, 0, numTokens);
			for (int i = 0; i < intTarget.length; i++) {
				target.set(i, new IntExpr(intTarget[i]));
			}
		} else if (fifo instanceof Fifo_boolean) {
			ListExpr target = (ListExpr) value;
			boolean[] boolTarget = new boolean[target.getSize()];
			System.arraycopy(((Fifo_boolean) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), boolTarget, 0, numTokens);
			for (int i = 0; i < boolTarget.length; i++) {
				target.set(i, new BoolExpr(boolTarget[i]));
			}
		} else if (fifo instanceof Fifo_String) {
			ListExpr target = (ListExpr) value;
			String[] stringTarget = new String[target.getSize()];
			System.arraycopy(((Fifo_String) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), stringTarget, 0, numTokens);
			for (int i = 0; i < stringTarget.length; i++) {
				target.set(i, new StringExpr(stringTarget[i]));
			}
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
	 * Sets the actor attribute.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	/**
	 * Set the communication FIFOs map for interpreter to be able to execute
	 * read/write accesses.
	 * 
	 * @param fifos
	 */
	public void setFifos(Map<String, Fifo> fifos) {
		this.fifos = fifos;
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

	public void setProcess(OrccProcess process) {
		this.process = process;
	}

	@Override
	public void visit(Assign instr) {
		try {
			LocalVariable target = instr.getTarget();
			target.setValue((Expression) instr.getValue().accept(
					exprInterpreter));
		} catch (OrccRuntimeException e) {
			String file;
			if (actor == null) {
				file = "";
			} else {
				file = actor.getFile();
			}

			throw new OrccRuntimeException(file, instr.getLocation(), "", e);
		}
	}

	@Override
	public void visit(Call call) {
		// Get called procedure
		Procedure proc = call.getProcedure();

		// Set the input parameters of the called procedure if any
		List<Expression> callParams = call.getParameters();

		// Special "print" case
		if (call.isPrint()) {
			if (process != null) {
				for (int i = 0; i < callParams.size(); i++) {
					if (callParams.get(i).isStringExpr()) {
						// String characters rework for escaped control
						// management
						String str = ((StringExpr) callParams.get(i))
								.getValue();
						String unescaped = OrccUtil.getUnescapedString(str);
						process.write(unescaped);
					} else {
						Object value = callParams.get(i)
								.accept(exprInterpreter);
						process.write(String.valueOf(value));
					}
				}
			}
		} else {
			List<LocalVariable> procParams = proc.getParameters().getList();
			for (int i = 0; i < callParams.size(); i++) {
				Variable procVar = procParams.get(i);
				procVar.setValue((Expression) callParams.get(i).accept(
						exprInterpreter));
			}

			// Allocate procedure local List variables
			for (Variable local : proc.getLocals()) {
				Type type = local.getType();
				if (type.isList()) {
					local.setValue((Expression) type.accept(listAllocator));
				}
			}

			// Interpret procedure body
			visit(proc);

			// Get procedure result if any
			if (call.hasResult()) {
				call.getTarget().setValue(returnValue);
			}
		}
	}

	@Override
	public void visit(IfNode node) {
		// Interpret first expression ("if" condition)
		Expression condition = (Expression) node.getValue().accept(
				exprInterpreter);

		// if (condition is true)
		if (condition != null && condition.isBooleanExpr()) {
			int oldBranch = branch;
			if (((BoolExpr) condition).getValue()) {
				visit(node.getThenNodes());
				branch = 0;
			} else {
				visit(node.getElseNodes());
				branch = 1;
			}

			visit(node.getJoinNode());
			branch = oldBranch;
		} else {
			throw new OrccRuntimeException("Condition not boolean at line "
					+ node.getLocation().getStartLine() + "\n");
		}
	}

	@Override
	public void visit(Load instr) {
		LocalVariable target = instr.getTarget();
		Variable source = instr.getSource().getVariable();
		if (instr.getIndexes().isEmpty()) {
			target.setValue(source.getValue());
		} else {
			try {
				Expression value = source.getValue();
				for (Expression index : instr.getIndexes()) {
					if (value.isListExpr()) {
						value = ((ListExpr) value).get((IntExpr) index
								.accept(exprInterpreter));
					}
				}
				target.setValue(value);
			} catch (IndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLocation().getStartLine());
			}
		}
	}

	@Override
	public void visit(PhiAssignment phi) {
		Expression value = phi.getValues().get(branch);
		phi.getTarget().setValue((Expression) value.accept(exprInterpreter));
	}

	@Override
	public void visit(Procedure procedure) {
		if (procedure.isNative()) {
			int numParams = procedure.getParameters().getLength();
			Class<?>[] parameterTypes = new Class<?>[numParams];
			Object[] args = new Object[numParams];
			int i = 0;
			for (LocalVariable parameter : procedure.getParameters()) {
				args[i] = parameter.getValue().accept(
						new JavaExpressionConverter());
				parameterTypes[i] = args[i].getClass();

				i++;
			}

			try {
				Class<?> clasz = Class.forName(actor.getName());
				Method method = clasz.getMethod(procedure.getName(),
						parameterTypes);
				method.invoke(null, args);
			} catch (Exception e) {
				throw new OrccRuntimeException(
						"exception during native procedure call", e);
			}
		} else {
			// Allocate local List variables
			for (Variable local : procedure.getLocals()) {
				Type type = local.getType();
				if (type.isList()) {
					local.setValue((Expression) type.accept(listAllocator));
				}
			}

			super.visit(procedure);
		}
	}

	@Override
	public void visit(Return instr) {
		if (instr.getValue() != null) {
			this.returnValue = (Expression) instr.getValue().accept(
					exprInterpreter);
			this.blockReturn = true;
		}
	}

	@Override
	public void visit(SpecificInstruction instr) {
		throw new OrccRuntimeException("does not know how to interpret a "
				+ "specific instruction");
	}

	@Override
	public void visit(Store instr) {
		Variable variable = instr.getTarget();
		if (instr.getIndexes().isEmpty()) {
			variable.setValue((Expression) instr.getValue().accept(
					exprInterpreter));
		} else {
			try {
				Expression target = variable.getValue();
				Iterator<Expression> it = instr.getIndexes().iterator();
				IntExpr index = (IntExpr) it.next().accept(exprInterpreter);
				while (it.hasNext()) {
					if (target.isListExpr()) {
						target = ((ListExpr) target).get(index);
					}
					index = (IntExpr) it.next().accept(exprInterpreter);
				}

				if (target.isListExpr()) {
					Expression value = (Expression) instr.getValue().accept(
							exprInterpreter);
					((ListExpr) target).set(index, value);
				}
			} catch (IndexOutOfBoundsException e) {
				throw new OrccRuntimeException(
						"Array index out of bounds at line "
								+ instr.getLocation().getStartLine());
			}
		}
	}

	@Override
	public void visit(WhileNode node) {
		int oldBranch = branch;
		branch = 0;
		visit(node.getJoinNode());

		// Interpret first expression ("while" condition)
		Expression condition = (Expression) node.getValue().accept(
				exprInterpreter);

		// while (condition is true) do
		if (condition != null && condition.isBooleanExpr()) {
			branch = 1;
			while (((BoolExpr) condition).getValue()) {
				visit(node.getNodes());
				visit(node.getJoinNode());

				// Interpret next value of "while" condition
				condition = (Expression) node.getValue()
						.accept(exprInterpreter);
				if (condition == null || !condition.isBooleanExpr()) {
					throw new OrccRuntimeException(
							"Condition not boolean at line "
									+ node.getLocation().getStartLine() + "\n");
				}
			}
		} else {
			throw new OrccRuntimeException("Condition not boolean at line "
					+ node.getLocation().getStartLine() + "\n");
		}

		branch = oldBranch;
	}

	private void writeFifo(Expression value, Fifo fifo, int numTokens) {
		ListExpr target = (ListExpr) value;
		if (fifo instanceof Fifo_int) {
			int[] fifoArray = ((Fifo_int) fifo).getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((IntExpr) obj_elem).getIntValue();
			}
			((Fifo_int) fifo).writeEnd(numTokens, fifoArray);
		} else if (fifo instanceof Fifo_boolean) {
			boolean[] fifoArray = ((Fifo_boolean) fifo)
					.getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((BoolExpr) obj_elem).getValue();
			}
			((Fifo_boolean) fifo).writeEnd(numTokens, fifoArray);
		} else if (fifo instanceof Fifo_String) {
			String[] fifoArray = ((Fifo_String) fifo).getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((StringExpr) obj_elem).getValue();
			}
			((Fifo_String) fifo).writeEnd(numTokens, fifoArray);
		}
	}

}
