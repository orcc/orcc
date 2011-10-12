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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExternalTarget;
import net.sf.orcc.cal.cal.Fsm;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.ScheduleFsm;
import net.sf.orcc.cal.cal.StatementForeach;
import net.sf.orcc.cal.cal.Variable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
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

	private void addState(Fsm fsm, Set<String> nameSet, List<INode> nodes) {
		ILeafNode leaf = (ILeafNode) nodes.get(0);
		String name = leaf.getText();
		if (!nameSet.contains(name)) {
			AstState state = CalFactory.eINSTANCE.createAstState();
			state.setName(name);
			state.setNode(leaf);

			fsm.getStates().add(state);
			nameSet.add(name);
		}
	}

	private void buildStates(Fsm fsm) {
		Set<String> nameSet = new HashSet<String>();
		// source states
		for (AstTransition transition : fsm.getTransitions()) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(transition,
					CalPackage.eINSTANCE.getAstTransition_Source());
			addState(fsm, nameSet, nodes);
		}
	}

	/**
	 * Returns the scope that contains all variables and parameters of the given
	 * action/function/procedure.
	 * 
	 * @param eObject
	 * @param parameters
	 * @param variables
	 * @param reference
	 * @return
	 */
	private IScope getScope(EObject eObject, List<Variable> parameters,
			List<Variable> variables, EReference reference) {
		IScope outer = getScope(eObject.eContainer(), reference);
		return Scopes.scopeFor(variables, Scopes.scopeFor(parameters, outer));
	}

	private IScope getScope(Fsm fsm) {
		if (fsm.getStates().isEmpty()) {
			buildStates(fsm);
		}
		return Scopes.scopeFor(fsm.getStates());
	}

	/**
	 * Returns a scope vi-1 -> ... -> v1 -> v0 -> parameters where vi is the
	 * given variable (the scope includes previous variables up to it).
	 * 
	 * @param variable
	 * @param parameters
	 * @param variables
	 * @param reference
	 * @return
	 */
	private IScope getScope(Variable variable, List<Variable> parameters,
			List<Variable> variables, EReference reference) {
		IScope outer = getScope(variable.eContainer().eContainer(), reference);
		IScope inner = Scopes.scopeFor(parameters, outer);
		for (Variable aVar : variables) {
			List<Variable> elements = new ArrayList<Variable>(1);
			if (aVar == variable) {
				break;
			}
			elements.add(aVar);
			inner = Scopes.scopeFor(elements, inner);
		}

		return inner;
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
	 * Returns the scope of procedures within a unit.
	 * 
	 * @param unit
	 *            a unit
	 * @param reference
	 *            a reference
	 * @return a scope
	 */
	public IScope scope_AstProcedure(AstUnit unit, EReference reference) {
		return Scopes.scopeFor(unit.getProcedures(),
				delegateGetScope(unit, reference));
	}

	/**
	 * Returns the scope for a source state referenced inside a transition.
	 * 
	 * @param fsm
	 *            a fsm
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_AstTransition_source(Fsm fsm, EReference reference) {
		return getScope(fsm);
	}

	/**
	 * Returns the scope for a source state referenced inside a transition.
	 * 
	 * @param fsm
	 *            a fsm
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_AstTransition_target(Fsm fsm, EReference reference) {
		return getScope(fsm);
	}

	public IScope scope_ExternalTarget_from(ExternalTarget target,
			EReference reference) {
		return getScope(target.getFsm().getContents());
	}

	public IScope scope_ExternalTarget_fsm(ExternalTarget target,
			EReference reference) {
		AstActor actor = EcoreUtil2.getContainerOfType(target, AstActor.class);
		return Scopes.scopeFor(actor.getLocalFsms());
	}

	public IScope scope_ExternalTarget_state(ExternalTarget target,
			EReference reference) {
		return getScope(target.getFsm().getContents());
	}

	public IScope scope_ExternalTarget_to(ExternalTarget target,
			EReference reference) {
		return getScope((Fsm) target.eContainer());
	}

	/**
	 * Returns the scope of functions within an actor.
	 * 
	 * @param actor
	 *            an actor
	 * @param reference
	 *            a reference
	 * @return a scope
	 */
	public IScope scope_Function(AstActor actor, EReference reference) {
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
	public IScope scope_Function(AstUnit unit, EReference reference) {
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
	public IScope scope_InputPattern_port(AstAction action, EReference reference) {
		AstActor actor = (AstActor) action.eContainer();
		return Scopes.scopeFor(actor.getInputs());
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
	public IScope scope_OutputPattern_port(AstAction action,
			EReference reference) {
		AstActor actor = (AstActor) action.eContainer();
		return Scopes.scopeFor(actor.getOutputs());
	}

	/**
	 * Returns the scope for a source state referenced inside a transition.
	 * 
	 * @param schedule
	 *            a schedule
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_ScheduleFsm_initialState(ScheduleFsm schedule,
			EReference reference) {
		return getScope(schedule.getContents());
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
	public IScope scope_VariableReference_variable(AstAction action,
			EReference reference) {
		List<Variable> tokens = new ArrayList<Variable>();
		for (InputPattern pattern : action.getInputs()) {
			tokens.addAll(pattern.getTokens());
		}
		return getScope(action, tokens, action.getVariables(), reference);
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
	public IScope scope_VariableReference_variable(AstActor actor,
			EReference reference) {
		List<Variable> elements = new ArrayList<Variable>();
		elements.addAll(actor.getParameters());
		elements.addAll(actor.getStateVariables());

		return Scopes.scopeFor(elements, delegateGetScope(actor, reference));
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
	public IScope scope_VariableReference_variable(AstProcedure procedure,
			EReference reference) {
		return getScope(procedure, procedure.getParameters(),
				procedure.getVariables(), reference);
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
	public IScope scope_VariableReference_variable(ExpressionList list,
			EReference reference) {
		List<Variable> elements = new ArrayList<Variable>();
		for (Generator generator : list.getGenerators()) {
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
	public IScope scope_VariableReference_variable(Function function,
			EReference reference) {
		return getScope(function, function.getParameters(),
				function.getVariables(), reference);
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
	public IScope scope_VariableReference_variable(StatementForeach foreach,
			EReference reference) {
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(foreach.getVariable());
		return Scopes.scopeFor(variables,
				getScope(foreach.eContainer(), reference));
	}

	/**
	 * Returns the scope for a variable referenced inside a variable
	 * declaration.
	 * 
	 * @param variable
	 *            a variable declaration
	 * @param reference
	 *            a variable reference
	 * @return a scope
	 */
	public IScope scope_VariableReference_variable(Variable variable,
			EReference reference) {
		EObject cter = variable.eContainer();
		List<Variable> parameters;
		List<Variable> variables;
		if (cter instanceof Function) {
			parameters = ((Function) cter).getParameters();
			variables = ((Function) cter).getVariables();
		} else if (cter instanceof AstProcedure) {
			parameters = ((AstProcedure) cter).getParameters();
			variables = ((AstProcedure) cter).getVariables();
		} else if (cter instanceof AstAction) {
			AstAction action = (AstAction) cter;
			parameters = new ArrayList<Variable>();
			for (InputPattern pattern : action.getInputs()) {
				parameters.addAll(pattern.getTokens());
			}
			variables = action.getVariables();
		} else {
			// in a state variable
			return getScope(variable.eContainer(), reference);
		}

		return getScope(variable, parameters, variables, reference);
	}

}
