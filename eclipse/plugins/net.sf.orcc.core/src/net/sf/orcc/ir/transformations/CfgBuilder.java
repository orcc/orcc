/*
 * Copyright (c) 2009-2012, IETR/INSA of Rennes
 * Copyright (c) 2012, Synflow
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
package net.sf.orcc.ir.transformations;

import java.util.List;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.GraphFactory;
import net.sf.orcc.ir.Cfg;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class defines a transformation to build the CFG of procedures.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class CfgBuilder extends AbstractActorVisitor<CfgNode> {

	private IrFactory factory = IrFactory.eINSTANCE;

	protected Cfg cfg;

	protected CfgNode last;

	protected boolean flag;

	protected void addEdge(CfgNode node) {
		Edge edge = GraphFactory.eINSTANCE.createEdge(last, node);
		if (flag) {
			edge.setAttribute("flag", factory.createExprBool(true));
			// reset flag to false (so it is only set for this edge)
			flag = false;
		}
		cfg.getEdges().add(edge);
	}

	/**
	 * Creates a node and adds it to this CFG.
	 * 
	 * @return a newly-created node
	 */
	protected CfgNode addNode(Node node) {
		CfgNode cfgNode = factory.createCfgNode(node);
		cfg.add(cfgNode);
		return cfgNode;
	}

	@Override
	public CfgNode caseNodeBlock(NodeBlock node) {
		CfgNode cfgNode = addNode(node);
		if (last != null) {
			addEdge(cfgNode);
		}

		return cfgNode;
	}

	@Override
	public CfgNode caseNodeIf(NodeIf node) {
		CfgNode cfgNode = addNode(node);
		if (last != null) {
			addEdge(cfgNode);
		}

		CfgNode join = addNode(node.getJoinNode());

		last = cfgNode;
		flag = true;
		last = doSwitch(node.getThenNodes());

		// reset flag (in case there are no nodes in "then" branch)
		flag = false;
		addEdge(join);

		last = cfgNode;
		last = doSwitch(node.getElseNodes());
		addEdge(join);
		last = join;

		return join;
	}

	@Override
	public CfgNode caseNodeWhile(NodeWhile node) {
		CfgNode join = addNode(node.getJoinNode());

		if (last != null) {
			addEdge(join);
		}

		flag = true;
		last = join;
		CfgNode cfgNode = addNode(node);
		addEdge(cfgNode);

		last = join;
		flag = true;
		last = doSwitch(node.getNodes());

		// reset flag (in case there are no nodes in "then" branch)
		flag = false;
		addEdge(join);
		last = cfgNode;

		return cfgNode;
	}

	@Override
	public CfgNode caseProcedure(Procedure procedure) {
		last = null;
		cfg = factory.createCfg();
		procedure.setCfg(cfg);
		return super.caseProcedure(procedure);
	}

	/**
	 * Visits the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes
	 * @return the last node of the node list
	 */
	public CfgNode doSwitch(List<Node> nodes) {
		for (Node node : nodes) {
			last = doSwitch(node);
		}

		return last;
	}

}
