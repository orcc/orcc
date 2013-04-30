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

import net.sf.orcc.graphiti.ui.figure.VertexFigure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;

/**
 * This interface defines an IShape.
 * 
 * @author Jonathan Piat
 * @author Matthieu Wipliez
 * 
 */
public interface IShape extends IFigure {

	/**
	 * Returns the connection anchor associated with this shape. The connection
	 * anchor may be a predefined eclipse implementation or a custom one.
	 * 
	 * @return A ConnectionAnchor object.
	 */
	public ConnectionAnchor getConnectionAnchor(VertexFigure figure,
			String portName, boolean isOutput);

	/**
	 * Returns a new shape of the same class as this shape.
	 * 
	 * @return An implementation of {@link IShape}.
	 */
	public IShape newShape();

	/**
	 * Equivalent to <code>super.paintFigure(graphics)</code>.
	 * 
	 * @param graphics
	 */
	public void paintSuperFigure(Graphics graphics);

	/**
	 * Sets the size of this shape.
	 * 
	 * @param dim
	 *            The {@link Dimension}.
	 */
	public void setDimension(Dimension dim);

}
