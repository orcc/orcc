/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.ir.transforms;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.common.Location;
import net.sf.orcc.common.Port;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.InitPortNode;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;

/**
 * Adds control flow.
 * 
 * @author Jérôme GORIN
 * 
 */
public class AddInstantationProcedure {

	private String actorName;

	public AddInstantationProcedure(Actor actor) {
		List<Procedure> instations = new ArrayList<Procedure>();
		this.actorName = actor.getName();
		Procedure inputInit = createInitProcedure("Input", actor.getInputs());
		Procedure inputOutput = createInitProcedure("Output", actor
				.getOutputs());
		instations.add(inputInit);
		instations.add(inputOutput);
		actor.setInstantations(instations);
	}

	private Procedure createInitProcedure(String Attributs,
			OrderedMap<Port> ports) {
		List<VarDef> parameters = new ArrayList<VarDef>();
		List<VarDef> locals = new ArrayList<VarDef>();
		List<AbstractNode> nodes = new ArrayList<AbstractNode>();

		VarDef parameter = new VarDef(false, false, 0, new Location(), "fifo",
				null, null, null, new VoidType());

		parameters.add(parameter);
		for (Port port : ports) {
			VarUse varuse = new VarUse(parameter, null);
			VarExpr expr = new VarExpr(new Location(), varuse);

			InitPortNode node = new InitPortNode(0, new Location(), port
					.getName(), 0, expr);

			nodes.add(node);
		}

		return new Procedure(actorName + "_init" + Attributs, false,
				new Location(), new VoidType(), parameters, locals, nodes);

	}

}
