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
package net.sf.orcc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.orcc.OrccProjectNature;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * This class contains utility methods for dealing with resources.
 * 
 * @author Matthieu Wipliez
 * @author Antoine Lorence
 * 
 */
public class OrccUtil {

	public static final String IR_SUFFIX = "ir";
	public static final String CAL_SUFFIX = "cal";
	public static final String NETWORK_SUFFIX = "xdf";
	public static final String DIAGRAM_SUFFIX = "xdfdiag";

	public static final String PROJECT_OUTPUT_DIR = "bin";

	/**
	 * Creates a new file if needed and returns its path
	 * 
	 * @param path
	 *            the path of the file
	 * @param fileName
	 *            the name of the file
	 * @return the full path to the file
	 */
	public static String createFile(String path, String fileName) {
		// checks output folder exists, and if not creates it
		String newPath = path + File.separator + fileName;
		File file = new File(newPath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newPath;
	}

	/**
	 * If it does not exist, creates the given folder. If the parent folders do
	 * not exist either, create them.
	 * 
	 * @param folder
	 *            a folder
	 * @throws CoreException
	 */
	public static void createFolder(IFolder folder) throws CoreException {
		IPath path = folder.getFullPath();
		if (folder.exists()) {
			return;
		}

		int n = path.segmentCount();
		if (n < 2) {
			throw new IllegalArgumentException("the path of the given folder "
					+ "must have at least two segments");
		}

		// check project
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(path.segment(0));
		if (!project.exists()) {
			project.create(null);
		}

		// open project
		if (!project.isOpen()) {
			project.open(null);
		}

		// check folder
		folder = root.getFolder(path.uptoSegment(2));
		if (!folder.exists()) {
			folder.create(true, true, null);
		}

		// and then check all the descendants
		for (int i = 2; i < n; i++) {
			folder = folder.getFolder(new Path(path.segment(i)));
			if (!folder.exists()) {
				folder.create(true, true, null);
			}
		}
	}

	/**
	 * Creates a new folder if needed and returns its path
	 * 
	 * @param path
	 *            the path of the folder
	 * @param fileName
	 *            the name of the folder
	 * @return the full path to the folder
	 */
	public static String createFolder(String path, String folderName) {
		// checks output folder exists, and if not creates it
		String newPath = path + File.separator + folderName;
		File folder = new File(newPath);
		if (!folder.exists()) {
			folder.mkdir();
		}
		return newPath;
	}

	/**
	 * Search in given folder for files resources with given suffix, and add
	 * them to the given files list
	 * 
	 * @param suffix
	 * @param files
	 * @param folder
	 * @throws CoreException
	 */
	private static void findFiles(final String suffix,
			final List<IFile> files, final IFolder folder) throws CoreException {
		for (IResource resource : folder.members()) {
			if (resource.getType() == IResource.FOLDER) {
				findFiles(suffix, files, (IFolder) resource);
			} else if (resource.getType() == IResource.FILE
					&& resource.getFileExtension().equals(suffix)) {
				files.add((IFile) resource);
			}
		}
	}

	/**
	 * Returns all the files with the given extension found in the given
	 * folders.
	 * 
	 * @param srcFolders
	 *            a list of folders
	 * @return a list of files
	 */
	public static List<IFile> getAllFiles(String fileExt,
			List<IFolder> srcFolders) {
		List<IFile> vtlFiles = new ArrayList<IFile>();
		try {
			for (IFolder folder : srcFolders) {
				findFiles(fileExt, vtlFiles, folder);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		// sort them by name
		Collections.sort(vtlFiles, new Comparator<IFile>() {

			@Override
			public int compare(IFile f1, IFile f2) {
				return f1.getFullPath().toOSString()
						.compareTo(f2.getFullPath().toOSString());
			}

		});

		return vtlFiles;
	}

	/**
	 * Returns the list of IFolder containing:
	 * <ul>
	 * <li>Source folders of the given project</li>
	 * <li>Source folders of the projects the given project depends on</li>
	 * </ul>
	 * 
	 * @param project
	 *            a project
	 * @return a list of absolute workspace paths
	 * @throws CoreException
	 */
	public static List<IFolder> getAllSourceFolders(IProject project) {
		List<IFolder> srcFolders = new ArrayList<IFolder>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return srcFolders;
		}

		// add source folders of this project
		srcFolders.addAll(getSourceFolders(project));

		// add source folders of required projects
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			for (String name : javaProject.getRequiredProjectNames()) {
				IProject refProject = root.getProject(name);
				srcFolders.addAll(getAllSourceFolders(refProject));
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return srcFolders;
	}

	/**
	 * Returns the list of IFolder containing:
	 * <ul>
	 * <li>Source folders of the given project</li>
	 * <li>Source folders of the projects depending on the given project</li>
	 * </ul>
	 * 
	 * @param project
	 * @return
	 */
	public static List<IFolder> getAllDependingSourceFolders(
			final IProject project) {
		final List<IFolder> srcFolders = new ArrayList<IFolder>();
		srcFolders.addAll(getSourceFolders(project));

		for (final IProject dependingProject : getReferencingProjects(project)) {
			srcFolders.addAll(getSourceFolders(dependingProject));
		}

		return srcFolders;
	}

	/**
	 * Read the given stream and return its content as a String
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static String getContents(InputStream stream) throws IOException {
		StringBuilder builder = new StringBuilder();
		int n = stream.available();
		while (n > 0) {
			byte[] bytes = new byte[n];
			n = stream.read(bytes);
			String str = new String(bytes, 0, n);
			builder.append(str);
			n = stream.available();
		}

		return builder.toString();
	}

	/**
	 * Returns a new string that is an escaped version of the given string.
	 * Espaced means that '\\', '\n', '\r', '\t' are replaced by "\\\\", "\\n",
	 * "\\r", "\\t" respectively.
	 * 
	 * @param string
	 *            a string
	 * @return a new string that is an escaped version of the given string
	 */
	public static String getEscapedString(String string) {
		StringBuilder builder = new StringBuilder(string.length());
		for (int i = 0; i < string.length(); i++) {
			char chr = string.charAt(i);
			switch (chr) {
			case '\\':
				builder.append("\\\\");
				break;
			case '"':
				builder.append("\"");
				break;
			case '\n':
				builder.append("\\n");
				break;
			case '\r':
				builder.append("\\r");
				break;
			case '\t':
				builder.append("\\t");
				break;
			default:
				builder.append(chr);
				break;
			}
		}

		return builder.toString();
	}

	/**
	 * Returns the file in the given project that has the given qualified name
	 * and the given extension. Looks in source folders first and then output
	 * folders.
	 * 
	 * @param project
	 *            project
	 * @param qualifiedName
	 *            qualified name of a network
	 * @return if there is such a network, a file, otherwise <code>null</code>
	 */
	public static IFile getFile(IProject project, String qualifiedName,
			String extension) {
		String name = qualifiedName.replace('.', '/');
		IPath path = new Path(name).addFileExtension(extension);

		for (IFolder folder : getAllSourceFolders(project)) {
			IFile inputFile = folder.getFile(path);
			if (inputFile != null && inputFile.exists()) {
				return inputFile;
			}
		}

		for (IFolder folder : getOutputFolders(project)) {
			IFile inputFile = folder.getFile(path);
			if (inputFile != null && inputFile.exists()) {
				return inputFile;
			}
		}

		return null;
	}

	/**
	 * Returns the output folder of the given project.
	 * 
	 * @param project
	 *            a project
	 * @return the output folder of the given project, or <code>null</code> if
	 *         none is found
	 */
	public static IFolder getOutputFolder(IProject project) {
		return project.getFolder(PROJECT_OUTPUT_DIR);
	}

	/**
	 * Returns the output locations of the given project and the project it
	 * references in its build path.
	 * 
	 * @param project
	 *            a project
	 * @return the output location of the given project, or an empty list
	 */
	public static List<IFolder> getOutputFolders(IProject project) {
		List<IFolder> vtlFolders = new ArrayList<IFolder>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return vtlFolders;
		}

		// add output folders of this project
		IFolder outputFolder = getOutputFolder(project);
		if (outputFolder != null) {
			vtlFolders.add(outputFolder);
		}

		// add output folders of required projects
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			for (String name : javaProject.getRequiredProjectNames()) {
				IProject refProject = root.getProject(name);
				outputFolder = getOutputFolder(refProject);
				if (outputFolder != null) {
					vtlFolders.add(outputFolder);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return vtlFolders;
	}

	/**
	 * Returns the qualified name of the given file, i.e. qualified.name.of.File
	 * for <code>/project/sourceFolder/qualified/name/of/File.fileExt</code> or
	 * <code>/project/outputFolder/qualified/name/of/File.fileExt</code>.
	 * 
	 * @param file
	 *            a file
	 * @return a qualified name, or <code>null</code> if the file is not in a
	 *         source folder
	 */
	public static String getQualifiedName(IFile file) {
		IProject project = file.getProject();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return null;
		}

		try {
			IPath path = file.getParent().getFullPath();
			IPackageFragment fragment = null;
			if (javaProject.getOutputLocation().isPrefixOf(path)) {
				// create relative path
				int count = path.matchingFirstSegments(javaProject
						.getOutputLocation());
				IPath relPath = path.removeFirstSegments(count);

				// creates full path to source
				for (IFolder folder : getSourceFolders(project)) {
					path = folder.getFullPath().append(relPath);
					fragment = javaProject.findPackageFragment(path);
					if (fragment != null) {
						break;
					}
				}
			} else {
				fragment = javaProject.findPackageFragment(path);
			}

			if (fragment == null) {
				return null;
			}

			String name = file.getFullPath().removeFileExtension()
					.lastSegment();
			if (fragment.isDefaultPackage()) {
				// handles the default package case
				return name;
			}
			return fragment.getElementName() + "." + name;
		} catch (JavaModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the qualified package of the given file, i.e. qualified.name.of
	 * for <code>/project/sourceFolder/qualified/name/of/File.fileExt</code>
	 * 
	 * @param file
	 *            a file
	 * @return a qualified name, or <code>null</code> if the file is not in a
	 *         source folder
	 */
	public static String getQualifiedPackage(IFile file) {
		IProject project = file.getProject();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return null;
		}

		try {
			IPath path = file.getParent().getFullPath();
			IPackageFragment fragment = javaProject.findPackageFragment(path);
			return fragment.getElementName();
		} catch (JavaModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the list of source folders of the given project as a list of
	 * absolute workspace paths.
	 * 
	 * @param project
	 *            a project
	 * @return a list of absolute workspace paths
	 */
	public static List<IFolder> getSourceFolders(IProject project) {
		List<IFolder> srcFolders = new ArrayList<IFolder>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return srcFolders;
		}

		// iterate over package roots
		try {
			for (IPackageFragmentRoot root : javaProject
					.getPackageFragmentRoots()) {
				IResource resource = root.getCorrespondingResource();
				if (resource != null && resource.getType() == IResource.FOLDER) {
					srcFolders.add((IFolder) resource);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return srcFolders;
	}

	/**
	 * Returns a list of projects which depends on the given project.
	 * 
	 * @param project
	 * @return
	 */
	public static Set<IProject> getReferencingProjects(final IProject project) {

		final Set<IProject> result = new HashSet<IProject>();
		final IWorkspaceRoot wpRoot = ResourcesPlugin.getWorkspace().getRoot();
		// Check all projects in the workspace root
		for (final IProject wpProject : wpRoot.getProjects()) {
			try {
				// Keep only open Orcc projects, different from the given
				// project
				if (!wpProject.isOpen()
						|| !project.hasNature(OrccProjectNature.NATURE_ID)
						|| wpProject == project) {
					continue;
				}
				// Keep only valid Java projects
				final IJavaProject wpJavaProject = JavaCore.create(wpProject);
				if (!wpJavaProject.exists()) {
					// This should never happen
					continue;
				}
				// Loop over all classpath entries of the wpJavaProject
				for (final String requiredProject : wpJavaProject
						.getRequiredProjectNames()) {
					// The wpJavaProject require the given IProject
					if (wpRoot.getProject(requiredProject).equals(project)) {
						result.add(wpProject);
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * Returns a new string that is an unescaped version of the given string.
	 * Unespaced means that "\\\\", "\\n", "\\r", "\\t" are replaced by '\\',
	 * '\n', '\r', '\t' respectively.
	 * 
	 * @param string
	 *            a string
	 * @return a new string that is an unescaped version of the given string
	 */
	public static String getUnescapedString(String string) {
		StringBuilder builder = new StringBuilder(string.length());
		boolean escape = false;
		for (int i = 0; i < string.length(); i++) {
			char chr = string.charAt(i);
			if (escape) {
				switch (chr) {
				case '\\':
					builder.append('\\');
					break;
				case 'n':
					builder.append('\n');
					break;
				case 'r':
					builder.append('\r');
					break;
				case 't':
					builder.append('\t');
					break;
				default:
					// we could throw an exception here
					builder.append(chr);
					break;
				}
				escape = false;
			} else {
				if (chr == '\\') {
					escape = true;
				} else {
					builder.append(chr);
				}
			}
		}

		return builder.toString();
	}

	/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param target
	 *            file to write content to
	 * @return true if the file has correctly been written
	 */
	public static boolean printFile(CharSequence content, File target) {
		try {
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(target));
			ps.print(content);
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			OrccLogger.severe("Unable to write file " + target.getPath()
					+ " : " + e.getCause());
			OrccLogger.severe(e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Run an external programs with the given commands list
	 * 
	 * @param cmdList
	 *            the list of command containing the program and its arguments
	 */
	public static void runExternalProgram(List<String> cmdList) {
		try {
			ProcessBuilder builder = new ProcessBuilder(cmdList);
			Process process = builder.start();
			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = new String();
			while ((line = reader.readLine()) != null) {
				OrccLogger.traceln(line);
			}
		} catch (Exception e) {
			OrccLogger.severeln(e.getMessage());
		}
	}

	/**
	 * Sets the contents of the given file, creating it if it does not exist.
	 * 
	 * @param file
	 *            a file
	 * @param source
	 *            an input stream
	 * @throws CoreException
	 */
	public static void setFileContents(IFile file, InputStream source)
			throws CoreException {
		if (file.exists()) {
			file.setContents(source, true, false, null);
		} else {
			IContainer container = file.getParent();
			if (container.getType() == IResource.FOLDER) {
				createFolder((IFolder) container);
			}
			file.create(source, true, null);
		}
	}

	/**
	 * Returns a string that contains all objects separated with the given
	 * separator.
	 * 
	 * @param objects
	 *            an iterable of objects
	 * @param sep
	 *            a separator string
	 * @return a string that contains all objects separated with the given
	 *         separator
	 */
	public static String toString(Iterable<? extends Object> objects, String sep) {
		StringBuilder builder = new StringBuilder();
		Iterator<? extends Object> it = objects.iterator();
		if (it.hasNext()) {
			builder.append(it.next());
			while (it.hasNext()) {
				builder.append(sep);
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

	/**
	 * Validate the given object according to the specification of its model.
	 * Knowing that the object will be deeply inspected by the validator, this
	 * method may be time consuming on top-level objects. This method is useful
	 * to validate the OCL-based constraints and invariants that have been
	 * specified in the model. The validation errors and warning are transmitted
	 * directly to the OrccLogger.
	 * 
	 * @param headDiagMsg
	 *            a message to preface all diagnostic message displayed by the
	 *            logger
	 * @param eObject
	 *            the object to validate
	 * @return <code>true</code>if the given object is valid, <code>false</code>
	 *         otherwise
	 */
	public static boolean validateObject(String headDiagMsg, EObject eObject) {
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObject);
		if (diagnostic.getSeverity() == Diagnostic.ERROR
				|| diagnostic.getSeverity() == Diagnostic.WARNING) {
			for (Diagnostic childDiag : diagnostic.getChildren()) {
				Diagnostic childDiagnostic = childDiag;
				switch (childDiagnostic.getSeverity()) {
				case Diagnostic.ERROR:
				case Diagnostic.WARNING:
					OrccLogger.warnln(headDiagMsg + " :"
							+ childDiagnostic.getMessage());
				}
			}
			return false;
		}
		return true;
	}
}
