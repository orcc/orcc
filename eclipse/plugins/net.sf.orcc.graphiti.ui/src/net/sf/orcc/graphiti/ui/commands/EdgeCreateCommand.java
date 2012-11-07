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
 * This class provides a Command that creates a dependency. ComplexSource and
 * target are set when they are connected.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 */
public class EdgeCreateCommand extends Command {

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

	private Vertex source;

	private Vertex target;

	/**
	 * Creates a new command using the given newly created edge.
	 * 
	 * @param edge
	 *            The newly created edge.
	 */
	public EdgeCreateCommand(Edge edge) {
		this.edge = edge;
	}

	@Override
	public void execute() {
		// parent graph
		parentGraph = source.getParent();

		// edge has been set in the constructor.
		edge.setSource(source);
		edge.setTarget(target);

		String connection = source.getValue(ObjectType.PARAMETER_ID) + " - "
				+ target.getValue(ObjectType.PARAMETER_ID);

		PortChooser portChooser = new PortChooser(connection);
		if (edge.getParameter(ObjectType.PARAMETER_SOURCE_PORT) != null) {
			edge.setValue(ObjectType.PARAMETER_SOURCE_PORT, portChooser
					.getSourcePort(source));
		}

		if (edge.getParameter(ObjectType.PARAMETER_TARGET_PORT) != null) {
			edge.setValue(ObjectType.PARAMETER_TARGET_PORT, portChooser
					.getTargetPort(target));
		}

		parentGraph.addEdge(edge);
	}

	@Override
	public String getLabel() {
		if (edge != null) {
			String type = edge.getType().getName();
			return "Create " + type;
		} else {
			return "Create edge";
		}
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
	}
}
