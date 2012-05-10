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

import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Cfg;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * This class defines a transformation to build the CFG of procedures.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class CfgBuilder extends AbstractIrVisitor<CfgNode> {

	protected Cfg cfg;

	private IrFactory factory = IrFactory.eINSTANCE;

	protected boolean flag;

	protected CfgNode last;

	protected void addEdge(CfgNode node) {
		Edge edge = cfg.add(last, node);
		if (flag) {
			edge.setAttribute("flag", factory.createExprBool(true));
			// reset flag to false (so it is only set for this edge)
			flag = false;
		}
	}

	/**
	 * Creates a node and adds it to this CFG.
	 * 
	 * @return a newly-created node
	 */
	protected CfgNode addNode(Block node) {
		CfgNode cfgNode = factory.createCfgNode(node);
		cfg.add(cfgNode);
		return cfgNode;
	}

	@Override
	public CfgNode caseBlockBasic(BlockBasic node) {
		CfgNode cfgNode = addNode(node);
		if (last != null) {
			addEdge(cfgNode);
		}

		return cfgNode;
	}

	@Override
	public CfgNode caseBlockIf(BlockIf node) {
		CfgNode cfgNode = addNode(node);
		if (last != null) {
			addEdge(cfgNode);
		}

		CfgNode join = addNode(node.getJoinBlock());

		last = cfgNode;
		flag = true;
		last = doSwitch(node.getThenBlocks());

		// reset flag (in case there are no nodes in "then" branch)
		flag = false;
		addEdge(join);

		last = cfgNode;
		last = doSwitch(node.getElseBlocks());
		addEdge(join);
		last = join;

		return join;
	}

	@Override
	public CfgNode caseBlockWhile(BlockWhile node) {
		CfgNode join = addNode(node.getJoinBlock());

		if (last != null) {
			addEdge(join);
		}

		flag = true;
		last = join;
		CfgNode cfgNode = addNode(node);
		addEdge(cfgNode);

		last = join;
		flag = true;
		last = doSwitch(node.getBlocks());

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
	public CfgNode doSwitch(List<Block> nodes) {
		for (Block node : nodes) {
			last = doSwitch(node);
		}

		return last;
	}

}
