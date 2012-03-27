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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.runtime.RuntimeFactory;
import net.sf.orcc.runtime.impl.GenericSource;
import net.sf.orcc.runtime.impl.IntfChannel;
import net.sf.orcc.runtime.impl.IntfNet;
import net.sf.orcc.runtime.impl.SystemIO;

/**
 * This class handle handle descriptors used by the native functions.
 * 
 * @author Thavot Richard
 * 
 */
public class SimulatorDescriptor {

	private static Map<Integer, GenericSource> descsMap = new HashMap<Integer, GenericSource>();

	public static BigInteger create(IntfNet net) {
		Integer desc = net.hashCode();
		descsMap.put(desc, net);
		return new BigInteger(desc.toString());
	}

	public static BigInteger create(IntfChannel channel) {
		Integer desc = channel.hashCode();
		descsMap.put(desc, channel);
		return new BigInteger(desc.toString());
	}

	public static BigInteger create(SystemIO io) {
		Integer desc = io.hashCode();
		descsMap.put(desc, io);
		return new BigInteger(desc.toString());
	}

	public static GenericSource get(BigInteger descriptor) {
		if (descsMap.containsKey(descriptor.intValue()))
			return descsMap.get(descriptor.intValue());
		return null;
	}

	public static IntfNet getIntfNet(BigInteger descriptor) {
		if (descsMap.containsKey(descriptor.intValue())) {
			GenericSource g = descsMap.get(descriptor.intValue());
			if (g.isIntfNet())
				return (IntfNet) g;
		}
		return RuntimeFactory.createIntfNet();
	}

	public static IntfChannel getIntfChannel(BigInteger descriptor) {
		if (descsMap.containsKey(descriptor.intValue())) {
			GenericSource g = descsMap.get(descriptor.intValue());
			if (g.isIntfChannel())
				return (IntfChannel) g;
		}
		return RuntimeFactory.createIntfChannel();
	}

	public static SystemIO getSystemIO(BigInteger descriptor) {
		if (descsMap.containsKey(descriptor.intValue())) {
			GenericSource g = descsMap.get(descriptor.intValue());
			if (g.isSystemIO())
				return (SystemIO) g;
		}
		return RuntimeFactory.createSystemIO();
	}

	public static boolean contains(BigInteger descriptor) {
		return descsMap.containsKey(descriptor.intValue());
	}

	/**
	 * Kill all simulation descriptors by closing them correctly.
	 */
	public static void killDescriptors() {
		for (GenericSource g : descsMap.values()) {
			g.close();
		}
		descsMap.clear();
	}

	public static void finalize(BigInteger descriptor) {
		if (descsMap.containsKey(descriptor.intValue())) {
			descsMap.remove(descriptor.intValue()).close();
		}
	}

}
