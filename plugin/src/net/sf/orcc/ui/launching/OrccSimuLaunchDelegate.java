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

import net.sf.orcc.debug.model.OrccProcess;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

/**
 * 
 * @author Matthieu Wipliez
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class OrccSimuLaunchDelegate implements ILaunchConfigurationDelegate {

//	private IOConsole console;
//
//	private IOConsoleOutputStream out;
//
//	/**
//	 * Checks that the URL that points to Orcc frontend executable is not null,
//	 * and throws a CoreException otherwise.
//	 * 
//	 * @param url
//	 * @throws CoreException
//	 */
//	private void checkUrl(URL url) throws CoreException {
//		if (url == null) {
//			String detail1 = "This may be caused by a missing cal2ir.d.byte or "
//					+ "cal2ir.native file in the fragment matching your "
//					+ "platform.";
//			IStatus s1 = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
//					detail1);
//
//			String detail2 = "Another cause of this problem might be that no "
//					+ "Orcc frontend is available for your platform.";
//			IStatus s2 = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
//					detail2);
//
//			IStatus status = new MultiStatus(OrccActivator.PLUGIN_ID, 0,
//					new IStatus[] { s1, s2 },
//					"No executable of Orcc frontend available!", null);
//			throw new CoreException(status);
//		}
//	}
//
//	private void createCmdLine(ILaunchConfiguration configuration,
//			List<String> cmdList) throws CoreException, IOException {
//		String exeName;
//		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
//			exeName = "cal2ir.d.byte";
//		} else {
//			exeName = "cal2ir.native";
//		}
//
//		Bundle bundle = OrccActivator.getDefault().getBundle();
//		IPath path = new Path("frontend/" + exeName);
//
//		URL url = FileLocator.find(bundle, path, null);
//		checkUrl(url);
//		url = FileLocator.toFileURL(url);
//
//		// get an OS-specific executable name
//		path = new Path(url.getPath());
//		String exe = path.toOSString();
//
//		File file = new File(exe);
//		file.setExecutable(true);
//
//		String networkName = configuration.getAttribute(INPUT_FILE, "");
//		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");
//
//		cmdList.add(quoteFile(exe));
//		cmdList.add("-i");
//		cmdList.add(quoteFile(networkName));
//		cmdList.add("-o");
//		cmdList.add(quoteFile(outputFolder));
//
//		if (configuration.getAttribute(DEBUG_MODE, DEFAULT_DEBUG)) {
//			cmdList.add("--debug");
//		}
//		if (configuration.getAttribute(ENABLE_CACHE, DEFAULT_CACHE)) {
//			cmdList.add("--cache");
//		}
//		if (configuration.getAttribute(KEEP_INTERMEDIATE, DEFAULT_KEEP)) {
//			cmdList.add("--keep");
//		}
//		if (configuration.getAttribute(DOT_CFG, DEFAULT_DOT_CFG)) {
//			cmdList.add("--dot-cfg");
//		}
//	}
//
//	private void createConsole(IProcess process) {
//		IConsole console = DebugUITools.getConsole(process);
//		if (console instanceof IOConsole) {
//			this.console = (IOConsole) console;
//			out = this.console.newOutputStream();
//		}
//	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		
		OrccProcess process = new OrccProcess(launch, configuration, monitor);
		launch.addProcess(process);
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			process.start("debugger");
		}else {
			process.start("simulator");			
		}
		
//		List<String> cmdList = new ArrayList<String>();
//		try {
//			createCmdLine(configuration, cmdList);
//
//			String[] cmdLine = cmdList.toArray(new String[] {});
//
//			int value = launchFrontend(configuration.getName(), launch,
//					monitor, cmdLine);
//
//			out.write("Orcc frontend exit code: " + value + "\n");
//			if (value == 0) {
//				monitor.subTask("Launching interpreter...");
//				out.write("\n");
//				out.write("*********************************************"
//						+ "**********************************\n");
//				out.write("Launching Orcc interpretation...\n");
//				if (mode.equals(ILaunchManager.DEBUG_MODE)) {
//					launchDebugInterpreter(launch, monitor, configuration);
//				} else {
//					launchSimuInterpreter(launch, monitor, configuration);
//
//				}
//				out.write("Orcc interpretation ended.");
//			}
//			monitor.worked(1);
//			monitor.done();
//		} catch (IOException e) {
//			MessageDialog.openError(null, "Error launching frontend", e
//					.getMessage());
//		}
	}
//
//
//	/**
//	 * Launches the front-end, and returns the exit value.
//	 * 
//	 * @param name
//	 *            Configuration name.
//	 * @param launch
//	 *            Launch.
//	 * @param monitor
//	 *            Monitor.
//	 * @param cmdLine
//	 *            Command-line.
//	 * @param envp
//	 *            Environment.
//	 * @return the exit value of the process.
//	 * @throws CoreException
//	 */
//	private int launchFrontend(String name, ILaunch launch,
//			IProgressMonitor monitor, String[] cmdLine) throws CoreException {
//		monitor.beginTask("Compiling dataflow program", 2);
//		monitor.subTask("Launching frontend...");
//
//		Process process = DebugPlugin.exec(cmdLine, null);
//		IProcess proc = DebugPlugin.newProcess(launch, process, name);
//		createConsole(proc);
//
//		try {
//			return process.waitFor();
//		} catch (InterruptedException e) {
//			return -1;
//		}
//	}
//
//	/**
//	 * Quote file if file contains spaces.
//	 * 
//	 * @param file
//	 *            The file to check
//	 * @return file or a new string that contains file between double quotes.
//	 */
//	public String quoteFile(String file) {
//		if (file.contains(" ")) {
//			return '"' + file + '"';
//		} else {
//			return file;
//		}
//	}

}
