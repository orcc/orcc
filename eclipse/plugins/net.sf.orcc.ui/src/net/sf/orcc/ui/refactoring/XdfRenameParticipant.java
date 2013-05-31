/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.ui.refactoring;

import java.io.IOException;
import java.io.InputStream;

import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class defines a RenameParticipant for XDF networks.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfRenameParticipant extends RenameParticipant {

	private IFile networkFile;

	private XdfReferencesUpdater referenceUpdater;

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		if (!"xdf".equals(new Path(getArguments().getNewName())
				.getFileExtension())) {
			return RefactoringStatus.createFatalErrorStatus("The new name"
					+ " of the network must end with .xdf");
		}

		// create reference updater
		referenceUpdater = new XdfReferencesUpdater(networkFile);
		referenceUpdater.checkConditions(context);

		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		String qualifiedName = OrccUtil.getQualifiedName(networkFile);

		// old name of the network
		String simpleName = networkFile.getFullPath().removeFileExtension()
				.lastSegment();

		// new name of the network
		String newName = new Path(getArguments().getNewName())
				.removeFileExtension().lastSegment();

		// new qualified name
		String newQualifiedName = qualifiedName.replace(simpleName, newName);

		return referenceUpdater.createChange(newQualifiedName);
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		String qualifiedName = OrccUtil.getQualifiedName(networkFile);

		// old name of the network
		String simpleName = networkFile.getFullPath().removeFileExtension()
				.lastSegment();

		// new name of the network
		String newName = new Path(getArguments().getNewName())
				.removeFileExtension().lastSegment();

		// composite change
		CompositeChange change = new CompositeChange("Update name of '"
				+ qualifiedName + "' and rename associated resources");

		// change network name
		InputStream in = networkFile.getContents();
		try {
			String networkContents = OrccUtil.getContents(in);
			in.close();
			String name = "<XDF name=\"" + simpleName + "\"";
			int offset = networkContents.indexOf(name);

			TextFileChange textChange = new TextFileChange("Change name of '"
					+ qualifiedName + "'", networkFile);
			change.add(textChange);

			ReplaceEdit edit = new ReplaceEdit(offset + 11,
					simpleName.length(), newName);
			textChange.setEdit(edit);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// change layout (if it exists)
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile layout = root.getFile(networkFile.getFullPath()
				.removeFileExtension().addFileExtension("layout"));
		if (layout.exists()) {
			RenameResourceChange renameChange = new RenameResourceChange(
					layout.getFullPath(), new Path(newName).addFileExtension(
							"layout").lastSegment());
			change.add(renameChange);
		}

		return change;
	}

	@Override
	public String getName() {
		return "XDF rename participant";
	}

	@Override
	protected boolean initialize(Object element) {
		networkFile = (IFile) element;
		return true;
	}

}
