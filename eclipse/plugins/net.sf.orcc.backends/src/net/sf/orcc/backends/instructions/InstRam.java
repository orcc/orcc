/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.Use;

/**
 * This interface defines a specific instruction that manipulates a RAM
 * associated to a var.
 * 
 * @author Matthieu Wipliez
 * @model abstract="true" extends="net.sf.orcc.ir.InstSpecific"
 */
public interface InstRam extends InstSpecific {

	/**
	 * Returns the port on which operations should be performed.
	 * 
	 * @return the port on which operations should be performed
	 * @model
	 */
	int getPort();

	/**
	 * Returns the variable used by this RAM instruction.
	 * 
	 * @return the variable used by this RAM instruction
	 * @model containment="true"
	 */
	Use getSource();

	/**
	 * Sets the port on which operations should be performed.
	 * 
	 * @param port
	 *            the port on which operations should be performed
	 */
	void setPort(int port);

	/**
	 * Sets the variable used by this RAM instruction.
	 * 
	 * @param var
	 *            the variable used by this RAM instruction
	 */
	void setSource(Use source);

}
