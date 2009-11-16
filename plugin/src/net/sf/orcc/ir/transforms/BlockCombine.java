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
package net.sf.orcc.ir.transforms;

import java.io.File;
import java.util.ListIterator;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;

/**
 * Removes phi assignments and translates them to copies.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class BlockCombine extends AbstractActorTransformation {

	private BlockNode previous;

	@Override
	public Object visit(BlockNode node, Object... args) {
		if (previous == null) {
			previous = node;
		} else {
			// add instructions of this block after previous block's
			// instructions
			previous.addAll(node);

			// remove this block
			ListIterator<?> it = (ListIterator<?>) args[0];
			it.remove();
		}
		
		return null;
	}

	@Override
	public Object visit(IfNode node, Object... args) {
		previous = null;
		visit(node.getThenNodes());
		previous = null;
		visit(node.getElseNodes());
		previous = null;
		visit(node.getJoinNode(), args);
		previous = null;
		return null;
	}

	@Override
	public Object visit(WhileNode node, Object... args) {
		previous = null;
		visit(node.getNodes());
		previous = null;
		visit(node.getJoinNode(), args);
		previous = null;
		return null;
	}

	@Override
	protected void visitProcedure(Procedure procedure) {
		super.visitProcedure(procedure);
		previous = null;
	}

}
