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
package net.sf.orcc.ir.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.ir.AbstractLocalizable;
import net.sf.orcc.ir.CFG;
import net.sf.orcc.ir.CFGEdge;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;

/**
 * This class defines an abstract node.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractNode extends AbstractLocalizable implements
		CFGNode {

	private static int labelCount;

	/**
	 * Resets the label count. The label count is used to assign labels to newly
	 * created CFG nodes. Labels must be unique within a {@link Procedure},
	 * which means this function can NOT be called in the middle of a procedure.
	 */
	public static synchronized void resetLabelCount() {
		labelCount = 0;
	}

	/**
	 * Set the label count to the given value. This function should be called
	 * before visiting a procedure to ensure newly-created nodes do not have the
	 * same labels as existing nodes.
	 * 
	 * @param labelCount
	 *            new value of labelCount
	 * @see #resetLabelCount()
	 */
	public static void setLabelCount(int labelCount) {
		AbstractNode.labelCount = labelCount;
	}

	private int label;

	private Procedure procedure;

	protected AbstractNode(Location location, Procedure procedure) {
		super(location);
		this.procedure = procedure;
		labelCount++;
		this.label = labelCount;
	}

	@Override
	public int getLabel() {
		return label;
	}

	@Override
	public List<CFGNode> getPredecessors() {
		if (procedure == null) {
			return null;
		}

		CFG cfg = procedure.getCFG();
		if (cfg == null) {
			return null;
		}

		Set<CFGEdge> edges = cfg.incomingEdgesOf(this);
		List<CFGNode> predecessors = new ArrayList<CFGNode>(edges.size());
		for (CFGEdge edge : edges) {
			predecessors.add(cfg.getEdgeSource(edge));
		}
		return predecessors;
	}

	@Override
	public Procedure getProcedure() {
		return procedure;
	}

	@Override
	public List<CFGNode> getSuccessors() {
		if (procedure == null) {
			return null;
		}

		CFG cfg = procedure.getCFG();
		if (cfg == null) {
			return null;
		}

		Set<CFGEdge> edges = cfg.outgoingEdgesOf(this);
		List<CFGNode> successors = new ArrayList<CFGNode>(edges.size());
		for (CFGEdge edge : edges) {
			successors.add(cfg.getEdgeTarget(edge));
		}
		return successors;
	}

	@Override
	public boolean isBlockNode() {
		return false;
	}

	@Override
	public boolean isCFGNode() {
		return true;
	}

	@Override
	public boolean isIfNode() {
		return false;
	}

	@Override
	public boolean isInstruction() {
		return false;
	}

	@Override
	public boolean isWhileNode() {
		return false;
	}

}
