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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cal.CalStandaloneSetup;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.Import;
import net.sf.orcc.cal.generator.CalGenerator;
import net.sf.orcc.df.util.XdfConstants;
import net.sf.orcc.util.CommandLineUtil;
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.xtext.builder.MonitorBasedCancelIndicator;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;

/**
 * This class defines an RVC-CAL command line version of the frontend. It should
 * be used with the following command-line, when all plugins are installed in
 * eclipse :
 * 
 * <pre>
 * eclipse -application net.sf.orcc.cal.cli -data &lt;workspacePath&gt; &lt;projectName&gt; [&lt;network&gt;]
 * </pre>
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 * 
 */
public class FrontendCli implements IApplication {

	// Display the command line usage of this application
	private final String USAGE = "Usage : \n"
			+ "net.sf.orcc.cal.cli <project> [<network>]";

	private final ResourceSet resourceSet;
	private final IWorkspace workspace;
	private boolean wasAutoBuildEnabled;

	private IProject project;
	private IFile networkFile;

	final Injector injector;

	public FrontendCli() {

		// Configure Log4j to output logs in System.out console (used in Xtext
		// framework)
		final Layout l = new PatternLayout(
				PatternLayout.TTCC_CONVERSION_PATTERN);
		final Appender a = new ConsoleAppender(l, ConsoleAppender.SYSTEM_OUT);
		Logger.getRootLogger().addAppender(a);
		// Default level to Info, reduces amount of messages displayed
		Logger.getRootLogger().setLevel(Level.INFO);

		injector = new CalStandaloneSetup()
				.createInjectorAndDoEMFRegistration();

		workspace = ResourcesPlugin.getWorkspace();
		wasAutoBuildEnabled = false;

		project = null;
		networkFile = null;

		// Get the resource set used by Frontend
		resourceSet = injector.getInstance(ResourceSet.class);
	}

	@Override
	public Object start(IApplicationContext context) {

		final String[] args = (String[]) context.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);

		if (!parseCommandLine(args)) {
			// parseCommandLine() already displayed an error message before
			// returning false
			return IApplication.EXIT_RELAUNCH;
		}

