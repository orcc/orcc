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

import net.sf.orcc.OrccProjectNature;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.frontend.Frontend;
import net.sf.orcc.util.EcoreHelper;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

/**
 * This class defines an actor builder invoked by Xtext. The class is referenced
 * by an extension point in the plugin.xml as an Xtext builder participant.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorBuilder implements IXtextBuilderParticipant {

	/**
	 * Creates a new actor builder.
	 */
	public ActorBuilder() {
	}

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor)
			throws CoreException {
		// only build Orcc projects
		IProject project = context.getBuiltProject();
		if (!project.hasNature(OrccProjectNature.NATURE_ID)) {
			if (project.hasNature("net.sf.orcc.ui.OrccNature")) {
				migrateNature(project);
			} else {
				return;
			}
		}

		// create front-end
		IFolder outputFolder = OrccUtil.getOutputFolder(project);
		if (outputFolder == null) {
			return;
		}
		Frontend.instance.setOutputFolder(outputFolder);

		// if build is cleaning, remove output folder completely
		BuildType type = context.getBuildType();
		if (type == BuildType.CLEAN) {
			// first refresh so that everything can be removed by delete
			outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
			outputFolder.delete(true, null);
		}

		// clear cache associated with removed files
		for (Delta delta : context.getDeltas()) {
			if (delta.getNew() == null) {
				CacheManager.instance.removeCache(delta.getUri());
			}
		}

		// build actors/units
		ResourceSet set = context.getResourceSet();
		monitor.beginTask("Building actors", context.getDeltas().size());
		for (Delta delta : context.getDeltas()) {
			if (delta.getNew() != null) {
				IResourceDescription desc = delta.getNew();

				// load resource and compile
				Resource resource = set.getResource(desc.getURI(), true);
				for (EObject obj : resource.getContents()) {
					if (obj.eClass()
							.equals(CalPackage.eINSTANCE.getAstEntity())) {
						AstEntity entity = (AstEntity) obj;
						IFile file = EcoreHelper.getFile(resource);
						if (!hasErrors(file)) {
							// and then we compile
							Frontend.getEntity(entity);
						}
					}
				}
			}

			if (monitor.isCanceled()) {
				break;
			}
			monitor.worked(1);
		}

		// to free up some memory
		CacheManager.instance.unloadAllCaches();

		monitor.done();
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
		IMarker[] markers = file.findMarkers(EValidator.MARKER, true,
				IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			if (IMarker.SEVERITY_ERROR == marker.getAttribute(IMarker.SEVERITY,
					IMarker.SEVERITY_INFO)) {
				// an error => no compilation
				return true;
			}
		}

		return false;
	}

	/**
	 * Migrates the old Orcc nature to the new one.
	 * 
	 * @param project
	 *            project affected
	 * @throws CoreException
	 */
	private void migrateNature(IProject project) throws CoreException {
		IProjectDescription desc = project.getDescription();
		String[] natures = desc.getNatureIds();
		for (int i = 0; i < natures.length; i++) {
			if ("net.sf.orcc.ui.OrccNature".equals(natures[i])) {
				natures[i] = OrccProjectNature.NATURE_ID;
			}
		}
		desc.setNatureIds(natures);
		project.setDescription(desc, null);
	}

}
