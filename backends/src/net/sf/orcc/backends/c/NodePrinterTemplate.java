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
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Variable;
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

	protected ExprToString exprPrinter;

	protected StringTemplateGroup group;

	protected StringTemplate template;

	protected VarDefPrinter varDefPrinter;

	public NodePrinterTemplate(StringTemplateGroup group,
			StringTemplate template, String id, VarDefPrinter varDefPrinter,
			ExprToString exprPrinter) {
		attrName = "nodes";
		this.actorName = id;
		this.exprPrinter = exprPrinter;
		this.group = group;
		this.template = template;
		this.varDefPrinter = varDefPrinter;
	}

	@Override
	public void visit(Assign node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("assignVarNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue()));

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
			LocalVariable varDef = node.getTarget();
			nodeTmpl.setAttribute("res", varDefPrinter.getVarDefName(varDef));
		}

		nodeTmpl.setAttribute("name", node.getProcedure().getName());
		for (Expression parameter : node.getParameters()) {
			nodeTmpl
					.setAttribute("parameters", exprPrinter.toString(parameter));
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Decrement node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("decrementNode");
		Variable variable = node.getVar();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(variable));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(HasTokens node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("hasTokensNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public Object visit(IfNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("ifNode");

		Expression expr = node.getValue();
		nodeTmpl.setAttribute("expr", exprPrinter.toString(expr));

		// save current template
		StringTemplate previousTempl = template;
		String previousAttrName = attrName;
		template = nodeTmpl;
		attrName = "thenNodes";

		for (CFGNode subNode : node.getThenNodes()) {
			subNode.accept(this, args);
		}

		List<CFGNode> elseNodes = node.getElseNodes();
		if (elseNodes.size() > 0) {
			attrName = "elseNodes";
			for (CFGNode subNode : elseNodes) {
				subNode.accept(this, args);
			}
		}

		// restore previous template and attribute name
		attrName = previousAttrName;
		template = previousTempl;
		template.setAttribute(attrName, nodeTmpl);

		return null;
	}

	@Override
	public void visit(Increment node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("incrementNode");
		Variable variable = node.getVar();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(variable));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(InitPort node, Object... args) {
		// nothing to print
	}

	@Override
	public void visit(Load node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("loadNode");

		LocalVariable target = node.getTarget();
		nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(target));

		Variable source = node.getSource().getVariable();
		nodeTmpl.setAttribute("source", varDefPrinter.getVarDefName(source));

		List<Expression> indexes = node.getIndexes();
		for (Expression index : indexes) {
			nodeTmpl.setAttribute("indexes", exprPrinter.toString(index));
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Peek node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("peekNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
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
		LocalVariable varDef = node.getTarget();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
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
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue()));
		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(SelfAssignment node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("selfAssignmentNode");

		Variable varDef = node.getVar();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
		nodeTmpl.setAttribute("op", ExprToString.toString(node.getOp()));
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue()));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Store node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("storeNode");

		Variable variable = node.getTarget().getVariable();
		nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(variable));

		List<Expression> indexes = node.getIndexes();
		for (Expression index : indexes) {
			nodeTmpl.setAttribute("indexes", exprPrinter.toString(index));
		}
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue()));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public Object visit(WhileNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("whileNode");
		Expression expr = node.getValue();
		nodeTmpl.setAttribute("expr", exprPrinter.toString(expr));

		// save current template
		StringTemplate previousTempl = template;
		String previousAttrName = attrName;
		template = nodeTmpl;
		attrName = "nodes";

		for (CFGNode subNode : node.getNodes()) {
			subNode.accept(this, args);
		}

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
		LocalVariable varDef = node.getTarget();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
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

	@Override
	public void visit(SpecificInstruction instruction, Object... args) {
		((AbstractCInstruction) instruction).accept(this, args);
	}

}
