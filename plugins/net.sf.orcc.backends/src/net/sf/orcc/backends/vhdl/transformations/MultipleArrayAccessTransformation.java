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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * This transformation transforms an actor so that there is at most one access
 * (read or write) per each given array accessed by an action. This pass should
 * be run after inline.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class MultipleArrayAccessTransformation extends
		AbstractActorTransformation {

	/**
	 * This class counts the number of loads/stores to each given array.
	 * 
	 */
	private static class RWCounter extends AbstractActorTransformation {

		private Map<Variable, Integer> numRW;

		public RWCounter() {
			numRW = new HashMap<Variable, Integer>();
		}

		public Map<Variable, Integer> getMap() {
			return numRW;
		}

		@Override
		public void visit(Load load) {
			visitLoadStore(load.getSource().getVariable(), load.getIndexes());
		}

		@Override
		public void visit(Store store) {
			visitLoadStore(store.getTarget(), store.getIndexes());
		}

		/**
		 * Visits a load or a store, and updates the numRW map.
		 * 
		 * @param variable
		 *            a variable
		 * @param indexes
		 *            a list of indexes
		 */
		private void visitLoadStore(Variable variable, List<Expression> indexes) {
			if (!indexes.isEmpty()) {
				Integer numReads = numRW.get(variable);
				if (numReads == null) {
					numRW.put(variable, 1);
				} else {
					numRW.put(variable, numReads + 1);
				}
			}
		}

	}

	/**
	 * Adds an FSM to the given action scheduler.
	 * 
	 * @param actionScheduler
	 *            action scheduler
	 */
	private void addFsm(ActionScheduler actionScheduler) {
		FSM fsm = new FSM();
		fsm.setInitialState("init");
		fsm.addState("init");
		for (Action action : actionScheduler.getActions()) {
			fsm.addTransition("init", "init", action);
		}

		actionScheduler.getActions().clear();
		actionScheduler.setFsm(fsm);
	}

	@Override
	public void visit(Action action) {
		// we are only interested in the body
		visit(action.getBody());
	}

	@Override
	public void visit(Procedure procedure) {
		RWCounter counter = new RWCounter();
		counter.visit(procedure);

		Map<Variable, Integer> numRW = counter.getMap();
		boolean needsTransformation = false;
		for (Integer value : numRW.values()) {
			if (value > 1) {
				needsTransformation = true;
				break;
			}
		}

		if (needsTransformation) {
			FSM fsm = actor.getActionScheduler().getFsm();
			if (fsm == null) {
				addFsm(actor.getActionScheduler());
			}
		}
	}

}
