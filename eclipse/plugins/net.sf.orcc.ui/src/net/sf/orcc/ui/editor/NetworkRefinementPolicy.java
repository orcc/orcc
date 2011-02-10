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
package net.sf.orcc.ui.editor;

import java.util.List;

import net.sf.graphiti.model.DefaultRefinementPolicy;
import net.sf.graphiti.model.Vertex;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * This class extends the default refinement policy with XDF-specific policy.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NetworkRefinementPolicy extends DefaultRefinementPolicy {

	private final String[] fileExtensions = { "nl", "xdf" };

	@Override
	public String getNewRefinement(Vertex vertex) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		// prompts the user to choose a file
		final String message = "The selected instance can be refined by an existing "
				+ "actor or network.";
		MessageDialog dialog = new MessageDialog(shell,
				"Set/Update Refinement", null, message, MessageDialog.QUESTION,
				new String[] { "Select actor", "Select network" }, 0);
		int index = dialog.open();
		String newRefinement = null;
		if (index == 0) {
			newRefinement = selectActor(vertex, shell);
		} else if (index == 1) {
			// network = same behavior as before
			newRefinement = useExistingFile(vertex, shell);
			if (newRefinement != null) {
				for (String extension : fileExtensions) {
					IPath path = getAbsolutePath(vertex.getParent()
							.getFileName(), newRefinement);
					if (extension.equals(path.getFileExtension())) {
						return new Path(newRefinement).removeFileExtension()
								.toString();
					}
				}
			}
		}

		return newRefinement;
	}

	/**
	 * Returns the project to which the vertex belongs.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return a project
	 */
	private IProject getProject(Vertex vertex) {
		return vertex.getParent().getFile().getProject();
	}

	@Override
	public IFile getRefinementFile(Vertex vertex) {
		String refinement = getRefinement(vertex);
		if (refinement == null) {
			return null;
		}

		// first try network
		IPath path = getAbsolutePath(vertex.getParent().getFileName(),
				refinement);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (String extension : fileExtensions) {
			IPath extPath = path.addFileExtension(extension);
			IResource resource = root.findMember(extPath);
			if (resource != null && resource.getType() == IResource.FILE) {
				return (IFile) resource;
			}
		}

		// then actor
		IProject project = getProject(vertex);
		try {
			List<IFolder> folders = OrccUtil.getAllSourceFolders(project);
			for (IFolder folder : folders) {
				String actorPath = refinement.replace('.', '/');
				IFile file = folder.getFile(new Path(actorPath + ".cal"));
				if (file != null && file.exists()) {
					return file;
				}
			}
		} catch (CoreException e) {
			return null;
		}

		return null;
	}

	/**
	 * Selects the qualified identifier of an actor.
	 * 
	 * @param vertex
	 *            a vertex
	 * @param shell
	 *            shell
	 * @return the qualified identifier of an actor
	 */
	private String selectActor(Vertex vertex, Shell shell) {
		IProject project = getProject(vertex);

		FilteredActorsDialog dialog = new FilteredActorsDialog(project, shell);
		dialog.setTitle("Select actor");
		dialog.setMessage("&Select existing actor:");
		String refinement = getRefinement(vertex);
		if (refinement != null) {
			dialog.setInitialPattern(refinement);
		}
		int result = dialog.open();
		if (result == Window.OK) {
			return (String) dialog.getFirstResult();
		}

		return null;
	}

}
