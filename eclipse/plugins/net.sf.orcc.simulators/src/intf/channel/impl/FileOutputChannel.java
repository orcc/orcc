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

package intf.channel.impl;

import java.math.BigInteger;

import net.sf.orcc.runtime.RuntimeFactory;
import net.sf.orcc.simulators.SimulatorDescriptor;

/**
 * This class defines native functions for the File unit.
 * 
 * This class uses the SimulatorDecriptor class to handle descriptors.
 * 
 * @author Thavot Richard
 * 
 */
public class FileOutputChannel {

	public static BigInteger open(String path, String mode) {
		return SimulatorDescriptor.create(RuntimeFactory
				.createFileOutputChannel(path, mode));
	}

	public static void setOption(BigInteger desc, String name, String value) {
		SimulatorDescriptor.getIntfChannel(desc).setOption(name, value);
	}

	public static String getOption(BigInteger desc, String name) {
		return SimulatorDescriptor.getIntfChannel(desc).getOption(name);
	}

	public static boolean exists(BigInteger desc) {
		return SimulatorDescriptor.getIntfChannel(desc).exists();
	}

	public static void close(BigInteger desc) {
		SimulatorDescriptor.finalize(desc);
	}

	public static boolean isInputShutdown(BigInteger desc) {
		return SimulatorDescriptor.getIntfChannel(desc).isInputShutdown();
	}

	public static boolean isOutputShutdown(BigInteger desc) {
		return SimulatorDescriptor.getIntfChannel(desc).isOutputShutdown();
	}
	
	public static void writeByte(BigInteger desc, BigInteger b) {
		SimulatorDescriptor.getIntfChannel(desc).writeByte(b.byteValue());
	}
	
	public static BigInteger readByte(BigInteger desc) {
		byte[] b = new byte[] { SimulatorDescriptor.getIntfNet(desc).readByte() };
		return new BigInteger(b);
	}

}
