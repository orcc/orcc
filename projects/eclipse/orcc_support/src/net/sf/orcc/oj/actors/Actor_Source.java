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
package net.sf.orcc.oj.actors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.orcc.debug.Location;
import net.sf.orcc.debug.type.StringType;
import net.sf.orcc.oj.CLIParameters;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.debug.AbstractActorDebug;

public class Actor_Source extends AbstractActorDebug {

	private IntFifo fifo_O;

	public String fileName;

	private RandomAccessFile in;

	public Actor_Source() {
		super("Actor_Source.java");

		fileName = CLIParameters.getInstance().getSourceFile();
		actionLocation.put("untagged", new Location(80, 17, 42));

		variables.put("fileName", new StringType());
	}

	@Override
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
		int[] source = new int[1];
		int i = 0;

		try {
			while (!suspended && fifo_O.hasRoom(1)) {
				int byteRead = in.read();
				if (byteRead == -1) {
					// back to beginning
					in.seek(0L);
				}

				source[0] = byteRead;
				fifo_O.put(source);
				i++;
			}
		} catch (IOException e) {
			String msg = "I/O exception: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}

		return i;
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("O".equals(portName)) {
			fifo_O = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

}
