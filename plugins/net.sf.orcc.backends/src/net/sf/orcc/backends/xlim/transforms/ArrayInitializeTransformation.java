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
package net.sf.orcc.backends.xlim.transforms;

import net.sf.orcc.OrccException;
import net.sf.orcc.interpreter.ListAllocator;
import net.sf.orcc.interpreter.NodeInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.ir.transformations.PhiRemoval;

/**
 * 
 * This class defines a transformation that initialize array when it is defined
 * by a procedure
 * 
 * @author Herv� Yviquel
 * 
 */
public class ArrayInitializeTransformation extends AbstractActorTransformation {

	private AbstractActorTransformation phiRemover;
	private NodeInterpreter nodeInterpreter;
	private ListAllocator listAllocator;

	public ArrayInitializeTransformation() {
		phiRemover = new PhiRemoval();
		nodeInterpreter = new NodeInterpreter();
		listAllocator = new ListAllocator();
	}

	@Override
	public void transform(Actor actor) throws OrccException {
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
					stateVar.setValue((Expression) type.accept(listAllocator));
				}
			} else {
				// initialize
				stateVar.setValue(initConst);
			}
		}

		for (Action action : actor.getInitializes()) {
			for (CFGNode node : action.getBody().getNodes()) {

				node.accept(this);
				node.accept(nodeInterpreter);
			}
		}
		System.out.println("");
	}

	@Override
	public void visit(Call call) {
		// Remove Phi instruction of initialize function
		phiRemover.visitProcedure(call.getProcedure());
		// Set initialize to external thus it will not be printed
		call.getProcedure().setExternal(true);
	}

}
