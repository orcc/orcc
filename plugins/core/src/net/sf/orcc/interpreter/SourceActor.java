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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.orcc.ir.Actor;

public class SourceActor extends AbstractInterpretedActor {

	private CommunicationFifo fifo_O;

	public String fileName;
	private RandomAccessFile in;

	public SourceActor(String id, Actor actor, String inputBitstream) {
		super(id, actor);
		fileName = inputBitstream;
	}

	@Override
	public void close() {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() {
		// Connect to FIFO
		fifo_O = (CommunicationFifo) actor.getOutput("O").fifo();
		try {
			in = new RandomAccessFile(fileName, "r");
		} catch (FileNotFoundException e) {
			String msg = "file not found: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	@Override
	public Integer schedule() {
		Object[] source = new Integer[1];
		int running = 0;

		try {
			if (fifo_O.hasRoom(1)) {
				int byteRead = in.read();
				if (byteRead != -1) {
					source[0] = byteRead;
					fifo_O.put(source);
					running = 1;
				}
			}
		} catch (IOException e) {
			String msg = "I/O exception: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
		return running;
	}

	@Override
	public int step(boolean doStepInto) {
		return schedule();
	}

}
