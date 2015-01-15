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
package net.sf.orcc.ir.transform;

import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a transformation that transforms variable and procedure
 * names according to a transformation map or a regular expression. This can be
 * useful to transform names that are forbidden in a target language.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RenameTransformation extends DfVisitor<Object> {

	private class IrVisitor extends AbstractIrVisitor<Object> {

		@Override
		public Object caseProcedure(Procedure procedure) {
			String name = procedure.getName();
			if (transformations.containsKey(name)) {
				procedure.setName(transformations.get(name));
			}

			checkParameters(procedure.getParameters());
			checkVariables(procedure.getLocals());
			return null;
		}

	}

	private final Map<String, String> transformations;
	
	private Boolean toLowerCase = true;
	
	/**
	 * Creates a transformation that uses a replacement map.
	 * 
	 * @param transformations
	 *            a map from string to string
	 */
	public RenameTransformation(Map<String, String> transformations) {
		this.transformations = transformations;

		irVisitor = new IrVisitor();
	}
	
	public RenameTransformation(Map<String, String> transformations, Boolean toLowerCase) {
		this(transformations);
		this.toLowerCase = toLowerCase;
	}


	@Override
	public Object caseActor(Actor actor) {
		checkVariables(actor.getParameters());
		checkVariables(actor.getStateVars());
		checkPorts(actor.getInputs());
		checkPorts(actor.getOutputs());
		return super.caseActor(actor);
	}

	@Override
	public Object caseNetwork(Network network) {
		checkPorts(network.getInputs());
		checkPorts(network.getOutputs());

		return super.caseNetwork(network);
	}

	private void checkPorts(EList<Port> inputs) {
		for (Port port : inputs) {
			checkport(port);
		}
	}

	private void checkport(Port port) {
		String name = toLowerCase ? port.getName().toLowerCase() : port.getName();
		if (transformations.containsKey(name)) {
			port.setName(transformations.get(name));
		}
	}

	private void checkParameters(List<Param> parameters) {
		for (Param param : parameters) {
			checkVariable(param.getVariable());
		}
	}

	private void checkVariable(Var var) {
		String name = var.getName();
		if (transformations.containsKey(name)) {
			var.setName(transformations.get(name));
		}
	}

	private void checkVariables(List<Var> variables) {
		for (Var var : variables) {
			checkVariable(var);
		}
	}

}
