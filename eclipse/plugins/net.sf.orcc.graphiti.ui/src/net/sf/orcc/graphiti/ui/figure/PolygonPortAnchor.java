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

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.geometry.Geometry;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

/**
 * This class provides a connection anchor for polygons.
 * 
 * @author Jonathan Piat
 * @author Matthieu Wipliez
 * 
 */
public class PolygonPortAnchor extends AbstractConnectionAnchor {

	private PortAnchorReferenceManager mgr;

	/**
	 * Creates a new polygon port anchor.
	 * 
	 * @param figure
	 *            The owning vertex figure.
	 * @param portName
	 *            The port name associated with this connection anchor.
	 * @param isOutput
	 *            Whether the connection is input (false) or output (true).
	 */
	public PolygonPortAnchor(VertexFigure figure, String portName,
			boolean isOutput) {
		super(figure);
		mgr = new PortAnchorReferenceManager(figure, portName, isOutput);
	}

	/**
	 * Gets a Rectangle from {@link #getBox()} and returns the Point where a
	 * line from the center of the Rectangle to the Point <i>reference</i>
	 * intersects the Rectangle.
	 * 
	 * @param reference
	 *            The reference point
	 * @return The anchor location
	 */
	@Override
	public Point getLocation(Point reference) {
		Polygon owner;
		if (getOwner() instanceof VertexFigure) {
			owner = (Polygon) getOwner().getChildren().get(0);
		} else {
			throw new NullPointerException();
		}

		Point center = getReferencePoint();
		if (reference.x == center.x && reference.y == center.y) {
			return center;
		}

		// The line run
		float run = (reference.x - center.x);

		PointList pointList = owner.getPoints();
		for (int i = 0; i < pointList.size() - 1; i++) {
			Point start = pointList.getPoint(i);
			Point end = pointList.getPoint(i + 1);

			// Translate from relative to absolute coordinates
			owner.translateToAbsolute(start);
			owner.translateToAbsolute(end);

			// Check intersection
			if (Geometry.linesIntersect(center.x, center.y, reference.x,
					reference.y, start.x, start.y, end.x, end.y)) {
				float p = ((float) (start.y - end.y))
						/ ((float) (start.x - end.x));
				float d = start.y - p * start.x;

				// Compute xAnchor
				int xAnchor;
				if (run == 0) {
					// Line equation: x = center.x
					xAnchor = center.x;
				} else {
					// Line equation: y = ax + b = px + d =>
					// x = (d - b) / (a - p)
					float rise = (reference.y - center.y);
					float a = rise / run;
					float b = center.y - a * center.x;
					xAnchor = (int) ((d - b) / (a - p));
				}

				// yAnchor is just y = px + d
				int yAnchor = (int) (p * xAnchor + d);
				return new Point(xAnchor, yAnchor);
			}
		}

		// Should never happen
		return center;
	}

	@Override
	public Point getReferencePoint() {
		Point reference = mgr.getReferencePoint(this);
		if (reference == null) {
			return super.getReferencePoint();
		} else {
			return reference;
		}
	}

}
