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
package net.sf.orcc.graphiti.ui.editpolicies;

import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.commands.EdgeCreateCommand;
import net.sf.orcc.graphiti.ui.commands.EdgeReconnectCommand;
import net.sf.orcc.graphiti.ui.editparts.VertexEditPart;
import net.sf.orcc.graphiti.ui.figure.EdgeFigure;

import org.eclipse.draw2d.Connection;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * This class provides methods that deal with creations and connections of
 * dependencies. When the user clicks a source port, the method
 * <code>getConnectionCreateCommand</code> is first called. When they click the
 * destination port, <code>getConnectionCompleteCommand</code> is called.
 * 
 * <code>getReconnectSourceCommand</code> and
 * <code>getReconnectTargetCommand</code> are called when the user reconnects
 * one end of a dependency (they have previously disconnected).
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 */
public class EdgeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Connection createDummyConnection(Request req) {
		Object obj = ((CreateConnectionRequest) req).getNewObject();
		EdgeFigure conn = new EdgeFigure((Edge) obj);
		return conn;
	}

	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		EdgeCreateCommand command = (EdgeCreateCommand) request
				.getStartCommand();
		VertexEditPart vertexEditPart = (VertexEditPart) request
				.getTargetEditPart();
		command.setTarget((Vertex) (vertexEditPart.getModel()));
		return command;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		EdgeCreateCommand command = new EdgeCreateCommand((Edge) request
				.getNewObject());
		VertexEditPart vertexEditPart = (VertexEditPart) request
				.getTargetEditPart();
		command.setSource((Vertex) (vertexEditPart.getModel()));
		request.setStartCommand(command);
		return command;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		EdgeReconnectCommand command = new EdgeReconnectCommand();
		command.setOriginalEdge((Edge) request.getConnectionEditPart()
				.getModel());
		VertexEditPart vertexEditPart = (VertexEditPart) getHost();
		command.setSource((Vertex) vertexEditPart.getModel());
		return command;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		EdgeReconnectCommand command = new EdgeReconnectCommand();
		command.setOriginalEdge((Edge) request.getConnectionEditPart()
				.getModel());
		VertexEditPart vertexEditPart = (VertexEditPart) getHost();
		command.setTarget((Vertex) vertexEditPart.getModel());
		return command;
	}
}
