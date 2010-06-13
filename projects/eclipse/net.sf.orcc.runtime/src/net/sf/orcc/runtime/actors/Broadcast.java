/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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

import net.sf.orcc.debug.Location;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.debug.AbstractActorDebug;

/**
 * This class defines a generic broadcast actor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class Broadcast extends AbstractActorDebug {

	protected Fifo input;

	protected Fifo outputs[];

	/**
	 * Creates a new broadcast with the given number of outputs.
	 * 
	 * @param numOutputs
	 *            number of output ports.
	 */
	public Broadcast(int numOutputs) {
		super("Broadcast.java");

		outputs = new Fifo[numOutputs];
		actionLocation.put("untagged", new Location(89, 13, 31));
	}

	@Override
	final public String getNextSchedulableAction() {
		if (input.hasTokens(1) && outputsHaveRoom()) {
			return "untagged";
		}

		return null;
	}

	final protected boolean outputsHaveRoom() {
		boolean hasRoom = true;
		for (int i = 0; i < outputs.length && hasRoom; i++) {
			hasRoom &= outputs[i].hasRoom(1);
		}

		return hasRoom;
	}

	@Override
	final public void setFifo(String portName, Fifo fifo) {
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
