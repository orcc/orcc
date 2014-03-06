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
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

public class CalRenameParticipant extends RenameParticipant {

	private final ChangesFactory factory;

	private IFile originalFile;
	private IFile newFile;
	private String originalFilename;
	private String newFilename;
	private String originalBasename;
	private String newBasename;

	public CalRenameParticipant() {
		super();
		factory = new ChangesFactory();
	}

	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IFile) {

			originalFile = (IFile) element;
			originalFilename = originalFile.getFullPath().toFile()
					.getName();
			newFilename = getArguments().getNewName();
			originalBasename = originalFilename.substring(0,
					originalFilename.lastIndexOf("." + OrccUtil.CAL_SUFFIX));
			newBasename = newFilename.substring(0,
					newFilename.lastIndexOf("." + OrccUtil.CAL_SUFFIX));

			final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace()
					.getRoot();
			newFile = wpRoot.getFile(originalFile.getFullPath()
					.removeLastSegments(1).append(newFilename));
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Actor/Unit rename particpant";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		final CompositeChange changes = new CompositeChange(
				"Pre-rename updates");
		changes.add(getFileContentUpdatesChange());
		return changes.getChildren().length > 0 ? changes : null;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		final CompositeChange changes = new CompositeChange(
				"Post-rename updates");
		changes.add(getNetworksContentUpdatesChanges());
		changes.add(getDiagramsContentUpdatesChanges());
		changes.add(getOtherCalContentUpdatesChanges());
		return changes.getChildren().length > 0 ? changes : null;
	}

	private Change getFileContentUpdatesChange() {
		factory.clearReplacementMaps();

		final Pattern actorUnitPattern = Pattern
				.compile("(actor|unit)(\\s+)"+originalBasename+"(\\s*)\\(");
		final String actorUnitNameReplacement = "$1$2" + newBasename + "$3(";

		factory.addReplacement(actorUnitPattern, actorUnitNameReplacement);
		return factory
				.getReplacementChange(originalFile, "Update file content");
	}

	private Change getNetworksContentUpdatesChanges() {
		factory.clearReplacementMaps();

		final String oldQualifiedName = OrccUtil.getQualifiedName(originalFile);
		final String newQualifiedName = OrccUtil.getQualifiedName(newFile);
		factory.addReplacement("<Class name=\"" + oldQualifiedName + "\"/>",
				"<Class name=\"" + newQualifiedName + "\"/>");

		final String title = "Update network files";
		return factory.getReplacementChange(originalFile.getProject(),
				OrccUtil.NETWORK_SUFFIX, title);

	}

	private Change getDiagramsContentUpdatesChanges() {
		factory.clearReplacementMaps();

		final IFile irFile = OrccUtil.getFile(originalFile.getProject(),
				OrccUtil.getQualifiedName(originalFile), OrccUtil.IR_SUFFIX);

		final String originalRefinement = irFile.getFullPath()
				.toString();
		final String newRefinement = originalRefinement.replace(
				originalBasename, newBasename);

		factory.addReplacement("key=\"refinement\" value=\""
				+ originalRefinement + "\"", "key=\"refinement\" value=\""
				+ newRefinement + "\"");

		final String title = "Update diagram files";
		return factory.getReplacementChange(originalFile.getProject(),
				OrccUtil.DIAGRAM_SUFFIX, title);
	}

	private Change getOtherCalContentUpdatesChanges() {
		factory.clearReplacementMaps();

		final String originalQualifiedName = OrccUtil
				.getQualifiedName(originalFile);
		final Pattern importPattern = Pattern.compile("import(\\s+)"
				+ originalQualifiedName + "(\\.(\\*|\\w+))(\\s*);");

		final String targetQualifiedName = OrccUtil.getQualifiedName(newFile);
		final String replacement = "import$1" + targetQualifiedName + "$2$4;";

		factory.addReplacement(importPattern, replacement);

		return factory.getReplacementChange(originalFile.getProject(),
				OrccUtil.CAL_SUFFIX, "Update actors referencing this unit.");
	}
}
