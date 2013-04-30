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

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.graphiti.model.AbstractObject;
import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.Edge;
import net.sf.orcc.graphiti.model.FileFormat;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Parameter;
import net.sf.orcc.graphiti.model.Transformation;
import net.sf.orcc.graphiti.model.Vertex;

import org.eclipse.draw2d.geometry.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GenericGraphWriter {

	private Graph graph;

	/**
	 * Creates a writer for the given graph.
	 * 
	 * @param graph
	 *            The {@link Graph} to write.
	 */
	public GenericGraphWriter(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Writes the graph associated with this writer to the given output stream.
	 * The output file absolute path is passed to the underlying stylesheet(s).
	 * 
	 * @param path
	 *            The file absolute path.
	 * @param byteStream
	 *            The {@link OutputStream} to write to.
	 */
	public void write(String path, OutputStream byteStream) {
		Element element = null;
		Configuration configuration = graph.getConfiguration();
		FileFormat format = configuration.getFileFormat();

		List<Transformation> transformations = format
				.getExportTransformations();
		try {
			for (Transformation transformation : transformations) {
				if (transformation.isXslt()) {
					if (element == null) {
						// writes graph
						element = writeGraph();
					}
					
					XsltTransformer transformer = new XsltTransformer(
							configuration.getContributorId(),
							transformation.getFileName());
					transformer.setParameter("path", path);
					element = transformer.transformDomToDom(element);
				} else {
					transformation.getInstance().transform(graph, byteStream);
					return;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (format.getContentType().equals("text")) {
			String content = element.getTextContent();
			try {
				byteStream.write(content.getBytes());
			} catch (IOException e) {
				// byte stream is a byte array output stream
			}
		} else {
			DomHelper.write(element.getOwnerDocument(), byteStream);
		}
	}

	private void writeEdges(Element edgesElement) {
		Document document = edgesElement.getOwnerDocument();
		Set<Edge> edges = graph.edgeSet();
		for (Edge edge : edges) {
			Element edgeElement = document.createElement("edge");
			edgeElement.setAttribute("source", (String) edge.getSource()
					.getValue(ObjectType.PARAMETER_ID));
			edgeElement.setAttribute("target", (String) edge.getTarget()
					.getValue(ObjectType.PARAMETER_ID));
			edgeElement.setAttribute("type", edge.getType().getName());

			Element parameters = document.createElement("parameters");
			edgeElement.appendChild(parameters);
			writeParameters(edge, edge.getType(), parameters);

			edgesElement.appendChild(edgeElement);
		}
	}

	/**
	 * Writes the graph.
	 * 
	 * @return A &lt;graph&gt; element.
	 */
	private Element writeGraph() {
		Document document = DomHelper.createDocument("", "graph");
		Element graphElement = document.getDocumentElement();
		graphElement.setAttribute("type", graph.getType().getName());

		Element parameters = document.createElement("parameters");
		graphElement.appendChild(parameters);
		writeParameters(graph, graph.getType(), parameters);

		Element vertices = document.createElement("vertices");
		graphElement.appendChild(vertices);
		writeVertices(vertices);

		Element edges = document.createElement("edges");
		graphElement.appendChild(edges);
		writeEdges(edges);

		return graphElement;
	}

	private void writeParameters(AbstractObject abstractObject,
			ObjectType type, Element parametersElement) {
		Document document = parametersElement.getOwnerDocument();
		List<Parameter> parameters = type.getParameters();
		for (Parameter parameter : parameters) {
			Element parameterElement = document.createElement("parameter");
			String parameterName = parameter.getName();
			parameterElement.setAttribute("name", parameterName);

			Class<?> parameterType = parameter.getType();
			if (parameterType == List.class) {
				List<?> list = (List<?>) abstractObject.getValue(parameterName);
				if (list != null) {
					for (Object obj : list) {
						if (obj != null) {
							Element element = document.createElement("element");
							element.setAttribute("value", obj.toString());
							parameterElement.appendChild(element);
						}
					}
					parametersElement.appendChild(parameterElement);
				}
			} else if (parameterType == Map.class) {
				Map<?, ?> map = (Map<?, ?>) abstractObject
						.getValue(parameterName);
				if (map != null) {
					for (Entry<?, ?> entry : map.entrySet()) {
						Object key = entry.getKey();
						Object value = entry.getValue();
						if (key != null && value != null) {
							Element entryElt = document.createElement("entry");
							entryElt.setAttribute("key", key.toString());
							entryElt.setAttribute("value", value.toString());
							parameterElement.appendChild(entryElt);
						}
					}
					parametersElement.appendChild(parameterElement);
				}
			} else {
				Object value = abstractObject.getValue(parameterName);
				if (value != null) {
					parameterElement.setAttribute("value", value.toString());
					parametersElement.appendChild(parameterElement);
				}
			}
		}
	}

	private void writeVertices(Element verticesElement) {
		Document document = verticesElement.getOwnerDocument();
		Set<Vertex> vertices = graph.vertexSet();
		for (Vertex vertex : vertices) {
			Element vertexElement = document.createElement("vertex");
			vertexElement.setAttribute("type", vertex.getType().getName());

			// add layout information
			Rectangle bounds = (Rectangle) vertex
					.getValue(Vertex.PROPERTY_SIZE);
			vertexElement.setAttribute("x", String.valueOf(bounds.x));
			vertexElement.setAttribute("y", String.valueOf(bounds.y));

			// and parameters
			Element parameters = document.createElement("parameters");
			vertexElement.appendChild(parameters);
			writeParameters(vertex, vertex.getType(), parameters);

			verticesElement.appendChild(vertexElement);
		}
	}

}
