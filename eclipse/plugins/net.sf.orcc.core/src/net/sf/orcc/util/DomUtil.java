/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.orcc.OrccRuntimeException;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

/**
 * This class defines utility methods to create DOM documents, to print them to
 * an output stream using DOM 3 Load Save objects and to make easier the parsing
 * of XML files.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class DomUtil {

	private static DOMImplementation impl;

	private static DOMImplementationRegistry registry;

	/**
	 * Creates a new DOM document.
	 * 
	 * @param docElt
	 *            name of the document element
	 * @return a new DOM document
	 */
	public static Document createDocument(String docElt) {
		getImplementation();
		return impl.createDocument("", docElt, null);
	}

	/**
	 * @param root
	 * @return
	 */
	public static Element getFirstElementChild(Node root) {
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				return (Element) node;
			}
			node = node.getNextSibling();
		}
		return null;
	}

	/**
	 * Creates a new instance of the DOM registry and get an implementation of
	 * DOM 3 with Load Save objects.
	 */
	private static void getImplementation() {
		try {
			if (registry == null) {
				registry = DOMImplementationRegistry.newInstance();
			}

			if (impl == null) {
				impl = registry.getDOMImplementation("Core 3.0 XML 3.0 LS");
				if (impl == null) {
					throw new OrccRuntimeException(
							"no DOM 3 implementation found");
				}
			}
		} catch (ClassNotFoundException e) {
			throw new OrccRuntimeException("DOM error", e);
		} catch (InstantiationException e) {
			throw new OrccRuntimeException("DOM error", e);
		} catch (IllegalAccessException e) {
			throw new OrccRuntimeException("DOM error", e);
		}
	}

	/**
	 * Returns the next sibling element of the given node.
	 * 
	 * @param node
	 *            the given node
	 * @return the next sibling element of the given node
	 */
	public static Element getNextElementSibling(Node node) {
		node = node.getNextSibling();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				return (Element) node;
			}
			node = node.getNextSibling();
		}
		return null;
	}

	public static Node getNode(String tagName, NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}

		return null;
	}

	/**
	 * Returns the value of the attribute for the given node.
	 * 
	 * @param attrName
	 *            the name of the attribute
	 * @param node
	 *            the given node
	 * 
	 * @return the value of the attribute for the given node
	 */
	public static String getNodeAttr(String attrName, Node node) {
		NamedNodeMap attrs = node.getAttributes();
		for (int y = 0; y < attrs.getLength(); y++) {
			Node attr = attrs.item(y);
			if (attr.getNodeName().equalsIgnoreCase(attrName)) {
				return attr.getNodeValue();
			}
		}
		return "";
	}

	/**
	 * Returns the attribute's value of the first node tagged with the given tag
	 * name from the given list of nodes.
	 * 
	 * @param tagName
	 *            the tag of the node
	 * @param attrName
	 *            the name of the attribute
	 * @param nodes
	 *            the list of the nodes
	 * @return the attribute's value of the node tagged with the given tag name
	 */
	public static String getNodeAttr(String tagName, String attrName,
			NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				getNodeAttr(attrName, node);
			}
		}

		return "";
	}

	/**
	 * Return the integer value of the given node.
	 * 
	 * @param node
	 *            a node
	 * @return the integer value of the given node
	 */
	public static int getNodeIntValue(Node node) throws NumberFormatException {
		return Integer.parseInt(getNodeValue(node));
	}

	/**
	 * Return the integer value of the node identified by the given tag from the
	 * given list of nodes.
	 * 
	 * @param tagName
	 *            a tag
	 * @param nodes
	 *            a list of nodes
	 * @return the integer value of the node identified by the given tag from
	 *         the given list of nodes
	 */
	public static int getNodeIntValue(String tagName, NodeList nodes)
			throws NumberFormatException {
		return Integer.parseInt(getNodeValue(tagName, nodes));
	}

	/**
	 * Return the string value of the given node (eventually empty).
	 * 
	 * @param node
	 *            a node
	 * @return the string value of the given node
	 */
	public static String getNodeValue(Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int x = 0; x < childNodes.getLength(); x++) {
			Node data = childNodes.item(x);
			if (data.getNodeType() == Node.TEXT_NODE)
				return data.getNodeValue();
		}
		return "";
	}

	/**
	 * Return the string value (eventually empty) of the node identified with
	 * the given tag from the given list of nodes.
	 * 
	 * @param tagName
	 *            a tag identifying the node
	 * @param nodes
	 *            a list of nodes
	 * @return the string value of the node identified with the given tag from
	 *         the given list of nodes
	 */
	public static String getNodeValue(String tagName, NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				getNodeValue(node);
			}
		}
		return "";
	}

	/**
	 * Parses the given input stream as XML and returns the corresponding DOM
	 * document.
	 * 
	 * @param is
	 *            an input stream
	 * @return a DOM document
	 */
	public static Document parseDocument(InputStream is) {
		getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl;

		// create input
		LSInput input = implLS.createLSInput();
		input.setByteStream(is);

		// parse without comments and whitespace
		LSParser builder = implLS.createLSParser(
				DOMImplementationLS.MODE_SYNCHRONOUS, null);
		DOMConfiguration config = builder.getDomConfig();
		config.setParameter("comments", false);
		config.setParameter("element-content-whitespace", false);

		return builder.parse(input);
	}

	/**
	 * Parses the given String as XML and returns the corresponding DOM
	 * document. The String is converted to an input stream so that encoding is
	 * taken into account (otherwise if we pass a String to an LSInput DOM will
	 * ignore the encoding and assume UTF-16).
	 * 
	 * @param str
	 *            a String
	 * @return a DOM document
	 */
	public static Document parseDocument(String str) {
		return parseDocument(new ByteArrayInputStream(str.getBytes()));
	}

	/**
	 * Writes the given node to the given output stream.
	 * 
	 * @param os
	 *            an output stream
	 * @param node
	 *            a DOM node
	 */
	public static void writeDocument(OutputStream os, Node node) {
		getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl;

		// serialize to XML
		LSOutput output = implLS.createLSOutput();
		output.setByteStream(os);

		// serialize the document, close the stream
		LSSerializer serializer = implLS.createLSSerializer();
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.write(node, output);
	}

	/**
	 * Returns a string representation of the given node. Like
	 * {@link #parseDocument(String)}, we use an intermediary byte array output
	 * stream so we can specify the encoding.
	 * 
	 * @param node
	 *            a DOM node
	 */
	public static String writeToString(Node node) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		writeDocument(os, node);
		return os.toString();
	}

}
