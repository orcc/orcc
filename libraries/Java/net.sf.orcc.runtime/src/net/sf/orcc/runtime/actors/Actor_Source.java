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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.orcc.runtime.CLIParameters;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_int;

public class Actor_Source implements IActor {

	private Fifo_int fifo_O;

	private String fileName;

	private RandomAccessFile in;

	public Actor_Source() {
		fileName = CLIParameters.getInstance().getSourceFile();
	}

	public Actor_Source(String fileName) {
		this.fileName = fileName;
	}

	public String getNextSchedulableAction() {
		if (fifo_O.hasRoom(1)) {
			return "untagged";
		}
		return null;
	}

	@Override
	public void initialize() {
		try {
			in = new RandomAccessFile(fileName, "r");
		} catch (FileNotFoundException e) {
			String msg = "file not found: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	@Override
	public int schedule() {
		int i = 0;

		try {
			while (fifo_O.hasRoom(1)) {
				int byteRead = in.read();
				if (byteRead == -1) {
					// back to beginning
					in.seek(0L);
					byteRead = in.read();
				}

				int[] source = fifo_O.getWriteArray(1);
				int source_Index = fifo_O.getWriteIndex(1);
				source[source_Index] = byteRead;
				fifo_O.writeEnd(1, source);

				i++;
			}
		} catch (IOException e) {
			String msg = "I/O exception: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}

		return i;
	}

	@Override
	public void setFifo(String portName, Fifo fifo) {
		if ("O".equals(portName)) {
			fifo_O = (Fifo_int) fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Close the input stream file
	 */
	public void close() {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
