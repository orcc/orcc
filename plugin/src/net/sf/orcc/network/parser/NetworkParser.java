/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.network.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.common.GlobalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.Port;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.CustomAttribute;
import net.sf.orcc.network.attributes.FlagAttribute;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.ICustomAttribute;
import net.sf.orcc.network.attributes.IFlagAttribute;
import net.sf.orcc.network.attributes.IStringAttribute;
import net.sf.orcc.network.attributes.ITypeAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.attributes.StringAttribute;
import net.sf.orcc.network.attributes.TypeAttribute;
import net.sf.orcc.network.attributes.ValueAttribute;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

/**
 * This class defines an XDF network parser.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NetworkParser {

	/**
	 * Calls the parser with args[0] as the file name.
	 * 
	 * @param args
	 *            arguments
	 * @throws OrccException
	 *             if the file could not be parsed
	 */
	public static void main(String[] args) throws OrccException {
		if (args.length == 1) {
			new NetworkParser(args[0]).parseNetwork();
		} else {
			System.err.println("Usage: NetworkParser "
					+ "<absolute path of top-level XDF network>");
		}
	}

	/**
	 * XDF expression parser.
	 */
	private final ExprParser exprParser;

	/**
	 * absolute file name of the XDF file
	 */
	private File file;

	/**
	 * the graph representing the network we are parsing
	 */
	private DirectedGraph<Vertex, Connection> graph;

	/**
	 * list of input ports
	 */
	private OrderedMap<Port> inputs;

	/**
	 * map of string -> instances
	 */
	private Map<String, Instance> instances;

	/**
	 * list of output ports
	 */
	private OrderedMap<Port> outputs;

	/**
	 * list of parameters
	 */
	private OrderedMap<GlobalVariable> parameters;

	/**
	 * parent path of {@link #file}
	 */
	private String path;

	/**
	 * XDF type parser.
	 */
	private final TypeParser typeParser;

	/**
	 * list of variables
	 */
	private OrderedMap<GlobalVariable> variables;

	/**
	 * Creates a new network parser.
	 * 
	 * @param fileName
	 *            absolute file name of an XDF file
	 */
	public NetworkParser(String fileName) {
		file = new File(fileName);
		path = file.getParent();
		exprParser = new ExprParser();
		typeParser = new TypeParser(exprParser);
	}

	/**
	 * If vertexName is not empty, returns a new Port whose name is set to
	 * portName.
	 * 
	 * @param vertexName
	 *            the name of a vertex
	 * @param portName
	 *            the name of a port
	 * @return a port, or <code>null</code> if no port should be returned
	 */
	private Port getPort(String vertexName, String portName) {
		if (vertexName.isEmpty()) {
			return null;
		} else {
			return new Port(new Location(), null, portName);
		}
	}

	/**
	 * If vertexName is empty, returns a new Vertex that contains a port from
	 * the ports map that has the name portName. If vertexName is not empty,
	 * returns a new Vertex that contains an instance from the instances map.
	 * 
	 * @param vertexName
	 *            the name of a vertex
	 * @param portName
	 *            the name of a port
	 * @param ports
	 *            a map of input ports "exclusive or" output ports
	 * @return a vertex that contains a port or an instance
	 */
	private Vertex getVertex(String vertexName, String portName, String kind,
			OrderedMap<Port> ports) throws OrccException {
		if (vertexName.isEmpty()) {
			Port port = ports.get(portName);
			if (port == null) {
				throw new OrccException("An Connection element has an invalid"
						+ " \"src-port\" " + "attribute");
			}

			return new Vertex(kind, port);
		} else {
			Instance instance = instances.get(vertexName);
			if (instance == null) {
				throw new OrccException("An Connection element has an invalid"
						+ " \"src-port\" " + "attribute");
			}

			return new Vertex(instance);
		}
	}

	/**
	 * Returns a map of attribute names -> values by parsing the "Attribute"
	 * nodes.
	 * 
	 * @param node
	 *            the first node of a node list, or <code>null</code> if the
	 *            caller had no children.
	 * @return a (possibly empty) map of attributes
	 * @throws OrccException
	 *             if an attribute could not be parsed
	 */
	private Map<String, IAttribute> parseAttributes(Node node)
			throws OrccException {
		Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();

		while (node != null) {
			// only parses Attribute nodes, other nodes are ignored.
			if (node.getNodeName().equals("Attribute")) {
				Element attribute = (Element) node;
				String kind = attribute.getAttribute("kind");
				String attrName = attribute.getAttribute("name");

				IAttribute attr;
				if (kind.equals(ICustomAttribute.NAME)) {
					attr = new CustomAttribute(attribute.getChildNodes());
				} else if (kind.equals(IFlagAttribute.NAME)) {
					attr = new FlagAttribute();
				} else if (kind.equals(IStringAttribute.NAME)) {
					String value = attribute.getAttribute("value");
					attr = new StringAttribute(value);
				} else if (kind.equals(ITypeAttribute.NAME)) {
					IType type = typeParser
							.parseType(attribute.getFirstChild()).getResult();
					attr = new TypeAttribute(type);
				} else if (kind.equals(IValueAttribute.NAME)) {
					IExpr expr = exprParser.parseExpr(node.getFirstChild());
					attr = new ValueAttribute(expr);
				} else {
					throw new OrccException("unsupported attribute kind: \""
							+ kind + "\"");
				}

				attributes.put(attrName, attr);
			}

			node = node.getNextSibling();
		}

		return attributes;
	}

	/**
	 * Parses the body of the XDF document. The body can contain any element
	 * among the supported elements. Supported elements are: Connection, Decl
	 * (kind=Param or kind=Var), Instance, Package, Port.
	 * 
	 * @param root
	 * @throws OrccException
	 */
	private void parseBody(Element root) throws OrccException {
		Node node = root.getFirstChild();
		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = node.getNodeName();
				if (name.equals("Connection")) {
					parseConnection(element);
				} else if (name.equals("Decl")) {
					parseDecl(element);
				} else if (name.equals("Instance")) {
					Instance instance = parseInstance(element);
					instances.put(instance.getId(), instance);
					graph.addVertex(new Vertex(instance));
				} else if (name.equals("Package")) {
					throw new OrccException(
							"Package elements are not supported by Orcc yet");
				} else if (name.equals("Port")) {
					parsePort(element);
				} else {
					throw new OrccException("invalid node \"" + name + "\"");
				}
			}

			node = node.getNextSibling();
		}
	}

	/**
	 * Parses the given DOM element as a connection, and adds a matching
	 * Connection to the graph of the network being parsed.
	 * 
	 * @param connection
	 *            a DOM element named "Connection"
	 * @throws OrccException
	 */
	private void parseConnection(Element connection) throws OrccException {
		String src = connection.getAttribute("src");
		String src_port = connection.getAttribute("src-port");
		String dst = connection.getAttribute("dst");
		String dst_port = connection.getAttribute("dst-port");

		Vertex source = getVertex(src, src_port, "Input", inputs);
		Port srcPort = getPort(src, src_port);
		Vertex target = getVertex(dst, dst_port, "Output", outputs);
		Port dstPort = getPort(dst, dst_port);

		Node child = connection.getFirstChild();
		Map<String, IAttribute> attributes = parseAttributes(child);
		Connection conn = new Connection(srcPort, dstPort, attributes);
		graph.addEdge(source, target, conn);
	}

	private void parseDecl(Element decl) throws OrccException {
		String kind = decl.getAttribute("kind");
		String name = decl.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccException("Decl has an empty name");
		}

		Location location = new Location();
		if (kind.equals("Param")) {
			ParseContinuation<IType> cont = typeParser.parseType(decl
					.getFirstChild());
			IType type = cont.getResult();
			GlobalVariable var = new GlobalVariable(location, type, name);
			parameters.register(file.getAbsolutePath(), location, name, var);
		} else if (kind.equals("Variable")) {
			ParseContinuation<IType> cont = typeParser.parseType(decl
					.getFirstChild());
			IType type = cont.getResult();
			IExpr expr = exprParser.parseExpr(cont.getNode());
			GlobalVariable var = new GlobalVariable(location, type, name, expr);
			parameters.register(file.getAbsolutePath(), location, name, var);
		} else {
			throw new OrccException("unsupported Decl kind: \"" + kind + "\"");
		}
	}

	/**
	 * Parses an "Instance" element and returns an {@link Instance}.
	 * 
	 * @param instance
	 *            a DOM element named "Instance".
	 * @return an instance
	 * @throws OrccException
	 *             if the instance is not well-formed
	 */
	private Instance parseInstance(Element instance) throws OrccException {
		// instance id
		String id = instance.getAttribute("id");
		if (id.isEmpty()) {
			throw new OrccException("An Instance element "
					+ "must have a valid \"id\" attribute");
		}

		// instance class
		String clasz = null;
		Node child = instance.getFirstChild();
		while (child != null) {
			if (child.getNodeName().equals("Class")) {
				clasz = ((Element) child).getAttribute("name");
				break;
			} else {
				child = child.getNextSibling();
			}
		}

		if (clasz == null || clasz.isEmpty()) {
			throw new OrccException("An Instance element "
					+ "must have a valid \"Class\" child.");
		}

		// instance parameters
		Map<String, IExpr> parameters = parseParameters(child);

		return new Instance(path, id, clasz, parameters);
	}

	/**
	 * Parses the file given to the constructor of this class.
	 * 
	 * @return a network
	 * @throws OrccException
	 *             if the file could not be parsed
	 */
	public Network parseNetwork() throws OrccException {
		try {
			// input
			DOMImplementationRegistry registry = DOMImplementationRegistry
					.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS) registry
					.getDOMImplementation("Core 3.0 XML 3.0 LS");
			LSInput input = impl.createLSInput();
			input.setByteStream(new FileInputStream(file));

			// parse without comments and whitespace
			LSParser builder = impl.createLSParser(
					DOMImplementationLS.MODE_SYNCHRONOUS, null);
			DOMConfiguration config = builder.getDomConfig();
			config.setParameter("comments", false);
			config.setParameter("element-content-whitespace", false);

			// returns the document parsed from the input
			return parseXDF(builder.parse(input));
		} catch (FileNotFoundException e) {
			throw new OrccException("I/O error when parsing network", e);
		} catch (ClassCastException e) {
			throw new OrccException("could not initialize DOM parser", e);
		} catch (ClassNotFoundException e) {
			throw new OrccException("could not initialize DOM parser", e);
		} catch (InstantiationException e) {
			throw new OrccException("could not initialize DOM parser", e);
		} catch (IllegalAccessException e) {
			throw new OrccException("could not initialize DOM parser", e);
		}
	}

	private Map<String, IExpr> parseParameters(Node node) throws OrccException {
		Map<String, IExpr> parameters = new HashMap<String, IExpr>();
		while (node != null) {
			if (node.getNodeName().equals("Parameter")) {
				String name = ((Element) node).getAttribute("name");
				if (name.isEmpty()) {
					throw new OrccException("A Parameter element "
							+ "must have a valid \"name\" attribute");
				}

				IExpr expr = exprParser.parseExpr(node.getFirstChild());
				parameters.put(name, expr);
			}

			node = node.getNextSibling();
		}

		return parameters;
	}

	/**
	 * Parses a port, and adds it to {@link #inputs} or {@link #outputs},
	 * depending on the port's kind attribute.
	 * 
	 * @param eltPort
	 *            a DOM element named "Port"
	 * @throws OrccException
	 */
	private void parsePort(Element eltPort) throws OrccException {
		Location location = new Location();
		IType type = typeParser.parseType(eltPort.getFirstChild()).getResult();
		String name = eltPort.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccException("Port has an empty name");
		}

		// creates a port
		Port port = new Port(location, type, name);

		// adds the port to inputs or outputs depending on its kind
		String kind = eltPort.getAttribute("kind");
		if (kind.equals("Input")) {
			inputs.register(file.toString(), location, name, port);
		} else if (kind.equals("Output")) {
			outputs.register(file.toString(), location, name, port);
		} else {
			throw new OrccException("Port \"" + name + "\", invalid kind: \""
					+ kind + "\"");
		}

		graph.addVertex(new Vertex(kind, port));
	}

	/**
	 * Parses the given document as an XDF network.
	 * 
	 * @param doc
	 *            a DOM document that supposedly represent an XDF network
	 * @return a Network
	 * @throws OrccException
	 *             if the DOM document is not a well-formed XDF network
	 */
	private Network parseXDF(Document doc) throws OrccException {
		Element root = doc.getDocumentElement();
		if (!root.getNodeName().equals("XDF")) {
			throw new OrccException("Expected \"XDF\" start element");
		}

		String name = root.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccException("Expected a \"name\" attribute");
		}

		graph = new DirectedMultigraph<Vertex, Connection>(Connection.class);
		inputs = new OrderedMap<Port>();
		instances = new HashMap<String, Instance>();
		outputs = new OrderedMap<Port>();
		parameters = new OrderedMap<GlobalVariable>();
		variables = new OrderedMap<GlobalVariable>();

		parseBody(root);

		if (instances.isEmpty()) {
			throw new OrccException(
					"A valid network must contain at least one instance");
		}

		return new Network(name, inputs, outputs, parameters, variables, graph);
	}

}
