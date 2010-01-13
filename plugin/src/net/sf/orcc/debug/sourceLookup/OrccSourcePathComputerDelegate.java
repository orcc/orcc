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
package net.sf.orcc.debug.sourceLookup;

import static net.sf.orcc.ui.launching.OrccLaunchConstants.INPUT_FILE;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.DirectorySourceContainer;

/**
 * Computes the default source lookup path for a ORCC launch configuration. The
 * default source lookup path is the folder or project containing the RVC-CAL
 * model being simulated.
 */
public class OrccSourcePathComputerDelegate implements
		ISourcePathComputerDelegate {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.internal.core.sourcelookup.ISourcePathComputerDelegate
	 * #computeSourceContainers(org.eclipse.debug.core.ILaunchConfiguration,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ISourceContainer[] computeSourceContainers(
			ILaunchConfiguration configuration, IProgressMonitor monitor)
			throws CoreException {
		// Get source path
		String inputFile = configuration.getAttribute(INPUT_FILE, "");
		File dir = new File(inputFile).getParentFile();

		return new ISourceContainer[] { new DirectorySourceContainer(dir, true) };

		// String path = "net.sf.orcc.examples";
		// ISourceContainer sourceContainer = null;
		// if (path != null) {
		// IResource resource = ResourcesPlugin.getWorkspace().getRoot()
		// .findMember(new Path(path));
		// if (resource != null) {
		// IContainer container = resource.getParent();
		// if (container.getType() == IResource.PROJECT) {
		// sourceContainer = new ProjectSourceContainer(
		// (IProject) container, false);
		// } else if (container.getType() == IResource.FOLDER) {
		// sourceContainer = new FolderSourceContainer(container,
		// false);
		// }
		// }
		// }
		// if (sourceContainer == null) {
		// sourceContainer = new WorkspaceSourceContainer();
		// }
		// return new ISourceContainer[] { sourceContainer };
	}
}
