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

import net.sf.orcc.graphiti.model.DefaultRefinementPolicy;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
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
			newRefinement = selectNetwork(vertex, shell);
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

		IProject project = getProject(vertex);
		String qualifiedName = refinement.replace('.', '/');

		// first try networks
		IFile file = OrccUtil.getFile(project, qualifiedName,
				OrccUtil.NETWORK_SUFFIX);
		if (file != null) {
			return file;
		}

		// then actors
		file = OrccUtil.getFile(project, qualifiedName, OrccUtil.CAL_SUFFIX);
		if (file != null) {
			return file;
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

		FilteredRefinementDialog dialog = new FilteredRefinementDialog(project,
				shell, OrccUtil.CAL_SUFFIX);
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

	/**
	 * Selects the qualified identifier of a network.
	 * 
	 * @param vertex
	 *            a vertex
	 * @param shell
	 *            shell
	 * @return the qualified identifier of a network
	 */
	private String selectNetwork(Vertex vertex, Shell shell) {
		IProject project = getProject(vertex);

		FilteredRefinementDialog dialog = new FilteredRefinementDialog(project,
				shell, OrccUtil.NETWORK_SUFFIX);
		dialog.setTitle("Select network");
		dialog.setMessage("&Select existing network:");
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
