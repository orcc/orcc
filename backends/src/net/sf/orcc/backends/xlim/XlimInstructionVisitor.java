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

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InitPort;
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
public class XlimInstructionVisitor implements InstructionVisitor {

	/**
	 * Current action name
	 */
	private String actionName;

	/**
	 * Names templates
	 */
	private XlimNames names;

	/**
	 * Map for input type
	 */
	private Map<String, Element> readMap;

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
	 * @param readMap
	 *            Temporary mapping for inputs
	 * @param writeMap
	 *            Temporary mapping for outputs
	 */
	public XlimInstructionVisitor(XlimNames names, Element root,
			String actionName, Map<String, Element> readMap,
			Map<String, Element> writeMap) {
		this.names = names;
		this.root = root;
		this.actionName = actionName;
		this.readMap = readMap;
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
		node.getValue().accept(new XlimExprVisitor(names, root));
		Element operationE = XlimNodeTemplate.newTargetOperation(root,
				"assign", names.getVarName(node.getTarget()));
		XlimNodeTemplate.newInPort(operationE, names.getTempName());
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
			param.accept(new XlimExprVisitor(names, root));
			params.put(it.next().getName(), names.getTempName());
		}

		XlimNames newname = new XlimNames(names, params);
		XlimNodeVisitor visitor = new XlimNodeVisitor(newname, root,
				actionName, readMap, writeMap);
		for (CFGNode nodei : node.getProcedure().getNodes()) {
			nodei.accept(visitor);
		}

		Variable target = node.getTarget();
		if (target != null) {
			Element operationE = XlimNodeTemplate.newOperation(root, "noop");
			XlimNodeTemplate.newInPort(operationE, names.getTempName());
			XlimNodeTemplate.newOutPort(operationE, names.getVarName(target),
					target.getType());
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
	 * Add init port node
	 * 
	 * @param node
	 *            Init port node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(InitPort node, Object... args) {
		System.out.println("CHECK INIT PORT");
		// TODO Let's see when it happens

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
		Element operationE = XlimNodeTemplate.newOperation(root, "noop");

		String name = names.getVarName(node.getSource());
		XlimNodeTemplate.newInPort(operationE, name);

		LocalVariable local = node.getTarget();
		Type outtype = node.getTarget().getType();
		XlimNodeTemplate.newOutPort(operationE, names.getVarName(local),
				outtype);

		if (readMap.containsKey(name)) {
			outtype.accept(new XlimTypeSizeVisitor(readMap.get(name)));
		}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PhiAssignment phi, Object... args) {
		Element phiE = XlimNodeTemplate.newPHI(root);
		System.out.println("CHECK PHIS");

		XlimNodeTemplate.newInPHIPort(phiE, names.getVarName(phi.getVars().get(
				0)), "then");
		XlimNodeTemplate.newInPHIPort(phiE, names.getVarName(phi.getVars().get(
				1)), "else");

		Element portO = XlimNodeTemplate.newOutPort(phiE, names.getVarName(phi
				.getTarget()), phi.getTarget().getType());
		phi.getTarget().getType().accept(new XlimTypeSizeVisitor(portO));
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
		Element operationE = XlimNodeTemplate.newPortOperation(root, "pinRead",
				node.getPort().getName());
		operationE.setAttribute("removable", "no");
		operationE.setAttribute("style", "simple");

		String name = names.getVarName(node.getTarget());
		Element port = XlimNodeTemplate.newOutPort(operationE, name);
		readMap.put(name, port);
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
		node.getValue().accept(new XlimExprVisitor(names, root));

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
		node.getValue().accept(new XlimExprVisitor(names, root));

		Element operationE = XlimNodeTemplate.newOperation(root, "noop");

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		String name = names.getVarName(node.getTarget());
		Element port = XlimNodeTemplate.newOutPort(operationE, name);

		writeMap.put(name, port);
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
		Element operationE = XlimNodeTemplate.newPortOperation(root,
				"pinWrite", node.getPort().getName());
		operationE.setAttribute("style", "simple");
		String name = names.getVarName(node.getTarget());
		XlimNodeTemplate.newInPort(operationE, name);

		node.getPort().getType().accept(
				new XlimTypeSizeVisitor(writeMap.get(name)));
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
