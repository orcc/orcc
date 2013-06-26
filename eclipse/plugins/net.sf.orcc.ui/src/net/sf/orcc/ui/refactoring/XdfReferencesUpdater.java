/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
import java.util.Map.Entry;

import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class updates references to a given file in all XDF files of the
 * workspace.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfReferencesUpdater {

	private Map<IFile, String> changedFiles;

	private IFile networkFile;

	public XdfReferencesUpdater(IFile file) {
		networkFile = file;
	}

	public void checkConditions(CheckConditionsContext context) {
		// create map for changed files
		changedFiles = new HashMap<IFile, String>();

		// retrieve delta factory
		ResourceChangeChecker checker = (ResourceChangeChecker) context
				.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = checker
				.getDeltaFactory();

		// get qualified name of network file
		String qualifiedName = OrccUtil.getQualifiedName(networkFile);

		// fill map and signal changes to the factory
		IProject project = networkFile.getProject();
		List<IFolder> folders = OrccUtil.getSourceFolders(project);
		List<IFile> files = OrccUtil.getAllFiles("xdf", folders);
		for(IProject relativeProject : project.getReferencingProjects()){
			folders = OrccUtil.getSourceFolders(relativeProject);
			files.addAll(OrccUtil.getAllFiles("xdf", folders));
		}
			
		try {
			for (IFile file : files) {
				InputStream in = file.getContents();
				String contents = OrccUtil.getContents(in);
				in.close();
				if (contents.contains(qualifiedName)) {
					changedFiles.put(file, contents);
					deltaFactory.change(file);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Change createChange(String newQualifiedName) {
		String qualifiedName = OrccUtil.getQualifiedName(networkFile);
		int length = qualifiedName.length();

		// composite change
		CompositeChange change = new CompositeChange("Replace references to '"
				+ qualifiedName + "' by references to '" + newQualifiedName
				+ "'");

		// update references
		for (Entry<IFile, String> entry : changedFiles.entrySet()) {
			IFile file = entry.getKey();
			String contents = entry.getValue();

			int offset = contents.indexOf(qualifiedName);
			if (offset != -1) {
				TextFileChange textChange = new TextFileChange(
						"Update references to " + qualifiedName, file);
				change.add(textChange);

				MultiTextEdit multiEdit = new MultiTextEdit();
				textChange.setEdit(multiEdit);
				while (offset != -1) {
					ReplaceEdit edit = new ReplaceEdit(offset, length,
							newQualifiedName);
					multiEdit.addChild(edit);
					offset = contents.indexOf(qualifiedName, offset + length);
				}

			}
		}

		return change;
	}

}
