/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalTargetContainer;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines methods to move data from one procedure to another by
 * Updates the data dependencies of the given procedure.
 * 
 * <p>
 * This method will import any variable used by expressions in the procedure, by
 * adding a global variable, a Store at the definition and a Load before the
 * use.
 * </p>
 * 
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DataMover extends AbstractActorVisitor {

	private BlockNode targetBlock;

	private Map<String, GlobalVariable> variableMap;

	/**
	 * Creates a new code mover
	 */
	public DataMover(Actor actor) {
		// visit expressions too
		super(true);

		this.actor = actor;
		variableMap = new HashMap<String, GlobalVariable>();
	}

	private void storeLoadLocalVariable(LocalVariable target, VarExpr expr,
			Instruction instruction) {
		GlobalVariable stateVar = variableMap.get(target.getName());
		if (stateVar == null) {
			stateVar = new GlobalVariable(target.getLocation(),
					target.getType(), "g_" + target.getBaseName(), true);

			actor.getStateVars().put(stateVar.getName(), stateVar);

			variableMap.put(target.getName(), stateVar);
		}

		if (!procedure.getLocals().contains(target.getName())) {
			LocalVariable duplicate = new LocalVariable(target.isAssignable(),
					target.getIndex(), target.getLocation(),
					target.getBaseName(), target.getType());

			OrderedMap<String, LocalVariable> variables = procedure.getLocals();
			variables.put(duplicate.getName(), duplicate);
			expr.setVar(new Use(duplicate));

			Load duplicateLoad = new Load(duplicate, new Use(stateVar));
			duplicateLoad.setBlock(targetBlock);
			itInstruction.previous();
			itInstruction.add(duplicateLoad);
			itInstruction.next();
		}
	}

	private void updateExpr(LocalVariable variable, VarExpr expr, Load load) {
		// no need to add another state variable, will just add the load
		// to the target block

		LocalVariable duplicate = new LocalVariable(variable.isAssignable(),
				variable.getIndex(), variable.getLocation(),
				variable.getBaseName(), variable.getType());

		OrderedMap<String, LocalVariable> variables = procedure.getLocals();
		variables.put(duplicate.getName(), duplicate);
		expr.setVar(new Use(duplicate));

		Load duplicateLoad = new Load(duplicate, new Use(load.getSource()
				.getVariable()));
		duplicateLoad.setBlock(targetBlock);
		itInstruction.previous();
		itInstruction.add(duplicateLoad);
		itInstruction.next();
	}

	@Override
	public void visit(BlockNode node) {
		this.targetBlock = node;
		super.visit(node);
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
		LocalVariable variable = (LocalVariable) expr.getVar().getVariable();
		Instruction instruction = variable.getInstruction();
		if (instruction != null) {
			if (instruction.isLoad()) {
				Load load = (Load) variable.getInstruction();
				if (load.getIndexes().isEmpty()
						&& !procedure.getLocals().contains(variable.getName())) {
					// only update for load of state scalars not already
					// treated
					updateExpr(variable, expr, load);
					return;
				}
			}

			if (instruction instanceof LocalTargetContainer) {
				LocalVariable target = ((LocalTargetContainer) instruction)
						.getTarget();
				storeLoadLocalVariable(target, expr, instruction);
			}
		}
	}

}
