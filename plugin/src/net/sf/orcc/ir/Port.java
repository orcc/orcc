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
package net.sf.orcc.ir;

import net.sf.orcc.ir.ICommunicationFifo;

/**
 * This class defines a port. A port is just a variable, with a location, a
 * type, a name.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Port extends Variable implements Comparable<Port> {

	private ICommunicationFifo fifoBinding;

	/**
	 * the number of tokens consumed by this port.
	 */
	private int tokensConsumed;

	/**
	 * the number of tokens produced by this port.
	 */
	private int tokensProduced;

	/**
	 * Creates a new port with the given location, type, and name.
	 * 
	 * @param location
	 *            the port location
	 * @param type
	 *            the port type
	 * @param name
	 *            the port name
	 */
	public Port(Location location, Type type, String name) {
		super(location, type, name, true);
	}

	/**
	 * Creates a new port from the given port
	 */
	public Port(Port port) {
		super(port);
	}

	/**
	 * Bind the current port to a communication FIFO interface.
	 * 
	 * @param fifo
	 *            the communication FIFO
	 */
	public void bind(ICommunicationFifo fifo) {
		this.fifoBinding = fifo;
	}

	@Override
	public int compareTo(Port port) {
		return getName().compareTo(port.getName());
	}

	/**
	 * Returns the communication FIFO interface corresponding to this Port.
	 * 
	 * @return fifo_binding the communication FIFO implemented interface
	 */
	public ICommunicationFifo fifo() {
		return fifoBinding;
	}

	/**
	 * Returns the number of tokens consumed by this port.
	 * 
	 * @return the number of tokens consumed by this port
	 */
	public int getNumTokensConsumed() {
		return tokensConsumed;
	}

	/**
	 * Returns the number of tokens produced by this port.
	 * 
	 * @return the number of tokens produced by this port
	 */
	public int getNumTokensProduced() {
		return tokensProduced;
	}

	/**
	 * Increases the number of tokens consumed by this port by the given
	 * integer.
	 * 
	 * @param n
	 *            a number of tokens
	 * @throws IllegalArgumentException
	 *             if n is less or equal to zero
	 */
	public void increaseTokensConsumption(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		tokensConsumed += n;
	}

	/**
	 * Increases the number of tokens produced by this port by the given
	 * integer.
	 * 
	 * @param n
	 *            a number of tokens
	 * @throws IllegalArgumentException
	 *             if n is less or equal to zero
	 */
	public void increaseTokensProduction(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		tokensProduced += n;
	}

	/**
	 * Resets the number of tokens consumed by this port.
	 */
	public void resetTokensConsumption() {
		tokensConsumed = 0;
	}

	/**
	 * Resets the number of tokens produced by this port.
	 */
	public void resetTokensProduction() {
		tokensProduced = 0;
	}

}
