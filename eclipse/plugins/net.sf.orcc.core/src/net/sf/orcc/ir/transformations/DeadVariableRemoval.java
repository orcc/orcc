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
package net.sf.orcc.ir.transformations;

import java.util.Iterator;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a very simple Dead Var Elimination.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DeadVariableRemoval extends AbstractActorVisitor {

	protected boolean changed;

	@Override
	public void visit(InstAssign assign) {
		Var variable = assign.getTarget();
		if (!variable.isUsed()) {
			// do not remove assign to variables that are used by writes
			if (isPort(variable)) {
				return;
			}

			// clean up uses
			assign.setTarget(null);
			assign.setValue(null);

			// remove instruction
			itInstruction.remove();

			procedure.getLocals().remove(variable.getName());
			changed = true;
		}
	}

	@Override
	public void visit(InstCall call) {
		if (call.hasResult()) {
			Var variable = call.getTarget();
			if (!variable.isUsed()) {
				// do not remove call to variables that are used by writes
				if (isPort(variable)) {
					return;
				}

				// clean up target
				call.setTarget(null);

				// remove instruction
				itInstruction.remove();

				// remove result
				procedure.getLocals().remove(variable.getName());
				changed = true;
			}
		}
	}

	@Override
	public void visit(InstLoad load) {
		Var target = load.getTarget();
		if (!target.isUsed()) {
			// do not remove loads to variables that are used by writes
			if (isPort(target)) {
				return;
			}

			// remove instruction
			itInstruction.remove();

			procedure.getLocals().remove(target.getName());
			changed = true;
		}
	}

	@Override
	public void visit(InstPhi phi) {
		Var variable = phi.getTarget();
		if (!variable.isUsed()) {
			// do not remove phi to variables that are used by writes
			if (isPort(variable)) {
				return;
			}

			// remove instruction
			itInstruction.remove();

			procedure.getLocals().remove(variable.getName());
			changed = true;
		}
	}

	@Override
	public void visit(Procedure procedure) {
		changed = true;

		while (changed) {
			changed = false;

			// first shot: removes locals not used by any instruction
			OrderedMap<String, Var> locals = procedure.getLocals();
			Iterator<Var> it = locals.iterator();
			while (it.hasNext()) {
				Var local = it.next();
				if (!local.isUsed() && local.getInstruction() == null
						&& local.getInstructions() == null) {
					changed = true;
					it.remove();
				}
			}

			super.visit(procedure);
		}
	}

	@Override
	public void visit(InstStore store) {
		Var target = store.getTarget();
		if (!target.isUsed()) {
			// do not remove stores to variables that are used by writes, or
			// variables that are parameters
			if (!target.isGlobal()
					&& (isPort(target) || procedure.getParameters().contains(
							target.getName()))) {
				return;
			}

			// remove instruction
			itInstruction.remove();

			procedure.getLocals().remove(target.getName());
			changed = true;
		}
	}

}
