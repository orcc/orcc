/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.llvm.transformations;

import java.util.List;

import net.sf.orcc.backends.ir.InstGetElementPtr;
import net.sf.orcc.backends.ir.IrSpecificFactory;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;

/**
 * Add GetElementPtr instructions in actor IR.
 * 
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class GetElementPtrAdder extends AbstractActorVisitor<Object> {

	private Var addGEP(Var array, Type type, List<Expression> indexes,
			BlockBasic currentNode) {
		// Make a new localVariable that will contains the elt to access
		Var eltVar = procedure.newTempLocalVariable(IrUtil.copy(type),
				array.getName() + "_" + "elt");

		InstGetElementPtr gep = IrSpecificFactory.eINSTANCE
				.createInstGetElementPtr(array, eltVar, indexes);
		currentNode.add(indexInst, gep);

		return eltVar;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		Var source = load.getSource().getVariable();
		EList<Expression> indexes = load.getIndexes();

		if (!indexes.isEmpty()) {
			TypeList typeList = (TypeList) source.getType();
			
			Var newSource = addGEP(source, typeList.getInnermostType(), indexes,
					load.getBlock());

			load.setSource(IrFactory.eINSTANCE.createUse(newSource));
		}
		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		Var target = store.getTarget().getVariable();
		EList<Expression> indexes = store.getIndexes();

		if (!indexes.isEmpty()) {
			TypeList typeList = (TypeList) target.getType();

			Var newTarget = addGEP(target, typeList.getInnermostType(), indexes,
					store.getBlock());

			store.setTarget(IrFactory.eINSTANCE.createDef(newTarget));
		}
		return null;
	}

}
