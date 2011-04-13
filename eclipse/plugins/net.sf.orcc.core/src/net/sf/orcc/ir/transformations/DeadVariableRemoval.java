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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * This class defines a very simple Dead Var Elimination.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DeadVariableRemoval extends AbstractActorVisitor {

	protected boolean changed;

	private List<Instruction> instructionsToVisit;

	private List<Var> unusedLocals;

	private void handleInstruction(Var target, Instruction instruction) {
		// do not remove assign to variables that are used by writes
		if (isPort(target)) {
			return;
		}

		List<Use> uses = EcoreHelper.getUses(instruction);
		for (Use use : uses) {
			for (Def def : use.getVariable().getDefs()) {
				Instruction instDef = EcoreHelper.getContainerOfType(def,
						Instruction.class);
				if (instDef != null) {
					instructionsToVisit.add(instDef);
				}
			}
		}

		// remove instruction
		EcoreHelper.delete(instruction);

		// adds target to list of to-be-removed variables
		unusedLocals.add(target);
		changed = true;
	}

	@Override
	public void visit(InstAssign assign) {
		Var target = assign.getTarget().getVariable();
		if (!target.isUsed()) {
			handleInstruction(target, assign);
		}
	}

	@Override
	public void visit(InstCall call) {
		if (call.hasResult()) {
			Var target = call.getTarget().getVariable();
			if (target != null && !target.isUsed()) {
				handleInstruction(target, call);
			}
		}
	}

	@Override
	public void visit(InstLoad load) {
		Var target = load.getTarget().getVariable();
		if (target != null && !target.isUsed()) {
			handleInstruction(target, load);
		}
	}

	@Override
	public void visit(InstPhi phi) {
		Var target = phi.getTarget().getVariable();
		if (target != null && !target.isUsed()) {
			handleInstruction(target, phi);
		}
	}

	@Override
	public void visit(InstStore store) {
		Var target = store.getTarget().getVariable();
		if (target != null && !target.isUsed()) {
			// do not remove stores to variables that are used by writes, or
			// variables that are parameters
			if (!target.isGlobal()
					&& (isPort(target) || target.eContainmentFeature() == IrPackage.eINSTANCE
							.getProcedure_Parameters())) {
				return;
			}

			handleInstruction(target, store);
		}
	}

	@Override
	public void visit(NodeBlock block) {
		// adds all instructions to the list
		instructionsToVisit.addAll(block.getInstructions());
	}

	@Override
	public void visit(Procedure procedure) {
		unusedLocals = new ArrayList<Var>();
		instructionsToVisit = new ArrayList<Instruction>();

		// first shot: removes locals not used by any instruction
		List<Var> locals = procedure.getLocals();
		for (Var local : locals) {
			if (!local.isUsed() && !local.isDefined()) {
				unusedLocals.add(local);
			}
		}

		// step 1: adds all instructions to the list
		super.visit(procedure);

		// step 2: keep visiting instructions until there are none left
		while (!instructionsToVisit.isEmpty()) {
			Instruction instruction = instructionsToVisit.remove(0);
			instruction.accept(this);
		}

		// procedure.removeLocals(unusedLocals);
		for (Var var : unusedLocals) {
			procedure.getLocals().remove(var);
		}
	}

}
