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

import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.commands.VertexRenameCommand;
import net.sf.orcc.graphiti.ui.editparts.VertexEditPart;
import net.sf.orcc.graphiti.ui.figure.VertexFigure;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;

/**
 * This class provides a {@link DirectEditPolicy} for a vertex id.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class VertexDirectEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		CellEditor editor = request.getCellEditor();
		editor.setValidator(new ICellEditorValidator() {

			@Override
			public String isValid(Object value) {
				VertexEditPart part = (VertexEditPart) getHost();
				Vertex vertex = (Vertex) part.getModel();
				Graph graph = vertex.getParent();

				String vertexId = (String) value;
				if (vertexId.isEmpty()) {
					return "";
				}

				vertex = graph.findVertex(vertexId);
				if (graph.vertexExistsCaseInsensitive(vertexId)
						|| vertex != null
						&& !vertex.equals(getHost().getModel())) {
					return "A vertex already exists with the same identifier";
				}

				return null;
			}

		});

		Vertex vertex = (Vertex) getHost().getModel();
		if (editor.getValidator().isValid(editor.getValue()) == null) {
			VertexRenameCommand cmd = new VertexRenameCommand(vertex);
			cmd.setName((String) editor.getValue());
			return cmd;
		} else {
			String id = (String) vertex.getValue(ObjectType.PARAMETER_ID);
			VertexFigure figure = (VertexFigure) getHostFigure();
			figure.getLabelId().setText(id);
			figure.adjustSize();
			return null;
		}
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		VertexFigure figure = (VertexFigure) getHostFigure();
		figure.getLabelId().setText(value);
		figure.adjustSize();
	}

}
