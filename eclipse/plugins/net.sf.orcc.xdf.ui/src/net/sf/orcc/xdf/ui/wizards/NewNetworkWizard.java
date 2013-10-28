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
package net.sf.orcc.xdf.ui.wizards;

import java.io.IOException;
import java.util.Collections;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.xdf.ui.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.PictogramsFactory;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
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

	public NewNetworkWizard() {
		super();
		// setNeedsProgressMonitor(true);
		setWindowTitle("New XDF Network (tests)");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

		this.workbench = workbench;

		WizardNewFileCreationPage page = new WizardNewFileCreationPage("filenameSelection", selection);
		page.setFileExtension(Activator.DIAGRAM_SUFFIX);
		page.setDescription("Here is my description");

		// Fill the page with a filename, if user selected one
		if (!selection.isEmpty()) {
			Object firstSel = selection.getFirstElement();
			if (firstSel instanceof IFile) {
				IFile selectedFile = (IFile) firstSel;
				String fileName = selectedFile.getName();
				String ext = selectedFile.getFileExtension();
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
		
		IFile file = page.createNewFile();
		if (file == null) {
			return false;
		}

		// File name (without extension)
		String networkName = file.getFullPath().removeFileExtension().lastSegment();

		// Create a new network
		Network network = DfFactory.eINSTANCE.createNetwork();
		network.setName(networkName);
		// Create a new diagram
		Diagram diagram = Graphiti.getPeCreateService().createDiagram(Activator.DIAGRAM_SUFFIX, networkName, true);

		// Link diagram to network
		PictogramLink link = PictogramsFactory.eINSTANCE.createPictogramLink();
		link.getBusinessObjects().add(network);
		diagram.setLink(link);

		// Initialize URIs for diagram and xdf resources
		IPath xdfPath = file.getFullPath();
		URI xdfUri = URI.createPlatformResourceURI(xdfPath.toString(), true);
		IPath diagramPath = xdfPath.removeFileExtension().addFileExtension("diagram");
		URI diagramUri = URI.createPlatformResourceURI(diagramPath.toString(), true);

		// Create a resource to store network content
		// Obtain a new resource set
		ResourceSet resourceSet = new ResourceSetImpl();

		// Create resources
		Resource xdfResource = resourceSet.createResource(xdfUri);
		Resource diagramResource = resourceSet.createResource(diagramUri);

		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		xdfResource.getContents().add(network);
		diagramResource.getContents().add(diagram);

		try {
			xdfResource.save(Collections.EMPTY_MAP);
			diagramResource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			return false;
		}

		// Open editor on new file.
		IWorkbenchWindow dw = workbench.getActiveWorkbenchWindow();
		try {
			if (dw != null) {
				BasicNewResourceWizard.selectAndReveal(file, dw);
				IWorkbenchPage activePage = dw.getActivePage();
				if (activePage != null) {
					IDE.openEditor(activePage, file, true);
				}
			}
		} catch (PartInitException e) {
			MessageDialog.openError(dw.getShell(), "Problem opening editor",
					e.getMessage());
			return false;
		}

		return true;
	}

}