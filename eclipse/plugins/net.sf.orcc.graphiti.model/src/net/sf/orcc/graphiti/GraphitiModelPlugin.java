package net.sf.orcc.graphiti;

/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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

import java.util.Collection;
import java.util.Map;

import net.sf.orcc.graphiti.io.ConfigurationParser;
import net.sf.orcc.graphiti.model.Configuration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class GraphitiModelPlugin extends AbstractUIPlugin {

	/**
	 * The shared instance.
	 */
	private static GraphitiModelPlugin plugin;

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "net.sf.orcc.graphiti.model";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static GraphitiModelPlugin getDefault() {
		return plugin;
	}

	/**
	 * map of configuration name to configuration
	 */
	private Map<String, Configuration> configurations;

	/**
	 * The constructor
	 */
	public GraphitiModelPlugin() {
		plugin = this;
	}

	/**
	 * Returns the configuration with the given name.
	 * 
	 * @param name
	 *            configuration name
	 */
	public Configuration getConfiguration(String name) {
		return configurations.get(name);
	}

	/**
	 * Returns the list of configurations.
	 * 
	 * @return A reference to the {@link Configuration} list.
	 */
	public Collection<Configuration> getConfigurations() {
		return configurations.values();
	}

	/**
	 * Parses the configurations available and (re)loads them.
	 * 
	 * @throws CoreException
	 *             If the file formats cannot be added to Eclipse content type
	 *             system.
	 */
	public void loadConfigurations() throws CoreException {
		ConfigurationParser parser = new ConfigurationParser();
		configurations = parser.getConfigurations();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		loadConfigurations();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
