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
package net.sf.orcc.network;

import java.util.HashMap;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

/**
 * This class defines a broadcast as a particular instance.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Broadcast extends Instance {

	public static final String CLASS = "";

	private int numOutput;

	private Type type;

	/**
	 * Creates a new broadcast whose name is composed from the given actor name
	 * and port name. The broadcast will have the number of outputs given and
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
	public Broadcast(String actorName, String portName, int numOutput, Type type) {
		super("broadcast_" + actorName + "_" + portName, CLASS,
				new HashMap<String, Expression>());
		this.numOutput = numOutput;
		this.type = type;
	}

	public int getNumOutput() {
		return numOutput;
	}

	public Type getType() {
		return type;
	}

}
