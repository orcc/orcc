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

import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.actions.GraphitiClipboard;
import net.sf.orcc.graphiti.ui.editparts.VertexEditPart;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.Transfer;

/**
 * This class provides a command that copies vertices.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 * 
 */
public class CopyCommand extends Command {

	private List<?> list;

	/**
	 * Creates a new cut command with the selected objects.
	 * 
	 * @param objects
	 *            A list of objects to cut.
	 */
	public CopyCommand(List<?> objects) {
		list = objects;
	}

	@Override
	public void execute() {
		// copy vertices
		List<Vertex> vertices = new ArrayList<Vertex>();
		for (Object obj : list) {
			if (obj instanceof VertexEditPart) {
				VertexEditPart vertexEditPart = (VertexEditPart) obj;
				Vertex vertex = (Vertex) vertexEditPart.getModel();

				// copy vertex and add to list
				vertex = new Vertex(vertex);
				vertices.add(vertex);
			}
		}

		// prepare transfer
		LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
		Object[] verticesArray = vertices.toArray();
		transfer.setSelection(new StructuredSelection(verticesArray));

		// put in clipboard
		Object[] data = new Object[] { verticesArray };
		Transfer[] transfers = new Transfer[] { transfer };
		GraphitiClipboard.getInstance().setContents(data, transfers);
	}

	@Override
	public String getLabel() {
		return "Copy";
	}
}
