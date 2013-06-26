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
package net.sf.orcc.graphiti.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.orcc.graphiti.model.AbstractObject;
import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.FileFormat;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Parameter;
import net.sf.orcc.graphiti.model.Transformation;
import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.geometry.Rectangle;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class provides a generic graph parser. Generic means that it can parse
 * any format (text-based or XML-based) that contains a graph.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GenericGraphParser {

	private Collection<Configuration> configurations;

	/**
	 * Creates a new parser using the given configuration list.
	 * 
	 * @param configurations
	 *            A {@link List} of {@link Configuration}s.
	 */
	public GenericGraphParser(Collection<Configuration> configurations) {
		this.configurations = configurations;
	}

	/**
	 * Checks that every vertex in <code>graph</code> has layout information. If
	 * it is the case, the {@link Graph#PROPERTY_HAS_LAYOUT} is set to
	 * <code>true</code>. Otherwise it is set to false.
	 * 
	 * @param graph
	 */
	private void checkLayout(Graph graph) {
		Set<Vertex> vertices = graph.vertexSet();
		for (Vertex vertex : vertices) {
			if (vertex.getValue(Vertex.PROPERTY_SIZE) == null) {
				graph.setValue(Graph.PROPERTY_HAS_LAYOUT, Boolean.FALSE);
				return;
			}
		}

		graph.setValue(Graph.PROPERTY_HAS_LAYOUT, Boolean.TRUE);
	}

	/**
	 * With a given configuration and file format, try to parse the file
	 * contents given as a byte input stream.
	 * 
	 * @param configuration
	 *            A supposedly valid configuration for the given contents.
	 * @param path
	 *            The file absolute path.
	 * @param in
	 *            The contents.
	 * @return A {@link Graph} if successful.
	 * @throws Exception
	 */
	private Graph parse(Configuration configuration, IFile file)
			throws Exception {
		FileFormat format = configuration.getFileFormat();
		List<Transformation> transformations = format
				.getImportTransformations();
		Element element = null;
		if (transformations.isEmpty()) {
			InputStream in = file.getContents();
			element = DomHelper.parse(in).getDocumentElement();
		} else {
			for (Transformation transformation : transformations) {
				if (transformation.isXslt()) {
					// fills the element from the input stream
					if (element == null) {
						InputStream in = file.getContents();
						element = DomHelper.parse(in).getDocumentElement();
					}

					XsltTransformer transformer = new XsltTransformer(
							configuration.getContributorId(),
							transformation.getFileName());
					transformer.setParameter("path", file.getLocation()
							.toString());
					element = transformer.transformDomToDom(element);
				} else {
					ITransformation tr = transformation.getInstance();
					return tr.transform(file);
				}
			}
		}

		return parseGraph(configuration, element);
	}

	/**
	 * Parses the given {@link IFile} and returns a graph. The file is parsed as
	 * follows:
	 * <ol>
	 * <li>Iterate through the configurations.</li>
	 * <li>In each configuration, for each file format matching the file
	 * extension, try to parse the file with the given configuration.</li>
	 * <li>If parsing fails, go to step 2.</li>
	 * </ol>
	 * 
	 * @param file
	 * @return
	 * @throws IncompatibleConfigurationFile
	 */
	public Graph parse(IFile file) throws IncompatibleConfigurationFile {
		// finds all suitable configurations
		String fileExt = file.getFileExtension();
		List<Configuration> configurations = new ArrayList<Configuration>();
		for (Configuration configuration : this.configurations) {
			FileFormat format = configuration.getFileFormat();
			if (format.getFileExtension().equals(fileExt)) {
				configurations.add(configuration);
			}
		}

		Configuration configuration;
		if (configurations.isEmpty()) {
			throw new IncompatibleConfigurationFile(
					"No configuration could parse the file");
		} else if (configurations.size() == 1) {
			configuration = configurations.get(0);
		} else {
			throw new IncompatibleConfigurationFile(
					"Many configurations could parse the file");
		}

		// parse with the configuration
		try {
			Graph graph = parse(configuration, file);
			graph.setFileName(file.getFullPath());
			return graph;
		} catch (Throwable e) {
			throw new IncompatibleConfigurationFile(
					"The file could not be parsed with the matching configuration",
					e);
		}
	}

	/**
	 * Parses the edges.
	 * 
	 * @param graph
	 *            The graph to add edges to.
	 * @param node
	 *            A child node of &lt;graph&gt;.
	 * @return The node following &lt;edges&gt;.
	 * @throws TransformedDocumentParseError
	 *             If the edges could not be parsed.
	 */
	private Node parseEdges(Graph graph, Node node)
			throws TransformedDocumentParseError {
		Configuration configuration = graph.getConfiguration();
		node = DomHelper.getFirstSiblingNamed(node, "edges");
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeName().equals("edge")) {
				Element element = (Element) child;

				String typeName = element.getAttribute("type");
				ObjectType type = configuration.getEdgeType(typeName);

				String sourceId = element.getAttribute("source");
				Vertex source = graph.findVertex(sourceId);

				String targetId = element.getAttribute("target");
				Vertex target = graph.findVertex(targetId);

				if (source == null && target == null) {
					throw new TransformedDocumentParseError("In the edge \""
							+ sourceId + "\" -> \"" + targetId + "\", \""
							+ sourceId + "\" nor \"" + targetId
							+ "\" could not be found.");
				} else if (source == null) {
					throw new TransformedDocumentParseError("In the edge \""
							+ sourceId + "\" -> \"" + targetId
							+ "\", the source vertex \"" + sourceId
							+ "\" could not be found.");
				} else if (target == null) {
					throw new TransformedDocumentParseError("In the edge \""
							+ sourceId + "\" -> \"" + targetId
							+ "\", the target vertex \"" + targetId
							+ "\" could not be found.");
				}

				Edge edge = new Edge(type, source, target);
				parseParameters(edge, type, child.getFirstChild());
				graph.addEdge(edge);
			}

			child = child.getNextSibling();
		}

		return node.getNextSibling();
	}

	/**
	 * Parses the graph.
	 * 
	 * @param configuration
	 *            The configuration to create a graph with.
	 * @param element
	 *            The &lt;graph&gt; element.
	 * @return A newly-created graph with the given configuration.
	 * @throws TransformedDocumentParseError
	 *             If <code>element</code> cannot be parsed.
	 */
	private Graph parseGraph(Configuration configuration, Element element)
			throws TransformedDocumentParseError {
		String typeName = element.getAttribute("type");
		ObjectType type = configuration.getGraphType(typeName);
		Graph graph = new Graph(configuration, type, true);

		// parse different sections
		Node node = element.getFirstChild();
		node = parseParameters(graph, type, node);
		node = parseVertices(graph, node);
		node = parseEdges(graph, node);

		checkLayout(graph);

		return graph;
	}

	/**
	 * Parses a parameter.
	 * 
	 * @param parameter
	 *            The parameter we got from the configuration.
	 * @param child
	 *            The &lt;parameter&gt; element.
	 * @return An object, either a {@link List}, a {@link Map}, an
	 *         {@link Integer}, a {@link Float}, a {@link Boolean}, or a
	 *         {@link String}. May be <code>null</code> if there are no
	 *         elements/entries and the parameter is a list/map, or if the value
	 *         field is absent or empty, and the parameter is a scalar.
	 */
	private Object parseParameter(Parameter parameter, Element child) {
		Class<?> parameterType = parameter.getType();
		if (parameterType == List.class) {
			List<String> list = new ArrayList<String>();
			Node element = child.getFirstChild();
			if (element == null) {
				return null;
			}

			while (element != null) {
				if (element.getNodeName().equals("element")) {
					String eltValue = ((Element) element).getAttribute("value");
					list.add(eltValue);
				}

				element = element.getNextSibling();
			}

			return list;
		} else if (parameterType == Map.class) {
			Map<String, String> map = new TreeMap<String, String>();
			Node element = child.getFirstChild();
			if (element == null) {
				return null;
			}

			while (element != null) {
				if (element.getNodeName().equals("entry")) {
					String key = ((Element) element).getAttribute("key");
					String value = ((Element) element).getAttribute("value");
					map.put(key, value);
				}

				element = element.getNextSibling();
			}

			return map;
		} else {
			Element element = child;
			String value = element.getAttribute("value");
			if (!element.hasAttribute("value") || value.isEmpty()) {
				return null;
			} else {
				if (parameterType == Integer.class) {
					return Integer.valueOf(value);
				} else if (parameterType == Float.class) {
					return Float.valueOf(value);
				} else if (parameterType == Boolean.class) {
					return Boolean.valueOf(value);
				} else if (parameterType == String.class) {
					return value;
				} else {
					return value;
				}
			}
		}
	}

	/**
	 * Parses the parameters and set the properties of the given
	 * <code>propertyBean</code>, that has the given type.
	 * 
	 * @param abstractObject
	 *            The target property bean.
	 * @param type
	 *            The type of <code>propertyBean</code>.
	 * @param node
	 *            A previous sibling of &lt;parameters&gt;, or
	 *            &lt;parameters&gt; itself.
	 * @return The node following &lt;parameters&gt;.
	 */
	private Node parseParameters(AbstractObject abstractObject,
			ObjectType type, Node node) {
		node = DomHelper.getFirstSiblingNamed(node, "parameters");

		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeName().equals("parameter")) {
				Element element = ((Element) child);
				String parameterName = element.getAttribute("name");
				Parameter parameter = type.getParameter(parameterName);
				Object value = parseParameter(parameter, element);
				if (value != null) {
					abstractObject.setValue(parameterName, value);
				}
			}

			child = child.getNextSibling();
		}

		return node.getNextSibling();
	}

	/**
	 * Parses the vertices.
	 * 
	 * @param graph
	 *            The graph to add vertices to.
	 * @param node
	 *            A child node of &lt;graph&gt;.
	 * @return The node following &lt;vertices&gt;.
	 */
	private Node parseVertices(Graph graph, Node node) {
		Configuration configuration = graph.getConfiguration();
		node = DomHelper.getFirstSiblingNamed(node, "vertices");
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeName().equals("vertex")) {
				String typeName = ((Element) child).getAttribute("type");
				ObjectType type = configuration.getVertexType(typeName);
				Vertex vertex = new Vertex(type);

				// set layout information if present
				String xAttr = ((Element) child).getAttribute("x");
				String yAttr = ((Element) child).getAttribute("y");
				if (!xAttr.isEmpty() && !yAttr.isEmpty()) {
					int x = Integer.parseInt(xAttr);
					int y = Integer.parseInt(yAttr);
					vertex.setValue(Vertex.PROPERTY_SIZE, new Rectangle(x, y,
							0, 0));
				}

				parseParameters(vertex, type, child.getFirstChild());
				graph.addVertex(vertex);
			}

			child = child.getNextSibling();
		}

		return node.getNextSibling();
	}

}
