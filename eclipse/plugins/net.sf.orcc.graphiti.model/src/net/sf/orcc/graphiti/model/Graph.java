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
package net.sf.orcc.graphiti.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.Multigraph;

/**
 * This class is an attributed multigraph (allowing more than one connection
 * between any two vertices). It may be directed or not, as specified at
 * creation.
 * 
 * @author Jonathan Piat
 * @author Matthieu Wipliez
 * 
 */
public class Graph extends AbstractObject {

	/**
	 * String for the "child added" property. Set when a vertex or a port is
	 * added to a vertex.
	 */
	public static final String PROPERTY_ADD = "child added";

	/**
	 * String for the "hasLayout" property. This is a boolean property
	 * indicating if the graph has layout information or not. If it has none,
	 * the graph should be automatically laid out.
	 */
	public static final String PROPERTY_HAS_LAYOUT = "has layout";

	/**
	 * String for the "child removed" property. Set when a vertex or a port is
	 * removed from a vertex.
	 */
	public static final String PROPERTY_REMOVE = "child removed";

	/**
	 * The configuration associated with this graph.
	 */
	private Configuration configuration;

	private IPath fileName;

	private AbstractBaseGraph<Vertex, Edge> graph;

	private Map<String, Vertex> vertices;

	/**
	 * Creates a new graph, directed or not.
	 * 
	 * @param configuration
	 *            The configuration to use with this graph.
	 * @param type
	 *            The graph type.
	 * @param directed
	 *            Specifies whether the graph should be directed or not.
	 */
	public Graph(Configuration configuration, ObjectType type, boolean directed) {
		super(type);

		this.configuration = configuration;
		if (directed) {
			graph = new DirectedMultigraph<Vertex, Edge>(Edge.class);
		} else {
			graph = new Multigraph<Vertex, Edge>(Edge.class);
		}
		vertices = new HashMap<String, Vertex>();

		// set default values
		List<Parameter> parameters = type.getParameters();
		for (Parameter parameter : parameters) {
			setValue(parameter.getName(), parameter.getDefault());
		}
		
		// initially, no layout
		setValue(PROPERTY_HAS_LAYOUT, false);
	}

	/**
	 * Creates a new empty graph with the same properties as the source graph
	 * but configuration and type, that are overridden by the supplied
	 * parameters.
	 * 
	 * @param graph
	 *            The source graph.
	 * @param configuration
	 *            The configuration to use with this graph.
	 * @param type
	 *            The graph type.
	 */
	public Graph(Graph graph, Configuration configuration, ObjectType type) {
		super(graph);
		this.configuration = configuration;
		if (graph.isDirected()) {
			this.graph = new DirectedMultigraph<Vertex, Edge>(Edge.class);
		} else {
			this.graph = new Multigraph<Vertex, Edge>(Edge.class);
		}
		this.vertices = new HashMap<String, Vertex>();
		this.type = type;
	}

	/**
	 * 
	 * @param edge
	 *            The edge to add
	 * @return
	 */
	public boolean addEdge(Edge edge) {
		Vertex source = edge.getSource();
		Vertex target = edge.getTarget();
		boolean res = graph.addEdge(source, target, edge);
		source.firePropertyChange(Vertex.PROPERTY_SRC_VERTEX, null, source);
		target.firePropertyChange(Vertex.PROPERTY_DST_VERTEX, null, target);
		return res;
	}

	/**
	 * @see AbstractBaseGraph#addVertex(Vertex)
	 * @param child
	 *            The vertex to add
	 * @return true if the vertex is added, false if command failed
	 */
	public boolean addVertex(Vertex child) {
		boolean res = graph.addVertex(child);
		if (res) {
			// only updates this graph and fires property change if vertex not
			// already present in the graph

			child.parent = this;

			vertices.put((String) child.getValue(ObjectType.PARAMETER_ID),
					child);

			firePropertyChange(PROPERTY_ADD, null, child);
		}
		return res;
	}

	/**
	 * Changes the identifier of the given vertex.
	 * 
	 * @param vertex
	 *            A vertex.
	 * @param id
	 *            Its new id.
	 */
	void changeVertexId(Vertex vertex, String id) {
		String oldId = (String) vertex.getValue(ObjectType.PARAMETER_ID);
		if (oldId != null && id != null && !oldId.equals(id)) {
			vertices.remove(oldId);
			vertices.put(id, vertex);
		}
	}

	/**
	 * @see AbstractBaseGraph#edgeSet()
	 * @return
	 */
	public Set<Edge> edgeSet() {
		return graph.edgeSet();
	}

	/**
	 * Finds and returns the vertex of the graph with the given vertex id.
	 * 
	 * @param vertexId
	 *            The vertex id.
	 * @return The vertex with the given id, or <code>null</code> if not found.
	 */
	public Vertex findVertex(String vertexId) {
		return vertices.get(vertexId);
	}

	/**
	 * Returns the configuration associated with this Graph.
	 * 
	 * @return The configuration associated with this Graph.
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Returns the file in which this graph is defined.
	 * 
	 * @return the file in which this graph is defined
	 */
	public IFile getFile() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFile(fileName);
		return file;
	}

	/**
	 * Returns the name of the file in which this graph is defined.
	 * 
	 * @return the name of the file in which this graph is defined
	 */
	public IPath getFileName() {
		return fileName;
	}

	/**
	 * @see AbstractBaseGraph#incomingEdgesOf(Vertex)
	 * @param vertex
	 * @return
	 */
	public Set<Edge> incomingEdgesOf(Vertex vertex) {
		return graph.incomingEdgesOf(vertex);
	}

	/**
	 * Returns true if this graph is directed.
	 * 
	 * @return True if this graph is directed, false otherwise.
	 */
	public boolean isDirected() {
		return graph instanceof DirectedMultigraph<?, ?>;
	}

	/**
	 * @see AbstractBaseGraph#outgoingEdgesOf(Vertex)
	 * @param vertex
	 * @return
	 */
	public Set<Edge> outgoingEdgesOf(Vertex vertex) {
		return graph.outgoingEdgesOf(vertex);
	}

	/**
	 * @see AbstractBaseGraph#removeEdge(Edge)
	 * @param edge
	 * @return
	 */
	public boolean removeEdge(Edge edge) {
		Vertex source = edge.getSource();
		Vertex target = edge.getTarget();
		boolean res = graph.removeEdge(edge);
		source.firePropertyChange(Vertex.PROPERTY_SRC_VERTEX, source, null);
		target.firePropertyChange(Vertex.PROPERTY_DST_VERTEX, target, null);
		return res;
	}

	/**
	 * @see AbstractBaseGraph#removeVertex(Graph)
	 * @param child
	 * @return
	 */
	public boolean removeVertex(Vertex child) {
		boolean res = graph.removeVertex(child);
		child.parent = null;

		vertices.remove(child.getValue(ObjectType.PARAMETER_ID));

		firePropertyChange(PROPERTY_REMOVE, null, child);
		return res;
	}

	/**
	 * Sets the name of the file in which this graph is defined.
	 * 
	 * @param fileName
	 *            a file name
	 */
	public void setFileName(IPath fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return type.getName();
	}

	/**
	 * @see AbstractBaseGraph#vertexSet()
	 * @return
	 */
	public Set<Vertex> vertexSet() {
		return graph.vertexSet();
	}

}
