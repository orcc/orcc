/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

/**
 * This class defines an actor transformation that transforms code so that at
 * most it contains at most one store per variable per cycle.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StoreOnceTransformation extends AbstractActorVisitor<Object> {

	private List<Var> globalsToStore;

	private Map<Var, Var> globalsToLoad;

	private Map<Var, Var> globalToLocalMap;

	public StoreOnceTransformation() {
		globalsToLoad = new HashMap<Var, Var>();
		globalToLocalMap = new HashMap<Var, Var>();
		globalsToStore = new ArrayList<Var>();
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		if (load.getIndexes().isEmpty()) {
			// gets source, target, block of this load
			NodeBlock block = (NodeBlock) load.eContainer();
			Var source = load.getSource().getVariable();
			Var target = load.getTarget().getVariable();

			// removes this load
			IrUtil.delete(load);
			indexInst--;

			// find the local associated with the source
			Var local = globalToLocalMap.get(source);
			if (local == null) {
				// we haven't loaded the variable yet, add it to the "to load"
				// set
				globalToLocalMap.put(source, target);
				globalsToLoad.put(source, target);
			} else {
				// we already loaded the variable, add an assign
				if (target != local) {
					InstAssign assign = eINSTANCE.createInstAssign(target,
							local);
					indexInst++;
					block.getInstructions().add(indexInst, assign);
				}
			}
		}
		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		if (store.getIndexes().isEmpty()) {
			Var target = store.getTarget().getVariable();
			Expression value = store.getValue();

			Var local = globalToLocalMap.get(target);
			if (local == null) {
				// variable not loaded yet, create new local
				local = procedure.newTempLocalVariable(target.getType(),
						"local_" + target.getName());
				globalToLocalMap.put(target, local);
			}

			// replace store by assignment "local := value;"
			NodeBlock block = (NodeBlock) store.eContainer();
			InstAssign assign = eINSTANCE.createInstAssign(local, value);
			IrUtil.delete(store);
			block.getInstructions().add(indexInst, assign);

			// add global to store list
			if (!globalsToStore.contains(target)) {
				globalsToStore.add(target);
			}
		}
		return null;
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		super.caseProcedure(procedure);

		// add loads
		int i = 0;
		for (Entry<Var, Var> entry : globalsToLoad.entrySet()) {
			Var global = entry.getKey();
			Var local = entry.getValue();
			InstLoad load = eINSTANCE.createInstLoad(local, global);
			procedure.getFirst().add(i++, load);
		}

		// add stores
		for (Var global : globalsToStore) {
			Var local = globalToLocalMap.get(global);
			InstStore store = eINSTANCE.createInstStore(global, local);
			procedure.getLast().add(store);
		}

		globalsToLoad.clear();
		globalToLocalMap.clear();
		globalsToStore.clear();
		return null;
	}

}
