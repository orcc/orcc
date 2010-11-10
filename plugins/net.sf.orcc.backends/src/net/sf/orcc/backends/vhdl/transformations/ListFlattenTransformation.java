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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.vhdl.instructions.AssignIndex;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * This class defines an actor transformation that transforms declarations and
 * uses of multi-dimensional lists to declarations and uses of lists with a
 * single dimension.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class ListFlattenTransformation extends AbstractActorTransformation {

	/**
	 * Flattens a multi-dimensional list expression to a list with a single
	 * dimension.
	 * 
	 * @param expression
	 *            an expression
	 * @param values
	 *            a list of values
	 */
	private void flattenList(Expression expression, List<Expression> values) {
		if (expression.isListExpr()) {
			List<Expression> expressions = ((ListExpr) expression).getValue();
			for (Expression subExpr : expressions) {
				flattenList(subExpr, values);
			}
		} else {
			values.add(expression);
		}
	}

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
	 */
	private void printAssignment(List<Expression> indexes, Type type) {
		List<Expression> listIndex = new ArrayList<Expression>(indexes.size());
		int concatenatedSize = 0;
		Iterator<Integer> iit = type.getDimensions().iterator();

		// returns the load or store, and has the effect that instructions will
		// be inserted before it
		Instruction instruction = instructionIterator.previous();

		for (Expression expr : indexes) {
			int size;

			// Indexes must have the same size as the lists
			if (iit.hasNext()) {
				size = iit.next();
			} else {
				size = ((TypeInt) type).getSize();
			}

			// index goes from 0 to size - 1, and we remove the sign bit
			int indexSize = IntExpr.getSize(size - 1) - 1;

			// new index variable
			LocalVariable indexVar = procedure.newTempLocalVariable("",
					IrFactory.eINSTANCE.createTypeUint(indexSize), "index");
			listIndex.add(new VarExpr(new Use(indexVar)));

			// add the assign instruction for each index
			Assign assign = new Assign(indexVar, expr);
			instructionIterator.add(assign);

			// size of the concatenated index
			concatenatedSize += indexSize;
		}

		// creates the variable that will hold the concatenation of indexes
		LocalVariable indexVar = procedure.newTempLocalVariable("",
				IrFactory.eINSTANCE.createTypeUint(concatenatedSize),
				"concat_index");

		// sets indexVar as memory index
		Use.removeUses(instruction, indexes);
		indexes.clear();
		indexes.add(new VarExpr(new Use(indexVar)));

		// add a special assign instruction that assigns the index variable the
		// concatenation of index expressions
		AssignIndex assignIndex = new AssignIndex(indexVar, listIndex, type);
		instructionIterator.add(assignIndex);

		// so the load (or store) is not endlessly revisited
		instructionIterator.next();
	}

	@Override
	public void transform(Actor actor) throws OrccException {
		// VHDL synthesizers don't support multi-dimensional memory yet
		for (StateVariable variable : actor.getStateVars()) {
			if (variable.getType().isList()) {
				List<Expression> newValues = new ArrayList<Expression>();
				flattenList(variable.getValue(), newValues);
				variable.setConstantValue(new ListExpr(newValues));
			}
		}

		super.transform(actor);
	}

	@Override
	public void visit(Load load) {
		List<Expression> indexes = load.getIndexes();

		// a VHDL memory is always global
		if (!indexes.isEmpty() && load.getSource().getVariable().isGlobal()) {
			Type type = load.getSource().getVariable().getType();
			printAssignment(indexes, type);
		}
	}

	@Override
	public void visit(Store store) {
		List<Expression> indexes = store.getIndexes();

		// a VHDL memory is always global
		if (!indexes.isEmpty() && store.getTarget().isGlobal()) {
			Type type = store.getTarget().getType();
			printAssignment(indexes, type);
		}
	}

}
