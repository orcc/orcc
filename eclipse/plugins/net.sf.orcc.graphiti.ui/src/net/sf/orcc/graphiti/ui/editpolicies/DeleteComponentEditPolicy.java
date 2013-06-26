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

import java.util.List;

import net.sf.orcc.graphiti.ui.commands.DeleteCommand;
import net.sf.orcc.graphiti.ui.editparts.EdgeEditPart;
import net.sf.orcc.graphiti.ui.editparts.VertexEditPart;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * This class overrides the createDeleteCommand to return a command that can
 * delete a vertex.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 */
public class DeleteComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		if (getHost() instanceof VertexEditPart) {
			VertexEditPart part = (VertexEditPart) getHost();
			List<?> incoming = part.getSourceConnections();
			List<?> outgoing = part.getTargetConnections();
			if (!incoming.isEmpty() || !outgoing.isEmpty()) {
				CompoundCommand compound = new CompoundCommand();
				for (Object obj : incoming) {
					DeleteCommand command = new DeleteCommand(
							((EdgeEditPart) obj).getModel());
					compound.add(command);
				}

				for (Object obj : outgoing) {
					DeleteCommand command = new DeleteCommand(
							((EdgeEditPart) obj).getModel());
					compound.add(command);
				}

				DeleteCommand command = new DeleteCommand(getHost().getModel());
				compound.add(command);

				return compound;
			}
		}

		DeleteCommand command = new DeleteCommand(getHost().getModel());
		return command;
	}

}
