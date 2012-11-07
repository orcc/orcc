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
package net.sf.orcc.graphiti.ui.figure.shapes;

import net.sf.orcc.graphiti.ui.figure.PolygonPortAnchor;
import net.sf.orcc.graphiti.ui.figure.VertexFigure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.geometry.Dimension;

/**
 * This class provides an abstract polygon shape for hexagon, losange and
 * triangle shapes.
 * 
 * @author Matthieu Wipliez
 * 
 */
abstract public class AbstractPolygonShape extends Polygon implements IShape {

	/**
	 * Creates a new abstract polygon shape.
	 */
	public AbstractPolygonShape() {
		setLayoutManager(new GridLayout(2, false));
		setFill(true);
	}

	@Override
	public ConnectionAnchor getConnectionAnchor(VertexFigure figure,
			String portName, boolean isOutput) {
		return new PolygonPortAnchor(figure, portName, isOutput);
	}

	@Override
	public IShape newShape() {
		try {
			return getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public void paintFigure(Graphics graphics) {
		GradientPattern.paintFigure(this, getBackgroundColor(), getBounds(),
				graphics);
	}

	@Override
	public void paintSuperFigure(Graphics graphics) {
		super.paintFigure(graphics);
	}

	@Override
	abstract public void setDimension(Dimension dim);

	@Override
	protected boolean useLocalCoordinates() {
		return true;
	}

}
