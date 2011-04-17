/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.debug.breakpoints;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Adapter to create breakpoints in RVC-CAL files.
 */
public class OrccLineBreakpointAdapter implements IToggleBreakpointsTarget {

	@Override
	public boolean canToggleLineBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return getEditor(part) != null;
	}

	@Override
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	/**
	 * Returns the editor being used to edit a CAL file, associated with the
	 * given part, or <code>null</code> if none.
	 * 
	 * @param part
	 *            workbench part
	 * @return the editor being used to edit a ORCC file, associated with the
	 *         given part, or <code>null</code> if none
	 */
	private ITextEditor getEditor(IWorkbenchPart part) {
		if (part instanceof ITextEditor) {
			ITextEditor editorPart = (ITextEditor) part;
			IResource resource = (IResource) editorPart.getEditorInput()
					.getAdapter(IResource.class);
			if (resource != null) {
				String extension = resource.getFileExtension();
				if (extension != null && extension.equals("cal")) {
					return editorPart;
				}
			}
		}
		return null;
	}

	@Override
	public void toggleLineBreakpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		ITextEditor textEditor = getEditor(part);
		if (textEditor != null) {
			IResource resource = (IResource) textEditor.getEditorInput()
					.getAdapter(IResource.class);
			ITextSelection textSelection = (ITextSelection) selection;
			int lineNumber = textSelection.getStartLine() + 1;

			IBreakpoint[] breakpoints = DebugPlugin.getDefault()
					.getBreakpointManager()
					.getBreakpoints(JDIDebugModel.getPluginIdentifier());
			for (int i = 0; i < breakpoints.length; i++) {
				IBreakpoint breakpoint = breakpoints[i];
				if (resource.equals(breakpoint.getMarker().getResource())) {
					if (((ILineBreakpoint) breakpoint).getLineNumber() == lineNumber) {
						// remove
						breakpoint.delete();
						return;
					}
				}
			}

			// remove project and source folder
			IPath path = resource.getFullPath().removeFileExtension()
					.removeFirstSegments(2);
			String typeName = path.toPortableString().replace('/', '.');
			JDIDebugModel.createLineBreakpoint(resource, typeName, lineNumber,
					-1, -1, 0, true, null);
		}
	}

	@Override
	public void toggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) throws CoreException {
	}

	@Override
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
	}

}
