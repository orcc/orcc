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
package net.sf.orcc.backends.c;

import java.util.List;

import net.sf.orcc.backends.c.instructions.AbstractCInstruction;
import net.sf.orcc.backends.c.instructions.CInstructionVisitor;
import net.sf.orcc.backends.c.instructions.Decrement;
import net.sf.orcc.backends.c.instructions.Increment;
import net.sf.orcc.backends.c.instructions.SelfAssignment;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InitPort;
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
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * This class defines a node printer template.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NodePrinterTemplate implements CInstructionVisitor, NodeVisitor {

	/**
	 * Variable member access switch from private to protected.
	 * 
	 * @see net.sf.orcc.backend.cpp.CppNodePrinter
	 */
	protected String actorName;

	protected String attrName;

	protected StringTemplateGroup group;

	protected StringTemplate template;

	public NodePrinterTemplate(StringTemplateGroup group,
			StringTemplate template, String id) {
		attrName = "nodes";
		this.actorName = id;
		this.group = group;
		this.template = template;
	}

	@Override
	public void visit(Assign node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("assignVarNode");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("var", node.getTarget());
		nodeTmpl.setAttribute("expr", node.getValue());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public Object visit(BlockNode node, Object... args) {
		for (Instruction instruction : node) {
			instruction.accept(this, args);
		}

		return null;
	}

	@Override
	public void visit(Call node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("callNode");
		if (node.hasResult()) {
			nodeTmpl.setAttribute("res", node.getTarget().toString());
		}

		nodeTmpl.setAttribute("name", node.getProcedure().getName());
		for (Expression parameter : node.getParameters()) {
			nodeTmpl.setAttribute("parameters", parameter.toString());
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Decrement node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("decrementNode");
		nodeTmpl.setAttribute("var", node.getVar().toString());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(HasTokens node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("hasTokensNode");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("var", node.getTarget());
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public Object visit(IfNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("ifNode");

		Expression expr = node.getValue();
		nodeTmpl.setAttribute("expr", expr.toString());

		// save current template
		StringTemplate previousTempl = template;
		String previousAttrName = attrName;
		template = nodeTmpl;
		attrName = "thenNodes";

		visit(node.getThenNodes(), args);

		List<CFGNode> elseNodes = node.getElseNodes();
		if (elseNodes.size() > 0) {
			attrName = "elseNodes";
			visit(elseNodes, args);
		}

		// restore previous template and attribute name
		attrName = previousAttrName;
		template = previousTempl;
		template.setAttribute(attrName, nodeTmpl);

		node.getJoinNode().accept(this, args);

		return null;
	}

	@Override
	public void visit(Increment node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("incrementNode");
		nodeTmpl.setAttribute("var", node.getVar().toString());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(InitPort node, Object... args) {
		// nothing to print
	}

	private void visit(List<CFGNode> nodes, Object... args) {
		for (CFGNode node : nodes) {
			node.accept(this, args);
		}
	}

	@Override
	public void visit(Load node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("loadNode");

		nodeTmpl.setAttribute("target", node.getTarget());
		nodeTmpl.setAttribute("source", node.getSource().getVariable());

		List<Expression> indexes = node.getIndexes();
		for (Expression index : indexes) {
			nodeTmpl.setAttribute("indexes", index.toString());
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Peek node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("peekNode");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("var", node.getTarget());
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(PhiAssignment node, Object... args) {
	}

	@Override
	public void visit(Read node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("readNode");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("var", node.getTarget());
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(ReadEnd node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("readEndNode");

		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Return node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("returnNode");
		nodeTmpl.setAttribute("expr", node.getValue().toString());
		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(SelfAssignment node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("selfAssignmentNode");

		nodeTmpl.setAttribute("var", node.getVar());
		nodeTmpl.setAttribute("op", node.getOp().getText());
		nodeTmpl.setAttribute("expr", node.getValue().toString());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(SpecificInstruction instruction, Object... args) {
		((AbstractCInstruction) instruction).accept(this, args);
	}

	@Override
	public void visit(Store node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("storeNode");

		nodeTmpl.setAttribute("target", node.getTarget().getVariable());

		List<Expression> indexes = node.getIndexes();
		for (Expression index : indexes) {
			nodeTmpl.setAttribute("indexes", index.toString());
		}
		nodeTmpl.setAttribute("expr", node.getValue().toString());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public Object visit(WhileNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("whileNode");
		Expression expr = node.getValue();
		nodeTmpl.setAttribute("expr", expr.toString());

		// save current template
		StringTemplate previousTempl = template;
		String previousAttrName = attrName;
		template = nodeTmpl;
		attrName = "nodes";

		visit(node.getNodes(), args);

		// restore previous template
		attrName = previousAttrName;
		template = previousTempl;
		template.setAttribute(attrName, nodeTmpl);

		return null;
	}

	@Override
	public void visit(Write node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("writeNode");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("var", node.getTarget());
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(WriteEnd node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("writeEndNode");

		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());

		template.setAttribute(attrName, nodeTmpl);
	}

}
