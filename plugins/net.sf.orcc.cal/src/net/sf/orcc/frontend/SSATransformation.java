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
package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class converts the given actor to SSA form.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SSATransformation extends AbstractActorTransformation {

	private int branch;

	private Map<String, Integer> definitions;

	private BlockNode join;

	private Map<String, Integer> uses;

	/**
	 * Creates a new SSA transformation.
	 */
	public SSATransformation() {
		definitions = new HashMap<String, Integer>();
		uses = new HashMap<String, Integer>();
	}

	private void insertPhi(LocalVariable oldVar, LocalVariable newVar) {
		String name = oldVar.getBaseName();
		PhiAssignment phi = null;
		for (Instruction instruction : join.getInstructions()) {
			if (instruction.isPhi()) {
				PhiAssignment tempPhi = (PhiAssignment) instruction;
				if (tempPhi.getTarget().getBaseName().equals(name)) {
					phi = tempPhi;
					break;
				}
			}
		}

		if (phi == null) {
			LocalVariable target = newDefinition(oldVar);
			List<Use> uses = new ArrayList<Use>(2);
			phi = new PhiAssignment(new Location(), target, uses);
			
			Use use = new Use(oldVar, phi);
			uses.add(use);
			use = new Use(oldVar, phi);
			uses.add(use);
		}

		// replace use
		phi.getVars().get(branch - 1).remove();
		Use use = new Use(newVar, phi);
		phi.getVars().set(branch - 1, use);
	}

	/**
	 * Creates a new definition based on the given old variable.
	 * 
	 * @param oldVar
	 *            a variable
	 * @return a new definition based on the given old variable
	 */
	private LocalVariable newDefinition(LocalVariable oldVar) {
		String name = oldVar.getBaseName();

		// get index
		int index;
		if (definitions.containsKey(name)) {
			index = definitions.get(name) + 1;
		} else {
			index = 1;
		}
		definitions.put(name, index);

		// create new variable
		LocalVariable newVar = new LocalVariable(oldVar.isAssignable(), index,
				oldVar.getLocation(), name, oldVar.getType());
		procedure.getLocals().put(newVar.getName(), newVar);

		return newVar;
	}

	@Override
	public void visit(Assign assign, Object... args) {
		LocalVariable target = assign.getTarget();
		LocalVariable newTarget = newDefinition(target);

		// replace target by newTarget
		Use.replaceUses(target, newTarget);
		assign.setTarget(newTarget);

		if (branch != 0) {
			insertPhi(target, newTarget);
		}
	}

	@Override
	public void visit(IfNode ifNode, Object... args) {
		join = ifNode.getJoinNode();

		branch = 1;
		visit(ifNode.getThenNodes());
		branch = 2;
		visit(ifNode.getElseNodes());

		branch = 0;
		visit(ifNode.getJoinNode(), args);
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		definitions.clear();
		uses.clear();
		super.visitProcedure(procedure);
	}

}
