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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	private ResourceSet resourceSet;
	private IWorkspace workspace;
	private boolean isAutoBuildActivated;

	public FrontendCli() {
		orderedProjects = new ArrayList<IProject>();
		unorderedProjects = new ArrayList<IProject>();
		workspace = ResourcesPlugin.getWorkspace();
		isAutoBuildActivated = false;

		CalStandaloneSetup.doSetup();

		// Get the resource set used by Frontend
		resourceSet = Frontend.instance.getResourceSet();
		// Register the package to ensure it is available during loading.
		resourceSet.getPackageRegistry().put(CalPackage.eNS_URI,
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
	 * all its subfolders. Index is the qualified name corresponding to the
	 * file.
	 * 
	 * @param container
	 *            instance of IProject or IFolder to search in
	 * @return a map of qualified names / IFile descriptors
	 * @throws OrccException
	 */
	private Map<String, IFile> getAllFiles(IContainer container)
			throws OrccException {

		Map<String, IFile> calFiles = new HashMap<String, IFile>();
		IResource[] members = null;
		try {
			members = container.members();

			for (IResource resource : members) {

				if (resource.getType() == IResource.FOLDER) {

					calFiles.putAll(getAllFiles((IFolder) resource));

				} else if (resource.getType() == IResource.FILE
						&& resource.getFileExtension() != null
						&& (resource.getFileExtension().equals("cal") || resource
								.getFileExtension().equals("xdf"))) {

					String packageName = resource.getProjectRelativePath()
							.removeFirstSegments(1).removeFileExtension()
							.toString().replace('/', '.');
					calFiles.put(packageName, (IFile) resource);

				}
			}
		} catch (CoreException e) {
			throw new OrccException("Unable to get members of IContainer "
					+ container.getName());
		}

		return calFiles;
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

			IClasspathEntry[] classpathEntries = JavaCore
					.create(currentProject).getRawClasspath();

			for (IClasspathEntry cpEntry : classpathEntries) {

				// Check for projects referenced in build path
				if (cpEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {

					IProject projectInClasspath = workspace.getRoot()
							.getProject(cpEntry.getPath().toString());

					if (!unorderedProjects.contains(projectInClasspath)) {
						storeProjectToCompile(projectInClasspath);
					}
				}
			}

		} catch (CoreException e) {
			throw new OrccException("Unable to get referenced projects"
					+ currentProject.getName());
		}

		// This function is recursive, and projects referenced in classpath have
		// been stored, so adding the current project now ensure order is right
		if (!orderedProjects.contains(currentProject)) {
			orderedProjects.add(currentProject);
		}
	}

	/**
	 * Write IR files for all instance's children of a gived network
	 * 
	 * @param networkContent
	 *            the input stream of xdf file content
	 * @param qnameFileMap
	 */
	private void writeIrFilesFromXdfContent(InputStream networkContent,
			Map<String, IFile> qnameFileMap) {

		Document document = DomUtil.parseDocument(networkContent);

		Element root = document.getDocumentElement();

		NodeList rootChildren = root.getChildNodes();
		for (int i = 0; i < rootChildren.getLength(); ++i) {

			Node rootChild = rootChildren.item(i);

			if (rootChild.getNodeType() == Node.ELEMENT_NODE
					&& ((Element) rootChild).getNodeName().equals("Instance")) {

				Element instanceElt = (Element) rootChild;

				NodeList instanceChildren = instanceElt.getChildNodes();
				for (int j = 0; j < instanceChildren.getLength(); ++j) {

					Node instanceChild = instanceChildren.item(j);

					if (instanceChild.getNodeType() == Node.ELEMENT_NODE
							&& ((Element) instanceChild).getNodeName().equals(
									"Class")) {

						String qualifiedName = ((Element) instanceChild)
								.getAttribute("name");

						if (qnameFileMap.containsKey(qualifiedName)) {

							IFile file = qnameFileMap.get(qualifiedName);
							if (file.getFileExtension().equals("xdf")) {
								try {
									writeIrFilesFromXdfContent(
											new FileInputStream(file
													.getLocationURI().getPath()),
											qnameFileMap);
								} catch (FileNotFoundException e) {
									System.err
											.println("Unable to open file corresponding to "
													+ qualifiedName);
								}
							} else {
								writeIrFile(qnameFileMap.get(qualifiedName),
										qnameFileMap);
							}
						} else {
							System.err
									.println(qualifiedName
											+ " does not exists in the current workspace");
						}
					}
				}
			}
		}
	}

	/**
	 * Write the IR file corresponding to a *.cal one (Unit or Actor). This
	 * method search for imported Units, and ensure they are all build before
	 * doing the job for the current file
	 * 
	 * @param calFile
	 *            the file to build
	 * @param qnameFileMap
	 *            a map with all known xdf and cal files of the workspace,
	 *            indexed from their qualified name
	 */
	private void writeIrFile(IFile calFile, Map<String, IFile> qnameFileMap) {

		Frontend.instance.setOutputFolder(OrccUtil.getOutputFolder(calFile
				.getProject()));

		Resource resource = EcoreHelper.getResource(resourceSet, calFile);

		// Current actor to build
		AstEntity astEntity = (AstEntity) resource.getContents().get(0);

		for (Import importedQName : astEntity.getImports()) {
			String nameSpace = importedQName.getImportedNamespace();
			String importedUnit = nameSpace.substring(0,
					nameSpace.lastIndexOf('.'));

			if (qnameFileMap.containsKey(importedUnit)) {
				IFile file = qnameFileMap.remove(importedUnit);

				writeIrFile(file, qnameFileMap);
			}
		}

		if (astEntity.getUnit() != null) {
			System.out.println("Unit : " + calFile.getName() + " from project "
					+ calFile.getProject().getName());
		} else {
			System.out.println("Actor : " + calFile.getName()
					+ " from project " + calFile.getProject().getName());
		}

		// Really write Actor IR
		if (!hasErrors(resource)) {
			Frontend.getEntity(astEntity);
		}

	}

	/**
	 * Write IR files for all cla corresponding files of a project
	 * 
	 * @param p
	 *            project to compile
	 * @throws OrccException
	 */
	private void writeIrFilesFromProject(IProject p,
			Map<String, IFile> qnameFileMap) throws OrccException {

		Frontend.instance.setOutputFolder(OrccUtil.getOutputFolder(p));

		Map<String, Resource> resourceMap = new HashMap<String, Resource>();
		ArrayList<String> orderedUnits = new ArrayList<String>();

		System.out.println("-----------------------------");
		System.out.println("Building project " + p.getName());
		System.out.println("-----------------------------");

		// Save list of units qualified names and map of qualified name /
		// entities
		for (Entry<String, IFile> entry : qnameFileMap.entrySet()) {

			Resource resource = EcoreHelper.getResource(resourceSet,
					entry.getValue());
			AstEntity entity = (AstEntity) resource.getContents().get(0);

			resourceMap.put(entry.getKey(), resource);

			if (entity.getUnit() != null) {
				orderedUnits.add(entry.getKey());
			}
		}

		// Reorder unit list, to ensure units depending on others are built
		// after it/them
		ArrayList<String> tempUnitList = new ArrayList<String>();
		tempUnitList.addAll(orderedUnits);

		for (String curUnitQName : tempUnitList) {

			// Get imports for the current unit
			EList<Import> imports = ((AstEntity) resourceMap.get(curUnitQName)
					.getContents().get(0)).getImports();

			for (Import i : imports) {
				String importedNamespace = i.getImportedNamespace();
				String importQName = importedNamespace.substring(0,
						importedNamespace.lastIndexOf('.'));

				// Move the needed unit just before the current one
				if (orderedUnits.contains(importQName)) {
					orderedUnits.remove(importQName);
					orderedUnits.add(orderedUnits.indexOf(curUnitQName),
							importQName);
				}
			}
		}

		// Build units, and remove them from the resource map
		for (String unitQName : orderedUnits) {
			System.out.println("Unit : " + unitQName);

			Resource resource = resourceMap.remove(unitQName);
			if (!hasErrors(resource)) {
				Frontend.getEntity((AstEntity) resource.getContents().get(0));
			}
		}

		// Build actors
		for (String actorQName : resourceMap.keySet()) {
			System.out.println("Actor : " + actorQName);

			Resource resource = resourceMap.get(actorQName);
			if (!hasErrors(resource)) {
				Frontend.getEntity((AstEntity) resource.getContents().get(0));
			}
		}
	}

	@Override
	public Object start(IApplicationContext context) {
		String[] args = (String[]) context.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);

		String projectName = "";
		IProject baseProject = null;
		InputStream network = null;
		String networkName = "";

		if (args.length >= 1) {

			System.out.print("Command line arguments are \"");
			for (String arg : args) {
				System.out.print(arg + " ");
			}
			System.out.println("\"");

			projectName = args[0];
			baseProject = workspace.getRoot().getProject(projectName);
			if (baseProject == null) {
				System.err.println("Unable to find project " + projectName);
				return IApplication.EXIT_RELAUNCH;
			}

			if (args.length >= 2) {

				IFile networkFile = OrccUtil.getFile(baseProject, args[1],
						"xdf");
				if (networkFile != null) {
					try {
						network = new FileInputStream(networkFile
								.getLocationURI().getPath());
						networkName = args[1];
					} catch (FileNotFoundException e) {
						network = null;
					}
				}
			}
		} else {
			System.err.println("Usage : \n"
					+ "net.sf.orcc.cal.cli <project> [<network>]");
			return IApplication.EXIT_RELAUNCH;
		}

		try {
			// IMPORTANT : Disable auto-building, because it requires xtext UI
			// plugins to be launched
			disableAutoBuild();

			System.out.print("Setup " + projectName + " as working project ");
			storeProjectToCompile(baseProject);
			System.out.println("Done");

			if (network == null) {
				for (IProject project : orderedProjects) {
					writeIrFilesFromProject(project, getAllFiles(project));
				}
			} else {
				Map<String, IFile> allFiles = new HashMap<String, IFile>();
				for (IProject project : orderedProjects) {
					allFiles.putAll(getAllFiles(project));
				}

				System.out
						.println("-----------------------------------------------");
				System.out.println("Building needed files for network "
						+ networkName);
				System.out
						.println("-----------------------------------------------");

				writeIrFilesFromXdfContent(network, allFiles);
			}
			System.out.println("Done");

			// If needed, restore autoBuild config state in eclipse config file
			restoreAutoBuild();

			workspace.save(true, new NullProgressMonitor());

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
