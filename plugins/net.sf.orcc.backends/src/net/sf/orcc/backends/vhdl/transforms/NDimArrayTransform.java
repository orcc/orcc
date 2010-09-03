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
package net.sf.orcc.backends.vhdl.transforms;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class defines an actor transformation that transforms indexes
 * assignments from array(index_0, index_1, index_2) to array array(index) with
 * index = index_0 & index_1 & index_2.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class NDimArrayTransform extends AbstractActorTransformation {

	@SuppressWarnings("unchecked")
	@Override
	public void visit(Load load, Object... args) {
		List<Expression> indexes = load.getIndexes();
		if (!indexes.isEmpty()) {
			Iterator<Expression> it = indexes.iterator();
			Expression index = null;

			while (it.hasNext()) {
				Expression expr = it.next();

				Type type = expr.getType();
				LocalVariable indexVar = procedure.newTempLocalVariable("", type,
						"index");

				ListIterator<Instruction> iit = (ListIterator<Instruction>) args[0];
				iit.previous();
				Assign assign = new Assign(expr.getLocation(), indexVar, expr);
				iit.add(assign);
				iit.next();

				// Add index to indexes
				index = new VarExpr(new Use(indexVar));
			}

			Use.removeUses(load, indexes);
			indexes.clear();
			indexes.add(index);
		}
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public void visit(Store store, Object... args) {
	 * List<Expression> indexes = store.getIndexes(); int size = indexes.size();
	 * Type typemax = null; while (size != 0) { size--; if
	 * (!indexes.get(size).isIntExpr()) { Type type =
	 * indexes.get(size).getType(); //if (Cast.getSizeOfType(type) >
	 * Cast.getSizeOfType(typemax)) { typemax = type; //} LocalVariable local =
	 * new LocalVariable(true, size, new Location(),
	 * "index_"+IndexCount++,type); procedure.getLocals().put(local.getName(),
	 * local); block = store.getBlock(); ListIterator<Instruction> iit =
	 * (ListIterator<Instruction>) args[0]; iit.previous();
	 * createAssignNode(local, indexes.get(size)); indexes.remove(size); } } //
	 * Add index to indexes LocalVariable local = new LocalVariable(true, 0, new
	 * Location(), "index", typemax); indexes.add(new VarExpr(new Use(local)));
	 * }
	 */
}
