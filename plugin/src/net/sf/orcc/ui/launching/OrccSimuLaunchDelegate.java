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

import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_CACHE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DEBUG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_KEEP;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.ENABLE_CACHE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_BITSTREAM;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.KEEP_INTERMEDIATE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.OUTPUT_FOLDER;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.interpreter.InterpreterMain;
import net.sf.orcc.ui.OrccActivator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.framework.Bundle;

/**
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class OrccSimuLaunchDelegate implements ILaunchConfigurationDelegate {

	private static final String PROJECT_NAME = "orcc project";

	/**
	 * Checks that the URL that points to Orcc frontend executable is not null,
	 * and throws a CoreException otherwise.
	 * 
	 * @param url
	 * @throws CoreException
	 */
	private void checkUrl(URL url) throws CoreException {
		if (url == null) {
			String detail1 = "This may be caused by a missing cal2ir.d.byte or "
					+ "cal2ir.native file in the fragment matching your "
					+ "platform.";
			IStatus s1 = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					detail1);

			String detail2 = "Another cause of this problem might be that no "
					+ "Orcc frontend is available for your platform.";
			IStatus s2 = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					detail2);

			IStatus status = new MultiStatus(OrccActivator.PLUGIN_ID, 0,
					new IStatus[] { s1, s2 },
					"No executable of Orcc frontend available!", null);
			throw new CoreException(status);
		}
	}

	private void createCmdLine(ILaunchConfiguration configuration,
			List<String> cmdList) throws CoreException, IOException {
		String exeName;
		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
			exeName = "cal2ir.d.byte";
		} else {
			exeName = "cal2ir.native";
		}

		Bundle bundle = OrccActivator.getDefault().getBundle();
		IPath path = new Path("frontend/" + exeName);

		URL url = FileLocator.find(bundle, path, null);
		checkUrl(url);
		url = FileLocator.toFileURL(url);

		// get an OS-specific executable name
		path = new Path(url.getPath());
		String exe = path.toOSString();

		File file = new File(exe);
		file.setExecutable(true);

		String networkName = configuration.getAttribute(INPUT_FILE, "");
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");

		cmdList.add(quoteFile(exe));
		cmdList.add("-i");
		cmdList.add(quoteFile(networkName));
		cmdList.add("-o");
		cmdList.add(quoteFile(outputFolder));

		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
			cmdList.add("--debug");
		}
		if (configuration.getAttribute(ENABLE_CACHE, DEFAULT_CACHE)) {
			cmdList.add("--cache");
		}
		if (configuration.getAttribute(KEEP_INTERMEDIATE, DEFAULT_KEEP)) {
			cmdList.add("--keep");
		}
		if (configuration.getAttribute(DOT_CFG, DEFAULT_DOT_CFG)) {
			cmdList.add("--dot-cfg");
		}
	}

	private File createTempDir() throws IOException {
		// get temporary folder
		String folder = System.getProperty("java.io.tmpdir");
		File dir = new File(folder);

		// Random random = new Random();
		// File file = new File(dir, "orcc_" + Math.abs(random.nextInt()));
		// while (file.exists() || !file.mkdir()) {
		// file = new File(dir, "orcc_" + Math.abs(random.nextInt()));
		// }

		// TODO: when debugger works, remove that.
		File file = new File(dir, "orcc_42");
		if (!file.exists()) {
			file.mkdir();
		}

		return file;
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		List<String> cmdList = new ArrayList<String>();
		try {
			// new output folder in temp directory
			ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
			File file = createTempDir();
			wc.setAttribute(OUTPUT_FOLDER, file.getCanonicalPath());
			wc.doSave();

			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IProject project = workspace.getRoot().getProject(
					PROJECT_NAME);

			if (!project.exists()) {
				createCmdLine(configuration, cmdList);

				String[] cmdLine = cmdList.toArray(new String[] {});
				monitor.subTask("Launching frontend...");
				int value = launchFrontend(monitor, cmdLine);
				if (monitor.isCanceled()) {
					return;
				}
				monitor.worked(1);

				if (value == 0) {
					monitor.subTask("Launching interpreter...");
					launchBackend(configuration);

					if (monitor.isCanceled()) {
						return;
					}
					monitor.worked(1);
				}
			}

		} catch (IOException e) {
			MessageDialog.openError(null, "Error launching frontend", e
					.getMessage());
		} finally {
			monitor.done();
		}
	}

	/**
	 * Calls one of the backends.
	 * 
	 * @param configuration
	 *            The configuration.
	 * @throws CoreException
	 */
	private void launchBackend(ILaunchConfiguration configuration)
			throws CoreException {
		String inputFile = configuration.getAttribute(INPUT_FILE, "");
		String inputBitstream = configuration.getAttribute(INPUT_BITSTREAM, "");
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");

		String file = new File(inputFile).getName();
		String name = outputFolder + File.separator + file;
		int fifoSize = configuration.getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		try {
			InterpreterMain interpreter = InterpreterMain.getInstance();
			interpreter.interpretNetwork(name, inputBitstream, fifoSize);
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					"interpreter error", e);
			throw new CoreException(status);
		}
	}

	/**
	 * Launches the front-end, and returns the exit value.
	 * 
	 * @param monitor
	 *            Monitor.
	 * @param cmdLine
	 *            Command-line.
	 * @return the exit value of the process.
	 * @throws InterruptedException
	 */
	private int launchFrontend(IProgressMonitor monitor, String[] cmdLine)
			throws CoreException {
		monitor.beginTask("Compiling dataflow program", 2);
		monitor.subTask("Launching frontend...");

		try {
			Process process = DebugPlugin.exec(cmdLine, null);
			return process.waitFor();
		} catch (InterruptedException e) {
			Status status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					"Could not launch frontend", e);
			throw new CoreException(status);
		}
	}

	/**
	 * Quote file if file contains spaces.
	 * 
	 * @param file
	 *            The file to check
	 * @return file or a new string that contains file between double quotes.
	 */
	private String quoteFile(String file) {
		if (file.contains(" ")) {
			return '"' + file + '"';
		} else {
			return file;
		}
	}

}
