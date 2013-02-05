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

package net.sf.orcc.simulators.runtime.system.io.impl;

import java.math.BigInteger;

import net.sf.orcc.simulators.SimulatorDescriptor;
import net.sf.orcc.simulators.runtime.RuntimeFactory;
import net.sf.orcc.simulators.runtime.impl.SystemIO;

/**
 * This class defines native functions for the File unit.
 * 
 * This class uses the SimulatorDecriptor class to handle descriptors.
 * 
 * @author Thavot Richard
 * 
 */
public class AccessFile {

	public static BigInteger openFile(String fileName) {
		return SimulatorDescriptor.create(RuntimeFactory
				.createAccessFile(fileName));
	}

	public static BigInteger closeFile(BigInteger desc) {
		SimulatorDescriptor.finalize(desc);
		return new BigInteger("0");
	}

	public static BigInteger readByte(BigInteger desc) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isAccessFile()) {
			new BigInteger(SystemIO.toAccessFile(io).readByte().toString());
		}
		return new BigInteger("0");
	}

	public static void readBytes(BigInteger desc, byte buf[], BigInteger count) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isAccessFile()) {
			SystemIO.toAccessFile(io).readByte(buf, count.intValue());
		}
	}

	public static void writeByte(BigInteger desc, BigInteger v) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isAccessFile()) {
			SystemIO.toAccessFile(io).writeByte(v.byteValue());
		}
	}

	public static void writeBytes(BigInteger desc, byte buf[], BigInteger count) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isAccessFile()) {
			SystemIO.toAccessFile(io).writeByte(buf, count.intValue());
		}
	}

	public static BigInteger sizeOfFile(BigInteger desc) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isAccessFile()) {
			return new BigInteger(SystemIO.toAccessFile(io).sizeOfFile()
					.toString());
		}
		return new BigInteger("0");
	}

	public static void seek(BigInteger desc, BigInteger pos) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isAccessFile()) {
			SystemIO.toAccessFile(io).seek(pos.intValue());
		}
	}

	public static BigInteger filePointer(BigInteger desc) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isAccessFile()) {
			return new BigInteger(SystemIO.toAccessFile(io).filePointer()
					.toString());
		}
		return new BigInteger("0");
	}

}
