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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

/**
 * This class defines an RVC-CAL command line version of the frontend. It should
 * be used with the folowing command-line, when all plugins are loaded by
 * eclipse :
 * 
 * <pre>
 * eclipse -application net.sf.orcc.cal.cli -data &lt;workspacePath&gt; &lt;projectName&gt;
 * </pre>
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 * 
 */
public class FrontendCli implements IApplication {

	private List<IProject> orderedProjects;
	private List<IProject> unorderedProjects;
	private ResourceSet emfResourceSet;
	private IWorkspace workspace;
	private boolean isAutoBuildActivated;

	public FrontendCli() {
		orderedProjects = new ArrayList<IProject>();
		unorderedProjects = new ArrayList<IProject>();
		workspace = ResourcesPlugin.getWorkspace();
		isAutoBuildActivated = false;

		CalStandaloneSetup.doSetup();

		// Get the resource set used by Frontend
		emfResourceSet = Frontend.instance.getResourceSet();
		// Register the package to ensure it is available during loading.
		emfResourceSet.getPackageRegistry().put(CalPackage.eNS_URI,
				CalPackage.eINSTANCE);
	}

	private void disableAutoBuild() throws CoreException {
		IWorkspaceDescription desc = workspace.getDescription();
		if (desc.isAutoBuilding()) {
			isAutoBuildActivated = true;
			desc.setAutoBuilding(false);
			workspace.setDescription(desc);
		}
	}

	private void restoreAutoBuild() throws CoreException {
		if (isAutoBuildActivated) {
			IWorkspaceDescription desc = workspace.getDescription();
			desc.setAutoBuilding(true);
			workspace.setDescription(desc);
		}
	}

	/**
	 * Control if resource has errors
	 * 
	 * @param resource
	 *            to check
	 * @return true if errors were found in resource
	 */
	private boolean hasErrors(Resource resource) {
	
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
	
		return hasErrors;
	}

	/**
	 * Get all actors and units files from container (IProject or IFolder) and
	 * all its subfolders.
	 * 
	 * @param container
	 *            instance of IProject or IFolder to search in
	 * @return list of *.cal files from container
	 * @throws OrccException
	 */
	private List<IFile> getCalFiles(IContainer container) throws OrccException {
		List<IFile> actors = new ArrayList<IFile>();
	
		IResource[] members = null;
		try {
			members = container.members();

			for (IResource resource : members) {

				if (resource.getType() == IResource.FOLDER) {
					actors.addAll(getCalFiles((IFolder) resource));
				} else if (resource.getType() == IResource.FILE
						&& resource.getFileExtension() != null
						&& resource.getFileExtension().equals("cal")) {
					actors.add((IFile) resource);
				}
			}
		} catch (CoreException e) {
			throw new OrccException("Unable to get members of IContainer "
					+ container.getName());
		}
	
		return actors;
	}

	/**
	 * Add currentProject dependencies to an orderedList of projects to compile,
	 * then add currentProject itself. This method should not run in infinite
	 * loop if projects dependencies are cycling.
	 * 
	 * @param currentProject
	 * @throws OrccException
	 */
	private void storeProjectToCompile(IProject currentProject)
			throws OrccException {
		unorderedProjects.add(currentProject);

		try {
			IProject[] refProjects = currentProject.getReferencedProjects();
	
			for (IProject p : refProjects) {
				if (!unorderedProjects.contains(p)) {
					storeProjectToCompile(p);
				}
			}
	
		} catch (CoreException e) {
			throw new OrccException("Unable to get referenced projects"
					+ currentProject.getName());
		}

		if (!orderedProjects.contains(currentProject)) {
			orderedProjects.add(currentProject);
		}
	}

