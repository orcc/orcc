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

	public static Integer create(IntfNet net) {
		int desc = net.hashCode();
		descsMap.put(desc, net);
		return desc;
	}

	public static Integer create(IntfChannel channel) {
		int desc = channel.hashCode();
		descsMap.put(desc, channel);
		return desc;
	}

	public static Integer create(SystemIO io) {
		int desc = io.hashCode();
		descsMap.put(desc, io);
		return desc;
	}

	public static GenericSource get(Integer descriptor) {
		if (descsMap.containsKey(descriptor))
			return descsMap.get(descriptor);
		return null;
	}

	public static IntfNet getIntfNet(Integer descriptor) {
		if (descsMap.containsKey(descriptor)) {
			GenericSource g = descsMap.get(descriptor);
			if (g.isIntfNet())
				return (IntfNet) g;
		}
		return RuntimeFactory.createIntfNet();
	}

	public static IntfChannel getIntfChannel(Integer descriptor) {
		if (descsMap.containsKey(descriptor)) {
			GenericSource g = descsMap.get(descriptor);
			if (g.isIntfChannel())
				return (IntfChannel) g;
		}
		return RuntimeFactory.createIntfChannel();
	}

	public static SystemIO getSystemIO(Integer descriptor) {
		if (descsMap.containsKey(descriptor)) {
			GenericSource g = descsMap.get(descriptor);
			if (g.isSystemIO())
				return (SystemIO) g;
		}
		return RuntimeFactory.createSystemIO();
	}

	public static boolean contains(Integer descriptor) {
		return descsMap.containsKey(descriptor);
	}

	public static void finalize(Integer descriptor) {
		if (descsMap.containsKey(descriptor)) {
			descsMap.remove(descriptor).close();
		}
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

}
