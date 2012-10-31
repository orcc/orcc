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
import net.sf.orcc.backends.BackendFactory;
import net.sf.orcc.ui.console.OrccUiConsoleHandler;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.ui.DebugUITools;

/**
 * This class implements a launch configuration delegate to launch a backend.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccRunLaunchDelegate implements ILaunchConfigurationDelegate {

	@Override
	@SuppressWarnings("unchecked")
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		OrccProcess process = new OrccProcess(launch, configuration, monitor);
		launch.addProcess(process);

		try {
			monitor.subTask("Launching backend...");

			// Configure the logger with the console attached to the process
			OrccLogger.configureLoggerWithHandler(new OrccUiConsoleHandler(
					DebugUITools.getConsole(process)));

			OrccLogger.traceln("*********************************************"
					+ "**********************************");
			OrccLogger.traceln("Launching Orcc backend...");
			String backend = configuration.getAttribute(BACKEND, "");
			try {
				BackendFactory factory = BackendFactory.getInstance();
				factory.runBackend(process.getProgressMonitor(),
						configuration.getAttributes());

				OrccLogger.traceln("Orcc backend done.");
			} catch (OrccRuntimeException exception) {
				OrccLogger.severeln(exception.getMessage());
				OrccLogger.severeln(backend
						+ " backend could not generate code ("
						+ exception.getCause() + ")");
				process.terminate();
			} catch (Exception e) {
				// clear actor pool because it might not have been done if we
				// got an error too soon
				process.terminate();

				IStatus status = new Status(IStatus.ERROR,
						OrccActivator.PLUGIN_ID, backend
								+ " backend could not generate code", e);
				throw new CoreException(status);
			}
		} finally {
			process.terminate();
		}
	}

}
