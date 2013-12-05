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

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbstractHintLayout;
import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Figures using the StackLayout as their layout manager have their children
 * placed on top of one another. Order of placement is determined by the order
 * in which the children were added, first child added placed on the bottom.
 */
public class ManualGraphLayoutManager extends StackLayout {

	/**
	 * Returns the minimum size required by the input container. This is the
	 * size of the largest child of the container, as all other children fit
	 * into this size.
	 * 
	 * @see AbstractHintLayout#calculateMinimumSize(IFigure, int, int)
	 */
	@SuppressWarnings("rawtypes")
	protected Dimension calculateMinimumSize(IFigure figure, int wHint,
			int hHint) {
		if (wHint > -1)
			wHint = Math.max(0, wHint - figure.getInsets().getWidth());
		if (hHint > -1)
			hHint = Math.max(0, hHint - figure.getInsets().getHeight());
		Dimension d = new Dimension();
		List children = figure.getChildren();
		IFigure child;
		for (int i = 0; i < children.size(); i++) {
			child = (IFigure) children.get(i);
			if (!isObservingVisibility() || child.isVisible())
				d.union(child.getMinimumSize(wHint, hHint));
		}

		d.expand(figure.getInsets().getWidth(), figure.getInsets().getHeight());
		d.union(getBorderPreferredSize(figure));
		return d;

	}

	/**
	 * Calculates and returns the preferred size of the given figure. This is
	 * the union of the preferred sizes of the widest and the tallest of all its
	 * children.
	 * 
	 * @see AbstractLayout#calculatePreferredSize(IFigure, int, int)
	 */
	@SuppressWarnings("rawtypes")
	protected Dimension calculatePreferredSize(IFigure figure, int wHint,
			int hHint) {
		if (wHint > -1)
			wHint = Math.max(0, wHint - figure.getInsets().getWidth());
		if (hHint > -1)
			hHint = Math.max(0, hHint - figure.getInsets().getHeight());
		Dimension d = new Dimension();
		List children = figure.getChildren();
		IFigure child;
		for (int i = 0; i < children.size(); i++) {
			child = (IFigure) children.get(i);
			if (!isObservingVisibility() || child.isVisible())
				d.union(child.getPreferredSize(wHint, hHint));
		}

		d.expand(figure.getInsets().getWidth(), figure.getInsets().getHeight());
		d.union(getBorderPreferredSize(figure));
		return d;
	}

	/**
	 * Returns the origin for the given figure.
	 * 
	 * @param parent
	 *            the figure whose origin is requested
	 * @return the origin
	 */
	public Point getOrigin(IFigure parent) {
		return parent.getClientArea().getLocation();
	}

	/**
	 * @see org.eclipse.draw2d.LayoutManager#layout(IFigure)
	 */
	@SuppressWarnings("rawtypes")
	public void layout(IFigure parent) {
		Rectangle r = parent.getClientArea();
		List children = parent.getChildren();
		IFigure child;
		for (int i = 0; i < children.size(); i++) {
			child = (IFigure) children.get(i);
			if (child instanceof ScrollPane) {
				child.setBounds(r);
			}
		}

		Iterator it = children.iterator();
		Point offset = getOrigin(parent);
		IFigure f;
		while (it.hasNext()) {
			f = (IFigure) it.next();
			Rectangle bounds = (Rectangle) getConstraint(f);
			if (bounds == null)
				continue;

			if (bounds.width == -1 || bounds.height == -1) {
				Dimension preferredSize = f.getPreferredSize(bounds.width,
						bounds.height);
				bounds = bounds.getCopy();
				if (bounds.width == -1)
					bounds.width = preferredSize.width;
				if (bounds.height == -1)
					bounds.height = preferredSize.height;
			}
			bounds = bounds.getTranslated(offset);
			f.setBounds(bounds);
		}
	}

	// public void layout(IFigure parent) {
	// Iterator children = parent.getChildren().iterator();
	// Point offset = getOrigin(parent);
	// IFigure f;
	// while (children.hasNext()) {
	// f = (IFigure)children.next();
	// Rectangle bounds = (Rectangle)getConstraint(f);
	// if (bounds == null) continue;
	//
	// if (bounds.width == -1 || bounds.height == -1) {
	// Dimension preferredSize = f.getPreferredSize(bounds.width,
	// bounds.height);
	// bounds = bounds.getCopy();
	// if (bounds.width == -1)
	// bounds.width = preferredSize.width;
	// if (bounds.height == -1)
	// bounds.height = preferredSize.height;
	// }
	// bounds = bounds.getTranslated(offset);
	// f.setBounds(bounds);
	// }
	// }

}
