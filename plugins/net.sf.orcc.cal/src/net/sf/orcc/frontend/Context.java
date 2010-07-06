/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.frontend;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.util.Scope;

/**
 * This class defines a context used by the AST transformer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Context {

	/**
	 * A map from global variables to local variables
	 */
	private Map<Variable, LocalVariable> mapGlobals;

	/**
	 * A map from AST variables to IR variables.
	 */
	private Scope<AstVariable, Variable> mapVariables;

	/**
	 * Contains the current procedures where local variables or nodes should be
	 * added by the expression transformer or statement transformer.
	 */
	private Procedure procedure;

	private Set<Variable> setGlobalsToLoad;

	private Set<Variable> setGlobalsToStore;

	/**
	 * Creates a new empty context.
	 */
	public Context() {
		mapVariables = new Scope<AstVariable, Variable>();
		mapGlobals = new HashMap<Variable, LocalVariable>();
		setGlobalsToLoad = new LinkedHashSet<Variable>();
		setGlobalsToStore = new LinkedHashSet<Variable>();
	}

	/**
	 * Creates a new context with the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public Context(Context context, Procedure procedure) {
		this.procedure = procedure;

		mapGlobals = new HashMap<Variable, LocalVariable>();
		mapVariables = new Scope<AstVariable, Variable>(context
				.getMapVariables().getParent(), true);
		setGlobalsToLoad = new LinkedHashSet<Variable>();
		setGlobalsToStore = new LinkedHashSet<Variable>();
	}

	/**
	 * Clears this context.
	 */
	public void clear() {
		mapGlobals.clear();
		mapVariables.clear();
		procedure = null;
		setGlobalsToLoad.clear();
		setGlobalsToStore.clear();
	}

	/**
	 * Returns the mapGlobals field.
	 * 
	 * @return the mapGlobals field
	 */
	public Map<Variable, LocalVariable> getMapGlobals() {
		return mapGlobals;
	}

	/**
	 * Returns the mapVariables field.
	 * 
	 * @return the mapVariables field
	 */
	public Scope<AstVariable, Variable> getMapVariables() {
		return mapVariables;
	}

	/**
	 * Returns the procedure field.
	 * 
	 * @return the procedure field
	 */
	public Procedure getProcedure() {
		return procedure;
	}

	/**
	 * Returns the setGlobalsToLoad field.
	 * 
	 * @return the setGlobalsToLoad field
	 */
	public Set<Variable> getSetGlobalsToLoad() {
		return setGlobalsToLoad;
	}

	/**
	 * Returns the setGlobalsToStore field.
	 * 
	 * @return the setGlobalsToStore field
	 */
	public Set<Variable> getSetGlobalsToStore() {
		return setGlobalsToStore;
	}

	/**
	 * Returns the IR variable associated with the given AST variable.
	 * 
	 * @param variable
	 *            an AST variable
	 * @return the IR variable associated with the given AST variable
	 */
	public Variable getVariable(AstVariable variable) {
		return mapVariables.get(variable);
	}

	/**
	 * Creates a new scope for mapVariables.
	 */
	public void newScope() {
		mapVariables = new Scope<AstVariable, Variable>(mapVariables, true);
	}

	/**
	 * Associates an IR variable with the given AST variable.
	 * 
	 * @param astVariable
	 *            an AST variable
	 * @param variable
	 *            an IR variable associated with the given AST variable
	 */
	public void putVariable(AstVariable astVariable, Variable variable) {
		mapVariables.put(astVariable, variable);
	}

}
