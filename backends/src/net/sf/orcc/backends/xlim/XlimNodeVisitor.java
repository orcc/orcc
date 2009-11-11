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

import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.INode;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.nodes.HasTokensNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.InitPortNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReadEndNode;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.nodes.WriteEndNode;
import net.sf.orcc.ir.nodes.WriteNode;

import org.w3c.dom.Element;

/**
 * XlimNodeVisitor prints all procedures nodes in XLIM
 * 
 * @author Samuel Keller EPFL
 */
public class XlimNodeVisitor implements NodeVisitor {

	/**
	 * Current action name
	 */
	private String actionName;

	/**
	 * Names templates
	 */
	private XlimNames names;

	/**
	 * Root element where to add everything
	 */
	private Element root;
	
	/**
	 * Map for input type
	 */
	private Map<String, Element> readMap;
	
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
		readMap  = new TreeMap<String, Element>();
		writeMap  = new TreeMap<String, Element>();
	}

	/**
	 * Add assignment node
	 * 
	 * @param node
	 *            Assignment node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(AssignVarNode node, Object... args) {
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
	public void visit(CallNode node, Object... args) {
		Map<String, String> params = new TreeMap<String, String>();
		Iterator<Variable> it = node.getProcedure().getParameters().getList()
				.iterator();

		for (IExpr param : node.getParameters()) {
			param.accept(new XlimExprVisitor(names, root));
			params.put(it.next().getName(), names.getTempName());
		}

		XlimNames newname = new XlimNames(names, params);
		XlimNodeVisitor visitor = new XlimNodeVisitor(newname, root, actionName);
		for (INode nodei : node.getProcedure().getNodes()) {
			nodei.accept(visitor);
		}

		Variable target = node.getTarget();
		if (target != null) {
			Element operationE = XlimNodeTemplate.newOperation(root, "noop");
			XlimNodeTemplate.newInPort(operationE, names.getTempName());
			XlimNodeTemplate.newOutPort(operationE, names.getVarName(target));
		}
	}

	/**
	 * Add empty node
	 * 
	 * @param node
	 *            Empty node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(EmptyNode node, Object... args) {
		System.out.println("CHECK EMPTY");
	}

	/**
	 * Add has tokens node
	 * 
	 * @param node
	 *            Has tokens node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(HasTokensNode node, Object... args) {
		System.out.println("CHECK HASTOKEN");
		// TODO I don't know

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

		Element moduleB = XlimNodeTemplate.newModule(root, "if");

		String decision = names.putDecision();
		Element moduleT = XlimNodeTemplate.newTestModule(moduleB, decision);

		node.getValue().accept(new XlimExprVisitor(names, moduleT));

		Element operationE = XlimNodeTemplate.newOperation(moduleT, "noop");

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		XlimNodeTemplate.newOutPort(operationE, decision);

		Element moduleY = XlimNodeTemplate.newModule(moduleB, "then");

		XlimNodeVisitor visitor = new XlimNodeVisitor(names, moduleY, actionName);
		for (INode operations : node.getThenNodes()) {
			operations.accept(visitor);
		}

		Element moduleN = XlimNodeTemplate.newModule(moduleB, "else");

		for (INode operations : node.getElseNodes()) {
			operations.accept(new XlimNodeVisitor(names, moduleN, actionName));
		}

		node.getJoinNode().accept(
				new XlimNodeVisitor(names, moduleB, actionName));

	}

	/**
	 * Add init port node
	 * 
	 * @param node
	 *            Init port node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(InitPortNode node, Object... args) {
		System.out.println("CHECK INIT PORT");
		// TODO Let's see when it happens

	}

	/**
	 * Add join node
	 * 
	 * @param node
	 *            Join node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(JoinNode node, Object... args) {
		System.out.println("CHECK JOIN");

		Element phiE = XlimNodeTemplate.newPHI(root);

		for (PhiAssignment phi : node.getPhiAssignments()) {
			System.out.println("CHECK PHIS");

			XlimNodeTemplate.newInPHIPort(phiE, names.getVarName(phi.getVars()
					.get(0)), "then");
			XlimNodeTemplate.newInPHIPort(phiE, names.getVarName(phi.getVars()
					.get(1)), "else");

			Element portO = XlimNodeTemplate.newOutPort(phiE, names
					.getVarName(phi.getTarget()));
			phi.getTarget().getType().accept(new XlimTypeSizeVisitor(portO));
		}
	}

	/**
	 * Add load node
	 * 
	 * @param node
	 *            Load node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(LoadNode node, Object... args) {
		Element operationE = XlimNodeTemplate.newOperation(root, "noop");

		String name = names.getVarName(node.getSource());
		XlimNodeTemplate.newInPort(operationE, name);

		LocalVariable local = node.getTarget();
		Element port = XlimNodeTemplate.newOutPort(operationE, names.getVarName(local));
		IType outtype = node.getTarget().getType();
		outtype.accept(new XlimTypeSizeVisitor(port));
		outtype.accept(new XlimTypeSizeVisitor(readMap.get(name))); 
	}

	/**
	 * Add peek node
	 * 
	 * @param node
	 *            Peek node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(PeekNode node, Object... args) {
		System.out.println("CHECK PEEK");
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ReadEndNode node, Object... args) {
		// TODO Auto-generated method stub
		System.out.println("READ END");
	}

	/**
	 * Add read node
	 * 
	 * @param node
	 *            Read node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(ReadNode node, Object... args) {
		Element operationE = XlimNodeTemplate.newPortOperation(root, "pinRead",
				node.getPort().getName());
		operationE.setAttribute("removable", "no");
		operationE.setAttribute("style", "simple");

		String name =  names.getVarName(node.getTarget());
		Element port = XlimNodeTemplate.newOutPort(operationE, name);
		readMap.put(name, port);
	}

	/**
	 * Add return node
	 * 
	 * @param node
	 *            Return node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(ReturnNode node, Object... args) {
		node.getValue().accept(new XlimExprVisitor(names, root));

		System.out.println("CHECK RETURN");
		// TODO Wait for "return" example

	}

	/**
	 * Add store node
	 * 
	 * @param node
	 *            Store node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(StoreNode node, Object... args) {
		node.getValue().accept(new XlimExprVisitor(names, root));

		Element operationE = XlimNodeTemplate.newOperation(root, "noop");

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		String name = names.getVarName(node.getTarget());
		Element port = XlimNodeTemplate.newOutPort(operationE, name);
		
		writeMap.put(name, port);
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

		Element moduleB = XlimNodeTemplate.newModule(root, "loop");

		String decision = names.putDecision();
		Element moduleT = XlimNodeTemplate.newTestModule(moduleB, decision);
		node.getValue().accept(new XlimExprVisitor(names, moduleT));

		Element operationE = XlimNodeTemplate.newOperation(moduleT, "noop");

		XlimNodeTemplate.newInPort(operationE, names.getTempName());

		XlimNodeTemplate.newOutPort(operationE, decision);

		Element moduleY = XlimNodeTemplate.newModule(moduleB, "body");

		XlimNodeVisitor visitor = new XlimNodeVisitor(names, moduleY, actionName);
		for (INode operations : node.getNodes()) {
			operations.accept(visitor);
		}

		node.getJoinNode().accept(
				new XlimNodeVisitor(names, moduleB, actionName));
	}

	@Override
	public void visit(WriteEndNode node, Object... args) {
		// TODO Auto-generated method stub.
		System.out.println("WRITE END");
	}

	/**
	 * Add write node
	 * 
	 * @param node
	 *            Write node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(WriteNode node, Object... args) {
		Element operationE = XlimNodeTemplate.newPortOperation(root,
				"pinWrite", node.getPort().getName());
		operationE.setAttribute("style", "simple");
		String name = names.getVarName(node.getTarget());
		XlimNodeTemplate.newInPort(operationE, name);
		
		node.getPort().getType().accept(new XlimTypeSizeVisitor(writeMap.get(name)));
	}
}
