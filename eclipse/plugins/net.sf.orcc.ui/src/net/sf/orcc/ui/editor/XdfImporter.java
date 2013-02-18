/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.ui.editor;

import static net.sf.orcc.graphiti.GraphitiModelPlugin.getDefault;
import static net.sf.orcc.graphiti.model.ObjectType.PARAMETER_ID;
import static net.sf.orcc.graphiti.model.ObjectType.PARAMETER_REFINEMENT;
import static net.sf.orcc.graphiti.model.ObjectType.PARAMETER_SOURCE_PORT;
import static net.sf.orcc.graphiti.model.ObjectType.PARAMETER_TARGET_PORT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graphiti.io.LayoutReader;
import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.common.collect.Iterables;

/**
 * This class defines a transformation from a file containing an XDF network to
 * a Graphiti graph.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfImporter {

	private Map<net.sf.orcc.graph.Vertex, Vertex> vertexMap;

	private void addConnections(Graph graph, ObjectType type,
			List<Connection> connections) {
		for (Connection connection : connections) {
			Port srcPort = connection.getSourcePort();
			Port tgtPort = connection.getTargetPort();
			net.sf.orcc.graph.Vertex srcVertex = connection.getSource();
			net.sf.orcc.graph.Vertex tgtVertex = connection.getTarget();

			Vertex source = vertexMap.get(srcVertex);
			Vertex target = vertexMap.get(tgtVertex);

			Edge edge = new Edge(type, source, target);
			if (srcPort != null) {
				edge.setValue(PARAMETER_SOURCE_PORT, srcPort.getName());
			}
			if (tgtPort != null) {
				edge.setValue(PARAMETER_TARGET_PORT, tgtPort.getName());
			}

			// buffer size
			Integer size = connection.getSize();
			if (size != null) {
				edge.setValue("buffer size", size);
			}

			graph.addEdge(edge);
		}
	}

	private void addParameters(Graph graph, Network network) {
		@SuppressWarnings("unchecked")
		List<Object> parameters = (List<Object>) graph
				.getValue("network parameter");
		for (Var parameter : network.getParameters()) {
			Type type = parameter.getType();
			String name = parameter.getName();
			parameters.add(type.toString() + " " + name);
		}
	}

	private void addVariables(Graph graph, Network network) {
		@SuppressWarnings("unchecked")
		Map<Object, Object> parameters = (Map<Object, Object>) graph
				.getValue("network variable declaration");
		for (Var variable : network.getVariables()) {
			Type type = variable.getType();
			String name = variable.getName();
			Expression value = variable.getInitialValue();
			parameters.put(type.toString() + " " + name,
					new ExpressionPrinter().doSwitch(value));
		}
	}

	private void addVertices(Graph graph, Network network) {
		Configuration configuration = graph.getConfiguration();
		for (Port port : Iterables.concat(network.getInputs(),
				network.getOutputs())) {
			String kind = (network.getInputs().contains(port)) ? "Input"
					: "Output";
			ObjectType type = configuration.getVertexType(kind + " port");

			Vertex vertex = new Vertex(type);
			vertex.setValue("port type", port.getType().toString());
			vertex.setValue("native", port.isNative());
			vertex.setValue(PARAMETER_ID, port.getName());

			// add vertex
			vertexMap.put(port, vertex);
			graph.addVertex(vertex);
		}

		for (net.sf.orcc.graph.Vertex v : network.getChildren()) {
			Instance instance = (Instance) v;
			Vertex vertex = getVertex(instance,
					configuration.getVertexType("Instance"));

			// add vertex
			vertexMap.put(instance, vertex);
			graph.addVertex(vertex);
		}
	}

	private Vertex getVertex(Instance instance, ObjectType type) {
		Vertex vertex = new Vertex(type);
		vertex.setValue(PARAMETER_ID, instance.getName());
		EObject entity = instance.getEntity();

		if (entity != null) {
			String name = EcoreHelper.getFeature(entity, "name");
			if (name != null) {
				vertex.setValue(PARAMETER_REFINEMENT, name);
			}
		}

		// parameters
		Map<String, String> parameters = new HashMap<String, String>();
		vertex.setValue("instance parameter", parameters);
		for (Argument argument : instance.getArguments()) {
			parameters.put(argument.getVariable().getName(),
					new ExpressionPrinter().doSwitch(argument.getValue()));
		}

		// attributes
		Attribute partName = instance.getAttribute("partName");
		if (partName != null) {
			vertex.setValue("partName", new ExpressionPrinter()
					.doSwitch(partName.getReferencedValue()));
		}
		Attribute skip = instance.getAttribute("skip");
		if (skip != null) {
			vertex.setValue("skip", true);
		}

		return vertex;
	}

	/**
	 * Transforms the given file to a Graphiti graph.
	 * 
	 * @param file
	 *            a file
	 * @return a graph
	 */
	public Graph transform(IFile file) {
		if (!file.exists()) {
			throw new OrccRuntimeException("The file " + file.getName()
					+ " does not exist.");
		}

		vertexMap = new HashMap<net.sf.orcc.graph.Vertex, Vertex>();

		ResourceSet set = new ResourceSetImpl();
		Network network = EcoreHelper.getEObject(set, file);

		Configuration configuration = getDefault().getConfiguration("XDF");
		ObjectType type = configuration.getGraphType("XML Dataflow Network");
		Graph graph = new Graph(configuration, type, true);

		graph.setValue(ObjectType.PARAMETER_ID, network.getSimpleName());

		addParameters(graph, network);
		addVariables(graph, network);

		addVertices(graph, network);
		addConnections(graph, configuration.getEdgeType("Connection"),
				network.getConnections());

		// read layout
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		file = root.getFile(file.getFullPath().removeFileExtension()
				.addFileExtension("layout"));
		if (file.exists()) {
			try {
				new LayoutReader().read(graph, file.getContents());
			} catch (CoreException e) {
				throw new OrccRuntimeException("error when reading layout", e);
			}
		}

		return graph;
	}

}
