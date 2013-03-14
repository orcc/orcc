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

import static net.sf.orcc.OrccLaunchConstants.BACKEND;
import net.sf.orcc.OrccActivator;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.Backend;
import net.sf.orcc.backends.BackendFactory;
import net.sf.orcc.ui.OrccUiActivator;
import net.sf.orcc.ui.console.OrccUiConsoleHandler;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

/**
 * This class implements a launch configuration delegate to launch a backend.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccRunLaunchDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		monitor.subTask("Launching backend...");

		// Configure the logger with the console attached to the process
		OrccLogger.configureLoggerWithHandler(new OrccUiConsoleHandler(
				OrccUiActivator.getOrccConsole("Compilation console")));

		Job job = new Job("Compile job") {

			@SuppressWarnings("unchecked")
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				String backendName = "unknown";
				try {
					backendName = configuration.getAttribute(BACKEND, "");
				} catch (CoreException e1) {
					OrccLogger
							.severeln("Unable to find backend name in configuration attributes !");
					return Status.CANCEL_STATUS;
				}

				setName(backendName);

				try {
					// Get the backend instance
					BackendFactory factory = BackendFactory.getInstance();
					Backend backend = factory.getBackend(backendName);

					// Configure it with options set in "Run Config" panel by
					// user
					backend.setOptions(configuration.getAttributes());
					// Launch compilation
					backend.compile();

				} catch (OrccRuntimeException e) {

					if (!e.getMessage().isEmpty()) {
						OrccLogger.severeln(e.getMessage());
					}
					OrccLogger.severeln(backendName
							+ " backend could not generate code ("
							+ e.getCause() + ")");

					return new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
							backendName + " backend could not generate code", e);

				} catch (Exception e) {
					return new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
							backendName + " backend could not generate code", e);
				}
				return Status.OK_STATUS;
			}
		};

		job.setUser(true);
		job.schedule();
	}
}
