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
package net.sf.orcc.simulators.slow;

import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ActorInterpreter;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.simulators.util.RuntimeExpressionEvaluator;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.util.EcoreHelper;

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

	/**
	 * Creates an actor interpreter dedicated to the simulator.
	 * 
	 * @param actor
	 *            the actor to interpret 
	 */
	public ConnectedActorInterpreter(Actor actor) {
		super(actor);
		exprInterpreter = new RuntimeExpressionEvaluator();
	}

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

		// get packageName and containerName for calling the correct native
		// function
		EObject entity = procedure.eContainer();
		String name = EcoreHelper.getFeature(entity, "name");
		int index = name.lastIndexOf('.');
		if (index != -1) {
			name = "net.sf.orcc.simulators.runtime." + name.substring(0, index)
					+ ".impl" + name.substring(index);
		}
		try {
			Class<?> clasz = Class.forName(name);
			return clasz.getMethod(procedure.getName(), parameterTypes).invoke(
					null, args);
		} catch (Exception e) {
			throw new OrccRuntimeException(
					"Native procedure call Exception for "
							+ procedure.getName(), e);
		}
	}

	@Override
	protected boolean checkOutputPattern(Pattern outputPattern) {
		boolean hasRooms = true;
		if (outputPattern != null) {
			for (Port port : outputPattern.getPorts()) {
				// check only connected output ports
				Attribute attr = port.getAttribute("fifo");
				if (attr != null) {
					@SuppressWarnings("unchecked")
					List<SimulatorFifo> fifos = (List<SimulatorFifo>) attr
							.getObjectValue();
					Integer nbOfTokens = outputPattern.getNumTokens(port);
					for (SimulatorFifo fifo : fifos) {
						hasRooms &= fifo.hasRoom(nbOfTokens);
					}
				}

			}
		}
		return hasRooms;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void execute(Action action) {
		// allocate patterns
		Pattern inputPattern = action.getInputPattern();
		Pattern outputPattern = action.getOutputPattern();
		allocatePattern(inputPattern);
		allocatePattern(outputPattern);

		for (Port port : inputPattern.getPorts()) {
			int numTokens = inputPattern.getNumTokens(port);
			// a schedulable action can't have unconnected ports
			// (@see #isSchedulable())
			SimulatorFifo fifo = (SimulatorFifo) port.getAttribute("fifo")
					.getObjectValue();
			Var variable = inputPattern.getPortToVarMap().get(port);
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
			// write tokens only on connected output ports
			Attribute attr = port.getAttribute("fifo");
			if (attr != null) {
				List<SimulatorFifo> fifos = (List<SimulatorFifo>) attr
						.getObjectValue();
				int numTokens = outputPattern.getNumTokens(port);
				Var variable = outputPattern.getPortToVarMap().get(port);
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
	}

	@Override
	protected boolean isSchedulable(Action action) {
		Pattern pattern = action.getInputPattern();
		// check tokens
		for (Port port : pattern.getPorts()) {
			Attribute att = port.getAttribute("fifo");
			if (att == null) {
				// this port is not connected, consequently this action cannot
				// be scheduled
				return false;
			}
			SimulatorFifo fifo = (SimulatorFifo) att.getObjectValue();
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
					.getObjectValue();

			Var peeked = pattern.getPortToVarMap().get(port);
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
