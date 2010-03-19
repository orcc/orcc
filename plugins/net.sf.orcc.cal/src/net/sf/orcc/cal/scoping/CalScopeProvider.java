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
import java.util.Collections;
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
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleScope;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
public class CalScopeProvider extends AbstractDeclarativeScopeProvider {

	/**
	 * Returns the scope for a variable referenced inside an action. An action
	 * is a bit different because it has its input patterns in addition to its
	 * local variables.
	 * 
	 * @param action
	 *            an action
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	private IScope getScope(Action action, EReference ref) {
		List<IEObjectDescription> elements = new ArrayList<IEObjectDescription>();
		for (InputPattern pattern : action.getInputs()) {
			for (Variable token : pattern.getTokens()) {
				IEObjectDescription element = EObjectDescription.create(token.getName(),
						token);
				elements.add(element);
			}
		}

		for (Variable variable : action.getVariables()) {
			IEObjectDescription element = EObjectDescription.create(variable.getName(),
					variable);
			elements.add(element);
		}

		IScope outer = getScope(action.eContainer(), ref);
		IScope scope = new SimpleScope(outer, elements);
		return scope;
	}

	/**
	 * Returns the scope for a variable referenced inside a function/procedure.
	 * 
	 * @param parameters
	 *            a list of parameters
	 * @param variables
	 *            a list of variables
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	private IScope getScope(List<Variable> parameters,
			List<Variable> variables, EObject obj, EReference ref) {
		List<IEObjectDescription> elements = new ArrayList<IEObjectDescription>();
		for (Variable variable : parameters) {
			IEObjectDescription element = EObjectDescription.create(variable.getName(),
					variable);
			elements.add(element);
		}

		for (Variable variable : variables) {
			IEObjectDescription element = EObjectDescription.create(variable.getName(),
					variable);
			elements.add(element);
		}

		IScope outer = getScope(obj.eContainer(), ref);
		IScope scope = new SimpleScope(outer, elements);
		return scope;
	}

	/**
	 * Returns the scope for a variable referenced inside an action.
	 * 
	 * @param action
	 *            an action
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Action action, EReference ref) {
		return getScope(action, ref);
	}

	/**
	 * Returns the scope for a variable referenced inside an actor.
	 * 
	 * @param actor
	 *            an actor
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Actor actor, EReference ref) {
		List<IEObjectDescription> elements = new ArrayList<IEObjectDescription>();
		for (Variable parameter : actor.getParameters()) {
			IEObjectDescription element = EObjectDescription.create(parameter.getName(),
					parameter);
			elements.add(element);
		}

		for (Variable stateVariable : actor.getStateVariables()) {
			IEObjectDescription element = EObjectDescription.create(stateVariable
					.getName(), stateVariable);
			elements.add(element);
		}

		IScope scope = new SimpleScope(elements);
		return scope;
	}

	/**
	 * Returns the scope for a variable referenced inside a foreach statement.
	 * 
	 * @param foreach
	 *            a foreach statement
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	@SuppressWarnings("unchecked")
	public IScope scope_VariableReference_variable(ForeachStatement foreach,
			EReference ref) {
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(foreach.getVariable());
		return getScope(variables, Collections.EMPTY_LIST, foreach, ref);
	}

	/**
	 * Returns the scope for a variable referenced inside a function.
	 * 
	 * @param func
	 *            a function
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Function func, EReference ref) {
		return getScope(func.getParameters(), func.getVariables(), func, ref);
	}

	/**
	 * Returns the scope for a variable referenced inside a generator.
	 * 
	 * @param list
	 *            a list expression
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	@SuppressWarnings("unchecked")
	public IScope scope_VariableReference_variable(ListExpression list,
			EReference ref) {
		List<Variable> variables = new ArrayList<Variable>();
		for (Generator generator : list.getGenerators()) {
			variables.add(generator.getVariable());
		}
		return getScope(variables, Collections.EMPTY_LIST, list, ref);
	}

	/**
	 * Returns the scope for a variable referenced inside a procedure.
	 * 
	 * @param proc
	 *            a procedure
	 * @param ref
	 *            unknown!
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Procedure proc,
			EReference ref) {
		return getScope(proc.getParameters(), proc.getVariables(), proc, ref);
	}

}
