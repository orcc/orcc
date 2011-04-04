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
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.expr.ExpressionVisitor;
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
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	private class BinOpSeqWriter implements ExpressionVisitor {

		@Override
		public void visit(ExprBinary expr, Object... args) {
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
		public void visit(ExprBool expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Boolean");
			exprElt.setAttribute("value", expr.toString());
			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(ExprFloat expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Real");
			exprElt.setAttribute("value", expr.toString());
			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(ExprInt expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Integer");
			exprElt.setAttribute("value", expr.toString());
			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(ExprList expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "List");
			for (Expression childExpr : expr.getValue()) {
				childExpr.accept(this, args[0]);
			}
		}

		@Override
		public void visit(ExprString expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "String");
			exprElt.setAttribute("value", expr.toString());
			((Element) args[0]).appendChild(exprElt);
		}

		@Override
		public void visit(ExprUnary expr, Object... args) {
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
		public void visit(ExprVar expr, Object... args) {
			Element exprElt = document.createElement("Expr");
			String value = expr.getUse().getVariable().getName();
			exprElt.setAttribute("kind", "Var");
			exprElt.setAttribute("name", value);
			((Element) args[0]).appendChild(exprElt);
		}

		private void writeOperator(OpBinary op, Element parent) {
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
	 * The writer's output file.
	 */
	private File file;

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

		document = DomUtil.createDocument("XDF");
		writeXDF(document.getDocumentElement(), network);

		file = new File(path, network.getName() + ".xdf");
		OutputStream os;
		try {
			os = new FileOutputStream(file);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}

		try {
			DomUtil.writeDocument(os, document);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				throw new OrccException("I/O error", e);
			}
		}

		writeChildren(path);
	}

	/**
	 * Return resulting file of XDFWriter
	 * 
	 * @return the File where network is written
	 */
	public File getFile() {
		return file;
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
			OrderedMap<String, Var> variables) throws OrccException {
		for (Var variable : variables) {
			Element decl = document.createElement("Decl");
			parent.appendChild(decl);

			decl.setAttribute("kind", kind);
			decl.setAttribute("name", variable.getName());
			decl.appendChild(writeType(variable.getType()));

			if (variable.isInitialized()) {
				writeExpr(decl, variable.getInitialValue());
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
	private void writePorts(Element parent, String kind,
			OrderedMap<String, Port> ports) throws OrccException {
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
		int size;

		if (type.isBool()) {
			name = "bool";
		} else if (type.isInt()) {
			name = "int";
			size = ((TypeInt) type).getSize();
			typeElt.appendChild(writeEntry("size",
					IrFactory.eINSTANCE.createExprInt(size)));
		} else if (type.isList()) {
			name = "List";
			size = ((TypeList) type).getSize();
			type = ((TypeList) type).getType();
			typeElt.appendChild(writeEntry("type", type));
			typeElt.appendChild(writeEntry("size",
					IrFactory.eINSTANCE.createExprInt(size)));
		} else if (type.isString()) {
			name = "String";
		} else if (type.isUint()) {
			name = "uint";
			size = ((TypeUint) type).getSize();
			typeElt.appendChild(writeEntry("size",
					IrFactory.eINSTANCE.createExprInt(size)));
		} else if (type.isVoid()) {
			throw new OrccException("void type is invalid in XDF");
		} else {
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
		writeDecls(xdf, "Var", network.getVariables());

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
