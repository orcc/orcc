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

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstSchedule;
import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.parsetree.AbstractNode;

public class CalLinkingService extends DefaultLinkingService {

	/**
	 * Keep stubs so that new ones aren't created for each linking pass.
	 */
	private final Map<String, AstState> stubbedRefs;

	private Resource stubsResource = null;

	private Map<String, AstFunction> functions;

	private Map<String, AstProcedure> procedures;

	/**
	 * Creates a new CAL linking service which creates builtin functions.
	 */
	public CalLinkingService() {
		stubbedRefs = new HashMap<String, AstState>();

		functions = new HashMap<String, AstFunction>();

		AstFunction function;
		function = CalFactory.eINSTANCE.createAstFunction();
		function.setName("bitand");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createAstFunction();
		function.setName("bitnot");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createAstFunction();
		function.setName("bitor");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createAstFunction();
		function.setName("bitxor");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createAstFunction();
		function.setName("lshift");
		functions.put(function.getName(), function);

		function = CalFactory.eINSTANCE.createAstFunction();
		function.setName("rshift");
		functions.put(function.getName(), function);

		procedures = new HashMap<String, AstProcedure>();

		AstProcedure procedure;
		procedure = CalFactory.eINSTANCE.createAstProcedure();
		procedure.setName("println");
		procedures.put(procedure.getName(), procedure);
	}

	/**
	 * Use a temporary 'child' resource to hold created stubs. The real resource
	 * URI is used to generate a 'temporary' resource to be the container for
	 * stub EObjects.
	 * 
	 * @param source
	 *            the real resource that is being parsed
	 * @return the cached reference to a resource named by the real resource
	 *         with the added extension 'xmi'
	 */
	private Resource makeResource(Resource source) {
		if (null != stubsResource)
			return stubsResource;
		URI stubURI = source.getURI();
		stubURI = stubURI.appendFileExtension("xmi");
		stubsResource = source.getResourceSet().getResource(stubURI, false);
		if (null == stubsResource) {
			// TODO find out if this should be cleaned up so as not to clutter
			// the project.
			source.getResourceSet().createResource(stubURI);
			stubsResource = source.getResourceSet().getResource(stubURI, false);
		}
		return stubsResource;
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
		AstFunction function = getFunction(name);
		if (function != null) {
			AstActor actor = getActor(context);
			actor.getBuiltinFunctions().add(function);
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
		AstProcedure procedure = getProcedure(name);
		if (procedure != null) {
			AstActor actor = getActor(context);
			actor.getBuiltinProcedures().add(procedure);
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
	private AstActor getActor(EObject context) {
		EObject container = context.eContainer();
		if (container == null) {
			return (AstActor) context;
		} else {
			return getActor(container);
		}
	}

	private AstFunction getFunction(String name) {
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
			if (CalPackage.Literals.AST_FUNCTION.isSuperTypeOf(requiredType)) {
				return builtinFunction(context, s);
			} else if (CalPackage.Literals.AST_PROCEDURE
					.isSuperTypeOf(requiredType)) {
				return builtinProcedure(context, s);
			} else if (CalPackage.Literals.AST_STATE
					.isSuperTypeOf(requiredType)) {
				return getState(context, ref, s);
			}
		}

		return Collections.emptyList();
	}

	private AstProcedure getProcedure(String name) {
		return procedures.get(name);
	}

	private List<EObject> getState(EObject context, EReference reference,
			String name) {
		AstActor actor;
		if (reference.getName().equals("initialState")) {
			AstSchedule schedule = (AstSchedule) context;
			actor = (AstActor) schedule.eContainer();
		} else if (reference.getName().equals("source")) {
			AstTransition transition = (AstTransition) context;
			AstSchedule schedule = (AstSchedule) transition.eContainer();
			actor = (AstActor) schedule.eContainer();
		} else if (reference.getName().equals("target")) {
			AstTransition transition = (AstTransition) context;
			AstSchedule schedule = (AstSchedule) transition.eContainer();
			actor = (AstActor) schedule.eContainer();
		} else {
			return Collections.emptyList();
		}

		AstState stub = stubbedRefs.get(name);
		if (null == stub) {
			// Create the model element instance using the factory
			stub = CalFactory.eINSTANCE.createAstState();
			stub.setName(name);
			// Attach the stub to the resource that's being parsed
			Resource res = makeResource(actor.eResource());
			res.getContents().add(stub);
		}
		return Collections.singletonList((EObject) stub);
	}

	public AstState getState(AstSchedule schedule, String name) {
		AstState state = CalFactory.eINSTANCE.createAstState();
		state.setName(name);
		schedule.getStates().add(state);
		return state;
	}

}
