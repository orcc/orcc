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
import org.eclipse.core.resources.IFolder;
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
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.resource.MoveResourceChange;

/**
 * This class contribute to perform all updates needed when user trigger the
 * Move refactoring on a network file (xdf).
 * 
 * @author Antoine Lorence
 * 
 */
public class NetworkMoveParticipant extends MoveParticipant {

	private final ChangesFactory factory;

	private IFile originalNetworkFile;
	private IFolder destination;
	private IPath newNetworkPath;
	private IPath originalDiagramPath;

	public NetworkMoveParticipant() {
		super();
		factory = new ChangesFactory();
	}

	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IFile) {
			originalNetworkFile = (IFile) element;
			destination = (IFolder) getArguments().getDestination();
			newNetworkPath = destination.getFile(originalNetworkFile.getName())
					.getFullPath();
			originalDiagramPath = originalNetworkFile.getFullPath()
					.removeFileExtension()
					.addFileExtension(OrccUtil.DIAGRAM_SUFFIX);
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Network move participant";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		final CompositeChange changes = new CompositeChange("Post-move updates");
		final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace().getRoot();
		if (wpRoot.exists(originalDiagramPath)) {
			changes.add(new MoveResourceChange(wpRoot
					.getFile(originalDiagramPath), destination));
		}

		changes.add(getOtherNetworksContentChanges());
		changes.add(getOtherDiagramsContentChanges());

		return changes.getChildren().length > 0 ? changes : null;
	}

	public Change getOtherNetworksContentChanges() {
		final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace().getRoot();
		final String oldQualifiedName = OrccUtil
				.getQualifiedName(originalNetworkFile);
		final String newQualifiedName = OrccUtil.getQualifiedName(wpRoot
				.getFile(newNetworkPath));
		factory.addReplacement("<Class name=\"" + oldQualifiedName + "\"/>",
				"<Class name=\"" + newQualifiedName + "\"/>");

		return factory.getReplacementChange(originalNetworkFile.getProject(),
				OrccUtil.NETWORK_SUFFIX, "Update network files");
	}

	public Change getOtherDiagramsContentChanges() {
		final String originalRefinement = originalNetworkFile.getFullPath()
				.toString();
		final String newRefinement = newNetworkPath.toString();

		factory.addReplacement("key=\"refinement\" value=\""
				+ originalRefinement + "\"", "key=\"refinement\" value=\""
				+ newRefinement + "\"");

		return factory.getReplacementChange(originalNetworkFile.getProject(),
				OrccUtil.DIAGRAM_SUFFIX, "Update diagram files");
	}
}
