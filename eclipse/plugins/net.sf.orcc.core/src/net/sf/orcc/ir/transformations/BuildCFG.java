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
import net.sf.orcc.cfg.Cfg;
import net.sf.orcc.cfg.CfgFactory;
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
public class BuildCFG extends AbstractActorVisitor<Node> {

	private Cfg cfg;

	private Node last;

	private boolean flag;

	private void addEdge(Node node) {
		Edge edge = GraphFactory.eINSTANCE.createEdge(last, node);
		if (flag) {
			edge.setAttribute("flag", IrFactory.eINSTANCE.createExprBool(true));
			// reset flag to false (so it is only set for this edge)
			flag = false;
		}
		cfg.getEdges().add(edge);
	}

	@Override
	public Node caseNodeBlock(NodeBlock node) {
		cfg.getVertices().add(node);
		if (last != null) {
			addEdge(node);
		}

		return node;
	}

	@Override
	public Node caseNodeIf(NodeIf node) {
		cfg.getVertices().add(node);
		if (last != null) {
			addEdge(node);
		}

		Node join = node.getJoinNode();
		cfg.getVertices().add(join);

		last = node;
		flag = true;
		last = doSwitch(node.getThenNodes());

		// reset flag (in case there are no nodes in "then" branch)
		flag = false;
		addEdge(join);

		last = node;
		last = doSwitch(node.getElseNodes());
		addEdge(join);
		last = join;

		return join;
	}

	@Override
	public Node caseNodeWhile(NodeWhile node) {
		Node join = node.getJoinNode();
		cfg.getVertices().add(join);

		if (last != null) {
			addEdge(join);
		}

		cfg.getVertices().add(node);
		addEdge(node);

		last = join;
		flag = true;
		last = doSwitch(node.getNodes());

		// reset flag (in case there are no nodes in "then" branch)
		flag = false;
		addEdge(join);
		last = node;

		return node;
	}

	@Override
	public Node caseProcedure(Procedure procedure) {
		last = null;
		cfg = CfgFactory.eINSTANCE.createCfg();
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
	public Node doSwitch(List<Node> nodes) {
		for (Node node : nodes) {
			last = doSwitch(node);
		}

		return last;
	}

}
