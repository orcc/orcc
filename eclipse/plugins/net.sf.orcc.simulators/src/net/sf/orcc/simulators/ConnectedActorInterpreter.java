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

import net.sf.dftools.util.util.EcoreHelper;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ActorInterpreter;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.runtime.SimulatorFifo;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.WriteListener;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an actor that can be interpreted by calling
 * {@link #initialize()} and {@link #schedule()}. It consists in an action
 * scheduler, an FSM state, and a node interpreter.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ConnectedActorInterpreter extends ActorInterpreter {

	private WriteListener listener;

	public ConnectedActorInterpreter(Actor actor, List<Argument> arguments,
			WriteListener listener) {
		super(actor, arguments);
		this.listener = listener;
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
	protected Object callNativeProcedure(Procedure procedure,
			List<Arg> arguments) {
		int numParams = arguments.size();
		Class<?>[] parameterTypes = new Class<?>[numParams];
		Object[] args = new Object[numParams];
		int i = 0;
		for (Arg arg : arguments) {
			if (arg.isByVal()) {
				Expression expr = ((ArgByVal) arg).getValue();
				args[i] = exprInterpreter.doSwitch(expr);
				parameterTypes[i] = args[i].getClass();
			}

			i++;
		}

		String methodName = procedure.getName();

		try {
			// get packageName and containerName for calling the correct native
			// function
			EObject entity = procedure.eContainer();
			String name = EcoreHelper.getFeature(entity, "name");
			int index = name.lastIndexOf('.');
			if (index != -1) {
				name = name.substring(0, index) + ".impl"
						+ name.substring(index);
			}

			Class<?> clasz = Class.forName(name);
			Method method = clasz
					.getMethod(procedure.getName(), parameterTypes);
			return method.invoke(null, args);
		} catch (Exception e) {
			throw new OrccRuntimeException(
					"exception during native procedure call to " + methodName,
					e);
		}
	}

	@Override
	protected void callPrintProcedure(Procedure procedure, List<Arg> arguments) {
		for (Arg arg : arguments) {
			if (arg.isByVal()) {
				Expression expr = ((ArgByVal) arg).getValue();
				if (expr.isExprString()) {
					// String characters rework for escaped control
					// management
					String str = ((ExprString) expr).getValue();
					String unescaped = OrccUtil.getUnescapedString(str);
					listener.writeText(unescaped);
				} else {
					Object value = exprInterpreter.doSwitch(expr);
					listener.writeText(String.valueOf(value));
				}
			}
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
		boolean hasRooms = true;
		if (outputPattern != null) {
			for (Port port : outputPattern.getPorts()) {
				Integer nbOfTokens = outputPattern.getNumTokens(port);
				@SuppressWarnings("unchecked")
				List<SimulatorFifo> fifos = (List<SimulatorFifo>) port
						.getAttribute(0).getPojoValue();
				for (SimulatorFifo fifo : fifos) {
					hasRooms &= fifo.hasRoom(nbOfTokens);
				}
			}
		}
		return hasRooms;
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
			SimulatorFifo fifo = (SimulatorFifo) port.getAttribute(0)
					.getPojoValue();

			Var variable = inputPattern.getVariable(port);
			Type type = ((TypeList) variable.getType()).getInnermostType();
			Object array = variable.getValue();
			for (int i = 0; i < numTokens; i++) {
				Object value = fifo.read();
				ValueUtil.set(type, array, value, i);
			}
		}

		// Interpret the whole action
		doSwitch(action.getBody());

		for (Port port : outputPattern.getPorts()) {
			int numTokens = outputPattern.getNumTokens(port);
			@SuppressWarnings("unchecked")
			List<SimulatorFifo> fifos = (List<SimulatorFifo>) port
					.getAttribute(0).getPojoValue();

			Var variable = outputPattern.getVariable(port);
			Type type = ((TypeList) variable.getType()).getInnermostType();
			Object array = variable.getValue();
			for (int i = 0; i < numTokens; i++) {
				Object value = ValueUtil.get(type, array, i);
				for (SimulatorFifo fifo : fifos) {
					fifo.write(value);
				}
			}
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
			SimulatorFifo fifo = (SimulatorFifo) port.getAttribute(0)
					.getPojoValue();
			boolean hasTok = fifo.hasTokens(pattern.getNumTokens(port));
			if (!hasTok) {
				return false;
			}
		}

		// allocates peeked variables
		pattern = action.getPeekPattern();
		for (Port port : pattern.getPorts()) {
			int numTokens = pattern.getNumTokens(port);
			SimulatorFifo fifo = (SimulatorFifo) port.getAttribute(0)
					.getPojoValue();

			Var peeked = pattern.getVariable(port);
			if (peeked != null) {
				peeked.setValue(ValueUtil.createArray((TypeList) peeked
						.getType()));
			}

			Type type = ((TypeList) peeked.getType()).getInnermostType();
			Object array = peeked.getValue();
			for (int i = 0; i < numTokens; i++) {
				Object value = fifo.peek(i);
				ValueUtil.set(type, array, value, i);
			}
		}

		Object result = doSwitch(action.getScheduler());
		return ValueUtil.isTrue(result);
	}

	@Override
	public String toString() {
		return "interpreter for actor " + actor.getName();
	}

}
