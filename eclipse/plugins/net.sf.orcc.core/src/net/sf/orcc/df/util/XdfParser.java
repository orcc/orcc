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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Vertex;
import net.sf.dftools.util.Attribute;
import net.sf.dftools.util.UtilFactory;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.util.BinOpSeqParser;
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class defines an XDF network parser.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfParser {
	
	/**
	 * This class defines a type entry.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class Entry {

		/**
		 * expression entry
		 */
		public static final int EXPR = 1;

		/**
		 * type entry
		 */
		public static final int TYPE = 2;

		/**
		 * the contents of this entry: expression or type.
		 */
		private Object content;

		/**
		 * the type of this entry
		 */
		private int type;

		/**
		 * Creates a new expression entry
		 * 
		 * @param expr
		 *            an expression
		 */
		public Entry(Expression expr) {
			this.content = expr;
			this.type = EXPR;
		}

		/**
		 * Creates a new type entry
		 * 
		 * @param type
		 *            a type
		 */
		public Entry(Type type) {
			this.content = type;
			this.type = TYPE;
		}

		/**
		 * Returns this entry's content as an expression
		 * 
		 * @return this entry's content as an expression
		 */
		public Expression getEntryAsExpr() {
			if (getType() == EXPR) {
				return (Expression) content;
			} else {
				throw new OrccRuntimeException(
						"this entry does not contain an expression");
			}
		}

		/**
		 * Returns this entry's content as a type
		 * 
		 * @return this entry's content as a type
		 */
		public Type getEntryAsType() {
			if (getType() == TYPE) {
				return (Type) content;
			} else {
				throw new OrccRuntimeException("this entry does not contain a type");
			}
		}

		/**
		 * Returns the type of this entry.
		 * 
		 * @return the type of this entry
		 */
		public int getType() {
			return type;
		}

	}

	/**
	 * This class defines a parser of XDF expressions.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ExprParser {

		/**
		 * Parses the given node as an expression and returns the matching
		 * Expression expression.
		 * 
		 * @param node
		 *            a node whose expected to be, or whose sibling is expected
		 *            to be, a DOM element named "Expr".
		 * @return an expression
		 */
		public Expression parseExpr(Node node) {
			ParseContinuation<Expression> cont = parseExprCont(node);
			Expression expr = cont.getResult();
			if (expr == null) {
				throw new OrccRuntimeException("Expected an Expr element");
			} else {
				return expr;
			}
		}

		/**
		 * Parses the given node as a binary operator and returns a parse
		 * continuation with the operator parsed.
		 * 
		 * @param node
		 *            a node that is expected, or whose sibling is expected, to
		 *            be a DOM element named "Op".
		 * @return a parse continuation with the operator parsed
		 */
		private ParseContinuation<OpBinary> parseExprBinaryOp(Node node) {
			while (node != null) {
				if (node.getNodeName().equals("Op")) {
					Element op = (Element) node;
					String name = op.getAttribute("name");
					return new ParseContinuation<OpBinary>(node,
							OpBinary.getOperator(name));
				}

				node = node.getNextSibling();
			}

			return new ParseContinuation<OpBinary>(node, null);
		}

		/**
		 * Parses the given node and its siblings as a sequence of binary
		 * operations, aka "BinOpSeq". A BinOpSeq is a sequence of expr, op,
		 * expr, op, expr...
		 * 
		 * @param node
		 *            the first child node of a Expr kind="BinOpSeq" element
		 * @return a parse continuation with a BinaryExpr
		 */
		private ParseContinuation<Expression> parseExprBinOpSeq(Node node) {
			List<Expression> expressions = new ArrayList<Expression>();
			List<OpBinary> operators = new ArrayList<OpBinary>();

			ParseContinuation<Expression> contE = parseExprCont(node);
			expressions.add(contE.getResult());
			node = contE.getNode();
			while (node != null) {
				ParseContinuation<OpBinary> contO = parseExprBinaryOp(node);
				OpBinary op = contO.getResult();
				node = contO.getNode();
				if (op != null) {
					operators.add(op);

					contE = parseExprCont(node);
					Expression expr = contE.getResult();
					if (expr == null) {
						throw new OrccRuntimeException(
								"Expected an Expr element");
					}

					expressions.add(expr);
					node = contE.getNode();
				}
			}

			Expression expr = BinOpSeqParser.parse(expressions, operators);
			return new ParseContinuation<Expression>(node, expr);
		}

		/**
		 * Parses the given node as an expression and returns the matching
		 * Expression expression.
		 * 
		 * @param node
		 *            a node whose sibling is expected to be a DOM element named
		 *            "Expr".
		 * @return an expression
		 */
		private ParseContinuation<Expression> parseExprCont(Node node) {
			Expression expr = null;
			while (node != null) {
				if (node.getNodeName().equals("Expr")) {
					Element elt = (Element) node;
					String kind = elt.getAttribute("kind");
					if (kind.equals("BinOpSeq")) {
						return parseExprBinOpSeq(elt.getFirstChild());
					} else if (kind.equals("Literal")) {
						expr = parseExprLiteral(elt);
						break;
					} else if (kind.equals("List")) {
						List<Expression> exprs = parseExprs(node
								.getFirstChild());
						expr = IrFactory.eINSTANCE.createExprList(exprs);
						break;
					} else if (kind.equals("UnaryOp")) {
						ParseContinuation<OpUnary> cont = parseExprUnaryOp(node
								.getFirstChild());
						OpUnary op = cont.getResult();
						Expression unaryExpr = parseExpr(cont.getNode());
						expr = IrFactory.eINSTANCE.createExprUnary(op,
								unaryExpr, null);
						break;
					} else if (kind.equals("Var")) {
						String name = elt.getAttribute("name");
						// look up variable, in variables scope, and if not
						// found in parameters scope
						Var var = network.getVariable(name);
						if (var == null) {
							var = network.getParameter(name);
						}

						if (var == null) {
							throw new OrccRuntimeException("In network \""
									+ network.getName()
									+ "\": unknown variable: \"" + name + "\"");
						}
						expr = IrFactory.eINSTANCE.createExprVar(var);
						break;
					} else {
						throw new OrccRuntimeException("In network \""
								+ network.getName()
								+ "\": Unsupported Expr kind: \"" + kind + "\"");
					}
				}

				node = node.getNextSibling();
			}

			return new ParseContinuation<Expression>(node, expr);
		}

		/**
		 * Parses the given "Expr" element as a literal and returns the matching
		 * Expression expression.
		 * 
		 * @param elt
		 *            a DOM element named "Expr"
		 * @return an expression
		 */
		private Expression parseExprLiteral(Element elt) {
			String kind = elt.getAttribute("literal-kind");
			String value = elt.getAttribute("value");
			if (kind.equals("Boolean")) {
				return IrFactory.eINSTANCE.createExprBool(Boolean
						.parseBoolean(value));
			} else if (kind.equals("Character")) {
				throw new OrccRuntimeException("Characters not supported yet");
			} else if (kind.equals("Integer")) {
				return IrFactory.eINSTANCE.createExprInt(Long.parseLong(value));
			} else if (kind.equals("Real")) {
				return IrFactory.eINSTANCE.createExprFloat(Float
						.parseFloat(value));
			} else if (kind.equals("String")) {
				return IrFactory.eINSTANCE.createExprString(value);
			} else {
				throw new OrccRuntimeException("Unsupported Expr "
						+ "literal kind: \"" + kind + "\"");
			}
		}

		private List<Expression> parseExprs(Node node) {
			List<Expression> exprs = new ArrayList<Expression>();
			while (node != null) {
				if (node.getNodeName().equals("Expr")) {
					exprs.add(parseExpr(node));
				}

				node = node.getNextSibling();
			}

			return exprs;
		}

		/**
		 * Parses the given node as a unary operator and returns a parse
		 * continuation with the operator parsed.
		 * 
		 * @param node
		 *            a node that is expected, or whose sibling is expected, to
		 *            be a DOM element named "Op".
		 * @return a parse continuation with the operator parsed
		 */
		private ParseContinuation<OpUnary> parseExprUnaryOp(Node node) {
			while (node != null) {
				if (node.getNodeName().equals("Op")) {
					Element op = (Element) node;
					String name = op.getAttribute("name");
					return new ParseContinuation<OpUnary>(node,
							OpUnary.getOperator(name));
				}

				node = node.getNextSibling();
			}

			throw new OrccRuntimeException("Expected an Op element");
		}

	}

	/**
	 * This class defines a parse continuation, by storing the next node that
	 * shall be parsed along with the result already computed.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class ParseContinuation<T> {

		final private Node node;

		final private T result;

		/**
		 * Creates a new parse continuation with the given DOM node and result.
		 * The constructor stores the next sibling of node.
		 * 
		 * @param node
		 *            a node that will be used to resume parsing after the
		 *            result has been stored
		 * @param result
		 *            the result
		 */
		public ParseContinuation(Node node, T result) {
			if (node == null) {
				this.node = null;
			} else {
				this.node = node.getNextSibling();
			}
			this.result = result;
		}

		/**
		 * Returns the node stored in this continuation.
		 * 
		 * @return the node stored in this continuation
		 */
		public Node getNode() {
			return node;
		}

		/**
		 * Returns the result stored in this continuation.
		 * 
		 * @return the result stored in this continuation
		 */
		public T getResult() {
			return result;
		}

	}

	/**
	 * This class defines a parser of XDF types.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class TypeParser {

		/**
		 * Default size of an signed/unsigned integer.
		 */
		private static final int defaultSize = 32;

		/**
		 * Parses the given node as an Type.
		 * 
		 * @param node
		 *            the node to parse as a type.
		 * @return a type
		 */
		public ParseContinuation<Type> parseType(Node node) {
			while (node != null) {
				if (node.getNodeName().equals("Type")) {
					Element eltType = (Element) node;
					String name = eltType.getAttribute("name");
					if (name.equals(TypeBool.NAME)) {
						return new ParseContinuation<Type>(node,
								IrFactory.eINSTANCE.createTypeBool());
					} else if (name.equals(TypeInt.NAME)) {
						Map<String, Entry> entries = parseTypeEntries(node
								.getFirstChild());
						Expression expr = parseTypeSize(entries);
						int size = new ExpressionEvaluator()
								.evaluateAsInteger(expr);
						return new ParseContinuation<Type>(node,
								IrFactory.eINSTANCE.createTypeInt(size));
					} else if (name.equals(TypeList.NAME)) {
						return new ParseContinuation<Type>(node,
								parseTypeList(node));
					} else if (name.equals(TypeString.NAME)) {
						return new ParseContinuation<Type>(node,
								IrFactory.eINSTANCE.createTypeString());
					} else if (name.equals(TypeUint.NAME)) {
						Map<String, Entry> entries = parseTypeEntries(node
								.getFirstChild());
						Expression expr = parseTypeSize(entries);
						int size = new ExpressionEvaluator()
								.evaluateAsInteger(expr);

						TypeUint type = IrFactory.eINSTANCE.createTypeUint();
						type.setSize(size);
						return new ParseContinuation<Type>(node, type);
					} else {
						throw new OrccRuntimeException("unknown type name: \""
								+ name + "\"");
					}
				}

				node = node.getNextSibling();
			}

			throw new OrccRuntimeException("Expected a Type element");
		}

		/**
		 * Parses the node and its siblings as type entries, and returns a map
		 * of entry names to contents.
		 * 
		 * @param node
		 *            The first node susceptible to be an entry, or
		 *            <code>null</code>.
		 * @return A map of entry names to contents.
		 */
		private Map<String, Entry> parseTypeEntries(Node node) {
			Map<String, Entry> entries = new HashMap<String, Entry>();
			while (node != null) {
				if (node.getNodeName().equals("Entry")) {
					Element element = (Element) node;
					String name = element.getAttribute("name");
					String kind = element.getAttribute("kind");

					Entry entry = null;
					if (kind.equals("Expr")) {
						Expression expr = exprParser.parseExpr(node
								.getFirstChild());
						entry = new Entry(expr);
					} else if (kind.equals("Type")) {
						entry = new Entry(parseType(node.getFirstChild())
								.getResult());
					} else {
						throw new OrccRuntimeException(
								"unsupported entry type: \"" + kind + "\"");
					}

					entries.put(name, entry);
				}

				node = node.getNextSibling();
			}

			return entries;
		}

		/**
		 * Parses a List type.
		 * 
		 * @param node
		 *            the Type node where this List is defined
		 * @return a ListType
		 */
		private Type parseTypeList(Node node) {
			Map<String, Entry> entries = parseTypeEntries(node.getFirstChild());
			Entry entry = entries.get("size");
			if (entry == null) {
				throw new OrccRuntimeException(
						"List type must have a \"size\" entry");
			}
			Expression expr = entry.getEntryAsExpr();

			entry = entries.get("type");
			if (entry == null) {
				throw new OrccRuntimeException(
						"List type must have a \"type\" entry");
			}
			Type type = entry.getEntryAsType();

			int size = new ExpressionEvaluator().evaluateAsInteger(expr);
			return IrFactory.eINSTANCE.createTypeList(size, type);
		}

		/**
		 * Gets a "size" entry from the given entry map, if found return its
		 * value, otherwise return {@link #defaultSize}.
		 * 
		 * @param entries
		 *            a map of entries
		 * @return an expression
		 */
		private Expression parseTypeSize(Map<String, Entry> entries) {
			Entry entry = entries.get("size");
			if (entry == null) {
				return IrFactory.eINSTANCE.createExprInt(defaultSize);
			} else {
				return entry.getEntryAsExpr();
			}
		}

	}

	/**
	 * XDF expression parser.
	 */
	private final ExprParser exprParser;

	private Network network;

	/**
	 * XDF type parser.
	 */
	private final TypeParser typeParser;

	private IFile file;

	/**
	 * Creates a new network parser.
	 */
	public XdfParser(Resource resource) {
		exprParser = new ExprParser();
		typeParser = new TypeParser();

		URI uri = resource.getURI();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		file = root.getFile(new Path(uri.toPlatformString(true)));
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
	private Port getPort(Vertex vertex, String dir, String portName) {
		if (vertex instanceof Port) {
			return null;
		} else {
			URI uri = EcoreUtil.getURI(((Instance) vertex).getEntity());
			uri = uri.appendFragment("//@" + dir + "." + portName);
			Port proxy = DfFactory.eINSTANCE.createPort();
			((InternalEObject) proxy).eSetProxyURI(uri);
			return proxy;
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
	 * @param kind
	 *            the kind of port
	 * @return a vertex that contains a port or an instance
	 */
	private Vertex getVertex(String vertexName, String portName, String kind) {
		if (vertexName.isEmpty()) {
			Port port;
			if ("Input".equals(kind)) {
				port = network.getInput(portName);
			} else {
				port = network.getOutput(portName);
			}
			if (port == null) {
				throw new OrccRuntimeException(
						"An Connection element has an invalid"
								+ " \"src-port\" " + "attribute");
			}

			return port;
		} else {
			Instance instance = network.getInstance(vertexName);
			if (instance == null) {
				throw new OrccRuntimeException(
						"An Connection element has an invalid"
								+ " \"src-port\" " + "attribute");
			}

			return instance;
		}
	}

	/**
	 * Parses the "Attribute" nodes.
	 * 
	 * @param attributes
	 *            a list of attributes to fill
	 * @param node
	 *            the first node of a node list, or <code>null</code> if the
	 *            caller had no children.
	 */
	private void parseAttributes(EList<Attribute> attributes, Node node) {
		while (node != null) {
			// only parses Attribute nodes, other nodes are ignored.
			if (node.getNodeName().equals("Attribute")) {
				Element attribute = (Element) node;
				String kind = attribute.getAttribute("kind");
				String attrName = attribute.getAttribute("name");
				UtilFactory factory = UtilFactory.eINSTANCE;

				Attribute attr;
				if (kind.equals(XdfConstants.CUSTOM)) {
					// TODO custom
					attr = factory.createAttribute(attrName,
							factory.createWrapperXml());
				} else if (kind.equals(XdfConstants.FLAG)) {
					attr = factory.createAttribute(attrName, null);
				} else if (kind.equals(XdfConstants.STRING)) {
					String value = attribute.getAttribute("value");
					attr = factory.createAttribute(attrName,
							factory.createWrapperString(value));
				} else if (kind.equals(XdfConstants.TYPE)) {
					Type type = typeParser.parseType(attribute.getFirstChild())
							.getResult();
					attr = factory.createAttribute(attrName, type);
				} else if (kind.equals(XdfConstants.VALUE)) {
					Expression expr = exprParser
							.parseExpr(node.getFirstChild());
					attr = factory.createAttribute(attrName, expr);
				} else {
					throw new OrccRuntimeException(
							"unsupported attribute kind: \"" + kind + "\"");
				}

				attributes.add(attr);
			}

			node = node.getNextSibling();
		}
	}

	/**
	 * Parses the body of the XDF document. The body can contain any element
	 * among the supported elements. Supported elements are: Connection, Decl
	 * (kind=Param or kind=Var), Instance, Package, Port.
	 * 
	 * @param root
	 */
	private void parseBody(Element root) {
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
					parseInstance(element);
				} else if (name.equals("Package")) {
					throw new OrccRuntimeException(
							"Package elements are not supported by Orcc yet");
				} else if (name.equals("Port")) {
					parsePort(element);
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
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
	 */
	private void parseConnection(Element connection) {
		String src = connection.getAttribute("src");
		String src_port = connection.getAttribute("src-port");
		String dst = connection.getAttribute("dst");
		String dst_port = connection.getAttribute("dst-port");

		Vertex source = getVertex(src, src_port, "Input");
		Port srcPort = getPort(source, "outputs", src_port);
		Vertex target = getVertex(dst, dst_port, "Output");
		Port dstPort = getPort(target, "inputs", dst_port);

		Node child = connection.getFirstChild();
		Connection conn = DfFactory.eINSTANCE.createConnection(source, srcPort,
				target, dstPort);
		parseAttributes(conn.getAttributes(), child);
		network.getConnections().add(conn);
	}

	private void parseDecl(Element decl) {
		String kind = decl.getAttribute("kind");
		String name = decl.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccRuntimeException("Decl has an empty name");
		}

		if (kind.equals("Param")) {
			ParseContinuation<Type> cont = typeParser.parseType(decl
					.getFirstChild());
			Type type = cont.getResult();
			Var var = IrFactory.eINSTANCE.createVar(0, type, name, true, false);
			network.getParameters().add(var);
		} else if (kind.equals("Variable")) {
			ParseContinuation<Type> cont = typeParser.parseType(decl
					.getFirstChild());
			Type type = cont.getResult();
			Expression expr = exprParser.parseExpr(cont.getNode());
			Var var = IrFactory.eINSTANCE.createVar(0, type, name, false, expr);
			network.getVariables().add(var);
		} else {
			throw new OrccRuntimeException("unsupported Decl kind: \"" + kind
					+ "\"");
		}
	}

	/**
	 * Parses an "Instance" element and returns an {@link Instance}.
	 * 
	 * @param instance
	 *            a DOM element named "Instance".
	 * @return an instance
	 */
	private void parseInstance(Element element) {
		// instance id
		String id = element.getAttribute("id");
		if (id.isEmpty()) {
			throw new OrccRuntimeException("An Instance element "
					+ "must have a valid \"id\" attribute");
		}

		// instance class
		String clasz = null;
		Node child = element.getFirstChild();
		while (child != null) {
			if (child.getNodeName().equals("Class")) {
				clasz = ((Element) child).getAttribute("name");
				break;
			} else {
				child = child.getNextSibling();
			}
		}

		if (clasz == null || clasz.isEmpty()) {
			throw new OrccRuntimeException("An Instance element "
					+ "must have a valid \"Class\" child.");
		}

		Entity proxy = null;
		IProject project = file.getProject();
		IFile file = OrccUtil.getFile(project, clasz, "ir");
		if (file == null || !file.exists()) {
			file = OrccUtil.getFile(project, clasz, "xdf");
			if (file != null && file.exists()) {
				proxy = DfFactory.eINSTANCE.createNetwork();
			}
		} else {
			proxy = DfFactory.eINSTANCE.createActor();
		}

		if (proxy == null) {
			throw new OrccRuntimeException("Actor or network \"" + clasz
					+ "\" not found!\nIf this actor/network has errors,"
					+ " please correct them and try again; otherwise, try to "
					+ "refresh/clean projects.");
		}

		// create instance
		Instance instance = DfFactory.eINSTANCE.createInstance();
		instance.setName(id);

		// create proxy
		String pathName = file.getFullPath().toString();
		URI uri = URI.createPlatformResourceURI(pathName, true);
		uri = uri.appendFragment("/0");
		instance.setEntity(proxy);
		((InternalEObject) proxy).eSetProxyURI(uri);

		// instance parameters and attributes
		parseParameters(instance, child);
		parseAttributes(instance.getAttributes(), child);

		// add instance
		network.add(instance);
	}

	/**
	 * Parses the file given to the constructor of this class.
	 * 
	 * @return a network
	 */
	public Network parseNetwork(InputStream inputStream) {
		try {
			// input
			Document document = DomUtil.parseDocument(inputStream);

			// parse the input, return the network
			parseXDF(document);
			return network;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new OrccRuntimeException(
						"I/O error when parsing network", e);
			}
		}
	}

	private void parseParameters(Instance instance, Node node) {
		while (node != null) {
			if (node.getNodeName().equals("Parameter")) {
				String name = ((Element) node).getAttribute("name");
				if (name.isEmpty()) {
					throw new OrccRuntimeException("A Parameter element "
							+ "must have a valid \"name\" attribute");
				}

				Expression expr = exprParser.parseExpr(node.getFirstChild());

				// create proxy to variable
				URI uri = EcoreUtil.getURI(instance.getEntity());
				uri = uri.appendFragment("//@parameters." + name);
				Var proxy = IrFactory.eINSTANCE.createVar();
				((InternalEObject) proxy).eSetProxyURI(uri);

				// create argument
				Argument argument = DfFactory.eINSTANCE.createArgument(proxy,
						expr);

				instance.getArguments().add(argument);
			}

			node = node.getNextSibling();
		}
	}

	/**
	 * Parses a port, and adds it to {@link #inputs} or {@link #outputs},
	 * depending on the port's kind attribute.
	 * 
	 * @param eltPort
	 *            a DOM element named "Port"
	 */
	private void parsePort(Element eltPort) {
		ParseContinuation<Type> cont = typeParser.parseType(eltPort
				.getFirstChild());
		Type type = cont.getResult();
		String name = eltPort.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccRuntimeException("Port has an empty name");
		}

		boolean native_ = false;
		Node node = cont.getNode();
		while (node != null) {
			if (node.getNodeName().equals("Note")) {
				Element note = (Element) node;
				if ("native".equals(note.getAttribute("kind"))) {
					native_ = true;
					break;
				}
			}
			node = node.getNextSibling();
		}

		// creates a port
		Port port = DfFactory.eINSTANCE.createPort(type, name, native_);

		// adds the port to inputs or outputs depending on its kind
		String kind = eltPort.getAttribute("kind");
		if (kind.equals("Input")) {
			network.addInput(port);
		} else if (kind.equals("Output")) {
			network.addOutput(port);
		} else {
			throw new OrccRuntimeException("Port \"" + name
					+ "\", invalid kind: \"" + kind + "\"");
		}
	}

	/**
	 * Parses the given document as an XDF network.
	 * 
	 * @param doc
	 *            a DOM document that supposedly represent an XDF network
	 */
	private void parseXDF(Document doc) throws OrccRuntimeException {
		Element root = doc.getDocumentElement();
		if (!root.getNodeName().equals("XDF")) {
			throw new OrccRuntimeException("Expected \"XDF\" start element");
		}

		String name = root.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccRuntimeException("Expected a \"name\" attribute");
		}

		this.network = DfFactory.eINSTANCE.createNetwork();
		String qName = OrccUtil.getQualifiedName(file);
		network.setName(qName);
		network.setFileName(file.getFullPath().toString());

		parseBody(root);
	}

}
