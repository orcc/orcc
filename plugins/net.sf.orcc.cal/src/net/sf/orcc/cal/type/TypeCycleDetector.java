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
package net.sf.orcc.cal.type;

import java.util.Set;

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.util.VoidSwitch;
import net.sf.orcc.cal.validation.CalJavaValidator;

import org.eclipse.emf.ecore.EObject;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * This class defines a cycle detector for type definitions.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeCycleDetector extends VoidSwitch {

	private DirectedGraph<AstVariable, DefaultEdge> graph;

	private AstVariable source;

	private CalJavaValidator validator;

	/**
	 * Creates a new type cycle detector.
	 */
	public TypeCycleDetector(CalJavaValidator validator) {
		this.validator = validator;
	}

	@Override
	public Void caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		if (source != null && variable != null) {
			if (!graph.containsVertex(variable)) {
				graph.addVertex(variable);
			}
			graph.addEdge(source, variable);
		}

		return null;
	}

	@Override
	public Void caseAstVariable(AstVariable variable) {
		source = variable;
		graph.addVertex(variable);
		doSwitch(variable.getType());
		source = null;
		return null;
	}

	/**
	 * Returns <code>true</code> if the actor has cycles in its type
	 * definitions.
	 * 
	 * @param actor
	 *            an actor
	 * @return <code>true</code> if the actor has cycles in its type definitions
	 */
	public boolean detectCycles(AstActor actor) {
		graph = new DefaultDirectedGraph<AstVariable, DefaultEdge>(
				DefaultEdge.class);
		doSwitch(actor);

		CycleDetector<AstVariable, DefaultEdge> cycleDetector = new CycleDetector<AstVariable, DefaultEdge>(
				graph);
		boolean hasCycles = cycleDetector.detectCycles();
		if (hasCycles) {
			Set<AstVariable> variables = cycleDetector.findCycles();
			if (!variables.isEmpty()) {
				for (AstVariable variable : variables) {
					error(variable.getName() + " has a cyclic type definition",
							variable, CalPackage.AST_VARIABLE);
				}
			}
		}

		graph = null;
		return hasCycles;
	}

	private void error(String string, EObject source, int feature) {
		if (validator != null) {
			validator.error(string, source, feature);
		}
	}

}
