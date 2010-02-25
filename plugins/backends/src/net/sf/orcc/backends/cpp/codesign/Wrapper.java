/*
 * Copyright (c) 2010, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the 
 *     names of its contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
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
package net.sf.orcc.backends.cpp.codesign;

import java.util.HashMap;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.attributes.IAttribute;

/**
 * This class defines a wrapper as a particular instance.
 * This class is only used in the context of the codesign.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class Wrapper extends Instance {

	public static final String CLASS = "";

	private int numInput;

	private int numOutput;

	/**
	 * Creates a new wrapper whose name is composed from the given actor name
	 * and port name. 
	 * The wrapper will have the number of inputs and outputs given and
	 * the given type.
	 * 
	 * @param actorName
	 *            name of the source actor
	 * @param portName
	 *            name of the source output port connected to this broadcast
	 * @param numOutput
	 *            number of outputs
	 * @param type
	 *            type of this broadcast
	 */
	public Wrapper(String actorName, int numInput, int numOutput) {
		super(actorName, CLASS, new HashMap<String, Expression>(),
				new HashMap<String, IAttribute>());
		this.numInput = numInput;
		this.numOutput = numOutput;
	}

	public int getNumInput() {
		return numInput;
	}

	public int getNumOutput() {
		return numOutput;
	}

}
