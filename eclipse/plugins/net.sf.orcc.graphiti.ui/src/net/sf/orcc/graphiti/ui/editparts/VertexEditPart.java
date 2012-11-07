/*
 * Copyright (c) 2008-2011, IETR/INSA of Rennes
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.graphiti.model.AbstractObject;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.editpolicies.DeleteComponentEditPolicy;
import net.sf.orcc.graphiti.ui.editpolicies.EdgeGraphicalNodeEditPolicy;
import net.sf.orcc.graphiti.ui.editpolicies.LayoutPolicy;
import net.sf.orcc.graphiti.ui.editpolicies.VertexDirectEditPolicy;
import net.sf.orcc.graphiti.ui.figure.VertexFigure;
import net.sf.orcc.graphiti.ui.figure.shapes.IShape;
import net.sf.orcc.graphiti.ui.figure.shapes.ShapeFactory;
import net.sf.orcc.graphiti.ui.properties.ModelPropertySource;
import net.sf.orcc.graphiti.ui.properties.PropertiesConstants;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.graph.CompoundDirectedGraphLayout;
import org.eclipse.draw2d.graph.EdgeList;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.draw2d.graph.NodeList;
import org.eclipse.draw2d.graph.Subgraph;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
 * The EditPart associated to the Graph gives methods to refresh the view when a
 * property has changed.
 * 
 * @author Samuel Beaussier
 * @author Nicolas Isch
 * @author Matthieu Wipliez
 * 
 */
