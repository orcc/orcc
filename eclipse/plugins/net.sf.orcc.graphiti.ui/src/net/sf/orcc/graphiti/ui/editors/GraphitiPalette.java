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

import java.util.Set;

import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.ui.GraphitiUiPlugin;
import net.sf.orcc.graphiti.ui.editparts.EdgeCreationFactory;
import net.sf.orcc.graphiti.ui.editparts.VertexCreationFactory;
import net.sf.orcc.graphiti.ui.figure.VertexFigure;
import net.sf.orcc.graphiti.ui.figure.shapes.IShape;
import net.sf.orcc.graphiti.ui.figure.shapes.ShapeFactory;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Creates the Palette on the GUI with all the tools and the appropriate icons
 * icons have to be in the same directory as the Model
 * 
 * @author Samuel Beaussier & Nicolas Isch
 * 
 */
public class GraphitiPalette {

	/**
	 * Add the different edge types.
	 * 
	 * @param graph
	 *            The graph used to configure this palette.
	 * @param paletteGroup
	 *            The palette group.
	 */
	private static void addEdgeTypes(Graph graph, PaletteContainer container) {
		if (graph != null) {
			PaletteDrawer edgeDrawer = new PaletteDrawer("Connections");

			Configuration config = graph.getConfiguration();
			Set<ObjectType> edgeTypes = config.getEdgeTypes();
			for (ObjectType type : edgeTypes) {
				String typeStr = type.getName();

				ImageDescriptor id = getImgDescEdge(type);

				ToolEntry tool = new ConnectionCreationToolEntry(typeStr,
						"Create a new " + typeStr,
						new EdgeCreationFactory(type), id, ImageDescriptor
								.getMissingImageDescriptor());

				edgeDrawer.add(tool);
			}

			// Add connection tool
			container.add(edgeDrawer);
		}
	}

	/**
	 * Add the different vertex types.
	 * 
	 * @param graph
	 *            The graph used to configure this palette.
	 * @param paletteGroup
	 *            The palette group.
	 */
	private static void addVertexTypes(Graph graph, PaletteContainer container) {
		if (graph != null) {
			PaletteDrawer toolDrawer = new PaletteDrawer("Vertices");

			Configuration config = graph.getConfiguration();
			Set<ObjectType> vertexTypes = config.getVertexTypes();
			for (ObjectType type : vertexTypes) {
				String typeStr = type.getName();

				ImageDescriptor id = getImgDescVertex(type);

				ToolEntry tool = new CreationToolEntry(typeStr, "Create a new "
						+ typeStr, new VertexCreationFactory(type), id, null);

				toolDrawer.add(tool);
			}

			container.add(toolDrawer);
		}
	}

	/**
	 * Returns a new image descriptor from the given edge type.
	 * 
	 * @param type
	 *            A string representation of the edge type.
	 * @return A new {@link ImageDescriptor}.
	 */
	private static ImageDescriptor getImgDescEdge(ObjectType type) {
		ImageDescriptor id;
		Boolean directed = (Boolean) type
				.getAttribute(ObjectType.ATTRIBUTE_DIRECTED);
		if (directed == null || directed) {
			id = ImageDescriptor.createFromImage(GraphitiUiPlugin
					.getImage("icons/directed_edge.gif"));
		} else {
			id = ImageDescriptor.createFromImage(GraphitiUiPlugin
					.getImage("icons/undirected_edge.gif"));
		}

		// retrieve the color
		Color color = (Color) type.getAttribute(ObjectType.ATTRIBUTE_COLOR);
		if (color == null) {
			color = ColorConstants.black;
		}

		// replace the "black" palette entry with the color.
		ImageData data = id.getImageData();
		if (data.palette.colors != null) {
			RGB rgb = data.palette.colors[0];
			rgb.red = color.getRed();
			rgb.green = color.getGreen();
			rgb.blue = color.getBlue();
		}

		// returns an image descriptor on the modified image data.
		id = ImageDescriptor.createFromImageData(data);
		return id;
	}

	/**
	 * Returns a new image descriptor from the given vertex type.
	 * 
	 * @param type
	 *            A string representation of the vertex type.
	 * @return A new {@link ImageDescriptor}.
	 */
	private static ImageDescriptor getImgDescVertex(ObjectType type) {
		// attributes
		int width = (Integer) type.getAttribute(ObjectType.ATTRIBUTE_WIDTH);
		int height = (Integer) type.getAttribute(ObjectType.ATTRIBUTE_HEIGHT);
		Color color = (Color) type.getAttribute(ObjectType.ATTRIBUTE_COLOR);
		String name = (String) type.getAttribute(ObjectType.ATTRIBUTE_SHAPE);
		IShape shape = ShapeFactory.createShape(name);

		// adjust width and height
		double ratio = (double) width / (double) height;
		width = 16;
		height = (int) (width / ratio);

		// Creates a new vertex figure
		Font font = Display.getDefault().getSystemFont();
		VertexFigure figure = new VertexFigure(font, new Dimension(width,
				height), color, shape);

		// Creates a new image of width x height on the current display
		Image image = new Image(Display.getCurrent(), width, height);

		// Paints the figure on it
		GC gc = new GC(image);
		Graphics graphics = new SWTGraphics(gc);
		figure.paint(graphics);

		// Get the image data back
		ImageData data = image.getImageData();
		ImageDescriptor id = ImageDescriptor.createFromImageData(data);

		// Disposes image (and GC btw) and SWT graphics
		image.dispose();
		graphics.dispose();

		return id;
	}

	/**
	 * Gets a palette root which is configured by the given {@link Graph}. If
	 * <code>graph == null</code>, <code>null</code> is returned.
	 * 
	 * @param graph
	 *            The graph used to configure this palette.
	 * @return A {@link PaletteRoot} or <code>null</code>.
	 */
	public static PaletteRoot getPaletteRoot(Graph graph) {
		if (graph == null) {
			return null;
		}

		PaletteRoot paletteModel = new PaletteRoot();
		PaletteGroup toolGroup = new PaletteGroup("Tools");
		paletteModel.add(toolGroup);

		// Add a selection tool to the group
		ToolEntry tool = new SelectionToolEntry();
		toolGroup.add(tool);
		paletteModel.setDefaultEntry(tool);

		// Add a marquee tool to the group
		toolGroup.add(new MarqueeToolEntry());

		addVertexTypes(graph, paletteModel);
		addEdgeTypes(graph, paletteModel);

		return paletteModel;
	}
}
