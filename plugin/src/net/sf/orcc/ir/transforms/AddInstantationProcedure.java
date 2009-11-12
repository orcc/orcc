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

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.IActorTransformation;
import net.sf.orcc.ir.INode;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.InitPortNode;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;

/**
 * Adds instantiation procedure.
 * 
 * @author Jérôme Gorin
 * 
 */
public class AddInstantationProcedure implements IActorTransformation {

	private String actorName;

	private String file;

	private Procedure createInitProcedure(String Attributs,
			OrderedMap<Port> ports) throws OrccException {
		OrderedMap<Variable> parameters = new OrderedMap<Variable>();
		OrderedMap<Variable> locals = new OrderedMap<Variable>();
		Location location = new Location();

		BlockNode block = new BlockNode(location);
		List<INode> nodes = new ArrayList<INode>();
		nodes.add(block);

		LocalVariable parameter = new LocalVariable(false, 0, location, "fifo",
				null, null, new VoidType());

		parameters.add(file, location, "fifo", parameter);
		for (Port port : ports) {
			Use varUse = new Use(parameter);
			VarExpr expr = new VarExpr(new Location(), varUse);

			InitPortNode node = new InitPortNode(null, new Location(), port
					.getName(), 0, expr);

			block.add(node);
		}

		return new Procedure(actorName + "_init" + Attributs, false,
				new Location(), new VoidType(), parameters, locals, nodes);
	}

	@Override
	public void transform(Actor actor) throws OrccException {
		this.actorName = actor.getName();
		this.file = actor.getFile();

		List<Procedure> instantiations = new ArrayList<Procedure>();
		Procedure inputInit = createInitProcedure("Input", actor.getInputs());
		instantiations.add(inputInit);
		Procedure inputOutput = createInitProcedure("Output", actor
				.getOutputs());
		instantiations.add(inputOutput);

		actor.setInstantations(instantiations);
	}

}