	/**
	 * Set projectName as current project and store dependencies to compile
	 * before current project
	 * 
	 * @param projectName
	 * @throws OrccException
	 */
	private void setProject(String projectName)
			throws OrccException {

		IProject currentProject = workspace.getRoot().getProject(projectName);
		if (currentProject != null) {
			storeProjectToCompile(currentProject);
		} else {
			throw new OrccException("Impossible to find project named "
					+ projectName);
		}
	}

	/**
	 * Write IR files of the project p under the default folder
	 * &lt;project_folder&gt;/bin
	 * 
	 * @param p
	 *            project to compile
	 * @throws OrccException
	 */
	private void writeProjectIR(IProject p) throws OrccException {

		Frontend.instance.setOutputFolder(OrccUtil.getOutputFolder(p));

		List<IFile> fileList = getCalFiles(p);

		Map<String, Resource> resourceList = new HashMap<String, Resource>();
		ArrayList<String> orderedUnits = new ArrayList<String>();

		System.out.println("-----------------------------");
		System.out.println("Building project " + p.getName());
		System.out.println("-----------------------------");

		// Save list of units qualified names and map of qualified name /
		// entities
		for (IFile file : fileList) {
			URI uri = URI.createPlatformResourceURI(file.getFullPath()
					.toString(), true);
			Resource resource = emfResourceSet.getResource(uri, true);

			AstEntity entity = (AstEntity) resource.getContents().get(0);
			String qName = Util.getQualifiedName(entity);

			resourceList.put(qName, resource);

			if (entity.getUnit() != null) {
				orderedUnits.add(qName);
			}
		}

		// Reorder unit list
		ArrayList<String> tempUnitList = new ArrayList<String>();
		tempUnitList.addAll(orderedUnits);
		
		for (String curUnitQName : tempUnitList) {

			EList<Import> imports = ((AstEntity) resourceList.get(curUnitQName)
					.getContents().get(0)).getImports();

			for (Import i : imports) {
				String importedNamespace = i.getImportedNamespace();
				String importedQName = importedNamespace.substring(0,
						importedNamespace.lastIndexOf('.'));

				if (orderedUnits.contains(importedQName)) {
					orderedUnits.remove(importedQName);
					orderedUnits.add(orderedUnits.indexOf(curUnitQName),
							importedQName);
				}
			}
		}

		// Build units in right order
		for (String unitQName : orderedUnits) {
			System.out.println("Unit : " + unitQName);

			Resource resource = resourceList.get(unitQName);
			if (!hasErrors(resource)) {
				Frontend.getEntity((AstEntity) resource.getContents().get(0));
			}
			resourceList.remove(unitQName);
		}

		// Build actors
		for (String actorQName : resourceList.keySet()) {
			System.out.println("Actor : " + actorQName);

			Resource resource = resourceList.get(actorQName);
			if (!hasErrors(resource)) {

				Frontend.getEntity((AstEntity) resource.getContents().get(0));
			}
		}
	}

	@Override
	public Object start(IApplicationContext context) {
		Map<?, ?> map = context.getArguments();

		String[] args = (String[]) map
				.get(IApplicationContext.APPLICATION_ARGS);

		String projectName = "";
		if (args.length == 1) {
			projectName = args[0];
		} else {
			System.err.println("Please pass the project name in argument.");
			return IApplication.EXIT_RELAUNCH;
		}

		try {
			// IMPORTANT : Disable auto-building, because it requires xtext UI
			// plugins to be launched
			disableAutoBuild();

			System.out.print("Setup " + projectName + " as working project ");
			setProject(projectName);
			System.out.println("Done");

			for (IProject p : orderedProjects) {
				writeProjectIR(p);
			}
			System.out.println("Done");

			// If needed, restore autoBuild config state in eclipse config file
			restoreAutoBuild();

		} catch (OrccException oe) {
			System.err.println(oe.getMessage());
		} catch (CoreException ce) {
			System.err.println(ce.getMessage());
		} finally {
			try {
			restoreAutoBuild();
			} catch (CoreException e) {
				System.err.println(e.getMessage());
			}
		}

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
	}

}
