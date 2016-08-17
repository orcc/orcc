/*
 * Copyright (c) 2016, Heriot-Watt University
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
package net.sf.orcc.simulators.runtime.std.stdio.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.simulators.runtime.impl.GenericWriter;

/**
 * This class defines native functions for the Writer actor.
 * 
 * @author Rob Stewart
 * 
 */
public class Writer extends GenericWriter {

	private static RandomAccessFile out;

	public static void Writer_init() {
		try {
			File oldFile = new File(outputFile);
			if (oldFile.exists()) {
				oldFile.delete();
			}
			out = new RandomAccessFile(outputFile, "rw");
		} catch (FileNotFoundException e) {
			String msg = "Cannot write to file: \"" + outputFile + "\"";
			throw new OrccRuntimeException(msg, e);
		}
	}

	public static void Writer_write(BigInteger b) {
		try {
			out.writeByte(b.byteValue());
		} catch (IOException e) {
			String msg = "Cannot write " + b + " to " + outputFile;
			throw new OrccRuntimeException(msg, e);
		}
	}

	public static void Writer_close() {
		try {
			out.close();
		} catch (IOException e) {
			String msg = "Cannot close " + outputFile;
			throw new OrccRuntimeException(msg, e);
		}
	}
}
