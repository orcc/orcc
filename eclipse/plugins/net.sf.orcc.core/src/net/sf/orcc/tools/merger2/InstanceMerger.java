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
package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transformation that merges a region of static instances
 * into a unique instance.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class InstanceMerger {
	private List<Action> actions;
	private List<Action> initializes;
	private OrderedMap<String, Port> inputs;
	private int nMerged;
	private OrderedMap<String, Port> outputs;
	private OrderedMap<String, GlobalVariable> parameters;
	private OrderedMap<String, Procedure> procs;
	private OrderedMap<String, GlobalVariable> stateVars;

	public InstanceMerger(StaticGraph graph) {
		nMerged = 0;
	}

	private Actor createActor(Instance instance1, Instance instance2) {
		// Set new properties of the actor
		String file = "";
		Boolean nativeFlag = false;
		String name = "Merged" + nMerged++;
		initializes = new ArrayList<Action>();
		inputs = new OrderedMap<String, Port>();
		outputs = new OrderedMap<String, Port>();
		parameters = new OrderedMap<String, GlobalVariable>();
		procs = new OrderedMap<String, Procedure>();
		stateVars = new OrderedMap<String, GlobalVariable>();
		actions = new ArrayList<Action>();
		ActionScheduler scheduler = new ActionScheduler(
				new ArrayList<Action>(), null);

		// Store properties of each instance
		storeInstanceProperty(instance1.getActor());
		storeInstanceProperty(instance2.getActor());

		return new Actor(name, file, parameters, inputs, outputs, nativeFlag,
				stateVars, procs, actions, initializes, scheduler);
	}

	public Vertex merge(Vertex vertex1, Vertex vertex2) {

		Instance instance1 = vertex1.getInstance();
		Instance instance2 = vertex2.getInstance();

		Actor newActor = createActor(instance1, instance2);

		Instance newInstance = new Instance("Merged" + nMerged++,
				newActor.getName());

		return new Vertex(newInstance);
	}

	private void storeInstanceProperty(Actor actor) {
		inputs.putAll(actor.getInputs());
		outputs.putAll(actor.getOutputs());
		parameters.putAll(actor.getParameters());
		stateVars.putAll(actor.getStateVars());
		procs.putAll(actor.getProcs());
		initializes.addAll(actor.getInitializes());
		actions.addAll(actor.getActions());

	}
}
