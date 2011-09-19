/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import org.eclipse.emf.common.util.EList;

/**
 * This interface defines a Call instruction, which possibly stores the result
 * to a local variable.
 * 
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Instruction"
 */
public interface InstCall extends Instruction {

	/**
	 * Returns the parameters of this call instruction.
	 * 
	 * @return the parameters of this call instruction
	 * @model containment="true"
	 */
	EList<Arg> getParameters();

	/**
	 * Returns the procedure referenced by this call instruction.
	 * 
	 * @return the procedure referenced by this call instruction
	 * @model
	 */
	Procedure getProcedure();

	/**
	 * Returns the target of this call (may be <code>null</code>).
	 * 
	 * @return the target of this node (may be <code>null</code>)
	 * @model containment="true"
	 */
	Def getTarget();

	/**
	 * Returns <code>true</code> if this call has a result.
	 * 
	 * @return <code>true</code> if this call has a result
	 */
	boolean hasResult();

	/**
	 * Returns <code>true</code> if this call is a call to the built-in "print"
	 * procedure.
	 * 
	 * @return <code>true</code> if this call is a call to the built-in "print"
	 *         procedure
	 */
	boolean isPrint();

	/**
	 * Sets the procedure referenced by this call instruction.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	void setProcedure(Procedure procedure);

	/**
	 * Sets the target of this call instruction.
	 * 
	 * @param target
	 *            a local variable (may be <code>null</code>)
	 */
	void setTarget(Def target);

}
