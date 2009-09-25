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
package net.sf.orcc.backends.llvm.nodes;

import java.util.List;

import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.expr.AbstractExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.IfNode;

/**
 * @author Jérôme GORIN
 * 
 */
public class BrNode extends AbstractLLVMNode {

	private AbstractExpr condition;

	private List<AbstractNode> elseNodes;

	private List<AbstractNode> thenNodes;

	private List<PhiNode> phiNodes;
	
	private LabelNode labelEndNode;

	private LabelNode labelEntryNode;

	private LabelNode labelFalseNode;

	private LabelNode labelTrueNode;

	

	public BrNode(IfNode node, List<PhiNode> phiNodes, LabelNode labelEntryNode,
			LabelNode labelTrueNode, LabelNode labelFalseNode,
			LabelNode labelEndNode) {
		super(node.getId(), node.getLocation());
		this.condition = node.getCondition();
		this.elseNodes = node.getElseNodes();
		this.thenNodes = node.getThenNodes();
		this.labelEntryNode = labelEntryNode;
		this.labelTrueNode = labelTrueNode;
		this.labelFalseNode = labelFalseNode;
		this.labelEndNode = labelEndNode;
		this.phiNodes = phiNodes;
	}

	public BrNode(int id, Location location, AbstractExpr condition,
			List<AbstractNode> thenNodes, List<AbstractNode> elseNodes, List<PhiNode> phiNodes,
			LabelNode labelEntryNode, LabelNode labelTrueNode, LabelNode labelFalseNode,
			LabelNode labelEndNode) {
		super(id, location);
		this.condition = condition;
		this.phiNodes = phiNodes;
		this.elseNodes = elseNodes;
		this.thenNodes = thenNodes;
		this.labelEntryNode = labelEntryNode;
		this.labelTrueNode = labelTrueNode;
		this.labelFalseNode = labelFalseNode;
		this.labelEndNode = labelEndNode;
	}

	@Override
	public void accept(LLVMNodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public AbstractExpr getCondition() {
		return condition;
	}

	public List<AbstractNode> getElseNodes() {
		return elseNodes;
	}

	public LabelNode getLabelEndNode() {
		return labelEndNode;
	}

	public LabelNode getLabelEntryNode() {
		return labelEntryNode;
	}

	public LabelNode getLabelFalseNode() {
		return labelFalseNode;
	}

	public LabelNode getLabelTrueNode() {
		return labelTrueNode;
	}
	
	public List<PhiNode> getPhiNodes() {
		return phiNodes;
	}

	public void setPhiNodes(List<PhiNode> phiNodes) {
		this.phiNodes =  phiNodes;
	}

	public List<AbstractNode> getThenNodes() {
		return thenNodes;
	}

	public void setCondition(AbstractExpr condition) {
		this.condition = condition;
	}
	
	
	public void setElseNodes(List<AbstractNode> elseNodes) {
		this.elseNodes = elseNodes;
	}

	public void setThenNodes(List<AbstractNode> thenNodes) {
		this.thenNodes = thenNodes;
	}

	public void setLabelEndNode(LabelNode labelEndNode) {
		this.labelEndNode = labelEndNode;
	}

	public void setLabelEntryNode(LabelNode labelEntryNode) {
		this.labelEntryNode = labelEntryNode;
	}

	public void setLabelFalseNode(LabelNode labelFalseNode) {
		this.labelFalseNode = labelFalseNode;
	}

	public void setLabelTrueNode(LabelNode labelTrueNode) {
		this.labelTrueNode = labelTrueNode;
	}

	@Override
	public String toString() {
		return "br i1 " + condition + ", label " + labelTrueNode + ", label "
				+ labelFalseNode;
	}

}
