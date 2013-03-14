/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.simulators;

import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;

import java.util.Map;

import net.sf.orcc.plugins.PluginFactory;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

/**
 * This class defines a factory class that contains a list of simulators and
 * their options. The simulators are classes that implement the
 * {@link Simulator} interface and are declared in the
 * <code>net.sf.orcc.simulators.simulator</code> extension point.
 * 
 * @author Pierre-Laurent Lagalaye
 * @author Matthieu Wipliez
 * 
 */
public class SimulatorFactory extends PluginFactory {

	private static final SimulatorFactory instance = new SimulatorFactory();

	/**
	 * Returns the single instance of this factory
	 * 
	 * @return the single instance of this factory
	 */
	public static SimulatorFactory getInstance() {
		return instance;
	}

	/**
	 * private constructor called when this class is loaded and instance is
	 * initialized
	 */
	private SimulatorFactory() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(Activator.PLUGIN_ID
						+ ".simulators");

		parsePlugins(elements);
	}

	/**
	 * Runs the simulator specified by the configuration.
	 * 
	 * @param monitor
	 *            Eclipse monitor to manage process
	 * @param mode
	 *            "run" or "debug"
	 * @param options
	 *            launch options
	 * @throws Exception
	 * @deprecated will be removed in a future version. Please consider using
	 *             {@link #getSimulator} instead
	 */
	@Deprecated
	public void runSimulator(IProgressMonitor monitor, String mode,
			Map<String, Object> options) throws Exception {
		// Get the simulator plugin
		String simulatorName = (String) options.get(SIMULATOR);
		Simulator simulator = (Simulator) plugins.get(simulatorName);

		simulator.setOptions(options);
		simulator.setProgressMonitor(monitor);
		simulator.run();
	}

	/**
	 * Return the simulator instance corresponding to <code>name</code>. Please
	 * note that simulator returned is not configured with an options map.
	 * 
	 * @param name
	 * @return
	 */
	public Simulator getSimulator(String name) {
		Simulator simulator = (Simulator) plugins.get(name);
		return simulator;
	}

}
