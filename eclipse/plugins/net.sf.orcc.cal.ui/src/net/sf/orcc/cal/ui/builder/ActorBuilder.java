/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.ui.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.OrccProjectNature;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.df.Unit;
import net.sf.orcc.frontend.Frontend;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.inject.Inject;

/**
 * This class defines an actor builder invoked by Xtext. The class is referenced
 * by an extension point in the plugin.xml as an Xtext builder participant.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorBuilder implements IXtextBuilderParticipant {

	@Inject
	private ResourceDescriptionsProvider provider;

	private ResourceSet currentResourceSet;

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor)
			throws CoreException {
		// only build Orcc projects
		final IProject project = context.getBuiltProject();
		if (!project.hasNature(OrccProjectNature.NATURE_ID)) {
			return;
		}

		// if build is cleaning, remove output folder completely
		final BuildType type = context.getBuildType();
		if (type == BuildType.CLEAN) {
			IFolder outputFolder = OrccUtil.getOutputFolder(project);
			// first refresh so that everything can be removed by delete
			outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
			outputFolder.delete(true, null);
			return;
		}

		// store result of build
		List<Unit> unitsBuilt = new ArrayList<Unit>();
		Set<IResourceDescription> builtResources = new HashSet<IResourceDescription>();

		// build actors/units
		currentResourceSet = context.getResourceSet();
		monitor.beginTask("Building actors", context.getDeltas().size());
		for (Delta delta : context.getDeltas()) {
			if (delta.getNew() != null) {
				IResourceDescription desc = delta.getNew();
				monitor.subTask(desc.getURI().lastSegment());
				builtResources.add(desc);
				EObject entity = build(desc, monitor);
				if (entity instanceof Unit) {
					unitsBuilt.add((Unit) entity);
				}
			} else {
				// CAL file has been deleted, need to delete the IR file
				deleteIr(delta.getOld(), monitor);
			}

			if (monitor.isCanceled()) {
				break;
			}
			monitor.worked(1);
		}

		// find out and build all entities that import things from the built
		// entities
		if ((type == BuildType.INCREMENTAL || type == BuildType.RECOVERY)
				&& !unitsBuilt.isEmpty()) {
			CacheManager.instance.unloadAllCaches();
			buildDependentEntities(monitor, builtResources, unitsBuilt);
		}

		// to free up some memory
		CacheManager.instance.unloadAllCaches();
		Frontend.instance.getResourceSet().getResources().clear();
		currentResourceSet = null;

		monitor.done();
	}

	/**
	 * Build the IR file corresponding to the given <i>desc</i>.
	 * 
	 * @param desc
	 *            A resource description corresponding to a CAL file, containing
	 *            code of an Actor or a Unit
	 * @param monitor
	 *            The monitor
	 * @return
	 * @throws CoreException
	 */
	private EObject build(IResourceDescription desc, IProgressMonitor monitor)
			throws CoreException {
		// load resource and compile
		final Resource resource = currentResourceSet.getResource(desc.getURI(),
				true);
		for (EObject obj : resource.getContents()) {
			if (obj.eClass().equals(CalPackage.eINSTANCE.getAstEntity())) {
				AstEntity entity = (AstEntity) obj;
				IFile file = EcoreHelper.getFile(resource);
				if (hasErrors(file)) {
					deleteIr(desc, monitor);
					return null;
				} else {
					return Frontend.getEntity(entity);
				}
			}
		}
		return null;
	}

	/**
	 * Build all entities which depends on one of the units in the given
	 * <i>units</i> list.
	 * 
	 * @param monitor
	 * @param builtDescs
	 *            A set of all entities built until now. Each is represented by
	 *            a ResourceDescription of the cal file
	 * @param units
	 *            A list of Units which has just been rebuilt. Actors depending
	 *            on these units have to be rebuilt too
	 * @throws CoreException
	 */
	private void buildDependentEntities(IProgressMonitor monitor,
			Set<IResourceDescription> builtDescs, List<Unit> units)
			throws CoreException {

		final IResourceDescriptions descs = provider
				.createResourceDescriptions();

		final Set<IResourceDescription> dependentDescs = new HashSet<IResourceDescription>();

		Set<String> unitQNames = new HashSet<String>();
		for (Unit unit : units) {
			unitQNames.add(unit.getName().toLowerCase());
		}

		for (IResourceDescription desc : descs.getAllResourceDescriptions()) {
			// Check only for descriptions that have not just been build
			if (builtDescs.contains(desc) || dependentDescs.contains(desc)) {
				continue;
			}

			for (QualifiedName importedElement : desc.getImportedNames()) {
				if (unitQNames.contains(importedElement.skipLast(1).toString())) {
					// We want to continue with the next desc
					dependentDescs.add(desc);
					break;
				}
			}
		}

		// build dependent descs
		monitor.beginTask("Building dependencies", dependentDescs.size());
		for (IResourceDescription desc : dependentDescs) {

			if (monitor.isCanceled()) {
				break;
			}
			monitor.subTask(desc.getURI().lastSegment());
			build(desc, monitor);
			monitor.worked(1);
		}
	}

	/**
	 * Returns <code>true</code> if the given file has errors.
	 * 
	 * @param file
	 *            a file containing an entity (actor/unit)
	 * @return <code>true</code> if the given file has errors
	 * @throws CoreException
	 */
	private boolean hasErrors(IFile file) throws CoreException {
		final IMarker[] markers = file.findMarkers(EValidator.MARKER, true,
				IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			if (IMarker.SEVERITY_ERROR == marker.getAttribute(IMarker.SEVERITY,
					IMarker.SEVERITY_INFO)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Delete on the disk the IR file corresponding to the CAL file described by
	 * the given description. This method only manipulate paths, so the given
	 * description can reference a file already deleted on the filesystem. This
	 * is useful to delete ir file when the original cal file has just been
	 * deleted.
	 * 
	 * @param calResourceDescription
	 *            A ResourceDescription for a CAL file
	 * 
	 */
	private void deleteIr(IResourceDescription calResourceDescription,
			IProgressMonitor monitor) {

		URI calURI = calResourceDescription.getURI();

		// Resolve the URI of cal file against the workspace, and convert the
		// IFile into an IPath to allow some transformations on it
		IFile calFile = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(URI.decode(calURI.path())));

		IProject p = calFile.getProject();
		if (!p.isOpen()) {
			return;
		}
		IFolder outFolder = OrccUtil.getOutputFolder(p);
		IPath calPath = calFile.getProjectRelativePath();

		// Replace the firsts segments (<proj>/<src>)
		// Replace "cal" extension by "ir"
		IPath irPath = calPath
				.removeFirstSegments(outFolder.getFullPath().segmentCount())
				.removeFileExtension().addFileExtension(OrccUtil.IR_SUFFIX);

		// Find the corresponding file under the <proj>/bin folder
		IFile irFile = outFolder.getFile(irPath);

		if (irFile.exists()) {
			try {
				irFile.delete(true, monitor);
			} catch (CoreException e) {
				OrccLogger.warnln("File " + irFile + " cannot be deleted: "
						+ e.getMessage());
			}
		}
	}
}
