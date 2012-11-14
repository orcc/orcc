/*
 * Copyright (c) 2009/2010, IETR/INSA of Rennes
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

import static net.sf.orcc.OrccLaunchConstants.BACKEND;

import java.util.Map;

import net.sf.orcc.plugins.PluginFactory;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

/**
 * A factory class that contains a list of back-ends and their options. The
 * back-ends are classes that implement the {@link Backend} interface and are
 * declared in the <code>net.sf.orcc.plugin.backend</code> extension point.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class BackendFactory extends PluginFactory {

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
	 * private constructor called when this class is loaded and instance is
	 * initialized
	 */
	private BackendFactory() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(Activator.PLUGIN_ID + ".backends");

		parsePlugins(elements);
	}

	/**
	 * Runs the back-end specified by the configuration.
	 * 
	 * @param monitor
	 *            progress monitor
	 * @param listener
	 *            write listener
	 * @param configuration
	 *            launch configuration
	 * @throws Exception
	 */
	public void runBackend(IProgressMonitor monitor, Map<String, Object> options)
			throws Exception {
		String backendName = (String) options.get(BACKEND);
		Backend backend = (Backend) plugins.get(backendName);

		backend.setProgressMonitor(monitor);

		backend.setOptions(options);

		// launches the compilation
		backend.compile();
	}

}
