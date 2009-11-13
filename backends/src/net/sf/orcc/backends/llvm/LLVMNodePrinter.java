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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.backends.llvm.nodes.BitcastNode;
import net.sf.orcc.backends.llvm.nodes.BrLabelNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.GetElementPtrNode;
import net.sf.orcc.backends.llvm.nodes.LLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.LoadFifo;
import net.sf.orcc.backends.llvm.nodes.PhiNode;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.backends.llvm.nodes.SextNode;
import net.sf.orcc.backends.llvm.nodes.TruncNode;
import net.sf.orcc.backends.llvm.nodes.ZextNode;
import net.sf.orcc.backends.llvm.type.LLVMAbstractType;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.IntExpr;
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
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.instructions.WriteEnd;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.util.OrderedMap;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * 
 * @author Jérôme GORIN
 * 
 */
public class LLVMNodePrinter implements LLVMNodeVisitor, NodeVisitor {

	private String actorName;

	private String attrName;

	private LLVMExprPrinter exprPrinter;

	private StringTemplateGroup group;

	private Procedure procedure;

	private StringTemplate template;

	private TypeToString typeToString;

	private LLVMVarDefPrinter varDefPrinter;

	public LLVMNodePrinter(StringTemplateGroup group, StringTemplate template,
			String actorName, Procedure procedure,
			LLVMVarDefPrinter varDefPrinter, LLVMExprPrinter exprPrinter,
			TypeToString typeToString) {
		attrName = "nodes";
		this.actorName = actorName;
		this.procedure = procedure;
		this.group = group;
		this.template = template;
		this.varDefPrinter = varDefPrinter;
		this.exprPrinter = exprPrinter;
		this.typeToString = typeToString;
	}

