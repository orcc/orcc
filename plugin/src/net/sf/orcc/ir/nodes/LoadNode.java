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

import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;

/**
 * This class defines a node that Loads data from memory to a local variable.
 * The source can be a global (scalar or array), or a local array.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class LoadNode extends AbstractNode implements ITargetContainer {

	private List<IExpr> indexes;

	private Use source;

	private LocalVariable target;

	public LoadNode(int id, Location location, LocalVariable target,
			Use source, List<IExpr> indexes) {
		super(id, location);
		setIndexes(indexes);
		setSource(source);
		setTarget(target);
	}

	@Override
	public void accept(NodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public List<IExpr> getIndexes() {
		return indexes;
	}

	/**
	 * Returns the source of this Load. The source is a Use because it may be a
	 * local.
	 * 
	 * @return the source of this Load
	 */
	public Use getSource() {
		return source;
	}

	@Override
	public LocalVariable getTarget() {
		return target;
	}

	public void setIndexes(List<IExpr> indexes) {
		if (this.indexes != null) {
			Use.removeUses(this, this.indexes);
		}
		this.indexes = indexes;
		Use.addUses(this, indexes);
	}

	public void setSource(Use source) {
		if (this.source != null) {
			this.source.remove();
		}
		this.source = source;
		source.setNode(this);
	}

	@Override
	public void setTarget(LocalVariable target) {
		CommonNodeOperations.setTarget(this, target);
	}

	@Override
	public void setTargetSimple(LocalVariable target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return target + " = " + source + indexes;
	}

}
