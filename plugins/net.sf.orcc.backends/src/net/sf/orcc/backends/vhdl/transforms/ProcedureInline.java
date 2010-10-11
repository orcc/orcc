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
package net.sf.orcc.backends.vhdl.transforms;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class defines an actor transformation that inline the procedure
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class ProcedureInline extends AbstractActorTransformation {

	private ListIterator<CFGNode> nodeIt;

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Call call, Object... args) {
		nodeIt = (ListIterator<CFGNode>) args[0];
		// Test if it is a VHDL procedure (a procedure with a return of type
		// void)
		if (call.getProcedure().getReturnType().isVoid()) {
			// Set the procedure to external thus it will not be printed
			call.getProcedure().setExternal(true);
			List<CFGNode> nodes = call.getProcedure().getNodes();
			Iterator<CFGNode> it = nodes.iterator();

			// Prints the procedure variables in the current process
			List<Variable> listVars = call.getProcedure().getLocals().getList();
			for (Variable vars : listVars) {
				procedure.newTempLocalVariable("", vars.getType(),
						vars.getName());
			}

			// Remove the procedure call and prints the nodes
			nodeIt.remove();
			while (it.hasNext()) {
				nodeIt.add(it.next());
			}
		}
	}
}
