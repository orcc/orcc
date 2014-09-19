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

import java.util.regex.Pattern;

import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

/**
 * Perform updates when user trigger the move refactoring on a cal file (Actor
 * or Unit)
 * 
 * @author Antoine Lorence
 * 
 */
public class CalMoveParticipant extends MoveParticipant {

	private final ChangesFactory factory;

	private IFile originalFile;
	private IFile destinationFile;
	private IFolder destination;

	public CalMoveParticipant() {
		super();
		factory = new ChangesFactory();
	}

	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IFile) {
			originalFile = (IFile) element;
			destination = (IFolder) getArguments().getDestination();

			destinationFile = destination.getFile(originalFile.getName());
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Actor/Unit move particpant";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		final CompositeChange changes = new CompositeChange("Pre-move updates");
		changes.add(getFileContentUpdatesChange());
		return changes.getChildren().length > 0 ? changes : null;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		final CompositeChange changes = new CompositeChange("Post-move updates");
		changes.add(getNetworksContentUpdatesChanges());
		changes.add(getDiagramsContentUpdatesChanges());
		changes.add(getOtherCalContentUpdatesChanges());
		return changes.getChildren().length > 0 ? changes : null;
	}

	/**
	 * Update the moved cal file
	 * 
	 * @return
	 */
	private Change getFileContentUpdatesChange() {
		final String originalPackage = OrccUtil
				.getQualifiedPackage(originalFile);
		final String destinationPackage = OrccUtil
				.getQualifiedPackage(destinationFile);

		final Pattern packagePattern = Pattern.compile("package(\\s+)"
				+ originalPackage + "(\\s*);");
		final String replacement = "package$1" + destinationPackage + "$2;";
		factory.addReplacement(packagePattern, replacement);

		return factory
				.getReplacementChange(originalFile, "Update file content");
	}

	private Change getOtherCalContentUpdatesChanges() {
		final String originalQualifiedName = OrccUtil
				.getQualifiedName(originalFile);
		final Pattern importPattern = Pattern.compile("import(\\s+)"
				+ originalQualifiedName + "(\\.(\\*|\\w+))(\\s*);");
		final String targetQualifiedName = OrccUtil
				.getQualifiedName(destinationFile);
		final String replacement = "import$1" + targetQualifiedName + "$2$4;";
		factory.addReplacement(importPattern, replacement);

		return factory.getReplacementChange(originalFile.getProject(),
				OrccUtil.CAL_SUFFIX, "Update actors referencing this unit.");
	}

	private Change getNetworksContentUpdatesChanges() {
		final String oldQualifiedName = OrccUtil.getQualifiedName(originalFile);
		final String newQualifiedName = OrccUtil
				.getQualifiedName(destinationFile);
		factory.addReplacement("<Class name=\"" + oldQualifiedName + "\"/>",
				"<Class name=\"" + newQualifiedName + "\"/>");

		return factory.getReplacementChange(originalFile.getProject(),
				OrccUtil.NETWORK_SUFFIX, "Update network files");
	}

	private Change getDiagramsContentUpdatesChanges() {
		final IFile irFile = OrccUtil.getFile(originalFile.getProject(),
				OrccUtil.getQualifiedName(originalFile), OrccUtil.IR_SUFFIX);

		final String originalRefinement = irFile.getFullPath().toString();

		final String originalRelativeFolder = originalFile.getParent()
				.getProjectRelativePath()
				.removeFirstSegments(1).toString();
		final String newRelativeFolder = destination.getProjectRelativePath()
				.removeFirstSegments(1).toString();

		final String newRefinement = originalRefinement.replace(
				originalRelativeFolder, newRelativeFolder);

		factory.addReplacement("key=\"refinement\" value=\""
				+ originalRefinement + "\"", "key=\"refinement\" value=\""
				+ newRefinement + "\"");

		return factory.getReplacementChange(originalFile.getProject(),
				OrccUtil.DIAGRAM_SUFFIX, "Update diagram files");
	}
}
