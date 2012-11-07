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

import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * This class allows the creation of vertices.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 * 
 */
public class VertexCreateCommand extends Command {

	private Rectangle bounds;

	private Graph graph;

	private Vertex vertex;

	public VertexCreateCommand() {
	}

	@Override
	public void execute() {
		if (graph != null && vertex != null) {
			String id = getVertexId();
			if (id != null) {
				vertex.setValue(ObjectType.PARAMETER_ID, id);
				graph.addVertex(vertex);

				// retrieve the vertex bounds (they have been set by the edit
				// part)
				// and set the location
				Rectangle vertexBounds = (Rectangle) vertex
						.getValue(Vertex.PROPERTY_SIZE);
				Rectangle newBounds = vertexBounds.getCopy();
				newBounds.x = bounds.x;
				newBounds.y = bounds.y;
				vertex.setValue(Vertex.PROPERTY_SIZE, newBounds);
			}
		}
	}

	@Override
	public String getLabel() {
		if (vertex != null) {
			String type = vertex.getType().getName();
			return "Create " + type;
		} else {
			return "Create vertex";
		}
	}

	/**
	 * Returns a vertex identifier.
	 * 
	 * @return A unique vertex identifier, or <code>null</code>.
	 */
	private String getVertexId() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		InputDialog dialog = new InputDialog(shell, "New vertex",
				"Please enter a vertex identifier", "", new IInputValidator() {

					@Override
					public String isValid(String vertexId) {
						if (vertexId.isEmpty()) {
							return "";
						}

						if (graph != null) {
							Vertex vertex = graph.findVertex(vertexId);
							if (vertex != null) {
								return "A vertex already exists with the same identifier";
							}
						}

						return null;
					}

				});
		dialog.open();

		String value = dialog.getValue();
		if (value == null || value.isEmpty()) {
			return null;
		} else {
			return value;
		}
	}

	/**
	 * Sets the initial bounds of this vertex.
	 * 
	 * @param bounds
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	/**
	 * Sets this command model.
	 * 
	 * @param model
	 *            The model to use.
	 */
	public void setModel(Object model) {
		if (model instanceof Graph) {
			this.graph = (Graph) model;
		}
	}

	/**
	 * Sets the new object that should be added to the model.
	 * 
	 * @param newObject
	 *            the newly created object.
	 */
	public void setNewObject(Object newObject) {
		if (newObject instanceof Vertex) {
			this.vertex = (Vertex) newObject;
		}
	}

	@Override
	public void undo() {
		if (graph != null && vertex != null) {
			graph.removeVertex(vertex);
		}
	}
}
