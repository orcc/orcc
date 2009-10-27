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

import net.sf.orcc.common.Location;

/**
 * This class defines a join node. A join node is a node that contains a list of
 * <code>phi</code> assignments.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class JoinNode extends AbstractNode {

	private List<PhiAssignment> phis;

	/**
	 * Creates a new join node with the given id and location
	 * 
	 * @param id
	 *            node id
	 * @param location
	 *            location
	 */
	public JoinNode(int id, Location location) {
		super(id, location);
		this.phis = new ArrayList<PhiAssignment>();
	}

	@Override
	public void accept(NodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	/**
	 * Returns the list of phi assignments present in this node.
	 * 
	 * @return the list of phi assignments present in this node
	 */
	public List<PhiAssignment> getPhiAssignments() {
		return phis;
	}

}
