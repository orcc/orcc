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
package net.sf.orcc.backends.xlim.transforms;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * 
 * This class defines a transformation that replace local list by multiple
 * variables
 * 
 * @author Herve Yviquel
 * 
 */
public class LocalListToMultipleVariableTransformation extends
		AbstractActorTransformation {

	@Override
	public void visit(Store store) {
		Variable target = store.getTarget();
		List<Expression> indexes = store.getIndexes();
		Expression value = store.getValue();
		List<Integer> dimensions = target.getType().getDimensions();
		boolean test = false;
		if (dimensions.size() > 0) {
			test = dimensions.get(0) > 1;
		}

		if (!target.isGlobal() && !indexes.isEmpty()
				&& indexes.get(0).isIntExpr() && test) {
			instructionIterator.remove();
			int i = ((IntExpr) indexes.get(0)).getIntValue();
			LocalVariable lvar = procedure.newTempLocalVariable(null,
					value.getType(), target.getName() + "_" + i);
			instructionIterator.add(new Assign(lvar, value));

		}
	}

	@Override
	public void visit(Write write) {
		Variable target = write.getTarget();
		List<Integer> dimensions = target.getType().getDimensions();
		boolean test = false;
		if (dimensions.size() > 0) {
			test = dimensions.get(0) > 1;
		}
		if (!target.isGlobal() && target.getType().isList()
				&& write.getNumTokens() > 1 && test) {
			if (procedure.getLocals().get(target.getName() + "_" + 0) != null) {
				instructionIterator.remove();
				for (int i = 0; i < write.getNumTokens(); i++) {
					instructionIterator.add(new Write(write.getPort(), 1,
							procedure.getLocals().get(
									target.getName() + "_" + i)));
				}
			}
		}
	}
}
