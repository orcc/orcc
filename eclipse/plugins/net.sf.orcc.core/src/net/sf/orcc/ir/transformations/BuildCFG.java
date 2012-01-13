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
package net.sf.orcc.ir.transformations;

import java.util.List;

import net.sf.orcc.ir.CFG;
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

	private CFG graph;

	private Node last;

	@Override
	public Node caseNodeBlock(NodeBlock node) {
		graph.addVertex(node);
		if (last != null) {
			if (graph.containsVertex(node) && graph.containsVertex(last)) {
				graph.addEdge(last, node);
			}
			else{
				System.out.println("tututut");
			}
		}

		return node;
	}

	@Override
	public Node caseNodeIf(NodeIf node) {
		graph.addVertex(node);
		if (last != null) {
			graph.addEdge(last, node);
		}

		Node join = node.getJoinNode();
		graph.addVertex(join);

		last = node;
		last = doSwitch(node.getThenNodes());
		graph.addEdge(last, join);

		last = node;
		last = doSwitch(node.getElseNodes());
		graph.addEdge(last, join);
		last = join;
		
		return join;
	}

	@Override
	public Node caseNodeWhile(NodeWhile node) {
		Node join = node.getJoinNode();
		graph.addVertex(join);

		if (last != null) {
			graph.addEdge(last, join);
		}

		graph.addVertex(node);
		graph.addEdge(join, node);

		last = join;
		last = doSwitch(node.getNodes());
		graph.addEdge(last, join);
		last = node;
		
		return node;
	}

	@Override
	public Node caseProcedure(Procedure procedure) {
		last = null;
		graph = new CFG();
		procedure.setGraph(graph);
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
