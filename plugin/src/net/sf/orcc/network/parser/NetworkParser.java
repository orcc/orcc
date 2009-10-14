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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.attributes.CustomAttribute;
import net.sf.orcc.network.attributes.FlagAttribute;
import net.sf.orcc.network.attributes.ICustomAttribute;
import net.sf.orcc.network.attributes.IFlagAttribute;
import net.sf.orcc.network.attributes.IStringAttribute;
import net.sf.orcc.network.attributes.ITypeAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.attributes.StringAttribute;
import net.sf.orcc.network.attributes.TypeAttribute;
import net.sf.orcc.network.attributes.ValueAttribute;
import net.sf.orcc.network.attributes.IAttribute;
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
	 * absolute file name of the XDF file
	 */
	private File file;

	private DirectedGraph<Instance, Connection> graph;

	/**
	 * list of input ports
	 */
	private OrderedMap<VarDef> inputs;

	/**
	 * map of string -> instances
	 */
	private Map<String, Instance> instances;

	/**
	 * list of output ports
	 */
	private OrderedMap<VarDef> outputs;

	/**
	 * list of parameters
	 */
	private OrderedMap<VarDef> parameters;

	/**
	 * parent path of {@link #file}
	 */
	private String path;

	/**
	 * Creates a new network parser.
	 * 
	 * @param fileName
	 *            absolute file name of an XDF file
	 */
	public NetworkParser(String fileName) {
		file = new File(fileName);
		path = file.getParent();
	}

	private void checkConnections() throws OrccException {
		Set<Connection> connections = graph.edgeSet();
		for (Connection connection : connections) {
			Instance source = graph.getEdgeSource(connection);
			Instance target = graph.getEdgeTarget(connection);
			Actor srcActor = source.getActor();
			Actor tgtActor = target.getActor();

			IType srcType = connection.getSource().getType();
			IType dstType = connection.getTarget().getType();
			if (!srcType.equals(dstType)) {
				throw new OrccException("Type error: " + srcActor + "."
						+ connection.getSource() + " is " + srcType + ", "
						+ tgtActor + "." + connection.getTarget() + " is "
						+ dstType);
			}
		}
	}

	private void checkInstances(Instance source, String src, Instance target,
			String dst) throws OrccException {
		if (source == null) {
			throw new OrccException("A Connection refers to "
					+ "a non-existent Instance: \"" + src + "\"");
		}
		if (target == null) {
			throw new OrccException("A Connection refers to "
					+ "a non-existent Instance: \"" + dst + "\"");
		}
	}

	private void checkNetwork() throws OrccException {
		if (instances.isEmpty()) {
			throw new OrccException(
					"A valid network must contain at least one instance");
		}

		checkConnections();
	}

	private void checkPorts(String src, String src_port, String dst,
			String dst_port) throws OrccException {
		if (src.isEmpty()) {
			throw new OrccException("A Connection element "
					+ "must have a valid non-empty \"src\" attribute");
		} else if (src_port.isEmpty()) {
			throw new OrccException("An Connection element "
					+ "must have a valid non-empty \"src-port\" " + "attribute");
		} else if (dst.isEmpty()) {
			throw new OrccException("An Connection element "
					+ "must have a valid non-empty \"dst\" attribute");
		} else if (dst_port.isEmpty()) {
			throw new OrccException("An Connection element "
					+ "must have a valid non-empty \"dst-port\" " + "attribute");
		}
	}

	private void checkPortsVarDef(VarDef srcPort, String src_port,
			VarDef dstPort, String dst_port) throws OrccException {
		if (srcPort == null) {
			throw new OrccException("A Connection refers to "
					+ "a non-existent source port: \"" + src_port + "\"");
		}
		if (dstPort == null) {
			throw new OrccException("A Connection refers to "
					+ "a non-existent target port: \"" + dst_port + "\"");
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
					IType type = parseType(attribute.getFirstChild());
					attr = new TypeAttribute(type);
				} else if (kind.equals(IValueAttribute.NAME)) {
					IExpr expr = parseExpr(attribute.getFirstChild());
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
					graph.addVertex(instance);
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

	private void parseConnection(Element connection) throws OrccException {
		String src = connection.getAttribute("src");
		String src_port = connection.getAttribute("src-port");
		String dst = connection.getAttribute("dst");
		String dst_port = connection.getAttribute("dst-port");

		checkPorts(src, src_port, dst, dst_port);

		Instance source = instances.get(src);
		Instance target = instances.get(dst);

		checkInstances(source, src, target, dst);

		VarDef srcPort = source.getActor().getOutput(src_port);
		VarDef dstPort = target.getActor().getInput(dst_port);

		checkPortsVarDef(srcPort, src_port, dstPort, dst_port);

		Node child = connection.getFirstChild();
		Map<String, IAttribute> attributes = parseAttributes(child);
		Connection conn = new Connection(srcPort, dstPort, attributes);
		graph.addEdge(source, target, conn);
	}

	private void parseDecl(Element decl) throws OrccException {
		// TODO parse Decl
		throw new OrccException("Decl not yet implemented");
	}

	private IExpr parseExpr(Node node) throws OrccException {
		while (node != null) {
			if (node.getNodeName().equals("Expr")) {
				Element elt = (Element) node;
				String kind = elt.getAttribute("kind");
				if (kind.equals("Literal")) {
					kind = elt.getAttribute("literal-kind");
					String value = elt.getAttribute("value");
					if (kind.equals("Boolean")) {
						return new BooleanExpr(new Location(), Boolean
								.parseBoolean(value));
					} else if (kind.equals("Integer")) {
						return new IntExpr(new Location(), Integer
								.parseInt(value));
					} else if (kind.equals("String")) {
						return new StringExpr(new Location(), value);
					} else {
						throw new OrccException("Unsupported Expr "
								+ "literal kind: \"" + kind + "\"");
					}
				} else if (kind.equals("List")) {
					List<IExpr> exprs = parseExprs(node.getFirstChild());
					return new ListExpr(new Location(), exprs);
				} else {
					throw new OrccException("Unsupported Expr kind: \"" + kind
							+ "\"");
				}
			}

			node = node.getNextSibling();
		}

		throw new OrccException("Expected a Expr element");
	}

	private List<IExpr> parseExprs(Node node) throws OrccException {
		List<IExpr> exprs = new ArrayList<IExpr>();
		while (node != null) {
			if (node.getNodeName().equals("Expr")) {
				exprs.add(parseExpr(node));
			}

			node = node.getNextSibling();
		}

		return exprs;
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

				IExpr expr = parseExpr(node.getFirstChild());
				parameters.put(name, expr);
			}

			node = node.getNextSibling();
		}

		return parameters;
	}

	/**
	 * Parses a port.
	 * 
	 * @param port
	 *            a DOM element named "Port"
	 * @throws OrccException
	 */
	private void parsePort(Element port) throws OrccException {
		String name = port.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccException("A Port has an empty name");
		}

		String kind = port.getAttribute("kind");
		if (kind.equals("Input")) {

		} else if (kind.equals("Output")) {

		} else {
			throw new OrccException("Port \"" + name + "\", invalid kind: \""
					+ kind + "\"");
		}
	}

	/**
	 * Parses the given node as an IType.
	 * 
	 * @param node
	 *            the node to parse as a type.
	 * @return a type
	 */
	private IType parseType(Node node) {
		// TODO Auto-generated method stub
		return null;
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

		graph = new DirectedMultigraph<Instance, Connection>(Connection.class);
		inputs = new OrderedMap<VarDef>();
		instances = new HashMap<String, Instance>();
		outputs = new OrderedMap<VarDef>();
		parameters = new OrderedMap<VarDef>();

		parseBody(root);
		checkNetwork();

		return new Network(name, inputs, outputs, parameters, graph);
	}
}
