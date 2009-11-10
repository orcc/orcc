/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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

package net.sf.orcc.backends.xlim;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XlimNodeTemplate {

	/**
	 * Add a attribute if the value is not null
	 * 
	 * @param element
	 *            Element where to add the attribute
	 * @param attribute
	 *            Attribute name
	 * @param value
	 *            Attribute value
	 */
	private static void addNotNull(Element element, String attribute,
			String value) {
		if (value != null) {
			element.setAttribute(attribute, value);
		}
	}

	/**
	 * Create actor port
	 * 
	 * @param root
	 *            Root Element
	 * @param dir
	 *            Port direction
	 * @param name
	 *            Port name
	 * @return Created actor port
	 */
	public static Element newActorPort(Element root, String dir, String name) {
		Element port = root.getOwnerDocument().createElement("actor-port");
		port.setAttribute("dir", dir);
		port.setAttribute("name", name);
		root.appendChild(port);
		return port;
	}

	/**
	 * Create the design
	 * 
	 * @param document
	 *            Document where to add the design
	 * @param name
	 *            Name of the design
	 * @return Created design
	 */
	public static Element newDesign(Document document, String name) {
		Element design = document.createElement("design");
		design.setAttribute("name", name);
		document.appendChild(design);
		return design;
	}

	/**
	 * New In Port
	 * 
	 * @param root
	 *            Root Element
	 * @param source
	 *            Source of the port
	 * @param qualifier
	 *            PHI qualifier
	 * @return New In Port
	 */
	public static Element newInPHIPort(Element root, String source,
			String qualifier) {
		Element result = newPort(root, "in", source, null, null);
		result.setAttribute("qualifier", qualifier);
		return result;
	}

	/**
	 * New In Port
	 * 
	 * @param root
	 *            Root Element
	 * @param source
	 *            Source of the port
	 * @return New In Port
	 */
	public static Element newInPort(Element root, String source) {
		return newPort(root, "in", source, null, null);
	}

	/**
	 * New Module
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of module
	 * @return New Module
	 */
	public static Element newModule(Element root, String kind) {
		return newModule(root, kind, null, null, null, null);
	}

	/**
	 * New Module
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of module
	 * @param autostart
	 *            Autostarting ? (optional)
	 * @param name
	 *            Module name (optional)
	 * @param sourcename
	 *            Source name (optional)
	 * @param decision
	 *            Decision (test)
	 * @return New Module
	 */
	public static Element newModule(Element root, String kind,
			String autostart, String name, String sourcename, String decision) {
		Element module = root.getOwnerDocument().createElement("module");
		module.setAttribute("kind", kind);

		addNotNull(module, "autostart", autostart);
		addNotNull(module, "name", name);
		addNotNull(module, "sourcename", sourcename);
		addNotNull(module, "decision", decision);

		root.appendChild(module);
		return (module);
	}

	/**
	 * New Operation
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of operation
	 */
	public static Element newOperation(Element root, String kind) {
		return newOperation(root, kind, null, null, null);
	}

	/**
	 * New Operation
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of operation
	 * @param portName
	 *            Port name of operation (optional)
	 * @param target
	 *            Target of operation (optional)
	 * @param value
	 *            Value of operation (optional)
	 * @return New created operation
	 */
	public static Element newOperation(Element root, String kind,
			String portName, String target, String value) {
		Element operation = root.getOwnerDocument().createElement("operation");
		operation.setAttribute("kind", kind);

		addNotNull(operation, "portName", portName);
		addNotNull(operation, "target", target);
		addNotNull(operation, "value", value);

		root.appendChild(operation);
		return operation;
	}

	/**
	 * New Out Port
	 * 
	 * @param root
	 *            Root Element
	 * @param source
	 *            Source of the port
	 * @param size
	 *            Size of data
	 * @param typeName
	 *            Type of data
	 * @return New Out Port
	 */
	public static Element newOutPort(Element root, String source, String size,
			String typeName) {
		return newPort(root, "out", source, size, typeName);
	}

	/**
	 * New Port
	 * 
	 * @param root
	 *            Root Element
	 * @param dir
	 *            Direction of the port
	 * @param source
	 *            Source of the port
	 * @param size
	 *            Size of data (optional)
	 * @param typeName
	 *            Type of data (optional)
	 * @return New Port
	 */
	public static Element newPort(Element root, String dir, String source,
			String size, String typeName) {
		Element port = root.getOwnerDocument().createElement("port");
		port.setAttribute("dir", dir);
		port.setAttribute("source", source);

		addNotNull(port, "size", size);
		addNotNull(port, "typeName", typeName);

		root.appendChild(port);
		return port;
	}

	/**
	 * New Port Operation
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of operation
	 * @param portName
	 *            Port name of operation
	 * @return New created port operation
	 */
	public static Element newPortOperation(Element root, String kind,
			String portName) {
		return newOperation(root, kind, portName, null, null);
	}

	/**
	 * Create State Variable
	 * 
	 * @param root
	 *            Root Element
	 * @param name
	 *            State Variable name
	 * @return Created State variable
	 */
	public static Element newStateVar(Element root, String name) {
		return newStateVar(root, name, null);
	}

	/**
	 * Create State Variable
	 * 
	 * @param root
	 *            Root Element
	 * @param name
	 *            State Variable name
	 * @param sourceName
	 *            Source name (optional)
	 * @return Created State variable
	 */
	public static Element newStateVar(Element root, String name,
			String sourceName) {
		Element statevar = root.getOwnerDocument().createElement("stateVar");
		statevar.setAttribute("name", name);

		addNotNull(statevar, "sourceName", sourceName);
		root.appendChild(statevar);
		return statevar;
	}

	/**
	 * New Operation
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of operation
	 * @param target
	 *            Target of operation (optional)
	 * @return New created operation
	 */
	public static Element newTargetOperation(Element root, String kind,
			String target) {
		return newOperation(root, kind, null, target, null);
	}

	/**
	 * New Test Module
	 * 
	 * @param root
	 *            Root Element
	 * @param decision
	 *            Decision (test)
	 * @return New Test Module
	 */
	public static Element newTestModule(Element root, String decision) {
		return newModule(root, "test", null, null, null, decision);
	}

	/**
	 * New Value Operation
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of operation
	 * @param value
	 *            Value of operation
	 * @return New created value operation
	 */
	public static Element newValueOperation(Element root, String kind,
			String value) {
		return newOperation(root, kind, null, null, value);
	}

}
