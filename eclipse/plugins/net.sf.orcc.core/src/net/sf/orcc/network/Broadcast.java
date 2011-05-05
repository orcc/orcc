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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.util.OrderedMap;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a broadcast as a particular instance.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Broadcast {

	public static final String CLASS = "";

	private OrderedMap<String, Port> inputs;

	private int numOutputs;

	private List<Integer> outputList;

	private OrderedMap<String, Port> outputs;

	private Type type;

	/**
	 * Creates a new broadcast whose name is composed from the given actor name
	 * and port name. The broadcast will have the number of outputs given and
	 * the given type. Type is copied.
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
	public Broadcast(int numOutputs, Type type) {
		this.numOutputs = numOutputs;
		this.type = EcoreUtil.copy(type);

		inputs = new OrderedMap<String, Port>();
		String name = "input";
		inputs.put(name,
				IrFactory.eINSTANCE.createPort(EcoreUtil.copy(type), name));

		outputs = new OrderedMap<String, Port>();
		for (int i = 0; i < numOutputs; i++) {
			name = "output_" + i;
			outputs.put(name,
					IrFactory.eINSTANCE.createPort(EcoreUtil.copy(type), name));
		}
	}

	public Port getInput() {
		return inputs.get("input");
	}

	/**
	 * Returns the ordered map of input ports.
	 * 
	 * @return the ordered map of input ports
	 */
	public OrderedMap<String, Port> getInputs() {
		return inputs;
	}

	public int getNumOutputs() {
		return numOutputs;
	}

	public Port getOutput(String name) {
		return outputs.get(name);
	}

	/**
	 * Returns a list of integers containing [0, 1, ..., n - 1] where n is the
	 * number of ports of this broadcast.
	 * 
	 * @return a list of integers
	 */
	public List<Integer> getOutputList() {
		if (outputList == null) {
			outputList = new ArrayList<Integer>();
			for (int i = 0; i < numOutputs; i++) {
				outputList.add(i);
			}
		}

		return outputList;
	}

	/**
	 * Returns the ordered map of output ports.
	 * 
	 * @return the ordered map of output ports
	 */
	public OrderedMap<String, Port> getOutputs() {
		return outputs;
	}

	public Type getType() {
		return type;
	}

}
