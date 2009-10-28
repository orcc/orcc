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
package net.sf.orcc.network.serialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.ICustomAttribute;
import net.sf.orcc.network.attributes.IFlagAttribute;
import net.sf.orcc.network.attributes.IStringAttribute;
import net.sf.orcc.network.attributes.ITypeAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.attributes.XmlElement;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * This class defines an XDF network writer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XDFWriter {

	/**
	 * the document being created by this writer.
	 */
	private final Document document;

	/**
	 * the graph of the network being written.
	 */
	private final DirectedGraph<Vertex, Connection> graph;

	/**
	 * Creates a new network writer with the given output directory and writes
	 * the given network to the directory this network writer was built with.
	 * This method recursively writes the networks that are children of the
	 * given network.
	 * 
	 * @param path
	 *            a file that represents the absolute path of the output
	 *            directory
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if the network could not be written
	 */
	public XDFWriter(File path, Network network) throws OrccException {
		graph = network.getGraph();

		try {
			// output
			DOMImplementationRegistry registry = DOMImplementationRegistry
					.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS) registry
					.getDOMImplementation("Core 3.0 XML 3.0 LS");

			// create document
			document = ((DOMImplementation) impl).createDocument("", "XDF",
					null);
			writeXDF(document.getDocumentElement(), network);

			// serialize to XML
			LSOutput output = impl.createLSOutput();
			File file = new File(path, network.getName() + ".xdf");
			output.setByteStream(new FileOutputStream(file));

			// serialize the document, close the stream
			LSSerializer serializer = impl.createLSSerializer();
			serializer.getDomConfig().setParameter("format-pretty-print", true);
			serializer.write(document, output);
			output.getByteStream().close();

			writeChildren(path);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		} catch (ClassCastException e) {
			throw new OrccException("DOM error", e);
		} catch (ClassNotFoundException e) {
			throw new OrccException("DOM error", e);
		} catch (InstantiationException e) {
			throw new OrccException("DOM error", e);
		} catch (IllegalAccessException e) {
			throw new OrccException("DOM error", e);
		}
	}

	/**
	 * Appends Attribute elements to the given parent element. Each attribute of
	 * the attributes map is transformed to an Attribute DOM element.
	 * 
	 * @param parent
	 *            the parent element
	 * @param attributes
	 *            a map of attributes
	 * @throws OrccException
	 */
	private void writeAttributes(Element parent,
			Map<String, IAttribute> attributes) throws OrccException {
		// sort attributes by alphabetical order
		attributes = new TreeMap<String, IAttribute>(attributes);

		for (Entry<String, IAttribute> entry : attributes.entrySet()) {
			Element attributeElt = document.createElement("Attribute");
			attributeElt.setAttribute("name", entry.getKey());

			IAttribute attribute = entry.getValue();
			String kind;
			switch (attribute.getType()) {
			case IAttribute.CUSTOM: {
				kind = ICustomAttribute.NAME;
				List<XmlElement> children = ((ICustomAttribute) attribute)
						.getValue();
				for (XmlElement element : children) {
					attributeElt.appendChild(element.getDOMElement(document));
				}
				break;
			}
			case IAttribute.FLAG:
				kind = IFlagAttribute.NAME;
				break;
			case IAttribute.STRING: {
				kind = IStringAttribute.NAME;
				String value = ((IStringAttribute) attribute).getValue();
				attributeElt.setAttribute("value", value);
				break;
			}
			case IAttribute.TYPE: {
				kind = ITypeAttribute.NAME;
				IType type = ((ITypeAttribute) attribute).getValue();
				attributeElt.appendChild(writeType(type));
				break;
			}
			case IAttribute.VALUE: {
				kind = IValueAttribute.NAME;
				IExpr expr = ((IValueAttribute) attribute).getValue();
				attributeElt.appendChild(writeExpr(expr));
				break;
			}
			default:
				throw new OrccException("unknown attribute type");
			}

			attributeElt.setAttribute("kind", kind);
			parent.appendChild(attributeElt);
		}
	}

	/**
	 * Writes the children of the network's graph to the output path
	 * 
	 * @param path
	 *            output path
	 * @throws OrccException
	 */
	private void writeChildren(File path) throws OrccException {
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isNetwork()) {
					// writes the network
					Network child = instance.getNetwork();
					new XDFWriter(path, child);
				}
			}
		}
	}

	/**
	 * Returns a Connection element that represents the given connection.
	 * 
	 * @param connection
	 *            a connection
	 * @return a Connection DOM element
	 * @throws OrccException
	 *             if the connection could not be written
	 */
	private Element writeConnection(Connection connection) throws OrccException {
		Element connectionElt = document.createElement("Connection");
		Vertex source = graph.getEdgeSource(connection);
		Vertex target = graph.getEdgeTarget(connection);
		Port srcPort = connection.getSource();
		Port tgtPort = connection.getTarget();

		writeConnectionEndpoint(connectionElt, "src", source, srcPort);
		writeConnectionEndpoint(connectionElt, "dst", target, tgtPort);

		writeAttributes(connectionElt, connection.getAttributes());

		return connectionElt;
	}

	/**
	 * Writes a connection end-point. An end-point is identified by two
	 * attributes, <code>name</code>, and <code>name + "-port"</code>.
	 * <code>name</code> can be "src" or "dst".
	 * 
	 * @param connection
	 *            a connection
	 * @return a Connection DOM element
	 * @throws OrccException
	 *             if the connection could not be written
	 */
	private void writeConnectionEndpoint(Element connectionElt, String name,
			Vertex vertex, Port port) throws OrccException {
		String portAttr;
		String vertexAttr;

		if (port == null) {
			vertexAttr = "";
			portAttr = vertex.getPort().getName();
		} else {
			vertexAttr = vertex.getInstance().getId();
			portAttr = port.getName();
		}

		connectionElt.setAttribute(name, vertexAttr);
		connectionElt.setAttribute(name + "-port", portAttr);
	}

	/**
	 * Appends Decl elements to the given parent element with the given kind.
	 * Each declaration of the variables map is transformed to a Decl DOM
	 * element.
	 * 
	 * @param parent
	 *            the parent element
	 * @param kind
	 *            the kind of declarations
	 * @param variables
	 *            an ordered map of global variables
	 * @throws OrccException
	 */
	private void writeDecls(Element parent, String kind,
			OrderedMap<GlobalVariable> variables) throws OrccException {
		for (GlobalVariable variable : variables) {
			Element decl = document.createElement("Decl");
			parent.appendChild(decl);

			decl.setAttribute("kind", kind);
			decl.setAttribute("name", variable.getName());
			decl.appendChild(writeType(variable.getType()));

			if (variable.hasValue()) {
				decl.appendChild(writeExpr(variable.getValue()));
			}
		}
	}

	/**
	 * Returns an Entry element that represents the given expression entry.
	 * 
	 * @param name
	 *            the entry name
	 * @param expr
	 *            the entry value as an expression
	 * @return an Entry DOM element
	 * @throws OrccException
	 */
	private Element writeEntry(String name, IExpr expr) throws OrccException {
		Element entry = document.createElement("Entry");
		entry.setAttribute("kind", "Expr");
		entry.setAttribute("name", name);
		entry.appendChild(writeExpr(expr));
		return entry;
	}

	/**
	 * Returns an Entry element that represents the given type entry.
	 * 
	 * @param name
	 *            the entry name
	 * @param type
	 *            the entry value as a type
	 * @return an Entry DOM element
	 * @throws OrccException
	 */
	private Element writeEntry(String name, IType type) throws OrccException {
		Element entry = document.createElement("Entry");
		entry.setAttribute("kind", "Type");
		entry.setAttribute("name", name);
		entry.appendChild(writeType(type));
		return entry;
	}

	/**
	 * Returns an Expr element that represents the given expression. This method
	 * is a big switch on the expression type. Expressions are written directly
	 * in this method, except binary and unary expressions, which are written by
	 * {@link #writeExprBinOpSeq(Element, BinaryExpr)} and
	 * {@link #writeExprUnaryOp(Element, UnaryExpr)} respectively.
	 * 
	 * @param expr
	 *            an expression
	 * @return an Expr DOM element
	 * @throws OrccException
	 */
	private Element writeExpr(IExpr expr) throws OrccException {
		Element exprElt = document.createElement("Expr");
		String kind;
		String value;

		switch (expr.getType()) {
		case IExpr.BINARY:
			kind = "BinOpSeq";
			writeExprBinOpSeq(exprElt, (BinaryExpr) expr);
			break;
		case IExpr.BOOLEAN:
			kind = "Literal";
			value = Boolean.toString(((BooleanExpr) expr).getValue());
			exprElt.setAttribute("literal-kind", "Boolean");
			exprElt.setAttribute("value", value);
			break;
		case IExpr.INT:
			kind = "Literal";
			value = Integer.toString(((IntExpr) expr).getValue());
			exprElt.setAttribute("literal-kind", "Integer");
			exprElt.setAttribute("value", value);
			break;
		case IExpr.LIST:
			kind = "List";
			for (IExpr childExpr : ((ListExpr) expr).getValue()) {
				exprElt.appendChild(writeExpr(childExpr));
			}
			break;
		case IExpr.STRING:
			kind = "Literal";
			value = ((StringExpr) expr).getValue();
			exprElt.setAttribute("literal-kind", "String");
			exprElt.setAttribute("value", value);
			break;
		case IExpr.UNARY:
			kind = "UnaryOp";
			writeExprUnaryOp(exprElt, (UnaryExpr) expr);
			break;
		case IExpr.VAR:
			kind = "Var";
			value = ((VarExpr) expr).getVar().getVariable().getName();
			exprElt.setAttribute("name", value);
			break;
		default:
			throw new OrccException("unknown expression type");
		}

		exprElt.setAttribute("kind", kind);
		return exprElt;
	}

	private void writeExprBinOpSeq(Element exprElt, BinaryExpr expr) {

	}

	private void writeExprUnaryOp(Element exprElt, UnaryExpr expr) {

	}

	/**
	 * Returns an Instance element that represents the given instance.
	 * 
	 * @param instance
	 *            an instance
	 * @return an Instance DOM element
	 * @throws OrccException
	 */
	private Element writeInstance(Instance instance) throws OrccException {
		Element instanceElt = document.createElement("Instance");
		instanceElt.setAttribute("id", instance.getId());

		// class
		Element classElt = document.createElement("Class");
		classElt.setAttribute("name", instance.getClasz());
		instanceElt.appendChild(classElt);

		// parameters
		for (Entry<String, IExpr> parameter : instance.getParameters()
				.entrySet()) {
			Element parameterElt = document.createElement("Parameter");
			parameterElt.setAttribute("name", parameter.getKey());
			parameterElt.appendChild(writeExpr(parameter.getValue()));
			instanceElt.appendChild(parameterElt);
		}

		// attributes
		writeAttributes(instanceElt, instance.getAttributes());

		return instanceElt;
	}

	/**
	 * Appends Port elements to the given parent element with the given kind.
	 * Each port of the ports map is transformed to a Port DOM element.
	 * 
	 * @param parent
	 *            the parent element
	 * @param kind
	 *            the kind of ports
	 * @param ports
	 *            an ordered map of ports
	 * @throws OrccException
	 */
	private void writePorts(Element parent, String kind, OrderedMap<Port> ports)
			throws OrccException {
		for (Port port : ports) {
			Element portElt = document.createElement("Port");
			parent.appendChild(portElt);

			portElt.setAttribute("kind", kind);
			portElt.setAttribute("name", port.getName());
			portElt.appendChild(writeType(port.getType()));
		}
	}

	/**
	 * Returns a Type element that represents the given type.
	 * 
	 * @param type
	 *            a type
	 * @return a Type DOM element
	 * @throws OrccException
	 */
	private Element writeType(IType type) throws OrccException {
		Element typeElt = document.createElement("Type");

		String name;
		IExpr size;

		switch (type.getType()) {
		case IType.BOOLEAN:
			name = "bool";
			break;
		case IType.INT:
			name = "int";
			size = ((IntType) type).getSize();
			typeElt.appendChild(writeEntry("size", size));
			break;
		case IType.LIST:
			name = "List";
			size = ((ListType) type).getSize();
			type = ((ListType) type).getElementType();
			typeElt.appendChild(writeEntry("type", type));
			typeElt.appendChild(writeEntry("size", size));
			break;
		case IType.STRING:
			name = "String";
			break;
		case IType.UINT:
			name = "uint";
			size = ((UintType) type).getSize();
			typeElt.appendChild(writeEntry("size", size));
			break;
		case IType.VOID:
			throw new OrccException("void type is invalid in XDF");
		default:
			throw new OrccException("unknown type");
		}

		typeElt.setAttribute("name", name);
		return typeElt;
	}

	/**
	 * Writes the top-level XDF element.
	 * 
	 * @param xdf
	 *            the XDF element
	 * @param network
	 *            the network
	 * @throws OrccException
	 */
	private void writeXDF(Element xdf, Network network) throws OrccException {
		xdf.setAttribute("name", network.getName());
		writePorts(xdf, "Input", network.getInputs());
		writePorts(xdf, "Output", network.getOutputs());
		writeDecls(xdf, "Param", network.getParameters());
		writeDecls(xdf, "Variable", network.getVariables());

		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				xdf.appendChild(writeInstance(vertex.getInstance()));
			}
		}

		for (Connection connection : graph.edgeSet()) {
			xdf.appendChild(writeConnection(connection));
		}
	}

}
