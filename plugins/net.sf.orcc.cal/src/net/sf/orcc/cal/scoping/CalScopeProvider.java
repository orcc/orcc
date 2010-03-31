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
package net.sf.orcc.cal.scoping;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.cal.cal.Action;
import net.sf.orcc.cal.cal.Actor;
import net.sf.orcc.cal.cal.ForeachStatement;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.ListExpression;
import net.sf.orcc.cal.cal.Procedure;
import net.sf.orcc.cal.cal.Variable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
public class CalScopeProvider extends AbstractDeclarativeScopeProvider {

	/**
	 * Returns the scope for a variable referenced inside an action.
	 * 
	 * @param action
	 *            an action
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Action action,
			EReference reference) {
		List<Variable> elements = new ArrayList<Variable>();
		for (InputPattern pattern : action.getInputs()) {
			elements.addAll(pattern.getTokens());
		}
		elements.addAll(action.getVariables());

		Actor actor = (Actor) action.eContainer();
		return Scopes.scopeFor(elements, getScope(actor, reference));
	}

	/**
	 * Returns the scope for a variable referenced inside an actor.
	 * 
	 * @param actor
	 *            an actor
	 * @param ref
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Actor actor,
			EReference reference) {
		List<Variable> elements = new ArrayList<Variable>();
		elements.addAll(actor.getStateVariables());
		return Scopes.scopeFor(elements);
	}

	/**
	 * Returns the scope for a variable referenced inside a foreach statement.
	 * 
	 * @param foreach
	 *            a foreach statement
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(ForeachStatement foreach,
			EReference reference) {
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(foreach.getVariable());
		return Scopes.scopeFor(variables, getScope(foreach.eContainer(),
				reference));
	}

	/**
	 * Returns the scope for a variable referenced inside a function.
	 * 
	 * @param function
	 *            a function
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Function function,
			EReference reference) {
		List<Variable> elements = new ArrayList<Variable>();
		elements.addAll(function.getParameters());
		elements.addAll(function.getVariables());

		Actor actor = (Actor) function.eContainer();
		return Scopes.scopeFor(elements, getScope(actor, reference));
	}

	/**
	 * Returns the scope for a variable referenced inside a generator.
	 * 
	 * @param list
	 *            a list expression
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(ListExpression list,
			EReference reference) {
		List<Variable> elements = new ArrayList<Variable>();
		for (Generator generator : list.getGenerators()) {
			elements.add(generator.getVariable());
		}
		EObject container = list.eContainer();
		return Scopes.scopeFor(elements, getScope(container, reference));
	}

	/**
	 * Returns the scope for a variable referenced inside a procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Procedure procedure,
			EReference reference) {
		List<Variable> elements = new ArrayList<Variable>();
		elements.addAll(procedure.getParameters());
		elements.addAll(procedure.getVariables());

		Actor actor = (Actor) procedure.eContainer();
		return Scopes.scopeFor(elements, getScope(actor, reference));
	}

}
