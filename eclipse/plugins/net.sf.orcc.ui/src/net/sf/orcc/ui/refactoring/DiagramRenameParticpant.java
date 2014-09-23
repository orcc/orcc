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
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;

/**
 * This class contribute to perform all updates needed when user trigger the
 * Rename refactoring on a diagram file.
 * 
 * @author Antoine Lorence
 * 
 */
public class DiagramRenameParticpant extends RenameParticipant {

	private NetworkRenameParticipant networkRenameParticipant;

	private IFile originalDiagramFile;
	private IPath originalNetworkPath;
	private String newDiagramFilename;
	private String newNetworkFilename;

	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IFile) {
			originalDiagramFile = (IFile) element;

			originalNetworkPath = originalDiagramFile.getFullPath()
					.removeFileExtension()
					.addFileExtension(OrccUtil.NETWORK_SUFFIX);

			newDiagramFilename = getArguments().getNewName();

			final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace()
					.getRoot();
			if (wpRoot.exists(originalNetworkPath)) {
				final IFile networkFile = wpRoot.getFile(originalNetworkPath);
				networkRenameParticipant = new NetworkRenameParticipant();
				newNetworkFilename = newDiagramFilename.replace("."
						+ OrccUtil.DIAGRAM_SUFFIX, "."
						+ OrccUtil.NETWORK_SUFFIX);

				final RenameArguments renameArguments = new RenameArguments(
						newNetworkFilename, true);
				networkRenameParticipant.initialize(getProcessor(),
						networkFile, renameArguments);
			} else {
				networkRenameParticipant = null;
			}

			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Diagram rename participant";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		if (networkRenameParticipant == null) {
			return null;
		}

		networkRenameParticipant.registerThisNetworkUpdate();
		networkRenameParticipant.registerThisDiagramUpdate();

		return networkRenameParticipant.getChangesFactory().getAllChanges(
				originalDiagramFile.getProject(), "Pre-rename updates");
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		// The corresponding XDF must be renamed
		if (networkRenameParticipant == null) {
			return null;
		}

		networkRenameParticipant.registerOtherNetworksUpdates();
		networkRenameParticipant.registerOtherDiagramsUpdates();
		final CompositeChange changes = (CompositeChange) networkRenameParticipant
				.getChangesFactory()
				.getAllChanges(originalDiagramFile.getProject(),
						"Post-rename updates");
		changes.add(new RenameResourceChange(originalNetworkPath,
				newNetworkFilename));
		return changes;
	}
}
