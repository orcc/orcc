/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FrontendCli implements IApplication {

	private List<IFile> actors;

	private XtextResourceSet resourceSet;

	public FrontendCli() {
	}

	private void getActors(IContainer container) throws CoreException {
		for (IResource resource : container.members()) {
			if (resource.getType() == IResource.FOLDER) {
				getActors((IFolder) resource);
			} else if (resource.getType() == IResource.FILE
					&& resource.getFileExtension().equals("cal")) {
				actors.add((IFile) resource);
			}
		}
	}

	private void processActor(IFile actorPath) {
		URI uri = URI.createPlatformResourceURI(actorPath.getFullPath()
				.toString(), true);
		Resource resource = resourceSet.getResource(uri, true);
		AstEntity entity = (AstEntity) resource.getContents().get(0);

		boolean hasErrors = false;

		// contains linking errors
		List<Diagnostic> errors = resource.getErrors();
		if (!errors.isEmpty()) {
			for (Diagnostic error : errors) {
				System.err.println(error);
			}

			hasErrors = true;
		}

		// validates (unique names and CAL validator)
		IResourceValidator v = ((XtextResource) resource)
				.getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = v.validate(resource, CheckMode.ALL,
				CancelIndicator.NullImpl);

		for (Issue issue : issues) {
			if (issue.getSeverity() == Severity.ERROR) {
				System.err.println(issue.toString());
				hasErrors = true;
			} else {
				System.out.println(issue.toString());
			}
		}

		// only compile if there are no errors
		if (!hasErrors) {
			Frontend.instance.compile(entity);
		}
	}

	private void setProject(String projectName) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		actors = new ArrayList<IFile>();
		try {
			getActors(project);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		IFolder outputFolder = OrccUtil.getOutputFolder(project);
		Frontend.instance.setOutputFolder(outputFolder);
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		Map<?, ?> map = context.getArguments();
		String[] args = (String[]) map
				.get(IApplicationContext.APPLICATION_ARGS);
		if (args.length == 2) {
			System.out.println("setup of CAL Xtext parser");
			new CalStandaloneSetup().createInjectorAndDoEMFRegistration();
			System.out.println("done");

			setProject(args[0]);

			resourceSet = new XtextResourceSet();
			resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL,
					Boolean.TRUE);
			for (IFile actor : actors) {
				processActor(actor);
			}
		} else {
			System.err.println("Usage: Frontend <project name>");
		}
		return null;
	}

	@Override
	public void stop() {
	}

}
