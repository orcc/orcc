/*
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its contributors may be used
 *     to endorse or promote products derived from this software without specific
 *     prior written permission.
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
package net.sf.orcc.simulators;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Broadcast;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_String;
import net.sf.orcc.runtime.Fifo_boolean;
import net.sf.orcc.runtime.Fifo_int;

/**
 * This class defines a broadcast that can be interpreted by calling
 * {@link #schedule()}.
 * 
 * @author Ghislain Roquier
 * 
 */
public class BroadcastInterpreter {

	/**
	 * Communication fifos map : key = related I/O port name; value =
	 * Fifo_int/boolean/String
	 */
	private Map<Port, Fifo> fifos;

	private Broadcast broadcast;

	private Fifo input;

	private List<Fifo> outputs;

	public BroadcastInterpreter(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

	public void initialize() {
		input = fifos.get(broadcast.getInput());
		outputs = new LinkedList<Fifo>();
		for (Port port : broadcast.getOutputs().getList()) {
			outputs.add(fifos.get(port));
		}
	}

	public boolean schedule() {
		if (input.hasTokens(1) && outputsHaveRoom()) {
			if (input instanceof Fifo_int) {
				int[] tokens = ((Fifo_int) input).getReadArray(1);
				int tokens_Index = input.getReadIndex(1);
				int token = tokens[tokens_Index];

				for (Fifo output : outputs) {
					int[] outputTokens = ((Fifo_int) output).getWriteArray(1);
					int output_Index = output.getWriteIndex(1);
					outputTokens[output_Index] = token;
					((Fifo_int) output).writeEnd(1, outputTokens);
				}
			} else if (input instanceof Fifo_boolean) {
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
			} else if (input instanceof Fifo_String) {
				String[] tokens = ((Fifo_String) input).getReadArray(1);
				int tokens_Index = input.getReadIndex(1);
				String token = tokens[tokens_Index];

				for (Fifo output : outputs) {
					String[] outputTokens = ((Fifo_String) output)
							.getWriteArray(1);
					int output_Index = output.getWriteIndex(1);
					outputTokens[output_Index] = token;
					((Fifo_String) output).writeEnd(1, outputTokens);
				}
			}
			((Fifo_int) input).readEnd(1);
			return true;
		} else {
			return false;
		}
	}

	final private boolean outputsHaveRoom() {
		boolean hasRoom = true;
		for (Fifo output : outputs) {
			hasRoom &= output.hasRoom(1);
		}
		return hasRoom;
	}

	public void setFifos(Map<Port, Fifo> fifos) {
		this.fifos = fifos;
	}

}
