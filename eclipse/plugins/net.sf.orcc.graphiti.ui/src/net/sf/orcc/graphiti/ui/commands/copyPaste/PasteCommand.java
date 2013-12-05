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
package net.sf.orcc.graphiti.ui.commands.copyPaste;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.editparts.GraphEditPart;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * This class provides a command that pastes vertices from the clipboard.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 * 
 */
public class PasteCommand extends Command {

	private List<Object> added;

	private boolean dirty;

	/**
	 * The target EditPart.
	 */
	private Graph graph;

	private List<Vertex> vertices;

	/**
	 * Creates a new paste command with the given target part and vertices.
	 * 
	 * @param target
	 *            The target part.
	 * @param vertices
	 *            A list of vertices to cut.
	 */
	public PasteCommand(GraphEditPart target, List<Vertex> vertices) {
		this.graph = (Graph) target.getModel();
		this.vertices = vertices;
	}

	private String checkVertexId(Graph graph, Vertex vertex) {
		String id = (String) vertex.getValue(ObjectType.PARAMETER_ID);
		Vertex existing = graph.findVertex(id);
		if (existing != vertex) {
			id = getVertexId(id);
			if (id != null) {
				vertex.setValue(ObjectType.PARAMETER_ID, id);
			}
		}

		return id;
	}

	@Override
	public void execute() {

	}

	/**
	 * Returns a vertex identifier.
	 * 
	 * @param initialValue
	 *            The initial id.
	 * @return A unique vertex identifier, or <code>null</code>.
	 */
	private String getVertexId(String initialValue) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		InputDialog dialog = new InputDialog(shell, "New vertex",
				"Please enter a vertex identifier", initialValue,
				new IInputValidator() {

					@Override
					public String isValid(String vertexId) {
						if (vertexId.isEmpty()) {
							return "";
						}

						if (graph != null) {
							if (graph.vertexExistsCaseInsensitive(vertexId)) {
								return "A vertex already exists with the same identifier";
							}
						}

						return null;
					}

				});
		int res = dialog.open();
		if (res == InputDialog.OK) {
			String value = dialog.getValue();
			if (value == null || value.isEmpty()) {
				return null;
			} else {
				return value;
			}
		} else {
			return null;
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	public void run() {
		added = new ArrayList<Object>();

		for (Vertex vertex : vertices) {
			// check id
			if (checkVertexId(graph, vertex) != null) {
				// Adds the cloned graph to the parent graph and the added list
				added.add(vertex);
				Rectangle previousBounds = (Rectangle) vertex
						.getValue(Vertex.PROPERTY_SIZE);
				graph.addVertex(vertex);
				Rectangle bounds = (Rectangle) vertex
						.getValue(Vertex.PROPERTY_SIZE);

				Rectangle newBounds = new Rectangle(previousBounds.x
						+ previousBounds.width + 10, previousBounds.y
						+ previousBounds.height + 10, bounds.width,
						bounds.height);
				vertex.firePropertyChange(Vertex.PROPERTY_SIZE, null, newBounds);

				dirty = true;
			}
		}
	}

	@Override
	public void undo() {
		for (Object model : added) {
			if (model instanceof Vertex) {
				graph.removeVertex((Vertex) model);
			}
		}
	}
}
