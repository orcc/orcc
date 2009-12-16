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
package net.sf.orcc.interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.sf.orcc.ir.ICommunicationFifo;

/**
 * A FIFO of object arrays for exchanging data between actors.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class CommunicationFifo implements ICommunicationFifo {

	private Object[] queue;

	private int size;
	private int readPos;
	private int writePos;
	private FileOutputStream fos;
	private OutputStreamWriter out;

	public CommunicationFifo(int size, boolean enableTraces, String fileName,
			String fifoName) throws Exception {
		this.size = size;
		queue = new Object[size];
		// Create network communication tracing file
		if (enableTraces) {
			File file = new File(fileName).getParentFile();
			try {
				fos = new FileOutputStream(new File(file, fifoName
						+ "_traces.txt"));
				this.out = new OutputStreamWriter(fos, "UTF-8");
			} catch (FileNotFoundException e) {
				String msg = "file not found: \"" + fileName + "\"";
				throw new RuntimeException(msg, e);
			}
		} else {
			fos = null;
			out = null;
		}
	}

	public boolean hasRoom(int n) {
		if (readPos > writePos) {
			return (readPos - writePos) > n;
		}
		return (size - writePos + readPos) > n;
	}

	public boolean hasTokens(int n) {
		if (writePos >= readPos) {
			return (writePos - readPos) >= n;
		} else {
			return (size - readPos + writePos) >= n;
		}
	}

	public void get(Object[] target) {
		if (readPos + target.length <= size) {
			System.arraycopy(queue, readPos, target, 0, target.length);
			readPos += target.length;
			if (readPos == size) {
				readPos=0;
			}
		} else {
			System.arraycopy(queue, readPos, target, 0, size - readPos);
			System.arraycopy(queue, 0, target, size - readPos, target.length
					+ readPos - size);
			readPos = target.length + readPos - size;
		}
	}

	public void peek(Object[] target) {
		if (readPos + target.length <= size) {
			System.arraycopy(queue, readPos, target, 0, target.length);
		} else {
			System.arraycopy(queue, readPos, target, 0, size - readPos);
			System.arraycopy(queue, 0, target, size - readPos, target.length
					+ readPos - size);
		}
	}

	public void put(Object[] source) {
		if (writePos + source.length <= size) {
			System.arraycopy(source, 0, queue, writePos, source.length);
			writePos += source.length;
			if (writePos == size) {
				writePos=0;
			}
		} else {
			System.arraycopy(source, 0, queue, writePos, size - writePos);
			System.arraycopy(source, size - writePos, queue, 0, source.length
					+ writePos - size);
			writePos = source.length + writePos - size;
		}
		if (out != null) {
			try {
				for (int i = 0; i < source.length; i++) {
					out.write(source[i] + "");
				}
				out.write("\n");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

}
