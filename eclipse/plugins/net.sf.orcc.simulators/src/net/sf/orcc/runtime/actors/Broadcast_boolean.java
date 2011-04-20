/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.runtime.actors;

import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_boolean;

/**
 * This class defines a broadcast actor for boolean FIFOs.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Broadcast_boolean extends Broadcast {

	/**
	 * Creates a new broadcast with the given number of outputs.
	 * 
	 * @param numOutputs
	 *            number of output ports.
	 */
	public Broadcast_boolean(int numOutputs) {
		super(numOutputs);
	}

	@Override
	public int schedule() {
		int i = 0;
		while (input.hasTokens(1) && outputsHaveRoom()) {
			boolean[] tokens = ((Fifo_boolean) input).getReadArray(1);
			int tokens_Index = input.getReadIndex(1);
			boolean token = tokens[tokens_Index];

			for (Fifo output : outputs) {
				boolean[] outputTokens = ((Fifo_boolean) output)
						.getWriteArray(1);
				int output_Index = output.getWriteIndex(1);
				outputTokens[output_Index] = token;
				((Fifo_boolean) output).writeEnd(1, outputTokens);
			}

			((Fifo_boolean) input).readEnd(1);
			i++;
		}

		return i;
	}

}
