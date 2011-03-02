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

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.serialize.IRCloner;
import net.sf.orcc.moc.CSDFMoC;
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
	private List<Action> actions;
	private Actor composite;
	private List<Action> initializes;
	private MultiMap<Actor, Port> inputs;
	private Map<Port, GlobalVariable> internalize;
	private MultiMap<Actor, Port> outputs;

	private OrderedMap<String, GlobalVariable> parameters;
	private OrderedMap<String, Procedure> procs;
	private ActionScheduler scheduler;
	private OrderedMap<String, GlobalVariable> stateVars;

	public ActorMerger(MultiMap<Actor, Port> inputs,
			MultiMap<Actor, Port> outputs) {
		this.internalize = new HashMap<Port, GlobalVariable>();
		this.inputs = inputs;
		this.outputs = outputs;
		this.parameters = new OrderedMap<String, GlobalVariable>();
		this.stateVars = new OrderedMap<String, GlobalVariable>();
		this.procs = new OrderedMap<String, Procedure>();
		this.initializes = new ArrayList<Action>();
		this.actions = new ArrayList<Action>();
		this.scheduler = new ActionScheduler(actions, null);
		this.composite = new Actor("", "", parameters, getPorts(inputs),
				getPorts(outputs), false, stateVars, procs, actions,
				initializes, scheduler);
	}

	public void add(Actor candidate, int rate) {
		// Clone the actor so as to isolate transformations on other
		// instance of this actor
		Actor clone = new IRCloner(candidate).clone();

		// Resolve potential conflict in names
		new ConflictSolver(composite).resolve(clone);

		// Internalize ports not define as extern in composite actor
		InternalizeInputs(candidate, rate);
		InternalizeOutputs(candidate, rate);

		// Add all properties
		parameters.putAll(clone.getParameters());
		stateVars.putAll(clone.getStateVars());
		procs.putAll(clone.getProcs());
		actions.addAll(clone.getActions());
		initializes.addAll(clone.getInitializes());

		// Merge scheduler
		mergeScheduler(candidate.getActionScheduler(), rate);
	}

	public Actor getComposite() {
		return composite;
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

	private void InternalizeInputs(Actor candidate, int rate) {
		// Get MoC of the current candidate to define the size of the state
		// variable required
		// TODO: extend to QSDF
		CSDFMoC moc = (CSDFMoC) candidate.getMoC();

		for (Port port : candidate.getInputs()) {
			// Check if the current is external from the composite actor
			if (inputs.containsKey(candidate)) {
				Collection<Port> externPorts = inputs.get(candidate);
				if (externPorts.contains(port)) {
					// Port is external
					continue;
				}
			}

			internalizePort(port, rate * moc.getNumTokensConsumed(port),
					candidate);
		}
	}

	private void InternalizeOutputs(Actor candidate, int rate) {
		// Get MoC of the current candidate to define the size of the state
		// variable required
		// TODO: extend to QSDF
		CSDFMoC moc = (CSDFMoC) candidate.getMoC();

		for (Port port : candidate.getOutputs()) {
			// Check if the current is external from the composite actor
			if (outputs.containsKey(candidate)) {
				Collection<Port> externPorts = outputs.get(candidate);
				if (externPorts.contains(port)) {
					// Port is external
					continue;
				}
			}

			internalizePort(port, rate * moc.getNumTokensProduced(port),
					candidate);
		}
	}

	private void internalizePort(Port port, int tokenSize, Actor candidate) {
		// Check if this port has not been internalize before
		GlobalVariable portVar;

		if (internalize.containsKey(port)) {
			portVar = internalize.get(port);
		} else {
			// Port has never been internalize, create a new one
			TypeList typeList = IrFactory.eINSTANCE.createTypeList(tokenSize,
					port.getType());
			portVar = new GlobalVariable(new Location(), typeList,
					port.getName(), true);

			stateVars.put(portVar.getName(), portVar);
		}

		new InternalizeFifoAccess(port, portVar).visit(candidate);
	}

	public void mergeFSM(FSM candidate, int rate) {
		FSM fsm = scheduler.getFsm();

		for (int i = 0; i < rate; i++) {
			for (Transition transition : candidate.getTransitions()) {
				// Add source state
				String source = transition.getSourceState().getName() + i;
				fsm.addState(source);

				for (NextStateInfo nextState : transition.getNextStateInfo()) {
					// Add transition
					String target = nextState.getTargetState().getName() + i;
					fsm.addState(target);

					fsm.addTransition(source, nextState.getAction(), target);
				}
			}
		}
	}

	public void mergeScheduler(ActionScheduler candidate, int rate) {
		List<Action> actions = scheduler.getActions();

		// Add all actions outside an fsm
		for (int i = 0; i < rate; i++) {
			actions.addAll(candidate.getActions());
		}

		if (candidate.getFsm() == null) {
			// Candidate has no FSM, merging is finished
			return;
		}

		if (scheduler.hasFsm()) {
			// Composite actor has already an FSM
			mergeFSM(candidate.getFsm(), rate);
		} else if (candidate.hasFsm()) {
			// Composite actor has no FSM, take the FSM from candidate
			FSM fsm = candidate.getFsm();
			scheduler.setFsm(fsm);
			mergeFSM(candidate.getFsm(), rate - 1);
		}
	}

}