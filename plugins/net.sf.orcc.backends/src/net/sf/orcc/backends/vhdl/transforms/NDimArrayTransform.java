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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.backends.vhdl.instructions.AssignIndex;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class defines an actor transformation that transforms indexes
 * assignments from a N dimension array(index_0, index_1, index_2) to an one
 * dimension array(index) with index = index_0 & index_1 & index_2.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class NDimArrayTransform extends AbstractActorTransformation {

	/**
	 * Prints the indexes of an NDim array, each index is print separately and
	 * cast to the correct size Arguments of the function are the indexes, the
	 * indexes iterator, the instruction iterator and the instruction type.
	 * 
	 * @param indexes
	 *            a list of indexes
	 * @param iit
	 *            an indexes iterator
	 * @param it
	 *            an instruction iterator
	 * @param type
	 *            the instruction type
	 * 
	 * @return listIndex a list of the new indexes printed
	 */
	private List<Expression> printAssignement(List<Expression> indexes,
			Iterator<Integer> iit, ListIterator<Instruction> it, Type type) {
		List<Expression> listIndex = new ArrayList<Expression>();

		for (Expression expr : indexes) {
			int size;
			VarExpr index;

			// Indexes must have the same size as the lists
			if (iit.hasNext()) {
				size = iit.next();
			} else {
				size = ((TypeInt) type).getSize();
			}
			// The VHDL require an index from 0 to size -1 so the signed bit
			// is remove (-1) and it subtracts 1 to the size of the list
			size = IntExpr.getSize(size - 1) - 1;

			// Add the assign instruction
			LocalVariable indexVar = procedure.newTempLocalVariable("",
					IrFactory.eINSTANCE.createTypeUint(size), "index");
			it.previous();
			Assign assign = new Assign(expr.getLocation(), indexVar, expr);
			it.add(assign);
			it.next();
			index = new VarExpr(new Use(indexVar));
			listIndex.add(index);
		}
		return listIndex;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void visit(Load load, Object... args) {
		List<Expression> indexes = load.getIndexes();

		// An VHDL memory is always global
		if (!indexes.isEmpty() && load.getSource().getVariable().isGlobal()) {
			Type type = load.getSource().getVariable().getType();
			Iterator<Integer> iit = type.getDimensions().iterator();
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			List<Expression> listIndex = printAssignement(indexes, iit, it,
					type);

			// Assign index to memory
			Use.removeUses(load, indexes);
			indexes.clear();
			AssignIndex assignIndex = new AssignIndex(load.getTarget(),
					listIndex);
			it.previous();
			it.add(assignIndex);
			it.next();
			it.remove();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void visit(Store store, Object... args) {
		List<Expression> indexes = store.getIndexes();

		// An VHDL memory is always global
		if (!indexes.isEmpty() && store.getTarget().isGlobal()) {
			Type type = store.getTarget().getType();
			Iterator<Integer> iit = type.getDimensions().iterator();
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			List<Expression> listIndex = printAssignement(indexes, iit, it,
					type);

			// Assign index to memory
			Use.removeUses(store, indexes);
			indexes.clear();
			AssignIndex assignIndex = new AssignIndex(
					(LocalVariable) store.getValue(), listIndex);
			it.previous();
			it.add(assignIndex);
			it.next();
			it.remove();
		}
	}
}
