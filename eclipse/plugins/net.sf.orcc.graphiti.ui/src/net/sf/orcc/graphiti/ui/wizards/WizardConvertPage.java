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
package net.sf.orcc.graphiti.ui.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.editors.GraphEditor;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class provides a convert page for the save as wizard.
 * 
 * @author Matthieu Wipliez
 */
public class WizardConvertPage extends WizardPage implements IGraphTypeSettable {

	private List<Combo> edgeComboList;

	private Configuration newConfiguration;

	private ObjectType newGraphType;

	private Set<ObjectType> originalEdgeTypes;

	private Graph originalGraph;

	private Set<ObjectType> originalVertexTypes;

	private List<Combo> vertexComboList;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param selection
	 */
	public WizardConvertPage(IStructuredSelection selection) {
		super("convertGraph");

		setTitle("Convert types");

		Object obj = selection.getFirstElement();
		if (obj instanceof GraphEditor) {
			GraphEditor editor = (GraphEditor) obj;
			originalGraph = editor.getContents();

			// fills the original graph, vertex and edge types
			Configuration configuration = originalGraph.getConfiguration();
			originalEdgeTypes = configuration.getEdgeTypes();
			originalVertexTypes = configuration.getVertexTypes();
		}
	}

	private void convertEdgeTypes(Graph graph,
			Map<ObjectType, ObjectType> edgeTypes) {
		Set<Edge> edges = originalGraph.edgeSet();
		for (Edge edge : edges) {
			ObjectType newType = edgeTypes.get(edge.getType());
			if (newType != null) {
				edge = new Edge(edge);
				String sourceId = (String) edge.getSource().getValue(
						ObjectType.PARAMETER_ID);
				String targetId = (String) edge.getTarget().getValue(
						ObjectType.PARAMETER_ID);
				Vertex source = graph.findVertex(sourceId);
				Vertex target = graph.findVertex(targetId);

				if (source != null && target != null) {
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(newType);
					graph.addEdge(edge);
				}
			}
		}
	}

	private void convertVertexTypes(Graph graph,
			Map<ObjectType, ObjectType> vertexTypes) {
		Set<Vertex> vertices = originalGraph.vertexSet();
		for (Vertex vertex : vertices) {
			ObjectType newType = vertexTypes.get(vertex.getType());
			if (newType != null) {
				vertex = new Vertex(vertex);
				vertex.setType(newType);
				graph.addVertex(vertex);
			}
		}
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		layout.verticalSpacing = 9;

		createExplanationLabel(container);
		createVertexTypes(container);
		createEdgeTypes(container);

		setControl(container);
		setPageComplete(true);
	}

	/**
	 * Creates a {@link Combo} for each edge type in the original configuration.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	private void createEdgeTypes(Composite parent) {
		edgeComboList = new ArrayList<Combo>();
		for (ObjectType type : originalEdgeTypes) {
			Label label = new Label(parent, SWT.NULL);
			label.setText("Convert \"" + type.getName() + "\" to:");

			Combo edgeCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY
					| SWT.SIMPLE);
			edgeComboList.add(edgeCombo);
		}
	}

	/**
	 * Creates a {@link Label} to tell the user what to do.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	private void createExplanationLabel(Composite parent) {
		Label label = new Label(parent, SWT.NULL);
		label.setText("Please choose how the following vertices and edges "
				+ "shall be converted:");
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);
	}

	/**
	 * Creates a {@link Combo} for each vertex type in the original
	 * configuration.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	private void createVertexTypes(Composite parent) {
		vertexComboList = new ArrayList<Combo>();
		for (ObjectType type : originalVertexTypes) {
			Label label = new Label(parent, SWT.NULL);
			label.setText("Convert \"" + type.getName() + "\" to:");

			Combo vertexCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY
					| SWT.SIMPLE);
			vertexComboList.add(vertexCombo);
		}
	}

	/**
	 * Returns a {@link Map} that maps an existing {@link EdgeType} to a new
	 * one. If the new type is not specified (i.e. left blank by the user), no
	 * mapping is inserted.
	 * 
	 * @return A {@link Map} that maps an existing {@link EdgeType} to a new
	 *         one.
	 */
	private Map<ObjectType, ObjectType> fillEdgeTypes() {
		Map<ObjectType, ObjectType> edgeTypes = new HashMap<ObjectType, ObjectType>();
		int i = 0;
		for (ObjectType type : originalEdgeTypes) {
			Combo combo = edgeComboList.get(i);
			i++;
			int index = combo.getSelectionIndex();
			if (index != -1) {
				String name = combo.getItem(index);
				ObjectType newType = newConfiguration.getEdgeType(name);
				edgeTypes.put(type, newType);
			}
		}

		return edgeTypes;
	}

