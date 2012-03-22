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
package net.sf.orcc.backends.transformations;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines an actor transformation that transforms code so that at
 * most it contains at most one store and one per static global variable per cycle.
 * 
 * @author Thavot Richard
 * 
 */
public class LoadAndStoreOnceTransformation extends
		AbstractActorVisitor<Object> {

	private class IndexedGlobalVar {

		private final Var var;
		private final EList<Expression> indexes;
		private final String name;

		IndexedGlobalVar(Var var, EList<Expression> indexes) {
			this.var = var;
			this.indexes = indexes;
			this.name = toString();
		}

		public IndexedGlobalVar(Var var) {
			this.var = var;
			this.indexes = null;
			this.name = toString();
		}

		public Var getVar() {
			return var;
		}

		public boolean isStaticIndexes() {
			boolean s = true;
			if (indexes != null) {
				for (Expression expr : indexes) {
					s &= expr.isExprInt();
				}
			}
			return s;
		}

		public InstLoad createInstLoad(Var target) {
			if (indexes != null) {
				return IrFactory.eINSTANCE.createInstLoad(target, var, indexes);
			} else {
				return IrFactory.eINSTANCE.createInstLoad(target, var);
			}
		}

		public InstStore createInstStore(Var source) {
			if (indexes != null) {
				return IrFactory.eINSTANCE
						.createInstStore(var, indexes, source);
			} else {
				return IrFactory.eINSTANCE.createInstStore(var, source);
			}
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof IndexedGlobalVar) {
				return o.toString().equals(name);
			}
			return false;
		}

		@Override
		public String toString() {
			String s = var.getName();
			if (indexes != null) {
				for (Expression expr : indexes) {
					s += "[";
					s += new ExpressionPrinter().doSwitch(expr);
					s += "]";
				}
			}
			return s;
		}

	}

	private Map<IndexedGlobalVar, Var> globalsToLoad;
	private Map<Var, Var> globalsLoaded;
	private Set<IndexedGlobalVar> globalsToStore;
	private Map<Procedure, List<Var>> procsToLoadedVarsMap;

	public LoadAndStoreOnceTransformation() {
		this.procsToLoadedVarsMap = new HashMap<Procedure, List<Var>>();
	}

	private LoadAndStoreOnceTransformation(
			Map<Procedure, List<Var>> procsToLoadedVarsMap) {
		this.procsToLoadedVarsMap = procsToLoadedVarsMap;
	}

	@Override
	public Object caseActor(Actor actor) {

		super.caseActor(actor);

		return null;
	}

	@Override
	public Object caseProcedure(Procedure procedure) {

		globalsToLoad = new HashMap<IndexedGlobalVar, Var>();
		globalsLoaded = new HashMap<Var, Var>();
		globalsToStore = new HashSet<IndexedGlobalVar>();

		super.caseProcedure(procedure);

		if (procedure.eContainer() instanceof Action) {
			addLoads(procedure);
			addStores(procedure);
		} else {
			addParameters(procedure);
		}

		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		Var loadedVar = load.getSource().getVariable();
		if (loadedVar.isGlobal()) {
			Var localVar = load.getTarget().getVariable();
			IndexedGlobalVar global = new IndexedGlobalVar(loadedVar,
					load.getIndexes());
			//Only if var is global and its access is static
			if (global.isStaticIndexes()) {
				if (!globalsToLoad.containsKey(global)) {
					//Create a reference to a global var
					globalsToLoad.put(global, localVar);
				} else {
					//Reference already exists
					Var refVar = globalsToLoad.get(global);
					globalsLoaded.put(localVar, refVar);
				}
				// removes this load
				IrUtil.delete(load);
				indexInst--;
			}
		}
		return null;
	}

	@Override
	public Object caseInstAssign(InstAssign assign) {
		// Replace if a reference already exists to a global var
		super.doSwitch(assign.getValue());
		return null;
	}

	@Override
	public Object caseInstCall(InstCall call) {
		// Replace if a reference already exists to a global var
		for (Arg arg : call.getParameters()) {
			if (arg.isByVal()) {
				ArgByVal argByVal = (ArgByVal) arg;
				super.doSwitch(argByVal.getValue());
			}
		}
		// retrieve loaded variables
		Procedure calledProc = call.getProcedure();
		List<Var> loadedVars = procsToLoadedVarsMap.get(calledProc);
		if (loadedVars == null) {
			// transform procedure
			new LoadAndStoreOnceTransformation(procsToLoadedVarsMap)
					.doSwitch(calledProc);
			loadedVars = procsToLoadedVarsMap.get(calledProc);
		}
		// add additional parameters
		int i = 0;
		for (Var var : loadedVars) {
			IndexedGlobalVar global = new IndexedGlobalVar(var);
			Var local = null;
			if (!globalsToLoad.containsKey(global)) {
				local = procedure.newTempLocalVariable(var.getType(), "local_"
						+ var.getName());
				globalsToLoad.put(new IndexedGlobalVar(var), local);
			} else {
				local = globalsToLoad.get(global);
			}
			call.getParameters().add(i++,
					IrFactory.eINSTANCE.createArgByVal(local));
		}

		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		// Replace if a reference already exists to a global var
		super.doSwitch(store.getValue());
		Var storedVar = store.getTarget().getVariable();
		if (storedVar.isGlobal()) {
			IndexedGlobalVar global = new IndexedGlobalVar(storedVar,
					store.getIndexes());
			//Only if var is global and its access is static
			if (global.isStaticIndexes()) {
				if (globalsToLoad.containsKey(global)) {
					globalsToStore.add(global);
					//Replace Store by an assignment
					Var localVar = globalsToLoad.get(global);
					NodeBlock block = (NodeBlock) store.eContainer();
					InstAssign assign = eINSTANCE.createInstAssign(localVar,
							store.getValue());
					IrUtil.delete(store);
					block.getInstructions().add(indexInst, assign);
				}
			}
		}
		return null;
	}

	@Override
	public Object caseExprVar(ExprVar exprVar) {
		// Replace if a reference already exists to a global var
		Var var = exprVar.getUse().getVariable();
		if (globalsLoaded.containsKey(var)) {
			exprVar.getUse().setVariable(globalsLoaded.get(var));
		}
		return null;
	}

	/**
	 * Add Load instructions at the beginning of the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	private void addLoads(Procedure procedure) {
		EList<Instruction> block = procedure.getFirst().getInstructions();
		for (Entry<IndexedGlobalVar, Var> entry : globalsToLoad.entrySet()) {
			IndexedGlobalVar global = entry.getKey();
			InstLoad load = global.createInstLoad(entry.getValue());
			block.add(0, load);
		}
	}

	/**
	 * Add Store instructions at the end of the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	private List<Var> addStores(Procedure procedure) {
		EList<Instruction> block = procedure.getLast().getInstructions();
		List<Var> storedVars = new ArrayList<Var>();
		int lastInst = block.size();
		for (Entry<IndexedGlobalVar, Var> entry : globalsToLoad.entrySet()) {
			IndexedGlobalVar global = entry.getKey();
			if (globalsToStore.contains(global)) {
				storedVars.add(global.getVar());
				InstStore store = global.createInstStore(entry.getValue());
				block.add(lastInst - 1, store);
			}
		}
		return storedVars;
	}

	/**
	 * Adds one parameter to the given procedure for each loaded global
	 * variables by the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	private void addParameters(Procedure procedure) {
		int i = 0;
		List<Var> loadedVars = new ArrayList<Var>();
		for (Entry<IndexedGlobalVar, Var> entry : globalsToLoad.entrySet()) {
			IndexedGlobalVar global = entry.getKey();
			loadedVars.add(global.getVar());
			Var local = entry.getValue();
			procedure.getParameters().add(i++,
					IrFactory.eINSTANCE.createParam(local));
		}
		procsToLoadedVarsMap.put(procedure, loadedVars);
	}

}
