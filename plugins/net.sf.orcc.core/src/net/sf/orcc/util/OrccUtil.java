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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.Actor;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * This class contains utility methods for dealing with resources.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrccUtil {

	/**
	 * Returns the list of ALL source folders of the required projects as well
	 * as of the given project as a list of absolute workspace paths.
	 * 
	 * @param project
	 *            a project
	 * @return a list of absolute workspace paths
	 * @throws CoreException
	 */
	public static List<IFolder> getAllSourceFolders(IProject project)
			throws CoreException {
		List<IFolder> srcFolders = new ArrayList<IFolder>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return srcFolders;
		}

		// add source folders of required projects
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (String name : javaProject.getRequiredProjectNames()) {
			IProject refProject = root.getProject(name);
			srcFolders.addAll(getAllSourceFolders(refProject));
		}

		// add source folders of this project
		srcFolders.addAll(getSourceFolders(project));

		return srcFolders;
	}

	/**
	 * Returns a new string that is an escaped version of the given string.
	 * Espaced means that '\\', '\n', '\r', '\t' are replaced by "\\\\, "\\n",
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
	 * Returns the file name that corresponds to the qualified name of the
	 * actor.
	 * 
	 * @param actor
	 *            an actor
	 * @return the file name that corresponds to the qualified name of the actor
	 */
	public static String getFile(Actor actor) {
		return actor.getName().replace('.', '/');
	}

	/**
	 * Returns the folder that corresponds to the package of the given actor.
	 * 
	 * @param actor
	 *            an actor
	 * @return the folder that corresponds to the package of the given actor
	 */
	public static String getFolder(Actor actor) {
		return actor.getPackage().replace('.', '\\');
	}

	/**
	 * Returns the output location of the given project.
	 * 
	 * @param project
	 *            a project
	 * @return the output location of the given project, or <code>null</code> if
	 *         none is found
	 */
	public static String getOutputFolder(IProject project) {
		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return null;
		}

		IPath path;
		try {
			path = javaProject.getOutputLocation();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			return root.getFile(path).getLocation().toOSString();
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
	public static List<String> getOutputFolders(IProject project)
			throws CoreException {
		List<String> vtlFolders = new ArrayList<String>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return vtlFolders;
		}

		// add output folders of required projects
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (String name : javaProject.getRequiredProjectNames()) {
			IProject refProject = root.getProject(name);
			String outputFolder = getOutputFolder(refProject);
			if (outputFolder != null) {
				vtlFolders.add(outputFolder);
			}
		}

		// add output folders of this project
		String outputFolder = getOutputFolder(project);
		if (outputFolder != null) {
			vtlFolders.add(outputFolder);
		}

		return vtlFolders;
	}

	/**
	 * Returns the list of source folders of the given project as a list of
	 * absolute workspace paths.
	 * 
	 * @param project
	 *            a project
	 * @return a list of absolute workspace paths
	 * @throws CoreException
	 */
	public static List<IFolder> getSourceFolders(IProject project)
			throws CoreException {
		List<IFolder> srcFolders = new ArrayList<IFolder>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return srcFolders;
		}

		// iterate over package roots
		for (IPackageFragmentRoot root : javaProject.getPackageFragmentRoots()) {
			IResource resource = root.getCorrespondingResource();
			if (resource != null && resource.getType() == IResource.FOLDER) {
				srcFolders.add((IFolder) resource);
			}
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
