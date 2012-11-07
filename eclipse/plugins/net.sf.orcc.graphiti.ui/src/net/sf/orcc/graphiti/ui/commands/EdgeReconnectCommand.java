/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.commands;

import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.commands.refinement.PortChooser;

import org.eclipse.gef.commands.Command;

/**
 * This class provides a Command that reconnects a dependency. Reconnection is a
 * bit trickier than creation, since we must remember the previous dependency.
 * We inherit from EdgeCreateCommand so we just need to store the previous
 * dependency and parent graph, while keeping most of the original behavior.
 * 
 * @author Matthieu Wipliez
 */
public class EdgeReconnectCommand extends Command {

	/**
	 * The edge is stored as an attribute so it can be used both in the
	 * <code>execute</code> and <code>undo</code> methods.
	 */
	private Edge edge;

	/**
	 * The parentGraph is stored as an attribute so it can be used both in the
	 * <code>execute</code> and <code>undo</code> methods.
	 */
	private Graph parentGraph;

	private Edge previousEdge;

	private Vertex source;

	private Vertex target;

	@Override
	public void execute() {
		// Disconnect
		parentGraph = source.getParent();
		parentGraph.removeEdge(previousEdge);

		// Clone edge and assign ports
		edge = new Edge(previousEdge);

		if (edge.getSource() != source) {
			edge.setSource(source);

			String connection = edge.getSource().getValue(
					ObjectType.PARAMETER_ID)
					+ " - "
					+ edge.getTarget().getValue(ObjectType.PARAMETER_ID);
			PortChooser portChooser = new PortChooser(connection);
			if (edge.getParameter(ObjectType.PARAMETER_SOURCE_PORT) != null) {
				edge.setValue(ObjectType.PARAMETER_SOURCE_PORT,
						portChooser.getSourcePort(source));
			}
		} else if (edge.getTarget() != target) {
			edge.setTarget(target);

			String connection = edge.getSource().getValue(
					ObjectType.PARAMETER_ID)
					+ " - "
					+ edge.getTarget().getValue(ObjectType.PARAMETER_ID);
			PortChooser portChooser = new PortChooser(connection);
			if (edge.getParameter(ObjectType.PARAMETER_TARGET_PORT) != null) {
				edge.setValue(ObjectType.PARAMETER_TARGET_PORT,
						portChooser.getTargetPort(target));
			}
		}

		parentGraph.addEdge(edge);
	}

	/**
	 * Sets the original dependency (before it is reconnected).
	 * 
	 * @param edge
	 *            The edge.
	 */
	public void setOriginalEdge(Edge edge) {
		this.previousEdge = edge;

		// We also set these because we do not know which one will be set by the
		// EdgeGraphicalNodeEditPolicy (ie if getReconnectSourceCommand or
		// getReconnectTargetCommand is called)
		source = edge.getSource();
		target = edge.getTarget();
	}

	/**
	 * Sets the source of the dependency to create/reconnect.
	 * 
	 * @param source
	 *            The dependency source as a Port.
	 */
	public void setSource(Vertex source) {
		this.source = source;
	}

	/**
	 * Sets the target of the dependency to create/reconnect.
	 * 
	 * @param target
	 *            The dependency target as a Port.
	 */
	public void setTarget(Vertex target) {
		this.target = target;
	}

	@Override
	public void undo() {
		parentGraph.removeEdge(edge);
		parentGraph.addEdge(previousEdge);
	}
}