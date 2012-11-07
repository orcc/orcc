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
package net.sf.orcc.graphiti.ui.figure;

import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

/**
 * This class implements {@link CellEditorLocator} to edit a {@link Vertex}'s
 * id. It is based on Daniel Lee's implementation for the flow example.
 * 
 * @author Daniel Lee
 * @author Matthieu Wipliez
 */
public class VertexCellEditorLocator implements CellEditorLocator {

	private VertexFigure vertexFigure;

	/**
	 * Creates a new VertexCellEditorLocator for the given vertexFigure
	 * 
	 * @param figure
	 *            the figure
	 */
	public VertexCellEditorLocator(VertexFigure figure) {
		vertexFigure = figure;
	}

	/**
	 * @see CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
	 */
	public void relocate(CellEditor celleditor) {
		Text text = (Text) celleditor.getControl();
		Point pref;
		if (text.getText().isEmpty()) {
			pref = new Point(13, 13);
		} else {
			pref = text.computeSize(-1, -1);
		}

		Label label = vertexFigure.getLabelId();
		Rectangle labelBounds = label.getBounds().getCopy();
		label.translateToAbsolute(labelBounds);

		Rectangle figureBounds = vertexFigure.getBounds().getCopy();
		vertexFigure.translateToAbsolute(figureBounds);
		int start = (figureBounds.width - pref.x) / 2;

		text.setBounds(figureBounds.x + start, labelBounds.y, pref.x, pref.y);
	}

}
