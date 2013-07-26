/*
 * Copyright (c) 2010, EPFL
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
 *   * Neither the name of the EPFL nor the names of its contributors may be used 
 *     to endorse or promote products derived from this software without specific 
 *     prior written permission.
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
package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines generic transformations that are necessary for an
 * actor merger.
 * 
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * @author Jani Boutellier
 * 
 */
public class ActorMergerBase extends DfSwitch<Actor> {

	protected static final DfFactory dfFactory = DfFactory.eINSTANCE;
	protected static final IrFactory irFactory = IrFactory.eINSTANCE;

	protected Map<Port, Var> buffersMap = new HashMap<Port, Var>();
	protected Copier copier;

	protected Actor superActor;

	protected void createBuffers(Procedure body, Map<Connection, Integer> maxTokens) {

		BlockBasic block = body.getLast();

		int index = 0;
		// Create buffers and counters for inner connections
		for (Connection conn : maxTokens.keySet()) {

			// create inner buffer
			int size = maxTokens.get(conn);
			if (size > 0) {
				String name = "buffer_" + index++;
				Type eltType = conn.getSourcePort().getType();
				Type type = irFactory.createTypeList(size, eltType);
				Var buffer = body.newTempLocalVariable(type, name);
	
				// create write counter
				Var writeIdx = body.newTempLocalVariable(
						irFactory.createTypeInt(32), name + "_w");
				block.add(irFactory.createInstAssign(writeIdx, irFactory.createExprInt(0)));
	
				// create read counter
				Var readIdx = body.newTempLocalVariable(
						irFactory.createTypeInt(32), name + "_r");
				block.add(irFactory.createInstAssign(readIdx, irFactory.createExprInt(0)));
	
				buffersMap.put(conn.getSourcePort(), buffer);
				buffersMap.put(conn.getTargetPort(), buffer);
			}
		}
	}
		
	protected void copyVariables(Network network) {
		// Move variables
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			for (Var var : new ArrayList<Var>(actor.getStateVars())) {
				superActor.addStateVar(var);
			}
			for (Var param : new ArrayList<Var>(actor.getParameters())) {
				superActor.addStateVar(param);
			}
		}
	}

	protected void copyProcedures(Network network) {
		// Move procedures
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			for (Procedure proc : new ArrayList<Procedure>(actor.getProcs())) {
				if (!proc.isNative()) {
					proc.setName(actor.getName() + "_" + proc.getName());
				}
				superActor.getProcs().add(proc);
			}
		}
	}
	
}
