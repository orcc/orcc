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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * 
 * This class defines a transformation that initialize array when it is defined
 * by a procedure
 * 
 * @author Herve Yviquel
 * 
 */
public class GlobalArrayInitializer extends AbstractActorVisitor<Object> {

	private class SpecialActorInterpreter extends ActorInterpreter {

		public SpecialActorInterpreter(Actor actor,
				Map<String, Expression> parameters) {
			super(actor, parameters);
		}

		@Override
		public Object caseInstStore(InstStore instr) {
			Var target = instr.getTarget().getVariable();
			Type type = target.getType();
			// Allocate value field of list if it is initialized
			if (type.isList() && target.getValue() == null) {
				target.setValue((Expression) listAllocator.doSwitch(type));
			}
			return super.caseInstStore(instr);
		}

	}

	private ActorInterpreter actorInterpreter;

	private boolean initToZero;

	private ListAllocator listAllocator;

	public GlobalArrayInitializer(boolean initToZero) {
		this.initToZero = initToZero;
		listAllocator = new ListAllocator(initToZero);
	}

	@Override
	public Object caseActor(Actor actor) {
		actorInterpreter = new SpecialActorInterpreter(actor,
				new HashMap<String, Expression>(0));

		// Initialize value field if there is an initial value
		for (Var stateVar : actor.getStateVars()) {
			Expression initConst = stateVar.getInitialValue();
			if (initConst != null) {
				stateVar.setValue(IrUtil.copy(initConst));
			}
		}

		for (Action action : actor.getInitializes()) {
			for (Node node : action.getBody().getNodes()) {
				actorInterpreter.doSwitch(node);
				this.doSwitch(node);

			}
		}

		// Copy computed value to initialValue field and clean value field
		for (Var stateVar : actor.getStateVars()) {
			Type type = stateVar.getType();
			if (type.isList() && stateVar.getInitialValue() == null
					&& stateVar.getValue() != null) {
				stateVar.setInitialValue(IrUtil.copy(stateVar.getValue()));
			} else if (stateVar.getInitialValue() == null && initToZero) {
				if (type.isList()) {
					stateVar.setInitialValue(listAllocator.doSwitch(stateVar
							.getType()));
				} else {
					stateVar.setInitialValue(IrFactory.eINSTANCE
							.createExprInt(0));
				}
				initializeExpression(stateVar.getInitialValue());
			}
		}

		return null;
	}

	@Override
	public Object caseInstCall(InstCall call) {
		// Set initialize to native so it will not be printed
		call.getProcedure().setNative(true);
		return null;
	}

	private void initializeExpression(Expression expr) {
		TreeIterator<EObject> it = EcoreUtil.getAllContents(expr, true);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof ExprInt) {
				ExprInt exprInt = (ExprInt) object;
				exprInt.setValue(BigInteger.ZERO);
			} else if (object instanceof Use) {
				ExprBool exprBool = (ExprBool) object;
				exprBool.setValue(false);
			}
		}
	}

}
