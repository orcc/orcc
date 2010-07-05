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
package net.sf.orcc.runtime;

import java.io.IOException;

/**
 * This class defines a FIFO of booleans.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Fifo_boolean extends Fifo {

	/**
	 * The contents of the FIFO.
	 */
	private final Boolean[] contents;

	/**
	 * Creates a new FIFO with the given size.
	 * 
	 * @param size
	 *            the size of this FIFO
	 */
	public Fifo_boolean(int size) {
		super(size);
		contents = new Boolean[size];
	}

	/**
	 * Creates a new FIFO with the given size and a file for tracing exchanged
	 * data.
	 * 
	 * @param size
	 *            the size of the FIFO
	 * @param folderName
	 *            output traces folder
	 * @param fifoName
	 *            name of the fifo (and the trace file)
	 */
	public Fifo_boolean(int size, String folderName, String fifoName) {
		super(size, folderName, fifoName);
		contents = new Boolean[size];
	}

	/**
	 * Returns the array where <code>numTokens</code> can be read.
	 * 
	 * @param numTokens
	 *            a number of tokens to read
	 * @return the array where <code>numTokens</code> can be read
	 */
	final public Boolean[] getReadArray(int numTokens) {
		if (read + numTokens <= size) {
			return contents;
		} else {
			Boolean[] buffer = new Boolean[numTokens];

			int numEnd = size - read;
			int numBeginning = numTokens - numEnd;

			// Copy the end of the fifo
			if (numEnd != 0) {
				System.arraycopy(contents, read, buffer, 0, numEnd);
			}

			// Copy the rest of the data at the beginning of the FIFO
			if (numBeginning != 0) {
				System.arraycopy(contents, 0, buffer, numEnd, numBeginning);
			}

			return buffer;
		}
	}

	/**
	 * Returns the array where <code>numTokens</code> can be written.
	 * 
	 * @param numTokens
	 *            a number of tokens to write
	 * @return the array where <code>numTokens</code> can be written
	 */
	final public Boolean[] getWriteArray(int numTokens) {
		if (write + numTokens <= size) {
			return contents;
		} else {
			return new Boolean[numTokens];
		}
	}

	public String toString() {
		return write + "/" + read;
	}

	/**
	 * Signals that writing is finished.
	 * 
	 * @param numTokens
	 *            the number of tokens that were written
	 */
	final public void writeEnd(int numTokens, Boolean[] buffer) {
		fillCount += numTokens;
		if (write + numTokens <= size) {
			write += numTokens;
		} else {
			int numEnd = size - write;
			int numBeginning = numTokens - numEnd;

			// Copy data at the end of the FIFO
			if (numEnd != 0) {
				System.arraycopy(buffer, 0, contents, write, numEnd);
			}

			// Copy the rest of data at the beginning of the FIFO
			if (numBeginning != 0) {
				System.arraycopy(buffer, numEnd, contents, 0, numBeginning);
			}

			write = numBeginning;
		}
		// Output tracing if required
		if (out != null) {
			try {
				for (int i = 0; i < buffer.length; i++) {
					if (buffer[i] == null)
						break;
					if (buffer[i]) {
						out.write("1\n");
					} else {
						out.write("0\n");
					}
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