		// The IR generation process starts now
		try {
			// IMPORTANT : Disable auto-building, because it requires Xtext UI
			// plugins to be launched
			wasAutoBuildEnabled = CommandLineUtil.disableAutoBuild(workspace);

			// Get the projects to compile in the right order
			OrccLogger.traceln("Setup " + project.getName()
					+ " as working project");
			final Collection<IProject> orderedProjects = getOrderedProjects(project);

			// Check for missing output folders in project
			for (final IProject proj : orderedProjects) {
				final IFolder outDir = OrccUtil.getOutputFolder(proj);
				if (!outDir.exists()) {
					outDir.create(true, true, new NullProgressMonitor());
				}
			}

			final Multimap<IProject, Resource> resourcesMap = HashMultimap
					.create();
			// Classical full build, store all files for each projects
			if (networkFile == null) {
				for (final IProject project : orderedProjects) {
					// Get all CAL files of the project
					final List<IFile> files = OrccUtil.getAllFiles(
							OrccUtil.CAL_SUFFIX,
							OrccUtil.getSourceFolders(project));

					// Store these files to build them later
					for (final IFile file : files) {
						resourcesMap.put(project, getResource(file));
					}
				}
			}
			// Specific build. We only write IR needed to build the network
			// referenced on command line
			else {
				// Compute the list of all xdf and cal files in required
				// projects. Store all these files
				// in a map, indexed by their qualified name
				final Map<String, IFile> allFiles = new HashMap<String, IFile>();
				for (final IProject project : orderedProjects) {
					final List<IFolder> folders = OrccUtil
							.getAllSourceFolders(project);
					final List<IFile> networks = OrccUtil.getAllFiles(
							OrccUtil.NETWORK_SUFFIX, folders);
					for (final IFile file : networks) {
						allFiles.put(OrccUtil.getQualifiedName(file), file);
					}
					final List<IFile> entities = OrccUtil.getAllFiles(
							OrccUtil.CAL_SUFFIX, folders);
					for (final IFile file : entities) {
						allFiles.put(OrccUtil.getQualifiedName(file), file);
					}
				}

				storeReferencedEntities(networkFile, allFiles, resourcesMap);
			}

			final CalGenerator calGenerator = (CalGenerator) injector
					.getInstance(IGenerator.class);
			final JavaIoFileSystemAccess fsa = injector
					.getInstance(JavaIoFileSystemAccess.class);

			MonitorBasedCancelIndicator cancelIndicator = new MonitorBasedCancelIndicator(
					new NullProgressMonitor()); //maybe use reflection to read from fsa
			GeneratorContext generatorContext = new GeneratorContext();
			generatorContext.setCancelIndicator(cancelIndicator);
			
			for (final IProject project : orderedProjects) {

				OrccLogger.traceln("+-------------------");
				OrccLogger.traceln("| " + project.getName());
				OrccLogger.traceln("+-------------------");

				fsa.setOutputPath(OrccUtil.getOutputFolder(project)
						.getLocation().toString());
				calGenerator.beforeBuild(project, resourceSet);
				for (final Resource res : resourcesMap.get(project)) {
					calGenerator.doGenerate(res, fsa, generatorContext);
					OrccLogger.traceln("Build " + res.getURI().toString());
				}
				calGenerator.afterBuild();
			}

			// Avoid warning messages of type "The workspace exited
			// with unsaved changes in the previous session" the next
			// time an IApplication (FrontendCli) will be launched
			// This method can be called ONLY if auto-building has
			// been disabled
			OrccLogger.traceln("Saving workspace");
			workspace.getRoot().refreshLocal(IWorkspaceRoot.DEPTH_INFINITE,
					new NullProgressMonitor());
			workspace.save(true, new NullProgressMonitor());

			final IJobManager manager = Job.getJobManager();
			int i = 0;
			while (!manager.isIdle()) {
				OrccLogger.traceln("Waiting for completion of"
						+ " currently running jobs - " + ++i);
				Thread.sleep(500);
			}
		} catch (CoreException e) {
			OrccLogger.severeln(e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			OrccLogger.severeln(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			OrccLogger.severeln(e.getMessage());
			e.printStackTrace();
		} finally {
			try {

				if (wasAutoBuildEnabled) {
					CommandLineUtil.enableAutoBuild(workspace);
					wasAutoBuildEnabled = false;
				}

				OrccLogger.traceln("End of frontend execution");
				return IApplication.EXIT_OK;

			} catch (CoreException e) {
				OrccLogger.severeln(e.getMessage());
				e.printStackTrace();
			}
		}

		return IApplication.EXIT_RESTART;
	}

	/**
	 * Parse the command line and initialize the project to work with. If a
	 * network qualified name is passed in cli arguments, initialize the
	 * networkFile member.
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

		project = workspace.getRoot().getProject(args[0]);
		if (project == null) {
			OrccLogger.severeln("Unable to find the project " + args[0]);
			OrccLogger.traceln(USAGE);
			return false;
		}

		if (args.length >= 2 && !args[1].isEmpty()) {
			networkFile = OrccUtil.getFile(project, args[1],
					OrccUtil.NETWORK_SUFFIX);
			if (networkFile == null) {
				OrccLogger.severeln("Unable to find the network " + args[1]);
			}
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
			OrccLogger.severeln("Project " + project.getName()
					+ " is not a Java project.");
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
	 * <p>
	 * Returns the EMF resource corresponding to the given file. The file must
	 * exists in the workspace.
	 * </p>
	 * 
	 * <p>
	 * Returned resource is fully loaded (it should not contains proxy objects)
	 * </p>
	 * 
	 * @param file
	 * @return A loaded EMF resource
	 */
	private Resource getResource(final IFile file) {
		if (!file.exists()) {
			OrccLogger.severeln("File " + file.getFullPath().toString()
					+ " does not exists !");
			return null;
		}
		final URI uri = URI.createPlatformResourceURI(file.getFullPath()
				.toString(), true);
		return resourceSet.getResource(uri, true);
	}

	/**
	 * <p>
	 * Parse the given <em>netFile</em> network file, and use the given
	 * <em>workspaceMap</em> to build a list of actors it references.
	 * </p>
	 * 
	 * <p>
	 * Fill the given <em>files</em> Multimap with all actors referenced in the
	 * network (and its sub-networks) and all units imported in these actors.
	 * Each Multimap entry is indexed by the project where the corresponding
	 * IFile is stored.
	 * </p>
	 * 
	 * @param netFile
	 *            An IFile instance, containing a XDF network
	 * @param workspaceMap
	 *            A map of all workspace files, indexed by their qualified name
	 * @param files
	 *            The resulting Multimap, containing all files needed to build
	 *            the network, indexed by the project of each file.
	 * @throws FileNotFoundException
	 */
	private void storeReferencedEntities(final IFile netFile,
			final Map<String, IFile> workspaceMap,
			final Multimap<IProject, Resource> files)
			throws FileNotFoundException {

		final Document document = DomUtil.parseDocument(new FileInputStream(
				netFile.getLocation().toFile()));
		final Element root = document.getDocumentElement();

		final NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			final Node child = children.item(i);
			if (child.getNodeType() != Node.ELEMENT_NODE) {
				// Only ELEMENT nodes in XDF file
				continue;
			}

			final Element tag = (Element) child;
			if (tag.getNodeName().equals(XdfConstants.INSTANCE_TAG)) {
				final NodeList instChildren = tag.getChildNodes();
				for (int j = 0; j < instChildren.getLength(); ++j) {
					final Node instChild = instChildren.item(j);
					if (instChild.getNodeType() != Node.ELEMENT_NODE)
						continue;

					final Element classElement = (Element) instChild;
					if (classElement.getNodeName().equals(
							XdfConstants.CLASS_TAG)) {
						final String qualifiedName = classElement
								.getAttribute(XdfConstants.NAME_ATTR);

						if (workspaceMap.containsKey(qualifiedName)) {
							final IFile file = workspaceMap.get(qualifiedName);
							if (OrccUtil.NETWORK_SUFFIX.equals(file
									.getFileExtension())) {
								// Run this method on the sub-network
								storeReferencedEntities(file, workspaceMap,
										files);
							} else {
								// Store the imports of the CAL actor
								storeImportedResources(file, files);
								// Store the CAL Actor itself
								files.put(file.getProject(), getResource(file));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Stores in given <em>resultMap</em> all Units files imported by the given
	 * <em>calFile</em>.
	 * 
	 * @param calFile
	 * @param resultMap
	 */
	private void storeImportedResources(final IFile calFile,
			final Multimap<IProject, Resource> resultMap) {

		final AstEntity astEntity = (AstEntity) getResource(calFile)
				.getContents().get(0);
		final EList<Import> imports = astEntity.getImports();
		for (final Import imp : imports) {

			final String namespace = imp.getImportedNamespace();
			final String qname = namespace.substring(0,
					namespace.lastIndexOf('.'));

			final IFile importedFile = OrccUtil.getFile(project, qname,
					OrccUtil.CAL_SUFFIX);

			// The imported file can import files itself
			storeImportedResources(importedFile, resultMap);

			resultMap.put(importedFile.getProject(), getResource(importedFile));
		}
	}

	/**
	 * Unused
	 */
	@Override
	public void stop() {
	}

}