	/**
	 * Returns a {@link Map} that maps an existing {@link VertexType} to a new
	 * one. If the new type is not specified (i.e. left blank by the user), no
	 * mapping is inserted.
	 * 
	 * @return A {@link Map} that maps an existing {@link VertexType} to a new
	 *         one.
	 */
	private Map<ObjectType, ObjectType> fillVertexTypes() {
		Map<ObjectType, ObjectType> vertexTypes = new HashMap<ObjectType, ObjectType>();
		int i = 0;
		for (ObjectType type : originalVertexTypes) {
			Combo combo = vertexComboList.get(i);
			i++;
			int index = combo.getSelectionIndex();
			if (index != -1) {
				String name = combo.getItem(index);
				ObjectType newType = newConfiguration.getVertexType(name);
				vertexTypes.put(type, newType);
			}
		}

		return vertexTypes;
	}

	/**
	 * Returns the converted graph.
	 * 
	 * @return The converted graph.
	 */
	public Graph getGraph() {
		// creates a new empty graph with the same properties as originalGraph
		// but configuration and type, that are overridden by newConfiguration
		// and newGraphType
		Graph graph = new Graph(originalGraph, newConfiguration, newGraphType);

		// change vertex and edge types
		Map<ObjectType, ObjectType> vertexTypes = fillVertexTypes();
		Map<ObjectType, ObjectType> edgeTypes = fillEdgeTypes();
		convertVertexTypes(graph, vertexTypes);
		convertEdgeTypes(graph, edgeTypes);

		return graph;
	}

	@Override
	public void setGraphType(Configuration configuration, ObjectType type) {
		newConfiguration = configuration;
		newGraphType = type;
		((IGraphTypeSettable) getNextPage()).setGraphType(configuration, type);

		updateDescription();
		updateVertexTypes();
		updateEdgeTypes();

		getControl().pack();
	}

	/**
	 * Updates the description of this page.
	 */
	private void updateDescription() {
		setDescription("Convert \"" + originalGraph.getType().getName()
				+ "\" to \"" + newGraphType.getName() + "\".");
	}

	/**
	 * Updates each edge combo list using the new configuration's edge types.
	 */
	private void updateEdgeTypes() {
		for (Combo edgeCombo : edgeComboList) {
			Set<ObjectType> newEdges = newConfiguration.getEdgeTypes();
			String[] items = new String[newEdges.size()];
			int i = 0;
			for (ObjectType edgeType : newEdges) {
				items[i] = edgeType.getName();
				i++;
			}
			edgeCombo.setItems(items);
			edgeCombo.select(-1);
		}
	}

	/**
	 * Updates each vertex combo list using the new configuration's vertex
	 * types.
	 */
	private void updateVertexTypes() {
		for (Combo vertexCombo : vertexComboList) {
			Set<ObjectType> newVertices = newConfiguration.getVertexTypes();
			String[] items = new String[newVertices.size()];
			int i = 0;
			for (ObjectType vertexType : newVertices) {
				items[i] = vertexType.getName();
				i++;
			}
			vertexCombo.setItems(items);
			vertexCombo.select(-1);
		}
	}

}