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
package net.sf.orcc.backends.llvm;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.backends.llvm.nodes.BrLabelNode;
import net.sf.orcc.backends.llvm.nodes.LLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.LoadFifo;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.AbstractExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.nodes.HasTokensNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.nodes.WriteNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * 
 * @author Jérôme GORIN
 * 
 */
public class NodePrinterTemplate implements LLVMNodeVisitor {

	private String actorName;

	private VarDefPrinter varDefPrinter;

	private StringTemplateGroup group;

	private StringTemplate template;

	private String attrName;

	public NodePrinterTemplate(StringTemplateGroup group,
			StringTemplate template, String actorName,
			VarDefPrinter varDefPrinter) {
		attrName = "nodes";
		this.actorName = actorName;
		this.group = group;
		this.template = template;
		this.varDefPrinter = varDefPrinter;
	}

	@Override
	public void visit(AssignVarNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("assignVarNode");

		// varDef contains the variable (with the same name as the port)
		VarDef varDef = node.getVar();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));

		ExprToString expr = new ExprToString(varDefPrinter, node.getValue());
		nodeTmpl.setAttribute("expr", expr.toString());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(CallNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("callNode");
		if (node.hasRes()) {
			VarDef varDef = node.getRes();
			nodeTmpl.setAttribute("res", varDefPrinter.getVarDefName(varDef));
		}

		nodeTmpl.setAttribute("name", node.getProcedure().getName());
		for (AbstractExpr parameter : node.getParameters()) {
			ExprToString expr = new ExprToString(varDefPrinter, parameter);
			nodeTmpl.setAttribute("parameters", expr.toString());
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(EmptyNode node, Object... args) {
		// nothing to print
	}

	@Override
	public void visit(HasTokensNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("hasTokensNode");

		// varDef contains the variable (with the same name as the port)
		VarDef varDef = node.getVarDef();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getFifoName());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(BrNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("brNode");

		ExprToString expr = new ExprToString(varDefPrinter, node.getCondition());
		nodeTmpl.setAttribute("expr", expr.toString());
		nodeTmpl.setAttribute("thenLabelNode", node.getLabelTrueNode());
		nodeTmpl.setAttribute("elseLabelNode", node.getLabelFalseNode());
		
		// save current template
		StringTemplate previousTempl = template;
		String previousAttrName = attrName;
		template = nodeTmpl;
		attrName = "thenNodes";

		for (AbstractNode subNode : node.getThenNodes()) {
			subNode.accept(this, args);
		}

		List<AbstractNode> elseNodes = node.getElseNodes();
		if (!(elseNodes.size() == 1 && elseNodes.get(0) instanceof EmptyNode)) {
			attrName = "elseNodes";
			nodeTmpl.setAttribute("endLabelNode", node.getLabelEndNode());
			for (AbstractNode subNode : elseNodes) {
				subNode.accept(this, args);
			}
		}
		
		// restore previous template and attribute name
		attrName = previousAttrName;
		template = previousTempl;
		template.setAttribute(attrName, nodeTmpl);
		
		JoinNode joinNode = node.getJoinNode();
		joinNode.accept(this,  node.getLabelTrueNode(), node.getLabelFalseNode());
	}

	
	@Override
	public void visit(IfNode node, Object... args) {
		
	}

	@Override
	public void visit(JoinNode node, Object... args) {
		// there is nothing to print.
		List<PhiAssignment> phis = node.getPhis();
		if (!phis.isEmpty()) {
			for (PhiAssignment phi : phis) {
				VarDef varDef = phi.getVarDef();
				//VarDef source = phi.getVars().get(phiIndex).getVarDef();
				StringTemplate nodeTmpl = group.getInstanceOf("joinNode");

				// varDef contains the variable (with the same name as the port)
				nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(varDef));

				template.setAttribute(attrName, nodeTmpl);
			}
		}
	}

	@Override
	public void visit(LoadNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("loadNode");

		VarDef varDef = node.getTarget();
		nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(varDef));

		TypeToString type = new TypeToString(varDef.getType());
		nodeTmpl.setAttribute("typesource", type.toString());

		varDef = node.getSource().getVarDef();
		nodeTmpl.setAttribute("source", varDefPrinter.getVarDefName(varDef));

		List<AbstractExpr> indexes = node.getIndexes();
		for (AbstractExpr index : indexes) {
			ExprToString expr = new ExprToString(varDefPrinter, index);
			try {
				if (Integer.parseInt(expr.toString()) > 0) {
					nodeTmpl.setAttribute("indexes", expr.toString());
				}
			} catch (NumberFormatException e) {

			}
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(PeekNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("peekNode");

		// varDef contains the variable (with the same name as the port)
		VarDef varDef = node.getVarDef();
		TypeToString type = new TypeToString(varDef.getType());
		nodeTmpl.setAttribute("typevar", type.toString());
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getFifoName());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(ReadNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("readNode");

		// varDef contains the variable (with the same name as the port)
		VarDef varDef = node.getVarDef();
		TypeToString type = new TypeToString(varDef.getType());
		nodeTmpl.setAttribute("typevar", type.toString());
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getFifoName());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(LoadFifo node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("loadFifo");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getFifoName());

		template.setAttribute(attrName, nodeTmpl);
	}
	
	@Override
	public void visit(ReturnNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("returnNode");

		ExprToString expr = new ExprToString(varDefPrinter, node.getValue());
		nodeTmpl.setAttribute("expr", expr.toString());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(StoreNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("storeNode");

		VarDef varDef = node.getTarget().getVarDef();
		TypeToString type = new TypeToString(varDef.getType());
		nodeTmpl.setAttribute("typetarget", type.toString());
		nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(varDef));

		List<AbstractExpr> indexes = node.getIndexes();
		for (AbstractExpr index : indexes) {
			ExprToString expr = new ExprToString(varDefPrinter, index);
			nodeTmpl.setAttribute("indexes", expr.toString());
		}

		
		ExprToString expr = new ExprToString(varDefPrinter, node.getValue());
		
		AbstractExpr abstractexpr = node.getValue();
		if (abstractexpr instanceof IntExpr){
			nodeTmpl.setAttribute("expr", type.toString()+ " "+ expr.toString());
		}
		else
		{
			nodeTmpl.setAttribute("expr", expr);
		}
		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(WhileNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("whileNode");

		ExprToString expr = new ExprToString(varDefPrinter, node.getCondition());
		nodeTmpl.setAttribute("expr", expr.toString());

		// save current template
		StringTemplate previousTempl = template;
		String previousAttrName = attrName;
		template = nodeTmpl;
		attrName = "nodes";

		for (AbstractNode subNode : node.getNodes()) {
			subNode.accept(this, args);
		}

		// restore previous template
		attrName = previousAttrName;
		template = previousTempl;
		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(WriteNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("writeNode");

		// varDef contains the variable (with the same name as the port)
		VarDef varDef = node.getVarDef();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getFifoName());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}
	
	public void visit(BrLabelNode node, Object... args){
		StringTemplate nodeTmpl = group.getInstanceOf("brlabelNode");

		// varDef contains the variable (with the same name as the port)
		LabelNode labelnode = node.getLabelNode();
		nodeTmpl.setAttribute("name", labelnode.getLabelName());

		template.setAttribute(attrName, nodeTmpl);
	}
	
	public void visit(LabelNode node, Object... args){
		StringTemplate nodeTmpl = group.getInstanceOf("labelNode");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("name", node.getLabelName());

		template.setAttribute(attrName, nodeTmpl);
	}
}
