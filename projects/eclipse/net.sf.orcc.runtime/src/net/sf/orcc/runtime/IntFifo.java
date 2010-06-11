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

/**
 * This class defines a FIFO of integers.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IntFifo {

	/**
	 * The contents of the FIFO.
	 */
	private int[] contents;

	private int fillCount;

	private int read;

	private int size;

	private int write;

	public IntFifo(int size) {
		this.size = size;
		contents = new int[size];
	}

	/**
	 * Returns the array where <code>numTokens</code> can be read.
	 * 
	 * @param numTokens
	 *            a number of tokens to read
	 * @return the array where <code>numTokens</code> can be read
	 */
	final public int[] getReadArray(int numTokens) {
		if (read + numTokens <= size) {
			return contents;
		} else {
			int[] buffer = new int[numTokens];

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
	 * Returns the index at which tokens can be read in the array returned by
	 * {@link #getReadArray(int)}.
	 * 
	 * @param numTokens
	 *            a number of tokens to read
	 * @return the index at which tokens can be read in the array returned by
	 *         {@link #getReadArray(int)}
	 */
	final public int getReadIndex(int numTokens) {
		if (read + numTokens <= size) {
			return read;
		} else {
			return 0;
		}
	}

	/**
	 * Returns the array where <code>numTokens</code> can be written.
	 * 
	 * @param numTokens
	 *            a number of tokens to write
	 * @return the array where <code>numTokens</code> can be written
	 */
	final public int[] getWriteArray(int numTokens) {
		if (write + numTokens <= size) {
			return contents;
		} else {
			return new int[numTokens];
		}
	}

	/**
	 * Returns the index at which tokens can be written in the array returned by
	 * {@link #getWriteArray(int)}.
	 * 
	 * @param numTokens
	 *            a number of tokens to write
	 * @return the index at which tokens can be written in the array returned by
	 *         {@link #getWriteArray(int)}
	 */
	final public int getWriteIndex(int numTokens) {
		if (write + numTokens <= size) {
			return write;
		} else {
			return 0;
		}
	}

	/**
	 * Returns <code>true</code> if there is enough room for the given number of
	 * tokens in this FIFO.
	 * 
	 * @param numTokens
	 *            a number of tokens
	 * @return <code>true</code> if there is enough room for the given number of
	 *         tokens in this FIFO
	 */
	final public boolean hasRoom(int numTokens) {
		return (size - fillCount) >= numTokens;
	}

	/**
	 * Returns <code>true</code> if the FIFO contains at least the given number
	 * of tokens.
	 * 
	 * @param numTokens
	 *            a number of tokens
	 * @return <code>true</code> if the FIFO contains at least the given number
	 *         of tokens
	 */
	final public boolean hasTokens(int numTokens) {
		return fillCount >= numTokens;
	}

	/**
	 * Signals that reading is finished.
	 * 
	 * @param numTokens
	 *            the number of tokens that were read
	 */
	final public void readEnd(int numTokens) {
		fillCount -= numTokens;
		read += numTokens;
		if (read > size) {
			read -= size;
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
	final public void writeEnd(int numTokens, int[] buffer) {
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
	}

}
