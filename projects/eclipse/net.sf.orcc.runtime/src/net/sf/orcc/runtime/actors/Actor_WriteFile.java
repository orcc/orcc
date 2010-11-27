/*
 * Copyright (c) 2010, AKATECH SA
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
 *   * Neither the name of the AKATECH SA nor the names of its
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

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_boolean;
import net.sf.orcc.runtime.Fifo_int;

public class Actor_WriteFile implements IActor {

	private Fifo_int fifo_In;
	private Fifo_boolean fifo_EOF;
	
	private String fileName;
	private DataOutputStream out;

	public Actor_WriteFile(String s) {
		fileName = s;
		try {
			out = new DataOutputStream(new FileOutputStream (fileName));
		} catch (FileNotFoundException e) {
			String msg = "file not found: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	public String getNextSchedulableAction() {
		if (fifo_In.hasRoom(1)) {
			return "write";
		}
		return null;
	}

	@Override
	public void initialize() {
	}

	@Override
	public int schedule() {
		int i = 0;
		boolean res;

		do {
			res = false;
			if (out != null && fifo_In.hasTokens(1)) {
				res = true;
				int[] IN = fifo_In.getReadArray(1);
				int IN_Index = fifo_In.getReadIndex(1);
				try {
					out.writeByte(IN[IN_Index]);
				} catch (IOException e) {
					String msg = "I/O exception while writing file \""
						+ fileName + "\"";
					throw new RuntimeException(msg, e);
				}
				fifo_In.readEnd(1);
			} else if (out != null && fifo_EOF.hasTokens(1)) {
				res = true;
				fifo_EOF.getReadArray(1);
				fifo_EOF.getReadIndex(1);
//				close();
				fifo_EOF.readEnd(1);
			}
		} while (res);

		return i;
	}

	@Override
	public void setFifo(String portName, Fifo fifo) {
		if ("In".equals(portName)) {
			fifo_In = (Fifo_int) fifo;
		} else if("EOF".equals(portName)) {
			fifo_EOF = (Fifo_boolean) fifo;
		}
		else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Close the input stream file
	 */
	public void close() {
		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
