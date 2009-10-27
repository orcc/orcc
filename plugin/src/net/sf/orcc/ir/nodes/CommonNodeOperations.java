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

import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Use;

/**
 * This class defines operations common to nodes.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CommonNodeOperations {

	/**
	 * Sets the local variable assigned by this node. Uses are updated to point
	 * to this node.
	 * 
	 * @param node
	 *            an {@link AbstractNode} that implements
	 *            {@link ITargetContainer}
	 * @param target
	 *            a local variable
	 */
	public static void setTarget(ITargetContainer node, LocalVariable target) {
		LocalVariable thisTarget = node.getTarget();
		if (thisTarget != null) {
			thisTarget.removeUse((AbstractNode) node);
		}

		if (target != null) {
			target.addUse((AbstractNode) node);
		}

		node.setTargetSimple(target);
	}

	/**
	 * Sets the value of this node. Uses are updated to point to this node.
	 * 
	 * @param node
	 *            an {@link AbstractNode} that implements
	 *            {@link IValueContainer}
	 * @param value
	 *            an expression
	 */
	public static void setValue(IValueContainer node, IExpr value) {
		IExpr thisValue = node.getValue();
		if (thisValue != null) {
			Use.removeUses((AbstractNode) node, thisValue);
		}

		if (value != null) {
			Use.addUses((AbstractNode) node, value);
		}

		node.setValueSimple(value);
	}

}
