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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class defines a MoveParticipant for Cal actors.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalMoveParticipant extends MoveParticipant {

	private IFile actorFile;

	private XdfReferencesUpdater referenceUpdater;

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		// create reference updater
		referenceUpdater = new XdfReferencesUpdater(actorFile);
		referenceUpdater.checkConditions(context);

		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		Object dest = getArguments().getDestination();
		if (dest instanceof IFolder) {
			IFolder destFolder = (IFolder) dest;
			IFile destFile = destFolder.getFile(actorFile.getName());
			String oldPackage = OrccUtil.getQualifiedPackage(actorFile);
			String newPackage = OrccUtil.getQualifiedPackage(destFile);
			String newQualifiedName = OrccUtil.getQualifiedName(destFile);

			// composite change
			CompositeChange change = new CompositeChange("Replace package '"
					+ oldPackage + "' by references to '" + newPackage + "'");

			// change package
			InputStream in = actorFile.getContents();
			try {
				String networkContents = OrccUtil.getContents(in);
				int offset = networkContents.indexOf(oldPackage);

				TextChange textChange = new TextFileChange("Replace package '"
						+ oldPackage + "' by references to '" + newPackage
						+ "'", destFile);
				change.add(textChange);

				ReplaceEdit edit = new ReplaceEdit(offset, oldPackage.length(),
						newPackage);

				// necessary
				MultiTextEdit multiEdit = new MultiTextEdit();
				textChange.setEdit(multiEdit);
				multiEdit.addChild(edit);
			} catch (IOException e) {
				e.printStackTrace();
			}

			change.add(referenceUpdater.createChange(newQualifiedName));
			return change;
		}

		return null;
	}

	@Override
	public String getName() {
		return "Cal move participant";
	}

	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IFile) {
			IFile file = (IFile) element;
			if ("cal".equals(file.getFileExtension())) {
				actorFile = file;
				return true;
			}
		}

		return false;
	}

}
