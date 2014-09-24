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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
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
		factory.clearConfiguration();
		factory.resetResults();
		if (element instanceof IFile) {

			originalFile = (IFile) element;
			originalFilename = originalFile.getFullPath().toFile()
					.getName();
			newFilename = getArguments().getNewName();
			originalBasename = originalFilename.substring(0,
					originalFilename.lastIndexOf("." + OrccUtil.CAL_SUFFIX));
			int idx = newFilename.lastIndexOf("." + OrccUtil.CAL_SUFFIX);
			if (idx == -1) {
				newBasename = newFilename;
			} else {
				newBasename = newFilename.substring(0, idx);
			}

			newFile = OrccUtil.workspaceRoot().getFile(
					originalFile.getFullPath().removeLastSegments(1)
							.append(newFilename));
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
		if (!newFilename.endsWith('.' + OrccUtil.CAL_SUFFIX)) {
			return RefactoringStatus
					.createErrorStatus("The new name must have the suffix ."
							+ OrccUtil.CAL_SUFFIX);
		}
		return new RefactoringStatus();
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		final Pattern actor = Pattern.compile("actor(\\s+)" + originalBasename
				+ "(\\s*)\\(");
		factory.addReplacement(OrccUtil.CAL_SUFFIX, actor, "actor$1"
				+ newBasename + "$2" + '(');

		final Pattern unit = Pattern.compile("unit(\\s+)" + originalBasename
				+ "(\\s*):");
		factory.addReplacement(OrccUtil.CAL_SUFFIX, unit, "unit$1"
				+ newBasename + "$2" + ':');

		return factory.getAllChanges(originalFile.getProject(),
				"Pre-rename updates");
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		registerNetworkUpdates();
		registerDiagramUpdates();
		registerOtherCalUpdates();
		return factory.getAllChanges(originalFile.getProject(), "Post-rename updates");
	}

	private void registerNetworkUpdates() {
		final String oldQualifiedName = OrccUtil.getQualifiedName(originalFile);
		final String newQualifiedName = OrccUtil.getQualifiedName(newFile);
		factory.addReplacement(OrccUtil.NETWORK_SUFFIX, "<Class name=\""
				+ oldQualifiedName + "\"/>", "<Class name=\""
				+ newQualifiedName + "\"/>");
	}

	private void registerDiagramUpdates() {
		final IFile irFile = OrccUtil.getFile(originalFile.getProject(),
				OrccUtil.getQualifiedName(originalFile), OrccUtil.IR_SUFFIX);

		final String originalRefinement = irFile.getFullPath().toString();
		final String newRefinement = originalRefinement.replace(
				originalBasename, newBasename);

		factory.addReplacement(OrccUtil.DIAGRAM_SUFFIX,
				"key=\"refinement\" value=\"" + originalRefinement + "\"",
				"key=\"refinement\" value=\"" + newRefinement + "\"");
	}

	private void registerOtherCalUpdates() {
		final String originalQualifiedName = OrccUtil
				.getQualifiedName(originalFile);
		final Pattern importPattern = Pattern.compile("import(\\s+)"
				+ originalQualifiedName + "(\\.(\\*|\\w+))(\\s*);");

		final String targetQualifiedName = OrccUtil.getQualifiedName(newFile);
		final String replacement = "import$1" + targetQualifiedName + "$2$4;";

		factory.addReplacement(OrccUtil.CAL_SUFFIX, importPattern, replacement);
	}
}
