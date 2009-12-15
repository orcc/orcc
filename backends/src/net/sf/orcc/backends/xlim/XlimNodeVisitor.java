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

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import net.sf.orcc.backends.xlim.templates.XlimModuleTemplate;
import net.sf.orcc.backends.xlim.templates.XlimNodeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimOperationTemplate;
import net.sf.orcc.backends.xlim.templates.XlimTypeTemplate;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;

import org.w3c.dom.Element;

/**
 * XlimNodeVisitor prints all procedures nodes in XLIM
 * 
 * @author Samuel Keller EPFL
 */
public class XlimNodeVisitor implements NodeVisitor, XlimTypeTemplate,
		XlimModuleTemplate, XlimOperationTemplate {

	/**
	 * Current action name
	 */
	private String actionName;

	/**
	 * Vector of inputs names
	 */
	private Vector<String> inputs;

	/**
	 * Names templates
	 */
	private XlimNames names;

	/**
	 * Root element where to add everything
	 */
	private Element root;

	/**
	 * Map for output type
	 */
	private Map<String, Element> writeMap;

	/**
	 * XlimNodeVisitor Constructor
	 * 
	 * @param names
	 *            Names templates
	 * @param root
	 *            Root element where to add everything
	 * @param actionName
	 *            Current action name
	 */
	public XlimNodeVisitor(XlimNames names, Element root, String actionName) {
		this.names = names;
		this.root = root;
		this.actionName = actionName;
		this.inputs = new Vector<String>();
		this.writeMap = new TreeMap<String, Element>();
	}

	/**
	 * XlimNodeVisitor Constructor
	 * 
	 * @param names
	 *            Names templates
	 * @param root
	 *            Root element where to add everything
	 * @param actionName
	 *            Current action name
	 * @param inputs
	 *            Vector of inputs names
	 * @param writeMap
	 *            Temporary mapping for outputs
	 */
	public XlimNodeVisitor(XlimNames names, Element root, String actionName,
			Vector<String> inputs, Map<String, Element> writeMap) {
		this.names = names;
		this.root = root;
		this.actionName = actionName;
		this.inputs = inputs;
		this.writeMap = writeMap;
	}

	/**
	 * Add block node
	 * 
	 * @param node
	 *            Block node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(BlockNode node, Object... args) {
		XlimInstructionVisitor iv = new XlimInstructionVisitor(names, root,
				actionName, inputs, writeMap);
		for (Instruction instruction : node) {
			instruction.accept(iv, args);
		}
	}

	/**
	 * Add if node
	 * 
	 * @param node
	 *            If node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(IfNode node, Object... args) {
		Element moduleB = XlimNodeTemplate.newModule(root, IF);

		String decision = names.putDecision();
		Element moduleT = XlimNodeTemplate.newTestModule(moduleB, decision);

		node.getValue().accept(new XlimExprVisitor(names, moduleT));

		Element operationE = XlimNodeTemplate.newOperation(moduleT, NOOP);

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		XlimNodeTemplate.newOutPort(operationE, decision, "1", BOOL);

		Element moduleY = XlimNodeTemplate.newModule(moduleB, THEN);

		XlimNodeVisitor visitor = new XlimNodeVisitor(names, moduleY,
				actionName, inputs, writeMap);
		for (CFGNode operations : node.getThenNodes()) {
			operations.accept(visitor);
		}

		Element moduleN = XlimNodeTemplate.newModule(moduleB, ELSE);

		for (CFGNode operations : node.getElseNodes()) {
			operations.accept(new XlimNodeVisitor(names, moduleN, actionName,
					inputs, writeMap));
		}

		node.getJoinNode().accept(
				new XlimNodeVisitor(names, moduleB, actionName, inputs,
						writeMap));
	}

	/**
	 * Add while node
	 * 
	 * @param node
	 *            While node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(WhileNode node, Object... args) {
		// TODO Wait for "while" example to check this
		System.out.println("CHECK WHILE");

		Element moduleB = XlimNodeTemplate.newModule(root, LOOP);

		String decision = names.putDecision();
		Element moduleT = XlimNodeTemplate.newTestModule(moduleB, decision);
		node.getValue().accept(new XlimExprVisitor(names, moduleT));

		Element operationE = XlimNodeTemplate.newOperation(moduleT, NOOP);

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		XlimNodeTemplate.newOutPort(operationE, decision, "1", BOOL);

		Element moduleY = XlimNodeTemplate.newModule(moduleB, BODY);

		XlimNodeVisitor visitor = new XlimNodeVisitor(names, moduleY,
				actionName, inputs, writeMap);
		for (CFGNode operations : node.getNodes()) {
			operations.accept(visitor);
		}

		node.getJoinNode().accept(
				new XlimNodeVisitor(names, moduleB, actionName, inputs,
						writeMap));
	}
}
