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

import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;

/**
 * This class contribute to perform all updates needed when user trigger the
 * Rename refactoring on a network file (xdf).
 * 
 * @author Antoine Lorence
 * 
 */
public class NetworkRenameParticipant extends RenameParticipant {

	private final ChangesFactory factory;

	private IFile originalNetworkFile;
	private IPath originalDiagramPath;
	private String originalFilename;
	private String originalBasename;
	private String newFilename;
	private String newBasename;

	public NetworkRenameParticipant() {
		super();
		factory = new ChangesFactory();
	}

	ChangesFactory getChangesFactory() {
		return factory;
	}

	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IFile) {
			originalNetworkFile = (IFile) element;
			originalFilename = originalNetworkFile.getFullPath().toFile()
					.getName();
			originalBasename = originalFilename.substring(0,
							originalFilename.lastIndexOf("."
									+ OrccUtil.NETWORK_SUFFIX));
			newFilename = getArguments().getNewName();
			newBasename = newFilename.substring(0,
					newFilename.lastIndexOf("." + OrccUtil.NETWORK_SUFFIX));

			originalDiagramPath = originalNetworkFile.getFullPath()
					.removeFileExtension()
					.addFileExtension(OrccUtil.DIAGRAM_SUFFIX);

			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Network rename participant";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		registerThisNetworkUpdate();
		registerThisDiagramUpdate();

		return factory.getAllChanges(originalNetworkFile.getProject(), "Pre-rename updates");
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		final CompositeChange changes = new CompositeChange(
				"Post-rename updates");

		final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace().getRoot();
		if (wpRoot.exists(originalDiagramPath)) {
			changes.add(new RenameResourceChange(originalDiagramPath,
					newBasename + '.' + OrccUtil.DIAGRAM_SUFFIX));
		}
		changes.add(getOtherNetworksContentChanges());
		changes.add(getOtherDiagramsContentChanges());

		return changes.getChildren().length > 0 ? changes : null;
	}

	public void registerThisNetworkUpdate() {
		factory.addReplacement(OrccUtil.NETWORK_SUFFIX, "<XDF name=\"" + originalBasename + "\">",
				"<XDF name=\"" + newBasename + "\">");
	}

	public void registerThisDiagramUpdate() {

		final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace().getRoot();
		if (wpRoot.exists(originalDiagramPath)) {
			factory.addReplacement(OrccUtil.DIAGRAM_SUFFIX, originalFilename
					+ "#/", newFilename + "#/");
			factory.addReplacement(OrccUtil.DIAGRAM_SUFFIX, "name=\""
					+ originalBasename + "\"", "name=\"" + newBasename + "\"");
		}
	}

	public Change getOtherNetworksContentChanges() {
		final IPath newFilePath = originalNetworkFile.getFullPath()
				.removeLastSegments(1).append(newFilename);
		final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace().getRoot();

		final String oldQualifiedName = OrccUtil
				.getQualifiedName(originalNetworkFile);
		final String newQualifiedName = OrccUtil.getQualifiedName(wpRoot
				.getFile(newFilePath));
		factory.addReplacement("<Class name=\"" + oldQualifiedName + "\"/>",
				"<Class name=\"" + newQualifiedName + "\"/>");

		return factory.getReplacementChange(originalNetworkFile.getProject(),
				OrccUtil.NETWORK_SUFFIX, "Update network files");
	}

	public Change getOtherDiagramsContentChanges() {
		final String originalRefinement = originalNetworkFile.getFullPath().toString();
		final String newRefinement = originalNetworkFile.getFullPath().removeLastSegments(1)
				.append(newFilename).toString();

		factory.addReplacement("key=\"refinement\" value=\"" + originalRefinement + "\"",
				"key=\"refinement\" value=\"" + newRefinement + "\"");

		return factory.getReplacementChange(originalNetworkFile.getProject(),
				OrccUtil.DIAGRAM_SUFFIX, "Update diagram files");
	}
}
