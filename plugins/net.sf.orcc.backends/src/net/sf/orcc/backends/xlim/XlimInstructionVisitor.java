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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import net.sf.orcc.backends.xlim.templates.XlimAttributeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimModuleTemplate;
import net.sf.orcc.backends.xlim.templates.XlimNodeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimOperationTemplate;
import net.sf.orcc.backends.xlim.templates.XlimTypeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimValueTemplate;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.ReadEnd;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.instructions.WriteEnd;

import org.w3c.dom.Element;

/**
 * XlimNodeVisitor prints all instructions nodes in XLIM
 * 
 * @author Samuel Keller EPFL
 */
public class XlimInstructionVisitor implements InstructionVisitor,
		XlimOperationTemplate, XlimAttributeTemplate, XlimModuleTemplate,
		XlimValueTemplate, XlimTypeTemplate {

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
	 * XlimInstructionVisitor Constructor
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
	public XlimInstructionVisitor(XlimNames names, Element root,
			String actionName, Vector<String> inputs,
			Map<String, Element> writeMap) {
		this.names = names;
		this.root = root;
		this.actionName = actionName;
		this.inputs = inputs;
		this.writeMap = writeMap;
	}

	/**
	 * Add assignment node
	 * 
	 * @param node
	 *            Assignment node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Assign node, Object... args) {
		/*
		 * node.getValue().accept(new XlimExprVisitor(names, root)); Element
		 * operationE = XlimNodeTemplate.newTargetOperation(root, "assign",
		 * names.getVarName(node.getTarget()));
		 * XlimNodeTemplate.newInPort(operationE, names.getTempName());
		 */
		node.getValue().accept(new XlimExprVisitor(names, root, actionName));
		Element operationE = XlimNodeTemplate.newOperation(root, NOOP);
		XlimNodeTemplate.newInPort(operationE, names.getTempName());
		XlimNodeTemplate.newOutPort(operationE, names.getVarName(
				node.getTarget(), actionName), node.getTarget().getType());

	}

	/**
	 * Add call node
	 * 
	 * @param node
	 *            Call node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Call node, Object... args) {
		Map<String, String> params = new TreeMap<String, String>();
		Iterator<Variable> it = node.getProcedure().getParameters().getList()
				.iterator();

		for (Expression param : node.getParameters()) {
			param.accept(new XlimExprVisitor(names, root, actionName));
			params.put(it.next().getName(), names.getTempName());
		}

		XlimNames newname = new XlimNames(names, params);
		XlimNodeVisitor visitor = new XlimNodeVisitor(newname, root,
				actionName, inputs, writeMap);
		for (CFGNode nodei : node.getProcedure().getNodes()) {
			nodei.accept(visitor);
		}

		Variable target = node.getTarget();
		if (target != null) {
			Element operationE = XlimNodeTemplate.newOperation(root, NOOP);
			XlimNodeTemplate.newInPort(operationE, names.getTempName());
			XlimNodeTemplate.newOutPort(operationE,
					names.getVarName(target, actionName), target.getType());
		}
	}

	/**
	 * Add has tokens node
	 * 
	 * @param node
	 *            Has tokens node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(HasTokens node, Object... args) {
		System.out.println("CHECK HASTOKEN");
		// TODO I don't know

	}

	/**
	 * Add load node
	 * 
	 * @param node
	 *            Load node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Load node, Object... args) {
		Element operationE;
		String name = names.getVarName(node.getSource(), actionName);
		boolean inport = inputs.contains(name);
		if (node.getSource().getVariable().getType().isList() && !inport) {
			node.getIndexes().get(0)
					.accept(new XlimExprVisitor(names, root, actionName));
			operationE = XlimNodeTemplate.newNameOperation(root, VARREF,
					names.getVarName(node.getSource(), actionName));
			XlimNodeTemplate.newInPort(operationE, names.getTempName());
		} else {
			operationE = XlimNodeTemplate.newOperation(root, NOOP);

			name = names.getVarName(node.getSource(), actionName);
			XlimNodeTemplate.newInPort(operationE, name);
		}

		LocalVariable local = node.getTarget();
		Type outtype = node.getTarget().getType();
		XlimNodeTemplate.newOutPort(operationE,
				names.getVarName(local, actionName), outtype);
	}

	/**
	 * Add peek node
	 * 
	 * @param node
	 *            Peek node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Peek node, Object... args) {
		System.out.println("CHECK PEEK");
		Element operationE = XlimNodeTemplate.newValueOperation(root, LITINT,
				Integer.toString(node.getNumTokens()));

		XlimNodeTemplate.newOutPort(operationE, names.putTempName(), INT,
				node.getNumTokens());

		Port port = node.getPort();
		String portname = port.getName();

		Element peekE = XlimNodeTemplate.newPortOperation(root, PINPEEK,
				portname);

		XlimNodeTemplate.newInPort(peekE, names.getTempName());
		XlimNodeTemplate.newOutPort(peekE,
				names.getVarName(node.getTarget(), actionName), port.getType());
	}

	@Override
	public void visit(PhiAssignment phi, Object... args) {
		/*
		 * Element phiE = XlimNodeTemplate.newPHI(root);
		 * System.out.println("CHECK PHIS");
		 * 
		 * XlimNodeTemplate.newInPHIPort(phiE,
		 * names.getVarName(phi.getVars().get( 0)), THEN);
		 * XlimNodeTemplate.newInPHIPort(phiE,
		 * names.getVarName(phi.getVars().get( 1)), ELSE);
		 * 
		 * Element portO = XlimNodeTemplate.newOutPort(phiE,
		 * names.getVarName(phi .getTarget(), actionName),
		 * phi.getTarget().getType()); phi.getTarget().getType().accept(new
		 * XlimTypeSizeVisitor(portO));
		 */
	}

	/**
	 * Add read node
	 * 
	 * @param node
	 *            Read node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Read node, Object... args) {
		Element operationE = XlimNodeTemplate.newPortOperation(root, PINREAD,
				node.getPort().getName());
		operationE.setAttribute(REMOVABLE, NO);
		operationE.setAttribute(STYLE, SIMPLE);

		String name = names.getVarName(node.getTarget(), actionName);
		Element port = XlimNodeTemplate.newOutPort(operationE, name);
		node.getPort().getType().accept(new XlimTypeSizeVisitor(port));
		inputs.add(name);
	}

	/**
	 * Add read end node
	 * 
	 * @param node
	 *            Read end node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(ReadEnd node, Object... args) {
		// TODO Auto-generated method stub
		System.out.println("READ END");
	}

	/**
	 * Add return node
	 * 
	 * @param node
	 *            Return node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Return node, Object... args) {
		if (node.getValue() == null) {
			return;
		}
		node.getValue().accept(new XlimExprVisitor(names, root, actionName));

		System.out.println("CHECK RETURN");
		// TODO Wait for "return" example

	}

	@Override
	public void visit(SpecificInstruction node, Object... args) {
		// there is no specific XLIM instruction
	}

	/**
	 * Add store node
	 * 
	 * @param node
	 *            Store node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Store node, Object... args) {
		node.getValue().accept(new XlimExprVisitor(names, root, actionName));

		Variable var = node.getTarget();
		if (var instanceof StateVariable) {
			Element operationE = XlimNodeTemplate
					.newDiffOperation(root, ASSIGN);
			operationE.setAttribute(TARGET,
					names.getVarName(node.getTarget(), actionName));
			String data = names.getTempName();

			for (Expression expr : node.getIndexes()) {
				expr.accept(new XlimExprVisitor(names, root, actionName));
				XlimNodeTemplate.newInPort(operationE, names.getTempName());
			}
			XlimNodeTemplate.newInPort(operationE, data);
			root.appendChild(operationE);
		} else {
			Element operationE = XlimNodeTemplate.newOperation(root, NOOP);

			XlimNodeTemplate.newInPort(operationE, names.getTempName());

			String name = names.getVarName(node.getTarget(), actionName);
			Element port = XlimNodeTemplate.newOutPort(operationE, name);

			writeMap.put(name, port);
		}
	}

	/**
	 * Add write node
	 * 
	 * @param node
	 *            Write node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Write node, Object... args) {
		Element operationE = XlimNodeTemplate.newPortOperation(root, PINWRITE,
				node.getPort().getName());
		operationE.setAttribute(STYLE, SIMPLE);
		String name = names.getVarName(node.getTarget(), actionName);
		XlimNodeTemplate.newInPort(operationE, name);

		node.getPort().getType()
				.accept(new XlimTypeSizeVisitor(writeMap.get(name)));
	}

	/**
	 * Add write end node
	 * 
	 * @param node
	 *            Write end node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(WriteEnd node, Object... args) {
		// TODO Auto-generated method stub.
		System.out.println("WRITE END");
	}

}
