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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * @author Antoine Lorence
 * 
 */
public class ChangesFactory {

	interface Replacement {
		boolean isConcerned(final String content);

		List<ReplaceEdit> getReplacements(final String content);
	}

	class StandardReplacement implements Replacement {
		private String pattern;
		private String replacement;

		public StandardReplacement(String p, String r) {
			pattern = p;
			replacement = r;
		}

		@Override
		public boolean isConcerned(final String content) {
			return content.contains(pattern);
		}

		@Override
		public List<ReplaceEdit> getReplacements(final String content) {
			final List<ReplaceEdit> replacements = new ArrayList<ReplaceEdit>();
			int idx = 0;
			while ((idx = content.indexOf(pattern, idx + 1)) != -1) {
				replacements.add(new ReplaceEdit(idx, pattern.length(),
						replacement));
				idx += pattern.length();
			}
			return replacements;
		}
	}

	class RegexpReplacement implements Replacement {
		private Pattern pattern;
		private String replacement;

		public RegexpReplacement(Pattern p, String r) {
			pattern = p;
			replacement = r;
		}

		@Override
		public boolean isConcerned(final String content) {
			return pattern.matcher(content).find();
		}

		@Override
		public List<ReplaceEdit> getReplacements(final String content) {
			final List<ReplaceEdit> replacements = new ArrayList<ReplaceEdit>();
			final Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				int s = matcher.start();
				int e = matcher.end();
				final String replaced = pattern
						.matcher(content.substring(s, e)).replaceAll(
								replacement);
				replacements.add(new ReplaceEdit(s, e - s, replaced));
			}
			return replacements;
		}
	}

	final private Map<Pattern, String> regexpReplacements;
	final private Map<String, String> simpleReplacements;

	final private Multimap<String, Replacement> replacements;
	final private Map<IFile, TextEdit> results;

	public ChangesFactory() {
		regexpReplacements = new HashMap<Pattern, String>();
		simpleReplacements = new HashMap<String, String>();
		replacements = HashMultimap.create();
		results = new HashMap<IFile, TextEdit>();
	}

	@Deprecated
	public void addReplacement(final Pattern pattern, final String replacement) {
		regexpReplacements.put(pattern, replacement);
	}

	@Deprecated
	public void addReplacement(final String search, final String replacement) {
		simpleReplacements.put(search, replacement);
	}

	public void addReplacement(final String suffix, final Pattern pattern,
			final String replacement) {
		replacements.put(suffix, new RegexpReplacement(pattern, replacement));
	}

	public void addReplacement(final String suffix, final String pattern,
			final String replacement) {
		replacements.put(suffix, new StandardReplacement(pattern, replacement));
	}

	public Change getUniqueFileReplacement(final IFile file, final Pattern pattern, final String repl) {
		final Replacement replacement = new RegexpReplacement(pattern, repl);
		final String content = FilesManager.readFile(file.getRawLocation().toString());
		final MultiTextEdit edits = new MultiTextEdit();
		if(replacement.isConcerned(content)) {
			for(ReplaceEdit edit : replacement.getReplacements(content)) {
				edits.addChild(edit);
			}
		}

		final TextFileChange result = new TextFileChange("qsd", file);
		result.setEdit(edits);
		return result;
	}

	private void clearReplacementMaps() {
		regexpReplacements.clear();
		simpleReplacements.clear();

		replacements.clear();
		results.clear();
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
		for (Map.Entry<Pattern, String> replacement : regexpReplacements
				.entrySet()) {
			result = replacement.getKey().matcher(result)
					.replaceAll(replacement.getValue());
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

		for (final Pattern pattern : regexpReplacements.keySet()) {
			if (pattern.matcher(content).find()) {
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
	@Deprecated
	public Change getReplacementChange(final IFile file,
			final String changeTitle) {
		final String content = FilesManager.readFile(file.getRawLocation()
				.toString());
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
	@Deprecated
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

	public void computeResults(final IProject project) {
		final List<IFolder> folders = OrccUtil
				.getAllDependingSourceFolders(project);
		List<IFile> files;
		for (String suffix : replacements.keySet()) {
			files = OrccUtil.getAllFiles(suffix, folders);
			for (IFile file : files) {
				String content = FilesManager.readFile(file.getRawLocation()
						.toString());
				for (Replacement replaceInfo : replacements.get(suffix)) {
					if (replaceInfo.isConcerned(content)) {
						TextEdit textEdit = results.get(file);
						if (textEdit == null) {
							textEdit = new MultiTextEdit();
							results.put(file, textEdit);
						}
						for (ReplaceEdit replaceEdit : replaceInfo
								.getReplacements(content)) {
							textEdit.addChild(replaceEdit);
						}
					}
				}
			}
		}
	}

	public Change getAllChanges() {
		final CompositeChange result = new CompositeChange("THENAME");
		for (Entry<IFile, TextEdit> entry : results.entrySet()) {
			final IFile file = entry.getKey();
			final TextFileChange fileChange = new TextFileChange("Changes to "
					+ file.getName(), file);
			fileChange.setEdit(entry.getValue());
			result.add(fileChange);
		}
		clearReplacementMaps();
		return result;
	}
}
