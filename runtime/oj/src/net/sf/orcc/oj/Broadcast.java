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
package net.sf.orcc.oj;

import java.util.HashMap;
import java.util.Map;


/**
 * A generic broadcast actor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Broadcast implements IActorDebug {

	private Map<String, Location> actionLocation;

	private IntFifo input;

	private IntFifo outputs[];

	/**
	 * Creates a new broadcast with the given number of outputs.
	 * 
	 * @param numOutputs
	 *            number of output ports.
	 */
	public Broadcast(int numOutputs) {
		outputs = new IntFifo[numOutputs];
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("untagged", new Location(89, 13, 31));
	}

	@Override
	public String getFile() {
		return "Broadcast.java";
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	@Override
	public void initialize() {
	}

	private boolean outputsHaveRoom() {
		boolean hasRoom = true;
		for (int i = 0; i < outputs.length && hasRoom; i++) {
			hasRoom &= outputs[i].hasRoom(1);
		}

		return hasRoom;
	}

	@Override
	public int schedule() {
		int i = 0;
		int[] tokens = new int[1];
		while (input.hasTokens(1) && outputsHaveRoom()) {
			input.get(tokens);
			for (IntFifo output : outputs) {
				output.put(tokens);
			}

			i++;
		}

		return i;
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if (portName.equals("input")) {
			input = fifo;
		} else if (portName.startsWith("output_")) {
			try {
				int portNumber = Integer.parseInt(portName.substring(7));
				outputs[portNumber] = fifo;
			} catch (NumberFormatException e) {
				String msg = "invalid port name: \"" + portName + "\"";
				throw new IllegalArgumentException(msg, e);
			}
		} else {
			String msg = "invalid port name: \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

}
