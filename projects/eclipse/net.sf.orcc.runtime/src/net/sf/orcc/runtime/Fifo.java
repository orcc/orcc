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
package net.sf.orcc.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * This class defines an abstract FIFO.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class Fifo {

	protected int fillCount;

	protected int read;

	final protected int size;

	protected int write;

	protected FileOutputStream fos = null;

	protected OutputStreamWriter out = null;

	/**
	 * Creates a new FIFO with the given size.
	 * 
	 * @param size
	 *            the size of the FIFO
	 */
	public Fifo(int size) {
		this.size = size;
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
	public Fifo(int size, String folderName, String fifoName) {
		this.size = size;
		// Create network communication tracing file
		File file = new File(folderName);
		try {
			fos = new FileOutputStream(new File(file, fifoName + "_traces.txt"));
			this.out = new OutputStreamWriter(fos, "UTF-8");
		} catch (FileNotFoundException e) {
			String msg = "folder not found: \"" + folderName + "\"";
			throw new RuntimeException(msg, e);
		} catch (UnsupportedEncodingException e) {
			String msg = "unsupported utf8 encoding for folder : \""
					+ folderName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	public void close() {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	@Override
	public String toString() {
		return write + "/" + read;
	}

}
