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
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * This class provides a reference manager for port anchors. It contains a main
 * method, getReferencePoint, which clients call to get a reference point for
 * their connection anchor. There is one reference manager for each connection
 * anchor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PortAnchorReferenceManager {

	private VertexFigure figure;

	private boolean isOutput;

	private String portName;

	/**
	 * Creates a new port anchor reference manager.
	 * 
	 * @param figure
	 *            The owning vertex figure.
	 * @param portName
	 *            The port name associated with this connection anchor.
	 * @param isOutput
	 *            Whether the connection is input (false) or output (true).
	 */
	public PortAnchorReferenceManager(VertexFigure figure, String portName,
			boolean isOutput) {
		this.figure = figure;
		this.portName = portName;
		this.isOutput = isOutput;
	}

	/**
	 * Returns a reference point for the given connection anchor. It uses the
	 * underlying vertex figure to retrieve the label associated with the port
	 * name given at creation time.
	 * 
	 * @param anchor
	 *            An abstract {@link ConnectionAnchor}.
	 * @return A reference {@link Point}.
	 */
	public Point getReferencePoint(AbstractConnectionAnchor anchor) {
		if (portName == null || portName.isEmpty()) {
			return null;
		} else {
			Label label;
			if (isOutput) {
				label = figure.getOutputPortLabel(portName);
				if (label == null) {
					return null;
				}

				Rectangle bounds = label.getBounds();
				int x = bounds.x + bounds.width + 5;
				int y = bounds.y + bounds.height / 2;

				Point ref = new Point(x, y);
				label.translateToAbsolute(ref);

				return ref;
			} else {
				label = figure.getInputPortLabel(portName);
				if (label == null) {
					return null;
				}

				Rectangle bounds = label.getBounds();
				int x = bounds.x - 5;
				int y = bounds.y + bounds.height / 2;

				Point ref = new Point(x, y);
				label.translateToAbsolute(ref);

				return ref;
			}
		}
	}

}
