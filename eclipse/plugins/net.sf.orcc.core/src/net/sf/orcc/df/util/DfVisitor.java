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
package net.sf.orcc.df.util;

import java.util.HashSet;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

import org.eclipse.emf.ecore.EObject;

/**
 * This class implements a no-op visitor on an actor. This class should be
 * extended by classes that implement actor visitors and transformations.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @since 1.2
 */
public class DfVisitor<T> extends DfSwitch<T> {

	protected Actor actor;

	protected AbstractIrVisitor<T> irVisitor;

	protected boolean visitOnce;

	private Set<Actor> visited;

	/**
	 * Creates a new visitor that visits objects from the Df model. Each actor
	 * is visited exactly one time.
	 */
	public DfVisitor() {
		this(null, true);
	}

	/**
	 * Creates a new visitor that visits objects from the Df model, and
	 * delegates to the given irVisitor for objects of the Ir model. Each actor
	 * is visited exactly one time.
	 * 
	 * @param irVisitor
	 *            a concrete implementation of AbstractIrVisitor that is invoked
	 *            when visiting the IR
	 */
	public DfVisitor(AbstractIrVisitor<T> irVisitor) {
		this(irVisitor, true);
	}

	/**
	 * Creates a new visitor that visits an object from the Df model, and
	 * delegates to the given irVisitor for objects of the Ir model. Unless
	 * visitOnce is false, each actor is visited exactly one time. If visitOnce
	 * is false, then this visitor visits actors each time they are referenced.
	 * 
	 * @param irVisitor
	 *            a concrete implementation of AbstractIrVisitor that is invoked
	 *            when visiting the IR
	 * @param visitOnce
	 *            <code>true</code> for the default behavior (visit each actor
	 *            once), <code>false</code> for visiting actors when they are
	 *            referenced (possibly multiple times)
	 */
	public DfVisitor(AbstractIrVisitor<T> irVisitor, boolean visitOnce) {
		this.irVisitor = irVisitor;
		this.visitOnce = visitOnce;
		if (visitOnce) {
			visited = new HashSet<Actor>();
		}
	}

	@Override
	public T caseAction(Action action) {
		doSwitch(action.getInputPattern());
		doSwitch(action.getOutputPattern());
		doSwitch(action.getPeekPattern());
		doSwitch(action.getScheduler());
		doSwitch(action.getBody());

		return null;
	}

	@Override
	public T caseActor(Actor actor) {
		for (Port port : actor.getInputs()) {
			doSwitch(port);
		}
		for (Port port : actor.getOutputs()) {
			doSwitch(port);
		}
		for (Var parameter : actor.getParameters()) {
			doSwitch(parameter);
		}

		for (Var stateVar : actor.getStateVars()) {
			doSwitch(stateVar);
		}

		for (Procedure procedure : actor.getProcs()) {
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
	public T caseConnection(Connection connection) {
		return null;
	}

	@Override
	public T caseInstance(Instance instance) {
		Actor actor = instance.getAdapter(Actor.class);
		if (actor == null) {
			// instance of something else than an actor
			doSwitch(instance.getEntity());
		} else {
			// instance of an actor
			if (visitOnce) {
				// if visitOnce is true, make sure we did not visit this actor
				if (visited.contains(actor)) {
					// actor already visited, return
					return null;
				}

				// add actor to visited set
				visited.add(actor);
			}

			// visit actor (note the fall-through if visitOnce is false)
			doSwitch(actor);
		}
		return null;
	}

	@Override
	public T caseNetwork(Network network) {
		for (Vertex vertex : network.getVertices()) {
			doSwitch(vertex);
		}

		for (Connection connection : network.getConnections()) {
			doSwitch(connection);
		}

		return null;
	}

	@Override
	public T casePattern(Pattern pattern) {
		return null;
	}

	@Override
	public T casePort(Port port) {
		return null;
	}

	@Override
	public T defaultCase(EObject eObject) {
		if (irVisitor != null) {
			if (irVisitor.isSwitchFor(eObject.eClass().getEPackage())) {
				return irVisitor.doSwitch(eObject);
			}
		}
		return null;
	}

	@Override
	public final T doSwitch(EObject eObject) {
		// allow null objects
		if (eObject == null) {
			return null;
		}
		return doSwitch(eObject.eClass(), eObject);
	}

	/**
	 * Returns the value of the <code>actor</code> attribute. This may be
	 * <code>null</code> if the visitor did not set it.
	 * 
	 * @return the value of the <code>actor</code> attribute
	 */
	final public Actor getActor() {
		return actor;
	}

	@Override
	public String toString() {
		String retString = "Base : ";
		retString += this.getClass().getName();

		retString += " / irVisitor : ";
		if (irVisitor == null) {
			retString += "null";
		} else {
			retString += irVisitor.getClass().getName();
		}

		return retString;
	}

}
