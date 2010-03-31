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
package net.sf.orcc.cal.linking;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cal.cal.Actor;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Procedure;
import net.sf.orcc.cal.cal.Schedule;
import net.sf.orcc.cal.cal.State;
import net.sf.orcc.cal.cal.Transition;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.parsetree.AbstractNode;

public class CalLinkingService extends DefaultLinkingService {

	private Map<String, Function> functions;

	private Map<String, Procedure> procedures;

	/**
	 * Creates a new CAL linking service which creates builtin functions.
	 */
	public CalLinkingService() {
		functions = new HashMap<String, Function>();

		Function function;
		function = CalFactory.eINSTANCE.createFunction();
		function.setName("bitand");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createFunction();
		function.setName("bitnot");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createFunction();
		function.setName("bitor");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createFunction();
		function.setName("bitxor");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createFunction();
		function.setName("lshift");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createFunction();
		function.setName("rshift");
		functions.put(function.getName(), function);

		procedures = new HashMap<String, Procedure>();

		Procedure procedure;
		procedure = CalFactory.eINSTANCE.createProcedure();
		procedure.setName("println");
		procedures.put(procedure.getName(), procedure);
	}

	/**
	 * Returns a singleton if <code>name</code> is a builtin function, and an
	 * empty list otherwise.
	 * 
	 * @param context
	 *            the context in which a function is referenced.
	 * @param name
	 *            function name
	 * @return a list
	 */
	private List<EObject> builtinFunction(EObject context, String name) {
		Function function = getFunction(name);
		if (function != null) {
			Actor actor = getActor(context);
			actor.getFunctions().add(function);
			return Collections.singletonList((EObject) function);
		}

		return Collections.emptyList();
	}

	/**
	 * Returns a singleton if <code>name</code> is a builtin procedure, and an
	 * empty list otherwise.
	 * 
	 * @param context
	 *            the context in which a procedure is referenced.
	 * @param name
	 *            procedure name
	 * @return a list
	 */
	private List<EObject> builtinProcedure(EObject context, String name) {
		Procedure procedure = getProcedure(name);
		if (procedure != null) {
			Actor actor = getActor(context);
			actor.getProcedures().add(procedure);
			return Collections.singletonList((EObject) procedure);
		}

		return Collections.emptyList();
	}

	/**
	 * Returns the actor in which <code>context</code> occurs.
	 * 
	 * @param context
	 *            an object
	 * @return an actor
	 */
	private Actor getActor(EObject context) {
		EObject container = context.eContainer();
		if (container == null) {
			return (Actor) context;
		} else {
			return getActor(container);
		}
	}

	private Function getFunction(String name) {
		return functions.get(name);
	}

	public List<EObject> getLinkedObjects(EObject context, EReference ref,
			AbstractNode node) {
		List<EObject> result = super.getLinkedObjects(context, ref, node);
		if (result != null && !result.isEmpty()) {
			return result;
		}

		final EClass requiredType = ref.getEReferenceType();
		final String s = getCrossRefNodeAsString(node);
		if (requiredType != null && s != null) {
			if (CalPackage.Literals.FUNCTION.isSuperTypeOf(requiredType)) {
				return builtinFunction(context, s);
			} else if (CalPackage.Literals.PROCEDURE
					.isSuperTypeOf(requiredType)) {
				return builtinProcedure(context, s);
			} else if (CalPackage.Literals.STATE.isSuperTypeOf(requiredType)) {
				return getState(context, ref, s);
			}
		}

		return Collections.emptyList();
	}

	private Procedure getProcedure(String name) {
		return procedures.get(name);
	}

	private List<EObject> getState(EObject context, EReference reference,
			String name) {
		if (reference.getName().equals("initialState")) {
			Schedule schedule = (Schedule) context;
			State state = getState(schedule, name);
			// schedule.setInitialState(state);

			return Collections.singletonList((EObject) state);
		} else if (reference.getName().equals("source")) {
			Transition transition = (Transition) context;
			Schedule schedule = (Schedule) transition.eContainer();
			State state = getState(schedule, name);

			return Collections.singletonList((EObject) state);
		} else if (reference.getName().equals("target")) {
			Transition transition = (Transition) context;
			Schedule schedule = (Schedule) transition.eContainer();
			State state = getState(schedule, name);

			return Collections.singletonList((EObject) state);
		}

		return Collections.emptyList();
	}

	public State getState(Schedule schedule, String name) {
		State state = CalFactory.eINSTANCE.createState();
		state.setName(name);
		schedule.getStates().add(state);
		return state;
	}

}
