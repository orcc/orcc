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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Parameter;
import net.sf.orcc.graphiti.model.ParameterPosition;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.DelegatingLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * This class provides drawing for a dependency.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 */
public class EdgeFigure extends PolylineConnection {

	private Edge edge;

	private Map<String, Label> parameterFigures;

	/**
	 * Creates the Figure associated to the connection
	 * 
	 * @param edge
	 *            The Edge model associated with this figure.
	 */
	public EdgeFigure(Edge edge) {
		// Sets Layout Manager
		this.setLayoutManager(new DelegatingLayout());
		this.edge = edge;

		parameterFigures = new HashMap<String, Label>();

		// update parameters
		for (Parameter parameter : edge.getParameters()) {
			refresh(parameter.getName(), edge.getValue(parameter.getName()));
		}

		Boolean directed = (Boolean) edge.getType().getAttribute(
				ObjectType.ATTRIBUTE_DIRECTED);
		if (directed == null || directed) {
			// we consider the edge directed by default.
			setTargetDecoration(new PolygonDecoration());
		}

		Color color = (Color) edge.getType().getAttribute(
				ObjectType.ATTRIBUTE_COLOR);
		if (color == null) {
			color = ColorConstants.black;
		}

		setForegroundColor(color);
		setLineWidth(1);
	}

	@Override
	public void paintFigure(Graphics graphics) {
		if (graphics instanceof SWTGraphics) {
			graphics.pushState();
			try {
				// Needs advanced capabilities or throws SWTException
				graphics.setAntialias(SWT.ON);
			} catch (RuntimeException e) {
				// No anti alias, less pretty but it will work!
			}

			super.paintFigure(graphics);
			graphics.popState();
		} else {
			// ScaledGraphics and PrinterGraphics do not have advanced
			// capabilities... so we try with SWTGraphics
			super.paintFigure(graphics);
		}
	}

	public void refresh(String parameterName, Object value) {
		Label label = parameterFigures.get(parameterName);
		if (label == null) {
			value = edge.getValue(parameterName);
			if (value != null) {
				Parameter parameter = edge.getParameter(parameterName);
				ParameterPosition position = parameter.getPosition();

				if (position != null) {
					Label parameterLabel = new Label(value.toString());
					parameterLabel
							.setForegroundColor(new Color(null, 224, 0, 0));
					Object locator = new PropertyLocator(this, position);
					add(parameterLabel, locator);
					parameterFigures.put(parameterName, parameterLabel);
				}
			}
		} else {
			label.setText(value.toString());
		}
	}
}
