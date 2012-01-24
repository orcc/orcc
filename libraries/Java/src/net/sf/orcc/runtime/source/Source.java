/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.runtime.source;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * This class defines native functions for the Source actor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Source extends GenericSource {

	private static RandomAccessFile in;

	private static int nbLoops = 1;

	public static void source_exit(Integer status) {
		// System.exit(status);
	}

	public static int source_getNbLoop() {
		return nbLoops;
	}

	public static void source_init() {
		try {
			in = new RandomAccessFile(fileName, "r");
		} catch (FileNotFoundException e) {
			String msg = "file not found: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	public static void source_readNBytes(int outTable[], Integer nbTokenToRead) {
		try {
			byte[] b = new byte[outTable.length];
			in.read(b);
			for (int i = 0; i < nbTokenToRead; i++) {
				outTable[i] = b[i] & 0xFF;
			}
		} catch (IOException e) {
			String msg = "I/O error when reading file \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	public static void source_rewind() {
		try {
			// and for Damien, no there are no rewind on RandomAccessFile :)
			in.seek(0L);
		} catch (IOException e) {
			String msg = "I/O error when rewinding file \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	public static int source_sizeOfFile() {
		try {
			if (in == null) {
				return 0;
			}
			return (int) in.length();
		} catch (IOException e) {
			String msg = "I/O error when getting size of file \"" + fileName
					+ "\"";
			throw new RuntimeException(msg, e);
		}
	}

}