public class VertexEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, NodeEditPart,
		ITabbedPropertySheetPageContributor {

	private DirectEditManager directEditManager;

	/**
	 * This attribute is set by {@link GraphEditPart#addNodes}.
	 */
	private Node node;

	@Override
	public void activate() {
		super.activate();
		((Vertex) getModel()).addPropertyChangeListener(this);
	}

	/**
	 * Adds edges of this EditPart to the {@link EdgeList} of the parent.
	 * 
	 * @param edges
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void addEdges(EdgeList edges) {
		List connections = new ArrayList<EdgeEditPart>();
		connections.addAll(getSourceConnections());
		// connections.addAll(getTargetConnections());

		for (Object connection : connections) {
			if (connection instanceof EdgeEditPart) {
				EdgeEditPart dependency = (EdgeEditPart) connection;
				VertexEditPart source = (VertexEditPart) dependency.getSource();
				VertexEditPart target = (VertexEditPart) dependency.getTarget();

				if ((source != null && target != null) && (source != target)) {
					org.eclipse.draw2d.graph.Edge edge = new org.eclipse.draw2d.graph.Edge(
							source.node, target.node);
					edges.add(edge);
				}
			}
		}
	}

	/**
	 * Adds this node to its parent. In the future will also add its children if
	 * it has any.
	 * 
	 * @param nodes
	 *            The list of {@link Node} in the Draw2D Graph.
	 * @param parent
	 *            Its parent subgraph.
	 */
	@SuppressWarnings("unchecked")
	void addNodes(NodeList nodes, Subgraph parent) {
		node = new Node(this, parent);
		nodes.add(node);

		// Graphical stuff
		Figure figure = (Figure) getFigure();
		node.setSize(figure.getPreferredSize());
		node.setPadding(new Insets(35, 35, 35, 35));
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new DeleteComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LayoutPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new EdgeGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new VertexDirectEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		Vertex vertex = (Vertex) getModel();

		// Get default width and height
		int width = (Integer) vertex.getAttribute(ObjectType.ATTRIBUTE_WIDTH);
		int height = (Integer) vertex.getAttribute(ObjectType.ATTRIBUTE_HEIGHT);

		// Get dimension, color, shape
		Dimension dimension = new Dimension(width, height);
		Color color = (Color) vertex.getAttribute(ObjectType.ATTRIBUTE_COLOR);
		String name = (String) vertex.getAttribute(ObjectType.ATTRIBUTE_SHAPE);
		IShape shape = ShapeFactory.createShape(name);

		// Creates the figure with the specified properties, sets its id
		Font font = ((GraphicalEditPart) getParent()).getFigure().getFont();
		VertexFigure figure = new VertexFigure(font, dimension, color, shape);
		String id = (String) vertex.getValue(ObjectType.PARAMETER_ID);
		figure.setId(id);

		// update the figure position (if the graph has layout information)
		if ((Boolean) vertex.getParent().getValue(Graph.PROPERTY_HAS_LAYOUT)) {
			Rectangle bounds = (Rectangle) vertex
					.getValue(Vertex.PROPERTY_SIZE);
			if (bounds != null) {
				figure.setBounds(bounds);
			}
		}

		// update its size
		updatePorts(figure);

		return figure;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		((Vertex) getModel()).removePropertyChangeListener(this);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			return new ModelPropertySource((AbstractObject) getModel());
		}
		return super.getAdapter(adapter);
	}

	@Override
	public String getContributorId() {
		return PropertiesConstants.CONTRIBUTOR_ID;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getModelSourceConnections() {
		if (getModel() instanceof Vertex) {
			Vertex vertex = (Vertex) getModel();
			Graph parent = vertex.getParent();

			// we get the *output* dependencies of vertex
			Set<Edge> edges = parent.outgoingEdgesOf(vertex);

			// return the dependencies
			List dependencies = new ArrayList(edges);
			return dependencies;
		}

		return null;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getModelTargetConnections() {
		if (getModel() instanceof Vertex) {
			Vertex vertex = (Vertex) getModel();
			Graph parent = vertex.getParent();

			// we get the *input* dependencies of vertex
			Set<Edge> edges = parent.incomingEdgesOf(vertex);

			// dependencies
			List dependencies = new ArrayList(edges);
			return dependencies;
		}

		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		VertexFigure figure = (VertexFigure) getFigure();
		Edge edge = (Edge) connection.getModel();
		Connection conn = (Connection) connection.getFigure();
		return figure.getSourceAnchor(edge, conn);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		VertexFigure figure = (VertexFigure) getFigure();
		return figure.getSourceAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		VertexFigure figure = (VertexFigure) getFigure();
		Edge edge = (Edge) connection.getModel();
		Connection conn = (Connection) connection.getFigure();
		return figure.getTargetAnchor(edge, conn);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		VertexFigure figure = (VertexFigure) getFigure();
		return figure.getTargetAnchor();
	}

	@Override
	public void performRequest(Request request) {
		if (request.getType() == REQ_DIRECT_EDIT) {
			if (directEditManager == null) {
				VertexFigure figure = (VertexFigure) getFigure();
				directEditManager = new VertexDirectEditManager(this, figure);
			}
			directEditManager.show();
		} else if (request.getType() == REQ_OPEN) {
			Command command = getCommand(request);
			if (command != null && command.canExecute()) {
				command.execute();
			}
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if (propertyName.equals(Vertex.PROPERTY_SIZE)) {
			VertexFigure vertexFigure = (VertexFigure) getFigure();
			vertexFigure.setBounds((Rectangle) evt.getNewValue());
			refresh();
		} else if (propertyName.equals(Vertex.PROPERTY_SRC_VERTEX)) {
			updatePorts(getFigure());
			// refresh will cause target anchor to be rightly put
			refresh();
		} else if (propertyName.equals(Vertex.PROPERTY_DST_VERTEX)) {
			updatePorts(getFigure());
			// refresh will cause source anchor to be rightly put
			refresh();
		} else {
			// other parameters
			if (propertyName.equals(ObjectType.PARAMETER_ID)) {
				refreshVisuals();
			}

			// updates the figure size
			Vertex vertex = (Vertex) getModel();
			((VertexFigure) getFigure()).adjustSize();
			Rectangle bounds = getFigure().getBounds().getCopy();
			vertex.setValue(Vertex.PROPERTY_SIZE, bounds);
		}
	}

	@Override
	protected void refreshVisuals() {
		Vertex vertex = (Vertex) getModel();

		VertexFigure figure = (VertexFigure) getFigure();
		figure.setId((String) vertex.getValue(ObjectType.PARAMETER_ID));
	}

	/**
	 * This method is called by the {@link GraphLayoutManager}, and applies back
	 * the changes of the {@link CompoundDirectedGraphLayout} algorithm to the
	 * different figures, by setting their bounds.
	 */
	void updateFigures() {
		Vertex vertex = (Vertex) getModel();
		Rectangle bounds = (Rectangle) vertex.getValue(Vertex.PROPERTY_SIZE);
		if (bounds == null) {
			bounds = getFigure().getBounds();
		}

		Rectangle newBounds = bounds.getCopy();
		newBounds.x = node.x;
		newBounds.y = node.y;
		vertex.setValue(Vertex.PROPERTY_SIZE, newBounds);

		// Updates edges
		for (Object connection : getSourceConnections()) {
			if (connection instanceof EdgeEditPart) {
				EdgeEditPart part = (EdgeEditPart) connection;
				part.updateFigures();
			}
		}

		for (Object connection : getTargetConnections()) {
			if (connection instanceof EdgeEditPart) {
				EdgeEditPart part = (EdgeEditPart) connection;
				part.updateFigures();
			}
		}
	}

	/**
	 * Update the ports of the given figure. The figure has to be passed to the
	 * function because updatePorts can be called by createFigure, and
	 * getFigure() at this time returns null.
	 * 
	 * @param fig
	 */
	private void updatePorts(IFigure fig) {
		Vertex vertex = (Vertex) getModel();
		VertexFigure figure = (VertexFigure) fig;
		Graph parent = vertex.getParent();

		figure.resetPorts();

		for (Edge edge : parent.incomingEdgesOf(vertex)) {
			String port = (String) edge
					.getValue(ObjectType.PARAMETER_TARGET_PORT);
			figure.addInputPort(port);
		}

		for (Edge edge : parent.outgoingEdgesOf(vertex)) {
			String port = (String) edge
					.getValue(ObjectType.PARAMETER_SOURCE_PORT);
			figure.addOutputPort(port);
		}

		figure.adjustSize();
		vertex.setValue(Vertex.PROPERTY_SIZE, figure.getBounds().getCopy());
	}
}
