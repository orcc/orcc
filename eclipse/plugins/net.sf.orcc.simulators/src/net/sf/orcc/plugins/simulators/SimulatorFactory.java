/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.plugins.simulators;

import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;

import java.util.HashMap;
import java.util.List;

import net.sf.orcc.OrccActivator;
import net.sf.orcc.debug.model.OrccDebugTarget;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.plugins.PluginFactory;
import net.sf.orcc.plugins.PluginOption;
import net.sf.orcc.plugins.simulators.Simulator.SimulatorEvent;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * A factory class that contains a list of simulators and their options. The
 * simulators are classes that implement the {@link Simulator} interface and are
 * declared in the <code>net.sf.orcc.plugin.simulator</code> extension point.
 * 
 * @author Pierre-Laurent Lagalaye
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
		pluginOptions = new HashMap<String, List<PluginOption>>();
		options = new HashMap<String, PluginOption>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(OrccActivator.PLUGIN_ID
						+ ".options");

		parseOptions(elements);
		elements = registry.getConfigurationElementsFor(OrccActivator.PLUGIN_ID
				+ ".simulators");

		parsePlugins(elements);
	}

	/**
	 * Runs the simulator specified by the configuration.
	 * 
	 * @param process
	 *            the process that launched the simulator
	 * @param configuration
	 *            launch configuration
	 * @throws Exception
	 */
	public void runSimulator(OrccProcess process, ILaunch launch,
			ILaunchConfiguration configuration, String option) throws Exception {
		// Get the simulator plugin
		String simulator = configuration.getAttribute(SIMULATOR, "");
		Simulator simuObj = (Simulator) plugins.get(simulator);
		// Set the launch configuration of the simulator
		simuObj.setLaunchConfiguration(configuration);
		// Create a new thread from the Runnable simulator
		Thread simulatorThread = new Thread(simuObj);

		// Create and link a new debug target to the thread if we are in DEBUG
		// mode. Then start the thread. Send a configuration message to the
		// thread if we are not in debug mode.
		try {
			if (option.equals("debugger")) {
				// Set the parent OrccProcess of the simulator, the progress
				// monitor and activate debug mode
				simuObj.configure(process, process.getProgressMonitor(), true);
				// Add the DebugTarget as a listener of the simulator
				OrccDebugTarget target = new OrccDebugTarget(launch, process,
						(Simulator) simuObj);
				launch.addDebugTarget(target);
				((Simulator) simuObj).addPropertyChangeListener(target);
				// Start the thread...
				simulatorThread.start();
			} else {
				simuObj.configure(process, process.getProgressMonitor(), false);
				// Start the thread...
				simulatorThread.start();
			}
			// Start the simulator
			((Simulator) simuObj).message(SimulatorEvent.START, null);
			// ...wait for the end of simulation
			simulatorThread.join();
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					"simulator error", e);
			throw new CoreException(status);
		}
	}

}
