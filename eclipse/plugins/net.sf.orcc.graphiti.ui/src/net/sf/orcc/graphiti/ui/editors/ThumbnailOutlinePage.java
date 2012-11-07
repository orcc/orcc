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
package net.sf.orcc.graphiti.ui.editors;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This class provides a thumbnail outline page.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ThumbnailOutlinePage extends ContentOutlinePage {

	private Canvas canvas;

	private DisposeListener disposeListener;

	private GraphEditor editor;

	private Thumbnail thumbnail;

	public ThumbnailOutlinePage(GraphEditor editor) {
		super(new GraphicalViewerImpl());
		this.editor = editor;
	}

	public void createControl(Composite parent) {
		canvas = new Canvas(parent, SWT.BORDER);
		LightweightSystem lws = new LightweightSystem(canvas);

		RootEditPart root = editor.getGraphicalViewer().getRootEditPart();
		ScalableFreeformRootEditPart scalable = (ScalableFreeformRootEditPart) root;
		thumbnail = new ScrollableThumbnail((Viewport) scalable.getFigure());
		thumbnail.setSource(scalable.getLayer(LayerConstants.PRINTABLE_LAYERS));

		lws.setContents(thumbnail);

		disposeListener = new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (thumbnail != null) {
					thumbnail.deactivate();
					thumbnail = null;
				}
			}
		};

		Control control = editor.getGraphicalViewer().getControl();
		control.addDisposeListener(disposeListener);
	}

	public void dispose() {
		editor.getSelectionSynchronizer().removeViewer(getViewer());
		Control control = editor.getGraphicalViewer().getControl();
		if (control != null && !control.isDisposed()) {
			control.removeDisposeListener(disposeListener);
		}
		super.dispose();
	}

	public Control getControl() {
		return canvas;
	}

}