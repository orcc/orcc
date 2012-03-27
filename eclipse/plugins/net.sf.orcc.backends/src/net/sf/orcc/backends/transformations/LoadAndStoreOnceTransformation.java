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
import java.util.Collection;
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
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines an actor transformation that transforms code so that at
 * most it contains at most one store and one per static global variable per
 * cycle.
 * 
 * @author Matthieu Wipliez, Thavot Richard
 * 
 */
public class LoadAndStoreOnceTransformation extends
		AbstractActorVisitor<Object> {

	/**
	 * Locks a variable whether all accesses are not static. e.g x=0, x[10] are
	 * static but not x[i]
	 * 
	 * @author Thavot Richard
	 * 
	 */
	private class GlobalsLocked extends AbstractActorVisitor<Object> {

		Map<Var, Boolean> globalsLockedMap = new HashMap<Var, Boolean>();

		public GlobalsLocked(Procedure procedure) {
			super.doSwitch(procedure);
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			Var loadedVar = load.getSource().getVariable();
			EList<Expression> indexes = load.getIndexes();
			tryLockingVar(loadedVar, indexes);
			return null;
		}

		@Override
		public Object caseInstStore(InstStore store) {
			Var storedVar = store.getTarget().getVariable();
			EList<Expression> indexes = store.getIndexes();
			tryLockingVar(storedVar, indexes);
			return null;
		}

		@Override
		public Object caseInstCall(InstCall call) {
			for (Arg arg : call.getParameters()) {
				if (arg.isByVal()) {
					ArgByVal argByVal = (ArgByVal) arg;
					if (argByVal.getValue().isExprVar()) {
						Var value = ((ExprVar) argByVal.getValue()).getUse()
								.getVariable();
						tryLockingVar(value, null);
					}
				}
			}
			return null;
		}

		private void tryLockingVar(Var var, EList<Expression> indexes) {
			if (var.isGlobal()) {
				if (LoadAndStoreOnceTransformation.isStaticIndexes(indexes)) {
					if (indexes == null & var.getType().isList()) {
						globalsLockedMap.put(var, true);
					} else if (indexes.isEmpty() & var.getType().isList()) {
						globalsLockedMap.put(var, true);
					} else if (!globalsLockedMap.containsKey(var)) {
						globalsLockedMap.put(var, false);
					}
				} else {
					globalsLockedMap.put(var, true);
				}
			}
		}

		public Set<Var> getGlobalsLockedSet() {
			Set<Var> globalsLockedSet = new HashSet<Var>();
			for (Entry<Var, Boolean> entry : globalsLockedMap.entrySet()) {
				if (entry.getValue())
					globalsLockedSet.add(entry.getKey());
			}
			return globalsLockedSet;
		}

	}

	private Map<Procedure, Map<String, Var>> procsKeyToLoadedVarsMap;
	private Map<Procedure, Map<String, Collection<Expression>>> procsKeyToLoadedIndexesMap;

	private Map<Var, Var> localToLocalsMap;

	private Set<Var> globalsLockedSet;

	private Map<String, Var> keyToGlobalsMap;
	private Map<String, Collection<Expression>> keyToIndexesMap;
	private Map<String, Var> keyToLocalsMap;
	private Map<String, NodeBlock> keyToFirstLoadMap;
	private Map<String, NodeBlock> keyToLastStoredMap;

	public LoadAndStoreOnceTransformation() {
		this.procsKeyToLoadedVarsMap = new HashMap<Procedure, Map<String, Var>>();
		this.procsKeyToLoadedIndexesMap = new HashMap<Procedure, Map<String, Collection<Expression>>>();
	}

	private LoadAndStoreOnceTransformation(
			Map<Procedure, Map<String, Var>> procsToLoadedVarsMap,
			Map<Procedure, Map<String, Collection<Expression>>> procsKeyToLoadedIndexesMap) {
		this.procsKeyToLoadedVarsMap = procsToLoadedVarsMap;
		this.procsKeyToLoadedIndexesMap = procsKeyToLoadedIndexesMap;
	}

	@Override
	public Object caseActor(Actor actor) {

		super.caseActor(actor);

		return null;
	}

	@Override
	public Object caseProcedure(Procedure procedure) {

		// if a variable is locked then no transformation is applied
		globalsLockedSet = new GlobalsLocked(procedure).getGlobalsLockedSet();

		localToLocalsMap = new HashMap<Var, Var>();

		keyToGlobalsMap = new HashMap<String, Var>();
		keyToIndexesMap = new HashMap<String, Collection<Expression>>();
		keyToLocalsMap = new HashMap<String, Var>();
		keyToFirstLoadMap = new HashMap<String, NodeBlock>();
		keyToLastStoredMap = new HashMap<String, NodeBlock>();

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
		if (loadedVar.isGlobal() & !globalsLockedSet.contains(loadedVar)) {
			Var localVar = load.getTarget().getVariable();
			EList<Expression> indexes = load.getIndexes();
			if (isStaticIndexes(indexes)) {
				String key = getKey(loadedVar, indexes);
				if (!keyToGlobalsMap.containsKey(key)) {
					keyToGlobalsMap.put(key, loadedVar);
					keyToLocalsMap.put(key, localVar);
					keyToFirstLoadMap.put(key, load.getBlock());
					if (!indexes.isEmpty())
						keyToIndexesMap.put(key, indexes);
				} else {
					localToLocalsMap.put(localVar, keyToLocalsMap.get(key));
				}
				IrUtil.delete(load);
				indexInst--;
			}
		}
		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		// Replace if a reference already exists to a global var
		super.doSwitch(store.getValue());
		//
		Var storedVar = store.getTarget().getVariable();
		if (storedVar.isGlobal() & !globalsLockedSet.contains(storedVar)) {
			EList<Expression> indexes = store.getIndexes();
			if (isStaticIndexes(indexes)) {
				String key = getKey(storedVar, indexes);
				Var localVar = keyToLocalsMap.get(key);
				if (localVar == null) {
					Type type = storedVar.getType();
					if (!indexes.isEmpty()) {
						type = ((TypeList) type).getInnermostType();
					}
					localVar = procedure.newTempLocalVariable(type, "local_"
							+ storedVar.getName());
					keyToGlobalsMap.put(key, storedVar);
					keyToIndexesMap.put(key, indexes);
					keyToLocalsMap.put(key, localVar);
				}
				NodeBlock block = store.getBlock();
				keyToLastStoredMap.put(key, block);
				InstAssign assign = eINSTANCE.createInstAssign(localVar,
						store.getValue());
				IrUtil.delete(store);
				block.getInstructions().add(indexInst, assign);

			}
		}
		return null;
	}

	@Override
	public Object caseInstCall(InstCall call) {
		for (Arg arg : call.getParameters()) {
			if (arg.isByVal()) {
				ArgByVal argByVal = (ArgByVal) arg;
				// Replace if a reference already exists to a global var
				super.doSwitch(argByVal.getValue());
			}
		}
		// retrieve loaded variables
		Procedure calledProc = call.getProcedure();
		Map<String, Var> loadedVars = procsKeyToLoadedVarsMap.get(calledProc);
		if (loadedVars == null) {
			// transform procedure
			new LoadAndStoreOnceTransformation(procsKeyToLoadedVarsMap,
					procsKeyToLoadedIndexesMap).doSwitch(calledProc);
			loadedVars = procsKeyToLoadedVarsMap.get(calledProc);
		}
		Map<String, Collection<Expression>> loadedIndexes = procsKeyToLoadedIndexesMap
				.get(calledProc);
		// add additional parameters
		int i = 0;
		for (Entry<String, Var> entry : loadedVars.entrySet()) {
			String key = entry.getKey();
			Var var = entry.getValue();
			Collection<Expression> indexes = loadedIndexes.get(key);
			Var localVar = null;
			if (keyToLocalsMap.containsKey(key)) {
				localVar = keyToLocalsMap.get(key);
			} else {
				Type type = var.getType();
				keyToGlobalsMap.put(key, var);
				if(indexes == null){
					if(type.isList()){
						localVar = var;
					}else{
						localVar = procedure.newTempLocalVariable(type,
								"local_" + var.getName());
						keyToLocalsMap.put(key, localVar);
						keyToFirstLoadMap.put(key, call.getBlock());
					}
				}else if(indexes.isEmpty()){
					localVar = procedure.newTempLocalVariable(type,
							"local_" + var.getName());
					keyToLocalsMap.put(key, localVar);
					keyToFirstLoadMap.put(key, call.getBlock());
				}else{
					type = ((TypeList) type).getInnermostType();
					localVar = procedure.newTempLocalVariable(type,
							"local_" + var.getName());
					keyToLocalsMap.put(key, localVar);
					keyToIndexesMap.put(key, indexes);
					keyToFirstLoadMap.put(key, call.getBlock());
				}
			}
			keyToLastStoredMap.put(key, call.getBlock());
			call.getParameters().add(i++,
					IrFactory.eINSTANCE.createArgByVal(localVar));
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
	public Object caseExprVar(ExprVar exprVar) {
		// Replace if a reference already exists to a global var
		Var var = exprVar.getUse().getVariable();
		if (localToLocalsMap.containsKey(var)) {
			exprVar.getUse().setVariable(localToLocalsMap.get(var));
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
		for (Entry<String, Var> entry : keyToGlobalsMap.entrySet()) {
			String key = entry.getKey();
			if (keyToFirstLoadMap.containsKey(key)) {
				InstLoad load = IrFactory.eINSTANCE.createInstLoad(
						keyToLocalsMap.get(key), entry.getValue());
				Collection<Expression> indexes = keyToIndexesMap.get(key);
				if (indexes != null) {
					load.getIndexes().addAll(IrUtil.copy(indexes));
				}
				keyToFirstLoadMap.get(key).getInstructions().add(0, load);
			}
		}
	}

	/**
	 * Add Store instructions at the end of the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	private void addStores(Procedure procedure) {
		for (Entry<String, Var> entry : keyToGlobalsMap.entrySet()) {
			String key = entry.getKey();
			if (keyToLastStoredMap.containsKey(key)) {
				InstStore store = IrFactory.eINSTANCE.createInstStore(
						entry.getValue(), keyToLocalsMap.get(key));
				Collection<Expression> indexes = keyToIndexesMap.get(key);
				if (indexes != null) {
					store.getIndexes().addAll(IrUtil.copy(indexes));
				}
				EList<Instruction> block = keyToLastStoredMap.get(key)
						.getInstructions();
				int lastInstIndex = block.size() - 1;
				if (block.get(lastInstIndex).isReturn()) {
					block.add(lastInstIndex, store);
				} else {
					block.add(store);
				}
			}
		}
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
		Map<String, Var> loadedVars = new HashMap<String, Var>();
		Map<String, Collection<Expression>> loadedIndexes = new HashMap<String, Collection<Expression>>();
		for (Entry<String, Var> entry : keyToGlobalsMap.entrySet()) {
			String key = entry.getKey();
			procedure.getParameters().add(i++,
					IrFactory.eINSTANCE.createParam(keyToLocalsMap.get(key)));
			loadedVars.put(key, entry.getValue());
			Collection<Expression> indexes = keyToIndexesMap.get(key);
			if (indexes != null)
				loadedIndexes.put(key, indexes);
		}
		for (Var var : globalsLockedSet) {
			String key = getKey(var, null);
			procedure.getParameters().add(i++,
					IrFactory.eINSTANCE.createParam(var));
			loadedVars.put(key, var);
		}
		procsKeyToLoadedVarsMap.put(procedure, loadedVars);
		procsKeyToLoadedIndexesMap.put(procedure, loadedIndexes);
	}

	/**
	 * Return a String key according the varName and the indexes
	 */
	private String getKey(Var var, Collection<Expression> indexes) {
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

	/**
	 * Returns true whether the indexes is null or empty and whether every
	 * indexes are an ExprInt
	 * 
	 * @param indexes
	 * 
	 */
	private static boolean isStaticIndexes(Collection<Expression> indexes) {
		boolean s = true;
		if (indexes != null) {
			for (Expression expr : indexes) {
				s &= expr.isExprInt();
			}
		}
		return s;
	}

}
