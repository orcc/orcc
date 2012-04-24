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

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

import org.eclipse.emf.ecore.EObject;

/**
 * This class implements a no-op visitor on an actor. This class should be
 * extended by classes that implement actor visitors and transformations.
 * 
 * @author Matthieu Wipliez
 * @since 1.2
 */
public class DfVisitor<T> extends DfSwitch<T> {

	protected Actor actor;

	protected AbstractIrVisitor<T> irVisitor;
	
	public DfVisitor() {
	}

	/**
	 * Creates a new abstract actor visitor that visits all nodes and
	 * instructions of all procedures (including the ones referenced by
	 * actions).
	 */
	public DfVisitor(AbstractIrVisitor<T> irVisitor) {
		this.irVisitor = irVisitor;
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
	public T casePattern(Pattern pattern) {
		return null;
	}

	@Override
	public T defaultCase(EObject eObject) {
		if (irVisitor.isSwitchFor(eObject.eClass().getEPackage())) {
			return irVisitor.doSwitch(eObject);
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

}
