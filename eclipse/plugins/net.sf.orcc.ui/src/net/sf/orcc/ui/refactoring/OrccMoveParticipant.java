/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;

/**
 * This sharable move participant perform all updates necessary when 1 or more
 * file are moved accross packages.
 * 
 * @author Antoine Lorence
 *
 */
public class OrccMoveParticipant extends MoveParticipant implements
		ISharableParticipant {

	private final ChangesFactory factory;

	private IFolder destinationFolder;
	private IProject originalProject;
	private final List<IFile> files;

	public OrccMoveParticipant() {
		super();
		factory = new ChangesFactory();
		destinationFolder = null;
		files = new ArrayList<IFile>();
	}

	@Override
	protected boolean initialize(Object element) {
		files.clear();
		final Object dest = getArguments().getDestination();
		if (dest instanceof IFolder) {
			destinationFolder = (IFolder) dest;
			if (element instanceof IFile) {
				originalProject = ((IFile) element).getProject();
				return registerFile((IFile) element);
			}
		}
		return false;
	}

	@Override
	public void addElement(Object element, RefactoringArguments arguments) {
		if (element instanceof IFile) {
			registerFile((IFile) element);
		}
	}

	private boolean registerFile(IFile file) {
		files.add(file);
		if (OrccUtil.CAL_SUFFIX.equals(file.getFileExtension())) {
			addCalFilesUpdates(file);
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Orcc Move participant";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		factory.computeResults(originalProject);
		return factory.getAllChanges();
	}

	private void addCalFilesUpdates(IFile file) {
		final IFile destinationFile = destinationFolder.getFile(file.getName());

		final String origQualifiedName = OrccUtil.getQualifiedName(file);
		final String newQualifiedName = OrccUtil
				.getQualifiedName(destinationFile);
		final Pattern importPattern = Pattern.compile("import(\\s+)"
				+ origQualifiedName + "(\\.(\\*|\\w+))(\\s*);");

		final String replacement = "import$1" + newQualifiedName + "$2$4;";
		factory.addReplacement(OrccUtil.CAL_SUFFIX, importPattern, replacement);

		factory.addReplacement(OrccUtil.NETWORK_SUFFIX, "<Class name=\""
				+ origQualifiedName + "\"/>", "<Class name=\""
				+ newQualifiedName + "\"/>");

		final IFile irFile = OrccUtil.getFile(file.getProject(),
				OrccUtil.getQualifiedName(file), OrccUtil.IR_SUFFIX);

		final String originalRefinement = irFile.getFullPath().toString();

		final String originalRelativeFolder = file.getParent()
				.getProjectRelativePath().removeFirstSegments(1).toString();
		final String newRelativeFolder = destinationFolder
				.getProjectRelativePath().removeFirstSegments(1).toString();

		final String newRefinement = originalRefinement.replace(
				originalRelativeFolder, newRelativeFolder);

		factory.addReplacement(OrccUtil.DIAGRAM_SUFFIX,
				"key=\"refinement\" value=\"" + originalRefinement + "\"",
				"key=\"refinement\" value=\"" + newRefinement + "\"");
	}
}
