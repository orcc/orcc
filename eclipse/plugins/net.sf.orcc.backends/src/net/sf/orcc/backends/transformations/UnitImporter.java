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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Unit;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines a transformation that imports objects defined in units.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class UnitImporter extends AbstractActorVisitor<Object> {

	private Copier copier;

	private int indexProc;

	private int indexVar;

	public UnitImporter() {
		copier = new EcoreUtil.Copier();
	}

	@Override
	public Object caseActor(Actor actor) {
		this.actor = actor;

		List<Procedure> procs2 = new ArrayList<Procedure>(actor.getProcs());
		for (Procedure procedure : procs2) {
			doSwitch(procedure);
		}

		for (Action action : actor.getActions()) {
			doSwitch(action);
		}

		for (Action initialize : actor.getInitializes()) {
			doSwitch(initialize);
		}

		return null;
	}

	@Override
	public Object caseInstCall(InstCall call) {
		Procedure proc = call.getProcedure();
		Procedure procInActor = (Procedure) doSwitch(proc);
		call.setProcedure(procInActor);
		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		Use use = load.getSource();
		Var var = use.getVariable();
		if (var.eContainer() instanceof Unit) {
			Var varInActor = (Var) copier.get(var);
			if (varInActor == null) {
				varInActor = (Var) copier.copy(var);
				actor.getStateVars().add(indexVar++, varInActor);
			}
			use.setVariable(varInActor);
		}

		return null;
	}

	@Override
	public Object caseProcedure(Procedure proc) {
		if (proc.eContainer() instanceof Unit) {
			Procedure procInActor = (Procedure) copier.get(proc);
			if (procInActor == null) {
				procInActor = (Procedure) copier.copy(proc);

				TreeIterator<EObject> it = EcoreUtil.getAllContents(proc, true);
				while (it.hasNext()) {
					EObject object = it.next();

					if (object instanceof Def) {
						Def def = (Def) object;
						Var copyVar = (Var) copier.get(def.getVariable());
						Def copyDef = (Def) copier.get(def);
						copyDef.setVariable(copyVar);
					} else if (object instanceof Use) {
						Use use = (Use) object;
						Var var = use.getVariable();
						Var copyVar = (Var) copier.get(var);
						Use copyUse = (Use) copier.get(use);
						if (copyVar == null) {
							// happens for variables loaded from units
							// handled by caseInstLoad
							copyUse.setVariable(var);
						} else {
							copyUse.setVariable(copyVar);
						}
					} else if (object instanceof InstCall) {
						InstCall innerCall = (InstCall) object;
						Procedure copyProc = (Procedure) doSwitch(innerCall
								.getProcedure());
						InstCall copyCall = (InstCall) copier.get(innerCall);
						copyCall.setProcedure(copyProc);
					}
				}

				actor.getProcs().add(indexProc++, procInActor);
				doSwitch(procInActor);
			}
			return procInActor;
		} else {
			super.caseProcedure(proc);
			return proc;
		}
	}

}
