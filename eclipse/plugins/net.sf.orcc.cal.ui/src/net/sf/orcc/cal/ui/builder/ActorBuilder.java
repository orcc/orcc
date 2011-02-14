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

import static net.sf.orcc.OrccProperties.PRETTYPRINT_JSON;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.ui.internal.CalActivator;
import net.sf.orcc.frontend.Frontend;
import net.sf.orcc.ui.OrccProjectNature;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

import com.google.inject.Injector;

/**
 * This class defines an actor builder invoked by Xtext. The class is referenced
 * by an extension point in the plugin.xml as an Xtext builder participant.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorBuilder implements IXtextBuilderParticipant {

	private Frontend frontend;

	/**
	 * Creates a new actor builder.
	 */
	public ActorBuilder() {
		// since the actor builder is not created by Guice we have to manually
		// retrieve the injector so we can inject the front-end (and its
		// dependencies)
		Injector injector = CalActivator.getInstance().getInjector(
				"net.sf.orcc.cal.Cal");
		frontend = injector.getInstance(Frontend.class);
	}

	@Override
	public void build(IBuildContext context, IProgressMonitor monitor)
			throws CoreException {
		// only build Orcc projects
		IProject project = context.getBuiltProject();
		if (!project.hasNature(OrccProjectNature.NATURE_ID)) {
			return;
		}

		IFolder outputFolder = OrccUtil.getOutputFolder(project);
		if (outputFolder == null) {
			return;
		}

		// if build is cleaning, remove output folder completely
		BuildType type = context.getBuildType();
		if (type == BuildType.CLEAN) {
			// first refresh so that everything can be removed by delete
			outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
			outputFolder.delete(true, null);
		}

		// set output folder
		frontend.setOutputFolder(outputFolder.getLocation().toOSString());

		// whether IR is to be pretty-printed or not
		String compactIR = project.getPersistentProperty(PRETTYPRINT_JSON);
		frontend.setPrettyPrint(Boolean.parseBoolean(compactIR));

		ResourceSet set = context.getResourceSet();
		monitor.beginTask("Building actors", context.getDeltas().size());
		for (Delta delta : context.getDeltas()) {
			if (delta.getNew() == null) {
				// deletion
				IResourceDescription desc = delta.getOld();
				for (IEObjectDescription objDesc : desc
						.getExportedObjects(CalPackage.eINSTANCE.getAstActor())) {
					File file = new File(outputFolder + File.separator
							+ objDesc.getName() + ".json");
					file.delete();
				}
			} else {
				// creation
				IResourceDescription desc = delta.getNew();
				Resource resource = set.getResource(desc.getURI(), true);
				for (EObject obj : resource.getContents()) {
					if (obj.eClass()
							.equals(CalPackage.eINSTANCE.getAstEntity())) {
						AstEntity entity = (AstEntity) obj;
						build(resource, entity);
					}
				}
			}

			if (monitor.isCanceled()) {
				break;
			}
			monitor.worked(1);
		}

		monitor.done();
	}

	/**
	 * Builds the actor defined in the given resource.
	 * 
	 * @param resource
	 *            the resource of the actor
	 * @param actor
	 *            the AST actor
	 * @throws CoreException
	 *             if something goes wrong
	 */
	private void build(Resource resource, AstEntity entity)
			throws CoreException {
		try {
			URL resourceUrl = new URL(resource.getURI().toString());
			URL url = FileLocator.toFileURL(resourceUrl);
			IPath path = new Path(url.getPath());

			// get markers to check for errors
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile file = root.getFileForLocation(path);
			IMarker[] markers = file.findMarkers(EValidator.MARKER, true,
					IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (IMarker.SEVERITY_ERROR == marker.getAttribute(
						IMarker.SEVERITY, IMarker.SEVERITY_INFO)) {
					// an error => no compilation
					return;
				}
			}

			// if using clustering, we need to transform the types
			// new TypeTransformer(null).transformTypes(entity);

			// and then we compile
			AstActor actor = entity.getActor();
			if (actor != null) {
				String fileName = path.toOSString();
				frontend.compile(fileName, actor);
			}
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, "net.sf.orcc.cal.ui",
					"could not generate code for " + resource, e);
			throw new CoreException(status);
		} catch (OrccException e) {
			IStatus status = new Status(IStatus.ERROR, "net.sf.orcc.cal.ui",
					"could not generate code for " + resource, e);
			throw new CoreException(status);
		}
	}

}
