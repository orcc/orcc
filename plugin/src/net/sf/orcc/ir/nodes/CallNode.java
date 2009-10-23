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

import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.Use;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.expr.IExpr;

/**
 * This class defines a Call node.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CallNode extends AbstractNode implements ITargetContainer {

	private List<IExpr> parameters;

	private Procedure procedure;

	private LocalVariable target;

	public CallNode(int id, Location location, LocalVariable target,
			Procedure procedure, List<IExpr> parameters) {
		super(id, location);
		setParameters(parameters);
		setTarget(target);
		this.procedure = procedure;
	}

	@Override
	public void accept(NodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public List<IExpr> getParameters() {
		return parameters;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	@Override
	public LocalVariable getTarget() {
		return target;
	}

	public boolean hasResult() {
		return (getTarget() != null);
	}

	/**
	 * Sets the parameters of this call node. Uses are updated to point to this
	 * node.
	 * 
	 * @param parameters
	 *            a list of expressions
	 */
	private void setParameters(List<IExpr> parameters) {
		if (this.parameters != null) {
			Use.removeUses(this, this.parameters);
		}
		this.parameters = parameters;
		Use.addUses(this, parameters);
	}

	/**
	 * Sets the procedure referenced by this call node.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
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
		String str = hasResult() ? getTarget() + " = " : "";
		return str + procedure + "(" + parameters + ")";
	}

}
