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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.cal.generator.CalGenerator;
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.apache.commons.lang.StringUtils;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.inject.Injector;

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

	private final String USAGE = "Usage : \n"
			+ "net.sf.orcc.cal.cli <project> [<network>]";

	private final ResourceSet resourceSet;
	private final IWorkspace workspace;
	private boolean isAutoBuildActivated;

	private IProject project;
	private File networkFile;
	private final String networkName;

	final Injector injector;

	public FrontendCli() {

		injector = new CalStandaloneSetup()
				.createInjectorAndDoEMFRegistration();

		workspace = ResourcesPlugin.getWorkspace();
		isAutoBuildActivated = false;

		project = null;
		networkFile = null;
		networkName = "";

		// Get the resource set used by Frontend
		resourceSet = injector.getInstance(ResourceSet.class);
	}

	@Override
	public Object start(IApplicationContext context) {

		final String[] args = (String[]) context.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);

		if (!parseCommandLine(args)) {
			// parseCommandLine already displayed an error message before
			// returning false
			return IApplication.EXIT_RELAUNCH;
		}

		try {
			// IMPORTANT : Disable auto-building, because it requires Xtext UI
			// plugins to be launched
			disableAutoBuild();

			// Get the projects to compile in the right order
			OrccLogger.traceln("Setup " + project.getName() + " as working project ");
			final Collection<IProject> orderedProjects = getOrderedProjects(project);

			// Check for missing output folders in project
			for (final IProject proj : orderedProjects) {
				final IFolder outDir = OrccUtil.getOutputFolder(proj);
				if (!outDir.exists()) {
					outDir.create(true, true, new NullProgressMonitor());
				}
			}

			final Map<IProject, List<Resource>> resourcesMap = new HashMap<IProject, List<Resource>>();
			if (networkFile == null) {
				for (final IProject project : orderedProjects) {
					final List<Resource> resources = new ArrayList<Resource>();

					final List<IFile> files = OrccUtil.getAllFiles(
							OrccUtil.CAL_SUFFIX,
							OrccUtil.getSourceFolders(project));

					for (final IFile file : files) {
						final URI uri = URI.createPlatformResourceURI(file
								.getFullPath().toString(), true);
						resources.add(resourceSet.getResource(uri, true));
					}

					resourcesMap.put(project, resources);
				}
			} else {
				final InputStream networkStream = new FileInputStream(networkFile);
				Map<String, IFile> allFiles = new HashMap<String, IFile>();
				for (IProject project : orderedProjects) {
					allFiles.putAll(getAllFiles(project, true));
				}

				System.out
						.println("-----------------------------------------------");
				System.out.println("Building needed files for network "
						+ networkName);
				System.out
						.println("-----------------------------------------------");

				writeIrFilesFromXdfContent(networkStream, allFiles);
			}

			final CalGenerator calGenerator = (CalGenerator) injector
					.getInstance(IGenerator.class);
			final JavaIoFileSystemAccess fsa = injector
					.getInstance(JavaIoFileSystemAccess.class);

			for (final IProject project : orderedProjects) {

				OrccLogger.traceln("+-------------------");
				OrccLogger.traceln("| " + project.getName());
				OrccLogger.traceln("+-------------------");

				fsa.setOutputPath(OrccUtil.getOutputFolder(project)
						.getLocation().toString());
				calGenerator.beforeBuild(project, resourceSet);
				for (final Resource res : resourcesMap.get(project)) {
					calGenerator.doGenerate(res, fsa);
					OrccLogger.traceln("Build " + res.getURI().toString());
				}
				calGenerator.afterBuild();
			}

			// If needed, restore autoBuild config state in eclipse config file
			restoreAutoBuild();

			OrccLogger.traceln("Build ends");
			workspace.save(true, new NullProgressMonitor());
		} catch (OrccException oe) {
			System.err.println(oe.getMessage());
		} catch (CoreException ce) {
			System.err.println(ce.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				restoreAutoBuild();
				return IApplication.EXIT_OK;
			} catch (CoreException e) {
				System.err.println(e.getMessage());
			}
		}

		return IApplication.EXIT_RESTART;
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
	 * Parse the command line and initialize the project to wotk with. If a
	 * network qualified name is passed in cli arguments, initialize the
	 * networkFile class member.
	 * 
	 * @param args
	 * @return
	 */
	private boolean parseCommandLine(final String[] args) {

		if (args.length == 0) {
			OrccLogger.severeln("Unable to parse command line arguments");
			OrccLogger.traceln(USAGE);
			return false;
		}

		OrccLogger.traceln("Command line arguments are \""
				+ StringUtils.join(args, ' ') + "\"");

		final String projectName = args[0];
		project = workspace.getRoot().getProject(projectName);
		if (project == null) {
			OrccLogger.severeln("Unable to find the project " + projectName);
			OrccLogger.traceln(USAGE);
			return false;
		}

		if (args.length >= 2 && !args[1].isEmpty()) {
			final IFile ifile = OrccUtil.getFile(project, args[1],
					OrccUtil.NETWORK_SUFFIX);
			networkFile = new File(ifile.getFullPath().toString());
		}

		return true;
	}

	/**
	 * Return a Collection containing all projects required to build the given
	 * project. The collection is sorted in the correct build order: the given
	 * project will be the last in the resulting Collection.
	 * 
	 * @param project
	 * @return
	 * @throws JavaModelException
	 */
	private Collection<IProject> getOrderedProjects(final IProject project)
			throws JavaModelException {
		final Collection<IProject> projects = new LinkedHashSet<IProject>();

		final IJavaProject javaProject = JavaCore.create(project);
		if (javaProject == null) {
			OrccLogger.severeln("");
			return projects;
		}

		for (final String required : javaProject.getRequiredProjectNames()) {
			final IProject proj = OrccUtil.workspaceRoot().getProject(required);
			projects.addAll(getOrderedProjects(proj));
		}

		projects.add(project);

		return projects;
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
	 * Get all actors, units and, if includeNetworks == true, network files from
	 * container (IProject or IFolder) and all its subfolders. Index is the
	 * qualified name corresponding to the file.
	 * 
	 * @param container
	 *            instance of IProject or IFolder to search in
	 * @param includeNetworks
	 *            set to true to include xdf files in the returned map
	 * @return a map of qualified names / IFile descriptors
	 * @throws OrccException
	 */
	private Map<String, IFile> getAllFiles(IContainer container,
			boolean includeNetworks) throws OrccException {

		Map<String, IFile> calFiles = new HashMap<String, IFile>();
		IResource[] members = null;
		try {
			members = container.members();

			for (IResource resource : members) {

				if (resource.getType() == IResource.FOLDER) {

					calFiles.putAll(getAllFiles((IFolder) resource,
							includeNetworks));

				} else if (resource.getType() == IResource.FILE
						&& resource.getFileExtension() != null) {
					if (resource.getFileExtension().equals(OrccUtil.CAL_SUFFIX)
							|| (includeNetworks && resource.getFileExtension()
									.equals(OrccUtil.NETWORK_SUFFIX))) {
						String packageName = resource.getProjectRelativePath()
								.removeFirstSegments(1).removeFileExtension()
								.toString().replace('/', '.');
						calFiles.put(packageName, (IFile) resource);
					}

				}
			}
		} catch (CoreException e) {
			throw new OrccException("Unable to get members of IContainer "
					+ container.getName());
		}

		return calFiles;
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
							if (file.getFileExtension().equals(
									OrccUtil.NETWORK_SUFFIX)) {
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

		IFolder outFolder = OrccUtil.getOutputFolder(calFile.getProject());

		if (astEntity.getUnit() != null) {
			System.out.println(" Unit: " + calFile.getName() + " from project "
					+ calFile.getProject().getName() + " built in folder "
					+ outFolder.toString());
		} else {
			System.out.println("Actor: " + calFile.getName() + " from project "
					+ calFile.getProject().getName() + " built in folder "
					+ outFolder.toString());
		}

		// Really write Actor IR
		if (!hasErrors(resource)) {
			Frontend.getEntity(astEntity);
		}

	}

	@Override
	public void stop() {
	}

}
