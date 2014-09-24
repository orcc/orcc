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
 * files are moved across packages.
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

	/**
	 * Main initialization. Called once per move action, with the first file to
	 * move as argument.
	 * 
	 * We use this method to perform some cleans (if this instance is reused
	 * from a previous move), initialize the target folder and register the
	 * first file to store.
	 */
	@Override
	protected boolean initialize(Object element) {
		files.clear();
		factory.clearConfiguration();
		factory.resetResults();
		final Object dest = getArguments().getDestination();
		if (dest instanceof IFolder) {
			destinationFolder = (IFolder) dest;
			if (element instanceof IFile) {
				originalProject = ((IFile) element).getProject();
				registerFile((IFile) element);
				return true;
			}
		}
		return false;
	}

	/**
	 * Add another file in the list of files to move. At this point, we are sure
	 * that all the files added are originally contained in the same package.
	 */
	@Override
	public void addElement(Object element, RefactoringArguments arguments) {
		if (element instanceof IFile) {
			registerFile((IFile) element);
		}
	}

	@Override
	public String getName() {
		return "Orcc Move participant";
	}

	/**
	 * Store the given file to the list of files to move, and register
	 * replacements to apply on other files specific to its type, its name, its
	 * qualified name, etc.
	 * 
	 * @param file
	 */
	private void registerFile(IFile file) {
		files.add(file);
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

	/**
	 * <p>
	 * Generates the Change instance whose update all the files that will be
	 * moved in this action. These changes will be applied before really moving
	 * the file.
	 * </p>
	 * 
	 * <p>
	 * For CAL files (actor & units), the package information have to be
	 * updated. For diagrams and network, we must ensure if only 1 file is
	 * moved, the other is also.
	 * </p>
	 * 
	 * <p>
	 * Note: In some specific case, if a file to move references another file to
	 * move, the updates have to be applied now (before moving files) to avoid
	 * issues in the resulting file
	 * </p>
	 */
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		final CompositeChange change = new CompositeChange("Pre-move updates");
		for (final IFile file : files) {
			if (OrccUtil.CAL_SUFFIX.equals(file.getFileExtension())) {

				final String origPackage = OrccUtil.getQualifiedPackage(file);
				final String newPackage = OrccUtil
						.getQualifiedPackage(destinationFolder.getFile(file
								.getName()));

				final Pattern pattern = Pattern.compile("package(\\s+)"
						+ origPackage + "(\\s*);");
				final String replacement = "package$1" + newPackage + "$2;";

				factory.addSpecificFileReplacement(file, pattern, replacement);
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

		// Specific case, we get the changes for the list of moved files.
		change.add(factory.getAllChanges(files, "Pre-move updates"));
		factory.resetResults();
		return change.getChildren().length > 0 ? change : null;
	}

	/**
	 * Create the change object which will manage changes for all files but the
	 * moved ones.
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return factory.getAllChanges(originalProject, "Update depending files",
				files);
	}

	/**
	 * Register updates to perform in files (actors, units, networks and
	 * diagrams) when a CAL file is moved.
	 * 
	 * @param file
	 */
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

	/**
	 * Register updates to perform in files (networks and diagrams) when a
	 * network file is moved.
	 * 
	 * @param file
	 */
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
