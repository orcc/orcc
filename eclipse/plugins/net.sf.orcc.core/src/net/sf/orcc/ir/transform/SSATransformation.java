/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.ir.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class converts the given actor to SSA form.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SSATransformation extends AbstractIrVisitor<Void> {

	/*
	 * FIXME : if this transformation is applied more than one time on an actor,
	 * it may causes a NullPointerException. Check commitPhi method, the
	 * oldVar.getName() call and the value of oldVar, set to null by
	 * phi.setOldVariable(null) call.
	 */

	/**
	 * ith branch (or 0 if we are not in a branch)
	 */
	private int branch;

	/**
	 * maps a variable name to a local variable (used when creating new
	 * definitions)
	 */
	private Map<String, Var> definitions;

	/**
	 * join node (if any)
	 */
	private BlockBasic join;

	/**
	 * contains the current while node being treated (if any)
	 */
	private BlockWhile loop;

	/**
	 * maps a variable name to a local variable (used when replacing uses)
	 */
	private Map<String, Var> uses;

	/**
	 * Creates a new SSA transformation.
	 */
	public SSATransformation() {
		definitions = new HashMap<String, Var>();
		uses = new HashMap<String, Var>();
	}

	/**
	 * Commits the phi assignments in the given join node.
	 * 
	 * @param innerJoin
	 *            a NodeBlock that contains phi assignments
	 */
	private void commitPhi(BlockBasic innerJoin) {
		for (Instruction instruction : innerJoin.getInstructions()) {
			InstPhi phi = (InstPhi) instruction;
			Var oldVar = phi.getOldVariable();
			Var newVar = phi.getTarget().getVariable();

			// updates the current value of "var"
			uses.put(oldVar.getName(), newVar);

			if (join != null) {
				insertPhi(oldVar, newVar);
			}

			// remove reference to old variable in phi
			phi.setOldVariable(null);
		}
	}

	/**
	 * Inserts a phi in the (current) join node.
	 * 
	 * @param oldVar
	 *            old variable
	 * @param newVar
	 *            new variable
	 */
	private void insertPhi(Var oldVar, Var newVar) {
		String name = oldVar.getName();
		InstPhi phi = null;
		for (Instruction instruction : join.getInstructions()) {
			if (instruction.isInstPhi()) {
				InstPhi tempPhi = (InstPhi) instruction;
				if (tempPhi.getTarget().getVariable().getName().equals(name)) {
					phi = tempPhi;
					break;
				}
			}
		}

		if (phi == null) {
			Var target = newDefinition(oldVar);
			List<Expression> values = new ArrayList<Expression>(2);
			values.add(IrFactory.eINSTANCE.createExprVar(oldVar));
			values.add(IrFactory.eINSTANCE.createExprVar(oldVar));

			phi = IrFactory.eINSTANCE.createInstPhi(target, values);
			phi.setOldVariable(oldVar);
			join.add(phi);

			if (loop != null) {
				List<Use> uses = new ArrayList<Use>(oldVar.getUses());
				for (Use use : uses) {
					Block node = EcoreHelper.getContainerOfType(use,
							Block.class);

					// only changes uses that are in the loop
					if (node != join && EcoreUtil.isAncestor(loop, node)) {
						use.setVariable(target);
					}
				}
			}
		}

		// replace use
		IrUtil.removeUses(phi.getValues().get(branch - 1));
		phi.getValues().set(branch - 1,
				IrFactory.eINSTANCE.createExprVar(newVar));
	}

	/**
	 * Creates a new definition based on the given old variable.
	 * 
	 * @param oldVar
	 *            a variable
	 * @return a new definition based on the given old variable
	 */
	private Var newDefinition(Var oldVar) {
		String name = oldVar.getName();

		// get index
		int index;
		if (definitions.containsKey(name)) {
			index = definitions.get(name).getIndex() + 1;
		} else {
			index = 1;
		}

		// create new variable
		Var newVar = IrFactory.eINSTANCE.createVar(oldVar.getLineNumber(),
				oldVar.getType(), name, oldVar.isAssignable(), index);
		procedure.getLocals().add(newVar);
		definitions.put(name, newVar);

		return newVar;
	}

	/**
	 * Replaces the definition created.
	 * 
	 * @param definition
	 *            a definition
	 */
	private void replaceDef(Def definition) {
		if (definition != null) {
			Var target = definition.getVariable();
			String name = target.getName();

			// v_old is the value of the variable before the assignment
			Var oldVar = uses.get(name);
			if (oldVar == null) {
				// may be null if the variable is used without having been
				// assigned first
				// happens with function parameters for instance
				oldVar = target;
			}

			Var newTarget = newDefinition(target);
			uses.put(name, newTarget);

			if (branch != 0) {
				insertPhi(oldVar, newTarget);
			}

			definition.setVariable(newTarget);
		}
	}

	/**
	 * Replaces uses in the given object.
	 * 
	 * @param object
	 *            an object
	 */
	private void replaceUses(EObject eObject) {
		for (Use use : EcoreHelper.getObjects(eObject, Use.class)) {
			Var oldVar = use.getVariable();
			if (oldVar.isLocal()) {
				Var newVar = uses.get(oldVar.getName());
				if (newVar != null) {
					// newVar may be null if oldVar is a function parameter
					// for instance
					use.setVariable(newVar);
				}
			}
		}
	}

	/**
	 * Replaces uses of oldVar by newVar in the given objects.
	 * 
	 * @param objects
	 *            a list of objects
	 */
	private void replaceUses(List<? extends EObject> eObjects) {
		for (EObject eObject : eObjects) {
			replaceUses(eObject);
		}
	}

	/**
	 * Restore variables that were concerned by phi assignments.
	 */
	private void restoreVariables() {
		for (Instruction instruction : join.getInstructions()) {
			InstPhi phi = (InstPhi) instruction;
			Var oldVar = phi.getOldVariable();
			uses.put(oldVar.getName(), oldVar);
		}
	}

	@Override
	public Void caseInstAssign(InstAssign assign) {
		replaceUses(assign.getValue());
		replaceDef(assign.getTarget());
		return null;
	}

	@Override
	public Void caseInstCall(InstCall call) {
		replaceUses(call.getParameters());
		replaceDef(call.getTarget());
		return null;
	}

	@Override
	public Void caseBlockIf(BlockIf nodeIf) {
		int outerBranch = branch;
		BlockBasic outerJoin = join;
		BlockWhile outerLoop = loop;

		replaceUses(nodeIf.getCondition());

		join = nodeIf.getJoinBlock();
		loop = null;

		branch = 1;
		doSwitch(nodeIf.getThenBlocks());

		// restore variables used in phi assignments
		restoreVariables();

		branch = 2;
		doSwitch(nodeIf.getElseBlocks());

		// commit phi
		BlockBasic innerJoin = join;
		branch = outerBranch;
		join = outerJoin;
		loop = outerLoop;
		commitPhi(innerJoin);
		return null;
	}

	@Override
	public Void caseInstLoad(InstLoad load) {
		replaceUses(load.getIndexes());
		replaceDef(load.getTarget());
		return null;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		definitions.clear();
		uses.clear();
		branch = 0;
		return super.caseProcedure(procedure);
	}

	@Override
	public Void caseInstReturn(InstReturn returnInstr) {
		Expression value = returnInstr.getValue();
		if (value != null) {
			replaceUses(value);
		}
		return null;
	}

	@Override
	public Void caseInstStore(InstStore store) {
		replaceUses(store.getIndexes());
		replaceUses(store.getValue());
		return null;
	}

	@Override
	public Void caseBlockWhile(BlockWhile nodeWhile) {
		int outerBranch = branch;
		BlockBasic outerJoin = join;
		BlockWhile outerLoop = loop;

		replaceUses(nodeWhile.getCondition());

		branch = 2;
		join = nodeWhile.getJoinBlock();
		loop = nodeWhile;

		doSwitch(nodeWhile.getBlocks());

		// commit phi
		BlockBasic innerJoin = join;
		branch = outerBranch;
		join = outerJoin;
		loop = outerLoop;
		commitPhi(innerJoin);
		return null;
	}

}
