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

import static net.sf.orcc.ui.launching.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_CACHE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DEBUG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DEFAULT_KEEP;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.DOT_CFG;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.ENABLE_CACHE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.KEEP_INTERMEDIATE;
import static net.sf.orcc.ui.launching.OrccLaunchConstants.OUTPUT_FOLDER;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.opendf.eclipse.debug.model.OpendfDebugTarget;
import net.sf.orcc.backends.BackendFactory;
import net.sf.orcc.ui.OrccActivator;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
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
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPage;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageTwo;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.framework.Bundle;

/**
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccDebugLaunchDelegate implements ILaunchConfigurationDelegate {

	private static final String PROJECT_NAME = "orcc project";

	/**
	 * Returns a free port number on localhost, or -1 if unable to find a free
	 * port.
	 * 
	 * @return a free port number on localhost, or -1 if unable to find a free
	 *         port
	 */
	public static int findFreePort() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(0);
			return socket.getLocalPort();
		} catch (IOException e) {
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		return -1;
	}

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

	/**
	 * Creates the given project with Java nature and builder, and opens it.
	 * 
	 * @param project
	 *            project handle
	 * @param monitor
	 *            monitor
	 * @param outputURI
	 *            output URI
	 * @throws CoreException
	 */
	private void createAndOpen(final IProject project,
			IProgressMonitor monitor, final URI outputURI) throws CoreException {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// create the project
		if (!project.exists()) {
			// new description with Java nature and correct location.
			final IProjectDescription description = workspace
					.newProjectDescription(PROJECT_NAME);
			description.setLocationURI(outputURI);
			description.setNatureIds(new String[] { JavaCore.NATURE_ID });

			// set builder
			ICommand command = description.newCommand();
			command.setBuilderName(JavaCore.BUILDER_ID);
			description.setBuildSpec(new ICommand[] { command });

			// create the project with the description
			// note: creating the project, and later setting the
			// description seems not to work as expected.
			project.create(description, monitor);
		}

		// open the project
		if (!project.isOpen()) {
			project.open(monitor);
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

		Random random = new Random();
		File file = new File(dir, "orcc_" + Math.abs(random.nextInt()));
		while (file.exists() || !file.mkdir()) {
			file = new File(dir, "orcc_" + Math.abs(random.nextInt()));
		}

		file.deleteOnExit();

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

			createCmdLine(configuration, cmdList);

			String[] cmdLine = cmdList.toArray(new String[] {});
			monitor.subTask("Launching frontend...");
			int value = launchFrontend(monitor, cmdLine);
			if (monitor.isCanceled()) {
				return;
			}
			monitor.worked(1);

			if (value == 0) {
				monitor.subTask("Launching backend...");
				launchBackend(configuration);

				if (monitor.isCanceled()) {
					return;
				}
				monitor.worked(1);

				monitor.subTask("Starting debugging...");
				launchDebugger(configuration, launch, monitor);
			}

			monitor.worked(1);
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
		String backend = configuration.getAttribute(BACKEND, "");

		String inputFile = configuration.getAttribute(INPUT_FILE, "");
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");

		String file = new File(inputFile).getName();
		String name = outputFolder + File.separator + file;
		int fifoSize = configuration.getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		try {
			BackendFactory factory = BackendFactory.getInstance();
			factory.runBackend(backend, name, fifoSize);
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OrccActivator.PLUGIN_ID,
					backend + " backend could not generate code", e);
			throw new CoreException(status);
		}
	}

	private void launchDebugger(ILaunchConfiguration configuration,
			ILaunch launch, IProgressMonitor monitor) throws CoreException,
			IOException {

		int commandPort = findFreePort();
		int eventPort = findFreePort();
		if (commandPort == -1 || eventPort == -1) {
			throw new CoreException(new Status(IStatus.ERROR,
					OrccActivator.PLUGIN_ID, 0, "Unable to find free port",
					null));
		}

		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");
		final URI outputURI = new File(outputFolder).toURI();

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(PROJECT_NAME);

		// create and open the project
		createAndOpen(project, monitor, outputURI);

		setClasspathAndBuild(project, monitor);

		// load class
		URL url0 = outputURI.toURL();
		URL[] urls = { url0 };
		URLClassLoader cl = new URLClassLoader(urls);
		try {
			Class<?> clasz = cl.loadClass("net.sf.orcc.oj.Interpreter");
			clasz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		IProcess p = DebugPlugin.newProcess(launch, null, "Opendf Debugger");

		IDebugTarget target = new OpendfDebugTarget(launch, p, commandPort,
				eventPort);
		launch.addDebugTarget(target);
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

	private void setClasspathAndBuild(IProject project, IProgressMonitor monitor)
			throws CoreException {
		// add classpath
		IClasspathEntry[] entries = new IClasspathEntry[2];

		// source entry
		IJavaProject myJavaProject = JavaCore.create(project);
		IPath srcPath = project.getFullPath().append("src");
		entries[0] = JavaCore.newSourceEntry(srcPath);

		// container entry
		entries[1] = JavaRuntime.getDefaultJREContainerEntry();

		myJavaProject.setRawClasspath(entries, monitor);

		// set output folder
		IPath binPath = project.getFullPath().append("bin");
		myJavaProject.setOutputLocation(binPath, monitor);

		// build
		project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
	}

}
