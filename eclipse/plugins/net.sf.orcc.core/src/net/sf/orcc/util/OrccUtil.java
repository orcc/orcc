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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.df.Actor;

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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * This class contains utility methods for dealing with resources.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccUtil {

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

	private static void findFiles(String fileExt, List<IFile> vtlFiles,
			IFolder vtl) throws CoreException {
		for (IResource resource : vtl.members()) {
			if (resource.getType() == IResource.FOLDER) {
				findFiles(fileExt, vtlFiles, (IFolder) resource);
			} else if (resource.getType() == IResource.FILE
					&& resource.getFileExtension().equals(fileExt)) {
				vtlFiles.add((IFile) resource);
			}
		}
	}

	/**
	 * Returns all the files with the given extension in the given folders.
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
	 * Returns the list of ALL source folders of the required projects as well
	 * as of the given project as a list of absolute workspace paths.
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

	public static String getContents(InputStream in) throws IOException {
		StringBuilder builder = new StringBuilder();
		int n = in.available();
		while (n > 0) {
			byte[] bytes = new byte[n];
			n = in.read(bytes);
			String str = new String(bytes, 0, n);
			builder.append(str);
			n = in.available();
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
	 * Returns the file name that corresponds to the qualified name of the
	 * actor/unit.
	 * 
	 * @param entity
	 *            an actor/unit
	 * @return the file name that corresponds to the qualified name of the actor
	 */
	public static String getFile(String name) {
		return name.replace('.', '/');
	}

	/**
	 * Returns the folder that corresponds to the package of the given actor.
	 * 
	 * @param actor
	 *            an actor
	 * @return the folder that corresponds to the package of the given actor
	 */
	public static String getFolder(Actor actor) {
		return actor.getPackage().replace('.', '/');
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
		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return null;
		}

		IPath path;
		try {
			path = javaProject.getOutputLocation();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			return root.getFolder(path);
		} catch (JavaModelException e) {
			return null;
		}
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
	 * Loads the given group and recursively loads the groups it imports.
	 * 
	 * @param group
	 *            a ST group
	 */
	private static void loadGroup(STGroup group) {
		group.load();
		for (STGroup importedGroup : group.getImportedGroups()) {
			loadGroup(importedGroup);
		}
	}

	/**
	 * Loads a template group.
	 * 
	 * @param groupName
	 *            the name of the group to load
	 * @return a StringTemplate group
	 */
	public static STGroup loadGroup(String fullPath, ClassLoader cl) {
		ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(cl);

			STGroup group = new STGroupFile(fullPath);
			loadGroup(group);
			return group;
		} finally {
			// restore class loader even if there is an unexpected exception
			Thread.currentThread().setContextClassLoader(oldCl);
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

}
