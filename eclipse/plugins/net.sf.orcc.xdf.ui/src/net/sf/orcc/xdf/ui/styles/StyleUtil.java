/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.xdf.ui.styles;

import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Style;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

/**
 * Styles are created here with "plain"-methods, i.e. all values have to be set
 * explicitly.
 * 
 * See chapters "Using Styles" and "Color Schemas" in tutorial for details.
 */
public class StyleUtil {

	private static final IColorConstant INSTANCE_TEXT_FOREGROUND = new ColorConstant(0, 0, 0);
	private static final IColorConstant INSTANCE_FOREGROUND = new ColorConstant(98, 131, 167);
	private static final IColorConstant INSTANCEPORT_BACKGROUND = INSTANCE_FOREGROUND;
	private static final IColorConstant CONNECTION_COLOR = new ColorConstant(0, 0, 0);

	private static void setCommonTextValues(Diagram diagram, IGaService gaService, Style style) {
		style.setFilled(false);
		style.setAngle(0);
		style.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		style.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		style.setForeground(gaService.manageColor(diagram, INSTANCE_TEXT_FOREGROUND));
	}

	private static void setCommonValues(Style style) {
		style.setLineStyle(LineStyle.SOLID);
		style.setLineVisible(true);
		style.setLineWidth(2);
		style.setTransparency(0.0);
	}

	/**
	 * Return the style used for all elements with no specific style.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style getCommonStyle(Diagram diagram) {
		final String styleId = "COMMONSTYLE";
		IGaService gaService = Graphiti.getGaService();

		// Is style already persisted?
		Style style = gaService.findStyle(diagram, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(diagram, styleId);
			setCommonValues(style);
		}
		return style;
	}

	/**
	 * Return the style used for Instance shapes.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForInstance(Diagram diagram) {
		final String styleId = "INSTANCE";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(true);
			style.setForeground(gaService.manageColor(diagram, INSTANCE_FOREGROUND));

			gaService.setRenderingStyle(style, PredefinedColoredAreas.getCopperWhiteGlossAdaptions());
		}
		return style;
	}

	/**
	 * Return the style used for ports displayed inside an Instance Shape.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForInstancePort(Diagram diagram) {
		final String styleId = "INSTANCE-PORT";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(true);
			style.setLineVisible(false);
			style.setBackground(gaService.manageColor(diagram, INSTANCEPORT_BACKGROUND));
		}
		return style;
	}

	/**
	 * Return the style used for inputs ports displayed in the network
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForInputPort(Diagram diagram) {
		final String styleId = "INPUT-PORT";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(true);
			style.setLineVisible(false);
			style.setBackground(gaService.manageColor(diagram, INSTANCEPORT_BACKGROUND));
		}
		return style;
	}

	/**
	 * Return the style used for inputs ports displayed in the network
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForOutputPort(Diagram diagram) {
		final String styleId = "INPUT-PORT";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(true);
			style.setLineVisible(false);
			style.setBackground(gaService.manageColor(diagram, INSTANCEPORT_BACKGROUND));
		}
		return style;
	}

	/**
	 * Return the style used for connections.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForConnection(Diagram diagram) {
		final String styleId = "CONNECTION";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(true);
			style.setLineVisible(true);
			style.setLineWidth(2);
			final Color commonColor = gaService.manageColor(diagram, CONNECTION_COLOR);
			style.setForeground(commonColor);
			style.setBackground(commonColor);
		}
		return style;
	}

	/**
	 * Return the style used for the text displaying name of an Instance.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForInstanceText(Diagram diagram) {
		final String styleId = "INSTANCE-TEXT";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			setCommonTextValues(diagram, gaService, style);
			style.setFont(gaService.manageDefaultFont(diagram, false, true));
		}
		return style;
	}

	public static Style getStyleForInstancePortText(Diagram diagram) {
		final String styleId = "INSTANCE-PORT-TEXT";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(false);
			style.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			style.setForeground(gaService.manageColor(diagram, INSTANCE_TEXT_FOREGROUND));
		}
		return style;
	}

	public static Style getStyleForPortText(Diagram diagram) {
		final String styleId = "PORT-TEXT";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getCommonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			setCommonTextValues(diagram, gaService, style);
			style.setFont(gaService.manageDefaultFont(diagram, false, true));
		}
		return style;
	}

}
