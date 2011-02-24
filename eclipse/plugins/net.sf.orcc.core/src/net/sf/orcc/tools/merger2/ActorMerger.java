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
import java.util.List;
import java.util.Map.Entry;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.util.MultiMap;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transformation that merge multiple actors into a single
 * actor.
 * 
 * @author Jerome Gorin
 * 
 */
public class ActorMerger extends AbstractActorVisitor {

	private List<Action> actions;
	private List<Action> initializes;
	private MultiMap<Actor, Port> inputs;
	private MultiMap<Actor, Port> outputs;
	private OrderedMap<String, GlobalVariable> parameters;
	private String prefix;
	private Procedure procedure;
	private OrderedMap<String, Procedure> procs;
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

	/*
	 * private void checkVariables(OrderedMap<String, GlobalVariable> variables,
	 * OrderedMap<String, GlobalVariable> targets) { for (GlobalVariable
	 * variable : variables) { String name = variable.getName(); GlobalVariable
	 * var = new GlobalVariable(variable.getLocation(), variable.getType(),
	 * prefix + "_" + name, variable.isAssignable(),
	 * variable.getInitialValue());
	 * 
	 * targets.put(var.getName(), var); } }
	 */

	public Actor getActor() {
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

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void visit(Action action) {

		super.visit(action.getScheduler());
		Procedure scheduler = procedure;
		super.visit(action.getBody());
		Procedure body = procedure;

		actions.add(new Action(action.getLocation(), action.getTag(), action
				.getInputPattern(), action.getOutputPattern(), scheduler, body));

	}

	@Override
	public void visit(List<CFGNode> nodes) {
		super.visit(nodes);
	}

	@Override
	public void visit(Procedure procedure) {

		super.visit(procedure);

		this.procedure = new Procedure(procedure.getName(),
				procedure.isNative(), procedure.getLocation(),
				procedure.getReturnType(), procedure.getParameters(),
				procedure.getLocals(), procedure.getNodes());
	}

}