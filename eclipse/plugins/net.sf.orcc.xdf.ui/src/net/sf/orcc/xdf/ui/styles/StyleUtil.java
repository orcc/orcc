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
	// private static final IColorConstant GREEN = new ColorConstant(77, 201,
	// 124);
	// private static final IColorConstant YELLOW = new ColorConstant(224, 218,
	// 74);
	private static final IColorConstant BLACK = new ColorConstant(0, 0, 0);
	// private static final IColorConstant WHITE = new ColorConstant(255, 255,
	// 255);
	private static final IColorConstant DARK_GREY = new ColorConstant(100, 100, 100);
	// private static final IColorConstant LIGHT_GREY = new ColorConstant(200,
	// 200, 200);

	private static final IColorConstant INSTANCE_FOREGROUND = BLACK;
	private static final IColorConstant INSTANCEPORT_BACKGROUND = DARK_GREY;
	private static final IColorConstant CONNECTION_COLOR = BLACK;

	/**
	 * Return the style used for all elements with no specific style.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style commonStyle(final Diagram diagram) {
		final String styleId = "COMMON_GENERIC";
		final IGaService gaService = Graphiti.getGaService();

		// Is style already persisted?
		Style style = gaService.findStyle(diagram, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(diagram, styleId);
			style.setLineStyle(LineStyle.SOLID);
			style.setLineVisible(true);
			style.setLineWidth(1);
			style.setTransparency(0.0);
		}
		return style;
	}

	/**
	 * Return the style used for Instance shapes.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style basicInstanceShape(final Diagram diagram) {
		final String styleId = "INSTANCE";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = commonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(true);
			gaService.setRenderingStyle(style, PredefinedColoredAreas.getSilverWhiteGlossAdaptions());
		}
		return style;
	}

	public static Style networkInstanceShape(final Diagram diagram) {
		final String styleId = "NETWORK_INSTANCE";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = commonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			gaService.setRenderingStyle(style, XdfGradients.networkGradient());
		}
		return style;
	}

	public static Style actorInstanceShape(final Diagram diagram) {
		final String styleId = "ACTOR_INSTANCE";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = commonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			gaService.setRenderingStyle(style, XdfGradients.actorGradient());
		}
		return style;
	}

	/**
	 * Return the style used for ports displayed inside an Instance Shape.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style instancePortShape(final Diagram diagram) {
		final String styleId = "INSTANCE_PORT";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = commonStyle(diagram);
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
	public static Style inputPortShape(final Diagram diagram) {
		final String styleId = "INPUT_PORT";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = commonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setLineVisible(true);
			style.setLineWidth(1);
			gaService.setRenderingStyle(style, XdfGradients.inputPortGradient());
		}
		return style;
	}

	/**
	 * Return the style used for inputs ports displayed in the network
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style outputPortShape(final Diagram diagram) {
		final String styleId = "OUTPUT_PORT";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = commonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setLineVisible(true);
			style.setLineWidth(1);
			gaService.setRenderingStyle(style, XdfGradients.outputPortGradient());
		}
		return style;
	}

	/**
	 * Return the style used for connections.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style connection(final Diagram diagram) {
		final String styleId = "CONNECTION";
		final IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		final Style parentStyle = commonStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setLineVisible(true);
			style.setLineWidth(2);
			style.setForeground(gaService.manageColor(diagram, CONNECTION_COLOR));
			style.setBackground(gaService.manageColor(diagram, CONNECTION_COLOR));
		}
		return style;
	}

	private static Style commonTextStyle(final Diagram diagram) {
		final String styleId = "COMMON_TEXT";
		final IGaService gaService = Graphiti.getGaService();

		// Is style already persisted?
		Style style = gaService.findStyle(diagram, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(diagram, styleId);
			style.setFilled(false);
			style.setAngle(0);
			style.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			style.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			style.setForeground(gaService.manageColor(diagram, INSTANCE_FOREGROUND));
			style.setFont(gaService.manageDefaultFont(diagram, false, false));
		}
		return style;
	}

	/**
	 * Return the style used for the text displaying name of an Instance.
	 * 
	 * @param diagram
	 * @return
	 */
	public static Style instanceText(final Diagram diagram) {
		final String styleId = "INSTANCE_TEXT";
		final IGaService gaService = Graphiti.getGaService();

		final Style parentStyle = commonTextStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) {
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(false);
			style.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			style.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			style.setFont(gaService.manageFont(diagram, "Arial", 9, false, true));
		}
		return style;
	}

	public static Style instancePortText(final Diagram diagram) {
		final String styleId = "INSTANCE_PORT_TEXT";
		final IGaService gaService = Graphiti.getGaService();

		final Style parentStyle = commonTextStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) {
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(false);
			style.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			style.setForeground(gaService.manageColor(diagram, INSTANCE_FOREGROUND));
		}
		return style;
	}

	public static Style portText(final Diagram diagram) {
		final String styleId = "PORT_TEXT";
		final IGaService gaService = Graphiti.getGaService();

		final Style parentStyle = commonTextStyle(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) {
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(false);
			style.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			style.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
		}
		return style;
	}
}
