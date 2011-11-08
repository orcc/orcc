/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.xlim.transformations;

import java.util.Collections;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.ActorInterpreter;
import net.sf.orcc.ir.util.ValueUtil;

/**
 * 
 * This class defines a transformation that initialize array when it is defined
 * by a procedure
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * 
 */
public class GlobalArrayInitializer extends AbstractActorVisitor<Object> {

	private class XlimActorInitializer extends ActorInterpreter {
		private boolean initToZero;

		public XlimActorInitializer(Actor actor, boolean initToZero) {
			super(actor, Collections.<Var, Expression> emptyMap());
			this.initToZero = initToZero;
		}

		public void initialize() {
			try {
				// initializes state variables
				for (Var stateVar : actor.getStateVars()) {
					initializeVar(stateVar);
				}

				// Get initializing procedure if any
				for (Action action : actor.getInitializes()) {
					if (isSchedulable(action)) {
						execute(action);
						continue;
					}
				}
			} catch (OrccRuntimeException ex) {
				throw new OrccRuntimeException(
						"Runtime exception thrown by actor " + actor.getName(),
						ex);
			}
		}

		protected void initializeVar(Var variable) {
			Type type = variable.getType();
			Expression initConst = variable.getInitialValue();
			if (initConst == null) {
				if (initToZero) {
					if (type.isList()) {
						variable.setValue(ValueUtil
								.createArray((TypeList) type));
					} else if (type.isBool()) {
						variable.setValue(false);
					} else if (type.isInt()) {
						variable.setValue(0);
					}
				}
			} else {
				// evaluate initial constant value
				if (type.isList()) {
					exprInterpreter.setType((TypeList) type);
				}
				variable.setValue(exprInterpreter.doSwitch(initConst));
			}
		}
	}

	private boolean initToZero;

	public GlobalArrayInitializer(boolean initToZero) {
		this.initToZero = initToZero;
	}

	@Override
	public Object caseActor(Actor actor) {
		new XlimActorInitializer(actor, initToZero).initialize();
		return null;
	}

}
