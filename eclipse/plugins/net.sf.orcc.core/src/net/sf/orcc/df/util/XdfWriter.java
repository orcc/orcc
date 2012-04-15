/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
 * Copyright (c) 2012, Synflow
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
package net.sf.orcc.df.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;

import net.sf.dftools.graph.Vertex;
import net.sf.dftools.util.Attribute;
import net.sf.dftools.util.util.EcoreHelper;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
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
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.util.DomUtil;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class defines an XDF network writer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfWriter {

	/**
	 * This class defines a writer of binary operation sequences. This writer
	 * translates a binary expression tree to a binary operation sequence with
	 * respect to operator precedence.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class BinOpSeqWriter extends AbstractActorVisitor<Expression> {

		private Element parentElt;

		private int parentPrec;

		public BinOpSeqWriter(Element elt, int precedence) {
			parentElt = elt;
			parentPrec = precedence;
		}

		@Override
		public Expression caseExprBinary(ExprBinary expr) {
			int currentPrec = expr.getOp().getPrecedence();

			if (parentPrec < currentPrec) {
				// create a new Expr element
				Element exprElt = document.createElement("Expr");
				exprElt.setAttribute("kind", "BinOpSeq");
				parentElt.appendChild(exprElt);

				Element oldParent = parentElt;
				parentElt = exprElt;

				int oldPrec = parentPrec;
				parentPrec = currentPrec;

				doSwitch(expr.getE1());
				writeOperator(expr.getOp());
				doSwitch(expr.getE2());

				parentElt = oldParent;
				parentPrec = oldPrec;
			} else {
				int oldPrec = parentPrec;
				parentPrec = currentPrec;

				// append expression 1, operator, expression 2 to the parent
				doSwitch(expr.getE1());
				writeOperator(expr.getOp());
				doSwitch(expr.getE2());

				parentPrec = oldPrec;
			}

			return null;
		}

		@Override
		public Expression caseExprBool(ExprBool expr) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Boolean");
			exprElt.setAttribute("value", String.valueOf(expr.isValue()));
			parentElt.appendChild(exprElt);

			return null;
		}

		@Override
		public Expression caseExprFloat(ExprFloat expr) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Real");
			exprElt.setAttribute("value", expr.getValue().toString());
			parentElt.appendChild(exprElt);

			return null;
		}

		@Override
		public Expression caseExprInt(ExprInt expr) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "Integer");
			exprElt.setAttribute("value", expr.getValue().toString());
			parentElt.appendChild(exprElt);

			return null;
		}

		@Override
		public Expression caseExprList(ExprList expr) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "List");
			parentElt.appendChild(exprElt);

			Element oldParent = parentElt;
			parentElt = exprElt;

			for (Expression childExpr : expr.getValue()) {
				doSwitch(childExpr);
			}

			parentElt = oldParent;

			return null;
		}

		@Override
		public Expression caseExprString(ExprString expr) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "Literal");
			exprElt.setAttribute("literal-kind", "String");
			exprElt.setAttribute("value", expr.getValue());
			parentElt.appendChild(exprElt);

			return null;
		}

		@Override
		public Expression caseExprUnary(ExprUnary expr) {
			Element exprElt = document.createElement("Expr");
			exprElt.setAttribute("kind", "UnaryOp");

			Element op = document.createElement("Op");
			op.setAttribute("name", expr.getOp().getText());
			exprElt.appendChild(op);

			Element oldParent = parentElt;
			parentElt = exprElt;

			int oldPrec = parentPrec;
			parentPrec = Integer.MIN_VALUE;
			// visit the expression of this unary expression
			doSwitch(expr.getExpr());

			parentElt = oldParent;
			parentPrec = oldPrec;

			parentElt.appendChild(exprElt);

			return null;
		}

		@Override
		public Expression caseExprVar(ExprVar var) {
			Element exprElt = document.createElement("Expr");
			String value = var.getUse().getVariable().getName();
			exprElt.setAttribute("kind", "Var");
			exprElt.setAttribute("name", value);
			parentElt.appendChild(exprElt);

			return null;
		}

		private void writeOperator(OpBinary op) {
			Element opElt = document.createElement("Op");
			opElt.setAttribute("name", op.getText());
			parentElt.appendChild(opElt);
		}

	}

	/**
	 * the document being created by this writer.
	 */
	private Document document;

	/**
	 * Writes the XDF representation of the given network and its descendants in
	 * the given path.
	 * 
	 * @param path
	 *            path in which XDF files should be written
	 * @param network
	 *            a network
	 * @return the file to which the network was written
	 */
	public File write(File path, Network network) {
		document = DomUtil.createDocument("XDF");
		writeXDF(document.getDocumentElement(), network);

		File file = new File(path, network.getName() + ".xdf");
		OutputStream os;
		try {
			os = new FileOutputStream(file);
		} catch (IOException e) {
			throw new OrccRuntimeException("I/O error", e);
		}

		try {
			DomUtil.writeDocument(os, document);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				throw new OrccRuntimeException("I/O error", e);
			}
		}

		writeChildren(network, path);

		return file;
	}

	/**
	 * Writes the XDF representation of the given network to the given output
	 * stream.
	 * 
	 * @param network
	 *            a network
	 * @param os
	 *            an output stream
	 */
	public void write(Network network, OutputStream os) {
		document = DomUtil.createDocument("XDF");
		writeXDF(document.getDocumentElement(), network);

		try {
			DomUtil.writeDocument(os, document);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				throw new OrccRuntimeException("I/O error", e);
			}
		}
	}

	/**
	 * Appends Attribute elements to the given parent parent. Each attribute of
	 * the attributes map is transformed to an Attribute DOM parent.
	 * 
	 * @param parent
	 *            the parent parent
	 * @param attributes
	 *            a list of attributes
	 */
	private void writeAttributes(Element parent, EList<Attribute> attributes) {
		// sort attributes by alphabetical order
		ECollections.sort(attributes, new Comparator<Attribute>() {

			@Override
			public int compare(Attribute attr1, Attribute attr2) {
				return attr1.getName().compareTo(attr2.getName());
			}

		});

		for (Attribute attribute : attributes) {
			Element attributeElt = document.createElement("Attribute");
			attributeElt.setAttribute("name", attribute.getName());

			String kind;
			Object value = attribute.getValue();
			if (value instanceof String) {
				String str = (String) value;
				if (str.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
					// that is somewhat arbitrary, but a String is only
					// considered XML content if it starts with that prologue
					// (which written by default by DomUtil.writeToString)
					Document document = DomUtil.parseDocument(str);
					Element docElt = document.getDocumentElement();
					kind = XdfConstants.CUSTOM;

					Node imported = this.document.importNode(docElt, true);
					attributeElt.appendChild(imported);
				} else {
					kind = XdfConstants.STRING;
					attributeElt.setAttribute("value", (String) value);
				}
			} else if (value instanceof Type) {
				kind = XdfConstants.TYPE;
				Type type = (Type) value;
				attributeElt.appendChild(writeType(type));
			} else if (value instanceof Expression) {
				kind = XdfConstants.VALUE;
				Expression expr = (Expression) value;
				writeExpr(attributeElt, expr);
			} else {
				// default is flag
				kind = XdfConstants.FLAG;
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
	 */
	private void writeChildren(Network network, File path) {
		for (Instance instance : network.getInstances()) {
			if (instance.isNetwork()) {
				// writes the network
				Network child = instance.getNetwork();
				new XdfWriter().write(path, child);
			}
		}
	}

	/**
	 * Returns a Connection parent that represents the given connection.
	 * 
	 * @param connection
	 *            a connection
	 * @return a Connection DOM parent
	 */
	private Element writeConnection(Connection connection) {
		Element connectionElt = document.createElement("Connection");
		Vertex source = connection.getSource();
		Vertex target = connection.getTarget();
		Port srcPort = connection.getSourcePort();
		Port tgtPort = connection.getTargetPort();

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
	 */
	private void writeConnectionEndpoint(Element connectionElt, String name,
			Vertex vertex, Port port) {
		String portAttr;
		String vertexAttr;

		if (port == null) {
			vertexAttr = "";
			portAttr = ((Port) vertex).getName();
		} else {
			vertexAttr = EcoreHelper.getFeature(vertex, "name");
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
	 */
	private void writeDecls(Element parent, String kind, List<Var> variables) {
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
	 */
	private Element writeEntry(String name, Expression expr) {
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
	 */
	private Element writeEntry(String name, Type type) {
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
	 */
	private void writeExpr(Element parent, Expression expr) {
		new BinOpSeqWriter(parent, Integer.MIN_VALUE).doSwitch(expr);
	}

	/**
	 * Returns an Instance parent that represents the given instance.
	 * 
	 * @param instance
	 *            an instance
	 * @return an Instance DOM parent
	 */
	private Element writeInstance(Instance instance) {
		Element instanceElt = document.createElement("Instance");
		instanceElt.setAttribute("id", instance.getName());

		// class
		EObject entity = instance.getEntity();
		if (entity != null) {
			Element classElt = document.createElement("Class");
			String name = EcoreHelper.getFeature(entity, "name");
			classElt.setAttribute("name", name);
			instanceElt.appendChild(classElt);
		}

		// parameters
		for (Argument argument : instance.getArguments()) {
			Element parameterElt = document.createElement("Parameter");
			parameterElt.setAttribute("name", argument.getVariable().getName());
			writeExpr(parameterElt, argument.getValue());
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
	 */
	private void writePorts(Element parent, String kind, List<Port> ports) {
		for (Port port : ports) {
			Element portElt = document.createElement("Port");
			parent.appendChild(portElt);

			portElt.setAttribute("kind", kind);
			portElt.setAttribute("name", port.getName());
			portElt.appendChild(writeType(port.getType()));

			// attributes
			writeAttributes(portElt, port.getAttributes());
		}
	}

	/**
	 * Returns a Type parent that represents the given type.
	 * 
	 * @param type
	 *            a type
	 * @return a Type DOM parent
	 */
	private Element writeType(Type type) {
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
			throw new OrccRuntimeException("void type is invalid in XDF");
		} else {
			throw new OrccRuntimeException("unknown type");
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
	 */
	private void writeXDF(Element xdf, Network network) {
		xdf.setAttribute("name", network.getSimpleName());
		writePorts(xdf, "Input", network.getInputs());
		writePorts(xdf, "Output", network.getOutputs());
		writeDecls(xdf, "Param", network.getParameters());
		writeDecls(xdf, "Variable", network.getVariables());

		for (Instance instance : network.getInstances()) {
			xdf.appendChild(writeInstance(instance));
		}

		for (Connection connection : network.getConnections()) {
			xdf.appendChild(writeConnection(connection));
		}
	}

}
