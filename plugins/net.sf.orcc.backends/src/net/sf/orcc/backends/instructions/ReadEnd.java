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
package net.sf.orcc.backends.instructions;

import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.SpecificInstruction;

/**
 * This class defines a ReadEnd instruction. This instruction is used in code
 * generation to signal that an action has finished reading from a FIFO.
 * 
 * @author Jérôme Gorin
 * @author Matthieu Wipliez
 * 
 */
public class ReadEnd extends SpecificInstruction {

	private Read read;

	/**
	 * Creates a new ReadEnd from the given Read.
	 * 
	 * @param read
	 *            a read
	 */
	public ReadEnd(Read read) {
		super(read.getLocation());
		this.read = read;
	}

	/**
	 * Returns the number of tokens used by this ReadEnd.
	 * 
	 * @return the number of tokens used by this ReadEnd
	 */
	public int getNumTokens() {
		return read.getNumTokens();
	}

	/**
	 * Returns the port used by this ReadEnd.
	 * 
	 * @return the port used by this ReadEnd
	 */
	public Port getPort() {
		return read.getPort();
	}

	/**
	 * Returns the target of this ReadEnd.
	 * 
	 * @return the target of this ReadEnd
	 */
	public Variable getTarget() {
		return read.getTarget();
	}

	@Override
	public boolean isReadEnd() {
		return true;
	}

	@Override
	public String toString() {
		return "readEnd(" + getPort() + ", " + getNumTokens() + ")";
	}

}
