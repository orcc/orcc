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

import net.sf.orcc.graphiti.model.AbstractObject;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.ui.editpolicies.LayoutPolicy;
import net.sf.orcc.graphiti.ui.properties.ModelPropertySource;
import net.sf.orcc.graphiti.ui.properties.PropertiesConstants;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.CompoundDirectedGraphLayout;
import org.eclipse.draw2d.graph.EdgeList;
import org.eclipse.draw2d.graph.NodeList;
import org.eclipse.draw2d.graph.Subgraph;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
 * This class extends {@link AbstractGraphicalEditPart} by setting its figure
 * layout manager to {@link GraphLayoutManager}. It also extends the
 * {@link EditPart#isSelectable()} method to return false, causing the selection
 * tool to act like the marquee tool when no particular children has been
 * selected.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GraphEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, ITabbedPropertySheetPageContributor {

	/**
	 * The subgraph associated with this graph edit part. Set by
	 * {@link GraphEditPart#addNodes}.
	 */
	private Subgraph subgraph;

	@Override
	public void activate() {
		super.activate();
		((Graph) getModel()).addPropertyChangeListener(this);
	}

	/**
	 * Adds edges of this EditPart to the {@link EdgeList} of the parent.
	 * 
	 * @param edges
	 *            A list of edges in the graph.
	 */
	void addEdges(EdgeList edges) {
		for (Object child : getChildren()) {
			if (child instanceof VertexEditPart) {
				VertexEditPart part = (VertexEditPart) child;
				part.addEdges(edges);
			}
		}
	}

	/**
	 * Adds nodes of this EditPart to the {@link NodeList} of the parent. This
	 * method sets size and padding for all nodes. Subgraph have insets equal to
	 * 20 (top) and 0 otherwise.
	 * 
	 * @param nodes
	 *            A list of nodes in the graph.
	 * @param parent
	 *            If non-null, the parent subgraph.
	 */
	@SuppressWarnings("unchecked")
	void addNodes(NodeList nodes) {
		subgraph = new Subgraph(this);
		subgraph.innerPadding = new Insets(0, 0, 0, 0);
		subgraph.insets = new Insets(20, 0, 0, 0);
		Figure figure = (Figure) getFigure();
		subgraph.setSize(figure.getPreferredSize());
		subgraph.setPadding(new Insets(2, 2, 2, 2));
		nodes.add(subgraph);

		for (Object child : getChildren()) {
			if (child instanceof VertexEditPart) {
				VertexEditPart part = (VertexEditPart) child;
				part.addNodes(nodes, subgraph);
			}
		}
	}

	/**
	 * Automatically layouts the graphs, vertices and edges in this graphiti
	 * document edit part.
	 * 
	 * @param direction
	 *            The direction, one of:
	 *            <UL>
	 *            <LI>{@link org.eclipse.draw2d.PositionConstants#EAST}
	 *            <LI>{@link org.eclipse.draw2d.PositionConstants#SOUTH}
	 *            </UL>
	 */
	public void automaticallyLayoutGraphs(int direction) {
		LayoutManager layoutMgr = new GraphLayoutManager(this, direction);
		layoutMgr.layout(getFigure());

		getFigure().setLayoutManager(new FreeformLayout());
		getFigure().revalidate();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new LayoutPolicy());
	}

	@Override
	protected IFigure createFigure() {
		// The figure associated with this graph edit part is only a
		// free form layer
		Figure f = new FreeformLayer();
		f.setLayoutManager(new FreeformLayout());

		// Create the static router for the connection layer
		ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		ShortestPathConnectionRouter router = new ShortestPathConnectionRouter(
				f);
		router.setSpacing(2);
		// ManhattanConnectionRouter router = new ManhattanConnectionRouter();
		connLayer.setConnectionRouter(router);

		return f;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		((Graph) getModel()).removePropertyChangeListener(this);
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
	public List<Object> getModelChildren() {
		Graph graph = (Graph) getModel();
		List<Object> children = new ArrayList<Object>();
		children.addAll(graph.vertexSet());
		return children;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Graph.PROPERTY_ADD)) {
			refresh();
		} else if (evt.getPropertyName().equals(Graph.PROPERTY_REMOVE)) {
			refresh();
		}
	}

	/**
	 * This method is called by the {@link GraphLayoutManager}, and applies back
	 * the changes of the {@link CompoundDirectedGraphLayout} algorithm to the
	 * different figures, by setting their bounds.
	 */
	void updateFigures() {
		for (Object child : getChildren()) {
			if (child instanceof VertexEditPart) {
				VertexEditPart part = (VertexEditPart) child;
				part.updateFigures();
			}
		}
	}

}
