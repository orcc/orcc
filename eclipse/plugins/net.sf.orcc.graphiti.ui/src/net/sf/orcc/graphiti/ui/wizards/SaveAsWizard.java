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

import java.io.IOException;
import java.io.InputStream;

import net.sf.orcc.graphiti.ui.editors.GraphEditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * This class provides a save as graph wizard.
 * 
 * @author Matthieu Wipliez
 */
public class SaveAsWizard extends Wizard implements INewWizard {

	private IStructuredSelection selection;

	private IWorkbench workbench;

	/**
	 * Constructor for {@link SaveAsWizard}.
	 */
	public SaveAsWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Save As...");
	}

	@Override
	public void addPages() {
		IWizardPage page = new WizardGraphTypePage(selection);
		page.setDescription("Save the graph with the chosen type.");
		addPage(page);

		addPage(new WizardConvertPage(selection));

		// To improve user experience, the selection is the editor's input file.
		IStructuredSelection selection = this.selection;
		Object obj = selection.getFirstElement();
		if (obj instanceof GraphEditor) {
			GraphEditor editor = (GraphEditor) obj;
			IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				IFile file = ((IFileEditorInput) input).getFile();
				selection = new StructuredSelection(file);
			}
		}

		page = new WizardSaveGraphPage(selection);
		page.setDescription("Save graph as.");
		addPage(page);
	}

	/**
	 * Returns the workbench which was passed to <code>init</code>.
	 * 
	 * @return the workbench
	 */
	public IWorkbench getWorkbench() {
		return workbench;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		this.workbench = workbench;
	}

	@Override
	public boolean performFinish() {
		final WizardConvertPage convertPage = (WizardConvertPage) getPage("convertGraph");
		final WizardSaveGraphPage page = (WizardSaveGraphPage) getPage("saveGraph");
		page.setGraph(convertPage.getGraph());

		InputStream in = page.getInitialContents();
		if (in == null) {
			return false;
		}
		try {
			in.close();
		} catch (IOException e) {
			// don't care because in is a byte array input stream
		}

		IFile file = page.createNewFile();
		if (file == null) {
			return false;
		}

		// Open editor on new file.
		IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();
		try {
			if (dw != null) {
				BasicNewResourceWizard.selectAndReveal(file, dw);
				IWorkbenchPage activePage = dw.getActivePage();
				if (activePage != null) {
					IDE.openEditor(activePage, file, true);
				}
			}
		} catch (PartInitException e) {
			MessageDialog.openError(dw.getShell(), "Problem opening editor", e
					.getMessage());
		}

		return true;
	}
}