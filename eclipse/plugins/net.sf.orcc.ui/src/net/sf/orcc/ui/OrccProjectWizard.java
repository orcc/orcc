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
package net.sf.orcc.ui;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccProjectNature;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.xtext.ui.XtextProjectHelper;

/**
 * This class provides a wizard to create a new Orcc project.
 * 
 * @author Matthieu Wipliez
 */
public class OrccProjectWizard extends BasicNewProjectResourceWizard {

	@Override
	public void addPages() {
		super.addPages();
		super.setWindowTitle("New Orcc Project");
	}

	@Override
	protected void initializeDefaultPageImageDescriptor() {
		super.initializeDefaultPageImageDescriptor();
	}

	@Override
	public boolean performFinish() {
		boolean finish = super.performFinish();
		try {
			IProject project = this.getNewProject();
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 3];

			// add new natures
			System.arraycopy(natures, 0, newNatures, 2, natures.length);
			newNatures[0] = OrccProjectNature.NATURE_ID;
			newNatures[1] = XtextProjectHelper.NATURE_ID;
			newNatures[2] = JavaCore.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);

			// retrieves the up-to-date description
			description = project.getDescription();

			// filters out the Java builder
			ICommand[] commands = description.getBuildSpec();
			List<ICommand> buildSpec = new ArrayList<ICommand>(commands.length);
			for (ICommand command : commands) {
				if (!JavaCore.BUILDER_ID.equals(command.getBuilderName())) {
					buildSpec.add(command);
				}
			}

			// updates the description and replaces the existing description
			description.setBuildSpec(buildSpec.toArray(new ICommand[0]));
			project.setDescription(description, null);

			final IProgressMonitor monitor = new NullProgressMonitor();
			// Create a new source folder
			final IFolder src = project.getFolder("src");
			src.create(true, false, monitor);
			final IClasspathEntry srcEntry = JavaCore.newSourceEntry(src
					.getFullPath());

			// Get the JavaProject corresponding to the newly created project
			final IJavaProject javaProject = JavaCore.create(project);
			// Change the default first classpath entry
			final IClasspathEntry[] entries = javaProject.getRawClasspath();
			// was the project itself, now is the source folder freshly created
			entries[0] = srcEntry;
			// Update the classpath entries list
			javaProject.setRawClasspath(entries, monitor);

		} catch (CoreException e) {
			e.printStackTrace();
		}

		return finish;
	}

}