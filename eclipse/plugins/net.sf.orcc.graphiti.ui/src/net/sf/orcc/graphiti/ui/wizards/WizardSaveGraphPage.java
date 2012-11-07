/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import net.sf.orcc.graphiti.io.GenericGraphWriter;
import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.Graph;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.ui.GraphitiUiPlugin;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * This class provides a page for the save as graph wizard.
 * 
 * @author Matthieu Wipliez
 */
public class WizardSaveGraphPage extends WizardNewFileCreationPage implements
		IGraphTypeSettable {

	private String fileName;

	private Graph graph;

	/**
	 * Constructor for {@link WizardSaveGraphPage}.
	 * 
	 * @param selection
	 *            The current resource selection.
	 */
	public WizardSaveGraphPage(IStructuredSelection selection) {
		super("saveGraph", selection);

		// if the selection is a file, gets its file name and removes its
		// extension. Otherwise, let fileName be null.
		Object obj = selection.getFirstElement();
		if (obj instanceof IFile) {
			IFile file = (IFile) obj;
			String ext = file.getFileExtension();
			fileName = file.getName();
			int idx = fileName.indexOf(ext);
			if (idx != -1) {
				fileName = fileName.substring(0, idx - 1);
			}
		}

		setTitle("Choose file name and parent folder");
	}

	/**
	 * Displays an error message with the given exception.
	 * 
	 * @param message
	 *            A description of the error.
	 * @param exception
	 *            An exception.
	 */
	private void errorMessage(String message, Throwable exception) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		IStatus status = new Status(IStatus.ERROR, GraphitiUiPlugin.PLUGIN_ID,
				message, exception);
		ErrorDialog.openError(shell, "Save error",
				"The file could not be saved.", status, IStatus.ERROR);
	}

	@Override
	public InputStream getInitialContents() {
		// set graph name
		String fileName = getFileName();
		if (fileName != null) {
			IPath filePath = new Path(fileName).removeFileExtension();
			graph.setValue(ObjectType.PARAMETER_ID, filePath.toString());
		}

		// retrieve the IFile so we can get its location
		final IPath containerPath = getContainerFullPath();
		IPath filePath = containerPath.append(getFileName());
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
		graph.setFileName(filePath);

		// writes graph
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GenericGraphWriter writer = new GenericGraphWriter(graph);
		try {
			writer.write(file.getLocation().toString(), out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (RuntimeException e) {
			errorMessage("Exception", e);
		}

		return null;
	}

	/**
	 * Sets a new graph for this page.
	 * 
	 * @param graph
	 *            A {@link Graph}.
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
		Configuration configuration = graph.getConfiguration();
		ObjectType type = graph.getType();
		String fileExt = configuration.getFileFormat().getFileExtension();
		setFileExtension(fileExt);
		if (fileName == null) {
			setFileName("New " + type.getName() + "." + fileExt);
		} else {
			setFileName(fileName + "." + fileExt);
		}
	}

	@Override
	public void setGraphType(Configuration configuration, ObjectType type) {
		// create an empty graph, may be overridden
		graph = new Graph(configuration, type, true);

		String fileExt = configuration.getFileFormat().getFileExtension();
		if (fileName == null) {
			setFileName("New " + type.getName() + "." + fileExt);
		} else {
			setFileName(fileName + "." + fileExt);
		}
	}

}