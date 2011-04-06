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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.VarGlobal;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * 
 * This class defines a transformation that initialize array when it is defined
 * by a procedure
 * 
 * @author Herve Yviquel
 * 
 */
public class ArrayInitializeTransformation extends AbstractActorVisitor {

	private class SpecialActorInterpreter extends ActorInterpreter {

		public SpecialActorInterpreter(Map<String, Expression> parameters,
				Actor actor, OrccProcess process) {
			super(parameters, actor, process);
		}

		@Override
		public void visit(Store instr) {
			Var target = instr.getTarget();
			Type type = target.getType();
			// Allocate value field of list if it is initialized
			if (type.isList() && target.getValue() == null) {
				target.setValue((Expression) type.accept(listAllocator));
			}
			super.visit(instr);
		}
	}

	private ActorInterpreter actorInterpreter;

	@Override
	public void visit(Actor actor) {
		actorInterpreter = new SpecialActorInterpreter(
				new HashMap<String, Expression>(0), actor, null);
		
		// Initialize value field if there is an initial value
		for (Var stateVar : actor.getStateVars()) {
			Expression initConst = ((VarGlobal) stateVar)
					.getInitialValue();
			if (initConst != null) {
				stateVar.setValue(initConst);
			}
		}

		for (Action action : actor.getInitializes()) {
			for (Node node : action.getBody().getNodes()) {
				node.accept(actorInterpreter);
				node.accept(this);
				
			}
		}

		// Copy computed value to initialValue field and clean value field
		for (Var stateVar : actor.getStateVars()) {
			Type type = stateVar.getType();
			VarGlobal s = (VarGlobal) stateVar;
			if (type.isList() && s.getInitialValue() == null
					&& s.getValue() != null) {
				s.setInitialValue(s.getValue());
			}
			stateVar.setValue(null);
		}
	}

	@Override
	public void visit(Call call) {
		// Set initialize to native so it will not be printed
		call.getProcedure().setNative(true);
	}

}
