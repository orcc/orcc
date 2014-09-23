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
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.resource.MoveResourceChange;

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
				files.add((IFile) element);
				registerFile((IFile) element);
				return true;
			}
		}
		return false;
	}

	@Override
	public void addElement(Object element, RefactoringArguments arguments) {
		if (element instanceof IFile) {
			files.add((IFile) element);
			registerFile((IFile) element);
		}
	}

	@Override
	public String getName() {
		return "Orcc Move participant";
	}

	private void registerFile(IFile file) {
		final String suffix = file.getFileExtension();
		if(suffix != null) {
			if (OrccUtil.CAL_SUFFIX.equals(suffix)) {
				registerCalUpdates(file);
			} else if (OrccUtil.NETWORK_SUFFIX.equals(suffix)) {
				registerNetworksUpdates(file);
			}
		}
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange change = new CompositeChange("Pre-move updates");
		for (IFile file : files) {
			if (OrccUtil.CAL_SUFFIX.equals(file.getFileExtension())) {

				final String origPackage = OrccUtil.getQualifiedPackage(file);
				final String newPackage = OrccUtil
						.getQualifiedPackage(destinationFolder.getFile(file
								.getName()));

				final Pattern pattern = Pattern.compile("package(\\s+)"
						+ origPackage + "(\\s*);");
				final String replacement = "package$1" + newPackage + "$2;";

				change.add(factory.getUniqueFileReplacement("Update package", file, pattern,
						replacement));
			} else if (OrccUtil.NETWORK_SUFFIX.equals(file.getFileExtension())) {
				final IFile diagFile = file.getProject().getFile(
						file.getProjectRelativePath().removeFileExtension()
								.addFileExtension(OrccUtil.DIAGRAM_SUFFIX));
				if(diagFile.exists() && !files.contains(diagFile)) {
					change.add(new MoveResourceChange(diagFile, destinationFolder));
				}
			} else if (OrccUtil.DIAGRAM_SUFFIX.equals(file.getFileExtension())) {
				final IFile netFile = file.getProject().getFile(
						file.getProjectRelativePath().removeFileExtension()
								.addFileExtension(OrccUtil.NETWORK_SUFFIX));
				if(netFile.exists() && !files.contains(netFile)) {
					registerNetworksUpdates(netFile);
					change.add(new MoveResourceChange(netFile, destinationFolder));
				}
			}
		}
		return change.getChildren().length > 0 ? change : null;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {

		final List<IFile> invalidPaths = new ArrayList<IFile>();
		for (IFile file : files) {
			if (factory.isAffected(file)) {
				invalidPaths.add(file);
			}
		}
		return factory.getAllChanges(originalProject, "Update depending files",
				invalidPaths, destinationFolder);
	}

	private void registerCalUpdates(IFile file) {
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

	private void registerNetworksUpdates(IFile file) {

		final IFile destinationFile = destinationFolder.getFile(file.getName());
		final IPath newNetworkPath = destinationFile.getFullPath();

		final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace().getRoot();
		final String oldQualifiedName = OrccUtil.getQualifiedName(file);
		final String newQualifiedName = OrccUtil.getQualifiedName(wpRoot
				.getFile(newNetworkPath));
		factory.addReplacement(OrccUtil.NETWORK_SUFFIX, "<Class name=\""
				+ oldQualifiedName + "\"/>", "<Class name=\""
				+ newQualifiedName + "\"/>");

		final String originalRefinement = file.getFullPath().toString();
		final String newRefinement = newNetworkPath.toString();

		factory.addReplacement(OrccUtil.DIAGRAM_SUFFIX,
				"key=\"refinement\" value=\"" + originalRefinement + "\"",
				"key=\"refinement\" value=\"" + newRefinement + "\"");
	}
}
