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

import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.TargetContainer;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines an instruction that performs an operation on a port. This
 * can be a Peek, a Read or a Write.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractFifoInstruction extends AbstractInstruction
		implements TargetContainer {

	private int numTokens;

	private Port port;

	private Variable target;

	public AbstractFifoInstruction(Location location, Port port, int numTokens,
			Variable target) {
		super(location);
		this.numTokens = numTokens;
		setPort(port);
		setTarget(target);
	}

	/**
	 * Returns the number of tokens used by this FIFO operation.
	 * 
	 * @return the number of tokens used by this FIFO operation
	 */
	public int getNumTokens() {
		return numTokens;
	}

	/**
	 * Returns the port used by this FIFO operation.
	 * 
	 * @return the port used by this FIFO operation
	 */
	public Port getPort() {
		return port;
	}

	@Override
	public Variable getTarget() {
		return target;
	}

	@Override
	public void internalSetTarget(Variable target) {
		this.target = target;
	}

	/**
	 * Returns true if this FIFO operation is a unit operation, which means it
	 * uses exactly one token from its port.
	 * 
	 * @return true if this FIFO operation is a unit operation
	 */
	public boolean isUnit() {
		return numTokens == 1;
	}

	/**
	 * Sets the number of tokens used by this FIFO operation.
	 * 
	 * @param numTokens
	 *            the number of tokens used by this FIFO operation
	 */
	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
	}

	/**
	 * Sets the port used by this FIFO operation.
	 * 
	 * @param port
	 *            the port used by this FIFO operation
	 */
	public void setPort(Port port) {
		if (this.port != null) {
			this.port.removeUse(this);
		}
		this.port = port;
		if (port != null) {
			port.addUse(this);
		}
	}

	@Override
	public void setTarget(Variable target) {
		CommonNodeOperations.setTarget(this, target);
	}

}
