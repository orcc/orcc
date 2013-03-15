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
package net.sf.orcc.ui.launching;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;
import net.sf.orcc.OrccActivator;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.simulators.Simulator;
import net.sf.orcc.simulators.SimulatorFactory;
import net.sf.orcc.ui.console.OrccUiConsoleHandler;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccLogger.OrccLevel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.DebugUITools;

/**
 * This class implements a launch configuration delegate to launch a simulator.
 * 
 * @author Matthieu Wipliez
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class OrccSimuLaunchDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		// set the log level
		if (mode.equals("debug")
				|| configuration.getAttribute(DEBUG_MODE, false)) {
			OrccLogger.setLevel(OrccLevel.ALL);
		} else {
			OrccLogger.setLevel(OrccLevel.NOTICE);
		}

		Job job = new Job("Simulation job") {

			Simulator currentSimulator;

			@SuppressWarnings("unchecked")
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				String simulatorName = "unknown";
				try {
					simulatorName = configuration.getAttribute(SIMULATOR, "");
				} catch (CoreException e1) {
					OrccLogger
							.severeln("Unable to find simulator name in configuration attributes !");
					return Status.CANCEL_STATUS;
				}

				OrccLogger.traceln("Simulation starts: " + simulatorName);
				this.setName(simulatorName);

				IStatus returnStatus = Status.OK_STATUS;

				try {
					Simulator simulator = SimulatorFactory.getInstance()
							.getSimulator(simulatorName);

					currentSimulator = simulator;

					simulator.setOptions(configuration.getAttributes());
					simulator.run();

				} catch (OrccRuntimeException e) {

					if (!e.getMessage().isEmpty()) {
						OrccLogger.severeln(e.getMessage());
					}
					OrccLogger.severeln(simulatorName + " error ("
							+ e.getCause() + ")");

					returnStatus = new Status(IStatus.ERROR,
							OrccActivator.PLUGIN_ID, simulatorName + " error",
							e);

				} catch (Exception e) {

					e.printStackTrace();

					Throwable throwable = e;
					StringBuilder builder = new StringBuilder();
					while (throwable != null
							&& throwable.getCause() != throwable) {
						builder.append(throwable.getLocalizedMessage());
						builder.append('\n');
						throwable = throwable.getCause();
					}

					returnStatus = new Status(IStatus.ERROR,
							OrccActivator.PLUGIN_ID, simulatorName
									+ " simulation error: "
									+ builder.toString());
				} finally {
					OrccLogger.restoreLevels();
					OrccLogger.setLevel(OrccLevel.ALL);
				}

				return returnStatus;
			}

			@Override
			protected void canceling() {
				currentSimulator.stop();
				super.canceling();
				OrccLogger
						.traceln("Simulation aborted (from eclipse control).");
				this.done(Status.OK_STATUS);
			}
		};

		IProcess process = new OrccProcess(job, launch);
		launch.addProcess(process);

		// Configure the logger with the console attached to the process
		OrccLogger.configureLoggerWithHandler(new OrccUiConsoleHandler(
				DebugUITools.getConsole(process)));

		job.setUser(true);
		job.schedule();
	}
}
