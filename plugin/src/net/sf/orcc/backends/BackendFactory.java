/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * A factory class that contains a list of back-ends. The back-ends are classes
 * that implement the {@link IBackend} interface and are declared in the
 * <code>net.sf.orcc.plugin.backend</code> extension point.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class BackendFactory {

	private static final BackendFactory instance = new BackendFactory();

	/**
	 * Returns the single instance of this factory
	 * 
	 * @return the single instance of this factory
	 */
	public static BackendFactory getInstance() {
		return instance;
	}

	/**
	 * list of back-ends.
	 */
	private final Map<String, IBackend> backends;

	/**
	 * private constructor called when this class is loaded and instance is
	 * initialized
	 */
	private BackendFactory() {
		backends = new TreeMap<String, IBackend>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor("net.sf.orcc.plugin.backend");
		for (IConfigurationElement element : elements) {
			String name = element.getAttribute("name");
			try {
				Object obj = element.createExecutableExtension("class");
				backends.put(name, (IBackend) obj);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the list of the names of registered back-ends
	 * 
	 * @return the list of the names of registered back-ends
	 */
	public List<String> listBackends() {
		return new ArrayList<String>(backends.keySet());
	}

	/**
	 * Runs the given back-end on the given input file with the given FIFO size.
	 * 
	 * @param name
	 *            back-end name. Must belong to the list returned by
	 *            {@link #listBackends()}
	 * @param fileName
	 *            name of top-level input network
	 * @param fifoSize
	 *            default size of FIFOs
	 * @throws Exception
	 */
	public void runBackend(String name, String fileName, int fifoSize)
			throws Exception {
		IBackend backend = backends.get(name);
		backend.generateCode(fileName, fifoSize);
	}
}
