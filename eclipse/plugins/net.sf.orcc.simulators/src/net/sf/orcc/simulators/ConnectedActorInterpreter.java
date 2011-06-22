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
package net.sf.orcc.simulators;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_String;
import net.sf.orcc.runtime.Fifo_boolean;
import net.sf.orcc.runtime.Fifo_int;

/**
 * This class defines an actor that can be interpreted by calling
 * {@link #initialize()} and {@link #schedule()}. It consists in an action
 * scheduler, an FSM state, and a node interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ConnectedActorInterpreter extends ActorInterpreter {

	/**
	 * Communication fifos map : key = related I/O port name; value =
	 * Fifo_int/boolean/String
	 */
	private Map<String, Fifo> fifos;
	
	private String id;
	
	public ConnectedActorInterpreter(String id, Actor actor, Map<String, Expression> parameters) {
		super(actor, parameters);
		this.id = id;
	}

	/**
	 * Calls the given native procedure. This method may be overridden if one
	 * wishes not to call native procedures (e.g. abstract interpreter).
	 * 
	 * @param procedure
	 *            a native procedure
	 * @return the result of calling the given procedure
	 */
	@Override
	protected Object callNativeProcedure(Procedure procedure, List<Expression> parameters) {
		int numParams = parameters.size();
		Class<?>[] parameterTypes = new Class<?>[numParams];
		Object[] args = new Object[numParams];
		int i = 0;
		for (Expression parameter : parameters) {
			args[i] = doSwitch(parameter);
			parameterTypes[i] = args[i].getClass();

			i++;
		}

		String methodName = procedure.getName();
		try {
			Class<?> clasz = Class.forName(actor.getName());
			Method method = clasz
					.getMethod(procedure.getName(), parameterTypes);
			Object res = method.invoke(null, args);
			if (res instanceof Boolean) {
				return IrFactory.eINSTANCE.createExprBool((Boolean) res);
			} else if (res instanceof Integer) {
				return IrFactory.eINSTANCE.createExprInt((Integer) res);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new OrccRuntimeException(
					"exception during native procedure call to " + methodName);
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
			for (Port port : outputPattern.getPorts()) {
				Integer nbOfTokens = outputPattern.getNumTokens(port);
				if (!fifos.get(id+"_"+port.getName()).hasRoom(nbOfTokens)) {
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
			Fifo fifo = fifos.get(id+"_"+port.getName());
			peekFifo(inputPattern.getVariable(port).getValue(), fifo, numTokens);
		}

		// Interpret the whole action
		doSwitch(action.getBody());

		for (Port port : inputPattern.getPorts()) {
			int numTokens = inputPattern.getNumTokens(port);
			Fifo fifo = fifos.get(id+"_"+port.getName());
			fifo.readEnd(numTokens);
		}

		for (Port port : outputPattern.getPorts()) {
			int numTokens = outputPattern.getNumTokens(port);
			Fifo fifo = fifos.get(id+"_"+port.getName());
			writeFifo(outputPattern.getVariable(port).getValue(), fifo,
					numTokens);
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
			Fifo fifo = fifos.get(id+"_"+port.getName());
			boolean hasTok = fifo.hasTokens(pattern.getNumTokens(port));
			if (!hasTok) {
				return false;
			}
		}

		// allocates peeked variables
		pattern = action.getPeekPattern();
		for (Port port : pattern.getPorts()) {
			Var peeked = pattern.getVariable(port);
			if (peeked != null) {
				peeked.setValue(tokenAllocator.doSwitch(peeked.getType()));
				int numTokens = pattern.getNumTokens(port);
				Fifo fifo = fifos.get(id+"_"+port.getName());
				peekFifo(peeked.getValue(), fifo, numTokens);
			}
		}

		Object result = doSwitch(action.getScheduler());
		if (result instanceof ExprBool) {
			return ((ExprBool) result).isValue();
		} else {
			return false;
		}
	}

	private void peekFifo(Expression value, Fifo fifo, int numTokens) {
		if (fifo instanceof Fifo_int) {
			ExprList target = (ExprList) value;
			int[] intTarget = new int[target.getSize()];
			System.arraycopy(((Fifo_int) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), intTarget, 0, numTokens);
			for (int i = 0; i < intTarget.length; i++) {
				target.set(i, IrFactory.eINSTANCE.createExprInt(intTarget[i]));
			}
		} else if (fifo instanceof Fifo_boolean) {
			ExprList target = (ExprList) value;
			boolean[] boolTarget = new boolean[target.getSize()];
			System.arraycopy(((Fifo_boolean) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), boolTarget, 0, numTokens);
			for (int i = 0; i < boolTarget.length; i++) {
				target.set(i, IrFactory.eINSTANCE.createExprBool(boolTarget[i]));
			}
		} else if (fifo instanceof Fifo_String) {
			ExprList target = (ExprList) value;
			String[] stringTarget = new String[target.getSize()];
			System.arraycopy(((Fifo_String) fifo).getReadArray(numTokens),
					fifo.getReadIndex(numTokens), stringTarget, 0, numTokens);
			for (int i = 0; i < stringTarget.length; i++) {
				target.set(i,
						IrFactory.eINSTANCE.createExprString(stringTarget[i]));
			}
		}
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

	private void writeFifo(Expression value, Fifo fifo, int numTokens) {
		ExprList target = (ExprList) value;
		if (fifo instanceof Fifo_int) {
			int[] fifoArray = ((Fifo_int) fifo).getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((ExprInt) obj_elem).getIntValue();
			}
			((Fifo_int) fifo).writeEnd(numTokens, fifoArray);
		} else if (fifo instanceof Fifo_boolean) {
			boolean[] fifoArray = ((Fifo_boolean) fifo)
					.getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((ExprBool) obj_elem).isValue();
			}
			((Fifo_boolean) fifo).writeEnd(numTokens, fifoArray);
		} else if (fifo instanceof Fifo_String) {
			String[] fifoArray = ((Fifo_String) fifo).getWriteArray(numTokens);
			int index = fifo.getWriteIndex(numTokens);
			for (Expression obj_elem : target.getValue()) {
				fifoArray[index++] = ((ExprString) obj_elem).getValue();
			}
			((Fifo_String) fifo).writeEnd(numTokens, fifoArray);
		}
	}

}
