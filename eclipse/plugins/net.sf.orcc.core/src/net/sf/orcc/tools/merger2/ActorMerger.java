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
package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.serialize.IRCloner;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.util.MultiMap;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transformation that merge multiple actors into a single
 * actor.
 * 
 * @author Jerome Gorin
 * 
 */
public class ActorMerger {

	/**
	 * This class defines a transformation that rename all possible conflicts
	 * between the composite actor and the candidate to merge with.
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private class ResolveConflictName extends AbstractActorVisitor {
		private OrderedMap<String, Procedure> refProcs;

		public ResolveConflictName() {
			this.refProcs = new OrderedMap<String, Procedure>();
			replacementMap = new HashMap<String, String>();

			// Store all procedure names of the composite actor
			refProcs.putAll(procs);
			getProcedures(actions);
			getProcedures(initializes);
		}

		private void compareVariables(
				OrderedMap<String, GlobalVariable> refVariables,
				OrderedMap<String, GlobalVariable> variables) {
			for (Variable variable : variables) {
				String name = variable.getName();

				// Check if Global variable name is already used in the
				// reference actor
				if (refVariables.contains(name)) {
					// Name is used set a unique index to the name
					int index = 0;

					while (refVariables.contains(name + index)) {
						index++;
					}

					// Store result in replacement map
					replacementMap.put(name, name + index);
				}
			}
		}

		private void getProcedures(List<Action> actions) {
			for (Action action : actions) {
				// Get all procedures contain in an action
				Procedure scheduler = action.getScheduler();
				Procedure body = action.getBody();
				procs.put(scheduler.getName(), scheduler);
				procs.put(body.getName(), body);
			}
		}

		public void resolve(Actor actor) {
			// Check conflicts on global variables
			compareVariables(parameters, actor.getParameters());
			compareVariables(stateVars, actor.getStateVars());

			// Check all procedures
			visit(actor);

			if (!replacementMap.isEmpty()) {
				// Rename all conflicts founds
				new RenameTransformation(replacementMap).visit(actor);
			}
		}

		@Override
		public void visit(Procedure procedure) {
			String name = procedure.getName();

			// Check if procedure name is already used in the reference actor
			if (refProcs.contains(name)) {
				// Name is used set a unique index to the name
				int index = 0;

				while (refProcs.contains(name + index)) {
					index++;
				}

				// Store result in replacement map
				replacementMap.put(name, name + index);
			}
		}

	}

	private List<Action> actions;
	private List<Action> initializes;
	private MultiMap<Actor, Port> inputs;
	private MultiMap<Actor, Port> outputs;
	private OrderedMap<String, GlobalVariable> parameters;
	private OrderedMap<String, Procedure> procs;
	private Map<String, String> replacementMap;
	private ActionScheduler scheduler;

	private OrderedMap<String, GlobalVariable> stateVars;

	public ActorMerger(MultiMap<Actor, Port> inputs,
			MultiMap<Actor, Port> outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.parameters = new OrderedMap<String, GlobalVariable>();
		this.stateVars = new OrderedMap<String, GlobalVariable>();
		this.procs = new OrderedMap<String, Procedure>();
		this.initializes = new ArrayList<Action>();
		this.actions = new ArrayList<Action>();
	}

	public void add(Actor candidate) {
		// Clone the actor so to isolate transformations from another possible
		// instance of this actor
		Actor clone = new IRCloner(candidate).clone();

		// Resolve potential conflict in names
		new ResolveConflictName().resolve(clone);
	}

	public Actor getComposite() {
		return new Actor("", "", parameters, getPorts(inputs),
				getPorts(outputs), false, stateVars, procs, actions,
				initializes, scheduler);
	}

	private OrderedMap<String, Port> getPorts(MultiMap<Actor, Port> ports) {
		OrderedMap<String, Port> orderPorts = new OrderedMap<String, Port>();

		for (Entry<Actor, Collection<Port>> entry : ports.entrySet()) {
			for (Port port : entry.getValue()) {
				orderPorts.put(port.getName(), port);
			}
		}

		return orderPorts;
	}

}