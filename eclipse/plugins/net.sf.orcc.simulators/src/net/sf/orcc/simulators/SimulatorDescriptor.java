/*
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.simulators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handle handle descriptors used by the native functions.
 * 
 * @author Thavot Richard
 * 
 */
public class SimulatorDescriptor {

	private static Map<Integer, RandomAccessFile> fileDescs = new HashMap<Integer, RandomAccessFile>();

	public static int openFile(String path) throws FileNotFoundException {
		RandomAccessFile in = new RandomAccessFile(path, "rw");
		fileDescs.put(in.hashCode(), in);
		return in.hashCode();
	}

	public static RandomAccessFile getFile(int descriptor) {
		return fileDescs.get(descriptor);
	}

	public static boolean containsFile(int descriptor) {
		return fileDescs.containsKey(descriptor);
	}

	public static void closeFile(int descriptor) throws IOException {
		fileDescs.get(descriptor).close();
		fileDescs.remove(descriptor);
	}

	/**
	 *  Kill all simulation descriptors by closing them correctly.
	 */
	public static void killDescriptors() {
		for (RandomAccessFile in : fileDescs.values()) {
			try {
				in.close();
			} catch (IOException e) {
				String msg = "I/O error : A file cannot be close";
				throw new RuntimeException(msg, e);
			}
		}
		fileDescs.clear();
	}
}
