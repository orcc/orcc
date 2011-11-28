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

package std.io.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.runtime.impl.GenericSource;
import net.sf.orcc.simulators.SimulatorDescriptor;

/**
 * This class defines native functions for the File unit.
 * 
 * This class uses the SimulatorDecriptor class to handle descriptors.
 * 
 * @author Thavot Richard
 * 
 */
public class AccessFile extends GenericSource {

	private static Map<Integer, RandomAccessFile> randomAccessFiles = new HashMap<Integer, RandomAccessFile>();

	public static Integer openFile(String fileName) {
		try {
			if (fileName.isEmpty())
				fileName = getFileName();
			Integer desc = SimulatorDescriptor.create(fileName,
					AccessFile.class.getMethod("closeFile", Integer.class));
			randomAccessFiles.put(desc, new RandomAccessFile(
					SimulatorDescriptor.get(desc), "rw"));
			return desc;
		} catch (Exception e) {
			String msg = "I/O error : A file cannot be open";
			throw new RuntimeException(msg, e);
		}
	}

	public static Integer closeFile(Integer desc) {
		if (SimulatorDescriptor.contains(desc)) {
			try {
				randomAccessFiles.get(desc).close();
				SimulatorDescriptor.finalize(desc);
			} catch (IOException e) {
				String msg = "I/O error : A file cannot be close";
				throw new RuntimeException(msg, e);
			}
		}
		return 0;
	}

	public static Integer readByte(Integer desc) {
		if (SimulatorDescriptor.contains(desc)) {
			try {
				return randomAccessFiles.get(desc).readUnsignedByte();
			} catch (IOException e) {
				String msg = "I/O error : readByte function";
				throw new RuntimeException(msg, e);
			}
		}
		return 0;
	}

	public static void readBytes(Integer desc, byte buf[], Integer count) {
		if (SimulatorDescriptor.contains(desc)) {
			RandomAccessFile raf = randomAccessFiles.get(desc);
			try {
				for (int i = 0; i < count; i++) {
					buf[i] = (byte) raf.readUnsignedByte();
				}
			} catch (IOException e) {
				String msg = "I/O error : readNBytes function";
				throw new RuntimeException(msg, e);
			}
		}
	}

	public static void writeByte(Integer desc, Integer v) {
		if (SimulatorDescriptor.contains(desc)) {
			try {
				randomAccessFiles.get(desc).writeByte(v);
			} catch (IOException e) {
				String msg = "I/O error : writeByte function";
				throw new RuntimeException(msg, e);
			}
		}
	}

	public static void writeBytes(Integer desc, byte buf[], Integer count) {
		if (SimulatorDescriptor.contains(desc)) {
			RandomAccessFile raf = randomAccessFiles.get(desc);
			try {
				for (int i = 0; i < count; i++) {
					raf.writeByte(buf[i]);
				}
			} catch (IOException e) {
				String msg = "I/O error : readNBytes function";
				throw new RuntimeException(msg, e);
			}
		}
	}

	public static Integer sizeOfFile(Integer desc) {
		if (SimulatorDescriptor.contains(desc)) {
			try {
				return (int) randomAccessFiles.get(desc).length();
			} catch (IOException e) {
				String msg = "I/O error : sizeOfFile function";
				throw new RuntimeException(msg, e);
			}
		}
		return 0;
	}

	public static void seek(Integer desc, Integer pos) {
		if (SimulatorDescriptor.contains(desc)) {
			try {
				randomAccessFiles.get(desc).seek(pos);
			} catch (IOException e) {
				String msg = "I/O error : seek function";
				throw new RuntimeException(msg, e);
			}
		}
	}

	public static Integer filePointer(Integer desc) {
		if (SimulatorDescriptor.contains(desc)) {
			try {
				return (int) randomAccessFiles.get(desc).getFilePointer();
			} catch (IOException e) {
				String msg = "I/O error : filePointer function";
				throw new RuntimeException(msg, e);
			}
		}
		return 0;
	}

}
