/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.df.transformations;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines a transformation that instantiates actors and networks in
 * a network.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Instantiator extends DfSwitch<Void> {

	@Override
	public Void caseNetwork(Network network) {
		Copier copier = new Copier();
		for (Instance instance : network.getInstances()) {
			Entity entity = instance.getEntity();
			if (entity.isNetwork()) {
				Network subNetwork = instance.getNetwork();
				new Instantiator().doSwitch(subNetwork);
			}

			// copy entity
			Entity copy = (Entity) copier.copy(entity);
			copier.copyReferences();

			// set name, attributes, arguments
			copy.setName(instance.getName());
			copy.getAttributes().addAll(
					copier.copyAll(instance.getAttributes()));
			for (Argument argument : instance.getArguments()) {
				Var var = (Var) copier.get(argument.getVariable());
				Expression value = IrUtil.copy(argument.getValue());

				var.setInitialValue(value);
			}

			// add to entities
			network.getEntities().add(copy);
		}

		// copy connections
		List<Connection> connections = new ArrayList<Connection>(
				network.getConnections());
		for (Connection connection : connections) {
			Vertex sourceCopy = (Vertex) copier.get(connection.getSource());
			Vertex targetCopy = (Vertex) copier.get(connection.getTarget());
			Connection copy = DfFactory.eINSTANCE.createConnection(sourceCopy,
					connection.getSourcePort(), targetCopy,
					connection.getTargetPort(),
					copier.copyAll(connection.getAttributes()));
			network.getConnections().add(copy);
		}

		// remove all instances (automatically removes connections too)
		network.getInstances().clear();

		return null;
	}

}
