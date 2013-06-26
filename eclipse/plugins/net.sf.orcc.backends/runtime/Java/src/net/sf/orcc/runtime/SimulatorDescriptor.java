package net.sf.orcc.runtime;
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


import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccRuntimeException;

/**
 * This class handle handle descriptors used by the native functions.
 * 
 * @author Thavot Richard
 * 
 */
public class SimulatorDescriptor {

	private static Map<Integer, File> fileDescs = new HashMap<Integer, File>();
	private static Map<Integer, Method> shutters = new HashMap<Integer, Method>();

	public static Integer create(String fileName, Method shutter) {
		File f = new File(fileName);
		Integer desc = f.hashCode();
		fileDescs.put(desc, f);
		shutters.put(desc, shutter);
		return desc;
	}

	public static File get(Integer descriptor) {
		return fileDescs.get(descriptor);
	}

	public static boolean contains(Integer descriptor) {
		return fileDescs.containsKey(descriptor);
	}

	public static void finalize(Integer descriptor) {
		fileDescs.remove(descriptor);
	}

	/**
	 * Kill all simulation descriptors by closing them correctly.
	 */
	public static void killDescriptors() {

		Set<Integer> descs = new HashSet<Integer>(fileDescs.keySet());
		for (Integer desc : descs) {
			Method shutter = shutters.get(desc);
			if (shutter != null) {
				Object args[] = new Object[1];
				args[0] = desc;
				try {
					shutter.invoke(null, args);
				} catch (Exception e) {
					throw new OrccRuntimeException(
							"exception during the killing "
									+ "of the descriptors by calling to "
									+ shutter.getName(), e);
				}
			}
		}
		
		if (!fileDescs.isEmpty()) {
			fileDescs.clear();
			throw new OrccRuntimeException("A descriptor may be still alive");
		}

		shutters.clear();
	}
}
