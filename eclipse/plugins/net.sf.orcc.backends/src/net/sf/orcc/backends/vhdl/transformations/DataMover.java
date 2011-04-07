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

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class defines methods to move data from one procedure to another by
 * updating the data dependencies of the given procedure.
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

	//private NodeBlock targetBlock;

	//private Map<Var, Var> variableMap;

	/**
	 * Creates a new code mover
	 */
	public DataMover(Actor actor) {
		// visit expressions too
		super(true);

		this.actor = actor;
		//variableMap = new HashMap<Var, Var>();
	}
//
//	/**
//	 * This method adds spill code so that the given variable is stored after it
//	 * has been defined by the given instruction in another procedure and it is
//	 * loaded before it is used in this procedure by the given expression.
//	 * 
//	 * @param variable
//	 * @param expr
//	 * @param instruction
//	 */
//	private void addSpillCode(Var variable, ExprVar expr) {
//		if (!procedure.getLocals().contains(variable.getName())) {
//			Var stateVar = variableMap.get(variable);
//			if (stateVar == null) {
//				stateVar = getOrAddGlobal(variable);
//				addStoreToGlobal(variable, stateVar);
//			}
//
//			// duplicates the local variable
//			Var duplicate = new VarLocal(variable.isAssignable(),
//					variable.getIndex(), variable.getLocation(),
//					variable.getBaseName(), variable.getType());
//
//			// add it to the locals
//			List<Var> variables = procedure.getLocals();
//			variables.put(duplicate.getName(), duplicate);
//
//			// updates the expression
//			expr.setVar(new Use(duplicate));
//
//			// adds a Load
//			Load duplicateLoad = new Load(duplicate, new Use(stateVar));
//			itInstruction.previous();
//			itInstruction.add(duplicateLoad);
//		}
//	}
//
//	/**
//	 * Adds a Store of the given local variable to the given global variable.
//	 * 
//	 * @param variable
//	 *            a local variable
//	 * @param stateVar
//	 *            a global variable
//	 */
//	private void addStoreToGlobal(VarLocal variable, VarGlobal stateVar) {
//		Store store = new Store(stateVar, new VarExpr(new Use(variable)));
//
//		Instruction instruction = variable.getInstruction();
//		NodeBlock block = instruction.getBlock();
//		int index = block.indexOf(instruction);
//		block.add(index + 1, store);
//		variableMap.put(variable, stateVar);
//	}
//
//	/**
//	 * Finds the global variable that matches the given local variable, and if
//	 * it does not exist, creates it.
//	 * 
//	 * @param variable
//	 *            a local variable
//	 * @return the corresponding global variable
//	 */
//	private VarGlobal getOrAddGlobal(VarLocal variable) {
//		VarGlobal stateVar = actor.getStateVars().get(
//				"g_" + variable.getBaseName());
//		if (stateVar == null) {
//			stateVar = new VarGlobal(variable.getLocation(),
//					variable.getType(), "g_" + variable.getBaseName(), true);
//
//			actor.getStateVars().put(stateVar.getName(), stateVar);
//		}
//
//		return stateVar;
//	}
//
//	@Override
//	public void visit(NodeBlock node) {
//		this.targetBlock = node;
//		super.visit(node);
//	}
//
//	@Override
//	public void visit(ExprVar expr, Object... args) {
//		Var variable = expr.getVar().getVariable();
//		if (variable.isDefined()) {
//			InstLoad load = EcoreHelper.getContainerOfType(variable.getDefs()
//					.get(0), InstLoad.class);
//			if (load != null && load.getIndexes().isEmpty()) {
//				// registers loaded variables into the variableMap
//				variableMap.put(variable, load.getSource().getVariable());
//			} else {
//				
//			}
//			if (instruction instanceof RamRead) {
//				if (targetBlock == instruction.getBlock()) {
//					return;
//				}
//			}
//
//			if (instruction instanceof LocalTargetContainer) {
//				addSpillCode(variable, expr);
//			}
//		}
//	}

}
