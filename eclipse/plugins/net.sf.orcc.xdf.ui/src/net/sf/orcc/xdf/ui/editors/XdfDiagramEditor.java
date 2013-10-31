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
package net.sf.orcc.xdf.ui.editors;

import java.io.IOException;

import net.sf.orcc.xdf.ui.Activator;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.graphiti.ui.editor.EditorInputAdapter;
import org.eclipse.graphiti.ui.editor.IDiagramEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

/**
 * This class customize the default diagram editor.
 * 
 * It is used to allow opening the right editor when user double-click on a Xdf
 * file.
 * 
 * @author Antoine Lorence
 */
public class XdfDiagramEditor extends DiagramEditor {

	public static final String DIAGRAM_EDITOR_ID = "net.sf.orcc.xdf.ui.editors.XdfDiagramEditor"; //$NON-NLS-1$

	public XdfDiagramEditor() {
		super();
	}

	/**
	 * When user try to open a Xdf file directly, this overridden method try to
	 * find the corresponding diagram. If the diagram can't be found, it is
	 * created.
	 */
	@Override
	protected DiagramEditorInput convertToDiagramEditorInput(IEditorInput input) throws PartInitException {

		if (input instanceof IFileEditorInput) {
			final IFileEditorInput fileInput = (IFileEditorInput) input;

			// The input is an Xdf resource
			if (Activator.NETWORK_SUFFIX.equals(fileInput.getFile().getFileExtension())) {
				final IWorkspaceRoot workspaceRoot = fileInput.getFile().getWorkspace().getRoot();

				final URI xdfUri = URI.createPlatformResourceURI(fileInput.getFile().getFullPath().toString(), true);
				final URI diagramUri = xdfUri.trimFileExtension()
						.appendFileExtension(Activator.DIAGRAM_SUFFIX);
				final IPath diagramPath = new Path(diagramUri.toPlatformString(true));

				// The diagram associated with the Xdf doesn't exists
				if (!workspaceRoot.exists(diagramPath)) {
					try {
						// Create it (empty). Its content will be updated in
						// UpdateDiagramFeature
						XdfUtil.createDiagramResource(diagramUri);
					} catch (IOException e) {
						throw new PartInitException("Unable to create the diagram " + diagramUri); //$NON-NLS-1$
					}
				}

				input = new FileEditorInput(workspaceRoot.getFile(diagramPath));
			}
		}

		final IEditorInput newInput = EditorInputAdapter.adaptToDiagramEditorInput(input);
		if (!(newInput instanceof IDiagramEditorInput)) {
			throw new PartInitException("Unknown editor input: " + input); //$NON-NLS-1$
		}
		return (DiagramEditorInput) newInput;
	}

}
