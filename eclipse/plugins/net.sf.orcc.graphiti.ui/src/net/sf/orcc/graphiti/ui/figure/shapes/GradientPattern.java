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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

/**
 * @author mwipliez
 * 
 */
public class GradientPattern {

	/**
	 * Paints this {@link IShape} with the given background {@link Color}, in
	 * the specified {@link Rectangle} bounds, on the given {@link Graphics}. If
	 * the graphics do not have advanced capabilities (such as printer or zoom
	 * manager), the function will try to use the current display to draw the
	 * {@link IShape} as an image.
	 * 
	 * @param shape
	 * @param backgroundColor
	 * @param bounds
	 * @param graphics
	 */
	public static void paintFigure(IShape shape, Color backgroundColor,
			Rectangle bounds, Graphics graphics) {
		if (graphics instanceof SWTGraphics) {
			// advanced graphics
			Color fg = new Color(null, 224, 224, 224);

			// square gradient, from left-bottom to right-top
			int max = Math.max(bounds.width, bounds.height);
			Pattern pattern = new Pattern(backgroundColor.getDevice(), 0, max,
					max, 0, backgroundColor, 192, fg, 192);

			graphics.pushState();
			try {
				// Needs advanced capabilities or throws SWTException
				graphics.setAntialias(SWT.ON);
				graphics.setBackgroundPattern(pattern);
			} catch (RuntimeException e) {
				// No anti alias, not pattern, less pretty but it will work!
			}

			shape.paintSuperFigure(graphics);
			graphics.popState();

			// pattern is not used anymore by graphics => dispose
			pattern.dispose();
		} else {
			// ScaledGraphics and PrinterGraphics do not have advanced
			// capabilities... so we try with SWTGraphics

			// Creates a new image of width x height on the current display
			Image image = new Image(Display.getCurrent(), bounds.width,
					bounds.height);

			// Paints the figure on it using SWT graphics
			GC gc = new GC(image);
			Graphics swtGraphics = new SWTGraphics(gc);
			paintFigure(shape, backgroundColor, bounds, swtGraphics);

			// Draws the image on the original graphics
			graphics.drawImage(image, 0, 0);

			// Disposes image (and GC btw) and SWT graphics
			image.dispose();
			swtGraphics.dispose();
		}
	}
}
