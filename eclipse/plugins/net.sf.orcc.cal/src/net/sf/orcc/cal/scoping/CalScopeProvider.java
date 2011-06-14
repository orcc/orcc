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

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstExpressionList;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;

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
	 * Returns the scope of functions within an actor.
	 * 
	 * @param actor
	 *            an actor
	 * @param reference
	 *            a reference
	 * @return a scope
	 */
	public IScope scope_AstFunction(AstActor actor, EReference reference) {
		return Scopes.scopeFor(actor.getFunctions(),
				delegateGetScope(actor, reference));
	}

	/**
	 * Returns the scope of functions within a unit.
	 * 
	 * @param unit
	 *            a unit
	 * @param reference
	 *            a reference
	 * @return a scope
	 */
	public IScope scope_AstFunction(AstUnit unit, EReference reference) {
		return Scopes.scopeFor(unit.getFunctions(),
				delegateGetScope(unit, reference));
	}

	/**
	 * Returns the scope for an input pattern.
	 * 
	 * @param action
	 *            an action
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_AstInputPattern_port(AstAction action,
			EReference reference) {
		AstActor actor = (AstActor) action.eContainer();
		return Scopes.scopeFor(actor.getInputs(), getScope(actor, reference));
	}

	/**
	 * Returns the scope for an output pattern.
	 * 
	 * @param action
	 *            an action
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_AstOutputPattern_port(AstAction action,
			EReference reference) {
		AstActor actor = (AstActor) action.eContainer();
		return Scopes.scopeFor(actor.getOutputs(), getScope(actor, reference));
	}

	/**
	 * Returns the scope of procedures within an actor.
	 * 
	 * @param actor
	 *            an actor
	 * @param reference
	 *            a reference
	 * @return a scope
	 */
	public IScope scope_AstProcedure(AstActor actor, EReference reference) {
		return Scopes.scopeFor(actor.getProcedures(),
				delegateGetScope(actor, reference));
	}

	/**
	 * Returns the scope for a variable referenced inside an action.
	 * 
	 * @param action
	 *            an action
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_AstVariableReference_variable(AstAction action,
			EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		for (AstInputPattern pattern : action.getInputs()) {
			elements.addAll(pattern.getTokens());
		}
		elements.addAll(action.getVariables());

		AstActor actor = (AstActor) action.eContainer();
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
	public IScope scope_AstVariableReference_variable(AstActor actor,
			EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		elements.addAll(actor.getParameters());
		elements.addAll(actor.getStateVariables());

		return Scopes.scopeFor(elements, delegateGetScope(actor, reference));
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
	public IScope scope_AstVariableReference_variable(AstExpressionList list,
			EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		for (AstGenerator generator : list.getGenerators()) {
			elements.add(generator.getVariable());
		}
		EObject container = list.eContainer();
		return Scopes.scopeFor(elements, getScope(container, reference));
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
	public IScope scope_AstVariableReference_variable(AstFunction function,
			EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		elements.addAll(function.getParameters());
		elements.addAll(function.getVariables());

		EObject cter = function.eContainer();
		return Scopes.scopeFor(elements, getScope(cter, reference));
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
	public IScope scope_AstVariableReference_variable(AstProcedure procedure,
			EReference reference) {
		List<AstVariable> elements = new ArrayList<AstVariable>();
		elements.addAll(procedure.getParameters());
		elements.addAll(procedure.getVariables());

		AstActor actor = (AstActor) procedure.eContainer();
		return Scopes.scopeFor(elements, getScope(actor, reference));
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
	public IScope scope_AstVariableReference_variable(
			AstStatementForeach foreach, EReference reference) {
		List<AstVariable> variables = new ArrayList<AstVariable>();
		variables.add(foreach.getVariable());
		return Scopes.scopeFor(variables,
				getScope(foreach.eContainer(), reference));
	}

}
