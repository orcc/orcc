/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.ui.refactoring;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * 
 * @author Antoine Lorence
 * 
 */
public class ChangesFactory {

	final private ResourceSet resourceSet;

	final private Map<String, String> regexpReplacements;
	final private Map<String, String> simpleReplacements;

	public ChangesFactory() {
		resourceSet = new ResourceSetImpl();
		regexpReplacements = new HashMap<String, String>();
		simpleReplacements = new HashMap<String, String>();
	}

	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	public void addRegexpReplacement(final String pattern,
			final String replacement) {
		regexpReplacements.put(pattern, replacement);
	}

	public void addReplacement(final String search,
			final String replacement) {
		simpleReplacements.put(search, replacement);
	}

	public void clearReplacementMaps() {
		regexpReplacements.clear();
		simpleReplacements.clear();
	}

	/**
	 * Returns the content of the given file as String or null if an error
	 * occurred (file not found, given IFile is not a file, etc.).
	 * 
	 * @param file
	 *            The file content
	 * @return
	 */
	private String getFileContent(final IFile file) {
		try {
			final InputStream inputStream = file.getContents();
			final String fileContent = OrccUtil.getContents(inputStream);
			inputStream.close();
			return fileContent;
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
		return null;
	}

	/**
	 * Perform replacements in given subject
	 * 
	 * @param subject
	 * @return
	 */
	private String performReplacement(final String subject) {
		if (subject == null) {
			return null;
		}
		String result = subject;
		for (Map.Entry<String, String> replacement : regexpReplacements
				.entrySet()) {
			result = result.replaceAll(replacement.getKey(),
					replacement.getValue());
		}

		for (Map.Entry<String, String> replacement : simpleReplacements
				.entrySet()) {
			result = result.replace(replacement.getKey(),
					replacement.getValue());
		}

		return result;
	}

	/**
	 * Check if the given content contains text impacted by replacements
	 * previously stored
	 * 
	 * @param content
	 * @return
	 */
	private boolean contentNeedsUpdate(final String content) {

		for (final String patternString : regexpReplacements.keySet()) {
			final Pattern pattern = Pattern.compile(patternString);
			if (pattern.matcher(content).matches()) {
				return true;
			}
		}

		for (final String searchString : simpleReplacements.keySet()) {
			if (content.contains(searchString)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Build a Change object for replacement on given file.
	 * 
	 * @param file
	 * @param changeTitle
	 *            The built Change label
	 * @return A Change object, or null if no replacement can be applied
	 */
	public Change getReplacementChange(final IFile file,
			final String changeTitle) {
		final String content = getFileContent(file);
		if (contentNeedsUpdate(content)) {
			final String newContent = performReplacement(content);
			final TextFileChange textFileChange = new TextFileChange(
					changeTitle, file);
			textFileChange.setEdit(new ReplaceEdit(0, content.length(),
					newContent));
			return textFileChange;
		}
		return null;
	}

	/**
	 * Build a Change object containing all replacements to perform in all files
	 * with given suffix, contained in given project (and its referenced
	 * projects).
	 * 
	 * @param project
	 *            The base project to search files
	 * @param suffix
	 *            The suffix of files to apply changes
	 * @param changeTitle
	 *            The built Change label
	 * @return A multi-files Change object, or null if no file have to be
	 *         updated
	 */
	public Change getReplacementChange(final IProject project,
			final String suffix, final String changeTitle) {
		final CompositeChange changes = new CompositeChange(changeTitle);

		final List<IFile> files = OrccUtil.getAllFiles(suffix,
				OrccUtil.getAllDependingSourceFolders(project));

		for (final IFile file : files) {
			changes.add(getReplacementChange(file, "replacement"));
		}

		return changes.getChildren().length > 0 ? changes : null;
	}
}
