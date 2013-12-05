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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import net.sf.orcc.graphiti.ui.editpolicies.LayoutPolicy;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * This class extends {@link AbstractGraphicalEditPart} by setting its figure
 * layout manager to {@link GraphLayoutManager}. It also extends the
 * {@link EditPart#isSelectable()} method to return false, causing the selection
 * tool to act like the marquee tool when no particular children has been
 * selected.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StatusEditPart extends AbstractGraphicalEditPart {

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LayoutPolicy());
	}

	@Override
	protected IFigure createFigure() {
		// The figure associated with this graph edit part is only a
		// free form layer
		Figure root = new FreeformLayer();
		root.setLayoutManager(new FreeformLayout());

		IStatus status = (IStatus) getModel();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Throwable exc = status.getException();
		Throwable cause = exc.getCause();
		if (cause == null) {
			exc.printStackTrace(new PrintStream(bos));
		} else {
			cause.printStackTrace(new PrintStream(bos));
		}

		Display d = Display.getCurrent();
		Image image = d.getSystemImage(SWT.ICON_ERROR);
		Label labelImage = new Label(image);
		root.add(labelImage, new Rectangle(5, 5, -1, -1));

		Label label = new Label(status.getMessage() + ": " + exc.getMessage()
				+ "\n" + bos.toString());
		root.add(label, new Rectangle(10 + image.getBounds().width, 5, -1, -1));

		return root;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}
}
