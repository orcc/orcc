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

import net.sf.orcc.ir.Type;

import org.w3c.dom.Element;

/**
 * XlimNodeTemplate groups elements templates for XLIM output
 * 
 * @author Samuel Keller
 */
public class XlimNodeTemplate {

	/**
	 * Calculate value size
	 * 
	 * @param value
	 *            value to evaluate
	 * @return Value size
	 */
	private static String calcSize(int value) {
		int size = 1;
		value >>= 1;
		while (value > 0) {
			value >>= 1;
			size++;
		}
		return Integer.toString(size + 1);
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
	public static Element newDesign(Element design, String name) {
		design.setAttribute("name", name);
		return design;
	}

	/**
	 * New Operation
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of operation
	 */
	public static Element newDiffOperation(Element root, String kind) {
		Element operation = root.getOwnerDocument().createElement("operation");
		operation.setAttribute("kind", kind);
		return operation;
	}

	/**
	 * New Initial Value
	 * 
	 * @param root
	 *            Root Element
	 * @return New created Initial Value
	 */
	public static Element newInitValue(Element root) {
		Element init = root.getOwnerDocument().createElement("initValue");
		root.appendChild(init);
		return init;
	}

	/**
	 * New Initial Value
	 * 
	 * @param root
	 *            Root Element
	 * @param typeName
	 *            Type name
	 * @return New created Initial Value
	 */
	public static Element newInitValue(Element root, String typeName) {
		Element init = newInitValue(root);
		init.setAttribute("typeName", typeName);
		return init;
	}

	/**
	 * New Initial Value
	 * 
	 * @param root
	 *            Root Element
	 * @param size
	 *            Type size
	 * @param typeName
	 *            Type name
	 * @param value
	 *            Initial value
	 * @return New created Initial Value
	 */
	public static Element newInitValue(Element root, String size,
			String typeName, String value) {
		Element init = newInitValue(root, typeName);
		init.setAttribute("size", size);
		init.setAttribute("value", value);
		return init;
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
		Element result = newPort(root, "in", source);
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
		return newPort(root, "in", source);
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
		Element module = root.getOwnerDocument().createElement("module");
		module.setAttribute("kind", kind);
		root.appendChild(module);
		return (module);
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
	 * @return New Module
	 */
	public static Element newModule(Element root, String kind,
			String autostart, String name) {
		Element module = newModule(root, kind);

		module.setAttribute("autostart", autostart);
		module.setAttribute("name", name);
		return (module);
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
	 * @return New Module
	 */
	public static Element newModule(Element root, String kind,
			String autostart, String name, String sourcename) {
		Element module = newModule(root, kind, autostart, name);
		module.setAttribute("sourcename", sourcename);
		return (module);
	}

	/**
	 * New Operation
	 * 
	 * @param root
	 *            Root Element
	 * @param kind
	 *            Kind of operation
	 * @param name
	 *            Name of operation
	 * @return New created operation
	 */
	public static Element newNameOperation(Element root, String kind,
			String name) {
		Element operation = newOperation(root, kind);
		operation.setAttribute("name", name);
		return operation;
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
		Element operation = newDiffOperation(root, kind);
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
	 * @return New Out Port
	 */
	public static Element newOutPort(Element root, String source) {
		return newPort(root, "out", source);
	}

	/**
	 * New Out Port
	 * 
	 * @param root
	 *            Root Element
	 * @param source
	 *            Source of the port
	 * @param typeName
	 *            Type of data
	 * @param value
	 *            Integer value
	 * @return New Out Port
	 */
	public static Element newOutPort(Element root, String source,
			String typeName, int value) {
		Element port = newOutPort(root, source);
		port.setAttribute("typeName", typeName);
		port.setAttribute("size", calcSize(value));
		return port;
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
		Element port = newOutPort(root, source);
		port.setAttribute("typeName", typeName);
		port.setAttribute("size", size);
		return port;
	}

	/**
	 * New Out Port
	 * 
	 * @param root
	 *            Root Element
	 * @param source
	 *            Source of the port
	 * @param type
	 *            Type of data
	 * @return New Out Port
	 */
	public static Element newOutPort(Element root, String source, Type type) {
		Element port = newOutPort(root, source);
		type.accept(new XlimTypeSizeVisitor(port));
		return port;
	}

	/**
	 * New PHI
	 * 
	 * @param root
	 *            Root Element
	 * @return New created PHI
	 */
	public static Element newPHI(Element root) {
		Element phi = root.getOwnerDocument().createElement("PHI");
		root.appendChild(phi);
		return phi;
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
	 * @return New Port
	 */
	private static Element newPort(Element root, String dir, String source) {
		Element port = root.getOwnerDocument().createElement("port");
		port.setAttribute("dir", dir);
		port.setAttribute("source", source);
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
		Element operation = newOperation(root, kind);
		operation.setAttribute("portName", portName);
		return operation;
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
		Element statevar = root.getOwnerDocument().createElement("stateVar");
		statevar.setAttribute("name", name);
		root.appendChild(statevar);
		return statevar;
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
		Element statevar = newStateVar(root, name);
		statevar.setAttribute("sourceName", sourceName);
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
	 *            Target of operation
	 * @return New created operation
	 */
	public static Element newTargetOperation(Element root, String kind,
			String target) {
		Element operation = newOperation(root, kind);
		operation.setAttribute("target", target);
		return operation;
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
		Element module = newModule(root, "test");
		module.setAttribute("decision", decision);
		return module;
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
		Element operation = newOperation(root, kind);
		operation.setAttribute("value", value);
		return operation;
	}

}
