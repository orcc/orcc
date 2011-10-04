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
package net.sf.orcc.network.attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * This class defines an XML element. The purpose of this class is simply to
 * avoid direct reference to the original DOM document's nodes. Indeed, in the
 * DOM a node references its owner, thus an {@link ICustomAttribute} will still
 * reference the DOM document that contained the network where the attribute was
 * declared.
 * 
 * <p>
 * Our model of an XML element is quite simple. An element may have attributes
 * as a string to string map, and may have children as a list.
 * </p>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XmlElement {

	private Map<String, String> attributes;

	private List<XmlElement> children;

	private String name;

	/**
	 * Creates an empty XML element.
	 */
	public XmlElement() {
		attributes = new HashMap<String, String>();
		children = new ArrayList<XmlElement>();
	}

	/**
	 * Creates a new XML element from the given DOM element
	 * 
	 * @param element
	 *            a DOM element
	 */
	public XmlElement(Element element) {
		this();

		this.name = element.getTagName();

		// add attributes
		NamedNodeMap attrs = element.getAttributes();
		int n = attrs.getLength();
		for (int i = 0; i < n; i++) {
			Node node = attrs.item(i);
			attributes.put(node.getNodeName(), node.getNodeValue());
		}

		// add children
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				XmlElement child = new XmlElement((Element) node);
				children.add(child);
			}

			node = node.getNextSibling();
		}
	}

	/**
	 * Returns the value of the attribute with the given name.
	 * 
	 * @param name
	 *            attribute name
	 * @return the value of the attribute with the given name
	 */
	public String getAttribute(String name) {
		return attributes.get(name);
	}

	/**
	 * Returns the attributes of this element.
	 * 
	 * @return the attributes of this element
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Returns the children of this element.
	 * 
	 * @return the children of this element
	 */
	public List<XmlElement> getChildren() {
		return children;
	}

	/**
	 * Converts this element to a DOM element. This method recursively converts
	 * the children of this element and appends them to the DOM element
	 * returned. The DOM element returned must be appended to an existing DOM
	 * element in the given document to be manipulated.
	 * 
	 * @param document
	 *            the document in which the DOM elements will be created
	 * @return a DOM element
	 */
	public Element getDOMElement(Document document) {
		Element element = document.createElement(getName());
		for (Entry<String, String> entry : attributes.entrySet()) {
			element.setAttribute(entry.getKey(), entry.getValue());
		}

		for (XmlElement child : children) {
			element.appendChild(child.getDOMElement(document));
		}

		return element;
	}

	/**
	 * Returns the name of this object.
	 * 
	 * @return the name of this object
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

}
