/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
import java.io.OutputStream;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

/**
 * This class provides various methods to reduce the amount of copy/paste when
 * dealing with DOM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DomHelper {

	/**
	 * Creates a new document with the given namespace and document element.
	 * 
	 * @param namespaceURI
	 *            The document namespace, may be <code>""</code>.
	 * @param qualifiedName
	 *            The document element name
	 * @return the created document
	 */
	public static Document createDocument(String namespaceURI,
			String qualifiedName) {
		DOMImplementation impl = getDOMImplementation();
		return impl.createDocument(namespaceURI, qualifiedName, null);
	}

	/**
	 * Returns a DOM implementation that has the following features:
	 * <ul>
	 * <li>Core 3.0</li>
	 * <li>XML 3.0</li>
	 * <li>LS</li>
	 * </ul>
	 * 
	 * @return A {@link DOMImplementation} object that can be cast to
	 *         {@link DOMImplementationLS}.
	 */
	public static DOMImplementation getDOMImplementation() {
		try {
			DOMImplementationRegistry registry = DOMImplementationRegistry
					.newInstance();
			return registry.getDOMImplementation("Core 3.0 XML 3.0 LS");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the first child of <code>node</code> that has the given name, or
	 * <code>null</code>.
	 * 
	 * @param node
	 *            A node.
	 * @param name
	 *            The name of the node we are looking for.
	 * @return The first node whose name matches, or <code>null</code>.
	 */
	public static Node getFirstChildNamed(Node node, String name) {
		return getFirstSiblingNamed(node.getFirstChild(), name);
	}

	/**
	 * Returns the first sibling of <code>node</code>, or <code>node</code>
	 * itself, which has the given name. If none is found, the function returns
	 * <code>null</code>.
	 * 
	 * @param node
	 *            A node.
	 * @param name
	 *            The name of the node we are looking for.
	 * @return The first node whose name matches, or <code>null</code>.
	 */
	public static Node getFirstSiblingNamed(Node node, String name) {
		while (node != null && !node.getNodeName().equals(name)) {
			node = node.getNextSibling();
		}

		return node;
	}

	/**
	 * Parses the input stream and returns a DOM {@link Document}.
	 * 
	 * @param byteStream
	 *            The input stream.
	 * @return The {@link Document} parsed from the input.
	 */
	public static Document parse(InputStream byteStream) {
		// input
		DOMImplementationLS impl = (DOMImplementationLS) getDOMImplementation();
		LSInput input = impl.createLSInput();
		input.setByteStream(byteStream);

		// parse without comments and whitespace
		LSParser builder = impl.createLSParser(
				DOMImplementationLS.MODE_SYNCHRONOUS, null);
		DOMConfiguration config = builder.getDomConfig();
		config.setParameter("comments", false);
		config.setParameter("element-content-whitespace", false);

		// returns the document parsed from the input
		return builder.parse(input);
	}

	/**
	 * Writes the given document to the given output stream.
	 * 
	 * @param document
	 *            A DOM document.
	 * @param byteStream
	 *            The {@link OutputStream} to write to.
	 */
	public static void write(Document document, OutputStream byteStream) {
		DOMImplementationLS impl = (DOMImplementationLS) document
				.getImplementation();

		LSOutput output = impl.createLSOutput();
		output.setByteStream(byteStream);

		LSSerializer serializer = impl.createLSSerializer();
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.write(document, output);
	}

}
