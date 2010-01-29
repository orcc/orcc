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
package net.sf.orcc.tools.merger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transform that changes the load/store on FIFO arrays so
 * they use global arrays.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ChangeFifoArrayAccess extends AbstractActorTransformation {

	private OrderedMap<Variable> stateVars;

	public void transform(Actor actor) {
		stateVars = actor.getStateVars();
		super.transform(actor);
	}

	@SuppressWarnings("unchecked")
	private void updateIndex(Variable var, Instruction instr,
			List<Expression> indexes, Object... args) {
		Expression index = indexes.get(0);

		if (index.equals(new IntExpr(0))) {
			Variable varCount = stateVars.get(var.getName() + "_count");
			Use use = new Use(varCount, instr);
			indexes.set(0, new VarExpr(use));

			indexes = new ArrayList<Expression>(0);
			use = new Use(varCount);
			Use useStore = new Use(varCount);
			Store store = new Store(useStore, indexes, new BinaryExpr(
					new VarExpr(use), BinaryOp.PLUS, new IntExpr(1),
					new IntType(new IntExpr(32))));
			use.setNode(store);
			useStore.setNode(store);

			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			it.add(store);
		} else {
			System.err.println("TODO index");
		}
	}

	@Override
	public void visit(Load node, Object... args) {
		Use use = node.getSource();
		Variable var = use.getVariable();
		if (var.isPort()) {
			use.remove();
			use = new Use(stateVars.get(var.getName()), node);
			node.setSource(use);

			updateIndex(var, node, node.getIndexes(), args);
		}
	}

	@Override
	public void visit(Store node, Object... args) {
		Use use = node.getTarget();
		Variable var = use.getVariable();
		if (var.isPort()) {
			use.remove();
			use = new Use(stateVars.get(var.getName()), node);
			node.setTarget(use);

			updateIndex(var, node, node.getIndexes(), args);
		}
	}

}
