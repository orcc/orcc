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

import static net.sf.orcc.OrccProperties.DEFAULT_OUTPUT;
import static net.sf.orcc.OrccProperties.PROPERTY_OUTPUT;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.ui.internal.CalActivator;
import net.sf.orcc.frontend.Frontend;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.Severity;

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
		// retrieve output folder from project
		// by default output in .generated
		IProject project = context.getBuiltProject();
		String outputFolder = project.getPersistentProperty(PROPERTY_OUTPUT);
		if (outputFolder == null) {
			project.setPersistentProperty(PROPERTY_OUTPUT, new Path(project
					.getLocation().toOSString()).append(DEFAULT_OUTPUT)
					.toOSString());
		}
		frontend.setOutputFolder(outputFolder);

		ResourceSet set = context.getResourceSet();
		for (Resource resource : set.getResources()) {
			List<EObject> contents = resource.getContents();
			Iterator<EObject> it = contents.iterator();
			if (it.hasNext()) {
				EObject obj = it.next();
				if (obj instanceof AstActor) {
					AstActor actor = (AstActor) obj;
					build(resource, actor);
				}
			}
		}
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
	private void build(Resource resource, AstActor actor) throws CoreException {
		// contains linking errors
		List<Diagnostic> errors = actor.eResource().getErrors();
		if (!errors.isEmpty()) {
			return;
		}

		// validates (unique names and CAL validator)
		IResourceValidator v = ((XtextResource) resource)
				.getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = v.validate(resource, CheckMode.ALL,
				new CancelIndicator.NullImpl());

		for (Issue issue : issues) {
			if (issue.getSeverity() == Severity.ERROR) {
				return;
			}
		}

		// only compile if there are no errors
		try {
			URL resourceUrl = new URL(resource.getURI().toString());
			URL url = FileLocator.toFileURL(resourceUrl);
			IPath path = new Path(url.getPath());
			String file = path.toOSString();

			frontend.compile(file, actor);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, "net.sf.orcc.cal.ui",
					"could not generate code for " + actor.getName(), e);
			throw new CoreException(status);
		} catch (OrccException e) {
			IStatus status = new Status(IStatus.ERROR, "net.sf.orcc.cal.ui",
					"could not generate code for " + actor.getName(), e);
			throw new CoreException(status);
		}
	}

}
