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
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
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

	@SuppressWarnings({ "unchecked" })
	@Override
	public void visit(Load load, Object... args) {
		List<Expression> indexes = load.getIndexes();
		// An VHDL memory is always global
		if (!indexes.isEmpty() && load.getSource().getVariable().isGlobal()) {
			Iterator<Expression> it = indexes.iterator();
			LocalVariable indexVar = null;
			Type type = load.getSource().getVariable().getType();
			Iterator<Integer> typeit = null;
			Integer sizeindex = 0;
			VarExpr index = null;				

			if (!type.getDimensions().isEmpty())
				typeit = type.getDimensions().iterator();

			// Print a new assignment made up of index_i = expression for each
			// indexes.
			while (it.hasNext()) {
				Expression expr = it.next();
				Integer size;

				// Index size must be similar to the list size
				if (!type.getDimensions().isEmpty()) 
					size = typeit.next();
				else 
					size = ((TypeInt) type).getSize();

				// A type is printed with a size of 2^size so the size must be recompute
				int i;
				for (i=0; Math.pow(2, i) < size; i++){
				}
				size = i;
				sizeindex += size;

				// Add the assign instruction
				indexVar = procedure.newTempLocalVariable("",
						IrFactory.eINSTANCE.createTypeUint(size), "index");
				ListIterator<Instruction> iit = (ListIterator<Instruction>) args[0];
				iit.previous();
				Assign assign = new Assign(expr.getLocation(), indexVar, expr);
				iit.add(assign);
				iit.next();

				index = new VarExpr(new Use(indexVar));
			}

			// Removes indexes (Ndim)
			Use.removeUses(load, indexes);
			indexes.clear();

			// Create a new assignment (index = index1 & index2 & indexn)		
			/*if (indexes.size() > 1) {		
				indexVar = procedure.newTempLocalVariable("",
						IrFactory.eINSTANCE.createTypeUint(sizeindex), "index");
				index = new VarExpr(new Use(indexVar));				
				BinaryOp op = null;
				BinaryExpr assignment = new BinaryExpr(index, op.PLUS, null, null);
				ListIterator<Instruction> iit = (ListIterator<Instruction>) args[0];
				iit.previous();			
				Assign assign = new Assign(load.getLocation(), indexVar, index);
				iit.add(assign);							
				iit.next();
				indexes.add(assignment);
			} else {*/
				indexes.add(index);
			//}
		}
	}
}