	@Override
	public void visit(Assign node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("assignVarNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				varDef.getType()));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(BitcastNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("BitcastNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getVar();
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("type", typeToString.toString(varDef.getType()));
		nodeTmpl.setAttribute("expr", varDefPrinter.getVarDefName(node
				.getValue(), true));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(BlockNode node, Object... args) {
		// TODO Auto-generated method stub

	}

	public void visit(BrLabelNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("brlabelNode");

		// varDef contains the variable (with the same name as the port)
		LabelNode labelnode = node.getLabelNode();
		nodeTmpl.setAttribute("name", labelnode.getLabelName());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(BrNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("brNode");
		List<CFGNode> conditionNodes = node.getConditionNodes();
		List<CFGNode> thenNodes = node.getThenNodes();
		List<CFGNode> elseNodes = node.getElseNodes();
		List<PhiNode> phiNodes = node.getPhiNodes();

		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getCondition(),
				new BoolType()));
		nodeTmpl.setAttribute("thenLabelNode", node.getLabelTrueNode());
		nodeTmpl.setAttribute("elseLabelNode", node.getLabelFalseNode());
		nodeTmpl.setAttribute("endLabelNode", node.getLabelEndNode());

		// save current template
		StringTemplate previousTempl = template;
		String previousAttrName = attrName;
		template = nodeTmpl;

		attrName = "conditionNodes";
		for (CFGNode subNode : conditionNodes) {
			subNode.accept(this, args);
		}

		attrName = "thenNodes";
		for (CFGNode subNode : thenNodes) {
			subNode.accept(this, args);
		}

		attrName = "elseNodes";
		for (CFGNode subNode : elseNodes) {
			subNode.accept(this, args);
		}

		// restore previous template and attribute name
		attrName = previousAttrName;
		template = previousTempl;
		template.setAttribute(attrName, nodeTmpl);

		for (PhiNode phiNode : phiNodes) {
			phiNode.accept(this, args);
		}
	}

	@Override
	public void visit(Call node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("callNode");

		if (node.hasResult()) {
			LocalVariable varDef = node.getTarget();
			nodeTmpl.setAttribute("res", varDefPrinter.getVarDefName(varDef,
					false));
		}

		Procedure proc = node.getProcedure();
		OrderedMap<Variable> procParameters = proc.getParameters();

		nodeTmpl.setAttribute("return", typeToString.toString(proc
				.getReturnType()));
		nodeTmpl.setAttribute("name", proc.getName());
		Iterator<Variable> it = procParameters.iterator();
		for (Expression parameter : node.getParameters()) {
			Type type = it.next().getType();
			nodeTmpl.setAttribute("parameters", exprPrinter.toString(parameter,
					type));
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(GetElementPtrNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("getElementPtrNode");

		LocalVariable local = node.getTarget();
		nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(local,
				false));

		Variable variable = node.getSource().getVariable();
		nodeTmpl.setAttribute("source", varDefPrinter.getVarDefName(variable,
				true));

		for (Expression index : node.getIndexes()) {
			nodeTmpl.setAttribute("indexes", exprPrinter.toString(index,
					new IntType(new IntExpr(new Location(), 32))));
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(HasTokens node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("hasTokensNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(IfNode node, Object... args) {

	}

	@Override
	public void visit(InitPort node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("initPortNode");

		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getFifoName());
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				false));
		nodeTmpl.setAttribute("index", (Integer) args[0]);

		template.setAttribute(attrName, nodeTmpl);
	}

	public void visit(LabelNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("labelNode");
		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("name", node.getLabelName());
		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Load node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("loadNode");

		LocalVariable target = node.getTarget();
		nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(target,
				false));

		Variable source = node.getSource().getVariable();
		nodeTmpl.setAttribute("source", varDefPrinter.getVarDefName(source,
				true));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(LoadFifo node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("loadFifo");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getFifoName());
		nodeTmpl.setAttribute("index", node.getIndex());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Peek node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("peekNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl.setAttribute("typevar", typeToString
				.toString(varDef.getType()));
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(PhiAssignment node, Object... args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PhiNode node, Object... args) {
		Map<LabelNode, LocalVariable> assignements = node.getAssignements();

		StringTemplate nodeTmpl = group.getInstanceOf("phiNode");

		// varDef contains the variable (with the same name as the port)
		nodeTmpl.setAttribute("target", varDefPrinter.getVarDefName(node
				.getVarDef(), false));
		nodeTmpl.setAttribute("type", typeToString.toString(node.getType()));

		for (Entry<LabelNode, LocalVariable> assignement : assignements
				.entrySet()) {
			StringTemplate phiTmpl = group.getInstanceOf("phiPair");

			phiTmpl.setAttribute("value", varDefPrinter.getVarDefName(
					assignement.getValue(), false));
			phiTmpl.setAttribute("label", assignement.getKey());

			nodeTmpl.setAttribute("assignements", phiTmpl);
		}

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(Read node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("readNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(ReadEnd node, Object... args) {

	}

	@Override
	public void visit(Return node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("returnNode");
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				procedure.getReturnType()));
		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(SelectNode node, Object... args) {

		// there is nothing to print.
		List<PhiAssignment> phis = node.getPhis();
		Expression condition = node.getCondition();

		if (!phis.isEmpty()) {
			for (PhiAssignment phi : phis) {
				LocalVariable target = phi.getTarget();
				List<Use> varuses = phi.getVars();

				StringTemplate nodeTmpl = group.getInstanceOf("selectNode");

				// varDef contains the variable (with the same name as the port)
				nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(
						target, false));
				nodeTmpl.setAttribute("type", typeToString.toString(target
						.getType()));

				nodeTmpl.setAttribute("expr", exprPrinter.toString(condition,
						new BoolType()));

				LocalVariable varDefTrue = (LocalVariable) varuses.get(0)
						.getVariable();
				LocalVariable varDefFalse = (LocalVariable) varuses.get(1)
						.getVariable();

				nodeTmpl.setAttribute("trueVar", varDefPrinter.getVarDefName(
						varDefTrue, true));
				nodeTmpl.setAttribute("falseVar", varDefPrinter.getVarDefName(
						varDefFalse, true));

				template.setAttribute(attrName, nodeTmpl);
			}
		}
	}

	@Override
	public void visit(SextNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("SextNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getVar();
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("type", typeToString.toString(varDef.getType()));
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				varDef.getType()));

		template.setAttribute(attrName, nodeTmpl);

	}

	@Override
	public void visit(Store node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("storeNode");

		Variable variable = node.getTarget().getVariable();
		nodeTmpl.setAttribute("var", varDefPrinter
				.getVarDefName(variable, true));

		Type type = variable.getType();
		if (type.getType() == LLVMAbstractType.POINT) {
			type = ((PointType) type).getElementType();
		}

		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				type));

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(TruncNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("TruncNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getVar();
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("type", typeToString.toString(varDef.getType()));
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				varDef.getType()));

		template.setAttribute(attrName, nodeTmpl);

	}

	@Override
	public void visit(WhileNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("whileNode");

		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				new BoolType()));

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
	}

	@Override
	public void visit(Write node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("writeNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getTarget();
		nodeTmpl.setAttribute("var", varDefPrinter.getVarDefName(varDef, true));
		nodeTmpl.setAttribute("actorName", actorName);
		nodeTmpl.setAttribute("fifoName", node.getPort());
		nodeTmpl.setAttribute("numTokens", node.getNumTokens());

		template.setAttribute(attrName, nodeTmpl);
	}

	@Override
	public void visit(WriteEnd node, Object... args) {

	}

	@Override
	public void visit(ZextNode node, Object... args) {
		StringTemplate nodeTmpl = group.getInstanceOf("ZextNode");

		// varDef contains the variable (with the same name as the port)
		LocalVariable varDef = node.getVar();
		nodeTmpl
				.setAttribute("var", varDefPrinter.getVarDefName(varDef, false));
		nodeTmpl.setAttribute("type", typeToString.toString(varDef.getType()));
		nodeTmpl.setAttribute("expr", exprPrinter.toString(node.getValue(),
				varDef.getType()));

		template.setAttribute(attrName, nodeTmpl);

	}
}
