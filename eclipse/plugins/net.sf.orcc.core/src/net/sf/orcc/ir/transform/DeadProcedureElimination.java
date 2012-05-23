/*
 * Copyright (c) 2009, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.ir.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * 
 * Removes the unused procedures
 * 
 * @author Richard Thavot
 * 
 */
public class DeadProcedureElimination extends DfVisitor<Object> {

	private class IrVisitor extends AbstractIrVisitor<Object> {

		@Override
		public Object caseInstCall(InstCall call) {
			Procedure calledProc = call.getProcedure();
			List<InstCall> instCalls = procToCalledInstsMap.get(calledProc);
			if (instCalls == null) {
				new DeadProcedureElimination(procToCalledInstsMap)
						.doSwitch(calledProc);
				instCalls = new ArrayList<InstCall>();
				procToCalledInstsMap.put(calledProc, instCalls);
			}
			instCalls.add(call);

			return null;
		}

		@Override
		public Object caseProcedure(Procedure procedure) {
			this.procedure = procedure;
			return doSwitch(procedure.getBlocks());
		}

	}

	private Map<Procedure, List<InstCall>> procToCalledInstsMap;

	private Set<Procedure> removeProcSet;

	public DeadProcedureElimination() {
		irVisitor = new IrVisitor();
		procToCalledInstsMap = new HashMap<Procedure, List<InstCall>>();
	}

	private DeadProcedureElimination(
			Map<Procedure, List<InstCall>> procToCalledInstsMap) {
		irVisitor = new IrVisitor();
		this.procToCalledInstsMap = procToCalledInstsMap;
	}

	@Override
	public Object caseActor(Actor actor) {

		// 0. Load actor
		this.actor = actor;

		// 1. Visit all InstCall define inside actions
		for (Action initialize : actor.getInitializes()) {
			super.doSwitch(initialize);
		}
		for (Action action : actor.getActions()) {
			super.doSwitch(action);
		}

		// 2.1 Remove uncalled procedure
		removeProcSet = new HashSet<Procedure>();
		for (Procedure procedure : actor.getProcs()) {
			if (!procedure.isNative()) {
				List<InstCall> instCalls = procToCalledInstsMap.get(procedure);
				if (instCalls == null) {
					removeProcSet.add(procedure);
				}
			}
		}
		actor.getProcs().removeAll(removeProcSet);

		return null;
	}

}
