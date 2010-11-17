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
import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a very simple Dead Variable Elimination.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DeadVariableRemoval extends AbstractActorTransformation {

	private boolean changed;

	@Override
	public void visit(Assign assign) {
		LocalVariable variable = assign.getTarget();
		if (!variable.isUsed()) {
			// clean up uses
			assign.setTarget(null);
			assign.setValue(null);

			// remove instruction
			instructionIterator.remove();

			procedure.getLocals().remove(variable.getName());
			changed = true;
		}
	}

	@Override
	public void visit(Call call) {
		if (call.hasResult()) {
			LocalVariable variable = call.getTarget();
			if (!variable.isUsed()) {
				// clean up target
				call.setTarget(null);

				// do not remove call instruction because it may have
				// side-effects
				// maybe something worth checking later

				// remove result
				procedure.getLocals().remove(variable.getName());
				changed = true;
			}
		}
	}

	@Override
	public void visit(Load load) {
		LocalVariable target = load.getTarget();
		if (!target.isUsed()) {
			// clean up uses
			load.setTarget(null);
			load.setSource(null);
			Use.removeUses(load, load.getIndexes());

			// remove instruction
			instructionIterator.remove();

			procedure.getLocals().remove(target.getName());
			changed = true;
		}
	}

	@Override
	public void visit(Peek peek) {
		Variable variable = peek.getTarget();
		if (!variable.isUsed()) {
			// clean up uses
			peek.setTarget(null);
			peek.setPort(null);

			// remove instruction
			instructionIterator.remove();

			procedure.getLocals().remove(variable.getName());
			changed = true;
		}
	}

	@Override
	public void visit(PhiAssignment phi) {
		Variable variable = phi.getTarget();
		if (!variable.isUsed()) {
			// clean up uses
			phi.setTarget(null);
			List<Expression> values = phi.getValues();

			for (Expression value : values) {
				VarExpr varExpr = (VarExpr) value;
				varExpr.getVar().remove();
			}

			// remove instruction
			instructionIterator.remove();

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
			OrderedMap<String, Variable> locals = procedure.getLocals();
			Iterator<Variable> it = locals.iterator();
			while (it.hasNext()) {
				LocalVariable local = (LocalVariable) it.next();
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
	public void visit(Read read) {
		Variable variable = read.getTarget();
		if (variable != null && !variable.isUsed()) {
			// clean up target
			read.setTarget(null);

			// do not remove read instruction because it has side-effects!

			// remove target
			procedure.getLocals().remove(variable.getName());
			changed = true;
		}
	}

	@Override
	public void visit(Store store) {
		Variable target = store.getTarget();
		if (!target.isUsed()) {
			// do not remove stores to variables that are used by writes, or
			// variables that are parameters
			if (target.isPort()
					|| procedure.getParameters().contains(target.getName())) {
				return;
			}

			// clean up uses
			store.setTarget(null);
			Use.removeUses(store, store.getIndexes());
			store.setValue(null);

			// remove instruction
			instructionIterator.remove();

			procedure.getLocals().remove(target.getName());
			changed = true;
		}
	}

}
