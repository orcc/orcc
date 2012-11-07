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
package net.sf.orcc.graphiti.ui.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.commands.copyPaste.PasteCommand;
import net.sf.orcc.graphiti.ui.editparts.GraphEditPart;
import net.sf.orcc.graphiti.ui.editparts.VertexEditPart;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * This class provides an implementation of the paste action.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 * 
 */
public class PasteAction extends SelectionAction implements
		PropertyChangeListener {

	/**
	 * Constructor for PasteAction.
	 * 
	 * @param editor
	 */
	public PasteAction(IWorkbenchPart editor) {
		super(editor);
	}

	@Override
	protected boolean calculateEnabled() {
		// Enabled if the clipboard is not empty and we know where to paste:
		// either the selected object is a GraphEditPart or a VertexEditPart
		List<?> selection = getSelectedObjects();
		List<?> vertices = getClipboardContents();
		return (vertices != null && vertices.isEmpty() == false
				&& vertices.get(0) instanceof Vertex && selection != null
				&& selection.size() == 1 && (selection.get(0) instanceof GraphEditPart || selection
				.get(0) instanceof VertexEditPart));
	}

	protected List<?> getClipboardContents() {
		LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
		Object data = GraphitiClipboard.getInstance().getContents(transfer);
		if (data instanceof IStructuredSelection) {
			return ((IStructuredSelection) data).toList();
		} else {
			return null;
		}
	}

	/**
	 * @see org.eclipse.gef.ui.actions.EditorPartAction#init()
	 */
	@Override
	protected void init() {
		setId(ActionFactory.PASTE.getId());
		setText("Paste");
		setToolTipText("Paste");

		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(false);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(GraphitiClipboard.CONTENTS_SET_EVENT))
			setEnabled(calculateEnabled());
	}

	/**
	 * Executes a new {@link PasteCommand}.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		Object obj = getSelectedObjects().get(0);
		GraphEditPart part = null;
		if (obj instanceof GraphEditPart) {
			part = (GraphEditPart) obj;
		} else if (obj instanceof VertexEditPart) {
			part = (GraphEditPart) ((VertexEditPart) obj).getParent();
		}

		// execute the paste command
		List<Vertex> contents = (List<Vertex>) getClipboardContents();
		PasteCommand command = new PasteCommand(part, contents);
		command.run();
		if (command.isDirty()) {
			execute(command);
		}
	}
}
