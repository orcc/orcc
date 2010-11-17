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
package net.sf.orcc.backends.vhdl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * This class defines a map that returns, for each action, the variables that
 * are loaded by its scheduler procedure.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class VHDLTemplateData extends AbstractActorTransformation {

	private Set<String> strings;

	public VHDLTemplateData() {
		strings = new HashSet<String>();
	}

	/**
	 * Returns the list of variables.
	 * 
	 * @return the list of variables
	 */
	public List<String> getVariablesList() {
		return new ArrayList<String>(strings);
	}

	@Override
	public void transform(Actor actor) {
		for (Action action : actor.getActions()) {
			visit(action.getScheduler());
		}
	}

	@Override
	public void visit(Load node) {
		Variable var = node.getSource().getVariable();
		if (!var.isPort() && !var.getType().isList() && var.isAssignable()) {
			strings.add(var.getName());
		}
	}

	@Override
	public void visit(Peek node) {
		Variable port = node.getPort();
		String name = port.getName() + "_data";
		strings.add(name);
	}

}
