/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.dialogs;

import java.io.IOException;

import net.sf.orcc.df.Network;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * This class provides a wizard to create a new XDF network.
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 */
public class NewNetworkWizard extends Wizard implements INewWizard {

	private IWorkbench workbench;

	private final boolean openWhenFinished;

	private Network createdNetwork;

	public NewNetworkWizard() {
		this(true);
	}

	public NewNetworkWizard(boolean openWhenFinished) {
		super();
		// setNeedsProgressMonitor(true);
		setWindowTitle("New XDF Network");


		this.openWhenFinished = openWhenFinished;
		createdNetwork = null;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

		this.workbench = workbench;

		final WizardNewFileCreationPage page = new WizardNewFileCreationPage(
				"filenameSelection", selection) {
			@Override
			protected boolean validatePage() {
				if (!super.validatePage()) {
					return false;
				}

				final IPath path = this.getContainerFullPath();
				final IResource member = ResourcesPlugin.getWorkspace()
						.getRoot().findMember(path);
				if (member instanceof IProject) {
					setMessage("The network can't be created directly "
							+ "in a project. Please select a source folder.",
							DialogPage.ERROR);
					return false;
				} else if (member instanceof IFolder) {
					for (final IFolder sourceFolder : OrccUtil
							.getAllSourceFolders(member.getProject())) {

						// Both are URI starting from project. We can compare
						// their string representation
						if (member.toString().startsWith(
								sourceFolder.toString())) {
							return true;
						}
					}
					setMessage("Target container must be a source folder",
							DialogPage.ERROR);
					return false;
				}

				return true;
			}

			// We don't want "Advanced" section to be displayed.
			@Override
			protected void createAdvancedControls(Composite parent) {
				// Does nothing, DO NOT REMOVE. Overwrites link related stuff
			}

			@Override
			protected IStatus validateLinkedResource() {
				// Does nothing, DO NOT REMOVE. Overwrites link related stuff
				return Status.OK_STATUS;
			}

			@Override
			protected void createLinkTarget() {
				// Does nothing, DO NOT REMOVE. Overwrites link related stuff
			}
		};
		page.setFileExtension(OrccUtil.NETWORK_SUFFIX);
		page.setDescription("Select a parent source folder and a name for the new network.");
		page.setAllowExistingResources(false);

		// Fill the page with a filename, if user selected one
		if (!selection.isEmpty()) {
			final Object firstSel = selection.getFirstElement();
			if (firstSel instanceof IFile) {
				final IFile selectedFile = (IFile) firstSel;
				final String fileName = selectedFile.getName();
				final String ext = selectedFile.getFileExtension();
				if (ext.isEmpty()) {
					page.setFileName(fileName);
				} else {
					int idx = fileName.indexOf(ext);
					if (idx > 0) {
						page.setFileName(fileName.substring(0, idx - 1));
					}
				}
			}
		}

		addPage(page);
	}

	@Override
	public boolean performFinish() {
		final WizardNewFileCreationPage page = (WizardNewFileCreationPage) getPage("filenameSelection");

		final IFile file = page.createNewFile();
		if (file == null) {
			return false;
		}

		final URI xdfUri = URI.createPlatformResourceURI(file.getFullPath()
				.toString(), true);

		try {
			createdNetwork = XdfUtil.createNetworkResource(xdfUri);
		} catch (IOException e) {
			return false;
		}

		// Open editor on new file.
		if (openWhenFinished) {
			final IWorkbenchWindow dw = workbench.getActiveWorkbenchWindow();
			try {
				if (dw != null) {
					BasicNewResourceWizard.selectAndReveal(file, dw);
					final IWorkbenchPage activePage = dw.getActivePage();
					if (activePage != null) {
						IDE.openEditor(activePage, file, true);
					}
				}
			} catch (PartInitException e) {
				MessageDialog.openError(dw.getShell(),
						"Problem opening editor", e.getMessage());
				return false;
			}
		}
		return true;
	}

	public Network getCreatedNetwork() {
		return createdNetwork;
	}
}