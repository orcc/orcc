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
package net.sf.orcc.ir.instructions;

import java.util.List;

import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.TargetContainer;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines an assignment of the result of a <code>phi</code> function
 * to a target local variable.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PhiAssignment extends AbstractInstruction implements
		TargetContainer {

	private List<Use> localUses;

	private LocalVariable target;

	/**
	 * Creates a new <code>phi</code> assignment with the given target and list
	 * of uses.
	 * 
	 * @param target
	 *            a local variable
	 * @param vars
	 *            a list of uses
	 */
	public PhiAssignment(BlockNode block, Location location,
			LocalVariable target, List<Use> vars) {
		super(block, location);
		setTarget(target);
		this.localUses = vars;
	}

	@Override
	public void accept(InstructionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	@Override
	public LocalVariable getTarget() {
		return target;
	}

	public List<Use> getVars() {
		return localUses;
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
		return getTarget() + " = phi(" + localUses + ")";
	}

}
