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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.backends.xlim.instructions.CustomPeek;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * 
 * This class defines a transformation that replace Peek instruction by our
 * custom Peek instruction.
 * 
 * @author Herve Yviquel
 * 
 */
public class CustomPeekAdder extends AbstractActorTransformation {

	private class NeededPeekFinder extends AbstractActorTransformation {

		private List<CustomPeek> neededPeeks;
		private Peek originalPeek;

		public List<CustomPeek> find(Peek originalPeek) {
			neededPeeks = new ArrayList<CustomPeek>();
			this.originalPeek = originalPeek;
			visit(originalPeek.getBlock().getProcedure());
			return neededPeeks;
		}

		@Override
		public void visit(Load load) {
			if (load.getSource().getVariable() == originalPeek.getTarget()) {
				int index = ((IntExpr) load.getIndexes().get(0)).getIntValue();
				CustomPeek newPeek = new CustomPeek(originalPeek.getLocation(),
						originalPeek.getPort(), index, load.getTarget());
				neededPeeks.add(newPeek);
				unusedLoads.add(load);
			}
		}
	}

	private class VariableReplacer extends AbstractActorTransformation {
		private Variable addedVariable;
		private Variable removedVariable;

		public void replace(Variable removedVariable, Variable addedVariable,
				Procedure procedure) {
			this.removedVariable = removedVariable;
			this.addedVariable = addedVariable;
			visit(procedure);
		}

		public void visit(List<CFGNode> nodes) {
			ListIterator<CFGNode> it = nodes.listIterator();
			while (it.hasNext()) {
				CFGNode node = it.next();
				itNode = it;
				if (node.isBlockNode()) {
					node.accept(this);
				}
			}
		}

		@Override
		public void visit(Load load) {
			if (load.getSource().getVariable() == removedVariable) {
				load.setSource(new Use(addedVariable));
			}
		}
	}

	private List<Load> unusedLoads;

	@Override
	public void transform(Actor actor) {
		this.actor = actor;

		itAction = actor.getActions().listIterator();
		while (itAction.hasNext()) {
			Action action = itAction.next();
			visit(action);
		}

		itAction = actor.getInitializes().listIterator();
		while (itAction.hasNext()) {
			Action action = itAction.next();
			visit(action);
		}
	}

	@Override
	public void visit(Action action) {
		unusedLoads = new ArrayList<Load>();
		visit(action.getScheduler());
	}

	@Override
	public void visit(Load load) {
		if (unusedLoads.contains(load)) {
			itInstruction.remove();
		}
	}

	@Override
	public void visit(Peek peek) {
		if (!peek.isUnit()) {
			itInstruction.remove();
			Instruction instruction = itInstruction.next();
			if (instruction.isAssign() && !itInstruction.hasNext()) {
				itInstruction.remove();
				WhileNode whileNode = (WhileNode) itNode.next();
				Store store = (Store) ((BlockNode) whileNode.getNodes().get(0))
						.getInstructions().get(1);
				(new VariableReplacer()).replace(store.getTarget(),
						peek.getTarget(), procedure);
				itNode.remove();
			}
			for (CustomPeek newPeek : (new NeededPeekFinder()).find(peek)) {
				itInstruction.add(newPeek);
			}

			while (itInstruction.hasPrevious()) {
				itInstruction.previous();
			}
		}
	}
}
