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

import java.util.List;

import net.sf.orcc.common.LocalUse;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.Use;
import net.sf.orcc.ir.expr.IExpr;

/**
 * This class defines a node that Stores data to memory from an expression. The
 * target can be a global (scalar or array), or a local array.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StoreNode extends AbstractNode {

	private List<IExpr> indexes;

	private Use target;

	private IExpr value;

	public StoreNode(int id, Location location, Use target,
			List<IExpr> indexes, IExpr value) {
		super(id, location);
		this.indexes = indexes;
		this.target = target;
		this.value = value;
	}

	@Override
	public void accept(NodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public List<IExpr> getIndexes() {
		return indexes;
	}

	/**
	 * Returns the target of this Store. The target is a {@link Use}.
	 * 
	 * @return the target of this Store
	 */
	public Use getTarget() {
		return target;
	}

	public IExpr getValue() {
		return value;
	}

	public void setIndexes(List<IExpr> indexes) {
		this.indexes = indexes;
	}

	public void setTarget(LocalUse target) {
		this.target = target;
	}

	public void setValue(IExpr value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return target.toString() + indexes + " = " + value;
	}

}
