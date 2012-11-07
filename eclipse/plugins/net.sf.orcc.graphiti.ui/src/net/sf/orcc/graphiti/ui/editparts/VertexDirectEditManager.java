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
package net.sf.orcc.graphiti.ui.editparts;

import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.figure.VertexCellEditorLocator;
import net.sf.orcc.graphiti.ui.figure.VertexFigure;

import org.eclipse.draw2d.Label;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

/**
 * This class extends {@link DirectEditManager} to edit a {@link Vertex}'s id.
 * It is based on Daniel Lee's implementation for the flow example.
 * 
 * @author Daniel Lee
 * @author Matthieu Wipliez
 */
public class VertexDirectEditManager extends DirectEditManager {

	private VerifyListener verifyListener;

	private Label vertexLabel;

	/**
	 * Creates a new VertexDirectEditManager with the given attributes.
	 * 
	 * @param source
	 *            the source EditPart
	 * @param editorType
	 *            type of editor
	 * @param locator
	 *            the CellEditorLocator
	 */
	public VertexDirectEditManager(VertexEditPart source,
			VertexFigure vertexFigure) {
		super(source, TextCellEditor.class, new VertexCellEditorLocator(
				vertexFigure));
		vertexLabel = vertexFigure.getLabelId();
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
	 */
	protected void initCellEditor() {
		CellEditor editor = getCellEditor();

		final Text text = (Text) editor.getControl();

		verifyListener = new VerifyListener() {
			public void verifyText(VerifyEvent event) {
				Text text = (Text) getCellEditor().getControl();
				String oldText = text.getText();
				String newText = oldText.substring(0, event.start) + event.text
						+ oldText.substring(event.end, oldText.length());

				GC gc = new GC(text);
				Point size = gc.textExtent(newText);
				gc.dispose();
				if (size.x == 0) {
					size.x = size.y;
				} else {
					size = text.computeSize(size.x, size.y);
				}

				// String error =
				// getCellEditor().getValidator().isValid(newText);
				// if (error == null || error.isEmpty()) {
				// text.setBackground(text.getParent().getBackground());
				// } else {
				// text.setBackground(ColorConstants.red);
				// }

				text.setSize(size.x, size.y);
			}
		};
		text.addVerifyListener(verifyListener);

		String initialLabelText = vertexLabel.getText();
		editor.setValue(initialLabelText);
	}

	@Override
	public void showFeedback() {
		// this is to remove the shadow around the Text component
		getEditPart().showSourceFeedback(getDirectEditRequest());
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#unhookListeners()
	 */
	protected void unhookListeners() {
		super.unhookListeners();
		Text text = (Text) getCellEditor().getControl();
		text.removeVerifyListener(verifyListener);
		verifyListener = null;
	}
}
