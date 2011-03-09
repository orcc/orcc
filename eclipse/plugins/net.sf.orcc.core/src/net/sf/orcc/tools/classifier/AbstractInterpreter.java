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
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;

/**
 * This class defines an abstract interpreter of an actor. It refines the
 * concrete interpreter by not relying on anything that is data-dependent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AbstractInterpreter extends ActorInterpreter {

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
		super(new HashMap<String, Expression>(), actor, null);

		nodeInterpreter = new AbstractNodeInterpreter();
		exprInterpreter = new AbstractExpressionEvaluator();
	}

	@Override
	protected boolean checkOutputPattern(Pattern outputPattern) {
		return true;
	}

	@Override
	public void execute(Action action) {
		scheduledAction = action;
		((AbstractNodeInterpreter) nodeInterpreter).setSchedulableMode(false);
		super.execute(action);
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
		// unlike parent, do not check the number of tokens present on FIFOs

		// allocates peeked variables
		Pattern pattern = action.getInputPattern();
		for (Port port : pattern.getPorts()) {
			Variable peeked = pattern.getPeeked(port);
			if (peeked != null) {
				peeked.setValue((Expression) peeked.getType().accept(
						listAllocator));
			}
		}

		// check isSchedulable procedure
		((AbstractNodeInterpreter) nodeInterpreter).setSchedulableMode(true);
		Expression result = interpretProc(action.getScheduler());
		if (result == null) {
			throw new OrccRuntimeException("could not determine if action "
					+ action.toString() + " is schedulable");
		}
		return result.isBooleanExpr() && ((BoolExpr) result).getValue();
	}

	/**
	 * Sets the configuration that should be used by the interpreter.
	 * 
	 * @param configuration
	 *            a configuration as a map of ports and values
	 */
	public void setConfiguration(Map<Port, Expression> configuration) {
		((AbstractNodeInterpreter) nodeInterpreter)
				.setConfiguration(configuration);
	}

}
