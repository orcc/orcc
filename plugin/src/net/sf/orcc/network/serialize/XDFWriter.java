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
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExprVisitor;
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
	 * This class defines a writer of binary operation sequences. This writer
	 * translates a binary expression tree to a binary operation sequence with
	 * respect to operator precedence.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class BinOpSeqWriter implements ExprVisitor {

		@Override
		public void visit(BinaryExpr expr, Object... args) {
			Element parent = ((Element) args[0]);

			int parentPrec = (Integer) args[1];
			int currentPrec = expr.getOp().getPrecedence();

			if (parentPrec < currentPrec) {
				// create a new Expr element
				Element exprElt = document.createElement("Expr");
				parent.appendChild(exprElt);
				exprElt.setAttribute("kind", "BinOpSeq");

				expr.getE1().accept(this, exprElt, currentPrec);
				writeOperator(expr.getOp(), exprElt);
				expr.getE2().accept(this, exprElt, currentPrec);
			} else {
				// append expression 1, operator, expression 2 to the parent
				expr.getE1().accept(this, parent, currentPrec);
				writeOperator(expr.getOp(), parent);
				expr.getE2().accept(this, parent, currentPrec);
			}
		}

		@Override
		public void visit(BooleanExpr expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			String value = Boolean.toString(expr.getValue());
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Boolean");
			exprElt.setAttribute("value", value);
			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(IntExpr expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			String value = Integer.toString(expr.getValue());
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Integer");
			exprElt.setAttribute("value", value);
			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(ListExpr expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "List");
			for (Expression childExpr : expr.getValue()) {
				childExpr.accept(this, args[0]);
			}
		}

		@Override
		public void visit(StringExpr expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			String value = expr.getValue();
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "String");
			exprElt.setAttribute("value", value);
			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(UnaryExpr expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "UnaryOp");

			Element op = document.createElement("Op");
			op.setAttribute("name", expr.getOp().getText());
			exprElt.appendChild(op);

			// visit the expression of this unary expression
			expr.getExpr().accept(this, exprElt, Integer.MIN_VALUE);

			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(VarExpr expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			String value = expr.getVar().getVariable().getName();
			exprElt.setAttribute("kind", "Var");
			exprElt.setAttribute("name", value);
			((Element) args[0]).appendChild(exprElt);
		}

		private void writeOperator(BinaryOp op, Element parent) {
			Element opElt = document.createElement("Op");
			opElt.setAttribute("name", op.getText());
			parent.appendChild(opElt);
		}

	}

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
	 * Appends Attribute elements to the given parent parent. Each attribute of
	 * the attributes map is transformed to an Attribute DOM parent.
	 * 
	 * @param parent
	 *            the parent parent
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
				Type type = ((ITypeAttribute) attribute).getValue();
				attributeElt.appendChild(writeType(type));
				break;
			}
			case IAttribute.VALUE: {
				kind = IValueAttribute.NAME;
				Expression expr = ((IValueAttribute) attribute).getValue();
				writeExpr(attributeElt, expr);
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
	 * Returns a Connection parent that represents the given connection.
	 * 
	 * @param connection
	 *            a connection
	 * @return a Connection DOM parent
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
	 * @return a Connection DOM parent
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
	 * Appends Decl elements to the given parent parent with the given kind.
	 * Each declaration of the variables map is transformed to a Decl DOM
	 * parent.
	 * 
	 * @param parent
	 *            the parent parent
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
				writeExpr(decl, variable.getValue());
			}
		}
	}

	/**
	 * Returns an Entry parent that represents the given expression entry.
	 * 
	 * @param name
	 *            the entry name
	 * @param expr
	 *            the entry value as an expression
	 * @return an Entry DOM parent
	 * @throws OrccException
	 */
	private Element writeEntry(String name, Expression expr)
			throws OrccException {
		Element entry = document.createElement("Entry");
		entry.setAttribute("kind", "Expr");
		entry.setAttribute("name", name);
		writeExpr(entry, expr);
		return entry;
	}

	/**
	 * Returns an Entry parent that represents the given type entry.
	 * 
	 * @param name
	 *            the entry name
	 * @param type
	 *            the entry value as a type
	 * @return an Entry DOM parent
	 * @throws OrccException
	 */
	private Element writeEntry(String name, Type type) throws OrccException {
		Element entry = document.createElement("Entry");
		entry.setAttribute("kind", "Type");
		entry.setAttribute("name", name);
		entry.appendChild(writeType(type));
		return entry;
	}

	/**
	 * Appends an Expr parent that represents the given expression to the given
	 * parent. This method just creates a {@link BinOpSeqWriter} to fill the
	 * Expr parent.
	 * 
	 * @param parent
	 *            the parent parent to which an Expr parent should be added
	 * @param expr
	 *            an expression
	 * @throws OrccException
	 */
	private void writeExpr(Element parent, Expression expr)
			throws OrccException {
		expr.accept(new BinOpSeqWriter(), parent, Integer.MIN_VALUE);
	}

	/**
	 * Returns an Instance parent that represents the given instance.
	 * 
	 * @param instance
	 *            an instance
	 * @return an Instance DOM parent
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
		for (Entry<String, Expression> parameter : instance.getParameters()
				.entrySet()) {
			Element parameterElt = document.createElement("Parameter");
			parameterElt.setAttribute("name", parameter.getKey());
			writeExpr(parameterElt, parameter.getValue());
			instanceElt.appendChild(parameterElt);
		}

		// attributes
		writeAttributes(instanceElt, instance.getAttributes());

		return instanceElt;
	}

	/**
	 * Appends Port elements to the given parent parent with the given kind.
	 * Each port of the ports map is transformed to a Port DOM parent.
	 * 
	 * @param parent
	 *            the parent parent
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
	 * Returns a Type parent that represents the given type.
	 * 
	 * @param type
	 *            a type
	 * @return a Type DOM parent
	 * @throws OrccException
	 */
	private Element writeType(Type type) throws OrccException {
		Element typeElt = document.createElement("Type");

		String name;
		Expression size;

		switch (type.getType()) {
		case Type.BOOLEAN:
			name = "bool";
			break;
		case Type.INT:
			name = "int";
			size = ((IntType) type).getSize();
			typeElt.appendChild(writeEntry("size", size));
			break;
		case Type.LIST:
			name = "List";
			size = ((ListType) type).getSize();
			type = ((ListType) type).getElementType();
			typeElt.appendChild(writeEntry("type", type));
			typeElt.appendChild(writeEntry("size", size));
			break;
		case Type.STRING:
			name = "String";
			break;
		case Type.UINT:
			name = "uint";
			size = ((UintType) type).getSize();
			typeElt.appendChild(writeEntry("size", size));
			break;
		case Type.VOID:
			throw new OrccException("void type is invalid in XDF");
		default:
			throw new OrccException("unknown type");
		}

		typeElt.setAttribute("name", name);
		return typeElt;
	}

	/**
	 * Writes the top-level XDF parent.
	 * 
	 * @param xdf
	 *            the XDF parent
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
