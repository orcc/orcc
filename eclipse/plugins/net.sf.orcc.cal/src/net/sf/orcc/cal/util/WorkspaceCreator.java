/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes and EPFL
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
 *   * Neither the name of the IETR/INSA of Rennes and EPFL nor the names of its
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
package net.sf.orcc.cal.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccProjectNature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * This application take a folder path in argument and create a valid Eclipse
 * workspace from .project files found in it. The .metadata (workspace
 * information) folder is created in the current Eclipse workspace.
 * 
 * In command-line, workspace folder can be set by using
 * "-data &lt;workspace&gt;" argument.
 * 
 * @author alorence
 * 
 */
public class WorkspaceCreator implements IApplication {

	private IProgressMonitor progressMonitor;
	private String nature;
	private IWorkspace workspace;
	private boolean isAutoBuildActivated;

	public WorkspaceCreator() {
		progressMonitor = new NullProgressMonitor();

		nature = OrccProjectNature.NATURE_ID;

		workspace = ResourcesPlugin.getWorkspace();
		isAutoBuildActivated = false;
	}

	private void disableAutoBuild() throws CoreException {
		IWorkspaceDescription desc = workspace.getDescription();
		if (desc.isAutoBuilding()) {
			isAutoBuildActivated = true;
			desc.setAutoBuilding(false);
			workspace.setDescription(desc);
		}
	}

	private void restoreAutoBuild() throws CoreException {
		if (isAutoBuildActivated) {
			IWorkspaceDescription desc = workspace.getDescription();
			desc.setAutoBuilding(true);
			workspace.setDescription(desc);
		}
	}

	/**
	 * Scan the list of IJavaProject rax Classpath, extract projects from this
	 * list and add them to the IProject referencedProjects list.
	 * 
	 * @throws CoreException
	 */
	private void registerReferencedProjects(IJavaProject jp, IProject p)
			throws CoreException {

		List<IProject> referencedProjects = new ArrayList<IProject>();

		IClasspathEntry[] classpathEntries = jp.getRawClasspath();
		for (IClasspathEntry cpe : classpathEntries) {
			if (cpe.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
				IProject referencedProject = (IProject) workspace.getRoot()
						.getProject(cpe.getPath().toString());

				referencedProjects.add(referencedProject);
			}
		}

		IProjectDescription desc = p.getDescription();
		desc.setReferencedProjects(referencedProjects.toArray(new IProject[0]));
		p.setDescription(desc, progressMonitor);
	}

	/**
	 * Open searchFolder and try to find .project files inside it. Then, try to
	 * create an eclipse projects and add it to the current workspace.
	 * 
	 * @param searchFolder
	 * @throws CoreException
	 * @throws OrccException
	 */
	private void searchForProjects(File searchFolder) throws CoreException,
			OrccException {

		if (!searchFolder.isDirectory()) {
			throw new OrccException("Bad path to search project : "
					+ searchFolder.getPath());
		} else {
			File[] children = searchFolder.listFiles();
			for (File child : children) {
				if (child.isDirectory()) {
					searchForProjects(child);
				} else if (child.getName().equals(
						IProjectDescription.DESCRIPTION_FILE_NAME)) {
					IPath projectFile = new Path(child.getAbsolutePath());

					IProjectDescription description = workspace
							.loadProjectDescription(projectFile);

					if (description.hasNature(nature)) {
						IProject project = workspace.getRoot().getProject(
								description.getName());

						if (!project.exists()) {
							project.create(description, progressMonitor);
							project.open(progressMonitor);

							IJavaProject jp = JavaCore.create(project);
							registerReferencedProjects(jp, project);
							
							System.out.println(project.getName());
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
	 * IApplicationContext)
	 */
	@Override
	public Object start(IApplicationContext context) {
		Map<?, ?> map = context.getArguments();

		String[] args = (String[]) map
				.get(IApplicationContext.APPLICATION_ARGS);

		if (args.length == 1) {

			try {
				disableAutoBuild();

				File searchPath = new File(args[0]).getCanonicalFile();
				System.out.println("Register projects from "
						+ searchPath.getAbsolutePath() + " to workspace "
						+ workspace.getRoot().getLocation());
				searchForProjects(searchPath);

				return IApplication.EXIT_OK;

			} catch (CoreException e) {
				System.err.println(e.getMessage());
			} catch (OrccException e) {
				System.err.println(e.getMessage());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} finally {
				try {
					restoreAutoBuild();
				} catch (CoreException e) {
					System.err.println(e.getMessage());
				}
			}
		} else {
			System.err
					.println("Please add the path to a directory containing .project files.");
		}

		return IApplication.EXIT_RELAUNCH;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	@Override
	public void stop() {
	}

}
